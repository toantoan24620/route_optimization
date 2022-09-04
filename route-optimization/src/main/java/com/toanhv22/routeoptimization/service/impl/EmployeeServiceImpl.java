package com.toanhv22.routeoptimization.service.impl;

import com.toanhv22.routeoptimization.constant.ResponseStatusEnum;
import com.toanhv22.routeoptimization.constant.VehicleTypeEnum;
import com.toanhv22.routeoptimization.dto.request.EmployeeRequest;
import com.toanhv22.routeoptimization.dto.response.EmployeeResponse;
import com.toanhv22.routeoptimization.entity.Employee;
import com.toanhv22.routeoptimization.exception.BusinessException;
import com.toanhv22.routeoptimization.mapper.EmployeeMapper;
import com.toanhv22.routeoptimization.repository.EmployeeRepository;
import com.toanhv22.routeoptimization.service.EmployeeService;
import com.toanhv22.routeoptimization.utils.ULID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeMapper employeeMapper;
    private final EmployeeRepository employeeRepository;

    @Override
    public List<EmployeeResponse> findAll() {
        return employeeRepository.findAll().stream()
                .filter(Employee::getStatus)
                .map(employeeMapper::entityToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String employeeId) {
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        if(!employee.isPresent()){
            throw new BusinessException(ResponseStatusEnum.ENTITY_NOT_FOUND, "nhân viên", "ID là " + employeeId);
        }
        employee.get().setStatus(false);
        employeeRepository.save(employee.get());
    }

    @Override
    public List<EmployeeResponse> findByNameContaining(String name) {
        List<Employee> employees = employeeRepository.findByNameContaining(name);
        if(employees.isEmpty()){
            throw new BusinessException(ResponseStatusEnum.LIST_EMPTY, "nhân viên", "tên là: " + name);
        }
        return employees.stream().map(employeeMapper::entityToResponse).collect(Collectors.toList());
    }

    @Override
    public EmployeeResponse findByStaffCode(String staffCode) {
        Optional<Employee> employee = employeeRepository.findByStaffCode(staffCode);
        if(!employee.isPresent()){
            throw new BusinessException(ResponseStatusEnum.LIST_EMPTY, "nhân viên", "mã nhân viên là: " + staffCode);
        }
        return employeeMapper.entityToResponse(employee.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public EmployeeResponse create(EmployeeRequest employeeRequest) {
        if(employeeRepository.findByStaffCode(employeeRequest.getStaffCode()).isPresent()){
            throw new BusinessException(ResponseStatusEnum.ENTITY_DUPLICATED,"nhân viên","mã nhân viên: " + employeeRequest.getStaffCode());
        }
        if(employeeRepository.findByIdentifyNumber(employeeRequest.getIdentifyNumber()).isPresent()){
            throw new BusinessException(ResponseStatusEnum.DUPLICATED_IDENTIFY_NUMBER,employeeRequest.getIdentifyNumber());
        }
        Employee employee = employeeMapper.requestToEntity(employeeRequest);
        employee.setId(ULID.nextULID());
        employee.setStatus(true);
        return employeeMapper.entityToResponse(employeeRepository.save(employee));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public EmployeeResponse update(EmployeeRequest employeeRequest) {
        Optional<Employee> tempEmployee = employeeRepository.findByIdentifyNumber(employeeRequest.getIdentifyNumber());
        if(tempEmployee.isPresent() && !tempEmployee.get().getId().equals(employeeRequest.getId())){
            throw new BusinessException(ResponseStatusEnum.DUPLICATED_IDENTIFY_NUMBER,employeeRequest.getIdentifyNumber());
        }
        Employee employee = employeeMapper.requestToEntity(employeeRequest);
        return employeeMapper.entityToResponse(employeeRepository.save(employee));
    }
}
