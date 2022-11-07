package com.toanhv22.routeoptimization.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleCreationRequest {
    private String warehouseId;
    private double capacity;
}
