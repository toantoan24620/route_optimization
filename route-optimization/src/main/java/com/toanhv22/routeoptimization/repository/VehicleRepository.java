package com.toanhv22.routeoptimization.repository;

import com.toanhv22.routeoptimization.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle,String> {
    List<Vehicle> findByNameContaining(String name);
    Optional<Vehicle> findByLicensePlateAndStatus(String licensePlate, Boolean active);
    List<Vehicle> findByStatus(Boolean status);
    Optional<Vehicle> findByLicensePlate(String licensePlate);
    List<Vehicle> findByCapacityWeightGreaterThanEqualAndStatusIsTrue(Integer capacityWeight);
}
