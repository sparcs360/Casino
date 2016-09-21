package com.sparcs.casino.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.sparcs.casino.Customer;

/**
 * The base implementation of a {@link Room}
 * 
 * @author Lee Newfeld
 */
public abstract class RoomImpl implements Room, ApplicationContextAware {

	private final Logger log = LoggerFactory.getLogger(getClass());
	
	protected ApplicationContext applicationContext;
	
	/**
	 * The {@link GameManager Game Manager} overseeing the {@link GameState}.
	 * null if no one is in the {@link Room}.
	 */
	private GameManager gameManager;
	
	/**
	 * The {@link Spectator}s watching the {@link GameState}
	 */
    private List<Spectator> spectators;
	
    /**
     * Constructor
     */
	protected RoomImpl() {

        spectators = new ArrayList<>();
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

		this.applicationContext = applicationContext;
	}
	
	@Override
	public GameManager getGameManager() {

		return gameManager;
	}

	@Override
	public List<Spectator> getSpectators() {

        return spectators;
	}

	@Override
	public boolean isEmpty() {

		return gameManager == null ||
			   (gameManager.getGameState().getPlayers().isEmpty() && spectators.isEmpty());
	}

	@Override
	public Spectator enter(Customer customer) {

		Objects.requireNonNull(customer, "customer");

		// Create the GameManager if it doesn't exist
		if( gameManager == null ) {
			
			gameManager = (GameManager)applicationContext.getBean("GameManager");
			gameManager.initialise(this);
		}
		
		Spectator spectator = grantSpectatorRole(customer);
        spectators.add(spectator);
        return spectator;
	}

	@Override
	public Player joinGame(Spectator spectator) {

		Objects.requireNonNull(spectator, "spectator");
		if( !spectators.contains(spectator) ) {
			
			throw new GameException("Only spectators can join the game"); 
		}

        spectators.remove(spectator);
		Player player = grantPlayerRole(spectator.getCustomer());
		gameManager.getGameState().getPlayers().add(player);
        return player;
	}

	@Override
	public Spectator leaveGame(Player player) {

		Objects.requireNonNull(player, "player");
		if( !gameManager.getGameState().getPlayers().contains(player) ) {
			
			throw new GameException("Only players can leave the game"); 
		}
		
        gameManager.getGameState().getPlayers().remove(player);
        Spectator spectator = grantSpectatorRole(player.getCustomer());
        spectators.add(spectator);
        return spectator;
	}

	@Override
	public void exit(Spectator spectator) {

		Objects.requireNonNull(spectator, "spectator");
		if( !spectators.contains(spectator) ) {
			
			throw new GameException("Only spectators can leave the room"); 
		}

		spectators.remove(spectator);
		
		// Kill the GameManager if no one is in the room
		if( isEmpty() ) {
			
			gameManager.shutdown(this);
			gameManager = null;
		}
	}

    @Override
	public boolean executeGameLoop() {

    	log.trace("{}: executeGameLoop", this);

    	// Is the game running?
		if( gameManager == null || !gameManager.isGameRunning() ) {
			
			log.debug("{}: Game isn't running", this);
    		return false;
    	}
		
		return gameManager.update(this);
	}

    /**
     * Decorate a {@link Customer} with the {@link Spectator} role.
     * 
     * @param customer The Customer to grant the role to
     * @return The granted role
     */
	private Spectator grantSpectatorRole(Customer customer) {
		
		Spectator spectator = null;
        try {
        	
			spectator = (Spectator)applicationContext.getBean("Spectator", customer);

        } catch( BeansException e ) {
        	
        	log.error("RoomImpl.grantSpectatorRole: Failed to grant to {}", customer, e);
        	// PANIC
        	throw new GameException(
        			"RoomImpl.grantSpectatorRole: Failed to grant to " + customer.toString(), e);
        }
		return spectator;
	}

    /**
     * Decorate a {@link Customer} with the {@link Player} role.
     * 
     * @param customer The {@link Customer} to grant the role to
     * @return The granted role
     */
	private Player grantPlayerRole(Customer customer) {
		
		Player player = null;
        try {
        	
			player = (Player)applicationContext.getBean("Player", customer);

        } catch( BeansException e ) {
        	
        	log.error("RoomImpl.grantPlayerRole: Failed to grant to {}", customer, e);
        	// PANIC
        	throw new GameException(
        			"RoomImpl.grantPlayerRole: Failed to grant to " + customer.toString(), e);
        }
		return player;
	}
}
