package com.toanhv22.routeoptimization.repository;

import com.toanhv22.routeoptimization.entity.ConfigParam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigParamRepository extends JpaRepository<ConfigParam, String> {
}
