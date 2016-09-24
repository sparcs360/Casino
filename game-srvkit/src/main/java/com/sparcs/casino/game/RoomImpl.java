package com.sparcs.casino.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.sparcs.casino.Customer;
import com.sparcs.casino.events.EventBroker;

/**
 * The base implementation of a {@link Room}
 * 
 * @author Lee Newfeld
 */
public abstract class RoomImpl implements Room, ApplicationContextAware {

	private final Logger log = LoggerFactory.getLogger(getClass());

	protected ApplicationContext applicationContext;

	/**
	 * The {@link EventBroker} handles event publish/subscribe
	 */
	@Autowired
	private EventBroker broker;

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
	public EventBroker getEventBroker() {

		return broker;
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

		log.trace("{}: {} entered", this, customer);

		// Inform subscribers
		broker.raiseEvent(new EnterEvent(this, customer));
		
		// Create game assets if this is the first customer through the door
		if( isEmpty() ) {
			
			createGameAssets();
		}
		
		Spectator spectator = addSpectator(customer);
		return spectator;
	}

	@Override
	public Player joinGame(Spectator spectator) {

		Objects.requireNonNull(spectator, "spectator");
		
		log.trace("{}: {} wants to join the game", this, spectator);

		if( !spectators.contains(spectator) ) {
			
			throw new GameException("Only spectators can join the game"); 
		}
		
		// Inform subscribers
		broker.raiseEvent(new JoinGameEvent(this, spectator));

        removeSpectator(spectator);
		return addPlayer(spectator);
	}

	@Override
	public Spectator leaveGame(Player player) {

		Objects.requireNonNull(player, "player");
		
		log.trace("{}: {} wants to leave the game", this, player);

		if( !gameManager.getGameState().getPlayers().contains(player) ) {
			
			throw new GameException("Only players can leave the game"); 
		}
		
		// Inform subscribers
		broker.raiseEvent(new LeaveGameEvent(this, player));

		removePlayer(player);
        return addSpectator(player.getCustomer());
	}

	@Override
	public void exit(Spectator spectator) {

		Objects.requireNonNull(spectator, "spectator");

		log.trace("{}: {} wants to exit the room", this, spectator);

		if( !spectators.contains(spectator) ) {
			
			throw new GameException("Only spectators can leave the room"); 
		}

		// Inform subscribers
		broker.raiseEvent(new ExitEvent(this, spectator));

		removeSpectator(spectator);
		
		// If no one is in the room, tidy up the game assets
		if( isEmpty() ) {
			
			destroyGameAssets();
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
		
		broker.dispatchEvents();
		
		return gameManager.update(this);
	}

    //---
    
    /**
     * Create and initialise a {@link GameManager}.
     */
	private void createGameAssets() {
		
		log.trace("Creating Game Assets");
		
		gameManager = (GameManager)applicationContext.getBean("GameManager");
		gameManager.initialise(this);
	}

	private void destroyGameAssets() {
		
		log.trace("Destroying Game Assets");
		
    	// There are undispatched events if we're shutting down because the
		// room is empty
		broker.dispatchEvents();

		gameManager.shutdown(this);
		gameManager = null;
	}

	/**
	 * Grant a {@link Customer} the {@link Spectator} role.
	 * 
	 * @param customer The {@link Customer} to grant the role to
	 * @return The {@link Spectator}
	 */
	private Spectator addSpectator(Customer customer) {

		Spectator spectator = grantSpectatorRole(customer);
		log.trace("{} was granted {}", customer,
				spectator.getClass().getSimpleName());
        spectators.add(spectator);
		return spectator;
	}

	/**
	 * Revoke the {@link Spectator} role from a {@link Customer}.
	 * 
	 * @param spectator The {@link Spectator} role.
	 */
	private void removeSpectator(Spectator spectator) {
		
		log.trace("{} was revoked {}", spectator.getCustomer(), spectator);
		spectators.remove(spectator);
	}

	/**
	 * Grant a {@link Customer} the {@link Player} role.
	 * 
	 * @param customer The {@link Customer} to grant the role to
	 * @return The {@link Player}
	 */
	private Player addPlayer(Spectator spectator) {
		
		Customer customer = spectator.getCustomer();
		Player player = grantPlayerRole(customer);
		log.trace("{} was granted {}", customer, player);
		gameManager.getGameState().getPlayers().add(player);
        return player;
	}

	/**
	 * Revoke the {@link Player} role from a {@link Customer}.
	 * 
	 * @param player The {@link Player} role.
	 */
	private void removePlayer(Player player) {
		
		log.trace("{} was revoked {}", player.getCustomer(), player);
		gameManager.getGameState().getPlayers().remove(player);
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
        	
			player = (Player)applicationContext.getBean("Player", customer, gameManager);

        } catch( BeansException e ) {
        	
        	log.error("RoomImpl.grantPlayerRole: Failed to grant to {}", customer, e);
        	// PANIC
        	throw new GameException(
        			"RoomImpl.grantPlayerRole: Failed to grant to " + customer.toString(), e);
        }
		return player;
	}
}
