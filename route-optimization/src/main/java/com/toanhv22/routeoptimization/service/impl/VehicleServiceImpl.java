package com.toanhv22.routeoptimization.service.impl;

import com.toanhv22.routeoptimization.constant.ResponseStatusEnum;
import com.toanhv22.routeoptimization.dto.request.VehicleRequest;
import com.toanhv22.routeoptimization.dto.response.VehicleResponse;
import com.toanhv22.routeoptimization.dto.response.WarehouseResponse;
import com.toanhv22.routeoptimization.entity.Vehicle;
import com.toanhv22.routeoptimization.exception.BusinessException;
import com.toanhv22.routeoptimization.mapper.VehicleMapper;
import com.toanhv22.routeoptimization.mapper.WarehouseMapper;
import com.toanhv22.routeoptimization.repository.VehicleRepository;
import com.toanhv22.routeoptimization.repository.WarehouseRepository;
import com.toanhv22.routeoptimization.service.VehicleService;
import com.toanhv22.routeoptimization.utils.ULID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;
    private final VehicleMapper vehicleMapper;
    private final WarehouseRepository warehouseRepository;
    private final WarehouseMapper warehouseMapper;

    @Override
    public List<VehicleResponse> getAll(Boolean active) {
        List<VehicleResponse> vehicleResponses = vehicleRepository.findByStatus(active).stream().map(vehicleMapper::entityToResponse).collect(Collectors.toList());
        mapWarehouseToVehicle(vehicleResponses);

        return vehicleResponses;
    }

    @Override
    public VehicleResponse getByLicensePlate(String licensePlate, Boolean active) {
        Optional<Vehicle> vehicle = vehicleRepository.findByLicensePlateAndStatus(licensePlate, active);

        if(!vehicle.isPresent()){
            throw new BusinessException(ResponseStatusEnum.LIST_EMPTY,"phương tiện","biển số xe là: " + licensePlate);
        }
        WarehouseResponse warehouse = warehouseMapper.entityToResponse(warehouseRepository.findById(vehicle.get().getWarehouseId()).get());
        VehicleResponse vehicleResponse = vehicleMapper.entityToResponse(vehicle.get());
        vehicleResponse.setWarehouse(warehouse);

        return vehicleResponse;
    }

    @Override
    public List<VehicleResponse> getByName(String name) {
        List<VehicleResponse> vehicleResponses = vehicleRepository.findByNameContaining(name).stream().map(vehicleMapper::entityToResponse).collect(Collectors.toList());

        if(vehicleResponses.isEmpty()){
            throw new BusinessException(ResponseStatusEnum.LIST_EMPTY,"phương tiện","tên xe là: "+name);
        }
        mapWarehouseToVehicle(vehicleResponses);

        return vehicleResponses;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public VehicleResponse create(VehicleRequest vehicleRequest) {
        Optional<Vehicle> vehicle = vehicleRepository.findByLicensePlate(vehicleRequest.getLicensePlate());
        if(vehicle.isPresent()){
            throw new BusinessException(ResponseStatusEnum.ENTITY_DUPLICATED,"Biển số xe", vehicleRequest.getLicensePlate());
        }
        Vehicle temp = vehicleMapper.requestToEntity(vehicleRequest);
        temp.setId(ULID.nextULID());
        temp.setStatus(true);
        VehicleResponse vehicleResponse = vehicleMapper.entityToResponse(vehicleRepository.save(temp));

        vehicleResponse.setWarehouse(warehouseMapper.entityToResponse(warehouseRepository.findById(temp.getWarehouseId()).get()));
        return vehicleResponse;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public VehicleResponse update(VehicleRequest vehicleRequest) {
        Optional<Vehicle> vehicle = vehicleRepository.findByLicensePlate(vehicleRequest.getLicensePlate());
        if(vehicle.isPresent() && !vehicle.get().getId().equals(vehicleRequest.getId())){
            throw new BusinessException(ResponseStatusEnum.ENTITY_DUPLICATED,"Biển số xe",vehicleRequest.getLicensePlate());
        }
        Vehicle temp = vehicleMapper.requestToEntity(vehicleRequest);
        VehicleResponse vehicleResponse = vehicleMapper.entityToResponse(vehicleRepository.save(temp));
        vehicleResponse.setWarehouse(warehouseMapper.entityToResponse(warehouseRepository.findById(temp.getWarehouseId()).get()));

        return vehicleResponse;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String vehicleId) {
        Optional<Vehicle> vehicle = vehicleRepository.findById(vehicleId);
        if(!vehicle.isPresent()){
            throw new BusinessException(ResponseStatusEnum.LIST_EMPTY,"phương tiện","ID là: "+vehicleId);
        }
        vehicle.get().setStatus(false);
    }

    @Override
    public VehicleResponse getById(String id) {
        return vehicleMapper.entityToResponse(vehicleRepository.findById(id).get());
    }

    @Override
    public List<VehicleResponse> getByWeightGreater(Integer weight) {
        List<Vehicle> vehicles =  vehicleRepository.findByCapacityWeightGreaterThanEqualAndStatusIsTrue(weight);
        if(vehicles.isEmpty())
            throw new BusinessException(ResponseStatusEnum.LIST_EMPTY,"phương tiện","tiêu chí tìm kiếm");
        List<VehicleResponse> vehicleResponses = vehicles.stream().map(vehicleMapper::entityToResponse).collect(Collectors.toList());
        mapWarehouseToVehicle(vehicleResponses);
        return vehicleResponses;
    }

    public void mapWarehouseToVehicle(List<VehicleResponse> vehicleResponses){
        List<WarehouseResponse> warehouses = warehouseRepository.findAll().stream().map(warehouseMapper::entityToResponse).collect(Collectors.toList());

        for(int i = 0;i < vehicleResponses.size(); i++){
            for(int j = 0; j < warehouses.size(); j++){
                if(vehicleResponses.get(i).getWarehouseId().equals(warehouses.get(j).getId())){
                    vehicleResponses.get(i).setWarehouse(warehouses.get(j));
                }
            }
        }
    }
}
