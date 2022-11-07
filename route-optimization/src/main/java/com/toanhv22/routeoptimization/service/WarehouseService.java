package com.toanhv22.routeoptimization.service;

import com.toanhv22.routeoptimization.dto.request.WarehouseRequest;
import com.toanhv22.routeoptimization.dto.response.WarehouseResponse;
import com.toanhv22.routeoptimization.entity.Warehouse;

import java.util.List;

public interface WarehouseService {
    List<WarehouseResponse> findAll(Boolean active);
    WarehouseResponse findById(String id,Boolean active);
    List<WarehouseResponse> findByName(String name,Boolean active);
    List<WarehouseResponse>  findByArea(String area,Boolean active);
    void delete(String warehouseID);
    WarehouseResponse create(WarehouseRequest warehouseRequest);
    WarehouseResponse update(WarehouseRequest warehouseRequest);
}
