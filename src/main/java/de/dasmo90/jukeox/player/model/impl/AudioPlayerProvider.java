package de.dasmo90.jukeox.player.model.impl;

import de.dasmo90.jukeox.player.model.api.AudioPlayer;
import de.dasmo90.jukeox.player.model.api.AudioPlayerListener;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

/**
 * Created by mbuerger on 07.11.2015.
 */
public class AudioPlayerProvider {

	private volatile static boolean initializing = false;

	private static Initializer initializer;

	public static AudioPlayer getAudioPlayerInstance() {

		return AUDIO_PLAYER_INSTANCE;
	}

	public static AudioPlayer getAudioPlayerInstance(AudioPlayerListener audioPlayerListener) {

		AUDIO_PLAYER_INSTANCE.addAudioPlayerListener(audioPlayerListener);

		return AUDIO_PLAYER_INSTANCE;
	}

	private AudioPlayerProvider() {

	}

	public static void shutdown() {

		Platform.exit();
	}

	public static void initialize(Initializer initializer) {

		synchronized (AudioPlayerProvider.class) {

			if (AUDIO_PLAYER_INSTANCE.isInitialized() || initializing) {

				throw new IllegalStateException(
						"Audio player already initialized.");
			}

			initializing = true;
		}
		AudioPlayerProvider.initializer = initializer;

		Application.launch(AudioPlayerProvider.JavaFXInitializer.class);
	}

	private static AudioPlayerImpl AUDIO_PLAYER_INSTANCE = new AudioPlayerImpl();

	public static class JavaFXInitializer extends Application {

		@Override
		public void init() throws Exception {

			super.init();
		}

		@Override
		public void stop() throws Exception {

			super.stop();

			AUDIO_PLAYER_INSTANCE.setInitialized(false);

			AudioPlayerProvider.initializer = null;
		}

		@Override
		public void start(Stage primaryStage) throws Exception {

			AUDIO_PLAYER_INSTANCE.setInitialized(true);

			AudioPlayerProvider.initializing = false;

			AudioPlayerProvider.initializer.onInitialized();
		}
	}

	public interface Initializer {

		void onInitialized();
	}
}
