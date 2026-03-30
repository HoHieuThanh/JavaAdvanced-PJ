package com.restaurant.util;

public class Print {
    // Source - https://stackoverflow.com/a/5762502

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    // Lỗi
    public static void redText(String str){
        System.out.println(ANSI_RED + str + ANSI_RESET);
    }

    // Thông báo
    public static void yellowText(String str){
        System.out.println(ANSI_YELLOW + str + ANSI_RESET);
    };

    // Thành công
    public static void greenText(String str){
        System.out.println(ANSI_GREEN + str + ANSI_RESET);
    };

    // Hoạt động hiện tại
    public static void blueText(String str){
        System.out.println(ANSI_BLUE + str + ANSI_RESET);
    };

    public static void invalidSelection(){
        System.out.println(ANSI_YELLOW + "Lựa chọn không hợp lệ" + ANSI_RESET);
    }
}

