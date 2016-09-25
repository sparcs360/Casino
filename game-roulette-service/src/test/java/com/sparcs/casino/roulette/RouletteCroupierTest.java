package com.sparcs.casino.roulette;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;
import java.util.function.Predicate;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ReflectionUtils;

import com.sparcs.casino.BaseTest;
import com.sparcs.casino.EventTallier;
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
	
	private RouletteCroupierImpl croupier;

	@Autowired
	private RouletteWheel realWheel;
	
	private RouletteWheel riggedWheel;

	private RouletteSpectator spectator;

	@Before
	public void beforeTest() {

		super.beforeTest();

		// Create a rigged wheel!
		riggedWheel = Mockito.spy(realWheel);
		when(riggedWheel.getResult()).thenReturn(7);
		assertEquals(7, riggedWheel.getResult());

		// Spectator enters the room
		spectator = (RouletteSpectator)room.enter(sparcs);
		croupier = (RouletteCroupierImpl)room.getGameManager();
		
		// Replace real wheel with rigged wheel
		Field wheelField = ReflectionUtils.findField(RouletteCroupierImpl.class, "wheel");
		assertNotNull("Expecting RouletteWheel to be in a field called 'wheel'", wheelField);
		ReflectionUtils.makeAccessible(wheelField);
		ReflectionUtils.setField(wheelField, croupier, riggedWheel);
		assertEquals(7, croupier.getSpinResult());

		log.trace("Feature under test: {}", croupier);
	}
	
	@Test
	public void gameShouldRunWhenSpectatorEntersRoom() {

		log.trace("+gameShouldRunWhenSpectatorEntersRoom");

		// Spectator stays for a while...
		for( int i=0; i<50; i++ ) {
			
			assertTrue("Game should never end", croupier.update() );
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
			
			assertTrue("Game should never end", croupier.update() );
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
		int chipsBefore = player.getChipCount();
		SingleBet bet = RouletteBet.singleBet(player, 1, 7);
		
		EventTallier tallyOfBetPlacedEvents = new EventTallier();
		room.getEventBroker().subscribe(tallyOfBetPlacedEvents, RouletteCroupier.BetPlacedEvent.class);

		player.requestBet(bet);
		
		room.executeGameLoop();	// Dispatches events
		assertEquals("One BetPlacedEvent should have been received", 1, tallyOfBetPlacedEvents.getTally());
		
		assertEquals("Player should have 1 active bet", 1, player.getBets().size());
		assertTrue("The only bet should be the one just placed", player.getBets().contains(bet));
		assertEquals("Player should have one less chip", chipsBefore - 1, player.getChipCount());

		log.trace("-betAcceptedFromPlayerWhenBettingAllowed");
	}

	@Test
	public void betRejectedFromPlayerWhenNotEnoughChips() {

		log.trace("+betRejectedFromPlayerWhenNotEnoughChips");

		// Spectator joins game
		RoulettePlayer player = (RoulettePlayer)room.joinGame(spectator);

		// Wait until we can bet
		waitUntil( player, p -> p.isBettingAllowed() );

		// Put 1M chip on lucky 7!
		int chipsBefore = player.getChipCount();
		SingleBet bet = RouletteBet.singleBet(player, 1000000, 7);
		
		EventTallier tallyOfBetRejectedEvent = new EventTallier();
		room.getEventBroker().subscribe(tallyOfBetRejectedEvent, RouletteCroupier.BetRejectedEvent.class);

		player.requestBet(bet);
		
		room.executeGameLoop();	// Dispatches events
		assertEquals("One BetRejectedEvent should have been received", 1, tallyOfBetRejectedEvent.getTally());
		
		assertEquals("Player should have no active bet", 0, player.getBets().size());
		assertEquals("Player should have same number of chip as before", chipsBefore, player.getChipCount());

		log.trace("-betRejectedFromPlayerWhenNotEnoughChips");
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
		int chipsBefore = currentPlayer.getChipCount();
		SingleBet bet = RouletteBet.singleBet(originalPlayer, 1, 7);
		
		EventTallier tallyOfBetRejectedEvent = new EventTallier();
		room.getEventBroker().subscribe(tallyOfBetRejectedEvent, RouletteCroupier.BetRejectedEvent.class);

		originalPlayer.requestBet(bet);
		
		room.executeGameLoop();	// Dispatches events
		assertEquals("One BetRejectedEvent should have been received", 1, tallyOfBetRejectedEvent.getTally());
		
		assertEquals("Original Player should have no active bet", 0, originalPlayer.getBets().size());
		assertEquals("Current Player should have no active bet", 0, currentPlayer.getBets().size());
		assertEquals("Current Player should have same number of chips", chipsBefore, currentPlayer.getChipCount());

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
		EventTallier tallyOfBetRejectedEvent = new EventTallier();
		room.getEventBroker().subscribe(tallyOfBetRejectedEvent, RouletteCroupier.BetRejectedEvent.class);

		player.requestBet(RouletteBet.singleBet(player, 1, 7));
		
		room.executeGameLoop();	// Dispatches events
		assertEquals("One BetRejectedEvent should have been received", 1, tallyOfBetRejectedEvent.getTally());

		log.trace("-betRejectedFromPlayerWhenBettingNotAllowed");
	}

	@Test
	public void winningBetPaysOut() {

		log.trace("+winningBetPaysOut");

		// Spectator joins game
		RoulettePlayer player = (RoulettePlayer)room.joinGame(spectator);

		// Wait until we can bet
		waitUntil( player, p -> p.isBettingAllowed() );

		// Put 1 chip on lucky 7!
		int chipsBefore = player.getChipCount();
		SingleBet bet = RouletteBet.singleBet(player, 1, 7);

		EventTallier tallyOfBetWinEvent = new EventTallier();
		room.getEventBroker().subscribe(tallyOfBetWinEvent, RouletteCroupier.BetWinEvent.class);

		player.requestBet(bet);
		
		room.executeGameLoop();	// Dispatches events
		assertEquals("One BetWinEvent should have been received", 0, tallyOfBetWinEvent.getTally());

		// Wait until Croupier resolves bets
		waitUntil( player, p -> p.areBetsResolved() );

		assertEquals("One BetWinEvent should have been received", 1, tallyOfBetWinEvent.getTally());
		assertEquals("Winnings should be added", chipsBefore + 35, player.getChipCount());
		assertEquals("Player should have no active bet", 0, player.getBets().size());

		log.trace("-winningBetPaysOut");
	}

	@Test
	public void loosingBetDoesntPayOut() {

		log.trace("+loosingBetDoesntPayOut");

		// Spectator joins game
		RoulettePlayer player = (RoulettePlayer)room.joinGame(spectator);

		// Wait until we can bet
		waitUntil( player, p -> p.isBettingAllowed() );

		// Put 1 chip on #1 (surely it can't be a 7 again?)
		int chipsBefore = player.getChipCount();
		SingleBet bet = RouletteBet.singleBet(player, 1, 1);

		EventTallier tallyOfBetWinEvent = new EventTallier();
		room.getEventBroker().subscribe(tallyOfBetWinEvent, RouletteCroupier.BetWinEvent.class);

		player.requestBet(bet);
		
		room.executeGameLoop();	// Dispatches events
		assertEquals("One BetWinEvent should have been received", 0, tallyOfBetWinEvent.getTally());

		// Wait until Croupier resolves bets
		waitUntil( player, p -> p.areBetsResolved() );

		assertEquals("One BetWinEvent should have been received", 0, tallyOfBetWinEvent.getTally());
		assertEquals("Should have one less chip", chipsBefore - 1, player.getChipCount());

		log.trace("-loosingBetDoesntPayOut");
	}

	/**
	 * Run game loop until the condition is met for the given player.<br>
	 * The urge to write an event queue is growing... <code>:o|</code>
	 * 
	 * @param condition
	 */
	private void waitUntil(RoulettePlayer player, Predicate<RoulettePlayer> condition) {

		do {

			room.executeGameLoop();

		} while( !condition.test(player) );
	}
}
