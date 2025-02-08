package com.trimble.car.lease.management.repository;

import com.trimble.car.lease.management.model.EndCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EndCustomerRepository extends JpaRepository<EndCustomer, Long> {
}