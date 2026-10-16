Feature:
  As a pet store employee,
  I want to be able to add an image to a pet,
  so that each pet can be visualised by users

  @Happy
  Scenario: Upload an image for an existing pet
    Given i upload a valid petID information with an image
    When i send a POST request
    Then the response code should be 200

  @Sad
  Scenario: Attempt to update a pet using POST
    Given i update a pets information
    When i send a POST request to /pet
    Then the response code should be 400
