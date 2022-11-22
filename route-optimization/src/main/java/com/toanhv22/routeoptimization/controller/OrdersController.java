package com.toanhv22.routeoptimization.controller;

import com.toanhv22.routeoptimization.dto.request.OrdersRequest;
import com.toanhv22.routeoptimization.dto.response.OrdersResponse;
import com.toanhv22.routeoptimization.factory.response.GeneralResponse;
import com.toanhv22.routeoptimization.factory.response.ResponseFactory;
import com.toanhv22.routeoptimization.service.OrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("route-optimization/private/v1/orders")
@Validated
public class OrdersController {
    private final ResponseFactory responseFactory;
    private final OrdersService ordersService;

    @GetMapping("/id/{id}")
    public ResponseEntity<GeneralResponse<OrdersResponse>> findById(@PathVariable String id){
        return responseFactory.success(ordersService.findById(id));
    }

    @GetMapping("/customer/{username}")
    public ResponseEntity<GeneralResponse<List<OrdersResponse>>> findByUsername(@PathVariable String username, @RequestParam String status, @RequestParam String vehicleType){
        return responseFactory.success(ordersService.findByUsername(username, vehicleType,status));
    }

    @GetMapping("/warehouse/{warehouseCode}")
    public ResponseEntity<GeneralResponse<List<OrdersResponse>>> findByWarehouseCode(@PathVariable String warehouseCode, @RequestParam String status, @RequestParam String vehicleType){
        return responseFactory.success(ordersService.findByWarehouseCode(warehouseCode, vehicleType, status));
    }

    @GetMapping("/vehicle/{vehicleType}/status/{status}")
    public ResponseEntity<GeneralResponse<List<OrdersResponse>>> findByVehicleTypeAndStatus(@PathVariable String vehicleType, @PathVariable String status){
        return responseFactory.success(ordersService.findByVehicleTypeAndStatus(vehicleType, status));
    }

    @PostMapping
    public ResponseEntity<GeneralResponse<OrdersResponse>> create(@RequestBody @Valid OrdersRequest ordersRequest){
        return responseFactory.success(ordersService.create(ordersRequest));
    }

    @PutMapping
    public ResponseEntity<GeneralResponse<OrdersResponse>> update(@RequestBody @Valid OrdersRequest ordersRequest){
        return responseFactory.success(ordersService.update(ordersRequest));
    }
}
