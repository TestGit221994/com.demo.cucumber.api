package com.demo.api.cucumber.utils;

import com.demo.api.cucumber.contants.APIBODY;
import com.demo.api.cucumber.contants.FILELOCATION;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Files;

import java.io.File;

public class ApiResponseHelper {


    public static ObjectMapper objectMapper;
    public static void createJSONFileAndStore(APIBODY apiBody, String response, String fileName){
       try{
        FILELOCATION fileLocation = (apiBody.getTypeOfAPIBody().equals("apiRequestBody")) ? fileLocation = FILELOCATION.apiRequestBodyLocation : FILELOCATION.apiResponseBodyLocation;

        File targetFile = new File(fileLocation.getFileLocation()+fileName+".json");

        byte[] responseAsByteArray = response.getBytes();

        Files.write(responseAsByteArray, targetFile);
        }
        catch (Exception ae) {

        }
    }

    public static String convertAPIRequestBodyObjectToString(Object object){
        String requestBody="";
        try {
            requestBody=getObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(object);
        }catch (Exception ae){

        }return requestBody;
    }

    private static ObjectMapper getObjectMapper(){
        if(objectMapper==null){
            objectMapper = new ObjectMapper();
            return objectMapper;
        }return objectMapper;
    }









}
