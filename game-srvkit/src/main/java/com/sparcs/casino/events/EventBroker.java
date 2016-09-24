package com.sparcs.casino.events;

import java.util.function.Consumer;

/**
 * An agent for asynchronous routing of {@link Event}s between a single Publisher
 * and multiple Subscribers (i.e., {@link Consumer} of {@link Event} ).
 * 
 * @author Lee Newfeld
 */
public interface EventBroker {

	/**
	 * Register an interest in a particular subclass of {@link Event}.  Whenever
	 * an event of this type is {@link #raiseEvent(Event) raised}, the
	 * {@link Consumer} will be called.
	 * 
	 * @param subscriber The method to execute when an event is received
	 * @param eventClass The type of event to subscribe to.
	 */
	void subscribe(Consumer<Event> subscriber, Class<? extends Event> eventClass);
	
	/**
	 * Unregister from a previous {@link #subscribe(Consumer, Class) subscription}.
	 * 
	 * @param subscriber The method that event were being sent to
	 * @param eventClass The type of event subscribed to.
	 */
	void unsubscribe(Consumer<Event> subscriber, Class<? extends Event> eventClass);

	/**
	 * Raised an {@link Event}.  {@link #subscribe(Consumer, Class) Subscribers}
	 * will receive the event during the next call to {@link #dispatchEvents()}.
	 * 
	 * @param event
	 */
	void raiseEvent(Event event);
	
	/**
	 * Dispatch all {@link #raiseEvent(Event) events raised} since the last call
	 * to this method. 
	 */
	void dispatchEvents();

	/**
	 * Dump unregistered subscribers (if any) to the log.
	 */
	void shutdown();
}
