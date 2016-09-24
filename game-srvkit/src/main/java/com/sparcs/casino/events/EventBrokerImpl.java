package com.sparcs.casino.events;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
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
		
		eventQueue = new PriorityQueue<>();
		allSubscribers = new HashMap<>();
	}
	
	@Override
	public void subscribe(Consumer<Event> subscriber, Class<? extends Event> eventClass) {

		Set<Consumer<Event>> subscribers = allSubscribers.get(eventClass);
		if( subscribers == null ) {
			subscribers = new HashSet<>();
			allSubscribers.put(eventClass, subscribers);
		}
		subscribers.add(subscriber);

		log.trace("{} subscribed to {} [count={}]", subscriber, eventClass, subscribers.size());
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

		log.trace("{} unsubscribed from {} [count={}]", subscriber, eventClass, subscribers.size());
	}

//	@Override
//	public Set<EventSubscriber> getSubscribers(Class<? extends Event> eventClass) {
//
//		return allSubscribers.get(eventClass);
//	}

	@Override
	public void raiseEvent(Event event) {

		log.trace("raiseEvent recieved {}", event);

		eventQueue.add(event);
	}

	@Override
	public void dispatchEvent() {

		Event event = eventQueue.poll();
		if( event == null ) {
			return;
		}

		allSubscribers.get(event.getClass())
			.stream()
			.peek(subscriber -> log.trace("dispatching event {} to {}", event, subscriber))
			.forEach(subscriber -> subscriber.accept(event));
	}

	@Override
	public void shutdown() {

		if( !allSubscribers.isEmpty() ) {
			log.warn("{} shutting down with regisatered subscribers:", this);

			allSubscribers.entrySet()
				.stream()
				.forEach( es -> log.trace("{}->{}", es.getKey(), es.getValue()) );
		}
	}

	@Override
	public String toString() {
		
		return String.format("%s@%x", getClass().getSimpleName(), hashCode());
	}
}
