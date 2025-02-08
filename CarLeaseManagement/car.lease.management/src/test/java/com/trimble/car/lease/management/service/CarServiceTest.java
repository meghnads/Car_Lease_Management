package com.trimble.car.lease.management.service;

import com.trimble.car.lease.management.dto.CarDto;
import com.trimble.car.lease.management.model.Car;
import com.trimble.car.lease.management.model.EndCustomer;
import com.trimble.car.lease.management.repository.CarRepository;
import com.trimble.car.lease.management.repository.EndCustomerRepository;
import com.trimble.car.lease.management.repository.LeaseRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CarServiceTest {

    @InjectMocks
    private CarService carService;

    @Mock
    private CarRepository carRepository;

    @Mock
    private LeaseRepository leaseRepository;

    @Mock
    private EndCustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterCar() {
        Car car = new Car();
        car.setMake("Toyota");
        car.setModel("Camry");

        when(carRepository.save(any(Car.class))).thenReturn(car);

        Car result = carService.registerCar(car);

        assertNotNull(result);
        assertEquals("Idle", result.getStatus());
        verify(carRepository, times(1)).save(car);
    }

    @Test
    void testGetCarStatus_CarExists() {
        Car car = new Car();
        car.setStatus("Leased");
        when(carRepository.findById(1L)).thenReturn(Optional.of(car));

        String status = carService.getCarStatus(1L);

        assertEquals("Leased", status);
        verify(carRepository, times(1)).findById(1L);
    }

    @Test
    void testGetCarStatus_CarDoesNotExist() {
        when(carRepository.findById(1L)).thenReturn(Optional.empty());

        String status = carService.getCarStatus(1L);

        assertEquals("Car not found", status);
    }

    @Test
    void testGetAllCars() {
        Car car1 = new Car();
        Car car2 = new Car();
        when(carRepository.findAll()).thenReturn(List.of(car1, car2));

        List<Car> result = carService.getAllCars();

        assertEquals(2, result.size());
        verify(carRepository, times(1)).findAll();
    }

    @Test
    void testUpdateCarStatus_CarExists() {
        Car car = new Car();
        car.setStatus("Idle");
        when(carRepository.findById(1L)).thenReturn(Optional.of(car));

        carService.updateCarStatus(1L, "Leased");

        assertEquals("Leased", car.getStatus());
        verify(carRepository, times(1)).save(car);
    }

    @Test
    void testUpdateCarStatus_CarNotFound() {
        when(carRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            carService.updateCarStatus(1L, "Leased");
        });

        assertEquals("Car not found", exception.getMessage());
    }

    @Test
    void testGetCarsByStatus() {
        Car car1 = new Car();
        car1.setStatus("Idle");
        Car car2 = new Car();
        car2.setStatus("Idle");

        when(carRepository.findByStatus("Idle")).thenReturn(List.of(car1, car2));

        List<CarDto> result = carService.getCarsByStatus("Idle");

        assertEquals(2, result.size());
        assertEquals("Idle", result.get(0).getStatus());
    }

    @Test
    void testRegisterCustomerToCar_Success() {
        Car car = new Car();
        car.setId(1L);
        car.setStatus("Idle");
        EndCustomer customer = new EndCustomer();
        customer.setId(1L);

        when(carRepository.findById(1L)).thenReturn(Optional.of(car));
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        carService.registerCustomerToCar(1L, 1L);

        assertEquals("Leased", car.getStatus());
        verify(carRepository, times(1)).save(car);
    }

    @Test
    void testRegisterCustomerToCar_CarNotFound() {
        when(carRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            carService.registerCustomerToCar(1L, 1L);
        });

        assertEquals("Car not found", exception.getMessage());
    }

    @Test
    void testRegisterCustomerToCar_CustomerNotFound() {
        Car car = new Car();
        car.setId(1L);

        when(carRepository.findById(1L)).thenReturn(Optional.of(car));
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            carService.registerCustomerToCar(1L, 1L);
        });

        assertEquals("Customer not found", exception.getMessage());
    }
}

