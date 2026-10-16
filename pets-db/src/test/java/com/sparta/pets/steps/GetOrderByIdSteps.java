package com.sparta.pets.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GetOrderByIdSteps {

    private static final String BASE_URI = "https://petstore.swagger.io/v3";

    private String storedOrderJson;
    private int orderId;

    @Given("an order exists")
    public void anOrderExists() {
        createOrder(5);
    }

    @Given("an order exists with ID {int}")
    public void anOrderExistsWithId(int id) {
        createOrder(id);
    }

    @Given("no order exists with ID {int}")
    public void noOrderExistsWithId(int id) {
        given().baseUri(BASE_URI)
                .when().delete("/store/order/" + id);
    }

    @When("I send a GET request to the {string} endpoint with its ID")
    public void iSendAGetRequestToTheEndpointWithItsId(String endpoint) {
        String path = endpoint.replace("{orderId}", String.valueOf(orderId));
        PlaceOrderSteps.response = given().baseUri(BASE_URI)
                .accept(ContentType.JSON)
                .when().get(path);
    }

    @When("I send a GET request to the {string} endpoint")
    public void iSendAGetRequestToTheEndpoint(String endpoint) {
        PlaceOrderSteps.response = given().baseUri(BASE_URI)
                .accept(ContentType.JSON)
                .when().get(endpoint);
    }

    @When("I send a POST request to the {string} endpoint with its ID")
    public void iSendAPostRequestToTheEndpointWithItsId(String endpoint) {
        String path = endpoint.replace("{orderId}", String.valueOf(orderId));
        PlaceOrderSteps.response = given().baseUri(BASE_URI)
                .contentType(ContentType.JSON)
                .when().post(path);
    }

    @Then("the Content-Type header should be {string}")
    public void theContentTypeHeaderShouldBe(String expected) {
        String actual = PlaceOrderSteps.response.getContentType();
        assertTrue(actual.startsWith(expected), "Expected " + expected + " but was " + actual);
    }

    @Then("the response should match the stored order")
    public void theResponseShouldMatchTheStoredOrder() {
        JsonPath stored = JsonPath.from(storedOrderJson);
        JsonPath received = PlaceOrderSteps.response.jsonPath();

        assertEquals(stored.getLong("id"), received.getLong("id"), "id");
        assertEquals(stored.getLong("petId"), received.getLong("petId"), "petId");
        assertEquals(stored.getInt("quantity"), received.getInt("quantity"), "quantity");
        assertEquals(stored.getString("status"), received.getString("status"), "status");
        assertEquals(stored.getBoolean("complete"), received.getBoolean("complete"), "complete");
        assertEquals(parseDate(stored.getString("shipDate")), parseDate(received.getString("shipDate")), "shipDate");
    }

    @Then("the response ID should be {int}")
    public void theResponseIdShouldBe(int expectedId) {
        assertEquals(expectedId, PlaceOrderSteps.response.jsonPath().getInt("id"));
    }

    private void createOrder(int id) {
        orderId = id;
        storedOrderJson = """
                {
                  "id": %d,
                  "petId": 101,
                  "quantity": 1,
                  "shipDate": "2026-10-15T09:30:00.000Z",
                  "status": "placed",
                  "complete": false
                }
                """.formatted(id);

        Response setup = given().baseUri(BASE_URI)
                .contentType(ContentType.JSON)
                .body(storedOrderJson)
                .when().post("/store/order");
        assertEquals(200, setup.getStatusCode(), "Set-up failed: could not create order " + id);
    }

    private Instant parseDate(String value) {
        assertNotNull(value, "shipDate should be present");
        try {
            return OffsetDateTime.parse(value).toInstant();
        } catch (DateTimeParseException e) {
            return OffsetDateTime.parse(value, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ")).toInstant();
        }
    }
}