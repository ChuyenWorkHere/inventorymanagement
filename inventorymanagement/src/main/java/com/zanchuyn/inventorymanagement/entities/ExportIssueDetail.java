package com.zanchuyn.inventorymanagement.entities;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "chitietphieuxuat")
public class ExportIssueDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "productId", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "issueId", nullable = false)
    private ExportIssue issue;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;
}
