package com.sparcs.casino.game;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.sparcs.casino.Casino;
import com.sparcs.casino.Customer;

/**
 * The base implementation of a gaming room within the {@link Casino}.
 * 
 * @author Lee Newfeld
 */
public abstract class RoomImpl implements Room {

	private final Logger log = LoggerFactory.getLogger(getClass());
	
	private int tick;

	/**
	 * The {@link GameImpl} inside the {@link Room}
	 */
	@Autowired
	private GameImpl game;
	
    private List<Customer> spectators;
	
    /**
     * Constructor
     */
	protected RoomImpl() {

        spectators = new ArrayList<>();
	}
	
	@PostConstruct
	private void initialise() {

		game.onReset(this);
	}

	@Override
	public Game getGame() {
		
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
	public void join(Customer player) {

        getSpectators().remove(player);
        game.getPlayers().add(player);
	}

	@Override
	public void leave(Customer player) {

        game.getPlayers().remove(player);
        getSpectators().add(player);
	}

	@Override
	public void exit(Customer customer) {

        spectators.remove(customer);
	}

	/**
	 * @return The current game time
	 */
	public int getTick() {

		return tick;
	}

    @Override
	public boolean executeGameLoop() {

		if( isEmpty() ) {
			log.debug("{}: Empty - nothing to do", this);
    		return false;
    	}
		
		tick++;
		log.debug("{}: Tick={}", this, tick);

		return game.onUpdate(this);
	}
}
