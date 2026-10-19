package com.sparta.pets.steps;

import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class UpdateUserReferencesStepdefs {
    private String username;
    private static final String BASE_URI = "http://localhost:8080/api/v3";
    private Response response;

    private final String user = """
    {
      "id": 173,
      "username": "SCP173_DontBlink",
      "firstName": "Sculpture",
      "lastName": "Euclid",
      "email": "scp173@site19.example.com",
      "password": "Containment173!",
      "phone": "07700900173",
      "userStatus": 1
    }
    """;

    @Given("I have created a new user")
    public void iHaveCreatedANewUser() {
        username = "SCP173_DontBlink";
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

    @And("I send a GET request to see if it successfully made a new user")
    public void iSendAGETRequestToSeeIfItSuccessfullyMadeANewUser() {
        response = RestAssured
                .given()
                    .baseUri(BASE_URI)
                .when()
                    .get("/user/" + username);
                response.then()
                        .statusCode(200)
                        .body("username", equalTo(username));


    }

    @When("I send a PUT request to change my details")
    public void iSendAPUTRequestToChangeMyDetails() {
        response = RestAssured
                .given()
                    .baseUri(BASE_URI)
                    .contentType(ContentType.JSON)
                    .body(user)
                .when()
                    .put("/user/" + username);
    }

    @Then("The accounts details should have been changed, I should get http code {int}")
    public void theAccountsDetailsShouldHaveBeenChangedIShouldGetHttpCode(int expectedCode) {
      int actualHttpCode = response.statusCode();
      assertThat(actualHttpCode, is(expectedCode));
    }

//    @And("I send a GET request to see if it successfully made a new user for this")
//    public void iSendAGETRequestToSeeIfItSuccessfullyMadeANewUserForThis() {
//        // Write code here that turns the phrase above into concrete actions
//        throw new PendingException();
//    }
//
//
//
//    @When("I send a PUT request to change my details with incorrect details")
//    public void iSendAPUTRequestToChangeMyDetailsWithIncorrectDetails() {
//        // Write code here that turns the phrase above into concrete actions
//        throw new PendingException();
//    }
//
//    @Then("I should get a http status of {int}")
//    public void iShouldGetAHttpStatusOf(int arg0) {
//        // Write code here that turns the phrase above into concrete actions
//        throw new PendingException();
//    }
//
//    @Given("I have a {string} that does not exist")
//    public void iHaveAThatDoesNotExist(String arg0) {
//        // Write code here that turns the phrase above into concrete actions
//        throw new PendingException();
//    }
//
//    @When("I send a PUT request to change the details of the nonexistent user")
//    public void iSendAPUTRequestToChangeTheDetailsOfTheNonexistentUser() {
//        // Write code here that turns the phrase above into concrete actions
//        throw new PendingException();
//    }
}
