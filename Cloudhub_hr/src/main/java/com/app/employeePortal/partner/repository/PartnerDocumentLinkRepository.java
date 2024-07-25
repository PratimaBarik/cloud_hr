package com.app.employeePortal.partner.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.employeePortal.partner.entity.PartnerDocumentLink;

public interface PartnerDocumentLinkRepository extends JpaRepository<PartnerDocumentLink, String> {

	//List<PartnerDocumentLink> getDocumentByPartnerId(String partnerId);
	
	@Query(value = "select a  from PartnerDocumentLink a  where a.partner_id=:partnerId" )
    public List<PartnerDocumentLink>  getDocumentByPartnerId(@Param(value="partnerId")String partnerId);

}
