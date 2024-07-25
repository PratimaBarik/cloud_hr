package com.app.employeePortal.commercial.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.commercial.entity.CustomerCommission;
@Repository
public interface CustomerCommissionRepository extends JpaRepository<CustomerCommission, String>{

	@Query(value = "select a  from CustomerCommission a  where a.customerId=:customerId" )
	List<CustomerCommission> getByCustomerId(@Param(value="customerId")String customerId);

	CustomerCommission findByCustomerId(String customerId);

	List<CustomerCommission> getCustomerCommissionListByOrgId(String orgId);

	

}

