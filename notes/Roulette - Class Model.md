# Roulette

A implementation of Roulette built on top of the [Game Framework](Game Framework - Class Model.md).

## Interfaces

| Name                             | Description                                                      |
|----------------------------------|------------------------------------------------------------------|
| **RouletteHall**                 | A *Hall* of *Room*s, each containing a game of Roulette          |
| **RouletteRoom**                 | A *Room* hosting a game of Roulette                              |
| **RouletteCroupier**             | The Croupier overseeing a game of Roulette                       |
| **RouletteGameState**            | The state of a game of Roulette                                  |
| **RouletteWheel**                | The Wheel on a Roulette table                                    |
| **RouletteSpectator**            | A *Customer* within a **RouletteRoom** watching a game of Roulette |
| **RoulettePlayer**               | A *Customer* within a **RouletteRoom** playing a game of Roulette | 
| **RouletteBet**                  | A *Bet* placed on a game of Roulette |
| **RouletteCroupier.ConsideredBetEvent** (abstract) | A *Bet*, placed by a **RoulettePlayer**, that's being considered by the **RouletteCroupier** |
| **RouletteCroupier.BetPlacedEvent** | A **RouletteCroupier** has accepted a *Bet* placed by a **RoulettePlayer** |
| **RouletteCroupier.BetRejectedEvent** | A **RouletteCroupier** has rejected a *Bet* placed by a **RoulettePlayer** |
| **RouletteCroupier.BetWinEvent** | A **RoulettePlayer** has won a *Bet* |

## Class Model

![![](http://)](http://yuml.me/4fddb929)

### [yuml](http://yuml.me/diagram/scruffy/class/draw) script

```
// Framework Entities (shaded in orange)
[Hall{bg:sandybrown}]
[Room{bg:sandybrown}]
[GameManager{bg:sandybrown}]
[GameState{bg:sandybrown}]
[Spectator{bg:sandybrown}]
[Player{bg:sandybrown}]
[Bet{bg:sandybrown}]
[EventBroker{bg:sandybrown}]
[Event{bg:sandybrown}]

// Framework Associations
[Hall]++1-rooms\n0..*>[Room]
[Room]++1-broker\n1>[EventBroker]
[Room]<>1-spectators\n0..*>[Spectator]
[Room]++room\n1-gameManager\n1>[GameManager]
[EventBroker]-[Event]
[GameState]<>1-players\n0..*>[Player]
[GameManager]++1-state\n1>[GameState]
[GameManager]++1-currentBets\n0..*>[Bet]
[Player]<>1-bets\n0..*>[Bet]

//==========

// Roulette Entities
[RouletteHall]
[RouletteRoom]
[RouletteCroupier|isBettingAllowed();areBetsResolved();considerBet();getBets()]
[RouletteCroupier.Stage|STARTING_NEW_GAME;BETS_RESOLVED]
[RouletteGameState]
[RouletteWheel|reset();update();getResult()]
[RouletteWheel.Stage|AT_REST;SPINNING;BALL_SPINNING;NO_MORE_BETS;BALL_AT_REST;BETS_RESOLVED]
[RouletteSpectator]
[RoulettePlayer|isBettingAllowed();areBetsResolved();requestBet();getBets()]
[RouletteBet|calculateWinnings()]
[RouletteBet.SingleBet|getNumber();isValid()]
[RouletteCroupier.ConsideredBetEvent]
[RouletteCroupier.BetPlacedEvent]
[RouletteCroupier.BetRejectedEvent|getReason()]
[RouletteCroupier.BetWinEvent|getWinnings()]

// Inheritence from Framework
[Hall]^-[RouletteHall]
[Room]^-[RouletteRoom]
[GameManager]^-[RouletteCroupier]
[GameState]^-[RouletteGameState]
[Spectator]^-[RouletteSpectator]
[Player]^-[RoulettePlayer]
[Bet]^-[RouletteBet]

// Associations
[RouletteBet]^-[RouletteBet.SingleBet]
[RouletteCroupier]++1-stage\n1>[RouletteCroupier.Stage]
[RouletteCroupier]++1-wheel\n1>[RouletteWheel]
[RouletteWheel]++1-stage\n1>[RouletteWheel.Stage]

[Event]^-[RouletteCroupier.ConsideredBetEvent]
[RouletteCroupier.ConsideredBetEvent]<>1-player\n1>[RoulettePlayer]
[RouletteCroupier.ConsideredBetEvent]<>1-bet\n1>[RouletteBet]

[RouletteCroupier.ConsideredBetEvent]^-[RouletteCroupier.BetPlacedEvent]
[RouletteCroupier.ConsideredBetEvent]^-[RouletteCroupier.BetRejectedEvent]
[RouletteCroupier.ConsideredBetEvent]^-[RouletteCroupier.BetWinEvent]
```
