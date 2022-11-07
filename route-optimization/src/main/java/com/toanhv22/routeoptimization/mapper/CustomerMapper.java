package com.toanhv22.routeoptimization.mapper;

import com.toanhv22.routeoptimization.dto.request.CustomerRequest;
import com.toanhv22.routeoptimization.dto.response.CustomerResponse;
import com.toanhv22.routeoptimization.entity.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerResponse entityToResponse(Customer customer);
    Customer requestToEntity(CustomerRequest customerRequest);
}
