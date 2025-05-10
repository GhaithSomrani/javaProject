package com.multivendor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.multivendor.model.VendorOrderDetail;

@Repository
public interface VendorOrderDetailRepository extends JpaRepository<VendorOrderDetail, Long> {
}