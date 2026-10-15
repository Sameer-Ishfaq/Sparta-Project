package com.sparta.pets.steps;

import com.sparta.pets.pojo.GetPet.GetPetResponse;
import com.sparta.pets.util.PetsAPIConfig;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;

public class PetStepdefs
{
    Response resp;
    GetPetResponse presp;
    private static PetsAPIConfig config;
    private String endpoint;
    private int id;

    @BeforeAll
    public static void setup()
    {
        config = new PetsAPIConfig();
        config.LoadFromFile();
    }
    @Given("I am searching for a pet using a valid ID - {int}")
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

    @Given("I am searching for a pet using an invalid ID - {int}")
    public void iAmSearchingForAPetUsingAnInvalidId(int id)
    {
        this.id = id;
    }
}