Feature: Terrain

  Scenario: Initialising Terrain
    Given a game
    When I initialise the terrain
    Then the terrain should be initialised

  Scenario: Getting the character from terrain
    Given a Terrain
    When I get the char value
    Then I should get the char value t