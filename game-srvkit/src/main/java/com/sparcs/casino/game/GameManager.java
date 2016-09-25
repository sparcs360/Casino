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
     */
    void initialise();

    /**
     * Execute a time slice of the Game
     * 
     * @return true if the Game is still running after the update 
     */
    boolean update();
    
    /**
     * The {@link Room#isEmpty() Room is empty}, shutdown the game
     */
    void shutdown();
}
