package com.app.employeePortal.recruitment.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.app.employeePortal.action.mapper.ActionHistoryMapper;
import com.app.employeePortal.candidate.mapper.CandidateViewMapper;
import com.app.employeePortal.candidate.mapper.CandidateWebsiteMapper;
import com.app.employeePortal.customer.mapper.CustomerNetflixMapper;
import com.app.employeePortal.customer.mapper.CustomerRecruitmentMapper;
import com.app.employeePortal.employee.mapper.EmployeeViewMapper;
import com.app.employeePortal.recruitment.mapper.CandidateProjectMapper;
import com.app.employeePortal.recruitment.mapper.CommissionMapper;
import com.app.employeePortal.recruitment.mapper.FunnelMapper;
import com.app.employeePortal.recruitment.mapper.JobVacancyMapper;
import com.app.employeePortal.recruitment.mapper.PingMapper;
import com.app.employeePortal.recruitment.mapper.ProcessDocumentLinkMapper;
import com.app.employeePortal.recruitment.mapper.RecruitmentActionMapper;
import com.app.employeePortal.recruitment.mapper.RecruitmentCloseRuleMapper;
import com.app.employeePortal.recruitment.mapper.RecruitmentOpportunityMapper;
import com.app.employeePortal.recruitment.mapper.RecruitmentProcessMapper;
import com.app.employeePortal.recruitment.mapper.RecruitmentRecruitOwnerMapper;
import com.app.employeePortal.recruitment.mapper.RecruitmentStageApproveMapper;
import com.app.employeePortal.recruitment.mapper.RecruitmentStageMapper;
import com.app.employeePortal.recruitment.mapper.UpworkMapper;
import com.app.employeePortal.recruitment.mapper.WebSiteRecruitmentOpportunityMapper;
import com.app.employeePortal.recruitment.mapper.WebsiteMapper;
import com.app.employeePortal.recruitment.mapper.WebsitePartnerLinkMapper;
import com.app.employeePortal.task.mapper.TaskViewMapper;

import freemarker.template.TemplateException;

public interface RecruitmentService {

	RecruitmentOpportunityMapper linkRecruitmentToOpportunity(RecruitmentOpportunityMapper recruitmentOpportunityMapper);

	List<RecruitmentOpportunityMapper> getRecriutmentListByOppId(String opportunityId,String orgId,String userId)throws Exception;
    
    String saveRecruitmentProcess(RecruitmentProcessMapper recruitmentProcessMapper);
    
    List<RecruitmentProcessMapper> getProcessesOfAdmin(String orgId);
    
    String createStagesForProcess(RecruitmentStageMapper recruitmentStageMapper);
    
    List<RecruitmentStageMapper> getStagesOfProcess(String processId);
     
    List<RecruitmentStageMapper> getAllProcessStagesOfAdmin(String orgId);

	String linkProfileToRecruitment(RecruitmentOpportunityMapper recruitmentOpportunityMapper);

	String updateProcessOfRecruiter(RecruitmentProcessMapper recruitmentProcessMapper);
	
	RecruitmentProcessMapper getProcessMapperByProcessId(String processId);

	public String updateStageOfProcess(RecruitmentStageMapper recruitmentStageMapper);

	RecruitmentStageMapper getStageDetailsByStageId(String id);

	String linkSkillToRecruitment(RecruitmentOpportunityMapper recruitmentOpportunityMapper);

	RecruitmentOpportunityMapper getRecuitmentByProfile(String opportunityId, String profileId, String orgId,String userId)throws Exception;

	List<RecruitmentOpportunityMapper> getRecriutmentListByOppId(String opportunityId);

	List<CandidateViewMapper> getCandidatesBasedOnSkill(String skill, String profileId, String oppId,String orgId)throws Exception;

	void linkCandidateToRecruitment(RecruitmentOpportunityMapper recruitmentOpportunityMapper);
	
  	String createRecruitmentStageApprove(RecruitmentStageApproveMapper recruitmentStageApproveMapper);

	RecruitmentOpportunityMapper updateStageOfAProfile(RecruitmentOpportunityMapper recruitmentOpportunityMapper);

	RecruitmentStageApproveMapper getRecruitmentStageApprove(String stageId);

	String createNoteForRecrutmentStage(RecruitmentOpportunityMapper recruitmentOpportunityMapper);

	List<RecruitmentOpportunityMapper> getRecruitStageNote(String profileId);

	List<RecruitmentOpportunityMapper> getrecruitmentsOfACandidate(String candidateId);

	int getNoOfRecruitmentOfOpportunity(String opportunityId);

	int getTotalNoOfPositionOfRecruitments(String opportunityId);

	int getTotalFilledPosition(String opportunityId);

	Map getSkillsCountInDescription(String recruitmentId, String orgId);

	boolean updateProcessPublish(RecruitmentProcessMapper recruitmentProcessMapper);

	boolean unpublishTheStages(RecruitmentProcessMapper recruitmentProcessMapper);

	boolean publishTheRecruitment(RecruitmentOpportunityMapper recruitmentOpportunityMapper);

	public boolean UnpublishTheRecruitment(RecruitmentOpportunityMapper recruitmentOpportunityMapper);

	List<RecruitmentOpportunityMapper> getPublishedRecruitemntsToWebsite(String website);

	List<RecruitmentOpportunityMapper> getRecruitmentSummary(String oppId);
	
	List<RecruitmentStageMapper> getActiveStagesOfProcess(String processId);

	ByteArrayInputStream exportRecruitmentSummary(List<RecruitmentOpportunityMapper> summaryList);

	String createTaskForProfile(RecruitmentStageApproveMapper recruitmentStageApproveMapper);

	List<RecruitmentStageApproveMapper> getTasksOfStages(String profileId);

	List<RecruitmentStageApproveMapper> getOfProfoileDetails(String profileId);

	List<CandidateViewMapper> getCandidatesBasedOnSkillByUserId(String skill, String profileId, String oppId,
			String userId) throws Exception;

	boolean deleteProfilesOfARecruitment(String recruitId);

	String updateRecriutment(RecruitmentOpportunityMapper recruitmentOpportunityMapper);

	RecruitmentOpportunityMapper getRecriutmentListByOppIdandRecruitId(String opportunityId, String recruitmentId,String orgId);

	String updateStatusOfRecrutment(RecruitmentOpportunityMapper recruitmentOpportunityMapper);

	RecruitmentOpportunityMapper getRecruitProfileStatusLinkByProfileId(String profileId);

	HashMap getRecruitDashBoardRecordByuserIdAndDateRange(String userId, String startDate, String endDate);

	List<RecruitmentOpportunityMapper> getOnBoardedCandidateByuserIdAndDateRange(String recruiterId, String startDate,
			String endDate);

	List<RecruitmentOpportunityMapper> getOfferedCandidateByuserIdAndDateRange(String userId, String startDate,
			String endDate);

	List<RecruitmentOpportunityMapper> getOpenRecruitmentByuserIdAndDateRange(String recruiterId, String startDate,
			String endDate);

	List<RecruitmentOpportunityMapper> getRecruitmentOfRecruiterId(String recruiterId,String orgId);

	List<RecruitmentProcessMapper> getProcessOfAdminInSetting(String orgId);

	List<EmployeeViewMapper> getAllRecruiterByDepartmentId();

	List<RecruitmentOpportunityMapper> getSelectedRecruitmentByuserIdAndDateRange(String recruiterId, String startDate, String endDate);

	int getNoOfRecruitmentByJobOrderAndCategory(String category);

	List<RecruitmentOpportunityMapper>  getOpenRecruitmentByJobOrderAndCategory(String category);

	List<RecruitmentOpportunityMapper> getJobOrderListByOppId(String opportunityId);

	List<RecruitmentOpportunityMapper> getPublishRequirement();

	//public HashMap getNoOfRecruitmentByCreationDate();

	String saveCommission(CommissionMapper commisionMapper);

	List<CommissionMapper> getCommissionByOrgId(String orgId,String type);

	void updateCommission(CommissionMapper commissionMapper);

	CommissionMapper getCommissionMapperByUserId(String userId);

	RecruitmentOpportunityMapper updateCandidateOnboarding(RecruitmentOpportunityMapper recruitmentOpportunityMapper) throws IOException, TemplateException;

	RecruitmentOpportunityMapper getRecruitmentOpportunityMapperByProfileId(String profileId);

	List<RecruitmentOpportunityMapper> getDashBoardOpenRecruitmentByRecruiterId(String userId);

	boolean requirementExistsByJobOrder(String jobOrder);

	String saveWebsite(WebsiteMapper websiteMapper);

	List<WebsiteMapper> getWebsiteListByOrgId(String orgId);

	List<RecruitmentOpportunityMapper> getTagCandidateListByRecriutmentId(String recriutmentId);

	RecruitmentOpportunityMapper getRecriutmentUpdateResponse(String recruitmentId, String orgId) throws Exception;

	Object getRecruitDashBoardRecordByuserIdAndDateRange1(String recruitmentId, String startMonth, String endMonth);
	
	void updatefeedback(RecruitmentOpportunityMapper recruitmentOpportunityMapper,String recruitmentStageNoteId);

	RecruitmentOpportunityMapper getFeedbackByRecruitmentStageNoteId(String recruitmentStageNoteId);

	void updateRecruitmentCandidate(RecruitmentOpportunityMapper recruitmentOpportunityMapper, String profileId);

	RecruitmentOpportunityMapper getRecruitmentCandidateProfileId(String profileId);
	
	//List<RecruitmentOpportunityMapper> getRecruitmentListForAllUsers(List<String> mapperList);

    List<RecruitmentOpportunityMapper> getDashBoardOpenRecruitmentByEmployeeId(String userId);

	boolean ipAddressExists(String url);
	
	List<RecruitmentOpportunityMapper> getRecruitmentProgressDetailsListsByRecruiterId(String userId);

	List<RecruitmentOpportunityMapper> getRecruitmentProgressDetailsListsByUserId(String userId);

	public HashMap getOnboardCountByOpportunityId(String oppId);


	HashMap getAllUserRecordByuserIdAndDateRange(String userId, String startDate, String endDate);

	List<RecruitmentOpportunityMapper> getSalesOpenRecruitmentByuserIdAndDateRange(String userId, String startDate,
			String endDate);
	
	List<RecruitmentStageMapper> getActiveStagesOfProcessWithCandidate(String processId, String recruitmentId);

	String candidateNo(int size);

	RecruitmentOpportunityMapper getRecriutmentByOppIdAndRecruitId(String opportunityId, String recruitId, String orgId)
			throws Exception;

	String closeRecruitment(String recruitmentId);

	List<RecruitmentOpportunityMapper> getCloseRecruitmentOfOpportunity(String opportunityId, String orgId,
			String userId) throws Exception;

	String OpenRecruitment(String recruitmentId);

	String publishWebsiteRequirementPing(String recruitmentId, PingMapper pingMapper);

	String saveUpwork(UpworkMapper upworkMapper);

	UpworkMapper getUpworkByOrgId(String orgId);

	List<RecruitmentOpportunityMapper> getSalesOpenRecruitmentByOrgId(String orgId);

	List<CustomerRecruitmentMapper> getCustomerListByUserId(String userId);

	List<RecruitmentOpportunityMapper> getOfferedCandidateByOrgId(String orgId);

	Object getAllRecordByorgIdAndDateRange(String orgId, String startDate, String endDate);

	String saveWebsiteForPartner(WebsitePartnerLinkMapper websitePartnerLinkMapper);

	List<WebsitePartnerLinkMapper> getWebsitePartnerListByOrgId(String orgId);
	
	List<CustomerRecruitmentMapper> getRecruiterCloserByUserId(String userId,String type,String startDate,String endDate);

	List<CustomerRecruitmentMapper> getSalesCloserByUserId(String userId,String startDate,String endDate);

	List<CustomerRecruitmentMapper> getUsersCloserByOrgId(String organisationId,String startDate,String endDate);

	String getSelectedCandidateEmailContent(RecruitmentOpportunityMapper recruitmentOpportunityMapper)throws IOException, TemplateException;

	String getDropCandidateEmailContent(RecruitmentOpportunityMapper recruitmentOpportunityMappernew)throws IOException, TemplateException;

	List<CustomerRecruitmentMapper> getCustomerRequirementByOrgId(String organizationId, String startDate,
			String endDate);

	String updateCustomerLatestRequirement() throws Exception;

	void linkCandidateToRecruitment(CandidateWebsiteMapper candidateMapper);

	public HashMap getCandidateExternalAndInternalCountListByRecruitmentId(String recruitmentId);
	
	public List<RecruitmentOpportunityMapper> getSalesOpenByuserIdAndDateRange(String userId);

	String getUpdateStageCandidateEmailContent(RecruitmentOpportunityMapper recruitmentOpportunityMappernew)
			throws IOException, TemplateException;

	List<RecruitmentRecruitOwnerMapper> getTagCandidateRecruitOwnerListByRecriutmentId(String recriutmentId);


    void averageFeedBack();

    
	public HashMap getCandidateCountCameFromWebsitePerMonth(String startDate, String endDate);

	List<RecruitmentOpportunityMapper> getDeletedRecriutmentListByOppId(String opportunityId, String orgId,
			String userId);

	List<RecruitmentOpportunityMapper> getOpenRecuitmentByCustomerId(String customerId, String orgId);

	List<FunnelMapper> getFunelRecordByOrganizationId(String orgId);

	HashMap getSeedoMeterRecordByOrganizationId(String orgId);

	boolean stageExistsByWeightage(double probability, String recruitmentProcessId);

	List<CustomerNetflixMapper> getCustomersPositionUserId(String userId);
	
	List<CustomerNetflixMapper> getAllCustomersPositonByUserId(String userId);

	List<CustomerNetflixMapper> getAllCustomerCloserByUserIdAndDateRange(String userId, String startDate,
			String endDate);

	List<CustomerNetflixMapper> getCustomerCloserByUserIdAndDateRange(String userId, String startDate, String endDate);

	//public HashMap getRecruitrtCountByuserId(String userId);
	
	 RecruitmentCloseRuleMapper updateRecruitmentCloseRule(RecruitmentCloseRuleMapper recruitmentCloseRuleMapper);

	 RecruitmentCloseRuleMapper getRecruitmentCloseRuleByOrgId(String orgId);

	List<CustomerRecruitmentMapper> getCustomerRecruitSummaryListByOrgId(String organizationId);

	List<CustomerNetflixMapper> getAllCustomerAlphabetByUserId(String userId);

	List<CustomerNetflixMapper> getAlphabetOrderCustomersByUserId(String userId);

	RecruitmentStageMapper updateReruitmentStage(RecruitmentStageMapper recruitmentStageMapper);

	List<RecruitmentOpportunityMapper> getOrgDashBoardOpenRecruitmentByOrgId(String orgId);

	List<FunnelMapper> getSalesFunnelRecordByUserId(String userId);

	List<FunnelMapper> getRecruiterFunnelRecordByUserId(String userId);

	public HashMap getWorkFlowDeleteHistoryCountList(String orgId);

	public void deleteWorkflowByProcessId(String processId);

	List<RecruitmentOpportunityMapper> getOpenRecuitmentByContactId(String contactId, String orgId);

	public List<RecruitmentProcessMapper> getAllWorkFlowDeleteHistoryList(String orgId);

	List<RecruitmentActionMapper> getRecruiterRecruitmentActionByUserId(String userId);

	List<RecruitmentActionMapper> getSalesRecruitmentActionByUserId(String userId);

	TaskViewMapper approveAction(String profileId,RecruitmentActionMapper recruitmentActionMapper);

	public void deleteRecuitmentByStageId(String stageId);

	RecruitmentOpportunityMapper deleteCandidateOnboarding(String profileId);

	RecruitmentOpportunityMapper changeCandidateAnotherStage(String profileId, String stageId);

//	List<ProcessDocumentLinkMapper> saveProcessDocumentLink(List<ProcessDocumentLinkMapper> processDocumentLinkMapper);
//
//	List<ProcessDocumentLinkMapper> getProcessDocumentLinkByProcessId(String processId);

	ProcessDocumentLinkMapper convertDocumentToMandatory(ProcessDocumentLinkMapper processDocumentLinkMapper);

	List<ActionHistoryMapper> getRecruiterActionHistoryByUserId(String userId);

	List<ActionHistoryMapper> getSalesActionHistoryByUserId(String userId);

	RecruitmentOpportunityMapper getProfileDetails(String profileId);

	List<RecruitmentOpportunityMapper> getOpenRequirementListByOrgId(String orgId);

	List<JobVacancyMapper> getJobVacancyListForWebsite();

	RecruitmentOpportunityMapper updateCandidateReOnboarding(RecruitmentOpportunityMapper recruitmentOpportunityMapper);

	void linkCandidateToRecruitmentForWebsite(WebSiteRecruitmentOpportunityMapper recruitmentOpportunityMapper);

	List<CandidateProjectMapper> getListOfOpenProjectByCandidateId(String candidateId);

	List<RecruitmentOpportunityMapper> getSuggestedRecquirementToCandidate(String candidateId, String orgId);

	List<RecruitmentOpportunityMapper> getDashBoardRecruitmentByCandidateId(String candidateId);

	RecruitmentOpportunityMapper getRecruitmentDetailsByRecruitmentId(String recruitmentId);

	List<CandidateProjectMapper> getListOfCustomerNameFromOpenProjectByCandidateIdForWeb(String candidateId);

	List<CandidateProjectMapper> getListOfProjectNameByCustomerId(String customerId);

	List<CandidateProjectMapper> getListOfCandidateByProjectName(String projectName);

	List<CandidateProjectMapper> getListOfOpenProjectByOrgId(String orgId);

	List<CandidateProjectMapper> getListOfOpenProjectCandidateListByOrgId(String orgId);

	List<RecruitmentOpportunityMapper> getAllRecruitmentsByOrgId(String orgId, String userId);


}
