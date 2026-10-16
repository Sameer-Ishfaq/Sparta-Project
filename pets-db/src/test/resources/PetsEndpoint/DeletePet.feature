Feature: Delete Pet By ID
  As a pet store employee
  I want to delete pets that we no longer sell
  So that our database only contains pets currently available for sale

  Background:
    Given I am using the pet api with path set to "pet"

  @Happy
  Scenario: Delete an existing pet
    Given a pet exists in the database with a valid ID
    When I send a DELETE request to /pet/{petId}
    Then I should receive a 200 response code
    And the pet should no longer exist in the database

  @Sad
  Scenario Outline: Delete a pet that does not exist
    Given a pet ID - <id> - does not exist in the database
    When I send a DELETE request to /pet/{petId}
    Then I should receive a 404 response code

    Examples:
    | id |
    | -999 |
    | -998 |