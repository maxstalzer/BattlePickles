Feature: Turn

  Scenario: setting the turn of the game
    When i set the turn to 1
    Then the turn should be 1
