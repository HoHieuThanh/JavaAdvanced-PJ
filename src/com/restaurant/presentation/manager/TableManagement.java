package com.restaurant.presentation.manager;

import com.restaurant.model.entity.RestaurantTable;
import com.restaurant.model.enums.TableStatus;
import com.restaurant.service.TableService;

import java.util.List;
import java.util.Scanner;

public class TableManagement {
    TableService tableService = new TableService();
    Scanner scanner = new Scanner(System.in);
    
     void showTables() {
        List<RestaurantTable> list = tableService.getAll();

        if (list.isEmpty()) {
            System.out.println("Không có bàn nào!");
            return;
        }

        System.out.println("\n===== DANH SÁCH BÀN =====");
        displayListTable(list);
    }


     void addTable() {
        System.out.print("Số bàn: ");
        int number = Integer.parseInt(scanner.nextLine());

        int capacity;
        while (true) {
            System.out.print("Sức chứa: ");
            capacity = Integer.parseInt(scanner.nextLine());

            if (capacity < 0) {
                System.out.println("Không hợp lệ!");
            } else break;
        }

        tableService.addTable(number, capacity);
    }


     void updateTable() {

        System.out.print("Nhập ID bàn: ");
        int id = Integer.parseInt(scanner.nextLine());

        RestaurantTable t = tableService.findById(id);

        if (t == null) {
            System.out.println("Không tìm thấy bàn!");
            return;
        }

        // Hiển thị cũ
        System.out.println("Số bàn: " + t.getTableNumber());
        System.out.println("Sức chứa: " + t.getCapacity());
        System.out.println("Trạng thái: " + t.getStatus());

        System.out.print("Số bàn mới: ");
        int number = Integer.parseInt(scanner.nextLine());

        int capacity;
        while (true) {
            System.out.print("Sức chứa mới: ");
            capacity = Integer.parseInt(scanner.nextLine());

            if (capacity < 0) {
                System.out.println("Không hợp lệ!");
            } else break;
        }

        // chọn status
        System.out.println("Trạng thái:");
        System.out.println("1. AVAILABLE");
        System.out.println("2. OCCUPIED");
        System.out.println("3. RESERVED");

        int choice = Integer.parseInt(scanner.nextLine());

        TableStatus status = switch (choice) {
            case 1 -> TableStatus.AVAILABLE;
            case 2 -> TableStatus.OCCUPIED;
            case 3 -> TableStatus.RESERVED;
            default -> TableStatus.AVAILABLE;
        };

        tableService.updateTable(id, number, capacity, status);
    }

     void deleteTable() {

        System.out.print("Nhập ID bàn cần xóa: ");
        int id = Integer.parseInt(scanner.nextLine());

        RestaurantTable t = tableService.findById(id);

        if (t == null) {
            System.out.println("Không tìm thấy bàn!");
            return;
        }

        System.out.print("Bạn có chắc muốn xóa bàn " + t.getTableNumber() + "? (1/0): ");
        int confirm = Integer.parseInt(scanner.nextLine());

        if (confirm != 1) {
            System.out.println("Đã hủy!");
            return;
        }

        tableService.deleteTable(id);
    }

    // tìm bàn theo tt
     void searchTableByStatus() {

        System.out.println("Chọn trạng thái:");
        System.out.println("1. AVAILABLE");
        System.out.println("2. OCCUPIED");
        System.out.println("3. RESERVED");

        int choice = Integer.parseInt(scanner.nextLine());

        TableStatus status;

        switch (choice) {
            case 1:
                status = TableStatus.AVAILABLE;
                break;
            case 2:
                status = TableStatus.OCCUPIED;
                break;
            case 3:
                status = TableStatus.RESERVED;
                break;
            default:
                System.out.println("Lựa chọn không hợp lệ!");
                return;
        }

        List<RestaurantTable> list = tableService.findByStatus(status);

        if (list.isEmpty()) {
            System.out.println("Không tìm thấy bàn nào!");
            return;
        }

        System.out.println("\n===== KẾT QUẢ =====");
        displayListTable(list);

    }

    void displayListTable(List<RestaurantTable> list){
        System.out.printf("| %-5s | %-10s | %-10s | %-15s |\n",
                "ID", "Số bàn", "Sức chứa", "Trạng thái");

        for (RestaurantTable t : list) {
            System.out.printf("| %-5d | %-10d | %-10d | %-15s |\n",
                    t.getId(),
                    t.getTableNumber(),
                    t.getCapacity(),
                    t.getStatus());
        }
    }
}
