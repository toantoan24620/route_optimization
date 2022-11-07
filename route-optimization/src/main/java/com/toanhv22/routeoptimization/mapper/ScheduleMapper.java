package com.toanhv22.routeoptimization.mapper;

import com.toanhv22.routeoptimization.dto.response.ScheduleResponse;
import com.toanhv22.routeoptimization.entity.Schedule;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {
    ScheduleResponse entityToResponse(Schedule schedule);
}
