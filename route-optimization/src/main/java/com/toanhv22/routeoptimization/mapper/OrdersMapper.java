package com.toanhv22.routeoptimization.mapper;

import com.toanhv22.routeoptimization.dto.request.OrdersRequest;
import com.toanhv22.routeoptimization.dto.response.OrdersResponse;
import com.toanhv22.routeoptimization.entity.Orders;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrdersMapper {
    Orders requestToEntity(OrdersRequest ordersRequest);

    OrdersResponse entityToResponse(Orders orders);
}
