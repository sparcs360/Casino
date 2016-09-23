package com.sparcs.casino.roulette;

import static org.junit.Assert.*;

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
}
