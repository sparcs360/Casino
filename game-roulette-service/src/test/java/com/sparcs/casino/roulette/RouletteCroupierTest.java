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
import com.sparcs.casino.game.Spectator;
import com.sparcs.casino.roulette.internal.RouletteCroupierImpl;

/**
 * Tests of RouletteCroupier behaviour
 * @author Lee Newfeld
 *
 */
public class RouletteCroupierTest extends BaseTest {

	private static final Logger log = LoggerFactory.getLogger(RouletteCroupierTest.class);

	@Mock
	RouletteRoom room;
	
	@Mock
	Spectator spectator;

	List<Spectator> spectators;
	
	@Autowired
	private RouletteCroupierImpl croupier;

	@Before
	public void beforeTest() {

		super.beforeTest();

		spectators = new ArrayList<>();

        // Create mocks
        room = Mockito.mock(RouletteRoom.class);
        spectator = Mockito.mock(Spectator.class);
        
        // Add mock functionality
        when(room.getGameManager()).thenReturn(croupier);
        when(room.getSpectators()).thenReturn(spectators);
        when(spectator.toString()).thenReturn("Lee");
        when(spectator.getCustomer()).thenReturn(lee);

		log.trace("Feature under test: {}", croupier);
	}
	
	@Test
	public void gameShouldRunWhenSpectatorEntersRoom() {

		log.trace("+todo");

		// Fired when someone walks into the empty room
		spectators.add(spectator);
		croupier.initialise(room);
		
		// Spectator stays for a while...
		for( int i=0; i<50; i++ ) {
			
			assertTrue("Game should never end", croupier.update(room) );
		}

		// Spectator leaves
		croupier.shutdown(room);

		log.trace("-todo");
	}
}
