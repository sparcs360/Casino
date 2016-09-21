# Game Framework

## Interfaces

| Name | Description |
|-|-|
| **Casino** | A venue where **Customer**s go to play games! |
| **Customer** | A patron of the **Casino** |
| **CustomerRole** | A function performed by a **Customer** in a particular situation |
| **Spectator** (a *CustomerRole*) | A **Customer** watching a Game |
| **Player** (a *CustomerRole*) | A **Customer** playing a Game |
| **Hall** | Represents a set of **Room**s hosting a particular type of Game |
| **Room** | Represents a Room within a **Hall** of the **Casino**.  It hosts: a **GameManager** and a set of **Customer**s (playing a **Spectator** or **Player** role) |
| **GameManager** | The referee of a Game |
| **GameState** | The current state of a Game |


## Class Model

![yuml](http://yuml.me/a12e2d52)

### [yuml](http://yuml.me/diagram/scruffy/class/draw) script

```
[Casino|signIn();findRooms();signOut()]++1-visitors\n0..*>[Customer|getNickName()]
[Customer]^-[CustomerRole]
[CustomerRole]<>-1>[Customer]
[CustomerRole]^-[Spectator]
[CustomerRole]^-[Player]
 
[Casino]++1-halls\n0..*>[Hall|executeGameLoops()]
[Hall]++1-rooms\n0..*>[Room|isEmpty();enter();joinGame();leaveGame();exit();executeGameLoop()]
[Room]<>1-spectators\n0..*>[Spectator]
[Room]++1-gameManager\n1>[GameManager|isGameRunning();initialise();update();shutdown()]
[GameManager]++1-state\n1>[GameState]
[GameState]<>1-players\n0..*>[Player]
```
