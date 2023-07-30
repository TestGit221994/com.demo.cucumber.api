package com.demo.api.cucumber.utils;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;

import java.util.Locale;
import java.util.Random;

public class RandomDataGenerateUtil {

    public static Integer generateRandomIntegerNo(Integer range) {
        return new Random().nextInt(range);
    }

    public static String generateRandomString(Integer length) {
        String text = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++)
            sb.append(text.charAt(new Random().nextInt(text.length())));
        return sb.toString();
    }

    public static String generateRandomNumericString(int length) {
        String textnumber = "0123456789";
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++)
            sb.append(textnumber.charAt(new Random().nextInt(textnumber.length())));
        return sb.toString();

    }

    public static String getFullName() {
        return generateRandomString(3) + " " + generateRandomString(3);
    }

    public static String getEmailID() {
        return generateRandomString(3) + String.valueOf(generateRandomIntegerNo(2)) + "@gmail.com";
    }

    public Integer createNumberValueUsingFaker(int noLength) {
        String noLen = "";
        for (int i = 0; i < noLength; i++) {
            noLen = noLen + "#";
        }
        return Integer.valueOf(new FakeValuesService(new Locale("en-IND"), new RandomService()).numerify(noLen));
    }

    public String createStringValueUsingFaker(int noLength, Boolean IsAllCapital) {
        String noLen = "";
        for (int i = 0; i < noLength; i++) {
            noLen = noLen + "?";
        }
        if (IsAllCapital) {
            return new FakeValuesService(new Locale("en-IND"), new RandomService()).letterify(noLen, true);
        } else {
            return new FakeValuesService(new Locale("en-IND"), new RandomService()).letterify(noLen, false);
        }
    }

    public String createAlphaNumericValueUsingFaker(int noLength, Boolean isUpper) {
        String lenNo = "";
        Boolean status = true;
        for (int i = 0; i < noLength; i++) {
            if (status) {
                lenNo = lenNo + "#";
                status = false;
            } else {
                lenNo = lenNo + "?";
                status = true;
            }
        }
        if (isUpper) {
            return new FakeValuesService(new Locale("en-IND"), new RandomService()).bothify(lenNo, true);
        } else {
            return new FakeValuesService(new Locale("en-IND"), new RandomService()).bothify(lenNo, false);
        }
    }

    public String createAlphaNumericRegixValueUsingFaker(int lenght) {
        return new FakeValuesService(new Locale("en-IND"), new RandomService()).regexify("[a-z0-9]{" + lenght + "}");
    }

    public String getName() {
        return new Faker(new Locale("en-IND")).name().fullName();
    }

    public String getAddress() {
        return new Faker(new Locale("en-IND")).address().fullAddress();
    }

    public String getEmailAddress() {
        return new Faker(new Locale("en-IND")).name().firstName() + "@gmail.com";
    }



}
