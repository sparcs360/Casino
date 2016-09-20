package com.sparcs.casino.game;

import java.util.List;

/**
 * Represents a Hall of {@link Room}s hosting {@link Game}s.
 *  
 * @author Lee Newfeld
 */
public interface Hall {

	/**
	 * @return The {@link Room}s in this Hall.
	 */
    List<Room> getRooms();
}
