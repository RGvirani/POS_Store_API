package com.POS_Store.App.models;

import jakarta.persistence.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@Entity
@Table(name = "employees")
public class EmployeeM {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false,unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Position position;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    private Double salary;

    @Column(nullable = false, unique = true)
    private String phone;

    @Column(nullable = false)
    private LocalDate hireDate;

    @Enumerated(EnumType.STRING)
    @Column
    private Department department;

    @Column
    private String address;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public enum Role {
        EMPLOYEE,
        ADMIN
    }

    public enum Position {
        // Sales Department
        SALES_ASSOCIATE,
        ONLINE_SALES_SPECIALIST,
        SALES_MANAGER,

        // Customer Service Department
        CUSTOMER_SERVICE_REPRESENTATIVE,
        RETURNS_SPECIALIST,
        CUSTOMER_SERVICE_MANAGER,

        // Inventory Department
        INVENTORY_CLERK,
        STOCK_MANAGER,
        WAREHOUSE_ASSISTANT,

        // Marketing Department
        MARKETING_COORDINATOR,
        SOCIAL_MEDIA_MANAGER,
        DIGITAL_MARKETING_SPECIALIST,
        VISUAL_MERCHANDISER,

        // Finance Department
        ACCOUNTANT,
        PAYROLL_SPECIALIST,
        FINANCIAL_ANALYST,

        // Human Resources Department
        HR_COORDINATOR,
        TRAINING_SPECIALIST,
        HR_MANAGER,

        // Operations Department
        STORE_MANAGER,
        ASSISTANT_STORE_MANAGER,
        OPERATIONS_MANAGER,

        // E-commerce and IT Department
        WEB_DEVELOPER,
        ECOMMERCE_MANAGER,
        IT_SUPPORT_SPECIALIST,

        // Specialized Positions
        SHOE_SPECIALIST,
        CUSTOMIZATION_SPECIALIST,
        REPAIR_SPECIALIST
    }


    public enum Department {
        SALES,
        CUSTOMER_SERVICE,
        INVENTORY,
        MARKETING,
        FINANCE,
        HUMAN_RESOURCES,
        OPERATIONS,
        ECOMMERCE,
        IT
    }



    // Getters and Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password  = passwordEncoder.encode(password);
    }

    public Position getPosition() {
        return position;
    }
    public void setPosition(Position position) {
        this.position = position;
    }

    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
    }

    public Double getSalary() {
        return salary;
    }
    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }
    public void setHireDate(LocalDate hireDate) {

        this.hireDate = hireDate;
    }

    public Department getDepartment() {
        return department;
    }
    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    @PrePersist
    public void prePersist() {
        // Set the createdAt field to the current UTC time before persisting the entity
        if (hireDate == null) {
            hireDate = LocalDate.now();
        }
    }

}
