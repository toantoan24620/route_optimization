package com.toanhv22.routeoptimization.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleUpdateRequest {
    private String id;
    private LocalDate deliveryDate;
    private String vehicleId;
    private String employeeId;
    private String status;
}