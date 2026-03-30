package com.restaurant.util;

import java.util.regex.Pattern;

public class ValidatorUtil {

//    private static final String EMAIL_REGEX =
//            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]";

    private static final String PHONE_REGEX =
            "^[0-9]{10}$";

    public static boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static boolean isValidEmail(String email) {
        return email.endsWith("@gmail.com");
    }

    public static boolean isValidPhone(String phone) {
        return Pattern.matches(PHONE_REGEX, phone);
    }

    public static boolean isValidPassword(String password) {
        return password != null && password.length() >= 6;
    }
}

