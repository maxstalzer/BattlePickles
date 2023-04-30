Feature: online Multiplayer

  Scenario: Saving a game to a database
    Given a game with all gurkins placed
    And a database
    When I save a game
    Then I should be able to see it in the list of available saves
    And I should be able to load the game from the database


  Scenario: Saving a single game to a database
    Given a singleplayer game
    And a database
    When I save a game
    Then I should be able to see it in the list of available saves
    And I should be able to load the game from the database

#  Scenario: Delete an existing database
#    Given a database
#    And a game
#    When I delete the database "tester123"
#    Then the database "tester123" should not exist

  Scenario: Updating a game in a database
    Given a game
    And an existing database
    When I update a game
    Then The game should be the same as the one i save
