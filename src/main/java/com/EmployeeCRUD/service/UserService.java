package com.EmployeeCRUD.service;

import com.EmployeeCRUD.models.User;
import java.util.HashMap;
import java.util.Map;

public class UserService {
    private static final Map<String, User> users = new HashMap<>();

    static {
        users.put("lopa", new User("lopa", "lopa@3", true)); // Admin user
        users.put("user", new User("user", "pw2", false)); // Regular user
    }

    public User authenticate(String username, String password) {
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}
