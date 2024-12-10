package com.zanchuyn.inventorymanagement.dtos.request;


import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class ImportIssueRequest {
    private String supplier;
    private String description;
    private List<ProductRequest> listProduct;
}
