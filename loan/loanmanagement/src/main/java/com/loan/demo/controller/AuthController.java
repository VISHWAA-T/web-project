package com.loan.demo.controller;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.loan.demo.model.User;
import com.loan.demo.service.AuthService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://127.0.0.1:5500", allowCredentials = "true")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> data, HttpSession session) {
        String username = data.get("username");
        String password = data.get("password");

        Optional<User> userOpt = authService.authenticate(username, password);
        Map<String, Object> response = new HashMap<>();

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            session.setAttribute("userId", user.getId());
            session.setAttribute("role", user.getRole());

            response.put("success", true);
            response.put("userId", user.getId());
            response.put("role", user.getRole());
        } else {
            response.put("success", false);
            response.put("message", "Invalid credentials");
        }

        return response;
    }


    @PostMapping("/logout")
    public Map<String, Object> logout(HttpSession session) {
        session.invalidate();
        return Map.of("success", true, "message", "Logged out successfully");
    }

    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody Map<String, String> data) {
        String username = data.get("username");
        String password = data.get("password");
        String role = data.get("role");

        User user = authService.registerUser(username, password, role);
        return Map.of("success", true, "message", "User registered", "userId", user.getId());
    }
}
