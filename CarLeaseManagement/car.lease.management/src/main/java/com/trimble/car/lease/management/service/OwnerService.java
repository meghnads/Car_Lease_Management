package com.trimble.car.lease.management.service;

import com.trimble.car.lease.management.model.Owner;
import com.trimble.car.lease.management.repository.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OwnerService {

    private final OwnerRepository ownerRepository;

    public Owner registerOwner(Owner owner) {
        return ownerRepository.save(owner);
    }

    public void deleteOwner(Long id) {
        ownerRepository.deleteById(id);
    }
}