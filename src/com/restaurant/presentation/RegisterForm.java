package com.restaurant.presentation;

import com.restaurant.dao.UserDAO;
import com.restaurant.model.entity.User;
import com.restaurant.model.enums.UserRole;
import com.restaurant.util.InputValidate;
import com.restaurant.util.Print;
import com.restaurant.util.ValidatorUtil;

import java.util.Scanner;

public class RegisterForm {

    Scanner scanner = new Scanner(System.in);
    public User registerForm (UserDAO userDAO){
        String username;
        while (true) {
            username = InputValidate.getString(scanner, "Nhập tên đăng nhập: ");

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

        String fullName;
        while (true) {
            fullName = InputValidate.getString(scanner,"Nhập họ tên: " );

            if (ValidatorUtil.isEmpty(fullName)) {
                Print.yellowText("Họ tên không được để trống!");
                continue;
            }

            break;
        }

        String email;
        while (true) {
            email = InputValidate.getString(scanner,"Nhập email: " );

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
            phone = InputValidate.getString(scanner, "Nhập số điện thoại: ");

            if (ValidatorUtil.isEmpty(phone)) {
                Print.yellowText("Số điện thoại không được để trống!");
                continue;
            }

            if (!ValidatorUtil.isValidPhone(phone)) {
                Print.yellowText("Số điện thoại phải gồm đúng 10 chữ số!");
                continue;
            }

            if (userDAO.findByPhone(phone) != null) {
                Print.yellowText("Số điện thoại đã được sử dụng!");
                continue;
            }

            break;
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPhone(phone);
        user.setRole(UserRole.CUSTOMER);
        user.setActive(true);

        return user;
    }
}
