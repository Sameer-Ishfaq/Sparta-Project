package com.sparta.pets.steps;

import com.sparta.pets.pojo.GetPet.GetPetResponse;
import com.sparta.pets.util.PetsAPIConfig;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.And;
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

import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class PetStepdefs
{
    private enum PostRequestType
    {
        ADD_PET,
        ADD_INVALID_PET,
        UPLOAD_IMAGE,
        UPDATE_PET_WITH_POST
    }

    private static final AtomicLong NEXT_PET_ID = new AtomicLong(System.currentTimeMillis());
    private static final byte[] TEST_IMAGE = Base64.getDecoder().decode(
            "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mNk+A8AAQUBAScY42YAAAAASUVORK5CYII=");

    Response resp;
    GetPetResponse presp;
    private static PetsAPIConfig config;
    private String endpoint;
    private int id;
    private long postPetId;
    private String postPetName;
    private Map<String, Object> postRequestBody;
    private PostRequestType postRequestType;

    private String petStatus;

    @BeforeAll
    public static void setup()
    {
        config = new PetsAPIConfig();
        config.LoadFromFile();
    }
    @Given("I am searching for a pet using a valid ID {int}")
    public void iAmSearchingForAPetUsingAValidId(int id)
    {
        this.id = id;
    }

    @And("the endpoint is set to {string}")
    public void theEndpointIsSetTo(String endpoint)
    {
        this.endpoint = endpoint;
    }

    @When("I send a GET request to the endpoint using my ID")
    public void iSendAGetRequestToTheEndpointUsingMyId()
    {
        var uri = config.GetProperty("pets.uri");
        resp = RestAssured.given().baseUri(uri).basePath(endpoint + "/" + id).accept("application/json").get().thenReturn();
    }

    @Then("status code {int} should be returned")
    public void statusCodeShouldBeReturned(int statusCode)
    {
        MatcherAssert.assertThat(resp.statusCode(), Matchers.is(statusCode));
    }

    @And("the returned name should be {string}")
    public void theReturnedNameShouldBe(String name)
    {

        presp = resp.as(GetPetResponse.class);
        MatcherAssert.assertThat(presp.getName(), Matchers.is(name));
    }
    @Given("I am searching for a pet using an invalid ID {int}")
    public void iAmSearchingForAPetUsingAnInvalidId(int id)
    {
        this.id = id;
    }

    @Given("i add a pet with the valid information")
    public void iAddAPetWithTheValidInformation()
    {
        postPetId = NEXT_PET_ID.incrementAndGet();
        postPetName = "Cucumber Test Pet";
        postRequestBody = petBody(postPetId, postPetName, "available");
        postRequestType = PostRequestType.ADD_PET;
    }

    @Given("I add a pet with the invalid information in the correct format")
    public void iAddAPetWithTheInvalidInformationInTheCorrectFormat()
    {
        postPetId = NEXT_PET_ID.incrementAndGet();
        postRequestBody = petBody(postPetId, "", "not-a-valid-status");
        postRequestType = PostRequestType.ADD_INVALID_PET;
    }

    @Given("i upload a valid petID information with an image")
    public void iUploadAValidPetIdInformationWithAnImage()
    {
        postPetId = NEXT_PET_ID.incrementAndGet();
        createPet(postPetId, "Pet With Image", "available");
        postRequestType = PostRequestType.UPLOAD_IMAGE;
    }

    @Given("i update a pets information")
    public void iUpdateAPetsInformation()
    {
        postPetId = NEXT_PET_ID.incrementAndGet();
        createPet(postPetId, "Pet Before Update", "available");
        postRequestBody = petBody(postPetId, "Pet Updated With Post", "sold");
        postRequestType = PostRequestType.UPDATE_PET_WITH_POST;
    }

    @When("^[iI] send a POST request$")
    public void iSendAPostRequest()
    {
        if (postRequestType == PostRequestType.UPLOAD_IMAGE)
        {
            resp = RestAssured.given()
                    .baseUri(baseUri())
                    .accept(ContentType.JSON)
                    .pathParam("petId", postPetId)
                    .multiPart("file", "pet-image.png", TEST_IMAGE, "image/png")
                    .multiPart("additionalMetadata", "Image uploaded by Cucumber test")
                    .when()
                    .post("/pet/{petId}/uploadImage");
            return;
        }

        resp = postPet(postRequestBody);
    }

    @When("^i send a POST request to /pet$")
    public void iSendAPostRequestToPet()
    {
        if (postRequestType != PostRequestType.UPDATE_PET_WITH_POST)
        {
            throw new IllegalStateException("No pet update has been prepared for this scenario");
        }

        resp = postPet(postRequestBody);
    }

    @Then("the response code should be {int}")
    public void theResponseCodeShouldBe(int expectedStatusCode)
    {
        MatcherAssert.assertThat(resp.statusCode(), Matchers.is(expectedStatusCode));
    }

    @Then("a new pet should be added returning status code {int}")
    public void aNewPetShouldBeAddedReturningStatusCode(int expectedStatusCode)
    {
        MatcherAssert.assertThat(resp.statusCode(), Matchers.is(expectedStatusCode));

        Response getResponse = getPet(postPetId);
        MatcherAssert.assertThat(getResponse.statusCode(), Matchers.is(200));
        MatcherAssert.assertThat(getResponse.jsonPath().getString("name"), Matchers.is(postPetName));
    }

    @Then("a new pet should be added returning status {int} telling me there is a validation error")
    public void aNewPetShouldBeAddedReturningAValidationError(int expectedStatusCode)
    {
        MatcherAssert.assertThat(resp.statusCode(), Matchers.is(expectedStatusCode));

        String responseBody = resp.asString().toLowerCase();
        MatcherAssert.assertThat(
                responseBody,
                Matchers.anyOf(
                        Matchers.containsString("validation"),
                        Matchers.containsString("error")));
    }

    private void createPet(long petId, String name, String status)
    {
        Response createResponse = postPet(petBody(petId, name, status));
        MatcherAssert.assertThat(
                "The pet required by the scenario could not be created",
                createResponse.statusCode(),
                Matchers.is(200));
    }

    private Response postPet(Map<String, Object> requestBody)
    {
        return RestAssured.given()
                .baseUri(baseUri())
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/pet");
    }

    private Response getPet(long petId)
    {
        return RestAssured.given()
                .baseUri(baseUri())
                .accept(ContentType.JSON)
                .pathParam("petId", petId)
                .when()
                .get("/pet/{petId}");
    }

    private Map<String, Object> petBody(long petId, String name, String status)
    {
        return Map.of(
                "id", petId,
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

    @And("the status is set to {string}")
    public void theStatusIsSetTo(String status) {
        this.petStatus = status;
    }

    @When("I send a get request to the endpoint using my status")
    public void iSendAGETRequestToFindPetsByStatus() {
        var uri = config.GetProperty("pets.uri");

        resp = RestAssured.given()
                .baseUri(uri)
                .basePath(endpoint)
                .queryParam("status", petStatus)
                .accept("application/json")
                .get()
                .thenReturn();
    }

    @When("I send a GET request to the endpoint using my tags {string}")
    public void iSendAGETRequestUsingMyTags(String tags) {
        var uri = config.GetProperty("pets.uri");

        resp = RestAssured.given()
                .baseUri(uri)
                .basePath(endpoint)
                .queryParam("tags", tags.split("\\s*,\\s*"))
                .accept("application/json")
                .get()
                .thenReturn();
    }
}
