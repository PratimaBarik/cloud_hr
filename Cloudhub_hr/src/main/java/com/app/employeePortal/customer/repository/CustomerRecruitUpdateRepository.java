package com.app.employeePortal.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.customer.entity.CustomerRecruitUpdate;

@Repository
public interface CustomerRecruitUpdateRepository extends JpaRepository<CustomerRecruitUpdate, String>{

	public CustomerRecruitUpdate findByCustomerId(String customerId);

	public CustomerRecruitUpdate findByContactId(String contactId);

}
