package com.multivendor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.multivendor.model.VendorCategoryCommission;

@Repository
public interface VendorCategoryCommissionRepository extends JpaRepository<VendorCategoryCommission, Long> {
    VendorCategoryCommission findByIdVendorAndIdCategory(Long vendorId, Long categoryId);
}
