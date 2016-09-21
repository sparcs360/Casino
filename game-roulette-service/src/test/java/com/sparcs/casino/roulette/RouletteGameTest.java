package com.sparcs.casino.roulette;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.sparcs.casino.BaseTest;
import com.sparcs.casino.roulette.internal.RouletteRoomImpl;

public class RouletteGameTest extends BaseTest {

	private static final Logger log = LoggerFactory.getLogger(RouletteGameTest.class);

	@Autowired
	private RouletteRoomImpl room;

	@Before
	public void beforeTest() {

		super.beforeTest();
		
		log.trace("Feature under test: {}", room);
	}

	@Test
	public void gameLoopDoesntRunWhenRoomIsEmpty() {

		log.trace("+gameLoopDoesntRunWhenRoomIsEmpty");

		assertTrue(room.isEmpty());
		assertFalse(room.executeGameLoop());
		
		log.trace("-gameLoopDoesntRunWhenRoomIsEmpty");
	}

	@Test
	public void gameLoopRunsWhenRoomIsntEmpty() {

		log.trace("+gameLoopRunsWhenRoomIsntEmpty");

		room.enter(lee);

		assertFalse(room.isEmpty());
		assertTrue(room.executeGameLoop());

		log.trace("-gameLoopRunsWhenRoomIsntEmpty");
	}
}
