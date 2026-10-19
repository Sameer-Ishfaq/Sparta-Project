# PetStore API Test Framework

## Project overview

This repository was created for the Sparta Global API Testing Mini Project.

The aim was to build a reusable test framework for the Swagger PetStore API during one sprint. The project was completed by our group using Git, GitHub, Maven , Docker , IntelliJ , Trello , Java, Cucumber, Pet store API Docs & Postman.

The framework sends requests to the API and checks that each response is correct. It covers successful requests (happy paths) and expected failures (sad paths).

> These tests create, update, and delete PetStore data. They must only run against a test environment.

## Project goal

Deliver a clear and maintainable framework that tests the PetStore REST API, validates response data from at least three endpoint areas, and produces useful test results for the team.

## What is tested?

1. **Pets:** Create, find, update, delete, search by status or tags, and upload an image.
2. **Store:** View inventory and create, find, or delete an order.
3. **Users:** Create, find, update, delete, log in, and log out.

The tests are written as readable Cucumber scenarios using `Given`, `When`, and `Then`.

## Tools and working method

1. Java 21 and Maven
2. REST Assured for API requests
3. Cucumber for feature files
4. JUnit and Hamcrest for checks
5. Git and GitHub for version control
6. Trello for Scrum planning and task management
7. IntelliJ IDEA for development and UML generation

The project lasted one sprint and was completed by a team of 8 people. The team used Trello to organise user stories, development tasks, reviews, and defects. Standup meetings were held throughout each day to discuss progress, blockers, and the next tasks. The completed framework was prepared for a 15 to 20 minute demonstration.

## Scrum planning

The Trello board used these columns:

1. Product Backlog
2. Sprint Backlog
3. In Progress
4. In Review
5. Done
6. Notes

Features should begin as user stories, with their Cucumber scenarios recorded as test cases. Development tasks and discovered defects should have their own tickets. The Notes column should contain the project goal and Definition of Done.

## Definition of Done

A task is complete when its code and scenarios are finished, the affected test suite passes, results have been reviewed, no secret data is committed, and another team member has reviewed the change.

## Class diagram

> **UML DIAGRAM GOES HERE**

## Security

ALL secret files and keys are placed in git ignore file.

## Adding a test

1. Add a user story and test case to the project board.
2. Add a scenario to the correct feature folder.
3. Reuse an existing Java step or add one under `com.sparta.pets.steps`.
4. Validate the status code and important response fields.
5. Run the affected suite and review its report.
6. Open a pull request and move the ticket to **In Review**.

Defects found during testing should be recorded as issues with reproduction steps, the expected result, the actual result, and supporting evidence.

## Further collaboration and handover

If another group takes over, repository will be provided and board access, environment instructions, the latest report, known defects, unfinished stories, and the prioritised backlog.

The new group should run all three suites before changing the code. This creates a baseline and separates existing failures from newly introduced problems.

Future work should use short development branches and pull requests.
