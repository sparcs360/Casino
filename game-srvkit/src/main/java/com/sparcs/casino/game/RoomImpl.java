package com.sparcs.casino.game;

import java.util.ArrayList;
import java.util.List;

import com.sparcs.casino.Casino;
import com.sparcs.casino.Customer;

/**
 * The base implementation of a gaming room within the {@link Casino}.
 * 
 * @author Lee Newfeld
 *
 * @param <G> The {@link Game} subclass that the room hosts 
 */
public class RoomImpl<G extends Game> implements Room<G> {

	private G game;
	
    private List<Customer> spectators;
	
	public RoomImpl(G game) {

		this.game = game;
        spectators = new ArrayList<>();
	}

	@Override
	public G getGame() {
		
		return game;
	}

	@Override
	public List<Customer> getSpectators() {

        return spectators;
	}

	@Override
	public boolean isEmpty() {

		return spectators.isEmpty() && game.getPlayers().isEmpty();
	}

	@Override
	public void enter(Customer customer) {

        spectators.add(customer);
	}

	@Override
	public void leave(Customer customer) {

        spectators.remove(customer);
	}
}
