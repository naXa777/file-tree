package com.exadel.filetree.data;

/**
 * Created with IntelliJ IDEA.
 * User: naXa!
 * Date: 23.06.13
 * Time: 12:36
 */

public class ChangeDescription implements Comparable<ChangeDescription> {
    public enum Type{NOT_CHANGED, MODIFIED, CREATED, DELETED, ROLLBACKED}

    private FileIndex fileIndex;
    private Type change;

    public ChangeDescription(FileIndex fileIndex) {
        this( fileIndex, Type.NOT_CHANGED );
    }

    public ChangeDescription(FileIndex fileIndex, Type change) {
        this.fileIndex = fileIndex;
        this.change = change;
    }

    public Type getChange() {
        return change;
    }

    public boolean isFile() {
        return fileIndex.isFile();
    }

    public String getFilename() {
        return fileIndex.getFileName();
    }

    public String getParent() {
        return fileIndex.getPath();
    }

    public Long getSize() {
        return fileIndex.getSize();
    }

    @Override
    public String toString() {
        switch (change) {
            case NOT_CHANGED:
                return "NO CHANGES";
            case MODIFIED:
                return "MODIFIED";
            case CREATED:
                return "NEW FILE";
            case DELETED:
                return "DELETED";
            case ROLLBACKED:
                return "REPLACED WITH ITS PREVIOUS VERSION";
            default:
                return "[NOTHING]";
        }
    }

    @Override
    public int compareTo(ChangeDescription that) {
        if ((this.isFile() && that.isFile()) ||
                (!this.isFile() && !that.isFile()))
            return this.getFilename().compareTo( that.getFilename() );
        else
            return this.isFile()? 1 : -1;
    }
}
