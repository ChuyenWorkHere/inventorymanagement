package com.zanchuyn.inventorymanagement.repositories;

import com.zanchuyn.inventorymanagement.entities.ExportIssueDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExportIssueDetailRepository extends JpaRepository<ExportIssueDetail, Integer> {

    List<ExportIssueDetail> findAllByIssue_issueId(Integer issueId);


}
