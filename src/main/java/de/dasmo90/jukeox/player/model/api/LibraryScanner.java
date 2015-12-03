package de.dasmo90.jukeox.player.model.api;

import de.dasmo90.jukeox.player.model.exception.FileIsNoFolderException;

import java.io.File;
import java.util.List;

/**
 * Created by dasmo90 on 03.12.2015.
 */
public interface LibraryScanner {

	void addScannedPath(File file) throws FileIsNoFolderException;

	List<File> getScannedPaths();

	Hierarchy getHierarchy();
}
