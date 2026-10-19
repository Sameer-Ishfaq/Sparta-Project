
Feature:
  Creates multiple users by using a list

  As a User/manager I want to be able to create multiple users
  So that its easier to handle multiple customers

  @Happy
    Scenario Outline: Create List Users with Valid Credentials
    Given I Want to Create a List of Users with input Array <List>
    When I add a valid details
    And I post to /user/createdWithList/<List>
    Then I should get http code 200
    And I should have a new user created

    Examples:
      |List                                    |
      |sparta_list_user_01,sparta_list_user_02 |

  @Sad
    Scenario Outline: Create List Users with invalid Details
    Given I want to create a List of users with input array <List>
    When I add invalid details
    And I post to user/createdWithList/<List>
    Then I should have an unexpected error
    And User should not be created

    Examples:
      |List                                                    |
      |sparta_invalid_list_user_01,sparta_invalid_list_user_02 |
