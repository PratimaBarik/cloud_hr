package com.app.employeePortal.permission.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.employeePortal.permission.entity.Sourcing;

public interface SourcingRepository extends JpaRepository<Sourcing, String>{

	public Sourcing findByOrgId(String orgId);

	
	
}
