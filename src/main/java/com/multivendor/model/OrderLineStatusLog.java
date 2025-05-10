package com.multivendor.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "order_line_status_log")
public class OrderLineStatusLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long id_order_detail;
    private Long id_vendor;
    private String old_status;
    private String new_status;
    private String comment;
    private Long changed_by;
    private Date date_add;

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

    public String getOld_status() {
        return old_status;
    }

    public void setOld_status(String old_status) {
        this.old_status = old_status;
    }

    public String getNew_status() {
        return new_status;
    }

    public void setNew_status(String new_status) {
        this.new_status = new_status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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