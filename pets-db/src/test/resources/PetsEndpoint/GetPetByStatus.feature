Feature:
  AS a pet store employee,
  I want to search for the details of a pet in the database by status,
  SO THAT i can view its information.

  @Happy
  Given i am searching for a pet that exists within the database,
    When i send a GET request to /pet,
    Then the response code should be 200

  @Sad
  Given i am searching for a pet status that does not exist in the database
    When i send as GET request to /pet/findByStatus with a pet not in db
    Then HTTP status code should be 400 invalid status value
