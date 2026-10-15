Feature: User Stories for /user/

  @Happy
  Scenario: Create User with username and incorrect details
    Given I Want to Create a User with username <username>
    When I add a valid details
    And I post to /user/<username>
    Then I should get http code 200
    And I should have a new user created

  @Sad
  Scenario: Create User with invalid Details
    Given I want to create a user with the username <username>
    When I add invalid details
    And I post to /user/<username>
    Then I should have an unexpected error
    And User should not be created

  @Happy
  Scenario: Create List Users with Valid Credentials
    Given I Want to Create a List of Users with input Array <List>
    When I add a valid details
    And I post to /user/createdWithList/<List>
    Then I should get http code 200
    And I should have a new user created

  @Sad
  Scenario: Create List Users with invalid Details
    Given I want to create a List of users with input array
    When I add invalid details
    And I post to user/createdWithList/<List>
    Then I should have an unexpected error
    And User should not be created


  @Happy
  Scenario: Login User when correct details Entered
    Given I want to Login to account
    When I add an Existing username <username>
    And I enter and I enter password that matches the one for Username <password>
    And I GET user/login
    Then I should get http code 200
    And I am able to access Account

    Example:
      |username|password|
      |user01  |Test1235|
      |user02  |Test1235|
      |user03  |Test1235|

  @Sad
  Scenario: Login User when incorrect details Entered
    Given I want to Login to account <username>
    When I add a non Existing username or password
    And I GET user/login
    Then I am Given http code 400 "	Invalid username/password supplied"
    And I am unable to access the Account

  Example:
  |username|password|
  |user01  |FalsePword1|
  |user02  |FalsePword2|
  |user03  |FalsePword3|


  @Happy
  Scenario: Login User when Incorrect Details are Entered
    Given I want to Logout of account
    When I am logged in as a user
    And I request GET user/Logout
    Then I get http code 200
    And I should be logged out

  @Happy
  Scenario: Logout user without permission
    Given I want to Logout of account <username>
    When I am not logged in as a user
    And I request GET user/Logout
    Then I get http code "default Unexpected failure"


