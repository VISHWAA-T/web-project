package com.loan.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.loan.demo.model.User;
import com.loan.demo.service.UserService;

import java.util.*;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://127.0.0.1:5500", allowCredentials = "true")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public Map<String, Object> createUser(@RequestBody User user) {
        User savedUser = userService.createUser(user);
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("user", savedUser);
        return response;
    }

    @PutMapping("/{id}")
    public Map<String, Object> updateUser(@PathVariable Long id, @RequestBody User user) {
        Map<String, Object> response = new HashMap<>();
        Optional<User> updated = userService.updateUser(id, user);
        if (updated.isPresent()) {
            response.put("success", true);
            response.put("user", updated.get());
        } else {
            response.put("success", false);
            response.put("message", "User not found");
        }
        return response;
    }

    @DeleteMapping("/{id}")
    public Map<String, Object> deleteUser(@PathVariable Long id) {
        boolean deleted = userService.deleteUser(id);
        Map<String, Object> response = new HashMap<>();
        if (deleted) {
            response.put("success", true);
            response.put("message", "User deleted");
        } else {
            response.put("success", false);
            response.put("message", "User not found");
        }
        return response;
    }
}

