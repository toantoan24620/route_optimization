package com.toanhv22.routeoptimization.repository;

import com.toanhv22.routeoptimization.entity.DeliveryInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryInformationRepository extends JpaRepository<DeliveryInformation, String> {
    List<DeliveryInformation> findByCustomerId(String customerId);
}
