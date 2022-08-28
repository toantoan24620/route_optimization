package com.toanhv22.routeoptimization.service.impl;

import com.toanhv22.routeoptimization.dto.response.CityProvinceResponse;
import com.toanhv22.routeoptimization.mapper.CityProvinceMapper;
import com.toanhv22.routeoptimization.repository.CityProvinceRepository;
import com.toanhv22.routeoptimization.service.CityProvinceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CityProvinceServiceImpl implements CityProvinceService {

    private final CityProvinceRepository cityProvinceRepository;
    private final CityProvinceMapper cityProvinceMapper;

    @Override
    public List<CityProvinceResponse> findAll() {
        return cityProvinceRepository.findAll().stream().map(cityProvinceMapper::entityToResponse).collect(Collectors.toList());
    }
}
