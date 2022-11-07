package com.toanhv22.routeoptimization.service.impl;

import com.toanhv22.routeoptimization.dto.response.WardsResponse;
import com.toanhv22.routeoptimization.mapper.WardsMapper;
import com.toanhv22.routeoptimization.repository.WardsRepository;
import com.toanhv22.routeoptimization.service.WardsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class WardsServiceImpl implements WardsService {

    private final WardsMapper wardsMapper;
    private final WardsRepository wardsRepository;

    @Override
    public List<WardsResponse> findAll() {
        return wardsRepository.findAll().stream().map(wardsMapper::entityToResponse).collect(Collectors.toList());
    }

    @Override
    public List<WardsResponse> findByDistrictCode(String districtCode) {
        return wardsRepository.findByDistrictCode(districtCode).stream().map(wardsMapper::entityToResponse).collect(Collectors.toList());
    }

    @Override
    public WardsResponse findByCode(String code) {
        return wardsMapper.entityToResponse(wardsRepository.findByCode(code));
    }
}
