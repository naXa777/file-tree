package com.exadel.filetree.service;

import com.exadel.filetree.data.FileIndex;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: naXa!
 * Date: 24.06.13
 * Time: 15:36
 */
@Service
public class SpringDBService extends AbstractService {
    private JdbcTemplate jdbcTemplate;

    /**
     * Default constructor
     */
    public SpringDBService() {
        super();
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    /**
     * Setter for jdbcTemplate (used in Spring context)
     * @param jdbcTemplate reference to initialized jdbcTemplate object
     */
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Check. Method tells us whether directory was described before or not.
     *
     * @param dir reference to a directory
     * @return <code>TRUE</code>, if description for directory is present in database; <code>FALSE</code> otherwise
     */
    @Override
    public boolean wasSerialized(File dir) throws ServiceException {
        if (dir == null)
            throw new ServiceException( "Directory not found." );
        int N;

        try {
            N = this.jdbcTemplate.queryForObject( "SELECT COUNT(ID) FROM root_folder " +
                                                  "WHERE (Path = ?)", new Object[] {dir.getAbsolutePath()}, Integer.class );
        } catch (DataAccessException exc) {
            throw new ServiceException( "Data access denied.", exc );
        }

        return (N > 0);
    }

    /**
     * Serialization. Method stores directory description (current state) into DB.
     *
     * @param description  description of directory content
     * @param dir reference to a directory
     */
    @Override
    public void saveDescription(Set<FileIndex> description, File dir) throws ServiceException {
        final String clearQuery = "CALL clearFolder(?)";
        final String insertQuery= "CALL insertFile(?, ?, ?, ?, ?)";
        String parentName = dir.getAbsolutePath();

        try {
            // clear old file indices
            jdbcTemplate.update( clearQuery, parentName );

            for (FileIndex fi : description) {
                // insert - into file_index
                String filename = fi.getFullName();
                // representing date-time in string format
                SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
                String modifDateTime = sdf.format( fi.getLastModif() );
                Long size = fi.getSize();
                String type = fi.isFile()? "File" : "Directory";
                jdbcTemplate.update( insertQuery, filename, modifDateTime, size, type, parentName );
            }
        } catch (DataAccessException exc) {
            throw new ServiceException( "Data access denied.", exc );
        }
    }

    /**
     * Deserialization. Method gets saved directory description from DB.
     *
     * @param dir reference to a directory
     * @return description of directory content
     */
    @Override
    public Set<FileIndex> loadDescription(File dir) throws ServiceException {
        final String query = "SELECT Filename, LastModification, Size, Type " +
                             "FROM file_index AS fi, root_folder AS rf " +
                             "WHERE ((fi.Parent = rf.ID) AND (rf.Path = ?))";

        try {
            return new HashSet<FileIndex>( jdbcTemplate.query(
                    query,
                    new RowMapper<FileIndex>() {
                        @Override
                        public FileIndex mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                            FileIndex fileIndex = new FileIndex();
                            fileIndex.setFullName(resultSet.getString("Filename"));
                            fileIndex.setLastModif(resultSet.getTimestamp("LastModification"));
                            fileIndex.setFile(resultSet.getString("Type").equals("File"));
                            if (fileIndex.isFile())
                                fileIndex.setSize(resultSet.getLong("Size"));
                            return fileIndex;
                        }
                    },
                    dir.getAbsolutePath()) );
        } catch (DataAccessException exc) {
            throw new ServiceException( "Data access denied.", exc );
        }
    }
}
