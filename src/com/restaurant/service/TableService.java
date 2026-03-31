package com.restaurant.service;

import com.restaurant.dao.TableDAO;
import com.restaurant.model.entity.RestaurantTable;
import com.restaurant.model.enums.TableStatus;
import com.restaurant.util.Print;

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

    // check trùng số bàn
    public RestaurantTable findByTableNumber(int tableNumber){return  tableDAO.findByTableNumber(tableNumber);}

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
            Print.greenText("Thêm bàn thành công!");
        } else {
            Print.redText("Thêm bàn thất bại!");
        }
    }

    // sửa bàn
    public void updateTable(RestaurantTable tableUpdated) {
        RestaurantTable table = new RestaurantTable();
        table.setId(tableUpdated.getId());
        table.setTableNumber(tableUpdated.getTableNumber());
        table.setCapacity(tableUpdated.getCapacity());
        table.setStatus(tableUpdated.getStatus());

        boolean result = tableDAO.update(table);

        if (result) {
            Print.greenText("Cập nhật bàn thành công!");
        } else {
            Print.redText("Cập nhật thất bại!");
        }
    }

    // xoá bàn
    public void deleteTable(int id) {

        RestaurantTable existing = tableDAO.findById(id);

        if (existing == null) {
           Print.yellowText("Không tìm thấy bàn!");
            return;
        }

        boolean result = tableDAO.delete(id);

        if (result) {
            Print.greenText("Xóa bàn thành công!");
        } else {
            Print.redText("Xóa bàn thất bại!");
        }
    }

    // tìm bàn theo tt
    public List<RestaurantTable> findByStatus(TableStatus status) {
        return tableDAO.findByStatus(status);
    }

}
