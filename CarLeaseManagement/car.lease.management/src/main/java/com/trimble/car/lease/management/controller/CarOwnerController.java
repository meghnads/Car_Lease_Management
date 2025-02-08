package com.trimble.car.lease.management.controller;

import com.trimble.car.lease.management.dto.LeaseHistoryDto;
import com.trimble.car.lease.management.model.Car;
import com.trimble.car.lease.management.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/car-owner")
@RequiredArgsConstructor
public class CarOwnerController {

    private final CarService carService;

    @PostMapping("/register-car")
    public ResponseEntity<String> registerCar(@RequestBody Car car) {
        carService.registerCar(car);
        return ResponseEntity.ok("Car registered successfully!");
    }

    @GetMapping("/status/{carId}")
    public ResponseEntity<String> getCarStatus(@PathVariable Long carId) {
        String status = carService.getCarStatus(carId);
        return ResponseEntity.ok("Car status: " + status);
    }

    @GetMapping("/lease-history/{carId}")
    public ResponseEntity<List<LeaseHistoryDto>> getLeaseHistory(@PathVariable Long carId) {
        List<LeaseHistoryDto> history = carService.getLeaseHistory(carId);
        return ResponseEntity.ok(history);
    }
}
