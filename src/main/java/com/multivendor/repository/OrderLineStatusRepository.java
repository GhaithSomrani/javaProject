package com.multivendor.repository;

import com.multivendor.model.OrderLineStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderLineStatusRepository extends JpaRepository<OrderLineStatus, Long> {
    OrderLineStatus findByIdOrderDetailAndIdVendor(Long orderDetailId, Long vendorId);

    // Add this method to get all order lines for a vendor
    List<OrderLineStatus> findByIdVendor(Long vendorId);
}