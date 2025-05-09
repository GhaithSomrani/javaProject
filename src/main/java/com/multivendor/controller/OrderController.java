// OrderController.java
package com.multivendor.controller;

import com.multivendor.model.Order;
import com.multivendor.model.OrderDetail;
import com.multivendor.service.OrderProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderProcessingService orderProcessingService;

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Map<String, Object> payload) {
        Order order = new Order();
        order.setId_customer(Long.valueOf(payload.get("customerId").toString()));
        order.setTotal_paid(new BigDecimal(payload.get("totalPaid").toString()));

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> detailsData = (List<Map<String, Object>>) payload.get("orderDetails");
        List<OrderDetail> orderDetails = new ArrayList<>();

        for (Map<String, Object> detailData : detailsData) {
            OrderDetail detail = new OrderDetail();
            detail.setId_product(Long.valueOf(detailData.get("productId").toString()));
            detail.setProduct_quantity(Integer.valueOf(detailData.get("quantity").toString()));
            detail.setUnit_price(new BigDecimal(detailData.get("unitPrice").toString()));
            orderDetails.add(detail);
        }

        return ResponseEntity.ok(orderProcessingService.processOrder(order, orderDetails));
    }

    @PutMapping("/status")
    public ResponseEntity<Void> updateOrderLineStatus(@RequestBody Map<String, Object> payload) {
        Long orderDetailId = Long.valueOf(payload.get("orderDetailId").toString());
        Long vendorId = Long.valueOf(payload.get("vendorId").toString());
        String newStatus = payload.get("status").toString();
        String comment = payload.get("comment") != null ? payload.get("comment").toString() : null;
        Long changedBy = Long.valueOf(payload.get("changedBy").toString());

        orderProcessingService.updateOrderLineStatus(orderDetailId, vendorId, newStatus, comment, changedBy);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/vendor/{vendorId}")
    public ResponseEntity<List<OrderDetail>> getVendorOrders(@PathVariable Long vendorId) {
        return ResponseEntity.ok(orderProcessingService.getVendorOrders(vendorId));
    }
}