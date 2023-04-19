Feature: Singleplayer

  Scenario: Creating a singleplayer game
    When I create a singleplayer game
    Then I should create a new Player and AI

  Scenario: Creating an easy AI
    Given a singleplayer game
    When I set the difficulty of the AI to easy
    Then the difficulty of the AI should be easy

  Scenario: AI should be able to generate its own board
    Given a singleplayer game
    Then the board should be randomly generated

  Scenario: An easy AI shoots another player
    Given a singleplayer game
    And an Easy AI
    When the AI shoots
    Then the AI should shoot at a random location

  Scenario: An medium AI shoots another player
    Given a singleplayer game
    And a Medium AI
    When the AI shoots
    Then the AI should shoot at a random location

  Scenario: A medium AI shoots another player twice
    Given a singleplayer game
    And a Medium AI
    And a board with all gurkins placed
    When the AI shoots again
    Then there should be no gurkins left
    And all tiles should be hit

  Scenario: A medium AI kills all gurkins
    Given a singleplayer game
    And a Medium AI
    And a board with all gurkins placed
    When the AI shoots again
    Then there should be no gurkins left
    And all tiles should be hit
    And the AI should have won the game