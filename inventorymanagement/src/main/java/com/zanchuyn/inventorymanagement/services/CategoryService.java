package com.zanchuyn.inventorymanagement.services;

import com.zanchuyn.inventorymanagement.entities.Category;
import com.zanchuyn.inventorymanagement.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Category findById(Integer id) {
        return categoryRepository.findById(id).orElse(null);
    }
}
