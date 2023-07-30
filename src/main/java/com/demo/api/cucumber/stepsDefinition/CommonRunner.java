package com.demo.api.cucumber.stepsDefinition;


import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = {"src/test/resources/FeatureFiles/"}, glue = {
        "com/demo/api/cucumber/stepsDefinition"}, monochrome = true,tags = "@Demo",dryRun = false, plugin = {"io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
})

public class CommonRunner extends AbstractTestNGCucumberTests {

}
