Feature: Multiplayer
  #  ---------------------------- Game setup -------------------------------------
  Scenario: Creating a new Multiplayer game
    When I create a multiplayer game
    Then I should create two new players

  Scenario: Creating a Player
    When I create a new Player
    Then the player should have a board and a shot results board

  Scenario: Naming a player
    Given a player
    When I update the players name
    Then the player name should be the name

#  ------------------------ Gurkin placement --------------------
#  Terrain autoplacement

  Scenario: placing a gurkin on a tile
    Given a tile
    When set a gurkin on the tile
    Then the gurkin is on the tile

  Scenario: placing a gurkin on a tile that already has a gurkin
    Given a tile with a gurkin
    When I try to place another gurkin
    Then the coordinates are not valid

  Scenario: placing a yardlong in a valid position
    Given a board
    And a valid coordinate
    When I try to place a yardlong
    Then the yardlong is placed

  Scenario: placing a Zuchinni in a valid position
    Given a board
    And a valid coordinate
    When I try to place a Zuchinni
    Then the Zuchinni is placed

  Scenario: placing a Pickle in a valid position
    Given a board
    And a valid coordinate
    When I try to place a Pickle
    Then the Pickle is placed

  Scenario: placing a Conichon in a valid position
    Given a board
    And a valid coordinate
    When I try to place a Conichon
    Then the Conichon is placed

  Scenario: playing a yardlong in an invalid position vertically
    Given a board
    And an invalid coordinate
    When I try to place a yardlong incorrectly
    Then I should be told it is invalid

#  Scenario: player1 finishes placing gurkins
#    Given a player
#    And its player1s turn
#    And a board
#    When the player has placed all their gurkins
#    Then the turn changes to player2
  Scenario: shooting a tile with a gurkin on
    Given a player
    And a board
    And its player1s turn
    When I shoot a tile with a gurkin on it
    Then I get the string hit
    And The turn changes
