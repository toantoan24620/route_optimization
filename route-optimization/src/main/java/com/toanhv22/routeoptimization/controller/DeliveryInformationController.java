package com.toanhv22.routeoptimization.controller;

import com.toanhv22.routeoptimization.dto.request.CustomerRequest;
import com.toanhv22.routeoptimization.dto.request.DeliveryInformationRequest;
import com.toanhv22.routeoptimization.dto.response.CustomerResponse;
import com.toanhv22.routeoptimization.dto.response.DeliveryInformationResponse;
import com.toanhv22.routeoptimization.factory.response.GeneralResponse;
import com.toanhv22.routeoptimization.factory.response.ResponseFactory;
import com.toanhv22.routeoptimization.service.DeliveryInformationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("route-optimization/private/v1/delivery-informations")
@Validated
public class DeliveryInformationController {
    private final DeliveryInformationService deliveryInformationService;
    private final ResponseFactory responseFactory;

    @GetMapping("/customerId/{customerId}")
    public ResponseEntity<GeneralResponse<List<DeliveryInformationResponse>>> findByCustomerId(@PathVariable String customerId){
        return responseFactory.success(deliveryInformationService.findByCustomerId(customerId));
    }

    @PostMapping
    public ResponseEntity<GeneralResponse<DeliveryInformationResponse>> create(@RequestBody @Valid DeliveryInformationRequest deliveryInformationRequest){
        return responseFactory.success(deliveryInformationService.create(deliveryInformationRequest));
    }

    @PutMapping
    public ResponseEntity<GeneralResponse<DeliveryInformationResponse>> update(@RequestBody @Valid DeliveryInformationRequest deliveryInformationRequest){
        return responseFactory.success(deliveryInformationService.update(deliveryInformationRequest));
    }
}
