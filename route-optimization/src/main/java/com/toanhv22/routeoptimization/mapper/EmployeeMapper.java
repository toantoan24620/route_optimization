package com.toanhv22.routeoptimization.mapper;

import com.toanhv22.routeoptimization.dto.request.EmployeeRequest;
import com.toanhv22.routeoptimization.dto.response.EmployeeResponse;
import com.toanhv22.routeoptimization.entity.Employee;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    EmployeeResponse entityToResponse(Employee employee);
    Employee requestToEntity(EmployeeRequest employeeRequest);
}
