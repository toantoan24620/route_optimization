package com.toanhv22.routeoptimization.repository;

import com.toanhv22.routeoptimization.entity.Orders;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, String> {
    List<Orders> findByWarehouseIdAndStatus(String warehouseId, String status);
    List<Orders> findByWarehouseId(String warehouseId);
    List<Orders> findByWarehouseIdAndVehicleType(String warehouseId, String vehicleType);
    List<Orders> findByWarehouseIdAndVehicleTypeAndStatus(String warehouseId, String vehicleType, String status);

    List<Orders> findByCustomerIdAndStatus(String customerId, String status);
    List<Orders> findByCustomerIdAndVehicleType(String customerId, String vehicleType);
    List<Orders> findByCustomerIdAndVehicleTypeAndStatus(String customerId, String vehicleType, String status);
    List<Orders> findByCustomerId(String customerId);

    List<Orders> findByStatus(String status);
    List<Orders> findByVehicleTypeAndStatus(String vehicleType, String status);
    List<Orders> findByVehicleType(String vehicleType);
}
