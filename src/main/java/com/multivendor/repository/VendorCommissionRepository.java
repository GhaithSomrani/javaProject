package com.multivendor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.multivendor.model.VendorCommission;

@Repository
public interface VendorCommissionRepository extends JpaRepository<VendorCommission, Long> {
    VendorCommission findTopByIdVendorOrderByDateAddDesc(Long vendorId);
}