package com.app.employeePortal.recruitment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.employeePortal.recruitment.entity.OpportunityRecruiterLink;

public interface OpportunityRecruiterLinkRepository extends JpaRepository<OpportunityRecruiterLink, String> {
	
	@Query(value = "select a  from OpportunityRecruiterLink a  where a.opportunity_id=:oppId and a.live_ind = true" )
	public List<OpportunityRecruiterLink> getRecruiterLinkByOppId(@Param(value="oppId")String oppId);

	@Query(value = "select a  from OpportunityRecruiterLink a  where a.recruiter_id=:recruiterId and a.live_ind = true" )
	public List<OpportunityRecruiterLink> getRecruiterLinkByRecruiterId(@Param(value="recruiterId")String recruiterId);

	@Query(value = "select a  from OpportunityRecruiterLink a  where a.opportunity_id=:opportunity_id" )
	public List<OpportunityRecruiterLink> getProfileDetailByOppertunityId(@Param(value="opportunity_id")String opportunity_id);

	//public List<OpportunityRecruiterLink> getProfileDetailByEmployeeIdAndonBoardInd(String employee_id);

}
