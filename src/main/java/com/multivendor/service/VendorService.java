package com.multivendor.service;

import com.multivendor.model.Vendor;
import com.multivendor.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class VendorService {
    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private CustomerService customerService;

    public Vendor registerVendor(Integer customerId, String shopName, String logo) {
        // Check if customer exists
        if (!customerService.exists(customerId)) {
            throw new RuntimeException("Customer not found");
        }

        Vendor vendor = new Vendor();
        vendor.setId_customer(customerId);
        vendor.setShop_name(shopName);
        vendor.setLogo(logo);
        vendor.setStatus("pending");
        vendor.setDate_add(new Date());

        return vendorRepository.save(vendor);
    }

    public Vendor approveVendor(Integer vendorId) {
        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));
        vendor.setStatus("active");
        return vendorRepository.save(vendor);
    }

    public Vendor rejectVendor(Integer vendorId) {
        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));
        vendor.setStatus("rejected");
        return vendorRepository.save(vendor);
    }

    public List<Vendor> getPendingVendors() {
        return vendorRepository.findByStatus("pending");
    }

    public Vendor findByCustomerId(Integer customerId) {
        return vendorRepository.findByIdCustomer(customerId);
    }
}
