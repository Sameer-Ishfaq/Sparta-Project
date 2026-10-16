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

/**
 * "I should get http code {int}" is defined in CreateUserStepDefs and reused here.
 */
public class UserLoginStepdef {

    private static PetsAPIConfig config;

    // TODO: store the responses from the one-off API calls here
    private static Response loginValidResponse;
    private static Response loginInvalidResponse;

    private String username;
    private String password;

    @BeforeAll
    public static void setup() {
        config = new PetsAPIConfig();
        config.LoadFromFile();
        RestAssured.baseURI = config.GetProperty("pets.uri");
        // TODO: call the API once here and store the response(s)
    }

    // ---------- @Happy ----------

    @Given("I want to Login to account")
    public void iWantToLoginToAccount() {
        throw new PendingException();
    }

    @When("I add an Existing username {word}")
    public void iAddAnExistingUsername(String username) {
        throw new PendingException();
    }

    @And("I enter and I enter password that matches the one for Username {word}")
    public void iEnterPasswordThatMatches(String password) {
        throw new PendingException();
    }

    // Regex used because "/" is an alternation character in Cucumber expressions
    @And("^I GET user/login$")
    public void iGetUserLogin() {
        throw new PendingException();
    }

    @And("I am able to access Account")
    public void iAmAbleToAccessAccount() {
        throw new PendingException();
    }

    // ---------- @Sad ----------

    @Given("I want to Login to account {word}")
    public void iWantToLoginToAccount(String username) {
        throw new PendingException();
    }

    @When("I add a non Existing username or password {word}")
    public void iAddANonExistingUsernameOrPassword(String password) {
        throw new PendingException();
    }

    @Then("I am Given http code {int} {string}")
    public void iAmGivenHttpCode(int code, String message) {
        throw new PendingException();
    }

    @And("I am unable to access the Account")
    public void iAmUnableToAccessTheAccount() {
        throw new PendingException();
    }
}