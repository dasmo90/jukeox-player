package de.dasmo90.jukeox.player.ui;

import de.dasmo90.jukeox.player.model.api.AudioPlayer;
import de.dasmo90.jukeox.player.model.exception.AudioPlayerException;
import de.dasmo90.jukeox.player.model.impl.AudioPlayerProvider;
import org.apache.log4j.Logger;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.Console;
import java.util.Scanner;

/**
 * Created by dasmo90 on 12.11.2015.
 */
public class ConsoleUI {

	private static final Logger LOGGER = Logger.getLogger(ConsoleUI.class);

	private AudioPlayer audioPlayer = AudioPlayerProvider.getAudioPlayerInstance();

	public void start() {

		LOGGER.info("[UI Console] UI console activated.");
		LOGGER.info("[UI Console] Type \"play\", \"pause\" or \"stop\" to control media.");
		LOGGER.info("[UI Console] Type \"exit\" to UI Console.");

		Scanner in = new Scanner(System.in);

		while (true) {

			String line = in.nextLine();

			try {
				switch (line.toLowerCase()) {
					case "play":

						audioPlayer.play();
						break;
					case "pause":

						audioPlayer.pause();
						break;
					case "stop":

						audioPlayer.stop();
						break;
					case "exit":

						LOGGER.info("[UI Console] Shutting down console.");

						AudioPlayerProvider.shutdown();

						return;
					default:

						LOGGER.info("[UI Console] Unknown command: \"" + line.toLowerCase() + "\".");
				}
			} catch (AudioPlayerException audioPlayerException) {

				LOGGER.error("[UI Console] Error: ", audioPlayerException);
			}

		}
	}
}
