package com.trimble.car.lease.management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class LeaseHistoryDto {
    private LocalDate startDate;
    private LocalDate endDate;
}
