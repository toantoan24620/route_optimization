package com.toanhv22.routeoptimization.service;

import com.toanhv22.routeoptimization.dto.request.OrdersRequest;
import com.toanhv22.routeoptimization.dto.response.OrdersResponse;

import java.util.List;

public interface OrdersService {
    List<OrdersResponse> findByWarehouseCode(String warehouseCode, String vehicleType, String status);
    List<OrdersResponse> findByUsername(String username, String vehicleType, String status);
    List<OrdersResponse> findByVehicleTypeAndStatus(String vehicleType, String status);
    OrdersResponse findById(String id);

    OrdersResponse create(OrdersRequest ordersRequest);
    OrdersResponse update(OrdersRequest ordersRequest);
}
