package com.sparcs.casino.game;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Lee Newfeld
 */
public class EventBrokerTest {

	private static final Logger log = LoggerFactory.getLogger(EventBrokerTest.class);

	private interface EventBroker {
		
		void subscribe(EventSubscriber subscriber, Class<? extends Event> eventClass);
		void unsubscribe(EventSubscriber subscriber, Class<? extends Event> eventClass);
		Set<EventSubscriber> getSubscribers(Class<? extends Event> eventClass);

		void raiseEvent(Event event);
		void dispatchEvent();
	}
	
	private interface EventSubscriber {

		void onEventRecieved(Event event);
	}
	
	private interface Event {

	}
	
	//---
	
	private static class EventBrokerImpl implements EventBroker {

		private static final Logger log = LoggerFactory.getLogger(EventBrokerImpl.class);

		private Queue<Event> eventQueue;
		
		private Map<Class<? extends Event>, Set<EventSubscriber>> allSubscribers;

		public EventBrokerImpl() {
			
			eventQueue = new PriorityQueue<>();
			allSubscribers = new HashMap<>();
		}
		
		@Override
		public void subscribe(EventSubscriber subscriber, Class<? extends Event> eventClass) {

			Set<EventSubscriber> subscribers = allSubscribers.get(eventClass);
			if( subscribers == null ) {
				subscribers = new HashSet<>();
				allSubscribers.put(eventClass, subscribers);
			}
			subscribers.add(subscriber);

			log.trace("{} subscribed to {} [count={}]", subscriber, eventClass, subscribers.size());
		}

		@Override
		public void unsubscribe(EventSubscriber subscriber, Class<? extends Event> eventClass) {

			Set<EventSubscriber> subscribers = allSubscribers.get(eventClass);
			if( subscribers != null ) {
				subscribers.remove(subscriber);
				if( subscribers.isEmpty() ) {
					allSubscribers.remove(eventClass);
				}
			}

			log.trace("{} unsubscribed from {} [count={}]", subscriber, eventClass, subscribers.size());
		}

		@Override
		public Set<EventSubscriber> getSubscribers(Class<? extends Event> eventClass) {

			return allSubscribers.get(eventClass);
		}

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
				.peek(s -> log.trace("dispatching event {} to {}", event, s))
				.forEach(s -> s.onEventRecieved(event));
		}
	}

	private static class TestEventSubscriber implements EventSubscriber {

		private int recieveCount = 0;
		
		@Override
		public void onEventRecieved(Event event) {
			
			recieveCount++;
			System.out.println(this.toString() + ": Recieved Message");
		}
		
		public int getRecieveCount() {
			
			return recieveCount;
		}
	}
	
	private static class EventImpl implements Event {
		
	}

	@Test
	public void todo() {

		log.trace("+todo");
		
		EventBroker eventBroker = new EventBrokerImpl();

		TestEventSubscriber subscriber1 = new TestEventSubscriber();
		TestEventSubscriber subscriber2 = new TestEventSubscriber();

		assertNull(eventBroker.getSubscribers(EventImpl.class));
		eventBroker.subscribe(subscriber1, EventImpl.class);
		assertEquals(1, eventBroker.getSubscribers(EventImpl.class).size());
		eventBroker.unsubscribe(subscriber1, EventImpl.class);
		assertNull(eventBroker.getSubscribers(EventImpl.class));
		eventBroker.subscribe(subscriber1, EventImpl.class);
		assertEquals(1, eventBroker.getSubscribers(EventImpl.class).size());
		eventBroker.subscribe(subscriber2, EventImpl.class);
		assertEquals(2, eventBroker.getSubscribers(EventImpl.class).size());
		
		assertEquals(0, subscriber1.getRecieveCount());
		assertEquals(0, subscriber2.getRecieveCount());
		
		eventBroker.raiseEvent(new EventImpl());

		assertEquals(0, subscriber1.getRecieveCount());
		assertEquals(0, subscriber2.getRecieveCount());

		eventBroker.dispatchEvent();

		assertEquals(1, subscriber1.getRecieveCount());
		assertEquals(1, subscriber2.getRecieveCount());

		log.trace("-todo");
	}
}
