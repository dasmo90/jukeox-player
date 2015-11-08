package de.dasmo90.jukeox.player.model.api;

import de.dasmo90.jukeox.player.model.exception.AudioPlayerException;

/**
 * @author dasmo90
 */
public interface Playlist {

	void addSong(Song song);

	Song getNextSong() throws AudioPlayerException;

}
