package de.dasmo90.jukeox.player.model.impl;

import de.dasmo90.jukeox.player.model.api.Song;

import java.io.File;

/**
 * Created by mbuerger on 14.11.2015.
 */
public class SongImpl implements Song {

	/**
	 * The file.
	 */
	private File file;

	/**
	 * Sets the file.
	 *
	 * @param file the file
	 */
	public void setFile(File file) {

		this.file = file;
	}

	@Override
	public File getFile() {

		return file;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Song song = (Song) o;

		return file.getAbsolutePath().equals(song.getFile().getAbsolutePath());

	}

	@Override
	public int hashCode() {
		return file.getAbsolutePath().hashCode();
	}
}
