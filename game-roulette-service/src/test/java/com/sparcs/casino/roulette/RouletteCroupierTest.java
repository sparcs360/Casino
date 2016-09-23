package com.sparcs.casino.roulette;

import static org.junit.Assert.*;

import java.util.function.Predicate;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.sparcs.casino.BaseTest;
import com.sparcs.casino.roulette.internal.RouletteCroupierImpl;
import com.sparcs.casino.roulette.internal.RouletteRoomImpl;

/**
 * Tests of RouletteCroupier behaviour
 * @author Lee Newfeld
 *
 */
public class RouletteCroupierTest extends BaseTest {

	private static final Logger log = LoggerFactory.getLogger(RouletteCroupierTest.class);

	@Autowired
	private RouletteRoomImpl room;
	
	private RouletteCroupierImpl croupier;

	private RouletteSpectator spectator;
	
	@Before
	public void beforeTest() {

		super.beforeTest();

		// Spectator enters the room
		spectator = (RouletteSpectator)room.enter(lee);
		croupier = (RouletteCroupierImpl)room.getGameManager();

		log.trace("Feature under test: {}", croupier);
	}
	
	@Test
	public void gameShouldRunWhenSpectatorEntersRoom() {

		log.trace("+gameShouldRunWhenSpectatorEntersRoom");

		// Spectator stays for a while...
		for( int i=0; i<50; i++ ) {
			
			assertTrue("Game should never end", croupier.update(room) );
		}

		log.trace("-gameShouldRunWhenSpectatorEntersRoom");
	}

	@Test
	public void gameShouldRunWhenPlayerMakesNoBets() {

		log.trace("+gameShouldRunWhenPlayerMakesNoBets");
		
		// Spectator joins game
		room.joinGame(spectator);

		// Spectator stays for a while...
		for( int i=0; i<50; i++ ) {
			
			assertTrue("Game should never end", croupier.update(room) );
		}

		log.trace("-gameShouldRunWhenPlayerMakesNoBets");
	}

	@Test
	public void betAcceptedFromPlayerWhenBettingAllowed() {

		log.trace("+betAcceptedFromPlayerWhenBettingAllowed");

		// Spectator joins game
		RoulettePlayer player = (RoulettePlayer)room.joinGame(spectator);

		// Wait until we can bet
		waitUntil( player, p -> p.isBettingAllowed() );

		// Put 1 chip on lucky 7!
		assertTrue("Bet should be accepted", player.requestBet(RouletteBet.singleBet(1, 7)));

		log.trace("-betAcceptedFromPlayerWhenBettingAllowed");
	}

	@Test
	public void betRejectedFromNonPlayer() {

		log.trace("+betRejectedFromNonPlayer");

		// Spectator joins game, leves and rejoins the game
		RoulettePlayer originalPlayer = (RoulettePlayer)room.joinGame(spectator);
		spectator = (RouletteSpectator)room.leaveGame(originalPlayer);
		RoulettePlayer currentPlayer = (RoulettePlayer)room.joinGame(spectator);

		// Wait until we can bet
		waitUntil( currentPlayer, p -> p.isBettingAllowed() );

		// Put 1 chip on lucky 7!
		assertFalse("Bet should be rejected", originalPlayer.requestBet(RouletteBet.singleBet(1, 7)));

		log.trace("-betRejectedFromNonPlayer");
	}

	@Test
	public void betRejectedFromPlayerWhenNotBettingAllowed() {

		log.trace("+betRejectedFromPlayerWhenNotBettingAllowed");

		// Spectator joins game
		RoulettePlayer player = (RoulettePlayer)room.joinGame(spectator);

		// Wait until we cannot bet
		waitUntil( player, p -> !p.isBettingAllowed() );

		// Put 1 chip on lucky 7!
		assertFalse("Bet should be rejected", player.requestBet(RouletteBet.singleBet(1, 7)));

		log.trace("-betRejectedFromPlayerWhenNotBettingAllowed");
	}

	@Test
	public void winningSingleBetPaysOut() {

		log.trace("+winningSingleBetPaysOut");

		// Spectator joins game
		RoulettePlayer player = (RoulettePlayer)room.joinGame(spectator);

		// Wait until we can bet
		waitUntil( player, p -> p.isBettingAllowed() );

		// Put 1 chip on lucky 7!
		assertTrue("Bet should be accepted", player.requestBet(RouletteBet.singleBet(1, 7)));

		// Wait until Croupier resolves bets
		waitUntil( player, p -> p.areBetsResolved() );
		
		log.trace("-winningSingleBetPaysOut");
	}

	/**
	 * Run game loop until the condition is met for the given player.<br>
	 * The urge to write an event queue is growing... <code>:o|</code>
	 * 
	 * @param condition
	 */
	private void waitUntil(RoulettePlayer player, Predicate<RoulettePlayer> condition) {

		do {
			
			croupier.update(room);
			
		} while( !condition.test(player) );
	}
}
