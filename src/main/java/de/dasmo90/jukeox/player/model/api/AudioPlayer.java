package de.dasmo90.jukeox.player.model.api;

import de.dasmo90.jukeox.player.model.exception.AudioPlayerException;

/**
 * @author dasmo90
 */
public interface AudioPlayer {

	void play() throws AudioPlayerException;

	void setPlaylist(Playlist playlist);

	void pause();

	void stop();

}
