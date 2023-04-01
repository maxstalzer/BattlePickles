@tag
Feature: Player Creation
  @tag1
  Scenario: placing a gurkin on a tile
    Given a tile
    When set a gurkin on the tile
    Then the gurkin is on the tile