package com.restaurant.presentation.manager;

import com.restaurant.model.entity.RestaurantTable;
import com.restaurant.model.enums.TableStatus;
import com.restaurant.service.TableService;
import com.restaurant.util.InputValidate;
import com.restaurant.util.Print;

import java.util.Scanner;

public class TableForm {

    public static RestaurantTable inputTable(
            Scanner scanner,
            String title,
            RestaurantTable oldTable,
            TableService tableService
    ) {

        System.out.println("\n===== " + title + " =====");

        boolean isUpdate = (oldTable != null);

        // ===== SỐ BÀN =====
        int tableNumber;
        while (true) {
            String input = InputValidate.getString(scanner, "Số bàn: ");

            if (isUpdate && input.isEmpty()) {
                tableNumber = oldTable.getTableNumber(); // giữ nguyên
                break;
            }

            try {
                tableNumber = Integer.parseInt(input);
                // check trùng
                RestaurantTable exist = tableService.findByTableNumber(tableNumber);
                if (tableNumber <= 0){
                    Print.yellowText("Số bàn phải lớn hơn 0!");
                    continue;
                }
                else if (!isUpdate){
                    if (exist!=null){
                        Print.yellowText("Số bàn đã tồn tại!");
                        continue;
                    }
                }
                break;

            } catch (Exception e) {
                Print.yellowText("Vui lòng nhập số hợp lệ!");
            }
        }

        // ===== SỨC CHỨA =====
        int capacity;
        while (true) {
            String input = InputValidate.getString(scanner, "Sức chứa: ");

            if (isUpdate && input.isEmpty()) {
                capacity = oldTable.getCapacity(); // giữ nguyên
                break;
            }

            try {
                capacity = Integer.parseInt(input);

                if (capacity <= 0) {
                    Print.yellowText("Sức chứa phải lớn hơn 0!");
                    continue;
                }

                break;

            } catch (Exception e) {
                Print.yellowText("Vui lòng nhập số hợp lệ!");
            }
        }

        // ===== TRẠNG THÁI =====
        TableStatus status = TableStatus.AVAILABLE;
        if (isUpdate) {
            while (true) {
                System.out.println("""
                        Trạng thái:
                        1. AVAILABLE
                        2. OCCUPIED
                        3. RESERVED
                        """);

                String input = InputValidate.getString(scanner, "Lựa chọn: ");

                if (isUpdate && input.isEmpty()) {
                    status = oldTable.getStatus();
                    break;
                }

                try {
                    int choice = Integer.parseInt(input);

                    switch (choice) {
                        case 1:
                            status = TableStatus.AVAILABLE;
                            break;
                        case 2:
                            status = TableStatus.OCCUPIED;
                            break;
                        case 3:
                            status = TableStatus.RESERVED;
                            break;
                        default:
                            Print.yellowText("Chỉ chọn 1-3!");
                            continue;
                    }

                    break;

                } catch (Exception e) {
                    Print.yellowText("Lựa chọn không hợp lệ!");
                }
            }
        }

        RestaurantTable table = new RestaurantTable();
        if (isUpdate) {
            table.setId(oldTable.getId());
        }
        table.setTableNumber(tableNumber);
        table.setCapacity(capacity);
        table.setStatus(status);
        return table;
    }
}
