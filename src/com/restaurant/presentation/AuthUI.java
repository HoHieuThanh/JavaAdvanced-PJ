package com.restaurant.presentation;

import com.restaurant.dao.UserDAO;
import com.restaurant.model.entity.User;
import com.restaurant.presentation.manager.ManagerUI;
import com.restaurant.service.AuthService;
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

        String username;
        while (true) {
            System.out.print("Nhập tên đăng nhập: ");
            username = scanner.nextLine().trim();

            if (ValidatorUtil.isEmpty(username)) {
                Print.yellowText("Tên đăng nhập không được để trống!");
                continue;
            }

            if (userDAO.findByUsername(username) != null) {
                Print.yellowText("Tên đăng nhập đã tồn tại!");
                continue;
            }

            break;
        }

        String password;
        while (true) {
            System.out.print("Nhập mật khẩu: ");
            password = scanner.nextLine();

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

        String fullName;
        while (true) {
            System.out.print("Nhập họ tên: ");
            fullName = scanner.nextLine().trim();

            if (ValidatorUtil.isEmpty(fullName)) {
                Print.yellowText("Họ tên không được để trống!");
                continue;
            }

            break;
        }

        String email;
        while (true) {
            System.out.print("Nhập email: ");
            email = scanner.nextLine().trim();

            if (ValidatorUtil.isEmpty(email)) {
                Print.yellowText("Email không được để trống!");
                continue;
            }

            if (!ValidatorUtil.isValidEmail(email)) {
                Print.yellowText("Email không đúng định dạng!");
                continue;
            }

            if (userDAO.findByEmail(email) != null) {
                Print.yellowText("Email đã được sử dụng!");
                continue;
            }

            break;
        }

        String phone;
        while (true) {
            System.out.print("Nhập số điện thoại: ");
            phone = scanner.nextLine().trim();

            if (ValidatorUtil.isEmpty(phone)) {
                Print.yellowText("Số điện thoại không được để trống!");
                continue;
            }

            if (!ValidatorUtil.isValidPhone(phone)) {
                Print.yellowText("Số điện thoại phải gồm đúng 10 chữ số!");
                continue;
            }

            break;
        }
        authService.registerCustomer(username, password, fullName, email, phone);
    }

    public void login() {

        System.out.println("===== ĐĂNG NHẬP =====");

        String username;

        while (true) {
            System.out.print("Nhập tên đăng nhập: ");
            username = scanner.nextLine().trim();

            if (ValidatorUtil.isEmpty(username)) {
                Print.yellowText("Tên đăng nhập không được để trống!");
                continue;
            }

            break;
        }

        String password;

        while (true) {
            System.out.print("Nhập mật khẩu: ");
            password = scanner.nextLine();

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
            Print.yellowText("Tên đăng nhập hoặc mật khẩu không đúng!");
            return;
        }

        if (!user.isActive()) {
            Print.yellowText("Tài khoản đã bị vô hiệu hóa!");
            return;
        }

        Print.greenText("Đăng nhập thành công! Xin chào: " + user.getFullName());

        switch (user.getRole()) {
            case MANAGER:
                Print.blueText("Bạn đang đăng nhập với quyền QUẢN LÝ");
                new ManagerUI().menu();
                break;

            case CHEF:
                Print.blueText("Bạn đang đăng nhập với quyền ĐẦU BẾP");
                break;

            case CUSTOMER:
                Print.blueText("Bạn đang đăng nhập với quyền KHÁCH HÀNG");
                new CustomerUI(user.getId()).menu();
                break;

        }
    }
}
