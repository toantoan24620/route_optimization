package com.toanhv22.routeoptimization.repository;

import com.toanhv22.routeoptimization.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle,String> {
}
