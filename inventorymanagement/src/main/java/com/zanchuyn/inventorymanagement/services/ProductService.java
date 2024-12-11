package com.zanchuyn.inventorymanagement.services;

import com.zanchuyn.inventorymanagement.dtos.ProductDto;
import com.zanchuyn.inventorymanagement.dtos.request.ProductRequest;
import com.zanchuyn.inventorymanagement.entities.Product;
import com.zanchuyn.inventorymanagement.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ModelMapper mapper;

    public List<ProductDto> addProducts(List<ProductRequest> productRequestList) {
        List<Product> productList = new ArrayList<>();
        for (ProductRequest pr : productRequestList) {
            Product product = Product.builder()
                    .name(pr.getName())
                    .productDescription(pr.getProductDescription())
                    .unit(pr.getUnit())
                    .category(categoryService.findById(pr.getCategoryId()))
                    .build();
            if (!isExistProduct(product)) {
                productList.add(product);
            }

        }
        List<Product> products = new ArrayList<>();
        if (!productList.isEmpty()) {
            products = productRepository.saveAll(productList);
        }

        return products.stream()
                .map(item -> mapper.map(item, ProductDto.class))
                .toList();
    }

    public ProductDto findByName(String name) {
        return mapper.map(productRepository.findByName(name), ProductDto.class);
    }

    public boolean isExistProduct(Product product) {
        List<Product> productList = productRepository.findAll();
        return productList.stream()
                .anyMatch(item -> item.getName().equalsIgnoreCase(product.getName()));
    }

    public Product findById(Integer id) {
        return productRepository.findById(id).get();
    }
}
