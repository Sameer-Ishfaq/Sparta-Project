Feature: Place an order for a pet
  As a Customer
  I want to place an order for a pet
  So that I can purchase the pet I want

  @happy
  Scenario: Place an order with all fields
    Given I have a valid order with every field filled in
    When I send a POST request to the "/store/order" endpoint
    Then the HTTP status code should be 200 OK
    And the response should match the order I sent

  @happy
  Scenario: Place an order with only the basic fields
    Given I have an order with only an ID, pet ID and quantity
    When I send a POST request to the "/store/order" endpoint
    Then the HTTP status code should be 200 OK
    And the response should contain the ID, pet ID and quantity I sent

  @happy
  Scenario: A placed order is saved and can be retrieved
    Given I have placed a valid order
    When I send a GET request to the "/store/order/{orderId}" endpoint for that order
    Then the HTTP status code should be 200 OK
    And the response should match the order I placed

  @sad
  Scenario: Place an order with malformed JSON
    Given I have an order body with broken JSON syntax
    When I send a POST request to the "/store/order" endpoint
    Then the HTTP status code should be 400 Bad Request

  @sad
  Scenario: Place an order with the wrong data type
    Given I have an order where the pet ID is text instead of a number
    When I send a POST request to the "/store/order" endpoint
    Then the HTTP status code should be 400 Bad Request

  @sad
  Scenario: Place an order with an unsupported content type
    Given I have a valid order
    When I send a POST request to the "/store/order" endpoint as plain text
    Then the HTTP status code should be 415 Unsupported Media Type
