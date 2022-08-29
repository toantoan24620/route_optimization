package com.toanhv22.routeoptimization.service;

import com.toanhv22.routeoptimization.dto.response.WardsResponse;

import java.util.List;

public interface WardsService {
    List<WardsResponse> findByDistrictCode(String districtCode);
}
