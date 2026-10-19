package com.sparta.pets.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;

public class DeleteOrderSteps {

    private static final String BASE_URI = "http://localhost:8080/api/v3";

    private String basePath = "/store/order";
    private Response response;

    private RequestSpecification request() {
        return RestAssured.given()
                .baseUri(BASE_URI)
                .basePath(basePath != null ? basePath : "/store/order")
                .accept(ContentType.JSON);
    }

    @Given("an order exists with id {int} for pet {int} with quantity {int}")
    public void anOrderExistsWithIdForPetWithQuantity(int orderId, int petId, int quantity) {
        String orderBody = """
                {
                    "id": %d,
                    "petId": %d,
                    "quantity": %d,
                    "status": "placed",
                    "complete": false
                }
                """.formatted(orderId, petId, quantity);

        Response createResponse = request()
                .contentType(ContentType.JSON)
                .body(orderBody)
                .when()
                .post()
                .thenReturn();

        if (createResponse.statusCode() != 200) {
            System.err.println("Setup failed with status " + createResponse.statusCode() + ": " + createResponse.getBody().asString());
        }

        MatcherAssert.assertThat(
                "Order creation should succeed before the test",
                createResponse.statusCode(),
                Matchers.is(200)
        );
    }

    // Single @When annotation matches both "When I delete..." and "And I delete..."
    @When("I delete the order with id {int}")
    public void iDeleteTheOrderWithId(int orderId) {
        response = request()
                .when()
                .delete("/" + orderId)
                .thenReturn();
    }

    @When("I delete the order with id {string}")
    public void iDeleteTheOrderWithInvalidId(String orderId) {
        response = request()
                .when()
                .delete("/" + orderId)
                .thenReturn();
    }

    @Then("the order status code should be {int}")
    public void theOrderStatusStatusCodeShouldBe(int expectedStatus) {
        MatcherAssert.assertThat(response.statusCode(), Matchers.is(expectedStatus));
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
}