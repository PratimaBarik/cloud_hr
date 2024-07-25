package com.app.employeePortal.customer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.customer.entity.CustomerSkillLink;

@Repository

public interface CustomerSkillLinkRepository extends JpaRepository<CustomerSkillLink, String> {
	
	@Query(value = "select a  from CustomerSkillLink a  where a.skillName=:skillName and a.customerId=:customerId" )
	public CustomerSkillLink getCustomerIdBySkill(@Param(value="skillName")String skillName, @Param(value="customerId")String customerId);


	@Query(value = "select a  from CustomerSkillLink a  where a.customerId=:customerId" )
	public List<CustomerSkillLink> getByCustomerId(@Param(value="customerId")String customerId);
	
	public CustomerSkillLink findByCustomerSkillLinkId(String customerSkillLinkId);

	@Query(value = "select a  from CustomerSkillLink a  where a.customerSkillLinkId=:customerSkillLinkId" )
	public CustomerSkillLink getCustomerByCustomerSkillLinkId(@Param(value="customerSkillLinkId")String customerSkillLinkId);

	//public List<CustomerSkillLink> findBySkillName(String skillName);


	public List<CustomerSkillLink> findBySkillNameAndCustomerId(String definationId, String customerId);
	

}