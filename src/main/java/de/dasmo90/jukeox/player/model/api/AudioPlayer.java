package de.dasmo90.jukeox.player.model.api;

import de.dasmo90.jukeox.player.model.exception.AudioPlayerException;

/**
 * @author dasmo90
 */
public interface AudioPlayer {

	void play() throws AudioPlayerException;

	void pause() throws AudioPlayerException;

	void stop() throws AudioPlayerException;

	void setPlaylist(Playlist playlist);

	void addAudioPlayerListener(AudioPlayerListener audioPlayerListener);

	void removeAudioPlayerListener(AudioPlayerListener audioPlayerListener);

	State getState();

	enum State {
		PLAYING, PAUSED, STOPPED
	}
}
