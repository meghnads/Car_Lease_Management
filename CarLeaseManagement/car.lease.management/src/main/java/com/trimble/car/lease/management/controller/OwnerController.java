package com.trimble.car.lease.management.controller;

import com.trimble.car.lease.management.model.Owner;
import com.trimble.car.lease.management.service.OwnerService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/owners")
@RequiredArgsConstructor
public class OwnerController {

    private final OwnerService ownerService;
    private static final Logger logger = LoggerFactory.getLogger(OwnerController.class);

    @PostMapping("/register")
    public ResponseEntity<Owner> registerOwner(@RequestBody Owner owner) {
        logger.info("Request to register owner: {}", owner);
        Owner registeredOwner = ownerService.registerOwner(owner);
        return ResponseEntity.ok(registeredOwner);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOwner(@PathVariable Long id) {
        logger.info("Request to delete owner with id: {}", id);
        ownerService.deleteOwner(id);
        return ResponseEntity.noContent().build();
    }
}
