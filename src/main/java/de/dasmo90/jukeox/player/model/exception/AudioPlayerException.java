package de.dasmo90.jukeox.player.model.exception;

/**
 * Created by mbuerger on 06.11.2015.
 */
public class AudioPlayerException extends Exception {

	public AudioPlayerException(String message) {
		super(message);
	}

	public AudioPlayerException(Exception e) {
		super(e);
	}
}
