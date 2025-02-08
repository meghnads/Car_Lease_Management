package com.trimble.car.lease.management.service;


import com.trimble.car.lease.management.dto.CarDto;
import com.trimble.car.lease.management.dto.LeaseHistoryDto;
import com.trimble.car.lease.management.model.Car;
import com.trimble.car.lease.management.model.EndCustomer;
import com.trimble.car.lease.management.model.Lease;
import com.trimble.car.lease.management.repository.CarRepository;
import com.trimble.car.lease.management.repository.EndCustomerRepository;
import com.trimble.car.lease.management.repository.LeaseRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EndCustomerService {
    private final CarRepository carRepository;
    private final LeaseRepository leaseRepository;
    private final EndCustomerRepository customerRepository;

    public EndCustomer registerCustomer(EndCustomer customer) {
        return customerRepository.save(customer);
    }

    public List<EndCustomer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public List<CarDto> getAllCars() {
        return carRepository.findAll().stream()
                .map(car -> new CarDto(car.getId(), car.getMake(), car.getModel(), car.getYear(), car.getStatus()))
                .collect(Collectors.toList());
    }

    public void startLease(Long carId, Long customerId) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new EntityNotFoundException("Car not found"));
        EndCustomer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));

        long activeLeases = leaseRepository.countByCustomerIdAndEndDateIsNull(customerId);
        if (activeLeases >= 2) {
            throw new RuntimeException("Customer already has 2 active leases");
        }

        Lease lease = new Lease();
        lease.setCar(car);
        lease.setCustomer(customer);
        leaseRepository.save(lease);

        car.setStatus(lease.getStartDate().isAfter(LocalDate.now()) ? "Scheduled" : "Leased");
        carRepository.save(car);
    }

    public void endLease(Long leaseId) {
        Lease lease = leaseRepository.findById(leaseId)
                .orElseThrow(() -> new EntityNotFoundException("Lease not found"));
        LocalDate endDate = new java.util.Date().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        lease.setEndDate(endDate);
        leaseRepository.save(lease);

        Car car = lease.getCar();
        car.setStatus(lease.getStartDate().isAfter(LocalDate.now()) ? "Scheduled" : "Idle");
        carRepository.save(car);
    }

    public List<LeaseHistoryDto> getCustomerLeaseHistory(Long customerId) {
        return leaseRepository.findByCustomerId(customerId).stream()
                .map(lease -> new LeaseHistoryDto( ))
                .collect(Collectors.toList());
    }
}
