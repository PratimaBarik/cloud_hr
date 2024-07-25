package com.app.employeePortal.candidate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.employeePortal.candidate.entity.DefinationDetailsDelete;

public interface DefinationDeleteRepository extends JpaRepository<DefinationDetailsDelete, String>{

	List<DefinationDetailsDelete> findByOrgId(String orgId);

	DefinationDetailsDelete findByDefinationId(String definationId);

	
}
