package com.toanhv22.routeoptimization.service.impl;

import com.toanhv22.routeoptimization.constant.ResponseStatusEnum;
import com.toanhv22.routeoptimization.dto.request.DeliveryInformationRequest;
import com.toanhv22.routeoptimization.dto.response.DeliveryInformationResponse;
import com.toanhv22.routeoptimization.entity.DeliveryInformation;
import com.toanhv22.routeoptimization.entity.Wards;
import com.toanhv22.routeoptimization.exception.BusinessException;
import com.toanhv22.routeoptimization.mapper.DeliveryInformationMapper;
import com.toanhv22.routeoptimization.mapper.WardsMapper;
import com.toanhv22.routeoptimization.repository.DeliveryInformationRepository;
import com.toanhv22.routeoptimization.repository.WardsRepository;
import com.toanhv22.routeoptimization.service.DeliveryInformationService;
import com.toanhv22.routeoptimization.utils.ULID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class DeliveryInformationServiceImpl implements DeliveryInformationService {

    private final DeliveryInformationMapper deliveryInformationMapper;
    private final DeliveryInformationRepository deliveryInformationRepository;
    private final WardsMapper wardsMapper;
    private final WardsRepository wardsRepository;

    @Override
    public List<DeliveryInformationResponse> findByCustomerId(String customerId) {
        List<DeliveryInformation> deliveryInformations = deliveryInformationRepository.findByCustomerId(customerId);
        if(deliveryInformations.isEmpty())
            throw new BusinessException(ResponseStatusEnum.LIST_EMPTY,"thông tin giao hàng","id khách hàng: "+customerId);

        List<DeliveryInformationResponse> deliveryInformationResponses = deliveryInformations.stream().map(deliveryInformationMapper::entityToResponse).collect(Collectors.toList());
        mapWardsToDeliveryInformation(deliveryInformationResponses);
        return deliveryInformationResponses;
    }

    @Override
    public DeliveryInformationResponse update(DeliveryInformationRequest deliveryInformationRequest) {
        DeliveryInformation deliveryInformation = deliveryInformationMapper.requestToEntity(deliveryInformationRequest);
        DeliveryInformationResponse deliveryInformationResponse = deliveryInformationMapper.entityToResponse(deliveryInformationRepository.save(deliveryInformation));

        Wards wards = wardsRepository.findByCode(deliveryInformationRequest.getWardsCode());
        deliveryInformationResponse.setWards(wardsMapper.entityToResponse(wards));

        return deliveryInformationResponse;
    }

    @Override
    public DeliveryInformationResponse create(DeliveryInformationRequest deliveryInformationRequest) {
        DeliveryInformation deliveryInformation = deliveryInformationMapper.requestToEntity(deliveryInformationRequest);
        deliveryInformation.setId(ULID.nextULID());
        DeliveryInformationResponse deliveryInformationResponse = deliveryInformationMapper.entityToResponse(deliveryInformationRepository.save(deliveryInformation));

        Wards wards = wardsRepository.findByCode(deliveryInformationRequest.getWardsCode());
        deliveryInformationResponse.setWards(wardsMapper.entityToResponse(wards));

        return deliveryInformationResponse;
    }

    public void mapWardsToDeliveryInformation(List<DeliveryInformationResponse> deliveryInformationResponses){
        List<Wards> wards = wardsRepository.findAll();
        for(DeliveryInformationResponse item:deliveryInformationResponses){
            for(int i=0;i<wards.size();i++){
                if(item.getWardsCode().equals(wards.get(i).getCode())){
                    item.setWards(wardsMapper.entityToResponse(wards.get(i)));
                    break;
                }
            }
        }
    }
}
