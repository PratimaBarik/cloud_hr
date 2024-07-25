package com.app.employeePortal.leads.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.leads.entity.LeadsOpportunityLink;
@Repository
public interface LeadsOpportunityLinkRepository  extends JpaRepository<LeadsOpportunityLink, String>{

	@Query(value = "select a  from LeadsOpportunityLink a  where a.leadsId=:leadsId" )
    public List<LeadsOpportunityLink> getOpportunityIdByLeadsId(@Param(value="leadsId")String leadsId);

	@Query(value = "select a  from LeadsOpportunityLink a  where a.leadOppId=:leadOppId and a.liveInd=true" )
	public LeadsOpportunityLink getLeadsOpportunityDetailsById(@Param(value="leadOppId")String leadOppId);

	@Query(value = "select a  from LeadsOpportunityLink a  where a.leadsId=:leadsId and a.liveInd=true" )
    public LeadsOpportunityLink getOpportunityByLeadsId(@Param(value="leadsId")String leadsId);
	
}
