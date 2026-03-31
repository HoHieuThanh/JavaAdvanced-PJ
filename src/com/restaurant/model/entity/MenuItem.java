package com.restaurant.model.entity;

import com.restaurant.model.enums.MenuCategory;

public class MenuItem {
    private int itemId;
    private String name;
    private double price;
    private MenuCategory category;
    private Integer stock;
    private boolean isAvailable;

    public MenuItem() {
    }

    public MenuItem(int itemId, String name, double price, MenuCategory category, Integer stock, boolean isAvailable) {
        this.itemId = itemId;
        this.name = name;
        this.price = price;
        this.category = category;
        this.stock = stock;
        this.isAvailable = isAvailable;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public MenuCategory getCategory() {
        return category;
    }

    public void setCategory(MenuCategory category) {
        this.category = category;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
