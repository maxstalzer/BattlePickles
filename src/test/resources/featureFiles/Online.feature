Feature: online Multiplayer

  Scenario: Saving a game to a database
    Given a game
    And a database
    When I save a game
    Then I should be able to load the game from the database

#  Scenario: Your momma
#    Given Your momma
#    And me
#    When I bang her
#    Then you should be traumatized