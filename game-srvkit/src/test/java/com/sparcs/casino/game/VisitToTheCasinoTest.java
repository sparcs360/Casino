package com.sparcs.casino.game;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.sparcs.casino.BaseTest;
import com.sparcs.casino.Customer;
import com.sparcs.casino.game.GameType;
import com.sparcs.casino.game.Player;
import com.sparcs.casino.game.Room;
import com.sparcs.casino.game.Spectator;
import com.sparcs.casino.testgame.SnoozeGameManager;
import com.sparcs.casino.testgame.SnoozeHall;
import com.sparcs.casino.testgame.SnoozeRoom;

/**
 * A vehicle for prototyping the Game API
 *   
 * @author Lee Newfeld
 */
public class VisitToTheCasinoTest extends BaseTest {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(VisitToTheCasinoTest.class);

    @Autowired
    protected SnoozeHall hall;

	@Before
    public void beforeTest() {

		super.beforeTest();
		
        // Add mock functionality
        when(casino.findRooms(any(Customer.class), eq(GameType.ROULETTE))).thenReturn(hall.getRooms());
    }

	@Test
	public void shouldHaveAnEnjoyableTime() {

        // Lee enters the Casino
        Customer customer = casino.signIn("SPARCS", "abc");
        
        assertEquals("Should be SPARCS", sparcs, customer);
        
        // He wants to play Roulette, where's the action?
        List<Room> rooms = casino.findRooms(customer, GameType.ROULETTE);
        
        assertNotNull("Should find some Rooms", rooms);
        assertTrue("Should be more than one room", rooms.size() > 0);

        // Find an empty room...
        Optional<Room> firstEmptyRoom =
        		rooms.stream()
        			 .filter(r -> r.isEmpty())
        			 .findFirst();
        
        assertTrue("Should find an empty room", firstEmptyRoom.isPresent());
        
        SnoozeRoom room = (SnoozeRoom)firstEmptyRoom.get();

        // Go inside...
        Spectator spectator = room.enter(sparcs);
        
        // Meet the Croupier...
        @SuppressWarnings("unused")
		SnoozeGameManager croupier = (SnoozeGameManager)room.getGameManager();

        // Take a chair
        Player player = room.joinGame(spectator);
        
        // Run a cycle of all game loops
        while( hall.executeGameLoops() ) {
        	
        }

        // Stand up
        spectator = room.leaveGame(player);

        // Leave the room
        room.exit(spectator);

        // Go home
        casino.signOut(customer);
	}
}
