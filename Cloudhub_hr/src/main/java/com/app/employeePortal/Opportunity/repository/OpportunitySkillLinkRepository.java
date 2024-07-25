package com.app.employeePortal.Opportunity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.Opportunity.entity.OpportunitySkillLink;

@Repository
public interface OpportunitySkillLinkRepository extends JpaRepository<OpportunitySkillLink, String> {

    @Query(value = "select a  from OpportunitySkillLink a  where a.opportunitySkillLinkId=:opportunitySkillLinkId " )
    public OpportunitySkillLink getOpportunitySkillLinkId(@Param(value="opportunitySkillLinkId")String opportunitySkillLinkId);

    @Query(value = "select a  from OpportunitySkillLink a  where a.opportunityId=:opportunityId " )
    public List<OpportunitySkillLink> getSkillListByOpportunityId(@Param(value="opportunityId")String opportunityId);

	public OpportunitySkillLink findByOpportunityId(String opportunityId);

}
