package com.toanhv22.routeoptimization.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleResponse {
    private String id;
    private String vehicleId;
    private String employeeId;
    private LocalDateTime deliveryDate;
}
