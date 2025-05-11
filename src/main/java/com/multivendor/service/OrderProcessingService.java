package com.multivendor.service;

import com.multivendor.model.*;
import com.multivendor.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderProcessingService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private OrderLineStatusRepository orderLineStatusRepository;

    @Autowired
    private OrderLineStatusLogRepository orderLineStatusLogRepository;

    // Add this repository for getVendorOrders method
    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public Order processOrder(Order order, List<OrderDetail> orderDetails) {
        // Save order
        order.setDate_add(new Date());
        order.setCurrent_state("pending");
        Order savedOrder = orderRepository.save(order);

        // Process each order detail and create vendor-specific records
        for (OrderDetail detail : orderDetails) {
            detail.setId_order(savedOrder.getId_order());
            OrderDetail savedDetail = orderDetailRepository.save(detail);

            // Since we don't have a way to find which vendor owns a product
            // let's use a default vendor (vendor ID 1) for demonstration
            Integer vendorId = 1; // Default vendor ID

            if (vendorId != null) {
                // Create order line status
                OrderLineStatus lineStatus = new OrderLineStatus();
                lineStatus.setId_order_detail(savedDetail.getId_order_detail());
                lineStatus.setId_vendor(vendorId.longValue()); // Convert Integer to Long
                lineStatus.setStatus("pending");
                lineStatus.setDate_add(new Date());
                orderLineStatusRepository.save(lineStatus);

                // Log status change
                logStatusChange(savedDetail.getId_order_detail(), vendorId.longValue(), null, "pending", "System", null);
            }
        }

        return savedOrder;
    }

    public void updateOrderLineStatus(Long orderDetailId, Long vendorId, String newStatus, String comment, Long changedBy) {
        // Get current status
        OrderLineStatus currentStatus = orderLineStatusRepository.findByIdOrderDetailAndIdVendor(orderDetailId, vendorId);
        String oldStatus = currentStatus.getStatus();

        // Update status
        currentStatus.setStatus(newStatus);
        orderLineStatusRepository.save(currentStatus);

        // Log change
        logStatusChange(orderDetailId, vendorId, oldStatus, newStatus, comment, changedBy);
    }

    private void logStatusChange(Long orderDetailId, Long vendorId, String oldStatus, String newStatus, String comment, Long changedBy) {
        OrderLineStatusLog log = new OrderLineStatusLog();
        log.setId_order_detail(orderDetailId);
        log.setId_vendor(vendorId);
        log.setOld_status(oldStatus);
        log.setNew_status(newStatus);
        log.setComment(comment);
        log.setChanged_by(changedBy);
        log.setDate_add(new Date());
        orderLineStatusLogRepository.save(log);
    }

    // Simplified method that doesn't rely on vendor-product relationship
    public List<OrderDetail> getVendorOrders(Long vendorId) {
        // This is a simplified implementation to retrieve orders for a specific vendor
        List<OrderLineStatus> orderLineStatuses = orderLineStatusRepository.findByIdVendor(vendorId);
        List<OrderDetail> vendorOrders = new ArrayList<>();

        for (OrderLineStatus status : orderLineStatuses) {
            OrderDetail orderDetail = orderDetailRepository.findById(status.getId_order_detail())
                    .orElse(null);
            if (orderDetail != null) {
                vendorOrders.add(orderDetail);
            }
        }

        return vendorOrders;
    }
}