package com.restaurant.service;

import com.restaurant.dao.UserDAO;
import com.restaurant.model.entity.User;
import com.restaurant.model.enums.UserRole;
import com.restaurant.util.PasswordHasher;
import com.restaurant.util.Print;
import java.util.List;

public class ManagerService {
    UserDAO userDAO = new UserDAO();

    public boolean createStaff(User user) {
        user.setPassword(PasswordHasher.hash(user.getPassword()));
        return userDAO.insert(user);
    }

    public List<User> getByRole(UserRole role) {
        return userDAO.findByRole(role);
    }

    public boolean lockUser(int userId) {

        User user = userDAO.findById(userId);

        if (user == null) {
            Print.redText("Không tìm thấy user!");
            return false;
        }

        user.setActive(false);

        return userDAO.updateStatus(userId, false);
    }


}
