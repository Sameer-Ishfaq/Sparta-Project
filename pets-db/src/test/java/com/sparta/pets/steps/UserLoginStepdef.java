package com.sparta.pets.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;

/**
 * "I should get http code {int}" is defined in UserCreateStepdefs and reused here,
 * so the login response is stored in the shared {@link UserContext}.
 */
public class UserLoginStepdef {

    private static final String SESSION_MESSAGE = "Logged in user session";

    private final UserContext context;

    private String username;
    private String password;

    public UserLoginStepdef(UserContext context) {
        this.context = context;
    }

    // ---------- @Happy ----------

    @Given("I want to Login to account")
    public void iWantToLoginToAccount() {
        // Nothing to set up - the username and password come from the following steps
    }

    @When("I add an Existing username {word}")
    public void iAddAnExistingUsername(String username) {
        this.username = username;
    }

    @And("I enter and I enter password that matches the one for Username {word}")
    public void iEnterPasswordThatMatches(String password) {
        this.password = password;
    }

    // Regex used because "/" is an alternation character in Cucumber expressions
    @And("^I GET user/login$")
    public void iGetUserLogin() {
        context.setResponse(context.request()
                .queryParam("username", username)
                .queryParam("password", password)
                .when()
                .get("/user/login"));
    }

    @And("I am able to access Account")
    public void iAmAbleToAccessAccount() {
        MatcherAssert.assertThat(context.getResponse().asString(), Matchers.containsString(SESSION_MESSAGE));
        MatcherAssert.assertThat(context.getResponse().header("X-Expires-After"), Matchers.notNullValue());
    }

    // ---------- @Sad ----------

    @Given("I want to Login to account {word}")
    public void iWantToLoginToAccount(String username) {
        this.username = username;
    }

    @When("I add a non Existing username or password {word}")
    public void iAddANonExistingUsernameOrPassword(String password) {
        this.password = password;
    }

    @Then("I am Given http code {int} {string}")
    public void iAmGivenHttpCode(int code, String message) {
        MatcherAssert.assertThat(context.getResponse().statusCode(), Matchers.is(code));
        MatcherAssert.assertThat(context.getResponse().asString(), Matchers.containsString(message.trim()));
    }

    @And("I am unable to access the Account")
    public void iAmUnableToAccessTheAccount() {
        MatcherAssert.assertThat(context.getResponse().asString(), Matchers.not(Matchers.containsString(SESSION_MESSAGE)));
    }
}
