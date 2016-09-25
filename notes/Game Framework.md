# Game Framework

## Interfaces

| Name                             | Description                                                      |
|----------------------------------|------------------------------------------------------------------|
| **Casino**                       | A venue where **Customer**s go to play games!                    |
| **Bank**                         | The owner of all **Account**s                                    |
| **Customer**                     | A patron of the **Casino**                                       |
| **Account**                      | A record of a **Customer**'s changing chip count (as a result of placing **Bet**) |
| **CustomerRole**                 | A function performed by a **Customer** in a particular situation |
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

![yuml](http://yuml.me/9f106769)

### [yuml](http://yuml.me/diagram/scruffy/class/draw) script

```
[Casino|signIn();findRooms();signOut()]++1-visitors\n0..*>[Customer|getNickName();getChipCount()]
[Casino]++1-bank\n1>[Bank]
[Customer]^-[CustomerRole|getCustomer()]
[CustomerRole]<>-1>[Customer]
[CustomerRole]^-[Spectator]
[CustomerRole]^-[Player]
[Account|getChipCount();addChips();deductChips()]<>1-1>[Customer]
[Bank]++1-accounts\n0..*>[Account]

// Game Domain
[Casino]++1-halls\n0..*>[Hall|executeGameLoops()]

[Hall]++1-rooms\n0..*>[Room|isEmpty();enter();joinGame();leaveGame();exit();executeGameLoop()]

[Room]<>1-spectators\n0..*>[Spectator]
[Room]++1-broker\n1>[EventBroker|subscribe();unsubscribe();raiseEvent();postEvent();dispatchEvents()]
[Room]++room\n1-gameManager\n1>[GameManager|isGameRunning();initialise();update();shutdown()]

[EventBroker]-[Event]

[Player]<>1-bets\n0..*>[Bet]

[GameManager]++1-currentBets\n0..*>[Bet]
[GameManager]++1-state\n1>[GameState]
[GameState]<>1-players\n0..*>[Player]
```
