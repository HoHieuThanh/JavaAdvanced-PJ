package com.restaurant.presentation.chef;
import com.restaurant.util.InputValidate;
import com.restaurant.util.Print;

import java.util.Scanner;

public class ChefUI {

    ChefManagement chefManagement = new ChefManagement();
    private Scanner scanner = new Scanner(System.in);

    public void menu() {

        while (true) {
            System.out.println("""
                    
                    ======== MENU ĐẦU BẾP ========
                    | 1. Xem danh sách món       |
                    |----------------------------|
                    | 2. Cập nhật trạng thái món |
                    |----------------------------|
                    | 0. Đăng xuất               |
                    ==============================
                    """);

            int choice = InputValidate.getInteger(scanner, "Lụa chọn: ");

            switch (choice) {
                case 1:
                    chefManagement.showKitchen();
                    break;
                case 2:
                    chefManagement.updateStatus();
                    break;
                case 0:
                    Print.greenText("Đăng xuất thành công!");
                    return;
                default:
                    Print.invalidSelection();
            }
        }
    }
}
