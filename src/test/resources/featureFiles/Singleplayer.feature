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