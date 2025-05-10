package com.multivendor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.multivendor.model.VendorCommissionLog;

@Repository
public interface VendorCommissionLogRepository extends JpaRepository<VendorCommissionLog, Long> {
}