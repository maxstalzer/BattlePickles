Feature: online Multiplayer

  Scenario: Saving a game to a database
    Given a game
    And a database
    When I save a game
    Then I should be able to see it in the list of available saves
    And I should be able to load the game from the database


