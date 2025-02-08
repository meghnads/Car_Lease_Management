package com.trimble.car.lease.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trimble.car.lease.management.model.Lease;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaseRepository extends JpaRepository<Lease, Long> {
    List<Lease> findByCarId(Long carId);
    List<Lease> findByCustomerId(Long customerId);
}
