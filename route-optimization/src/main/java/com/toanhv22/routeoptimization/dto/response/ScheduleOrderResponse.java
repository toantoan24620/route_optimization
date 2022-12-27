package com.toanhv22.routeoptimization.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleOrderResponse {
    private String id;

    private String scheduleId;

    private String orderId;

    private Integer sequence;

    private String status;

    private LocalDateTime receivedDate;

    private int moveTimeToNode;

    private double distanceToNode;

    private String customerName;

    private Double totalMoney;

    private String itemName;

    private String phoneNumber;

    private String address;

    private String orderLat;

    private String orderLng;

    private String arrivalTime;

    private String leftTime;
}
