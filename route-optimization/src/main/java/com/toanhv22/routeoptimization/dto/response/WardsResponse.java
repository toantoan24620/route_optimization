package com.toanhv22.routeoptimization.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WardsResponse {
    private String code;
    private String name;
    private String type;
    private String slug;
    private String nameWithType;
    private String path;
    private String pathWithType;
    private String districtCode;
}
