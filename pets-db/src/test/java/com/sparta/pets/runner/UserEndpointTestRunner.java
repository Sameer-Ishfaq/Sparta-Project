package com.sparta.pets.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/UsersEndpoint",
        glue = "com.sparta.pets.steps",
        plugin = {
                "pretty",
                "html:target/cucumber-reports/report.html",
                "json:target/cucumber-reports/report.json"
        },
        tags = "not @ignore",
        publish = false
)
public class UserEndpointTestRunner {
}
