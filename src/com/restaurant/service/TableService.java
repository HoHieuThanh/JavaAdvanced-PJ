package com.restaurant.service;

import com.restaurant.dao.TableDAO;
import com.restaurant.model.entity.RestaurantTable;
import com.restaurant.model.enums.TableStatus;

import java.util.List;

public class TableService {

    private TableDAO tableDAO = new TableDAO();

    // lấy danh sách bàn
    public List<RestaurantTable> getAll() {
        return tableDAO.findAll();
    }

    // tìm theo id
    public RestaurantTable findById(int id) {
        return tableDAO.findById(id);
    }

    // thêm bàn
    public void addTable(int number, int capacity) {

        if (capacity <= 0) {
            System.out.println("Sức chứa phải lớn hơn 0!");
            return;
        }

        if (tableDAO.findByTableNumber(number) != null) {
            System.out.println("Số bàn đã tồn tại!");
            return;
        }

        RestaurantTable table = new RestaurantTable();
        table.setTableNumber(number);
        table.setCapacity(capacity);
        table.setStatus(TableStatus.AVAILABLE);

        boolean result = tableDAO.insert(table);

        if (result) {
            System.out.println("Thêm bàn thành công!");
        } else {
            System.out.println("Thêm bàn thất bại!");
        }
    }

    // sửa bàn
    public void updateTable(int id, int number, int capacity, TableStatus status) {

        RestaurantTable existing = tableDAO.findById(id);

        if (existing == null) {
            System.out.println("Không tìm thấy bàn!");
            return;
        }

        if (capacity <= 0) {
            System.out.println("Sức chứa không hợp lệ!");
            return;
        }

        RestaurantTable table = new RestaurantTable();
        table.setId(id);
        table.setTableNumber(number);
        table.setCapacity(capacity);
        table.setStatus(status);

        boolean result = tableDAO.update(table);

        if (result) {
            System.out.println("Cập nhật bàn thành công!");
        } else {
            System.out.println("Cập nhật thất bại!");
        }
    }

    // xoá bàn
    public void deleteTable(int id) {

        RestaurantTable existing = tableDAO.findById(id);

        if (existing == null) {
            System.out.println("Không tìm thấy bàn!");
            return;
        }

        boolean result = tableDAO.delete(id);

        if (result) {
            System.out.println("Xóa bàn thành công!");
        } else {
            System.out.println("Xóa bàn thất bại!");
        }
    }

    // tìm bàn theo tt
    public List<RestaurantTable> findByStatus(TableStatus status) {
        return tableDAO.findByStatus(status);
    }

}
