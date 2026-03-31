package com.restaurant.dao;

import com.restaurant.model.entity.RestaurantTable;
import com.restaurant.model.enums.TableStatus;
import com.restaurant.util.DBConnection;
import com.restaurant.util.Print;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TableDAO {

    // Thêm bàn
    public boolean insert(RestaurantTable table) {
        String sql = "INSERT INTO restaurant_tables(table_number, capacity, status) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, table.getTableNumber());
            ps.setInt(2, table.getCapacity());
            ps.setString(3, table.getStatus().name());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Cập nhật
    public boolean update(RestaurantTable table) {
        String sql = "UPDATE restaurant_tables SET table_number = ?, capacity = ?, status = ? WHERE table_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, table.getTableNumber());
            ps.setInt(2, table.getCapacity());
            ps.setString(3, table.getStatus().name());
            ps.setInt(4, table.getId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            Print.redText("Lỗi cập nhật bàn!");
        }
        return false;
    }


    // Cập nhật trạng thái
    public boolean updateStatus(int tableId, TableStatus status) {
        String sql = "UPDATE restaurant_tables SET status = ? WHERE table_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status.name());
            ps.setInt(2, tableId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Xóa bàn
    public boolean delete(int tableId) {
        String sql = "DELETE FROM restaurant_tables WHERE table_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, tableId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Lấy tất cả bàn
    public List<RestaurantTable> findAll() {
        List<RestaurantTable> list = new ArrayList<>();
        String sql = "SELECT * FROM restaurant_tables";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                RestaurantTable table = mapResultSet(rs);
                list.add(table);
            }

        } catch (Exception e) {
            Print.redText("Lỗi kết nối dữ liệu Bàn!");
        }

        return list;
    }

    // Tìm theo ID
    public RestaurantTable findById(int tableId) {
        String sql = "SELECT * FROM restaurant_tables WHERE table_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, tableId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapResultSet(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // Tìm theo số bàn
    public RestaurantTable findByTableNumber(int tableNumber) {
        String sql = "SELECT * FROM restaurant_tables WHERE table_number = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, tableNumber);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapResultSet(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // Tìm theo trạng thái
    public List<RestaurantTable> findByStatus(TableStatus status) {
        List<RestaurantTable> list = new ArrayList<>();
        String sql = "SELECT * FROM restaurant_tables WHERE status = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status.name());

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapResultSet(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }


    // Mapping ResultSet → Object
    private RestaurantTable mapResultSet(ResultSet rs) throws SQLException {
        RestaurantTable table = new RestaurantTable();

        table.setId(rs.getInt("table_id"));
        table.setTableNumber(rs.getInt("table_number"));
        table.setCapacity(rs.getInt("capacity"));
        table.setStatus(TableStatus.valueOf(rs.getString("status")));

        return table;
    }
}

