Feature: User Stories for /user/
  I want to be able to delete a user

  @Happy
  Scenario Outline: Deleting an existing user
    Given I have created a user that now exists
    And I want to delete the created "<username>"
    When I DELETE /user/{username} of "<username>"
    Then I should get a status code {int}

    Examples:
      |username|
      |Test_user_67|

  @Sad
  Scenario Outline: Deleting a user that does not exist
    Given I want to delete a user with the username "<username>"
    When I want to \\/user\\/\\{username}
    Then I should get a status code {int}

    Examples:
      | username                           |
      | I_hope_this_is_not_a_real_username |

