package com.sparcs.casino.roulette;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.sparcs.casino.BaseTest;

/**
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
	public void wheelShouldBeAtRestWhenConstructed() {

		log.trace("+wheelShouldBeAtRestWhenConstructed");
		
		assertEquals(RouletteWheel.State.AT_REST, wheel.getState());

		log.trace("-wheelShouldBeAtRestWhenConstructed");
	}
	
	@Test
	public void wheelShouldBeAtRestAfterReset() {

		log.trace("+wheelShouldBeAtRestAfterReset");
		
		wheel.reset();
		assertEquals(RouletteWheel.State.AT_REST, wheel.getState());

		log.trace("-wheelShouldBeAtRestAfterReset");
	}
}
