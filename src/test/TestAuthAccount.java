package test;

import com.restaurant.presentation.AuthUI;
import com.restaurant.util.DBConnection;

import java.sql.Connection;

public class TestAuthAccount {
    public static void main(String[] args) {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.println("Kết nối thành công!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        AuthUI authUI = new AuthUI();

        authUI.register();
    }
}
