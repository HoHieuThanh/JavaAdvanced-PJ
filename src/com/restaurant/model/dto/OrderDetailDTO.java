package com.restaurant.model.dto;

public class OrderDetailDTO {
    private int orderItemId;
    private String itemName;
    private int quantity;
    private double price;
    private double total;
    private String status;

    public OrderDetailDTO() {
    }

    public OrderDetailDTO(int orderItemId, String itemName, int quantity, double price, double total, String status) {
        this.orderItemId = orderItemId;
        this.itemName = itemName;
        this.quantity = quantity;
        this.price = price;
        this.total = total;
        this.status = status;
    }

    public int getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(int orderItemId) {
        this.orderItemId = orderItemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

