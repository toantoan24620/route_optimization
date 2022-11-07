package com.toanhv22.routeoptimization.service.impl;

import com.toanhv22.routeoptimization.constant.ResponseStatusEnum;
import com.toanhv22.routeoptimization.dto.request.WarehouseRequest;
import com.toanhv22.routeoptimization.dto.response.WarehouseResponse;
import com.toanhv22.routeoptimization.entity.Wards;
import com.toanhv22.routeoptimization.entity.Warehouse;
import com.toanhv22.routeoptimization.exception.BusinessException;
import com.toanhv22.routeoptimization.mapper.WarehouseMapper;
import com.toanhv22.routeoptimization.repository.WardsRepository;
import com.toanhv22.routeoptimization.repository.WarehouseRepository;
import com.toanhv22.routeoptimization.service.WarehouseService;
import com.toanhv22.routeoptimization.utils.ULID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class WarehouseServiceImpl implements WarehouseService {

    private final WarehouseMapper warehouseMapper;
    private final WarehouseRepository warehouseRepository;
    private final WardsRepository wardsRepository;

    @Override
    public List<WarehouseResponse> findAll(Boolean active) {
        List<WarehouseResponse> warehouseResponses =  warehouseRepository.findAll().stream().filter(item -> item.getStatus() == active).map(warehouseMapper::entityToResponse).collect(Collectors.toList());
        List<Wards> wards = wardsRepository.findAll();
        mapPathWithTypeToWarehouses(warehouseResponses,wards);
        return warehouseResponses;
    }

    @Override
    public WarehouseResponse findById(String id,Boolean active) {
        Optional<Warehouse> warehouse = warehouseRepository.findByIdAndStatus(id,active);
        if(!warehouse.isPresent()){
            throw new BusinessException(ResponseStatusEnum.LIST_EMPTY, "nhà kho", "mã nhà kho là: " + id);
        }
        Wards wards = wardsRepository.findByCode(warehouse.get().getWardsCode());
        WarehouseResponse warehouseResponse =  warehouseMapper.entityToResponse(warehouse.get());
        warehouseResponse.setPathWithType(wards.getPathWithType());
        return warehouseResponse;
    }

    @Override
    public List<WarehouseResponse> findByName(String name,Boolean active) {
        List<Warehouse> warehouses = warehouseRepository.findByNameContainingAndStatus(name, active);
        if(warehouses.isEmpty()){
            throw new BusinessException(ResponseStatusEnum.LIST_EMPTY, "nhà kho", "tên nhà kho là: " + name);
        }
        List<WarehouseResponse> warehouseResponses = warehouses.stream().map(warehouseMapper::entityToResponse).collect(Collectors.toList());
        List<Wards> wards = wardsRepository.findAll();
        mapPathWithTypeToWarehouses(warehouseResponses,wards);
        return warehouseResponses;
    }

    @Override
    public List<WarehouseResponse> findByArea(String area, Boolean active) {
        List<Wards> wards = wardsRepository.findByPathWithTypeContaining(area);
        if(wards.isEmpty()){
            throw new BusinessException(ResponseStatusEnum.LIST_EMPTY, "nhà kho", "khu vực là: " + area);
        }
        List<Warehouse> warehouse = warehouseRepository.findByStatus(active);
        List<WarehouseResponse> result = new ArrayList<>();
        for(int i=0;i<warehouse.size();i++){
            String code = warehouse.get(i).getWardsCode();
            Optional<Wards> wardsTemp = wards.stream().filter(item -> item.getCode().equals(code)).findFirst();
            if(wardsTemp.isPresent()){
                WarehouseResponse warehouseResponse = warehouseMapper.entityToResponse(warehouse.get(i));
                warehouseResponse.setPathWithType(wardsTemp.get().getPathWithType());
                result.add(warehouseResponse);
            }
        }

        if(result.isEmpty()){
            throw new BusinessException(ResponseStatusEnum.LIST_EMPTY, "nhà kho", "khu vực là: " + area);
        }

        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String warehouseID) {
        Optional<Warehouse> warehouse = warehouseRepository.findById(warehouseID);
        if(!warehouse.isPresent()){
            throw new BusinessException(ResponseStatusEnum.LIST_EMPTY, "nhà kho", "ID là: " + warehouseID);
        }
        warehouse.get().setStatus(false);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public WarehouseResponse create(WarehouseRequest warehouseRequest) {
        // check duplicate code
        if(warehouseRepository.findByCode(warehouseRequest.getCode()).isPresent()){
            throw new BusinessException(ResponseStatusEnum.ENTITY_DUPLICATED,"Mã nhà kho",warehouseRequest.getCode());
        }
        warehouseRequest.setId(ULID.nextULID());
        warehouseRequest.setStatus(true);
        Warehouse warehouse = warehouseMapper.requestToEntity(warehouseRequest);

        Wards wards = wardsRepository.findByCode(warehouseRequest.getWardsCode());
        WarehouseResponse warehouseResponse = warehouseMapper.entityToResponse(warehouseRepository.save(warehouse));
        warehouseResponse.setPathWithType(wards.getPathWithType());
        return warehouseResponse;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public WarehouseResponse update(WarehouseRequest warehouseRequest) {
        Warehouse warehouse = warehouseMapper.requestToEntity(warehouseRequest);
        Wards wards = wardsRepository.findByCode(warehouseRequest.getWardsCode());

        WarehouseResponse warehouseResponse = warehouseMapper.entityToResponse(warehouseRepository.save(warehouse));
        warehouseResponse.setPathWithType(wards.getPathWithType());
        return warehouseResponse;
    }

    public void mapPathWithTypeToWarehouses(List<WarehouseResponse> warehouseResponses, List<Wards> wards){
        for(int i=0;i<warehouseResponses.size();i++){
            String code = warehouseResponses.get(i).getWardsCode();
            warehouseResponses.get(i).setPathWithType(wards.stream().filter(item -> item.getCode().equals(code)).collect(Collectors.toList()).get(0).getPathWithType());
        }
    }
}
