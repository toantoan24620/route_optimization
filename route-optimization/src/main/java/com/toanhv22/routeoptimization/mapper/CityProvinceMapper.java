package com.toanhv22.routeoptimization.mapper;
import com.toanhv22.routeoptimization.dto.response.CityProvinceResponse;
import com.toanhv22.routeoptimization.entity.CityProvince;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CityProvinceMapper {
    CityProvinceResponse entityToResponse(CityProvince cityProvince);
}
