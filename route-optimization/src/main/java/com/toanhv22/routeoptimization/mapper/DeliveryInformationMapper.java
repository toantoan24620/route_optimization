package com.toanhv22.routeoptimization.mapper;

import com.toanhv22.routeoptimization.dto.request.DeliveryInformationRequest;
import com.toanhv22.routeoptimization.dto.response.DeliveryInformationResponse;
import com.toanhv22.routeoptimization.entity.DeliveryInformation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DeliveryInformationMapper {
    DeliveryInformation requestToEntity(DeliveryInformationRequest deliveryInformationRequest);

    DeliveryInformationResponse entityToResponse(DeliveryInformation deliveryInformation);
}
