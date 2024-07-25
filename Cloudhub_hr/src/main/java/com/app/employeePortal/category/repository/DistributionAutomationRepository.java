package com.app.employeePortal.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.category.entity.DistributionAutomation;
@Repository
public interface DistributionAutomationRepository extends JpaRepository<DistributionAutomation, String>{

	@Query(value = "select exp  from DistributionAutomation exp  where exp.orgId=:orgId and exp.type=:type and exp.liveInd=true" )
	DistributionAutomation getByOrgIdAndType(@Param(value="orgId") String orgId, @Param(value="type") String type);

	DistributionAutomation getByDistributionAutomationId(String distributionAutomationId);

	

	
	
}
