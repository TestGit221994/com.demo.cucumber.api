package com.demo.api.cucumber.contants;

public enum FILELOCATION {

    apiRequestBodyLocation (System.getProperty("user.dir")+"/src/test/resources/RequestBody/"),
    apiResponseBodyLocation (System.getProperty("user.dir")+"/src/test/resources/ResponseBody/"),
    ;

    private final String fileLocation;

    FILELOCATION(String location) {
        this.fileLocation = location;
    }

    public String getFileLocation() {
        return this.fileLocation;
    }
}
