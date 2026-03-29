package com.restaurant.run;

import com.restaurant.presentation.AuthUI;

import java.util.Scanner;

public class RestaurantManagementApp {

    public static void main(String[] args) {

        AuthUI authUI = new AuthUI();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== HỆ THỐNG NHÀ HÀNG =====");
            System.out.println("1. Đăng nhập");
            System.out.println("2. Đăng ký");
            System.out.println("0. Thoát");

            System.out.print("Chọn: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    authUI.login();
                    break;
                case 2:
                    authUI.register();
                    break;
                case 0:
                    System.out.println("Thoát chương trình!");
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }
}
