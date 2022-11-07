package com.toanhv22.routeoptimization.controller;

import com.toanhv22.routeoptimization.dto.request.CustomerRequest;
import com.toanhv22.routeoptimization.dto.response.CustomerResponse;
import com.toanhv22.routeoptimization.dto.response.EmployeeResponse;
import com.toanhv22.routeoptimization.factory.response.GeneralResponse;
import com.toanhv22.routeoptimization.factory.response.ResponseFactory;
import com.toanhv22.routeoptimization.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("route-optimization/private/v1/customers")
@Validated
public class CustomerController {
    private final CustomerService customerService;
    private final ResponseFactory responseFactory;

    @GetMapping
    public ResponseEntity<GeneralResponse<List<CustomerResponse>>> findAll(){
        return responseFactory.success(customerService.findAll());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<GeneralResponse<CustomerResponse>> findById(@PathVariable String id){
        return responseFactory.success(customerService.findById(id));
    }

    @GetMapping("/phone-number/{phoneNumber}")
    public ResponseEntity<GeneralResponse<CustomerResponse>> findByPhoneNumber(@PathVariable String phoneNumber){
        return responseFactory.success(customerService.findByPhoneNumber(phoneNumber));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<GeneralResponse<CustomerResponse>> findByUsername(@PathVariable String username){
        return responseFactory.success(customerService.findByUsername(username));
    }

    @GetMapping("/full-name/{fullName}")
    public ResponseEntity<GeneralResponse<List<CustomerResponse>>> findByFullName(@PathVariable String fullName){
        return responseFactory.success(customerService.findByFullName(fullName));
    }

    @PostMapping
    public ResponseEntity<GeneralResponse<CustomerResponse>> create(@RequestBody @Valid CustomerRequest customerRequest){
        return responseFactory.success(customerService.create(customerRequest));
    }

    @PutMapping
    public ResponseEntity<GeneralResponse<CustomerResponse>> update(@RequestBody @Valid CustomerRequest customerRequest){
        return responseFactory.success(customerService.update(customerRequest));
    }
}
