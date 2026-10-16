Feature: Delete an Order
  As a customer of the pet store
  I want to delete an order
  So that I can cancel something I no longer want

  @Happy
  Scenario: Delete an existing order
    Given I am using the petstore API with the base path is set to "/store/order"
    And an order exists with id 8 for pet 1 with quantity 1
    When I delete the order with id 8
    Then I should get a 200 response code

  @Happy
  Scenario: A deleted order can no longer be looked up
    Given I am using the petstore API with the base path is set to "/store/order"
    And an order exists with id 8 for pet 1 with quantity 1
    When I delete the order with id 8
    Then I should get a 200 response code
    And the order with id 8 should no longer exist

  @Happy
  Scenario: Deleting one order does not delete another order
    Given I am using the petstore API with the base path is set to "/store/order"
    And an order exists with id 8 for pet 1 with quantity 1
    And an order exists with id 9 for pet 2 with quantity 3
    When I delete the order with id 8
    Then I should get a 200 response code
    And the order with id 9 should still exist

  @Sad
  Scenario: Delete an order that does not exist
    Given I am using the petstore API with the base path is set to "/store/order"
    When I delete the order with id 99999
    Then I should get a 404 response code

  @Sad
  Scenario: Delete an order with an invalid id
    Given I am using the petstore API with the base path is set to "/store/order"
    When I delete the order with id "abc"
    Then I should get a 400 response code

  @Sad
  Scenario: Delete the same order twice
    Given I am using the petstore API with the base path is set to "/store/order"
    And an order exists with id 8 for pet 1 with quantity 1
    And I delete the order with id 8
    When I delete the order with id 8
    Then I should get a 404 response code