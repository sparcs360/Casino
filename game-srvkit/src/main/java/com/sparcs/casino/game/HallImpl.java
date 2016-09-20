package com.sparcs.casino.game;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.sparcs.casino.Casino;

/**
 * The base implementation of a {@link Hall} of {@link Game}
 * {@link Room}s within the {@link Casino}.
 * 
 * @author Lee Newfeld
 */
public abstract class HallImpl implements Hall, ApplicationContextAware {

	private final Logger log = LoggerFactory.getLogger(getClass());

	protected ApplicationContext applicationContext;
	
	private List<Room> rooms;

	/**
	 * Constructor
	 */
    protected HallImpl() {

        rooms = new ArrayList<>();
    }
    
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

		this.applicationContext = applicationContext;
	}

	@Override
	public List<Room> getRooms() {

		return rooms;
	}
	
	@Override
	public boolean executeGameLoops() {

		log.debug("Executing game loops");

		boolean activity = false;
		for( Room room : rooms ) {

			if( room.executeGameLoop() ) {
				activity = true;
			}
		}
		
		return activity;
	}
}
