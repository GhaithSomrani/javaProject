// Required OrderDetail.java model
package com.multivendor.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "order_details")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_order_detail;

    private Long id_order;
    private Long id_product;
    private Integer product_quantity;
    private BigDecimal unit_price;

    // Getters and Setters
    public Long getId_order_detail() {
        return id_order_detail;
    }

    public void setId_order_detail(Long id_order_detail) {
        this.id_order_detail = id_order_detail;
    }

    public Long getId_order() {
        return id_order;
    }

    public void setId_order(Long id_order) {
        this.id_order = id_order;
    }

    public Long getId_product() {
        return id_product;
    }

    public void setId_product(Long id_product) {
        this.id_product = id_product;
    }

    public Integer getProduct_quantity() {
        return product_quantity;
    }

    public void setProduct_quantity(Integer product_quantity) {
        this.product_quantity = product_quantity;
    }

    public BigDecimal getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(BigDecimal unit_price) {
        this.unit_price = unit_price;
    }
}