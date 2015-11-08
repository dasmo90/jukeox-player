package de.dasmo90.jukeox.player.model.impl;

import de.dasmo90.jukeox.player.model.api.Playlist;
import de.dasmo90.jukeox.player.model.api.Song;
import de.dasmo90.jukeox.player.model.exception.AudioPlayerException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dasmo90
 */
public class PlaylistImpl implements Playlist {

	private List<Song> songs = new ArrayList<>();

	private int currentIndex = 0;

	public void addSong(Song song) {

		songs.add(song);
	}

	@Override
	public Song getNextSong() throws AudioPlayerException {

		if(currentIndex < songs.size()) {

			return null;
		}
		return songs.get(currentIndex++);
	}
}
