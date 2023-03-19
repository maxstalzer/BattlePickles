@Tag
  Feature: Ship placement
    I want to be 

    @Tag
    Scenario: Title of your scenario
      Given I want to write some precondition
      And Some other precondition
      When I complete action
      And some other action
      And yet another action
      Then I validate the outcomes
      And check other outcomes
    @Tag
    Scenario Outline: Title of scenario outline
      Given i want to write a step with <name>
      When I check for the <value> in the step
      Then I verify the <status> in step

      Examples:
        | name  | value | status  |
        | name1 | 1     | success |
        | name2 | 2     | failure |