package com.zanchuyn.inventorymanagement.controllers;

import com.zanchuyn.inventorymanagement.dtos.ImportIssueDetailDto;
import com.zanchuyn.inventorymanagement.dtos.ProductDto;
import com.zanchuyn.inventorymanagement.services.ImportIssueDetailService;
import com.zanchuyn.inventorymanagement.services.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<ProductDto> getAllProduct(HttpSession session) {
        return detailService.findAllProductByStatus("SUCCESS");
    }

    @ResponseBody
    @GetMapping("/success/{id}")
    public List<ImportIssueDetailDto> getAllProductDesc(@PathVariable("id") Integer id) {
        return detailService.findAllDetailByStatusAndProductId("SUCCESS", id);
    }

}
