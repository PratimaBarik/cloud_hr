package com.app.employeePortal.leads.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.employeePortal.leads.entity.LeadsSkillLink;

public interface LeadsSkillLinkRepository extends JpaRepository<LeadsSkillLink, String> {

	@Query(value = "select a  from LeadsSkillLink a  where a.leadsId=:leadsId")
	public List<LeadsSkillLink> getByLeadsId(@Param(value = "leadsId") String leadsId);

	public LeadsSkillLink findByLeadsSkillLinkId(String leadsSkillLinkId);
	
	

}
