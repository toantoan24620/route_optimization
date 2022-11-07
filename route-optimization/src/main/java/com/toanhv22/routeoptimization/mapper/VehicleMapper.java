package com.toanhv22.routeoptimization.mapper;

import com.toanhv22.routeoptimization.dto.request.VehicleRequest;
import com.toanhv22.routeoptimization.dto.response.VehicleResponse;
import com.toanhv22.routeoptimization.entity.Vehicle;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VehicleMapper {
    VehicleResponse entityToResponse(Vehicle vehicle);

    Vehicle requestToEntity(VehicleRequest vehicleRequest);
}
