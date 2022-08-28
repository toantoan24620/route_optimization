package com.toanhv22.routeoptimization.service;

import com.toanhv22.routeoptimization.dto.response.CityProvinceResponse;

import java.util.List;

public interface CityProvinceService{
    List<CityProvinceResponse> findAll();
}
