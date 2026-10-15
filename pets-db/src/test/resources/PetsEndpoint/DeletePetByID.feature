Feature: Delete Pet By ID
  As a pet store employee
  I want to be able to remove animals from the database
  So that animals we are no longer selling are available.

  Background:
    Given I am using the pet api with path set to "pet"
    And pet ID 1 exists

    @Happy
    Scenario: Attempt to delete pet with a valid ID
      When I try to delete pet ID "1"
      Then I should get a 200 response code
      And pet ID 1 should no longer exist
      And I should get an "Pet deleted" message

  @Sad
  Scenario: Attempt to delete pet with an invalid ID
    When I try to delete pet ID "invalid"
    Then I should get a 400 response code
    And I should get an "Invalid pet value" message