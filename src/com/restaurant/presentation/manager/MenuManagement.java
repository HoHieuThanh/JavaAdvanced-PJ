package com.restaurant.presentation.manager;

import com.restaurant.model.entity.MenuItem;
import com.restaurant.service.MenuService;
import com.restaurant.util.Print;

import java.util.List;
import java.util.Scanner;

public class MenuManagement {
    static MenuService menuService = new MenuService();
    Scanner scanner = new Scanner(System.in);

    void showMenuItems() {
        List<MenuItem> list = menuService.getAll();
        if (list.isEmpty()){
            Print.yellowText("===== THỰC ĐƠN TRỐNG =====");
            return;
        }

        System.out.println("\n===== THỰC ĐƠN =====");
        displayListItem(list);
    }

    void addMenuItem() {

        String name = "";
        while (name.isEmpty()){
            System.out.print("Tên món: ");
            name = scanner.nextLine();
            if (name.isEmpty()){
                System.out.println("Tên món ăn không được để trống");
            }
        }

        // Giá
        double price;
        while (true) {
            System.out.print("Giá: ");
            price = Double.parseDouble(scanner.nextLine());

            if (price <= 0) {
                System.out.println("Giá phải lớn hơn 0!");
            } else break;
        }

        // Danh mục
        int categoryChoice;
        while (true) {
            System.out.println("Chọn loại:");
            System.out.println("1. FOOD");
            System.out.println("2. DRINK");
            System.out.print("Chọn: ");

            categoryChoice = Integer.parseInt(scanner.nextLine());

            if (categoryChoice == 1 || categoryChoice == 2) break;

            System.out.println("Lựa chọn không hợp lệ!");
        }

        // Tồn kho
        Integer stock = null;
        if (categoryChoice == 2) {
            while (true) {
                System.out.print("Số lượng tồn kho: ");
                int s = Integer.parseInt(scanner.nextLine());

                if (s < 0) {
                    System.out.println("Stock không hợp lệ!");
                } else {
                    stock = s;
                    break;
                }
            }
        }

        // Có sẵn
        boolean isAvailable;
        while (true) {
            System.out.print("Có sẵn không? (1: Có, 0: Không): ");
            int choice = Integer.parseInt(scanner.nextLine());

            if (choice == 1) {
                isAvailable = true;
                break;
            } else if (choice == 0) {
                isAvailable = false;
                break;
            } else {
                System.out.println("Chỉ nhập 1 hoặc 0!");
            }
        }

        menuService.addItem(name, price, categoryChoice, stock, isAvailable);
    }


    void updateMenuItem() {

        System.out.print("Nhập ID món cần sửa: ");
        int id = Integer.parseInt(scanner.nextLine());

        MenuItem item = menuService.findById(id);

        if (item == null) {
            System.out.println("Không tìm thấy món!");
            return;
        }

        // Thông tin cũ
        System.out.println("\n--- Thông tin hiện tại ---");
        System.out.println("Tên: " + item.getName());
        System.out.println("Giá: " + item.getPrice());
        System.out.println("Loại: " + item.getCategory());
        System.out.println("Stock: " + item.getStock());
        System.out.println("Trạng thái: " + item.isAvailable());

        System.out.print("Tên mới: ");
        String name = scanner.nextLine();

        double price;
        while (true) {
            System.out.print("Giá mới: ");
            price = Double.parseDouble(scanner.nextLine());

            if (price <= 0) {
                System.out.println("Giá phải lớn hơn 0!");
            } else break;
        }

        int categoryChoice;
        while (true) {
            System.out.println("Chọn loại:");
            System.out.println("1. FOOD");
            System.out.println("2. DRINK");
            categoryChoice = Integer.parseInt(scanner.nextLine());

            if (categoryChoice == 1 || categoryChoice == 2) break;
            System.out.println("Không hợp lệ!");
        }

        Integer stock = null;
        if (categoryChoice == 2) {
            while (true) {
                System.out.print("Stock: ");
                int s = Integer.parseInt(scanner.nextLine());

                if (s < 0) {
                    System.out.println("Không hợp lệ!");
                } else {
                    stock = s;
                    break;
                }
            }
        }

        boolean isAvailable;
        while (true) {
            System.out.print("Có sẳn không? (1/0): ");
            int choice = Integer.parseInt(scanner.nextLine());

            if (choice == 1) {
                isAvailable = true;
                break;
            } else if (choice == 0) {
                isAvailable = false;
                break;
            } else {
                System.out.println("Chỉ nhập 1 hoặc 0!");
            }
        }

        menuService.updateFull(id, name, price, categoryChoice, stock, isAvailable);
    }


    void deleteMenuItem() {

        System.out.print("Nhập ID món cần xóa: ");
        int id = Integer.parseInt(scanner.nextLine());

        MenuItem item = menuService.findById(id);

        if (item == null) {
            System.out.println("Không tìm thấy món!");
            return;
        }

        System.out.println("Bạn có chắc muốn xóa món: " + item.getName() + " ?");
        System.out.print("Nhập 1 (Đồng ý) / 0 (Hủy): ");

        int confirm = Integer.parseInt(scanner.nextLine());

        if (confirm != 1) {
            System.out.println("Đã hủy thao tác!");
            return;
        }

        menuService.deleteItem(id);
    }

    void searchMenuItem() {

        System.out.print("Nhập tên món cần tìm: ");
        String keyword = scanner.nextLine();

        List<MenuItem> list = menuService.searchByName(keyword);

        if (list == null || list.isEmpty()) {
            System.out.println("Không tìm thấy món nào!");
            return;
        }

        // In dạng bảng
        System.out.println("\n===== KẾT QUẢ TÌM KIẾM =====");
        displayListItem(list);
    }

    void displayListItem(List<MenuItem> list) {
        System.out.printf("| %-5s | %-20s | %-10s | %-10s | %-10s | %-10s |\n",
                "ID", "Tên", "Loại", "Giá", "Stock", "Trạng thái");

        for (MenuItem item : list) {
            System.out.printf("| %-5d | %-20s | %-10s | %-10.2f | %-10s | %-10s |\n",
                    item.getId(),
                    item.getName(),
                    item.getCategory(),
                    item.getPrice(),
                    item.getStock() == null ? "-" : item.getStock(),
                    item.isAvailable() ? "Có bán" : "Ngừng bán"
            );
        }
    }
}
