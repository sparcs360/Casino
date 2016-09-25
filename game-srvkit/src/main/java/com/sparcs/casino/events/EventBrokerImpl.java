package com.sparcs.casino.events;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.function.Consumer;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Implementation of {@link EventBroker}
 * 
 * @author Lee Newfeld
 */
@Component
@Scope("prototype")
public class EventBrokerImpl implements EventBroker {

	private static final Logger log = LoggerFactory.getLogger(EventBrokerImpl.class);

	private Queue<Event> eventQueue;
	
	private Map<Class<? extends Event>, Set<Consumer<Event>>> allSubscribers;

	public EventBrokerImpl() {
		
		eventQueue = new LinkedList<>();
		allSubscribers = new HashMap<>();
	}
	
	@PostConstruct
	private void postConstruct() {

		log.trace("Created {}", this);
	}
	
	@Override
	public void subscribe(Consumer<Event> subscriber, Class<? extends Event> eventClass) {

		Set<Consumer<Event>> subscribers = allSubscribers.get(eventClass);
		if( subscribers == null ) {
			subscribers = new HashSet<>();
			allSubscribers.put(eventClass, subscribers);
		}
		subscribers.add(subscriber);

		log.trace("{} subscribed to {} [count={}]",
				subscriber, eventClass.getCanonicalName(), subscribers.size());
	}

	@Override
	public void unsubscribe(Consumer<Event> subscriber, Class<? extends Event> eventClass) {

		Set<Consumer<Event>> subscribers = allSubscribers.get(eventClass);
		if( subscribers != null ) {
			log.trace("unsubscribe: attempting to remove {}", subscriber);
			subscribers.remove(subscriber);
			if( subscribers.isEmpty() ) {
				allSubscribers.remove(eventClass);
			}
		}

		log.trace("{} unsubscribed from {} [count={}]",
				subscriber, eventClass.getCanonicalName(), subscribers.size());
	}

	@Override
	public void raiseEvent(Event event) {

		log.trace("Received: {}", event);

		eventQueue.add(event);
	}

	@Override
	public void dispatchEvents() {

		log.trace("+dispatchEvents(size={})", eventQueue.size());

		// TODO: Bail if too much work to do (there's always the next cycle!)
		for( Event event = eventQueue.poll(); event != null; event = eventQueue.poll() ) {

			// Get subscribers
			Set<Consumer<Event>> subscribers = allSubscribers.get(event.getClass());
			log.trace("Event: {} has {} subscriber(s)",
					event, subscribers == null ? 0 : subscribers.size());
			
			// Dispatch
			if( subscribers != null ) {
				for( Consumer<Event> subscriber : subscribers ) {
					
					log.trace("Dispatching to: {}", subscriber);
					subscriber.accept(event);
				}
			}
		}

		log.trace("-dispatchEvents(size={})", eventQueue.size());
	}

	@Override
	public void shutdown() {

		if( !allSubscribers.isEmpty() ) {
			log.warn("{} shutting down with unregistered subscribers:", this);

			allSubscribers.entrySet()
				.stream()
				.forEach( es -> log.trace("{}->{}", es.getKey(), es.getValue()) );
		}
	}

	@Override
	public String toString() {
		
		return String.format("%s@%x[subscriberCount=%d, queueLength=%d]",
				getClass().getSimpleName(), hashCode(),
				allSubscribers.size(), eventQueue.size());
	}
}
