package com.toanhv22.routeoptimization.service;

import com.toanhv22.routeoptimization.dto.request.VehicleRequest;
import com.toanhv22.routeoptimization.dto.response.VehicleResponse;

import java.util.List;

public interface VehicleService {
    List<VehicleResponse> getAll(Boolean active);

    VehicleResponse getByLicensePlate(String licensePlate, Boolean active);

    List<VehicleResponse> getByName(String name);

    VehicleResponse create(VehicleRequest vehicleRequest);

    VehicleResponse update(VehicleRequest vehicleRequest);

    void delete(String vehicleId);

    VehicleResponse getById(String id);

    List<VehicleResponse> getByWeightGreater(Integer weight);
}
