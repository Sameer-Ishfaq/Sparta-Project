Feature:
  AS a pet store employee,
  I want to search for the details of a pet in the database by status,
  SO THAT i can view its information.

  @Happy
  Scenario Outline: Search using a valid status
    When the endpoint is set to "/pet/findByStatus"
    And the status is set to "<status>"
    And I send a get request to the endpoint using my status
    Then status code <code> should be returned

    Examples:
      |status|code|
      |available|200|
      |pending|200|
      |sold|200|

  @Sad
  Scenario: Search using a invalid status
    When the endpoint is set to "/pet/findByStatus"
    And the status is set to "notStatus"
    And I send a get request to the endpoint using my status
    Then status code 400 should be returned
