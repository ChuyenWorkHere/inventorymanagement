package com.zanchuyn.inventorymanagement.controllers;

import com.zanchuyn.inventorymanagement.dtos.ImportIssueDetailDto;
import com.zanchuyn.inventorymanagement.entities.User;
import com.zanchuyn.inventorymanagement.services.ImportIssueDetailService;
import com.zanchuyn.inventorymanagement.services.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ImportIssueDetailService detailService;

    @ResponseBody
    @GetMapping("/")
    public ResponseEntity<?> getAllProduct(HttpSession session) {
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("/auth/");
        } else {
            return ResponseEntity.ok(detailService.findAllDetailByStatusAndProductId("SUCCESS"));
        }
    }

    @ResponseBody
    @GetMapping("/success/{id}")
    public List<ImportIssueDetailDto> getAllProductDesc(@PathVariable("id") Integer id) {
        return detailService.findAllDetailByStatusAndProductId("SUCCESS", id);
    }

}
