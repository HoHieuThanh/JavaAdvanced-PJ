package com.restaurant.presentation.manager;

import com.restaurant.model.entity.MenuItem;
import com.restaurant.service.MenuService;
import com.restaurant.util.InputValidate;
import com.restaurant.util.Print;

import java.util.List;
import java.util.Scanner;

public class MenuManagement {
    static MenuService menuService = new MenuService();
    Scanner scanner = new Scanner(System.in);

    void showMenuItems() {
        List<MenuItem> list = menuService.getAll();
        if (list.isEmpty()){
            Print.blueText("===== THỰC ĐƠN TRỐNG =====");
            return;
        }

        System.out.println("\n==================================== THỰC ĐƠN =====================================");
        displayListItem(list);
    }

    void addMenuItem() {
        MenuItem item = MenuForm.inputMenuItem(scanner, "THÊM MÓN", null);
        menuService.addItem(item);
    }


    void updateMenuItem() {
        int id = InputValidate.getInteger(scanner, "Nhập ID món cần sửa: ");
        MenuItem item = menuService.findById(id);
        if (item == null) {
            System.out.println("Không tìm thấy món!");
            return;
        }
        // Thông tin cũ
        System.out.println("\n--- Thông tin hiện tại ---");
        System.out.println("Tên: " + item.getName());
        System.out.println("Giá: " + item.getPrice());
        System.out.println("Loại: " + item.getCategory());
        System.out.println("Tồn kho: " + item.getStock());
        System.out.println("Trạng thái: " + item.isAvailable());

        MenuItem itemUpdate = MenuForm.inputMenuItem(scanner, "SỬA MÓN", item);
        menuService.updateItem(id, itemUpdate);
    }


    void deleteMenuItem() {
        int id = InputValidate.getInteger(scanner, "Nhập ID món cần xóa: ");
        MenuItem item = menuService.findById(id);
        if (item == null) {
            System.out.println("Không tìm thấy món!");
            return;
        }
        System.out.println("Bạn có chắc muốn xóa món: " + item.getName() + " ?");
        while (true) {
            int confirm = InputValidate.getInteger(scanner, "Nhập 1 (Đồng ý) / 0 (Hủy):  ");
            if (confirm == 0) {
                Print.greenText("Đã hủy thao tác!");
                return;
            } else if (confirm == 1){
                break;
            }else {
                Print.yellowText("Lựa chọn không hợp lệ !");
            }
        }
        menuService.deleteItem(id);
    }

    void searchMenuItem() {
        String keyword;
        while (true) {
             keyword = InputValidate.getString(scanner, "Nhập tên món cần tìm: ");
             if (!keyword.isEmpty()){
                 break;
             }
             Print.yellowText("Từ khoá không được để trống!");
        }
        List<MenuItem> list = menuService.searchByName(keyword);
        if (list == null || list.isEmpty()) {
            Print.yellowText("Không tìm thấy món nào!");
            return;
        }
        System.out.println("================================ KẾT QUẢ TÌM KIẾM ================================");
        displayListItem(list);
    }

    void displayListItem(List<MenuItem> list) {
        String line = "+------+----------------------+------------+------------+------------+------------+";
        System.out.printf("| %-4s | %-20s | %-10s | %-10s | %-10s | %-10s |\n",
                "Mã", "Tên", "Loại", "Giá", "Stock", "Trạng thái");
        System.out.println(line);
        for (MenuItem item : list) {
            System.out.printf("| %-4d | %-20s | %-10s | %-10.2f | %-10s | %-10s |\n",
                    item.getItemId(),
                    item.getName(),
                    item.getCategory(),
                    item.getPrice(),
                    item.getStock() == null ? "-" : item.getStock(),
                    item.isAvailable() ? "Có bán" : "Ngừng"
            );
        System.out.println(line);
        }
    }
}
