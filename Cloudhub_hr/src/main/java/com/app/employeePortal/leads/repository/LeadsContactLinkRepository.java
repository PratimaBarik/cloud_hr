package com.app.employeePortal.leads.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.leads.entity.LeadsContactLink;
@Repository
public interface LeadsContactLinkRepository extends JpaRepository<LeadsContactLink, String> {

	@Query(value = "select a  from LeadsContactLink a  where a.leadsId=:leadsId" )
    public List<LeadsContactLink> getContactIdByLeadsId(@Param(value="leadsId")String leadsId);

	@Query(value = "select a  from LeadsContactLink a  where a.contactId=:contactId" )
    public LeadsContactLink getContactDetailsById(@Param(value="contactId")String contactId);
	
	@Query(value = "select a  from LeadsContactLink a  where a.leadsId=:leadsId" )
    public LeadsContactLink getContactByLeadsId(@Param(value="leadsId")String leadsId);
}
