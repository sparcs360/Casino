package com.sparcs.casino.game;

import com.sparcs.casino.Casino;

/**
 * The base implementation of a gaming room within the {@link Casino}.
 * Subclass this when building your own game.
 * 
 * @author Lee Newfeld
 *
 * @param <G> The {@link Game} subclass that the room hosts 
 */
public class RoomImpl<G extends Game> implements Room<G> {

	private G game;
	
	public RoomImpl(G game) {

		this.game = game;
	}

	@Override
	public G getGame() {
		
		return game;
	}
}
