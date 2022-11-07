package com.toanhv22.routeoptimization.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdersRequest {
    private String id;
    private String itemName;
    private String itemCode;
    private Double weight;
    private Double size;
    private String status;
    private String note;
    private Double totalMoney;
    private String vehicleType;
    private String customerId;
    private String warehouseId;
    private String address;
    private String phoneNumber;
    private String latitude;
    private String longitude;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime finishDate;
}
