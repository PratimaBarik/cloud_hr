package com.app.employeePortal.commercial.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.commercial.entity.PartnerCommission;
@Repository
public interface PartnerCommissionRepository extends JpaRepository<PartnerCommission, String>{

	@Query(value = "select a  from PartnerCommission a  where a.partnerId=:partnerId" )
	List<PartnerCommission> getByPartnerId(@Param(value="partnerId")String partnerId);

	
	List<PartnerCommission> getPartnerCommissionListByOrgId(String orgId);

	PartnerCommission findByPartnerId(String partnerId);

	//PartnerCommission findByCustomerId(PartnerDetails partnerDetails);

	//PartnerCommission findByPartnerId(PartnerDetails partnerDetails);
}
