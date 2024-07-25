package com.app.employeePortal.permission.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.permission.entity.Compliance;


@Repository
public interface ComplianceRepository extends JpaRepository<Compliance, String>{

	/* @Query(value = "select a  from Compliance a  where a.candidateId=:candidateId and a.orgId=:loggedInOrgId" )
	 public Compliance getCompliance(@Param(value="candidateId")String candidateId,
			 												@Param(value="loggedInOrgId") String loggedInOrgId);	*/


	
	 public Compliance findByOrgId(String orgId);
	
}
