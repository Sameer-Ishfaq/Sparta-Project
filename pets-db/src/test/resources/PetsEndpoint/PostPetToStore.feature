Feature:
  As a pet store employee,
  I want to be able to add a new pet to the store,
  So That customers can view them

  @Happy
  Scenario: Add a pet with valid information
    Given i add a pet with the valid information
    When i send a POST request
    Then a new pet should be added returning status code 200

  @Sad
  Scenario: Add a pet with invalid information
    Given I add a pet with the invalid information in the correct format
    When I send a POST request
    Then a new pet should be added returning status 422 telling me there is a validation error
