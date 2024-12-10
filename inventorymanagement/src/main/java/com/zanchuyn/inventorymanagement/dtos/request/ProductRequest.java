package com.zanchuyn.inventorymanagement.dtos.request;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class ProductRequest {
    private String name;
    private Integer categoryId;
    private Integer quantity;
    private String unit;
    private LocalDate expiryDate;
    private Double price;
    private String productDescription;
}
