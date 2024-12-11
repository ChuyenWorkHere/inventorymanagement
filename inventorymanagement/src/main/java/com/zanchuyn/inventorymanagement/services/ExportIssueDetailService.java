package com.zanchuyn.inventorymanagement.services;

import com.zanchuyn.inventorymanagement.dtos.ExportIssueDetailDto;
import com.zanchuyn.inventorymanagement.repositories.ExportIssueDetailRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExportIssueDetailService {

    @Autowired
    private ExportIssueDetailRepository exportIssueDetailRepository;

    @Autowired
    private ModelMapper mapper;

    public List<ExportIssueDetailDto> findAllExportDetailByIssueId(Integer id) {
        return this.exportIssueDetailRepository.findAllByIssue_issueId(id)
                .stream()
                .map(item -> mapper.map(item, ExportIssueDetailDto.class))
                .toList();
    }
}
