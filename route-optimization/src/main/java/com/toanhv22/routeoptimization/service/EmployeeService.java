package com.toanhv22.routeoptimization.service;

import com.toanhv22.routeoptimization.dto.response.EmployeeResponse;

import java.util.List;

public interface EmployeeService {
    List<EmployeeResponse> findAll();
}
