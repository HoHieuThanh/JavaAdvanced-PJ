package com.restaurant.presentation.customer;

import com.restaurant.model.dto.OrderDetailDTO;
import com.restaurant.model.entity.MenuItem;
import com.restaurant.model.entity.OrderItem;
import com.restaurant.model.entity.RestaurantTable;
import com.restaurant.model.entity.User;
import com.restaurant.service.CustomerService;
import com.restaurant.util.InputValidate;
import com.restaurant.util.Print;

import java.util.List;
import java.util.Scanner;

public class CustomerManagement {

    private User currentUser;
    private int userId;

    public CustomerManagement(User user) {
        this.currentUser = user;
        this.userId = user.getId();
    }


    int orderId = -1;
    Scanner scanner = new Scanner(System.in);
    CustomerService customerService = new CustomerService();

    void showTables() {
        List<RestaurantTable> list = customerService.getAvailableTables();

        String line = "+----------+----------+";
        System.out.println("===== DANH SÁCH BÀN TRỐNG ====");
        System.out.printf("| %-8s | %-8s |\n", "Số bàn", "Sức chứa");
        System.out.println(line);

        for (RestaurantTable t : list) {
            System.out.printf("| %-8d | %-8d |\n", t.getTableNumber(), t.getCapacity());
        System.out.println(line);
        }
    }

    public void chooseTable() {
        int tableNumber = InputValidate.getInteger(scanner, "Bạn muốn chọn bàn số: ");
        boolean ok = customerService.chooseTable(tableNumber);
        if (!ok) return;
        orderId = customerService.createOrder(userId, tableNumber);
        Print.greenText("Chọn bàn thành công! Mã order của bạn: " + orderId);
    }

    void orderFood() {

        if (orderId == -1) {
            Print.redText("Bạn chưa chọn bàn!");
            return;
        }

        List<MenuItem> menu = customerService.getMenu();

        String line = "+------+----------------------+----------+----------+--------------+";
        System.out.println("============================== MENU ================================");
        System.out.printf("| %-4s | %-20s | %-8s | %-8s | %-12s |\n",
                "Mã", "Tên món", "Loại", "Giá", "Trạng thái");

        System.out.println(line);

        for (MenuItem m : menu) {
            System.out.printf("| %-4d | %-20s | %-8s | %-8.0f | %-12s |\n",
                    m.getItemId(),
                    m.getName(),
                    m.getCategory(),
                    m.getPrice(),
                    m.isAvailable() ? "Có bán" : "Ngừng bán"
            );
        System.out.println(line);
        }



        int itemId = InputValidate.getInteger(scanner, "Chọn món (Nhập mã sản phẩm): ");
        int quantity = InputValidate.getInteger(scanner, "Số lượng: ");

        if (customerService.addItem(userId, orderId, itemId, quantity)) {
            Print.greenText("Gọi món thành công!");
        }
    }

    void viewOrder() {

        List<OrderDetailDTO> list = customerService.getOrderDetail(userId, orderId);

        if (list == null || list.isEmpty()) {
            Print.yellowText("Không có món!");
            return;
        }

        String line = "+------+----------------------+----------+----------+--------------+------------+";
        System.out.println("================================== HOÁ ĐƠN ======================================");
        System.out.printf("| %-4s | %-20s | %-8s | %-8s | %-12s | %-10s |\n",
                "Mã", "Tên món", "SL", "Giá", "Thành tiền", "Trạng thái");

        System.out.println(line);

        double totalBill = 0;

        for (OrderDetailDTO i : list) {

            totalBill += i.getTotal();

            System.out.printf("| %-4d | %-20s | %-8d | %-8.0f | %-12.0f | %-10s |\n",
                    i.getOrderItemId(),
                    i.getItemName(),
                    i.getQuantity(),
                    i.getPrice(),
                    i.getTotal(),
                    i.getStatus()
            );
        System.out.println(line);
        }
        System.out.printf("| %-4s | %-20s | %-8s | %-8s | %-12.0f | %-10s |\n",
                "", "", "", "Tổng:", totalBill, "");

        System.out.println(line);
    }


    void cancelItem() {

        viewOrder();

        int id = InputValidate.getInteger(scanner, "ID món cần hủy: ");
        while (true) {
            int confirm = InputValidate.getInteger(scanner, "Bạn có chắc chắn muốn huỷ món? (Xác nhận: 1 / Huỷ: 0): ");
            if (confirm == 0) return;
            else if (confirm == 1) {
                break;
            } else {
                Print.invalidSelection();
            }
        }
        if (customerService.cancelItem(userId, id)) {
            Print.greenText("Hủy thành công!");
        }
    }

    void checkout() {

        if (orderId == -1) {
            Print.redText("Chưa có order!");
            return;
        }

        customerService.checkout(orderId);
        orderId = -1;
    }
}
