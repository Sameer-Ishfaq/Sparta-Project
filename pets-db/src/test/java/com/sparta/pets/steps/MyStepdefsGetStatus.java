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

public class MyStepdefsGetStatus {
    private String petStatus;
    Response resp;
    GetPetResponse presp;
    private static PetsAPIConfig config;
    private String endpoint;

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
}
