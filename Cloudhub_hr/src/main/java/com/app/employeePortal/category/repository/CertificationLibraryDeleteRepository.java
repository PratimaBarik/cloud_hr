package com.app.employeePortal.category.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.category.entity.CertificationLibraryDelete;

@Repository
public interface CertificationLibraryDeleteRepository extends JpaRepository<CertificationLibraryDelete, String> {

	CertificationLibraryDelete findByCertificationId(String certificationId);

	List<CertificationLibraryDelete> findByOrgId(String orgId);

	

	


}
