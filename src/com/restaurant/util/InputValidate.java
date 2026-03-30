package com.restaurant.util;

import java.util.Scanner;

public class InputValidate {
    public static int getInteger(Scanner sc, String suggestion){
        do {
            try {
                System.out.print(suggestion);
                return Integer.parseInt(sc.nextLine());
            }catch (Exception e){
                Print.yellowText("Vui lòng nhập 1 số nguyên!");
            }
        }while (true);
    };
    public  static double getDouble(Scanner sc, String suggestion){
        do {
            try {
                System.out.print(suggestion);
                return sc.nextDouble();
            }catch (Exception e){
                Print.yellowText("Vui lòng nhập đúng định dạng");
            }
        }while (true);
    }
}
