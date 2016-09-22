package com.sparcs.casino.roulette;

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

/**
 * A vehicle for prototyping the Roulette API!
 *   
 * @author Lee Newfeld
 */
public class VisitToTheCasinoTest extends BaseTest {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(VisitToTheCasinoTest.class);

    @Autowired
    protected RouletteHall hall;

	@Before
    public void beforeTest() {

		super.beforeTest();
		
        // Add mock functionality
        when(casino.findRooms(any(Customer.class), eq(GameType.ROULETTE))).thenReturn(hall.getRooms());
    }

	@Test
	public void shouldHaveAnEnjoyableTime() {

        // Lee enters the Casino
        Customer customer = casino.signIn("Lee", "abc");
        
        // He wants to play Roulette, where's the action?
        List<Room> rooms = casino.findRooms(customer, GameType.ROULETTE);
        
        // Find an empty room...
        Optional<Room> firstEmptyRoom =
        		rooms.stream()
        			 .filter(r -> r.isEmpty())
        			 .findFirst();
        
        RouletteRoom room = (RouletteRoom)firstEmptyRoom.get();

        // Go inside...
        Spectator spectator = room.enter(lee);
        
        // Meet the Croupier...
        @SuppressWarnings("unused")
		RouletteCroupier croupier = (RouletteCroupier)room.getGameManager();

        // Take a chair
        Player player = room.joinGame(spectator);
        
        // Run a cycle of all game loops
        for( int i=0; i<50; i++ ) {
        	
        	assertTrue(hall.executeGameLoops());
        }

        // Stand up
        spectator = room.leaveGame(player);

        // Leave the room
        room.exit(spectator);

        // Go home
        casino.signOut(customer);
	}
}
