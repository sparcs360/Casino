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
    
    /**
     * Have each {@link Room} execute a single cycle of its game loop
     * 
     * @return true if any games are running
     */
	boolean executeGameLoops();
}
