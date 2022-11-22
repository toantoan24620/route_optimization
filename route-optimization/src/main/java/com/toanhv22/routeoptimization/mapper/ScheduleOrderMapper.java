package com.toanhv22.routeoptimization.mapper;

import com.toanhv22.routeoptimization.dto.response.ScheduleOrderResponse;
import com.toanhv22.routeoptimization.entity.ScheduleOrder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ScheduleOrderMapper {
    ScheduleOrderResponse entityToResponse(ScheduleOrder scheduleOrder);
}
