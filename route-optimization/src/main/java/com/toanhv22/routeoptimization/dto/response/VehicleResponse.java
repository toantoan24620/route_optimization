package com.toanhv22.routeoptimization.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleResponse {
    private String id;
    private String name;
    private Boolean status;
    private String type;
    private String licensePlate;
    private Double averageFuelConsumption;
    private String warehouseId;
    private Integer capacityWeight;
    private Double capacityVolume;
    private Boolean shipped;
    private WarehouseResponse warehouse;
}
