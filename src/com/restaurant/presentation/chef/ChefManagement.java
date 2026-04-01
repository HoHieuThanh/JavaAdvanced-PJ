package com.restaurant.presentation.chef;

import com.restaurant.model.dto.KitchenItemDTO;
import com.restaurant.model.entity.OrderItem;
import com.restaurant.service.ChefService;
import com.restaurant.util.InputValidate;
import com.restaurant.util.Print;

import java.util.List;
import java.util.Scanner;

public class ChefManagement {
    Scanner scanner = new Scanner(System.in);
    ChefService chefService =  new ChefService();
    void showKitchen() {
        List<KitchenItemDTO> list = chefService.getKitchenItems();

        if (list.isEmpty()) {
            Print.yellowText("Không có món nào!");
            return;
        }

        System.out.println("\n===== DANH SÁCH MÓN TRONG BẾP =====");

        String line = "+------+--------+----------------------+----------------------+----------+------------+";
        System.out.println(line);

        System.out.printf("| %-4s | %-6s | %-20s | %-20s | %-8s | %-10s |\n",
                "ID", "Order", "Khách hàng", "Món", "SL", "Trạng thái");

        System.out.println(line);

        for (KitchenItemDTO item : list) {
            System.out.printf("| %-4d | %-6d | %-20s | %-20s | %-8d | %-10s |\n",
                    item.getOrderItemId(),
                    item.getOrderId(),
                    item.getCustomerName(),
                    item.getItemName(),
                    item.getQuantity(),
                    item.getStatus()
            );
        }

        System.out.println(line);
    }


    void updateStatus() {

        int id = InputValidate.getInteger(scanner, "Nhập ID món: ");

        chefService.updateStatus(id);
    }
}

