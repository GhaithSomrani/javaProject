
// Fixed CommissionService.java
package com.multivendor.service;

import com.multivendor.model.*;
        import com.multivendor.repository.*;
        import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class CommissionService {
    @Autowired
    private VendorCommissionRepository commissionRepository;

    @Autowired
    private VendorCategoryCommissionRepository categoryCommissionRepository;

    @Autowired
    private VendorCommissionLogRepository commissionLogRepository;

    @Autowired
    private VendorOrderDetailRepository vendorOrderDetailRepository;

    @Autowired
    private VendorTransactionRepository transactionRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private ProductRepository productRepository;

    public BigDecimal getCommissionRate(Long vendorId, Long categoryId) {
        // Check if there's a category-specific rate
        if (categoryId != null) {
            VendorCategoryCommission categoryRate = categoryCommissionRepository.findByIdVendorAndIdCategory(vendorId, categoryId);
            if (categoryRate != null) {
                return categoryRate.getCommission_rate();
            }
        }

        // If not, use vendor's default rate
        VendorCommission vendorRate = commissionRepository.findTopByIdVendorOrderByDateAddDesc(vendorId);
        return vendorRate != null ? vendorRate.getRate() : BigDecimal.valueOf(0);
    }

    public void setCommissionRate(Long vendorId, BigDecimal rate, Long changedBy) {
        VendorCommission commission = new VendorCommission();
        commission.setId_vendor(vendorId);
        commission.setRate(rate);
        commission.setDate_add(new Date());
        commissionRepository.save(commission);

        // Log the change
        VendorCommissionLog log = new VendorCommissionLog();
        log.setId_vendor(vendorId);
        log.setNew_rate(rate);
        log.setChanged_by(changedBy);
        log.setDate_add(new Date());
        commissionLogRepository.save(log);
    }

    public void setCategoryCommissionRate(Long vendorId, Long categoryId, BigDecimal rate, Long changedBy) {
        VendorCategoryCommission categoryCommission = categoryCommissionRepository.findByIdVendorAndIdCategory(vendorId, categoryId);
        BigDecimal oldRate = null;

        if (categoryCommission == null) {
            categoryCommission = new VendorCategoryCommission();
            categoryCommission.setId_vendor(vendorId);
            categoryCommission.setId_category(categoryId);
        } else {
            oldRate = categoryCommission.getCommission_rate();
        }

        categoryCommission.setCommission_rate(rate);
        categoryCommission.setDate_add(new Date());
        categoryCommissionRepository.save(categoryCommission);

        // Log the change
        VendorCommissionLog log = new VendorCommissionLog();
        log.setId_vendor(vendorId);
        log.setId_category(categoryId);
        log.setOld_rate(oldRate);
        log.setNew_rate(rate);
        log.setChanged_by(changedBy);
        log.setDate_add(new Date());
        commissionLogRepository.save(log);
    }

    @Transactional
    public void calculateCommission(Long orderDetailId, Long vendorId) {
        // Get order detail
        OrderDetail orderDetail = orderDetailRepository.findById(orderDetailId)
                .orElseThrow(() -> new RuntimeException("Order detail not found"));

        // Get product and its category
        Product product = productRepository.findById(orderDetail.getId_product())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Get commission rate
        BigDecimal rate = getCommissionRate(vendorId, product.getId_category());

        // Calculate amount
        BigDecimal amount = orderDetail.getUnit_price()
                .multiply(BigDecimal.valueOf(orderDetail.getProduct_quantity()))
                .multiply(rate)
                .divide(BigDecimal.valueOf(100));

        // Save vendor order detail
        VendorOrderDetail vendorOrderDetail = new VendorOrderDetail();
        vendorOrderDetail.setId_order_detail(orderDetailId);
        vendorOrderDetail.setId_vendor(vendorId);
        vendorOrderDetail.setCommission_rate(rate);
        vendorOrderDetail.setCommission_amount(amount);
        vendorOrderDetailRepository.save(vendorOrderDetail);

        // Create transaction record
        VendorTransaction transaction = new VendorTransaction();
        transaction.setId_vendor(vendorId);
        transaction.setAmount(orderDetail.getUnit_price().multiply(BigDecimal.valueOf(orderDetail.getProduct_quantity())));
        transaction.setCommission_amount(amount);
        transaction.setStatus("pending");
        transaction.setDate_add(new Date());
        transactionRepository.save(transaction);
    }
}

