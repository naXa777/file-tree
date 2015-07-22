package com.exadel.filetree.service;

import com.exadel.filetree.data.ChangeDescription;
import com.exadel.filetree.data.FileIndex;

import java.io.File;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: naXa!
 * Date: 20.06.13
 * Time: 21:36
 */
public interface IService {
    public Set<FileIndex> describeIt(File dir);
    public void saveDescription(Set<FileIndex> description, File dir) throws ServiceException;
    public Set<FileIndex> loadDescription(File dir) throws ServiceException;
    public Set<ChangeDescription> compareStates(Set<FileIndex> state1, Set<FileIndex> state2);
    public boolean wasSerialized(File dir) throws ServiceException;
}
