package com.app.employeePortal.contact.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.app.employeePortal.contact.mapper.ContactMapper;
import com.app.employeePortal.contact.mapper.ContactReportMapper;
import com.app.employeePortal.contact.mapper.ContactTypeMapper;
import com.app.employeePortal.contact.mapper.ContactViewMapper;
import com.app.employeePortal.contact.mapper.ContactViewMapperForDropDown;
import com.app.employeePortal.customer.mapper.TransferMapper;
import com.app.employeePortal.document.mapper.DocumentMapper;
import com.app.employeePortal.employee.mapper.NotesMapper;
import com.app.employeePortal.leads.mapper.ActivityMapper;

import freemarker.template.TemplateException;

public interface ContactService {

	public ContactViewMapper saveContact(ContactMapper contactMapper) throws IOException, TemplateException;

	public ContactViewMapper getContactDetailsById(String contactId);

	public List<ContactViewMapper> getContactListByUserId(String userId, int pageNo, int pageSize);

	public List<NotesMapper> getNoteListByContactId(String contactId);

	public NotesMapper getNotes(String id);

	public String addContactType(ContactTypeMapper contactTypeMapper);

	public List<ContactTypeMapper> getContactTypesByOrgId(String orgId);

	public String saveContactNotes(NotesMapper notesMapper);

	public ContactViewMapper updateContact(String contactId, ContactMapper contactMapper) throws IOException, TemplateException;

	public List<DocumentMapper> getContactDocumentListByContactId(String contactId);

	public List<ContactViewMapper> getContactListByName(String firstName);

	public ByteArrayInputStream exportEmployeeListToExcel(List<ContactViewMapper> customerList);

	public List<ContactViewMapper> getPartnerContactListByName(String name);

//	public List<ContactViewMapper> getAllContatList(int pageNo, int pageSize);

	public List<ContactViewMapper> getAllPartnerContatList(int pageNo, int pageSize);

	public HashMap getCountListByuserId(String userId);

	public void deleteDocumentById(String documentId);

	public String contactConvertToUser(String taskId);

	public List<String> updateTransferOneUserToAnother(String userId, TransferMapper transferMapper);

	public ContactViewMapper getContactCloserByContactId(String contactId);

	public List<ContactViewMapper> getContactListByUserIdWhichEmailPresent(String userId);

	public boolean contactExistsByEmail(ContactMapper contactMapper);

	List<ContactViewMapper> getContactDetailsListByUserId(String userId);

	List<ContactViewMapper> getAllPartnerContatListCount();

	List<ContactViewMapper> getAllCustomerContatListCount();

	List<ContactViewMapper> getCustomerContactListByUserId(String userId);

	public boolean CustomerContactExistsByFirstNameAndMiddleNameAndLastName(String firstName, String middleName,
			String lastName);

	public boolean PartnerContactExistsByFirstNameAndMiddleNameAndLastName(String firstName, String middleName,
			String lastName);

	public List<ContactViewMapperForDropDown> getAllCustomerContactListByUserIdForDropDown(String userId);

	List<ContactViewMapperForDropDown> getPartnerContactListByUserIdForDropDown(String userId);

	public List<ContactViewMapperForDropDown> getAllPartnerContactListByOrgIdForDropDown(String orgId);

	public List<Map<String, Double>> getAddedContactListCountByUserIdWithDateWise(String userId, String startDate,String endDate);

	public List<ContactViewMapper> getInvestorContactListByUserId(String userId, int pageNo, int pageSize,String filter);

    List<Map<String, Double>> getInvestorAddedContactCountByUserIdWithDateWise(String userId, String startDate, String endDate);

	public List<ContactViewMapperForDropDown> getAllInvestorContactListByUserIdForDropDown(String userId);

	public HashMap getCustomerContactCountListByuserId(String userId);
 
    public List<ContactViewMapper> getFilterContactListByUserId(String userId, int pageNo, int pageSize, String filter);

	public List<ContactViewMapper> getInvestorContactListByName(String name, String userId);

	public List<ContactViewMapper> getInvestorContactByCompany(String name, String userId);

//	public NotesMapper updateNoteDetails(NotesMapper notesMapper);

	public void deleteContactNotesById(String notesId);

	public List<ContactViewMapper> getContactListByOrgId(String orgId, int pageNo, int pageSize, String type);

	public List<ActivityMapper> getActivityListByContactId(String contactId);

	public Set<ContactViewMapper> getTeamContactListByUserId(String userId, int pageNo, int pageSize, String filter);

	public HashMap getTeamContactCountByUserId(String userId);

	List<ContactViewMapper> getContactListByUserIds(List<String> userId, int pageNo, int pageSize);

	ContactReportMapper getContactDetailsByIdForReport(String contactId);

	public HashMap getActivityRecordByContactId(String contactId);

	public List<ContactViewMapper> getTeamContactListByUnderUserId(String userId, int pageNo, int pageSize);

	public HashMap getTeamContactCountByUnderUserId(String userId);

	public HashMap getContactListCountByOrgId(String orgId, String type);

	public void deleteContact(String contactId);

	public ContactViewMapper reinitiateDeletedContactId(String contactId, String userId);

	public List<ContactViewMapper> getContactDetailsByNameByOrgLevel(String name, String orgId, String contactType);

	public List<ContactViewMapper> getContactBySectorInOrgLevel(String name, String orgId, String contactType);

	public List<ContactViewMapper> getContactBySourceInOrgLevel(String name, String orgId, String contactType);

	public List<ContactViewMapper> getContactByOwnerNameInOrgLevel(String name, String orgId, String contactType);

	public List<ContactViewMapper> getContactDetailsByNameForTeam(String name, String userId, String contactType);

	public List<ContactViewMapper> getContactBySectorForTeam(String name, String userId, String orgId,
			String contactType);

	public List<ContactViewMapper> getContactBySourceForTeam(String name, String userId, String orgId,
			String contactType);

	public List<ContactViewMapper> getContactByOwnerNameForTeam(String name, String userId, String orgId,
			String contactType);

	public List<ContactViewMapper> getContactDetailsByNameByUserId(String name, String userId, String contactType);

	public List<ContactViewMapper> getContactBySectorAndByUserId(String name, String userId, String orgId,
			String contactType);

	public List<ContactViewMapper> getContactBySourceAndByUserId(String name, String userId, String orgId,
			String contactType);

	public List<ContactViewMapper> getContactByOwnerNameAndByUserId(String name, String userId, String orgId,
			String contactType);

}
