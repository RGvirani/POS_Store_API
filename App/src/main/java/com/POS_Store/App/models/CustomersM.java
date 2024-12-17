package com.POS_Store.App.models;

import jakarta.persistence.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;



import java.time.ZonedDateTime;
import java.time.ZoneOffset;

@Entity
@Table(name = "Customers")
public class CustomersM {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false, length = 15)
    private String firstName;
    @Column(nullable = false,length = 15)
    private String lastName;
    @Column(nullable = false, unique = true,length = 100)
    private String email;
    @Column(nullable = false,length = 255)
    private String password;
    @Column(nullable = false, unique = true,length = 25)
    private String phone;
    @Column(length = 255)
    private String address;
    @Column(nullable = false, updatable = false)
    private ZonedDateTime createdAt;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    //Getters and setters

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
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
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
        if (createdAt == null) {
            createdAt = ZonedDateTime.now(ZoneOffset.UTC);
        }
    }

}
