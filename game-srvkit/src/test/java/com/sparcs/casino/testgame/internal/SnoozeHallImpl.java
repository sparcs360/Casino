package com.sparcs.casino.testgame.internal;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sparcs.casino.game.HallImpl;
import com.sparcs.casino.game.Room;
import com.sparcs.casino.testgame.SnoozeHall;
import com.sparcs.casino.testgame.SnoozeRoom;

/**
 * A Hall of {@link SnoozeRoom rooms}, each hosting a game of Snooze.
 * 
 * @author Lee Newfeld
 */
@Component
public class SnoozeHallImpl extends HallImpl implements SnoozeHall {

	private static final Logger log = LoggerFactory.getLogger(SnoozeHallImpl.class);

	private static final int ROOM_COUNT = 3;
	
	/**
	 * Configure the Hall based on custom properties
	 */
	@PostConstruct
	private void initialise() {

		// TODO: Move this into HallImpl?  How to handle general/specific configuration?
		log.debug("Constructing {} Room(s)", ROOM_COUNT);

		for (int i = 0; i < 3; i++) {

			log.trace("Creating Room #{}", i);
			Room room = (Room) applicationContext.getBean("Room");
			log.trace("Created Room #{}", i);

			this.getRooms().add(room);
		}
	}

	@Override
	public String toString() {

		return String.format("SnoozeGameHallImpl@%x",
				this.hashCode());
	}
}
