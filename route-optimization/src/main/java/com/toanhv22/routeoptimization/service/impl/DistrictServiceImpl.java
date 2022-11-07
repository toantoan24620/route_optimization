package com.toanhv22.routeoptimization.service.impl;

import com.toanhv22.routeoptimization.dto.response.DistrictResponse;
import com.toanhv22.routeoptimization.mapper.DistrictMapper;
import com.toanhv22.routeoptimization.repository.DistrictRepository;
import com.toanhv22.routeoptimization.service.DistrictService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class DistrictServiceImpl implements DistrictService{

    private final DistrictRepository districtRepository;
    private final DistrictMapper districtMapper;

    @Override
    public List<DistrictResponse> findByCityProvinceCode(String cityProvinceCode) {
        return districtRepository.findByCityProvinceCode(cityProvinceCode).stream().map(districtMapper::entityToResponse).collect(Collectors.toList());
    }

    @Override
    public DistrictResponse findByCode(String code) {
        return districtMapper.entityToResponse(districtRepository.findByCode(code));
    }
}
