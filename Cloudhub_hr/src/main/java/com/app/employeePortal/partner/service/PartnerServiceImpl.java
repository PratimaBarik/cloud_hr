package com.app.employeePortal.partner.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.app.employeePortal.Opportunity.entity.OpportunityDetails;
import com.app.employeePortal.Opportunity.entity.OpportunityDocumentLink;
import com.app.employeePortal.Opportunity.entity.OpportunityInfo;
import com.app.employeePortal.Opportunity.mapper.OpportunityMapper;
import com.app.employeePortal.Opportunity.mapper.OpportunityViewMapper;
import com.app.employeePortal.Opportunity.repository.OpportunityDetailsRepository;
import com.app.employeePortal.Opportunity.repository.OpportunityInfoRepository;
import com.app.employeePortal.Opportunity.service.OpportunityService;
import com.app.employeePortal.address.entity.AddressDetails;
import com.app.employeePortal.address.entity.AddressInfo;
import com.app.employeePortal.address.mapper.AddressMapper;
import com.app.employeePortal.address.repository.AddressInfoRepository;
import com.app.employeePortal.address.repository.AddressRepository;
import com.app.employeePortal.candidate.entity.DefinationDetails;
import com.app.employeePortal.candidate.entity.DefinationInfo;
import com.app.employeePortal.candidate.repository.DefinationInfoRepository;
import com.app.employeePortal.candidate.repository.DefinationRepository;
import com.app.employeePortal.contact.entity.ContactDetails;
import com.app.employeePortal.contact.entity.ContactInfo;
import com.app.employeePortal.contact.mapper.ContactMapper;
import com.app.employeePortal.contact.mapper.ContactViewMapper;
import com.app.employeePortal.contact.mapper.ContactViewMapperForDropDown;
import com.app.employeePortal.contact.repository.ContactInfoRepository;
import com.app.employeePortal.contact.repository.ContactRepository;
import com.app.employeePortal.contact.service.ContactService;
import com.app.employeePortal.customer.repository.CustomerContactLinkRepository;
import com.app.employeePortal.document.entity.DocumentDetails;
import com.app.employeePortal.document.mapper.DocumentMapper;
import com.app.employeePortal.document.repository.DocumentDetailsRepository;
import com.app.employeePortal.document.service.DocumentService;
import com.app.employeePortal.employee.entity.EmployeeDetails;
import com.app.employeePortal.employee.entity.Notes;
import com.app.employeePortal.employee.mapper.NotesMapper;
import com.app.employeePortal.employee.repository.EmployeeRepository;
import com.app.employeePortal.employee.repository.NotesRepository;
import com.app.employeePortal.notification.entity.NotificationDetails;
import com.app.employeePortal.notification.repository.NotificationRepository;
import com.app.employeePortal.partner.entity.PartnerAddressLink;
import com.app.employeePortal.partner.entity.PartnerContactLink;
import com.app.employeePortal.partner.entity.PartnerDetails;
import com.app.employeePortal.partner.entity.PartnerDocumentLink;
import com.app.employeePortal.partner.entity.PartnerInfo;
import com.app.employeePortal.partner.entity.PartnerNotesLink;
import com.app.employeePortal.partner.entity.PartnerOpportunityLink;
import com.app.employeePortal.partner.entity.PartnerSkillSet;
import com.app.employeePortal.partner.mapper.PartnerMapper;
import com.app.employeePortal.partner.mapper.PartnerMapperForDropDown;
import com.app.employeePortal.partner.mapper.PartnerSkillSetMapper;
import com.app.employeePortal.partner.mapper.PartnerWebsiteMapper;
import com.app.employeePortal.partner.repository.PartnerAddressLinkRepository;
import com.app.employeePortal.partner.repository.PartnerCandidateLinkRepository;
import com.app.employeePortal.partner.repository.PartnerContactLinkRepository;
import com.app.employeePortal.partner.repository.PartnerDetailsRepository;
import com.app.employeePortal.partner.repository.PartnerDocumentLinkRepository;
import com.app.employeePortal.partner.repository.PartnerInfoRepository;
import com.app.employeePortal.partner.repository.PartnerNotesLinkRepository;
import com.app.employeePortal.partner.repository.PartnerOpportunityLinkRepository;
import com.app.employeePortal.partner.repository.PartnerSkillSetRepository;
import com.app.employeePortal.permission.entity.Permission;
import com.app.employeePortal.permission.entity.ThirdParty;
import com.app.employeePortal.permission.repository.PermissionRepository;
import com.app.employeePortal.permission.repository.ThirdPartyRepository;
import com.app.employeePortal.sector.entity.SectorDetails;
import com.app.employeePortal.sector.repository.SectorDetailsRepository;
import com.app.employeePortal.task.repository.EmployeeTaskRepository;
import com.app.employeePortal.task.repository.TaskDetailsRepository;
import com.app.employeePortal.task.repository.TaskInfoRepository;
import com.app.employeePortal.util.Utility;

import freemarker.template.TemplateException;

@Service
@Transactional
public class PartnerServiceImpl implements PartnerService {

    @Autowired
    PartnerInfoRepository partnerInfoRepository;

    @Autowired
    PartnerDetailsRepository partnerDetailsRepository;

    @Autowired
    NotesRepository notesRepository;

    @Autowired
    DocumentDetailsRepository documentDetailsRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    NotificationRepository notificationRepository;
    @Autowired
    ContactRepository contactRepository;
    @Autowired
    PartnerDocumentLinkRepository partnerDocumentLinkRepository;

    @Autowired
    PartnerNotesLinkRepository partnerNotesLinkRepository;

    @Autowired
    PartnerContactLinkRepository partnerContactLinkRepository;

    @Autowired
    PartnerOpportunityLinkRepository partnerOpportunityLinkRepository;

    @Autowired
    OpportunityDetailsRepository opportunityDetailsRepository;
    @Autowired
    PartnerSkillSetRepository partnerSkillSetRepository;
    @Autowired
    OpportunityInfoRepository opportunityInfoRepository;
    @Autowired
    SectorDetailsRepository sectorDetailsRepository;
    @Autowired
    TaskInfoRepository taskInfoRepository;

    @Autowired
    ContactService contactService;

    @Autowired
    PartnerService partnerService;

    @Autowired
    OpportunityService opportunityService;

    @Autowired
    AddressInfoRepository addressInfoRepository;

    @Autowired
    PermissionRepository permissionRepository;
    @Autowired
    PartnerCandidateLinkRepository partnerCandidateLinkRepository;
    @Autowired
    TaskDetailsRepository taskDetailsRepository;
    @Autowired
    EmployeeTaskRepository employeeTaskRepository;
    @Autowired
    ContactInfoRepository contactInfoRepository;
    @Autowired
    DefinationInfoRepository definationInfoRepository;
    @Autowired
    DefinationRepository definationRepository;

    private String[] partner_headings = {"Partner Id", "Partner Name", "URL", "Phone#", "BusinessRegistrationNumber",
            "Tax Registration#", "Account Number", "Bank Name", "Notes"};

    @Autowired
    AddressRepository addressRepository;
    @Autowired
    PartnerAddressLinkRepository partnerAddressLinkRepository;
    @Autowired
    ThirdPartyRepository thirdPartyRepository;
    @Autowired
    CustomerContactLinkRepository customerContactLinkRepository;
    @Autowired
	DocumentService documentService;

    @Override
    public PartnerMapper saveToPartnerProcess(PartnerMapper partnerMapper) {
        String partnerId = null;

        PartnerMapper resultMapper = null;
        // String contactId = null;

        PartnerInfo partnerInfo = new PartnerInfo();
        partnerInfo.setCreation_date(new Date());
        PartnerInfo partner = partnerInfoRepository.save(partnerInfo);

        partnerId = partner.getPartner_id();

        PartnerDetails partnerDetails = new PartnerDetails();
        setPropertyOnInput(partnerMapper, partnerDetails, partnerId);
        partnerDetailsRepository.save(partnerDetails);
        /*
         * partnerMapper.getContactMapper().setPartnerId(partnerId); //contactId =
         * contactService.saveContact(partnerMapper.getContactMapper()); if (null !=
         * partnerMapper.getContactMapper()) {
         *
         * ContactInfo contactInfo = new ContactInfo(); contactInfo.setCreation_date(new
         * Date());
         *
         * ContactInfo contactt = contactInfoRepository.save(contactInfo);
         *
         * contactId = contactt.getContact_id(); ContactDetails contact = new
         * ContactDetails(); contact.setContactId(contactId);
         *
         * contact.setEmail_id(partnerMapper.getContactMapper().getEmailId());
         * contact.setFirst_name(partnerMapper.getContactMapper().getFirstName());
         * contact.setMiddle_name(partnerMapper.getContactMapper().getMiddleName());
         * contact.setLast_name(partnerMapper.getContactMapper().getLastName());
         * contact.setPhone_number(partnerMapper.getContactMapper().getPhoneNumber());
         * contact.setDesignation(partnerMapper.getContactMapper().getDesignation());
         * contact.setContactType("Partner"); contact.setLiveInd(true); String
         * middleName =" "; String lastName =""; String satutation ="";
         *
         * if(!StringUtils.isEmpty(contact.getLast_name())) {
         *
         * lastName = contact.getLast_name(); } if(contact.getSalutation() != null &&
         * contact.getSalutation().length()>0) { satutation = contact.getSalutation(); }
         *
         *
         * if(contact.getMiddle_name() != null && contact.getMiddle_name().length()>0) {
         *
         *
         * middleName = contact.getMiddle_name();
         * contact.setFullName(satutation+" "+contact.getFirst_name()+" "+middleName+" "
         * +lastName);
         *
         * }else {
         *
         * contact.setFullName(satutation+" "+contact.getFirst_name()+" "+lastName); }
         * contact.setTag_with_company(contact.getFullName());
         *
         * contactRepository.save(contact);
         *
         * /* insert partnerDocument link table
         */

        /*
         * PartnerContactLink partnerContactLink = new PartnerContactLink();
         * partnerContactLink.setPartnerId(partnerId);
         * partnerContactLink.setContactId(contactId);
         * partnerContactLink.setCreationDate(new Date());
         * partnerContactLinkRepository.save(partnerContactLink); }
         */
        /* insert to notification table */

        NotificationDetails notification = new NotificationDetails();
		notification.setAssignedTo(partnerMapper.getUserId());
        notification.setMessage(partnerMapper.getPartnerName() + " Created as a Partner.");
        notification.setMessageReadInd(false);
        notification.setNotificationDate(new Date());
        notification.setNotificationType("Distributor Creation");
        notification.setUser_id(partnerMapper.getUserId());
        notificationRepository.save(notification);

        resultMapper = getPartnerDetailsById(partnerId);

        return resultMapper;

    }

    private void setPropertyOnInput(PartnerMapper partnerMapper, PartnerDetails partnerr, String partnerId) {

        partnerr.setCreationDate(new Date());

        partnerr.setLiveInd(true);
        partnerr.setPartnerId(partnerId);
        partnerr.setUserId(partnerMapper.getUserId());

        partnerr.setPartnerName(partnerMapper.getName());
        partnerr.setPhoneNo(partnerMapper.getPhoneNo());
        partnerr.setCountryDialcode(partnerMapper.getCountryDialCode());
        partnerr.setEmail(partnerMapper.getEmail());

        partnerr.setUrl(partnerMapper.getUrl());
        partnerr.setCountry(partnerMapper.getCountry());
        partnerr.setSector(partnerMapper.getSectorId());

        partnerr.setBusinessRegistrationNumber(partnerMapper.getBusinessRegistrationNumber());
        partnerr.setTaxRegistrationNumber(partnerMapper.getTaxRegistrationNumber());
        partnerr.setAccountNumber(partnerMapper.getAccountNumber());
        partnerr.setBankName(partnerMapper.getBankName());
        partnerr.setNote(partnerMapper.getNote());
        partnerr.setStatus(partnerMapper.isStatus());
        partnerr.setTncInd(partnerMapper.getTncInd());
        partnerr.setImageURL(partnerMapper.getImageURL());
        partnerr.setAssignedTo(partnerMapper.getAssignedTo());
        partnerr.setDocument(partnerMapper.getDocument());
        partnerr.setDocumentShareInd(partnerMapper.isDocumentShareInd());
        partnerr.setChannel(partnerMapper.getChannel());
        
        
        List<String> skillList= partnerMapper.getSkills();
		if(null!=skillList && !skillList.isEmpty()) {
			for (String skillName : skillList) {
				PartnerSkillSet partnerSkillSet = new PartnerSkillSet();
				List<DefinationDetails> definationDetails = definationRepository.findByNameContaining(skillName);
		        for (DefinationDetails definationDetails2 : definationDetails) {
		            if (null != definationDetails2) {
					partnerSkillSet.setSkillName(definationDetails2.getDefinationId());
				}
				
				partnerSkillSet.setPartnerId(partnerId);
				partnerSkillSet.setCreationDate(new Date());
				partnerSkillSetRepository.save(partnerSkillSet);
		        }
			}
		}
        
		
        // partnerr.setFullName(partnerMapper.getFirstName()+"
        // "+partnerMapper.getMiddleName()+" "+partnerMapper.getLastName());
        // partnerr.setStatus(false);

        // partnerr.setSpace_for_bank_details(partnerMapper.getSpaceForBankDetails());
        if (partnerMapper.getAddress().size() > 0) {
            for (AddressMapper addressMapper : partnerMapper.getAddress()) {
                /* insert to address info & address deatils & customeraddressLink */

                AddressInfo addressInfo = new AddressInfo();
                addressInfo.setCreationDate(new Date());

                AddressInfo addressInfoo = addressInfoRepository.save(addressInfo);

                String addressId = addressInfoo.getId();

                if (null != addressId) {

                    AddressDetails addressDetails = new AddressDetails();
                    addressDetails.setAddressId(addressId);
                    addressDetails.setAddressLine1(addressMapper.getAddress1());
                    addressDetails.setAddressLine2(addressMapper.getAddress2());
                    addressDetails.setAddressType(addressMapper.getAddressType());
                    addressDetails.setCountry(addressMapper.getCountry());
                    addressDetails.setCreationDate(new Date());
                    addressDetails.setStreet(addressMapper.getStreet());
                    addressDetails.setCity(addressMapper.getCity());
                    addressDetails.setPostalCode(addressMapper.getPostalCode());
                    addressDetails.setTown(addressMapper.getTown());
                    addressDetails.setState(addressMapper.getState());
                    addressDetails.setLatitude(addressMapper.getLatitude());
                    addressDetails.setLongitude(addressMapper.getLongitude());
                    addressDetails.setLiveInd(true);
                    addressDetails.setHouseNo(addressMapper.getHouseNo());
                    addressRepository.save(addressDetails);

                    PartnerAddressLink Link = new PartnerAddressLink();
                    Link.setPartnerId(partnerId);

                    System.out.println("@@@@" + partnerId);
                    Link.setAddressId(addressId);
                    Link.setCreationDate(new Date());

                    partnerAddressLinkRepository.save(Link);

                }
            }
        }

        /* insert to Notification Table */
        NotificationDetails notification = new NotificationDetails();
        notification.setNotificationType("Opportunity");
        notification.setOrg_id(partnerMapper.getOrganizationId());
        notification.setUser_id(partnerMapper.getUserId());
        EmployeeDetails emp = employeeRepository.getEmployeesByuserId(partnerMapper.getUserId());
        
        String middleName1 =" ";
		String lastName1 ="";
		String salutation ="";

		if(!StringUtils.isEmpty(emp.getLastName())) {
			 
			lastName1 = emp.getLastName();
		 }
		 if(emp.getSalutation() != null && emp.getSalutation().length()>0) {
			 salutation = emp.getSalutation();
		 }


		 if(emp.getMiddleName() != null && emp.getMiddleName().length()>0) {

		 
			 middleName1 = emp.getMiddleName();
			 notification.setMessage("A Partner is created By " +salutation+" "+emp.getFirstName()+" "+middleName1+" "+lastName1);
		 }else {
			 
			 notification.setMessage("A Partner is created By " +salutation+" "+emp.getFirstName()+" "+lastName1);
		 }
	
		 
		 String middleName2 =" ";
			String lastName2 ="";
			String salutation1 ="";

			if(!StringUtils.isEmpty(emp.getLastName())) {
				 
				lastName2 = emp.getLastName();
			 }
			 if(emp.getSalutation() != null && emp.getSalutation().length()>0) {
				 salutation1 = emp.getSalutation();
			 }


			 if(emp.getMiddleName() != null && emp.getMiddleName().length()>0) {

			 
				 middleName2 = emp.getMiddleName();
				 notification.setAssignedBy(salutation1+" "+emp.getFirstName()+" "+middleName2+" "+lastName2);
			 }else {
				 
				 notification.setAssignedBy(salutation1+" "+emp.getFirstName()+" "+lastName2);
			 }
        
        notification.setAssignedTo(emp.getReportingManager());
        notification.setNotificationDate(new Date());
        notification.setMessageReadInd(false);
        notificationRepository.save(notification);
        
        /* insert to notes */
		String notesId = null;

		Notes notes = new Notes();
		notes.setNotes(partnerMapper.getNote());
		notes.setCreation_date(new Date());
		notes.setUserId(partnerMapper.getUserId());
		notes.setLiveInd(true);
		Notes note = notesRepository.save(notes);
		notesId = note.getNotes_id();

		/* insert to customer-notes-link */

		PartnerNotesLink partnerNotesLink = new PartnerNotesLink();
		partnerNotesLink.setPartnerId(partnerId);
		partnerNotesLink.setNoteId(notesId);
		partnerNotesLink.setCreationDate(new Date());

		partnerNotesLinkRepository.save(partnerNotesLink);

    }

	@Override
    public List<PartnerMapper> getPartnerDetailsListByUserIds(List<String> userId, int pageNo, int pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("creationDate").descending());
        List<PartnerMapper> resultMapper = new ArrayList<>();
        Page<PartnerDetails> list = partnerDetailsRepository.getPartnerListPageWiseByUserIds(userId, paging);
        		
        resultMapper =	list.stream().map(c ->{
        	
        	PartnerMapper mapper = getPartnerDetailsById(c.getPartnerId());
        	mapper.setPageCount(list.getTotalPages());
        	mapper.setDataCount(list.getSize());
        	mapper.setListCount(list.getTotalElements());
        return mapper;
        }).collect(Collectors.toList());
       
        return resultMapper;
    }
    
    @Override
    public List<PartnerMapper> getPartnerDetailsListByUserId(String userId, int pageNo, int pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("creationDate").descending());
        List<PartnerMapper> resultMapper = new ArrayList<>();
        Page<PartnerDetails> list = partnerDetailsRepository.getPartnerListPageWiseByUserId(userId, paging);
        		
        resultMapper =	list.stream().map(c ->{
        	
        	PartnerMapper mapper = getPartnerDetailsById(c.getPartnerId());
        	mapper.setPageCount(list.getTotalPages());
        	mapper.setDataCount(list.getSize());
        	mapper.setListCount(list.getTotalElements());
        return mapper;
        }).collect(Collectors.toList());
       
        return resultMapper;
    }

    private PartnerMapper getPartnerRelatedDetails(PartnerDetails partnerDetails) {
        PartnerMapper partnerMapper = new PartnerMapper();
        if (null != partnerDetails) {

            partnerMapper.setPartnerId(partnerDetails.getPartnerId());
            partnerMapper.setCreatorId(partnerDetails.getCreatorId());

            partnerMapper.setPartnerName(partnerDetails.getPartnerName());
            partnerMapper.setUserId(partnerDetails.getUserId());

            partnerMapper.setPartnerName(partnerDetails.getPartnerName());
            partnerMapper.setUserId(partnerDetails.getUserId());

            partnerMapper.setUrl(partnerDetails.getUrl());
            partnerMapper.setPhoneNo(partnerDetails.getPhoneNo());
            partnerMapper.setEmail(partnerDetails.getEmail());
            partnerMapper.setCreationDate(Utility.getISOFromDate(partnerDetails.getCreationDate()));

            partnerMapper.setTaxRegistrationNumber(partnerDetails.getTaxRegistrationNumber());
            partnerMapper.setBusinessRegistrationNumber(partnerDetails.getBusinessRegistrationNumber());
            partnerMapper.setAccountNumber(partnerDetails.getAccountNumber());
            partnerMapper.setBankName(partnerDetails.getBankName());
            partnerMapper.setNote(partnerDetails.getNote());
            partnerMapper.setStatus(partnerDetails.isStatus());
            partnerMapper.setCountry(partnerDetails.getCountry());
            partnerMapper.setSector(partnerDetails.getSector());

        }
        return partnerMapper;
    }

    @Override
    public ContactViewMapper savePartnerContact(ContactMapper contactMapper) throws IOException, TemplateException {

        ContactViewMapper resultMapper = contactService.saveContact(contactMapper);

        if (null != contactMapper) {

            /* insert partnerDocument link table */

            PartnerContactLink partnerContactLink = new PartnerContactLink();
            partnerContactLink.setPartnerId(contactMapper.getPartnerId());
            partnerContactLink.setContactId(resultMapper.getContactId());
            partnerContactLink.setCreationDate(new Date());
            partnerContactLinkRepository.save(partnerContactLink);
        }
        ThirdParty pem1 = thirdPartyRepository.findByOrgId(resultMapper.getOrganizationId());
        if (null != pem1) {
            resultMapper.setThirdPartyAccessInd(pem1.isPartnerContactInd());
        }
        return resultMapper;

    }

    @Override
    public String savePartnerNotes(NotesMapper notesMapper) {
        String partnerNotesId = null;
        if (null != notesMapper) {
            Notes notes = new Notes();
            notes.setNotes(notesMapper.getNotes());
            notes.setCreation_date(new Date());
            Notes note = notesRepository.save(notes);
            partnerNotesId = note.getNotes_id();

            /* insert to customer-notes-link */

            PartnerNotesLink partnerNotesLink = new PartnerNotesLink();
            partnerNotesLink.setPartnerId(notesMapper.getPartnerId());
            partnerNotesLink.setNoteId(partnerNotesId);
            partnerNotesLink.setCreationDate(new Date());

            partnerNotesLinkRepository.save(partnerNotesLink);

        }
        return partnerNotesId;
    }

    @Override
    public List<DocumentMapper> getDocumentListByPartnerId(String partnerId) {

        List<PartnerDocumentLink> partnerDocumentLinkList = partnerDocumentLinkRepository
                .getDocumentByPartnerId(partnerId);
        Set<String> documentIds = partnerDocumentLinkList.stream().map(PartnerDocumentLink::getDocumentId).collect(Collectors.toSet());
        if (documentIds != null && !documentIds.isEmpty()) {

            return documentIds.stream().map(documentId -> {
                DocumentMapper documentMapper = documentService.getDocument(documentId);

                if (null != documentMapper.getDocumentId()) {
                    return documentMapper;
                }
                return null;
            }).collect(Collectors.toList());

        }

        return null;
    }

    @Override
    public List<ContactViewMapper> getContactListByPartnerId(String partnerId) {
        List<PartnerContactLink> partnerContactLinkList = partnerContactLinkRepository.getContactByPartnerId(partnerId);

        if (partnerContactLinkList != null && !partnerContactLinkList.isEmpty()) {
            return partnerContactLinkList.stream().map(partnerContactLinkk -> {

                ContactViewMapper contactMapper = contactService
                        .getContactDetailsById(partnerContactLinkk.getContactId());
                if (null != contactMapper.getContactId()) {
                    return contactMapper;
                }
                ThirdParty thirdParty = thirdPartyRepository.findByOrgId(contactMapper.getOrganizationId());
                if (thirdParty != null) {
                    contactMapper.setThirdPartyAccessInd(thirdParty.isPartnerContactInd());
                    contactMapper.setPartnerId(partnerId);
                }
                System.out.println("date=" + contactMapper.getCreationDate());
                System.out.println("contactId=" + contactMapper.getContactId());

                return null;
            }).collect(Collectors.toList());

        }

        return null;
    }


    @Override
    public List<NotesMapper> getNoteListByPartnerId(String partnerId) {
        List<PartnerNotesLink> partnerNotesLinkList = partnerNotesLinkRepository.getNotesByPartnerId(partnerId);

        if (partnerNotesLinkList != null && !partnerNotesLinkList.isEmpty()) {

            return partnerNotesLinkList.stream().map(partnerNotesLink -> {
                NotesMapper notesMapper = contactService.getNotes(partnerNotesLink.getNoteId());
                if (null != notesMapper.getNotesId()) {
                    return notesMapper;
                }
                return null;
            }).collect(Collectors.toList());

        }

        return null;
    }


    @Override
    public PartnerMapper getPartnerDetailsById(String partnerId) {
        PartnerDetails partner = partnerDetailsRepository.getPartnerDetailsByIdAndLiveInd(partnerId);

        List<PartnerAddressLink> partnerAddressList = partnerAddressLinkRepository.getAddressListByPartnerId(partnerId);

        List<AddressMapper> addressList = new ArrayList<AddressMapper>();

        List<PartnerSkillSetMapper> skillList = new ArrayList<PartnerSkillSetMapper>();
        PartnerMapper partnerMapper = new PartnerMapper();

        if (null != partner) {

            if (partner.getSector() != null && partner.getSector().trim().length() > 0) {
                SectorDetails sector = sectorDetailsRepository.getSectorDetailsBySectorId(partner.getSector());
                if (null != sector) {
                    System.out.println("SectorDetails::::::::::::::::" + sector.getSectorName());
                    partnerMapper.setSector(sector.getSectorName());
                    partnerMapper.setSectorId(sector.getSectorId());
                }
            } else {
                partnerMapper.setSector("");
                partnerMapper.setSectorId("");
            }

            partnerMapper.setPartnerId(partner.getPartnerId());
            partnerMapper.setCreatorId(partner.getCreatorId());

            partnerMapper.setPartnerName(partner.getPartnerName());
            partnerMapper.setUrl(partner.getUrl());
            partnerMapper.setPhoneNo(partner.getPhoneNo());
            partnerMapper.setCountryDialCode(partner.getCountryDialcode());
            partnerMapper.setEmail(partner.getEmail());
            partnerMapper.setCreationDate(Utility.getISOFromDate(partner.getCreationDate()));
            partnerMapper.setUserId(partner.getUserId());
            partnerMapper.setTncInd(partner.getTncInd());
            partnerMapper.setDocument(partner.getDocument());
            partnerMapper.setDocumentShareInd(partner.isDocumentShareInd());
            EmployeeDetails employeeDetails = employeeRepository.getEmployeeDetailsByEmployeeId(partner.getUserId());
            if (null != employeeDetails) {
                String middleName = " ";
                String lastName = "";

                if (!StringUtils.isEmpty(employeeDetails.getLastName())) {

                    lastName = employeeDetails.getLastName();
                }

                if (employeeDetails.getMiddleName() != null && employeeDetails.getMiddleName().length() > 0) {

                    middleName = employeeDetails.getMiddleName();
                    partnerMapper.setOwnerName(employeeDetails.getFirstName() + " " + middleName + " " + lastName);
                } else {

                    partnerMapper.setOwnerName(employeeDetails.getFirstName() + " " + lastName);
                }
                partnerMapper.setOwnerImageId(employeeDetails.getImageId());
            }
            partnerMapper.setTaxRegistrationNumber(partner.getTaxRegistrationNumber());
            partnerMapper.setBusinessRegistrationNumber(partner.getBusinessRegistrationNumber());
            partnerMapper.setAccountNumber(partner.getAccountNumber());
            partnerMapper.setBankName(partner.getBankName());
            partnerMapper.setNote(partner.getNote());
            partnerMapper.setStatus(partner.isStatus());
            partnerMapper.setCountry(partner.getCountry());
            partnerMapper.setImageURL(partner.getImageURL());

            List<PartnerSkillSet> list = partnerSkillSetRepository.getSkillSetById(partnerId);
            if (null != list && !list.isEmpty()) {
                list.forEach(skillSetDetails->{
                    PartnerSkillSet list2 = partnerSkillSetRepository.getById(skillSetDetails.getSkillSetDetailsId());

                    PartnerSkillSetMapper mapper = new PartnerSkillSetMapper();
                    if (null != list2) {

                        DefinationDetails definationDetails1 = definationRepository
                                .findByDefinationId(list2.getSkillName());
                        if (null != definationDetails1) {
                            mapper.setSkillName(definationDetails1.getName());

                        }

                        mapper.setPartnerId(list2.getPartnerId());
                        mapper.setSkillSetDetailsId(list2.getSkillSetDetailsId());
                        skillList.add(mapper);
                    }
                });
                partnerMapper.setSkill(skillList);
            }
            EmployeeDetails employeeDetail = employeeRepository.getEmployeeDetailsByEmployeeId(partner.getAssignedTo());
            if (null != employeeDetail) {
                String middleName = " ";
                String lastName = "";

                if (!StringUtils.isEmpty(employeeDetail.getLastName())) {

                    lastName = employeeDetail.getLastName();
                }

                if (employeeDetail.getMiddleName() != null && employeeDetail.getMiddleName().length() > 0) {

                    middleName = employeeDetail.getMiddleName();
                    partnerMapper.setAssignedTo(employeeDetail.getFirstName() + " " + middleName + " " + lastName);

                } else {

                    partnerMapper.setAssignedTo(employeeDetail.getFirstName() + " " + lastName);

                }

            }

        }
        /* fetch partner address & set to partner mapper */
        if (null != partnerAddressList && !partnerAddressList.isEmpty()) {

            partnerAddressList.forEach(partnerAddressLink->{
                AddressDetails addressDetails = addressRepository
                        .getAddressDetailsByAddressId(partnerAddressLink.getAddressId());

                AddressMapper addressMapper = new AddressMapper();
                if (null != addressDetails) {

                    addressMapper.setAddress1(addressDetails.getAddressLine1());
                    addressMapper.setAddress2(addressDetails.getAddressLine2());
                    addressMapper.setAddressType(addressDetails.getAddressType());
                    addressMapper.setPostalCode(addressDetails.getPostalCode());

                    addressMapper.setStreet(addressDetails.getStreet());
                    addressMapper.setCity(addressDetails.getCity());
                    addressMapper.setTown(addressDetails.getTown());
                    addressMapper.setCountry(addressDetails.getCountry());
                    addressMapper.setLatitude(addressDetails.getLatitude());
                    addressMapper.setLongitude(addressDetails.getLongitude());
                    addressMapper.setState(addressDetails.getState());
                    addressMapper.setAddressId(addressDetails.getAddressId());
                    addressMapper.setHouseNo(addressDetails.getHouseNo());
                    addressList.add(addressMapper);
                }
            });

            System.out.println("addressList.......... " + addressList);
        }
        partnerMapper.setAddress(addressList);

        return partnerMapper;
    }

    @Override

    public String savePartnerOppotunity(OpportunityMapper opportunityMapper) {
        String oppotunityId = null;
        if (null != opportunityMapper) {

            OpportunityInfo opportunityInfo = new OpportunityInfo();

            opportunityInfo.setCreationDate(new Date());

            OpportunityInfo opportunityy = opportunityInfoRepository.save(opportunityInfo);

            String opportunityId = opportunityy.getId();

            OpportunityDetails opportunityDetails = new OpportunityDetails();
            opportunityDetails.setCurrency(opportunityMapper.getCurrency());
            opportunityDetails.setProposalAmount(opportunityMapper.getProposalAmount());
            opportunityDetails.setOpportunityName(opportunityMapper.getOpportunityName());
            opportunityDetails.setCustomerId(opportunityMapper.getCustomerId());
            opportunityDetails.setUserId(opportunityMapper.getUserId());
            opportunityDetails.setOpportunityId(opportunityId);
            try {
                opportunityDetails.setStartDate(
                        Utility.removeTime(Utility.getDateFromISOString(opportunityMapper.getStartDate())));
                opportunityDetails
                        .setEndDate(Utility.removeTime(Utility.getDateFromISOString(opportunityMapper.getStartDate())));
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            opportunityDetails.setCreationDate(new Date());
            opportunityDetails.setLiveInd(true);

            OpportunityDetails opportunityDetails1 = opportunityDetailsRepository.save(opportunityDetails);
            oppotunityId = opportunityDetails1.getOpportunityId();

            /* insert to partner-opportunity-link */

            PartnerOpportunityLink partnerOpportunityLink = new PartnerOpportunityLink();

            partnerOpportunityLink.setOpportunityId(oppotunityId);
            partnerOpportunityLink.setCreationDate(new Date());
            partnerOpportunityLinkRepository.save(partnerOpportunityLink);
        }
        return oppotunityId;
    }

    @Override
    public List<OpportunityViewMapper> getOpportunityListByPartnerId(String partnerId) {
        List<PartnerOpportunityLink> partnerOpportunityLinkList = partnerOpportunityLinkRepository
                .getOpportintyByPartnerId(partnerId);

        if (partnerOpportunityLinkList != null && !partnerOpportunityLinkList.isEmpty()) {
            return partnerOpportunityLinkList.stream().map(partnerOpportunityLink -> {

                OpportunityViewMapper opportunityViewMapper = opportunityService
                        .getOpportunityDetails(partnerOpportunityLink.getOpportunityId());

                if (null != opportunityViewMapper.getOpportunityId()) {
                    return opportunityViewMapper;
                }
                return null;
            }).collect(Collectors.toList());

        }

        return null;
    }

    public List<PartnerMapper> getPartnerName(String partnerName, String userId) {
        List<PartnerDetails> detailsList = partnerDetailsRepository
                .findByPartnerNameContainingAndLiveIndAndUserId(partnerName, true, userId);
        List<PartnerMapper> mapperList = new ArrayList<PartnerMapper>();
        if (null != detailsList && !detailsList.isEmpty()) {

            mapperList = detailsList.stream().map(li -> getPartnerDetailsById(li.getPartnerId()))
                    .collect(Collectors.toList());

            /*
             * detailsList.forEach(li->{
             *
             * AccountMapper accountMapper; try { accountMapper =
             * getAccountRelatedDetails(li); mapperList.add(accountMapper); } catch
             * (Exception e) { // TODO Auto-generated catch block e.printStackTrace(); }
             *
             * });
             */

            // for (AccountDetails accountDetails : detailsList) {
        }

        return mapperList;

    }

    @Override

    public ByteArrayInputStream exportEmployeeListToExcel(List<PartnerMapper> partnerList) {

        XSSFWorkbook workbook = new XSSFWorkbook();

        // Create a blank sheet
        XSSFSheet sheet = workbook.createSheet("partner");

        // Create a Font for styling header cells
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.BLACK.getIndex());

        // Create a CellStyle with the font
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        // Create a Row
        Row headerRow = sheet.createRow(0);

        // Create cells
        for (int i = 0; i < partner_headings.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(partner_headings[i]);
            cell.setCellStyle(headerCellStyle);
        }

        int rowNum = 1;
        if (null != partnerList && !partnerList.isEmpty()) {
            for (PartnerMapper partner : partnerList) {
            //partnerList.forEach(partner->{
                Row row = sheet.createRow(rowNum++);

                row.createCell(0).setCellValue(partner.getPartnerId());
                row.createCell(1).setCellValue(partner.getPartnerName());

                row.createCell(2).setCellValue(partner.getBankName());
                row.createCell(3).setCellValue(partner.getBusinessRegistrationNumber());
                row.createCell(4).setCellValue(partner.getTaxRegistrationNumber());
                row.createCell(5).setCellValue(partner.getAccountNumber());
                row.createCell(6).setCellValue(partner.getBankName());

                row.createCell(7).setCellValue(partner.getNote());

            }
        }
        // Resize all columns to fit the content size
        for (int i = 0; i < partner_headings.length; i++) {
            sheet.autoSizeColumn(i);
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            workbook.write(outputStream);
            outputStream.close();
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ByteArrayInputStream(outputStream.toByteArray());

    }

    public PartnerMapper updatePartner(String partnerId, PartnerMapper partnerMapper) {
        PartnerMapper resultMapper = null;

        PartnerDetails partner = partnerDetailsRepository.getPartnerDetailsByIdAndLiveInd(partnerId);

        if (null != partner) {

            partner.setLiveInd(false);
            partnerDetailsRepository.save(partner);

            PartnerDetails partnerDetail = new PartnerDetails();
            partnerDetail.setPartnerId(partnerId);

            partnerDetail.setUserId(partner.getUserId());

            if (null != partnerMapper.getPartnerName()) {

                partnerDetail.setPartnerName(partnerMapper.getPartnerName());
            } else {
                partnerDetail.setPartnerName(partner.getPartnerName());
            }

            if (null != partnerMapper.getPhoneNo()) {

                partnerDetail.setPhoneNo(partnerMapper.getPhoneNo());
            } else {
                partnerDetail.setPhoneNo(partner.getPhoneNo());
            }

            if (null != partnerMapper.getUrl()) {

                partnerDetail.setUrl(partnerMapper.getUrl());
            } else {
                partnerDetail.setUrl(partner.getUrl());
            }
            if (null != partnerMapper.getBusinessRegistrationNumber()) {

                partnerDetail.setBusinessRegistrationNumber(partnerMapper.getBusinessRegistrationNumber());
            } else {

                partnerDetail.setBusinessRegistrationNumber(partner.getBusinessRegistrationNumber());

            }
            if (null != partnerMapper.getTaxRegistrationNumber()) {

                partnerDetail.setTaxRegistrationNumber(partnerMapper.getTaxRegistrationNumber());
            } else {

                partnerDetail.setTaxRegistrationNumber(partner.getTaxRegistrationNumber());

            }
            if (null != partnerMapper.getAccountNumber()) {

                partnerDetail.setAccountNumber(partnerMapper.getAccountNumber());
            } else {

                partnerDetail.setAccountNumber(partner.getAccountNumber());

            }
            if (null != partnerMapper.getBankName()) {

                partnerDetail.setBankName(partnerMapper.getBankName());
            } else {

                partnerDetail.setBankName(partner.getBankName());

            }

            if (null != partnerMapper.getCountry()) {

                partnerDetail.setCountry(partnerMapper.getCountry());
            } else {

                partnerDetail.setCountry(partner.getCountry());

            }

            if (null != partnerMapper.getSectorId()) {

                partnerDetail.setSector(partnerMapper.getSectorId());
            } else {

                partnerDetail.setSector(partner.getSector());

            }

            if (null != partnerMapper.getNote()) {
            	List<PartnerNotesLink> list = partnerNotesLinkRepository.getNotesByPartnerId(partnerId);
				if (null != list && !list.isEmpty()) {
					list.sort((m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
					Notes notes = notesRepository.findByNoteId(list.get(0).getNoteId());
					if (null != notes) {
						notes.setNotes(partnerMapper.getNote());
						notesRepository.save(notes);
					}
				}
            }
            if (false != partnerMapper.isStatus()) {

                partnerDetail.setStatus(partnerMapper.isStatus());
                System.out.println("inside if");
            } else {
                partnerDetail.setStatus(partnerMapper.isStatus());
                System.out.println("inside else");
            }
            if (null != partnerMapper.getEmail()) {

                partnerDetail.setEmail(partnerMapper.getEmail());
            } else {
                partnerDetail.setEmail(partner.getEmail());
            }
            if (null != partnerMapper.getCountry()) {
                partnerDetail.setCountry(partnerDetail.getCountry());
            } else {
                partnerDetail.setCountry(partner.getCountry());
            }
            if (null != partnerMapper.getTncInd()) {
                partnerDetail.setTncInd(partnerDetail.getTncInd());
            } else {
                partnerDetail.setTncInd(partner.getTncInd());
            }
            if (null != partnerMapper.getAssignedTo()) {
                partnerDetail.setAssignedTo(partnerDetail.getAssignedTo());
            } else {
                partnerDetail.setAssignedTo(partner.getAssignedTo());
            }
            if (null != partnerMapper.getDocument()) {
                partnerDetail.setDocument(partnerMapper.getDocument());
            } else {
                partnerDetail.setDocument(partner.getDocument());
            }
            if (false != partnerMapper.isDocumentShareInd()) {
                partnerDetail.setDocumentShareInd(partnerMapper.isDocumentShareInd());
            } else {
                partnerDetail.setDocumentShareInd(partner.isDocumentShareInd());
            }
            partnerDetail.setCreationDate(new Date());
            partnerDetail.setLiveInd(true);

            PartnerDetails updatePartner = partnerDetailsRepository.save(partnerDetail);

            if (null != partnerMapper.getAddress()) {
                List<AddressMapper> addressList = partnerMapper.getAddress();

                for (AddressMapper addressMapper : addressList) {

                    PartnerAddressLink partnerAddressLink = partnerAddressLinkRepository.findByPartnerId(partnerId);
                    String addId = partnerAddressLink.getAddressId();
                    if (null != addId) {

                        /*
                         * for (AddressMapper addressMapperr : addressList) {
                         *
                         * PartnerAddressLink partnerAddressLink =
                         * partnerAddressLinkRepository.findByPartnerId(partnerId); String addId =
                         * partnerAddressLink.getAddressId(); if (null != addId) {
                         */

                        AddressDetails addressDetails = addressRepository.getAddressDetailsByAddressId(addId);
                        if (null != addressDetails) {

                            addressDetails.setLiveInd(false);
                            addressRepository.save(addressDetails);
                        }

                        AddressDetails newAddressDetails = new AddressDetails();
                        // newAddressDetails.setAddressId(addressid);

                        newAddressDetails.setAddressId(addId);
                        System.out.println("ADDID@@@@@@@" + addId);

                        if (null != addressMapper.getAddress1()) {
                            newAddressDetails.setAddressLine1(addressMapper.getAddress1());

                        } else {
                            newAddressDetails.setAddressLine1(addressDetails.getAddressLine1());
                        }

                        if (null != addressMapper.getAddress2()) {
                            newAddressDetails.setAddressLine2(addressMapper.getAddress2());
                        } else {
                            newAddressDetails.setAddressLine2(addressDetails.getAddressLine2());
                        }
                        if (null != addressMapper.getAddressType()) {
                            newAddressDetails.setAddressType(addressMapper.getAddressType());
                        } else {
                            newAddressDetails.setAddressType(addressDetails.getAddressType());
                        }
                        if (null != addressMapper.getTown()) {
                            newAddressDetails.setTown(addressMapper.getTown());
                        } else {
                            newAddressDetails.setTown(addressDetails.getTown());
                        }
                        if (null != addressMapper.getStreet()) {
                            newAddressDetails.setStreet(addressMapper.getStreet());
                        } else {
                            newAddressDetails.setStreet(addressDetails.getStreet());
                        }

                        if (null != addressMapper.getCity()) {
                            newAddressDetails.setCity(addressMapper.getCity());
                        } else {
                            newAddressDetails.setCity(addressDetails.getCity());
                        }

                        if (null != addressMapper.getPostalCode()) {
                            newAddressDetails.setPostalCode(addressMapper.getPostalCode());
                        } else {
                            newAddressDetails.setPostalCode(addressDetails.getPostalCode());
                        }

                        if (null != addressMapper.getState()) {
                            newAddressDetails.setState(addressMapper.getState());
                        } else {
                            newAddressDetails.setState(addressDetails.getState());
                        }

                        if (null != addressMapper.getLatitude()) {
                            newAddressDetails.setLatitude(addressMapper.getLatitude());
                        } else {
                            newAddressDetails.setLatitude(addressDetails.getLatitude());
                        }

                        if (null != addressMapper.getLongitude()) {
                            newAddressDetails.setLongitude(addressMapper.getLongitude());
                        } else {
                            newAddressDetails.setLongitude(addressDetails.getLongitude());
                        }

                        if (null != addressMapper.getHouseNo()) {
                            newAddressDetails.setHouseNo(addressMapper.getHouseNo());
                        } else {
                            newAddressDetails.setHouseNo(addressDetails.getHouseNo());
                        }
                        
                        newAddressDetails.setCreatorId("");
                        newAddressDetails.setCreationDate(new Date());
                        newAddressDetails.setLiveInd(true);
                        AddressDetails addressDetails1 = addressRepository.save(newAddressDetails);
                        String newAddressDetailsId = addressDetails1.getAddressId();

                    }
                }
            }

            resultMapper = getPartnerDetailsById(updatePartner.getPartnerId());
        }

        return resultMapper;
    }

    public PartnerMapper getPartnerMapper(PartnerDetails part) {
        PartnerMapper resultMapper = new PartnerMapper();
        resultMapper.setAccountNumber(part.getAccountNumber());
        resultMapper.setBusinessRegistrationNumber(part.getBusinessRegistrationNumber());
        resultMapper.setCreatorId(part.getCreatorId());
        resultMapper.setNote(part.getNote());
        resultMapper.setPartnerId(part.getPartnerId());
        resultMapper.setPartnerName(part.getPartnerName());
        resultMapper.setTaxRegistrationNumber(part.getTaxRegistrationNumber());
        resultMapper.setUrl(part.getUrl());
        resultMapper.setUserId(part.getUserId());
        resultMapper.setBankName(part.getBankName());
        resultMapper.setPhoneNo(part.getPhoneNo());

        resultMapper.setCreationDate(Utility.getISOFromDate(part.getCreationDate()));

        return resultMapper;
    }

    @Override
    public ContactViewMapper updateContactListByPartnerId(String partnerId, ContactMapper contactMapper) throws IOException, TemplateException {
        ContactViewMapper resultMapper = new ContactViewMapper();
        // sk PartnerContactLink contact1 =
        // partnerContactLinkRepository.getContact1ByPartnerId(partnerId);
        // sk resultMapper = contactService.updateContact(contact1.getContactId(),
        // contactMapper);

        resultMapper = contactService.updateContact(partnerId, contactMapper);
        ThirdParty pem1 = thirdPartyRepository.findByOrgId(resultMapper.getOrganizationId());
        if (null != pem1) {
            resultMapper.setThirdPartyAccessInd(pem1.isPartnerContactInd());
        }

        return resultMapper;

    }

    @Override
    public List<ContactViewMapper> getAllPartnerContatList(String userId, int pageNo, int pageSize) {

        /*
         * Pageable paging = PageRequest.of(pageNo,
         * pageSize,Sort.by("creationDate").descending()); List<PartnerDetails>
         * partnerList =
         * partnerDetailsRepository.findByUserIdAndVendorTypeAndLiveInd(userId,"Vendor",
         * paging);
         *
         * System.out.println("$$$$" + partnerList); // List<PartnerDetails> partner1 =
         * new ArrayList<PartnerDetails>(); List<ContactViewMapper> resultMapper = new
         * ArrayList<ContactViewMapper>();
         *
         * for (PartnerDetails PartnerDetails : partnerList) { String partnerId =
         * PartnerDetails.getPartnerId(); System.out.println("$$$$11" + partnerId);
         * List<PartnerContactLink> partnercontacts =
         * partnerContactLinkRepository.getAllPartner(partnerId);
         *
         * if (null != partnercontacts && !partnercontacts.isEmpty()) { for
         * (PartnerContactLink partnerContactLink : partnercontacts) {
         *
         * String contactId = partnerContactLink.getContactId(); ContactViewMapper
         * contactMapper = contactService.getContactDetailsById(contactId);
         * System.out.println("contactId>>>>>>>>>>" + contactMapper.getContactId());
         * System.out.println("contactId>>>>>>>>>>" + contactMapper.getCreationDate());
         * contactMapper.setPartnerId(partnerId); resultMapper.add(contactMapper); } } }
         * return resultMapper;
         */

        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("creationDate").descending());
        List<ContactViewMapper> resultMapper = new ArrayList<>();
        Page<ContactDetails> contactList = contactRepository.findByUserIdAndContactTypeAndLiveInd(userId, "Vendor", paging);
        System.out.println("###########" + contactList);
        if (null != contactList && !contactList.isEmpty()) {
        	resultMapper = contactList.stream().map(contact -> {
                ContactViewMapper contactMapper = contactService.getContactDetailsById(contact.getContactId());
                if (null != contactMapper.getContactId()) {
                    return contactMapper;
                }
                String middleName = " ";
                String lastName = "";
                String salutation = "";

                if (contact.getSalutation() != null && contact.getSalutation().length() > 0) {
                    salutation = contact.getSalutation();
                }
                if (!StringUtils.isEmpty(contact.getLast_name())) {

                    lastName = contact.getLast_name();
                }

                if (contact.getMiddle_name() != null && contact.getMiddle_name().length() > 0) {

                    middleName = contact.getMiddle_name();
                    contactMapper.setFullName(
                            salutation + " " + contact.getFirst_name() + " " + middleName + " " + lastName);
                } else {

                    contactMapper.setFullName(salutation + " " + contact.getFirst_name() + " " + lastName);
                }
                contactMapper.setPageCount(contactList.getTotalPages());
                contactMapper.setDataCount(contactList.getSize());
                contactMapper.setListCount(contactList.getTotalElements());

                return contactMapper;
            }).collect(Collectors.toList());

        }

        return resultMapper;
    }
    
    
	@Override
    public List<ContactViewMapper> getAllPartnerContatLists(List<String> userId, int pageNo, int pageSize) {

        /*
         * Pageable paging = PageRequest.of(pageNo,
         * pageSize,Sort.by("creationDate").descending()); List<PartnerDetails>
         * partnerList =
         * partnerDetailsRepository.findByUserIdAndVendorTypeAndLiveInd(userId,"Vendor",
         * paging);
         *
         * System.out.println("$$$$" + partnerList); // List<PartnerDetails> partner1 =
         * new ArrayList<PartnerDetails>(); List<ContactViewMapper> resultMapper = new
         * ArrayList<ContactViewMapper>();
         *
         * for (PartnerDetails PartnerDetails : partnerList) { String partnerId =
         * PartnerDetails.getPartnerId(); System.out.println("$$$$11" + partnerId);
         * List<PartnerContactLink> partnercontacts =
         * partnerContactLinkRepository.getAllPartner(partnerId);
         *
         * if (null != partnercontacts && !partnercontacts.isEmpty()) { for
         * (PartnerContactLink partnerContactLink : partnercontacts) {
         *
         * String contactId = partnerContactLink.getContactId(); ContactViewMapper
         * contactMapper = contactService.getContactDetailsById(contactId);
         * System.out.println("contactId>>>>>>>>>>" + contactMapper.getContactId());
         * System.out.println("contactId>>>>>>>>>>" + contactMapper.getCreationDate());
         * contactMapper.setPartnerId(partnerId); resultMapper.add(contactMapper); } } }
         * return resultMapper;
         */

        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("creationDate").descending());
        List<ContactViewMapper> resultMapper = new ArrayList<>();
        Page<ContactDetails> contactList = contactRepository.findByUserIdsAndContactTypeAndLiveInd(userId, "Vendor", paging);
        System.out.println("###########" + contactList);
        if (null != contactList && !contactList.isEmpty()) {
        	resultMapper = contactList.stream().map(contact -> {
                ContactViewMapper contactMapper = contactService.getContactDetailsById(contact.getContactId());
                if (null != contactMapper.getContactId()) {
                    return contactMapper;
                }
                String middleName = " ";
                String lastName = "";
                String salutation = "";

                if (contact.getSalutation() != null && contact.getSalutation().length() > 0) {
                    salutation = contact.getSalutation();
                }
                if (!StringUtils.isEmpty(contact.getLast_name())) {

                    lastName = contact.getLast_name();
                }

                if (contact.getMiddle_name() != null && contact.getMiddle_name().length() > 0) {

                    middleName = contact.getMiddle_name();
                    contactMapper.setFullName(
                            salutation + " " + contact.getFirst_name() + " " + middleName + " " + lastName);
                } else {

                    contactMapper.setFullName(salutation + " " + contact.getFirst_name() + " " + lastName);
                }
                contactMapper.setPageCount(contactList.getTotalPages());
                contactMapper.setDataCount(contactList.getSize());
                contactMapper.setListCount(contactList.getTotalElements());

                return contactMapper;
            }).collect(Collectors.toList());

        }

        return resultMapper;
    }

    @Override
    public String saveSkillSet(PartnerSkillSetMapper skillSetMapper) {
        DefinationDetails definationDetails1 = definationRepository
                .getBySkillNameAndLiveInd(skillSetMapper.getSkillName());
        if (null != definationDetails1) {
            PartnerSkillSet partnerSkillSet = new PartnerSkillSet();
            partnerSkillSet.setSkillName(definationDetails1.getDefinationId());
            partnerSkillSet.setPartnerId(skillSetMapper.getPartnerId());
            partnerSkillSet.setCreationDate(new Date());
            partnerSkillSetRepository.save(partnerSkillSet);
        } else {
            DefinationInfo definationInfo = new DefinationInfo();

            definationInfo.setCreation_date(new Date());
            String id = definationInfoRepository.save(definationInfo).getDefination_info_id();

            DefinationDetails newDefinationDetails = new DefinationDetails();
            newDefinationDetails.setDefinationId(id);
            newDefinationDetails.setName(skillSetMapper.getSkillName());
            newDefinationDetails.setOrg_id(skillSetMapper.getOrgId());
            newDefinationDetails.setUser_id(skillSetMapper.getUserId());
            newDefinationDetails.setCreation_date(new Date());
            newDefinationDetails.setLiveInd(true);
            newDefinationDetails.setEditInd(true);
            definationRepository.save(newDefinationDetails);

            PartnerSkillSet partnerSkillSet = new PartnerSkillSet();
            partnerSkillSet.setSkillName(id);
            partnerSkillSet.setPartnerId(skillSetMapper.getPartnerId());
            partnerSkillSet.setCreationDate(new Date());
            partnerSkillSetRepository.save(partnerSkillSet);

        }

        return skillSetMapper.getSkillName();
    }

    @Override
    public List<PartnerSkillSetMapper> getSkillSetDetails(String partnerId) {
        List<PartnerSkillSet> skillList = partnerSkillSetRepository.getSkillSetById(partnerId);
        if (null != skillList && !skillList.isEmpty()) {
            return skillList.stream().map(skillSet -> {
                PartnerSkillSetMapper skillSetMapper = getSkillSet(skillSet.getSkillSetDetailsId());
                if (null != skillSetMapper.getSkillSetDetailsId()) {
                    return skillSetMapper;
                }
                return null;
            }).collect(Collectors.toList());

        }

        return null;
    }

    @Override
    public PartnerSkillSetMapper getSkillSet(String id) {
        PartnerSkillSet partnerSkillSet = partnerSkillSetRepository.getById(id);
        PartnerSkillSetMapper skillSetMapper = new PartnerSkillSetMapper();

        if (null != skillSetMapper) {
            DefinationDetails definationDetails1 = definationRepository
                    .findByDefinationId(partnerSkillSet.getSkillName());
            if (null != definationDetails1) {
                skillSetMapper.setSkillName(definationDetails1.getName());

            }
            skillSetMapper.setCreationDate(Utility.getISOFromDate(partnerSkillSet.getCreationDate()));
            skillSetMapper.setSkillSetDetailsId(partnerSkillSet.getSkillSetDetailsId());
            skillSetMapper.setPartnerId(partnerSkillSet.getPartnerId());
        }

        return skillSetMapper;
    }

    @Override
    public List<PartnerMapper> getAllPartnerList(int pageNo, int pageSize) {

        List<PartnerMapper> resultMapper = new ArrayList<PartnerMapper>();

        // List<PartnerDetails> partner = partnerDetailsRepository.findAll();
        List<String> userId = permissionRepository.getUserList()
        		.stream()
			    .map(Permission::getUserId)
			    .collect(Collectors.toList());	
//        System.out.println(" user$$$$$$$$$$$$==" + permission.toString());

//        List<String> userId = permission.stream().
//        if (null != permission && !permission.isEmpty()) {
//
////            for (Permission permissionn : permission) {
//
//                List<PartnerMapper> mp = partnerService.getPartnerDetailsListByUserId(permissionn.getUserId(), pageNo,
//                        pageSize);
//
//                System.out.println(" userId$$$$$$$$$$$$==" + permissionn.getUserId());
//                if(null!=mp && !mp.isEmpty()) {
//                resultMapper.addAll(mp);
//                }
//            }
//
//        }
        if (null != userId && !userId.isEmpty()) {
        	resultMapper = partnerService.getPartnerDetailsListByUserIds(userId, pageNo,pageSize);
        }
        
        return resultMapper;

    }

    @Override
    public void deleteSkilsset(String id) {
        if (null != id) {
            PartnerSkillSet partnerSkillSet = partnerSkillSetRepository.getById(id);
            System.out.println("@@@sushi" + partnerSkillSet);
            if (null != partnerSkillSet) {
                partnerSkillSetRepository.delete(partnerSkillSet);
            }
        }

    }
    /*
     * @Override public HashMap getCountList() { List<PartnerDetails>
     * PartnerDetailsList = partnerDetailsRepository.findByActive(true); HashMap map
     * = new HashMap(); map.put("PartnerDetails", PartnerDetailsList.size());
     *
     * return map; }
     */

    @Override
    public HashMap getCountListByuserId(String userId) {
        List<PartnerDetails> PartnerDetailsList = partnerDetailsRepository.findByUserIdandLiveInd(userId);
        HashMap map = new HashMap();
        map.put("PartnerDetails", PartnerDetailsList.size());

        return map;
    }

    @Override
    public void deleteDocumentById(String documentId) {
        if (null != documentId) {
            DocumentDetails document = documentDetailsRepository.getDocumentDetailsById(documentId);

            document.setLive_ind(false);
            documentDetailsRepository.save(document);
        }

    }

    @Override
    public HashMap getPartnerContactCountByuserId(String userId) {
        List<ContactViewMapper> PartnerDetailsList = partnerService.getAllPartnerContatListByUserId(userId);
        HashMap map = new HashMap();
        map.put("record", PartnerDetailsList.size());

        return map;
    }

    /*
     * @Override public List<CandidateMapper> getCandidateListByPartnerId(String
     * partnerId) { List<PartnerCandidateLink> partnerCandidateLinkList =
     * partnerCandidateLinkRepository .getCandidateByPartnerId(partnerId);
     * List<CandidateMapper> resultList = new ArrayList<CandidateMapper>();
     *
     * if (partnerCandidateLinkList != null && !partnerCandidateLinkList.isEmpty())
     * {
     *
     * for (PartnerCandidateLink partnerCandidateLink : partnerCandidateLinkList) {
     *
     * CandidateMapper candidateMapper =
     * candudateService.getCandidate(partnerCandidateLink.getCandidateId());
     * resultList.add(candidateMapper); }
     *
     * }
     *
     * return resultList; }
     */

    @Override
    public List<String> updateTransferOneUserToAnother(String userId, PartnerMapper partnerMapper) {

        List<String> partnerList = partnerMapper.getPartnerIds();
        System.out.println("partnerList::::::::::::::::::::::::::::::::::::::::::::::::::::" + partnerList);

        if (null != partnerList && !partnerList.isEmpty()) {
            for (String partnerId : partnerList) {
                System.out.println("the partner id is : " + partnerId);
                PartnerDetails partnerDetails = partnerDetailsRepository.getpartnerDetailsById(partnerId);
                System.out
                        .println("partnerDetails::::::::::::::::::::::::::::::::::::::::::::::::::::" + partnerDetails);
                partnerDetails.setUserId(userId);
                partnerDetailsRepository.save(partnerDetails);
            }

        }
        return partnerList;
    }

    @Override
    public List<PartnerMapper> getPartnerByBusinessRegistrationNumber(String partnerName, String userId) {
        List<PartnerDetails> detailsList = partnerDetailsRepository
                .findByBusinessRegistrationNumberContainingAndLiveIndAndUserId(partnerName, true, userId);
        List<PartnerMapper> mapperList = new ArrayList<PartnerMapper>();
        if (null != detailsList && !detailsList.isEmpty()) {

            mapperList = detailsList.stream().map(li -> getPartnerDetailsById(li.getPartnerId()))
                    .collect(Collectors.toList());
        }
        return mapperList;
    }

    @Override
    public List<PartnerMapper> getPartnerByTaxRegistrationNumber(String partnerName, String userId) {
        List<PartnerDetails> detailsList = partnerDetailsRepository
                .findByTaxRegistrationNumberContainingAndLiveIndAndUserId(partnerName, true, userId);
        List<PartnerMapper> mapperList = new ArrayList<PartnerMapper>();
        if (null != detailsList && !detailsList.isEmpty()) {

            mapperList = detailsList.stream().map(li -> getPartnerDetailsById(li.getPartnerId()))
                    .collect(Collectors.toList());
        }
        return mapperList;
    }

    @Override
    public boolean emailExistsByWebsite(String email) {
        List<PartnerDetails> list = partnerDetailsRepository.findByEmail(email);
        if (list != null && !list.isEmpty()) {
            return true;
        }
        return false;
    }

    @Override
    public String saveToPartnerProcessForWebsite(PartnerWebsiteMapper partnerMapper) {
        String partnerId = null;
        String contactId = null;

        PartnerInfo partnerInfo = new PartnerInfo();
        partnerInfo.setCreation_date(new Date());
        PartnerInfo partner = partnerInfoRepository.save(partnerInfo);

        partnerId = partner.getPartner_id();

        PartnerDetails partnerDetails = new PartnerDetails();
        setPropertyOnInputForWebsite(partnerMapper, partnerDetails, partnerId);
        partnerDetailsRepository.save(partnerDetails);
        if (null != partnerMapper.getContactMapper()) {
            partnerMapper.getContactMapper().setPartnerId(partnerId);


            ContactInfo contactInfo = new ContactInfo();
            contactInfo.setCreation_date(new Date());

            ContactInfo contactt = contactInfoRepository.save(contactInfo);

            contactId = contactt.getContact_id();
            ContactDetails contact = new ContactDetails();
            contact.setContactId(contactId);

            contact.setEmail_id(partnerMapper.getContactMapper().getEmailId());
            contact.setFirst_name(partnerMapper.getContactMapper().getFirstName());
            contact.setMiddle_name(partnerMapper.getContactMapper().getMiddleName());
            contact.setLast_name(partnerMapper.getContactMapper().getLastName());
            contact.setPhone_number(partnerMapper.getContactMapper().getPhoneNumber());
            contact.setDesignation(partnerMapper.getContactMapper().getDesignation());
            contact.setContactType("Partner");
            contact.setLiveInd(true);
            String middleName = " ";
            String lastName = "";
            String satutation = "";

            if (!StringUtils.isEmpty(contact.getLast_name())) {

                lastName = contact.getLast_name();
            }
            if (contact.getSalutation() != null && contact.getSalutation().length() > 0) {
                satutation = contact.getSalutation();
            }

            if (contact.getMiddle_name() != null && contact.getMiddle_name().length() > 0) {

                middleName = contact.getMiddle_name();
                contact.setFullName(satutation + " " + contact.getFirst_name() + " " + middleName + " " + lastName);

            } else {

                contact.setFullName(satutation + " " + contact.getFirst_name() + " " + lastName);
            }
            contact.setTag_with_company(contact.getFullName());

            contactRepository.save(contact);

            /* insert partnerDocument link table */

            PartnerContactLink partnerContactLink = new PartnerContactLink();
            partnerContactLink.setPartnerId(partnerId);
            partnerContactLink.setContactId(contactId);
            partnerContactLink.setCreationDate(new Date());
            partnerContactLinkRepository.save(partnerContactLink);
        }
        /* insert to notification table */

        NotificationDetails notification = new NotificationDetails();
        notification.setMessage(partnerMapper.getPartnerName() + " Created as a Partner.");
        notification.setMessageReadInd(false);
        notification.setNotificationDate(new Date());
        notification.setNotificationType("Distributor Creation");
        notification.setUser_id(partnerMapper.getUserId());
        notificationRepository.save(notification);

        return partnerId;
    }

    private void setPropertyOnInputForWebsite(PartnerWebsiteMapper partnerMapper, PartnerDetails partnerr,
                                              String partnerId) {

        partnerr.setCreationDate(new Date());

        partnerr.setLiveInd(true);
        partnerr.setPartnerId(partnerId);
        partnerr.setUserId(partnerMapper.getUserId());

        partnerr.setPartnerName(partnerMapper.getPartnerName());
        partnerr.setPhoneNo(partnerMapper.getPhoneNo());
        partnerr.setCountryDialcode(partnerMapper.getCountryDialCode());
        partnerr.setEmail(partnerMapper.getEmail());

        partnerr.setUrl(partnerMapper.getUrl());
        partnerr.setSector(partnerMapper.getSectorId());

        partnerr.setBusinessRegistrationNumber(partnerMapper.getBusinessRegistrationNumber());
        partnerr.setTaxRegistrationNumber(partnerMapper.getTaxRegistrationNumber());
        partnerr.setAccountNumber(partnerMapper.getAccountNumber());
        partnerr.setBankName(partnerMapper.getBankName());
        partnerr.setNote(partnerMapper.getNote());
        partnerr.setStatus(partnerMapper.isStatus());
        partnerr.setTncInd(partnerMapper.getTncInd());
        partnerr.setImageURL(partnerMapper.getImageURL());
        partnerr.setDocument(partnerMapper.getDocument());
        partnerr.setDocumentShareInd(partnerMapper.isDocumentShareInd());
        partnerr.setAssignedTo(partnerMapper.getAssignedTo());
        partnerr.setChannel(partnerMapper.getChannel());

        
        List<String> skillList= partnerMapper.getSkills();
		if(null!=skillList && !skillList.isEmpty()) {
			for (String skillName : skillList) {
				PartnerSkillSet partnerSkillSet = new PartnerSkillSet();
				List<DefinationDetails> definationDetails = definationRepository.findByNameContaining(skillName);
		        for (DefinationDetails definationDetails2 : definationDetails) {
		            if (null != definationDetails2) {
					partnerSkillSet.setSkillName(definationDetails2.getDefinationId());
				}
				
				partnerSkillSet.setPartnerId(partnerId);
				partnerSkillSet.setCreationDate(new Date());
				partnerSkillSetRepository.save(partnerSkillSet);
		        }
			}
		}
        
        
        // partnerr.setFullName(partnerMapper.getFirstName()+"
        // "+partnerMapper.getMiddleName()+" "+partnerMapper.getLastName());
        // partnerr.setStatus(false);

        // partnerr.setSpace_for_bank_details(partnerMapper.getSpaceForBankDetails());
        if (partnerMapper.getAddress().size() > 0) {
            for (AddressMapper addressMapper : partnerMapper.getAddress()) {
                /* insert to address info & address deatils & customeraddressLink */

            	 partnerr.setCountry(addressMapper.getCountry());
            	
                AddressInfo addressInfo = new AddressInfo();
                addressInfo.setCreationDate(new Date());

                AddressInfo addressInfoo = addressInfoRepository.save(addressInfo);

                String addressId = addressInfoo.getId();

                if (null != addressId) {

                    AddressDetails addressDetails = new AddressDetails();
                    addressDetails.setAddressId(addressId);
                    addressDetails.setAddressLine1(addressMapper.getAddress1());
                    addressDetails.setAddressLine2(addressMapper.getAddress2());
                    addressDetails.setAddressType(addressMapper.getAddressType());
                    addressDetails.setCountry(addressMapper.getCountry());
                    addressDetails.setCreationDate(new Date());
                    addressDetails.setStreet(addressMapper.getStreet());
                    addressDetails.setCity(addressMapper.getCity());
                    addressDetails.setPostalCode(addressMapper.getPostalCode());
                    addressDetails.setTown(addressMapper.getTown());
                    addressDetails.setState(addressMapper.getState());
                    addressDetails.setLatitude(addressMapper.getLatitude());
                    addressDetails.setLongitude(addressMapper.getLongitude());
                    addressDetails.setLiveInd(true);
                    addressDetails.setHouseNo(addressMapper.getHouseNo());
                    addressRepository.save(addressDetails);

                    PartnerAddressLink Link = new PartnerAddressLink();
                    Link.setPartnerId(partnerId);

                    System.out.println("@@@@" + partnerId);
                    Link.setAddressId(addressId);
                    Link.setCreationDate(new Date());

                    partnerAddressLinkRepository.save(Link);

                }
            }
        }

        /* insert to Notification Table */
        NotificationDetails notification = new NotificationDetails();
        notification.setNotificationType("Opportunity");
        notification.setOrg_id(partnerMapper.getOrganizationId());
        notification.setUser_id(partnerMapper.getUserId());
        EmployeeDetails emp = employeeRepository.getEmployeesByuserId(partnerMapper.getUserId());
        notification.setAssignedBy(emp.getFullName());
        notification.setAssignedTo(emp.getReportingManager());
        notification.setNotificationDate(new Date());
        notification.setMessage("A Partner is created By " + emp.getFullName());
        notification.setMessageReadInd(false);
        notificationRepository.save(notification);

    }

    @Override
    public ContactViewMapper giveAccessContactToUser(String contactId, ContactViewMapper contactMapper) {

        ContactViewMapper contactMapper1 = null;
        ContactDetails contact = contactRepository.getContactDetailsById(contactId);
        if (null != contact) {
            contact.setAccessInd(contactMapper.getAccessInd());
        }
        contactRepository.save(contact);
        contactMapper1 = contactService.getContactDetailsById(contactId);
        contactMapper1.setPartnerId(contactMapper.getPartnerId());

        return contactMapper1;
    }

    @Override
    public boolean checkSkillInCustomerSkillSet(String skillName, String partnerId) {
        List<DefinationDetails> definationDetails = definationRepository.findByNameContaining(skillName);
        for (DefinationDetails definationDetails2 : definationDetails) {
            if (null != definationDetails2) {
                List<PartnerSkillSet> partnerSkillLink = partnerSkillSetRepository.findBySkillNameAndPartnerId(definationDetails2.getDefinationId(), partnerId);
                if (partnerSkillLink.size() > 0) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public List<PartnerMapper> getAllPartnerListByOrgId(String loggedOrgId) {

        return partnerDetailsRepository.findByOrgIdAndLiveInd(loggedOrgId, true).stream().map(c -> getPartnerDetailsById(c.getPartnerId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<PartnerMapper> getPartnerDetailsListByUserId(String userId) {

        return partnerDetailsRepository.getPartnerListByUserId(userId).stream().map(c -> getPartnerDetailsById(c.getPartnerId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<PartnerMapper> getAllPartnerListCount() {

        List<Permission> permission = permissionRepository.getUserList();
        System.out.println(" user$$$$$$$$$$$$==" + permission.toString());

        if (null != permission && !permission.isEmpty()) {

            return permission.stream().map(permissionn->{
                List<PartnerMapper> resultMapperr = partnerService
                        .getPartnerDetailsListByUserId(permissionn.getUserId());

                System.out.println(" userId$$$$$$$$$$$$==" + permissionn.getUserId());

                return resultMapperr;
            }).flatMap(l->l.stream()).collect(Collectors.toList());

        }
        return null;

    }

    @Override
    public List<ContactViewMapper> getAllPartnerContatListByUserId(String userId) {

        List<ContactDetails> contactList = contactRepository.findByUserIdAndContactType(userId, "Vendor");
        System.out.println("###########" + contactList);
        if (null != contactList && !contactList.isEmpty()) {
            return contactList.stream().map(contact -> {
                ContactViewMapper contactMapper = contactService.getContactDetailsById(contact.getContactId());
                if (null != contactMapper.getContactId()) {
                    return contactMapper;
                }

                String middleName = " ";
                String lastName = "";
                String salutation = "";

                if (contact.getSalutation() != null && contact.getSalutation().length() > 0) {
                    salutation = contact.getSalutation();
                }
                if (!StringUtils.isEmpty(contact.getLast_name())) {

                    lastName = contact.getLast_name();
                }

                if (contact.getMiddle_name() != null && contact.getMiddle_name().length() > 0) {

                    middleName = contact.getMiddle_name();
                    contactMapper.setFullName(
                            salutation + " " + contact.getFirst_name() + " " + middleName + " " + lastName);
                } else {

                    contactMapper.setFullName(salutation + " " + contact.getFirst_name() + " " + lastName);
                }
                return null;
            }).collect(Collectors.toList());

        }

        return null;
    }

	
	@Override
	public List<PartnerMapperForDropDown> getAllPartnerListForDropDown() {

		List<PartnerMapperForDropDown> resultMapper = new ArrayList<PartnerMapperForDropDown>();

		// List<PartnerDetails> partner = partnerDetailsRepository.findAll();
		List<Permission> permission = permissionRepository.getUserList();
		System.out.println(" user$$$$$$$$$$$$==" + permission.toString());

		if (null != permission && !permission.isEmpty()) {

			for (Permission permissionn : permission) {
		
				//List<PartnerMapper> mp = partnerService.getPartnerDetailsListByUserId(permissionn.getUserId());
				List<PartnerDetails> mp =partnerDetailsRepository.getPartnerListByUserId(permissionn.getUserId());
				if (null != mp && !mp.isEmpty()) {
					for (PartnerDetails partner : mp) {
						PartnerMapperForDropDown partnerMapperForDropDown = new PartnerMapperForDropDown();
						
						partnerMapperForDropDown.setPartnerId(partner.getPartnerId());
						partnerMapperForDropDown.setPartnerName(partner.getPartnerName());
						partnerMapperForDropDown.setCreationDate(Utility.getISOFromDate(partner.getCreationDate()));
						
						resultMapper.add(partnerMapperForDropDown);
					}
					}
				
			}

		}
		return resultMapper;

	}
	
	@Override
	public String deleteAndReinstatePartnerByPartnerId(String partnerId, PartnerMapper partnerMapper) {
		
		String message =null;
		
		PartnerDetails partnerList = partnerDetailsRepository.getPartnerDetailsByIdAndLiveInd(partnerId);
		if(null!=partnerList) {
			if(partnerMapper.isReInStateInd()==true) {
			partnerList.setReInStateInd(partnerMapper.isReInStateInd());
			partnerDetailsRepository.save(partnerList);
			message = "Partner deleted successfully";
			}else {
				partnerList.setReInStateInd(partnerMapper.isReInStateInd());
				partnerDetailsRepository.save(partnerList);
				message = "Partner reinstate successfully";
			}
		}

		return message;
	}

	@Override
	public List<PartnerMapper> getDeletedPartnerDetailsByUserId(String userId, int pageNo, int pageSize) {
		Pageable paging = PageRequest.of(pageNo, pageSize,Sort.by("creationDate").descending());
		System.out.println("userId=============++++++"+userId);
		List<PartnerDetails> partnerList = partnerDetailsRepository.getDeletedpartnerListPageByUserId(userId,paging);
		System.out.println("partnerList=============++++++"+partnerList.toString());
		List<PartnerMapper> mapperList = new ArrayList<PartnerMapper>();
		for (PartnerDetails partnerDetails : partnerList) {
			System.out.println("partner id........"+partnerDetails.getPartnerId());

			PartnerMapper partnerMapperr = getPartnerDetailsById(partnerDetails.getPartnerId());
			
			mapperList.add(partnerMapperr);
		
		}
		return mapperList;
	}
	
	@Override
    public List<ContactViewMapperForDropDown> getAllPartnerContatListForDropDown(String userId) {

        List<ContactDetails> contactList = contactRepository.findByUserIdAndContactType(userId, "Vendor");
        System.out.println("###########" + contactList);
        if (null != contactList && !contactList.isEmpty()) {
            return contactList.stream().map(contact -> {
            	ContactViewMapperForDropDown contactMapper = new ContactViewMapperForDropDown();
            
            	contactMapper.setContactId(contact.getContactId());
				contactMapper.setCreationDate(Utility.getISOFromDate(contact.getCreationDate()));
				contactMapper.setPartnerId(contact.getTag_with_company());

                String middleName = " ";
                String lastName = "";
                String salutation = "";

                if (contact.getSalutation() != null && contact.getSalutation().length() > 0) {
                    salutation = contact.getSalutation();
                }
                if (!StringUtils.isEmpty(contact.getLast_name())) {

                    lastName = contact.getLast_name();
                }

                if (contact.getMiddle_name() != null && contact.getMiddle_name().length() > 0) {

                    middleName = contact.getMiddle_name();
                    contactMapper.setFullName(
                            salutation + " " + contact.getFirst_name() + " " + middleName + " " + lastName);
                } else {

                    contactMapper.setFullName(salutation + " " + contact.getFirst_name() + " " + lastName);
                }
                return contactMapper;
            }).collect(Collectors.toList());

        }

        return null;
    }

	@Override
	public List<ContactViewMapperForDropDown> getAllPartnerContactListForDropDown() {
		List<ContactViewMapperForDropDown> resultMapper = new ArrayList<ContactViewMapperForDropDown>();

		// List<PartnerDetails> partner = partnerDetailsRepository.findAll();
		List<Permission> permission = permissionRepository.getUserList();
		System.out.println(" user$$$$$$$$$$$$==" + permission.toString());

		if (null != permission && !permission.isEmpty()) {

			for (Permission permissionn : permission) {
		
				//List<PartnerMapper> mp = partnerService.getPartnerDetailsListByUserId(permissionn.getUserId());
				List<ContactDetails> contactList = contactRepository.getPartnerContactByUserIdAndContactTypeAndLiveInd(permissionn.getUserId(), "Vendor");
				if (null != contactList && !contactList.isEmpty()) {
					for (ContactDetails contact : contactList) {
						ContactViewMapperForDropDown contactMapper = new ContactViewMapperForDropDown();
						
						contactMapper.setContactId(contact.getContactId());
						contactMapper.setCreationDate(Utility.getISOFromDate(contact.getCreationDate()));
						contactMapper.setPartnerId(contact.getTag_with_company());
						
						String middleName =" ";
						String lastName ="";
						String salutation ="";

						 if(contact.getSalutation() != null && contact.getSalutation().length()>0) {
							salutation = contact.getSalutation();
						 }
						if(!StringUtils.isEmpty(contact.getLast_name())) {

							 lastName = contact.getLast_name();
						 }


						 if(contact.getMiddle_name() != null && contact.getMiddle_name().length()>0) {


							 middleName = contact.getMiddle_name();
							 contactMapper.setFullName(salutation+" "+contact.getFirst_name()+" "+middleName+" "+lastName);
						 }else {

							 contactMapper.setFullName(salutation+" "+contact.getFirst_name()+" "+lastName);
						 }
						
						resultMapper.add(contactMapper);
					}
					}
				
			}

		}
		return resultMapper;

	}

	@Override
	public List<ContactViewMapper> getAllPartnerContatLists(String userId, int pageNo, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}

