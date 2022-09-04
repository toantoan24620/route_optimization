package com.toanhv22.routeoptimization.repository;

import com.toanhv22.routeoptimization.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, String> {
}
