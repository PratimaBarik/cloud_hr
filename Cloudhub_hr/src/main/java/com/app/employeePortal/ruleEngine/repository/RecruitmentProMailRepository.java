package com.app.employeePortal.ruleEngine.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.employeePortal.ruleEngine.entity.RecruitProMailRuleLink;

public interface RecruitmentProMailRepository extends JpaRepository<RecruitProMailRuleLink, String> {

	//RecruitProMailRuleLink getRecruitmentMailById(String orgId);
	
	@Query(value = "select a  from RecruitProMailRuleLink a  where a.org_id=:orgId" )
    public RecruitProMailRuleLink getRecruitmentMailById(@Param(value="orgId")String orgId);

	 @Query(value = "select a  from RecruitProMailRuleLink a  where a.org_id=:orgId and a.live_ind=true" )
	  public  List<RecruitProMailRuleLink> getRecruitMailRuleLink( @Param(value = "orgId") String orgId);

}
