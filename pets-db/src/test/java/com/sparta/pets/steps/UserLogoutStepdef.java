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

public class UserLogoutStepdef {

    private static PetsAPIConfig config;

    // TODO: store the responses from the one-off API calls here
    private static Response logoutLoggedInResponse;
    private static Response logoutNotLoggedInResponse;

    private String username;

    @BeforeAll
    public static void setup() {
        config = new PetsAPIConfig();
        config.LoadFromFile();
        RestAssured.baseURI = config.GetProperty("pets.uri");
        // TODO: call the API once here and store the response(s)
    }

    // ---------- @Happy ----------

    @Given("I want to Logout of account")
    public void iWantToLogoutOfAccount() {
        throw new PendingException();
    }

    @When("I am logged in as a user")
    public void iAmLoggedInAsAUser() {
        throw new PendingException();
    }

    // Regex used because "/" is an alternation character in Cucumber expressions
    @And("^I request GET user/Logout$")
    public void iRequestGetUserLogout() {
        throw new PendingException();
    }

    @Then("I get http code {int}")
    public void iGetHttpCode(int code) {
        throw new PendingException();
    }

    @And("I should be logged out")
    public void iShouldBeLoggedOut() {
        throw new PendingException();
    }

    // ---------- @Sad ----------

    @Given("I want to Logout of account {word}")
    public void iWantToLogoutOfAccount(String username) {
        throw new PendingException();
    }

    @When("I am not logged in as a user")
    public void iAmNotLoggedInAsAUser() {
        throw new PendingException();
    }

    @Then("I get http code {string}")
    public void iGetHttpCode(String message) {
        throw new PendingException();
    }
}