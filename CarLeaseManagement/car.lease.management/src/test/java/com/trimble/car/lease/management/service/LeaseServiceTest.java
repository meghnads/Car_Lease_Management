package com.trimble.car.lease.management.service;

import com.trimble.car.lease.management.dto.LeaseHistoryDto;
import com.trimble.car.lease.management.dto.LeaseRequestDto;
import com.trimble.car.lease.management.model.Car;
import com.trimble.car.lease.management.model.EndCustomer;
import com.trimble.car.lease.management.model.Lease;
import com.trimble.car.lease.management.repository.CarRepository;
import com.trimble.car.lease.management.repository.EndCustomerRepository;
import com.trimble.car.lease.management.repository.LeaseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LeaseServiceTest {

    @Mock
    private CarRepository carRepository;

    @Mock
    private EndCustomerRepository customerRepository;

    @Mock
    private LeaseRepository leaseRepository;

    @InjectMocks
    private LeaseService leaseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerCustomer() {
        EndCustomer customer = new EndCustomer();
        leaseService.registerCustomer(customer);
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    void getAvailableCars() {
        Car car1 = new Car();
        car1.setStatus("Idle");
        Car car2 = new Car();
        car2.setStatus("Idle");
        when(carRepository.findByStatus("Idle")).thenReturn(Arrays.asList(car1, car2));

        List<Car> availableCars = leaseService.getAvailableCars();
        assertEquals(2, availableCars.size());
        verify(carRepository, times(1)).findByStatus("Idle");
    }

    @Test
    void startLease() {
        LeaseRequestDto request = new LeaseRequestDto();
        request.setCarId(1L);
        request.setCustomerId(1L);

        Car car = new Car();
        car.setId(1L);
        car.setStatus("Idle");

        EndCustomer customer = new EndCustomer();
        customer.setId(1L);

        when(carRepository.findById(1L)).thenReturn(Optional.of(car));
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        leaseService.startLease(request);

        assertEquals("On Lease", car.getStatus());
        verify(leaseRepository, times(1)).save(any(Lease.class));
        verify(carRepository, times(1)).save(car);
    }

    @Test
    void startLease_CarNotAvailable() {
        LeaseRequestDto request = new LeaseRequestDto();
        request.setCarId(1L);
        request.setCustomerId(1L);

        Car car = new Car();
        car.setId(1L);
        car.setStatus("On Lease");

        when(carRepository.findById(1L)).thenReturn(Optional.of(car));

        Exception exception = assertThrows(RuntimeException.class, () -> leaseService.startLease(request));
        assertEquals("Car not available for lease", exception.getMessage());
    }

    @Test
    void endLease() {
        Lease lease = new Lease();
        lease.setId(1L);
        lease.setStartDate(LocalDate.now());
        lease.setCar(new Car());
        lease.getCar().setStatus("On Lease");

        when(leaseRepository.findById(1L)).thenReturn(Optional.of(lease));

        leaseService.endLease(1L);

        assertEquals("Idle", lease.getCar().getStatus());
        assertNotNull(lease.getEndDate());
        verify(leaseRepository, times(1)).save(lease);
        verify(carRepository, times(1)).save(lease.getCar());
    }

    @Test
    void getCustomerHistory() {
        Lease lease1 = new Lease();
        lease1.setStartDate(LocalDate.now().minusDays(10));
        lease1.setEndDate(LocalDate.now().minusDays(5));

        Lease lease2 = new Lease();
        lease2.setStartDate(LocalDate.now().minusDays(20));
        lease2.setEndDate(LocalDate.now().minusDays(15));

        when(leaseRepository.findByCustomerId(1L)).thenReturn(Arrays.asList(lease1, lease2));

        List<LeaseHistoryDto> history = leaseService.getCustomerHistory(1L);
        assertEquals(2, history.size());
        verify(leaseRepository, times(1)).findByCustomerId(1L);
    }

    @Test
    void getAllCustomers() {
        EndCustomer customer1 = new EndCustomer();
        EndCustomer customer2 = new EndCustomer();
        when(customerRepository.findAll()).thenReturn(Arrays.asList(customer1, customer2));

        List<EndCustomer> customers = leaseService.getAllCustomers();
        assertEquals(2, customers.size());
        verify(customerRepository, times(1)).findAll();
    }
}

