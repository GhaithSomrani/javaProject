package com.multivendor.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "vendor_transactions")
public class VendorTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long id_vendor;
    private BigDecimal amount;
    private BigDecimal commission_amount;
    private String status;
    private Date date_add;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId_vendor() {
        return id_vendor;
    }

    public void setId_vendor(Long id_vendor) {
        this.id_vendor = id_vendor;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getCommission_amount() {
        return commission_amount;
    }

    public void setCommission_amount(BigDecimal commission_amount) {
        this.commission_amount = commission_amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDate_add() {
        return date_add;
    }

    public void setDate_add(Date date_add) {
        this.date_add = date_add;
    }
}
