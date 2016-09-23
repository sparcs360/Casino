package com.sparcs.casino.roulette.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sparcs.casino.game.GameManagerImpl;
import com.sparcs.casino.game.Room;
import com.sparcs.casino.roulette.RouletteBet;
import com.sparcs.casino.roulette.RouletteCroupier;
import com.sparcs.casino.roulette.RoulettePlayer;
import com.sparcs.casino.roulette.RouletteWheel;

/**
 * The Croupier overseeing a game of Roulette
 *  
 * @author Lee Newfeld
 */
@Component("GameManager")
@Scope("prototype")
public class RouletteCroupierImpl extends GameManagerImpl implements RouletteCroupier {

	private static final Logger log = LoggerFactory.getLogger(RouletteCroupierImpl.class);

	// TODO: Make these .properties?
	private static final int WHEEL_AT_REST_DURATION = 5;
	private static final int WHEEL_SPINNING_DURATION = 5;
	private static final int WHEEL_BALL_SPINNING_DURATION = 5;
	private static final int WHEEL_NO_MORE_BETS_DURATION = 5;
	private static final int WHEEL_BALL_AT_REST_DURATION = 5;
	private static final int WHEEL_BETS_RESOLVED_DURATION = 5;

	private static final List<RouletteBet> NO_BETS = Collections.unmodifiableList(new ArrayList<>());

	private Stage stage;
	
	private RouletteWheel wheel;
	
	private Map<RoulettePlayer, List<RouletteBet>> currentBets;

	private long nextWheelEventTime;
	
	/**
	 * Constructor
	 * 
	 * @param wheel The installed Wheel 
	 */
	@Autowired
	public RouletteCroupierImpl(RouletteWheel wheel) {

		this.wheel = wheel;
	}
	
	@PostConstruct
	private void initialise() {

		log.trace("Created {}", this);
	}

	@Override
	protected void onInitialise(Room room) {

		log.trace("{}: onInitialise", this);

		stage = Stage.STARTING_NEW_GAME;
		currentBets = new HashMap<>();
		nextWheelEventTime = getGameState().getGameTime() + WHEEL_AT_REST_DURATION;

		shout("Greetings!  Feel free to watch, or take a seat and play");
	}

	@Override
	protected boolean onUpdate(Room room) {

		log.trace("{}: onUpdate", this);

		updateWheel();

		return true;
	}

	/**
	 * Keep an eye on the Wheel...
	 */
	private void updateWheel() {

		if( getGameState().getGameTime() >= nextWheelEventTime ) {

			switch(wheel.update()) {
			
				case AT_REST:
					stage = Stage.STARTING_NEW_GAME;
					shout("The Croupier gets ready to start a new game...");
					nextWheelEventTime = getGameState().getGameTime() + WHEEL_AT_REST_DURATION;
					break;

				case SPINNING:
					shout("The Croupier spins the Roulette Wheel...");
					nextWheelEventTime = getGameState().getGameTime() + WHEEL_SPINNING_DURATION;
					break;

				case BALL_SPINNING:
					shout("The Croupier spins the ball against the Wheel...");
					nextWheelEventTime = getGameState().getGameTime() + WHEEL_BALL_SPINNING_DURATION;
					break;

				case NO_MORE_BETS:
					shout("The Croupier announces 'No More Bets'...");
					nextWheelEventTime = getGameState().getGameTime() + WHEEL_NO_MORE_BETS_DURATION;
					break;

				case BALL_AT_REST:
					shout("The Ball comes to rest on number {}", wheel.getResult());
					nextWheelEventTime = getGameState().getGameTime() + WHEEL_BALL_AT_REST_DURATION;
					resolveBets();
					break;

				case BETS_RESOLVED:
					stage = Stage.BETS_RESOLVED;
					shout("The Croupier has resolved all the bets");
					nextWheelEventTime = getGameState().getGameTime() + WHEEL_BETS_RESOLVED_DURATION;
					break;
			}
		}
	}

	/**
	 * @return The number that the ball has settled on.
	 * {@link RouletteWheel#RESULT_UNDEFINED} if the ball is in the
	 * Croupier's hand.
	 */
	public int getSpinResult() {

		return wheel.getResult();
	}

	@Override
	protected void onShutdown(Room room) {

		log.trace("{}: onShutdown", this);
	}

	@Override
	public boolean isBettingAllowed() {

		return wheel.getStage().isBettingAllowed();
	}

	@Override
	public boolean areBetsResolved() {

		return stage==Stage.BETS_RESOLVED;
	}

	@Override
	public boolean considerBet(RoulettePlayer player, RouletteBet bet) {

		log.trace("{}: considerBet(player={}, bet={})",
				this, player, bet);

		// Must be in a betting phase
		if( !isBettingAllowed() ) {
			log.trace("{}: Rejecting bet (betting not currently allowed)", this);
			return false;
		}
		
		// Player must be seated at the table
		if( !getGameState().getPlayers().contains(player) ) {
			log.trace("{}: Rejecting bet (player not in player list)", this);
			return false;
		}
		
		// Player must have enough chips
		if( player.getChipCount() < bet.getStake() ) {
			log.trace("{}: Rejecting bet (not enough chips)", this);
			return false;
		}
		
		// Validate bet
		if( !bet.isValid(this) ) {
			log.trace("{}: Rejecting bet (not valid)", this);
			return false;
		}

		// We're accepting this bet...
		acceptBet(player, bet);
		return true;
	}

	/**
	 * Accept a {@link RouletteBet bet} made by a {@link RoulettePlayer player}.
	 * 
	 * @param player
	 * @param bet
	 */
	private void acceptBet(RoulettePlayer player, RouletteBet bet) {

		// Get current list of Bets
		List<RouletteBet> playerBets = currentBets.get(player);
		if( playerBets == null ) {
			log.trace("{}: This is the first bet for {}", this, player);
			playerBets = new ArrayList<>();
			currentBets.put(player, playerBets);
		}

		// TODO: Possibly merge bets?  Player may be putting more chips on an existing bet

		// Add bet to the list of bets
		// (Imagining a call to the Customer microservice...) 
		try {
			
			log.debug("{}: Bet accepted", this);
			player.deductChips(bet.getStake());
			playerBets.add(bet);
			log.trace("{}: {} now has {} active bet(s)", this, player, playerBets.size());

		} catch( Exception e ) {
			
		}
	}

	@Override
	public List<RouletteBet> getBets(RoulettePlayer player) {

		return currentBets.get(player) == null
				? NO_BETS
				: currentBets.get(player);
	}

	@Override
	public String toString() {

		return String.format("RouletteCroupier@%x",
				this.hashCode());
	}

	//---
	
	/**
	 * The {@link RouletteWheel#getResult() result} is in... resolve bets.
	 */
	private void resolveBets() {

		log.trace("{}: resolving bets", this);
		
		// Resolve bets
		currentBets.keySet()
			.stream()
			// I'd flatMap to Bets but I want the player too (for logging) 
			//.flatMap( player -> currentBets.get(player).stream())
			.forEach(player -> {
				currentBets.get(player)
					.stream()
					.forEach(bet -> resolveBet(player, bet));
			});

		log.trace("{}: resolved bets", this);

			
		// Remove all bets
		// TODO: We should leave winning bets on the table
		currentBets.clear();
	}

	/**
	 * Resolve the given bet
	 * 
	 * @param bet
	 */
	private void resolveBet(RoulettePlayer player, RouletteBet bet) {

		log.trace("{}: Resolving {}<-{}", this, player, bet);
		
		// Resolve bet
		int result = wheel.getResult();
		int winnings = bet.calculateWinnings(result);
		if( winnings == 0 ) {
			return;
		}

		shout("{} has won {}c with their bet of {}", player, winnings, bet);
		player.addChips(winnings);
	}
}
