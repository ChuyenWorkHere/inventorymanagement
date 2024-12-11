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
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ImportIssueDetailService detailService;

    @ResponseBody
    @GetMapping("/all/")
    public ResponseEntity<?> getAllProduct(HttpSession session) {
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("/auth/");
        } else {
            return ResponseEntity.ok(detailService.findAllDetailByStatusAndProductId("SUCCESS"));
        }
    }

    @ResponseBody
    @GetMapping("/")
    public ResponseEntity<?> getProducts(HttpSession session) {
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("/auth/");
        } else {
            return ResponseEntity.ok(detailService.findAllProductByStatus("SUCCESS"));
        }
    }

    @ResponseBody
    @GetMapping("/success/{id}")
    public List<ImportIssueDetailDto> getAllProductDesc(@PathVariable("id") Integer id) {
        return detailService.findAllDetailByStatusAndProductId("SUCCESS", id);
    }

    @ResponseBody
    @GetMapping("/filter/")
    public ResponseEntity<?> getFilteredProduct(
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "dateOrder", required = false) String dateOrder
    ) {
        List<ImportIssueDetailDto> issueDetailDtos = detailService.findAllDetailByStatusAndProductId("SUCCESS");


        if (!category.isEmpty()) {
            int cateId = Integer.parseInt(category);
            issueDetailDtos = issueDetailDtos
                    .stream()
                    .filter(item -> item.getProduct().getCategory().getCategoryId() == cateId).toList();

        }
        // Lọc theo status
        if (status != null && !status.isEmpty()) {
            issueDetailDtos = issueDetailDtos.stream()
                    .filter(item -> {
                        if (item.getExpiryDate() == null) return false;
                        long daysRemaining = ChronoUnit.DAYS.between(LocalDate.now(), item.getExpiryDate());
                        if ("good".equalsIgnoreCase(status)) {
                            return daysRemaining > 10; // Tốt: Số ngày còn hạn lớn hơn 10
                        } else if ("expired".equalsIgnoreCase(status)) {
                            return daysRemaining <= 10; // Sắp hết hạn: Số ngày còn hạn nhỏ hơn hoặc bằng 10
                        }
                        return true;
                    })
                    .toList();
        }
        // Lọc theo dateOrder
        if (dateOrder != null && !dateOrder.isEmpty()) {
            Comparator<ImportIssueDetailDto> comparator = Comparator.comparing(item -> item.getIssue().getCreateAt());
            if ("new".equalsIgnoreCase(dateOrder)) {
                issueDetailDtos = issueDetailDtos.stream()
                        .sorted(comparator.reversed()) // Mới nhất
                        .toList();
            } else if ("old".equalsIgnoreCase(dateOrder)) {
                issueDetailDtos = issueDetailDtos.stream()
                        .sorted(comparator) // Cũ nhất
                        .toList();
            }
        }
        return ResponseEntity.ok(issueDetailDtos);
    }
}
