package com.app.employeePortal.organization.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.organization.entity.EmailCredentialDetails;

@Repository
public interface EmailCredentialRepository extends JpaRepository<EmailCredentialDetails, String>{
	
	@Query(value = "select a  from EmailCredentialDetails a  where a.org_id=:orgId" )
	public EmailCredentialDetails getEmailCredentialsByOrgId(@Param(value="orgId") String empId);

	@Query(value = "select a  from EmailCredentialDetails a  where a.employee_id=:userId" )
	public EmailCredentialDetails getCredentialDetailsByUserId(@Param(value="userId") String userId);

	@Query(value = "select a  from EmailCredentialDetails a  where a.employee_id=:userId" )
	public List<EmailCredentialDetails> getEmailCredentialsByUserId(@Param(value="userId")String userId);
	
	@Query(value = "select a  from EmailCredentialDetails a  where a.employee_id=:employeeId" )
	public List<EmailCredentialDetails> getEmailCredentialByUserId(@Param(value="employeeId")String employeeId);
	
	@Query(value = "select a  from EmailCredentialDetails a  where a.employee_id=:employeeId and defaultInd=true" )
	public EmailCredentialDetails getEmailCredentialByUserIdAndDefaultInd(@Param(value="employeeId")String employeeId);

	@Query(value = "select a  from EmailCredentialDetails a  where a.email_credentials_id=:id" )
	public EmailCredentialDetails getEmailCredentialById(@Param(value="id")String id);
	

}
