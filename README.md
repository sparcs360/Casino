# Casino

![](https://img.shields.io/badge/release-0.0.1--RELEASE-brightgreen.svg)

Casino games built with Java 8 on top of the [Spring Framework](https://spring.io/).

- Games are built using a bespoke [Game Framework](https://github.com/sparcs360/Casino/blob/master/notes/Game%20Framework%20-%20Class%20Model.md).
  - It allows a set of games (of a particular type) to be hosted within a micro-service.
  - It expects to be running alongside micro-services representing [other domains](https://github.com/sparcs360/Casino/blob/master/notes/Other%20Domains%20-%20Class%20Model.md) (which are just stubs at the moment).
  - The Game Framework is tested using a contrived game that I call **Snooze** (because the player doesn't do anything... "*you snooze, you win!*").

- There's also a work in progress implementation of [Roulette](https://github.com/sparcs360/Casino/blob/master/notes/Roulette%20-%20Class%20Model.md), which:
  - Allows a Customer to enter a room hosting a game (making them a Spectator).
    - At this point, they can watch the Croupier play out game after game.
  - Allows a Spectator to join a game (making them a Player).
  - Allows a Player to place a Bet (only Single Bets are supported at the moment).
    - Chips are deducted according to the Stake (via the Bank)
    - The Croupier only takes Bets from Players with enough Chips
    - The Croupier pays out Bets that win (again, via the Bank).
  - Allows a Player to leave the game (making them a Spectator again).
  - Allows a Spectator to exit the room (where they lose their Spectator status).

- - -

# Git Workflow

Refer to [Branches.md](https://github.com/sparcs360/Casino/blob/master/notes/Branches.md) in the ```notes``` directory for information on the various branches, and how commits flow between them.

- - -

# Build with Maven

I'm using [v3.3.9](https://maven.apache.org/download.cgi).

## Modules

| Name                      | Description                                     | Dependencies                |
|:--------------------------|:------------------------------------------------|:----------------------------|
| **!parent**               | Parent POM                                      | Spring Boot (1.4.0.RELEASE) |
| **game-srvkit**           | Toolkit for creating a game service             | !parent                     |
| **game-roulette-service** | An implementation of Roulette using game-srvkit | !parent; game-srvkit        |

## Build Instructions

Make the ```!parent``` directory the current directory (this is where my bash prompt spends most of its time!) and then **compile**, **test** and **package** the modules using the ubiquitous:

```bash
$ mvn clean install
```

- - -

# Starting the micro-service

From the ```!parent``` directory, execute the following command to start an instance of the micro-service:

```bash
$ java -jar ../game-roulette-service/target/game-roulette-service-0.0.1-SNAPSHOT.jar
```

**Note**: The current release doesn't do very much because the "game loops" aren't being "pumped" automatically yet (see [Issue #10](https://github.com/sparcs360/Casino/issues/10)).

For now, look at ```com.sparcs.casino.roulette.VisitToTheCasinoTest``` in the ```src/test/java``` directory of **game-roulette-service** to get a feel for how the API works, or browse the JavaDocs (see **API Documentation** below).

## Configuration

To change the server port, or the number of Roulette Rooms created when the Application starts, edit ```src/main/resources/application.yaml``` of **game-roulette-service**:

```yaml
server:
  port: 10000
---

roulette:
  gameCount: 10
```

The **roulette.*** properties are managed by ```com.sparcs.casino.RouletteConfigurationProperties```.

## Logging

Extensive log messages are written.  Change the logging level in ```logback.xml``` in ```src/main/resources``` or ```test-logback.xml``` in ```src/test/resources```.  Current value is ```TRACE```, which is quite spammy.

```xml
<logger name="com.sparcs.casino" level="TRACE" .../>
```

- - -

# API Documentation

In adition to the UML class diagrams ([Other Domains](https://github.com/sparcs360/Casino/blob/master/notes/Other%20Domains%20-%20Class%20Model.md), [Game Framework](https://github.com/sparcs360/Casino/blob/master/notes/Game%20Framework%20-%20Class%20Model.md), and [Roulette](https://github.com/sparcs360/Casino/blob/master/notes/Roulette%20-%20Class%20Model.md)), you can generate JavaDocs by following this procedure:

- Make ```!parent``` the current directory
- Execute:

   ```bash
mvn clean javadoc:aggregate
   ```

- This will write a JavaDoc site to the directory ```../apidocs-<release>```
  - Where ```<release>``` is ```${parent.version}``` from ```!parent/pom.xml```
  - i.e., for the latest release on the master branch the directory is ```../apidocs-0.0.1-RELEASE```.
  - Note that the maven goal doesn't delete the directory first
