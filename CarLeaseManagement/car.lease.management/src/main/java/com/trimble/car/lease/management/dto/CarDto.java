package com.trimble.car.lease.management.dto;

public class CarDto {
    private Long id;
    private String make;
    private String model;
    private String status;

    public CarDto(Long id, String make, String model, String status) {
        this.id = id;
        this.make = make;
        this.model = model;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
