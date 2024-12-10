package com.zanchuyn.inventorymanagement.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "phieubaotri")
public class MaintenanceNote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maintenance_id")
    private int maintenanceId;

    @ManyToOne
    @JoinColumn(name = "productId", nullable = false)
    private Product product;

    @Column(name = "description")
    private String description;

    @Column(name = "scheduled_date")
    private LocalDateTime scheduledDate;

    @Column(name = "performed_date")
    private LocalDateTime performedDate;

    @Column(name = "notes")
    private String notes;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User handledBy;

}
