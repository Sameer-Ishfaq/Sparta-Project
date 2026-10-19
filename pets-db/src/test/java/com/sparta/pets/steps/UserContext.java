package com.sparta.pets.steps;

import com.sparta.pets.util.PetsAPIConfig;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class UserContext {

    private static final String BASE_URI = loadBaseUri();

    private final List<String> usernames = new ArrayList<>();
    private final List<Map<String, Object>> users = new ArrayList<>();
    private Response response;

    private static String loadBaseUri() {
        PetsAPIConfig config = new PetsAPIConfig();
        config.LoadFromFile();
        return config.GetProperty("docker.uri");
    }

    public RequestSpecification request() {
        return RestAssured.given()
                .baseUri(BASE_URI)
                .accept(ContentType.JSON);
    }

    public List<String> getUsernames() {
        return usernames;
    }

    public List<Map<String, Object>> getUsers() {
        return users;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }
}
