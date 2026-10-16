Feature: Find purchase order by ID
  As a Customer
  I want to look up my order using its order ID
  So that I can check the details and status of my purchase

  @happy
  Scenario: Retrieve an existing order
    Given an order exists
    When I send a GET request to the "/store/order/{orderId}" endpoint with its ID
    Then the HTTP status code should be 200 OK
    And the response should match the stored order

  @happy
  Scenario: Retrieve an order at the lowest valid ID
    Given an order exists with ID 1
    When I send a GET request to the "/store/order/1" endpoint
    Then the HTTP status code should be 200 OK
    And the response ID should be 1

  @happy
  Scenario: The response is returned as JSON
    Given an order exists
    When I send a GET request to the "/store/order/{orderId}" endpoint with its ID
    Then the Content-Type header should be "application/json"

  @sad
  Scenario:Retrieve an order that does not exist
    Given no order exists with ID 9999999
    When I send a GET request to the "/store/order/9999999" endpoint
    Then the HTTP status code should be 404 Not Found

  @sad
  Scenario:  Retrieve an order with a non-numeric ID
    When I send a GET request to the "/store/order/abc" endpoint
    Then the HTTP status code should be 400 Bad Request

  @sad
  Scenario: Use an unsupported method on the order ID endpoint
    Given an order exists
    When I send a POST request to the "/store/order/{orderId}" endpoint with its ID
    Then the HTTP status code should be 405 Method Not Allowed
