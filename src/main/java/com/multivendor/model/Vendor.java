package com.multivendor.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "vendors")
public class Vendor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_vendor; // Changed from Long to Integer

    private Integer id_customer; // Changed from Long to Integer
    private Integer id_supplier; // Changed from Long to Integer
    private String shop_name;
    private String logo;
    private String status;
    private Date date_add;

    // Getters and setters
    public Integer getId_vendor() { // Changed return type
        return id_vendor;
    }

    public void setId_vendor(Integer id_vendor) { // Changed parameter type
        this.id_vendor = id_vendor;
    }

    public Integer getId_customer() { // Changed return type
        return id_customer;
    }

    public void setId_customer(Integer id_customer) { // Changed parameter type
        this.id_customer = id_customer;
    }

    public Integer getId_supplier() { // Changed return type
        return id_supplier;
    }

    public void setId_supplier(Integer id_supplier) { // Changed parameter type
        this.id_supplier = id_supplier;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
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

    // Methods from diagram
    public boolean isByCustomerId(Integer customerId) { // Changed parameter type
        return this.id_customer.equals(customerId);
    }

    public boolean isApproved() {
        return "active".equals(this.status);
    }

    // Other methods shown in the diagram
    public Double getCommissionRate() {
        // Implementation
        return 0.0;
    }

    public List<Product> getProducts() {
        // Implementation
        return new ArrayList<>();
    }
}