package com.demo.api.cucumber.contants;

public enum APIHEADERS {

    headerContentType ("application/json; charset=utf-8; v=1.0"),
    ;

    private final String header;

    APIHEADERS(String header) {
        this.header = header;
    }

    public String getHeader() {
        return this.header;
    }
}
