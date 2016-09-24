package com.sparcs.casino.events;

import java.util.function.Consumer;

/**
 * An agent for asynchronous routing of {@link Event}s between a single Publisher
 * and multiple Subscribers (i.e., {@link Consumer} of {@link Event} ).
 * 
 * @author Lee Newfeld
 */
public interface EventBroker {
	
	void subscribe(Consumer<Event> subscriber, Class<? extends Event> eventClass);
	void unsubscribe(Consumer<Event> subscriber, Class<? extends Event> eventClass);

	void raiseEvent(Event event);
	void dispatchEvent();
	
	void shutdown();
}
