package com.zanchuyn.inventorymanagement.entities;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "hanghoa")
public class Product {

    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String productDescription;

    @Column(name = "unit")
    private String unit;

    @ManyToOne
    @JoinColumn(name = "categoryId", nullable = false)
    private Category category;
}
