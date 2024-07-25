package com.app.employeePortal.ruleEngine.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.ruleEngine.entity.RecruitmentStageMailRuleLink;

@Repository
public interface RecruitmentStageMailRuleLinkRepository extends JpaRepository<RecruitmentStageMailRuleLink, String>{

	@Query(value = "select a  from RecruitmentStageMailRuleLink a  where a.orgId=:orgId and a.liveInd=true" )
    List<RecruitmentStageMailRuleLink> getStageRuleLink(@Param(value="orgId") String orgId);

}
