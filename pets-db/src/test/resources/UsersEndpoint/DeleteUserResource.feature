Feature: User Stories for /user/
  I want to be able to delete a user

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