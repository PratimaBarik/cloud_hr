package com.app.employeePortal.leave.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.leave.entity.OrganizationLeaveRule;

@Repository
public interface OrganizationLeaveRuleRepository extends JpaRepository<OrganizationLeaveRule, String>{
	@Query(value = "select a  from OrganizationLeaveRule a  where a.org_id=:orgId " )
    public List<OrganizationLeaveRule> getOrganizationLeaveRuleByOrgId(@Param(value="orgId")String orgId);
	
	@Query(value = "select a  from OrganizationLeaveRule a  where a.org_id=:orgId and a.country=:country And a.live_ind=true" )
	public OrganizationLeaveRule getOrganizationLeaveRuleDetailsByOrgIdAndCountry(@Param(value="orgId") String orgId, 
			@Param(value="country") String country);
}
