package com.toanhv22.routeoptimization.service.impl;

import com.toanhv22.routeoptimization.constant.ResponseStatusEnum;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeMapper employeeMapper;
    private final EmployeeRepository employeeRepository;

    @Override
    public List<EmployeeResponse> findAll(Boolean active) {
        return employeeRepository.findAll().stream()
                .filter(item -> Objects.equals(item.getStatus(), active))
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
    public List<EmployeeResponse> findByNameContaining(String name, Boolean active) {
        List<Employee> employees = employeeRepository.findByNameContainingAndStatus(name, active);
        if(employees.isEmpty()){
            throw new BusinessException(ResponseStatusEnum.LIST_EMPTY, "nhân viên", "tên là: " + name);
        }
        return employees.stream().map(employeeMapper::entityToResponse).collect(Collectors.toList());
    }

    @Override
    public EmployeeResponse findByStaffCode(String staffCode, Boolean active) {
        Optional<Employee> employee = employeeRepository.findByStaffCodeAndStatus(staffCode,active);
        if(!employee.isPresent()){
            throw new BusinessException(ResponseStatusEnum.LIST_EMPTY, "nhân viên", "mã nhân viên là: " + staffCode);
        }
        return employeeMapper.entityToResponse(employee.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public EmployeeResponse create(EmployeeRequest employeeRequest) {
        if(employeeRepository.findByStaffCode(employeeRequest.getStaffCode()).isPresent()){
            throw new BusinessException(ResponseStatusEnum.ENTITY_DUPLICATED,"Mã nhân viên",employeeRequest.getStaffCode());
        }
        if(employeeRepository.findByIdentifyNumber(employeeRequest.getIdentifyNumber()).isPresent()){
            throw new BusinessException(ResponseStatusEnum.ENTITY_DUPLICATED,"Số CMT/CCCD",employeeRequest.getIdentifyNumber());
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

        // là 1 nhân viên khác đã có số CMT giống với số muốn sửa
        // không cho sửa mã nhân viên nên chỉ check số CMT/CCCD
        if(tempEmployee.isPresent() && !tempEmployee.get().getId().equals(employeeRequest.getId())){
            throw new BusinessException(ResponseStatusEnum.ENTITY_DUPLICATED,"Số CMT/CCCD",employeeRequest.getIdentifyNumber());
        }
        Employee employee = employeeMapper.requestToEntity(employeeRequest);
        return employeeMapper.entityToResponse(employeeRepository.save(employee));
    }
}
