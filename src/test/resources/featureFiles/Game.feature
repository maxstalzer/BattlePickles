#@tag
#Feature: Game
##  ---------------------------- Game mechanics -------------------------------------
#
#  Scenario: Creating a new Singleplayer game
#    When I create a singleplayer game
#    Then I should create a new Player and AI
#
#  Scenario: Creating a new Multiplayer game
#    When I create a multiplayer game
#    Then I should create two new players
#
#  Scenario: Creating a Player
#    When I create a new Player
#    Then the player should have a board and a shot results board
#
##  ------------------------- Player shooting feature --------------------------------
#
#  @tag1
#  Scenario: shooting a tile with a gurkin on
#    Given a player
#    And a board
#    When I shoot a tile with a gurkin on it
#    Then I get the string hit
#  @tag1
#  Scenario: Shooting a tile without a gurkin on it
#    Given a player
#    And a board
#    When I shoot a tile without a gurkin on it
#    Then I get the string miss
#  @tag1
#  Scenario: Shooting a tile you've shot before
#    Given a player
#    And a board
#    When I shoot a tile I've shot before
#    Then I get the string noob
#
#
#  @tag1
#  Scenario: Missing a shot
#    Given a player
#    And a board
#    When I miss a shot
#    Then My shotResults board has the character o
#  @tag1
#  Scenario: Hitting a gurkin
#    Given a player
#    And a board
#    When I hit a gurkin
#    Then My shotResults board has the character x
#
#  @tag1
#  Scenario: Killing a gurkin
#    Given a player
#    And a board
#    When i hit all points of a gurkin
#    Then all of the gurkins coordinates on the shot result board should be k
#
#
#
## --------------------------------- Placement feature ----------------------------------
#  @tag1
#  Scenario: placing a gurkin on a tile
#    Given a tile
#    When set a gurkin on the tile
#    Then the gurkin is on the tile
#  @tag1
#  Scenario: placing a gurkin on a tile that already has a gurkin
#    Given a tile with a gurkin
#    When I try to place another gurkin
#    Then the coordinates are not valid
#
#  @tag1
#  Scenario: placing a yardlong in a valid position
#    Given a board
#    And a valid coordinate
#    When I try to place a yardlong
#    Then the yardlong is placed
#
#  @tag1
#  Scenario: placing a Zuchinni in a valid position
#    Given a board
#    And a valid coordinate
#    When I try to place a Zuchinni
#    Then the Zuchinni is placed
#
#  @tag1
#  Scenario: placing a Pickle in a valid position
#    Given a board
#    And a valid coordinate
#    When I try to place a Pickle
#    Then the Pickle is placed
#
#  @tag1
#  Scenario: placing a Conichon in a valid position
#    Given a board
#    And a valid coordinate
#    When I try to place a Conichon
#    Then the Conichon is placed
#
#  @tag1
#  Scenario: placing terrain in a valid position
#    Given a board
#    And a valid coordinate
#    When I try to place Terrain
#    Then the Terrain is placed
#
#  Scenario: playing a yardlong in an invalid position vertically
#    Given a board
#    And an invalid coordinate
#    When I try to place a yardlong incorrectly
#    Then I should be told it is invalid
## ---------------------- Gurkin to character feature ---------------------
#  Scenario: getting the character associated with a Zuchinni
#    Given a Zuchinni
#    When use the zuzhinni to string method
#    Then the character z should be returned
#
#  Scenario: getting the character associated with a Yardlong
#    Given a yardlong
#    When use the yardlong to string method
#    Then the character y should be returned
#
#  Scenario: getting the character associated with a Terrain
#    Given a Terrain
#    When use the terrain to string method
#    Then the character t should be returned
#
#  Scenario: getting the character associated with a Pickle
#    Given a Pickle
#    When use the Pickle to string method
#    Then the character p should be returned
#
#  Scenario: getting the character associated with a Gherkin
#    Given a Gherkin
#    When use the Gherkin to string method
#    Then the character g should be returned
#
#  Scenario: getting the character associated with a Conichon
#    Given a Conichon
#    When use the conichon to string method
#    Then the character c should be returned
#
#
