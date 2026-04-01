package com.restaurant.service;

import com.restaurant.dao.UserDAO;
import com.restaurant.model.entity.User;
import com.restaurant.util.PasswordHasher;
import com.restaurant.util.Print;

public class AuthService {

    private UserDAO userDAO = new UserDAO();

    // Đăng ký (Customer)
    public void registerCustomer(User user) {
        user.setPassword(PasswordHasher.hash(user.getPassword()));
        boolean result = userDAO.insert(user);

        if (result) {
            Print.greenText("Đăng ký tài khoản thành công!");
        } else {
            Print.redText("Đăng ký thất bại!");
        }

    }



    // Đăng nhập
    public User login(String username, String password) {

        User user = userDAO.findByUsername(username);

        if (user == null) {
            Print.yellowText("Không tìm thấy tài khoản!");
            return null;
        }

        if (!user.isActive()) {
            Print.redText("Tài khoản đã bị khóa!");
            return null;
        }

        // Check password
        boolean isValid = PasswordHasher.verify(password, user.getPassword());

        if (!isValid) {
            Print.redText("Sai mật khẩu!");
            return null;
        }
        return user;
    }
}
