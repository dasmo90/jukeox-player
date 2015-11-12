package de.dasmo90.jukeox.player;

import de.dasmo90.jukeox.player.model.api.AudioPlayer;
import de.dasmo90.jukeox.player.model.api.Playlist;
import de.dasmo90.jukeox.player.model.api.Song;
import de.dasmo90.jukeox.player.model.exception.AudioPlayerException;
import de.dasmo90.jukeox.player.model.impl.AudioPlayerCallback;
import de.dasmo90.jukeox.player.model.impl.AudioPlayerProvider;
import de.dasmo90.jukeox.player.model.impl.PlaylistImpl;
import de.dasmo90.jukeox.player.ui.UI;
import org.apache.log4j.Logger;

import java.io.File;

/**
 * Main class of the JukeOx Player application.
 *
 * @author dasmo90
 */
public class JukeOxPlayer {

	/**
	 * Logger for the Main class.
	 */
	private static final Logger LOGGER = Logger.getLogger(JukeOxPlayer.class);

	/**
	 * A test audio player instance.
	 */
	private static AudioPlayer AUDIO_PLAYER = AudioPlayerProvider.getAudioPlayerInstance();
	/**
	 * Point of entry.
	 *
	 * @param args program arguments
	 */
	public static void main(String[] args) {

		LOGGER.info("JukeOx Player started!");

		UI.start();

		AudioPlayerProvider.initialize(() -> {

			AUDIO_PLAYER.setPlaylist(new PlaylistImpl() {

				@Override
				public Song getNextSong() throws AudioPlayerException {
					return () -> new File(
							"src" + File.separator +
									"main" + File.separator +
									"resources" + File.separator +
									"test.mp3");
				}
			});
			/*
			try {

				AUDIO_PLAYER.play();

			} catch (AudioPlayerException e) {

				LOGGER.error("Error in main method.", e);
			}
			*/
		});

		LOGGER.info("JukeOx Player stopped.");
	}
}
