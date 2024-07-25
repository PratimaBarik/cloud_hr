package com.app.employeePortal.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.category.entity.Module;

@Repository
public interface ModuleRepository extends JpaRepository<Module, String>{

	public Module findByOrgId(String orgId);
	
}
