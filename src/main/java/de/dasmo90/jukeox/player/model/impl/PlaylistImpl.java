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

	public void addSong(Song song) {

		songs.add(song);
	}

	@Override
	public Song getPlayedSong() throws AudioPlayerException {

		if(songs.isEmpty()) {

			throw new AudioPlayerException("Playlist is empty");
		}
		return songs.get(0);
	}
}
