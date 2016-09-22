package com.sparcs.casino.roulette;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
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

		log.trace("-shouldBeAtRestWhenConstructed");
	}
	
	@Test
	public void shouldBeAtRestAfterReset() {

		log.trace("+shouldBeAtRestAfterReset");
		
		wheel.reset();
		assertEquals(RouletteWheel.State.AT_REST, wheel.getState());

		log.trace("-shouldBeAtRestAfterReset");
	}

	@Test
	public void shouldStartSpinningWhenStarted() {

		log.trace("+shouldStartSpinningWhenStarted");
		
		wheel.start();
		assertEquals(RouletteWheel.State.SPINNING, wheel.getState());

		log.trace("-shouldStartSpinningWhenStarted");
	}

	@Rule public ExpectedException wheelCanOnlyBeStartedWhenAtRest =
			ExpectedException.none();
	@Test
	public void shouldntStartWhenNotAtRest() {

		log.trace("+shouldntStartWhenNotAtRest");

		wheel.start();
		
		wheelCanOnlyBeStartedWhenAtRest.expect(RouletteException.class);
		/* TODO: A way of distinguishing one Exception from another
		 * (subclass?  discriminator field?) 
		 */
		//wheelCanOnlyBeStartedWhenAtRest.expectMessage("...");

		wheel.start();

		log.trace("-shouldntStartWhenNotAtRest");
	}

	@Test
	public void shouldBeResetableWhileSpinning() {

		log.trace("+shouldBeResetableWhileSpinning");
		
		wheel.start();
		wheel.reset();
		wheel.start();
		assertEquals(RouletteWheel.State.SPINNING, wheel.getState());

		log.trace("-shouldBeResetableWhileSpinning");
	}
}
