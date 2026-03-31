package com.restaurant.presentation.customer;

import com.restaurant.model.entity.RestaurantTable;
import com.restaurant.service.CustomerService;
import com.restaurant.util.InputValidate;
import com.restaurant.util.Print;

import java.util.List;
import java.util.Scanner;

public class CustomerUI {

    private CustomerService customerService = new CustomerService();
    private Scanner scanner = new Scanner(System.in);

    private int currentUserId;
    private int currentOrderId = -1;

    public CustomerUI(int userId) {
        this.currentUserId = userId;
    }

    public void menu() {
        while (true) {
            System.out.println("""
                    ======== KHÁCH HÀNG ========
                    | 1. Xem bàn trống         |
                    |--------------------------|
                    | 2. Chọn bàn & tạo order  |
                    |--------------------------|
                    | 3. Xem menu & gọi món    |
                    |--------------------------|
                    | 4. Xem món đã gọi        |
                    |--------------------------|
                    | 5. Hủy món               |
                    |--------------------------|
                    | 0. Thoát                 |
                    ============================
                    """);

            int choice = InputValidate.getInteger(scanner, "Chọn: ");

            switch (choice) {
                case 1:
                    showAvailableTables();
                    break;
                case 2:
                    chooseTableAndCreateOrder();
                    break;
                case 3:
                    addItem();
                    break;
                case 4:
                    viewOrder();
                    break;
                case 5:
                    cancelItem();
                    break;
                case 0:
                    return;
                default:
                    Print.yellowText("Lựa chọn không hợp lệ!");
            }
        }
    }

    // 1. XEM BÀN TRỐNG
    private void showAvailableTables() {
        List<RestaurantTable> tables = customerService.getAvailableTables();

        if (tables.isEmpty()) {
            Print.yellowText("Không có bàn trống!");
            return;
        }

        Print.greenText("=== Danh sách bàn trống ===");
        System.out.printf("| %-5s | %-10s | %-10s |\n", "Mã", "Số bàn", "Sức chứa");

        for (RestaurantTable t : tables) {
            System.out.printf("| %-5d | %-10d | %-10d |\n",
                    t.getId(),
                    t.getTableNumber(),
                    t.getCapacity());
        }
        System.out.println("=====================");
    }

    // 2. CHỌN BÀN + TẠO ORDER
    private void chooseTableAndCreateOrder() {

        showAvailableTables();

        int tableId = InputValidate.getInteger(scanner, "Nhập ID bàn: ");

        boolean success = customerService.chooseTable(tableId);

        if (!success) return;

        currentOrderId = customerService.createOrder(currentUserId, tableId);
    }

    // 3. GỌI MÓN
    private void addItem() {

        if (currentOrderId == -1) {
            Print.redText("Bạn chưa chọn bàn!");
            return;
        }

        Print.blueText("===== DANH SÁCH MÓN =====");
        customerService.showMenu();

        int itemId = InputValidate.getInteger(scanner, "Nhập ID món: ");
        int quantity = InputValidate.getInteger(scanner, "Số lượng: ");

        customerService.addItem(currentOrderId, itemId, quantity);
    }

    // 4. XEM MÓN
    private void viewOrder() {

        if (currentOrderId == -1) {
            Print.redText("Chưa có order!");
            return;
        }

        Print.blueText("===== MÓN ĐÃ GỌI =====");
        customerService.viewOrder(currentOrderId);
    }

    // 5. HỦY MÓN
    private void cancelItem() {

        if (currentOrderId == -1) {
            Print.redText("Chưa có order!");
            return;
        }

        viewOrder();

        int id = InputValidate.getInteger(scanner, "Nhập ID món cần hủy: ");

        int confirm = InputValidate.getInteger(scanner, "Xác nhận hủy? (1/0): ");
        if (confirm == 1) {
            customerService.cancelItem(id);
        }

    }
}
