Feature: Game

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

    #  ---------------------------- Game setup -------------------------------------
  Scenario: Creating a new Multiplayer game
    When I create a multiplayer game
    Then I should create two new players

  Scenario: Creating a Player
    When I create a new Player
    Then the player should have a board and a shot results board

  Scenario: Naming a player
    Given a player
    When I update the players name
    Then the player name should be the name

#  ------------------------ Gurkin placement --------------------
#  Terrain autoplacement

  Scenario: placing a gurkin on a tile
    Given a tile
    When set a gurkin on the tile
    Then the gurkin is on the tile

  Scenario: placing a gurkin on a tile that already has a gurkin
    Given a tile with a gurkin
    When I try to place another gurkin
    Then the coordinates are not valid

  Scenario: placing a yardlong in a valid position
    Given a board
    And a valid coordinate
    When I try to place a yardlong
    Then the yardlong is placed

  Scenario: placing a Zuchinni in a valid position
    Given a board
    And a valid coordinate
    When I try to place a Zuchinni
    Then the Zuchinni is placed

  Scenario: placing a Pickle in a valid position
    Given a board
    And a valid coordinate
    When I try to place a Pickle
    Then the Pickle is placed

  Scenario: placing a Conichon in a valid position
    Given a board
    And a valid coordinate
    When I try to place a Conichon
    Then the Conichon is placed

  Scenario: playing a yardlong in an invalid position vertically
    Given a board
    And an invalid coordinate
    When I try to place a yardlong incorrectly
    Then I should be told it is invalid

#  Scenario: Player 1 has placed all 5 gurkins
#    Given a game
#    And a player
#    And all 5 gurkins have been placed
#    Then The turn changes

# ---------------------------- Attacking feature ------------------------

  Scenario: Player 1 attacks a tile with a gurkin on it
    Given a game
    And its player1s turn
    When I shoot a tile that has a gurkin and has not been shot before
    Then the shot result x is on that coordinate
    And the gurkins lives has decreased

  Scenario: Player 1 attacks a tile without a gurkin on it
    Given a game
    And its player1s turn
    When I shoot a tile that doesnt have a gurkin on it and has not been shot before
    Then the shot result is o on that coordinate

  Scenario: Player 1 attacks a tile with a gurkin on they have shot before
    Given a game
    And its player1s turn
    When I shoot a tile that has a gurkin and has not been shot before
    And I shoot a tile that i hit again
    Then the shot result x is on that coordinate
    And the turn is not changed
    And the gurkins life has only decreased by 1

  Scenario: Player 1 attacks a tile without a gurkin that they have shot before
    Given a game
    And its player1s turn
    When I shoot a tile that doesnt have a gurkin on it and has not been shot before
    And I shoot a tile that i hit again
    Then the shot result is o on that coordinate
    And the turn is not changed

  Scenario: Player 1 attacks a tile and kills a gurkin
    Given a game
    And its player1s turn
    When I shoot all tiles of that gurkin
    Then the shot result is k on those coordinates
    And the gurkin has no lives
# ------------------------------- WINNING THE GAME ----------------------------------
  Scenario: Player 1 has killed all the gurkins of player 2
    Given a game
    And its player1s turn
    And player1 has to kill the last gurkin
    When I shoot all tiles of that gurkin
    Then then player1 wins

  Scenario: Player 1 has not yet killed all the gurkins of player 2
    Given a game
    And its player2s turn
    And player1 has to kill the last gurkin
    Then player1 does not win

# ------------------------------- GURKIN EXTRA TESTS --------------------------------
  Scenario: Getting the char value of a yardlong
    Given a yardlong
    When I get the char value
    Then the char value should be y

  Scenario: Getting the char value of a Zuchinni
    Given a Zuchinni
    When I get the char value
    Then the char value should be z

  Scenario: Getting the char value of a Pickle
    Given a Pickle
    When I get the char value
    Then the char value should be p

  Scenario: Getting the char value of a Conichon
    Given a Conichon
    When I get the char value
    Then the char value should be c

  Scenario: Getting the char value of a Gherkin
    Given a Gherkin
    When I get the char value
    Then the char value should be g


# ------------------------------- TILE EXTRA TESTS --------------------------------
  Scenario: Checking if the gurkin on a tile is a specific gurkin
    Given a tile with a gurkin
    And a yardlong
    When I check if the gurkin is a yardlong
    Then the gurkin should be a yardlong

  Scenario: Setting a board on a tile
    Given a tile
    And a board
    When I set the board on the tile
    Then the tile should have a board

# ---------------------------------- GAME EXTRA TESTS -------------------------------
  Scenario: Getting the player1 from current turn
    Given a game
    And the turn is one
    When I get the current player
    Then the current player should be player1

  Scenario: Getting the player2 from current turn
    Given a multiplayer game
    And the turn is two
    When I get the current player
    Then the current player should be player2

  Scenario: Getting the AI player
    Given a singleplayer game
    Then the AI player should be player2

  Scenario: Getting the AI player in a multiplayer game
    Given a multiplayer game
    Then the AI player should be null

  Scenario: Getting the the opponent when the turn is 1
    Given a game
    And the turn is one
    Then the opponent should be player2

  Scenario: Getting the the opponent when the turn is 2
    Given a game
    And the turn is two
    Then the opponent should be player1

  Scenario: Attacking a coordinate when it's turn1
    Given a game
    And its player1s turn
    When I attack a coordinate
    Then the shot result should be on that coordinate
    And the tile should be hit

  Scenario: Attacking a coordinate as when its turn2
    Given a game
    And its player2s turn
    When I attack a coordinate
    Then the shot result should be on that coordinate
    And the tile should be hit
  Scenario: Setting the initial turn for when a game is saved
    Given a game
    And its player1s turn
    When I set the initial turn
    Then the turn should be 1

  Scenario: Getting the initial turn from a game
    Given a game
    And initial turn is player1
    Then the initial turn should be 1

  Scenario: initialising the board views
    Given a board with all gurkins paced in valid positions
    When I initialise the board views
    Then the board views should be initialised

  Scenario: initialising the board views Vertically
    Given a board with all gurkins paced in valid vertical positions
    When I initialise the board views
    Then the board views should be initialised vertically

# ----------------------------- EXTRA PLAYER TESTING ---------------------
  Scenario: initialising a player with attributes
    When i initialise a player with attributes
    Then the player should have those attributes

  Scenario: Resetting placement
    Given a player
    And its player1s turn
    When I reset the placement
    Then the board should be reset



















