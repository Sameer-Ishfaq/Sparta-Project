package com.sparta.pets.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;

public class UserLogoutStepdef {

    // One of the users Petstore is seeded with (user1 - user10 all share this password)
    private static final String SEEDED_USERNAME = "user1";
    private static final String SEEDED_PASSWORD = "XXXXXXXXXXX";

    private final UserContext context;

    private Response logoutResponse;

    private String username = SEEDED_USERNAME;

    public UserLogoutStepdef(UserContext context) {
        this.context = context;
    }

    // ---------- @Happy ----------

    @Given("I want to Logout of account")
    public void iWantToLogoutOfAccount() {
        // Nothing to set up - logs out the default seeded user
    }

    @When("I am logged in as a user")
    public void iAmLoggedInAsAUser() {
        Response loginResponse = context.request()
                .queryParam("username", username)
                .queryParam("password", SEEDED_PASSWORD)
                .when()
                .get("/user/login");
        MatcherAssert.assertThat("Could not log in before logging out", loginResponse.statusCode(), Matchers.is(200));
    }

    // Regex used because "/" is an alternation character in Cucumber expressions.
    // The path is case-sensitive: /user/Logout returns 500, so the request uses /user/logout.
    @And("^I request GET user/Logout$")
    public void iRequestGetUserLogout() {
        logoutResponse = context.request()
                .when()
                .get("/user/logout");
    }

    @Then("I get http code {int}")
    public void iGetHttpCode(int code) {
        MatcherAssert.assertThat(logoutResponse.statusCode(), Matchers.is(code));
    }

    @And("I should be logged out")
    public void iShouldBeLoggedOut() {
        MatcherAssert.assertThat(logoutResponse.asString(), Matchers.containsString("User logged out"));
    }

    // ---------- @Sad ----------

    @Given("I want to Logout of account {word}")
    public void iWantToLogoutOfAccount(String username) {
        this.username = username;
    }

    @When("I am not logged in as a user")
    public void iAmNotLoggedInAsAUser() {
        // Deliberately no login request, so there is no session to log out of
    }

    // "default Unexpected failure" is the Swagger docs' name for the catch-all error response
    @Then("I get http code {string}")
    public void iGetHttpCode(String message) {
        MatcherAssert.assertThat(message, logoutResponse.statusCode(), Matchers.greaterThanOrEqualTo(400));
    }
}
