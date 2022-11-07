package com.toanhv22.routeoptimization.service.impl;

import com.toanhv22.routeoptimization.constant.ConfigParamEnum;
import com.toanhv22.routeoptimization.constant.OrdersStatusEnum;
import com.toanhv22.routeoptimization.constant.ResponseStatusEnum;
import com.toanhv22.routeoptimization.constant.VehicleTypeEnum;
import com.toanhv22.routeoptimization.dto.request.OrdersRequest;
import com.toanhv22.routeoptimization.dto.response.DeliveryInformationResponse;
import com.toanhv22.routeoptimization.dto.response.OrdersResponse;
import com.toanhv22.routeoptimization.entity.*;
import com.toanhv22.routeoptimization.exception.BusinessException;
import com.toanhv22.routeoptimization.mapper.*;
import com.toanhv22.routeoptimization.repository.*;
import com.toanhv22.routeoptimization.service.OrdersService;
import com.toanhv22.routeoptimization.utils.Common;
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
public class OrdersServiceImpl implements OrdersService {

    private final CustomerRepository customerRepository;
    private final WarehouseRepository warehouseRepository;
    private final WardsRepository wardsRepository;
    private final OrdersRepository ordersRepository;
    private final OrdersMapper ordersMapper;
    private final WarehouseMapper warehouseMapper;
    private final WardsMapper wardsMapper;
    private final CustomerMapper customerMapper;
    private final ConfigParamRepository configParamRepository;

    @Override
    public List<OrdersResponse> findByWarehouseCode(String warehouseCode, String vehicleType, String status) {
        Optional<Warehouse> warehouse = warehouseRepository.findByCode(warehouseCode);
        if(!warehouse.isPresent()){
            throw new BusinessException(ResponseStatusEnum.LIST_EMPTY,"nhà kho","mã nhà kho: " + warehouseCode);
        }

        List<Orders> orders;
        if(vehicleType.equals("ALL") && status.equals("ALL")){
            orders = ordersRepository.findByWarehouseId(warehouse.get().getId());
        }else if(status.equals("ALL")){
            orders = ordersRepository.findByWarehouseIdAndVehicleType(warehouse.get().getId(), vehicleType);
        }else if(vehicleType.equals("ALL")){
            orders = ordersRepository.findByWarehouseIdAndStatus(warehouse.get().getId(), status);
        }else{
            orders = ordersRepository.findByWarehouseIdAndVehicleTypeAndStatus(warehouse.get().getId(), vehicleType, status);
        }

        if(orders.isEmpty()){
            throw new BusinessException(ResponseStatusEnum.LIST_EMPTY,"đơn hàng","tiêu chí tìm kiếm");
        }
        List<OrdersResponse> ordersResponses = orders.stream().map(ordersMapper::entityToResponse).collect(Collectors.toList());
        mapWarehouseAndCustomerInformationToOrders(ordersResponses);
        return ordersResponses;
    }

    @Override
    public List<OrdersResponse> findByUsername(String username, String vehicleType, String status) {
        Optional<Customer> customer = customerRepository.findByUsername(username);
        if(!customer.isPresent()){
            throw new BusinessException(ResponseStatusEnum.ENTITY_NOT_FOUND,"khách hàng","username: "+username);
        }

        List<Orders> orders;
        if(vehicleType.equals("ALL") && status.equals("ALL")){
            orders = ordersRepository.findByCustomerId(customer.get().getId());
        }else if(status.equals("ALL")){
            orders = ordersRepository.findByCustomerIdAndVehicleType(customer.get().getId(), vehicleType);
        }else if(vehicleType.equals("ALL")){
            orders = ordersRepository.findByCustomerIdAndStatus(customer.get().getId(), status);
        }else{
            orders = ordersRepository.findByCustomerIdAndVehicleTypeAndStatus(customer.get().getId(), vehicleType, status);
        }
        if(orders.isEmpty()){
            throw new BusinessException(ResponseStatusEnum.LIST_EMPTY,"đơn hàng","tiêu chí tìm kiếm");
        }
        List<OrdersResponse> ordersResponses = orders.stream().map(ordersMapper::entityToResponse).collect(Collectors.toList());
        mapWarehouseAndCustomerInformationToOrders(ordersResponses);
        return ordersResponses;
    }

    @Override
    public List<OrdersResponse> findByVehicleTypeAndStatus(String vehicleType, String status) {
        List<Orders> orders;
        if(vehicleType.equals("ALL") && status.equals("ALL")){
            orders = ordersRepository.findAll();
        }else if(status.equals("ALL")){
            orders = ordersRepository.findByVehicleType(vehicleType);
        }else if(vehicleType.equals("ALL")){
            orders = ordersRepository.findByStatus(status);
        }else{
            orders = ordersRepository.findByVehicleTypeAndStatus(vehicleType, status);
        }
        if(orders.isEmpty()){
            throw new BusinessException(ResponseStatusEnum.LIST_EMPTY,"đơn hàng","tiêu chí tìm kiếm");
        }
        List<OrdersResponse> ordersResponses = orders.stream().map(ordersMapper::entityToResponse).collect(Collectors.toList());
        mapWarehouseAndCustomerInformationToOrders(ordersResponses);
        return ordersResponses;
    }

    @Override
    public OrdersResponse findById(String id) {
        Optional<Orders> orders = ordersRepository.findById(id);
        if(!orders.isPresent()){
            throw new BusinessException(ResponseStatusEnum.ENTITY_NOT_FOUND,"đơn hàng","id: " + id);
        }
        OrdersResponse ordersResponse = ordersMapper.entityToResponse(orders.get());
        List<OrdersResponse> ordersResponses = new ArrayList<>();
        ordersResponses.add(ordersResponse);
        mapWarehouseAndCustomerInformationToOrders(ordersResponses);
        return ordersResponses.get(0);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrdersResponse create(OrdersRequest ordersRequest) {
        // set vehicle type for create
        Double maxWeight = Double.valueOf(configParamRepository.findById(ConfigParamEnum.MAX_WEIGHT_PER_PACKAGE.toString()).get().getValue());
        Double maxSize = Double.valueOf(configParamRepository.findById(ConfigParamEnum.MAX_SIZE_PER_PACKAGE.toString()).get().getValue());
        if(ordersRequest.getWeight() > maxWeight || ordersRequest.getSize() > maxSize)
            ordersRequest.setVehicleType(VehicleTypeEnum.CAR.toString());
        else ordersRequest.setVehicleType(VehicleTypeEnum.MOTORBIKE.toString());
        ordersRequest.setStatus(OrdersStatusEnum.PENDING.toString());

        Orders orders = ordersMapper.requestToEntity(ordersRequest);
        orders.setId(ULID.nextULID());

        OrdersResponse ordersResponse = ordersMapper.entityToResponse(ordersRepository.save(orders));
        List<OrdersResponse> ordersResponses = new ArrayList<>();
        ordersResponses.add(ordersResponse);

        mapWarehouseAndCustomerInformationToOrders(ordersResponses);
        return ordersResponses.get(0);
    }

    @Override
    public OrdersResponse update(OrdersRequest ordersRequest) {
        //update size, weight -> update vehicle type
        Double maxWeight = Double.valueOf(configParamRepository.findById(ConfigParamEnum.MAX_WEIGHT_PER_PACKAGE.toString()).get().getValue());
        Double maxSize = Double.valueOf(configParamRepository.findById(ConfigParamEnum.MAX_SIZE_PER_PACKAGE.toString()).get().getValue());
        Orders order = ordersMapper.requestToEntity(ordersRequest);
        if(order.getWeight() <= maxWeight || order.getSize() <= maxSize)
            order.setVehicleType(VehicleTypeEnum.MOTORBIKE.toString());

        OrdersResponse ordersResponse = ordersMapper.entityToResponse(ordersRepository.save(order));
        List<OrdersResponse> ordersResponses = new ArrayList<>();
        ordersResponses.add(ordersResponse);

        mapWarehouseAndCustomerInformationToOrders(ordersResponses);
        return ordersResponses.get(0);
    }

    public void mapWarehouseAndCustomerInformationToOrders(List<OrdersResponse> ordersResponses){
        List<Warehouse> warehouses = warehouseRepository.findAll();
        List<Wards> wards = wardsRepository.findAll();
        List<Customer> customers = customerRepository.findAll();

        for(OrdersResponse item:ordersResponses){
            for(int i=0;i<warehouses.size();i++){
                if(item.getWarehouseId().equals(warehouses.get(i).getId())){
                    item.setWarehouse(warehouseMapper.entityToResponse(warehouses.get(i)));
                    break;
                }
            }

            for(int i=0;i<wards.size();i++){
                if(item.getWarehouse().getWardsCode().equals(wards.get(i).getCode())){
                    item.getWarehouse().setPathWithType(wards.get(i).getPathWithType());
                    break;
                }
            }

            for(int i=0;i<customers.size();i++){
                if(item.getCustomerId().equals(customers.get(i).getId())){
                    item.setCustomer(customerMapper.entityToResponse(customers.get(i)));
                    break;
                }
            }
        }
    }
}
