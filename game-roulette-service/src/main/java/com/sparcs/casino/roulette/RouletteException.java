package com.sparcs.casino.roulette;

import java.io.Serializable;

import com.sparcs.casino.game.GameException;

/**
 * An Exception in he Roulette game.
 * 
 * @author Lee Newfeld
 */
public class RouletteException extends GameException implements Serializable {

	private static final long serialVersionUID = -6943111709369334512L;

	public RouletteException() {
        super();
    }

    public RouletteException(String message) {
        super(message);
    }
    
    public RouletteException(String message, Throwable cause) {
        super(message, cause);
    }

    public RouletteException(Throwable cause) {
        super(cause);
    }

    protected RouletteException(String message, Throwable cause,
                               boolean enableSuppression,
                               boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
