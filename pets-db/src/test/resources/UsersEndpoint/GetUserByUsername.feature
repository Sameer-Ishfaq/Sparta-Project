Feature: User stories for /user/
  As a User, I want to be able to get information about the username

@Happy
Scenario: Getting username that exists
Given I am looking for a username that exists "<username>"
When I enter a GET requests to /users/{username}
And I should get a http code 200
Then I should see details about the username: first name, last name, id, email, password, phone, user status

@Sad
Scenario Outline: Getting a username that does not exist
Given No user exists with the name <username>
When I send a GET request to /users/{username}
Then I should a http code 404

Examples:
|username|
|drum_and_bass_is_the_best_1  |
|i_love_eating_food|
|chicken_nugget_lover67|

