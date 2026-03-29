package com.restaurant.util;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class PasswordHasher {

    // Cost (độ mạnh)
    private static final int COST = 12;

    // Hash password
    public static String hash(String plainPassword) {
        if (plainPassword == null || plainPassword.isEmpty()) {
            throw new IllegalArgumentException("Mật khẩu không hợp lệ");
        }

        return BCrypt.withDefaults()
                .hashToString(COST, plainPassword.toCharArray());
    }

    // Verify password
    public static boolean verify(String plainPassword, String hashedPassword) {
        if (plainPassword == null || hashedPassword == null) {
            return false;
        }

        BCrypt.Result result = BCrypt.verifyer()
                .verify(plainPassword.toCharArray(), hashedPassword);

        return result.verified;
    }
}
