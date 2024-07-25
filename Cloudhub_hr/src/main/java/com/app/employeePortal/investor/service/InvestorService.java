package com.app.employeePortal.investor.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.app.employeePortal.contact.mapper.ContactMapper;
import com.app.employeePortal.contact.mapper.ContactReportMapper;
import com.app.employeePortal.contact.mapper.ContactViewMapper;
import com.app.employeePortal.customer.mapper.TransferMapper;
import com.app.employeePortal.document.mapper.DocumentMapper;
import com.app.employeePortal.employee.mapper.NotesMapper;
import com.app.employeePortal.investor.mapper.*;
import com.app.employeePortal.leads.mapper.ActivityMapper;

import freemarker.template.TemplateException;

public interface InvestorService {
	
    InvestorViewMapper saveInvestor(InvestorMapper investorMapper) throws IOException, TemplateException;

    InvestorViewMapper getInvestorDetailsById(String investorId);

    List<InvestorViewMapper> getInvestorsPageWiseByUserId(String userId, int pageNo, int pageSize);

//    List<InvestorViewMapper> getAllInvestorList(int pageNo, int pageSize);

    InvestorViewMapper updateInvestor(String investorId, InvestorMapper investorMapper) throws IOException, TemplateException;

    List<ContactViewMapper> getContactListByInvestorId(String investorId);

    List<InvestorViewMapper> getAllInvestor();

    List<NotesMapper> getNoteListByInvestorId(String investorId);

    String saveInvestorNotes(NotesMapper notesMapper);

    ContactViewMapper saveInvestorContact(ContactMapper contactMapper) throws IOException, TemplateException;

    List<DocumentMapper> getDocumentListByInvestorId(String investorId);

    List<InvestorViewMapper> getInvestorListByName(String name);

    List<InvestorViewMapper> getInvestors(int pageNo, int pageSize);

    HashMap getCountListByuserId(String userId);

    HashMap getCountNoOfContactByUserId(String userId);

    InvestorKeySkillMapper getKeySkilListByInvestorIdAndDateRange(String investorId, String orgId, String startDate, String endDate);

    List<String> getSkillSetOfSkillLibery(String orgId);

    String saveinvoiceInvestor(InvestorInvoiceMapper customerInvoiceMapper);

    List<InvestorInvoiceMapper> getInvoiceListByInvestorId(String investorId);

    boolean checkSkillInInvestorSkillSet(String skillName, String investorId);

    String saveSkillSet(InvestorSkillLinkMapper investorSkillLinkMapper);

    List<InvestorSkillLinkMapper> getSkillSetByInvestorId(String investorId);

    List<InvestorViewMapperForDropDown> getInvestorByUserIdForDropDown(String userId);

    HashMap getInvestorContactCountByInvestorId(String customerId);

    HashMap getDocumentCountByInvestorId(String investorId);

    List<String> updateTransferOneUserToAnother(String userId, TransferMapper transferMapper);

    List<Map<String, Double>> getInvestorCountSourceWiseByUserId(String userId, String orgId);

    List<Map<String, List<InvestorViewMapper>>> getInvestorListSourceWiseByUserId(String userId, String orgId);

	List<InvestorViewMapper> getFilterInvestorsPageWiseByUserId(String userId, int pageNo, int pageSize, String filter);

	List<InvestorViewMapper> getInvestorBySector(String name);

	List<InvestorViewMapper> getInvestorByOwnerName(String name);

//	NotesMapper updateNoteDetails(NotesMapper notesMapper);

	void deleteInvestorNotesById(String notesId);

	List<ActivityMapper> getActivityListByInvestorId(String investorId);

	List<InvestorViewMapper> getInvestorListByOrgId(String orgId, int pageNo, int pageSize, String filter);

//	List<ContactViewMapper> getInvestorContactListByOrgId(String orgId, int pageNo, int pageSize, String filter);

//	Set<InvestorViewMapper> getTeamInvestorDetailsByUserId(String userId, int pageNo, int pageSize, String filter);
//
//	Set<ContactViewMapper> getTeamInvestorContactListByUserId(String userId, int pageNo, int pageSize, String filter);

	HashMap getTeamInvestorCountByUserId(String userId);

	HashMap getTeamInvestorContactCountByUserId(String userId);

	boolean investorByUrl(String url);

	List<InvestorReportMapper> getAllInvestorByOrgIdForReport(String orgId, String startDate, String endDate);

	List<InvestorReportMapper> getAllInvestorByUserIdForReport(String userId, String startDate, String endDate);

	List<ContactReportMapper> getAllInvestorContactListByOrgIdForReport(String orgId, String startDate, String endDate);

	List<ContactReportMapper> getAllInvestorContactListByUserIdForReport(String userId, String startDate,
			String endDate);

	HashMap getActivityRecordByInvestorId(String investorId);

	HashMap getInvestorOpportunityCountByInvestorId(String investorId);

	HashMap getInvestorOppWeigthedValueCountByInvestorId(String investorId,String userId,String orgId);

	HashMap getInvestorWonOpportunityCountByInvestorId(String investorId);

	HashMap getInvestorOppProposalValueCountByInvestorId(String investorId,String userId,String orgId);

	HashMap getInvestorWonOppProposalValueCountByInvestorId(String customerId, String userId, String orgId);

	HashMap getInvestorWonOppWeigthedValueCountByInvestorId(String investorId, String userId, String orgId);

	InvestorOpportunityMapper saveInvestorOpportunity(InvestorOpportunityMapper opportunityMapper) throws TemplateException, IOException;

	HashMap getInvestorCountByCountry(String country);

	List<InvestorViewMapper> getInvestorListByCountry(String country);

	Set<InvestorViewMapper> getTeamInvestorListByUnderUserId(String userId, int pageNo, int pageSize);

	HashMap getTeamInvestorCountByUnderUserId(String userId);

	Set<ContactViewMapper> getTeamInvestorContactListByUnderUserId(String userId, int pageNo, int pageSize);

	HashMap getTeamInvestorContactCountByUnderUserId(String userId);

	HashMap getInvestorCountByOrgId(String orgId);

	List<InvestorOpportunityMapper> getWonOpportunityListByInvestorId(String investorId);

	void deleteInvestorById(String investorId);

	List<ContactViewMapper> getDeletedInvestorContactList(String userId);

	InvestorShareMapper saveShare(InvestorShareMapper investorShareMapper) throws IOException, TemplateException;

	InvestorShareMapper getShareByInvestorShareId(String investorsShareId);

	List<InvestorShareMapper> getAllInvestorShareList(String investorId);

	InvestorShareMapper getAllInvestorShare(String investorId);

	void deleteInvestorShareByInvestorShareId(String investorShareId, String userId);

	InvestorShareMapper updateInvestorShare(InvestorShareMapper investorShareMapper) throws IOException, TemplateException;

    Object createAndUpdateInvestorDocType(InvestorDocTypeMapper investorDocTypeMapper);

	Object getDocTypesByInvestor(String investorId, String userId, String orgId);

	List<InvestorViewMapper> getInvestorDetailsByNameInOrgLevel(String name, String orgId);

	List<InvestorViewMapper> getInvestorDetailsByNameForTeam(String name, String userId);

	List<InvestorViewMapper> getInvestorDetailsByNameInUserLevel(String name, String userId);

	List<InvestorViewMapper> getInvestorBySectorInOrgLevel(String name, String orgId);

	List<InvestorViewMapper> getInvestorBySourceInOrgLevel(String name, String orgId);

	List<InvestorViewMapper> getInvestorByOwnerNameInOrgLevel(String name, String orgId);

	List<InvestorViewMapper> getInvestorByClubInOrgLevel(String name, String orgId);

	List<InvestorViewMapper> getInvestorBySectorInUserLevel(String name, String userId, String orgId);

	List<InvestorViewMapper> getInvestorBySourceInUserLevel(String name, String userId, String orgId);

	List<InvestorViewMapper> getInvestorByOwnerNameInUserLevel(String name, String userId, String orgId);

	List<InvestorViewMapper> getInvestorByClubInUserLevel(String name, String userId, String orgId);

	List<InvestorViewMapper> getInvestorBySectorForTeam(String name, String userId, String orgId);

	List<InvestorViewMapper> getInvestorBySourceForTeam(String name, String userId, String orgId);

	List<InvestorViewMapper> getInvestorByOwnerNameForTeam(String name, String userId, String orgId);

	List<InvestorViewMapper> getInvestorByClubInForTeam(String name, String userId, String orgId);

	List<InvestorViewMapper> getInvestorListByOrgIdAndClubType(String orgId, int pageNo, int pageSize, String clubType);

	Set<InvestorViewMapper> getTeamInvestorListByUnderUserIdAndClubType(String loggedUserId, int pageNo, int pageSize,
			String clubType);

	List<InvestorViewMapper> getFilterInvestorsPageWiseByUserIdAndClubType(String userId, int pageNo, int pageSize,
			String clubType);
}
