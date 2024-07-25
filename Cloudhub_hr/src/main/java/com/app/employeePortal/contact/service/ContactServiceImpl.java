package com.app.employeePortal.contact.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.NonUniqueResultException;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.app.employeePortal.Opportunity.entity.OpportunityContactLink;
import com.app.employeePortal.Opportunity.entity.OpportunityDetails;
import com.app.employeePortal.Opportunity.repository.OpportunityContactLinkRepository;
import com.app.employeePortal.Opportunity.repository.OpportunityDetailsRepository;
import com.app.employeePortal.Team.entity.Team;
import com.app.employeePortal.Team.entity.TeamMemberLink;
import com.app.employeePortal.Team.repository.TeamMemberLinkRepo;
import com.app.employeePortal.Team.repository.TeamRepository;
import com.app.employeePortal.address.entity.AddressDetails;
import com.app.employeePortal.address.entity.AddressInfo;
import com.app.employeePortal.address.mapper.AddressMapper;
import com.app.employeePortal.address.repository.AddressInfoRepository;
import com.app.employeePortal.address.repository.AddressRepository;
import com.app.employeePortal.call.entity.CallDetails;
import com.app.employeePortal.call.entity.ContactCallLink;
import com.app.employeePortal.call.entity.EmployeeCallLink;
import com.app.employeePortal.call.repository.CallDetailsRepository;
import com.app.employeePortal.call.repository.ContactCallLinkRepo;
import com.app.employeePortal.call.repository.EmployeeCallRepository;
import com.app.employeePortal.contact.entity.ContactAddressLink;
import com.app.employeePortal.contact.entity.ContactDetails;
import com.app.employeePortal.contact.entity.ContactDocumentLink;
import com.app.employeePortal.contact.entity.ContactInfo;
import com.app.employeePortal.contact.entity.ContactNotesLink;
import com.app.employeePortal.contact.entity.ContactType;
import com.app.employeePortal.contact.entity.ContactUserLink;
import com.app.employeePortal.contact.mapper.ContactMapper;
import com.app.employeePortal.contact.mapper.ContactReportMapper;
import com.app.employeePortal.contact.mapper.ContactTypeMapper;
import com.app.employeePortal.contact.mapper.ContactViewMapper;
import com.app.employeePortal.contact.mapper.ContactViewMapperForDropDown;
import com.app.employeePortal.contact.repository.ContactAddressLinkRepository;
import com.app.employeePortal.contact.repository.ContactDocumentLinkRepository;
import com.app.employeePortal.contact.repository.ContactInfoRepository;
import com.app.employeePortal.contact.repository.ContactNotesLinkRepository;
import com.app.employeePortal.contact.repository.ContactRepository;
import com.app.employeePortal.contact.repository.ContactTypeRepository;
import com.app.employeePortal.contact.repository.ContactUserLinkRepository;
import com.app.employeePortal.customer.entity.Customer;
import com.app.employeePortal.customer.entity.CustomerContactLink;
import com.app.employeePortal.customer.entity.CustomerRecruitUpdate;
import com.app.employeePortal.customer.mapper.CustomerResponseMapper;
import com.app.employeePortal.customer.mapper.TransferMapper;
import com.app.employeePortal.customer.repository.CustomerContactLinkRepository;
import com.app.employeePortal.customer.repository.CustomerRecruitUpdateRepository;
import com.app.employeePortal.customer.repository.CustomerRepository;
import com.app.employeePortal.document.entity.DocumentDetails;
import com.app.employeePortal.document.mapper.DocumentMapper;
import com.app.employeePortal.document.repository.DocumentDetailsRepository;
import com.app.employeePortal.document.repository.DocumentTypeRepository;
import com.app.employeePortal.document.service.DocumentService;
import com.app.employeePortal.email.service.EmailService;
import com.app.employeePortal.employee.entity.EmployeeDetails;
import com.app.employeePortal.employee.entity.EmployeeInfo;
import com.app.employeePortal.employee.entity.Notes;
import com.app.employeePortal.employee.mapper.EmployeeShortMapper;
import com.app.employeePortal.employee.mapper.NotesMapper;
import com.app.employeePortal.employee.repository.EmployeeInfoRepository;
import com.app.employeePortal.employee.repository.EmployeeRepository;
import com.app.employeePortal.employee.repository.NotesRepository;
import com.app.employeePortal.employee.service.EmployeeService;
import com.app.employeePortal.event.entity.ContactEventLink;
import com.app.employeePortal.event.entity.EmployeeEventLink;
import com.app.employeePortal.event.entity.EventDetails;
import com.app.employeePortal.event.repository.ContactEventRepo;
import com.app.employeePortal.event.repository.EmployeeEventRepository;
import com.app.employeePortal.event.repository.EventDetailsRepository;
import com.app.employeePortal.investor.entity.Investor;
import com.app.employeePortal.investor.entity.InvestorContactLink;
import com.app.employeePortal.investor.entity.InvestorOppContactLink;
import com.app.employeePortal.investor.entity.InvestorOpportunity;
import com.app.employeePortal.investor.repository.InvestorContactLinkRepo;
import com.app.employeePortal.investor.repository.InvestorOppContactLinkRepo;
import com.app.employeePortal.investor.repository.InvestorOpportunityRepo;
import com.app.employeePortal.investor.repository.InvestorRepository;
import com.app.employeePortal.investorleads.entity.InvestorLeads;
import com.app.employeePortal.investorleads.repository.InvestorLeadsRepository;
import com.app.employeePortal.leads.entity.Leads;
import com.app.employeePortal.leads.mapper.ActivityMapper;
import com.app.employeePortal.leads.repository.LeadsRepository;
import com.app.employeePortal.notification.entity.Notificationparam;
import com.app.employeePortal.notification.repository.NotificationRepository;
import com.app.employeePortal.notification.service.NotificationService;
import com.app.employeePortal.organization.entity.OrganizationDetails;
import com.app.employeePortal.organization.repository.OrganizationRepository;
import com.app.employeePortal.partner.entity.PartnerDetails;
import com.app.employeePortal.partner.repository.PartnerDetailsRepository;
import com.app.employeePortal.partner.service.PartnerService;
import com.app.employeePortal.permission.entity.Permission;
import com.app.employeePortal.permission.repository.PermissionRepository;
import com.app.employeePortal.permission.repository.ThirdPartyRepository;
import com.app.employeePortal.recruitment.entity.OpportunityRecruitDetails;
import com.app.employeePortal.recruitment.entity.RecruitProfileLinkDetails;
import com.app.employeePortal.recruitment.repository.RecruitmentOpportunityDetailsRepository;
import com.app.employeePortal.recruitment.repository.RecruitmentProfileDetailsRepository;
import com.app.employeePortal.registration.entity.Country;
import com.app.employeePortal.registration.entity.Department;
import com.app.employeePortal.registration.entity.Designation;
import com.app.employeePortal.registration.entity.UserSettings;
import com.app.employeePortal.registration.repository.CountryRepository;
import com.app.employeePortal.registration.repository.DepartmentRepository;
import com.app.employeePortal.registration.repository.DesignationRepository;
import com.app.employeePortal.registration.repository.UserSessionRepository;
import com.app.employeePortal.registration.repository.UserSettingsRepository;
import com.app.employeePortal.registration.service.RegistrationService;
import com.app.employeePortal.sector.entity.SectorDetails;
import com.app.employeePortal.sector.repository.SectorDetailsRepository;
import com.app.employeePortal.source.entity.Source;
import com.app.employeePortal.source.repository.SourceRepository;
import com.app.employeePortal.task.entity.ContactTaskLink;
import com.app.employeePortal.task.entity.EmployeeTaskLink;
import com.app.employeePortal.task.entity.TaskDetails;
import com.app.employeePortal.task.entity.TaskDocumentLink;
import com.app.employeePortal.task.repository.ContactTaskRepo;
import com.app.employeePortal.task.repository.EmployeeTaskRepository;
import com.app.employeePortal.task.repository.TaskDetailsRepository;
import com.app.employeePortal.task.repository.TaskDocumentLinkRepository;
import com.app.employeePortal.util.Utility;

import freemarker.template.TemplateException;

@Service
@Transactional
public class ContactServiceImpl implements ContactService {
	@Autowired
	InvestorOppContactLinkRepo investorOppContactLinkRepo;
	@Autowired
	ContactRepository contactRepository;
	@Autowired
	AddressInfoRepository addressInfoRepository;
	@Autowired
	AddressRepository addressRepository;
	@Autowired
	ContactAddressLinkRepository contactAddressLinkRepository;
	@Autowired
	ContactNotesLinkRepository contactNotesLinkRepository;
	@Autowired
	NotesRepository notesRepository;
	@Autowired
	ThirdPartyRepository thirdPartyRepository;
	@Autowired
	ContactTypeRepository contactTypeRepository;

	@Autowired
	ContactInfoRepository contactInfoRepository;
	@Autowired
	DocumentDetailsRepository documentDetailsRepository;
	@Autowired
	DocumentTypeRepository documentTypeRepository;
	@Autowired
	ContactDocumentLinkRepository contactDocumentLinkRepository;
	@Autowired
	PartnerService partnerService;
	@Autowired
	CustomerContactLinkRepository customerContactLinkRepository;
	@Autowired
	OpportunityContactLinkRepository opportunityContactLinkRepository;
	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	PermissionRepository permissionRepository;
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	OpportunityDetailsRepository opportunityDetailsRepository;

	@Autowired
	InvestorContactLinkRepo investorContactLinkRepo;

	@Autowired
	ContactService contactService;
	@Autowired
	NotificationRepository notificationRepository;
	@Autowired
	PartnerDetailsRepository partnerDetailsRepository;
	@Autowired
	DesignationRepository designationRepository;
	@Autowired
	DepartmentRepository departmentRepository;
	@Autowired
	SectorDetailsRepository sectorDetailsRepository;
	@Autowired
	UserSettingsRepository userSettingsRepository;
	@Autowired
	ContactUserLinkRepository contactUserLinkRepository;
	@Autowired
	EmployeeInfoRepository employeeInfoRepository;
	@Autowired
	UserSessionRepository userSessionRepository;
	@Autowired
	RecruitmentOpportunityDetailsRepository recruitmentOpportunityDetailsRepository;
	@Autowired
	RecruitmentProfileDetailsRepository recruitmentProfileDetailsRepository;
	@Autowired
	RegistrationService registrationService;
	@Autowired
	EmailService emailService;
	@Autowired
	CustomerRecruitUpdateRepository customerRecruitUpdateRepository;
	@Autowired
	OrganizationRepository organizationRepository;
	@Autowired
	LeadsRepository leadsRepository;
	@Autowired
	InvestorRepository investorRepository;
	@Autowired
	InvestorLeadsRepository investorLeadsRepository;
	@Autowired
	InvestorOpportunityRepo investorOpportunityRepo;
	@Autowired
	EmployeeService employeeService;
	@Autowired
	ContactCallLinkRepo contactCallLinkRepo;
	@Autowired
	ContactEventRepo contactEventRepo;
	@Autowired
	ContactTaskRepo contactTaskRepo;
	@Autowired
	CallDetailsRepository callDetailsRepository;
	@Autowired
	EventDetailsRepository eventDetailsRepository;
	@Autowired
	TaskDetailsRepository taskDetailsRepository;
	@Autowired
	TeamRepository teamRepository;
	@Autowired
	TeamMemberLinkRepo teamMemberLinkRepo;
	@Autowired
	EmployeeCallRepository employeeCallRepository;
	@Autowired
	EmployeeTaskRepository employeeTaskRepository;
	@Autowired
	EmployeeEventRepository employeeEventRepository;
	@Autowired
	TaskDocumentLinkRepository taskDocumentLinkRepository;
	@Autowired
	CountryRepository countryRepository;

	@Autowired
	SourceRepository sourceRepository;

	private String[] contact_headings = { "ContactId", "First Name", "Middle Name", "Last Name", "Dial Code", "Mobile#",
			"Phone#", "Email", "Linkedin", "Company", "Department", "Designation" };
	@Autowired
	NotificationService notificationService;

	@Value("${companyName}")
	private String companyName;

	@Autowired
	DocumentService documentService;

	@Override
	public ContactViewMapper saveContact(ContactMapper contactMapper) throws IOException, TemplateException {
		String contactId = null;
		ContactViewMapper resultMapper = new ContactViewMapper();

		// if(contactMapper!=null) {

		ContactInfo contactInfo = new ContactInfo();
		contactInfo.setCreation_date(new Date());

		ContactInfo contactt = contactInfoRepository.save(contactInfo);

		contactId = contactt.getContact_id();
		System.out.println("contact id.........." + contactId);

		ContactDetails contact = new ContactDetails();

		setPropertyOnInput(contactMapper, contact, contactId);

		ContactDetails dbContact = contactRepository.save(contact);

		System.out.println("contactdetails id.........." + dbContact.getContact_details_id());

		/* insert to notification table */
		Notificationparam param = new Notificationparam();
		EmployeeDetails emp = employeeRepository.getEmployeesByuserId(contactMapper.getUserId());
		String name = employeeService.getEmployeeFullNameByObject(emp);
		param.setEmployeeDetails(emp);
		param.setAdminMsg("Contact " + "'" + Utility.FullName(contactMapper.getFirstName(),
				contactMapper.getMiddleName(), contactMapper.getLastName()) + "' created by " + name);
		param.setOwnMsg("Contact " + Utility.FullName(contactMapper.getFirstName(), contactMapper.getMiddleName(),
				contactMapper.getLastName()) + " created.");
		param.setNotificationType("Contact creation");
		param.setProcessNmae("Contact");
		param.setType("create");
		param.setEmailSubject("Korero alert- Contact created");
		param.setCompanyName(companyName);
		param.setUserId(contactMapper.getUserId());
		notificationService.createNotificationForDynamicUsers(param);
		resultMapper = getContactDetailsById(contactId);

		return resultMapper;
	}

	private void setPropertyOnInput(ContactMapper contactMapperr, ContactDetails contact, String contactId) {

		contact.setContactId(contactId);
		contact.setSalutation(contactMapperr.getSalutation());
		contact.setFirst_name(contactMapperr.getFirstName());
		contact.setMiddle_name(contactMapperr.getMiddleName());
		contact.setLast_name(contactMapperr.getLastName());
		contact.setMobile_number(contactMapperr.getMobileNumber());
		contact.setPhone_number(contactMapperr.getPhoneNumber());
		contact.setEmail_id(contactMapperr.getEmailId());
		contact.setLinkedin_public_url(contactMapperr.getLinkedinPublicUrl());
		// contact.setTag_with_company(contactMapperr.getTagWithCompany());
		contact.setDepartment(contactMapperr.getDepartmentId());
		contact.setDesignation(contactMapperr.getDesignationTypeId());
		contact.setSector(contactMapperr.getSectorId());
		contact.setSalary(contactMapperr.getSalary());
		contact.setNotes(contactMapperr.getNotes());
		contact.setCountry_dialcode(contactMapperr.getCountryDialCode());
		contact.setCountry_dialcode1(contactMapperr.getCountryDialCode1());
		contact.setImageId(contactMapperr.getImageId());
		contact.setCreationDate(new Date());
		contact.setContactId(contactId);
		contact.setAccessInd(0);
		contact.setWhatsappNumber(contactMapperr.getWhatsappNumber());
		contact.setSource(contactMapperr.getSource());
		contact.setSourceUserId(contactMapperr.getSourceUserId());
		contact.setBedroom(contactMapperr.getBedrooms());
		contact.setPrice(contactMapperr.getPrice());
		contact.setPropertyType(contactMapperr.getPropertyType());
		contact.setCountry(contactMapperr.getCountry());

		String middleName3 = " ";
		String lastName3 = "";

		if (!StringUtils.isEmpty(contactMapperr.getLastName())) {

			lastName3 = contactMapperr.getLastName();
		}
		if (contactMapperr.getMiddleName() != null && contactMapperr.getMiddleName().length() > 0) {

			middleName3 = contactMapperr.getMiddleName();
			contact.setFullName(contactMapperr.getFirstName() + " " + middleName3 + " " + lastName3);
		} else {

			contact.setFullName(contactMapperr.getFirstName() + " " + lastName3);
		}

		// contact.setFullName(contactMapperr.getFirstName()+"
		// "+contactMapperr.getMiddleName()+" "+contactMapperr.getLastName());

		if (null != contactMapperr.getCustomerId()) {
			contact.setContactType("Customer");
			contact.setTag_with_company(contactMapperr.getCustomerId());
		} else if (null != contactMapperr.getPartnerId()) {
			contact.setContactType("Vendor");
			contact.setTag_with_company(contactMapperr.getPartnerId());
		}

		else if (null != contactMapperr.getLeadsId()) {
			contact.setContactType("Leads");
			contact.setTag_with_company(contactMapperr.getLeadsId());
		}
		// Investor contact Link
		else if (!StringUtils.isEmpty(contactMapperr.getInvestorId())) {

			contact.setContactType("Investor");
			contact.setTag_with_company(contactMapperr.getInvestorId());
		}

		else if (null != contactMapperr.getInvestorLeadsId()) {
			contact.setContactType("InvestorLeads");
			contact.setTag_with_company(contactMapperr.getInvestorLeadsId());
		} else {
			contact.setContactType("B2C");
		}

		contact.setLiveInd(true);

		if (!StringUtils.isEmpty(contactMapperr.getUserId())) {
			contact.setUser_id(contactMapperr.getUserId());
		}
		if (!StringUtils.isEmpty(contactMapperr.getOrganizationId())) {
			contact.setOrganization_id(contactMapperr.getOrganizationId());
		}
		if (!StringUtils.isEmpty(contactMapperr.getCustomerId())) {
			contact.setCustomerId(contactMapperr.getCustomerId());
		}
		// String contactType = contact.getContactType();
		contactRepository.save(contact);

		if (contactMapperr.getAddress().size() > 0) {
			for (AddressMapper addressMapper : contactMapperr.getAddress()) {

				/* insert to address info & address deatils & customeraddressLink */
				AddressInfo addressInfo = new AddressInfo();
				addressInfo.setCreationDate(new Date());
				addressInfo.setCreatorId(addressMapper.getCreatorId());

				AddressInfo info = addressInfoRepository.save(addressInfo);

				String addressId = info.getId();

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
					addressDetails.setXlAddress(addressMapper.getXlAddress());
					addressRepository.save(addressDetails);

					ContactAddressLink contactAddressLink = new ContactAddressLink();
					contactAddressLink.setContact_id(contactId);
					contactAddressLink.setAddress_id(addressId);
					contactAddressLink.setCreation_date(new Date());
					contactAddressLinkRepository.save(contactAddressLink);

				}
			}
		}
		/* insert to notes */
		Notes notes = new Notes();
		notes.setNotes(contactMapperr.getNotes());
		notes.setCreation_date(new Date());
		notes.setLiveInd(true);
		Notes note = notesRepository.save(notes);
		String notesId = note.getNotes_id();

		/* insert to contact-notes-link */
		ContactNotesLink contactNotesLink = new ContactNotesLink();
		contactNotesLink.setContact_id(contactId);
		contactNotesLink.setNotesId(notesId);
		contactNotesLink.setCreation_date(new Date());
		contactNotesLinkRepository.save(contactNotesLink);

		/* insert to customer-contact-link */
		if (!StringUtils.isEmpty(contactMapperr.getCustomerId())) {
			CustomerContactLink customerContactLink = new CustomerContactLink();
			customerContactLink.setCustomerId(contactMapperr.getCustomerId());
			customerContactLink.setContactId(contactId);
			customerContactLink.setCreationDate(new Date());
			customerContactLinkRepository.save(customerContactLink);
		}

		/* insert to oppotunity-contact-link */
		if (!StringUtils.isEmpty(contactMapperr.getOpportunityId())) {
			OpportunityContactLink opportunityContactLink = new OpportunityContactLink();
			opportunityContactLink.setOpportunityId(contactMapperr.getOpportunityId());
			opportunityContactLink.setContactId(contactId);
			opportunityContactLink.setCreationDate(new Date());
			opportunityContactLinkRepository.save(opportunityContactLink);
		}
		/* insert to Investor-contact-link */
		if (!StringUtils.isEmpty(contactMapperr.getInvestorId())) {
			InvestorContactLink investorContactLink = new InvestorContactLink();
			investorContactLink.setContactId(contactId);
			investorContactLink.setInvestorId(contactMapperr.getInvestorId());
			investorContactLink.setCreationDate(new Date());
			investorContactLinkRepo.save(investorContactLink);
		}
		// InvestorOpp contact Link
		if (!StringUtils.isEmpty(contactMapperr.getInvOpportunityId())) {
			InvestorOppContactLink investorOppContactLink = new InvestorOppContactLink();
			investorOppContactLink.setInvOpportunityId(contactMapperr.getInvOpportunityId());
			investorOppContactLink.setContactId(contactId);
			investorOppContactLink.setCreationDate(new Date());
			investorOppContactLinkRepo.save(investorOppContactLink);
		}
	}

	@Override
	public ContactViewMapper getContactDetailsById(String contactId) {
		System.out.println("contact QC id ............." + contactId);

		ContactDetails contact = contactRepository.getContactDetailsById(contactId);

		List<ContactAddressLink> contactAddressList = contactAddressLinkRepository.getAddressListByContactId(contactId);
		List<AddressMapper> addressList = new ArrayList<AddressMapper>();

		ContactViewMapper contactMapper = new ContactViewMapper();

		if (null != contact) {
			contactMapper.setContactId(contactId);
			contactMapper.setFirstName(contact.getFirst_name());
			contactMapper.setMiddleName(contact.getMiddle_name());
			contactMapper.setLastName(contact.getLast_name());
			contactMapper.setMobileNumber(contact.getMobile_number());
			contactMapper.setPhoneNumber(contact.getPhone_number());
			contactMapper.setEmailId(contact.getEmail_id());
			contactMapper.setLinkedinPublicUrl(contact.getLinkedin_public_url());
//			contactMapper.setDepartment(contact.getDepartment());
			contactMapper.setCountryDialCode(contact.getCountry_dialcode());
			contactMapper.setCountryDialCode1(contact.getCountry_dialcode1());
			contactMapper.setSalary(contact.getSalary());
			contactMapper.setNotes(contact.getNotes());
			contactMapper.setUserId(contact.getUser_id());
			contactMapper.setOrganizationId(contact.getOrganization_id());
			contactMapper.setCustomerId(contact.getCustomerId());
			contactMapper.setAccessInd(contact.getAccessInd());
			contactMapper.setContactRole(contact.getContactRole());
			contactMapper.setWhatsappNumber(contact.getWhatsappNumber());
			contactMapper.setCreationDate(Utility.getISOFromDate(contact.getCreationDate()));
			contactMapper.setBedrooms(contact.getBedroom());
			contactMapper.setPrice(contact.getPrice());
			contactMapper.setPropertyType(contact.getPropertyType());

			if (!StringUtils.isEmpty(contact.getCountry())) {
				contactMapper.setCountry(contact.getCountry());
				Country country = countryRepository.getCountryDetailsByCountryNameAndOrgId(contact.getCountry(),
						contact.getOrganization_id());
				if (null != country) {
					contactMapper.setCountry_alpha3_code(country.getCountry_alpha3_code());
					contactMapper.setCountry_alpha2_code(country.getCountry_alpha2_code());
				}
			} else {
				List<ContactAddressLink> contactAddressLinks = contactAddressLinkRepository
						.getAddressListByContactId(contact.getContactId());
				if (null != contactAddressLinks && !contactAddressLinks.isEmpty()) {
					for (ContactAddressLink contactAddressLink : contactAddressLinks) {
						System.out.println("contactid=====" + contactAddressLink.getContact_id());
						AddressDetails addressDetails = addressRepository
								.getAddressDetailsByAddressId(contactAddressLink.getAddress_id());
						if (null != addressDetails) {
							if (!StringUtils.isEmpty(addressDetails.getCountry())) {
								contactMapper.setCountry(addressDetails.getCountry());
								Country country = countryRepository.getCountryDetailsByCountryNameAndOrgId(
										addressDetails.getCountry(), contact.getOrganization_id());
								if (null != country) {
									contactMapper.setCountry_alpha3_code(country.getCountry_alpha3_code());
									contactMapper.setCountry_alpha2_code(country.getCountry_alpha2_code());
								}
							}
						}
					}
				}
			}

			if (null != contact.getDepartment() && !contact.getDepartment().isEmpty()) {
				Department department = departmentRepository.getDepartmentDetailsById(contact.getDepartment());
				if (null != department) {
					contactMapper.setDepartment(department.getDepartmentName());
				}
			}
			contactMapper.setSourceUserId(contact.getSourceUserId());
			contactMapper.setSourceUserName(employeeService.getEmployeeFullName(contact.getSourceUserId()));

			if (null != contact.getSource() && !contact.getSource().isEmpty()) {
				Source source = sourceRepository.findBySourceId(contact.getSource());
				if (null != source) {
					contactMapper.setSource(source.getName());
				}
			}

			String middleName1 = " ";
			String lastName1 = "";
			String salutation = "";

			if (contact.getSalutation() != null && contact.getSalutation().length() > 0) {
				salutation = contact.getSalutation();
			}
			if (!StringUtils.isEmpty(contact.getLast_name())) {

				lastName1 = contact.getLast_name();
			}

			if (contact.getMiddle_name() != null && contact.getMiddle_name().length() > 0) {

				middleName1 = contact.getMiddle_name();
				contactMapper
						.setFullName(salutation + " " + contact.getFirst_name() + " " + middleName1 + " " + lastName1);
			} else {

				contactMapper.setFullName(salutation + " " + contact.getFirst_name() + " " + lastName1);
			}

			if (null != contact.getContactType()) {
				if (contact.getContactType().equalsIgnoreCase("customer")) {
					List<OpportunityDetails> opportunityList = opportunityDetailsRepository
							.getOpportunityListByContactIdAndLiveIndAndReinstateIndAndCloseIndAndLostIndAndWonInd(
									contactId);
					if (null != opportunityList && !opportunityList.isEmpty()) {

						double totalValue = opportunityList.stream().mapToDouble(li -> {
							double value = 0;
							if (null != li.getProposalAmount() && !StringUtils.isEmpty(li.getProposalAmount())) {
								value = Double.parseDouble(li.getProposalAmount());
							}
							return value;
						}).sum();
						contactMapper.setTotalProposalValue(totalValue);
						contactMapper.setOppNo(opportunityList.size());
					}
					Customer customer = customerRepository
							.getCustomerDetailsByCustomerId(contact.getTag_with_company());
					if (null != customer) {
						contactMapper.setTagWithCompany(customer.getName());
					}
				} else if (contact.getContactType().equalsIgnoreCase("Vendor")) {
					PartnerDetails PartnerDetails = partnerDetailsRepository
							.getPartnerDetailsByIdAndLiveInd(contact.getTag_with_company());
					if (null != PartnerDetails) {
						contactMapper.setTagWithCompany(PartnerDetails.getPartnerName());
					}
				} else if (contact.getContactType().equalsIgnoreCase("Leads")) {
					Leads leads = leadsRepository.getLeadsByIdAndLiveInd(contact.getTag_with_company());
					if (null != leads) {
						contactMapper.setTagWithCompany(leads.getName());
					}
				} else if (contact.getContactType().equalsIgnoreCase("Investor")) {
					List<InvestorOpportunity> opportunityList = investorOpportunityRepo
							.getOpportunityListByContactIdAndLiveInd(contactId);
					if (null != opportunityList && !opportunityList.isEmpty()) {
						double totalValue = opportunityList.stream().mapToDouble(li -> {
							double value = 0;
							if (!StringUtils.isEmpty(li.getProposalAmount())) {
								value = Double.parseDouble(li.getProposalAmount());
							}
							return value;
						}).sum();
						contactMapper.setTotalProposalValue(totalValue);
						contactMapper.setOppNo(opportunityList.size());
					}
					Investor investor = investorRepository.getById(contact.getTag_with_company());
					if (null != investor) {
						contactMapper.setTagWithCompany(investor.getName());
					}
				} else if (contact.getContactType().equalsIgnoreCase("InvestorLeads")) {
					InvestorLeads investorleads = investorLeadsRepository.getById(contact.getTag_with_company());
					if (null != investorleads) {
						contactMapper.setTagWithCompany(investorleads.getName());
					}
				}
			}
			contactMapper.setContactType(contact.getContactType());
			contactMapper.setCreationDate(Utility.getISOFromDate(contact.getCreationDate()));
			System.out.println("creation date" + contact.getCreationDate());
			// contactMapper.setTagWithCompany(contact.getTag_with_company());
			if (!StringUtils.isEmpty(contact.getDesignation())) {
				Designation designation = designationRepository.findByDesignationTypeId(contact.getDesignation());
				if (null != designation) {
					System.out.println("designation id**************" + contact.getDesignation());
					contactMapper.setDesignationTypeId(designation.getDesignationTypeId());
					contactMapper.setDesignation(designation.getDesignationType());
				}
			}
//			if (!StringUtils.isEmpty(contact.getDepartment())) {
//				Department department = departmentRepository.getDepartmentDetails(contact.getDepartment());
//				if (null != department) {
//					contactMapper.setDepartmentId(department.getDepartment_id());
//					contactMapper.setDepartment(department.getDepartmentName());
//				}
//			}

			if (!StringUtils.isEmpty(contact.getSector())) {
				SectorDetails sector = sectorDetailsRepository.getSectorDetailsBySectorId(contact.getSector());
				if (null != sector) {
					contactMapper.setSectorId(sector.getSectorId());
					contactMapper.setSector(sector.getSectorName());
				}
			}

			EmployeeDetails employeeDetails = employeeRepository.getEmployeeDetailsByEmployeeId(contact.getUser_id());
			if (null != employeeDetails) {
				String middleName = " ";
				String lastName = "";

				if (!StringUtils.isEmpty(employeeDetails.getLastName())) {

					lastName = employeeDetails.getLastName();
				}

				if (employeeDetails.getMiddleName() != null && employeeDetails.getMiddleName().length() > 0) {

					middleName = employeeDetails.getMiddleName();
					contactMapper.setOwnerName(employeeDetails.getFirstName() + " " + middleName + " " + lastName);
				} else {

					contactMapper.setOwnerName(employeeDetails.getFirstName() + " " + lastName);

				}
				contactMapper.setOwnerImageId(employeeDetails.getImageId());
			}

			contactMapper.setImageId(contact.getImageId());
			System.out.println("contactId==================+++++++++++" + contactId);
			CustomerRecruitUpdate dbCustomerRecruitUpdate = customerRecruitUpdateRepository.findByContactId(contactId);
			if (null != dbCustomerRecruitUpdate) {
				contactMapper.setLastRequirementOn(Utility.getISOFromDate(dbCustomerRecruitUpdate.getUpdatedDate()));
			}

		}

		/* fetch customer address & set to customer mapper */
//		if (null != contactAddressList && !contactAddressList.isEmpty()) {
//			contactAddressList.stream().map(contactAddressLink -> {
//				System.out.println("AdidDD==" + contactAddressLink.getAddress_id());
//				AddressDetails addressDetails = addressRepository
//						.getAddressDetailsByAddressId(contactAddressLink.getAddress_id());
//
//				AddressMapper addressMapper = new AddressMapper();
//				if (null != addressDetails) {
//					addressMapper.setAddress1(addressDetails.getAddressLine1());
//					addressMapper.setAddress2(addressDetails.getAddressLine2());
//					addressMapper.setAddressType(addressDetails.getAddressType());
//					addressMapper.setPostalCode(addressDetails.getPostalCode());
//					addressMapper.setStreet(addressDetails.getStreet());
//					addressMapper.setCity(addressDetails.getCity());
//					addressMapper.setTown(addressDetails.getTown());
//					addressMapper.setCountry(addressDetails.getCountry());
//					addressMapper.setLatitude(addressDetails.getLatitude());
//					addressMapper.setLongitude(addressDetails.getLongitude());
//					addressMapper.setState(addressDetails.getState());
//					addressMapper.setAddressId(addressDetails.getAddressId());
//					addressMapper.setHouseNo(addressDetails.getHouseNo());
//					addressList.add(addressMapper);
//					return addressList;
//
//				}
//				return null;
//			}).collect(Collectors.toList());
//
//		}
//		contactMapper.setAddress(addressList);

		return contactMapper;
	}

	@Override
	public List<ContactViewMapper> getContactListByUserId(String userId, int pageNo, int pageSize) {
		// Pageable paging = PageRequest.of(pageNo, pageSize);
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("creationDate").descending());
		List<ContactViewMapper> resultMapper = new ArrayList<>();
		Page<ContactDetails> contactList = contactRepository.findByUserIdAndContactTypeAndLiveInd(userId, "Customer",
				paging);
		System.out.println("###########" + contactList);
		if (null != contactList && !contactList.isEmpty()) {
			resultMapper = contactList.stream().map(contact -> {
				ContactViewMapper contactMapper = getContactDetailsById(contact.getContactId());
				String middleName = "";
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

			}).filter(l -> l != null).collect(Collectors.toList());
		}
		return resultMapper;
	}

	@Override
	public List<ContactViewMapper> getContactListByUserIds(List<String> userId, int pageNo, int pageSize) {
		// Pageable paging = PageRequest.of(pageNo, pageSize);
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("creationDate").descending());
		List<ContactViewMapper> resultMapper = new ArrayList<>();
		Page<ContactDetails> contactList = contactRepository.findByUserIdsAndContactTypeAndLiveInd(userId, "Customer",
				paging);
		System.out.println("###########" + contactList);
		if (null != contactList && !contactList.isEmpty()) {
			resultMapper = contactList.stream().map(contact -> {
				ContactViewMapper contactMapper = getContactDetailsById(contact.getContactId());
				String middleName = "";
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

			}).filter(l -> l != null).collect(Collectors.toList());
		}
		return resultMapper;
	}

	@Override
	public List<NotesMapper> getNoteListByContactId(String contactId) {

		System.out.println("inside contact lit.................");
		List<NotesMapper> resultList = new ArrayList<>();
		List<ContactNotesLink> contactNotesLinkList = contactNotesLinkRepository.getNoteListByContactId(contactId);

		System.out.println("contactNotesLinkList............" + contactNotesLinkList.size());
		if (contactNotesLinkList != null && !contactNotesLinkList.isEmpty()) {
			contactNotesLinkList.stream().map(customerNotesLink -> {
				NotesMapper notesMapper = getNotes(customerNotesLink.getNotesId());
				if (null != notesMapper) {
					resultList.add(notesMapper);
				}
				return notesMapper;
			}).collect(Collectors.toList());

		}
		return resultList;
	}

	@Override
	public NotesMapper getNotes(String id) {

		Notes notes = notesRepository.findByNoteId(id);
		NotesMapper notesMapper = new NotesMapper();
		if (null != notes) {
			notesMapper.setNotesId(notes.getNotes_id());
			notesMapper.setNotes(notes.getNotes());
			notesMapper.setEmployeeId(notes.getUserId());
			notesMapper.setOwnerName(id);
			notesMapper.setCreationDate(Utility.getISOFromDate(notes.getCreation_date()));
			if (!StringUtils.isEmpty(notes.getUserId())) {
				EmployeeDetails employeeDetails = employeeRepository.getEmployeesByuserId(notes.getUserId());
				String fullName = "";
				String middleName = "";
				String lastName = "";
				if (null != employeeDetails.getMiddleName()) {

					middleName = employeeDetails.getMiddleName();
				}
				if (null != employeeDetails.getLastName()) {
					lastName = employeeDetails.getLastName();
				}
				fullName = employeeDetails.getFirstName() + " " + middleName + " " + lastName;
				notesMapper.setOwnerName(fullName);
			}
		}
		System.out.println("contact notes.........." + notesMapper.toString());

		return notesMapper;

	}

	@Override
	public ContactViewMapper updateContact(String contactId, ContactMapper contactMapper)
			throws IOException, TemplateException {
		ContactViewMapper resultMapper = null;
		// List<AddressMapper> addressLists = new ArrayList<AddressMapper>();
		List<ContactAddressLink> contactAddressList = contactAddressLinkRepository.getAddressListByContactId(contactId);
		ContactDetails contact = contactRepository.getContactDetailsById(contactId);
		ContactDetails newContact = new ContactDetails();
		if (null != contact) {

			contact.setLiveInd(false);
			contactRepository.save(contact);
		}
		newContact.setContactId(contactId);

		if (null != contactMapper.getSalutation()) {

			newContact.setSalutation(contactMapper.getSalutation());
		} else {
			newContact.setSalutation(contact.getSalutation());
		}
		if (null != contactMapper.getFirstName()) {

			newContact.setFirst_name(contactMapper.getFirstName());
		} else {
			newContact.setFirst_name(contact.getFirst_name());
		}
		if (null != contactMapper.getLastName()) {

			newContact.setLast_name(contactMapper.getLastName());
		} else {
			newContact.setLast_name(contact.getLast_name());
		}
		if (null != contactMapper.getMiddleName()) {

			newContact.setMiddle_name(contactMapper.getMiddleName());
		} else {
			newContact.setMiddle_name(contact.getMiddle_name());
		}

		if (null != contactMapper.getMobileNumber()) {

			newContact.setMobile_number(contactMapper.getMobileNumber());
		} else {
			newContact.setMobile_number(contact.getMobile_number());
		}
		if (null != contactMapper.getPhoneNumber()) {

			newContact.setPhone_number(contactMapper.getPhoneNumber());
		} else {
			newContact.setPhone_number(contact.getPhone_number());
		}
		if (null != contactMapper.getEmailId()) {

			newContact.setEmail_id(contactMapper.getEmailId());
		} else {
			newContact.setEmail_id(contact.getEmail_id());
		}
		if (null != contactMapper.getWhatsappNumber()) {

			newContact.setWhatsappNumber(contactMapper.getWhatsappNumber());
		} else {
			newContact.setWhatsappNumber(contact.getWhatsappNumber());
		}

//		contact.setBedroom(contactMapperr.getBedrooms());
//		contact.setPrice(contactMapperr.getPrice());
//		contact.setPropertyType(contactMapperr.getPropertyType());

		if (!StringUtils.isEmpty(contactMapper.getBedrooms())) {
			newContact.setBedroom(contactMapper.getBedrooms());
		} else {
			newContact.setBedroom(contact.getBedroom());
		}

		if (!StringUtils.isEmpty(contactMapper.getPrice())) {
			newContact.setPrice(contactMapper.getPrice());
		} else {
			newContact.setPrice(contact.getPrice());
		}
		if (!StringUtils.isEmpty(contactMapper.getPropertyType())) {

			newContact.setPropertyType(contactMapper.getPropertyType());
		} else {
			newContact.setPropertyType(contact.getPropertyType());
		}
		/*
		 * if(null != contactMapper.getLinkedin() )
		 * 
		 * contact.setLinkedin(contactMapper.getLinkedin());
		 */
		if (null != contactMapper.getCustomerId()) {
			newContact.setContactType("Customer");
			newContact.setTag_with_company(contactMapper.getCustomerId());
		} else {
			newContact.setTag_with_company(contact.getTag_with_company());
		}
		if (null != contactMapper.getPartnerId()) {
			newContact.setContactType("Vendor");
			newContact.setTag_with_company(contactMapper.getPartnerId());
		} else {
			newContact.setTag_with_company(contact.getTag_with_company());
		}
		if (null != contactMapper.getLeadsId()) {
			newContact.setContactType("Leads");
			newContact.setTag_with_company(contactMapper.getLeadsId());
		} else {
			newContact.setTag_with_company(contact.getTag_with_company());
		}
		if (null != contactMapper.getInvestorId()) {
			newContact.setContactType("Investor");
			newContact.setTag_with_company(contactMapper.getInvestorId());
		} else {
			newContact.setTag_with_company(contact.getTag_with_company());
		}
		if (null != contactMapper.getInvestorLeadsId()) {
			newContact.setContactType("InvestorLeads");
			newContact.setTag_with_company(contactMapper.getInvestorLeadsId());
		} else {
			newContact.setTag_with_company(contact.getTag_with_company());
		}
		if (null != contactMapper.getDepartmentId()) {

			newContact.setDepartment(contactMapper.getDepartmentId());
		} else {
			newContact.setDepartment(contact.getDepartment());
		}
		if (null != contactMapper.getDesignationTypeId()) {

			newContact.setDesignation(contactMapper.getDesignationTypeId());
		} else {
			newContact.setDesignation(contact.getDesignation());
		}
		if (null != contactMapper.getSectorId()) {

			newContact.setSector(contactMapper.getSectorId());
		} else {
			newContact.setSector(contact.getSector());
		}
		if (null != contactMapper.getSalary()) {

			newContact.setSalary(contactMapper.getSalary());
		} else {
			newContact.setSalary(contact.getSalary());
		}

		if (!StringUtils.isEmpty(contactMapper.getCountry())) {

			newContact.setCountry(contactMapper.getCountry());
		} else {
			newContact.setCountry(contact.getCountry());
		}

		if (null != contactMapper.getNotes()) {
			List<ContactNotesLink> list = contactNotesLinkRepository.getNoteListByContactId(contactId);
			if (null != list && !list.isEmpty()) {
				list.sort((m1, m2) -> m2.getCreation_date().compareTo(m1.getCreation_date()));
				Notes notes = notesRepository.findByNoteId(list.get(0).getNotesId());
				if (null != notes) {
					notes.setNotes(contactMapper.getNotes());
					notesRepository.save(notes);
				}
			}
		}
		if (null != contactMapper.getLinkedinPublicUrl()) {

			newContact.setLinkedin_public_url(contactMapper.getLinkedinPublicUrl());
		} else {
			newContact.setLinkedin_public_url(contact.getLinkedin_public_url());
		}
		if (null != contactMapper.getCountryDialCode()) {

			newContact.setCountry_dialcode(contactMapper.getCountryDialCode());
		} else {
			newContact.setCountry_dialcode(contact.getCountry_dialcode());
		}
		if (null != contactMapper.getCountryDialCode1()) {

			newContact.setCountry_dialcode1(contactMapper.getCountryDialCode1());
		} else {
			newContact.setCountry_dialcode1(contact.getCountry_dialcode1());
		}
		if (null != contactMapper.getUserId()) {

			newContact.setUser_id(contactMapper.getUserId());
		} else {
			newContact.setUser_id(contact.getUser_id());
		}

		if (null != contactMapper.getOrganizationId()) {

			newContact.setOrganization_id(contactMapper.getOrganizationId());
		} else {
			newContact.setOrganization_id(contact.getOrganization_id());
		}

		if (null != contactMapper.getCustomerId()) {

			newContact.setCustomerId(contactMapper.getCustomerId());
		} else {
			newContact.setCustomerId(contact.getCustomerId());
		}
		if (null != contactMapper.getFirstName()) {
			String middleName = "";
			String lastName = "";

			if (contactMapper.getLastName() != null) {

				lastName = contactMapper.getLastName();
			}
			if (contactMapper.getMiddleName() != null && contactMapper.getMiddleName().length() > 0) {

				middleName = contactMapper.getMiddleName();
				newContact.setFullName(contactMapper.getFirstName() + " " + middleName + " " + lastName);
			} else {

				newContact.setFullName(contactMapper.getFirstName() + " " + lastName);
			}
		} else {
			newContact.setFullName(contact.getFullName());
		}

//		    	 if(null != contactMapper.getFullName() ) {
//		    		 newContact.setFullName(contactMapper.getFirstName()+" "+contactMapper.getMiddleName()+" "+contactMapper.getLastName());
//		    	 }else {
//		    		 newContact.setFullName(contact.getFullName());
//		    	 }
		newContact.setAccessInd(contactMapper.getAccessInd());
		newContact.setCreationDate(new Date());
		newContact.setLiveInd(true);
		newContact.setContactType(contact.getContactType());
		ContactDetails updateContact = contactRepository.save(newContact);

		if (null != contactMapper.getAddress()) {
			List<AddressMapper> addressList = contactMapper.getAddress();

			for (AddressMapper addressMapper : addressList) {

				for (ContactAddressLink contactAddressLink : contactAddressList) {
					AddressDetails addressDetails = addressRepository
							.getAddressDetailsByAddressId(contactAddressLink.getAddress_id());

					if (null != addressDetails) {

						addressDetails.setLiveInd(false);
						addressRepository.save(addressDetails);
					}

					AddressDetails newAddressDetails = new AddressDetails();
					// newAddressDetails.setAddressId(addressid);

					newAddressDetails.setAddressId(addressDetails.getAddressId());
					System.out.println("ADDID@@@@@@@" + addressDetails.getAddressId());

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

					if (null != addressMapper.getCountry()) {
						newAddressDetails.setCountry(addressMapper.getCountry());
					} else {
						newAddressDetails.setTown(addressDetails.getTown());
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
					addressRepository.save(newAddressDetails);
//					addressLists.add(addressMapper);
					// String newAddressDetailsId = addressDetails1.getAddressId();
				}
			}
		}
		// resultMapper.setAddress(addressLists);

		/* insert to Notification Table */
		Notificationparam param = new Notificationparam();
		EmployeeDetails emp = employeeRepository.getEmployeesByuserId(contactMapper.getUserId());
		String name = employeeService.getEmployeeFullNameByObject(emp);
		param.setEmployeeDetails(emp);
		param.setAdminMsg("Contact " + "'" + Utility.FullName(contactMapper.getFirstName(),
				contactMapper.getMiddleName(), contactMapper.getLastName()) + "' updated by " + name);
		param.setOwnMsg("Contact " + Utility.FullName(contactMapper.getFirstName(), contactMapper.getMiddleName(),
				contactMapper.getLastName()) + " updated.");
		param.setNotificationType("Contact update");
		param.setProcessNmae("Contact");
		param.setType("update");
		param.setEmailSubject("Korero alert- Contact updated");
		param.setCompanyName(companyName);
		param.setUserId(contactMapper.getUserId());
		notificationService.createNotificationForDynamicUsers(param);

		resultMapper = getContactDetailsById(updateContact.getContactId());
		return resultMapper;
	}

	public ContactViewMapper getContactMapper(ContactDetails contact) {
		ContactViewMapper resultMapper = new ContactViewMapper();
		resultMapper.setContactId(contact.getContactId());
		resultMapper.setFirstName(contact.getFirst_name());
		resultMapper.setLastName(contact.getLast_name());
		resultMapper.setMiddleName(contact.getMiddle_name());
		resultMapper.setMobileNumber(contact.getMobile_number());
		resultMapper.setPhoneNumber(contact.getPhone_number());
		resultMapper.setEmailId(contact.getEmail_id());
		// resultMapper.setTagWithCompany(contact.getTag_with_company());
		// resultMapper.setDepartment(contact.getDepartment());
		// resultMapper.setDesignation(contact.getDesignation());
		if (!StringUtils.isEmpty(contact.getDesignation())) {
			Designation designation = designationRepository.findByDesignationTypeId(contact.getDesignation());
			if (null != designation) {
				resultMapper.setDesignationTypeId(designation.getDesignationTypeId());
				resultMapper.setDesignation(designation.getDesignationType());
			} else {
				resultMapper.setDesignation("");
			}
		}
		if (!StringUtils.isEmpty(contact.getDepartment())) {
			Department department = departmentRepository.getDepartmentDetails(contact.getDepartment());
			if (null != department) {
				resultMapper.setDepartmentId(department.getDepartment_id());
				resultMapper.setDepartment(department.getDepartmentName());
			}
		}

		String middleName1 = " ";
		String lastName1 = "";
		String salutation = "";

		if (contact.getSalutation() != null && contact.getSalutation().length() > 0) {
			salutation = contact.getSalutation();
		}
		if (!StringUtils.isEmpty(contact.getLast_name())) {

			lastName1 = contact.getLast_name();
		}

		if (contact.getMiddle_name() != null && contact.getMiddle_name().length() > 0) {

			middleName1 = contact.getMiddle_name();
			resultMapper.setFullName(salutation + " " + contact.getFirst_name() + " " + middleName1 + " " + lastName1);
		} else {

			resultMapper.setFullName(salutation + " " + contact.getFirst_name() + " " + lastName1);
		}

		if (contact.getContactType().equalsIgnoreCase("customer")) {
			List<OpportunityDetails> opportunityList = opportunityDetailsRepository
					.getOpportunityListByContactIdAndLiveIndAndReinstateIndAndCloseIndAndLostIndAndWonInd(
							contact.getContactId());
			if (null != opportunityList && !opportunityList.isEmpty()) {

				double totalValue = opportunityList.stream().mapToDouble(li -> {
					double value = 0;
					if (null != li.getProposalAmount() && !StringUtils.isEmpty(li.getProposalAmount())) {
						value = Double.parseDouble(li.getProposalAmount());
					}
					return value;
				}).sum();
				resultMapper.setTotalProposalValue(totalValue);
				resultMapper.setOppNo(opportunityList.size());
			}
			Customer customer = customerRepository.getCustomerDetailsByCustomerId(contact.getTag_with_company());
			if (null != customer) {
				resultMapper.setTagWithCompany(customer.getName());
			}
		} else if (contact.getContactType().equalsIgnoreCase("Vendor")) {
			PartnerDetails PartnerDetails = partnerDetailsRepository
					.getPartnerDetailsByIdAndLiveInd(contact.getTag_with_company());
			if (null != PartnerDetails) {
				resultMapper.setTagWithCompany(PartnerDetails.getPartnerName());
			}
		} else if (contact.getContactType().equalsIgnoreCase("Leads")) {
			Leads leads = leadsRepository.getLeadsByIdAndLiveInd(contact.getTag_with_company());
			if (null != leads) {
				resultMapper.setTagWithCompany(leads.getName());
			}
		} else if (contact.getContactType().equalsIgnoreCase("Investor")) {
			List<InvestorOpportunity> opportunityList = investorOpportunityRepo
					.getOpportunityListByContactIdAndLiveInd(contact.getContactId());
			if (null != opportunityList && !opportunityList.isEmpty()) {
				double totalValue = opportunityList.stream().mapToDouble(li -> {
					double value = 0;
					if (null != li.getProposalAmount()) {
						value = Double.parseDouble(li.getProposalAmount());
					}
					return value;
				}).sum();
				resultMapper.setTotalProposalValue(totalValue);
				resultMapper.setOppNo(opportunityList.size());
			}
			Investor investor = investorRepository.getById(contact.getTag_with_company());
			if (null != investor) {
				resultMapper.setTagWithCompany(investor.getName());
			}
		} else if (contact.getContactType().equalsIgnoreCase("InvestorLeads")) {
			InvestorLeads investorleads = investorLeadsRepository.getById(contact.getTag_with_company());
			if (null != investorleads) {
				resultMapper.setTagWithCompany(investorleads.getName());
			}
		}

		resultMapper.setSalary(contact.getSalary());
		resultMapper.setNotes(contact.getNotes());
		resultMapper.setLinkedinPublicUrl(contact.getLinkedin_public_url());
		resultMapper.setCountryDialCode(contact.getCountry_dialcode());
		resultMapper.setCountryDialCode1(contact.getCountry_dialcode1());
		resultMapper.setUserId(contact.getUser_id());
		resultMapper.setOrganizationId(contact.getOrganization_id());
		resultMapper.setCustomerId(contact.getCustomerId());
		resultMapper.setCreationDate(Utility.getISOFromDate(contact.getCreationDate()));
		EmployeeDetails employeeDetails = employeeRepository.getEmployeeDetailsByEmployeeId(contact.getUser_id());
		if (null != employeeDetails) {
			String middleName = " ";
			String lastName = "";

			if (!StringUtils.isEmpty(employeeDetails.getLastName())) {

				lastName = employeeDetails.getLastName();
			}

			if (employeeDetails.getMiddleName() != null && employeeDetails.getMiddleName().length() > 0) {

				middleName = employeeDetails.getMiddleName();
				resultMapper.setOwnerName(employeeDetails.getFirstName() + " " + middleName + " " + lastName);
			} else {

				resultMapper.setOwnerName(employeeDetails.getFirstName() + " " + lastName);

			}
			resultMapper.setOwnerImageId(employeeDetails.getImageId());
		}
		return resultMapper;
	}

	@Override
	public String addContactType(ContactTypeMapper contactTypeMapper) {
		String contactTypeId = null;

		if (contactTypeMapper != null) {
			ContactType contactType = new ContactType();
			contactType.setContact_type_name(contactTypeMapper.getContactTypeName());
			contactType.setCreator_id(contactTypeMapper.getCreatorId());
			contactType.setOrg_id(contactTypeMapper.getOrganizationId());
			contactType.setCreation_date(new Date());
			contactType.setLive_ind(true);

			ContactType contact = contactTypeRepository.save(contactType);
			contactTypeId = contact.getContact_type_id();
		}

		return contactTypeId;
	}

	@Override
	public List<ContactTypeMapper> getContactTypesByOrgId(String orgId) {
		List<ContactType> contactTypetList = contactTypeRepository.getContactTypesListByOrgId(orgId);
		if (null != contactTypetList && !contactTypetList.isEmpty()) {
			return contactTypetList.stream().map(contact -> {

				ContactTypeMapper contactTypeMapper = new ContactTypeMapper();
				contactTypeMapper.setContactTypeName(contact.getContact_type_name());
				contactTypeMapper.setContactTypeId(contact.getContact_type_id());
				return contactTypeMapper;

			}).collect(Collectors.toList());

		}

		return null;
	}

	@Override
	public String saveContactNotes(NotesMapper notesMapper) {

		String notesId = null;

		if (null != notesMapper) {
			Notes notes = new Notes();
			notes.setNotes(notesMapper.getNotes());
			notes.setCreation_date(new Date());
			notes.setLiveInd(true);
			notes.setUserId(notesMapper.getUserId());
			Notes note = notesRepository.save(notes);
			notesId = note.getNotes_id();

			/* insert to customer-notes-link */

			ContactNotesLink contactNotesLink = new ContactNotesLink();
			contactNotesLink.setContact_id(notesMapper.getContactId());
			contactNotesLink.setNotesId(notesId);
			contactNotesLink.setCreation_date(new Date());

			contactNotesLinkRepository.save(contactNotesLink);

		}
		return notesId;

	}

	@Override
	public List<DocumentMapper> getContactDocumentListByContactId(String contactId) {
		List<DocumentMapper> resultList = new ArrayList<>();
		List<ContactDocumentLink> contactDocumentLinkList = contactDocumentLinkRepository
				.getDocumentByContactId(contactId);
		Set<String> documentIds = contactDocumentLinkList.stream().map(ContactDocumentLink::getDocument_id)
				.collect(Collectors.toSet());
		if (documentIds != null && !documentIds.isEmpty()) {
			documentIds.stream().map(documentId -> {
				DocumentMapper documentMapper = documentService.getDocument(documentId);
				if (null != documentMapper) {
					resultList.add(documentMapper);
					return documentMapper;
				}
				return null;
			}).collect(Collectors.toList());

		}

		return resultList;
	}

	@Override
	public List<ContactViewMapper> getContactListByName(String name) {
//		List<ContactDetails> detailsList = contactRepository.findByFullNameContainingAndLiveInd(name, true);
//		
//		List<ContactViewMapper> mapperList = new ArrayList<ContactViewMapper>();
//		if (null != detailsList && !detailsList.isEmpty()) {
//
//			mapperList = detailsList.stream().map(li -> getContactDetailsById(li.getContactId()))
//					.collect(Collectors.toList());
//
//		}
//
//		return mapperList;

		List<ContactDetails> allList = contactRepository.findAll();

		List<ContactDetails> detailsList = allList.parallelStream().filter(detail -> {
			return detail.getFullName() != null && Utility.containsIgnoreCase(detail.getFullName(), name.trim());
		}).collect(Collectors.toList());
		List<ContactViewMapper> mapperList = new ArrayList<>();
		if (detailsList != null && !detailsList.isEmpty()) {
			mapperList = detailsList.stream().map(contact -> getContactDetailsById(contact.getContactId()))
					.collect(Collectors.toList());
		}

		return mapperList;

	}

	@Override
	public ByteArrayInputStream exportEmployeeListToExcel(List<ContactViewMapper> contactList) {
		XSSFWorkbook workbook = new XSSFWorkbook();

		// Create a blank sheet
		XSSFSheet sheet = workbook.createSheet("Employee");

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
		for (int i = 0; i < contact_headings.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(contact_headings[i]);
			cell.setCellStyle(headerCellStyle);
		}

		int rowNum = 1;
		if (null != contactList && !contactList.isEmpty()) {
			for (ContactViewMapper contact : contactList) {
				Row row = sheet.createRow(rowNum++);

				row.createCell(0).setCellValue(contact.getContactId());

				row.createCell(1).setCellValue(contact.getFirstName());
				row.createCell(2).setCellValue(contact.getMiddleName());
				row.createCell(3).setCellValue(contact.getLastName());
				row.createCell(4).setCellValue(contact.getCountryDialCode());
				row.createCell(5).setCellValue(contact.getMobileNumber());
				row.createCell(6).setCellValue(contact.getPhoneNumber());
				row.createCell(7).setCellValue(contact.getEmailId());
				row.createCell(8).setCellValue(contact.getLinkedinPublicUrl());
				row.createCell(9).setCellValue(contact.getTagWithCompany());
				row.createCell(10).setCellValue(contact.getDepartment());
				row.createCell(11).setCellValue(contact.getDesignation());

			}
		}
		// Resize all columns to fit the content size
		for (int i = 0; i < contact_headings.length; i++) {
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

	@Override
	public List<ContactViewMapper> getPartnerContactListByName(String name) {
		List<ContactDetails> list = contactRepository.getContactDetailsByFullNameAndContactType(name, "Vendor");
		if (null != list && !list.isEmpty()) {
			return list.stream().map(contactDetails -> {
				ContactViewMapper contactViewMapper = getContactDetailsById(contactDetails.getContactId());
				if (null != contactViewMapper) {

					return contactViewMapper;

				}
				return null;
			}).collect(Collectors.toList());

		}
		return null;
	}

//	@Override
//	public List<ContactViewMapper> getAllContatList(int pageNo, int pageSize) {
////        List<Permission> permission = permissionRepository.getUserList();
////
////        if (null != permission && !permission.isEmpty()) {
////            return permission.stream().map(permissionn -> {
////                //List<ContactViewMapper>  mp =contactService.getCandidateListByUserId(permissionn.getUserId());
////                List<ContactViewMapper> mp = contactService.getContactListByUserId(permissionn.getUserId(), pageNo, pageSize);
////                System.out.println(" userId$$$$$$$$$$$$==" + permissionn.getUserId());
////                return mp;
////
////            }).filter(l -> l != null).flatMap(l -> l.stream()).collect(Collectors.toList());
////        }
////        return null;
//		List<String> userId = permissionRepository.getUserList().stream().map(Permission::getUserId)
//				.collect(Collectors.toList());
//		return contactService.getContactListByUserIds(userId, pageNo, pageSize);
//	}

	@Override
	public List<ContactViewMapper> getAllPartnerContatList(int pageNo, int pageSize) {
		List<String> userId = permissionRepository.getUserList().stream().map(Permission::getUserId)
				.collect(Collectors.toList());

//        if (null != permission && !permission.isEmpty()) {
//            return permission.stream().map(permissionn -> {
//                //List<ContactViewMapper>  mp =contactService.getCandidateListByUserId(permissionn.getUserId());
//                List<ContactViewMapper> mp = partnerService.getAllPartnerContatList(permissionn.getUserId(), pageNo, pageSize);
//
//                System.out.println(" userId$$$$$$$$$$$$==" + permissionn.getUserId());
//                return mp;
//            }).filter(l -> l != null).flatMap(l -> l.stream()).collect(Collectors.toList());
//        }

		return partnerService.getAllPartnerContatLists(userId, pageNo, pageSize);
	}

	/*
	 * @Override public HashMap getCountList() { List<ContactDetails> contactList =
	 * contactRepository.findByActive(true); HashMap map = new HashMap();
	 * map.put("contact", contactList.size());
	 * 
	 * return map; }
	 */

	@Override
	public HashMap getCountListByuserId(String userId) {
		List<ContactDetails> contactList = contactRepository.findByUserIdandLiveInd(userId);
		HashMap map = new HashMap();
		map.put("record", contactList.size());

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
	public String contactConvertToUser(String taskId) {
		String employeeId = null;
		ContactUserLink dbcontactUserLink = contactUserLinkRepository.findByTaskId(taskId);
		ContactDetails contact = contactRepository.getcontactDetailsById(dbcontactUserLink.getContactId());
		if (null != contact) {
			EmployeeDetails userExist = employeeRepository.getEmployeeByMailId(contact.getEmail_id());
			if (null != userExist) {

				throw new NonUniqueResultException(
						"User with email : " + contact.getEmail_id() + " is already present");

			} else {
				EmployeeInfo employeeInfo = new EmployeeInfo();
				employeeInfo.setCreationDate(new Date());
				employeeInfo.setCreatorId(contact.getUser_id());

				employeeId = employeeInfoRepository.save(employeeInfo).getId();
				dbcontactUserLink.setUserId(employeeId);
				contactUserLinkRepository.save(dbcontactUserLink);
				contact.setAccessInd(3);
				contactRepository.save(contact);
				if (null != employeeId) {

					EmployeeDetails employeeDetails = new EmployeeDetails();
					employeeDetails.setEmployeeId(employeeId);
					employeeDetails.setFirstName(contact.getFirst_name());
					employeeDetails.setMiddleName(contact.getMiddle_name());
					employeeDetails.setLastName(contact.getLast_name());
					// employeeDetails.setFullName(employeeMapper.getFirstName()+"
					// "+employeeMapper.getMiddleName()+" "+employeeMapper.getLastName());

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
						employeeDetails.setFullName(
								satutation + " " + contact.getFirst_name() + " " + middleName + " " + lastName);
					} else {

						employeeDetails.setFullName(satutation + " " + contact.getFirst_name() + " " + lastName);
					}
					// employeeDetails.setGender(contact.getgen());
					employeeDetails.setSalutation(contact.getSalutation());
					employeeDetails.setEmailId(contact.getEmail_id());
					employeeDetails.setMobileNo(contact.getMobile_number());
					employeeDetails.setPhoneNo(contact.getPhone_number());
					employeeDetails.setLinkedinPublicUrl(contact.getLinkedin_public_url());
					employeeDetails.setTimeZone(ZoneId.systemDefault().getId());
					employeeDetails.setSuspendInd(false);
					employeeDetails.setDateOfJoining(new Date());
					employeeDetails.setStatus("");
//					employeeDetails.setUserType("Internal");
					employeeDetails.setUserType("User");
					// employeeDetails.setLinkedinPublicUrl(employeeMapper.getLinkedinPublicUrl());
					employeeDetails.setDesignation("");
					employeeDetails.setCreatorId(employeeId);
					employeeDetails.setImageId(contact.getImageId());
					employeeDetails.setOrgId(contact.getOrganization_id());
					employeeDetails.setRole("User");
					// employeeDetails.setCountry(contact.getco());
					// employeeDetails.setFacebook(employeeMapper.getFacebook());
					// employeeDetails.setTwitter(employeeMapper.getTwitter());
					Department department = departmentRepository.findByDepartmentName(contact.getContactType());
					if (null != department) {
						employeeDetails.setDepartment(department.getDepartment_id());
					} else {
						Department dbDepartment = new Department();

						dbDepartment.setDepartmentName("Contact");
						dbDepartment.setCreationDate(new Date());
						dbDepartment.setUser_id(contact.getUser_id());
						dbDepartment.setOrgId(contact.getOrganization_id());
						dbDepartment.setEditInd(false);
						String departmentTypeId = departmentRepository.save(dbDepartment).getDepartment_id();
						employeeDetails.setDepartment(departmentTypeId);
					}

					employeeDetails.setCreationDate(new Date());
					employeeDetails.setCountryDialCode(contact.getCountry_dialcode());
					employeeDetails.setCountryDialCode1(contact.getCountry_dialcode1());
					// employeeDetails.setWorkplace(employeeMapper.getWorkplace());
					// employeeDetails.setCurrency(employeeMapper.getCurrency());
					// employeeDetails.setLabel(employeeMapper.getLabel());
					employeeDetails.setJobType("");
					employeeDetails.setEmployeeType(contact.getContactType());
					employeeDetails.setLiveInd(true);
					employeeDetails.setUserId(employeeId);
					employeeRepository.save(employeeDetails);
					String password = contact.getEmail_id().split("\\@")[0];
					System.out.println("password=" + password);
					/* insert to user settings */
					UserSettings userSettings = new UserSettings();
					userSettings.setCreationDate(new Date());
					userSettings.setEmail(contact.getEmail_id());
					userSettings.setUserId(employeeId);
					userSettings.setUserType("USER");
					userSettings.setPassword(new BCryptPasswordEncoder().encode(password));
					userSettings.setDeviceValInd(false);
					userSettings.setUserActiveInd(false);
					userSettings.setEmailValInd(false);
					userSettings.setPasswordActiveInd(false);
					userSettings.setLiveInd(true);
					// userSettings.setPassword(new BCryptPasswordEncoder().encode(password));
					userSettingsRepository.save(userSettings);

					String emailId = contact.getEmail_id();
					String name = contact.getFirst_name();
					String organizationId = contact.getOrganization_id();

					OrganizationDetails organizationDetails = organizationRepository
							.getOrganizationDetailsById(organizationId);
					System.out.println("employeeId........." + employeeId);
					/* send email */
					if (null != employeeId) {

						String from = "engage@tekorero.com";
						String to = emailId;
						System.out.println("EMAILID888=" + emailId);
						String orgName = organizationDetails.getName();
						String subject = "Welcome onboard " + orgName;
						/*
						 * String message = registrationService.
						 * prepareEmailValidationLink(token,employeeId,name,emailId,organizationId);
						 */
						String message = emailService.prepareEmailValidationLink(to, employeeId, organizationId, name,
								orgName);

						String serverUrl = "https://develop.tekorero.com/kite/email/send";
						HttpHeaders headers = new HttpHeaders();
						headers.setContentType(MediaType.MULTIPART_FORM_DATA);
						MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
						body.add("fromEmail", from);
						body.add("message", message);
						body.add("subject", subject);
						body.add("toEmail", to);
						HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
						RestTemplate restTemplate = new RestTemplate();
						ResponseEntity<String> response = restTemplate.postForEntity(serverUrl, requestEntity,
								String.class);
						System.out.println("BODY=" + body.toString());
						System.out.println("response======================" + response.toString());

					}

				}
			}
		}

		return employeeId;
	}

	@Override
	public List<String> updateTransferOneUserToAnother(String userId, TransferMapper transferMapper) {

		List<String> contactList = transferMapper.getContactIds();
		System.out.println("contactList::::::::::::::::::::::::::::::::::::::::::::::::::::" + contactList);
		if (null != contactList && !contactList.isEmpty()) {

			for (String contactId : contactList) {
				System.out.println("the contact id is : " + contactId);
				ContactDetails contactDetails = contactRepository.getcontactDetailsById(contactId);
				System.out
						.println("contactDetails::::::::::::::::::::::::::::::::::::::::::::::::::::" + contactDetails);
				contactDetails.setUser_id(userId);
				contactRepository.save(contactDetails);
				return contactList;
			}
		}
		return contactList;
	}

	@Override
	public ContactViewMapper getContactCloserByContactId(String contactId) {
		ContactViewMapper resultMapper = new ContactViewMapper();
		int requirementNo = 0;
		int onbordedCandidateNo = 0;
		float closerRatio = 0;
		List<OpportunityRecruitDetails> requitmentList = recruitmentOpportunityDetailsRepository
				.getRecruitmentListByContactId(contactId);
		System.out.println("RCQ" + requitmentList.toString());
		for (OpportunityRecruitDetails opportunityRecruitDetails : requitmentList) {
			requirementNo += (int) opportunityRecruitDetails.getNumber();

			List<RecruitProfileLinkDetails> onbordedCandidate = recruitmentProfileDetailsRepository
					.getProfileDetailByRecruitmentIdAndonBoardInd(opportunityRecruitDetails.getRecruitment_id());
			onbordedCandidateNo += onbordedCandidate.size();

		}
		System.out.println("onbordedCandidateNo=" + onbordedCandidateNo);
		System.out.println("requirementNo=" + requirementNo);

		try {
			closerRatio = (float) (onbordedCandidateNo / requirementNo) * 100;
		} catch (ArithmeticException e) {
			resultMapper.setCloser(0);
			return resultMapper;
		}
		resultMapper.setCloser(closerRatio);
		System.out.println("closerRatio=" + closerRatio);
		return resultMapper;
	}

	@Override
	public List<ContactViewMapper> getContactListByUserIdWhichEmailPresent(String userId) {
		List<ContactDetails> contactList = contactRepository.getContactByUserId(userId);
		System.out.println("###########" + contactList);
		if (null != contactList && !contactList.isEmpty()) {
			return contactList.stream().map(contact -> {
				if (null != contact.getEmail_id() && !contact.getEmail_id().isEmpty()) {
					if (null != contact.getCustomerId() && !contact.getCustomerId().isEmpty()) {
						ContactViewMapper contactMapper = getContactDetailsById(contact.getContactId());

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
					}
				}
				return null;
			}).collect(Collectors.toList());
		}
		return null;
	}

	@Override
	public boolean contactExistsByEmail(ContactMapper contactMapper) {
		String contactType = null;
		if (null != contactMapper.getCustomerId()) {
			contactType = "Customer";
			List<ContactDetails> contactDetails = contactRepository
					.findByEmailIdAndContactTypeAndLiveInd(contactMapper.getEmailId(), contactType);
			if (null != contactDetails && !contactDetails.isEmpty()) {
				return true;
			}
			return false;
		}
		if (null != contactMapper.getPartnerId()) {
			contactType = "Vendor";
			List<ContactDetails> contactDetails = contactRepository
					.findByEmailIdAndContactTypeAndLiveInd(contactMapper.getEmailId(), contactType);
			if (null != contactDetails && !contactDetails.isEmpty()) {
				return true;
			}
			return false;
		}
		if (null != contactMapper.getLeadsId()) {
			contactType = "Leads";
			List<ContactDetails> contactDetails = contactRepository
					.findByEmailIdAndContactTypeAndLiveInd(contactMapper.getEmailId(), contactType);
			if (null != contactDetails && !contactDetails.isEmpty()) {
				return true;
			}
			return false;
		}
		if (null != contactMapper.getInvestorId()) {
			contactType = "Investor";
			List<ContactDetails> contactDetails = contactRepository
					.findByEmailIdAndContactTypeAndLiveInd(contactMapper.getEmailId(), contactType);
			if (null != contactDetails && !contactDetails.isEmpty()) {
				return true;
			}
			return false;
		}
		if (null != contactMapper.getInvestorLeadsId()) {
			contactType = "InvestorLeads";
			List<ContactDetails> contactDetails = contactRepository
					.findByEmailIdAndContactTypeAndLiveInd(contactMapper.getEmailId(), contactType);
			if (null != contactDetails && !contactDetails.isEmpty()) {
				return true;
			}
			return false;
		}
		return false;
	}

	@Override
	public List<ContactViewMapper> getContactDetailsListByUserId(String userId) {
		List<ContactDetails> contactList = contactRepository.getcontactListByUserId(userId);
		return contactList.stream().map(contactDetails -> {
			System.out.println("partner id........" + contactDetails.getContactId());

			ContactViewMapper contactMapperr = getContactDetailsById(contactDetails.getContactId());
			return contactMapperr;

		}).collect(Collectors.toList());
	}

	@Override
	public List<ContactViewMapper> getCustomerContactListByUserId(String userId) {
		// Pageable paging = PageRequest.of(pageNo, pageSize);
		// Pageable paging = PageRequest.of(pageNo,
		// pageSize,Sort.by("creationDate").descending());
		List<ContactDetails> contactList = contactRepository.getCustomerContactByUserIdAndContactTypeAndLiveInd(userId,
				"Customer");
		System.out.println("###########" + contactList);
		if (null != contactList && !contactList.isEmpty()) {
			return contactList.stream().map(contact -> {
				ContactViewMapper contactMapper = getContactDetailsById(contact.getContactId());
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
	public List<ContactViewMapper> getAllPartnerContatListCount() {
		List<Permission> permission = permissionRepository.getUserList();
		System.out.println(" user$$$$$$$$$$$$==" + permission.toString());

		if (null != permission && !permission.isEmpty()) {
			return permission.stream().map(permissionn -> {
				// List<ContactViewMapper> mp
				// =contactService.getCandidateListByUserId(permissionn.getUserId());
				List<ContactViewMapper> mp = partnerService.getAllPartnerContatListByUserId(permissionn.getUserId());

				System.out.println(" userId$$$$$$$$$$$$==" + permissionn.getUserId());
				return mp;
			}).flatMap(l -> l.stream()).collect(Collectors.toList());
		}
		return null;
	}

	@Override
	public List<ContactViewMapper> getAllCustomerContatListCount() {
		List<Permission> permission = permissionRepository.getUserList();

		if (null != permission && !permission.isEmpty()) {
			return permission.stream().map(permissionn -> {
				// List<ContactViewMapper> mp
				// =contactService.getCandidateListByUserId(permissionn.getUserId());
				List<ContactViewMapper> mp = contactService.getCustomerContactListByUserId(permissionn.getUserId());

				System.out.println(" userId$$$$$$$$$$$$==" + permissionn.getUserId());
				return mp;
			}).flatMap(l -> l.stream()).collect(Collectors.toList());
		}
		return null;
	}

	@Override
	public boolean CustomerContactExistsByFirstNameAndMiddleNameAndLastName(String firstName, String middleName,
			String lastName) {
		// String type="Customer";
		String fullName = "";
		String middleName2 = "";
		String lastName2 = "";
		String firstName2 = "";

		if (!StringUtils.isEmpty(lastName)) {

			lastName2 = lastName;
		}
		if (firstName != null && firstName.length() > 0) {
			firstName2 = firstName;
		}

		if (middleName != null && middleName.length() > 0) {

			middleName2 = middleName;
			fullName = (firstName2 + " " + middleName2 + " " + lastName2);
		} else {

			fullName = (firstName2 + " " + lastName2);
		}

		List<ContactDetails> contactDetails = contactRepository.getContactDetailsByFullNameAndContactType(fullName,
				"Customer");
		if (contactDetails != null && !contactDetails.isEmpty()) {

			return true;
		}

		return false;
	}

	@Override
	public boolean PartnerContactExistsByFirstNameAndMiddleNameAndLastName(String firstName, String middleName,
			String lastName) {
		String type = "Partner";
		String fullName = "";
		String middleName2 = "";
		String lastName2 = "";
		String firstName2 = "";

		if (!StringUtils.isEmpty(lastName)) {

			lastName2 = lastName;
		}
		if (firstName != null && firstName.length() > 0) {
			firstName2 = firstName;
		}

		if (middleName != null && middleName.length() > 0) {

			middleName2 = middleName;
			fullName = (firstName2 + " " + middleName2 + " " + lastName2);
		} else {

			fullName = (firstName2 + " " + lastName2);
		}

		List<ContactDetails> contactDetails = contactRepository.getContactDetailsByFullNameAndContactType(fullName,
				type);
		if (contactDetails != null && !contactDetails.isEmpty()) {

			return true;
		}

		return false;
	}

	@Override
	public List<ContactViewMapperForDropDown> getAllCustomerContactListByUserIdForDropDown(String userId) {

		List<ContactDetails> contactList = contactRepository.getCustomerContactByUserIdAndContactTypeAndLiveInd(userId,
				"Customer");
		System.out.println("###########" + contactList);
		if (null != contactList && !contactList.isEmpty()) {
			return contactList.stream().map(contact -> {
				ContactViewMapperForDropDown contactMapper = new ContactViewMapperForDropDown();

				contactMapper.setContactId(contact.getContactId());
				contactMapper.setCreationDate(Utility.getISOFromDate(contact.getCreationDate()));
				contactMapper.setCustomerId(contact.getTag_with_company());

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
	public List<ContactViewMapperForDropDown> getPartnerContactListByUserIdForDropDown(String userId) {

		List<ContactDetails> contactList = contactRepository.getPartnerContactByUserIdAndContactTypeAndLiveInd(userId,
				"Vendor");
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
	public List<ContactViewMapperForDropDown> getAllPartnerContactListByOrgIdForDropDown(String orgId) {

		List<ContactDetails> contactList = contactRepository.getAllPartnerContactByOrgIdAndContactTypeAndLiveInd(orgId,
				"Vendor");
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
	public List<Map<String, Double>> getAddedContactListCountByUserIdWithDateWise(String userId, String startDate,
			String endDate) {
		List<Map<String, Double>> map = new ArrayList<>();

		Date startDate1 = null;
		Date endDate1 = null;
		try {
			endDate1 = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			startDate1 = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		int daysDifference = 0;
		Date temp_date = startDate1;
		while (endDate1.after(temp_date)) {
			daysDifference++;
			temp_date = new Date(temp_date.getTime() + 24 * 60 * 60 * 1000);
		}
		if (daysDifference == 0) {
			daysDifference++;
		}

//		long differenceInMillis = endDate1.getTime() -
//				startDate1.getTime();
		// Convert milliseconds to days
//        long daysDifference = TimeUnit.MILLISECONDS.toDays(differenceInMillis) + 1;
		System.out.println("Days  " + daysDifference);
		for (int i = 0; i < daysDifference; i++) {
			System.out.println("startDate1======" + startDate1);
			Date startDate2 = startDate1;
			System.out.println("startDate2======" + startDate2);
			Date endDate2 = Utility.getDateAfterEndDate(startDate1);

			Map map1 = new HashMap<>();
			List<ContactDetails> contactList = contactRepository.getCreatedContactListByUserIdAndDateRange(userId,
					"Customer", startDate2, endDate2);
			double number = contactList.size();
//			int c=i;
//			map1.put("name",++c);
			String date = Utility.getISOFromDate(startDate2);
			System.out.println("date======" + date);
			map1.put("Date", date);
			map1.put("Number", number);
			map.add(map1);
			startDate1 = Utility.getPlusDate(startDate1, 1);
		}
		return map;
	}

	@Override
	public List<ContactViewMapper> getInvestorContactListByUserId(String userId, int pageNo, int pageSize,
			String filter) {
		List<ContactViewMapper> resultList = new ArrayList<>();
		Pageable paging = null;
		if (filter.equalsIgnoreCase("creationdate")) {
			paging = PageRequest.of(pageNo, pageSize, Sort.by("creationDate").descending());

		} else if (filter.equalsIgnoreCase("ascending")) {
			paging = PageRequest.of(pageNo, pageSize, Sort.by("fullName"));

		} else if (filter.equalsIgnoreCase("descending")) {
			paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "fullName"));
		}
		Page<ContactDetails> contactList = contactRepository.findByUserIdAndContactTypeAndLiveInd(userId, "Investor",
				paging);
		System.out.println("###########" + contactList);
		if (null != contactList && !contactList.isEmpty()) {
			return contactList.stream().map(contact -> {
				ContactViewMapper contactMapper = getContactDetailsById(contact.getContactId());
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
		return resultList;
	}

	@Override
	public List<Map<String, Double>> getInvestorAddedContactCountByUserIdWithDateWise(String userId, String startDate,
			String endDate) {
		List<Map<String, Double>> map = new ArrayList<>();

		Date startDate1 = null;
		Date endDate1 = null;
		try {
			endDate1 = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			startDate1 = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		int daysDifference = 0;
		Date temp_date = startDate1;
		while (endDate1.after(temp_date)) {
			daysDifference++;
			temp_date = new Date(temp_date.getTime() + 24 * 60 * 60 * 1000);
		}
		if (daysDifference == 0) {
			daysDifference++;
		}

//		long differenceInMillis = endDate1.getTime() -
//				startDate1.getTime();
		// Convert milliseconds to days
//        long daysDifference = TimeUnit.MILLISECONDS.toDays(differenceInMillis) + 1;
		System.out.println("Days  " + daysDifference);
		for (int i = 0; i < daysDifference; i++) {
			System.out.println("startDate1======" + startDate1);
			Date startDate2 = startDate1;
			System.out.println("startDate2======" + startDate2);
			Date endDate2 = Utility.getDateAfterEndDate(startDate1);

			Map map1 = new HashMap<>();
			List<ContactDetails> contactList = contactRepository.getCreatedContactListByUserIdAndDateRange(userId,
					"Investor", startDate2, endDate2);
			double number = contactList.size();
//			int c=i;
//			map1.put("name",++c);
			String date = Utility.getISOFromDate(startDate2);
			System.out.println("date======" + date);
			map1.put("Date", date);
			map1.put("Number", number);
			map.add(map1);
			startDate1 = Utility.getPlusDate(startDate1, 1);
		}
		return map;
	}

	@Override
	public List<ContactViewMapperForDropDown> getAllInvestorContactListByUserIdForDropDown(String userId) {
		List<ContactDetails> contactList = contactRepository.getInvesterContactByUserIdAndContactTypeAndLiveInd(userId,
				"Investor");
		System.out.println("###########" + contactList);
		if (null != contactList && !contactList.isEmpty()) {
			return contactList.stream().map(contact -> {
				ContactViewMapperForDropDown contactMapper = new ContactViewMapperForDropDown();

				contactMapper.setContactId(contact.getContactId());
				contactMapper.setCreationDate(Utility.getISOFromDate(contact.getCreationDate()));
				contactMapper.setInvestorId(contact.getTag_with_company());

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
	public HashMap getCustomerContactCountListByuserId(String userId) {
		List<ContactDetails> contactList = contactRepository.getCustomerContactByUserIdAndContactTypeAndLiveInd(userId,
				"Customer");
		HashMap map = new HashMap();
		map.put("customerContactCount", contactList.size());

		return map;
	}

	@Override
	public List<ContactViewMapper> getFilterContactListByUserId(String userId, int pageNo, int pageSize,
			String filter) {
		Pageable paging = null;
		if (filter.equalsIgnoreCase("creationdate")) {
			paging = PageRequest.of(pageNo, pageSize, Sort.by("creationDate").descending());

		} else if (filter.equalsIgnoreCase("ascending")) {
			paging = PageRequest.of(pageNo, pageSize, Sort.by("fullName"));

		} else if (filter.equalsIgnoreCase("descending")) {
			paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "fullName"));
		}
		Page<ContactDetails> contactList = contactRepository.findByUserIdAndContactTypeAndLiveInd(userId, "Customer",
				paging);

		System.out.println("###########" + contactList);
		if (null != contactList && !contactList.isEmpty()) {
			return contactList.stream().map(contact -> {
				ContactViewMapper contactMapper = getContactDetailsById(contact.getContactId());
				String middleName = "";
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

			}).filter(l -> l != null).collect(Collectors.toList());
		}
		return null;
	}

	@Override
	public List<ContactViewMapper> getInvestorContactListByName(String name, String userId) {
		List<ContactDetails> contactList = contactRepository.getByUserIdAndContactTypeAndLiveInd(userId, "Investor");
		System.out.println("contactList=====" + contactList.size());
		List<ContactDetails> detailsList = contactList.stream().filter(detail -> {
			return detail.getFullName() != null && Utility.containsIgnoreCase(detail.getFullName(), name.trim());
		}).collect(Collectors.toList());

		List<ContactViewMapper> mapperList = new ArrayList<>();
		if (detailsList != null && !detailsList.isEmpty()) {
			mapperList = detailsList.stream().map(leads -> getContactDetailsById(leads.getContactId()))
					.collect(Collectors.toList());
		}

		return mapperList;

	}

	@Override
	public List<ContactViewMapper> getInvestorContactByCompany(String name, String userId) {

		List<Investor> allList = investorRepository.findAll();

		List<Investor> detailsList = allList.stream()
				.filter(entity -> entity.getName() != null && entity.getName().contains(name.trim()))
				.collect(Collectors.toList());

		List<ContactViewMapper> mapperList = new ArrayList<>();
		if (detailsList != null && !detailsList.isEmpty()) {
			for (Investor investor : detailsList) {

				List<ContactDetails> contactList = contactRepository.getInvestorContactByCompany(userId,
						investor.getInvestorId(), "Investor");
				if (null != contactList && !contactList.isEmpty()) {
					mapperList = contactList.stream().map(contact -> {
						ContactViewMapper contactMapper = getContactDetailsById(contact.getContactId());
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
			}
			return mapperList;
		}
		return mapperList;
	}

//	@Override
//	public NotesMapper updateNoteDetails(NotesMapper notesMapper) {
//
//		NotesMapper resultMapper = employeeService.updateNotes(notesMapper);
//
//		return resultMapper;
//	}

	@Override
	public void deleteContactNotesById(String notesId) {
		ContactNotesLink notesList = contactNotesLinkRepository.findByNotesId(notesId);
		if (null != notesList) {

			Notes notes = notesRepository.findByNoteId(notesId);
			if (null != notes) {
				notes.setLiveInd(false);
				notesRepository.save(notes);
			}
		}
	}

	@Override
	public List<ContactViewMapper> getContactListByOrgId(String orgId, int pageNo, int pageSize, String type) {
		Pageable paging = null;
		paging = PageRequest.of(pageNo, pageSize, Sort.by("creationDate").descending());

		Page<ContactDetails> contactList = contactRepository
				.getContactListByOrgIdAndContactTypeAndLiveIndPageWise(orgId, type, paging);
		List<ContactViewMapper> resultMapper = new ArrayList<>();
		if (null != contactList && !contactList.isEmpty()) {
			resultMapper = contactList.stream().map(li -> {
				ContactViewMapper mapper = getContactDetailsById(li.getContactId());
				mapper.setPageCount(contactList.getTotalPages());
				mapper.setDataCount(contactList.getSize());
				mapper.setListCount(contactList.getTotalElements());
				return mapper;
			}).collect(Collectors.toList());
		}
		return resultMapper;
	}

	@Override
	public HashMap getContactListCountByOrgId(String orgId, String type) {
		List<ContactDetails> list = contactRepository.getContactListByOrgIdAndContactTypeAndLiveInd(orgId, type);
		HashMap map = new HashMap();
		map.put("contact", list.size());

		return map;
	}

	@Override
	public List<ActivityMapper> getActivityListByContactId(String contactId) {
		List<ActivityMapper> resultList = new ArrayList<>();

		List<ContactCallLink> contactCallLink = contactCallLinkRepo.getCallListByContactIdAndLiveInd(contactId);
		if (null != contactCallLink && !contactCallLink.isEmpty()) {
			contactCallLink.stream().map(call -> {
				ActivityMapper activityMapper = new ActivityMapper();
				CallDetails callDetails = callDetailsRepository.getCallDetailsById(call.getCallId());
				if (null != callDetails) {
					activityMapper.setActivityType(callDetails.getSubject());
					activityMapper.setCallId(callDetails.getCall_id());
					activityMapper.setCategory("Call");
					activityMapper.setCreationDate(Utility.getISOFromDate(callDetails.getCreation_date()));
					activityMapper.setStartDate(Utility.getISOFromDate(callDetails.getCall_start_date()));
					activityMapper.setEndDate(Utility.getISOFromDate(callDetails.getCall_end_date()));
					activityMapper.setDescription(callDetails.getCall_description());
					activityMapper.setUserId(callDetails.getUser_id());
					activityMapper.setOrganizationId(callDetails.getOrganization_id());
					activityMapper.setWoner(employeeService.getEmployeeFullName(callDetails.getUser_id()));
					activityMapper.setAssignedTo(employeeService.getEmployeeFullName(callDetails.getAssignedTo()));

					List<EmployeeShortMapper> empList = new ArrayList<EmployeeShortMapper>();

					List<EmployeeCallLink> employeeList = employeeCallRepository.getEmpListByCallId(call.getCallId());

					if (null != employeeList && !employeeList.isEmpty()) {
						for (EmployeeCallLink employeeCallLink : employeeList) {
							if (null != employeeCallLink.getEmployee_id()) {
								if (!employeeCallLink.getEmployee_id().equals(callDetails.getAssignedTo())) {
									if (!employeeCallLink.getEmployee_id().equals(callDetails.getUser_id())) {
										EmployeeShortMapper employeeMapper = employeeService
												.getEmployeeFullNameAndEmployeeId(employeeCallLink.getEmployee_id());
										empList.add(employeeMapper);
									}
								}
							}
						}

					}
					activityMapper.setIncluded(empList);
					resultList.add(activityMapper);
				}
				return activityMapper;
			}).collect(Collectors.toList());
		}

		List<ContactEventLink> eventLink = contactEventRepo.getByContactIdAndLiveInd(contactId);
		if (null != eventLink && !eventLink.isEmpty()) {
			eventLink.stream().map(event -> {
				ActivityMapper activityMapper = new ActivityMapper();
				EventDetails eventDetails = eventDetailsRepository.getEventDetailsById(event.getEventId());
				if (null != eventDetails) {
					activityMapper.setActivityType(eventDetails.getSubject());
					activityMapper.setEventId(eventDetails.getEvent_id());
					activityMapper.setCategory("Event");
					activityMapper.setCreationDate(Utility.getISOFromDate(eventDetails.getCreation_date()));
					activityMapper.setStartDate(Utility.getISOFromDate(eventDetails.getStart_date()));
					activityMapper.setEndDate(Utility.getISOFromDate(eventDetails.getEnd_date()));
					activityMapper.setDescription(eventDetails.getEvent_description());
					activityMapper.setUserId(eventDetails.getUser_id());
					activityMapper.setOrganizationId(eventDetails.getOrganization_id());
					activityMapper.setWoner(employeeService.getEmployeeFullName(eventDetails.getUser_id()));
					activityMapper.setAssignedTo(employeeService.getEmployeeFullName(eventDetails.getAssignedTo()));

					List<EmployeeShortMapper> empList = new ArrayList<EmployeeShortMapper>();

					List<EmployeeEventLink> employeeList = employeeEventRepository
							.getEmpListByEventId(eventDetails.getEvent_id());

					if (null != employeeList && !employeeList.isEmpty()) {
						for (EmployeeEventLink employeeCallLink : employeeList) {
							if (null != employeeCallLink.getEmployee_id()) {
								if (!employeeCallLink.getEmployee_id().equals(eventDetails.getAssignedTo())) {
									if (!employeeCallLink.getEmployee_id().equals(eventDetails.getUser_id())) {
										EmployeeShortMapper employeeMapper = employeeService
												.getEmployeeFullNameAndEmployeeId(employeeCallLink.getEmployee_id());
										empList.add(employeeMapper);
									}
								}
							}
						}

					}
					activityMapper.setIncluded(empList);
					resultList.add(activityMapper);
				}
				return activityMapper;
			}).collect(Collectors.toList());
		}

		List<ContactTaskLink> taskLink = contactTaskRepo.getTaskListByContactIdAndLiveInd(contactId);
		if (null != taskLink && !taskLink.isEmpty()) {
			taskLink.stream().map(task -> {
				ActivityMapper activityMapper = new ActivityMapper();
				TaskDetails taskDetails = taskDetailsRepository.getTaskDetailsById(task.getTaskId());
				if (null != taskDetails) {
					activityMapper.setActivityType(taskDetails.getTask_name());
					activityMapper.setTaskId(taskDetails.getTask_id());
					activityMapper.setCategory("Task");
					activityMapper.setCreationDate(Utility.getISOFromDate(taskDetails.getCreation_date()));
					activityMapper.setStartDate(Utility.getISOFromDate(taskDetails.getStart_date()));
					activityMapper.setEndDate(Utility.getISOFromDate(taskDetails.getEnd_date()));
					activityMapper.setDescription(taskDetails.getTask_description());
					activityMapper.setUserId(taskDetails.getUser_id());
					activityMapper.setOrganizationId(taskDetails.getOrganization_id());
					activityMapper.setWoner(employeeService.getEmployeeFullName(taskDetails.getUser_id()));
					activityMapper.setAssignedTo(employeeService.getEmployeeFullName(taskDetails.getAssigned_to()));

					List<EmployeeShortMapper> empList = new ArrayList<EmployeeShortMapper>();

					List<EmployeeTaskLink> employeeList = employeeTaskRepository
							.getEmpListByTaskId(taskDetails.getTask_id());

					if (null != employeeList && !employeeList.isEmpty()) {
						for (EmployeeTaskLink employeeCallLink : employeeList) {
							if (null != employeeCallLink.getEmployee_id()) {
								if (!employeeCallLink.getEmployee_id().equals(taskDetails.getAssigned_to())) {
									if (!employeeCallLink.getEmployee_id().equals(taskDetails.getUser_id())) {
										EmployeeShortMapper employeeMapper = employeeService
												.getEmployeeFullNameAndEmployeeId(employeeCallLink.getEmployee_id());
										empList.add(employeeMapper);
									}
								}
							}
						}

					}
					activityMapper.setIncluded(empList);

					List<DocumentMapper> result = new ArrayList<>();
					List<TaskDocumentLink> docList = taskDocumentLinkRepository.findByTaskId(task.getTaskId());
					if (docList != null && !docList.isEmpty()) {
						result = docList.stream().map(taskDocumentLink -> {
							return documentService.getDocument(taskDocumentLink.getDocumentId());
						}).collect(Collectors.toList());

						activityMapper.setDocuments(result);
					}

					resultList.add(activityMapper);
				}
				return activityMapper;
			}).collect(Collectors.toList());
		}

		return resultList;
	}

	@Override
	public Set<ContactViewMapper> getTeamContactListByUserId(String userId, int pageNo, int pageSize, String filter) {
		Pageable paging = null;
		if (filter.equalsIgnoreCase("creationdate")) {
			paging = PageRequest.of(pageNo, pageSize, Sort.by("creationDate").descending());

		} else if (filter.equalsIgnoreCase("ascending")) {
			paging = PageRequest.of(pageNo, pageSize, Sort.by("fullName"));

		} else if (filter.equalsIgnoreCase("descending")) {
			paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "fullName"));
		}
		Team team = teamRepository.findByTeamLeadAndLiveInd(userId, true);

		List<String> userIds = teamMemberLinkRepo.findByTeamId(team.getTeamId()).stream()
				.map(TeamMemberLink::getTeamMemberId).collect(Collectors.toList());
		Page<ContactDetails> contactList = contactRepository.getTeamContactListdAndContactTypeAndLiveInd(userIds,
				"Customer", paging);
		Set<ContactViewMapper> result = new HashSet<>();
		System.out.println("###########" + contactList);
		if (null != contactList && !contactList.isEmpty()) {
			result = contactList.getContent().stream().map(contact -> {
				ContactViewMapper contactMapper = getContactDetailsById(contact.getContactId());
				String middleName = "";
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

			}).filter(l -> l != null).collect(Collectors.toSet());
		}
		return result;
	}

	@Override
	public HashMap getTeamContactCountByUserId(String userId) {
		HashMap map = new HashMap();
		Team team = teamRepository.findByTeamLeadAndLiveInd(userId, true);

		List<String> userIds = teamMemberLinkRepo.findByTeamId(team.getTeamId()).stream()
				.map(TeamMemberLink::getTeamMemberId).collect(Collectors.toList());
		List<ContactDetails> investorPage = contactRepository.getTeamContactListByUserIds(userIds, "Customer");
		map.put("ContactTeam", investorPage.size());

		return map;
	}

	@Override
	public ContactReportMapper getContactDetailsByIdForReport(String contactId) {

		ContactDetails contact = contactRepository.getContactDetailsById(contactId);

		// System.out.println("contact id ............."+contact.getContact_id());

		List<ContactAddressLink> contactAddressList = contactAddressLinkRepository.getAddressListByContactId(contactId);
		List<AddressMapper> addressList = new ArrayList<AddressMapper>();

		ContactReportMapper contactMapper = new ContactReportMapper();

		if (null != contact) {
			contactMapper.setContactId(contactId);
			contactMapper.setFirstName(contact.getFirst_name());
			contactMapper.setMiddleName(contact.getMiddle_name());
			contactMapper.setLastName(contact.getLast_name());
			contactMapper.setMobileNumber(contact.getMobile_number());
			contactMapper.setPhoneNumber(contact.getPhone_number());
			contactMapper.setEmailId(contact.getEmail_id());
			contactMapper.setLinkedinPublicUrl(contact.getLinkedin_public_url());

			contactMapper.setCountryDialCode(contact.getCountry_dialcode());
			contactMapper.setCountryDialCode1(contact.getCountry_dialcode1());
			contactMapper.setSalary(contact.getSalary());
			contactMapper.setNotes(contact.getNotes());
			contactMapper.setUserId(contact.getUser_id());
			contactMapper.setOrganizationId(contact.getOrganization_id());
			contactMapper.setCustomerId(contact.getCustomerId());
			contactMapper.setAccessInd(contact.getAccessInd());
			contactMapper.setContactRole(contact.getContactRole());
			contactMapper.setWhatsappNumber(contact.getWhatsappNumber());
			contactMapper.setCreationDate(Utility.getISOFromDate(contact.getCreationDate()));

			contactMapper.setSourceUserId(contact.getSourceUserId());
			contactMapper.setSourceUserName(employeeService.getEmployeeFullName(contact.getSourceUserId()));

			if (null != contact.getSource() && !contact.getSource().isEmpty()) {
				Source source = sourceRepository.findBySourceId(contact.getSource());
				if (null != source) {
					contactMapper.setSource(source.getName());
				}
			}

			String middleName1 = " ";
			String lastName1 = "";
			String salutation = "";

			if (contact.getSalutation() != null && contact.getSalutation().length() > 0) {
				salutation = contact.getSalutation();
			}
			if (!StringUtils.isEmpty(contact.getLast_name())) {

				lastName1 = contact.getLast_name();
			}

			if (contact.getMiddle_name() != null && contact.getMiddle_name().length() > 0) {

				middleName1 = contact.getMiddle_name();
				contactMapper
						.setFullName(salutation + " " + contact.getFirst_name() + " " + middleName1 + " " + lastName1);
			} else {

				contactMapper.setFullName(salutation + " " + contact.getFirst_name() + " " + lastName1);
			}

			if (contact.getContactType().equalsIgnoreCase("customer")) {
				List<OpportunityDetails> opportunityList = opportunityDetailsRepository
						.getOpportunityListByContactIdAndLiveIndAndReinstateIndAndCloseIndAndLostIndAndWonInd(
								contactId);
				if (null != opportunityList && !opportunityList.isEmpty()) {

					double totalValue = opportunityList.stream().mapToDouble(li -> {
						double value = 0;
						if (null != li.getProposalAmount() && !StringUtils.isEmpty(li.getProposalAmount())) {
							value = Double.parseDouble(li.getProposalAmount());
						}
						return value;
					}).sum();
					contactMapper.setTotalProposalValue(totalValue);
					contactMapper.setOppNo(opportunityList.size());
				}
				Customer customer = customerRepository.getCustomerDetailsByCustomerId(contact.getTag_with_company());
				if (null != customer) {
					contactMapper.setTagWithCompany(customer.getName());
				}
			} else if (contact.getContactType().equalsIgnoreCase("Vendor")) {
				PartnerDetails PartnerDetails = partnerDetailsRepository
						.getPartnerDetailsByIdAndLiveInd(contact.getTag_with_company());
				if (null != PartnerDetails) {
					contactMapper.setTagWithCompany(PartnerDetails.getPartnerName());
				}
			} else if (contact.getContactType().equalsIgnoreCase("Leads")) {
				Leads leads = leadsRepository.getLeadsByIdAndLiveInd(contact.getTag_with_company());
				if (null != leads) {
					contactMapper.setTagWithCompany(leads.getName());
				}
			} else if (contact.getContactType().equalsIgnoreCase("Investor")) {
				List<InvestorOpportunity> opportunityList = investorOpportunityRepo
						.getOpportunityListByContactIdAndLiveInd(contactId);
				if (null != opportunityList && !opportunityList.isEmpty()) {
					double totalValue = opportunityList.stream().mapToDouble(li -> {
						double value = 0;
						if (null != li.getProposalAmount()) {
							value = Double.parseDouble(li.getProposalAmount());
						}
						return value;
					}).sum();
					contactMapper.setTotalProposalValue(totalValue);
					contactMapper.setOppNo(opportunityList.size());
				}
				Investor investor = investorRepository.getById(contact.getTag_with_company());
				if (null != investor) {
					contactMapper.setTagWithCompany(investor.getName());
				}
			} else if (contact.getContactType().equalsIgnoreCase("InvestorLeads")) {
				InvestorLeads investorleads = investorLeadsRepository.getById(contact.getTag_with_company());
				if (null != investorleads) {
					contactMapper.setTagWithCompany(investorleads.getName());
				}
			}
			contactMapper.setContactType(contact.getContactType());
			contactMapper.setCreationDate(Utility.getISOFromDate(contact.getCreationDate()));
			System.out.println("creation date" + contact.getCreationDate());
			// contactMapper.setTagWithCompany(contact.getTag_with_company());
			if (!StringUtils.isEmpty(contact.getDesignation())) {
				Designation designation = designationRepository.findByDesignationTypeId(contact.getDesignation());
				if (null != designation) {
					System.out.println("designation id**************" + contact.getDesignation());
					contactMapper.setDesignationTypeId(designation.getDesignationTypeId());
					contactMapper.setDesignation(designation.getDesignationType());
				}
			}
			if (!StringUtils.isEmpty(contact.getDepartment())) {
				Department department = departmentRepository.getDepartmentDetails(contact.getDepartment());
				if (null != department) {
					contactMapper.setDepartmentId(department.getDepartment_id());
					contactMapper.setDepartment(department.getDepartmentName());
				}
			}

			if (!StringUtils.isEmpty(contact.getSector())) {
				SectorDetails sector = sectorDetailsRepository.getSectorDetailsBySectorId(contact.getSector());
				if (null != sector) {
					contactMapper.setSectorId(sector.getSectorId());
					contactMapper.setSector(sector.getSectorName());
				}
			}

			EmployeeDetails employeeDetails = employeeRepository.getEmployeeDetailsByEmployeeId(contact.getUser_id());
			if (null != employeeDetails) {
				String middleName = " ";
				String lastName = "";

				if (!StringUtils.isEmpty(employeeDetails.getLastName())) {

					lastName = employeeDetails.getLastName();
				}

				if (employeeDetails.getMiddleName() != null && employeeDetails.getMiddleName().length() > 0) {

					middleName = employeeDetails.getMiddleName();
					contactMapper.setOwnerName(employeeDetails.getFirstName() + " " + middleName + " " + lastName);
				} else {

					contactMapper.setOwnerName(employeeDetails.getFirstName() + " " + lastName);

				}
				contactMapper.setOwnerImageId(employeeDetails.getImageId());
			}

			contactMapper.setImageId(contact.getImageId());
			System.out.println("contactId==================+++++++++++" + contactId);
			CustomerRecruitUpdate dbCustomerRecruitUpdate = customerRecruitUpdateRepository.findByContactId(contactId);
			if (null != dbCustomerRecruitUpdate) {
				contactMapper.setLastRequirementOn(Utility.getISOFromDate(dbCustomerRecruitUpdate.getUpdatedDate()));
			}

		}

		/* fetch customer address & set to customer mapper */
		if (null != contactAddressList && !contactAddressList.isEmpty()) {

			contactAddressList.stream().map(contactAddressLink -> {
				AddressDetails addressDetails = addressRepository
						.getAddressDetailsByAddressId(contactAddressLink.getAddress_id());

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
					return addressList;

				}
				return null;
			}).collect(Collectors.toList());

		}
		contactMapper.setAddress(addressList);

		return contactMapper;
	}

	@Override
	public HashMap getActivityRecordByContactId(String contactId) {

		int count = 0;
		List<ContactCallLink> contactCallLink = contactCallLinkRepo.getCallListByContactIdAndLiveInd(contactId);
		if (null != contactCallLink && !contactCallLink.isEmpty()) {
			count = contactCallLink.size();
		}

		List<ContactEventLink> eventLink = contactEventRepo.getByContactIdAndLiveInd(contactId);
		if (null != eventLink && !eventLink.isEmpty()) {
			count += eventLink.size();
		}

		List<ContactTaskLink> taskLink = contactTaskRepo.getTaskListByContactIdAndLiveInd(contactId);
		if (null != taskLink && !taskLink.isEmpty()) {
			count += taskLink.size();
		}
		HashMap map = new HashMap();
		map.put("count", count);

		return map;

	}

	@Override
	public HashMap getTeamContactCountByUnderUserId(String userId) {
		HashMap map = new HashMap();

		Set<String> userIds = employeeRepository.getEmployeeDetailsByReportingManagerId(userId).stream()
				.map(EmployeeDetails::getUserId).collect(Collectors.toSet());
		userIds.add(userId);

		List<String> userIdss = new ArrayList<>(userIds);

		List<ContactDetails> contactList = contactRepository.getTeamContactListByUserIds(userIdss, "Customer");
		map.put("contactTeam", contactList.size());

		return map;
	}

	@Override
	public List<ContactViewMapper> getTeamContactListByUnderUserId(String userId, int pageNo, int pageSize) {
		Set<String> userIds = employeeRepository.getEmployeeDetailsByReportingManagerId(userId).stream()
				.map(EmployeeDetails::getUserId).collect(Collectors.toSet());
		userIds.add(userId);

		List<String> userIdss = new ArrayList<>(userIds);

		List<ContactViewMapper> result = new ArrayList<>();
		Pageable paging = null;
		paging = PageRequest.of(pageNo, pageSize, Sort.by("creationDate").descending());

		Page<ContactDetails> contactList = contactRepository.getTeamContactListdAndContactTypeAndLiveInd(userIdss,
				"Customer", paging);
		if (null != contactList && !contactList.isEmpty()) {
			result = contactList.getContent().stream().map(contact -> {
				ContactViewMapper contactMapper = getContactDetailsById(contact.getContactId());
				String middleName = "";
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

			}).filter(l -> l != null).collect(Collectors.toList());
		}
		return result;
	}

	@Override
	public void deleteContact(String contactId) {
		ContactDetails contact = contactRepository.getContactDetailsById(contactId);
		if (null != contact) {
			contact.setLiveInd(false);
			contactRepository.save(contact);
		}
	}

	@Override
	public ContactViewMapper reinitiateDeletedContactId(String contactId, String userId) {
		ContactDetails contact = contactRepository.findByContactId(contactId);
		if (null != contact) {
			contact.setLiveInd(true);
			contactRepository.save(contact);
		}
		ContactViewMapper resultmapper = getContactDetailsById(contactId);
		return resultmapper;
	}

	@Override
	public List<ContactViewMapper> getContactDetailsByNameByOrgLevel(String name, String orgId, String contactType) {
		List<ContactDetails> list = contactRepository.getContactListByOrgIdAndContactTypeAndLiveInd(orgId, contactType);
		List<ContactDetails> filterList = list.parallelStream().filter(detail -> {
			return detail.getFullName() != null && Utility.containsIgnoreCase(detail.getFullName(), name.trim());
		}).collect(Collectors.toList());
		List<ContactViewMapper> mapperList = new ArrayList<ContactViewMapper>();
		if (null != filterList && !filterList.isEmpty()) {

			mapperList = filterList.parallelStream().map(li -> getContactDetailsById(li.getContactId()))
					.collect(Collectors.toList());

		}

		return mapperList;

	}

	@Override
	public List<ContactViewMapper> getContactBySectorInOrgLevel(String name, String orgId, String contactType) {
		List<ContactViewMapper> mapperList = new ArrayList<>();
		SectorDetails sectorDetails = sectorDetailsRepository.getSectorDetailsBySectorNameAndOrgId(name, orgId);
		if (null != sectorDetails) {
			List<ContactDetails> detailsList = contactRepository
					.getBySectorAndOrganizationIdAndContactType(sectorDetails.getSectorId(), orgId, contactType);
			if (null != detailsList && !detailsList.isEmpty()) {
				mapperList = detailsList.stream().map(li -> getContactDetailsById(li.getContactId()))
						.collect(Collectors.toList());
			}
		}
		return mapperList;
	}

	@Override
	public List<ContactViewMapper> getContactBySourceInOrgLevel(String name, String orgId, String contactType) {
		List<ContactViewMapper> mapperList = new ArrayList<>();
		Source source = sourceRepository.findByNameAndOrgId(name, orgId);
		if (null != source) {
			List<ContactDetails> detailsList = contactRepository
					.getBySourceAndOrganizationIdAndContactType(source.getSourceId(), orgId, contactType);
			if (null != detailsList && !detailsList.isEmpty()) {
				mapperList = detailsList.stream().map(li -> getContactDetailsById(li.getContactId()))
						.collect(Collectors.toList());
			}
		}
		return mapperList;
	}

	@Override
	public List<ContactViewMapper> getContactByOwnerNameInOrgLevel(String name, String orgId, String contactType) {
		List<ContactViewMapper> mapperList = new ArrayList<>();

		List<EmployeeDetails> detailsList = employeeRepository.getEmployeesByOrgId(orgId);

		List<EmployeeDetails> filterList = new ArrayList<>();
		for (EmployeeDetails employeeDetails : detailsList) {
			String middleName = " ";
			String lastName = "";
			String ownerName = "";
			if (!StringUtils.isEmpty(employeeDetails.getLastName())) {

				lastName = employeeDetails.getLastName();
			}

			if (employeeDetails.getMiddleName() != null && employeeDetails.getMiddleName().length() > 0) {

				middleName = employeeDetails.getMiddleName();
				ownerName = employeeDetails.getFirstName() + " " + middleName + " " + lastName;
			} else {

				ownerName = employeeDetails.getFirstName() + " " + lastName;
			}
			if (ownerName != null && ownerName.contains(name.trim())) {
				filterList.add(employeeDetails);
			}
		}

		if (null != filterList && !filterList.isEmpty()) {
			mapperList = filterList.stream()
					.flatMap(employeeDetails -> contactRepository
							.getByUserIdAndContactTypeAndLiveInd(employeeDetails.getUserId(), contactType).stream())
					.map(contact -> getContactDetailsById(contact.getContactId())).collect(Collectors.toList());
		}

		return mapperList;
	}

	@Override
	public List<ContactViewMapper> getContactDetailsByNameForTeam(String name, String userId, String contactType) {
		Set<String> userIds = employeeRepository.getEmployeeDetailsByReportingManagerId(userId).stream()
				.map(EmployeeDetails::getUserId).collect(Collectors.toSet());
		userIds.add(userId);

		List<String> userIdss = new ArrayList<>(userIds);

		List<ContactDetails> list = contactRepository.getTeamContactListByUserIds(userIdss, contactType);
		List<ContactDetails> filterList = list.parallelStream().filter(detail -> {
			return detail.getFullName() != null && Utility.containsIgnoreCase(detail.getFullName(), name.trim());
		}).collect(Collectors.toList());
		List<ContactViewMapper> mapperList = new ArrayList<ContactViewMapper>();
		if (null != filterList && !filterList.isEmpty()) {

			mapperList = filterList.parallelStream().map(li -> getContactDetailsById(li.getContactId()))
					.collect(Collectors.toList());

		}

		return mapperList;

	}

	@Override
	public List<ContactViewMapper> getContactBySectorForTeam(String name, String userId, String orgId,
			String contactType) {
		Set<String> userIds = employeeRepository.getEmployeeDetailsByReportingManagerId(userId).stream()
				.map(EmployeeDetails::getUserId).collect(Collectors.toSet());
		userIds.add(userId);

		List<String> userIdss = new ArrayList<>(userIds);
		List<ContactViewMapper> mapperList = new ArrayList<>();
		SectorDetails sectorDetails = sectorDetailsRepository.getSectorDetailsBySectorNameAndOrgId(name, orgId);
		if (null != sectorDetails) {
			List<ContactDetails> list = contactRepository.getTeamContactListByUserIdsAndSectorAndContactType(userIdss,
					sectorDetails.getSectorId(),contactType);
			List<ContactDetails> filterList = list.parallelStream().filter(detail -> {
				return detail.getFullName() != null && Utility.containsIgnoreCase(detail.getFullName(), name.trim());
			}).collect(Collectors.toList());
			if (null != filterList && !filterList.isEmpty()) {

				mapperList = filterList.parallelStream().map(li -> getContactDetailsById(li.getContactId()))
						.collect(Collectors.toList());

			}
		}
		return mapperList;

	}

	@Override
	public List<ContactViewMapper> getContactBySourceForTeam(String name, String userId, String orgId,
			String contactType) {
		Set<String> userIds = employeeRepository.getEmployeeDetailsByReportingManagerId(userId).stream()
				.map(EmployeeDetails::getUserId).collect(Collectors.toSet());
		userIds.add(userId);

		List<String> userIdss = new ArrayList<>(userIds);
		List<ContactViewMapper> mapperList = new ArrayList<>();
		Source source = sourceRepository.findByNameAndOrgId(name, orgId);
		if (null != source) {
			List<ContactDetails> list = contactRepository.getTeamContactListByUserIdsAndSourceAndContactType(userIdss,
					source.getSourceId(),contactType);
			List<ContactDetails> filterList = list.parallelStream().filter(detail -> {
				return detail.getFullName() != null && Utility.containsIgnoreCase(detail.getFullName(), name.trim());
			}).collect(Collectors.toList());
			if (null != filterList && !filterList.isEmpty()) {

				mapperList = filterList.parallelStream().map(li -> getContactDetailsById(li.getContactId()))
						.collect(Collectors.toList());

			}
		}
		return mapperList;

	}

	@Override
	public List<ContactViewMapper> getContactByOwnerNameForTeam(String name, String userId, String orgId,
			String contactType) {
		List<ContactViewMapper> mapperList = new ArrayList<>();

		List<EmployeeDetails> detailsList = employeeRepository.getEmployeeDetailsByReportingManagerIdAndUserId(userId);

		List<EmployeeDetails> filterList = new ArrayList<>();
		for (EmployeeDetails employeeDetails : detailsList) {
			String middleName = " ";
            String lastName = "";
            String ownerName = "";
            if (!StringUtils.isEmpty(employeeDetails.getLastName())) {

                lastName = employeeDetails.getLastName();
            }

            if (employeeDetails.getMiddleName() != null && employeeDetails.getMiddleName().length() > 0) {

                middleName = employeeDetails.getMiddleName();
                ownerName = employeeDetails.getFirstName() + " " + middleName + " " + lastName;
            } else {

            	ownerName = employeeDetails.getFirstName() + " " + lastName;
            }
		    if (ownerName != null && ownerName.contains(name.trim())) {
		        filterList.add(employeeDetails);
		    }
		}

		if (null != filterList && !filterList.isEmpty()) {
			mapperList = filterList.stream().flatMap(
					employeeDetails -> contactRepository.findByUserIdAndContactType(employeeDetails.getUserId(),contactType).stream())
					.map(li -> getContactDetailsById(li.getContactId())).collect(Collectors.toList());
		}
		return mapperList;
	}

	@Override
	public List<ContactViewMapper> getContactDetailsByNameByUserId(String name, String userId, String contactType) {
		List<ContactDetails> list = contactRepository.findByUserIdAndContactType(userId,contactType);
		List<ContactDetails> filterList = list.parallelStream().filter(detail -> {
			return detail.getFullName() != null && Utility.containsIgnoreCase(detail.getFullName(), name.trim());
		}).collect(Collectors.toList());
		List<ContactViewMapper> mapperList = new ArrayList<ContactViewMapper>();
		if (null != filterList && !filterList.isEmpty()) {

			mapperList = filterList.parallelStream().map(li -> getContactDetailsById(li.getContactId()))
					.collect(Collectors.toList());

		}

		return mapperList;

	}

	@Override
	public List<ContactViewMapper> getContactBySectorAndByUserId(String name, String userId, String orgId,
			String contactType) {
		List<ContactViewMapper> mapperList = new ArrayList<ContactViewMapper>();

		SectorDetails sectorDetails = sectorDetailsRepository.getSectorDetailsBySectorNameAndOrgId(name, orgId);
		if (null != sectorDetails) {
			List<ContactDetails> list = contactRepository.findBySectorAndLiveIndAndUserIdAndContactType(sectorDetails.getSectorId(),
					userId,contactType);

			List<ContactDetails> filterList = list.parallelStream().filter(detail -> {
				return detail.getFullName() != null && Utility.containsIgnoreCase(detail.getFullName(), name.trim());
			}).collect(Collectors.toList());
			if (null != filterList && !filterList.isEmpty()) {

				mapperList = filterList.parallelStream().map(li -> getContactDetailsById(li.getContactId()))
						.collect(Collectors.toList());

			}
		}
		return mapperList;

	}

	@Override
	public List<ContactViewMapper> getContactBySourceAndByUserId(String name, String userId, String orgId,
			String contactType) {
		List<ContactViewMapper> mapperList = new ArrayList<>();

		Source source = sourceRepository.findByNameAndOrgId(name, orgId);
		if (null != source) {
			List<ContactDetails> list = contactRepository.findBySourceAndLiveIndAndUserIdAndContactType(source.getSourceId(),userId,contactType);

			List<ContactDetails> filterList = list.parallelStream().filter(detail -> {
				return detail.getFullName() != null && Utility.containsIgnoreCase(detail.getFullName(), name.trim());
			}).collect(Collectors.toList());
			if (null != filterList && !filterList.isEmpty()) {

				mapperList = filterList.parallelStream().map(li -> getContactDetailsById(li.getContactId()))
						.collect(Collectors.toList());

			}
		}
		return mapperList;

	}

	@Override
	public List<ContactViewMapper> getContactByOwnerNameAndByUserId(String name, String userId, String orgId,
			String contactType) {
		List<ContactViewMapper> mapperList = new ArrayList<>();

		List<EmployeeDetails> detailsList = employeeRepository.getEmployeesByOrgId(orgId);

		List<EmployeeDetails> filterList = new ArrayList<>();
		for (EmployeeDetails employeeDetails : detailsList) {
			String middleName = " ";
            String lastName = "";
            String ownerName = "";
            if (!StringUtils.isEmpty(employeeDetails.getLastName())) {

                lastName = employeeDetails.getLastName();
            }

            if (employeeDetails.getMiddleName() != null && employeeDetails.getMiddleName().length() > 0) {

                middleName = employeeDetails.getMiddleName();
                ownerName = employeeDetails.getFirstName() + " " + middleName + " " + lastName;
            } else {

            	ownerName = employeeDetails.getFirstName() + " " + lastName;
            }
		    if (ownerName != null && ownerName.contains(name.trim())) {
		        filterList.add(employeeDetails);
		    }
		}

		if (null != filterList && !filterList.isEmpty()) {
			mapperList = filterList.stream().flatMap(
					employeeDetails -> contactRepository.findByUserIdAndContactType(employeeDetails.getUserId(),contactType).stream())
					.map(contact -> getContactDetailsById(contact.getContactId())).collect(Collectors.toList());
			
		}
		return mapperList;
	}

}
