package com.sparcs.casino.roulette.internal;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sparcs.casino.roulette.RouletteWheel;

/**
 * Implementation of a {@link RouletteWheel}.
 * 
 * @author Lee Newfeld
 */
@Component("Wheel")
@Scope("prototype")
public class WheelImpl implements RouletteWheel {

}
