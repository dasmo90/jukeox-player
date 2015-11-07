package de.dasmo90.jukeox.player;

import de.dasmo90.jukeox.player.model.api.AudioPlayer;
import de.dasmo90.jukeox.player.model.api.AudioPlayerListener;
import de.dasmo90.jukeox.player.model.api.Playlist;
import de.dasmo90.jukeox.player.model.api.Song;
import de.dasmo90.jukeox.player.model.exception.AudioPlayerException;
import de.dasmo90.jukeox.player.model.impl.AudioPlayerProvider;
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
	private static AudioPlayer AUDIO_PLAYER = AudioPlayerProvider.getAudioPlayerInstance(new AudioPlayerListener() {

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

			LOGGER.info("Song ended: \""+song.getFile().getAbsolutePath()+"\"");

			AudioPlayerProvider.shutdown();
		}
	});
	/**
	 * Point of entry.
	 *
	 * @param args program arguments
	 */
	public static void main(String[] args) {

		LOGGER.info("JukeOx Player started!");

		AudioPlayerProvider.initialize(() -> {

			Playlist playlist = new Playlist() {

				@Override
				public void addSong(Song song) {

				}

				@Override
				public Song getPlayedSong() throws AudioPlayerException {
					return () -> new File(
							"src" + File.separator +
							"main" + File.separator +
							"resources" + File.separator +
							"test.mp3");
				}
			};

			AUDIO_PLAYER.setPlaylist(playlist);

			try {

				AUDIO_PLAYER.play();

			} catch (AudioPlayerException e) {

				LOGGER.error("Error in main method.", e);
			}

		});

		LOGGER.info("JukeOx Player stopped.");
	}
}
