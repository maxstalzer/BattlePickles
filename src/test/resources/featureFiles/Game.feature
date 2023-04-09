@tag
Feature: Game
  @tag1
  Scenario: shooting a tile with a gurkin on
    Given a player
    And a board
    When I shoot a tile with a gurkin on it
    Then I get the string hit
  @tag1
  Scenario: Shooting a tile without a gurkin on it
    Given a player
    And a board
    When I shoot a tile without a gurkin on it
    Then I get the string miss
  @tag1
  Scenario: Shooting a tile you've shot before
    Given a player
    And a board
    When I shoot a tile I've shot before
    Then I get the string noob


  @tag1
  Scenario: Missing a shot
    Given a player
    And a board
    When I miss a shot
    Then My shotResults board has the character o
  @tag1
  Scenario: Hitting a gurkin
    Given a player
    And a board
    When I hit a gurkin
    Then My shotResults board has the character x
  @tag1
  Scenario: placing a gurkin on a tile
    Given a tile
    When set a gurkin on the tile
    Then the gurkin is on the tile
  @tag1
  Scenario: placing a gurkin on a tile that already has a gurkin
    Given a tile with a gurkin
    When I try to place another gurkin
    Then the coordinates are not valid

  @tag1
  Scenario: placing a yardlong in a valid position
    Given a board
    And a valid coordinate
    When I try to place a yardlong
    Then the yardlong is placed

  @tag1
  Scenario: placing a Zuchinni in a valid position
    Given a board
    And a valid coordinate
    When I try to place a Zuchinni
    Then the Zuchinni is placed

  @tag1
  Scenario: placing a Pickle in a valid position
    Given a board
    And a valid coordinate
    When I try to place a Pickle
    Then the Pickle is placed

  @tag1
  Scenario: placing a Conichon in a valid position
    Given a board
    And a valid coordinate
    When I try to place a Conichon
    Then the Conichon is placed

  @tag1
  Scenario: placing terrain in a valid position
    Given a board
    And a valid coordinate
    When I try to place Terrain
    Then the Terrain is placed

  @tag1
  Scenario: Killing a gurkin
    Given a player
    And a board
    When i hit all points of a gurkin
    Then all of the gurkins coordinates on the shot result board should be k