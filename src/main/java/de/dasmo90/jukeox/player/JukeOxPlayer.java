package de.dasmo90.jukeox.player;

import de.dasmo90.jukeox.player.model.api.AudioPlayer;
import de.dasmo90.jukeox.player.model.api.Playlist;
import de.dasmo90.jukeox.player.model.api.Song;
import de.dasmo90.jukeox.player.model.exception.AudioPlayerException;
import de.dasmo90.jukeox.player.model.impl.AudioPlayerImpl;
import javafx.application.Application;
import javafx.stage.Stage;
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
	 * Point of entry.
	 *
	 * @param args program arguments
	 */
	public static void main(String[] args) {

		LOGGER.info("JukeOx Player started!");

		AudioPlayerImpl.initialize(() -> {

			Playlist playlist = new Playlist() {

				@Override
				public void addSong(Song song) {

				}

				@Override
				public Song getPlayedSong() throws AudioPlayerException {
					return () -> new File("src\\main\\resources\\test.mp3");
				}
			};

			AudioPlayerImpl.getInstance().setPlaylist(playlist);

			try {

				AudioPlayerImpl.getInstance().play();

			} catch (AudioPlayerException e) {

				LOGGER.error("Error in main method.", e);
			}

		});

		LOGGER.info("JukeOx Player stopped.");
	}
}
