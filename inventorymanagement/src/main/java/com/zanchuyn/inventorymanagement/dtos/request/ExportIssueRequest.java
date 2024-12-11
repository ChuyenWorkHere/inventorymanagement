package com.zanchuyn.inventorymanagement.dtos.request;


import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class ExportIssueRequest {

    private String description;

    private String requestedBy;

    List<ExportProductRequest> exportProductRequests;

}
