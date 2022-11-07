package com.toanhv22.routeoptimization.service;

import com.toanhv22.routeoptimization.dto.request.ConfigParamRequest;
import com.toanhv22.routeoptimization.dto.response.ConfigParamResponse;

import java.util.List;

public interface ConfigParamService {
    List<ConfigParamResponse> findAll();
    ConfigParamResponse findById(String id);
    ConfigParamResponse update(ConfigParamRequest configParamRequest);
}
