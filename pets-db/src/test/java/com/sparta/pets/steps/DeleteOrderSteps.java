
package com.sparta.pets.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;

public class DeleteOrderSteps {

    // The address of the Petstore API
    private static final String BASE_URI =
            "https://petstore3.swagger.io/api/v3";

    // These carry information from one step to the next
    private String basePath;
    private Response response;

    // Starts every request: base address + base path
    private RequestSpecification request() {
        return RestAssured.given()
                .baseUri(BASE_URI)
                .basePath(basePath);
    }


    // Given an order exists with id 8 for pet 1 with quantity 1
    @Given("an order exists with id {int} for pet {int} with quantity {int}")
    public void anOrderExistsWithIdForPetWithQuantity(
            int orderId, int petId, int quantity) {

        // Creating the order so the test has an order to work with
        String orderBody = """
                {
                    "id": %d,
                    "petId": %d,
                    "quantity": %d,
                    "shipDate": "2026-10-16T12:00:00.000Z",
                    "status": "placed",
                    "complete": true
                }
                """.formatted(orderId, petId, quantity);

        Response createResponse = request()
                .contentType("application/json")
                .body(orderBody)
                .when()
                .post()
                .thenReturn();

        MatcherAssert.assertThat(
                "Order creation should succeed before the test",
                createResponse.statusCode(),
                Matchers.is(200)
        );
    }

    @Given("I delete the order with id {int}")
    public void iDeleteTheOrderWithId(int orderId) {
        response = request()
                .when()
                .delete("/" + orderId)
                .thenReturn();
    }



    @Then("the order with id {int} should no longer exist")
    public void theOrderWithIdShouldNoLongerExist(int orderId) {
        Response lookupResponse = request()
                .when()
                .get("/" + orderId)
                .thenReturn();

        MatcherAssert.assertThat(
                lookupResponse.statusCode(),
                Matchers.is(404)
        );
    }


    @Then("the order with id {int} should still exist")
    public void theOrderWithIdShouldStillExist(int orderId) {
        Response lookupResponse = request()
                .when()
                .get("/" + orderId)
                .thenReturn();

        MatcherAssert.assertThat(
                lookupResponse.statusCode(),
                Matchers.is(200)
        );

        MatcherAssert.assertThat(
                lookupResponse.jsonPath().getInt("id"),
                Matchers.is(orderId)
        );
    }


    @When("I delete the order with id {string}")
    public void iDeleteTheOrderWithInvalidId(String orderId) {
        response = request()
                .when()
                .delete("/" + orderId)
                .thenReturn();
    }
}
