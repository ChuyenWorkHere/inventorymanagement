package com.zanchuyn.inventorymanagement.dtos;

import com.zanchuyn.inventorymanagement.entities.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ExportIssueDto {
    private Integer issueId;

    private LocalDateTime createAt;

    private String requestedBy;

    private String description;

    private String status;

    private User user;

    private List<ExportIssueDetailDto> exportIssueDetailList;
}
