package com.app.employeePortal.partner.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.partner.entity.PartnerAddressLink;
@Repository
public interface PartnerAddressLinkRepository extends JpaRepository<PartnerAddressLink, String> {

	
	@Query(value = "select a  from PartnerAddressLink a  where a.partnerId=:partnerId" )
    public List<PartnerAddressLink> getAddressListByPartnerId(@Param(value="partnerId")String partnerId);

	public PartnerAddressLink findByPartnerId(String partnerId);
}
