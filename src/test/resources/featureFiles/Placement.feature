@tag
Feature: Placement

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
