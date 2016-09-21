package com.sparcs.casino.roulette;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.sparcs.casino.BaseTest;
import com.sparcs.casino.game.Player;
import com.sparcs.casino.game.Spectator;
import com.sparcs.casino.roulette.internal.RouletteRoomImpl;

/**
 * Test of Room behaviour
 *  
 * @author Lee Newfeld
 */
public class RouletteRoomTest extends BaseTest {

	private static final Logger log = LoggerFactory.getLogger(RouletteRoomTest.class);

	@Autowired
	private RouletteRoomImpl room;

	@Before
	public void beforeTest() {

		super.beforeTest();
		
		log.trace("Feature under test: {}", room);
	}

	@Test
	public void roomIsEmptyAfterConstruction() {

		log.trace("+roomIsEmptyWhenNoSpectatorsOrPlayers");
		
		assertRoomIsEmpty();

		log.trace("-roomIsEmptyWhenNoSpectatorsOrPlayers");
	}

	@Test
	public void customerCanEnterAnEmptyRoom() {

		log.trace("+customerCanEnterAnEmptyRoom");

		RouletteSpectator spectator = (RouletteSpectator)room.enter(lee);

		assertRoomHasOneSpectator(spectator);

		log.trace("-customerCanEnterAnEmptyRoom");
	}

	@Test
	public void spectatorCanJoinGame() {

		log.trace("+spectatorCanJoinGame");

		RouletteSpectator spectator = (RouletteSpectator)room.enter(lee);
		RoulettePlayer player = (RoulettePlayer)room.joinGame(spectator);

		assertRoomHasOnePlayer(player);

		log.trace("-spectatorCanJoinGame");
	}

	@Test
	public void lastSpectatorToLeaveShouldResetRoom() {

		log.trace("+lastSpectatorToLeaveShouldResetRoom");

		room.exit(room.enter(lee));

		assertRoomIsEmpty();

		log.trace("-lastSpectatorToLeaveShouldResetRoom");
	}

	//---
	
	private void assertRoomIsEmpty() {
		
		assertTrue("Room should be empty", room.isEmpty());
		
		assertTrue("Room shouldn't have any spectators", room.getSpectators().size() == 0);
		assertNull("Room shouldn't have a game manager", room.getGameManager());
		assertFalse("Game loop shouldn't run", room.executeGameLoop());
	}

	private void assertRoomHasOneSpectator(Spectator spectator) {
		
		assertNotNull("Spectator should have been returned from RoomImpl.enter()", spectator);
        assertSame("Spectator role should apply to Lee", lee, spectator.getCustomer());

		assertFalse("Room shouldn't be empty", room.isEmpty());
		
		assertEquals("Room should have 1 spectator", 1, room.getSpectators().size());
        assertTrue("Lee should be in spectator list", room.getSpectators().contains(spectator));
        
		assertNotNull("Room should have a game manager", room.getGameManager());
		assertTrue("Game should have no players", room.getGameManager().getGameState().getPlayers().isEmpty());
		assertTrue("Game should be running", room.getGameManager().isGameRunning());
		assertTrue("Game loop should run", room.executeGameLoop());
	}

	private void assertRoomHasOnePlayer(Player player) {
		
		assertNotNull("Player should have been returned from RoomImpl.joinGame()", player);
		assertSame("Player role should apply to Lee", lee, player.getCustomer());

		assertFalse("Room shouldn't be empty", room.isEmpty());
		
		assertEquals("Room should have no spectators", 0, room.getSpectators().size());

		assertNotNull("Room should have a game manager", room.getGameManager());
		assertEquals("Game should have 1 player", 1, room.getGameManager().getGameState().getPlayers().size());
        assertTrue("Lee should be be in player list", room.getGameManager().getGameState().getPlayers().contains(player));
		assertTrue("Game should be running", room.getGameManager().isGameRunning());
		assertTrue("Game loop should run", room.executeGameLoop());
	}
}
