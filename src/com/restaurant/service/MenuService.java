package com.restaurant.service;

import com.restaurant.dao.MenuItemDAO;
import com.restaurant.model.entity.MenuItem;
import com.restaurant.model.enums.MenuCategory;

import java.util.List;

public class MenuService {

    private MenuItemDAO menuDAO = new MenuItemDAO();

    // thêm món
    public void addItem(String name, double price, int categoryChoice, Integer stock, boolean isAvailable) {

        if (price <= 0) {
            System.out.println("Giá không hợp lệ!");
            return;
        }

        MenuCategory category;

        if (categoryChoice == 1) {
            category = MenuCategory.FOOD;
            stock = null; // FOOD không có tồn kho
        } else {
            category = MenuCategory.DRINK;
        }

        MenuItem item = new MenuItem();
        item.setName(name);
        item.setPrice(price);
        item.setCategory(category);
        item.setStock(stock);
        item.setAvailable(isAvailable);

        boolean result = menuDAO.insert(item);

        if (result) {
            System.out.println("Thêm món thành công!");
        } else {
            System.out.println("Thêm món thất bại!");
        }
    }


    // lấy danh sách món
    public List<MenuItem> getAll() {
        return menuDAO.findAll();
    }

    // cập nhật món
    public void updateFull(int id, String name, double price, int categoryChoice, Integer stock, boolean isAvailable) {

        MenuItem existing = menuDAO.findById(id);

        if (existing == null) {
            System.out.println("Không tìm thấy món!");
            return;
        }

        if (price <= 0) {
            System.out.println("Giá không hợp lệ!");
            return;
        }

        MenuCategory category;

        if (categoryChoice == 1) {
            category = MenuCategory.FOOD;
            stock = null;
        } else {
            category = MenuCategory.DRINK;
        }

        MenuItem item = new MenuItem();
        item.setId(id);
        item.setName(name);
        item.setPrice(price);
        item.setCategory(category);
        item.setStock(stock);
        item.setAvailable(isAvailable);

        boolean result = menuDAO.update(item);

        if (result) {
            System.out.println("Sửa món thành công!");
        } else {
            System.out.println("Sửa món thất bại!");
        }
    }

    // xoá món
    public void deleteItem(int id) {

        MenuItem item = menuDAO.findById(id);

        if (item == null) {
            System.out.println("Không tìm thấy món!");
            return;
        }

        boolean result = menuDAO.delete(id);

        if (result) {
            System.out.println("Xóa món thành công!");
        } else {
            System.out.println("Xóa món thất bại!");
        }
    }
    public MenuItem findById(int id) {
        return menuDAO.findById(id);
    }

    public List<MenuItem> searchByName(String keyword) {

        if (keyword == null || keyword.trim().isEmpty()) {
            System.out.println("Tên tìm kiếm không được để trống!");
            return null;
        }

        return menuDAO.searchByName(keyword);
    }


}
