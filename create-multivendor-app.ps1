# PowerShell script to create Spring Boot multi-vendor e-commerce platform structure

# Create main directory structure if it doesn't exist
New-Item -Path "src/main/java/com/multivendor/config" -ItemType Directory -Force
New-Item -Path "src/main/java/com/multivendor/controller" -ItemType Directory -Force
New-Item -Path "src/main/java/com/multivendor/model" -ItemType Directory -Force
New-Item -Path "src/main/java/com/multivendor/repository" -ItemType Directory -Force
New-Item -Path "src/main/java/com/multivendor/service" -ItemType Directory -Force
New-Item -Path "src/main/resources" -ItemType Directory -Force

# Create main application class
$multiVendorApplicationContent = @'
package com.multivendor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MultiVendorApplication {
    public static void main(String[] args) {
        SpringApplication.run(MultiVendorApplication.class, args);
    }
}
'@

Set-Content -Path "src/main/java/com/multivendor/MultiVendorApplication.java" -Value $multiVendorApplicationContent

# Create SecurityConfig.java
$securityConfigContent = @'
package com.multivendor.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/vendors/register").permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }
}
'@

Set-Content -Path "src/main/java/com/multivendor/config/SecurityConfig.java" -Value $securityConfigContent

# Create Vendor model
$vendorModelContent = @'
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
    private Long id_vendor;

    private Long id_customer;
    private Long id_supplier;
    private String shop_name;
    private String logo;
    private String status;
    private Date date_add;

    // Getters and setters
    public Long getId_vendor() {
        return id_vendor;
    }

    public void setId_vendor(Long id_vendor) {
        this.id_vendor = id_vendor;
    }

    public Long getId_customer() {
        return id_customer;
    }

    public void setId_customer(Long id_customer) {
        this.id_customer = id_customer;
    }

    public Long getId_supplier() {
        return id_supplier;
    }

    public void setId_supplier(Long id_supplier) {
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
'@

Set-Content -Path "src/main/java/com/multivendor/model/Vendor.java" -Value $vendorModelContent

# Create VendorRepository interface
$vendorRepositoryContent = @'
package com.multivendor.repository;

import com.multivendor.model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Long> {
    Vendor findByIdCustomer(Long customerId);
    List<Vendor> findByStatus(String status);
}
'@

Set-Content -Path "src/main/java/com/multivendor/repository/VendorRepository.java" -Value $vendorRepositoryContent

# Create VendorController class
$vendorControllerContent = @'
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
'@

Set-Content -Path "src/main/java/com/multivendor/controller/VendorController.java" -Value $vendorControllerContent

# Create VendorService class
$vendorServiceContent = @'
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

    public Vendor registerVendor(Long customerId, String shopName, String logo) {
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

    public Vendor approveVendor(Long vendorId) {
        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));
        vendor.setStatus("active");
        return vendorRepository.save(vendor);
    }

    public Vendor rejectVendor(Long vendorId) {
        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));
        vendor.setStatus("rejected");
        return vendorRepository.save(vendor);
    }

    public List<Vendor> getPendingVendors() {
        return vendorRepository.findByStatus("pending");
    }

    public Vendor findByCustomerId(Long customerId) {
        return vendorRepository.findByIdCustomer(customerId);
    }
}
'@

Set-Content -Path "src/main/java/com/multivendor/service/VendorService.java" -Value $vendorServiceContent

# Create Customer model
$customerModelContent = @'
package com.multivendor.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_customer;

    private String email;
    private String firstname;
    private String lastname;
    private Date date_add;

    // Getters and Setters
    public Long getId_customer() {
        return id_customer;
    }

    public void setId_customer(Long id_customer) {
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
'@

Set-Content -Path "src/main/java/com/multivendor/model/Customer.java" -Value $customerModelContent

# Create Product model
$productModelContent = @'
package com.multivendor.model;

import javax.persistence.*;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_product;

    private String name;
    private Long id_category;

    // Getters and Setters
    public Long getId_product() {
        return id_product;
    }

    public void setId_product(Long id_product) {
        this.id_product = id_product;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId_category() {
        return id_category;
    }

    public void setId_category(Long id_category) {
        this.id_category = id_category;
    }
}
'@

Set-Content -Path "src/main/java/com/multivendor/model/Product.java" -Value $productModelContent

# Create CustomerService
$customerServiceContent = @'
package com.multivendor.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.multivendor.repository.CustomerRepository;
import com.multivendor.model.Customer;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public boolean exists(Long customerId) {
        // Implementation should check if the customer exists
        return customerRepository.existsById(customerId);
    }
}
'@

Set-Content -Path "src/main/java/com/multivendor/service/CustomerService.java" -Value $customerServiceContent

# Create CustomerRepository interface
$customerRepositoryContent = @'
package com.multivendor.repository;

import com.multivendor.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
'@

Set-Content -Path "src/main/java/com/multivendor/repository/CustomerRepository.java" -Value $customerRepositoryContent

# Create application.properties
$applicationPropertiesContent = @'
spring.datasource.url=jdbc:mysql://localhost:3306/multivendor_db
spring.datasource.username=root
spring.datasource.password=
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# Add JPA properties
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

# Logging configuration
logging.level.root=INFO
logging.level.com.multivendor=DEBUG
logging.level.org.springframework.web=INFO
logging.level.org.hibernate.SQL=DEBUG

# Server configuration
server.port=8080
'@

Set-Content -Path "src/main/resources/application.properties" -Value $applicationPropertiesContent

# Create pom.xml
$pomXmlContent = @'
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.7.8</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>

    <groupId>com.multivendor</groupId>
    <artifactId>multivendor-platform</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>Multi-Vendor E-Commerce Platform</name>
    <description>Spring Boot application for multi-vendor e-commerce platform</description>

    <properties>
        <!-- Fix for your Java version issue -->
        <java.version>17</java.version>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <dependencies>
        <!-- Spring Boot Starters -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-validation</artifactId>
        </dependency>

        <!-- Database -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.28</version>
        </dependency>

        <!-- JWT for stateless authentication -->
        <dependency>
            <groupId>io.jsonwebtoken</groupId>
            <artifactId>jjwt-api</artifactId>
            <version>0.11.5</version>
        </dependency>
        <dependency>
            <groupId>io.jsonwebtoken</groupId>
            <artifactId>jjwt-impl</artifactId>
            <version>0.11.5</version>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>io.jsonwebtoken</groupId>
            <artifactId>jjwt-jackson</artifactId>
            <version>0.11.5</version>
            <scope>runtime</scope>
        </dependency>

        <!-- Utils -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>

        <!-- Testing -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.security</groupId>
            <artifactId>spring-security-test</artifactId>
            <scope>test</scope>
        </dependency>

        <!-- Development Tools -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <excludes>
                        <exclude>
                            <groupId>org.projectlombok</groupId>
                            <artifactId>lombok</artifactId>
                        </exclude>
                    </excludes>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <configuration>
                    <source>17</source>
                    <target>17</target>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
'@

Set-Content -Path "pom.xml" -Value $pomXmlContent

# Create build.gradle
$buildGradleContent = @'
plugins {
    id 'org.springframework.boot' version '2.7.8'
    id 'io.spring.dependency-management' version '1.0.15.RELEASE'
    id 'java'
}

group = 'com.multivendor'
version = '0.0.1-SNAPSHOT'
sourceCompatibility = '17'

repositories {
    mavenCentral()
}

dependencies {
    // Spring Boot Starters
    implementation 'org.springframework.boot:spring-boot-starter-web'
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    implementation 'org.springframework.boot:spring-boot-starter-security'
    implementation 'org.springframework.boot:spring-boot-starter-validation'

    // Database
    implementation 'mysql:mysql-connector-java:8.0.28'

    // JWT for stateless authentication
    implementation 'io.jsonwebtoken:jjwt-api:0.11.5'
    runtimeOnly 'io.jsonwebtoken:jjwt-impl:0.11.5'
    runtimeOnly 'io.jsonwebtoken:jjwt-jackson:0.11.5'

    // Utils
    compileOnly 'org.projectlombok:lombok'
    annotationProcessor 'org.projectlombok:lombok'

    // Development Tools
    developmentOnly 'org.springframework.boot:spring-boot-devtools'

    // Testing
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
    testImplementation 'org.springframework.security:spring-security-test'
}

test {
    useJUnitPlatform()
}
'@

Set-Content -Path "build.gradle" -Value $buildGradleContent

Write-Host "All necessary files have been created in the proper directories." -ForegroundColor Green