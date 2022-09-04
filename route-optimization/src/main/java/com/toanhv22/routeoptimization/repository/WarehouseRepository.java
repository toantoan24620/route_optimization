package com.toanhv22.routeoptimization.repository;

import com.toanhv22.routeoptimization.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, String> {
}
