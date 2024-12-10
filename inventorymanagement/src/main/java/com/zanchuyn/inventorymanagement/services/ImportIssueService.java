package com.zanchuyn.inventorymanagement.services;

import com.zanchuyn.inventorymanagement.dtos.ImportIssueDto;
import com.zanchuyn.inventorymanagement.dtos.request.ImportIssueRequest;
import com.zanchuyn.inventorymanagement.dtos.request.ProductRequest;
import com.zanchuyn.inventorymanagement.entities.ImportIssue;
import com.zanchuyn.inventorymanagement.entities.User;
import com.zanchuyn.inventorymanagement.repositories.ImportIssueRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class ImportIssueService {

    @Autowired
    private ImportIssueRepository importIssueRepository;

    @Autowired
    private ImportIssueDetailService importIssueDetailService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ModelMapper mapper;

    @Transactional
    public ImportIssueDto addImportIssue(ImportIssueRequest issueRequest, User user) {
        List<ProductRequest> productRequestList = issueRequest.getListProduct();

        ImportIssue issue = ImportIssue.builder()
                .supplier(issueRequest.getSupplier())
                .createAt(LocalDate.now())
                .description(issueRequest.getDescription())
                .status("PENDING")
                .user(user)
                .build();

        ImportIssue createdIssue = importIssueRepository.save(issue);

        productService.addProducts(productRequestList);

        importIssueDetailService.addAllImportDetail(productRequestList, createdIssue);

        return mapper.map(createdIssue, ImportIssueDto.class);

    }
}
