package com.app.employeePortal.partner.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import com.app.employeePortal.Opportunity.mapper.OpportunityMapper;
import com.app.employeePortal.Opportunity.mapper.OpportunityViewMapper;
import com.app.employeePortal.contact.mapper.ContactMapper;
import com.app.employeePortal.contact.mapper.ContactViewMapper;
import com.app.employeePortal.contact.mapper.ContactViewMapperForDropDown;
import com.app.employeePortal.document.mapper.DocumentMapper;
import com.app.employeePortal.employee.mapper.NotesMapper;
import com.app.employeePortal.partner.mapper.PartnerMapper;
import com.app.employeePortal.partner.mapper.PartnerMapperForDropDown;
import com.app.employeePortal.partner.mapper.PartnerSkillSetMapper;
import com.app.employeePortal.partner.mapper.PartnerWebsiteMapper;

import freemarker.template.TemplateException;

public interface PartnerService {

	PartnerMapper saveToPartnerProcess(PartnerMapper partnerMapper);

	// List<PartnerMapper> getPartnerDetailsListByUserId(String userId);

	ContactViewMapper savePartnerContact(ContactMapper contactMapper) throws IOException, TemplateException;

	String savePartnerNotes(NotesMapper notesMapper);

	List<DocumentMapper> getDocumentListByPartnerId(String partnerId);

	List<ContactViewMapper> getContactListByPartnerId(String partnerId);

	List<NotesMapper> getNoteListByPartnerId(String partnerId);

	PartnerMapper getPartnerDetailsById(String partnerId);

	String savePartnerOppotunity(OpportunityMapper opportunityMapper);

	List<OpportunityViewMapper> getOpportunityListByPartnerId(String partnerId);

	List<PartnerMapper> getPartnerName(String partnerName, String userId);

	ByteArrayInputStream exportEmployeeListToExcel(List<PartnerMapper> partnerList);

	PartnerMapper updatePartner(String partnerId, PartnerMapper partnerMapper);

	ContactViewMapper updateContactListByPartnerId(String partnerId, ContactMapper contactViewMapper) throws IOException, TemplateException;

	public List<ContactViewMapper> getAllPartnerContatList(String userId, int pageNo, int pageSize);
	
	public List<ContactViewMapper> getAllPartnerContatLists(String userId, int pageNo, int pageSize);

	String saveSkillSet(PartnerSkillSetMapper skillSetMapper);

	public PartnerSkillSetMapper getSkillSet(String id);

	List<PartnerSkillSetMapper> getSkillSetDetails(String partnerId);

	List<PartnerMapper> getAllPartnerList(int pageNo, int pageSize);

	void deleteSkilsset(String id);

	// public HashMap getCountList();

	HashMap getCountListByuserId(String userId);

	void deleteDocumentById(String documentId);

	HashMap getPartnerContactCountByuserId(String userId);

	public List<String> updateTransferOneUserToAnother(String userId, PartnerMapper partnerMapper);

	List<PartnerMapper> getPartnerByBusinessRegistrationNumber(String partnerName, String userId);

	List<PartnerMapper> getPartnerByTaxRegistrationNumber(String partnerName, String userId);

	boolean emailExistsByWebsite(String email);

	String saveToPartnerProcessForWebsite(PartnerWebsiteMapper partnerMapper);

	ContactViewMapper giveAccessContactToUser(String contactId, ContactViewMapper contactMapper);

	public boolean checkSkillInCustomerSkillSet(String skillName, String partnerId);

	List<PartnerMapper> getAllPartnerListByOrgId(String loggedOrgId);

	List<PartnerMapper> getPartnerDetailsListByUserId(String userId, int pageNo, int pageSize);

	List<PartnerMapper> getPartnerDetailsListByUserId(String userId);

	List<PartnerMapper> getAllPartnerListCount();

	List<ContactViewMapper> getAllPartnerContatListByUserId(String userId);

	List<PartnerMapperForDropDown> getAllPartnerListForDropDown();

	Object deleteAndReinstatePartnerByPartnerId(String partnerId, PartnerMapper partnerMapper);

	List<PartnerMapper> getDeletedPartnerDetailsByUserId(String userId, int pageNo, int pageSize);

	List<ContactViewMapperForDropDown> getAllPartnerContatListForDropDown(String userId);

	List<ContactViewMapperForDropDown> getAllPartnerContactListForDropDown();

	List<PartnerMapper> getPartnerDetailsListByUserIds(List<String> userId, int pageNo, int pageSize);

	List<ContactViewMapper> getAllPartnerContatLists(List<String> userId, int pageNo, int pageSize);

}
