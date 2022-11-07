package com.toanhv22.routeoptimization.service;

import com.toanhv22.routeoptimization.dto.request.CustomerRequest;
import com.toanhv22.routeoptimization.dto.response.CustomerResponse;

import java.util.List;

public interface CustomerService {
    List<CustomerResponse> findAll();

    CustomerResponse findById(String id);

    CustomerResponse findByPhoneNumber(String phoneNumber);

    List<CustomerResponse> findByFullName(String fullName);

    CustomerResponse create(CustomerRequest customerRequest);

    CustomerResponse update(CustomerRequest customerRequest);

    CustomerResponse findByUsername(String username);
}
