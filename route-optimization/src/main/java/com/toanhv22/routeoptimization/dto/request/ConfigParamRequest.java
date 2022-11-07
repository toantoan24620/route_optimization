package com.toanhv22.routeoptimization.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfigParamRequest {
    private String id;
    private String name;
    private String value;
    private String description;
}
