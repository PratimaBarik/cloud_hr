package com.app.employeePortal.recruitment.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.employeePortal.recruitment.entity.OpportunityRecruitDetails;

public interface RecruitmentOpportunityDetailsRepository extends JpaRepository<OpportunityRecruitDetails, String> {

	//List<RecruitmentOpportunityDetails> getRecriutmentsByOpportunityId(String opportunityId);
	
	@Query(value = "select a  from OpportunityRecruitDetails a  where a.opportunity_id=:opportunityId and live_ind=true " )
	public List<OpportunityRecruitDetails> getRecriutmentsByOpportunityId(@Param(value="opportunityId") String opportunityId);

	@Query(value = "select a  from OpportunityRecruitDetails a  where a.opportunity_id=:opportunityId and live_ind=true and close_ind=false " )
	public List<OpportunityRecruitDetails> getOpenRecriutmentsByOpportunityIdAndCloseInd(@Param(value="opportunityId") String opportunityId);
	//RecruitmentOpportunityDetails getRecruitmentOpportunityDetailsById(String recruitmentProcessId);
	
	/*@Query(value = "select a  from RecruitmentOpportunityDetails a  where a.recruitment_process_id=:recruitmentProcessId" )
    public RecruitmentOpportunityDetails getRecruitmentOpportunityDetailsById(@Param(value="recruitmentProcessId")String recruitmentProcessId);*/

	@Query(value = "select a  from OpportunityRecruitDetails a  where a.opportunity_id=:opportunityId and a.recruitment_id=:recruitmentId and live_ind=true" )
	public OpportunityRecruitDetails getRecriutmentsByOpportunitysId(@Param(value="opportunityId") String opportunityId,@Param(value="recruitmentId") String recruitmentId);
	
	@Query(value = "select a  from OpportunityRecruitDetails a  where a.recruitment_id=:recruitmentId and live_ind=true" )
	public OpportunityRecruitDetails getRecruitmentDetailsByRecruitId(@Param(value="recruitmentId") String recruitmentId);

	@Query(value = "select a  from OpportunityRecruitDetails a  where a.job_order Like %:jobOrder% and live_ind=true" )
	public List<OpportunityRecruitDetails> getByJobOrder(@Param(value="jobOrder")String jobOrder);

	@Query(value="select a from OpportunityRecruitDetails a where a.category=:category and live_ind=true")
	public List<OpportunityRecruitDetails> getRecruitmentByJobOrderAndCategory(@Param(value="category")String category);

	//public List<OpportunityRecruitDetails> findByCreationDateBetweenAndLiveInd(String userId, Date currDate, Date nextDate, boolean b);

	@Query(value = "select a  from OpportunityRecruitDetails a  where a.recruitment_id=:recruitmentId and live_ind=true" )
	public  List<OpportunityRecruitDetails> getRecruitmentDetailsListByRecruitmentId(@Param(value="recruitmentId") String recruitmentId);
	
	@Query(value = "select a  from OpportunityRecruitDetails a  where a.opportunity_id=:opportunityId and live_ind=true" )
	public OpportunityRecruitDetails getRecriutmentByOpportunityId(@Param(value="opportunityId") String opportunityId);
	
	@Query(value = "select a  from OpportunityRecruitDetails a  where a.job_order=:jobOrder  and live_ind=true" )
	public OpportunityRecruitDetails getByJobOrderDetails(@Param(value="jobOrder")String jobOrder);

	@Query(value = "select a  from OpportunityRecruitDetails a  where a.opportunity_id=:opportunityId and closeInd=true" )
	public List<OpportunityRecruitDetails> getClosedRecriutmentsByOpportunityId(@Param(value="opportunityId") String opportunityId );

	@Query(value = "select a  from OpportunityRecruitDetails a  where a.opportunity_id=:opportunityId and closeInd=false and live_ind=true")
	public List<OpportunityRecruitDetails> getOpenRecruitmentListByOppId(@Param(value="opportunityId")String opportunityId);

	@Query(value = "select a  from OpportunityRecruitDetails a  where a.sponser_id=:contactId and live_ind=true")
	public List<OpportunityRecruitDetails> getRecruitmentListByContactId(@Param(value="contactId")String contactId);
	
	@Query(value = "select a  from OpportunityRecruitDetails a  where a.opportunity_id=:opportunityId and live_ind=true" )
	public List<OpportunityRecruitDetails> getRecriutmentByOpportunityIdAndLiveInd(@Param(value="opportunityId") String opportunityId);
	
	@Query(value = "select a  from OpportunityRecruitDetails a  where a.opportunity_id=:opportunityId and a.liveInd=true and a.creationDate BETWEEN :startDate AND :endDate")
	public List<OpportunityRecruitDetails> getRecruitmentListByOppIdAndDateRange(@Param(value ="opportunityId") String opportunityId,
			@Param(value ="startDate") Date startDate, 
			@Param(value ="endDate") Date endDate);
	@Query(value = "select a  from OpportunityRecruitDetails a  where a.recruitment_id=:recruitmentId and live_ind=true" )
	public OpportunityRecruitDetails getRecruitmentDetailsByRecruitmentId(@Param(value="recruitmentId")String recruitmentId);

	@Query(value = "select a  from OpportunityRecruitDetails a  where a.opportunity_id=:opportunityId and live_ind=false " )
	public List<OpportunityRecruitDetails> getDeletedOpenRecriutmentsByOpportunityId(@Param(value="opportunityId")String opportunityId);
	
	@Query(value = "select a  from OpportunityRecruitDetails a  where a.recruitment_id=:recruitmentId and a.liveInd=true and a.creationDate BETWEEN :startDate AND :endDate")
	public OpportunityRecruitDetails getRecruitmentListByRecruitIdAndDateRange(@Param(value ="recruitmentId") String recruitmentId,
			@Param(value ="startDate") Date startDate, 
			@Param(value ="endDate") Date endDate);

	public List<OpportunityRecruitDetails> findByUserIdAndCreationDateBetweenAndLiveInd(String userId, Date currDate,
			Date nextDate, boolean b);
	
	@Query(value = "select a  from OpportunityRecruitDetails a  where a.orgId=:orgId and closeInd=false and live_ind=true")
	public List<OpportunityRecruitDetails> getOpenRecruitmentsListByOrgIdAndCloseInd(@Param(value="orgId")String orgId);

	@Query(value = "select a  from OpportunityRecruitDetails a  where a.workPreferance=:workLocation and a.billing <=:billing and closeInd=false and live_ind=true")
	public List<OpportunityRecruitDetails> getRequirementByWorkPreferenceAndbilling(@Param(value ="workLocation") String workLocation,
			@Param(value ="billing") double billing);

	@Query(value = "select a  from OpportunityRecruitDetails a  where a.userId=:userId and live_ind=true" )
	public  List<OpportunityRecruitDetails> getRecruitmentDetailsListByUserId(@Param(value="userId") String userId);
	
	@Query(value = "select a  from OpportunityRecruitDetails a  where a.orgId=:orgId and live_ind=true " )
	public List<OpportunityRecruitDetails> getAllRecriutmentsByOrgIdAndLiveInd(@Param(value="orgId") String orgId);
	
}