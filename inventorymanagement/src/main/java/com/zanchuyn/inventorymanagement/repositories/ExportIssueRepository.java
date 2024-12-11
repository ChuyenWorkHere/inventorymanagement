package com.zanchuyn.inventorymanagement.repositories;

import com.zanchuyn.inventorymanagement.entities.ExportIssue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExportIssueRepository extends JpaRepository<ExportIssue, Integer> {
    List<ExportIssue> findAllByStatus(String status);
}
