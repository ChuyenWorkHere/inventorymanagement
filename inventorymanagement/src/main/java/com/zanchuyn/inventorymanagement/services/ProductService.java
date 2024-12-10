package com.zanchuyn.inventorymanagement.services;

import com.zanchuyn.inventorymanagement.dtos.ProductDto;
import com.zanchuyn.inventorymanagement.dtos.request.ProductRequest;
import com.zanchuyn.inventorymanagement.entities.Product;
import com.zanchuyn.inventorymanagement.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;


    @Autowired
    private ModelMapper mapper;

    public List<ProductDto> addProducts(List<ProductRequest> productRequestList) {
        List<ProductDto> productDtoList = productRequestList.stream()
                .map(item -> mapper.map(item, ProductDto.class))
                .filter(item -> !productRepository.existsByName(item.getName().trim()))
                .toList();

        List<Product> productList = productDtoList.stream()
                .map(item -> mapper.map(item, Product.class))
                .toList();
        List<Product> createdProductList = productRepository.saveAll(productList);
        return createdProductList
                .stream()
                .map(product -> mapper.map(product, ProductDto.class))
                .toList();
    }

    public ProductDto findByName(String name) {
        return mapper.map(productRepository.findByName(name), ProductDto.class);
    }
}
