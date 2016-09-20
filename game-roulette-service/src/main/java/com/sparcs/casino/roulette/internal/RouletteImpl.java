package com.sparcs.casino.roulette.internal;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sparcs.casino.game.GameImpl;
import com.sparcs.casino.roulette.Roulette;

/**
 * An implementation of the game of {@link Roulette}
 * 
 * @author Lee Newfeld
 */
@Component
@Scope("prototype")
public class RouletteImpl extends GameImpl implements Roulette {

}
