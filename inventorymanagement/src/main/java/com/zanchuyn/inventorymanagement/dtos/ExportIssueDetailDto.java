package com.zanchuyn.inventorymanagement.dtos;

import com.zanchuyn.inventorymanagement.entities.ExportIssue;
import com.zanchuyn.inventorymanagement.entities.Product;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ExportIssueDetailDto {
    private Integer id;

    private Product product;

    private ExportIssue issue;

    private Integer quantity;

}
