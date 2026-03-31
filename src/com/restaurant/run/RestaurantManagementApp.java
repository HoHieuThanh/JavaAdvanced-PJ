package com.restaurant.run;

import com.restaurant.presentation.AuthUI;
import com.restaurant.util.InputValidate;
import com.restaurant.util.Print;

import java.util.Scanner;

public class RestaurantManagementApp {

    public static void main(String[] args) {

        AuthUI authUI = new AuthUI();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("""
                    
                    ===== HỆ THỐNG NHÀ HÀNG =====
                    | 1. Đăng nhập              |
                    |---------------------------|
                    | 2. Đăng ký khách hàng     |
                    |---------------------------|
                    | 0. Thoát                  |
                    =============================
                    """);
            int choice = InputValidate.getInteger(scanner, "Lựa chọn: ");
            switch (choice) {
                case 1:
                    authUI.login();
                    break;
                case 2:
                    authUI.register();
                    break;
                case 0:
                    Print.greenText("Thoát chương trình!");
                    return;
                default:
                    Print.invalidSelection();
            }
        }
    }
}
