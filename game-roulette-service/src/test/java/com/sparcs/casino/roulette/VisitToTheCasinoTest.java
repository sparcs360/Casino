package com.sparcs.casino.roulette;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

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
import com.sparcs.casino.Casino;
import com.sparcs.casino.Customer;
import com.sparcs.casino.game.Game;
import com.sparcs.casino.game.GameType;
import com.sparcs.casino.game.Room;
import com.sparcs.casino.game.RoomImpl;

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
    
    private List<Room<? extends Game>> rouletteRooms;
    private Room<Roulette> rouletteRoom;
    private Roulette rouletteGame;

    @Before
    public void onlyOnce() {

        // Setup games and rooms
        rouletteGame = new RouletteImpl();
        rouletteRoom = new RoomImpl<Roulette>(rouletteGame);
        rouletteRooms = new ArrayList<>();
        rouletteRooms.add(rouletteRoom);
        
        // Mocks for functionality in other Domains
        lee = Mockito.mock(Customer.class);

        casino = Mockito.mock(Casino.class);
        when(casino.signIn(eq("Lee"), anyString())).thenReturn(lee);
        when(casino.getGamingRooms(any(Customer.class), eq(GameType.ROULETTE))).thenReturn(rouletteRooms);
    }

	@Test
	public void shouldHaveAnEnjoyableTime() {

        // Lee enters the Casino
        Customer customer = casino.signIn("Lee", "abc");
        assertEquals("Should be Lee", lee, customer);
        
        // He wants to play Roulette, where's the action?
        List<Room<Roulette>> rooms = casino.getGamingRooms(customer, GameType.ROULETTE);
        assertNotNull("Should find some Roulette Rooms", rooms);
        assertTrue("Our test room should be in the list", rooms.contains(rouletteRoom));

        // Go home
        casino.signOut(customer);
	}
}
