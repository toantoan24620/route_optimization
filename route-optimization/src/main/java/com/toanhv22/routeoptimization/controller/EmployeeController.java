package com.toanhv22.routeoptimization.controller;

import com.toanhv22.routeoptimization.dto.request.EmployeeRequest;
import com.toanhv22.routeoptimization.dto.response.EmployeeResponse;
import com.toanhv22.routeoptimization.factory.response.GeneralResponse;
import com.toanhv22.routeoptimization.factory.response.ResponseFactory;
import com.toanhv22.routeoptimization.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("route-optimization/private/v1/employees")
@Validated
public class EmployeeController {
    private final EmployeeService employeeService;
    private final ResponseFactory responseFactory;

    @GetMapping
    public ResponseEntity<GeneralResponse<List<EmployeeResponse>>> findAll(@RequestParam Boolean active){
        return responseFactory.success(employeeService.findAll(active));
    }

    @PostMapping
    public ResponseEntity<GeneralResponse<EmployeeResponse>> createEmployee(@RequestBody @Valid EmployeeRequest employeeRequest){
        return responseFactory.success(employeeService.create(employeeRequest));
    }

    @PutMapping
    public ResponseEntity<GeneralResponse<EmployeeResponse>> updateEmployee(@RequestBody @Valid EmployeeRequest employeeRequest){
        return responseFactory.success(employeeService.update(employeeRequest));
    }

    @DeleteMapping
    public ResponseEntity<GeneralResponse<Void>> deleteEmployee(@RequestParam String employeeId){
        employeeService.delete(employeeId);
        return responseFactory.success(null);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<GeneralResponse<List<EmployeeResponse>>> findByName(@PathVariable String name, @RequestParam Boolean active){
        return responseFactory.success(employeeService.findByNameContaining(name, active));
    }

    @GetMapping("/staffCode/{staffCode}")
    public ResponseEntity<GeneralResponse<EmployeeResponse>> findByStaffCode(@PathVariable String staffCode, @RequestParam Boolean active){
        return responseFactory.success(employeeService.findByStaffCode(staffCode, active));
    }
}
