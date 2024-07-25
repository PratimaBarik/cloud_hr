package com.app.employeePortal.registration.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.registration.entity.DesignationDelete;

@Repository
public interface DesignationDeleteRepository extends JpaRepository<DesignationDelete, String> {

	DesignationDelete findByDesignationTypeId(String designationTypeId);

	List<DesignationDelete> findByOrgId(String orgId);


}
