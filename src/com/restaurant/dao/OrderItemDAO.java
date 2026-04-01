package com.restaurant.dao;

import com.restaurant.model.dto.KitchenItemDTO;
import com.restaurant.model.dto.OrderDetailDTO;
import com.restaurant.model.entity.OrderItem;
import com.restaurant.model.enums.OrderItemStatus;
import com.restaurant.util.DBConnection;
import com.restaurant.util.Print;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderItemDAO {

    // THÊM MÓN
    public boolean insert(OrderItem item) {
        String sql = "INSERT INTO order_items (order_id, item_id, quantity, status) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, item.getOrderId());
            ps.setInt(2, item.getItemId());
            ps.setInt(3, item.getQuantity());
            ps.setString(4, item.getStatus().name());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            Print.redText("Lỗi thêm món vào order!");
        }

        return false;
    }

    // LẤY THEO ORDER
    public List<OrderItem> findByOrderId(int orderId) {
        List<OrderItem> list = new ArrayList<>();
        String sql = "SELECT * FROM order_items WHERE order_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(map(rs));
            }

        } catch (Exception e) {
            Print.redText("Lỗi lấy danh sách món!");
        }

        return list;
    }

    // HIỂN THỊ TÊN MÓN
    public void printOrderDetail(int orderId) {
        String sql = """
                SELECT oi.order_item_id, m.name, oi.quantity, oi.status
                FROM order_items oi
                JOIN menu_items m ON oi.item_id = m.item_id
                WHERE oi.order_id = ?
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();

            System.out.printf("%-5s %-20s %-10s %-15s\n",
                    "ID", "Tên món", "SL", "Trạng thái");

            while (rs.next()) {
                System.out.printf("%-5d %-20s %-10d %-15s\n",
                        rs.getInt("order_item_id"),
                        rs.getString("name"),
                        rs.getInt("quantity"),
                        rs.getString("status"));
            }

        } catch (Exception e) {
            Print.redText("Lỗi hiển thị chi tiết order!");
        }
    }

    // TÌM THEO ID
    public OrderItem findById(int id) {
        String sql = "SELECT * FROM order_items WHERE order_item_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return map(rs);
            }

        } catch (Exception e) {
            Print.redText("Lỗi tìm order item!");
        }

        return null;
    }

    // UPDATE STATUS
    public boolean updateStatus(int orderItemId, OrderItemStatus status) {
        String sql = "UPDATE order_items SET status = ? WHERE order_item_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status.name());
            ps.setInt(2, orderItemId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            Print.redText("Lỗi cập nhật trạng thái món!");
        }

        return false;
    }

    // TÍNH TỔNG TIỀN ORDER
    public double calculateTotal(int orderId) {
        String sql = """
        SELECT SUM(oi.quantity * m.price) AS total
        FROM order_items oi
        JOIN menu_items m ON oi.item_id = m.item_id
        WHERE oi.order_id = ? AND oi.status != 'CANCELLED'
    """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, orderId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getDouble("total");
            }

        } catch (Exception e) {
            Print.redText("Lỗi tính tổng tiền!");
        }

        return 0;
    }

    // Hiển thị hoá đơn cho khách hàng
    public List<OrderDetailDTO> findOrderDetail(int orderId) {

        List<OrderDetailDTO> list = new ArrayList<>();

        String sql = """
        SELECT 
            oi.order_item_id,
            m.name,
            oi.quantity,
            m.price,
            (oi.quantity * m.price) AS total,
            oi.status
        FROM order_items oi
        JOIN menu_items m ON oi.item_id = m.item_id
        WHERE oi.order_id = ?
    """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, orderId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                OrderDetailDTO dto = new OrderDetailDTO();

                dto.setOrderItemId(rs.getInt("order_item_id"));
                dto.setItemName(rs.getString("name"));
                dto.setQuantity(rs.getInt("quantity"));
                dto.setPrice(rs.getDouble("price"));
                dto.setTotal(rs.getDouble("total"));
                dto.setStatus(rs.getString("status"));

                list.add(dto);
            }

        } catch (Exception e) {
            Print.redText("Lỗi lấy chi tiết đơn!");
        }

        return list;
    }


    // tạo order item
    private OrderItem map(ResultSet rs) throws SQLException {
        OrderItem oi = new OrderItem();
        oi.setOrderItemId(rs.getInt("order_item_id"));
        oi.setOrderId(rs.getInt("order_id"));
        oi.setItemId(rs.getInt("item_id"));
        oi.setQuantity(rs.getInt("quantity"));
        oi.setStatus(OrderItemStatus.valueOf(rs.getString("status")));
        return oi;
    }

    // lấy order cho bếp
    public List<KitchenItemDTO> findAllForKitchen() {
        List<KitchenItemDTO> list = new ArrayList<>();

        String sql = """
        SELECT 
            oi.order_item_id,
            oi.order_id,
            u.full_name,
            m.name,
            oi.quantity,
            oi.status
        FROM order_items oi
        JOIN orders o ON oi.order_id = o.order_id
        JOIN users u ON o.user_id = u.user_id
        JOIN menu_items m ON oi.item_id = m.item_id
        WHERE oi.status != 'SERVED'
        ORDER BY oi.order_item_id
    """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                KitchenItemDTO dto = new KitchenItemDTO();

                dto.setOrderItemId(rs.getInt("order_item_id"));
                dto.setOrderId(rs.getInt("order_id"));
                dto.setCustomerName(rs.getString("full_name"));
                dto.setItemName(rs.getString("name"));
                dto.setQuantity(rs.getInt("quantity"));
                dto.setStatus(rs.getString("status"));

                list.add(dto);
            }

        } catch (Exception e) {
            Print.redText("Lỗi lấy danh sách bếp!");
        }

        return list;
    }


}
