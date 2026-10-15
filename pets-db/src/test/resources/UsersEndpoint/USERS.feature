Feature: User Stories for /user/

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











