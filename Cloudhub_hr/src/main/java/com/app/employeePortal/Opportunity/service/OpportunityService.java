package com.app.employeePortal.Opportunity.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.app.employeePortal.Opportunity.entity.OpportunityDetails;
import com.app.employeePortal.Opportunity.mapper.ConversionValueMapper;
import com.app.employeePortal.Opportunity.mapper.OpportunityDropdownMapper;
import com.app.employeePortal.Opportunity.mapper.OpportunityForecastLinkMapper;
import com.app.employeePortal.Opportunity.mapper.OpportunityMapper;
import com.app.employeePortal.Opportunity.mapper.OpportunityOrderMapper;
import com.app.employeePortal.Opportunity.mapper.OpportunityOrderViewMapper;
import com.app.employeePortal.Opportunity.mapper.OpportunityProductMapper;
import com.app.employeePortal.Opportunity.mapper.OpportunityReportMapper;
import com.app.employeePortal.Opportunity.mapper.OpportunitySkillLinkMapper;
import com.app.employeePortal.Opportunity.mapper.OpportunityViewMapper;
import com.app.employeePortal.contact.mapper.ContactMapper;
import com.app.employeePortal.contact.mapper.ContactViewMapper;
import com.app.employeePortal.customer.mapper.TransferMapper;
import com.app.employeePortal.document.mapper.DocumentMapper;
import com.app.employeePortal.employee.mapper.NotesMapper;
import com.app.employeePortal.organization.mapper.OrganizationValueMapper;
import com.app.employeePortal.recruitment.mapper.RecruitmentOpportunityMapper;

import freemarker.template.TemplateException;

public interface OpportunityService {

	public OpportunityViewMapper saveOpportunity(OpportunityMapper opportunityMapper);

	public List<OpportunityViewMapper> getOpportunityDetailsListPageWiseByUserId(String userId, int pageNo,
			int pageSize);

	public OpportunityViewMapper getOpportunityRelatedDetails(OpportunityDetails opportunityDetails);

	public List<OpportunityViewMapper> getOpportunityListByCustomerId(String customerId);

	public OpportunityViewMapper updateOpportunityDetails(String opportunityId, OpportunityMapper opportunityMapper);

	public String saveOpportunityNotes(NotesMapper notesMapper);

	public List<NotesMapper> getNoteListByOpportunityId(String opportunityId);

	public NotesMapper getNotes(String id);

	public List<OpportunityViewMapper> getOpportunityDetailsListByContactId(String contactId);

	public List<DocumentMapper> getOpportunityDetailsListBydocumentId(String opportunityId);

	public DocumentMapper getDocument(String id);

	public OpportunityViewMapper getOpportunityDetails(String opportunityId);

	public List<OpportunityViewMapper> getOpportunityDetailsByName(String opportunityName);

	public List<ContactViewMapper> getContactListByOpportunityId(String opportunityId);

	public ByteArrayInputStream exportCandidateListToExcel(List<OpportunityViewMapper> opportunityList);

	public ContactViewMapper saveOpportunityContact(ContactMapper contactMapper) throws IOException, TemplateException;

	public void deleteOppertunityDetailsById(String id);

	public List<OpportunityViewMapper> getDeleteOpportunityDetails(String loggeduserId, int pageNo, int pageSize);

	// public HashMap getCountList();

	public HashMap getCountListByuserId(String userId);

	public void deleteDocumentById(String documentId);

	public List<OpportunityViewMapper> getOpprtunityByRecruiterId(String recruiterId);

	public List<OpportunityViewMapper> getopportunityOfASalesUser(String userId);

	public String saveTagContact(ContactMapper contactMapper);

//	public List<OpportunityViewMapper> getAllOpportunityList(int pageNo, int pageSize);

	void reinstateOpportunityByOppId(String opportunityId);

	public HashMap getRecruitrtCountByuserId(String userId);

	public List<RecruitmentOpportunityMapper> getOpportunityListByJobOrder(String jobOrder);

	public String saveTagContactWithOppertunity(OpportunityMapper opportunityMapper);

	// public HashMap getNoOfOpportunityBycreationDate() ;

	List<String> updateTransferOneUserToAnother(String userId, TransferMapper transferMapper);

	public ContactViewMapper updateContactRoleByContactId(String contactId, ContactViewMapper contactViewMapper);

	public OpportunityViewMapper updateCustomerByOpportunityId(String opportunityId,
			OpportunityViewMapper opportunityViewMapper);

	public List<OpportunitySkillLinkMapper> saveInnitiativeSkillAndNumber(OpportunityMapper opportunityMapper);

	public List<OpportunitySkillLinkMapper> getSkillAndNumberByOppotunityId(String opportunityId);

	public HashMap getDeleteHistoryCountList(String userId);

	public void updateOpportunityWonIndByOpportunityId(String opportunityId, OpportunityMapper opportunityMapper);

	public void updateOpportunityLostIndByOpportunityId(String opportunityId, OpportunityMapper opportunityMapper);

	public void updateOpportunityCloseIndByOpportunityId(String opportunityId, OpportunityMapper opportunityMapper);

	public List<OpportunityViewMapper> getOpportunityDetailByUserIdAndCloseInd(String userId, int pageNo, int pageSize);

	public List<OpportunityViewMapper> getOpportunityDetailByUserIdAndLostInd(String userId, int pageNo, int pageSize);

	public List<OpportunityViewMapper> getOpportunityDetailByUserIdAndWonInd(String userId, int pageNo, int pageSize);

	public OpportunityForecastLinkMapper saveForecast(OpportunityForecastLinkMapper opportunityMapper);

	public List<OpportunityForecastLinkMapper> getForecastSkillAndNumberByOppotunityId(String opportunityId);

	public List<OpportunityForecastLinkMapper> updateOpportunityForecastOpportunityId(String opportunityId,
			OpportunityMapper opportunityMapper);

	public HashMap getOpportunityListByCloseInd(String userId);

	public HashMap getOpportunityListByLostInd(String userId);

	public HashMap getOpportunityListByWonInd(String userId);

	public HashMap getOpenRecruitmentAndOpenPositionCountByOpportunityId(String opportunityId);

	public OpportunityViewMapper updateOpportunityReinstateIndOnlyTrue(String opportunityId);

	public OpportunityViewMapper updateOpportunityCloseIndOnlyTrue(String opportunityId);

	public OpportunityViewMapper updateStage(OpportunityViewMapper opportunityMapper);

	public Map<String, List<OpportunityForecastLinkMapper>> getforecastByOrgrIdMonthWise(String orgId);

	List<OpportunityViewMapper> getOpportunityDetailsListByUserId(String userId);

	List<OpportunityViewMapper> getAllOpportunityListCount();

	public HashMap getOpportunityContactCountByCustomerId(String opportunityId);

	public HashMap getOpportunityDocumentCountByCustomerId(String opportunityId);

	public HashMap getCloseOpportunityListByUserIdAndDateRange(String userId, String startDate, String endDate);

	public HashMap getAddedOpportunityListByUserIdAndDateRange(String userId, String startDate, String endDate);

	List<OpportunityViewMapper> getClosedOpportunitiesByUserIdDateRange(String userId, String startDate,
			String endDate);

	List<OpportunityViewMapper> getAddedOpportunitiesByUserIdDateRange(String userId, String startDate, String endDate);

	public List<OpportunityDropdownMapper> getDropDownOpportunityList(String userId);

	public NotesMapper updateNoteDetails(NotesMapper notesMapper);

	public void deleteCustomerNotesById(String notesId);

	public List<OpportunityViewMapper> getOpportunityDetailsListPageWiseByOrgId(String orgId, int pageNo, int pageSize);

	public Set<OpportunityViewMapper> getTeamOpportunityDetailsByUserId(String userId, int pageNo, int pageSize,
			String filter);

	public HashMap getTeamOppertunityContactCountByUserId(String userId);

	public DocumentMapper updateContractIndForDocument(DocumentMapper documentMapperr);

	public List<OpportunityOrderViewMapper> saveOpportunityOrder(OpportunityOrderMapper opportunityOrderMapper);

	public List<OpportunityOrderViewMapper> getOpportunityOrderListByOpportunityId(String opportunityId);

	public String deleteOpportunityOrder(String opportunityOrderId);

	public List<OpportunityViewMapper> getOpportunityDetailsListPageWiseByIncludedUserId(String userId, int pageNo,
			int pageSize);

	public HashMap getCountListByIncludedUserId(String userId);

	public HashMap getActionRequiredRecordByToday(String userId);

	ConversionValueMapper ConvertOppertunityProposalValueAndWeightedValue(String proposalValue, String userCurrency,
			String orgCurrency, String oppCurrency, double probability);

	public List<OpportunityReportMapper> getClosedOpportunitiesByOrgIdDateRangeforReport(String orgId, String startDate,
			String endDate);

	public List<OpportunityReportMapper> getClosedOpportunitiesByUserIdDateRangeforReport(String userId,
			String startDate, String endDate);

	public List<OpportunityReportMapper> getOpenOpportunitiesByOrgIdDateRangeforReport(String orgId, String startDate,
			String endDate);

	public List<OpportunityReportMapper> getOpenOpportunitiesByUserIdDateRangeforReport(String userId, String startDate,
			String endDate);

	public List<OpportunityReportMapper> getOpportunitiesByOrgIdDateRangeforReport(String orgId, String startDate,
			String endDate);

	public List<OpportunityReportMapper> getOpportunitiesByUserIdDateRangeforReport(String userId, String startDate,
			String endDate);

	public HashMap getOpportunityCountByCountry(String country);

	public List<OpportunityViewMapper> getOpportunityListByCountry(String country);

	public HashMap getOpenOpportunityCountByCountry(String country);

	public List<OpportunityViewMapper> getOpenOpportunityListByCountry(String country);

	public List<OpportunityViewMapper> getOpportunityYearlyListByCountry(String country);

	public HashMap getOpportunityYearlyCountByCountry(String country);

	public HashMap getOpportunityListCountByOrgId(String orgId);

	public Set<OpportunityViewMapper> getTeamOppDetailsByUnderUserId(String userId, int pageNo, int pageSize);

	public HashMap getTeamOppertunityCountByUnderUserId(String userId);

	public List<OpportunityViewMapper> getOpenOppListBycontactId(String contactId);

	public HashMap getOpenOppCountBycontactId(String contactId);

	public HashMap getWonOppCountBycontactId(String contactId);

	public List<OpportunityViewMapper> getWonOppListBycontactId(String contactId);

	public HashMap getOppProposalValueCountByContactId(String contactId, String userId, String orgId);

	public HashMap getOppWeigthedValueCountByContactId(String contactId, String userId, String orgId);

	public List<OpportunityDropdownMapper> getDropDownOpportunityListByCustomerId(String customerId);

	public List<OpportunityViewMapper> getOpportunityListByUserIdAndQuarterAndYear(String userId, String quarter,
			int year);

	List<OpportunityReportMapper> getWonOpportunitiesByUserIdDateRangeforReport(String userId, String startDate,
			String endDate);

	public List<OpportunityReportMapper> getLostOpportunitiesByUserIdDateRangeforReport(String userId, String startDate,
			String endDate);

	public List<OrganizationValueMapper> getMultyOrgWonOppValueByYearAndQuarterForDashBoard(String emailId, int year,
			String quarter, String userId, String orgId);

	public List<OpportunityViewMapper> getAllWonOppList(String orgId, int year, String quarter);

	public String linkOppToProduct(OpportunityProductMapper mapper);

	public List<OpportunityProductMapper> getOpportunityLinkProductListByOpportunityId(String opportunityId);

	public List<OpportunityViewMapper> getOpportunityDetailsByNameAndTypeOrgLevel(String name, String orgId);

	public List<OpportunityViewMapper> getOpportunityDetailsByNameAndTypeForTeam(String name, String userId);

	public List<OpportunityViewMapper> getOpportunityDetailsByNameAndTypeAndUserId(String name, String userId);

	public List<OpportunityViewMapper> getOpportunityDetailsByNewOppIdAndTypeOrgLevel(String name, String orgId);

	public List<OpportunityViewMapper> getOpportunityDetailsByNewOppIdAndTypeForTeam(String name, String userId);

	public List<OpportunityViewMapper> getOpportunityDetailsByNewOppIdAndTypeAndUserId(String name,	String userId);

}
