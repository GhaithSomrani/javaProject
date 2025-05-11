package com.multivendor.repository;

import com.multivendor.model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Integer> {
    @Query("SELECT v FROM Vendor v WHERE v.id_customer = :customerId")

    Vendor findById_customer(@Param("customerId") Integer customerId);
    List<Vendor> findByStatus(String status);
}