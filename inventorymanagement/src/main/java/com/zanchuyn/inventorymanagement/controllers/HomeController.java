package com.zanchuyn.inventorymanagement.controllers;

import com.zanchuyn.inventorymanagement.entities.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String getHomePage(HttpSession session) {
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/login.html";
        } else {
            return "redirect:/index.html";
        }
    }
}
