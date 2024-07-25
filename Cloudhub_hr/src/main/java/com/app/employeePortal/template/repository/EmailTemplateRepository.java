package com.app.employeePortal.template.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.template.entity.EmailTemplateDetails;

@Repository
public interface EmailTemplateRepository  extends JpaRepository<EmailTemplateDetails, String>{
	@Query(value = "select a  from EmailTemplateDetails a  where a.organization_id=:orgId and live_ind=true" )
	public List<EmailTemplateDetails> getTempleteDetailsListByOrgId(@Param(value="orgId") String orgId);
}
