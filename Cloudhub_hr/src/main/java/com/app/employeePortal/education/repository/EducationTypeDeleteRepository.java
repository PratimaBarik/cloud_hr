package com.app.employeePortal.education.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.education.entity.EducationTypeDelete;

@Repository
public interface EducationTypeDeleteRepository extends JpaRepository<EducationTypeDelete, String>{

	List<EducationTypeDelete> findByOrgId(String orgId);

	EducationTypeDelete findByEducationTypeId(String educationTypeId);


	

}
