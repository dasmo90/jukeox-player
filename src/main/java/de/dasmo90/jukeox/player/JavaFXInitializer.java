package de.dasmo90.jukeox.player;

import javafx.application.Application;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

/**
 * Created by mbuerger on 06.11.2015.
 */
public class JavaFXInitializer extends Application {

	private static final Logger LOGGER = Logger.getLogger(JavaFXInitializer.class);

	@Override
	public void init() throws Exception {

		LOGGER.info("Application initialized.");

		super.init();
	}

	@Override
	public void stop() throws Exception {

		LOGGER.info("Application stopped.");

		super.stop();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		LOGGER.info("Application started.");

		JukeOxPlayer.onInitialized(primaryStage);
	}
}
