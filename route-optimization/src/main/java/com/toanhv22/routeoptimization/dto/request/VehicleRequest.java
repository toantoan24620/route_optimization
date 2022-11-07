package com.toanhv22.routeoptimization.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleRequest {
    private String id;

    @NotNull(message = "Tên xe không được để trống")
    private String name;

    @NotNull(message = "Loại xe không được để trống")
    private String type;

    @NotNull(message = "Biến số xe không được để trống")
    private String licensePlate;

    @NotNull(message = "Năng lượng tiêu thụ của xe không được để trống")
    private Double averageFuelConsumption;

    @NotNull(message = "Mã nhà kho không được để trống")
    private String warehouseId;

    @NotNull(message = "Tải trọng xe không được để trống")
    private Integer capacityWeight;

    @NotNull(message = "Sức chứa của xe không được để trống")
    private Double capacityVolume;

    private Boolean shipped;

    private Boolean status;
}
