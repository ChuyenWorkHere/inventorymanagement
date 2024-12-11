package com.zanchuyn.inventorymanagement.services;

import com.zanchuyn.inventorymanagement.dtos.ImportIssueDetailDto;
import com.zanchuyn.inventorymanagement.dtos.ProductDto;
import com.zanchuyn.inventorymanagement.dtos.request.ProductRequest;
import com.zanchuyn.inventorymanagement.entities.ImportIssue;
import com.zanchuyn.inventorymanagement.entities.ImportIssueDetail;
import com.zanchuyn.inventorymanagement.entities.Product;
import com.zanchuyn.inventorymanagement.repositories.ImportIssueDetailRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImportIssueDetailService {

    @Autowired
    private ImportIssueDetailRepository importIssueDetailRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private ModelMapper mapper;

    public List<ImportIssueDetail> addAllImportDetail(List<ProductRequest> productRequestList
            , ImportIssue issue) {

        List<ImportIssueDetail> issueDetails = new ArrayList<>();
        for (int i = 0; i < productRequestList.size(); i++) {

            ProductDto productDto = productService.findByName(productRequestList.get(i).getName());

            ImportIssueDetail issueDetail = ImportIssueDetail
                    .builder()
                    .product(mapper.map(productDto, Product.class))
                    .issue(issue)
                    .expiryDate(productRequestList.get(i).getExpiryDate())
                    .quantity(productRequestList.get(i).getQuantity())
                    .price(productRequestList.get(i).getPrice())
                    .build();
            issueDetails.add(issueDetail);
        }
        return importIssueDetailRepository.saveAll(issueDetails);
    }

    public List<ImportIssueDetailDto> findAllImportDetailByIssueId(Integer id) {
        List<ImportIssueDetail> importIssueDetails = importIssueDetailRepository.findAllByIssue_receiptId(id);
        return importIssueDetails.stream()
                .map(item -> mapper.map(item, ImportIssueDetailDto.class))
                .toList();
    }

    public List<ImportIssueDetailDto> findAllDetailByStatusAndProductId(String status, Integer productId) {
        List<ImportIssueDetail> issueDetails = importIssueDetailRepository.findAllDetailByStatusAndProductId(status, productId);
        return issueDetails.stream()
                .filter(item -> item.getExpiryDate().isAfter(LocalDate.now())) //Lọc những sp chưa hết hạn
                .map(item -> mapper.map(item, ImportIssueDetailDto.class))
                .toList();
    }

    public List<ProductDto> findAllProductByStatus(String status) {
        List<Product> productList = importIssueDetailRepository.findAllProductByStatus(status);
        List<ProductDto> productDtos = productList.stream()
                .map(item -> mapper.map(item, ProductDto.class))
                .toList();
        productDtos.forEach(item -> {
            Integer total = importIssueDetailRepository.getTotalQuantityByProductIdAndStatus(item.getProductId(), status);
            item.setQuantity(total == null ? 0 : total);
        });
        return productDtos;
    }

    public void saveAll(List<ImportIssueDetail> importIssueDetails) {
        this.importIssueDetailRepository.saveAll(importIssueDetails);
    }
}
