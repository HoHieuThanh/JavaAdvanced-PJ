package com.restaurant.presentation.manager;

import com.restaurant.util.InputValidate;
import com.restaurant.util.Print;

import java.util.List;
import java.util.Scanner;

public class ManagerUI {

    Scanner scanner = new Scanner(System.in);
    MenuManagement menuManagement = new MenuManagement();
    TableManagement tableManagement = new TableManagement();
    public void menu() {
        while (true) {
            System.out.println("""
                    
                    ===== QUẢN LÝ NHÀ HÀNG =====
                    | 1. Quản lý thực đơn      |
                    |--------------------------|
                    | 2. Quản lý bàn           |
                    |--------------------------|
                    | 0. Đăng xuất             |
                    ============================
                    """);
            int choice = InputValidate.getInteger(scanner, "Lựa chọn: ");
            switch (choice) {
                case 1:
                    menuManagement();
                    break;
                case 2:
                    tableManagement();
                    break;
                case 0:
                    Print.greenText("Đã đăng xuất!");
                    return;
                default:
                    Print.yellowText("Lựa chọn không hợp lệ!");
            }
        }
    }

    private void menuManagement() {
        while (true) {

            System.out.println("""
                    
                    ===== QUẢN LÝ THỰC ĐƠN =====
                    | 1. Xem danh sách món     |
                    |--------------------------|
                    | 2. Thêm món              |
                    |--------------------------|
                    | 3. Sửa món               |
                    |--------------------------|
                    | 4. Xóa món               |
                    |--------------------------|
                    | 5. Tìm kiếm món theo tên |
                    |--------------------------|
                    | 0. Quay lại              |
                    ============================
                    """);
            int choice = InputValidate.getInteger(scanner, "Lựa chọn: ");

            switch (choice) {
                case 1:
                    menuManagement.showMenuItems();
                    break;

                case 2:
                    menuManagement.addMenuItem();
                    break;

                case 3:
                    menuManagement.updateMenuItem();
                    break;

                case 4:
                    menuManagement.deleteMenuItem();
                    break;
                case 5:
                    menuManagement.searchMenuItem();
                    break;
                case 0:
                    return;

                default:
                    Print.yellowText("Lựa chọn không hợp lệ!");
            }
        }
    }




    private void tableManagement() {
        while (true) {
            System.out.println("""
                    
                    ======== QUẢN LÝ BÀN ========
                    | 1. Xem danh sách bàn      |
                    |---------------------------|
                    | 2. Thêm bàn               |
                    |---------------------------|
                    | 3. Sửa bàn                |
                    |---------------------------|
                    | 4. Xóa bàn                |
                    |---------------------------|
                    | 5. Tìm bàn theo trạng thái|
                    |---------------------------|
                    | 0. Quay lại               |
                    =============================
                    """);

            int choice = InputValidate.getInteger(scanner, "Lựa chọn: ");

            switch (choice) {
                case 1:
                    tableManagement.showTables();
                    break;
                case 2:
                    tableManagement.addTable();
                    break;
                case 3:
                    tableManagement.updateTable();
                    break;
                case 4:
                    tableManagement.deleteTable();
                    break;
                case 5:
                    tableManagement.searchTableByStatus();
                    break;
                case 0:
                    return;
                default:
                    Print.invalidSelection();
            }
        }
    }
}
