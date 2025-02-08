package com.trimble.car.lease.management.dto;

public class CustomerDto {

    private Long customer_id;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;

    // Default Constructor
    public CustomerDto() {
    }

    // Parameterized Constructor
    public CustomerDto(Long id, String name, String email, String phoneNumber, String address) {
        this.customer_id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    // Getters and Setters
    public Long getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(Long id) {
        this.customer_id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    // Override toString for better logging and debugging
    @Override
    public String toString() {
        return "CustomerDto{" +
                "id=" + customer_id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}

