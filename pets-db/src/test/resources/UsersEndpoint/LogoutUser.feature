Feature: Logout using user/logout
  As a user I want to be able to logout from my account
  So that I can end my session

  @Happy
    Scenario: Logout User
    Given I want to Logout of account
    When I am logged in as a user
    And I request GET user/Logout
    Then I get http code 200
    And I should be logged out

  @Sad
    Scenario: Logout user without permission
    Given I want to Logout of account <username>
    When I am not logged in as a user
    And I request GET user/Logout
    Then I get http code "default Unexpected failure"

