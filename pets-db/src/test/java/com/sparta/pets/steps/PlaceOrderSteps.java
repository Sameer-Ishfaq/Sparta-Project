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

public class PlaceOrderSteps {

    private static final String BASE_URI = "http://localhost:8080/api/v3";

    private String requestBody;
    private int orderId;
    static Response response;

    @Given("I have a valid order with every field filled in")
    public void iHaveAValidOrderWithEveryFieldFilledIn() {
        orderId = 5;
        requestBody = fullOrderJson(orderId, 101);
    }

    @Given("I have an order with only an ID, pet ID and quantity")
    public void iHaveAnOrderWithOnlyAnIdPetIdAndQuantity() {
        orderId = 7;
        requestBody = """
                {
                  "id": 7,
                  "petId": 102,
                  "quantity": 2
                }
                """;
    }

    @Given("I have placed a valid order")
    public void iHavePlacedAValidOrder() {
        orderId = 6;
        requestBody = fullOrderJson(orderId, 103);

        Response setup = given().baseUri(BASE_URI)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when().post("/store/order");
        assertEquals(200, setup.getStatusCode(), "Set-up failed: could not place the order");
    }

    @Given("I have an order body with broken JSON syntax")
    public void iHaveAnOrderBodyWithBrokenJsonSyntax() {
        requestBody = "{ \"id\": 5, \"petId\": ";
    }

    @Given("I have an order where the pet ID is text instead of a number")
    public void iHaveAnOrderWherePetIdIsText() {
        requestBody = fullOrderJson(8, 101).replace("\"petId\": 101", "\"petId\": \"abc\"");
    }

    @Given("I have a valid order")
    public void iHaveAValidOrder() {
        orderId = 9;
        requestBody = fullOrderJson(orderId, 101);
    }

    @When("I send a POST request to the {string} endpoint")
    public void iSendAPostRequestToTheEndpoint(String endpoint) {
        response = given().baseUri(BASE_URI)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when().post(endpoint);
    }

    @When("I send a POST request to the {string} endpoint as plain text")
    public void iSendAPostRequestToTheEndpointAsPlainText(String endpoint) {
        response = given().baseUri(BASE_URI)
                .contentType(ContentType.TEXT)
                .body(requestBody)
                .when().post(endpoint);
    }

    @When("I send a GET request to the {string} endpoint for that order")
    public void iSendAGetRequestToTheEndpointForThatOrder(String endpoint) {
        String path = endpoint.replace("{orderId}", String.valueOf(orderId));
        response = given().baseUri(BASE_URI)
                .accept(ContentType.JSON)
                .when().get(path);
    }

    @Then("the HTTP status code should be {int} {}")
    public void theHttpStatusCodeShouldBe(int expectedCode, String reasonPhrase) {
        assertEquals(expectedCode, response.getStatusCode());
    }

    @Then("the response should match the order I sent")
    public void theResponseShouldMatchTheOrderISent() {
        assertAllFieldsMatch();
    }

    @Then("the response should match the order I placed")
    public void theResponseShouldMatchTheOrderIPlaced() {
        assertAllFieldsMatch();
    }

    @Then("the response should contain the ID, pet ID and quantity I sent")
    public void theResponseShouldContainTheIdPetIdAndQuantityISent() {
        JsonPath sent = JsonPath.from(requestBody);
        JsonPath received = response.jsonPath();
        assertEquals(sent.getLong("id"), received.getLong("id"), "id");
        assertEquals(sent.getLong("petId"), received.getLong("petId"), "petId");
        assertEquals(sent.getInt("quantity"), received.getInt("quantity"), "quantity");
    }

    private String fullOrderJson(int id, int petId) {
        return """
                {
                  "id": %d,
                  "petId": %d,
                  "quantity": 1,
                  "shipDate": "2026-10-15T09:30:00.000Z",
                  "status": "placed",
                  "complete": false
                }
                """.formatted(id, petId);
    }

    private void assertAllFieldsMatch() {
        JsonPath sent = JsonPath.from(requestBody);
        JsonPath received = response.jsonPath();

        assertEquals(sent.getLong("id"), received.getLong("id"), "id");
        assertEquals(sent.getLong("petId"), received.getLong("petId"), "petId");
        assertEquals(sent.getInt("quantity"), received.getInt("quantity"), "quantity");
        assertEquals(sent.getString("status"), received.getString("status"), "status");
        assertEquals(sent.getBoolean("complete"), received.getBoolean("complete"), "complete");
        assertEquals(parseDate(sent.getString("shipDate")), parseDate(received.getString("shipDate")), "shipDate");
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