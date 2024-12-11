package com.zanchuyn.inventorymanagement.services;

import com.zanchuyn.inventorymanagement.dtos.ExportIssueDetailDto;
import com.zanchuyn.inventorymanagement.dtos.ExportIssueDto;
import com.zanchuyn.inventorymanagement.dtos.ImportIssueDetailDto;
import com.zanchuyn.inventorymanagement.dtos.request.ExportIssueRequest;
import com.zanchuyn.inventorymanagement.dtos.request.ExportProductRequest;
import com.zanchuyn.inventorymanagement.entities.*;
import com.zanchuyn.inventorymanagement.repositories.ExportIssueDetailRepository;
import com.zanchuyn.inventorymanagement.repositories.ExportIssueRepository;
import com.zanchuyn.inventorymanagement.repositories.ExportTemporaryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ExportIssueService {

    @Autowired
    private ExportIssueRepository exportIssueRepository;

    @Autowired
    private ExportIssueDetailRepository exportIssueDetailRepository;

    @Autowired
    private ExportTemporaryRepository exportTemporaryRepository;

    @Autowired
    private ImportIssueService importIssueService;

    @Autowired
    private ImportIssueDetailService importIssueDetailService;

    @Autowired
    private ExportIssueDetailService exportIssueDetailService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ModelMapper mapper;

    @Transactional
    public void addExportIssue(ExportIssueRequest issueRequest, User user) {
        ExportIssue exportIssue = ExportIssue.builder()
                .createAt(LocalDateTime.now())
                .description(issueRequest.getDescription())
                .requestedBy(issueRequest.getRequestedBy())
                .user(user)
                .build();

        if (user.getRole().equals("QUANLY")) {
            exportIssue.setStatus("SUCCESS");
            ExportIssue issue = exportIssueRepository.save(exportIssue);
            List<ExportProductRequest> exportProductRequests = issueRequest.getExportProductRequests();
            for (ExportProductRequest item : exportProductRequests) {
                Product product = productService.findById(item.getProductId());
                ExportIssueDetail issueDetail = ExportIssueDetail.builder()
                        .issue(issue)
                        .product(product)
                        .quantity(item.getQuantity())
                        .build();

                exportIssueDetailRepository.save(issueDetail);

                List<ImportIssueDetailDto> issueDetails = importIssueDetailService
                        .findAllDetailByStatusAndProductId("SUCCESS", product.getProductId());
                int remainingQuantity = item.getQuantity();
                for (ImportIssueDetailDto detailDto : issueDetails) {
                    if (remainingQuantity <= 0) break;
                    if (detailDto.getQuantity() <= remainingQuantity) {
                        remainingQuantity -= detailDto.getQuantity();
                        detailDto.setQuantity(0);
                    } else {
                        detailDto.setQuantity(detailDto.getQuantity() - remainingQuantity);
                        remainingQuantity = 0;
                    }
                }
                List<ImportIssueDetail> issueDetailList = issueDetails.stream()
                        .map(iDto -> mapper.map(iDto, ImportIssueDetail.class))
                        .toList();
                importIssueDetailService.saveAll(issueDetailList);
            }


        } else if (user.getRole().equals("NHANVIEN")) {
            exportIssue.setStatus("WAITING");
            ExportIssue issue = exportIssueRepository.save(exportIssue);

            List<ExportProductRequest> exportProductRequests = issueRequest.getExportProductRequests();
            for (ExportProductRequest item : exportProductRequests) {
                Product product = productService.findById(item.getProductId());
                ExportTemporary temporary = ExportTemporary.builder()
                        .exportIssue(issue)
                        .product(product)
                        .quantity(item.getQuantity())
                        .build();
                exportTemporaryRepository.save(temporary);
            }
        }
    }

    public List<ExportIssue> findAllIssueByStatus(String status) {
        return exportIssueRepository.findAllByStatus(status);
    }

    public ExportIssue findById(Integer id) {
        return this.exportIssueRepository.findById(id).get();
    }

    public ExportIssue updateExportIssue(ExportIssue issue) {
        ExportIssue exportIssue = new ExportIssue();
        if (issue.getStatus().equals("SUCCESS")) {
            List<ExportTemporary> temporaryList = this.exportTemporaryRepository.findByExportIssueId(issue.getIssueId());

            for (ExportTemporary item : temporaryList) {
                Product product = productService.findById(item.getProduct().getProductId());
                ExportIssueDetail issueDetail = ExportIssueDetail.builder()
                        .issue(issue)
                        .product(product)
                        .quantity(item.getQuantity())
                        .build();

                exportIssueDetailRepository.save(issueDetail);

                List<ImportIssueDetailDto> issueDetails = importIssueDetailService
                        .findAllDetailByStatusAndProductId("SUCCESS", product.getProductId());
                int remainingQuantity = item.getQuantity();
                for (ImportIssueDetailDto detailDto : issueDetails) {
                    if (remainingQuantity <= 0) break;
                    if (detailDto.getQuantity() <= remainingQuantity) {
                        remainingQuantity -= detailDto.getQuantity();
                        detailDto.setQuantity(0);
                    } else {
                        detailDto.setQuantity(detailDto.getQuantity() - remainingQuantity);
                        remainingQuantity = 0;
                    }
                }
                List<ImportIssueDetail> issueDetailList = issueDetails.stream()
                        .map(iDto -> mapper.map(iDto, ImportIssueDetail.class))
                        .toList();
                importIssueDetailService.saveAll(issueDetailList);
            }
        } else if (issue.getStatus().equals("REJECTED")) {
            exportIssue = exportIssueRepository.save(issue);
        }
        return exportIssue;
    }

    public ExportIssueDto findExportIssueDetail(Integer id) {
        Optional<ExportIssue> exportIssueOptional = exportIssueRepository.findById(id);
        if (exportIssueOptional.isEmpty()) {
            return null;
        } else {
            ExportIssue exportIssue = exportIssueOptional.get();
            ExportIssueDto exportIssueDto = mapper.map(exportIssue, ExportIssueDto.class);
            List<ExportIssueDetailDto> exportIssueDetailDtos = exportIssueDetailService.findAllExportDetailByIssueId(id);
            exportIssueDto.setExportIssueDetailList(exportIssueDetailDtos);
            return exportIssueDto;
        }
    }
}
