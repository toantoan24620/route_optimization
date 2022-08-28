package com.toanhv22.routeoptimization.repository;

import com.toanhv22.routeoptimization.entity.CityProvince;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityProvinceRepository extends JpaRepository<CityProvince,String> {
}
