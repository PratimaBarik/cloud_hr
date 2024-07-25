package com.app.employeePortal.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.customer.entity.CustomerInfo;

@Repository
public interface CustomerInfoRepository extends JpaRepository<CustomerInfo, String> {

}
