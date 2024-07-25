package com.app.employeePortal.category.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.category.entity.DistributionAutomationAssignedTo;
@Repository
public interface DistributionAutomationAssignedToRepository extends JpaRepository<DistributionAutomationAssignedTo, String>{

//	@Query(value = "select exp  from DistributionAutomation exp  where exp.orgId=:orgId and exp.type=:type and exp.liveInd=true" )
//	DistributionAutomation getByOrgIdAndType(@Param(value="orgId") String orgId, @Param(value="type") String type);

	List<DistributionAutomationAssignedTo> getByDistributionAutomationIdAndLiveInd(String distributionAutomationId,boolean b);

	

	
	
}
