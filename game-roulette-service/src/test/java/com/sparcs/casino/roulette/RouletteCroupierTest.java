package com.sparcs.casino.roulette;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.function.Predicate;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.sparcs.casino.BaseTest;
import com.sparcs.casino.roulette.RouletteBet.SingleBet;
import com.sparcs.casino.roulette.internal.RouletteCroupierImpl;
import com.sparcs.casino.roulette.internal.RouletteRoomImpl;

/**
 * Test {@link RouletteCroupierImpl} behaviour
 * 
 * @author Lee Newfeld
 *
 */
public class RouletteCroupierTest extends BaseTest {

	private static final Logger log = LoggerFactory.getLogger(RouletteCroupierTest.class);

	@Autowired
	private RouletteRoomImpl room;

	@InjectMocks
	private RouletteCroupierImpl croupier;

	@Autowired
	private RouletteWheel realWheel;
	@Mock
	private RouletteWheel riggedWheel;

	private RouletteSpectator spectator;

	@Before
	public void beforeTest() {

		super.beforeTest();

		// Spectator enters the room
		spectator = (RouletteSpectator)room.enter(lee);
		croupier = (RouletteCroupierImpl)room.getGameManager();

		// Create a rigged wheel!
		riggedWheel = Mockito.spy(realWheel);
		when(riggedWheel.getResult()).thenReturn(7);

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
		assertEquals("Player should have no active bet", 0, player.getBets().size());

		// Wait until we can bet
		waitUntil( player, p -> p.isBettingAllowed() );

		// Put 1 chip on lucky 7!
		SingleBet bet = RouletteBet.singleBet(1, 7);
		assertTrue("Bet should be accepted", player.requestBet(bet));
		assertEquals("Player should have 1 active bet", 1, player.getBets().size());
		assertTrue("The only bet should be the one just placed", player.getBets().contains(bet));

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
		SingleBet bet = RouletteBet.singleBet(1, 7);
		assertFalse("Bet should be rejected", originalPlayer.requestBet(bet));
		assertEquals("Original Player should have no active bet", 0, originalPlayer.getBets().size());
		assertEquals("Current Player should have no active bet", 0, currentPlayer.getBets().size());

		log.trace("-betRejectedFromNonPlayer");
	}

	@Test
	public void betRejectedFromPlayerWhenBettingNotAllowed() {

		log.trace("+betRejectedFromPlayerWhenBettingNotAllowed");

		// Spectator joins game
		RoulettePlayer player = (RoulettePlayer)room.joinGame(spectator);

		// Wait until we cannot bet
		waitUntil( player, p -> !p.isBettingAllowed() );

		// Put 1 chip on lucky 7!
		assertFalse("Bet should be rejected", player.requestBet(RouletteBet.singleBet(1, 7)));
		assertEquals("Player should have no active bet", 0, player.getBets().size());

		log.trace("-betRejectedFromPlayerWhenBettingNotAllowed");
	}

	@Test
	public void winningSingleBetPaysOut() {

		log.trace("+winningSingleBetPaysOut");

		// Spectator joins game
		RoulettePlayer player = (RoulettePlayer)room.joinGame(spectator);

		// Wait until we can bet
		waitUntil( player, p -> p.isBettingAllowed() );

		// Put 1 chip on lucky 7!
		SingleBet bet = RouletteBet.singleBet(1, 7);
		assertTrue("Bet should be accepted", player.requestBet(bet));

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
