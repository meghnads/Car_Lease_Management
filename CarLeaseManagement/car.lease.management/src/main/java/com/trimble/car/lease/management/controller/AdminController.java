package com.trimble.car.lease.management.controller;

import com.trimble.car.lease.management.dto.LeaseHistoryDto;
import com.trimble.car.lease.management.dto.LeaseRequestDto;
import com.trimble.car.lease.management.model.Car;
import com.trimble.car.lease.management.model.EndCustomer;
import com.trimble.car.lease.management.service.CarService;
import com.trimble.car.lease.management.service.LeaseService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final CarService carService;
    private final LeaseService leaseService;
    private static final Logger logger = LoggerFactory.getLogger(CarController.class);

    // Retrieve all cars
    @GetMapping("/all-cars")
    public ResponseEntity<List<Car>> getAllCars() {
        logger.info("Fetching all cars for the admin");
        return ResponseEntity.ok(carService.getAllCars());
    }

    // Register a car on behalf of a car owner
    @PostMapping("/register-car")
    public ResponseEntity<String> registerCar(@RequestBody Car car) {
        carService.registerCar(car);
        return ResponseEntity.ok("Car registered successfully by Admin!");
    }

    // Get car status
    @GetMapping("/status/{carId}")
    public ResponseEntity<String> getCarStatus(@PathVariable Long carId) {
        return ResponseEntity.ok(carService.getCarStatus(carId));
    }

    // View lease history for a car
    @GetMapping("/lease-history/{carId}")
    public ResponseEntity<List<LeaseHistoryDto>> getCarLeaseHistory(@PathVariable Long carId) {
        return ResponseEntity.ok(carService.getLeaseHistory(carId));
    }

    // Register a customer
    @PostMapping("/register-customer")
    public ResponseEntity<String> registerCustomer(@RequestBody EndCustomer customer) {
        leaseService.registerCustomer(customer);
        return ResponseEntity.ok("Customer registered successfully by Admin!");
    }

    // Get all registered customers
    @GetMapping("/all-customers")
    public ResponseEntity<List<EndCustomer>> getAllCustomers() {
        return ResponseEntity.ok(leaseService.getAllCustomers());
    }

    // Start a lease on behalf of a customer
    @PostMapping("/start-lease")
    public ResponseEntity<String> startLease(@RequestBody LeaseRequestDto request) {
        leaseService.startLease(request);
        return ResponseEntity.ok("Lease started successfully by Admin!");
    }

    // End a lease on behalf of a customer
    @PostMapping("/end-lease")
    public ResponseEntity<String> endLease(@RequestParam Long leaseId) {
        leaseService.endLease(leaseId);
        return ResponseEntity.ok("Lease ended successfully by Admin!");
    }

    @PostMapping("/update-car-status/{carId}")
    public ResponseEntity<String> updateCarStatus(@PathVariable Long carId, @RequestParam String status) {
        carService.updateCarStatus(carId, status);
        return ResponseEntity.ok("Car status updated by Admin!");
    }
}


