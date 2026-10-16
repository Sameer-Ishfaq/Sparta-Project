Feature: Login user using user/login
  As a user I want to be able to login to my account
  So that i can gain access to my orders

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
