package com.restaurant.presentation;

import com.restaurant.dao.UserDAO;
import com.restaurant.model.entity.User;
import com.restaurant.presentation.chef.ChefUI;
import com.restaurant.presentation.manager.ManagerUI;
import com.restaurant.service.AuthService;
import com.restaurant.util.InputValidate;
import com.restaurant.util.Print;
import com.restaurant.util.ValidatorUtil;
import com.restaurant.presentation.customer.CustomerUI;

import java.util.Scanner;

public class AuthUI {

    private AuthService authService = new AuthService();
    private UserDAO userDAO = new UserDAO();
    private Scanner scanner = new Scanner(System.in);

    public void register() {

        System.out.println("===== ĐĂNG KÝ KHÁCH HÀNG =====");

        User user = new RegisterForm().registerForm(userDAO);
        authService.registerCustomer(user);
    }

    public void login() {

        System.out.println("===== ĐĂNG NHẬP =====");

        String username;

        while (true) {
            username = InputValidate.getString(scanner, "Nhập tên đăng nhập: ");

            if (ValidatorUtil.isEmpty(username)) {
                Print.yellowText("Tên đăng nhập không được để trống!");
                continue;
            }

            break;
        }

        String password;

        while (true) {
            password = InputValidate.getString(scanner, "Nhập mật khẩu: ");

            if (ValidatorUtil.isEmpty(password)) {
                Print.yellowText("Mật khẩu không được để trống!");
                continue;
            }

            if (!ValidatorUtil.isValidPassword(password)) {
                Print.yellowText("Mật khẩu phải có ít nhất 6 ký tự!");
                continue;
            }

            break;
        }

        User user = authService.login(username, password);
        if (user == null) {
            return;
        }
        Print.greenText("\nĐăng nhập thành công! Xin chào: " + user.getFullName());

        switch (user.getRole()) {
            case MANAGER:
                Print.blueText("Bạn đang đăng nhập với quyền QUẢN LÝ");
                new ManagerUI().menu();
                break;

            case CHEF:
                Print.blueText("Bạn đang đăng nhập với quyền ĐẦU BẾP");
                new ChefUI().menu();
                break;

            case CUSTOMER:
                Print.blueText("Bạn đang đăng nhập với quyền KHÁCH HÀNG");
                new CustomerUI(user).menu();
                break;

        }
    }
}
