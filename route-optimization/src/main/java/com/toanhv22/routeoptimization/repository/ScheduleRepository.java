package com.toanhv22.routeoptimization.repository;

import com.toanhv22.routeoptimization.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, String> {
}
