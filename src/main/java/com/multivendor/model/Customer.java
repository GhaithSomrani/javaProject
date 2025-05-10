package com.multivendor.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_customer; // Changed from Long to Integer

    private String email;
    private String firstname;
    private String lastname;
    private Date date_add;

    // Getters and Setters
    public Integer getId_customer() { // Changed return type from Long to Integer
        return id_customer;
    }

    public void setId_customer(Integer id_customer) { // Changed parameter type from Long to Integer
        this.id_customer = id_customer;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Date getDate_add() {
        return date_add;
    }

    public void setDate_add(Date date_add) {
        this.date_add = date_add;
    }
}