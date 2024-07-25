package com.app.employeePortal.partner.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.employeePortal.partner.entity.PartnerSkillSet;
@Repository
public interface PartnerSkillSetRepository extends JpaRepository<PartnerSkillSet, String> {
	@Query(value = "select a  from PartnerSkillSet a  where a.partnerId=:partnerId" )
	List<PartnerSkillSet> getSkillSetById(@Param(value="partnerId")String partnerId);

	//List<PartnerSkillSet> findBySkillName(String skillName);

	List<PartnerSkillSet> findBySkillNameAndPartnerId(String definationId, String partnerId);
	
	
	

}
