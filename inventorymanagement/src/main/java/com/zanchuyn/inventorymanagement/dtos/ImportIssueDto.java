package com.zanchuyn.inventorymanagement.dtos;

import com.zanchuyn.inventorymanagement.entities.User;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ImportIssueDto {
    private Integer receiptId;

    private LocalDate createAt;

    private String supplier;

    private String description;

    private String status;

    private User user;
}
