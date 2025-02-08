package com.trimble.car.lease.management.repository;

import com.trimble.car.lease.management.model.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerRepository extends JpaRepository<Owner, Long> {
}
