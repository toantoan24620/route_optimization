package com.toanhv22.routeoptimization.service.impl;

import com.toanhv22.routeoptimization.constant.ConfigParamEnum;
import com.toanhv22.routeoptimization.dto.response.ScheduleOrderResponse;
import com.toanhv22.routeoptimization.entity.Customer;
import com.toanhv22.routeoptimization.entity.Orders;
import com.toanhv22.routeoptimization.entity.Schedule;
import com.toanhv22.routeoptimization.entity.ScheduleOrder;
import com.toanhv22.routeoptimization.mapper.ScheduleOrderMapper;
import com.toanhv22.routeoptimization.repository.*;
import com.toanhv22.routeoptimization.service.ScheduleOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleOrderServiceImpl implements ScheduleOrderService {

    private final ScheduleOrderRepository scheduleOrderRepository;

    private final ScheduleOrderMapper scheduleOrderMapper;

    private final OrdersRepository ordersRepository;

    private final CustomerRepository customerRepository;

    private final ConfigParamRepository configParamRepository;

    private final ScheduleRepository scheduleRepository;


    @Override
    public List<ScheduleOrderResponse> findByScheduleId(String scheduleId) {
        List<ScheduleOrder> scheduleOrders = scheduleOrderRepository.findByScheduleId(scheduleId);
        List<ScheduleOrderResponse> scheduleOrderResponses = scheduleOrders.stream().map(scheduleOrderMapper::entityToResponse).sorted(Comparator.comparingInt(ScheduleOrderResponse::getSequence)).collect(Collectors.toList());

//        for (int i = 0; i < scheduleOrderResponses.size(); i++) {
//            scheduleOrderResponses.get(i).setOrders(ordersService.findById(scheduleOrderResponses.get(i).getOrderId()));
//        }
        List<Orders> orders = ordersRepository.findAll();
        List<Customer> customers = customerRepository.findAll();
        String startTime = configParamRepository.findById(ConfigParamEnum.START_TIME.toString()).get().getValue();
        String[] startTimesTemp = startTime.split(":");
        int hours = Integer.parseInt(startTimesTemp[0]);
        int minutes = Integer.parseInt(startTimesTemp[1]);
        int unloadTime = Integer.parseInt(configParamRepository.findById(ConfigParamEnum.UNLOAD_TIME.toString()).get().getValue());

        for(int i = 0; i < scheduleOrderResponses.size(); i++){
            String orderId = scheduleOrderResponses.get(i).getOrderId();
            Orders order = orders.stream().filter(c -> c.getId().equals(orderId)).findFirst().orElse(null);

            minutes += scheduleOrderResponses.get(i).getMoveTimeToNode();
            hours += minutes/60;
            minutes = minutes % 60;

            if(minutes < 10){
                scheduleOrderResponses.get(i).setArrivalTime(hours + ":0" + minutes);
            }else{
                scheduleOrderResponses.get(i).setArrivalTime(hours + ":" + minutes);
            }

            minutes += unloadTime;
            hours += minutes/60;
            minutes = minutes % 60;

            if(minutes < 10){
                scheduleOrderResponses.get(i).setLeftTime(hours + ":0" + minutes);
            }else{
                scheduleOrderResponses.get(i).setLeftTime(hours + ":" + minutes);
            }

            if(order != null){
                scheduleOrderResponses.get(i).setStatus(order.getStatus());
                scheduleOrderResponses.get(i).setAddress(order.getAddress());
                scheduleOrderResponses.get(i).setTotalMoney(order.getTotalMoney());
                scheduleOrderResponses.get(i).setItemName(order.getItemName());
                scheduleOrderResponses.get(i).setPhoneNumber(order.getPhoneNumber());
                scheduleOrderResponses.get(i).setOrderLat(order.getLatitude());
                scheduleOrderResponses.get(i).setOrderLng(order.getLongitude());
                String customerId = order.getCustomerId();
                Customer customer = customers.stream().filter(c -> c.getId().equals(customerId)).findFirst().orElse(null);
                scheduleOrderResponses.get(i).setCustomerName(customer != null ? customer.getFullName() : null);
            }
        }
        return scheduleOrderResponses;
    }

    @Override
    public List<List<ScheduleOrderResponse>> findByWarehouseId(String warehouseId) {
        List<List<ScheduleOrderResponse>> result = new ArrayList<>();
        List<Schedule> scheduleOrders = scheduleRepository.findByWarehouseId(warehouseId);
        if(scheduleOrders.isEmpty())
            return Collections.emptyList();
        for(Schedule item: scheduleOrders){
            if(item.getStatus().equals("PENDING") || item.getStatus().equals("SCHEDULED")){
                result.add(findByScheduleId(item.getId()));
            }
        }
        return result;
    }
}
