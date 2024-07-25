package com.app.employeePortal.category.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.category.entity.CustomerType;
@Repository
public interface CustomerTypeRepository extends JpaRepository<CustomerType, String>{

	public CustomerType findByCustomerTypeId(String customerTypeId);

	public List<CustomerType> findByOrgIdAndLiveInd(String orgId, boolean b);

	public List<CustomerType> findByNameAndOrgId(String name, String orgId);

	public List<CustomerType> findByNameContainingAndOrgId(String name, String orgId);

	
	
}
