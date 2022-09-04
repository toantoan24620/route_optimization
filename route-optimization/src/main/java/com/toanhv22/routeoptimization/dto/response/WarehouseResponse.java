package com.toanhv22.routeoptimization.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseResponse {
    private String id;
    private String name;
    private String addressDetails;
    private String wardsCode;
    private String longitude;
    private String latitude;
}
