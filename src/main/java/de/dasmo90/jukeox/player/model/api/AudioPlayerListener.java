package de.dasmo90.jukeox.player.model.api;

/**
 * Created by mbuerger on 07.11.2015.
 */
public interface AudioPlayerListener {

	void onStarted(Song song);

	void onStopped(Song song);

	void onPaused(Song song);

	void onHalted(Song song);

	void onError(Song song);

	void onSongEnded(Song song);

	void onPlaylistEnded();
}
