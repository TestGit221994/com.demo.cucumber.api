package com.demo.api.cucumber.utils;
import com.demo.api.cucumber.contants.APIBODY;
import groovy.json.JsonException;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.SpecificationQuerier;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.simple.JSONObject;


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.*;
import static org.slf4j.LoggerFactory.getLogger;

public class ApiRequestHelper {

    /*
     * Execute and get response on the basic of request method type
     * if logsDetailsMode= true from config file, Then it will print response details
     */

    private static Logger logger;
    public static Response apiResponse = null;
    public static QueryableRequestSpecification queryableRequestSpecification;






    public static Response executeAndGetResponse(String testcaseName,String fullURL, String methodType, Map<String, String> apiHeaders, String jsonStringBody) {
        return executeAndGetResponse(testcaseName,fullURL, methodType, null, apiHeaders, jsonStringBody, true);
    }

    //Get Value from json
    public static String getValueFromJson(Response response, String nodePath) {
        String value = null;
        try {
            //value = response.jsonPath().getString(nodePath);
        } catch (Exception ae) {
            //logger.info("Exception while getValueFromJson : ", ae, false);
        }
        return value;
    }

    public static Map<String, Object> getValueInHashMapFromJson(Response response, String nodePath) {
        Map<String, Object> valueMap = null;
        try {
            //valueMap = response.jsonPath().getMap(nodePath);
        } catch (Exception ae) {
           // logger.info("Exception while getValueFromJson : ", ae, false);
        }
        return valueMap;
    }

    public static List<String> getListValueFromJson(Response response, String nodePath) {
        List<String> listValue = null;
        try {
            //listValue = response.jsonPath().getList(nodePath);
        } catch (Exception ae) {
            //logger.info("Exception while getValueFromJson : ", ae, false);
        }
        return listValue;
    }

    public static Response executeAndGetResponse(String testCaseName,String fullURL, String methodType, Map<String, String> apiParameters, Map<String, String> apiHeaders, String jsonBody, Boolean parameterIncludedBody) {
        Boolean disableCharSetFlag = false;
        Integer responseCode;
        logger = LoggerUtil.getLogger();

        //logger.info(" API Request Full URL : " +fullURL);
        //logger.info(" API Request Method Type : " +methodType);

        //remove the query params from full url
        String[] cmd = fullURL.split("\\?");
        String requestURL = cmd[0];

        //logger.info(" API Request URL after remove Query params : " + requestURL);
        //logger.info(" API Request apiParameters : " +apiParameters);
        //logger.info(" API Request Headers : " + apiHeaders);

        if (parameterIncludedBody) {
             logger.info(" Request Body is : " + jsonBody);
            }
       if (cmd.length > 1) {
                logger.info(" Request Parameters : " + Arrays.toString(cmd[1].split("&")));
            }

        // Prepare Request
        RequestSpecification requestSpecification = RestAssured.given();

        if (apiHeaders != null && apiHeaders.size() > 0) {
            // Set request headers
            for (Map.Entry<String, String> headers : apiHeaders.entrySet()) {
                //logger.info(" API Header Name : " +headers.getKey()+" Header Value : " +headers.getValue());
                requestSpecification.header(headers.getKey(), headers.getValue());
                if (headers.getValue().contains("application/fhir+json") || headers.getValue().contains("application/fhir+xml")) {
                    disableCharSetFlag = true;
                }
            }
        }

        if (jsonBody != null && (jsonBody.toString().endsWith(".xlsx") || jsonBody.toString().endsWith(".xls"))) {
            requestSpecification = requestSpecification.multiPart("file_storage", new File(jsonBody.toString()));
        } else if (jsonBody != null) {
            //logger.info(" API Request Body : " +jsonBody);
            ApiResponseHelper.createJSONFileAndStore(APIBODY.apiRequestBody,jsonBody,"apiRequestBodyOf_"+testCaseName.trim());
            requestSpecification = requestSpecification.body(jsonBody);
        }

        //logger.info(" API Request parameterIncludedBody : " +parameterIncludedBody);

        if (apiParameters != null) {
            if (apiParameters != null && apiParameters.size() > 0) {
                for (Map.Entry<String, String> parameters : apiParameters.entrySet()) {
                   // logger.info(" API Request Parameter Name : " +parameters.getKey()+" Parameter Value : " + parameters.getValue());
                    requestSpecification = requestSpecification.queryParam(parameters.getKey(), parameters.getValue());
                }
            }
        } else if (cmd.length > 1) {
            String parameters[] = cmd[1].split("&");
            for (int i = 0; i < parameters.length; i++) {
                requestSpecification = requestSpecification.queryParam(parameters[i].split("=")[0], parameters[i].split("=")[1]);
            }
        }

        //logger.info(" API Request Method Type : " +methodType);
        requestSpecification = requestSpecification.when();

        Response response = null;
        switch (methodType.toLowerCase()) {

            case "get":
                response = requestSpecification.get(requestURL);
                break;
            case "post":
                response = requestSpecification.post(requestURL);
                break;
            case "delete":
                response = requestSpecification.delete(requestURL);
                break;
            case "put":
                response = requestSpecification.put(requestURL);
                break;
            case "patch":
                response = requestSpecification.patch(requestURL);
                break;
        }

        queryableRequestSpecification = SpecificationQuerier.query(requestSpecification);
        logger.info(queryableRequestSpecification.getCookies());
        logger.info(queryableRequestSpecification.getBaseUri());
        logger.info(queryableRequestSpecification.getBasePath());
        logger.info(queryableRequestSpecification.getBody());

        if (true) {
            response = response.then().log().all().extract().response();
            logger.info(" API Response Status Code : " + response.getStatusCode());
            logger.info(" API Response Status Line : " + response.getStatusLine());
            logger.info(" API Response Cookie : " + response.getCookies());
            logger.info(" API Response Time : " + response.getTime());
            logger.info(" API Headers : " + response.getHeaders());
            ApiResponseHelper.createJSONFileAndStore(APIBODY.apiResponseBody,response.prettyPrint(),"apiResponseBodyOf_"+testCaseName.trim());
            logger.info(" API Response Body : " + response.getBody().prettyPrint());
        }
        return response;
    }


    public static org.json.JSONObject parseResponseAsJson(Response response) {

        String responseAsString = response.asString();
        org.json.JSONObject jsonObject = null;

        switch (response.getStatusCode()) {

            case 504:
                //Browser.wait(testConfig,90);
                break;
            case 505:
                //
                // Browser.wait(testConfig,90);
                break;
            default:
                responseAsString = response.asString();
                break;
        }

        try {
            jsonObject = new org.json.JSONObject(responseAsString);
            return jsonObject;
        } catch (Exception ae) {
            //testConfig.logException(ae);
        }
        return null;
    }

    public static String createJsonParameters(HashMap<String, String> parameters) {

        JSONObject jasonPostParameter = new JSONObject();
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            jasonPostParameter.put(entry.getKey(), entry.getValue());
        }
        return jasonPostParameter.toString();
    }

    public static JSONObject parseJSONFileInJSONObject(String fileLocationURL) {
        JSONObject jsonObject = null;
        InputStream inputStream;
        try {
            inputStream = new FileInputStream(fileLocationURL);
            //JsonToken jsonToken=new JsonToken(inputStream);
        } catch (Exception ae) {

        }
        return jsonObject;
    }

    public static Response executeAndGetResponse(String testCaseName,String fullURL, String methodType, Map<String, String> apiParameters, Map<String, String> apiHeaders) {
        return executeAndGetResponse(testCaseName,fullURL, methodType, apiParameters, apiHeaders, null, false);
    }

    public static void validateSchema(String resourceName, String schemaFileName, Response response) {

        /*
        com.fasterxml.jackson.databind.ObjectMapper objectMapper = new ObjectMapper();
        JsonSchemaFactory schemaFactory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V201909);

        try {
            InputStream schemaStream = new FileInputStream(new File(schemaFileName));
            InputStream jsonStream = new ByteArrayInputStream(response.asString().getBytes());

            JsonNode jsonNode = objectMapper.readTree(jsonStream);

            JsonSchema schema = schemaFactory.getSchema(schemaStream);

            Set<ValidationMessage> validationResult = schema.validate(jsonNode);

            if (validationResult.isEmpty()) {
                testConfig.logPass(" Schema validation Pass for " + resourceName);
            } else {
                validationResult.forEach(vm -> testConfig.logFail(" Schema validation Fail " + vm.getMessage()));
            }
        } catch (Exception ae) {
            testConfig.logException(" Schema validation exception", ae, false);
        }

         */
    }

    public static JSONArray parseRequestAsJSONArray(Response response) {
        String reponseAsString = response.asString();
        org.json.JSONArray jsonArray = null;

        switch (response.getStatusCode()) {

            case 504:
                //Browser.wait(testConfig,90);
                break;
            case 505:
                //Browser.wait(testConfig,30);
                break;
            default:
                reponseAsString = response.asString();
                break;
        }

        try {
            jsonArray = new JSONArray(reponseAsString);
            return jsonArray;
        } catch (JsonException | JSONException ae) {
            // testConfig.logException(ae);
        }
        return null;
    }

    public static Response executeAndGetResponse(String testcaseName,String fullURL, String methodType, Map<String, String> apiHeaders, Map<String, String> mapKeyParameter, String apiInBody) {
        return executeAndGetResponse(testcaseName,fullURL, methodType, mapKeyParameter, apiHeaders, apiInBody, true);
    }

    public static String createJsonParameter(Map<String, String> parameters) {
        LinkedHashMap<String, String> hasMap = new LinkedHashMap<String, String>(parameters);
        org.json.JSONObject jsonPostParameters = new org.json.JSONObject(hasMap);

        try {
            Field changeMap = jsonPostParameters.getClass().getDeclaredField("map");
            changeMap.setAccessible(true);
            changeMap.set(jsonPostParameters, new LinkedHashMap<>());
            changeMap.setAccessible(false);
        } catch (IllegalAccessException | NoSuchFieldException ae) {
            //testConfig.logException(ae);
        }

        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            try {
                jsonPostParameters.put(key, value);
            } catch (JSONException ae) {
                //testConfig.logException(ae);
            }
        }
        return jsonPostParameters.toString();
    }

    public static Response executeAndGetResponseForMultiPartFormParams(String fullURL, String methodType, Map<String, Object> multiPartParams, Map<String, String> apiHeaders) {
        int responseCode;
        String requestURL = fullURL;
        RequestSpecification requestSpecification = RestAssured.given();
        /*
        if(testConfig.getRunTimeProperty("Disable_Encoding")!=null && testConfig.getRunTimeProperty("Disable_Encoding").equalsIgnoreCase("true")){
            requestSpecification.urlEncodingEnabled(false);
        }

         */
        if (apiHeaders != null && apiHeaders.size() > 0) {
            requestSpecification = requestSpecification.headers(apiHeaders);
        }
        for (Map.Entry<String, Object> entry : multiPartParams.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            requestSpecification = requestSpecification.multiPart(key, value);
        }

        if (true) {
            requestSpecification = requestSpecification.log().all();
        }

        requestSpecification = requestSpecification.when();

        Response response = null;
        switch (methodType.toLowerCase()) {
            case "post":
                response = requestSpecification.post(requestURL);
                break;
            case "put":
                response = requestSpecification.put(requestURL);
                break;
            case "patch":
                response = requestSpecification.patch(requestURL);
                break;
        }

        if (true) {
            response = response.then().log().all().extract().response();

            logger.info(" API Response for " + requestURL + " : -" + response.asString());
            logger.info(" Response Cookie " + response.getCookies());
            logger.info(" Response Time " + response.getTime());
            logger.info(" Response Headers " + response.getHeaders());


        } else {
            logger.info(" Request URL " + requestURL);
            logger.info(" Response Headers " + apiHeaders);
            responseCode = response.getStatusCode();
            if (responseCode != 200 && responseCode != 201) {
                logger.info(" API response for " + requestURL + " :- " + response.asString());
            }
        }
        logger.info(" Response code : " + response.getStatusCode());
        //Browser.wait(testConfig,5);
        return response;
    }
}
