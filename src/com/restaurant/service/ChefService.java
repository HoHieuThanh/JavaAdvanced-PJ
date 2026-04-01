package com.restaurant.service;

import com.restaurant.dao.OrderItemDAO;
import com.restaurant.model.dto.KitchenItemDTO;
import com.restaurant.model.entity.OrderItem;
import com.restaurant.model.enums.OrderItemStatus;
import com.restaurant.util.Print;

import java.util.List;

public class ChefService {

    private OrderItemDAO orderItemDAO = new OrderItemDAO();

    public List<KitchenItemDTO> getKitchenItems() {
        return orderItemDAO.findAllForKitchen();
    }


    public void updateStatus(int id) {

        OrderItem item = orderItemDAO.findById(id);

        if (item == null) {
            Print.redText("Không tìm thấy món!");
            return;
        }

        OrderItemStatus current = item.getStatus();
        OrderItemStatus next;

        switch (current) {
            case PENDING -> next = OrderItemStatus.COOKING;
            case COOKING -> next = OrderItemStatus.READY;
            case READY -> next = OrderItemStatus.SERVED;
            default -> {
                Print.yellowText("Món đã hoàn thành, không thể cập nhật!");
                return;
            }
        }

        boolean result = orderItemDAO.updateStatus(id, next);

        if (result) {
            Print.greenText("Cập nhật thành công: " + current + " → " + next);
        } else {
            Print.redText("Cập nhật thất bại!");
        }
    }
}
