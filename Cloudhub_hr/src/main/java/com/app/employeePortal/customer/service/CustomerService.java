package com.app.employeePortal.customer.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.app.employeePortal.Opportunity.mapper.OpportunityMapper;
import com.app.employeePortal.Opportunity.mapper.OpportunityViewMapper;
import com.app.employeePortal.category.mapper.MinimumActivityRespMapper;
import com.app.employeePortal.contact.mapper.ContactMapper;
import com.app.employeePortal.contact.mapper.ContactReportMapper;
import com.app.employeePortal.contact.mapper.ContactViewMapper;
import com.app.employeePortal.contact.mapper.ContactViewMapperForDropDown;
import com.app.employeePortal.customer.mapper.*;
import com.app.employeePortal.document.mapper.DocumentMapper;
import com.app.employeePortal.employee.mapper.NotesMapper;
import com.app.employeePortal.leads.mapper.ActivityMapper;

import freemarker.template.TemplateException;

public interface CustomerService {

	public CustomerResponseMapper saveCustomer(CustomerMapper customerMapper) throws IOException, TemplateException;

	public CustomerResponseMapper getCustomerDetailsById(String customerId);

	public List<CustomerResponseMapper> getCustomerDetailsPageWiseByuserId(String userId, int pageNo, int pageSize);
	
	public List<NotesMapper> getNoteListByCustomerId(String customerId);

	public NotesMapper getNotes(String id);

	public List<ContactViewMapper> getContactListByCustomerId(String customerId);
	
	public CustomerResponseMapper updateCustomer(String customerId,CustomerMapper customerMapper) throws IOException, TemplateException;

	public String saveCustomerNotes(NotesMapper notesMapper);

	public ContactViewMapper saveCustomerContact(ContactMapper contactMapper) throws IOException, TemplateException;

	public List<DocumentMapper> getDocumentListByCustomerId(String contactId);

	public OpportunityViewMapper saveCustomerOpportunity(OpportunityMapper opportunityMapper);

	public List<OpportunityViewMapper> getOpportunityListByCustomerId(String customerId);

	public List<CustomerResponseMapper> getCustomerListByName(String name);

	

	public ByteArrayInputStream exportEmployeeListToExcel(List<CustomerResponseMapper> customerList);

	public List<CustomerResponseMapper> getAllCustomerList(int pageNo, int pageSize, String orgId);

	public HashMap getCountListByuserId(String userId);

	public void deleteDocumentsById(String documentId);

	public OpportunityViewMapper updateCandidateEducationalDetails(OpportunityViewMapper opportunityViewMapper);

	public OpportunityViewMapper updateCustomerOpportunity(String opportunityId,OpportunityMapper opportunityViewMapper);

	public String saverequirementCustomer(CustomerRecruitmentMapper customerRecruitmentMapper);

	public List<CustomerRecruitmentMapper> getCustomerRecriutmentListByCustomerId(String customerId);

	public CustomerRecruitmentMapper updateRequirementCustomer(CustomerRecruitmentMapper customerRecruitmentMapper);

	public List<CustomerResponseMapper> getCustomerListByCategory(String category, String userId);

	public HashMap getCountListByCategory(String categoryName,String userId);

	public HashMap getCountNoOfCustomerByUserId(String userId);

	public String saveinvoiceCustomer(CustomerInvoiceMapper customerInvoiceMapper);

	public List<CustomerInvoiceMapper> getInvoiceListByCustomerId(String customerId);

	List<String> updateTransferOneUserToAnother(String userId, TransferMapper transferMapper);

	public List<CustomerResponseMapper> getCustomerDetailsByName(String name);

	public List<CustomerResponseMapper> getCustomerDetailsBySector(String name);

	public List<CustomerResponseMapper> getCustomerDetailsByCountry(String name);

	public String saveCustomerInitiative(InitiativeDetailsMapper initiativeDetailsMapper);

	public List<InitiativeDetailsMapper> getInitiativeListByCustomerId(String customerId);

	public CustomerKeySkillMapper getKeySkilListByCustomerIdAndDateRange(String customerId, String orgId, String startDate, String endDate);

	public List<CustomerResponseMapper> getCustomerDetailsByOwnerName(String name);

    public List<InitiativeSkillMapper> getSkillListByInitiativeDetailsId(String initiativeDetailsId);

    public List<InitiativeDetailsMapper> getInitiativeSkillListByUserId(String userId);

    public List<InitiativeDetailsMapper> getInitiativeNameByUserId(String userId);

    public InitiativeDetailsMapper updateInitiativeDetails(String initiativeDetailsId,
            InitiativeDetailsMapper initiativeDetailsMapper);

	public String saveSkillSet(CustomerSkillLinkMapper customerSkillLinkMapper);

	public List<CustomerSkillLinkMapper> getSkillSetByCustomerId(String customerId);

	public CustomerSkillLinkMapper deleteSkillsSet(String customerSkillLinkId);

	public boolean checkSkillInCustomerSkillSet(String skillName, String customerId);

	public void deleteCustomerInnitiative(String initiativeDetailsId);

	public List<CustomerResponseMapper> getCustomerDetailsByuserId(String loggeduserId);

	List<CustomerResponseMapper> getAllCustomerListCount();

	public List<CustomerViewMapperrForMap> getCustomerDetailsByuserIdForMap(String userId);

	public List<CustomerViewMapperForDropDown> getCustomerDetailsByuserIdForDropDown(String userId);

	public List<CustomerViewMapperForDropDown> getCustomerDetailsByOrgIdForDropDown(String orgId);

	public HashMap getCustomerOppertunityCountByCustomerId(String customerId);

	public HashMap getCustomerContactCountByCustomerId(String customerId);

	public HashMap getCustomerDocumentCountByCustomerId(String customerId);

	public List<Map<String,Double>> getCustomerCountSourceWiseByUserId(String userId, String orgId);

	public List<Map<String, List<CustomerResponseMapper>>> getCustomerListSourceWiseByUserId(String userId, String orgId);

	List<CustomerResponseMapper> getFilterCustomerDetailsPageWiseByuserId(String userId, int pageNo, int pageSize,
			String filter);

	public List<CustomerViewMapperForDropDown> getCustomerDetailsByUserIdForDropDown(String userId);

	public List<ActivityMapper> getActivityListByCustomerId(String customerId);

//	public NotesMapper updateNoteDetails(NotesMapper notesMapper);

	public void deleteCustomerNotesById(String notesId);

	public List<CustomerResponseMapper> getCustomerListByOrgId(String orgId, int pageNo, int pageSize, String filter);

	Set<CustomerResponseMapper> getTeamCustomerDetailsPageWiseByuserId(String userId, int pageNo, int pageSize,
			String filter);

	public HashMap getTeamCustomerCountByUserId(String userId);

	public DocumentMapper updateContractIndForDocument(DocumentMapper documentMapperr);

	List<CustomerResponseMapper> getCustomerDetailsPageWiseByuserIds(List<String> userIds, int pageNo, int pageSize);

	public boolean customerByUrl(String url);


	public HashMap getCustomerOppertunityProposalValueCountByCustomerId(String customerId, String userId, String orgId);

	public HashMap getCustomerOppertunityWeightedValueCountByCustomerId(String customerId, String userId, String orgId);

	public HashMap getActivityRecordByCustomerId(String customerId);

	public HashMap getCustomerWonOppertunityCountByCustomerId(String customerId);

	public HashMap getCustomerWonOppertunityProposalValueCountByCustomerId(String customerId, String userId,
			String orgId);

	public HashMap getCustomerWonOppertunityWeightedValueCountByCustomerId(String customerId, String userId,
			String orgId);

	public List<CustomerReportMapper> getAllCustomerByOrgIdForReport(String orgId, String startDate, String endDate);

	public List<CustomerReportMapper> getAllCustomerByUserIdForReport(String userId, String startDate, String endDate);

	public List<ContactReportMapper> getAllCustomerContactListByOrgIdForReport(String orgId, String startDate,
			String endDate);

	public List<ContactReportMapper> getAllCustomerContactListByUserIdForReport(String userId, String startDate,
			String endDate);

	public HashMap getCustomerCountByCountry(String country);

	public List<CustomerResponseMapper> getCustomerListByCountry(String country);

    List<CampaignRespMapper> getCampaign(String customerId);

	CampaignRespMapper createCampaign(CampaignReqMapper requestMapper);

	CampaignRespMapper getCampaignByCustomerIdAndEventId(String customerId,String eventId);

	public List<CustomerResponseMapper> getCustomerDetailsByUnderUserId(String userId, int pageNo, int pageSize);

	public HashMap getCountCustomerDetailsByUnderUserId(String userId);

	public HashMap getCustomerListCountByOrgId(String orgId);

	public List<ContactViewMapperForDropDown> getAllCustomerContactListByCustomerIdForDropDown(String customerId);

	public List<OpportunityViewMapper> getWonOpportunityListByCustomerId(String customerId);

	public List<CustomerLobLinkMapper> getDistributorLObListByDistributorId(String customerId, String loggedInOrgId);

	public CustomerLobLinkMapper createOrUpdateLOb(CustomerLobLinkMapper customerLobLinkMapper);

	public List<CustomerResponseMapper> getCustomerDetailsByNameByOrgLevel(String name, String orgId);

	public List<CustomerResponseMapper> getCustomerBySectorInOrgLevel(String name, String orgId);

	public List<CustomerResponseMapper> getCustomerBySourceInOrgLevel(String name, String orgId);

	public List<CustomerResponseMapper> getCustomerByOwnerNameInOrgLevel(String name, String orgId);

	public List<CustomerResponseMapper> getCustomerDetailsByNameForTeam(String name, String userId);

	public List<CustomerResponseMapper> getCustomerBySectorForTeam(String name, String userId, String orgId);

	public List<CustomerResponseMapper> getCustomerBySourceForTeam(String name, String userId, String orgId);

	public List<CustomerResponseMapper> getCustomerByOwnerNameForTeam(String name, String userId, String orgId);

	public List<CustomerResponseMapper> getCustomerDetailsByNameByUserId(String name, String userId);

	public List<CustomerResponseMapper> getCustomerBySectorAndByUserId(String name, String userId, String orgId);

	public List<CustomerResponseMapper> getCustomerBySourceAndByUserId(String name, String userId, String orgId);

	public List<CustomerResponseMapper> getCustomerByOwnerNameAndByUserId(String name, String userId, String orgId);

}
