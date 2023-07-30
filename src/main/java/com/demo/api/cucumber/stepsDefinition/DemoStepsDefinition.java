package com.demo.api.cucumber.stepsDefinition;

import com.demo.api.cucumber.contants.APIHEADERS;
import com.demo.api.cucumber.contants.BASEURL;
import com.demo.api.cucumber.pojo.postapi.CreateActivities;
import com.demo.api.cucumber.utils.ApiRequestHelper;
import com.demo.api.cucumber.utils.ApiResponseHelper;
import com.demo.api.cucumber.utils.LoggerUtil;
import com.demo.api.cucumber.utils.RandomDataGenerateUtil;
import io.cucumber.core.internal.com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.*;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import org.apache.log4j.Logger;
import org.hamcrest.MatcherAssert;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import static org.slf4j.LoggerFactory.getLogger;

public class DemoStepsDefinition {

    private static Logger logger = LoggerUtil.getLogger();
    public static CreateActivities createActivities;
    public static Integer id;
    public static String testCaseName;


    @Given("I want to create test case {string}")
    public void iWantToCreateTestCase(String testName) {
        logger.info(" Service Name : " +testName);
        logger.info(" Thread name : " +Thread.currentThread().getId());
        testCaseName = testName;
    }

    @Then("Verify the status code is {string}")
    public void verifyTheStatusCodeIs(String statusCode) {
        Assert.assertEquals(statusCode,String.valueOf(ApiRequestHelper.apiResponse.getStatusCode()));
    }

    @Then("Validate employee details in response of POST API")
    public void validateEmployeeDetailsInResponseOfPOSTAPI() throws IOException {


    }

    @When("I hit the {string} method of create activities api with {string} end point")
    public void iHitTheMethodOfCreateActivitiesApiWithEndPoint(String requestMethod, String endPoint) throws JsonProcessingException {
        HashMap<String, String> apiHeaders = new HashMap<>();
        APIHEADERS headers = APIHEADERS.headerContentType;
        apiHeaders.put("Content-Type",headers.getHeader());

        createActivities = new CreateActivities();
        createActivities.setId(4);
        createActivities.setTitle(RandomDataGenerateUtil.generateRandomString(5));
        createActivities.setDueDate("2023-03-20T11:39:04.146Z");
        createActivities.setCompleted(true);

        BASEURL baseURL= BASEURL.FakeRESTApi;

        ApiRequestHelper.apiResponse= ApiRequestHelper.executeAndGetResponse(testCaseName,baseURL.getBaseURL()+endPoint.trim().toLowerCase(),
                requestMethod,null,
                apiHeaders, ApiResponseHelper.convertAPIRequestBodyObjectToString(createActivities),true);

    }

    @When("I hit the {string} method of get all activities api with {string} end point")
    public void iHitTheMethodOfGetAllActivitiesApiWithEndPoint(String requestMethod, String endPoint) {
        BASEURL baseURL= BASEURL.FakeRESTApi;

        ApiRequestHelper.apiResponse= ApiRequestHelper.executeAndGetResponse(testCaseName,baseURL.getBaseURL()+endPoint.trim().toLowerCase(),
                requestMethod,null,null,null,false);
    }

    @Then("Validate all activities details in api response")
    public void validateAllActivitiesDetailsInApiResponse() {

        JsonPath jsonPath = JsonPath.from(ApiRequestHelper.apiResponse.getBody().asString());

        Assert.assertEquals(jsonPath.getString("[0].title").trim(),"Activity 1");

    }

    @Then("Validate created activity details in api response")
    public void validateCreatedActivityDetailsInApiResponse() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        CreateActivities actualResponseBody = objectMapper.readValue(ApiRequestHelper.apiResponse.getBody().asString(), CreateActivities.class);
        id = actualResponseBody.getId();

        Assert.assertEquals(actualResponseBody.getId(),createActivities.getId());
        Assert.assertEquals(actualResponseBody.getTitle(),createActivities.getTitle());
        //Assert.assertEquals(actualResponseBody.getDueDate(),createActivities.getDueDate());
        Assert.assertEquals(actualResponseBody.isCompleted(),createActivities.isCompleted());

    }

    @When("I hit the {string} method of activity details api with {string} end point")
    public void iHitTheMethodOfActivityDetailsApiWithEndPoint(String requestMethod, String endPoint) {
        HashMap<String, String> apiHeaders = new HashMap<>();

        BASEURL baseURL= BASEURL.FakeRESTApi;
        id = createActivities.getId();
        String fullURL = baseURL.getBaseURL()+endPoint.trim().toLowerCase()+"/"+String.valueOf(id);

        ApiRequestHelper.apiResponse= ApiRequestHelper.executeAndGetResponse(testCaseName,fullURL,requestMethod,null,apiHeaders,null,true);

    }

    @Then("Validate activity details in api response")
    public void validateActivityDetailsInApiResponse() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        CreateActivities actualResponseBody = objectMapper.readValue(ApiRequestHelper.apiResponse.getBody().asString(), CreateActivities.class);

        Assert.assertEquals(actualResponseBody.getId().toString(),id.toString());
        Assert.assertEquals(actualResponseBody.getTitle(),"Activity 4");
        Assert.assertEquals(String.valueOf(actualResponseBody.isCompleted()),"true");

    }

    @When("I hit the {string} method of update activity api with {string} end point")
    public void iHitTheMethodOfUpdateActivityApiWithEndPoint(String requestMethod, String endPoint) throws JsonProcessingException {
        HashMap<String, String> apiHeaders = new HashMap<>();

        apiHeaders.put("Content-Type","application/json");

        BASEURL baseURL= BASEURL.FakeRESTApi;
        id = 4;
        String fullURL = baseURL.getBaseURL()+endPoint.trim().toLowerCase()+"/"+String.valueOf(id);

        createActivities = new CreateActivities();
        createActivities.setId(4);
        createActivities.setTitle("Activityy");
        createActivities.setDueDate("2023-03-21T17:58:49.5418856+00:00");
        createActivities.setCompleted(true);

        ObjectMapper objectMapper = new ObjectMapper();
        String employeeJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(createActivities);


        ApiRequestHelper.apiResponse= ApiRequestHelper.executeAndGetResponse(testCaseName,fullURL,requestMethod,null,apiHeaders,employeeJson,true);

    }

    @Then("Validate activity update details in response")
    public void validateActivityUpdateDetailsInResponse() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        CreateActivities actualResponseBody = objectMapper.readValue(ApiRequestHelper.apiResponse.getBody().asString(), CreateActivities.class);
        id = actualResponseBody.getId();

        Assert.assertEquals(actualResponseBody.getId(),createActivities.getId());
        Assert.assertEquals(actualResponseBody.getTitle(),createActivities.getTitle());
        //Assert.assertEquals(actualResponseBody.getDueDate(),createActivities.getDueDate());
        Assert.assertEquals(actualResponseBody.isCompleted(),createActivities.isCompleted());

    }

    @When("I hit the {string} method of delete activity api with {string} end point")
    public void iHitTheMethodOfDeleteActivityApiWithEndPoint(String requestMethod, String endPoint) {

        HashMap<String, String> apiHeaders = new HashMap<>();

        BASEURL baseURL= BASEURL.FakeRESTApi;
        id = 4;
        String fullURL = baseURL.getBaseURL()+endPoint.trim().toLowerCase()+"/"+String.valueOf(id);

        ApiRequestHelper.apiResponse= ApiRequestHelper.executeAndGetResponse(testCaseName,fullURL,requestMethod,null,apiHeaders,null,true);

    }

    @Then("Validate delete activity details in response")
    public void validateDeleteActivityDetailsInResponse() {
    }

    @Then("Validate the status code in api response {string}")
    public void validateTheStatusCodeInApiResponse(String statusCode) {
       LoggerUtil.getLogger().info(" Expected API Status Code : " +statusCode +" Actual API Status Code : " +String.valueOf(ApiRequestHelper.apiResponse.getStatusCode()).trim());
       Assert.assertEquals(statusCode,String.valueOf(ApiRequestHelper.apiResponse.getStatusCode()).trim());
    }

    @Then("Validate the content-type header in api response")
    public void validateTheContentTypeHeaderInApiResponse() {
        APIHEADERS contentType = APIHEADERS.headerContentType;

        LoggerUtil.getLogger().info(" Expected API Header : "+contentType.getHeader() +" Actual API Header : " + ApiRequestHelper.apiResponse.getHeaders().getValue("Content-Type").trim());

        Assert.assertEquals(ApiRequestHelper.apiResponse.getHeaders().getValue("Content-Type").trim(), contentType.getHeader());

    }

    @Then("Validate Schema of {string} Request Body")
    public void validateSchemaOfActivitiesGETAPIRequestBody(String schemaName) {

    MatcherAssert.assertThat(ApiRequestHelper.queryableRequestSpecification.getBody().toString(), JsonSchemaValidator.matchesJsonSchema(new File(System.getProperty("user.dir")+"/src/test/resources/Schema/Response/"+schemaName.trim()+".json")));

    }


    @Then("Validate Schema of {string} Activities GET API")
    public void validateSchemaOfSchemaOfResponseBody_Activities_GET_APIActivitiesGETAPI(String schemaName) {

        MatcherAssert.assertThat(ApiRequestHelper.apiResponse.getBody().asString(),JsonSchemaValidator.matchesJsonSchema(new File(System.getProperty("user.dir")+"/src/test/resources/Schema/Request/"+schemaName.trim()+".json")));

    }

    @Then("Validate Schema of {string} Activities POST API")
    public void validateSchemaOfSchemaOfResponseBody_Activities_POST_APIActivitiesPOSTAPI(String schemaName) {
        MatcherAssert.assertThat(ApiRequestHelper.apiResponse.getBody().asString(),JsonSchemaValidator.matchesJsonSchema(new File(System.getProperty("user.dir")+"/src/test/resources/Schema/Response/"+schemaName.trim()+".json")));

    }

    @Then("Validate Schema of {string} Activity details GET API")
    public void validateSchemaOfSchemaOfResponseBody_Activities_POST_APIActivityDetailsGETAPI(String schemaName) {
        MatcherAssert.assertThat(ApiRequestHelper.apiResponse.getBody().asString(),JsonSchemaValidator.matchesJsonSchema(new File(System.getProperty("user.dir")+"/src/test/resources/Schema/Response/"+schemaName.trim()+".json")));

    }

    @Then("Validate Schema of {string} Activities PUT API")
    public void validateSchemaOfSchemaOfRequestBody_Activities_POST_APIActivitiesPUTAPI(String schemaName) {
        MatcherAssert.assertThat(ApiRequestHelper.apiResponse.getBody().asString(),JsonSchemaValidator.matchesJsonSchema(new File(System.getProperty("user.dir")+"/src/test/resources/Schema/Response/"+schemaName.trim()+".json")));

    }

    @Then("Validate Request Schema of {string} Activities PUT API")
    public void validateRequestSchemaOfSchemaOfRequestBody_Activities_POST_APIActivitiesPUTAPI(String schemaName) {
        MatcherAssert.assertThat(ApiRequestHelper.apiResponse.getBody().asString(),JsonSchemaValidator.matchesJsonSchema(new File(System.getProperty("user.dir")+"/src/test/resources/Schema/Response/"+schemaName.trim()+".json")));

    }
}
