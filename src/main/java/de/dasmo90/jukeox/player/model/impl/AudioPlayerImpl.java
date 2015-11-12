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

	private volatile boolean initialized = false;

	private State state = null;

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

	public synchronized void play() throws AudioPlayerException {

		checkInitialized();

		if(State.PAUSED.equals(state) || State.STOPPED.equals(state)) {

			mediaPlayer.play();

			return;
		}

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

				state = State.PLAYING;

				for (AudioPlayerListener audioPlayerListener : audioPlayerListeners) {

					audioPlayerListener.onStarted(currentlyPlayedSong);
				}

			});

			mediaPlayer.setOnStopped(() -> {

				state = State.STOPPED;

				for (AudioPlayerListener audioPlayerListener : audioPlayerListeners) {

					audioPlayerListener.onStopped(currentlyPlayedSong);
				}

			});

			mediaPlayer.setOnHalted(() -> {

				for (AudioPlayerListener audioPlayerListener : audioPlayerListeners) {

					audioPlayerListener.onHalted(currentlyPlayedSong);
				}
			});

			mediaPlayer.setOnPaused(() -> {

				state = State.PAUSED;

				for (AudioPlayerListener audioPlayerListener : audioPlayerListeners) {

					audioPlayerListener.onPaused(currentlyPlayedSong);
				}
			});

			mediaPlayer.setOnError(() -> {

				for (AudioPlayerListener audioPlayerListener : audioPlayerListeners) {

					audioPlayerListener.onError(currentlyPlayedSong);
				}
			});

			mediaPlayer.setOnEndOfMedia(() -> {

				// TODO: Set state and playlist.

				for (AudioPlayerListener audioPlayerListener : audioPlayerListeners) {

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

	public synchronized void pause() throws AudioPlayerException {

		checkInitialized();

		if(State.PLAYING.equals(state)) {

			mediaPlayer.pause();
		}

	}

	public synchronized void stop() throws AudioPlayerException {

		checkInitialized();

		if(!State.STOPPED.equals(state)) {

			mediaPlayer.stop();
		}

	}

	public State getState() {

		return state;
	}

	@Override
	public void onPlaylistEnded() {

		LOGGER.info("Playlist ended.");
	}

	@Override
	public void onStarted(Song song) {

		LOGGER.info("Playing \""+song.getFile().getAbsolutePath()+"\"");
		LOGGER.info("State: " + state);
	}

	@Override
	public void onStopped(Song song) {

		LOGGER.info("Stopped \""+song.getFile().getAbsolutePath()+"\"");
		LOGGER.info("State: " + state);
	}

	@Override
	public void onPaused(Song song) {

		LOGGER.info("Paused \""+song.getFile().getAbsolutePath()+"\"");
		LOGGER.info("State: " + state);
	}

	@Override
	public void onHalted(Song song) {

		LOGGER.info("Halted \""+song.getFile().getAbsolutePath()+"\"");
		LOGGER.info("State: " + state);
	}

	@Override
	public void onError(Song song) {

		LOGGER.error("Error playing \"" + song.getFile().getAbsolutePath() + "\"");
		LOGGER.info("State: " + state);
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
