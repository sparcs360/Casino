package com.sparcs.casino.roulette.internal;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sparcs.casino.roulette.RouletteWheel;

/**
 * Implementation of a {@link RouletteWheel}.
 * 
 * @author Lee Newfeld
 */
@Component("Wheel")
@Scope("prototype")
public class RouletteWheelImpl implements RouletteWheel {

	private State state;

	@PostConstruct
	private void initialise() {
		
		reset();
	}

	@Override
	public void reset() {

		state = State.AT_REST;
	}

	@Override
	public State getState() {

		return state;
	}
}
