# Game Framework

A framework for creating games within the [Casino](Other Domains - Class Model.md).

## Interfaces

| Name                             | Description                                                      |
|----------------------------------|------------------------------------------------------------------|
| **Spectator** (a *CustomerRole*) | A **Customer** watching a Game                                   |
| **Player** (a *CustomerRole*)    | A **Customer** playing a Game                                    |
| **Hall**                         | Represents a set of **Room**s hosting a particular type of Game  |
| **Room**                         | Represents a Room within a **Hall** of the **Casino**.  It hosts: a **GameManager** and a set of **Customer**s (playing a **Spectator** or **Player** role) |
| **EventBroker**                  | An agent for the routing of **Event**s between a single Publisher and multiple Subscribers |
| **Event**                        | Something of importance that happened in one component of the Application (e.g., a state change) that is of potential interest to other components of the Application |
| **GameManager**                  | The referee of a Game                                            |
| **GameState**                    | The current state of a Game                                      |
| **Bet**                          | A wager, placed by a **Player**, predicting the outcome of a Game |

## Class Model

![yuml](http://yuml.me/19a74cb0)

### [yuml](http://yuml.me/diagram/scruffy/class/draw) script

```
// Other Domain Entities (shaded in orange)
[Casino{bg:sandybrown}]
[Customer{bg:sandybrown}]
[Bank{bg:sandybrown}]
[Account{bg:sandybrown}]
[CustomerRole{bg:sandybrown}]

// Other Domain Associations
[Casino]++1-visitors\n0..*>[Customer]
[Casino]++1-bank\n1>[Bank]
[Bank]++1-accounts\n0..*>[Account]
[Account]<>1-1>[Customer]
[CustomerRole]<>-1>[Customer]

//==========

// Entities
[Hall|executeGameLoops()]
[Room|isEmpty();enter();joinGame();leaveGame();exit();executeGameLoop()]
[Spectator]
[EventBroker|subscribe();unsubscribe();raiseEvent();dispatchEvents()]
[Event]
[Room.EnterEvent|room:Room;customer:Customer]
[Room.SpectatorEvent|room:Room;spectator:Spectator]
[Room.JoinGameEvent]
[Room.LeaveGameEvent|room:Room;player:Player]
[Room.ExitEvent]
[GameManager|isGameRunning();initialise();update();shutdown()]
[GameState]
[Player]
[Bet]

// Inheritance
[Customer]^-[CustomerRole{bg:sandybrown}]
[CustomerRole]^-[Spectator]
[CustomerRole]^-[Player]
[Event]^-[Room.EnterEvent]
[Event]^-[Room.SpectatorEvent]
[Event]^-[Room.LeaveGameEvent]
[Room.SpectatorEvent]^-[Room.JoinGameEvent]
[Room.SpectatorEvent]^-[Room.ExitEvent]

// Associations
[Casino{bg:sandybrown}]++1-halls\n0..*>[Hall]
[Hall]++1-rooms\n0..*>[Room]
[Room]<>1-spectators\n0..*>[Spectator]
[Room]++1-broker\n1>[EventBroker]
[Room]++room\n1-gameManager\n1>[GameManager]
[EventBroker]-[Event]
[GameManager]++1-currentBets\n0..*>[Bet]
[Player]<>1-bets\n0..*>[Bet]
[GameManager]++1-state\n1>[GameState]
[GameState]<>1-players\n0..*>[Player]
```
