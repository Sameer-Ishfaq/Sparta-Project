package com.sparta.pets.steps;

import com.sparta.pets.util.PetsAPIConfig;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class GetUserByUsernameStepdefs {
    private static final String BASE_URI = "https://petstore3.swagger.io/api/v3";

    private String username = "user1";
    private Response response;
    private static PetsAPIConfig config;

    @Given("I am looking for a username that exists {string}")
    public void iAmLookingForAUsernameThatExists(String username) {
        this.username = username;
    }

    @When("I enter a GET requests to \\/users\\/\\{username}")
    public void iEnterAGETRequestsToUsersUsername() {
        response = RestAssured
                .given()
                    .baseUri(BASE_URI)
                    .pathParam("username",username)
                .when()
                    .get("/user/{username}");
    }

    @And("I should get a http code {int}")
    public void iShouldGetAHttpCode(int expectedHttpCode) {
        int actualHttpCode = response.statusCode();
        assertThat(actualHttpCode,is(expectedHttpCode));
    }

    @Then("I should see details about the username: first name, last name, id, email, password, phone, user status")
    public void iShouldSeeDetailsAboutTheUsernameFirstNameLastNameIdEmailPasswordPhoneUserStatus() {
        response.prettyPrint();

        assertThat(response.jsonPath().getString("username"), is(username));
        assertThat(response.jsonPath().getString("firstName"), notNullValue());
        assertThat(response.jsonPath().getString("lastName"), notNullValue());
        assertThat(response.jsonPath().getString("id"), notNullValue());
        assertThat(response.jsonPath().getString("email"), notNullValue());
        assertThat(response.jsonPath().getString("password"),notNullValue());
        assertThat(response.jsonPath().getString("phone"), notNullValue());
        assertThat(response.jsonPath().getString("userStatus"), notNullValue());
    }

    /////////////////////////////////////////////////////////////////////////////////////////

    @Given("No user exists with the name {string}")
    public void noUserExistsWithTheNameUsername(String username) {this.username = username;}

    @When("I send a GET request to \\/users\\/\\{username}")
    public void iSendAGETRequestToUsersUsername() {
       response = RestAssured
               .given()
                    .baseUri(BASE_URI)
               .when()
                    .pathParam("username",username)
               .get("user/{username}");
    }

    @Then("I should a http code {int}")
    public void iShouldAHttpCode(int expectedHttpCode) {
        assertThat(response.statusCode(), is(expectedHttpCode));
    }
}
