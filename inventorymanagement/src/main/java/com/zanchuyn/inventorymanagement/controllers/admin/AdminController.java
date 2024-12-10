package com.zanchuyn.inventorymanagement.controllers.admin;

import com.zanchuyn.inventorymanagement.dtos.UserDto;
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

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/admin/management")
public class AdminController {

    private final UserService userService;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String getHomePage(HttpSession session) throws IOException {
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/auth/";
        } else if (!currentUser.getRole().equals("QUANTRI")) {
            return "redirect:/error.html";
        } else {
            return "forward:/admin/management/accounts/";
        }
    }

    @ResponseBody
    @GetMapping("/accounts/")
    public ResponseEntity<?> findAllUsers(HttpSession session) {
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("/auth/");
        } else if (!currentUser.getRole().equals("QUANTRI")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("error.html");
        } else {
            List<UserDto> listUserDtos = userService.findAll();
            return ResponseEntity.ok(listUserDtos);
        }
    }


    @ResponseBody
    @PostMapping("/add/")
    public ResponseEntity<Object> addUser(@RequestBody UserRequest userRequest, HttpSession session) {
        String username = userRequest.getUsername();
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("/auth/");
        } else if (!currentUser.getRole().equals("QUANTRI")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("error.html");
        } else {
            if (userService.findByUsername(username) != null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tài khoản đã tồn tại");
            } else {
                UserDto userDto = userService.addUser(userRequest);
                return ResponseEntity.status(HttpStatus.OK).body(userDto);
            }
        }
    }

    @ResponseBody
    @GetMapping("/accounts/{id}")
    public ResponseEntity<Object> findUserById(@PathVariable Integer id, HttpSession session) {
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("/auth/");
        } else if (!currentUser.getRole().equals("QUANTRI")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("error.html");
        } else {
            UserDto userDto = userService.findUserById(id);
            if (userDto != null) {
                return ResponseEntity.ok(userDto);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không có tài khoản người dùng được tìm thấy");
            }
        }
    }

    @ResponseBody
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Integer id, @RequestBody UserRequest userRequest, HttpSession session) {

        UserDto userDto = userService.updateUser(id, userRequest);
        if (userDto != null) {
            return ResponseEntity.status(HttpStatus.OK).body("account_detail.html?id=" + id);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
    }
}
