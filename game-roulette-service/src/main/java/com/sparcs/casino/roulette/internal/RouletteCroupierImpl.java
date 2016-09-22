package com.sparcs.casino.roulette.internal;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sparcs.casino.game.GameManagerImpl;
import com.sparcs.casino.game.Room;
import com.sparcs.casino.roulette.RouletteCroupier;
import com.sparcs.casino.roulette.RouletteWheel;

/**
 * The Croupier overseeing a game of Roulette
 *  
 * @author Lee Newfeld
 */
@Component("GameManager")
@Scope("prototype")
public class RouletteCroupierImpl extends GameManagerImpl implements RouletteCroupier {

	// TODO: Make these .properties?
	private static final int WHEEL_AT_REST_DURATION = 5;
	private static final int WHEEL_SPINNING_DURATION = 5;
	private static final int WHEEL_BALL_SPINNING_DURATION = 5;
	private static final int WHEEL_NO_MORE_BETS_DURATION = 5;
	private static final int WHEEL_BALL_AT_REST_DURATION = 5;
	private static final int WHEEL_BETS_RESOLVED_DURATION = 5;

	private static final Logger log = LoggerFactory.getLogger(RouletteCroupierImpl.class);
	
	@Autowired
	private RouletteWheel wheel;

	private long nextWheelEventTime;
	
	@PostConstruct
	private void initialise() {

		log.trace("Created {}", this);
	}

	@Override
	protected void onInitialise(Room room) {

		log.trace("{}.{}: onInitialise", room, this);

		nextWheelEventTime = getGameState().getGameTime() + WHEEL_AT_REST_DURATION;

		shout("Greetings {}!  Feel free to watch, or take a seat and play", room.getSpectators());
	}

	@Override
	protected boolean onUpdate(Room room) {

		log.trace("{}.{}: onUpdate", room, this);

		updateWheel();

		return true;
	}

	/**
	 * Keep an eye on the Wheel...
	 */
	private void updateWheel() {

		if( getGameState().getGameTime() >= nextWheelEventTime ) {

			switch(wheel.update()) {
			
				case AT_REST:
					shout("The Croupier gets ready to start a new game...");
					nextWheelEventTime = getGameState().getGameTime() + WHEEL_AT_REST_DURATION;
					break;

				case SPINNING:
					shout("The Croupier spins the Roulette Wheel...");
					nextWheelEventTime = getGameState().getGameTime() + WHEEL_SPINNING_DURATION;
					break;

				case BALL_SPINNING:
					shout("The Croupier spins the ball against the Wheel...");
					nextWheelEventTime = getGameState().getGameTime() + WHEEL_BALL_SPINNING_DURATION;
					break;

				case NO_MORE_BETS:
					shout("The Croupier announces 'No More Bets'...");
					nextWheelEventTime = getGameState().getGameTime() + WHEEL_NO_MORE_BETS_DURATION;
					break;

				case BALL_AT_REST:
					shout("The Ball comes to rest on number {}", wheel.getResult());
					nextWheelEventTime = getGameState().getGameTime() + WHEEL_BALL_AT_REST_DURATION;
					break;

				case BETS_RESOLVED:
					shout("The Croupier has resolved all the bets");
					nextWheelEventTime = getGameState().getGameTime() + WHEEL_BETS_RESOLVED_DURATION;
					break;
			}
		}
	}

	@Override
	protected void onShutdown(Room room) {

		log.trace("{}.{}: onShutdown", room, this);
	}
}
