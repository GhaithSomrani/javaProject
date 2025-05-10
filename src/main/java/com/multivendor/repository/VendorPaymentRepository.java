package com.multivendor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.multivendor.model.VendorPayment;
import java.util.List;

@Repository
public interface VendorPaymentRepository extends JpaRepository<VendorPayment, Long> {
    List<VendorPayment> findByIdVendor(Long vendorId);
}