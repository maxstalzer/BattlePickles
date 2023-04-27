Feature: online Multiplayer

  Scenario: Saving a game to a database
    Given a game
    And a database
    When I save a game
    Then I should be able to see it in the list of available saves
    And I should be able to load the game from the database

    Scenario: I have made my move and want to upload it to the database
      Given a game
      And a database
      When my turn is over
      Then i should be able to upload my game to the database
