@tag
  Feature: Player Shooting
    @tag1
    Scenario: shooting a tile with a gurkin on
      Given a player
      And a board
      When I shoot a tile with a gurkin on it
      Then I get the string hit
    @tag1
    Scenario: Shooting a tile with no gurkin on
      Given a player
      And a board
      When I shoot a tile with no gurkin on
      Then I get the string miss