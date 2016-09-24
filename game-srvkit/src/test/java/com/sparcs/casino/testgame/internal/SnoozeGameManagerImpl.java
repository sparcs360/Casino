package com.sparcs.casino.testgame.internal;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sparcs.casino.game.GameManagerImpl;
import com.sparcs.casino.game.Room;
import com.sparcs.casino.testgame.SnoozeGameManager;

/**
 * The referee overseeing a game of Snooze
 *  
 * @author Lee Newfeld
 */
@Component("GameManager")
@Scope("prototype")
public class SnoozeGameManagerImpl extends GameManagerImpl implements SnoozeGameManager {

	private static final Logger log = LoggerFactory.getLogger(SnoozeGameManagerImpl.class);

	private long endGameTime;

	@PostConstruct
	private void initialise() {

		log.trace("Created {}", this);
	}

	@Override
	protected void onInitialise(Room room) {

		log.trace("{}: onInitialise", this);

		// EVENT SUBSCRIPTION
		//
		// room.enter()
		room.getEventBroker().subscribe(be -> {
			
			Room.EnterEvent e = (Room.EnterEvent)be;
			shout("Greetings {}!  Feel free to watch, or take a seat and play", e.getCustomer().getNickName());
		}, Room.EnterEvent.class);

		// room.joinGame()
		room.getEventBroker().subscribe(be -> {
			
			Room.JoinGameEvent e = (Room.JoinGameEvent)be;
			shout("Thanks for joining us {}!", e.getSpectator().getNickName());
		}, Room.JoinGameEvent.class);

		// room.leaveGame()
		room.getEventBroker().subscribe(be -> {
			
			Room.LeaveGameEvent e = (Room.LeaveGameEvent)be;
			shout("Thanks for playing {}, come back soon!", e.getPlayer().getNickName());
		}, Room.LeaveGameEvent.class);

		// room.exit()
		room.getEventBroker().subscribe(be -> {
			
			Room.ExitEvent e = (Room.ExitEvent)be;
			shout("See you next time {}!", e.getSpectator().getNickName());
		}, Room.ExitEvent.class);

		endGameTime = (int) (Math.random() * 10) + 10;
		log.debug("{}: Game over in {} ticks", this, endGameTime);
	}

	@Override
	protected boolean onUpdate(Room room) {

		log.trace("{}: onUpdate", this);

		return getGameState().getGameTime() < endGameTime;
	}
	
	@Override
	protected void onShutdown(Room room) {

		log.trace("{}: onShutdown", this);
	}

	@Override
	public String toString() {

		return String.format("SnoozeGameManagerImpl@%x",
				this.hashCode());
	}
}
