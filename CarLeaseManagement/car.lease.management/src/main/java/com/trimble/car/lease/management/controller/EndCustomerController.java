package com.trimble.car.lease.management.controller;

import com.trimble.car.lease.management.dto.LeaseHistoryDto;
import com.trimble.car.lease.management.dto.LeaseRequestDto;
import com.trimble.car.lease.management.model.Car;
import com.trimble.car.lease.management.model.EndCustomer;
import com.trimble.car.lease.management.service.LeaseService;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/api/end-customer")
@RequiredArgsConstructor
public class EndCustomerController {

    private final LeaseService leaseService;

    @PostMapping("/register")
    public ResponseEntity<String> registerCustomer(@RequestBody EndCustomer customer) {
        leaseService.registerCustomer(customer);
        return ResponseEntity.ok("Customer registered successfully!");
    }

    @GetMapping("/available-cars")
    public ResponseEntity<List<Car>> getAvailableCars() {
        List<Car> cars = leaseService.getAvailableCars();
        return ResponseEntity.ok(cars);
    }

    @PostMapping("/start-lease")
    public ResponseEntity<String> startLease(@RequestBody LeaseRequestDto request) {
        leaseService.startLease(request);
        return ResponseEntity.ok("Lease started successfully!");
    }

    @PostMapping("/end-lease")
    public ResponseEntity<String> endLease(@RequestParam Long leaseId) {
        leaseService.endLease(leaseId);
        return ResponseEntity.ok("Lease ended successfully!");
    }

    @GetMapping("/history/{customerId}")
    public ResponseEntity<List<LeaseHistoryDto>> getCustomerHistory(@PathVariable Long customerId) {
        List<LeaseHistoryDto> history = leaseService.getCustomerHistory(customerId);
        return ResponseEntity.ok(history);
    }
}
