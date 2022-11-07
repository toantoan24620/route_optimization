package com.toanhv22.routeoptimization.repository;

import com.toanhv22.routeoptimization.entity.CostMatrix;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CostMatrixRepository extends JpaRepository<CostMatrix,String> {
    Optional<CostMatrix> findByOrderSourceIdAndOrderTargetId(String orderSourceId, String orderTargetId);
    List<CostMatrix> findByOrderSourceId(String orderSourceId);
}
