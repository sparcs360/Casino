package com.sparcs.casino.game;

/**
 * Represents the referee of a Game.
 *  
 * @author Lee Newfeld
 */
public interface GameManager {

    /**
     * @return true if a game is currently running.
     */
    boolean isGameRunning();
    
    /**
     * @return The current state if the game
     */
    GameState getGameState();

    /**
     * Reset the game back to its initial state.
     *  
     * @param room The {@link Room} that hosts the Game
     */
    void initialise(Room room);

    /**
     * Execute a time slice of the Game
     * 
     * @param room The {@link Room} that hosts the Game
     * @return true if the Game is still running after the update 
     */
    boolean update(Room room);
    
    /**
     * The {@link Room#isEmpty() Room is empty}, shutdown the game
     * 
     * @param room The {@link Room} that hosts the Game
     */
    void shutdown(Room room);
}
