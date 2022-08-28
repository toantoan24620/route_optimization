package com.toanhv22.routeoptimization.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CityProvinceResponse {
    private String code;
    private String name;
    private String slug;
    private String type;
    private String nameWithType;
}
