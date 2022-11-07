package com.toanhv22.routeoptimization.service;


import com.toanhv22.routeoptimization.dto.request.DeliveryInformationRequest;
import com.toanhv22.routeoptimization.dto.response.DeliveryInformationResponse;

import java.util.List;

public interface DeliveryInformationService {
    List<DeliveryInformationResponse> findByCustomerId(String customerId);
    DeliveryInformationResponse update(DeliveryInformationRequest deliveryInformationRequest);
    DeliveryInformationResponse create(DeliveryInformationRequest deliveryInformationRequest);
}
