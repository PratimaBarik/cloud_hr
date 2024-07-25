package com.app.employeePortal.leads.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.leads.entity.LeadsAddressLink;

@Repository
public interface LeadsAddressLinkRepository extends JpaRepository<LeadsAddressLink, String> {

	@Query(value = "select a  from LeadsAddressLink a  where a.leadsId=:leadsId" )
	public List<LeadsAddressLink> getAddressListByLeadsId(@Param(value="leadsId")String leadsId);

	public LeadsAddressLink findByLeadsId(String leadsId);

	

}
