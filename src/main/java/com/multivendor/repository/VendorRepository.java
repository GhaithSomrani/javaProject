// VendorRepository.java
package com.multivendor.repository;

import com.multivendor.model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Long> {
    Vendor findByIdCustomer(Long customerId);
    List<Vendor> findByStatus(String status);
}