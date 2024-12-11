package com.zanchuyn.inventorymanagement.repositories;

import com.zanchuyn.inventorymanagement.entities.ImportIssueDetail;
import com.zanchuyn.inventorymanagement.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImportIssueDetailRepository extends JpaRepository<ImportIssueDetail, Integer> {

    List<ImportIssueDetail> findAllByIssue_receiptId(Integer receiptId);

    @Query("SELECT iid " +
            "FROM ImportIssueDetail iid " +
            "WHERE iid.issue.status = :status " +
            "AND iid.product.productId = :productId " +
            "AND iid.quantity > 0 " +
            "ORDER BY iid.expiryDate ASC")
    List<ImportIssueDetail> findAllDetailByStatusAndProductId(@Param("status") String status,
                                                              @Param("productId") Integer productId);

    @Query("SELECT iid.product FROM ImportIssueDetail iid WHERE iid.issue.status = :status")
    List<Product> findAllProductByStatus(@Param("status") String status);

    @Query("SELECT SUM(iid.quantity) " +
            "FROM ImportIssueDetail iid " +
            "WHERE iid.product.id = :productId " +
            "AND iid.issue.status = :status")
    Integer getTotalQuantityByProductIdAndStatus(@Param("productId") Integer productId,
                                                 @Param("status") String status);

}
