package com.sparcs.casino.roulette.internal;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sparcs.casino.game.Room;
import com.sparcs.casino.game.RoomImpl;
import com.sparcs.casino.roulette.Roulette;
import com.sparcs.casino.roulette.RouletteRoom;

/**
 * A {@link Room} hosting a game of {@link Roulette}.
 * 
 * @author Lee Newfeld
 */
@Component
@Scope("prototype")
public class RouletteRoomImpl extends RoomImpl<Roulette> implements RouletteRoom {

}
