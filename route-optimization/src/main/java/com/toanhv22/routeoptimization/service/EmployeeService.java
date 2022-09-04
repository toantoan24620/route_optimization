package com.toanhv22.routeoptimization.service;

import com.toanhv22.routeoptimization.dto.request.EmployeeRequest;
import com.toanhv22.routeoptimization.dto.response.EmployeeResponse;
import com.toanhv22.routeoptimization.entity.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    List<EmployeeResponse> findAll();

    void delete(String employeeId);

    List<EmployeeResponse> findByNameContaining(String name);

    EmployeeResponse findByStaffCode(String staffCode);

    EmployeeResponse create(EmployeeRequest employeeRequest);

    EmployeeResponse update(EmployeeRequest employeeRequest);
}
