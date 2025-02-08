package com.trimble.car.lease.management.controller;

import com.trimble.car.lease.management.dto.LeaseHistoryDto;
import com.trimble.car.lease.management.dto.LeaseRequestDto;
import com.trimble.car.lease.management.service.LeaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/leases")
public class LeaseController {

    private final LeaseService leaseService;
    private static final Logger logger = LoggerFactory.getLogger(LeaseController.class);

    public LeaseController(LeaseService leaseService) {
        this.leaseService = leaseService;
    }

    @PostMapping("/start")
    public LeaseRequestDto startLease(@RequestBody LeaseRequestDto request) {
        logger.info("Request to start lease: {}", request);
        leaseService.startLease(request);
        return request;
    }

    @PostMapping("/end/{leaseId}")
    public ResponseEntity<String> endLease(@PathVariable Long leaseId) {
        logger.info("Request to end lease with id: {}", leaseId);
        leaseService.endLease(leaseId);
        return ResponseEntity.ok("Lease ended successfully");
    }

    @GetMapping("/history/{customerId}")
    public List<LeaseHistoryDto> getCustomerLeaseHistory(@PathVariable Long customerId) {
        logger.info("Request to get lease history for customer with id: {}", customerId);
        return leaseService.getCustomerHistory(customerId);
    }
}

