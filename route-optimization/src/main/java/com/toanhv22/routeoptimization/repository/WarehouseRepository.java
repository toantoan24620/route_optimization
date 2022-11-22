package com.toanhv22.routeoptimization.repository;

import com.toanhv22.routeoptimization.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, String> {
    List<Warehouse> findByNameContainingAndStatus(String name, Boolean status);
    Optional<Warehouse> findByIdAndStatus(String id, Boolean status);
    List<Warehouse> findByStatus(Boolean status);
    Optional<Warehouse> findByCode(String code);
    List<Warehouse> findByNameContaining(String name);

    @Query("SELECT w from Warehouse w " +
            "INNER JOIN Schedule s ON s.warehouseId = w.id AND s.id = ?1")
    Warehouse findByScheduleId(String scheduleId);
}
