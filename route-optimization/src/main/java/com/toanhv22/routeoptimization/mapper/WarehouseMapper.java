package com.toanhv22.routeoptimization.mapper;

import com.toanhv22.routeoptimization.dto.request.WarehouseRequest;
import com.toanhv22.routeoptimization.dto.response.WarehouseResponse;
import com.toanhv22.routeoptimization.entity.Warehouse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WarehouseMapper {
    WarehouseResponse entityToResponse(Warehouse warehouse);
    Warehouse requestToEntity(WarehouseRequest warehouseRequest);
}
