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
@Table(name = "phieuxuat")
public class ExportIssue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "issue_id")
    private Integer issueId;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private String status;

    @Column(name = "requested_by")
    private String requestedBy;


    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;
}
