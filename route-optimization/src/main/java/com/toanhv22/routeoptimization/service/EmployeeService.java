package com.toanhv22.routeoptimization.service;

import com.toanhv22.routeoptimization.dto.request.EmployeeRequest;
import com.toanhv22.routeoptimization.dto.response.EmployeeResponse;

import java.util.List;

public interface EmployeeService {
    List<EmployeeResponse> findAll(Boolean active);

    void delete(String employeeId);

    List<EmployeeResponse> findByNameContaining(String name, Boolean active);

    EmployeeResponse findByStaffCode(String staffCode, Boolean active);

    EmployeeResponse create(EmployeeRequest employeeRequest);

    EmployeeResponse update(EmployeeRequest employeeRequest);
}
