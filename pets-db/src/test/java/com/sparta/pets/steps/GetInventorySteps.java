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

import java.util.Map;

public class GetInventorySteps {

    private static final String BASE_URI = "http://localhost:8080/api/v3";

    private String basePath;
    private Response response;

    private RequestSpecification request() {
        return RestAssured.given()
                .baseUri(BASE_URI)
                .basePath(basePath)
                .accept(ContentType.JSON);
    }

    @Given("I am using the petstore API with the base path is set to {string}")
    public void iAmUsingThePetstoreApi(String basePath) {
        this.basePath = basePath;
    }

    @When("I request the store inventory")
    public void iRequestTheStoreInventory() {
        response = request()
                .when()
                .get()
                .thenReturn();
    }

    @Then("the inventory status code should be {int}")
    public void iShouldGetAResponseCode(int expectedStatus) {
        MatcherAssert.assertThat(response.statusCode(), Matchers.is(expectedStatus));
    }

    @When("I send a POST request to the store inventory")
    public void iSendAPostRequestToTheStoreInventory() {
        response = request()
                .when()
                .post()
                .thenReturn();
    }

    @When("I request the store inventory with the extra path {string}")
    public void iRequestTheStoreInventoryWithTheExtraPath(String extraPath) {
        response = request()
                .when()
                .get("/" + extraPath)
                .thenReturn();
    }

    @Then("the response content type should be JSON")
    public void theResponseContentTypeShouldBeJson() {
        MatcherAssert.assertThat(response.contentType(), Matchers.containsString("application/json"));
    }

    @Then("the inventory should contain at least one status")
    public void theInventoryShouldContainAtLeastOneStatus() {
        Map<String, Object> inventory = response.jsonPath().getMap("$");
        MatcherAssert.assertThat(inventory.size(), Matchers.greaterThan(0));
    }

    @Then("every inventory quantity should be a number")
    public void everyInventoryQuantityShouldBeANumber() {
        Map<String, Object> inventory = response.jsonPath().getMap("$");
        for (Object quantity : inventory.values()) {
            MatcherAssert.assertThat(quantity, Matchers.instanceOf(Number.class));
        }
    }

    @Then("no inventory quantity should be negative")
    public void noInventoryQuantityShouldBeNegative() {
        Map<String, Object> inventory = response.jsonPath().getMap("$");
        for (Object quantity : inventory.values()) {
            MatcherAssert.assertThat(((Number) quantity).doubleValue(), Matchers.greaterThanOrEqualTo(0.0));
        }
    }
}