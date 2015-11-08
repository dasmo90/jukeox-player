package de.dasmo90.jukeox.player.model.impl;

import de.dasmo90.jukeox.player.model.api.AudioPlayer;
import de.dasmo90.jukeox.player.model.api.AudioPlayerListener;
import de.dasmo90.jukeox.player.model.api.Playlist;
import de.dasmo90.jukeox.player.model.api.Song;
import de.dasmo90.jukeox.player.model.exception.AudioPlayerException;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import org.apache.log4j.Logger;

import java.io.File;
import java.net.MalformedURLException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author dasmo90
 */
public final class AudioPlayerImpl implements AudioPlayer, AudioPlayerListener {

	private static final Logger LOGGER = Logger.getLogger(AudioPlayerImpl.class);

	private boolean initialized = false;

	private Song currentlyPlayedSong;

	private Set<AudioPlayerListener> audioPlayerListeners;

	AudioPlayerImpl() {

		audioPlayerListeners = new HashSet<>();
		audioPlayerListeners.add(this);
	}

	private Playlist activePlaylist;

	private MediaPlayer mediaPlayer;

	boolean isInitialized() {

		return initialized;
	}

	void setInitialized(boolean initialized) {

		this.initialized = initialized;
	}

	private void checkInitialized() throws AudioPlayerException {

		if(!initialized) {

			throw new AudioPlayerException(
					"Audio player not initialized. " +
							"Please call \"AudioPlayerProvider.initialize\" and wait for the callback.");
		}
	}

	public void play() throws AudioPlayerException {

		checkInitialized();

		currentlyPlayedSong = activePlaylist.getNextSong();

		if(currentlyPlayedSong == null) {

			for(AudioPlayerListener audioPlayerListener : audioPlayerListeners) {

				audioPlayerListener.onPlaylistEnded();
			}

			return;
		}

		File soundFile = currentlyPlayedSong.getFile();

		try {

			String mediaUrl = soundFile.toURI().toURL().toString();
			Media hit = new Media(mediaUrl);
			mediaPlayer = new MediaPlayer(hit);

			// Register callbacks.
			mediaPlayer.setOnPlaying(() -> {

				for(AudioPlayerListener audioPlayerListener : audioPlayerListeners) {

					audioPlayerListener.onStarted(currentlyPlayedSong);
				}
			});

			mediaPlayer.setOnStopped(() -> {

				for(AudioPlayerListener audioPlayerListener : audioPlayerListeners) {

					audioPlayerListener.onStopped(currentlyPlayedSong);
				}
			});

			mediaPlayer.setOnHalted(() -> {

				for(AudioPlayerListener audioPlayerListener : audioPlayerListeners) {

					audioPlayerListener.onHalted(currentlyPlayedSong);
				}
			});

			mediaPlayer.setOnPaused(() -> {

				for(AudioPlayerListener audioPlayerListener : audioPlayerListeners) {

					audioPlayerListener.onPaused(currentlyPlayedSong);
				}
			});

			mediaPlayer.setOnError(() -> {

				for(AudioPlayerListener audioPlayerListener : audioPlayerListeners) {

					audioPlayerListener.onError(currentlyPlayedSong);
				}
			});

			mediaPlayer.setOnEndOfMedia(() -> {

				for(AudioPlayerListener audioPlayerListener : audioPlayerListeners) {

					audioPlayerListener.onSongEnded(currentlyPlayedSong);
				}
			});

			mediaPlayer.play();

		} catch (MalformedURLException e) {

			throw new AudioPlayerException(e);
		}
	}

	public void setPlaylist(Playlist playlist) {

		activePlaylist = playlist;
	}

	@Override
	public void addAudioPlayerListener(AudioPlayerListener audioPlayerListener) {

		audioPlayerListeners.add(audioPlayerListener);
	}

	@Override
	public void removeAudioPlayerListener(AudioPlayerListener audioPlayerListener) {

		audioPlayerListeners.remove(audioPlayerListener);
	}

	public void pause() throws AudioPlayerException {

		checkInitialized();

		mediaPlayer.pause();
	}

	public void stop() throws AudioPlayerException {

		checkInitialized();

		mediaPlayer.stop();
	}


	@Override
	public void onPlaylistEnded() {

		LOGGER.info("Playlist ended.");
	}

	@Override
	public void onStarted(Song song) {

		LOGGER.info("Playing \""+song.getFile().getAbsolutePath()+"\"");
	}

	@Override
	public void onStopped(Song song) {

		LOGGER.info("Stopped \""+song.getFile().getAbsolutePath()+"\"");
	}

	@Override
	public void onPaused(Song song) {

		LOGGER.info("Paused \""+song.getFile().getAbsolutePath()+"\"");
	}

	@Override
	public void onHalted(Song song) {

		LOGGER.info("Halted \""+song.getFile().getAbsolutePath()+"\"");
	}

	@Override
	public void onError(Song song) {

		LOGGER.error("Error playing \"" + song.getFile().getAbsolutePath() + "\"");
	}

	@Override
	public void onSongEnded(Song song) {

		try {

			play();

		} catch (AudioPlayerException e) {

			LOGGER.error("Error playing next song.", e);
		}
	}
}
