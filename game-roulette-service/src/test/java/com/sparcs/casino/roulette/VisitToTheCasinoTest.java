package com.sparcs.casino.roulette;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.sparcs.casino.BaseTest;
import com.sparcs.casino.otherdomains.Casino;
import com.sparcs.casino.otherdomains.Customer;

/**
 * A vehicle for prototyping the Roulette API!
 *   
 * @author Lee Newfeld
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class VisitToTheCasinoTest extends BaseTest {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(VisitToTheCasinoTest.class);

    @Mock
    private Customer lee;

    @Mock
    private Casino casino;

    @Before
    public void onlyOnce() {

    	log.info("onlyOnce");

        // Mocks for functionality in other Domains
        lee = Mockito.mock(Customer.class);

        casino = Mockito.mock(Casino.class);
        when(casino.signIn(eq("Lee"), anyString())).thenReturn(lee);
    }

	@Test
	public void shouldHaveAnEnjoyableTime() {

        // Lee enters the Casino
        Customer customer = casino.signIn("Lee", "abc");
        assertEquals("Should be Lee", lee, customer);

        // Go home
        casino.signOut(customer);
	}
}
