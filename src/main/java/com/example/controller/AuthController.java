package com.example.controller;

import com.example.entiy.User;
import com.example.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Map<String, String> login(@RequestParam String name, @RequestParam String pwd, HttpSession session) {
        User user = userService.login(name, pwd);
        Map<String, String> response = new HashMap<>();
        if (user != null) {
            session.setAttribute("user", user); // 将用户信息存储到会话中
            response.put("message", "Login successful");
        } else {
            response.put("message", "Login failed");
        }
        return response;
    }

    @GetMapping("/check")
    public Map<String, String> checkLogin(HttpSession session) {
        Map<String, String> response = new HashMap<>();
        if (session.getAttribute("user") != null) {
            response.put("message", "Authenticated");
        } else {
            response.put("message", "Not authenticated");
        }
        return response;
    }

    @PostMapping("/logout")
    public Map<String, String> logout(HttpSession session) {
        session.invalidate();
        Map<String, String> response = new HashMap<>();
        response.put("message", "Logout successful");
        return response;
    }
}