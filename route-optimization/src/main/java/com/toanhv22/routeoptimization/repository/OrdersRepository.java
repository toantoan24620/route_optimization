package com.toanhv22.routeoptimization.repository;

import com.toanhv22.routeoptimization.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, String> {
}
