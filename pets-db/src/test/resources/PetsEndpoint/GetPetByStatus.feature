Feature: Get Pets By Status
  As a pet store employee
  I want to find pets that are currently in stock
  So that I can see which pets are available for sale

  Background:
    Given I am using the pet api with path set to "pet"

  @Happy
  Scenario: Get pets that are available
    Given a pet exists in the database with status "available"
    When I send a GET request to "/pet/findByStatus" with status "available"
    Then I should get a 200 response code
    And the response should contain pets with status "available"

  @Sad
  Scenario: Search for pets using an invalid status
    Given I have an invalid pet status "unknown"
    When I send a GET request to "/pet/findByStatus" with status "unknown"
    Then I should get a 400 response code