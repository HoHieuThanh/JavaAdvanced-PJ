package com.restaurant.presentation;

import com.restaurant.model.entity.MenuItem;
import com.restaurant.model.entity.RestaurantTable;
import com.restaurant.model.enums.TableStatus;
import com.restaurant.service.MenuService;
import com.restaurant.service.TableService;

import java.util.List;
import java.util.Scanner;

public class ManagerUI {

    private Scanner scanner = new Scanner(System.in);
    private MenuService menuService = new MenuService();
    private TableService tableService = new TableService();

    public void menu() {
        while (true) {
            System.out.println("\n===== QUẢN LÝ NHÀ HÀNG =====");
            System.out.println("1. Quản lý thực đơn");
            System.out.println("2. Quản lý bàn");
            System.out.println("0. Đăng xuất");

            System.out.print("Chọn: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    menuManagement();
                    break;
                case 2:
                    tableManagement();
                    break;
                case 0:
                    System.out.println("Đã đăng xuất!");
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    private void menuManagement() {
        while (true) {
            System.out.println("\n===== QUẢN LÝ THỰC ĐƠN =====");
            System.out.println("1. Xem danh sách món");
            System.out.println("2. Thêm món");
            System.out.println("3. Sửa món");
            System.out.println("4. Xóa món");
            System.out.println("5. Tìm kiếm món theo tên");
            System.out.println("0. Quay lại");

            System.out.print("Chọn: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    showMenuItems();
                    break;

                case 2:
                    addMenuItem();
                    break;

                case 3:
                    updateMenuItem();
                    break;

                case 4:
                    deleteMenuItem();
                    break;
                case 5:
                    searchMenuItem();
                    break;


                case 0:
                    return;

                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    private void showMenuItems() {
        List<MenuItem> list = menuService.getAll();
        if (list.isEmpty()){
            System.out.println("\n===== THỰC ĐƠN TRỐNG =====");
            return;
        }

        System.out.println("\n===== THỰC ĐƠN =====");
        displayTitleItem(list);
    }

    private void addMenuItem() {

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


    private void updateMenuItem() {

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


    private void deleteMenuItem() {

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

    private void searchMenuItem() {

        System.out.print("Nhập tên món cần tìm: ");
        String keyword = scanner.nextLine();

        List<MenuItem> list = menuService.searchByName(keyword);

        if (list == null || list.isEmpty()) {
            System.out.println("Không tìm thấy món nào!");
            return;
        }

        // In dạng bảng
        System.out.println("\n===== KẾT QUẢ TÌM KIẾM =====");
        displayTitleItem(list);
    }

    private void displayTitleItem(List<MenuItem> list) {
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


    private void tableManagement() {
        while (true) {
            System.out.println("\n===== QUẢN LÝ BÀN =====");
            System.out.println("1. Xem danh sách bàn");
            System.out.println("2. Thêm bàn");
            System.out.println("3. Sửa bàn");
            System.out.println("4. Xóa bàn");
            System.out.println("5. Tìm bàn theo trạng thái");
            System.out.println("0. Quay lại");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    showTables();
                    break;
                case 2:
                    addTable();
                    break;
                case 3:
                    updateTable();
                    break;
                case 4:
                    deleteTable();
                    break;
                case 5:
                    searchTableByStatus();
                    break;

                case 0:
                    return;
            }
        }
    }


    private void showTables() {
        List<RestaurantTable> list = tableService.getAll();

        if (list.isEmpty()) {
            System.out.println("Không có bàn nào!");
            return;
        }

        System.out.println("\n===== DANH SÁCH BÀN =====");
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


    private void addTable() {
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


    private void updateTable() {

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

    private void deleteTable() {

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
    private void searchTableByStatus() {

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
