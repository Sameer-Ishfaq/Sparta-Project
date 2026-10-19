package com.sparta.pets.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;

import java.util.Map;


public class UserCreateStepdefs {

    private final UserContext context;

    public UserCreateStepdefs(UserContext context) {
        this.context = context;
    }

    // ---------- @Happy ----------

    @Given("I Want to Create a User with username {word}")
    public void iWantToCreateAUserWithUsername(String username) {
        context.getUsernames().add(username);
    }

    @When("I add a valid details")
    public void iAddAValidDetails() {
        long id = System.currentTimeMillis();
        for (String username : context.getUsernames()) {
            context.getUsers().add(Map.of(
                    "id", id++,
                    "username", username,
                    "firstName", "Test",
                    "lastName", "User",
                    "email", username + "@example.com",
                    "password", "Password123",
                    "phone", "07123456789",
                    "userStatus", 1));
        }
    }

    // Negative lookahead stops this matching the createdWithList endpoint.
    // Petstore creates users with POST /user (POST /user/{username} returns 405),
    // so the username in the step text is descriptive only - it is sent in the body.
    @And("^I post to /user/(?!createdWithList).+$")
    public void iPostToUser() {
        context.setResponse(context.request()
                .contentType(ContentType.JSON)
                .body(context.getUsers().get(0))
                .when()
                .post("/user"));
    }

    @Then("I should get http code {int}")
    public void iShouldGetHttpCode(int code) {
        MatcherAssert.assertThat(context.getResponse().statusCode(), Matchers.is(code));
    }

    @And("I should have a new user created")
    public void iShouldHaveANewUserCreated() {
        for (Map<String, Object> user : context.getUsers()) {
            Response getResponse = getUser((String) user.get("username"));
            MatcherAssert.assertThat(getResponse.statusCode(), Matchers.is(200));
            MatcherAssert.assertThat(getResponse.jsonPath().getString("username"), Matchers.is(user.get("username")));
            MatcherAssert.assertThat(getResponse.jsonPath().getString("email"), Matchers.is(user.get("email")));
        }
    }

    // ---------- @Sad ----------

    @Given("I want to create a user with the username {word}")
    public void iWantToCreateAUserWithTheUsername(String username) {
        context.getUsernames().add(username);
    }

    @When("I add invalid details")
    public void iAddInvalidDetails() {
        // Numeric fields sent as text, so the API cannot convert the body into a User
        for (String username : context.getUsernames()) {
            context.getUsers().add(Map.of(
                    "id", "not-a-number",
                    "username", username,
                    "userStatus", "not-a-number"));
        }
    }

    @Then("I should have an unexpected error")
    public void iShouldHaveAnUnexpectedError() {
        MatcherAssert.assertThat(context.getResponse().statusCode(), Matchers.is(400));
        MatcherAssert.assertThat(context.getResponse().jsonPath().getString("message"), Matchers.containsString("Input error"));
    }

    @And("User should not be created")
    public void userShouldNotBeCreated() {
        // Petstore currently answers 500 rather than 404 for a missing user, so only rule out success
        for (String username : context.getUsernames()) {
            MatcherAssert.assertThat(getUser(username).statusCode(), Matchers.not(200));
        }
    }

    private Response getUser(String username) {
        return context.request()
                .pathParam("username", username)
                .when()
                .get("/user/{username}");
    }
}
