package com.sparcs.casino.roulette;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.sparcs.casino.BaseTest;
import com.sparcs.casino.Casino;
import com.sparcs.casino.Customer;
import com.sparcs.casino.game.GameType;
import com.sparcs.casino.game.Room;

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
    
    @Autowired
    private RouletteHall rouletteHall;

	@Before
    public void beforeTest() {

        // Mocks for functionality in other Domains
        lee = Mockito.mock(Customer.class);

        casino = Mockito.mock(Casino.class);
        when(casino.signIn(eq("Lee"), anyString())).thenReturn(lee);
        when(casino.findRooms(any(Customer.class), eq(GameType.ROULETTE))).thenReturn(rouletteHall.getRooms());
    }

	@Test
	public void shouldHaveAnEnjoyableTime() {

        //####################################################################//
        // Lee enters the Casino
        Customer customer = casino.signIn("Lee", "abc");
        
        assertEquals("Should be Lee", lee, customer);
        
        //####################################################################//
        // He wants to play Roulette, where's the action?
        List<Room> rooms = casino.findRooms(customer, GameType.ROULETTE);
        
        assertNotNull("Should find some Rooms", rooms);
        assertTrue("Should be more than one room", rooms.size() > 0);

        //####################################################################//
        // Find an empty room...
        Optional<Room> firstEmptyRoom =
        		rooms.stream()
        			 .filter(r -> r.isEmpty())
        			 .findFirst();
        
        assertTrue("Should find an empty room", firstEmptyRoom.isPresent());
        
        RouletteRoom room = (RouletteRoom)firstEmptyRoom.get();
        
        assertEquals("Should be no spectators", 0, room.getSpectators().size());
        assertEquals("Should be no players", 0, room.getGame().getPlayers().size());

        //####################################################################//
        // Go inside...
        room.enter(lee);
        
        assertEquals("Should be one spectators", 1, room.getSpectators().size());
        assertTrue("Lee should be a spectator", room.getSpectators().contains(lee));
        assertEquals("Should be no players", 0, room.getGame().getPlayers().size());

        //####################################################################//
        // It should be a Roulette gaming room
        Roulette game = (Roulette)room.getGame();

        assertNotNull("Room should contain a game", game);
        assertFalse("Games in empty rooms shouldn't be running", game.isRunning());

        //####################################################################//
        // Take a chair
        room.join(customer);
        
        assertEquals("Should be one player", 1, room.getGame().getPlayers().size());
        assertTrue("Lee should be a player", game.getPlayers().contains(lee));
        assertEquals("Should be no spectators", 0, room.getSpectators().size());

        //####################################################################//
        // Stand up
        room.leave(customer);

        assertEquals("Should be one spectator", 1, room.getSpectators().size());
        assertTrue("Lee should be a spectator", room.getSpectators().contains(lee));
        assertEquals("Should be no players", 0, room.getGame().getPlayers().size());

        //####################################################################//
        // Leave the room
        room.exit(lee);
        
        assertEquals("Should be no spectators", 0, room.getSpectators().size());
        assertEquals("Should be no players", 0, room.getGame().getPlayers().size());

        //####################################################################//
        // Go home
        casino.signOut(customer);
	}
}
