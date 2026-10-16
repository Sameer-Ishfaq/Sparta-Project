package com.sparta.pets.steps;

import com.sparta.pets.pojo.GetPet.GetPetResponse;
import com.sparta.pets.util.PetsAPIConfig;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.json.simple.JSONObject;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class UpdateDeletePetStepdefs
{
    private static final String UPDATED_NAME = "Buddy Updated";
    private static final String UPDATED_STATUS = "sold";

    private static PetsAPIConfig config;
    private GetPetResponse presp;

    private String endpoint;
    private int petId;
    private Response response;

    @BeforeAll
    public static void setup()
    {
        config = new PetsAPIConfig();
        config.LoadFromFile();
    }

    @Given("I am using the pet api with path set to {string}")
    public void iAmUsingThePetApiWithPathSetTo(String path)
    {
        endpoint = path.startsWith("/") ? path : "/" + path;
    }

    @Given("pet ID {int} exists")
    public void petIdExists(int id)
    {
        petId = id;
        createPet(petId, "Test Pet", "available");
    }

    @Given("a pet exists in the database with a valid ID")
    public void aPetExistsInTheDatabaseWithAValidId()
    {
        petId = 9999;
        createPet(petId, "Pet To Delete", "available");
    }

    @Given("a pet ID - {int} - does not exist in the database")
    public void aPetIdDoesNotExistInTheDatabase(int id)
    {
        petId = id;

        Response lookupResponse = RestAssured.given()
                .baseUri(config.GetProperty("pets.uri"))
                .basePath(endpoint)
                .pathParam("petId", petId)
                .when()
                .get("/{petId}");

        MatcherAssert.assertThat(
                "The generated test pet ID unexpectedly already exists",
                lookupResponse.statusCode(),
                Matchers.is(404));
    }

    @When("I send a PUT request to update pet ID {int} with valid details")
    public void iSendAPutRequestToUpdatePetIdWithValidDetails(int id)
    {
        petId = id;
        response = RestAssured.given()
                .baseUri(baseUri())
                .basePath(endpoint)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(petBody(petId, UPDATED_NAME, UPDATED_STATUS))
                .when()
                .put();
    }

    @When("I send a PUT request with an invalid pet ID")
    public void iSendAPutRequestWithAnInvalidPetId()
    {
        Map<String, Object> invalidPet = Map.of(
                "id", "not-a-valid-id",
                "name", UPDATED_NAME,
                "photoUrls", List.of(),
                "status", UPDATED_STATUS);

        response = RestAssured.given()
                .baseUri(baseUri())
                .basePath(endpoint)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(invalidPet)
                .when()
                .put();
    }

    @When("^I send a DELETE request to /pet/\\{petId}$")
    public void iSendADeleteRequestToPetPetId()
    {
        response = RestAssured.given()
                .baseUri(baseUri())
                .basePath(endpoint)
                .pathParam("petId", petId)
                .when()
                .delete("/{petId}");
    }

    @Then("^I should (?:get|receive) a (\\d+) response code$")
    public void iShouldReceiveAResponseCode(int expectedStatusCode)
    {
        MatcherAssert.assertThat(response.statusCode(), Matchers.is(expectedStatusCode));
    }

    @And("pet ID {long} should contain the updated details")
    public void petIdShouldContainTheUpdatedDetails(long id)
    {
        Response getResponse = getPet(id);

        MatcherAssert.assertThat(getResponse.statusCode(), Matchers.is(200));
        MatcherAssert.assertThat(getResponse.jsonPath().getString("name"), Matchers.is(UPDATED_NAME));
        MatcherAssert.assertThat(getResponse.jsonPath().getString("status"), Matchers.is(UPDATED_STATUS));
    }

    @And("the pet should no longer exist in the database")
    public void thePetShouldNoLongerExistInTheDatabase()
    {
        MatcherAssert.assertThat(getPet(petId).statusCode(), Matchers.is(404));
    }

    private void createPet(long id, String name, String status)
    {
        Response createResponse = RestAssured.given()
                .baseUri(baseUri())
                .basePath(endpoint)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(petBody(id, name, status))
                .when()
                .post();

        MatcherAssert.assertThat(
                "The test pet could not be created",
                createResponse.statusCode(),
                Matchers.is(200));
    }

    private Response getPet(long id)
    {
        return RestAssured.given()
                .baseUri(baseUri())
                .basePath(endpoint)
                .accept(ContentType.JSON)
                .pathParam("petId", id)
                .when()
                .get("/{petId}");
    }

    private Map<String, Object> petBody(long id, String name, String status)
    {
        return Map.of(
                "id", id,
                "category", Map.of("id", 1, "name", "test"),
                "name", name,
                "photoUrls", List.of("https://example.com/pet.jpg"),
                "tags", List.of(Map.of("id", 1, "name", "test")),
                "status", status);
    }

    private String baseUri()
    {
        return config.GetProperty("pets.uri");
    }

    @When("I try to update pet ID {int} with name {string} and status {string}")
    public void iTryToUpdatePetIDWithNameAndStatus(int arg0, String arg1, String arg2)
    {
        JSONObject root = new JSONObject();
        root.put("id", arg0);
        root.put("name", arg1);
        root.put("status", arg2);

        response = RestAssured.given().baseUri(config.GetProperty("pets.uri")).basePath(endpoint).accept(ContentType.JSON).contentType(ContentType.JSON).body(root.toJSONString()).put().thenReturn();
        if (response.statusCode() == 200) presp = response.as(GetPetResponse.class);
    }

    @And("pet ID {int} should have the status {string}")
    public void petIDShouldHaveTheStatus(int arg0, String arg1)
    {
        MatcherAssert.assertThat(presp.getId(), Matchers.is(arg0));
        MatcherAssert.assertThat(presp.getStatus(), Matchers.is(arg1));
    }

    @And("pet ID {int} should have the name {string}")
    public void petIDShouldHaveTheName(int arg0, String arg1) {
        // Write code here that turns the phrase above into concrete actions
        MatcherAssert.assertThat(presp.getId(), Matchers.is(arg0));
        MatcherAssert.assertThat(presp.getName(), Matchers.is(arg1));
    }

    @And("I should get an {string} message")
    public void iShouldGetAnMessage(String arg0)
    {
        MatcherAssert.assertThat(response.jsonPath().getString("message"), Matchers.is(arg0));
    }

    @When("I try to update pet ID {string} with name {string} and status {string}")
    public void iTryToUpdatePetIDWithNameAndStatus(String arg0, String arg1, String arg2) {
        // Write code here that turns the phrase above into concrete actions
        JSONObject root = new JSONObject();
        root.put("id", arg0);
        root.put("name", arg1);
        root.put("status", arg2);

        response = RestAssured.given().baseUri(config.GetProperty("pets.uri")).basePath(endpoint).accept(ContentType.JSON).contentType(ContentType.JSON).body(root.toJSONString()).put().thenReturn();
    }
}
