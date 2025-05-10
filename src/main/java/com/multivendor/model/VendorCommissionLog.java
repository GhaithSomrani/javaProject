package com.multivendor.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "vendor_commission_logs")
public class VendorCommissionLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long id_vendor;
    private Long id_category;
    private BigDecimal old_rate;
    private BigDecimal new_rate;
    private Long changed_by;
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

    public Long getId_category() {
        return id_category;
    }

    public void setId_category(Long id_category) {
        this.id_category = id_category;
    }

    public BigDecimal getOld_rate() {
        return old_rate;
    }

    public void setOld_rate(BigDecimal old_rate) {
        this.old_rate = old_rate;
    }

    public BigDecimal getNew_rate() {
        return new_rate;
    }

    public void setNew_rate(BigDecimal new_rate) {
        this.new_rate = new_rate;
    }

    public Long getChanged_by() {
        return changed_by;
    }

    public void setChanged_by(Long changed_by) {
        this.changed_by = changed_by;
    }

    public Date getDate_add() {
        return date_add;
    }

    public void setDate_add(Date date_add) {
        this.date_add = date_add;
    }
}
