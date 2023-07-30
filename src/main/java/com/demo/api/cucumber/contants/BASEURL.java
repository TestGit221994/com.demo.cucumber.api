package com.demo.api.cucumber.contants;

public enum BASEURL {

    FakeRESTApi ("https://fakerestapi.azurewebsites.net/"),
    ;

    private final String baseURL;

    BASEURL(String url) {
        this.baseURL = url;
    }

    public String getBaseURL() {
        return this.baseURL;
    }
}
