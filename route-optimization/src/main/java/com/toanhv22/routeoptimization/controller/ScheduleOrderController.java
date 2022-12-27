package com.toanhv22.routeoptimization.controller;

import com.toanhv22.routeoptimization.dto.response.ScheduleOrderResponse;
import com.toanhv22.routeoptimization.factory.response.GeneralResponse;
import com.toanhv22.routeoptimization.factory.response.ResponseFactory;
import com.toanhv22.routeoptimization.service.ScheduleOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("route-optimization/private/v1/schedule-orders")
public class ScheduleOrderController {

    private final ResponseFactory responseFactory;
    private final ScheduleOrderService scheduleOrderService;

    @GetMapping("/scheduleId/{scheduleId}")
    public ResponseEntity<GeneralResponse<List<ScheduleOrderResponse>>> findByScheduleId(@PathVariable String scheduleId){
        return responseFactory.success(scheduleOrderService.findByScheduleId(scheduleId));
    }

    @GetMapping("/warehouseId/{warehouseId}")
    public ResponseEntity<GeneralResponse<List<List<ScheduleOrderResponse>>>> findByWarehouseId(@PathVariable String warehouseId){
        return responseFactory.success(scheduleOrderService.findByWarehouseId(warehouseId));
    }
}
