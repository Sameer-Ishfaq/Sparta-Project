package com.sparta.pets.steps;

import com.sparta.pets.util.PetsAPIConfig;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;


public class UserCreateListStepdef {

    private static PetsAPIConfig config;

    // TODO: store the responses from the one-off API calls here
    private static Response createListResponse;

    private String list;

    @BeforeAll
    public static void setup() {
        config = new PetsAPIConfig();
        config.LoadFromFile();
        RestAssured.baseURI = config.GetProperty("pets.uri");
        // TODO: call the API once here and store the response(s)
    }

    // ---------- @Happy ----------

    @Given("I Want to Create a List of Users with input Array {word}")
    public void iWantToCreateAListOfUsersWithInputArray(String list) {
        throw new PendingException();
    }

    // Optional leading slash because the sad scenario omits it
    @And("^I post to /?user/createdWithList/(.+)$")
    public void iPostToCreatedWithList(String list) {
        throw new PendingException();
    }

    // ---------- @Sad ----------

    @Given("I want to create a List of users with input array")
    public void iWantToCreateAListOfUsersWithInputArray() {
        throw new PendingException();
    }
}

