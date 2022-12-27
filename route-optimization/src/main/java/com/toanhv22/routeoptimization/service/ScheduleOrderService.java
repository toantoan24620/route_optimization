package com.toanhv22.routeoptimization.service;

import com.toanhv22.routeoptimization.dto.response.ScheduleOrderResponse;

import java.util.List;

public interface ScheduleOrderService {
    List<ScheduleOrderResponse> findByScheduleId(String scheduleId);

    List<List<ScheduleOrderResponse>> findByWarehouseId(String warehouseId);
}
