package com.sparcs.casino.game;

import com.sparcs.casino.Casino;

/**
 * The specification for a gaming room within the {@link Casino}.
 * 
 * @author Lee Newfeld
 *
 * @param <G> The {@link Game} subclass that the room hosts
 */
public interface Room<G extends Game> {

	/**
	 * @return The {@link Game} inside the room.
	 */
	G getGame();
}
