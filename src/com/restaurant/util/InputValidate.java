package com.restaurant.util;

import java.util.Scanner;

public class InputValidate {

    public static int getInteger(Scanner sc, String suggestion){
        while (true){
            try {
                System.out.print(suggestion);
                return Integer.parseInt(sc.nextLine());
            }catch (Exception e){
                Print.yellowText("Vui lòng nhập 1 số nguyên!");
            }
        }
    };

    public static double getDouble(Scanner sc, String suggestion) {
        double input;
        while (true) {
            try {
                System.out.print(suggestion);
                input = Double.parseDouble(sc.nextLine());
                if (input <= 0) {
                    Print.yellowText("Giá trị phải lớn hơn 0!");
                    continue;
                }
                return input;
            } catch (Exception e) {
                Print.yellowText("Vui lòng nhập số hợp lệ!");
            }
        }
    }

    public static String getString(Scanner sc, String suggestion){
        System.out.print(suggestion);
        return sc.nextLine().trim();
    }

}
