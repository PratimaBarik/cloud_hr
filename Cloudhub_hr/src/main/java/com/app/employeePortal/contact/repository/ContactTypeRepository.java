package com.app.employeePortal.contact.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.contact.entity.ContactType;

@Repository
public interface ContactTypeRepository extends JpaRepository<ContactType, String> {
	
	@Query(value = "select a  from ContactType a  where a.org_id=:orgId" )
	public List<ContactType> getContactTypesListByOrgId(@Param(value="orgId") String orgId);

}
