@tag
Feature: Attack
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
  Scenario: Killing a gurkin
    Given a player
    And a board
    When i hit all points of a gurkin
    Then all of the gurkins coordinates on the shot result board should be k