package com.toanhv22.routeoptimization.repository;

import com.toanhv22.routeoptimization.entity.Wards;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WardsRepository extends JpaRepository<Wards,String> {
    List<Wards> findByDistrictCode(String districtCode);
    Wards findByCode(String code);
    List<Wards> findByPathWithTypeContaining(String name);
}
