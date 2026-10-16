Feature:
  As a store employee,
  I want to search for the details of a pet in the database by TAGs,
  SO THAT i can view its information

  @Happy
  @Sad
  Scenario Outline: Search for pets using tags
    And the endpoint is set to "/pet/findByTags"
    When I send a GET request to the endpoint using my tags "<tags>"
    Then status code <status> should be returned

    Examples:
    |tags|status|
    |tags|400|
    |0, string|200|