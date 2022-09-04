package com.toanhv22.routeoptimization.repository;

import com.toanhv22.routeoptimization.entity.ScheduleOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleOrderRepository extends JpaRepository<ScheduleOrder, String> {
}
