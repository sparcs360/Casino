package com.sparcs.casino.testgame.internal;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sparcs.casino.game.Room;
import com.sparcs.casino.game.RoomImpl;
import com.sparcs.casino.testgame.SnoozeRoom;

/**
 * A {@link Room} hosting a game of Snooze.
 * 
 * @author Lee Newfeld
 */
@Component("Room")
@Scope("prototype")
public class SnoozeRoomImpl extends RoomImpl implements SnoozeRoom {

	private static final Logger log = LoggerFactory.getLogger(SnoozeRoomImpl.class);

	@PostConstruct
	private void initialise() {

		log.trace("Created {}", this);
	}

	@Override
	public String toString() {

		return String.format("SnoozeRoomImpl@%x",
				this.hashCode());
	}
}
