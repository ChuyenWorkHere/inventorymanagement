package com.zanchuyn.inventorymanagement.repositories;

import com.zanchuyn.inventorymanagement.entities.ExportTemporary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExportTemporaryRepository extends JpaRepository<ExportTemporary, Integer> {

    @Query("SELECT et FROM ExportTemporary et WHERE et.exportIssue.issueId = :issueId")
    List<ExportTemporary> findByExportIssueId(@Param("issueId") Integer id);
}
