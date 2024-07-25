package com.app.employeePortal.template.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.template.entity.SignatureDetails;

@Repository
public interface SignatureRepository extends JpaRepository<SignatureDetails, String>{
	@Query(value = "select a  from SignatureDetails a  where a.org_id=:orgId and a.type='Admin'" )
	public SignatureDetails getSignatureDetailsByOrgId(@Param(value="orgId") String orgId);
	
	@Query(value = "select a  from SignatureDetails a  where a.user_id=:userId and a.type='User'" )
	public SignatureDetails getSignatureDetailsByUserId(@Param(value="userId") String userId);
}
