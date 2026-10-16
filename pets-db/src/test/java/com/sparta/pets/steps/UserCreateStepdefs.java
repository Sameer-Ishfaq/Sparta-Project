package com.sparta.pets.steps;

import com.sparta.pets.util.PetsAPIConfig;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class UserCreateStepdefs {

    private static PetsAPIConfig config;

    // TODO: store the responses from the one-off API calls here
    private static Response createUserResponse;

    private String username;

    @BeforeAll
    public static void setup() {
        config = new PetsAPIConfig();
        config.LoadFromFile();
        RestAssured.baseURI = config.GetProperty("pets.uri"); // adjust to your config class
        // TODO: call the API once here and store the response(s)
    }

    // ---------- @Happy ----------

    @Given("I Want to Create a User with username {word}")
    public void iWantToCreateAUserWithUsername(String username) {
        throw new PendingException();
    }

    @When("I add a valid details")
    public void iAddAValidDetails() {
        throw new PendingException();
    }

    // Negative lookahead stops this matching the createdWithList endpoint
    @And("^I post to /user/(?!createdWithList)(.+)$")
    public void iPostToUser(String username) {
        throw new PendingException();
    }

    @Then("I should get http code {int}")
    public void iShouldGetHttpCode(int code) {
        throw new PendingException();
    }

    @And("I should have a new user created")
    public void iShouldHaveANewUserCreated() {
        throw new PendingException();
    }

    // ---------- @Sad ----------

    @Given("I want to create a user with the username {word}")
    public void iWantToCreateAUserWithTheUsername(String username) {
        throw new PendingException();
    }

    @When("I add invalid details")
    public void iAddInvalidDetails() {
        throw new PendingException();
    }

    @Then("I should have an unexpected error")
    public void iShouldHaveAnUnexpectedError() {
        throw new PendingException();
    }

    @And("User should not be created")
    public void userShouldNotBeCreated() {
        throw new PendingException();
    }
}