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

	private Stage stage;
	private int result;

	@PostConstruct
	private void initialise() {
		
		log.trace("Created {}", this);
		
		reset();
	}

	@Override
	public void reset() {

		log.trace("reset {}", this);

		setStage(Stage.AT_REST);
		setResult(RouletteWheel.RESULT_UNDEFINED);
	}

	@Override
	public Stage update() {

		log.trace("update {}", this);

		switch(stage) {
			case AT_REST: setStage(Stage.SPINNING); break;
			case SPINNING: setStage(Stage.BALL_SPINNING); break;
			case BALL_SPINNING: setStage(Stage.NO_MORE_BETS); break;
			case NO_MORE_BETS: {
				setStage(Stage.BALL_AT_REST);
				// TODO: Need a richer datatype than int to hold this result!
				setResult((int)(Math.random() * 37));
				break;
			}
			case BALL_AT_REST: setStage(Stage.BETS_RESOLVED); break;
			case BETS_RESOLVED: reset(); break;
			default: {

				throw new RouletteException(String.format("Unexpected Wheel State - %s", stage.name()));
			}
		}

		return stage;
	}

	@Override
	public Stage getStage() {

		return stage;
	}

	/**
	 * Update current lifecycle stage (with logging)
	 * 
	 * @param value New {@link Stage}
	 */
	protected void setStage(Stage value) {
		
		log.trace("state: {} -> {}", stage, value);

		stage = value;
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
