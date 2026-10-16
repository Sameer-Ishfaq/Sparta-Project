Feature:
  As a store employee,
  I want to search for the details of a pet in the database by TAGs,
  SO THAT i can view its information

  @Happy
  Scenario:
    Given i am searching for a pet by tag - <tag>
    And the endpoint is set to "/pet"
    When i send a GET request to the endpoint using a tag
    Then a status code of 200 should be returned

    @Sad
    Scenario:
      Given I am searching for a pet using an invalid tag - <tag>
      And the endpoint is set to "/pet"
      When I send a GET request to the endpoint using the tag
      Then status code 404 should be returned

      @Sad
      Given i add a pet with invalid information in the incorrect format
      When i send a POST request
      Then a new pet should be added returning status 400 telling me there is an invalid input