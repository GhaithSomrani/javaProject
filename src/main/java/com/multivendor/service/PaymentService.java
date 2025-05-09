// PaymentService.java
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
public class PaymentService {
    @Autowired
    private VendorTransactionRepository transactionRepository;
    @Autowired
    private VendorPaymentRepository paymentRepository;

    public List<VendorTransaction> getPendingTransactions(Long vendorId) {
        return transactionRepository.findByIdVendorAndStatus(vendorId, "pending");
    }

    @Transactional
    public VendorPayment processPayment(Long vendorId, String paymentMethod, String reference) {
        // Get pending transactions
        List<VendorTransaction> pendingTransactions = getPendingTransactions(vendorId);

        if (pendingTransactions.isEmpty()) {
            throw new RuntimeException("No pending transactions to process");
        }

        // Calculate total amount
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (VendorTransaction transaction : pendingTransactions) {
            totalAmount = totalAmount.add(transaction.getAmount().subtract(transaction.getCommission_amount()));
        }

        // Create payment record
        VendorPayment payment = new VendorPayment();
        payment.setId_vendor(vendorId);
        payment.setAmount(totalAmount);
        payment.setPayment_method(paymentMethod);
        payment.setReference(reference);
        payment.setStatus("completed");
        payment.setDate_add(new Date());
        VendorPayment savedPayment = paymentRepository.save(payment);

        // Update transaction status
        for (VendorTransaction transaction : pendingTransactions) {
            transaction.setStatus("paid");
            transactionRepository.save(transaction);
        }

        return savedPayment;
    }

    public List<VendorPayment> getPaymentHistory(Long vendorId) {
        return paymentRepository.findByIdVendor(vendorId);
    }

    public VendorPayment markAsPaid(Long paymentId) {
        VendorPayment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        payment.setStatus("paid");
        return paymentRepository.save(payment);
    }
}