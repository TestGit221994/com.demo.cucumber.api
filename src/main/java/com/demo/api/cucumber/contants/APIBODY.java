package com.demo.api.cucumber.contants;

public enum APIBODY {

    apiRequestBody ("apiRequestBody"),
    apiResponseBody ("apiResponseBody"),
    ;

    private final String typeOfAPIBody;

    APIBODY(String APIBody) {
        this.typeOfAPIBody = APIBody;
    }

    public String getTypeOfAPIBody() {
        return this.typeOfAPIBody;
    }
}
