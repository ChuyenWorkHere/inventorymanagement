package com.zanchuyn.inventorymanagement.entities;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "chitietbaotri")
public class MaintenanceNoteDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "productId", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "maintenanceId", nullable = false)
    private MaintenanceNote maintenanceNote;

    @Column(name = "notes")
    private String notes;
}
