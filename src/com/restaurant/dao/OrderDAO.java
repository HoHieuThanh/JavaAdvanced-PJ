package com.restaurant.dao;

import com.restaurant.model.entity.Order;
import com.restaurant.model.enums.OrderStatus;
import com.restaurant.util.DBConnection;
import com.restaurant.util.Print;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    // thêm order
    public int insert(Order order) {
        String sql = "INSERT INTO orders (table_id, user_id, total_amount, status) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, order.getTableId());
            ps.setInt(2, order.getUserId());
            ps.setDouble(3, 0.0);
            ps.setString(4, OrderStatus.PENDING.name());

            int rows = ps.executeUpdate();

            if (rows > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

        } catch (Exception e) {
            Print.redText("Lỗi thêm order!");
        }

        return -1;
    }

    // TÌM THEO ID
    public Order findById(int id) {
        String sql = "SELECT * FROM orders WHERE order_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return map(rs);
            }

        } catch (Exception e) {
            Print.redText("Lỗi tìm order!");
        }

        return null;
    }

    // LẤY THEO USER
    public List<Order> findByUser(int userId) {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE user_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(map(rs));
            }

        } catch (Exception e) {
            Print.redText("Lỗi lấy danh sách order!");
        }

        return list;
    }

    // UPDATE STATUS
    public boolean updateStatus(int orderId, OrderStatus status) {
        String sql = "UPDATE orders SET status = ? WHERE order_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status.name());
            ps.setInt(2, orderId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            Print.redText("Lỗi cập nhật trạng thái order!");
        }

        return false;
    }

    // tổng tiền
    public boolean updateTotal(int orderId, double total) {
        String sql = "UPDATE orders SET total_amount = ? WHERE order_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDouble(1, total);
            ps.setInt(2, orderId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            Print.redText("Lỗi cập nhật tổng tiền!");
        }

        return false;
    }

    // tạo order
    private Order map(ResultSet rs) throws SQLException {
        Order o = new Order();
        o.setOrderId(rs.getInt("order_id"));
        o.setTableId(rs.getInt("table_id"));
        o.setUserId(rs.getInt("user_id"));
        o.setTotalAmount(rs.getDouble("total_amount"));
        o.setStatus(OrderStatus.valueOf(rs.getString("status")));
        o.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        return o;
    }
}
