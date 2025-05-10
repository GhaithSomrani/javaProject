package com.multivendor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.multivendor.model.VendorTransaction;
import java.util.List;

@Repository
public interface VendorTransactionRepository extends JpaRepository<VendorTransaction, Long> {
    List<VendorTransaction> findByIdVendorAndStatus(Long vendorId, String status);
}
