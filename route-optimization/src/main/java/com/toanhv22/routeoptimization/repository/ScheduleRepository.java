package com.toanhv22.routeoptimization.repository;

import com.toanhv22.routeoptimization.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, String> {
    List<Schedule> findByWarehouseIdIn(List<String> warehouseIds);
    List<Schedule> findByVehicleId(String vehicleId);
    List<Schedule> findByEmployeeId(String employeeId);
    List<Schedule> findByDeliveryDate(LocalDate deliveryDate);
}
