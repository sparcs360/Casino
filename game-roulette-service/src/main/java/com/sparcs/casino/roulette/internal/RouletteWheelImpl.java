package com.sparcs.casino.roulette.internal;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sparcs.casino.roulette.RouletteException;
import com.sparcs.casino.roulette.RouletteWheel;

/**
 * Implementation of a {@link RouletteWheel}.
 * 
 * @author Lee Newfeld
 */
@Component("Wheel")
@Scope("prototype")
public class RouletteWheelImpl implements RouletteWheel {

	private static final Logger log = LoggerFactory.getLogger(RouletteWheelImpl.class);

	private State state;
	private int result;

	@PostConstruct
	private void initialise() {
		
		log.trace("Created {}", this);
		
		reset();
	}

	@Override
	public void reset() {

		log.trace("reset {}", this);

		setState(State.AT_REST);
		setResult(RouletteWheel.RESULT_UNDEFINED);
	}

	@Override
	public void start() {

		log.trace("start {}", this);

		if( state != State.AT_REST ) {
			throw new RouletteException("Wheel must be at rest");
		}
		
		setState(State.SPINNING);
	}

	@Override
	public State update() {

		log.trace("update {}", this);

		switch(state) {
			case AT_REST: setState(State.AT_REST); break;
			case SPINNING: setState(State.BALL_SPINNING); break;
			case BALL_SPINNING: setState(State.NO_MORE_BETS); break;
			case NO_MORE_BETS: {
				setState(State.BALL_AT_REST);
				// TODO: Need a richer datatype than int to hold this result!
				setResult((int)(Math.random() * 37));
				break;
			}
			case BALL_AT_REST: setState(State.BETS_RESOLVED); break;
			case BETS_RESOLVED: reset(); break;
			default: {

				throw new RouletteException(String.format("Unexpected Wheel State - %s", state.name()));
			}
		}

		return state;
	}

	@Override
	public State getState() {

		return state;
	}

	/**
	 * Update current state (with logging)
	 * 
	 * @param value New {@link State}
	 */
	protected void setState(State value) {
		
		log.trace("state: {} -> {}", state, value);

		state = value;
	}

	@Override
	public int getResult() {

		return result;
	}

	/**
	 * Update result (with logging)
	 * 
	 * @param value New result
	 */
	private void setResult(int value) {

		log.trace("result: {} -> {}", result, value);

		result = value;
	}
}
