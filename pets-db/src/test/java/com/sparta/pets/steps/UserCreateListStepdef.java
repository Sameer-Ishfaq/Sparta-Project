package com.sparta.pets.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.restassured.http.ContentType;

import java.util.Arrays;


public class UserCreateListStepdef {

    private final UserContext context;

    public UserCreateListStepdef(UserContext context) {
        this.context = context;
    }

    // ---------- @Happy ----------

    // {word} is a comma separated list of usernames, e.g. user_a,user_b
    @Given("I Want to Create a List of Users with input Array {word}")
    public void iWantToCreateAListOfUsersWithInputArray(String list) {
        context.getUsernames().addAll(Arrays.asList(list.split(",")));
    }

    @And("^I post to /?user/createdWithList/.+$")
    public void iPostToCreatedWithList() {
        context.setResponse(context.request()
                .contentType(ContentType.JSON)
                .body(context.getUsers())
                .when()
                .post("/user/createWithList"));
    }

    // ---------- @Sad ----------

    @Given("I want to create a List of users with input array {word}")
    public void iWantToCreateAListOfUsersWithInvalidInputArray(String list) {
        context.getUsernames().addAll(Arrays.asList(list.split(",")));
    }
}
