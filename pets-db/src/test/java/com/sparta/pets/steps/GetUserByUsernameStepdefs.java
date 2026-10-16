package com.sparta.pets.steps;

import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class GetUserByUsernameStepdefs {
    @Given("I am looking for a username that exists {string}")
    public void iAmLookingForAUsernameThatExists(String arg0) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("I enter a GET requests to \\/users\\/\\{username}")
    public void iEnterAGETRequestsToUsersUsername() {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @And("I should get a http code {int}")
    public void iShouldGetAHttpCode(int arg0) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("I should see details about the username: first name, last name, id, email, password, phone, user status")
    public void iShouldSeeDetailsAboutTheUsernameFirstNameLastNameIdEmailPasswordPhoneUserStatus() {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Given("No user exists with the name <username>")
    public void noUserExistsWithTheNameUsername() {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("I send a GET request to \\/users\\/\\{username}")
    public void iSendAGETRequestToUsersUsername() {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("I should a http code {int}")
    public void iShouldAHttpCode(int arg0) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}
