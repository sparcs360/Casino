package com.sparcs.casino.game;

import static org.junit.Assert.*;

import java.lang.reflect.Field;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ReflectionUtils;

import com.sparcs.casino.BaseTest;
import com.sparcs.casino.EventTallier;
import com.sparcs.casino.testgame.SnoozePlayer;
import com.sparcs.casino.testgame.SnoozeSpectator;
import com.sparcs.casino.testgame.internal.SnoozeGameManagerImpl;
import com.sparcs.casino.testgame.internal.SnoozeRoomImpl;

/**
 * Test of Room behaviour
 *  
 * @author Lee Newfeld
 */
public class SnoozeRoomTest extends BaseTest {

	private static final Logger log = LoggerFactory.getLogger(SnoozeRoomTest.class);

	@Autowired
	private SnoozeRoomImpl room;

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

		SnoozeSpectator spectator = (SnoozeSpectator)room.enter(lee);

		assertRoomHasOneSpectator(spectator);

		log.trace("-customerCanEnterAnEmptyRoom");
	}

	@Test
	public void notifiedWhenCustomerEntersRoom() {

		log.trace("+notifiedWhenCustomerEntersRoom");

		EventTallier t = new EventTallier();
		room.getEventBroker().subscribe(t, Room.EnterEvent.class);

		room.enter(lee);	// Raises the EnterRoom event
		assertEquals("Should not have recieved any events", 0, t.getTally());

		room.executeGameLoop();	// Dispatches events
		assertEquals("One event should have been received", 1, t.getTally());

		log.trace("-notifiedWhenCustomerEntersRoom");
	}

	@Test
	public void spectatorCanJoinGame() {

		log.trace("+spectatorCanJoinGame");

		SnoozeSpectator spectator = (SnoozeSpectator)room.enter(lee);
		SnoozePlayer player = (SnoozePlayer)room.joinGame(spectator);

		assertRoomHasOnePlayer(player);

		log.trace("-spectatorCanJoinGame");
	}

	@Test
	public void notifiedWhenSpectatorJoinsGame() {

		log.trace("+notifiedWhenSpectatorJoinsGame");

		EventTallier t = new EventTallier();
		room.getEventBroker().subscribe(t, Room.JoinGameEvent.class);

		room.joinGame(room.enter(lee));	// Raises EnterEvent, and JoinGameEvent
		assertEquals("Should not have recieved any events", 0, t.getTally());

		room.executeGameLoop();	// Dispatches events
		assertEquals("One event should have been received", 1, t.getTally());

		log.trace("-notifiedWhenSpectatorJoinsGame");
	}

	@Test
	public void lastSpectatorToExitShouldResetRoom() {

		log.trace("+lastSpectatorToExitShouldResetRoom");

		room.exit(room.enter(lee));

		assertRoomIsEmpty();

		log.trace("-lastSpectatorToExitShouldResetRoom");
	}

	@Test
	public void notifiedWhenLastSpectatorExitsRoom() {

		log.trace("+notifiedWhenLastSpectatorExitsRoom");

		EventTallier t = new EventTallier();
		room.getEventBroker().subscribe(t, Room.ExitEvent.class);

		room.exit(room.enter(lee));	// Raises and dispatches ExitRoom event (dispatches because room is empty and game is shutdown)
		assertEquals("One event should have been received", 1, t.getTally());

		log.trace("-notifiedWhenLastSpectatorExitsRoom");
	}

	@Test
	public void notifiedWhenPlayerLeaveGame() {

		log.trace("+notifiedWhenPlayerLeaveGame");

		EventTallier t = new EventTallier();
		room.getEventBroker().subscribe(t, Room.LeaveGameEvent.class);

		room.leaveGame(room.joinGame(room.enter(lee)));	// Raises EnterEvent, JoinGameEvent and LeaveGameEvent
		assertEquals("Should not have recieved any events", 0, t.getTally());

		room.executeGameLoop();	// Dispatches events
		assertEquals("One event should have been received", 1, t.getTally());

		log.trace("-notifiedWhenPlayerLeaveGame");
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
		Field roomField = ReflectionUtils.findField(SnoozeGameManagerImpl.class, "room");
		assertNotNull("Expecting the Room field to be called 'room' - was it renamed?", roomField);
		ReflectionUtils.makeAccessible(roomField);
		assertSame("Game Manager should have a reference to the room", room, ReflectionUtils.getField(roomField, room.getGameManager()));
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
