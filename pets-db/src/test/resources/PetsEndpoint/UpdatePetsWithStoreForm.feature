Feature: Update Pet By ID
  As a pet store employee
  I want to be able to update animals in the database
  So that the details of animals we are selling are up to date.

  Background:
    Given I am using the pet api with path set to "pet"
    And pet ID 1 exists

  @Happy
  Scenario: Attempt to update pet with a valid ID
    When I try to update pet ID 1 with name "Buddy" and status "available"
    Then I should get a 200 response code
    And pet ID 1 should have the name "Buddy"
    And pet ID 1 should have the status "available"

  @Sad
  Scenario: Attempt to update pet with an invalid ID
    When I try to update pet ID "INVALID" with name "Buddy" and status "available"
    Then I should get a 400 response code
    And I should get an "Input error: unable to convert input to io.swagger.petstore.model.Pet" message