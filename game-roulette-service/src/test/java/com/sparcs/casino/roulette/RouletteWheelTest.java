package com.sparcs.casino.roulette;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.sparcs.casino.BaseTest;

/**
 * Test of {@link RouletteWheel} behaviour
 * 
 * @author Lee Newfeld
 */
public class RouletteWheelTest extends BaseTest {

	private static final Logger log = LoggerFactory.getLogger(RouletteWheelTest.class);

	@Autowired
	private RouletteWheel wheel;
	
    @Before
    public void beforeTest() {

    	super.beforeTest();
    	
		log.trace("Feature under test: {}", wheel);
    }

	@Test
	public void shouldBeAtRestWhenConstructed() {

		log.trace("+shouldBeAtRestWhenConstructed");
		
		assertEquals(RouletteWheel.State.AT_REST, wheel.getState());
		assertEquals(RouletteWheel.RESULT_UNDEFINED, wheel.getResult());

		log.trace("-shouldBeAtRestWhenConstructed");
	}
	
	@Test
	public void shouldBeAtRestAfterReset() {

		log.trace("+shouldBeAtRestAfterReset");
		
		wheel.reset();
		assertEquals(RouletteWheel.State.AT_REST, wheel.getState());
		assertEquals(RouletteWheel.RESULT_UNDEFINED, wheel.getResult());

		log.trace("-shouldBeAtRestAfterReset");
	}

	@Test
	public void shouldBeResetableWhileSpinning() {

		log.trace("+shouldBeResetableWhileSpinning");
		
		wheel.update();
		wheel.reset();
		wheel.update();
		assertEquals(RouletteWheel.State.SPINNING, wheel.getState());
		assertEquals(RouletteWheel.RESULT_UNDEFINED, wheel.getResult());

		log.trace("-shouldBeResetableWhileSpinning");
	}

	@Test
	public void shouldFollowLifecycleAfterStart() {

		log.trace("+shouldFollowLifecycleAfterStart");

		RouletteWheel.State state;

		// wheel is initially AT_REST
		
		state = wheel.update();	// SPINNING
		assertSame("Returned State should be the current state", wheel.getState(), state);
		assertEquals(RouletteWheel.State.SPINNING, state);
		assertEquals(RouletteWheel.RESULT_UNDEFINED, wheel.getResult());

		state = wheel.update();	// BALL_SPINNING
		assertSame("Returned State should be the current state", wheel.getState(), state);
		assertEquals(RouletteWheel.State.BALL_SPINNING, state);
		assertEquals(RouletteWheel.RESULT_UNDEFINED, wheel.getResult());

		state = wheel.update();	// NO_MORE_BETS
		assertSame("Returned State should be the current state", wheel.getState(), state);
		assertEquals(RouletteWheel.State.NO_MORE_BETS, state);
		assertEquals(RouletteWheel.RESULT_UNDEFINED, wheel.getResult());

		state = wheel.update();	// BALL_AT_REST
		assertSame("Returned State should be the current state", wheel.getState(), state);
		assertEquals(RouletteWheel.State.BALL_AT_REST, state);
		assertNotEquals(RouletteWheel.RESULT_UNDEFINED, wheel.getResult());

		state = wheel.update();	// BETS_RESOLVED
		assertSame("Returned State should be the current state", wheel.getState(), state);
		assertEquals(RouletteWheel.State.BETS_RESOLVED, state);
		assertNotEquals(RouletteWheel.RESULT_UNDEFINED, wheel.getResult());

		// Back to AT_REST
		state = wheel.update();	// AT_REST
		assertSame("Returned State should be the current state", wheel.getState(), state);
		assertEquals(RouletteWheel.State.AT_REST, state);
		assertEquals(RouletteWheel.RESULT_UNDEFINED, wheel.getResult());

		log.trace("-shouldFollowLifecycleAfterStart");
	}
}
