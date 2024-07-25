package com.app.employeePortal.customer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.customer.entity.CustomerContactLink;
@Repository
public interface CustomerContactLinkRepository extends JpaRepository<CustomerContactLink, String> {

	@Query(value = "select a  from CustomerContactLink a  where a.customerId=:customerId" )
    public List<CustomerContactLink> getContactIdByCustomerId(@Param(value="customerId")String customerId);
	
	@Query(value = "select a  from CustomerContactLink a  where a.contactId=:contactId" )
    public List<CustomerContactLink> getCustomerLinkByContactId(@Param(value="contactId")String contactId);

}
