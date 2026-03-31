package com.restaurant.service;

import com.restaurant.dao.UserDAO;
import com.restaurant.model.entity.User;
import com.restaurant.model.enums.UserRole;
import com.restaurant.util.PasswordHasher;
import com.restaurant.util.Print;
import com.restaurant.util.ValidatorUtil;

public class AuthService {

    private UserDAO userDAO = new UserDAO();

    // Đăng ký (Customer)
    public boolean registerCustomer(String username, String password,
                                    String fullName, String email, String phone) {

        User user = new User();
        user.setUsername(username);
        user.setPassword(PasswordHasher.hash(password));
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPhone(phone);
        user.setRole(UserRole.CUSTOMER);
        user.setActive(true);

        boolean result = userDAO.insert(user);

        if (result) {
            System.out.println("Đăng ký tài khoản thành công!");
        } else {
            System.out.println("Đăng ký thất bại!");
        }

        return result;
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
