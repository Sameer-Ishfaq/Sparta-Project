Feature:
  Search for pet by ID

  Background:
  As a customer searching for a pet

  @Happy
  Scenario Outline:
    Given I am searching for a pet using a valid ID - <id>
    And the endpoint is set to "/pet"
    When I send a GET request to the endpoint using my ID
    Then status code 200 should be returned
    And the returned name should be <name>

    Examples:
    | id | name |
    | 10 | "doggie" |

    @Sad
    Scenario Outline:
      Given I am searching for a pet using an invalid ID - <id>
      And the endpoint is set to "/pet"
      When I send a GET request to the endpoint using my ID
      Then status code 404 should be returned

      Examples:
      | id |
      | -1 |
      | 99999999 |