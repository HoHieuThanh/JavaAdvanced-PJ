package com.restaurant.service;

import com.restaurant.dao.MenuItemDAO;
import com.restaurant.model.entity.MenuItem;
import com.restaurant.util.Print;

import java.util.List;
import java.util.Objects;

public class MenuService {

    private MenuItemDAO menuDAO = new MenuItemDAO();

    // thêm món
    public void addItem(MenuItem item) {
        boolean result = menuDAO.insert(item);
        if (result) {
            Print.greenText("Thêm món thành công!");
        } else {
            Print.redText("Thêm món thất bại!");
        }
    }


    // lấy danh sách món
    public List<MenuItem> getAll() {
        return menuDAO.findAll();
    }

    // cập nhật món
    public void updateItem(int id, MenuItem itemUpdate) {
        MenuItem oldItem = menuDAO.findById(id);
        boolean result = menuDAO.update(itemUpdate);
        if (isSame(oldItem, itemUpdate)) {
            Print.blueText("Không có thay đổi nào.");
        } else if (result) {
            Print.greenText("Sửa món thành công!");
        } else {
            Print.redText("Sửa món thất bại!");
        }

    }

    private boolean isSame(MenuItem oldItem, MenuItem newItem) {
        return oldItem.getName().equals(newItem.getName())
                && oldItem.getPrice() == newItem.getPrice()
                && oldItem.getCategory() == newItem.getCategory()
                && Objects.equals(oldItem.getStock(), newItem.getStock())
                && oldItem.isAvailable() == newItem.isAvailable();
    }


    // xoá món
    public void deleteItem(int id) {
        boolean result = menuDAO.delete(id);
        if (result) {
            Print.greenText("Xóa món thành công!");
        } else {
            Print.redText("Xóa món thất bại!");
        }
    }

    // tìm món theo id
    public MenuItem findById(int id) {
        return menuDAO.findById(id);
    }

    // tìm món theo tên
    public List<MenuItem> searchByName(String keyword) {
        return menuDAO.searchByName(keyword);
    }


}
