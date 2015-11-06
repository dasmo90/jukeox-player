package de.dasmo90.jukeox.player.model.impl;

import de.dasmo90.jukeox.player.model.api.AudioPlayer;
import de.dasmo90.jukeox.player.model.api.Playlist;
import de.dasmo90.jukeox.player.model.api.Song;
import de.dasmo90.jukeox.player.model.exception.AudioPlayerException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.io.File;
import java.net.MalformedURLException;

/**
 * @author dasmo90
 */
public class AudioPlayerImpl implements AudioPlayer {

	private static final Logger LOGGER = Logger.getLogger(AudioPlayerImpl.class);

	private boolean initialized = false;

	private Initializer initializer;

	private Playlist activePlaylist;

	public static AudioPlayer getInstance() {

		if(!INSTANCE.initialized) {

			throw new RuntimeException(
					"Audio player not initialized. Please call \"initialize\" and wait for the callback.");
		}

		return INSTANCE;
	}

	private AudioPlayerImpl() {

	}

	public static void initialize(Initializer initializer) {

		INSTANCE.initializer = initializer;

		Application.launch(AudioPlayerImpl.JavaFXInitializer.class);
	}

	private static AudioPlayerImpl INSTANCE = new AudioPlayerImpl();

	MediaPlayer mediaPlayer;

	public void play() throws AudioPlayerException {

		Song song = activePlaylist.getPlayedSong();

		File soundFile = song.getFile();

		try {

			String mediaUrl = soundFile.toURI().toURL().toString();
			Media hit = new Media(mediaUrl);
			mediaPlayer = new MediaPlayer(hit);

			LOGGER.info("Playing \""+mediaUrl+"\".");
			mediaPlayer.play();

			// Register callbacks.
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

			throw new AudioPlayerException(e);
		}
	}

	public void setPlaylist(Playlist playlist) {

		activePlaylist = playlist;
	}

	public void pause() {

	}

	public void stop() {

	}

	public static class JavaFXInitializer extends Application {

		@Override
		public void init() throws Exception {

			super.init();
		}

		@Override
		public void stop() throws Exception {

			super.stop();
		}

		@Override
		public void start(Stage primaryStage) throws Exception {

			INSTANCE.initialized = true;

			INSTANCE.initializer.onInitialized();
		}
	}

	public interface Initializer {

		void onInitialized();
	}
}
