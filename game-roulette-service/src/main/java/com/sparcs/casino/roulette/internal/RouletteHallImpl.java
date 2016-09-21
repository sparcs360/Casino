package com.sparcs.casino.roulette.internal;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sparcs.casino.RouletteConfigurationProperties;
import com.sparcs.casino.game.HallImpl;
import com.sparcs.casino.game.Room;
import com.sparcs.casino.roulette.RouletteHall;
import com.sparcs.casino.roulette.RouletteRoom;

/**
 * A Hall of {@link RouletteRoom rooms} hosting Roulette games.
 * 
 * @author Lee Newfeld
 */
@Component
public class RouletteHallImpl extends HallImpl implements RouletteHall {

	private static final Logger log = LoggerFactory.getLogger(RouletteHallImpl.class);

	@Autowired
	private RouletteConfigurationProperties config;

	/**
	 * Configure the Hall based on roulette.* properties
	 */
	@PostConstruct
	private void initialise() {

		// TODO: Move this into HallImpl?  How to handle general/specific configuration?
		log.debug("Constructing {} Room(s)", config.getGameCount());

		for (int i = 0; i < config.getGameCount(); i++) {

			log.trace("Creating Room #{}", i);
			Room room = (Room) applicationContext.getBean("Room");
			log.trace("Created Room #{}", i);

			this.getRooms().add(room);
		}
	}
}
