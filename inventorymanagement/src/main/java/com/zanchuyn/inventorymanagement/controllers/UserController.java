package com.zanchuyn.inventorymanagement.controllers;

import com.zanchuyn.inventorymanagement.dtos.UserDto;
import com.zanchuyn.inventorymanagement.dtos.request.PasswordRequest;
import com.zanchuyn.inventorymanagement.dtos.request.UserRequest;
import com.zanchuyn.inventorymanagement.entities.User;
import com.zanchuyn.inventorymanagement.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/account")
public class UserController {

    private final UserService userService;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @PostMapping("/update-password")
    @ResponseBody
    public ResponseEntity<?> updatePassword(@RequestBody PasswordRequest passwordRequest, HttpSession session) {
        User user = (User) session.getAttribute("user");
        System.out.println(user);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("/auth/");
        } else {
            boolean isSuccess = userService.updatePassword(passwordRequest, user);
            return ResponseEntity.ok(isSuccess);
        }
    }

    @GetMapping("/detail/")
    @ResponseBody
    public ResponseEntity<?> getCurrentUser(HttpSession session) {
        User user = (User) session.getAttribute("user");
        System.out.println(user);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("/auth/");
        } else {
            UserDto userDto = userService.findUserById(user.getUserId());
            return ResponseEntity.ok(userDto);
        }
    }

    @PutMapping("/update/")
    public ResponseEntity<?> updateUser(@RequestBody UserRequest userRequest, HttpSession session) {
        User user = (User) session.getAttribute("user");
        System.out.println(user);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("/auth/");
        } else {
            userService.updateUser(user.getUserId(), userRequest);
            return ResponseEntity.ok("account_detail.html");
        }
    }
}
