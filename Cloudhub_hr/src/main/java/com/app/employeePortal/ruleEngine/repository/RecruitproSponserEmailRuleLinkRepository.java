package com.app.employeePortal.ruleEngine.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.ruleEngine.entity.RecruitproSponserEmailRuleLink;

@Repository
public interface RecruitproSponserEmailRuleLinkRepository extends JpaRepository<RecruitproSponserEmailRuleLink, String>{

	@Query(value = "select a  from RecruitproSponserEmailRuleLink a  where a.org_id=:orgId and a.live_ind=true" )
	List<RecruitproSponserEmailRuleLink> getSponserEmailRuleLink( @Param(value = "orgId")String orgId);

}
