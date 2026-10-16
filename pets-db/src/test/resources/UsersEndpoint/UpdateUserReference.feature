Feature: User Stories for /user/
  I want to update information about a users account

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