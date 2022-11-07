package com.toanhv22.routeoptimization.repository;

import com.toanhv22.routeoptimization.entity.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DistrictRepository extends JpaRepository<District,String> {
    List<District> findByCityProvinceCode(String cityProvinceCode);
    District findByCode(String code);
}
