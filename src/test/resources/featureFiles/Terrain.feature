Feature: Terrain

  Scenario: Initialising Terrain
    Given a game
    When I initialise the terrain
    Then the terrain should be initialised

  Scenario: Getting the character from terrain
    Given a Terrain
    When I get the char value
    Then I should get the char value t

  Scenario: Hitting terrain
    Given a Terrain
    And a board with terrain
    When I hit a terrain
    Then the terrain should be hit

