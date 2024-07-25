package com.app.employeePortal.recruitment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.recruitment.entity.Upwork;

@Repository

public interface UpworkRepository extends JpaRepository<Upwork, String>{

	 public Upwork findByOrgId(String orgId);

	
	
}
