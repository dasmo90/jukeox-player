package de.dasmo90.jukeox.player.model.impl;

import de.dasmo90.jukeox.player.model.api.AudioPlayerListener;
import de.dasmo90.jukeox.player.model.api.Song;

/**
 * Created by mbuerger on 08.11.2015.
 */
public abstract class AudioPlayerCallback implements AudioPlayerListener {

	@Override
	public void onStarted(Song song) {

	}

	@Override
	public void onStopped(Song song) {

	}

	@Override
	public void onPaused(Song song) {

	}

	@Override
	public void onHalted(Song song) {

	}

	@Override
	public void onError(Song song) {

	}

	@Override
	public void onSongEnded(Song song) {

	}

	@Override
	public void onPlaylistEnded() {

	}
}
