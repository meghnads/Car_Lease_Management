package com.trimble.car.lease.management.dto;

import lombok.Data;

@Data
public class LeaseRequestDto {
    private Long carId;
    private Long customerId;
}
