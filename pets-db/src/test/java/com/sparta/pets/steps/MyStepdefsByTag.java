package com.sparta.pets.steps;

import com.sparta.pets.pojo.GetPet.GetPetResponse;
import com.sparta.pets.util.PetsAPIConfig;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class MyStepdefsByTag {
    private String petStatus;
    Response resp;
    GetPetResponse presp;
    private static PetsAPIConfig config;
    private String endpoint;

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
