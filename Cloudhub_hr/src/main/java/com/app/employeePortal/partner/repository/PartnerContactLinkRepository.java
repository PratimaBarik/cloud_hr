package com.app.employeePortal.partner.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.employeePortal.partner.entity.PartnerContactLink;

public interface PartnerContactLinkRepository extends JpaRepository<PartnerContactLink, String> {

	

	//List<PartnerContactLink> getContactByPartnerId(String partnerId);
	
	@Query(value = "select a  from PartnerContactLink a  where a.partnerId=:partnerId" )
    public List<PartnerContactLink>  getContactByPartnerId(@Param(value="partnerId")String partnerId);
	
	@Query(value = "select a  from PartnerContactLink a  where a.partnerId=:partnerId" )
	public PartnerContactLink getContact1ByPartnerId(@Param(value="partnerId")String partnerId);
	
	@Query(value = "select a  from PartnerContactLink a  where a.partnerId=:partnerId" )
	public List<PartnerContactLink> getAllPartner(@Param(value="partnerId")String partnerId);

}
