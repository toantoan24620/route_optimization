package com.toanhv22.routeoptimization.repository;

import com.toanhv22.routeoptimization.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,String> {
    List<Customer> findByFullNameContaining(String name);
    Optional<Customer> findByPhoneNumber(String phoneNumber);
    Optional<Customer> findByUsername(String username);
}
