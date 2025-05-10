package com.multivendor.repository;

import com.multivendor.model.OrderLineStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderLineStatusRepository extends JpaRepository<OrderLineStatus, Long> {
    OrderLineStatus findByIdOrderDetailAndIdVendor(Long orderDetailId, Long vendorId);
}