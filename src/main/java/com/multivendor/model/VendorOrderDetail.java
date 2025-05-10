package com.multivendor.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "vendor_order_details")
public class VendorOrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long id_order_detail;
    private Long id_vendor;
    private BigDecimal commission_rate;
    private BigDecimal commission_amount;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId_order_detail() {
        return id_order_detail;
    }

    public void setId_order_detail(Long id_order_detail) {
        this.id_order_detail = id_order_detail;
    }

    public Long getId_vendor() {
        return id_vendor;
    }

    public void setId_vendor(Long id_vendor) {
        this.id_vendor = id_vendor;
    }

    public BigDecimal getCommission_rate() {
        return commission_rate;
    }

    public void setCommission_rate(BigDecimal commission_rate) {
        this.commission_rate = commission_rate;
    }

    public BigDecimal getCommission_amount() {
        return commission_amount;
    }

    public void setCommission_amount(BigDecimal commission_amount) {
        this.commission_amount = commission_amount;
    }
}