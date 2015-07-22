package com.exadel.filetree.service;

import com.exadel.filetree.data.ChangeDescription;
import com.exadel.filetree.data.FileIndex;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created with IntelliJ IDEA.
 * User: naXa!
 * Date: 20.06.13
 * Time: 22:02
 */
@Service
abstract class AbstractService implements IService {
     AbstractService() {
    }

    /**
     * Method <code>describeIt</code> describes all files
     * and directories in root directory <code>dir</code>;
     *
     *
     * @param dir Root directory for describing
     * @return A set of descriptions for found files and directories
     */
    @Override
    public Set<FileIndex> describeIt(File dir) {
        if (dir == null)
            return null;
        Set<FileIndex> result = new HashSet<FileIndex>();

        if (dir.isDirectory()) {
            File content[] = dir.listFiles();
            if (content != null)
                for (File f : content)
                    result.add( new FileIndex( f ) );
            return result;
        } else {
            return null;
        }
    }

    /**
     * Comparison of two states: state1 & state2;
     * @param state1 Previous state
     * @param state2 Current state
     * @return Complete report about file changes.
     */
    @Override
    public Set<ChangeDescription> compareStates(Set<FileIndex> state1, Set<FileIndex> state2) {
        if (state1 == null || state2 == null)
            return null;
        Set<ChangeDescription> result = new TreeSet<ChangeDescription>();

        for (FileIndex fileIndex : state2) {
            if ( fileIndex.getFullName().isEmpty() )  // skip an empty record
                continue;
            if (state1.contains( fileIndex )) {
                for (FileIndex alterFileIndex : state1)
                    if (fileIndex.equals( alterFileIndex )) {
                            // Saved date of modification (rounded to seconds)
                        Date date1 = new Date( alterFileIndex.getLastModif().getTime() / 1000 * 1000 );
                            // Date of modification for present file (rounded to seconds)
                        Date date2 = new Date( fileIndex.getLastModif().getTime() / 1000 * 1000 );

                        if (date2.after( date1 ))
                            result.add( new ChangeDescription( alterFileIndex,
                                            ChangeDescription.Type.MODIFIED ) );
                        else if (date2.equals( date1 ))
                            result.add( new ChangeDescription( alterFileIndex,
                                            ChangeDescription.Type.NOT_CHANGED ) );
                        else    // дата изменения перенеслась в прошлое
                            result.add( new ChangeDescription( alterFileIndex,
                                            ChangeDescription.Type.ROLLBACKED ) );
                        break;
                    }
            } else
                result.add( new ChangeDescription( fileIndex, ChangeDescription.Type.CREATED ) );
        }
        for (FileIndex fileIndex : state1)
            if ( !state2.contains( fileIndex ) )
                result.add( new ChangeDescription( fileIndex, ChangeDescription.Type.DELETED ) );

        return result;
    }

}
