Feature: User Stories for /user/
As a user of the Petstore
  I want to be able to create, delete, manage and alter users.
  So that I can keep track of the current records.

  @Happy
  Scenario: Getting username that exists
    Given I am looking for a username that exists "<username>"
    When I enter a GET requests to /users/{username}
    And I should get a http code 200
    Then I should see details about the username: first name, last name, id, email, password, phone, user status

  @Sad
  Scenario Outline: Getting a username that does not exist
    Given No user exists with the name <username>
    When I send a GET request to /users/{username}
    Then I should a http code 404

    Examples:
      |username|
      |drum_and_bass_is_the_best_1  |
      |i_love_eating_food|
      |chicken_nugget_lover67|

  @Happy
  Scenario Outline: Updating an existing user
    Given I am logged in as an existing user "<username>"
    And I want to update my phone number on my account
    And I have recorded the current users phone number using a GET request
    When  I send a PUT request to change my details to "<new_phone>",
    Then I should get http code 200
    And The accounts phone number should be changed to my new phone number "<new_phone>"

    Examples:
      |username|new_phone|
      |user01  |01952773812|

  @Sad
  Scenario Outline: Updating a user with invalid details
    Given I am logged in as an existing user "<username>"
    When I want to update my "<phone_number>" on my account using PUT
    Then I should get a http status of 400

    Examples:
      |username|phone_number|
      |user_01 |00000000000 |
      |user_2  |12121212121 |

  @Happy
  Scenario Outline: Deleting an existing user
    Given am logged in an existing user "<username"
    When I DELETE /user/{username} of "<username>"
    Then I should get a status code 200

    Examples:
      |username|
      |user_01 |

  @Sad
  Scenario Outline: Deleting a user being logged out
    Given I am logged out using "<username>"
    And I want to delete the user
    When I DELETE /user/{username} of my user
    Then I should get a status code 401

    Examples:
      |username|
      |user_01 |

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
  Scenario Outline: Login User when correct details Entered
    Given I want to Login to account
    When I add an Existing username <username>
    And I enter and I enter password that matches the one for Username <password>
    And I GET user/login
    Then I should get http code 200
    And I am able to access Account

  Examples:
  |username|password|
  |user01  |Test1235|
  |user02  |Test1235|
  |user03  |Test1235|

  @Sad
  Scenario Outline: Login User when incorrect details Entered
    Given I want to Login to account <username>
    When I add a non Existing username or password <password>
    And I GET user/login
    Then I am Given http code 400 "	Invalid username/password supplied"
    And I am unable to access the Account

  Examples:
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













