package com.app.employeePortal.ruleEngine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.employeePortal.ruleEngine.entity.RecruitProNotificationLink;

public interface RecruitProNotificationRepository extends JpaRepository<RecruitProNotificationLink, String> {

	//RecruitProNotificationLink getRecruitNotificationById(String orgId);
	
	@Query(value = "select a  from RecruitProNotificationLink a  where a.org_id=:orgId" )
    public RecruitProNotificationLink getRecruitNotificationById(@Param(value="orgId")String orgId);

}
