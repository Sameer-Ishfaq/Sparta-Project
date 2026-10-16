Feature: Update Pet
  As a pet store employee
  I want to update an existing pet's information
  So that customers can view accurate pet details

  Background:
    Given I am using the pet api with path set to "pet"

  @Happy
  Scenario: Update an existing pet with valid details
    Given pet ID 1 exists
    When I send a PUT request to update pet ID 1 with valid details
    Then I should receive a 200 response code
    And pet ID 1 should contain the updated details

  @Sad
  Scenario: Attempt to update a pet with an invalid ID
    When I send a PUT request with an invalid pet ID
    Then I should receive a 400 response code
