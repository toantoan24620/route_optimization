package com.toanhv22.routeoptimization.service.impl;

import com.toanhv22.routeoptimization.dto.response.EmployeeResponse;
import com.toanhv22.routeoptimization.mapper.EmployeeMapper;
import com.toanhv22.routeoptimization.repository.EmployeeRepository;
import com.toanhv22.routeoptimization.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeMapper employeeMapper;
    private final EmployeeRepository employeeRepository;

    @Override
    public List<EmployeeResponse> findAll() {
        return employeeRepository.findAll().stream().map(employeeMapper::entityToResponse).collect(Collectors.toList());
    }
}
