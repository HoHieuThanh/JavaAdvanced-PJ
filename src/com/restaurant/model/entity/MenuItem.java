package com.restaurant.model.entity;

import com.restaurant.model.enums.MenuCategory;

import java.math.BigDecimal;

public class MenuItem {
    private int id;
    private String name;
    private double price;
    private MenuCategory category;
    private Integer stock;
    private boolean isAvailable;

    public MenuItem() {
    }

    public MenuItem(int id, String name, double price, MenuCategory category, Integer stock, boolean isAvailable) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.stock = stock;
        this.isAvailable = isAvailable;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
