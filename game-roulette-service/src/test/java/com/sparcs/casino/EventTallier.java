package com.sparcs.casino;

import java.util.function.Consumer;

import com.sparcs.casino.events.Event;
import com.sparcs.casino.events.EventBroker;

/**
 * Helper class for testing {@link Event}s dispached by the {@link EventBroker}.
 * 
 * @author Lee Newfeld
 */
public class EventTallier implements Consumer<Event> {
	
	private int tally;

	public int getTally() {
		
		return tally;
	}

	@Override
	public void accept(Event e) {

		tally++;
	}
}
