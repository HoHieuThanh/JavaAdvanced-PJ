package com.restaurant.dao;

import com.restaurant.model.entity.MenuItem;
import com.restaurant.model.enums.MenuCategory;
import com.restaurant.util.DBConnection;
import com.restaurant.util.Print;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MenuItemDAO {

    // Thêm món
    public boolean insert(MenuItem item) {
        String sql = "INSERT INTO menu_items(name, category, price, stock, is_available) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, item.getName());
            ps.setString(2, item.getCategory().name());
            ps.setDouble(3, item.getPrice());

            if (item.getStock() != null) {
                ps.setInt(4, item.getStock());
            } else {
                ps.setNull(4, Types.INTEGER);
            }

            ps.setBoolean(5, item.isAvailable());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Cập nhật
    public boolean update(MenuItem item) {
        String sql = "UPDATE menu_items SET name = ?, category = ?, price = ?, stock = ?, is_available = ? WHERE item_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, item.getName());
            ps.setString(2, item.getCategory().name());
            ps.setDouble(3, item.getPrice());

            if (item.getStock() != null) {
                ps.setInt(4, item.getStock());
            } else {
                ps.setNull(4, Types.INTEGER);
            }

            ps.setBoolean(5, item.isAvailable());
            ps.setInt(6, item.getItemId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            Print.redText("Lỗi cập nhật món!");
        }
        return false;
    }


    // Xóa món
    public boolean delete(int id) {
        String sql = "DELETE FROM menu_items WHERE item_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Lấy tất cả món
    public List<MenuItem> findAll() {
        List<MenuItem> list = new ArrayList<>();
        String sql = "SELECT * FROM menu_items";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                MenuItem item = mapResultSet(rs);
                list.add(item);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // Tìm theo ID
    public MenuItem findById(int id) {
        String sql = "SELECT * FROM menu_items WHERE item_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapResultSet(rs);
            }

        } catch (Exception e) {
            Print.redText("Lỗi tìm kiếm!");
        }

        return null;
    }

    public List<MenuItem> searchByName(String keyword) {
        List<MenuItem> list = new ArrayList<>();
        String sql = "SELECT * FROM menu_items WHERE LOWER(name) LIKE LOWER(?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + keyword + "%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapResultSet(rs));
            }

        } catch (Exception e) {
            Print.redText("Lỗi tìm kiếm!");
        }

        return list;
    }


    // Mapping ResultSet → Object
    private MenuItem mapResultSet(ResultSet rs) throws SQLException {
        MenuItem item = new MenuItem();

        item.setItemId(rs.getInt("item_id"));
        item.setName(rs.getString("name"));
        item.setCategory(MenuCategory.valueOf(rs.getString("category")));
        item.setPrice(rs.getDouble("price"));

        int stock = rs.getInt("stock");
        if (rs.wasNull()) {
            item.setStock(null);
        } else {
            item.setStock(stock);
        }

        item.setAvailable(rs.getBoolean("is_available"));

        return item;
    }
}
