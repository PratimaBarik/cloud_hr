package com.app.employeePortal.investor.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.app.employeePortal.Opportunity.mapper.OpportunityViewMapper;
import com.app.employeePortal.contact.mapper.ContactMapper;
import com.app.employeePortal.contact.mapper.ContactViewMapper;
import com.app.employeePortal.customer.mapper.TransferMapper;
import com.app.employeePortal.document.mapper.DocumentMapper;
import com.app.employeePortal.employee.mapper.NotesMapper;
import com.app.employeePortal.investor.mapper.InvestorOppFundRequest;
import com.app.employeePortal.investor.mapper.InvestorOppFundResponse;
import com.app.employeePortal.investor.mapper.InvestorOppReportMapper;
import com.app.employeePortal.investor.mapper.InvestorOpportunityMapper;

import freemarker.template.TemplateException;

public interface InvestorOppService {

	InvestorOpportunityMapper saveInvestorOpportunity(InvestorOpportunityMapper opportunityMapper)
			throws TemplateException, IOException;

	InvestorOpportunityMapper getOpportunityDetails(String invOpportunityId);

	List<InvestorOpportunityMapper> getAllOpportunityList(int pageNo, int pageSize);

	List<InvestorOpportunityMapper> getOpportunityDetailsListPageWiseByUserId(String userId, int pageNo, int pageSize);

	InvestorOpportunityMapper updateInvOpportunityDetails(String invOpportunityId,
			InvestorOpportunityMapper opportunityMapper) throws TemplateException, IOException;

	String saveInvOpportunityNotes(NotesMapper notesMapper);

	List<NotesMapper> getNoteListByInvOpportunityId(String invOpportunityId);

	NotesMapper getNotes(String id);

	List<InvestorOpportunityMapper> getInvOpportunityDetailsListByContactId(String contactId);

	List<DocumentMapper> getDocumentDetailsListByinvOpportunityId(String invOpportunityId);

	List<InvestorOpportunityMapper> getInvOpportunityDetailsByName(String opportunityName);

	ContactViewMapper saveInvOpportunityContact(ContactMapper contactMapper) throws IOException, TemplateException;

	List<ContactViewMapper> getContactListByInvOpportunityId(String invOpportunityId);

	List<InvestorOpportunityMapper> getInvOpportunityOfASalesUser(String userId);

	HashMap getInvestorOpportunityCountByUserId(String userId);

//    List<InvestorOpportunityMapper> getInvOpprtunityByRecruiterId(String userId);

	List<InvestorOpportunityMapper> getInvOpportunityDetailsListByUserId(String userId);

	HashMap getCloseOpportunityCountByUserIdAndDateRange(String userId, String startDate, String endDate);

	HashMap getAddedOpportunityCountByUserIdAndDateRange(String userId, String startDate, String endDate);

	List<InvestorOpportunityMapper> getOpenOpportunitiesByUserIdDateRange(String userId, String startDate,
			String endDate);

	List<InvestorOpportunityMapper> getClosedOpportunitiesByUserIdDateRange(String userId, String startDate,
			String endDate);

	void deleteOpportunityDetailsById(String id) throws TemplateException, IOException;

	void updateOpportunityWonIndByInvOpportunityId(String invOpportunityId,
			InvestorOpportunityMapper opportunityMapper) throws Exception;

	void updateOpportunityLostIndByInOpportunityId(String inOpportunityId, InvestorOpportunityMapper opportunityMapper);

	void updateOpportunityCloseIndByInOpportunityId(String inOpportunityId,
			InvestorOpportunityMapper opportunityMapper);

	void reinstateOpportunityByOppId(String inOpportunityId);

	Map getDeleteCountList(String userId);

	List<InvestorOpportunityMapper> getOpportunityDetailByUserIdAndCloseInd(String userId, int pageNo, int pageSize);

	Map getOpportunityListByCloseInd(String userId);

	Map getOpportunityListByLostInd(String userId);

	List<InvestorOpportunityMapper> getOpportunityDetailByUserIdAndLostInd(String userId, int pageNo, int pageSize);

	List<InvestorOpportunityMapper> getOpportunityDetailByUserIdAndWonInd(String userId, int pageNo, int pageSize);

	Map getOpportunityListByWonInd(String userId);

	List<InvestorOpportunityMapper> getOpportunityDetailByUserIdAndDeleteInd(String userId, int pageNo, int pageSize);

	InvestorOpportunityMapper updateStage(InvestorOpportunityMapper opportunityMapper);

//	NotesMapper updateNoteDetails(NotesMapper notesMapper);

	void deleteInvestorOpportunityNotesById(String notesId);

	List<InvestorOpportunityMapper> getOpportunityDetailsListPageWiseByOrgId(String orgId, int pageNo, int pageSize);

	List<InvestorOpportunityMapper> getInvestorOpportunitByInvOppWorkFlowId(String investorOppWorkflowId);

	Set<InvestorOpportunityMapper> getTeamInnvestorOppDetailsByUserId(String userId, int pageNo, int pageSize,
			String filter);

	HashMap getTeamInvestorOppContactCountByUserId(String userId);

	List<String> updateTransferOneUserToAnother(String userId, TransferMapper transferMapper);

	List<InvestorOpportunityMapper> getInvestorOppByinvestorId(String investorId);

	List<InvestorOpportunityMapper> getInvestorOppDetailsListPageWiseByIncludedUserId(String userId, int pageNo,
			int pageSize);

	HashMap getCountListByIncludedUserId(String userId);

	void deleteDocumentById(String documentId);

	List<InvestorOppReportMapper> getClosedOpportunitiesByOrgIdDateRange(String orgId, String startDate,
			String endDate);

	List<InvestorOppReportMapper> getOpenOpportunitiesByOrgIdDateRange(String orgId, String startDate, String endDate);

	List<InvestorOppReportMapper> getOpportunitiesByOrgIdDateRange(String orgId, String startDate, String endDate);

	List<InvestorOppReportMapper> getOpportunitiesByUserIdDateRange(String userId, String startDate, String endDate);

	Set<InvestorOpportunityMapper> getTeamInnvestorOppDetailsByUnderAUserId(String userId, int pageNo, int pageSize);

	HashMap getTeamInvestorOppContactCountByUnderAUserId(String userId);

	HashMap getInvestorOpportunityCountByOrgId(String orgId);

	HashMap getOpenInvOppCountByContactId(String contactId);

	List<InvestorOpportunityMapper> getOpenInvOppListBycontactId(String contactId);

	HashMap getWonInvOppCountBycontactId(String contactId);

	List<InvestorOpportunityMapper> getWonInvOppListBycontactId(String contactId);

	HashMap getInvestorOppProposalValueCountByContactId(String contactId, String userId, String orgId);

	HashMap getInvestorOppWeigthedValueCountByContactId(String contactId, String userId, String orgId);

	HashMap getInvestorOppCountByCountry(String country);

	List<InvestorOpportunityMapper> getInvestorOppListByCountry(String country);

	HashMap getOpenInvestorOppCountByCountry(String country);

	List<InvestorOpportunityMapper> getOpenInvestorOppListByCountry(String country);

	HashMap getInvestorOppYearlyCountByCountry(String country);

	List<InvestorOpportunityMapper> getInvestorOppYearlyListByCountry(String country);

	List<InvestorOppFundResponse> getInvestorOppFundByInvOpportunityId(String invOpportunityId, String userId);

	InvestorOppFundResponse updateInvestorOppFund(InvestorOppFundRequest requestBody);

	InvestorOppFundResponse updateInvestorOppFundToggle(InvestorOppFundRequest requestBody);

	List<InvestorOpportunityMapper> getDeletedInnOpportunityDetails(String loggeduserId, int pageNo, int pageSize);

    List<InvestorOpportunityMapper> getInvestorOppByNameByOrgLevel(String name, String orgId);

	List<InvestorOpportunityMapper> getInvestorOppBByNameForTeam(String name, String userId);

	List<InvestorOpportunityMapper> getInvestorOppBByNameByUserIdl(String name, String userId);

	List<InvestorOpportunityMapper> getOpportunityDetailsByNewOppIdAndTypeOrgLevel(String name, String orgId);

	List<InvestorOpportunityMapper> getOpportunityDetailsByNewOppIdAndTypeForTeam(String name, String userId);

	List<InvestorOpportunityMapper> getOpportunityDetailsByNewOppIdAndTypeAndUserId(String name, String userId);
}
