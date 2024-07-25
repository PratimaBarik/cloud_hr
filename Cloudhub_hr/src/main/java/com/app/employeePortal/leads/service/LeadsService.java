package com.app.employeePortal.leads.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.app.employeePortal.contact.mapper.ContactMapper;
import com.app.employeePortal.contact.mapper.ContactViewMapper;
import com.app.employeePortal.customer.mapper.CustomerResponseMapper;
import com.app.employeePortal.customer.mapper.InitiativeDetailsMapper;
import com.app.employeePortal.customer.mapper.TransferMapper;
import com.app.employeePortal.document.mapper.DocumentMapper;
import com.app.employeePortal.employee.mapper.NotesMapper;
import com.app.employeePortal.leads.mapper.ActivityMapper;
import com.app.employeePortal.leads.mapper.LeadsMapper;
import com.app.employeePortal.leads.mapper.LeadsOpportunityMapper;
import com.app.employeePortal.leads.mapper.LeadsReportMapper;
import com.app.employeePortal.leads.mapper.LeadsSkillLinkMapper;
import com.app.employeePortal.leads.mapper.LeadsViewMapper;

import freemarker.template.TemplateException;

public interface LeadsService {

	public LeadsViewMapper saveLeads(LeadsMapper leadsMapper) throws IOException, TemplateException;

	public LeadsViewMapper getLeadsDetailsById(String leadsId);

	public LeadsViewMapper updateLeads(String leadsId, LeadsMapper leadsMapper) throws IOException, TemplateException;

	public void deleteLeads(String leadsId) throws IOException, TemplateException;

	public String convertLeadsById(String leadsId, String assignedToId);

	public List<LeadsViewMapper> getLeadsDetailsByUserId(String userId);

	public String saveLeadsNotes(NotesMapper notesMapper);

	public List<NotesMapper> getNoteListByLeadsId(String leadsId);

	public List<DocumentMapper> getDocumentListByLeadsId(String leadsId);

	public ContactViewMapper saveleadsContact(ContactMapper contactMapper) throws IOException, TemplateException;

	public List<ContactViewMapper> getContactListByLeadsId(String leadsId);

	public void deleteDocumentsById(String leadsId);

	public LeadsOpportunityMapper saveleadsOpportunity(LeadsOpportunityMapper leadsOpportunityMapper);

	public List<LeadsOpportunityMapper> getOpportunityListByLeadsId(String leadsId);

	public String saveLeadsInitiative(InitiativeDetailsMapper initiativeDetailsMapper);

	public List<InitiativeDetailsMapper> getInitiativeListByLeadsId(String leadsId);

	public InitiativeDetailsMapper updateInitiativeDetails(String initiativeDetailsId,
			InitiativeDetailsMapper initiativeDetailsMapper);

	public LeadsOpportunityMapper updateLeadsOpportunity(String opportunityId,
			LeadsOpportunityMapper leadsOpportunityMapper);

	public ContactViewMapper updateContactDetails(String contactId, ContactMapper contactMapper) throws IOException, TemplateException;

//	public NotesMapper updateNoteDetails(String notesId, NotesMapper notesMapper);

	public String saveLeadsSkillSet(LeadsSkillLinkMapper leadsSkillLinkMapper);

	public List<LeadsSkillLinkMapper> getSkillSetByLeadsId(String leadsId);

	public LeadsSkillLinkMapper deleteSkillsSet(String leadsSkillLinkId);

	public List<LeadsViewMapper> getLeadsDetailsByName(String name);

	public List<LeadsViewMapper> getLeadsDetailsBySector(String name);

	public List<LeadsViewMapper> getLeadsDetailsByOwnerName(String name);

	public ByteArrayInputStream exportLeadsListToExcel(List<LeadsViewMapper> leadsList);

	public List<LeadsViewMapper> getLeadsDetailsByOrgId(String orgId);

	public String updateLeadsType(String leadsId, String type);

	public HashMap getCountListByuserId(String userId);

	public List<LeadsViewMapper> getQualifiedLeadsDetailsByUserId(String userId, String startDate, String endDate);

	public HashMap getQualifiedLeadsListCountByUserId(String userId, String startDate, String endDate);

	public String ReinstateLeadToJunk(String userId, TransferMapper transferMapper);

	public Map getQualifiedLeadsListCountByOrgId(String orgId, String startDate, String endDate);

	public List<LeadsViewMapper> getQualifiedLeadsDetailsByOrgId(String orgId, String startDate, String endDate);

	public Map getCreatededLeadsListCountByUserId(String userId, String startDate, String endDate);

	public List<LeadsViewMapper> getCreatededLeadsDetailsByUserId(String userId, String startDate, String endDate);

	public Map getCreatededLeadsListCountByOrgId(String orgId, String startDate, String endDate);

	public List<LeadsViewMapper> getCreatededLeadsDetailsByOrgId(String orgId, String startDate, String endDate);

	public Map getJunkedLeadsCountByUserId(String userId);

	public List<LeadsViewMapper> getJunkedLeadsListByUserId(String userId);

	public Map getJunkedLeadsCountByOrgId(String orgId);

	public List<LeadsViewMapper> getJunkedLeadsListByOrgId(String orgId);

	public List<LeadsViewMapper> getJunkedLeadsDetailsByOrgId(String orgId, String startDate, String endDate);

	public Map getJunkedLeadsListCountByOrgId(String orgId, String startDate, String endDate);

	public List<LeadsViewMapper> getJunkedLeadsDetailsByUserId(String userId, String startDate, String endDate);

	public Map getJunkedLeadsListCountByUserId(String userId, String startDate, String endDate);

	public String deleteLeadsOppertunity(String leadOppId);

	public List<LeadsViewMapper> getHotListByUserId(String userId);

	public Map getHotListCountByUserId(String userId);

	public Map getWormListCountByUserId(String userId);

	public List<String> transferLeadsOneUserToAnother(String userid, TransferMapper transferMapper);

	List<LeadsViewMapper> getWarmListByUserId(String userId);

	public Map getColdListCountByUserId(String userId);

	public List<LeadsViewMapper> getcoldListByUserId(String userId);

	public Map getLeadsListCountByUserIdAndtype(String userId);

	public List<Integer> getLeadsListCountByUserIdAndtypeAndDateRange(String userId, String startDate, String endDate);

	public Map getLeadsListCountByOrgIdAndtype(String orgId);

	public Map getLeadsListCountByOrgIdAndtypeAndDateRange(String orgId, String startDate, String endDate);

	public List<Map<String,Double>> getAddedLeadsListCountByUserIdWithDateWise(String userId, String startDate, String endDate);

	public List<Map<String, Double>> getLeadsCountSourceWiseByUserId(String userId, String orgId);

	public List<Map<String, List<LeadsViewMapper>>> getLeadsListSourceWiseByUserId(String userId, String orgId);

	public List<LeadsViewMapper> getHotListByUserIdAndDateRange(String userId, String startDate, String endDate);

	public List<LeadsViewMapper> getWarmListByUserIdAndDateRange(String userId, String startDate, String endDate);

	public List<LeadsViewMapper> getcoldListByUserIdAndDateRange(String userId, String startDate, String endDate);

	public List<LeadsViewMapper> getLeadsDetailsByContactName(String name);

	public List<ActivityMapper> getActivityListByLeadsId(String leadsId);

	public void deleteLeadsNotesById(String notesId);

	List<LeadsViewMapper> getLeadsDetailsByUserIdPagging(String userId, int pageNo, int pageSize, String filter);

	public List<LeadsViewMapper> getLeadsListByOrgId(String orgId, int pageNo, int pageSize, String filter, String type);

	public Set<LeadsViewMapper> getTeamLeadsDetailsByUserId(String userId, int pageNo, int pageSize, String filter);

	String saveLeadsTroughWebsite(LeadsMapper leadsMapper) throws IOException, TemplateException;

	public HashMap getTeamLeadsCountByUserId(String userId);

	public HashMap getActivityRecordByLeadsId(String leadsId);

	public boolean getLeadsByUrl(String url);

	public List<LeadsReportMapper> getAllLeadsByOrgIdForReport(String orgId, String startDate, String endDate);

	public List<LeadsReportMapper> getAllLeadsByUserIdForReport(String userId, String startDate, String endDate);

	public List<LeadsReportMapper> getQualifiedLeadsDetailsForReportByUserId(String userId, String startDate,
			String endDate);

	public List<LeadsReportMapper> getQualifiedLeadsDetailsForReportByOrgId(String orgId, String startDate,
			String endDate);

	public boolean getLeadsByEmail(String email);

	public List<LeadsViewMapper> getTeamLeadsDetailsByUnderAUserId(String userId, int pageNo, int pageSize, String type);

	public HashMap getTeamLeadsCountByUnderAUserId(String userId);

	public HashMap getLeadsListCountByOrgId(String orgId);

	public List<LeadsViewMapper> getLeadsDetailsByUserIdAndType(String userId, int pageNo, int pageSize, String filter,
			String type);

	public List<LeadsViewMapper> getLeadsDetailsByNameByOrgLevel(String name, String orgId);

	public List<LeadsViewMapper> getLeadsDetailsByNameForTeam(String name, String userId);

	public List<LeadsViewMapper> getLeadsDetailsByNameByUserId(String name, String userId);

	public List<LeadsViewMapper> getLeadsDetailsBySectorInOrgLevel(String name, String orgId);

	public List<LeadsViewMapper> getLeadsDetailsBySourceInOrgLevel(String name, String orgId);

	public List<LeadsViewMapper> getLeadsDetailsByOwnerNameInOrgLevel(String name, String orgId);

	public List<LeadsViewMapper> getLeadsDetailsBySectorForTeam(String name, String userId, String orgId);

	public List<LeadsViewMapper> getLeadsDetailsBySourceForTeam(String name, String userId, String orgId);

	public List<LeadsViewMapper> getLeadsDetailsByOwnerNameForTeam(String name, String userId, String orgId);

	public List<LeadsViewMapper> getLeadsBySectorInUserLevel(String name, String userId, String orgId);

	public List<LeadsViewMapper> getLeadsBySourceInUserLevel(String name, String userId, String orgId);

	public List<LeadsViewMapper> getLeadsByOwnerNameInUserLevel(String name, String userId, String orgId);




}
