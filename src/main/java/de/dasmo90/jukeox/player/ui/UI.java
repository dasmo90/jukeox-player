package de.dasmo90.jukeox.player.ui;

import de.dasmo90.jukeox.player.model.impl.AudioPlayerProvider;

/**
 * Created by dasmo90 on 12.11.2015.
 */
public class UI {

	public static void start() {

		new Thread(() -> {

			ConsoleUI consoleUI = new ConsoleUI();
			consoleUI.start();

		}).start();
	}
}
