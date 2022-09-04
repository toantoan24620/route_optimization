package com.toanhv22.routeoptimization.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleResponse {
    private String id;
    private String vehicleName;
    private String status;
    private String type;
    private String licensePlate;
    private Double averageFuelConsumption;
    private String warehouseId;
}
