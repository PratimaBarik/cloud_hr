package com.app.employeePortal.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.notification.entity.NotificationRule;

@Repository
public interface NotificationRuleRepository extends JpaRepository<NotificationRule, String>{

	@Query(value = "select u  from NotificationRule u  where u.orgId=:orgId  " )
	NotificationRule getNotificationByOrgId(@Param(value="orgId")String orgId);

	
}
