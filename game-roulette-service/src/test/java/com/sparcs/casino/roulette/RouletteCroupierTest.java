package com.sparcs.casino.roulette;

import static org.junit.Assert.*;
//import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.sparcs.casino.BaseTest;
import com.sparcs.casino.game.GameState;
import com.sparcs.casino.game.Spectator;

/**
 * Tests of RouletteCroupier behaviour
 * @author Lee Newfeld
 *
 */
public class RouletteCroupierTest extends BaseTest {

	private static final Logger log = LoggerFactory.getLogger(RouletteCroupierTest.class);

	@Mock
	RouletteRoom room;

	List<Spectator> spectators;
	
	@Autowired
	private RouletteCroupier croupier;

	@Before
	public void beforeTest() {

		super.beforeTest();

		spectators = new ArrayList<>();

        // Create mocks
        room = Mockito.mock(RouletteRoom.class);
        
        // Add mock functionality
        when(room.getGameManager()).thenReturn(croupier);
        when(room.getSpectators()).thenReturn(spectators);

		log.trace("Feature under test: {}", croupier);
	}
	
	@Test
	public void gameIsntInitiallyRunning() {

		log.trace("+gameIsntInitiallyRunning");
		
		assertFalse(croupier.isGameRunning());
		assertEquals(GameState.UNDEFINED_TIME, croupier.getGameState().getGameTime());

		log.trace("-gameIsntInitiallyRunning");
	}
}
