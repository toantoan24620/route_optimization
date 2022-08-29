package com.toanhv22.routeoptimization.mapper;

import com.toanhv22.routeoptimization.dto.response.WardsResponse;
import com.toanhv22.routeoptimization.entity.Wards;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WardsMapper {
    WardsResponse entityToResponse(Wards wards);
}
