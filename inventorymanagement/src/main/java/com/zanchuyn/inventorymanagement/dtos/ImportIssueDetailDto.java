package com.zanchuyn.inventorymanagement.dtos;

import com.zanchuyn.inventorymanagement.entities.Product;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ImportIssueDetailDto {
    private Integer id;

    private Product product;

    private Integer quantity;

    private LocalDate expiryDate;

    private Double price;
}
