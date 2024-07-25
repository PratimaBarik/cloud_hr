package com.app.employeePortal.investorleads.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.app.employeePortal.contact.mapper.ContactViewMapper;
import com.app.employeePortal.customer.mapper.TransferMapper;
import com.app.employeePortal.document.mapper.DocumentMapper;
import com.app.employeePortal.employee.mapper.NotesMapper;
import com.app.employeePortal.investorleads.mapper.InvestorLeadsMapper;
import com.app.employeePortal.investorleads.mapper.InvestorLeadsOpportunityMapper;
import com.app.employeePortal.investorleads.mapper.InvestorLeadsReportMapper;
import com.app.employeePortal.investorleads.mapper.InvestorLeadsViewMapper;
import com.app.employeePortal.leads.mapper.ActivityMapper;

import freemarker.template.TemplateException;

public interface InvestorLeadsService {

	public InvestorLeadsViewMapper saveInvestorLeads(InvestorLeadsMapper investorleadsMapper)
			throws IOException, TemplateException;

	public InvestorLeadsViewMapper getInvestorLeadsDetailsById(String investorleadsId);

	public InvestorLeadsViewMapper updateLeads(String investorleadsId, InvestorLeadsMapper investorleadsMapper)
			throws IOException, TemplateException;

	public void deleteInvestorLeads(String investorleadsId, String userId, String orgId)
			throws IOException, TemplateException;

	public List<ContactViewMapper> getContactListByInvestorLeadsId(String investorLeadsId);

	public List<DocumentMapper> getDocumentListByInvestorLeadsId(String investorLeadsId);

	public void deleteDocumentsById(String documentId);

	public InvestorLeadsOpportunityMapper saveInvestorLeadsOpportunity(
			InvestorLeadsOpportunityMapper investorLeadsOpportunityMapper);

	public List<InvestorLeadsOpportunityMapper> getOpportunityListByInvestorLeadsId(String investorLeadsId);

	public InvestorLeadsOpportunityMapper updateInvestorLeadsOpportunity(String investorLeadsOppId,
			InvestorLeadsOpportunityMapper investorLeadsOpportunityMapper);

	public void deleteLeadsOppertunity(String investorLeadOppId);

	public String updateInvestorLeadsType(String investorLeadsId, String type);

	public String convertInvestorLeadsById(String investorLeadsId, String assignedToId)
			throws TemplateException, IOException;

	public HashMap getCountListByuserId(String userId);

	public Map getQualifiedInvestorLeadsListCountByUserId(String userId, String startDate, String endDate);

	public List<InvestorLeadsViewMapper> getQualifiedInvestorLeadsDetailsByUserId(String userId, String startDate,
			String endDate);

	public String ReinstateInvestorLeadsToJunk(String userId, TransferMapper transferMapper);

	public List<InvestorLeadsViewMapper> getCreatededInvestorLeadsDetailsByUserId(String userId, String startDate,
			String endDate);

	public Map getInvestorCreatededLeadsListCountByUserId(String userId, String startDate, String endDate);

	public Map getJunkedInvestorLeadsCountByUserId(String userId);

	public List<InvestorLeadsViewMapper> getJunkedInvestorLeadsListByUserId(String userId);

	public List<InvestorLeadsViewMapper> getJunkedInvestorLeadsDetailsByUserId(String userId, String startDate,
			String endDate);

	public Map getJunkedInvestorLeadsListCountByUserId(String userId, String startDate, String endDate);

	// public List<String> transferInvestorLeadsOneUserToAnother(InvestorLeadsMapper
	// investorLeadsMapper);

	public Map getInvestorLeadsListCountByUserIdAndtypeAndDateRange(String userId, String startDate, String endDate);

	public List<Map<String, Double>> getAddedInvestorLeadsListCountByUserIdWithDateWise(String userId, String startDate,
			String endDate);

	public List<Map<String, Double>> getInvestorLeadsCountSourceWiseByUserId(String userId, String orgId);

	public List<Map<String, List<InvestorLeadsViewMapper>>> getInvestorLeadsListSourceWiseByUserId(String userId,
			String orgId);

	List<InvestorLeadsViewMapper> getInvestorLeadsHotByUserIdAndTypeAndDateRange(String userId, String startDate,
			String endDate);

	List<InvestorLeadsViewMapper> getInvestorLeadsColdByUserIdAndTypeAndDateRange(String userId, String startDate,
			String endDate);

	List<InvestorLeadsViewMapper> getInvestorLeadsWarmByUserIdAndTypeAndDateRange(String userId, String startDate,
			String endDate);

	public List<ActivityMapper> getActivityListByInvestorLeadsId(String investorleadsId);

	public List<InvestorLeadsViewMapper> getInvestorLeadsListByName(String name);

	public List<InvestorLeadsViewMapper> getInvestorLeadsBySector(String name);

	public List<InvestorLeadsViewMapper> getInvestorLeadsByOwnerName(String name);

	public String saveInvestorLeadsNotes(NotesMapper notesMapper);

	public List<NotesMapper> getNoteListByInvestorLeadsId(String investorLeadsId);

//	public NotesMapper updateNoteDetails(String notesId, NotesMapper notesMapper);

	public void deleteInvestorLeadsNotesById(String notesId);

	public List<InvestorLeadsViewMapper> getInvestorLeadsDetailsByUserId(String userId, int pageNo, int pageSize,
			String filter);

	List<InvestorLeadsViewMapper> getInvestorLeadsListByOrgId(String orgId, int pageNo, int pageSize, String filter);

	Set<InvestorLeadsViewMapper> getTeamInvestorLeadsDetailsByUserId(String userId, int pageNo, int pageSize,
			String filter);

	public List<String> transferInvestorLeadsOneUserToAnother(String userId, TransferMapper transferMapper);

	public HashMap getActivityRecordByInvestorLeadsId(String investorLeadsId);

	public HashMap getInvestorLeadsAllCount(String orgId);

	public List<InvestorLeadsReportMapper> getAllInvestorLeadsByOrgIdForReport(String orgId, String startDate,
			String endDate);

	public List<InvestorLeadsReportMapper> getAllInvestorLeadsByUserIdForReport(String userId, String startDate,
			String endDate);

	public List<InvestorLeadsReportMapper> getAllInvestorQualifiedLeadsListByOrgIdForReport(String orgId,
			String startDate, String endDate);

	public boolean getInvestorLeadsByUrl(String url);

	public boolean getInvestorLeadsByEmail(String email);

	public Set<InvestorLeadsViewMapper> getTeamInvestorLeadsDetailsUnderAyUserId(String userId, int pageNo,
			int pageSize);

	public HashMap getTeamInvestorLeadsCountUnderAyUserId(String userId);

	public HashMap getInvestorLeadsCountByOrgId(String orgId);

	public List<InvestorLeadsViewMapper> getInvLeadsDetailsByUserIdAndType(String userId, int pageNo, int pageSize,
			String filter, String type);

	public List<InvestorLeadsViewMapper> getTeamInvLeadsDetailsByUnderAUserId(String userId, int pageNo, int pageSize,
			String type);

	public List<InvestorLeadsViewMapper> getInvLeadsListByOrgId(String orgId, int pageNo, int pageSize, String filter,
			String type);

	public void deletePitchUserLevel(String userId);

	public List<InvestorLeadsViewMapper> getInvLeadsDetailsByNameByOrgLevel(String name, String orgId);

	public List<InvestorLeadsViewMapper> getInvLeadsDetailsBySectorInOrgLevel(String name, String orgId);

	public List<InvestorLeadsViewMapper> getInvLeadsDetailsBySourceInOrgLevel(String name, String orgId);

	public List<InvestorLeadsViewMapper> getInvLeadsDetailsByOwnerNameInOrgLevel(String name, String orgId);

	public List<InvestorLeadsViewMapper> getInvLeadsDetailsByNameForTeam(String name, String userId);

	public List<InvestorLeadsViewMapper> getInvLeadsDetailsBySectorForTeam(String name, String userId, String orgId);

	public List<InvestorLeadsViewMapper> getInvLeadsDetailsBySourceForTeam(String name, String userId, String orgId);

	public List<InvestorLeadsViewMapper> getInvLeadsDetailsByOwnerNameForTeam(String name, String userId, String orgId);

	public List<InvestorLeadsViewMapper> getInvLeadsDetailsByNameByUserId(String name, String userId);

	public List<InvestorLeadsViewMapper> getInvLeadsBySectorInUserLevel(String name, String userId, String orgId);

	public List<InvestorLeadsViewMapper> getInvLeadsBySourceInUserLevel(String name, String userId, String orgId);

	public List<InvestorLeadsViewMapper> getInvLeadsByOwnerNameInUserLevel(String name, String userId, String orgId);
}
