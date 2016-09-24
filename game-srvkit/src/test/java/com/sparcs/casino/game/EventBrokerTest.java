package com.sparcs.casino.game;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import com.sparcs.casino.events.Event;
import com.sparcs.casino.events.EventBroker;
import com.sparcs.casino.events.EventBrokerImpl;

/**
 * 
 * @author Lee Newfeld
 */
public class EventBrokerTest {

	private static final Logger log = LoggerFactory.getLogger(EventBrokerTest.class);
	
	private static class TestEventSubscriber implements Consumer<Event> {

		private List<Event> eventsRecieved;
		
		public TestEventSubscriber() {
			
			eventsRecieved = new ArrayList<>();
		}

		@Override
		public void accept(Event event) {
			
			eventsRecieved.add(event);
			log.debug("{} recieved {}", this, event);
		}
		
		public List<Event> getRecievedEvents() {
			
			return eventsRecieved;
		}
		
		@Override
		public String toString() {
			
			return String.format("%s@%x", getClass().getSimpleName(), hashCode());
		}
	}
	
	private static class TestEvent implements Event {
		
		private String message;
		
		public TestEvent(String message) {
			
			this.message = message;
		}

		@Override
		public String toString() {
			
			return String.format("%s@%x[%s]", getClass().getSimpleName(), hashCode(), message);
		}
	}
	
	private EventBroker eventBroker;
	private Map<Class<? extends Event>, Set<Consumer<Event>>> allSubscribers;
	
	@SuppressWarnings("unchecked")
	@Before
	public void beforeTest() {
		
		eventBroker = new EventBrokerImpl();
		
		Field allSubscribersField = ReflectionUtils.findField(EventBrokerImpl.class, "allSubscribers");
		assertNotNull(allSubscribersField);
		ReflectionUtils.makeAccessible(allSubscribersField);
		allSubscribers = (Map<Class<? extends Event>, Set<Consumer<Event>>>)ReflectionUtils.getField(allSubscribersField, eventBroker);
		assertNotNull(allSubscribers);
		assertNull(allSubscribers.get(TestEvent.class));
	}

	@Test
	public void shouldAddAndRemoveSubscribers() {

		log.trace("+shouldAddAndRemoveSubscribers");
		
		TestEventSubscriber subscriber1 = new TestEventSubscriber();
		TestEventSubscriber subscriber2 = new TestEventSubscriber();

		eventBroker.subscribe(subscriber1, TestEvent.class);
		assertEquals(1, allSubscribers.get(TestEvent.class).size());
		eventBroker.unsubscribe(subscriber1, TestEvent.class);
		assertNull(allSubscribers.get(TestEvent.class));
		eventBroker.subscribe(subscriber1, TestEvent.class);
		assertEquals(1, allSubscribers.get(TestEvent.class).size());
		eventBroker.subscribe(subscriber2, TestEvent.class);
		assertEquals(2, allSubscribers.get(TestEvent.class).size());
		
		log.trace("-shouldAddAndRemoveSubscribers");
	}

	@Test
	public void subscribersShouldRecieveEvent() {

		log.trace("+subscribersShouldRecieveEvent");
		
		TestEventSubscriber subscriber1 = new TestEventSubscriber();
		TestEventSubscriber subscriber2 = new TestEventSubscriber();
		eventBroker.subscribe(subscriber1, TestEvent.class);
		eventBroker.subscribe(subscriber2, TestEvent.class);

		assertEquals(0, subscriber1.getRecievedEvents().size());
		assertEquals(0, subscriber2.getRecievedEvents().size());

		TestEvent event = new TestEvent("TEST");
		eventBroker.raiseEvent(event);

		assertEquals(0, subscriber1.getRecievedEvents().size());
		assertEquals(0, subscriber2.getRecievedEvents().size());

		eventBroker.dispatchEvent();

		assertEquals(1, subscriber1.getRecievedEvents().size());
		assertSame(event, subscriber1.getRecievedEvents().get(0));
		assertEquals(1, subscriber2.getRecievedEvents().size());
		assertSame(event, subscriber2.getRecievedEvents().get(0));

		log.trace("-subscribersShouldRecieveEvent");
	}

	@Test
	public void subscriberAsClosure() {

		log.trace("+subscriberAsClosure");

		assertNull(allSubscribers.get(TestEvent.class));

		Consumer<Event> subscriber = e -> log.info("Recieved {}", e);
		eventBroker.subscribe(subscriber, TestEvent.class);

		assertEquals(1, allSubscribers.get(TestEvent.class).size());

		TestEvent event = new TestEvent("TEST");
		eventBroker.raiseEvent(event);
		eventBroker.dispatchEvent();

		eventBroker.unsubscribe(subscriber, TestEvent.class);

		assertNull(allSubscribers.get(TestEvent.class));

		eventBroker.shutdown();
		
		log.trace("-subscriberAsClosure");
	}
}
