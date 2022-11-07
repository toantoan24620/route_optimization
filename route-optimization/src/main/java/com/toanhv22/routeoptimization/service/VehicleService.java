package com.toanhv22.routeoptimization.service;

import com.toanhv22.routeoptimization.dto.request.VehicleRequest;
import com.toanhv22.routeoptimization.dto.response.VehicleResponse;
import com.toanhv22.routeoptimization.entity.Vehicle;

import java.util.List;
import java.util.Optional;

public interface VehicleService {
    List<VehicleResponse> getAll(Boolean active);

    List<VehicleResponse> getByShipped(Boolean shipped);

    VehicleResponse getByLicensePlate(String licensePlate, Boolean active);

    List<VehicleResponse> getByName(String name);

    VehicleResponse create(VehicleRequest vehicleRequest);

    VehicleResponse update(VehicleRequest vehicleRequest);

    void delete(String vehicleId);

    VehicleResponse getById(String id);
}
