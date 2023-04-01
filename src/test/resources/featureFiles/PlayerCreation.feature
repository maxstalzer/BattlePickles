@tag
Feature: Player Creation
  @tag1
  Scenario: placing a gurkin on a tile
    Given a tile
    When set a gurkin on the tile
    Then the gurkin is on the tile
  @tag1
  Scenario: shooting a tile with a gurkin on
    Given a player
    And a board
    When I shoot a tile with a gurkin on it
    Then I get the string hit