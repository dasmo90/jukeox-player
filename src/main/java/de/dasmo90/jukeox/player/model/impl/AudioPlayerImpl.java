package de.dasmo90.jukeox.player.model.impl;

import de.dasmo90.jukeox.player.model.api.AudioPlayer;
import de.dasmo90.jukeox.player.model.api.Playlist;
import de.dasmo90.jukeox.player.model.api.Song;
import de.dasmo90.jukeox.player.model.exception.AudioPlayerException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import org.apache.log4j.Logger;

import java.io.File;
import java.net.MalformedURLException;

/**
 * @author dasmo90
 */
public class AudioPlayerImpl implements AudioPlayer {

	private static final Logger LOGGER = Logger.getLogger(AudioPlayerImpl.class);

	private Playlist activePlaylist;

	public static AudioPlayer getInstance() {

		return INSTANCE;
	}

	private AudioPlayerImpl() {

	}

	private static AudioPlayer INSTANCE = new AudioPlayerImpl();

	MediaPlayer mediaPlayer;

	public void play() throws AudioPlayerException {

		Song song = activePlaylist.getPlayedSong();

		File soundFile = song.getFile();

		try {

			Media hit = new Media(soundFile.toURI().toURL().toString());
			mediaPlayer = new MediaPlayer(hit);
			mediaPlayer.play();

			mediaPlayer.setOnStopped(() -> {
				LOGGER.info("Stopped");
			});

			mediaPlayer.setOnHalted(() -> {
				LOGGER.info("Halted");
			});

			mediaPlayer.setOnPaused(() -> {
				LOGGER.info("Paused");
			});

			mediaPlayer.setOnError(() -> {
				LOGGER.error("Error");
			});

			mediaPlayer.setOnEndOfMedia(() -> {
				LOGGER.info("End of Media.");

				Platform.exit();
			});

		} catch (MalformedURLException e) {

			LOGGER.error(e.getMessage(), e);
		}
	}

	public void setPlaylist(Playlist playlist) {

		activePlaylist = playlist;
	}

	public void pause() {

	}

	public void stop() {

	}
}
