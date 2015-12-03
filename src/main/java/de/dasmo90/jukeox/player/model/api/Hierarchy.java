package de.dasmo90.jukeox.player.model.api;

import java.util.List;

/**
 * Created by mbuerger on 03.12.2015.
 */
public interface Hierarchy {

	String getDisplayName();

	Type getType();

	List<Hierarchy> getSubHierarchies();

	List<Song> getSongs();

	enum Type {
		SONG, ALBUM, ARTIST, FOLDER, GENRE
	}
}
