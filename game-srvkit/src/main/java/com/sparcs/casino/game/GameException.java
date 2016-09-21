package com.sparcs.casino.game;

import java.io.Serializable;

/**
 * 
 * @author Lee Newfeld
 */
public class GameException extends RuntimeException implements Serializable {

	private static final long serialVersionUID = 5478146591673445431L;
	
    public GameException() {
        super();
    }

    public GameException(String message) {
        super(message);
    }
    
    public GameException(String message, Throwable cause) {
        super(message, cause);
    }

    public GameException(Throwable cause) {
        super(cause);
    }

    protected GameException(String message, Throwable cause,
                               boolean enableSuppression,
                               boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
