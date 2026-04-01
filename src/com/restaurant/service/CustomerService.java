package com.restaurant.service;

import com.restaurant.dao.*;
import com.restaurant.model.dto.OrderDetailDTO;
import com.restaurant.model.entity.*;
import com.restaurant.model.enums.*;
import com.restaurant.util.Print;

import java.util.List;

public class CustomerService {

    private TableDAO tableDAO = new TableDAO();
    private OrderDAO orderDAO = new OrderDAO();
    private OrderItemDAO orderItemDAO = new OrderItemDAO();
    private MenuItemDAO menuDAO = new MenuItemDAO();

    // ===== MENU =====
    public List<MenuItem> getMenu() {
        return menuDAO.findAll();
    }

    // ===== BÀN TRỐNG =====
    public List<RestaurantTable> getAvailableTables() {
        return tableDAO.findByStatus(TableStatus.AVAILABLE);
    }

    // ===== CHỌN BÀN =====
    public boolean chooseTable(int tableNumber) {
        RestaurantTable table = tableDAO.findByTableNumber(tableNumber);

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
        return true;
    }

    // ===== TẠO ORDER =====
    public int createOrder(int userId, int tableId) {
        Order order = new Order();
        order.setUserId(userId);
        order.setTableId(tableId);
        order.setStatus(OrderStatus.PENDING);

        return orderDAO.insert(order);
    }

    // ===== GỌI MÓN =====
    public boolean addItem(int userId, int orderId, int itemId, int quantity) {

        if (quantity <= 0) {
            Print.redText("Số lượng phải > 0!");
            return false;
        }

        Order order = orderDAO.findById(orderId);
        if (order == null || order.getUserId() != userId) {
            Print.redText("Order không hợp lệ!");
            return false;
        }

        MenuItem item = menuDAO.findById(itemId);
        if (item == null || !item.isAvailable()) {
            Print.redText("Món không tồn tại hoặc ngừng bán!");
            return false;
        }

        if (item.getStock() != null && item.getStock() < quantity) {
            Print.redText("Không đủ hàng!");
            return false;
        }

        OrderItem oi = new OrderItem();
        oi.setOrderId(orderId);
        oi.setItemId(itemId);
        oi.setQuantity(quantity);
        oi.setStatus(OrderItemStatus.PENDING);

        boolean result = orderItemDAO.insert(oi);

        if (result) {
            if (item.getStock() != null) {
                item.setStock(item.getStock() - quantity);
                menuDAO.update(item);
            }

            double total = orderItemDAO.calculateTotal(orderId);
            orderDAO.updateTotal(orderId, total);

            return true;
        }

        return false;
    }

    // ===== XEM MÓN =====
    public List<OrderDetailDTO> getOrderDetail(int userId, int orderId) {

        Order order = orderDAO.findById(orderId);

        if (order == null || order.getUserId() != userId) {
            return null;
        }

        return orderItemDAO.findOrderDetail(orderId);
    }


    // ===== HỦY MÓN =====
    public boolean cancelItem(int userId, int orderItemId) {

        OrderItem item = orderItemDAO.findById(orderItemId);

        if (item == null) {
            Print.redText("Không tìm thấy món!");
            return false;
        }

        Order order = orderDAO.findById(item.getOrderId());
        if (order.getUserId() != userId) {
            Print.redText("Không có quyền!");
            return false;
        }

        if (item.getStatus() != OrderItemStatus.PENDING) {
            Print.redText("Không thể hủy!");
            return false;
        }

        boolean result = orderItemDAO.updateStatus(orderItemId, OrderItemStatus.CANCELLED);

        if (result) {
            MenuItem menuItem = menuDAO.findById(item.getItemId());
            if (menuItem.getStock() != null) {
                menuItem.setStock(menuItem.getStock() + item.getQuantity());
                menuDAO.update(menuItem);
            }

            double total = orderItemDAO.calculateTotal(item.getOrderId());
            orderDAO.updateTotal(item.getOrderId(), total);

            return true;
        }

        return false;
    }

    // ===== THANH TOÁN =====
    public void checkout(int orderId) {
        Order order = orderDAO.findById(orderId);

        if (order == null) {
            Print.redText("Order không tồn tại!");
            return;
        }

        orderDAO.updateStatus(orderId, OrderStatus.COMPLETED);

        RestaurantTable table = tableDAO.findById(order.getTableId());
        table.setStatus(TableStatus.AVAILABLE);
        tableDAO.update(table);

        Print.greenText("Thanh toán thành công!");
    }
}
