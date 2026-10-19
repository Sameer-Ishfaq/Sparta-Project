Feature: User Stories for /user/
  I want to be able to delete a user

  @Happy
  Scenario: Deleting an existing user
    Given I have created a user that now exists
    When I send a DELETE request for that user
    Then I should get a status code 200



