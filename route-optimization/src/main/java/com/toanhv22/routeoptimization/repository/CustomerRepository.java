package com.toanhv22.routeoptimization.repository;

import com.toanhv22.routeoptimization.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,String> {
}
