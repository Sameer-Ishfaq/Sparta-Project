Feature: Get Store Inventory
  As a store owner of the pet store
  I want to see the pet inventory by status
  So that I can manage my stock

  @Happy
  Scenario: Get the store inventory
    Given I am using the petstore API with the base path is set to "/store/inventory"
    When I request the store inventory
    Then the inventory status code should be 200

  @Happy
  Scenario: The inventory is returned as JSON
    Given I am using the petstore API with the base path is set to "/store/inventory"
    When I request the store inventory
    Then the inventory status code should be 200
    And the response content type should be JSON

  @Happy
  Scenario: The inventory contains valid quantities
    Given I am using the petstore API with the base path is set to "/store/inventory"
    When I request the store inventory
    Then the inventory status code should be 200
    And the inventory should contain at least one status
    And every inventory quantity should be a number
    And no inventory quantity should be negative

  @Sad
  Scenario: Request the inventory from a path that does not exist
    Given I am using the petstore API with the base path is set to "/store/inventories"
    When I request the store inventory
    Then the inventory status code should be 404

  @Sad
  Scenario: Send a POST request to the inventory
    Given I am using the petstore API with the base path is set to "/store/inventory"
    When I send a POST request to the store inventory
    Then the inventory status code should be 405

  @Sad
  Scenario: Request the inventory with an extra path segment
    Given I am using the petstore API with the base path is set to "/store/inventory"
    When I request the store inventory with the extra path "abc"
    Then the inventory status code should be 404