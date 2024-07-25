package com.app.employeePortal.Opportunity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.Opportunity.entity.OpportunityForecastLink;

@Repository
public interface OpportunityForecastLinkRepository extends JpaRepository<OpportunityForecastLink, String> {

	 @Query(value = "select a  from OpportunityForecastLink a  where a.opportunityId=:opportunityId " )
	List<OpportunityForecastLink> getSkillListByOpportunityId(@Param(value="opportunityId")String opportunityId);

	 @Query(value = "select a  from OpportunityForecastLink a  where a.opportunityId=:opportunityId " )
	 OpportunityForecastLink getopportunityDetailsById(@Param(value="opportunityId")String opportunityId);

	 @Query(value = "select a  from OpportunityForecastLink a  where a.opportunityForecastLinkId=:opportunityForecastLinkId " )
	OpportunityForecastLink getForecastSkillAndNumberByForcastId(@Param(value="opportunityForecastLinkId")String opportunityForecastLinkId);

	 @Query(value = "select a  from OpportunityForecastLink a  where a.orgId=:orgId " )
	List<OpportunityForecastLink> getForecastListByOrgId(@Param(value="orgId")String orgId);
	
	
	
}
