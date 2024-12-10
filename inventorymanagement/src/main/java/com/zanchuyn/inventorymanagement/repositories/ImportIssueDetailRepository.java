package com.zanchuyn.inventorymanagement.repositories;

import com.zanchuyn.inventorymanagement.entities.ImportIssueDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImportIssueDetailRepository extends JpaRepository<ImportIssueDetail, Integer> {

    List<ImportIssueDetail> findAllByIssue_receiptId(Integer receiptId);
}
