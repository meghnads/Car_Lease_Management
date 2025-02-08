package com.trimble.car.lease.management.service;

import com.trimble.car.lease.management.dto.LeaseRequestDto;
import com.trimble.car.lease.management.dto.LeaseHistoryDto;
import com.trimble.car.lease.management.model.Car;
import com.trimble.car.lease.management.model.EndCustomer;
import com.trimble.car.lease.management.model.Lease;
import com.trimble.car.lease.management.repository.CarRepository;
import com.trimble.car.lease.management.repository.EndCustomerRepository;
import com.trimble.car.lease.management.repository.LeaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LeaseService {

    private final CarRepository carRepository;
    private final EndCustomerRepository customerRepository;
    private final LeaseRepository leaseRepository;

    public void registerCustomer(EndCustomer customer) {
        customerRepository.save(customer);
    }

    public List<Car> getAvailableCars() {
        return carRepository.findByStatus("Idle");
    }

    public void startLease(LeaseRequestDto request) {
        Optional<Car> car = carRepository.findById(request.getCarId());
        if (car.isPresent() && car.get().getStatus().equals("Idle")) {
            Lease lease = new Lease();
            lease.setCar(car.get());
            lease.setStartDate(LocalDate.now());
            lease.setCustomer(customerRepository.findById(request.getCustomerId()).orElseThrow());
            car.get().setStatus("On Lease");
            leaseRepository.save(lease);
            carRepository.save(car.get());
        } else {
            throw new RuntimeException("Car not available for lease");
        }
    }

    public void endLease(Long leaseId) {
        Lease lease = leaseRepository.findById(leaseId).orElseThrow();
        lease.setEndDate(LocalDate.now());
        Car car = lease.getCar();
        car.setStatus("Idle");
        carRepository.save(car);
        leaseRepository.save(lease);
    }

    public List<LeaseHistoryDto> getCustomerHistory(Long customerId) {
        return leaseRepository.findByCustomerId(customerId).stream()
                .map(lease -> new LeaseHistoryDto(lease.getStartDate(), lease.getEndDate()))
                .collect(Collectors.toList());
    }

    public List<EndCustomer> getAllCustomers() {
        return customerRepository.findAll();
    }
}

