package com.toanhv22.routeoptimization.service;

import com.toanhv22.routeoptimization.dto.response.DistrictResponse;
import com.toanhv22.routeoptimization.entity.District;

import java.util.List;

public interface DistrictService {
    List<DistrictResponse> findByCityProvinceCode(String cityProvinceCode);
    DistrictResponse findByCode(String code);
}
