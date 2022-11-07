package com.toanhv22.routeoptimization.service.impl;

import com.toanhv22.routeoptimization.constant.ResponseStatusEnum;
import com.toanhv22.routeoptimization.dto.request.CustomerRequest;
import com.toanhv22.routeoptimization.dto.response.CustomerResponse;
import com.toanhv22.routeoptimization.entity.Customer;
import com.toanhv22.routeoptimization.exception.BusinessException;
import com.toanhv22.routeoptimization.mapper.CustomerMapper;
import com.toanhv22.routeoptimization.repository.CustomerRepository;
import com.toanhv22.routeoptimization.service.CustomerService;
import com.toanhv22.routeoptimization.utils.ULID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerMapper customerMapper;
    private final CustomerRepository customerRepository;

    @Override
    public List<CustomerResponse> findAll() {
        return customerRepository.findAll().stream().map(customerMapper::entityToResponse).collect(Collectors.toList());
    }

    @Override
    public CustomerResponse findById(String id) {
        return customerMapper.entityToResponse(customerRepository.findById(id).get());
    }

    @Override
    public CustomerResponse findByPhoneNumber(String phoneNumber) {
        Optional<Customer> customer = customerRepository.findByPhoneNumber(phoneNumber);
        if(!customer.isPresent()){
            throw new BusinessException(ResponseStatusEnum.ENTITY_NOT_FOUND,"khách hàng","số điện thoại: " + phoneNumber);
        }
        return customerMapper.entityToResponse(customer.get());
    }

    @Override
    public List<CustomerResponse> findByFullName(String fullName) {
        List<Customer> customers = customerRepository.findByFullNameContaining(fullName);
        if(customers.isEmpty()) throw new BusinessException(ResponseStatusEnum.ENTITY_NOT_FOUND,"khách hàng","tên: " + fullName);
        return customers.stream().map(customerMapper::entityToResponse).collect(Collectors.toList());
    }

    @Override
    public CustomerResponse create(CustomerRequest customerRequest) {
        // check trùng sđt
        if(customerRepository.findByPhoneNumber(customerRequest.getPhoneNumber()).isPresent()){
            throw new BusinessException(ResponseStatusEnum.ENTITY_DUPLICATED,"Số điện thoại",customerRequest.getPhoneNumber());
        }
        if(customerRepository.findByUsername(customerRequest.getUsername()).isPresent()){
            throw new BusinessException(ResponseStatusEnum.ENTITY_DUPLICATED,"Username",customerRequest.getUsername());
        }
        Customer customer = customerMapper.requestToEntity(customerRequest);
        customer.setId(ULID.nextULID());
        return customerMapper.entityToResponse(customerRepository.save(customer));
    }

    @Override
    public CustomerResponse update(CustomerRequest customerRequest) {
        // check trùng sđt
        Optional<Customer> customerWithPhoneNumber = customerRepository.findByPhoneNumber(customerRequest.getPhoneNumber());
        if(customerWithPhoneNumber.isPresent() && !customerWithPhoneNumber.get().getId().equals(customerRequest.getId())){
            throw new BusinessException(ResponseStatusEnum.ENTITY_DUPLICATED,"Số điện thoại",customerRequest.getPhoneNumber());
        }

        Optional<Customer> customerWithUsername = customerRepository.findByUsername(customerRequest.getUsername());
        if(customerWithUsername.isPresent() && !customerWithUsername.get().getId().equals(customerRequest.getId())){
            throw new BusinessException(ResponseStatusEnum.ENTITY_DUPLICATED,"Username",customerRequest.getUsername());
        }

        Customer temp = customerMapper.requestToEntity(customerRequest);
        return customerMapper.entityToResponse(customerRepository.save(temp));
    }

    @Override
    public CustomerResponse findByUsername(String username) {
        Optional<Customer> customer = customerRepository.findByUsername(username);

        if(!customer.isPresent()){
            throw new BusinessException(ResponseStatusEnum.ENTITY_NOT_FOUND,"khách hàng", "username: "+username);
        }
        return customerMapper.entityToResponse(customer.get());
    }
}
