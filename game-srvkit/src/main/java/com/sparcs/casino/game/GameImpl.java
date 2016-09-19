package com.sparcs.casino.game;

import java.util.ArrayList;
import java.util.List;

import com.sparcs.casino.Casino;
import com.sparcs.casino.Customer;

/**
 * The base implementation of a game within the {@link Casino}.
 * Subclass this to build your own game!
 * 
 * @author Lee Newfeld
 */
public class GameImpl implements Game {

	private Room<? extends Game> room;
	private List<Customer> players;
	
    public GameImpl() {

        players = new ArrayList<>();
    }
	
	@Override
	public void installInto(Room<? extends Game> room) {

		this.room = room;
	}

	@Override
	public List<Customer> getPlayers() {

		return players;
	}

	@Override
	public void join(Customer player) {

        room.getSpectators().remove(player);
        players.add(player);
	}

	@Override
	public void leave(Customer player) {

        players.remove(player);
        room.getSpectators().add(player);
	}

	@Override
	public boolean isRunning() {

		return false;
	}
}
