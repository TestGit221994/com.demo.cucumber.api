package com.demo.api.cucumber.utils;

public class WaitUtil {

    public static void pause(int seconds){
        try {
            Thread.sleep(seconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
