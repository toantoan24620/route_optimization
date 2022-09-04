package com.toanhv22.routeoptimization.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponse {
    private String id;
    private String name;
    private String phonenumber;
    private String birthday;
}
