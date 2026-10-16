package com.sparta.pets.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;

import java.util.Map;

public class GetInventorySteps {

    // The address of the API. Confirm this with your team (see the note at the end)
    private static final String BASE_URI = "https://petstore3.swagger.io/api/v3";

    // These two carry information from one step to the next
    private String basePath;
    private Response response;

    // Starts every request: base address + base path
    private RequestSpecification request() {
        return RestAssured.given()
                .baseUri(BASE_URI)
                .basePath(basePath);
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
    @Then("I should get a {int} response code")
    public void iShouldGetAResponseCode(int expectedStatus) {
        MatcherAssert.assertThat(response.statusCode(), Matchers.is(expectedStatus));
    }

    // "When I send a POST request to the store inventory"
    @When("I send a POST request to the store inventory")
    public void iSendAPostRequestToTheStoreInventory() {
        response = request()
                .when()
                .post()
                .thenReturn();
    }

    // "When I request the store inventory with the extra path "abc""
    @When("I request the store inventory with the extra path {string}")
    public void iRequestTheStoreInventoryWithTheExtraPath(String extraPath) {
        response = request()
                .when()
                .get("/" + extraPath)
                .thenReturn();
    }

    // "And the response content type should be JSON"
    @Then("the response content type should be JSON")
    public void theResponseContentTypeShouldBeJson() {
        MatcherAssert.assertThat(response.contentType(), Matchers.containsString("application/json"));
    }

    // "And the inventory should contain at least one status"
    @Then("the inventory should contain at least one status")
    public void theInventoryShouldContainAtLeastOneStatus() {
        Map<String, Object> inventory = response.jsonPath().getMap("$");
        MatcherAssert.assertThat(inventory.size(), Matchers.greaterThan(0));
    }

    // "And every inventory quantity should be a number"
    @Then("every inventory quantity should be a number")
    public void everyInventoryQuantityShouldBeANumber() {
        Map<String, Object> inventory = response.jsonPath().getMap("$");
        for (Object quantity : inventory.values()) {
            MatcherAssert.assertThat(quantity, Matchers.instanceOf(Number.class));
        }
    }

    // "And no inventory quantity should be negative"
    @Then("no inventory quantity should be negative")
    public void noInventoryQuantityShouldBeNegative() {
        Map<String, Object> inventory = response.jsonPath().getMap("$");
        for (Object quantity : inventory.values()) {
            MatcherAssert.assertThat(((Number) quantity).doubleValue(), Matchers.greaterThanOrEqualTo(0.0));
        }
    }

}