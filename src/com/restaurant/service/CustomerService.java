package com.restaurant.service;

import com.restaurant.dao.*;
import com.restaurant.model.entity.*;
import com.restaurant.model.enums.OrderItemStatus;
import com.restaurant.model.enums.OrderStatus;
import com.restaurant.model.enums.TableStatus;
import com.restaurant.util.Print;

import java.util.List;

public class CustomerService {

    private TableDAO tableDAO = new TableDAO();
    private OrderDAO orderDAO = new OrderDAO();
    private OrderItemDAO orderItemDAO = new OrderItemDAO();
    private MenuItemDAO menuDAO = new MenuItemDAO();

    // LẤY DANH SÁCH BÀN TRỐNG
    public List<RestaurantTable> getAvailableTables() {
        return tableDAO.findByStatus(TableStatus.AVAILABLE);
    }

    public void showMenu() {
        List<MenuItem> list = menuDAO.findAll();

        System.out.println("============= MENU =============");
        System.out.printf("| %-5s | %-20s | %-10s | %-10s |\n",
                "Mã", "Tên", "Giá", "Loại");

        for (MenuItem m : list) {
            System.out.printf("| %-5d | %-20s | %-10.2f | %-10s |\n",
                    m.getItemId(),
                    m.getName(),
                    m.getPrice(),
                    m.getCategory());
        }
        System.out.println("================================");
    }


    // CHỌN BÀN
    public boolean chooseTable(int tableId) {
        RestaurantTable table = tableDAO.findById(tableId);

        if (table == null) {
            Print.redText("Bàn không tồn tại!");
            return false;
        }

        if (table.getStatus() != TableStatus.AVAILABLE) {
            Print.redText("Bàn đã có người!");
            return false;
        }

        table.setStatus(TableStatus.OCCUPIED);
        tableDAO.update(table);

        Print.greenText("Chọn bàn thành công!");
        return true;
    }

    // TẠO ORDER
    public int createOrder(int userId, int tableId) {

        Order order = new Order();
        order.setUserId(userId);
        order.setTableId(tableId);
        order.setStatus(OrderStatus.PENDING);

        int orderId = orderDAO.insert(order);

        if (orderId == -1) {
            Print.redText("Tạo order thất bại!");
        } else {
            Print.greenText("Tạo order thành công!");
        }

        return orderId;
    }

    // GỌI MÓN
    public void addItem(int orderId, int itemId, int quantity) {

        if (quantity <= 0) {
            Print.redText("Số lượng phải > 0!");
            return;
        }

        MenuItem item = menuDAO.findById(itemId);

        if (item == null || !item.isAvailable()) {
            Print.redText("Món không tồn tại hoặc ngừng bán!");
            return;
        }

        // check stock nếu là đồ uống
        if (item.getStock() != null && item.getStock() < quantity) {
            Print.redText("Không đủ hàng!");
            return;
        }

        OrderItem oi = new OrderItem();
        oi.setOrderId(orderId);
        oi.setItemId(itemId);
        oi.setQuantity(quantity);
        oi.setStatus(OrderItemStatus.PENDING);

        boolean result = orderItemDAO.insert(oi);

        if (result) {
            Print.greenText("Gọi món thành công!");

            // trừ stock
            if (item.getStock() != null) {
                item.setStock(item.getStock() - quantity);
                menuDAO.update(item);
            }

            // cập nhật tiền
            double total = orderItemDAO.calculateTotal(orderId);
            orderDAO.updateTotal(orderId, total);

        } else {
            Print.redText("Gọi món thất bại!");
        }
    }

    // XEM MÓN
    public void viewOrder(int orderId) {
        orderItemDAO.printOrderDetail(orderId);
    }

    // HỦY MÓN
    public void cancelItem(int orderItemId) {

        OrderItem item = orderItemDAO.findById(orderItemId);

        if (item == null) {
            Print.redText("Không tìm thấy món!");
            return;
        }

        if (item.getStatus() != OrderItemStatus.PENDING) {
            Print.redText("Không thể hủy vì món đã được xử lý!");
            return;
        }

        boolean result = orderItemDAO.updateStatus(orderItemId, OrderItemStatus.CANCELLED);

        if (result) {
            Print.greenText("Hủy món thành công!");

            //  hoàn lại stock
            MenuItem menuItem = menuDAO.findById(item.getItemId());
            if (menuItem.getStock() != null) {
                menuItem.setStock(menuItem.getStock() + item.getQuantity());
                menuDAO.update(menuItem);
            }

            // cập nhật lại tiền
            double total = orderItemDAO.calculateTotal(item.getOrderId());
            orderDAO.updateTotal(item.getOrderId(), total);

        } else {
            Print.redText("Hủy thất bại!");
        }
    }
}
