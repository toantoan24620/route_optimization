package com.toanhv22.routeoptimization.mapper;

import com.toanhv22.routeoptimization.dto.response.DistrictResponse;
import com.toanhv22.routeoptimization.entity.District;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DistrictMapper {
    DistrictResponse entityToResponse(District district);
}
