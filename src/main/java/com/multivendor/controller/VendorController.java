package com.multivendor.controller;

import com.multivendor.model.Vendor;
import com.multivendor.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/vendors")
public class VendorController {
    @Autowired
    private VendorService vendorService;

    @PostMapping("/register")
    public ResponseEntity<Vendor> registerVendor(@RequestBody Map<String, Object> payload) {
        Long customerId = Long.valueOf(payload.get("customerId").toString());
        String shopName = payload.get("shopName").toString();
        String logo = payload.get("logo") != null ? payload.get("logo").toString() : null;

        Vendor vendor = vendorService.registerVendor(customerId, shopName, logo);
        return ResponseEntity.ok(vendor);
    }

    @GetMapping("/pending")
    public ResponseEntity<List<Vendor>> getPendingVendors() {
        return ResponseEntity.ok(vendorService.getPendingVendors());
    }

    @PutMapping("/{vendorId}/approve")
    public ResponseEntity<Vendor> approveVendor(@PathVariable Long vendorId) {
        return ResponseEntity.ok(vendorService.approveVendor(vendorId));
    }

    @PutMapping("/{vendorId}/reject")
    public ResponseEntity<Vendor> rejectVendor(@PathVariable Long vendorId) {
        return ResponseEntity.ok(vendorService.rejectVendor(vendorId));
    }

    @GetMapping("/{customerId}/check")
    public ResponseEntity<Boolean> isVendorApproved(@PathVariable Long customerId) {
        Vendor vendor = vendorService.findByCustomerId(customerId);
        if (vendor == null) {
            return ResponseEntity.ok(false);
        }
        return ResponseEntity.ok(vendor.isApproved());
    }
}