package com.trimble.car.lease.management.service;

import com.trimble.car.lease.management.model.Owner;
import com.trimble.car.lease.management.repository.OwnerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OwnerServiceTest {

    @Mock
    private OwnerRepository ownerRepository;

    @InjectMocks
    private OwnerService ownerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterOwner() {
        Owner owner = new Owner();
        owner.setId(1L);
        owner.setName("John Doe");

        when(ownerRepository.save(any(Owner.class))).thenReturn(owner);

        Owner registeredOwner = ownerService.registerOwner(owner);

        assertEquals(1L, registeredOwner.getId());
        assertEquals("John Doe", registeredOwner.getName());
    }

    @Test
    void testDeleteOwner_Success() {
        Long ownerId = 1L;
        doNothing().when(ownerRepository).deleteById(ownerId);

        ownerService.deleteOwner(ownerId);

        verify(ownerRepository, times(1)).deleteById(ownerId);
    }

    @Test
    void testDeleteOwner_NotFound() {
        Long ownerId = 1L;
        doThrow(new RuntimeException("Owner not found")).when(ownerRepository).deleteById(ownerId);

        assertThrows(RuntimeException.class, () -> ownerService.deleteOwner(ownerId));
    }
}