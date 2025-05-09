// Vendor.java
package com.multivendor.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "vendors")
public class Vendor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_vendor;

    private Long id_customer;
    private Long id_supplier;
    private String shop_name;
    private String logo;
    private String status;
    private Date date_add;

    // Add getters and setters

    // Methods from diagram
    public boolean isByCustomerId(Long customerId) {
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