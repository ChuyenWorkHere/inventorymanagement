package com.zanchuyn.inventorymanagement.controllers;

import com.zanchuyn.inventorymanagement.dtos.ImportIssueDto;
import com.zanchuyn.inventorymanagement.dtos.request.ImportIssueRequest;
import com.zanchuyn.inventorymanagement.entities.User;
import com.zanchuyn.inventorymanagement.services.ImportIssueService;
import com.zanchuyn.inventorymanagement.services.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/management/import")
public class ImportIssueController {

    @Autowired
    private ImportIssueService importIssueService;

    @Autowired
    private ProductService productService;


    @PostMapping("/add/")
    public ResponseEntity<?> addImportIssue(@RequestBody ImportIssueRequest issueRequest, HttpSession session) {
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("/auth/");
        } else {
            ImportIssueDto importIssueDto = importIssueService.addImportIssue(issueRequest, currentUser);
            return ResponseEntity.status(HttpStatus.CREATED).body("OK");
        }

    }
}
