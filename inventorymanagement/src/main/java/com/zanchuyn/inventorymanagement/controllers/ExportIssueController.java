package com.zanchuyn.inventorymanagement.controllers;


import com.zanchuyn.inventorymanagement.dtos.ExportIssueDto;
import com.zanchuyn.inventorymanagement.dtos.request.ExportIssueRequest;
import com.zanchuyn.inventorymanagement.entities.ExportIssue;
import com.zanchuyn.inventorymanagement.entities.User;
import com.zanchuyn.inventorymanagement.services.ExportIssueService;
import com.zanchuyn.inventorymanagement.services.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/management/export")
public class ExportIssueController {

    @Autowired
    private ExportIssueService exportIssueService;

    @Autowired
    private ProductService productService;


    @ResponseBody
    @PostMapping("/add/")
    public ResponseEntity<?> addImportIssue(@RequestBody ExportIssueRequest issueRequest, HttpSession session) {
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("/auth/");
        } else if (currentUser.getRole().equals("QUANTRI")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("index.html");
        } else {
            exportIssueService.addExportIssue(issueRequest, currentUser);
            return ResponseEntity.status(HttpStatus.CREATED).body("export_issue.html");
        }

    }

    @ResponseBody
    @GetMapping("/all/")
    public ResponseEntity<?> findAllWaitingIssue(HttpSession session) {
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("/auth/");
        } else {
            if (!currentUser.getRole().equals("QUANLY")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("index.html");
            } else {
                return ResponseEntity.ok(exportIssueService.findAllIssueByStatus("WAITING"));
            }
        }
    }

    @ResponseBody
    @PostMapping("/{id}")
    public ResponseEntity<?> approveIssue(@RequestParam(name = "action") String action
            , @PathVariable Integer id, HttpSession session) {
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("/auth/");
        } else {
            if (!currentUser.getRole().equals("QUANLY")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("index.html");
            } else {
                if (action.equalsIgnoreCase("approve")) {
                    ExportIssue issue = exportIssueService.findById(id);
                    if (issue != null) {
                        issue.setStatus("SUCCESS");
                    }
                    ExportIssue exportIssue = exportIssueService.updateExportIssue(issue);
                } else if (action.equalsIgnoreCase("reject")) {
                    ExportIssue issue = exportIssueService.findById(id);
                    if (issue != null) {
                        issue.setStatus("REJECTED");
                    }
                    ExportIssue exportIssue = exportIssueService.updateExportIssue(issue);
                }
                return ResponseEntity.ok("accept_export.html");
            }
        }
    }

    @ResponseBody
    @GetMapping("/history/")
    public ResponseEntity<?> findAllHistoricIssue(HttpSession session) {
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("/auth/");
        } else {
            if (!currentUser.getRole().equals("QUANLY")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("index.html");
            } else {
                List<ExportIssue> successIssue = exportIssueService.findAllIssueByStatus("SUCCESS");
                List<ExportIssue> rejectedIssue = exportIssueService.findAllIssueByStatus("REJECTED");
                List<ExportIssue> allHistoricIssues = new ArrayList<>();
                allHistoricIssues.addAll(successIssue);
                allHistoricIssues.addAll(rejectedIssue);
                return ResponseEntity.ok(allHistoricIssues);
            }
        }
    }

    @ResponseBody
    @GetMapping("/{id}")
    public ResponseEntity<?> findImportIssueDetail(@PathVariable Integer id, HttpSession session) {
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("/auth/");
        } else {
            if (!currentUser.getRole().equals("QUANLY")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("index.html");
            } else {
                ExportIssueDto exportIssueDto = exportIssueService.findExportIssueDetail(id);
                return ResponseEntity.ok(exportIssueDto);
            }
        }
    }
}
