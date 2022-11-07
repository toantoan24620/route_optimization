package com.toanhv22.routeoptimization.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryInformationResponse {
    private String id;
    private String addressDetails;
    private String phoneNumber;
    private String longitude;
    private String latitude;
    private String customerId;
    private String wardsCode;
    private WardsResponse wards;
}
