package com.multivendor.repository;

import com.multivendor.model.OrderLineStatusLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderLineStatusLogRepository extends JpaRepository<OrderLineStatusLog, Long> {
}