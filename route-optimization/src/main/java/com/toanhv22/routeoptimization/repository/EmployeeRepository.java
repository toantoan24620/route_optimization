package com.toanhv22.routeoptimization.repository;

import com.toanhv22.routeoptimization.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,String> {
    List<Employee> findByNameContaining(String name);
    Optional<Employee> findByStaffCode(String staffCode);
    Optional<Employee> findByIdentifyNumber(String identifyNumber);
}
