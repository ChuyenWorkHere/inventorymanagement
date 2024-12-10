package com.zanchuyn.inventorymanagement.entities;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "chitietphieunhap")
public class ImportIssueDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "receiptId")
    private ImportIssue issue;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    @Column(name = "price")
    private Double price;
}
