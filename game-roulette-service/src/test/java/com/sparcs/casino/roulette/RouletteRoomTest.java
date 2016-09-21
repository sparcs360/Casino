package com.sparcs.casino.roulette;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.sparcs.casino.BaseTest;
import com.sparcs.casino.roulette.internal.RouletteRoomImpl;

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
	public void roomIsEmptyWhenNoSpectatorsOrPlayers() {

		log.trace("+roomIsEmptyWhenNoSpectatorsOrPlayers");
		
		assertTrue(room.getSpectators().size() == 0);
		assertTrue(room.getGame().getPlayers().size() == 0);
		assertTrue(room.isEmpty());

		log.trace("-roomIsEmptyWhenNoSpectatorsOrPlayers");
	}

	@Test
	public void roomIsntEmptyWhenSomeoneEnters() {

		log.trace("+roomIsntEmptyWhenSomeoneEnters");

		room.enter(lee);

		assertFalse(room.isEmpty());
		assertTrue(room.executeGameLoop());

		log.trace("-roomIsntEmptyWhenSomeoneEnters");
	}
}
