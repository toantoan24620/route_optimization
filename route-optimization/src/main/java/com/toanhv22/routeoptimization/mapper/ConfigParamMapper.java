package com.toanhv22.routeoptimization.mapper;

import com.toanhv22.routeoptimization.dto.request.ConfigParamRequest;
import com.toanhv22.routeoptimization.dto.response.ConfigParamResponse;
import com.toanhv22.routeoptimization.entity.ConfigParam;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ConfigParamMapper {
    ConfigParamResponse entityToResponse(ConfigParam configParam);
    ConfigParam requestToEntity(ConfigParamRequest configParamRequest);
}
