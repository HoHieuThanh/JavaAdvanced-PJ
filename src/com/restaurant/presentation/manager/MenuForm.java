package com.restaurant.presentation.manager;

import com.restaurant.model.entity.MenuItem;
import com.restaurant.model.enums.MenuCategory;
import com.restaurant.util.InputValidate;
import com.restaurant.util.Print;

import java.util.Scanner;

public class MenuForm {

    public static MenuItem inputMenuItem(Scanner scanner, String title, MenuItem oldItem) {

        System.out.println("\n===== " + title + " =====");

        boolean isUpdate = (oldItem != null);


        // Tên
        String name;
        while (true) {
            name = InputValidate.getString(scanner,"Tên món: " );
            if (isUpdate && name.isEmpty()) {
                name = oldItem.getName();
                break;
            }
            if (!name.isEmpty()) break;
            Print.yellowText("Tên món không được để trống!");
        }

        // Giá
        double price;
        while (true) {
            String input = InputValidate.getString(scanner, "Giá: ");
            if (isUpdate && input.isEmpty()) {
                price = oldItem.getPrice();
                break;
            }
            try {
                price = Double.parseDouble(input);
                if (price <= 0) {
                    Print.yellowText("Giá phải lớn hơn 0!");
                    continue;
                }
                break;
            } catch (Exception ignored) {
                Print.yellowText("Vui lòng nhập vào 1 số!");
            }
        }

        // Loại
        int categoryChoice;
        while (true) {
            System.out.println("""
                    Chọn loại:
                    1. FOOD
                    2. DRINK
                    """);
            String input = InputValidate.getString(scanner, "Lựa chọn: ");
            if (isUpdate && input.isEmpty()) {
                categoryChoice = oldItem.getCategory() == MenuCategory.FOOD ? 1 : 2;
                break;
            }

            try {
                categoryChoice = Integer.parseInt(input);
                if (categoryChoice == 1 || categoryChoice == 2) break;
                Print.yellowText("Chỉ chọn 1 hoặc 2!");
            } catch (Exception ignored) {
                Print.yellowText("Lựa chọn không hợp lệ!");
            }
        }

        // ===== STOCK =====
        Integer stock = null;
        if (categoryChoice == 2) {

            while (true) {
                String input =InputValidate.getString(scanner, "Tồn kho: ");

                if (isUpdate && input.isEmpty()) {
                    stock = oldItem.getStock();
                    break;
                }

                try {
                    int s = Integer.parseInt(input);
                    if (isUpdate) {
                        if (s >= 0) {
                            stock = s;
                            break;
                        }
                        Print.yellowText("Số lượng tồn kho phải lớn hơn hoặc bằng 0!");
                    }else {
                        if (s>0) {
                            stock = s;
                            break;
                        }
                        Print.yellowText("Số lượng tồn kho phải lớn hơn 0!");
                    }
                } catch (Exception ignored) {
                    Print.yellowText("Số lượng không hợp lệ!");
                }
            }
        }

        // ===== AVAILABLE =====
        boolean isAvailable;
        while (true) {
            String input = InputValidate.getString(scanner, "Có sẵn không? (1: Có, 0: Không): ");

            if (isUpdate && input.isEmpty()) {
                isAvailable = oldItem.isAvailable();
                break;
            }

            if ("1".equals(input)) {
                isAvailable = true;
                break;
            }
            if ("0".equals(input)) {
                isAvailable = false;
                break;
            }

            Print.yellowText("Chỉ nhập 1 hoặc 0!");
        }

        // ===== CREATE OBJECT =====
        MenuItem item = new MenuItem();
        if (isUpdate) {
            item.setItemId(oldItem.getItemId());
        }
        item.setName(name);
        item.setPrice(price);
        item.setCategory(categoryChoice == 1 ? MenuCategory.FOOD : MenuCategory.DRINK);
        item.setStock(stock);
        item.setAvailable(isAvailable);

        return item;
    }
}
