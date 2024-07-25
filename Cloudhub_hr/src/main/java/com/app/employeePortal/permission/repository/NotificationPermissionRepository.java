package com.app.employeePortal.permission.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.permission.entity.NotificationPermission;

@Repository
public interface NotificationPermissionRepository extends JpaRepository<NotificationPermission, String> {

	@Query(value = "select a  from NotificationPermission a  where a.userId=:userId and a.orgId=:loggedInOrgId")
	public NotificationPermission getNotificationPermission(@Param(value = "userId") String userId,
			@Param(value = "loggedInOrgId") String loggedInOrgId);

}
