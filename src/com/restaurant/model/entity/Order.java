package com.restaurant.model.entity;

import com.restaurant.model.enums.OrderStatus;

import java.time.LocalDateTime;

public class Order {
    private int id;
    private int userId;
    private int tableId;
    private double totalAmount;
    private OrderStatus status;
    private LocalDateTime createdAt;

    public Order() {
    }

    public Order(int id, int userId, int tableId, double totalAmount, OrderStatus status, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.tableId = tableId;
        this.totalAmount = totalAmount;
        this.status = status;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
