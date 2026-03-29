package test;

import com.restaurant.util.PasswordHasher;

public class TestPassword {
    public static void main(String[] args) {

        String password = "123456";

        String hash = PasswordHasher.hash(password);
        System.out.println("Hash: " + hash);

        System.out.println("Đúng: " + PasswordHasher.verify("123456", hash));
        System.out.println("Sai: " + PasswordHasher.verify("abc", hash));
    }
}
