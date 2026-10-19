Feature:
  User story to create Users

  As a user I want to create a user account
  So that I can keep track of my orders

  @Happy
    Scenario Outline: Create User with username and correct details
    Given I Want to Create a User with username <username>
    When I add a valid details
    And I post to /user/<username>
    Then I should get http code 200
    And I should have a new user created

    Examples:
      |username      |
      |sparta_user_01|

  @Sad
    Scenario Outline: Create User with invalid Details
    Given I want to create a user with the username <username>
    When I add invalid details
    And I post to /user/<username>
    Then I should have an unexpected error
    And User should not be created

    Examples:
      |username              |
      |sparta_invalid_user_01|
