Feature: User Stories for /user/
  I want to update information about a users account

  @Happy
  Scenario: Updating an existing user
    Given I have created a new user
    And I send a GET request to see if it successfully made a new user
    When  I send a PUT request to change my details
    Then The accounts details should have been changed, I should get http code 200

  @Sad
  Scenario: Updating a user with invalid details
    Given I have created a new user for invalid details
    And I send a GET request to see if it successfully made a new user for this
    When I send a PUT request to change my details with incorrect details
    Then I should get a http status of 400

