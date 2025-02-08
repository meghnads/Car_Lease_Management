package com.trimble.car.lease.management.controller;

import com.trimble.car.lease.management.model.Car;
import com.trimble.car.lease.management.service.CarService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;
    private static final Logger logger = LoggerFactory.getLogger(CarController.class);

    @GetMapping("/all")
    public List<Car> getAllCars() {
        logger.info("Request to get all cars");
        return carService.getAllCars();
    }

    @PostMapping("/register")
    public Car registerCar(@RequestBody Car car) {
        logger.info("Request to register a car: {}", car);
        return carService.registerCar(car);
    }

}

