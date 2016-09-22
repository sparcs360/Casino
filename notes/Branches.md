# Contributing to [sparcs360/Casino](https://github.com/sparcs360/Casino)

[TOC]

- - -

## Branches

| Name | Purpose | Push to Branch |
|-|-|-|
| **master** | Public releases of Casino | &lt;none> |
| **develop** | Development for the next release  | master |
| **framework** | Development of the common game framework | develop |
| *framework_feature* | Development of a feature of the common game framework | framework |
| **roulette** | Development of the Roulette game | develop |
| *roulette_feature* | Development of a feature of the Roulette game | roulette |

- - -

## Game Framework Development

The section describes the workflow for promoting game framework features into the **develop** branch.

### Creating a new *framework_feature* branch

Do this when you want to add a feature to the game framework.

```bash
# Create a new branch called 'framework_feature' off the head of the 'framework' branch
$ git checkout -b framework_feature framework
```

Commit your feature changes on the **framework_feature** branch.

### Incorporating a finished *framework_feature* into *framework*

Do this when your game framework feature is complete.

```bash
# Switch to the 'framework' branch
$ git checkout framework
Switched to branch 'framework'

# merge commits in the 'framework_feature' branch onto 'framework'
# (with no-ff to preserve history)
$ git merge --no-ff framework_feature
Updating ea1b82a..05e9557
(Summary of changes)

# delete the 'framework_feature' branch - not needed anymore
$ git branch -d framework_feature
Deleted branch framework_feature (was 05e9557).
```

### Incorporating a new version of *framework* into *develop*

Do this to make a new set of game framework features available to Roulette developers.

Makes sure all framework features intended for this update have been merged into the **framework** branch.

```bash
# Switch to the develop branch
$ git checkout develop
Switched to branch 'develop'

# merge commits in the 'framework' branch onto 'develop'
# (with no-ff to preserve history)
$ git merge --no-ff framework
Updating ea1b82a..05e9557
(Summary of changes)

# push to github
$ git push origin develop
```

Roulette developers can now rebase their game code on the head of the **develop** branch to take advantage of the new game framework features.

## Roulette Development

This section describes the workflow for promoting Roulette features into the **develop** branch.

### Creating a new *roulette_feature* branch

Do this when you want to add a new feature to Roulette.

```bash
# Create a new branch called 'roulette_feature' off the head of the 'roulette' branch
$ git checkout -b roulette_feature roulette
```

Commit your feature changes on the **roulette_feature** branch.

### Incorporating a finished *roulette_feature* into *roulette*

Do this when your Roulette feature is complete.

```bash
# Switch to the 'roulette' branch
$ git checkout roulette
Switched to branch 'roulette'

# merge commits in the 'roulette_feature' branch onto 'roulette'
# (with no-ff to preserve history)
$ git merge --no-ff roulette_feature
Updating ea1b82a..05e9557
(Summary of changes)

# delete the 'roulette_feature' branch - not needed anymore
$ git branch -d roulette_feature
Deleted branch roulette_feature (was 05e9557).
```

### Use the latest game framework in your game

Do this when an update of the game framework has been pushed to **develop**.

```bash
# TODO
$ git rebase develop roulette
```

Release any merge conflicts.

### Incorporating a new version of *roulette* into develop

Do this when you're making a new set of Roulette features available for release.

Makes sure all game features intended for this update have been merged into the **roulette** branch.

```bash
# Switch to the develop branch
$ git checkout develop
Switched to branch 'develop'

# merge commits in the 'roulette' branch onto 'develop'
# (with no-ff to preserve history)
$ git merge --no-ff roulette
Updating ea1b82a..05e9557
(Summary of changes)

# push to github
$ git push origin develop
```

- - -

## Roulette Release

This section describes how to make a new public release of Roulette.

TODO
