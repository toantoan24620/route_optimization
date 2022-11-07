package com.toanhv22.routeoptimization.service;

import com.toanhv22.routeoptimization.dto.response.ScheduleResponse;

import java.util.List;

public interface ScheduleService {
    List<ScheduleResponse> findAll();

    List<ScheduleResponse> findByWarehouseName(String warehouseName);

    List<ScheduleResponse> findByVehicle(String licensePlate);

    List<ScheduleResponse> findByEmployee(String employeeCode);
}
