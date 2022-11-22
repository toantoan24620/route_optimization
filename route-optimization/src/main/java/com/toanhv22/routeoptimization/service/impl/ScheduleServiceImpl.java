package com.toanhv22.routeoptimization.service.impl;

import com.toanhv22.routeoptimization.constant.OrdersStatusEnum;
import com.toanhv22.routeoptimization.constant.ResponseStatusEnum;
import com.toanhv22.routeoptimization.dto.request.ScheduleUpdateRequest;
import com.toanhv22.routeoptimization.dto.response.ScheduleResponse;
import com.toanhv22.routeoptimization.entity.*;
import com.toanhv22.routeoptimization.exception.BusinessException;
import com.toanhv22.routeoptimization.mapper.ScheduleMapper;
import com.toanhv22.routeoptimization.repository.*;
import com.toanhv22.routeoptimization.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final VehicleRepository vehicleRepository;
    private final EmployeeRepository employeeRepository;
    private final ScheduleOrderRepository scheduleOrderRepository;
    private final ScheduleMapper scheduleMapper;
    private final WarehouseRepository warehouseRepository;

    @Override
    public List<ScheduleResponse> findAll() {
        List<Schedule> schedules = scheduleRepository.findAll();
        List<ScheduleResponse> scheduleResponses = schedules.stream().map(scheduleMapper::entityToResponse).collect(Collectors.toList());

        mapData(scheduleResponses);
        return scheduleResponses;
    }

    @Override
    public List<ScheduleResponse> findByWarehouseName(String warehouseName) {
        List<String> warehouseId = warehouseRepository.findByNameContaining(warehouseName).stream().map(Warehouse::getId).collect(Collectors.toList());
        if(warehouseId.isEmpty())
            throw new BusinessException(ResponseStatusEnum.LIST_EMPTY,"lịch trình","tiêu chí tìm kiếm");
        List<Schedule> schedules = scheduleRepository.findByWarehouseIdIn(warehouseId);
        if(schedules.isEmpty())
            throw new BusinessException(ResponseStatusEnum.LIST_EMPTY,"lịch trình","tiêu chí tìm kiếm");
        List<ScheduleResponse> scheduleResponses = schedules.stream().map(scheduleMapper::entityToResponse).collect(Collectors.toList());

        mapData(scheduleResponses);
        return scheduleResponses;
    }

    @Override
    public List<ScheduleResponse> findByVehicle(String licensePlate) {
        Optional<Vehicle> vehicle = vehicleRepository.findByLicensePlate(licensePlate);
        if(!vehicle.isPresent())
            throw new BusinessException(ResponseStatusEnum.LIST_EMPTY,"lịch trình","tiêu chí tìm kiếm");

        List<Schedule> schedules = scheduleRepository.findByVehicleId(vehicle.get().getId());
        if(schedules.isEmpty())
            throw new BusinessException(ResponseStatusEnum.LIST_EMPTY,"lịch trình","tiêu chí tìm kiếm");;
        List<ScheduleResponse> scheduleResponses = schedules.stream().map(scheduleMapper::entityToResponse).collect(Collectors.toList());

        mapData(scheduleResponses);
        return scheduleResponses;
    }

    @Override
    public List<ScheduleResponse> findByEmployee(String employeeCode) {
        Optional<Employee> employee = employeeRepository.findByStaffCode(employeeCode);
        if(!employee.isPresent())
            throw new BusinessException(ResponseStatusEnum.LIST_EMPTY,"lịch trình","tiêu chí tìm kiếm");
        List<Schedule> schedules = scheduleRepository.findByEmployeeId(employee.get().getId());
        if(schedules.isEmpty())
            throw new BusinessException(ResponseStatusEnum.LIST_EMPTY,"lịch trình","tiêu chí tìm kiếm");
        List<ScheduleResponse> scheduleResponses = schedules.stream().map(scheduleMapper::entityToResponse).collect(Collectors.toList());

        mapData(scheduleResponses);
        return scheduleResponses;
    }

    @Override
    public ScheduleResponse findById(String id) {
        Optional<Schedule> scheduleOptional = scheduleRepository.findById(id);
        if(!scheduleOptional.isPresent())
            throw new BusinessException(ResponseStatusEnum.LIST_EMPTY,"lịch trình","tiêu chí tìm kiếm");
        ScheduleResponse scheduleResponse = scheduleMapper.entityToResponse(scheduleOptional.get());
        List<ScheduleResponse> scheduleResponses = new ArrayList<>();
        scheduleResponses.add(scheduleResponse);
        mapData(scheduleResponses);
        return scheduleResponses.get(0);
    }

    @Override
    public List<ScheduleResponse> findByDeliveryDate(String deliveryDate) {
        String[] temp = deliveryDate.split("/");
        List<Schedule> schedules = scheduleRepository.findByDeliveryDate(LocalDate.of(Integer.parseInt(temp[2]),Integer.parseInt(temp[1]), Integer.parseInt(temp[0])));
        if(schedules.isEmpty()){
            throw new BusinessException(ResponseStatusEnum.LIST_EMPTY,"lịch trình","tiêu chí tìm kiếm");
        }
        List<ScheduleResponse> scheduleResponses = schedules.stream().filter(c -> (c.getStatus().equals(OrdersStatusEnum.SCHEDULED.toString()) || c.getStatus().equals(OrdersStatusEnum.DELIVERING.toString()))).map(scheduleMapper::entityToResponse).collect(Collectors.toList());
        mapData(scheduleResponses);
        return scheduleResponses;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ScheduleUpdateRequest request) {
        Schedule schedule = scheduleRepository.findById(request.getId()).get();
        schedule.setStatus(request.getStatus());
        schedule.setDeliveryDate(request.getDeliveryDate());
        schedule.setEmployeeId(request.getEmployeeId());
        schedule.setVehicleId(request.getVehicleId());

        scheduleRepository.save(schedule);
    }

    public void mapData(List<ScheduleResponse> scheduleResponses){
        // add vehicle objects
        List<Vehicle> vehicles = vehicleRepository.findAll();
        List<Employee> employees = employeeRepository.findAll();
        List<ScheduleOrder> scheduleOrders = scheduleOrderRepository.findAll();
        List<Warehouse> warehouses = warehouseRepository.findAll();
        for(int i = 0; i < scheduleResponses.size(); i++){
            String vehicleId = scheduleResponses.get(i).getVehicleId();
            String employeeId = scheduleResponses.get(i).getEmployeeId();
            String scheduleId = scheduleResponses.get(i).getId();
            String warehouseId = scheduleResponses.get(i).getWarehouseId();

            scheduleResponses.get(i).setVehicle(vehicles.stream().filter(c -> c.getId().equals(vehicleId)).findFirst().orElse(null));
            scheduleResponses.get(i).setEmployee(employees.stream().filter(c -> c.getId().equals(employeeId)).findFirst().orElse(null));
            scheduleResponses.get(i).setTotalOrders((int) scheduleOrders.stream().filter(c -> c.getScheduleId().equals(scheduleId)).count());
            scheduleResponses.get(i).setWarehouse(warehouses.stream().filter(c -> c.getId().equals(warehouseId)).findFirst().orElse(null));
        }
    }
}
