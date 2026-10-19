package com.sparta.pets.steps;

import com.sparta.pets.util.PetsAPIConfig;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


public class DeleteUserResourceStepdefs {
    private Response response;
    private static PetsAPIConfig config;
    private static final String BASE_URI = "http://localhost:8080/api/v3";

//This file relies on Docker container to be run, hense why the BASE_URI is local host

    private final String user = """
    {
      "id": 707,
      "username": "JettMain707",
      "firstName": "Radiant",
      "lastName": "Duelist",
      "email": "jettmain707@example.com",
      "password": "Spike123!",
      "phone": "07700900707",
      "userStatus": 1
    }
    """;
    private String username;
    private String name;

    @Given("I have created a user that now exists")
    public void iHaveCreatedAUserThatNowExists() {
        username ="JettMain707";
        RestAssured
                .given()
                    .baseUri(BASE_URI)
                    .contentType(ContentType.JSON)
                    .body(user)
                .when()
                    .post("/user")
                .then()
                    .statusCode(200);

    }

    @And("I want to delete the created {string}")
    public void iWantToDeleteTheCreated(String name) {
        this.name = name;

    }

    @When("I send a DELETE request for that user")
    public void iDELETEUserUsernameOf() {
        response =
                RestAssured
                        .given()
                            .baseUri(BASE_URI)
                        .when()
                            .delete("/user/" + username);
    }

    @Then("I should get a status code {int}")
    public void iShouldGetAStatusCodeInt(int expectedCode) {
        int actualHttpCode = response.statusCode();
        assertThat(actualHttpCode, is(expectedCode));
    }

//    @Given("I want to delete a user with the username {string}")
//    public void iWantToDeleteAUserWithTheUsername(String arg0) {
//        // Write code here that turns the phrase above into concrete actions
//        throw new PendingException();
//    }
//
//    @When("I want to \\\\\\/user\\\\\\/\\\\\\{username}")
//    public void iWantToUserUsername() {
//        // Write code here that turns the phrase above into concrete actions
//        throw new PendingException();
//    }
}
