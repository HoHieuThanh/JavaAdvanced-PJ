package com.restaurant.presentation.customer;

import com.restaurant.model.entity.*;
import com.restaurant.service.CustomerService;
import com.restaurant.util.InputValidate;
import com.restaurant.util.Print;

import java.util.List;
import java.util.Scanner;

public class CustomerUI {

    private CustomerService service = new CustomerService();
    private Scanner scanner = new Scanner(System.in);

    private User currentUser;
    private CustomerManagement management;

    public CustomerUI(User user) {
        this.currentUser = user;
        this.management = new CustomerManagement(user);
    }

    public void menu() {
        while (true) {
            System.out.println("""
                    
                    ======= KHÁCH HÀNG =======
                    | 1. Xem bàn trống       |
                    |------------------------|
                    | 2. Chọn bàn            |
                    |------------------------|
                    | 3. Xem menu & gọi món  |
                    |------------------------|
                    | 4. Xem hoá đơn         |
                    |------------------------|
                    | 5. Hủy món             |
                    |------------------------|
                    | 6. Thanh toán          |
                    |------------------------|
                    | 0. Đăng xuất           |
                    ==========================
                    """);

            int choice = InputValidate.getInteger(scanner, "Lựa chọn: ");

            switch (choice) {
                case 1 :
                    management.showTables();
                    break;
                case 2 :
                    management.chooseTable();
                    break;
                case 3 :
                    management.orderFood();
                    break;
                case 4 :
                    management.viewOrder();
                    break;
                case 5 :
                    management.cancelItem();
                    break;
                case 6 :
                    management.checkout();
                    break;
                case 0 :
                    Print.greenText("Đăng xuất thành công!");
                    return;
                default:
                    Print.invalidSelection();
            }
        }
    }
}
