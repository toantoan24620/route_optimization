package com.toanhv22.routeoptimization.dto.response;

import com.toanhv22.routeoptimization.entity.Employee;
import com.toanhv22.routeoptimization.entity.Vehicle;
import com.toanhv22.routeoptimization.entity.Warehouse;
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
    private String warehouseId;
    private LocalDateTime deliveryDate;
    private double weight;
    private int moveTime;
    private String status;

    private Vehicle vehicle;
    private Employee employee;
    private Warehouse warehouse;
    private int totalOrders;
}
