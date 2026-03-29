package com.restaurant.model.entity;


import com.restaurant.model.enums.OrderItemStatus;

public class OrderItem {
    private int id;
    private int orderId;
    private int menuItemId;
    private int quantity;
    private OrderItemStatus status;

    public OrderItem() {
    }

    public OrderItem(int id, int orderId, int menuItemId, int quantity, OrderItemStatus status) {
        this.id = id;
        this.orderId = orderId;
        this.menuItemId = menuItemId;
        this.quantity = quantity;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(int menuItemId) {
        this.menuItemId = menuItemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public OrderItemStatus getStatus() {
        return status;
    }

    public void setStatus(OrderItemStatus status) {
        this.status = status;
    }
}
