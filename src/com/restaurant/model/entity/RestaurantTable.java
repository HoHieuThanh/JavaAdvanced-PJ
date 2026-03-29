package com.restaurant.model.entity;

import com.restaurant.model.enums.TableStatus;

public class RestaurantTable {
    private int id;
    private int tableNumber;
    private int capacity;
    private TableStatus status;

    public RestaurantTable() {
    }

    public RestaurantTable(int id, int tableNumber, int capacity, TableStatus status) {
        this.id = id;
        this.tableNumber = tableNumber;
        this.capacity = capacity;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public TableStatus getStatus() {
        return status;
    }

    public void setStatus(TableStatus status) {
        this.status = status;
    }
}
