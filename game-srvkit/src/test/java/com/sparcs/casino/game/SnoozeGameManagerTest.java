package com.sparcs.casino.game;

import static org.junit.Assert.*;
//import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.sparcs.casino.BaseTest;
import com.sparcs.casino.game.GameState;
import com.sparcs.casino.game.Spectator;
import com.sparcs.casino.testgame.SnoozeGameManager;
import com.sparcs.casino.testgame.SnoozeRoom;

/**
 * Tests of SnoozeGameManager behaviour
 * 
 * @author Lee Newfeld
 *
 */
public class SnoozeGameManagerTest extends BaseTest {

	private static final Logger log = LoggerFactory.getLogger(SnoozeGameManagerTest.class);

	@Mock
	SnoozeRoom room;

	List<Spectator> spectators;
	
	@Autowired
	private SnoozeGameManager gameManager;

	@Before
	public void beforeTest() {

		super.beforeTest();

		spectators = new ArrayList<>();

        // Create mocks
        room = Mockito.mock(SnoozeRoom.class);
        
        // Add mock functionality
        when(room.getGameManager()).thenReturn(gameManager);
        when(room.getSpectators()).thenReturn(spectators);

		log.trace("Feature under test: {}", gameManager);
	}
	
	@Test
	public void gameIsntInitiallyRunning() {

		log.trace("+gameIsntInitiallyRunning");
		
		assertFalse(gameManager.isGameRunning());
		assertEquals(GameState.UNDEFINED_TIME, gameManager.getGameState().getGameTime());

		log.trace("-gameIsntInitiallyRunning");
	}

	@Rule public ExpectedException gameCantBeUpdatedWhenNotRunningException = ExpectedException.none(); 
	@Test
	public void gameCantBeUpdatedWhenNotRunning() {

		log.trace("+gameCantBeUpdatedWhenNotRunning");

		gameCantBeUpdatedWhenNotRunningException.expect(GameException.class);
		gameCantBeUpdatedWhenNotRunningException.expectMessage("Can't advance game time when game isn't running");

		gameManager.update();

		log.trace("-gameCantBeUpdatedWhenNotRunning");
	}
}
