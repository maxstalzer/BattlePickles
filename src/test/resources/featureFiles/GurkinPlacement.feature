@tag
Feature: Gurkin placement
  @tag1
  Scenario: placing a gurkin on a tile
    Given a tile
    When set a gurkin on the tile
    Then the gurkin is on the tile
  @tag1
  Scenario: placing a gurkin Horizontally
    Given a starting Coordinate
    And a board
    And a gurkin
    And the direction is horizontal
    When I place the gurkin
    Then the gurkin is placed horizontally

  @tag1
  Scenario: placing a gurkin Vertically
    Given a starting Coordinate
    And a board
    And a gurkin
    And the direction is Vertical
    When I place the gurkin
    Then the gurkin is placed Vertically
