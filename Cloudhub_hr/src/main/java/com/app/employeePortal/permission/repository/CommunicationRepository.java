package com.app.employeePortal.permission.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.permission.entity.Communication;
@Repository

public interface CommunicationRepository extends JpaRepository<Communication, String> {

	public Communication findByOrgId(String orgId);
	
}

