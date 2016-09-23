package com.sparcs.casino.roulette;

import static org.junit.Assert.*;

import java.util.stream.IntStream;

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
		
		assertEquals(RouletteWheel.Stage.AT_REST, wheel.getStage());
		assertEquals(RouletteWheel.RESULT_UNDEFINED, wheel.getResult());

		log.trace("-shouldBeAtRestWhenConstructed");
	}
	
	@Test
	public void shouldBeAtRestAfterReset() {

		log.trace("+shouldBeAtRestAfterReset");
		
		wheel.reset();
		assertEquals(RouletteWheel.Stage.AT_REST, wheel.getStage());
		assertEquals(RouletteWheel.RESULT_UNDEFINED, wheel.getResult());

		log.trace("-shouldBeAtRestAfterReset");
	}

	@Test
	public void shouldBeResetableWhileSpinning() {

		log.trace("+shouldBeResetableWhileSpinning");
		
		wheel.update();
		wheel.reset();
		wheel.update();
		assertEquals(RouletteWheel.Stage.SPINNING, wheel.getStage());
		assertEquals(RouletteWheel.RESULT_UNDEFINED, wheel.getResult());

		log.trace("-shouldBeResetableWhileSpinning");
	}

	@Test
	public void shouldFollowLifecycleAfterStart() {

		log.trace("+shouldFollowLifecycleAfterStart");


		// AT_REST
		RouletteWheel.Stage stage = wheel.getStage();
		
		assertTrue("Players can bet", stage.isBettingAllowed());

		// SPINNING
		stage = wheel.update();
		assertSame("Returned State should be the current state", wheel.getStage(), stage);
		assertEquals(RouletteWheel.Stage.SPINNING, stage);
		assertEquals(RouletteWheel.RESULT_UNDEFINED, wheel.getResult());
		
		assertTrue("Players can bet", stage.isBettingAllowed());

		// BALL_SPINNING
		stage = wheel.update();
		assertSame("Returned State should be the current state", wheel.getStage(), stage);
		assertEquals(RouletteWheel.Stage.BALL_SPINNING, stage);
		assertEquals(RouletteWheel.RESULT_UNDEFINED, wheel.getResult());
		
		assertTrue("Players can bet", stage.isBettingAllowed());

		// NO_MORE_BETS
		stage = wheel.update();
		assertSame("Returned State should be the current state", wheel.getStage(), stage);
		assertEquals(RouletteWheel.Stage.NO_MORE_BETS, stage);
		assertEquals(RouletteWheel.RESULT_UNDEFINED, wheel.getResult());
		
		assertFalse("Players cannot bet", stage.isBettingAllowed());

		// BALL_AT_REST
		stage = wheel.update();
		assertSame("Returned State should be the current state", wheel.getStage(), stage);
		assertEquals(RouletteWheel.Stage.BALL_AT_REST, stage);
		assertNotEquals(RouletteWheel.RESULT_UNDEFINED, wheel.getResult());
		
		assertFalse("Players cannot bet", stage.isBettingAllowed());

		// BETS_RESOLVED
		stage = wheel.update();
		assertSame("Returned State should be the current state", wheel.getStage(), stage);
		assertEquals(RouletteWheel.Stage.BETS_RESOLVED, stage);
		assertNotEquals(RouletteWheel.RESULT_UNDEFINED, wheel.getResult());
		
		assertFalse("Players cannot bet", stage.isBettingAllowed());

		// AT_REST (again)
		assertEquals(RouletteWheel.Stage.AT_REST, wheel.update());

		log.trace("-shouldFollowLifecycleAfterStart");
	}

	@Test
	public void shouldValidateBetableNumbers() {

		log.trace("+shouldValidateBetableNumbers");
		
		IntStream.of(-1, 0, 37)
				 .forEach(i -> assertFalse(RouletteWheel.canBetOnNumber(i)));

		IntStream.range(1, 36)
				 .forEach(i -> assertTrue(RouletteWheel.canBetOnNumber(i)));

		log.trace("-shouldValidateBetableNumbers");
	}
}
