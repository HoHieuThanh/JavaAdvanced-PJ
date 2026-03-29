package com.restaurant.service;

import com.restaurant.dao.UserDAO;
import com.restaurant.model.entity.User;
import com.restaurant.model.enums.UserRole;
import com.restaurant.util.PasswordHasher;
import com.restaurant.util.ValidatorUtil;

public class AuthService {

    private UserDAO userDAO = new UserDAO();

    // Đăng ký (Customer)
    public boolean registerCustomer(String username, String password,
                                    String fullName, String email, String phone) {

        if (ValidatorUtil.isEmpty(username)) {
            System.out.println("Tên đăng nhập không được để trống!");
            return false;
        }

        if (ValidatorUtil.isEmpty(password)) {
            System.out.println("Mật khẩu không được để trống!");
            return false;
        }

        if (ValidatorUtil.isEmpty(fullName)) {
            System.out.println("Họ tên không được để trống!");
            return false;
        }

        if (ValidatorUtil.isEmpty(email)) {
            System.out.println("Email không được để trống!");
            return false;
        }

        if (ValidatorUtil.isEmpty(phone)) {
            System.out.println("Số điện thoại không được để trống!");
            return false;
        }

        if (!ValidatorUtil.isValidPassword(password)) {
            System.out.println("Mật khẩu phải có ít nhất 6 ký tự!");
            return false;
        }

        if (!ValidatorUtil.isValidEmail(email)) {
            System.out.println("Email không đúng định dạng!");
            return false;
        }

        if (!ValidatorUtil.isValidPhone(phone)) {
            System.out.println("Số điện thoại phải gồm đúng 10 chữ số!");
            return false;
        }

        if (userDAO.findByUsername(username) != null) {
            System.out.println("Tên đăng nhập đã tồn tại!");
            return false;
        }

        if (userDAO.findByEmail(email) != null) {
            System.out.println("Email đã được sử dụng!");
            return false;
        }

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
//            System.out.println("Không tìm thấy tài khoản!");
            return null;
        }

        // Check active
        if (!user.isActive()) {
//            System.out.println("Tài khoản đã bị khóa!");
            return null;
        }

        // Check password
        boolean isValid = PasswordHasher.verify(password, user.getPassword());

        if (!isValid) {
            System.out.println("Sai mật khẩu!");
            return null;
        }

        System.out.println("Đăng nhập thành công!");
        return user;
    }
}
