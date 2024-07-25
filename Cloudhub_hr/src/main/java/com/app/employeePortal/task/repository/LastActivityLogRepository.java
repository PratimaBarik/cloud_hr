package com.app.employeePortal.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.employeePortal.task.entity.LastActivityLog;

public interface LastActivityLogRepository extends JpaRepository<LastActivityLog, String>{

	LastActivityLog findByUserTypeAndUserTypeId(String userType, String userTypeId);

	LastActivityLog findByUserTypeId(String id);

}
