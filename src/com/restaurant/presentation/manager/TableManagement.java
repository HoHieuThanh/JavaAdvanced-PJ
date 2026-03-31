package com.restaurant.presentation.manager;

import com.restaurant.model.entity.RestaurantTable;
import com.restaurant.model.enums.TableStatus;
import com.restaurant.service.TableService;
import com.restaurant.util.InputValidate;
import com.restaurant.util.Print;

import java.util.List;
import java.util.Scanner;

public class TableManagement {
    TableService tableService = new TableService();
    Scanner scanner = new Scanner(System.in);
    
     void showTables() {
        List<RestaurantTable> list = tableService.getAll();

        if (list.isEmpty()) {
            Print.blueText("Không có bàn nào!");
            return;
        }

        System.out.println("\n============== DANH SÁCH BÀN ==============");
        displayListTable(list);
    }


     void addTable() {
         RestaurantTable table = TableForm.inputTable(scanner, "THÊM BÀN", null, tableService);
         tableService.addTable(table.getTableNumber(), table.getCapacity());
     }


     void updateTable() {
         int id = InputValidate.getInteger(scanner, "Nhập ID bàn: ");
         RestaurantTable oldTable = tableService.findById(id);
         if (oldTable == null) {
             Print.yellowText("Không tìm thấy bàn!");
             return;
         }
         RestaurantTable updated = TableForm.inputTable(scanner, "SỬA BÀN", oldTable, tableService);
         tableService.updateTable(updated);
     }

     void deleteTable() {
         int id = InputValidate.getInteger(scanner, "Nhập ID bàn cần xóa: ");

        RestaurantTable t = tableService.findById(id);

        if (t == null) {
            Print.yellowText("Không tìm thấy bàn!");
            return;
        }
        while (true) {
            int confirm = InputValidate.getInteger(scanner,
                    "Bạn có chắc muốn xóa bàn " + t.getTableNumber() + "? (1/0): ");
            if (confirm == 0) {
                Print.greenText("Đã hủy!");
                return;
            }else if (confirm == 1) break;
            else {
                Print.yellowText("Lựa chọn không hợp lệ!");
            }
        }
        tableService.deleteTable(id);
    }

    // tìm bàn theo tt
     void searchTableByStatus() {

         System.out.println("""
                 Chọn trạng thái:
                 1. AVAILABLE
                 2. OCCUPIED
                 3. RESERVED
                 """);
         int choice = InputValidate.getInteger(scanner, "Lựa chọn: ");
        TableStatus status;
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
                Print.invalidSelection();
                return;
        }

        List<RestaurantTable> list = tableService.findByStatus(status);

        if (list.isEmpty()) {
            Print.yellowText("Không tìm thấy bàn nào!");
            return;
        }

         System.out.println("\n================= KẾT QUẢ =================");
        displayListTable(list);

    }

    void displayListTable(List<RestaurantTable> list){
        String line = "|----+------------+----------+------------|";
        System.out.printf("| %-2s | %-10s | %-8s | %-10s |\n",
                "ID", "Số bàn", "Sức chứa", "Trạng thái");
        System.out.println(line);

        for (RestaurantTable t : list) {
            System.out.printf("| %-2d | %-10d | %-8d | %-10s |\n",
                    t.getId(),
                    t.getTableNumber(),
                    t.getCapacity(),
                    t.getStatus());
            System.out.println(line);
        }
     }
}
