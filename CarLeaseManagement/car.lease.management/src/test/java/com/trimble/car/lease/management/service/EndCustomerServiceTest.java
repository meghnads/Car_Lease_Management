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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EndCustomerServiceTest {

    @Mock
    private CarRepository carRepository;

    @Mock
    private LeaseRepository leaseRepository;

    @Mock
    private EndCustomerRepository customerRepository;

    @InjectMocks
    private EndCustomerService endCustomerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterCustomer() {
        EndCustomer customer = new EndCustomer();
        customer.setId(1L);
        customer.setName("John Doe");

        when(customerRepository.save(any(EndCustomer.class))).thenReturn(customer);

        EndCustomer registeredCustomer = endCustomerService.registerCustomer(customer);

        assertEquals(1L, registeredCustomer.getId());
        assertEquals("John Doe", registeredCustomer.getName());
    }

    @Test
    void testGetAllCustomers() {
        EndCustomer customer1 = new EndCustomer();
        customer1.setId(1L);
        customer1.setName("John Doe");

        EndCustomer customer2 = new EndCustomer();
        customer2.setId(2L);
        customer2.setName("Jane Doe");

        when(customerRepository.findAll()).thenReturn(Arrays.asList(customer1, customer2));

        List<EndCustomer> customers = endCustomerService.getAllCustomers();

        assertEquals(2, customers.size());
        assertEquals("John Doe", customers.get(0).getName());
        assertEquals("Jane Doe", customers.get(1).getName());
    }

    @Test
    void testGetAllCars() {
        Car car1 = new Car();
        Car car2 = new Car();

        when(carRepository.findAll()).thenReturn(Arrays.asList(car1, car2));

        List<CarDto> cars = endCustomerService.getAllCars();

        assertEquals(2, cars.size());
        assertEquals(car1.getId(), cars.get(0).getId());
        assertEquals(car1.getMake(), cars.get(0).getMake());
        assertEquals(car1.getModel(), cars.get(0).getModel());
        assertEquals(car1.getYear(), cars.get(0).getYear());
        assertEquals(car1.getStatus(), cars.get(0).getStatus());

        assertEquals(car2.getId(), cars.get(1).getId());
        assertEquals(car2.getMake(), cars.get(1).getMake());
        assertEquals(car2.getModel(), cars.get(1).getModel());
        assertEquals(car2.getYear(), cars.get(1).getYear());
        assertEquals(car2.getStatus(), cars.get(1).getStatus());
    }

    @Test
    void testStartLease() {
        Long carId = 1L;
        Long customerId = 1L;
        Car car = new Car();
        car.setId(carId);
        car.setStatus("Available");

        EndCustomer customer = new EndCustomer();
        customer.setId(customerId);

        when(carRepository.findById(carId)).thenReturn(Optional.of(car));
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(leaseRepository.countByCustomerIdAndEndDateIsNull(customerId)).thenReturn(1L);
    }

    @Test
    void testStartLease_CarNotFound() {
        Long carId = 1L;
        Long customerId = 1L;

        when(carRepository.findById(carId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> endCustomerService.startLease(carId, customerId));
    }

    @Test
    void testStartLease_CustomerNotFound() {
        Long carId = 1L;
        Long customerId = 1L;
        Car car = new Car();
        car.setId(carId);
        car.setStatus("Available");

        when(carRepository.findById(carId)).thenReturn(Optional.of(car));
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> endCustomerService.startLease(carId, customerId));
    }

    @Test
    void testStartLease_TooManyActiveLeases() {
        Long carId = 1L;
        Long customerId = 1L;
        Car car = new Car();
        car.setId(carId);
        car.setStatus("Available");

        EndCustomer customer = new EndCustomer();
        customer.setId(customerId);

        when(carRepository.findById(carId)).thenReturn(Optional.of(car));
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(leaseRepository.countByCustomerIdAndEndDateIsNull(customerId)).thenReturn(2L);

        assertThrows(RuntimeException.class, () -> endCustomerService.startLease(carId, customerId));
    }

    @Test
    void testEndLease() {
        Long leaseId = 1L;
        Lease lease = new Lease();
        lease.setId(leaseId);
        lease.setStartDate(LocalDate.now().minusDays(1));
        lease.setCar(new Car());

        when(leaseRepository.findById(leaseId)).thenReturn(Optional.of(lease));

        endCustomerService.endLease(leaseId);

        assertNotNull(lease.getEndDate());
        verify(leaseRepository, times(1)).save(lease);
        verify(carRepository, times(1)).save(lease.getCar());
    }

    @Test
    void testEndLease_LeaseNotFound() {
        Long leaseId = 1L;

        when(leaseRepository.findById(leaseId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> endCustomerService.endLease(leaseId));
    }

    @Test
    void testGetCustomerLeaseHistory() {
        Long customerId = 1L;
        Lease lease1 = new Lease();
        Lease lease2 = new Lease();

        when(leaseRepository.findByCustomerId(customerId)).thenReturn(Arrays.asList(lease1, lease2));

        List<LeaseHistoryDto> leaseHistory = endCustomerService.getCustomerLeaseHistory(customerId);

        assertEquals(2, leaseHistory.size());
    }
}