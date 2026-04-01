package com.restaurant.presentation.manager;

import com.restaurant.dao.UserDAO;
import com.restaurant.model.entity.User;
import com.restaurant.model.enums.UserRole;
import com.restaurant.presentation.RegisterForm;
import com.restaurant.service.ManagerService;
import com.restaurant.util.InputValidate;
import com.restaurant.util.Print;

import java.util.List;
import java.util.Scanner;

public class ManagerManagement {

    Scanner scanner= new Scanner(System.in);
    private ManagerService userService = new ManagerService();

    public void createStaff() {

        User user = new RegisterForm().registerForm(new UserDAO());

        System.out.println("""
            Chọn role:
            1. MANAGER
            2. CHEF
            """);

        int choice = InputValidate.getInteger(scanner, "Chọn: ");

        UserRole role = (choice == 1) ? UserRole.MANAGER : UserRole.CHEF;
        user.setRole(role);

        if (userService.createStaff(user)) {
            Print.greenText("Tạo tài khoản thành công!");
        }
    }

    public void viewUsersByRole() {

        System.out.println("""
            1. MANAGER
            2. CHEF
            3. CUSTOMER
            """);

        int choice = InputValidate.getInteger(scanner, "Chọn: ");

        UserRole role = switch (choice) {
            case 1 -> UserRole.MANAGER;
            case 2 -> UserRole.CHEF;
            default -> UserRole.CUSTOMER;
        };

        List<User> list = userService.getByRole(role);

        if (list.isEmpty()) {
            Print.yellowText("Không có dữ liệu!");
            return;
        }

        printUserTable(list);
    }

    private void printUserTable(List<User> list) {

        String line = "+------+----------+----------------------+----------------------+------------+----------+";
        System.out.println("=============================== DANH SÁCH TÀI KHOẢN =====================================");
        System.out.printf("| %-4s | %-8s | %-20s | %-20s | %-10s | %-8s |\n",
                "ID", "User", "Họ tên", "Email", "SĐT", "Status");

        System.out.println(line);

        for (User u : list) {
            System.out.printf("| %-4d | %-8s | %-20s | %-20s | %-10s | %-8s |\n",
                    u.getId(),
                    u.getUsername(),
                    u.getFullName(),
                    u.getEmail(),
                    u.getPhone(),
                    u.isActive() ? "Active" : "Locked"
            );
        System.out.println(line);
        }

    }

    public void lockUser() {

        int id = InputValidate.getInteger(scanner, "Nhập ID user: ");

        while (true){
            int confirm = InputValidate.getInteger(scanner, "Xác nhận khóa? (Xác nhận: 1 / Huỷ: 0): ");
            if (confirm == 0) return;
            else if (confirm == 1) break;
            else Print.invalidSelection();
        }

        if (userService.lockUser(id)) {
            Print.greenText("Khóa tài khoản thành công!");
        }
    }


}

