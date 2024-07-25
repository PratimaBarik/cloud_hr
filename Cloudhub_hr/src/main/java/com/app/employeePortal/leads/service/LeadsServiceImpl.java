package com.app.employeePortal.leads.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.app.employeePortal.employee.entity.EmployeeAddressLink;
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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.employeePortal.Opportunity.entity.OpportunityDetails;
import com.app.employeePortal.Opportunity.mapper.OpportunityMapper;
import com.app.employeePortal.Opportunity.mapper.OpportunityViewMapper;
import com.app.employeePortal.Opportunity.repository.OpportunityDetailsRepository;
import com.app.employeePortal.Opportunity.service.OpportunityService;
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
import com.app.employeePortal.call.entity.CustomerCallLink;
import com.app.employeePortal.call.entity.EmployeeCallLink;
import com.app.employeePortal.call.entity.LeadsCallLink;
import com.app.employeePortal.call.repository.CallDetailsRepository;
import com.app.employeePortal.call.repository.CustomerCallLinkRepo;
import com.app.employeePortal.call.repository.EmployeeCallRepository;
import com.app.employeePortal.call.repository.LeadsCallLinkRepo;
import com.app.employeePortal.candidate.entity.DefinationDetails;
import com.app.employeePortal.candidate.entity.DefinationInfo;
import com.app.employeePortal.candidate.repository.DefinationInfoRepository;
import com.app.employeePortal.candidate.repository.DefinationRepository;
import com.app.employeePortal.category.entity.DistributionAutomation;
import com.app.employeePortal.category.entity.DistributionAutomationAssignedTo;
import com.app.employeePortal.category.entity.LeadsCatagory;
import com.app.employeePortal.category.entity.LobDetails;
import com.app.employeePortal.category.repository.DistributionAutomationAssignedToRepository;
import com.app.employeePortal.category.repository.DistributionAutomationRepository;
import com.app.employeePortal.category.repository.LeadsCatagoryRepository;
import com.app.employeePortal.category.repository.LobDetailsRepository;
import com.app.employeePortal.config.AesEncryptor;
import com.app.employeePortal.contact.entity.ContactDetails;
import com.app.employeePortal.contact.entity.ContactNotesLink;
import com.app.employeePortal.contact.mapper.ContactMapper;
import com.app.employeePortal.contact.mapper.ContactViewMapper;
import com.app.employeePortal.contact.repository.ContactNotesLinkRepository;
import com.app.employeePortal.contact.repository.ContactRepository;
import com.app.employeePortal.contact.service.ContactService;
import com.app.employeePortal.customer.entity.Customer;
import com.app.employeePortal.customer.entity.CustomerAddressLink;
import com.app.employeePortal.customer.entity.CustomerContactLink;
import com.app.employeePortal.customer.entity.CustomerDocumentLink;
import com.app.employeePortal.customer.entity.CustomerInfo;
import com.app.employeePortal.customer.entity.CustomerInitiativeLink;
import com.app.employeePortal.customer.entity.InitiativeDetails;
import com.app.employeePortal.customer.mapper.CustomerResponseMapper;
import com.app.employeePortal.customer.mapper.InitiativeDetailsMapper;
import com.app.employeePortal.customer.mapper.InitiativeSkillMapper;
import com.app.employeePortal.customer.mapper.TransferMapper;
import com.app.employeePortal.customer.repository.CustomerAddressLinkRepository;
import com.app.employeePortal.customer.repository.CustomerContactLinkRepository;
import com.app.employeePortal.customer.repository.CustomerDocumentLinkRepository;
import com.app.employeePortal.customer.repository.CustomerInfoRepository;
import com.app.employeePortal.customer.repository.CustomerInitiativeLinkRepository;
import com.app.employeePortal.customer.repository.CustomerNotesLinkRepository;
import com.app.employeePortal.customer.repository.CustomerRepository;
import com.app.employeePortal.customer.repository.InitiativeDetailsRepository;
import com.app.employeePortal.document.entity.DocumentDetails;
import com.app.employeePortal.document.entity.DocumentType;
import com.app.employeePortal.document.mapper.DocumentMapper;
import com.app.employeePortal.document.repository.DocumentDetailsRepository;
import com.app.employeePortal.document.repository.DocumentTypeRepository;
import com.app.employeePortal.document.service.DocumentService;
import com.app.employeePortal.employee.entity.EmployeeDetails;
import com.app.employeePortal.employee.entity.Notes;
import com.app.employeePortal.employee.mapper.EmployeeShortMapper;
import com.app.employeePortal.employee.mapper.NotesMapper;
import com.app.employeePortal.employee.repository.EmployeeRepository;
import com.app.employeePortal.employee.repository.NotesRepository;
import com.app.employeePortal.employee.service.EmployeeService;
import com.app.employeePortal.event.entity.CustomerEventLink;
import com.app.employeePortal.event.entity.EmployeeEventLink;
import com.app.employeePortal.event.entity.EventDetails;
import com.app.employeePortal.event.entity.LeadsEventLink;
import com.app.employeePortal.event.repository.CustomerEventRepo;
import com.app.employeePortal.event.repository.EmployeeEventRepository;
import com.app.employeePortal.event.repository.EventDetailsRepository;
import com.app.employeePortal.event.repository.LeadsEventRepo;
import com.app.employeePortal.investor.entity.Investor;
import com.app.employeePortal.investor.mapper.InvestorViewMapper;
import com.app.employeePortal.investorleads.entity.InvestorLeadsDocumentLink;
import com.app.employeePortal.leads.entity.Leads;
import com.app.employeePortal.leads.entity.LeadsAddressLink;
import com.app.employeePortal.leads.entity.LeadsContactLink;
import com.app.employeePortal.leads.entity.LeadsDocumentLink;
import com.app.employeePortal.leads.entity.LeadsInfo;
import com.app.employeePortal.leads.entity.LeadsInnitiativeLink;
import com.app.employeePortal.leads.entity.LeadsNotesLink;
import com.app.employeePortal.leads.entity.LeadsOpportunityLink;
import com.app.employeePortal.leads.entity.LeadsSkillLink;
import com.app.employeePortal.leads.entity.RoundRobbinConfig;
import com.app.employeePortal.leads.mapper.ActivityMapper;
import com.app.employeePortal.leads.mapper.LeadsMapper;
import com.app.employeePortal.leads.mapper.LeadsOpportunityMapper;
import com.app.employeePortal.leads.mapper.LeadsReportMapper;
import com.app.employeePortal.leads.mapper.LeadsSkillLinkMapper;
import com.app.employeePortal.leads.mapper.LeadsViewMapper;
import com.app.employeePortal.leads.repository.LeadsAddressLinkRepository;
import com.app.employeePortal.leads.repository.LeadsContactLinkRepository;
import com.app.employeePortal.leads.repository.LeadsDocumentLinkRepository;
import com.app.employeePortal.leads.repository.LeadsInfoRepository;
import com.app.employeePortal.leads.repository.LeadsInnitiativeLinkRepository;
import com.app.employeePortal.leads.repository.LeadsNotesLinkRepository;
import com.app.employeePortal.leads.repository.LeadsOpportunityLinkRepository;
import com.app.employeePortal.leads.repository.LeadsRepository;
import com.app.employeePortal.leads.repository.LeadsSkillLinkRepository;
import com.app.employeePortal.leads.repository.RoundRobbinConfigRepo;
import com.app.employeePortal.notification.entity.Notificationparam;
import com.app.employeePortal.notification.service.NotificationService;
import com.app.employeePortal.opportunityWorkflow.entity.OpportunityStages;
import com.app.employeePortal.opportunityWorkflow.entity.OpportunityWorkflowDetails;
import com.app.employeePortal.opportunityWorkflow.repository.OpportunityStagesRepository;
import com.app.employeePortal.opportunityWorkflow.repository.OpportunityWorkflowDetailsRepository;
import com.app.employeePortal.registration.entity.Country;
import com.app.employeePortal.registration.repository.CountryRepository;
import com.app.employeePortal.sector.entity.SectorDetails;
import com.app.employeePortal.sector.repository.SectorDetailsRepository;
import com.app.employeePortal.source.entity.Source;
import com.app.employeePortal.source.repository.SourceRepository;
import com.app.employeePortal.task.entity.CustomerTaskLink;
import com.app.employeePortal.task.entity.EmployeeTaskLink;
import com.app.employeePortal.task.entity.LeadsTaskLink;
import com.app.employeePortal.task.entity.TaskDetails;
import com.app.employeePortal.task.entity.TaskDocumentLink;
import com.app.employeePortal.task.repository.CustomerTaskRepo;
import com.app.employeePortal.task.repository.EmployeeTaskRepository;
import com.app.employeePortal.task.repository.LeadsTaskRepo;
import com.app.employeePortal.task.repository.TaskDetailsRepository;
import com.app.employeePortal.task.repository.TaskDocumentLinkRepository;
import com.app.employeePortal.util.Utility;

import freemarker.template.TemplateException;

@Service
@Transactional
public class LeadsServiceImpl implements LeadsService {

	@Autowired
	LeadsInfoRepository leadsInfoRepository;

	@Autowired
	LeadsRepository leadsRepository;

	@Autowired
	SectorDetailsRepository sectorDetailsRepository;

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	AddressInfoRepository addressInfoRepository;

	@Autowired
	AddressRepository addressRepository;

	@Autowired
	LeadsAddressLinkRepository leadsAddressLinkRepository;

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	CustomerAddressLinkRepository customerAddressLinkRepository;

	@Autowired
	ContactRepository contactRepository;

	@Autowired
	LeadsContactLinkRepository leadsContactLinkRepository;

	@Autowired
	NotesRepository notesRepository;
	@Autowired
	LeadsNotesLinkRepository leadsNotesLinkRepository;
	@Autowired
	LeadsDocumentLinkRepository leadsDocumentLinkRepository;
	@Autowired
	DocumentDetailsRepository documentDetailsRepository;
	@Autowired
	DocumentTypeRepository documentTypeRepository;
	@Autowired
	ContactService contactService;
	@Autowired
	CustomerInfoRepository customerInfoRepository;
	@Autowired
	OpportunityDetailsRepository opportunityDetailsRepository;

	@Autowired
	LeadsOpportunityLinkRepository leadsOpportunityLinkRepository;
	@Autowired
	LeadsInnitiativeLinkRepository leadsInnitiativeLinkRepository;

	@Autowired
	InitiativeDetailsRepository initiativeDetailsRepository;
	@Autowired
	DefinationRepository definationRepository;

	@Autowired
	OpportunityService opportunityService;

	@Autowired
	EmployeeService employeeService;

	@Autowired
	CustomerContactLinkRepository customerContactLinkRepository;

	@Autowired
	CustomerDocumentLinkRepository customerDocumentLinkRepository;
	@Autowired
	CustomerNotesLinkRepository customerNotesLinkRepository;

	@Autowired
	CustomerInitiativeLinkRepository customerInitiativeLinkRepository;
	@Autowired
	LeadsSkillLinkRepository leadsSkillLinkRepository;
	@Autowired
	DefinationInfoRepository definationInfoRepository;
	@Autowired
	LeadsCatagoryRepository leadsCatagoryRepository;
	@Autowired
	OpportunityStagesRepository opportunityStagesRepository;
	@Autowired
	OpportunityWorkflowDetailsRepository opportunityWorkflowDetailsRepository;
	@Autowired
	SourceRepository sourceRepository;
	@Autowired
	CountryRepository countryRepository;
	@Autowired
	LeadsCallLinkRepo leadsCallLinkRepo;
	@Autowired
	CallDetailsRepository callDetailsRepository;
	@Autowired
	LeadsEventRepo leadsEventRepo;
	@Autowired
	EventDetailsRepository eventDetailsRepository;
	@Autowired
	LeadsTaskRepo leadsTaskRepo;
	@Autowired
	TaskDetailsRepository taskDetailsRepository;
	@Autowired
	ContactNotesLinkRepository contactNotesLinkRepository;
	@Autowired
	CustomerCallLinkRepo customerCallLinkRepository;
	@Autowired
	LeadsCallLinkRepo leadsCallLinkRepository;
	@Autowired
	LeadsEventRepo leadsEventLinkRepository;
	@Autowired
	CustomerEventRepo customerEventLinkRepository;
	@Autowired
	LeadsTaskRepo leadsTaskLinkRepository;
	@Autowired
	CustomerTaskRepo customerTaskLinkRepository;
//	@Autowired
//	DynamoDBMapper dynamoDB;
	@Autowired
	TeamRepository teamRepository;
	@Autowired
	TeamMemberLinkRepo teamMemberLinkRepo;
	@Autowired
	DistributionAutomationRepository distributionAutomationRepository;
	@Autowired
	DistributionAutomationAssignedToRepository distributionAutomationAssignedToRepository;
	@Autowired
	RoundRobbinConfigRepo roundRobbinConfigRepo;
	@Autowired
	EmployeeCallRepository employeeCallRepository;
	@Autowired
	EmployeeTaskRepository employeeTaskRepository;
	@Autowired
	EmployeeEventRepository employeeEventRepository;
	@Autowired
	NotificationService notificationService;
	@Autowired
	DocumentService documentService;
	@Autowired
	TaskDocumentLinkRepository taskDocumentLinkRepository;
	@Autowired
	LobDetailsRepository lobDetailsRepository;
	@Autowired
	AesEncryptor encryptor;

	private String[] leads_headings = { "Leads Id", "CompanyName", "FirstName", "MiddleName", "LastName", "URL",
			"Phone Number", "Notes" };

	@Override
	public LeadsViewMapper saveLeads(LeadsMapper leadsMapper) throws IOException, TemplateException {
		LeadsViewMapper resultMapper = null;
		String leadsInfoId = null;

		LeadsInfo leadsInfo = new LeadsInfo();

		leadsInfo.setCreationDate(new Date());

		leadsInfoId = leadsInfoRepository.save(leadsInfo).getId();

		Leads leads = new Leads();

		setPropertyOnInput(leadsMapper, leads, leadsInfoId);

		ContactMapper contactMapper = new ContactMapper();
		contactMapper.setSalutation(leadsMapper.getSalutation());
		contactMapper.setFirstName(leadsMapper.getFirstName());
		contactMapper.setMiddleName(leadsMapper.getMiddleName());
		contactMapper.setLastName(leadsMapper.getLastName());
		contactMapper.setEmailId(leadsMapper.getEmail());
		contactMapper.setPhoneNumber(leadsMapper.getPhoneNumber());
		contactMapper.setMobileNumber(leadsMapper.getPhoneNumber());
		contactMapper.setLeadsId(leadsInfoId);
		contactMapper.setCountryDialCode(leadsMapper.getCountryDialCode());
		contactMapper.setCountryDialCode1(leadsMapper.getCountryDialCode());
		contactMapper.setImageId(leadsMapper.getImageId());
		contactMapper.setAddress(leadsMapper.getAddress());
		contactMapper.setUserId(leadsMapper.getUserId());
		contactMapper.setOrganizationId(leadsMapper.getOrganizationId());
		contactMapper.setBedrooms(leadsMapper.getBedrooms());
		contactMapper.setPrice(leadsMapper.getPrice());
		contactMapper.setPropertyType(leadsMapper.getPropertyType());

		ContactViewMapper resultMapperr = contactService.saveContact(contactMapper);
		System.out.println("contactID===========++++++++++++++++++++++++++++++++++===" + resultMapperr.getContactId());
		if (null != resultMapperr.getContactId()) {

			/* insert to leads-contact-link */
			LeadsContactLink leadsContactLink = new LeadsContactLink();
			leadsContactLink.setLeadsId(contactMapper.getLeadsId());
			leadsContactLink.setContactId(resultMapperr.getContactId());
			leadsContactLink.setCreationDate(new Date());

			leadsContactLinkRepository.save(leadsContactLink);
		}

		leadsRepository.save(leads);

		/* insert to Notification Table */
		Notificationparam param = new Notificationparam();
		EmployeeDetails emp = employeeRepository.getEmployeesByuserId(leadsMapper.getUserId());
		String name = employeeService.getEmployeeFullNameByObject(emp);
		param.setEmployeeDetails(emp);
		param.setAdminMsg("Lead " + "'" + leadsMapper.getName() + "' created by " + name);
		param.setOwnMsg("Lead " + leadsMapper.getName() + " created.");
		param.setNotificationType("Lead Creation");
		param.setProcessNmae("Leads");
		param.setType("create");
		param.setEmailSubject("Korero alert- Lead created");
		param.setCompanyName("Korero");
		param.setUserId(leadsMapper.getUserId());

		if (!leadsMapper.getUserId().equals(leadsMapper.getAssignedTo())) {
			List<String> assignToUserIds = new ArrayList<>();
			assignToUserIds.add(leadsMapper.getAssignedTo());
			param.setAssignToUserIds(assignToUserIds);
			param.setAssignToMsg("Lead " + "'" + leadsMapper.getName() + "'" + " assigned to "
					+ employeeService.getEmployeeFullName(leadsMapper.getAssignedTo()) + " by " + name);
		}
		notificationService.createNotificationForDynamicUsers(param);

		resultMapper = getLeadsDetailsById(leadsInfoId);
		return resultMapper;
	}

	private void setPropertyOnInput(LeadsMapper leadsMapper, Leads leads, String leadsInfoId) {

		leads.setLeadsId(leadsInfoId);
		// leads.setName(leadsMapper.getName());
		leads.setSalutation(leadsMapper.getSalutation());
		leads.setFirstName(leadsMapper.getFirstName());
		leads.setMiddleName(leadsMapper.getMiddleName());
		leads.setLastName(leadsMapper.getLastName());
		leads.setImageId(leadsMapper.getImageId());
		leads.setUrl(leadsMapper.getUrl());
		leads.setNotes(leadsMapper.getNotes());
		leads.setCountryDialCode(leadsMapper.getCountryDialCode());
		leads.setPhoneNumber(leadsMapper.getPhoneNumber());
		leads.setEmail(leadsMapper.getEmail());
		leads.setUserId(leadsMapper.getUserId());
		leads.setOrganizationId(leadsMapper.getOrganizationId());
		leads.setGroup(leadsMapper.getGroup());
		leads.setVatNo(leadsMapper.getVatNo());
		leads.setCreationDate(new Date());
		leads.setLiveInd(true);
		leads.setDocumentId(leadsMapper.getDocumentId());
		leads.setSector(leadsMapper.getSectorId());
		System.out.println("get sector in frontend............" + leadsMapper.getSectorId());
		leads.setCountry(leadsMapper.getCountry());
		leads.setZipcode(leadsMapper.getZipcode());
		leads.setZipcode(leadsMapper.getZipcode());
		leads.setCategory(leadsMapper.getCategory());
		leads.setImageURL(leadsMapper.getImageURL());
		leads.setAssignedTo(leadsMapper.getAssignedTo());
		leads.setBusinessRegistration(leadsMapper.getBusinessRegistration());
		leads.setConvertInd(false);
		leads.setCompanyName(leadsMapper.getName());
		leads.setType(leadsMapper.getType());
		leads.setTypeUpdationDate(new Date());
		leads.setJunkInd(false);
		leads.setSource(leadsMapper.getSource());
		leads.setSourceUserId(leadsMapper.getSourceUserId());
		leads.setCreationType("InApp");
		leads.setAssignedBy(leadsMapper.getUserId());
		leads.setBedroom(leadsMapper.getBedrooms());
		leads.setPrice(leadsMapper.getPrice());
		leads.setPropertyType(leadsMapper.getPropertyType());
		leads.setLob(leadsMapper.getLobId());

		String middleName2 = "";
		String lastName2 = "";
		String satutation1 = "";

		if (!StringUtils.isEmpty(leadsMapper.getLastName())) {

			lastName2 = leadsMapper.getLastName();
		}
		if (leadsMapper.getMiddleName() != null && leadsMapper.getMiddleName().length() > 0) {

			middleName2 = leadsMapper.getMiddleName();
			if (leadsMapper.getSalutation() != null && leadsMapper.getSalutation().length() > 0) {
				satutation1 = leadsMapper.getSalutation();
				leads.setName(satutation1 + " " + leadsMapper.getFirstName() + " " + middleName2 + " " + lastName2);
			} else {

				leads.setName(leadsMapper.getFirstName() + " " + middleName2 + " " + lastName2);
			}
		} else {

			if (leadsMapper.getSalutation() != null && leadsMapper.getSalutation().length() > 0) {
				satutation1 = leadsMapper.getSalutation();
				leads.setName(satutation1 + " " + leadsMapper.getFirstName() + " " + lastName2);
			} else {

				leads.setName(leadsMapper.getFirstName() + " " + lastName2);
			}
		}

		if (leadsMapper.getAddress().size() > 0) {
			for (AddressMapper addressMapper : leadsMapper.getAddress()) {
				/* insert to address info & address deatils & leadsaddressLink */

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
					addressDetails.setXlAddress(addressMapper.getXlAddress());
					addressRepository.save(addressDetails);

					LeadsAddressLink leadsAddressLink = new LeadsAddressLink();
					leadsAddressLink.setLeadsId(leadsInfoId);
					leadsAddressLink.setAddressId(addressId);
					leadsAddressLink.setCreationDate(new Date());

					leadsAddressLinkRepository.save(leadsAddressLink);

				}
			}
		}

		Notes notes = new Notes();
		notes.setCreation_date(new Date());
		notes.setNotes(leadsMapper.getNotes());
		notes.setLiveInd(true);
		Notes note = notesRepository.save(notes);

		String notesId = note.getNotes_id();
		/* insert to CallNoteLink table */

		LeadsNotesLink callNote = new LeadsNotesLink();
		callNote.setLeadsId(leadsInfoId);
		callNote.setNotesId(notesId);
		callNote.setCreationDate(new Date());
		leadsNotesLinkRepository.save(callNote);

	}

	@Override
	public LeadsViewMapper getLeadsDetailsById(String leadsId) {
		System.out.println("leadsId" + leadsId);
		Leads leads = leadsRepository.getLeadsByIdAndLiveInd(leadsId);
		System.out.println("Leads object is..." + leads);

		LeadsViewMapper leadsViewMapper = new LeadsViewMapper();

		if (null != leads) {

			if (null != leads.getSector() && leads.getSector().trim().length() > 0) {
				SectorDetails sector = sectorDetailsRepository.getSectorDetailsBySectorId(leads.getSector());
				System.out.println("get sectordetails by id returns........." + leads.getSector());
				System.out.println("sector object is......." + sector);
				if (null != sector) {
					leadsViewMapper.setSector(sector.getSectorName());
					leadsViewMapper.setSectorId(leads.getSector());
				} else {
					leadsViewMapper.setSector("");
					leadsViewMapper.setSectorId("");
				}
			}
			leadsViewMapper.setLeadsId(leadsId);
			// leadsViewMapper.setName(leads.getName());
			leadsViewMapper.setSalutation(leads.getSalutation());
			leadsViewMapper.setFirstName(leads.getFirstName());
			leadsViewMapper.setMiddleName(leads.getMiddleName());
			leadsViewMapper.setLastName(leads.getLastName());
			leadsViewMapper.setImageId(leads.getImageId());
			leadsViewMapper.setUrl(leads.getUrl());
			leadsViewMapper.setNotes(leads.getNotes());
			leadsViewMapper.setEmail(leads.getEmail());
			leadsViewMapper.setGroup(leads.getGroup());
			leadsViewMapper.setVatNo(leads.getVatNo());
			leadsViewMapper.setPhoneNumber(leads.getPhoneNumber());
			leadsViewMapper.setCountryDialCode(leads.getCountryDialCode());
			leadsViewMapper.setUserId(leads.getUserId());
			leadsViewMapper.setOrganizationId(leads.getOrganizationId());
			leadsViewMapper.setCreationDate(Utility.getISOFromDate(leads.getCreationDate()));
			leadsViewMapper.setImageURL(leads.getImageURL());

			if (!StringUtils.isEmpty(leads.getCountry())) {
				leadsViewMapper.setCountry(leads.getCountry());
				Country country = countryRepository.getCountryDetailsByCountryNameAndOrgId(leads.getCountry(),
						leads.getOrganizationId());
				if (null != country) {
					leadsViewMapper.setCountry_alpha3_code(country.getCountry_alpha3_code());
					leadsViewMapper.setCountry_alpha2_code(country.getCountry_alpha2_code());
				}
			} else {
				List<LeadsAddressLink> leadsAddressLinkList = leadsAddressLinkRepository
						.getAddressListByLeadsId(leads.getLeadsId());
				if (null != leadsAddressLinkList && !leadsAddressLinkList.isEmpty()) {
					for (LeadsAddressLink leadsAddressLink : leadsAddressLinkList) {
						AddressDetails addressDetails = addressRepository
								.getAddressDetailsByAddressId(leadsAddressLink.getAddressId());
						if (null != addressDetails) {
							if (!StringUtils.isEmpty(addressDetails.getCountry())) {
								leadsViewMapper.setCountry(addressDetails.getCountry());
								Country country = countryRepository.getCountryDetailsByCountryNameAndOrgId(
										addressDetails.getCountry(), leads.getOrganizationId());
								if (null != country) {
									leadsViewMapper.setCountry_alpha3_code(country.getCountry_alpha3_code());
									leadsViewMapper.setCountry_alpha2_code(country.getCountry_alpha2_code());
								}
							}
						}
					}
				}
			}
			// customerMapper.setSector(sector.getSectorName());
			leadsViewMapper.setZipcode(leads.getZipcode());

			leadsViewMapper.setDocumentId(leads.getDocumentId());
			leadsViewMapper.setCategory(leads.getCategory());
			leadsViewMapper.setBusinessRegistration(leads.getBusinessRegistration());
			leadsViewMapper.setConvertInd(leads.isConvertInd());
			leadsViewMapper.setConvertionDate(Utility.getISOFromDate(leads.getConvertionDate()));
			leadsViewMapper.setCompanyName(leads.getCompanyName());
			leadsViewMapper.setType(leads.getType());
			leadsViewMapper.setTypeUpdationDate(Utility.getISOFromDate(leads.getTypeUpdationDate()));
			leadsViewMapper.setJunkInd(leads.isJunkInd());
			leadsViewMapper.setSourceUserID(leads.getSourceUserId());
			leadsViewMapper.setSourceUserName(employeeService.getEmployeeFullName(leads.getSourceUserId()));
			leadsViewMapper.setCreationType(leads.getCreationType());
			leadsViewMapper.setAssignedBy(employeeService.getEmployeeFullName(leads.getAssignedBy()));
			leadsViewMapper.setBedrooms(leads.getBedroom());
			leadsViewMapper.setPrice(leads.getPrice());
			leadsViewMapper.setPropertyType(leads.getPropertyType());

			Source source = sourceRepository.findBySourceId(leads.getSource());
			if (null != source) {
				leadsViewMapper.setSource(source.getName());
			}

			LobDetails lobs = lobDetailsRepository.findByLobDetailsId(leads.getLob());
			if (null != lobs) {
				leadsViewMapper.setLob(lobs.getName());
				leadsViewMapper.setLobId(lobs.getLobDetailsId());
			}

			String middleName2 = "";
			String lastName2 = "";
			String satutation1 = "";

			if (!StringUtils.isEmpty(leads.getLastName())) {

				lastName2 = leads.getLastName();
			}
			if (leads.getMiddleName() != null && leads.getMiddleName().length() > 0) {

				middleName2 = leads.getMiddleName();
				if (leads.getSalutation() != null && leads.getSalutation().length() > 0) {
					satutation1 = leads.getSalutation();
					leadsViewMapper
							.setName(satutation1 + " " + leads.getFirstName() + " " + middleName2 + " " + lastName2);
				} else {

					leadsViewMapper.setName(leads.getFirstName() + " " + middleName2 + " " + lastName2);
				}
			} else {

				if (leads.getSalutation() != null && leads.getSalutation().length() > 0) {
					satutation1 = leads.getSalutation();
					leadsViewMapper.setName(satutation1 + " " + leads.getFirstName() + " " + lastName2);
				} else {

					leadsViewMapper.setName(leads.getFirstName() + " " + lastName2);
				}
			}

			EmployeeDetails employeeDetails = employeeRepository.getEmployeeDetailsByEmployeeId(leads.getUserId());
			if (null != employeeDetails) {
				String middleName = " ";
				String lastName = "";

				if (!StringUtils.isEmpty(employeeDetails.getLastName())) {

					lastName = employeeDetails.getLastName();
				}

				if (employeeDetails.getMiddleName() != null && employeeDetails.getMiddleName().length() > 0) {

					middleName = employeeDetails.getMiddleName();
					leadsViewMapper.setOwnerName(employeeDetails.getFirstName() + " " + middleName + " " + lastName);
					leadsViewMapper.setOwnerImageId(employeeDetails.getImageId());
				} else {

					leadsViewMapper.setOwnerName(employeeDetails.getFirstName() + " " + lastName);
					leadsViewMapper.setOwnerImageId(employeeDetails.getImageId());
				}

			}
			EmployeeDetails employeeDetail = employeeRepository.getEmployeeDetailsByEmployeeId(leads.getAssignedTo());
			if (null != employeeDetail) {
				String middleName = " ";
				String lastName = "";

				if (!StringUtils.isEmpty(employeeDetail.getLastName())) {

					lastName = employeeDetail.getLastName();
				}

				if (employeeDetail.getMiddleName() != null && employeeDetail.getMiddleName().length() > 0) {

					middleName = employeeDetail.getMiddleName();
					leadsViewMapper.setAssignedTo(employeeDetail.getFirstName() + " " + middleName + " " + lastName);

				} else {

					leadsViewMapper.setAssignedTo(employeeDetail.getFirstName() + " " + lastName);

				}

			}
//		//leads opportunity
//		LeadsOpportunityLink leadsOpportunityLinkList = leadsOpportunityLinkRepository.getOpportunityByLeadsId(leadsId);
//		if (leadsOpportunityLinkList != null ) {
//			leadsViewMapper.setOpportunityName(leadsOpportunityLinkList.getOpportunityName());
//			leadsViewMapper.setProposalValue(leadsOpportunityLinkList.getProposalValue());
//		}

//			List<AddressMapper> addressList = new ArrayList<AddressMapper>();
//			List<LeadsAddressLink> leadsAddressLink = leadsAddressLinkRepository.getAddressListByLeadsId(leadsId);

//			if (null != leadsAddressLink && !leadsAddressLink.isEmpty()) {
//
//				for (LeadsAddressLink leadsAddressLink2 : leadsAddressLink) {
//					System.out.println("addressId" + leadsAddressLink2.getAddressId());
//					AddressDetails addressDetails = addressRepository
//							.getAddressDetailsByAddressId(leadsAddressLink2.getAddressId());
//
//					AddressMapper addressMapper = new AddressMapper();
//					if (null != addressDetails) {
//
//						addressMapper.setAddress1(addressDetails.getAddressLine1());
//						addressMapper.setAddress2(addressDetails.getAddressLine2());
//						// addressMapper.setAddressType(addressDetails.getAddress_type());
//						addressMapper.setPostalCode(addressDetails.getPostalCode());
//
//						addressMapper.setAddress1(addressDetails.getAddressLine1());
//						addressMapper.setAddress2(addressDetails.getAddressLine2());
//						addressMapper.setAddressType(addressDetails.getAddressType());
//						addressMapper.setPostalCode(addressDetails.getPostalCode());
//
//						addressMapper.setStreet(addressDetails.getStreet());
//						addressMapper.setCity(addressDetails.getCity());
//						addressMapper.setTown(addressDetails.getTown());
//						addressMapper.setCountry(addressDetails.getCountry());
//						addressMapper.setLatitude(addressDetails.getLatitude());
//						addressMapper.setLongitude(addressDetails.getLongitude());
//						addressMapper.setState(addressDetails.getState());
//						addressMapper.setAddressId(addressDetails.getAddressId());
//						addressMapper.setHouseNo(addressDetails.getHouseNo());
//						Country country1 = countryRepository.getCountryDetailsByCountryNameAndOrgId(
//								addressDetails.getCountry(), leads.getOrganizationId());
//						if (null != country1) {
//							addressMapper.setCountry_alpha2_code(country1.getCountry_alpha2_code());
//							addressMapper.setCountry_alpha3_code(country1.getCountry_alpha3_code());
//						}
//						addressList.add(addressMapper);
//					}
//				}
//
//				System.out.println("addressList.......... " + addressList);
//			}
//			leadsViewMapper.setAddress(addressList);
		}
		return leadsViewMapper;
	}

	@Override
	public LeadsViewMapper updateLeads(String leadsId, LeadsMapper leadsMapper) throws IOException, TemplateException {
		LeadsViewMapper resultMapper = null;

		Leads leads = leadsRepository.getLeadsByIdAndLiveInd(leadsId);

		if (null != leads) {

			leads.setLiveInd(false);
			leadsRepository.save(leads);

			Leads newleads = new Leads();

			newleads.setLeadsId(leadsId);

			if (null != leadsMapper.getName()) {
				newleads.setName(leadsMapper.getName());
			} else {
				newleads.setName(leads.getName());
			}
			if (null != leadsMapper.getSalutation()) {
				newleads.setSalutation(leadsMapper.getSalutation());
			} else {
				newleads.setSalutation(leads.getSalutation());
			}
			if (null != leadsMapper.getFirstName()) {
				newleads.setFirstName(leadsMapper.getFirstName());
			} else {
				newleads.setFirstName(leads.getFirstName());
			}
			if (null != leadsMapper.getMiddleName()) {
				newleads.setMiddleName(leadsMapper.getMiddleName());
			} else {
				newleads.setMiddleName(leads.getMiddleName());
			}
			if (null != leadsMapper.getLastName()) {
				newleads.setLastName(leadsMapper.getLastName());
			} else {
				newleads.setLastName(leads.getLastName());
			}
			if (null != leadsMapper.getImageId()) {
				newleads.setImageId(leadsMapper.getImageId());
			} else {
				newleads.setImageId(leads.getImageId());
			}

			if (null != leadsMapper.getUrl()) {
				newleads.setUrl(leadsMapper.getUrl());
			} else {
				newleads.setUrl(leads.getUrl());
			}

			if (null != leadsMapper.getNotes()) {
				List<LeadsNotesLink> list = leadsNotesLinkRepository.getNotesIdByLeadsId(leadsId);
				if (null != list && !list.isEmpty()) {
					list.sort((m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
					Notes notes = notesRepository.findByNoteId(list.get(0).getNotesId());
					if (null != notes) {
						notes.setNotes(leadsMapper.getNotes());
						notesRepository.save(notes);
					}
				}
			}

			if (null != leadsMapper.getPhoneNumber()) {

				newleads.setPhoneNumber(leadsMapper.getPhoneNumber());
			} else {
				newleads.setPhoneNumber(leads.getPhoneNumber());

			}

			if (null != leadsMapper.getCountryDialCode()) {

				newleads.setCountryDialCode(leadsMapper.getCountryDialCode());
			} else {
				newleads.setCountryDialCode(leads.getCountryDialCode());

			}

			if (null != leadsMapper.getCountry()) {

				newleads.setCountry(leadsMapper.getCountry());
			} else {
				newleads.setCountry(leads.getCountry());

			}

			if (null != leadsMapper.getSectorId()) {

				newleads.setSector(leadsMapper.getSectorId());
			} else {
				newleads.setSector(leads.getSector());

			}

			if (null != leadsMapper.getZipcode()) {

				newleads.setZipcode(leadsMapper.getZipcode());
			} else {
				newleads.setZipcode(leads.getZipcode());

			}

			if (null != leadsMapper.getEmail()) {

				newleads.setEmail(leadsMapper.getEmail());
			} else {
				newleads.setEmail(leads.getEmail());

			}
			if (null != leadsMapper.getVatNo()) {

				newleads.setVatNo(leadsMapper.getVatNo());
			} else {
				newleads.setVatNo(leads.getVatNo());

			}
			newleads.setUserId(leads.getUserId());
			newleads.setOrganizationId(leads.getOrganizationId());

			if (null != leadsMapper.getDocumentId()) {

				newleads.setDocumentId(leadsMapper.getDocumentId());
			} else {
				newleads.setDocumentId(leads.getDocumentId());

			}

			if (null != leadsMapper.getCategory()) {

				newleads.setCategory(leadsMapper.getCategory());
			} else {
				newleads.setCategory(leads.getCategory());

			}
			if (null != leadsMapper.getAssignedTo()) {

				newleads.setAssignedTo(leadsMapper.getAssignedTo());
			} else {
				newleads.setAssignedTo(leads.getAssignedTo());

			}
			if (null != leadsMapper.getBusinessRegistration()) {

				newleads.setBusinessRegistration(leadsMapper.getBusinessRegistration());
			} else {
				newleads.setBusinessRegistration(leads.getBusinessRegistration());

			}
			if (null != leadsMapper.getCompanyName()) {

				newleads.setCompanyName(leadsMapper.getCompanyName());
			} else {
				newleads.setCompanyName(leads.getCompanyName());

			}
			if (null != leadsMapper.getSource()) {

				newleads.setSource(leadsMapper.getSource());
			} else {
				newleads.setSource(leads.getSource());

			}
			if (null != leadsMapper.getImageURL()) {

				newleads.setImageURL(leadsMapper.getImageURL());
			} else {
				newleads.setImageURL(leads.getImageURL());

			}

			if (null != leadsMapper.getAssignedTo() && !leads.getAssignedBy().equals(leadsMapper.getAssignedTo())) {

				newleads.setAssignedBy(leadsMapper.getUserId());
			} else {
				newleads.setAssignedBy(leads.getAssignedBy());

			}

			if (!StringUtils.isEmpty(leadsMapper.getBedrooms())) {
				newleads.setBedroom(leadsMapper.getBedrooms());
			} else {
				newleads.setBedroom(leads.getBedroom());
			}

			if (!StringUtils.isEmpty(leadsMapper.getPrice())) {
				newleads.setPrice(leadsMapper.getPrice());
			} else {
				newleads.setPrice(leads.getPrice());
			}

			if (!StringUtils.isEmpty(leadsMapper.getPropertyType())) {
				newleads.setPropertyType(leadsMapper.getPropertyType());
			} else {
				newleads.setPropertyType(leads.getPropertyType());
			}

			if (!StringUtils.isEmpty(leadsMapper.getLobId())) {
				newleads.setLob(leadsMapper.getLobId());
			} else {
				newleads.setLob(leads.getLob());
			}

			if (null != leadsMapper.getFirstName()) {
				String middleName = "";
				String lastName = "";
				String satutation1 = "";

				if (leadsMapper.getLastName() != null) {

					lastName = leadsMapper.getLastName();
				}
				if (leadsMapper.getMiddleName() != null && leadsMapper.getMiddleName().length() > 0) {

					middleName = leadsMapper.getMiddleName();
					if (leadsMapper.getSalutation() != null && leadsMapper.getSalutation().length() > 0) {
						satutation1 = leads.getSalutation();
						newleads.setName(
								satutation1 + " " + leadsMapper.getFirstName() + " " + middleName + " " + lastName);
					} else {

						newleads.setName(leadsMapper.getFirstName() + " " + middleName + " " + lastName);
					}
				} else {
					if (leads.getSalutation() != null && leads.getSalutation().length() > 0) {
						satutation1 = leads.getSalutation();
						newleads.setName(satutation1 + " " + leadsMapper.getFirstName() + " " + lastName);
					} else {

						newleads.setName(leadsMapper.getFirstName() + " " + lastName);
					}
					newleads.setName(leadsMapper.getFirstName() + " " + lastName);
				}
			} else {
				newleads.setName(leads.getName());
			}

			// customerMapper.setCreationDate(Utility.getISOFromDate(customer.getCreation_date()))

			newleads.setCreationDate(new Date());
			newleads.setLiveInd(true);
			newleads.setConvertInd(false);

			Leads updatedleads = leadsRepository.save(newleads);

			if (null != leadsMapper.getAddress()) {
				List<AddressMapper> addressList = leadsMapper.getAddress();

				for (AddressMapper addressMapper : addressList) {

					String addId = addressMapper.getAddressId();
					if (null != addId) {

						AddressDetails addressDetails = addressRepository.getAddressDetailsByAddressId(addId);
						if (null != addressDetails) {

							addressDetails.setLiveInd(false);
							addressRepository.save(addressDetails);
						}

						AddressDetails newAddressDetails = new AddressDetails();

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
					} else {
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

							LeadsAddressLink leadsAddressLink = new LeadsAddressLink();
							leadsAddressLink.setLeadsId(leadsId);
							leadsAddressLink.setAddressId(addressId);
							leadsAddressLink.setCreationDate(new Date());

							leadsAddressLinkRepository.save(leadsAddressLink);

						}
					}
				}
			}
			ContactMapper contactMapper = new ContactMapper();
			contactMapper.setSalutation(leadsMapper.getSalutation());
			contactMapper.setFirstName(leadsMapper.getFirstName());
			contactMapper.setMiddleName(leadsMapper.getMiddleName());
			contactMapper.setLastName(leadsMapper.getLastName());
			contactMapper.setEmailId(leadsMapper.getEmail());
			contactMapper.setPhoneNumber(leadsMapper.getPhoneNumber());
			contactMapper.setMobileNumber(leadsMapper.getPhoneNumber());
			contactMapper.setLeadsId(leadsMapper.getLeadsId());
			contactMapper.setCountryDialCode(leadsMapper.getCountryDialCode());
			contactMapper.setCountryDialCode1(leadsMapper.getCountryDialCode());
			contactMapper.setImageId(leadsMapper.getImageId());
			contactMapper.setAddress(leadsMapper.getAddress());
			contactMapper.setUserId(leadsMapper.getUserId());
			contactMapper.setOrganizationId(leadsMapper.getOrganizationId());
			contactMapper.setSectorId(leadsMapper.getSector());
			contactMapper.setBedrooms(leadsMapper.getBedrooms());
			contactMapper.setPrice(leadsMapper.getPrice());
			contactMapper.setPropertyType(leadsMapper.getPropertyType());

			LeadsContactLink leadsContactLinkList = leadsContactLinkRepository.getContactByLeadsId(leadsId);
			if (null != leadsContactLinkList) {

				ContactViewMapper mapper = contactService.updateContact(leadsContactLinkList.getContactId(),
						contactMapper);
				if (null != mapper) {
					leadsContactLinkList.setLeadsId(leadsId);
					leadsContactLinkList.setContactId(mapper.getContactId());
					leadsContactLinkList.setCreationDate(new Date());

					leadsContactLinkRepository.save(leadsContactLinkList);
				}
			}
//				//leads opportunity update
//				LeadsOpportunityLink leadsOpportunityLinkList = leadsOpportunityLinkRepository.getOpportunityByLeadsId(leadsId);
//				if (leadsOpportunityLinkList != null ) {
//					if (null != leadsMapper.getOpportunityName()) {
//
//						leadsOpportunityLinkList.setOpportunityName(leadsMapper.getOpportunityName());
//					} 
//					if (null != leadsMapper.getProposalValue()) {
//
//						leadsOpportunityLinkList.setProposalValue(leadsMapper.getProposalValue());
//					} 
//					
//					leadsOpportunityLinkRepository.save(leadsOpportunityLinkList);
//				}

			/* insert to Notification Table */
			Notificationparam param = new Notificationparam();
			EmployeeDetails emp = employeeRepository.getEmployeesByuserId(leadsMapper.getUserId());
			String name = employeeService.getEmployeeFullNameByObject(emp);
			param.setEmployeeDetails(emp);
			param.setAdminMsg("Lead " + "'" + leadsMapper.getName() + "' updated by " + name);
			param.setOwnMsg("Lead " + leadsMapper.getName() + " updated.");
			param.setNotificationType("Lead update");
			param.setProcessNmae("Leads");
			param.setType("update");
			param.setEmailSubject("Korero alert- Lead updated");
			param.setCompanyName("Korero");
			param.setUserId(leadsMapper.getUserId());

			if (!leadsMapper.getUserId().equals(leads.getAssignedTo())) {
				List<String> assignToUserIds = new ArrayList<>();
				assignToUserIds.add(leadsMapper.getAssignedTo());
				param.setAssignToUserIds(assignToUserIds);
				param.setAssignToMsg("Lead " + "'" + leadsMapper.getName() + "'" + " assigned to "
						+ employeeService.getEmployeeFullName(leadsMapper.getAssignedTo()) + " by " + name);
			}
			notificationService.createNotificationForDynamicUsers(param);

			resultMapper = getLeadsDetailsById(updatedleads.getLeadsId());
		}
		return resultMapper;
	}

	@Override
	public void deleteLeads(String leadsId) throws IOException, TemplateException {
		if (null != leadsId) {
			Leads leads = leadsRepository.getLeadsByIdAndLiveInd(leadsId);

			leads.setLiveInd(false);
			leadsRepository.save(leads);

			/* insert to Notification Table */
			Notificationparam param = new Notificationparam();
			EmployeeDetails emp = employeeRepository.getEmployeesByuserId(leads.getUserId());
			String name = employeeService.getEmployeeFullNameByObject(emp);
			param.setEmployeeDetails(emp);
			param.setAdminMsg("Lead " + "'" + leads.getName() + "' deleted by " + name);
			param.setOwnMsg("Lead " + leads.getName() + " deleted.");
			param.setNotificationType("Lead deleted");
			param.setProcessNmae("Leads");
			param.setType("deleted");
			param.setEmailSubject("Korero alert- Lead deleted");
			param.setCompanyName("Korero");
			param.setUserId(leads.getUserId());

			if (!leads.getUserId().equals(leads.getAssignedTo())) {
				List<String> assignToUserIds = new ArrayList<>();
				assignToUserIds.add(leads.getAssignedTo());
				param.setAssignToUserIds(assignToUserIds);
				param.setAssignToMsg("Lead " + "'" + leads.getName() + "' deleted by " + name);
			}
			notificationService.createNotificationForDynamicUsers(param);
		}

	}

	@Override
	public String convertLeadsById(String leadsId, String assignedToId) {

		if (null != leadsId) {
			Leads leads = leadsRepository.getLeadsByIdAndLiveInd(leadsId);
			if (null != leads) {
				leads.setConvertInd(true);
				leads.setConvertionDate(new Date());
				leadsRepository.save(leads);

				CustomerInfo info = new CustomerInfo();
				String customerId = customerInfoRepository.save(info).getId();

				if (null != customerId) {
					Customer customer = new Customer();
					customer.setCustomerId(customerId);
					customer.setName(leads.getCompanyName());
					customer.setUrl(leads.getUrl());
					customer.setNotes(leads.getNotes());
					customer.setCountryDialCode(leads.getCountryDialCode());
					customer.setPhoneNumber(leads.getPhoneNumber());
					customer.setEmail(leads.getEmail());
					customer.setUserId(leads.getUserId());
					customer.setOrganizationId(leads.getOrganizationId());
					customer.setGroup(leads.getGroup());
					customer.setVatNo(leads.getVatNo());
					customer.setCreationDate(new Date());
					customer.setLiveInd(true);
					customer.setCountry(leads.getCountry());
					customer.setZipcode(leads.getZipcode());
					customer.setZipcode(leads.getZipcode());
					customer.setCategory(leads.getCategory());
					customer.setImageURL(leads.getImageURL());
					customer.setAssignedTo(assignedToId);
					customer.setSector(leads.getSector());
					customer.setBusinessRegistration(leads.getBusinessRegistration());
					customer.setSource(leads.getSource());
					customerRepository.save(customer);
					// Customer address Link
					List<LeadsAddressLink> leadsAddressLinkList = leadsAddressLinkRepository
							.getAddressListByLeadsId(leadsId);
					if (leadsAddressLinkList != null && !leadsAddressLinkList.isEmpty()) {
						for (LeadsAddressLink leadsAddressLink : leadsAddressLinkList) {
							CustomerAddressLink customerAddressLink = new CustomerAddressLink();
							customerAddressLink.setAddressId(leadsAddressLink.getAddressId());
							customerAddressLink.setCustomerId(customerId);
							customerAddressLinkRepository.save(customerAddressLink);
						}
					}
					// Customer Document Link
					List<LeadsDocumentLink> leadsDocumentLinkList = leadsDocumentLinkRepository
							.getDocumentByLeadsId(leadsId);
					if (leadsDocumentLinkList != null && !leadsDocumentLinkList.isEmpty()) {
						for (LeadsDocumentLink leadsDocumentLink : leadsDocumentLinkList) {
							CustomerDocumentLink customerDocumentLink = new CustomerDocumentLink();
							customerDocumentLink.setDocumentId(leadsDocumentLink.getDocumentId());
							customerDocumentLink.setCustomerId(customerId);
							customerDocumentLinkRepository.save(customerDocumentLink);
						}
					}
					// Customer notes Link
//				List<LeadsNotesLink> leadsNotesLinkList = leadsNotesLinkRepository.getNotesIdByLeadsId(leadsId);
//				if (leadsNotesLinkList != null && !leadsNotesLinkList.isEmpty()) {
//				for (LeadsNotesLink leadsNotesLink : leadsNotesLinkList) {
//					CustomerNotesLink customerNotesLink = new CustomerNotesLink();
//					customerNotesLink.setNoteId(leadsNotesLink.getNoteId());
//					customerNotesLink.setCustomerId(customerId);
//					customerNotesLinkRepository.save(customerNotesLink);
//				}
//				}
					// Customer Initiative Link
					List<LeadsInnitiativeLink> leadsInnitiativeLinkList = leadsInnitiativeLinkRepository
							.getSkilListByInitiativeDetailsIdAndLiveInd(leadsId);
					if (leadsInnitiativeLinkList != null && !leadsInnitiativeLinkList.isEmpty()) {
						for (LeadsInnitiativeLink leadsInnitiativeLink : leadsInnitiativeLinkList) {
							CustomerInitiativeLink customerInitiativeLink = new CustomerInitiativeLink();
							customerInitiativeLink
									.setInitiativeDetailsId(leadsInnitiativeLink.getInitiativeDetailsId());
							customerInitiativeLink.setSkilId(leadsInnitiativeLink.getSkilId());
							customerInitiativeLink.setLiveInd(true);
							customerInitiativeLinkRepository.save(customerInitiativeLink);
						}
//				for (LeadsInnitiativeLink leadsInnitiativeLink : leadsInnitiativeLinkList) {
//					InitiativeDetails initiativeDetails = new InitiativeDetails();
//					initiativeDetails.setInitiativeDetailsId(leadsInnitiativeLink.getInitiativeDetailsId());
//					initiativeDetails.setCustomerId(customerId);
//					initiativeDetailsRepository.save(initiativeDetails);
//				}
					}

					// Customer contact Link
					List<LeadsContactLink> leadsContactLinkList = leadsContactLinkRepository
							.getContactIdByLeadsId(leadsId);
					if (leadsContactLinkList != null && !leadsContactLinkList.isEmpty()) {
						for (LeadsContactLink leadsContactLink : leadsContactLinkList) {

							ContactDetails contact = contactRepository
									.getContactDetailsById(leadsContactLink.getContactId());
							if (null != contact) {
								contact.setContactType("Customer");
								contact.setTag_with_company(customerId);
								contactRepository.save(contact);
							}

							CustomerContactLink customerContactLink = new CustomerContactLink();
							customerContactLink.setContactId(leadsContactLink.getContactId());
							customerContactLink.setCustomerId(customerId);
							customerContactLinkRepository.save(customerContactLink);

							// Contact notes Link
							List<LeadsNotesLink> leadsNotesLinkList1 = leadsNotesLinkRepository
									.getNotesIdByLeadsId(leadsId);
							if (leadsNotesLinkList1 != null && !leadsNotesLinkList1.isEmpty()) {
								for (LeadsNotesLink leadsNotesLink : leadsNotesLinkList1) {
									ContactNotesLink contactNotesLink = new ContactNotesLink();
									contactNotesLink.setContact_id(leadsContactLink.getContactId());
									contactNotesLink.setNotesId(leadsNotesLink.getNotesId());
									contactNotesLink.setCreation_date(new Date());

									contactNotesLinkRepository.save(contactNotesLink);
								}
							}
						}
					}

					// Customer Opportunity Link
					LeadsOpportunityLink leadsOpportunityLinkList = leadsOpportunityLinkRepository
							.getOpportunityByLeadsId(leadsId);
					if (leadsOpportunityLinkList != null) {
						LeadsOpportunityMapper leadsOpportunityMapper = getLeadsOpportunityById(
								leadsOpportunityLinkList.getLeadOppId());
						if (null != leadsOpportunityMapper) {
							OpportunityMapper opportunityMapper = addDataInOpportunityMapper(leadsOpportunityMapper);
							opportunityMapper.setCustomerId(customerId);
							if (null != opportunityMapper) {
								OpportunityViewMapper resultMapper = opportunityService
										.saveOpportunity(opportunityMapper);
								if (null != resultMapper.getOpportunityId()) {
									leadsOpportunityLinkList.setLiveInd(false);
									leadsOpportunityLinkRepository.save(leadsOpportunityLinkList);
								}
							}
						}
					}

					// Customer Call Link
					List<LeadsCallLink> leadsCallLinkList = leadsCallLinkRepository
							.getCallListByLeadsIdAndLiveInd(leadsId);
					if (leadsCallLinkList != null && !leadsCallLinkList.isEmpty()) {
						for (LeadsCallLink leadsCallLink : leadsCallLinkList) {
							CustomerCallLink customerCallLink = new CustomerCallLink();
							customerCallLink.setCallId(leadsCallLink.getCallId());
							customerCallLink.setCustomerId(customerId);
							customerCallLink.setCreationDate(new Date());
							customerCallLink.setLiveInd(true);
							customerCallLinkRepository.save(customerCallLink);
						}
					}

					// Customer Event Link
					List<LeadsEventLink> leadsEventLinkList = leadsEventLinkRepository.getByLeadsIdAndLiveInd(leadsId);
					if (leadsEventLinkList != null && !leadsEventLinkList.isEmpty()) {
						for (LeadsEventLink leadsEventLink : leadsEventLinkList) {
							CustomerEventLink customerEventLink = new CustomerEventLink();
							customerEventLink.setEventId(leadsEventLink.getEventId());
							customerEventLink.setCustomerId(customerId);
							customerEventLink.setCreationDate(new Date());
							customerEventLink.setLiveInd(true);
							customerEventLinkRepository.save(customerEventLink);
						}
					}

					// Customer Task Link
					List<LeadsTaskLink> leadsTaskLinkList = leadsTaskLinkRepository
							.getTaskListByLeadsIdAndLiveInd(leadsId);
					if (leadsEventLinkList != null && !leadsEventLinkList.isEmpty()) {
						for (LeadsTaskLink leadsTaskLink : leadsTaskLinkList) {
							CustomerTaskLink customerTaskLink = new CustomerTaskLink();
							customerTaskLink.setTaskId(leadsTaskLink.getTaskId());
							customerTaskLink.setCustomerId(customerId);
							customerTaskLink.setCreationDate(new Date());
							customerTaskLink.setLiveInd(true);
							customerTaskLinkRepository.save(customerTaskLink);
						}
					}

					return leads.getCompanyName() + " Successfully Converted to Customer";
				}
			}
			return "we are not found the leads";
		}
		return "Sorry this lead is not converted to customer";
	}

	@Override
	public List<LeadsViewMapper> getLeadsDetailsByUserId(String userId) {
		System.out.println("start.....1");
		List<Leads> leadsList = leadsRepository.getLeadsListByUserId(userId);
		System.out.println("leadsList>>>" + leadsList.toString());
		List<LeadsViewMapper> mapperList = new ArrayList<>();

		if (null != leadsList && !leadsList.isEmpty()) {
			mapperList = leadsList.stream().map(li -> getLeadsDetailsById(li.getLeadsId()))
					.collect(Collectors.toList());

		}
		return mapperList;
	}

	@Override
	public ContactViewMapper saveleadsContact(ContactMapper contactMapper) throws IOException, TemplateException {
		ContactViewMapper resultMapper = contactService.saveContact(contactMapper);
		System.out.println("contactID===========++++++++++++++++++++++++++++++++++===" + resultMapper.getContactId());
		if (null != resultMapper) {

			/* insert to leads-contact-link */
			LeadsContactLink leadsContactLink = new LeadsContactLink();
			leadsContactLink.setLeadsId(contactMapper.getLeadsId());
			leadsContactLink.setContactId(resultMapper.getContactId());
			leadsContactLink.setCreationDate(new Date());

			leadsContactLinkRepository.save(leadsContactLink);

		}
		return resultMapper;
	}

	@Override
	public List<ContactViewMapper> getContactListByLeadsId(String leadsId) {
		List<LeadsContactLink> leadsContactLinkList = leadsContactLinkRepository.getContactIdByLeadsId(leadsId);
		if (leadsContactLinkList != null && !leadsContactLinkList.isEmpty()) {
			return leadsContactLinkList.stream().map(leadsContactLink -> {
				ContactViewMapper contactMapper = contactService.getContactDetailsById(leadsContactLink.getContactId());

				/*
				 * ThirdParty thirdParty =
				 * thirdPartyRepository.findByOrgId(contactMapper.getOrganizationId()); if
				 * (thirdParty != null) {
				 * contactMapper.setThirdPartyAccessInd(thirdParty.isCustomerContactInd()); }
				 */
				return contactMapper;
			}).collect(Collectors.toList());

		}

		return null;
	}

	@Override
	public ContactViewMapper updateContactDetails(String contactId, ContactMapper contactMapper)
			throws IOException, TemplateException {

		ContactViewMapper resultMapper = contactService.updateContact(contactId, contactMapper);

		return resultMapper;
	}

//	@Override
//	public String saveLeadsNotes(NotesMapper notesMapper) {
//
//		String leadsNotesId = null;
//		if (null != notesMapper) {
//			DynamoNote notes = new DynamoNote();
//			notes.setNotes(notesMapper.getNotes());
//			notes.setCreationDate(new Date());
//			notes.setUserId(notesMapper.getEmployeeId());
//			notes.setLeadsId(notesMapper.getLeadsId());
//			notes.setLiveInd(true);
//			dynamoDB.save(notes);
//			
////			Notes note = notesRepository.save(notes);
//			leadsNotesId = "Note Created Successfully";
//
//		}
//		return leadsNotesId;
//
//	}

//@Override
//public List<NotesMapper> getNoteListByLeadsId(String leadsId) {
//	List<DynamoNote> notes = dynamoDB.findByLeadsId(leadsId);
//	if (leadsNotesLinkList != null && !leadsNotesLinkList.isEmpty()) {
//		return leadsNotesLinkList.stream().map(leadsNotesLink->{
//			NotesMapper notesMapper = getNotes(leadsNotesLink.getNotesId());
//			return notesMapper;
//		}).collect(Collectors.toList());
//	}
//	return null;
//}
//
//private NotesMapper getNotes(String id) {
//	DynamoNote notes = dynamoDB.findByNoteId(id);
//	NotesMapper notesMapper = new NotesMapper();
//	if(null!=notes) {
//	notesMapper.setNotesId(notes.getNoteId());
//	notesMapper.setNotes(notes.getNotes());
//	notesMapper.setCreationDate(Utility.getISOFromDate(notes.getCreationDate()));
//	notesMapper.setLiveInd(notes.isLiveInd());
//	if (!StringUtils.isEmpty(notes.getUserId())) {
//		EmployeeDetails employeeDetails = employeeRepository.getEmployeesByuserId(notes.getUserId());
//		String fullName = "";
//		String middleName = "";
//		String lastName = "";
//		if (null != employeeDetails.getMiddleName()) {
//
//			middleName = employeeDetails.getMiddleName();
//		}
//		if (null != employeeDetails.getLastName()) {
//			lastName = employeeDetails.getLastName();
//		}
//		fullName = employeeDetails.getFirstName() + " " + middleName + " " + lastName;
//		notesMapper.setOwnerName(fullName);
//	}
//	}
//	return notesMapper;
//
//}

	@Override
	public String saveLeadsNotes(NotesMapper notesMapper) {

		String leadsNotesId = null;
		if (null != notesMapper) {
			Notes notes = new Notes();
			notes.setNotes(notesMapper.getNotes());
			notes.setCreation_date(new Date());
			notes.setUserId(notesMapper.getEmployeeId());
			notes.setLiveInd(true);
			Notes note = notesRepository.save(notes);
			leadsNotesId = note.getNotes_id();

			/* insert to leads-notes-link */

			LeadsNotesLink leadsNotesLink = new LeadsNotesLink();
			leadsNotesLink.setLeadsId(notesMapper.getLeadsId());
			leadsNotesLink.setNotesId(leadsNotesId);
			leadsNotesLink.setCreationDate(new Date());

			leadsNotesLinkRepository.save(leadsNotesLink);

		}
		return leadsNotesId;

	}

	@Override
	public List<NotesMapper> getNoteListByLeadsId(String leadsId) {
		List<NotesMapper> result = new ArrayList<>();
		List<LeadsNotesLink> leadsNotesLinkList = leadsNotesLinkRepository.getNotesIdByLeadsId(leadsId);
		if (leadsNotesLinkList != null && !leadsNotesLinkList.isEmpty()) {
			result = leadsNotesLinkList.stream().map(leadsNotesLink -> {
				NotesMapper notesMapper = getNotes(leadsNotesLink.getNotesId());
				return notesMapper;
			}).collect(Collectors.toList());
		}
		return result;
	}

	private NotesMapper getNotes(String id) {
		Notes notes = notesRepository.findByNoteId(id);
		NotesMapper notesMapper = new NotesMapper();
		if (null != notes) {
			notesMapper.setNotesId(notes.getNotes_id());
			notesMapper.setNotes(notes.getNotes());
			notesMapper.setCreationDate(Utility.getISOFromDate(notes.getCreation_date()));
			notesMapper.setLiveInd(notes.isLiveInd());
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
		return notesMapper;

	}

//	@Override
//	public NotesMapper updateNoteDetails(String notesId, NotesMapper notesMapper) {
//
//		NotesMapper resultMapper = employeeService.updateNotes(notesMapper);
//
//		return resultMapper;
//	}

	@Override
	public List<DocumentMapper> getDocumentListByLeadsId(String leadsId) {
		List<DocumentMapper> resultList = new ArrayList<>();
		List<LeadsDocumentLink> leadsDocumentLinkList = leadsDocumentLinkRepository.getDocumentByLeadsId(leadsId);
		Set<String> documentIds = leadsDocumentLinkList.stream().map(LeadsDocumentLink::getDocumentId)
				.collect(Collectors.toSet());
		if (documentIds != null && !documentIds.isEmpty()) {
			documentIds.stream().map(documentId -> {

				DocumentDetails doc = documentDetailsRepository.getDocumentDetailsById(documentId);
				DocumentMapper documentMapper = new DocumentMapper();
				if (null != doc) {
					documentMapper.setDocumentId(doc.getDocument_id());
					documentMapper.setDocumentName(doc.getDocument_name());
					documentMapper.setDocumentTitle(doc.getDocument_title());
					// documentMapper.setDocumentContentType(doc.getDocument_type());
					documentMapper.setDocumentDescription(doc.getDoc_description());
					System.out.println("What2........" + doc.getDocument_type());

					DocumentType type = documentTypeRepository.getTypeDetails(doc.getDocument_type());
					if (type != null) {
						documentMapper.setDocumentContentType(type.getDocumentTypeName());

					} else {
						documentMapper.setDocumentContentType("");
					}

					EmployeeDetails details = employeeRepository.getEmployeeDetailsByEmployeeId(doc.getEmployee_id());
					System.out.println("What2........" + doc.getEmployee_id());
					documentMapper.setUploadedBy(details.getFirstName() + " " + details.getLastName());
					System.out.println("What2........" + details.getFirstName() + details.getLastName());
					documentMapper.setCreationDate(Utility.getISOFromDate(doc.getCreation_date()));

					resultList.add(documentMapper);
				}
				return documentMapper;
			}).collect(Collectors.toList());

		}

		return resultList;
	}

	@Override
	public void deleteDocumentsById(String documentId) {
		if (null != documentId) {
			DocumentDetails document = documentDetailsRepository.getDocumentDetailsById(documentId);
			if (null != document) {
				document.setLive_ind(false);
				documentDetailsRepository.save(document);
			}
		}
	}

	@Override
	public LeadsOpportunityMapper saveleadsOpportunity(LeadsOpportunityMapper leadsOpportunityMapper) {

		LeadsOpportunityLink leadsOpportunityLink = new LeadsOpportunityLink();
		leadsOpportunityLink.setLeadsId(leadsOpportunityMapper.getLeadsId());
		leadsOpportunityLink.setOpportunityName(leadsOpportunityMapper.getOpportunityName());
		leadsOpportunityLink.setProposalValue(leadsOpportunityMapper.getProposalAmount());
		leadsOpportunityLink.setCreationDate(new Date());
		leadsOpportunityLink.setLiveInd(true);
		leadsOpportunityLink.setUserId(leadsOpportunityMapper.getUserId());
		leadsOpportunityLink.setContactId(leadsOpportunityMapper.getContactId());
		leadsOpportunityLink.setAssignedTo(leadsOpportunityMapper.getAssignedTo());
		leadsOpportunityLink.setOrgId(leadsOpportunityMapper.getOrgId());
		try {
			leadsOpportunityLink.setStartDate(
					Utility.removeTime(Utility.getDateFromISOString(leadsOpportunityMapper.getStartDate())));
			leadsOpportunityLink
					.setEndDate(Utility.removeTime(Utility.getDateFromISOString(leadsOpportunityMapper.getEndDate())));
		} catch (Exception e) {
			e.printStackTrace();
		}

		leadsOpportunityLink.setCurrency(leadsOpportunityMapper.getCurrency());
		leadsOpportunityLink.setDescription(leadsOpportunityMapper.getDescription());
		leadsOpportunityLink.setOppStage(leadsOpportunityMapper.getOppStage());
		leadsOpportunityLink.setOppWorkflow(leadsOpportunityMapper.getOppWorkflow());
		LeadsOpportunityLink leadsOpportunityLink1 = leadsOpportunityLinkRepository.save(leadsOpportunityLink);

		LeadsOpportunityMapper resultMapper = getLeadsOpportunityById(leadsOpportunityLink1.getLeadOppId());

		return resultMapper;

	}

	@Override
	public List<LeadsOpportunityMapper> getOpportunityListByLeadsId(String leadsId) {
		List<LeadsOpportunityMapper> leadsOpportunityMapper = new ArrayList<>();
		List<LeadsOpportunityLink> leadsOpportunityLinkList = leadsOpportunityLinkRepository
				.getOpportunityIdByLeadsId(leadsId);
		if (leadsOpportunityLinkList != null && !leadsOpportunityLinkList.isEmpty()) {
			leadsOpportunityLinkList.stream().map(leadsOpportunityLink -> {
				LeadsOpportunityMapper opportunityMapper = getLeadsOpportunityById(leadsOpportunityLink.getLeadOppId());
				if (null != opportunityMapper.getLeadsId()) {
					leadsOpportunityMapper.add(opportunityMapper);
				}

				return leadsOpportunityMapper;
			}).collect(Collectors.toList());

		}

		return leadsOpportunityMapper;
	}

	@Override
	public LeadsOpportunityMapper updateLeadsOpportunity(String leadsOppId,
			LeadsOpportunityMapper opportunityViewMapper) {
		LeadsOpportunityMapper resultMapper = new LeadsOpportunityMapper();
		LeadsOpportunityLink newOpportunity = leadsOpportunityLinkRepository.getLeadsOpportunityDetailsById(leadsOppId);
		if (null != newOpportunity) {
			if (null != opportunityViewMapper.getOpportunityName()) {
				newOpportunity.setOpportunityName(opportunityViewMapper.getOpportunityName());
			}
			if (null != opportunityViewMapper.getProposalAmount()) {
				newOpportunity.setProposalValue(opportunityViewMapper.getProposalAmount());
			}
			if (null != opportunityViewMapper.getUserId()) {
				newOpportunity.setUserId(opportunityViewMapper.getUserId());
			}
			if (null != opportunityViewMapper.getContactId()) {
				newOpportunity.setContactId(opportunityViewMapper.getContactId());
			}
			if (null != opportunityViewMapper.getOrgId()) {
				newOpportunity.setOrgId(opportunityViewMapper.getOrgId());
			}
			if (null != opportunityViewMapper.getCurrency()) {
				newOpportunity.setCurrency(opportunityViewMapper.getCurrency());
			}
			if (null != opportunityViewMapper.getLeadsId()) {
				newOpportunity.setLeadsId(opportunityViewMapper.getLeadsId());
			}
			if (null != opportunityViewMapper.getDescription()) {
				newOpportunity.setDescription(opportunityViewMapper.getDescription());
			}
			if (null != opportunityViewMapper.getAssignedTo()) {
				newOpportunity.setAssignedTo(opportunityViewMapper.getAssignedTo());
			}
			if (null != opportunityViewMapper.getOppStage()) {
				newOpportunity.setOppStage(opportunityViewMapper.getOppStage());
			}
			if (null != opportunityViewMapper.getOppWorkflow()) {
				newOpportunity.setOppWorkflow(opportunityViewMapper.getOppWorkflow());
			}

			try {
				if (null != opportunityViewMapper.getStartDate()) {
					newOpportunity.setStartDate(
							Utility.removeTime(Utility.getDateFromISOString(opportunityViewMapper.getStartDate())));
				}
				if (null != opportunityViewMapper.getEndDate()) {
					newOpportunity.setEndDate(
							Utility.removeTime(Utility.getDateFromISOString(opportunityViewMapper.getEndDate())));
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			LeadsOpportunityLink newOpportunity1 = leadsOpportunityLinkRepository.save(newOpportunity);

			resultMapper = getLeadsOpportunityById(newOpportunity1.getLeadOppId());
		}
		return resultMapper;
	}

	public LeadsOpportunityMapper getLeadsOpportunityById(String leadsOppId) {
		LeadsOpportunityMapper leadsOpportunityMapper = new LeadsOpportunityMapper();
		LeadsOpportunityLink leadsOpportunityLink = leadsOpportunityLinkRepository
				.getLeadsOpportunityDetailsById(leadsOppId);
		if (null != leadsOpportunityLink) {
			leadsOpportunityMapper.setOpportunityName(leadsOpportunityLink.getOpportunityName());
			leadsOpportunityMapper.setProposalAmount(leadsOpportunityLink.getProposalValue());
			leadsOpportunityMapper.setStartDate(Utility.getISOFromDate(leadsOpportunityLink.getStartDate()));
			leadsOpportunityMapper.setEndDate(Utility.getISOFromDate(leadsOpportunityLink.getEndDate()));
			leadsOpportunityMapper.setCreationDate(Utility.getISOFromDate(leadsOpportunityLink.getCreationDate()));
			leadsOpportunityMapper.setUserId(leadsOpportunityLink.getUserId());
			leadsOpportunityMapper.setContactId(leadsOpportunityLink.getContactId());
			leadsOpportunityMapper.setAssignedTo(leadsOpportunityLink.getAssignedTo());
			leadsOpportunityMapper.setOrgId(leadsOpportunityLink.getOrgId());
			leadsOpportunityMapper.setCurrency(leadsOpportunityLink.getCurrency());
			leadsOpportunityMapper.setDescription(leadsOpportunityLink.getDescription());
			leadsOpportunityMapper.setLeadsId(leadsOpportunityLink.getLeadsId());

			Leads leads = leadsRepository.getLeadsByIdAndLiveInd(leadsOpportunityLink.getLeadsId());
			if (null != leads) {
				leadsOpportunityMapper.setLeadsName(leads.getCompanyName());
			}

			OpportunityWorkflowDetails workflowDetails = opportunityWorkflowDetailsRepository
					.getOpportunityWorkflowDetailsByOpportunityWorkflowDetailsId(leadsOpportunityLink.getOppWorkflow());
			if (null != workflowDetails) {
				leadsOpportunityMapper.setOppWorkflow(workflowDetails.getWorkflowName());
			}

			OpportunityStages oppStages = opportunityStagesRepository
					.getOpportunityStagesByOpportunityStagesId(leadsOpportunityLink.getOppStage());
			if (null != oppStages) {
				leadsOpportunityMapper.setOppStage(oppStages.getStageName());
			}
			if (leadsOpportunityLink.getContactId() != null
					&& leadsOpportunityLink.getContactId().trim().length() > 0) {
				ContactDetails contact = contactRepository.getContactDetailsById(leadsOpportunityLink.getContactId());
				if (null != contact) {
					String middleName = " ";
					String lastName = "";

					if (!StringUtils.isEmpty(contact.getLast_name())) {

						lastName = contact.getLast_name();
					}
					if (!StringUtils.isEmpty(contact.getMiddle_name())) {
						middleName = contact.getMiddle_name();

					}
					leadsOpportunityMapper.setContactId(contact.getContactId());
					leadsOpportunityMapper.setContactName(contact.getFirst_name() + " " + middleName + " " + lastName);

				}
			}

			leadsOpportunityMapper
					.setAssignedToName(employeeService.getEmployeeFullName(leadsOpportunityLink.getAssignedTo()));
			leadsOpportunityMapper.setOwner(employeeService.getEmployeeFullName(leadsOpportunityLink.getUserId()));

		}

		return leadsOpportunityMapper;

	}

	@Override
	public String deleteLeadsOppertunity(String leadOppId) {
		String message = null;
		LeadsOpportunityLink leadsOpportunityLink = leadsOpportunityLinkRepository
				.getLeadsOpportunityDetailsById(leadOppId);
		if (null != leadsOpportunityLink) {
			leadsOpportunityLink.setLiveInd(false);
			leadsOpportunityLinkRepository.save(leadsOpportunityLink);
			message = " Leads Oppertunity deleted successfuly ";
		}
		return message;
	}

	public OpportunityMapper addDataInOpportunityMapper(LeadsOpportunityMapper leadsOpportunityMapper) {
		OpportunityMapper opportunityMapper = new OpportunityMapper();

		opportunityMapper.setOpportunityName(leadsOpportunityMapper.getOpportunityName());
		opportunityMapper.setProposalAmount(leadsOpportunityMapper.getProposalAmount());
		opportunityMapper.setUserId(leadsOpportunityMapper.getUserId());
		opportunityMapper.setOrgId(leadsOpportunityMapper.getOrgId());
		opportunityMapper.setContactId(leadsOpportunityMapper.getContactId());
		opportunityMapper.setSalesUserIds(leadsOpportunityMapper.getAssignedTo());
		opportunityMapper.setCurrency(leadsOpportunityMapper.getCurrency());
		opportunityMapper.setDescription(leadsOpportunityMapper.getDescription());
		opportunityMapper.setOppStage(leadsOpportunityMapper.getOppStage());
		opportunityMapper.setOppWorkflow(leadsOpportunityMapper.getOppWorkflow());
		opportunityMapper.setStartDate(leadsOpportunityMapper.getStartDate());
		opportunityMapper.setEndDate(leadsOpportunityMapper.getEndDate());

		return opportunityMapper;
	}

	@Override
	public String saveLeadsInitiative(InitiativeDetailsMapper initiativeDetailsMapper) {
		String initiativeDetailsId = null;
		if (null != initiativeDetailsMapper) {
			InitiativeDetails initiativeDetails = new InitiativeDetails();
			initiativeDetails.setInitiativeName(initiativeDetailsMapper.getInitiativeName());
			initiativeDetails.setLeadsId(initiativeDetailsMapper.getLeadsId());
			initiativeDetails.setUserId(initiativeDetailsMapper.getUserId());
			initiativeDetails.setOrgId(initiativeDetailsMapper.getOrgId());
			initiativeDetailsRepository.save(initiativeDetails);
			initiativeDetailsId = initiativeDetails.getInitiativeDetailsId();

			/* insert to leads-initiative-link */

			List<String> skillList = initiativeDetailsMapper.getSkillList();
			if (null != skillList && !skillList.isEmpty()) {
				for (String skillId : skillList) {
					LeadsInnitiativeLink leadsInnitiativeLink = new LeadsInnitiativeLink();
					leadsInnitiativeLink.setSkilId(skillId);
					leadsInnitiativeLink.setInitiativeDetailsId(initiativeDetails.getInitiativeDetailsId());
					leadsInnitiativeLink.setUserId(initiativeDetailsMapper.getUserId());
					leadsInnitiativeLink.setOrgId(initiativeDetailsMapper.getOrgId());
					leadsInnitiativeLink.setLiveInd(true);
					leadsInnitiativeLinkRepository.save(leadsInnitiativeLink);
				}
			}
		}
		return initiativeDetailsId;
	}

	@Override
	public List<InitiativeDetailsMapper> getInitiativeListByLeadsId(String leadsId) {
		List<InitiativeDetailsMapper> resultList = new ArrayList<>();

		List<InitiativeDetails> customerInitiativeList = initiativeDetailsRepository
				.getInitiativeListByLeadsId(leadsId);

		if (null != customerInitiativeList && !customerInitiativeList.isEmpty()) {
			customerInitiativeList.stream().map(li -> {
				List<InitiativeSkillMapper> InitiativeSkillMapper1 = new ArrayList<>();
				InitiativeDetailsMapper mapper = new InitiativeDetailsMapper();
				mapper.setLeadsId(leadsId);
				mapper.setInitiativeName(li.getInitiativeName());
				mapper.setInitiativeDetailsId(li.getInitiativeDetailsId());
				mapper.setUserId(li.getUserId());
				mapper.setOrgId(li.getOrgId());
				InitiativeSkillMapper1 = getSkillLibery(li.getInitiativeDetailsId());
				mapper.setInitiativeSkillMapper(InitiativeSkillMapper1);
				resultList.add(mapper);
				return resultList;
			}).collect(Collectors.toList());
		}
		return resultList;

	}

	private List<InitiativeSkillMapper> getSkillLibery(String initiativeDetailsId) {

		List<InitiativeSkillMapper> InitiativeSkillMapper1 = new ArrayList<>();
		List<LeadsInnitiativeLink> initiativeLinkList = leadsInnitiativeLinkRepository
				.getSkilListByInitiativeDetailsIdAndLiveInd(initiativeDetailsId);

		if (null != initiativeLinkList && !initiativeLinkList.isEmpty()) {
			initiativeLinkList.stream().map(li -> {

				InitiativeSkillMapper initiativeSkillMapper = new InitiativeSkillMapper();

				DefinationDetails definationDetails = definationRepository.findByDefinationId(li.getSkilId());
				if (null != definationDetails) {
					initiativeSkillMapper.setDefinationId(definationDetails.getDefinationId());
					initiativeSkillMapper.setName(definationDetails.getName());
				}
				// skillLibery.add(definationDetails.getName());
				InitiativeSkillMapper1.add(initiativeSkillMapper);

				return InitiativeSkillMapper1;
			}).collect(Collectors.toList());
		}
		return InitiativeSkillMapper1;
	}

	@Override
	public InitiativeDetailsMapper updateInitiativeDetails(String initiativeDetailsId,
			InitiativeDetailsMapper initiativeDetailsMapper) {
		InitiativeDetailsMapper initiativeDetailMapper = null;

		InitiativeDetails initiativeDetails = initiativeDetailsRepository
				.getInitiativeDetailsByInitiativeDetailsId(initiativeDetailsId);
		if (null != initiativeDetails) {
			initiativeDetails.setInitiativeName(initiativeDetailsMapper.getInitiativeName());
			initiativeDetailsRepository.save(initiativeDetails);
		}

		List<LeadsInnitiativeLink> initiativeLinkList = leadsInnitiativeLinkRepository
				.getSkilListByInitiativeDetailsIdAndLiveInd(initiativeDetailsId);
		if (null != initiativeLinkList && !initiativeLinkList.isEmpty()) {
			for (LeadsInnitiativeLink leadsInnitiativeLink : initiativeLinkList) {
				leadsInnitiativeLink.setLiveInd(false);
			}
		}
		List<String> skillList = initiativeDetailsMapper.getSkillList();
		if (null != skillList && !skillList.isEmpty()) {
			for (String skillId : skillList) {
				LeadsInnitiativeLink leadsInnitiativeLink = new LeadsInnitiativeLink();
				leadsInnitiativeLink.setSkilId(skillId);
				leadsInnitiativeLink.setInitiativeDetailsId(initiativeDetailsId);
				leadsInnitiativeLink.setUserId(initiativeDetailsMapper.getUserId());
				leadsInnitiativeLink.setOrgId(initiativeDetailsMapper.getOrgId());
				leadsInnitiativeLink.setLiveInd(true);
				leadsInnitiativeLinkRepository.save(leadsInnitiativeLink);
			}
		}

		initiativeDetailMapper = getInitiativeDetailsByInitiativeDetailsId(initiativeDetailsId);

		return initiativeDetailMapper;
	}

	public InitiativeDetailsMapper getInitiativeDetailsByInitiativeDetailsId(String initiativeDetailsId) {
		InitiativeDetailsMapper initiativeDetailsMapper = new InitiativeDetailsMapper();
		InitiativeDetails initiativeDetails = initiativeDetailsRepository
				.getInitiativeDetailsByInitiativeDetailsId(initiativeDetailsId);
		if (null != initiativeDetails) {
			initiativeDetailsMapper.setCustomerId(initiativeDetails.getCustomerId());
			initiativeDetailsMapper.setInitiativeDetailsId(initiativeDetails.getInitiativeDetailsId());
			initiativeDetailsMapper.setInitiativeName(initiativeDetails.getInitiativeName());
			initiativeDetailsMapper.setOrgId(initiativeDetails.getOrgId());
			initiativeDetailsMapper.setUserId(initiativeDetails.getUserId());
		}

		List<InitiativeSkillMapper> InitiativeSkillMapper1 = new ArrayList<>();
		List<LeadsInnitiativeLink> initiativeLinkList = leadsInnitiativeLinkRepository
				.getSkilListByInitiativeDetailsIdAndLiveInd(initiativeDetailsId);

		if (null != initiativeLinkList && !initiativeLinkList.isEmpty()) {
			initiativeLinkList.stream().map(li -> {

				InitiativeSkillMapper initiativeSkillMapper = new InitiativeSkillMapper();

				DefinationDetails definationDetails = definationRepository.findByDefinationId(li.getSkilId());
				if (null != definationDetails) {
					initiativeSkillMapper.setDefinationId(definationDetails.getDefinationId());
					initiativeSkillMapper.setName(definationDetails.getName());
				}
				// skillLibery.add(definationDetails.getName());
				InitiativeSkillMapper1.add(initiativeSkillMapper);

				return InitiativeSkillMapper1;
			}).collect(Collectors.toList());
		}
		initiativeDetailsMapper.setInitiativeSkillMapper(InitiativeSkillMapper1);

		return initiativeDetailsMapper;
	}

	@Override
	public String saveLeadsSkillSet(LeadsSkillLinkMapper leadsSkillLinkMapper) {

		DefinationDetails definationDetails1 = definationRepository
				.getBySkillNameAndLiveInd(leadsSkillLinkMapper.getSkillName());

		if (null != definationDetails1) {

			LeadsSkillLink leadsSkillLink1 = new LeadsSkillLink();
			leadsSkillLink1.setSkillName(definationDetails1.getDefinationId());
			leadsSkillLink1.setLeadsId(leadsSkillLinkMapper.getLeadsId());
			leadsSkillLink1.setCreationDate(new Date());
			leadsSkillLink1.setEditInd(true);
			leadsSkillLink1.setUserId(leadsSkillLinkMapper.getOrgId());
			leadsSkillLink1.setOrgId(leadsSkillLinkMapper.getOrgId());
			leadsSkillLinkRepository.save(leadsSkillLink1);

		} else {

			DefinationInfo definationInfo = new DefinationInfo();

			definationInfo.setCreation_date(new Date());
			String id = definationInfoRepository.save(definationInfo).getDefination_info_id();

			DefinationDetails newDefinationDetails = new DefinationDetails();
			newDefinationDetails.setDefinationId(id);
			newDefinationDetails.setName(leadsSkillLinkMapper.getSkillName());
			newDefinationDetails.setOrg_id(leadsSkillLinkMapper.getOrgId());
			newDefinationDetails.setUser_id(leadsSkillLinkMapper.getUserId());
			newDefinationDetails.setCreation_date(new Date());
			newDefinationDetails.setLiveInd(true);
			newDefinationDetails.setEditInd(true);
			definationRepository.save(newDefinationDetails);

			LeadsSkillLink leadsSkillLink1 = new LeadsSkillLink();
			leadsSkillLink1.setSkillName(id);
			leadsSkillLink1.setLeadsId(leadsSkillLinkMapper.getLeadsId());
			leadsSkillLink1.setCreationDate(new Date());
			leadsSkillLink1.setEditInd(true);
			leadsSkillLink1.setUserId(leadsSkillLinkMapper.getOrgId());
			leadsSkillLink1.setOrgId(leadsSkillLinkMapper.getOrgId());
			leadsSkillLinkRepository.save(leadsSkillLink1);

		}

		return leadsSkillLinkMapper.getSkillName();

	}

	@Override
	public List<LeadsSkillLinkMapper> getSkillSetByLeadsId(String leadsId) {

		List<LeadsSkillLink> skillList = leadsSkillLinkRepository.getByLeadsId(leadsId);
		if (null != skillList && !skillList.isEmpty()) {
			return skillList.stream().map(leadsSkillLink -> {

				LeadsSkillLinkMapper leadsSkillLinkMapper = new LeadsSkillLinkMapper();

				DefinationDetails definationDetails1 = definationRepository
						.findByDefinationId(leadsSkillLink.getSkillName());
				if (null != definationDetails1) {
					leadsSkillLinkMapper.setSkillName(definationDetails1.getName());

				}
				leadsSkillLinkMapper.setLeadsId(leadsSkillLink.getLeadsId());
				leadsSkillLinkMapper.setCreationDate(Utility.getISOFromDate(leadsSkillLink.getCreationDate()));
				leadsSkillLinkMapper.setLeadsSkillLinkId(leadsSkillLink.getLeadsSkillLinkId());
				leadsSkillLinkMapper.setEditInd(leadsSkillLink.isEditInd());
				leadsSkillLinkMapper.setUserId(leadsSkillLink.getOrgId());
				leadsSkillLinkMapper.setOrgId(leadsSkillLink.getOrgId());
				return leadsSkillLinkMapper;

			}).collect(Collectors.toList());
		}
		return null;
	}

	@Override
	public LeadsSkillLinkMapper deleteSkillsSet(String leadsSkillLinkId) {

		LeadsSkillLinkMapper resultMapper = new LeadsSkillLinkMapper();
		if (null != leadsSkillLinkId) {
			LeadsSkillLink leadsSkillLink = leadsSkillLinkRepository.findByLeadsSkillLinkId(leadsSkillLinkId);
			if (null != leadsSkillLink) {
				leadsSkillLinkRepository.delete(leadsSkillLink);
			}
		}
		return resultMapper;
	}

	@Override
	public List<LeadsViewMapper> getLeadsDetailsByName(String name) {
		List<Leads> detailsList = leadsRepository.findByLiveInd(true);
		List<Leads> filterList = detailsList.parallelStream().filter(detail -> {
			return detail.getCompanyName() != null && Utility.containsIgnoreCase(detail.getCompanyName(), name.trim());
		}).collect(Collectors.toList());
		List<LeadsViewMapper> mapperList = new ArrayList<LeadsViewMapper>();
		if (null != filterList && !filterList.isEmpty()) {

			mapperList = filterList.parallelStream().map(li -> getLeadsDetailsById(li.getLeadsId()))
					.collect(Collectors.toList());

		}

		return mapperList;
	}

	@Override
	public List<LeadsViewMapper> getLeadsDetailsByContactName(String name) {
		List<Leads> detailsList = leadsRepository.findByLiveIndAndConvertIndAndJunkInd(true, false, false);
		List<Leads> filterList = detailsList.parallelStream().filter(detail -> {
			return detail.getName() != null && Utility.containsIgnoreCase(detail.getName(), name.trim());
		}).collect(Collectors.toList());
		List<LeadsViewMapper> mapperList = new ArrayList<LeadsViewMapper>();
		if (null != filterList && !filterList.isEmpty()) {

			mapperList = filterList.parallelStream().map(li -> getLeadsDetailsById(li.getLeadsId()))
					.collect(Collectors.toList());

		}

		return mapperList;
	}

	@Override
	public List<LeadsViewMapper> getLeadsDetailsBySector(String name) {
		List<LeadsViewMapper> list = new ArrayList<>();
		SectorDetails sectorDetails = sectorDetailsRepository.getSectorDetailsBySectorName(name);
		if (null != sectorDetails) {
			List<Leads> sectorlist = leadsRepository.getSectorLinkBySector(sectorDetails.getSectorId());
			if (null != sectorlist && !sectorlist.isEmpty()) {
				return sectorlist.stream().map(leads -> {
					LeadsViewMapper leadsViewMapper = getLeadsDetailsById(leads.getLeadsId());
					if (null != leadsViewMapper) {
						return leadsViewMapper;
					}
					return null;
				}).collect(Collectors.toList());

			}

			return list;
		}
		return list;
	}

	@Override
	public List<LeadsViewMapper> getLeadsDetailsByOwnerName(String name) {
//		List<LeadsViewMapper> list = new ArrayList<>();
//		EmployeeDetails employeeDetails = employeeRepository.findByFullName(name);
//		if (null != employeeDetails) {
//			List<Leads> leadslist = leadsRepository.getLeadsListByOwnerId(employeeDetails.getUserId());
//			if (null != leadslist && !leadslist.isEmpty()) {
//				return leadslist.stream().map(leads -> {
//					LeadsViewMapper leadsViewMapper = getLeadsDetailsById(leads.getLeadsId());
//					if (null != leadsViewMapper) {
//						return leadsViewMapper;
//					}
//					return null;
//				}).collect(Collectors.toList());
//
//			}
//			return list;
//		}
//		return list;
		List<LeadsViewMapper> list = new ArrayList<>();
		List<EmployeeDetails> detailsList = employeeRepository.findByLiveInd(true);
		List<EmployeeDetails> filterList = detailsList.parallelStream().filter(detail -> {
			return detail.getFullName() != null && Utility.containsIgnoreCase(detail.getFullName(), name.trim());
		}).collect(Collectors.toList());

		if (null != filterList && !filterList.isEmpty()) {
			for (EmployeeDetails employeeDetails : filterList) {
				List<Leads> leadslist = leadsRepository.getLeadsListByOwnerId(employeeDetails.getUserId());
				if (null != leadslist && !leadslist.isEmpty()) {
					return leadslist.stream().map(leads -> {
						LeadsViewMapper leadsViewMapper = getLeadsDetailsById(leads.getLeadsId());
						if (null != leadsViewMapper) {
							return leadsViewMapper;
						}
						return null;
					}).collect(Collectors.toList());

				}
				return list;
			}

		}
		return list;
	}

	@Override
	public ByteArrayInputStream exportLeadsListToExcel(List<LeadsViewMapper> leadsList) {
		XSSFWorkbook workbook = new XSSFWorkbook();

		// Create a blank sheet
		XSSFSheet sheet = workbook.createSheet("leads");

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
		for (int i = 0; i < leads_headings.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(leads_headings[i]);
			cell.setCellStyle(headerCellStyle);
		}

		int rowNum = 1;
		if (null != leadsList && !leadsList.isEmpty()) {
			for (LeadsViewMapper leads : leadsList) {
				Row row = sheet.createRow(rowNum++);

				row.createCell(0).setCellValue(leads.getLeadsId());
				row.createCell(1).setCellValue(leads.getCompanyName());
				row.createCell(2).setCellValue(leads.getFirstName());
				row.createCell(3).setCellValue(leads.getMiddleName());
				row.createCell(4).setCellValue(leads.getLastName());
				row.createCell(5).setCellValue(leads.getUrl());
				row.createCell(6).setCellValue(leads.getPhoneNumber());
				row.createCell(7).setCellValue(leads.getNotes());

			}
		}
		// Resize all columns to fit the content size
		for (int i = 0; i < leads_headings.length; i++) {
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
	public List<LeadsViewMapper> getLeadsDetailsByOrgId(String orgId) {
		List<Leads> leadsList = leadsRepository.getLeadsListByOrgId(orgId);
		List<LeadsViewMapper> mapperList = new ArrayList<>();

		if (null != leadsList && !leadsList.isEmpty()) {
			mapperList = leadsList.stream().map(li -> getLeadsDetailsById(li.getLeadsId()))
					.collect(Collectors.toList());

		}
		return mapperList;
	}

	@Override
	public String updateLeadsType(String leadsId, String type) {
		String msg = null;
		Leads leads = leadsRepository.getLeadsByIdAndLiveInd(leadsId);
		if (null != leads) {
			leads.setType(type);
			leads.setTypeUpdationDate(new Date());
			leadsRepository.save(leads);
			msg = "Lead Changed Successfuly";
		} else {
			msg = "Something Went Wrong";
		}
		return msg;

	}

	@Override
	public HashMap getCountListByuserId(String userId) {
		List<Leads> leadsList = leadsRepository.getLeadListByUserId(userId);
		HashMap map = new HashMap();
		map.put("LeadsDetails", leadsList.size());

		return map;
	}

	@Scheduled(cron = "0 10 00 * * *")
	public void convertLeadsToJunk() {
		System.out.println("ss schedular started");
		List<String> list = new ArrayList<>();
		list.add("Hot");
		list.add("Cold");
		list.add("Warm");
		list.add("Not Defined");
		LeadsCatagory leadsCatagory = leadsCatagoryRepository.findByLiveInd(true);
		if (null != leadsCatagory) {
			for (String type : list) {
				List<Leads> leadsList = leadsRepository.getLeadsListByType(type);
				if (null != leadsList && !leadsList.isEmpty()) {
					for (Leads lead : leadsList) {
						Date todayDay = new Date();
						Date end_date = null;
						Date start_date = null;
						try {
							end_date = Utility.getDateAfterEndDate(Utility.removeTime(todayDay));
							start_date = Utility.removeTime(lead.getTypeUpdationDate());
						} catch (Exception e) {
							e.printStackTrace();
						}
						int count = 0;
						Date temp_date = start_date;
						while (end_date.after(temp_date)) {
							count++;
							temp_date = new Date(temp_date.getTime() + 24 * 60 * 60 * 1000);
						}
						if (count == 0) {
							count++;
						}

						if (lead.getType().equalsIgnoreCase("Hot")) {
							if (count > leadsCatagory.getHot()) {
								lead.setJunkInd(true);
								lead.setJunkedDate(new Date());
								leadsRepository.save(lead);
							}
						}
						if (lead.getType().equalsIgnoreCase("Cold")) {
							if (count > leadsCatagory.getHot()) {
								lead.setJunkInd(true);
								lead.setJunkedDate(new Date());
								leadsRepository.save(lead);
							}
						}
						if (lead.getType().equalsIgnoreCase("Warm")) {
							if (count > leadsCatagory.getHot()) {
								lead.setJunkInd(true);
								lead.setJunkedDate(new Date());
								leadsRepository.save(lead);
							}
						}
						if (lead.getType().equalsIgnoreCase("Not Defined")) {
							if (count > leadsCatagory.getHot()) {
								lead.setJunkInd(true);
								lead.setJunkedDate(new Date());
								leadsRepository.save(lead);
							}
						}

					}
				}
			}

		}
	}

	@Override
	public List<LeadsViewMapper> getQualifiedLeadsDetailsByUserId(String userId, String startDate, String endDate) {
		List<LeadsViewMapper> mapperList = new ArrayList<>();
		Date end_date = null;
		Date start_date = null;
		try {
			end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<Leads> leadsList = leadsRepository.getQualifiedLeadsListByUserIdAndDateRange(userId, start_date, end_date);
		System.out.println("leadsList>>>" + leadsList.size());

		if (null != leadsList && !leadsList.isEmpty()) {
			leadsList.stream().map(li -> {
				LeadsViewMapper mapper = getLeadsDetailsById(li.getLeadsId());
				if (null != mapper.getLeadsId()) {
					mapperList.add(mapper);
				}

				return mapperList;
			}).collect(Collectors.toList());

		}

		return mapperList;
	}

	@Override
	public HashMap getQualifiedLeadsListCountByUserId(String userId, String startDate, String endDate) {
		Date end_date = null;
		Date start_date = null;
		try {
			end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<Leads> leadsList = leadsRepository.getQualifiedLeadsListByUserIdAndDateRange(userId, start_date, end_date);
		System.out.println("leadsList>>>" + leadsList.size());

		HashMap map = new HashMap();
		map.put("qualifiedLeadsList", leadsList.size());

		return map;
	}

	@Override
	public String ReinstateLeadToJunk(String userId, TransferMapper transferMapper) {
		String message = null;
		if (null != transferMapper.getLeadsIds() && !transferMapper.getLeadsIds().isEmpty()) {
			List<String> leadsIds = transferMapper.getLeadsIds();
			for (String leadsId : leadsIds) {
				Leads leads = leadsRepository.getLeadsByIdAndLiveInd(leadsId);
				if (null != leads) {
					leads.setUserId(userId);
					leads.setJunkInd(false);
					leads.setTypeUpdationDate(new Date());
					leadsRepository.save(leads);
				}
			}
			message = "Successfuly junk leads converted to leads And Transfered To "
					+ employeeService.getEmployeeFullName(userId);
		}
		return message;
	}

	@Override
	public Map getQualifiedLeadsListCountByOrgId(String orgId, String startDate, String endDate) {
		Date end_date = null;
		Date start_date = null;
		try {
			end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("leadsList>>>" + orgId);
		System.out.println("leadsList>>>" + start_date);
		System.out.println("leadsList>>>" + end_date);

		List<Leads> leadsList = leadsRepository.getQualifiedLeadsListByOrgIdAndDateRange(orgId, start_date, end_date);
		System.out.println("leadsList>>>" + leadsList.size());

		HashMap map = new HashMap();
		map.put("qualifiedLeadsList", leadsList.size());

		return map;
	}

	@Override
	public List<LeadsViewMapper> getQualifiedLeadsDetailsByOrgId(String orgId, String startDate, String endDate) {
		List<LeadsViewMapper> mapperList = new ArrayList<>();
		Date end_date = null;
		Date start_date = null;
		try {
			end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<Leads> leadsList = leadsRepository.getQualifiedLeadsListByOrgIdAndDateRange(orgId, start_date, end_date);
		System.out.println("leadsList>>>" + leadsList.size());

		if (null != leadsList && !leadsList.isEmpty()) {
			leadsList.stream().map(li -> {
				LeadsViewMapper mapper = getLeadsDetailsById(li.getLeadsId());
				if (null != mapper.getLeadsId()) {
					mapperList.add(mapper);
				}

				return mapperList;
			}).collect(Collectors.toList());

		}

		return mapperList;
	}

	@Override
	public Map getCreatededLeadsListCountByUserId(String userId, String startDate, String endDate) {
		Date end_date = null;
		Date start_date = null;
		try {
			end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<Leads> leadsList = leadsRepository.getCreatedLeadsListByUserIdAndDateRange(userId, start_date, end_date);
		System.out.println("leadsList>>>" + leadsList.size());

		HashMap map = new HashMap();
		map.put("createdLeadsList", leadsList.size());

		return map;
	}

	@Override
	public List<LeadsViewMapper> getCreatededLeadsDetailsByUserId(String userId, String startDate, String endDate) {
		List<LeadsViewMapper> mapperList = new ArrayList<>();
		Date end_date = null;
		Date start_date = null;
		try {
			end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<Leads> leadsList = leadsRepository.getCreatedLeadsListByUserIdAndDateRange(userId, start_date, end_date);
		System.out.println("leadsList>>>" + leadsList.size());

		if (null != leadsList && !leadsList.isEmpty()) {
			leadsList.stream().map(li -> {
				LeadsViewMapper mapper = getLeadsDetailsById(li.getLeadsId());
				if (null != mapper.getLeadsId()) {
					mapperList.add(mapper);
				}

				return mapperList;
			}).collect(Collectors.toList());

		}

		return mapperList;
	}

	@Override
	public Map getCreatededLeadsListCountByOrgId(String orgId, String startDate, String endDate) {
		Date end_date = null;
		Date start_date = null;
		try {
			end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("leadsList>>>" + orgId);
		System.out.println("leadsList>>>" + start_date);
		System.out.println("leadsList>>>" + end_date);

		List<Leads> leadsList = leadsRepository.getCreatedLeadsListByOrgIdAndDateRange(orgId, start_date, end_date);
		System.out.println("leadsList>>>" + leadsList.size());
		HashMap map = new HashMap();
		map.put("createdLeadsList", leadsList.size());

		return map;
	}

	@Override
	public List<LeadsViewMapper> getCreatededLeadsDetailsByOrgId(String orgId, String startDate, String endDate) {
		List<LeadsViewMapper> mapperList = new ArrayList<>();
		Date end_date = null;
		Date start_date = null;
		try {
			end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<Leads> leadsList = leadsRepository.getCreatedLeadsListByOrgIdAndDateRange(orgId, start_date, end_date);
		System.out.println("leadsList>>>" + leadsList.size());

		if (null != leadsList && !leadsList.isEmpty()) {
			leadsList.stream().map(li -> {
				LeadsViewMapper mapper = getLeadsDetailsById(li.getLeadsId());
				if (null != mapper.getLeadsId()) {
					mapperList.add(mapper);
				}

				return mapperList;
			}).collect(Collectors.toList());

		}

		return mapperList;
	}

	@Override
	public Map getJunkedLeadsCountByUserId(String userId) {
		List<Leads> leadsList = leadsRepository.getJunkLeadsListByUserId(userId);
		System.out.println("leadsList>>>" + leadsList.size());
		HashMap map = new HashMap();
		map.put("junkedList", leadsList.size());

		return map;
	}

	@Override
	public List<LeadsViewMapper> getJunkedLeadsListByUserId(String userId) {
		List<LeadsViewMapper> mapperList = new ArrayList<>();
		List<Leads> leadsList = leadsRepository.getJunkLeadsListByUserId(userId);
		System.out.println("leadsList>>>" + leadsList.size());
		if (null != leadsList && !leadsList.isEmpty()) {
			leadsList.stream().map(li -> {
				LeadsViewMapper mapper = getLeadsDetailsById(li.getLeadsId());
				if (null != mapper.getLeadsId()) {
					mapperList.add(mapper);
				}

				return mapperList;
			}).collect(Collectors.toList());

		}

		return mapperList;
	}

	@Override
	public Map getJunkedLeadsCountByOrgId(String orgId) {
		List<Leads> leadsList = leadsRepository.getJunkLeadsListByOrgId(orgId);
		System.out.println("leadsList>>>" + leadsList.size());
		HashMap map = new HashMap();
		map.put("junkedList", leadsList.size());

		return map;
	}

	@Override
	public List<LeadsViewMapper> getJunkedLeadsListByOrgId(String orgId) {
		List<LeadsViewMapper> mapperList = new ArrayList<>();
		List<Leads> leadsList = leadsRepository.getJunkLeadsListByOrgId(orgId);
		System.out.println("leadsList>>>" + leadsList.size());

		if (null != leadsList && !leadsList.isEmpty()) {
			leadsList.stream().map(li -> {
				LeadsViewMapper mapper = getLeadsDetailsById(li.getLeadsId());
				if (null != mapper.getLeadsId()) {
					mapperList.add(mapper);
				}

				return mapperList;
			}).collect(Collectors.toList());

		}

		return mapperList;
	}

	@Override
	public Map getJunkedLeadsListCountByUserId(String userId, String startDate, String endDate) {
		Date end_date = null;
		Date start_date = null;
		try {
			end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<Leads> leadsList = leadsRepository.getJunkedLeadsListByUserIdAndDateRange(userId, start_date, end_date);
		System.out.println("leadsList>>>" + leadsList.size());

		HashMap map = new HashMap();
		map.put("junkedLeadsList", leadsList.size());

		return map;
	}

	@Override
	public List<LeadsViewMapper> getJunkedLeadsDetailsByUserId(String userId, String startDate, String endDate) {
		List<LeadsViewMapper> mapperList = new ArrayList<>();
		Date end_date = null;
		Date start_date = null;
		try {
			end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<Leads> leadsList = leadsRepository.getJunkedLeadsListByUserIdAndDateRange(userId, start_date, end_date);
		System.out.println("leadsList>>>" + leadsList.size());

		if (null != leadsList && !leadsList.isEmpty()) {
			leadsList.stream().map(li -> {
				LeadsViewMapper mapper = getLeadsDetailsById(li.getLeadsId());
				if (null != mapper.getLeadsId()) {
					mapperList.add(mapper);
				}

				return mapperList;
			}).collect(Collectors.toList());

		}

		return mapperList;
	}

	@Override
	public Map getJunkedLeadsListCountByOrgId(String orgId, String startDate, String endDate) {
		Date end_date = null;
		Date start_date = null;
		try {
			end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("leadsList>>>" + orgId);
		System.out.println("leadsList>>>" + start_date);
		System.out.println("leadsList>>>" + end_date);

		List<Leads> leadsList = leadsRepository.getJunkedLeadsListByOrgIdAndDateRange(orgId, start_date, end_date);
		System.out.println("leadsList>>>" + leadsList.size());

		HashMap map = new HashMap();
		map.put("junkedLeadsList", leadsList.size());

		return map;
	}

	@Override
	public List<LeadsViewMapper> getJunkedLeadsDetailsByOrgId(String orgId, String startDate, String endDate) {
		List<LeadsViewMapper> mapperList = new ArrayList<>();
		Date end_date = null;
		Date start_date = null;
		try {
			end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<Leads> leadsList = leadsRepository.getJunkedLeadsListByOrgIdAndDateRange(orgId, start_date, end_date);
		System.out.println("leadsList>>>" + leadsList.size());

		if (null != leadsList && !leadsList.isEmpty()) {
			leadsList.stream().map(li -> {
				LeadsViewMapper mapper = getLeadsDetailsById(li.getLeadsId());
				if (null != mapper.getLeadsId()) {
					mapperList.add(mapper);
				}

				return mapperList;
			}).collect(Collectors.toList());

		}

		return mapperList;
	}

	@Override
	public List<LeadsViewMapper> getHotListByUserId(String userId) {
		List<LeadsViewMapper> resulList = new ArrayList<>();
		List<Leads> list = leadsRepository.getLeadsByUserIdAndType(userId, "Hot");
		if (null != list && !list.isEmpty()) {
			list.stream().map(leads -> {
				LeadsViewMapper leadsMapper = getLeadsDetailsById(leads.getLeadsId());
				if (null != leadsMapper) {
					resulList.add(leadsMapper);
				}
				return resulList;
			}).collect(Collectors.toList());

		}
		return resulList;
	}

	@Override
	public List<LeadsViewMapper> getWarmListByUserId(String userId) {
		List<LeadsViewMapper> resulList = new ArrayList<>();
		List<Leads> list = leadsRepository.getLeadsByUserIdAndType(userId, "Warm");
		if (null != list && !list.isEmpty()) {
			list.stream().map(leads -> {
				LeadsViewMapper leadsMapper = getLeadsDetailsById(leads.getLeadsId());
				if (null != leadsMapper) {
					resulList.add(leadsMapper);
				}
				return resulList;
			}).collect(Collectors.toList());

		}
		return resulList;
	}

	@Override
	public Map getHotListCountByUserId(String userId) {
		List<Leads> leadsList = leadsRepository.getLeadsByUserIdAndType(userId, "Hot");

		HashMap map = new HashMap();
		map.put("hotList", leadsList.size());

		return map;
	}

	@Override
	public Map getWormListCountByUserId(String userId) {
		List<Leads> leadsList = leadsRepository.getLeadsByUserIdAndType(userId, "Warm");

		HashMap map = new HashMap();
		map.put("warmList", leadsList.size());

		return map;
	}

	@Override
	public List<String> transferLeadsOneUserToAnother(String userid, TransferMapper transferMapper) {
		List<String> leadsList = transferMapper.getLeadsIds();
		System.out.println("candiList::::::::::::::::::::::::::::::::::::::::::::::::::::" + leadsList);
		if (null != leadsList && !leadsList.isEmpty()) {
			for (String leadsId : leadsList) {
				System.out.println("the candiate id is : " + leadsId);
				System.out.println("the user id is : " + userid);
				Leads lead = leadsRepository.getLeadsByIdAndLiveInd(leadsId);
				if (null != lead) {
					System.out
							.println("candidate::::::::::::::::::::::::::::::::::::::::::::::::::::" + lead.toString());
					System.out.println("the user id is :=== " + userid);

					lead.setUserId(userid);
					lead.setJunkInd(false);
					lead.setTypeUpdationDate(new Date());
					leadsRepository.save(lead);
				}
			}

		}
		return leadsList;
	}

	@Override
	public Map getColdListCountByUserId(String userId) {
		List<Leads> leadsList = leadsRepository.getLeadsByUserIdAndType(userId, "Cold");

		HashMap map = new HashMap();
		map.put("coldList", leadsList.size());

		return map;
	}

	@Override
	public List<LeadsViewMapper> getcoldListByUserId(String userId) {
		List<LeadsViewMapper> resulList = new ArrayList<>();
		List<Leads> list = leadsRepository.getLeadsByUserIdAndType(userId, "Cold");
		if (null != list && !list.isEmpty()) {
			list.stream().map(leads -> {
				LeadsViewMapper leadsMapper = getLeadsDetailsById(leads.getLeadsId());
				if (null != leadsMapper) {
					resulList.add(leadsMapper);
				}
				return resulList;
			}).collect(Collectors.toList());

		}
		return resulList;
	}

	@Override
	public Map getLeadsListCountByUserIdAndtype(String userId) {
		HashMap map = new HashMap();

		List<Leads> leadsList = leadsRepository.getLeadsByUserIdAndType(userId, "Cold");
		map.put("coldList", leadsList.size());

		List<Leads> leadsList1 = leadsRepository.getLeadsByUserIdAndType(userId, "Warm");
		map.put("warmList", leadsList1.size());

		List<Leads> leadsList2 = leadsRepository.getLeadsByUserIdAndType(userId, "Warm");
		map.put("warmList", leadsList2.size());

		return map;
	}

	@Override
	public List<Integer> getLeadsListCountByUserIdAndtypeAndDateRange(String userId, String startDate, String endDate) {
		Date end_date = null;
		Date start_date = null;
		try {
			end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("leadsList>>>" + userId);
		System.out.println("leadsList>>>" + start_date);
		System.out.println("leadsList>>>" + end_date);

//		HashMap map = new HashMap();
		List<Integer> map = new ArrayList<Integer>();

		List<Leads> leadsList2 = leadsRepository.getLeadsByUserIdAndTypeWithDateRange(userId, "Hot", start_date,
				end_date);
		map.add(leadsList2.size());

		List<Leads> leadsList1 = leadsRepository.getLeadsByUserIdAndTypeWithDateRange(userId, "Warm", start_date,
				end_date);
		map.add(leadsList1.size());

		List<Leads> leadsList = leadsRepository.getLeadsByUserIdAndTypeWithDateRange(userId, "Cold", start_date,
				end_date);
		map.add(leadsList.size());

		return map;
	}

	@Override
	public Map getLeadsListCountByOrgIdAndtype(String orgId) {
		HashMap map = new HashMap();

		List<Leads> leadsList = leadsRepository.getLeadsByOrgIdAndType(orgId, "Cold");
		map.put("coldList", leadsList.size());

		List<Leads> leadsList1 = leadsRepository.getLeadsByOrgIdAndType(orgId, "Warm");
		map.put("warmList", leadsList1.size());

		List<Leads> leadsList2 = leadsRepository.getLeadsByOrgIdAndType(orgId, "Hot");
		map.put("warmList", leadsList2.size());

		return map;
	}

	@Override
	public Map getLeadsListCountByOrgIdAndtypeAndDateRange(String orgId, String startDate, String endDate) {
		Date end_date = null;
		Date start_date = null;
		try {
			end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("leadsList>>>" + orgId);
		System.out.println("leadsList>>>" + start_date);
		System.out.println("leadsList>>>" + end_date);

		HashMap map = new HashMap();

		List<Leads> leadsList = leadsRepository.getLeadsByOrgIdAndTypeWithDateRange(orgId, "Cold", start_date,
				end_date);
		map.put("coldList", leadsList.size());

		List<Leads> leadsList1 = leadsRepository.getLeadsByOrgIdAndTypeWithDateRange(orgId, "Warm", start_date,
				end_date);
		map.put("warmList", leadsList1.size());

		List<Leads> leadsList2 = leadsRepository.getLeadsByOrgIdAndTypeWithDateRange(orgId, "Hot", start_date,
				end_date);
		map.put("warmList", leadsList2.size());

		return map;
	}

	@Override
	public List<Map<String, Double>> getAddedLeadsListCountByUserIdWithDateWise(String userId, String startDate,
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
			List<Leads> leadsList1 = leadsRepository.getCreatedLeadsListByUserIdAndDateRange(userId, startDate2,
					endDate2);
			double number = leadsList1.size();
//			int c=i;
//			map1.put("name",++c);
			String date = Utility.getISOFromDate(startDate2);
			System.out.println("date======" + date);
			map1.put("date", date);
			map1.put("number", number);
			map.add(map1);
			startDate1 = Utility.getPlusDate(startDate1, 1);
		}
		return map;
	}

	@Override
	public List<Map<String, Double>> getLeadsCountSourceWiseByUserId(String userId, String orgId) {
		List<Map<String, Double>> map = new ArrayList<>();

		List<Source> sourceList = sourceRepository.findByOrgIdAndLiveInd(orgId, true);
		if (null != sourceList && !sourceList.isEmpty()) {
			for (Source source : sourceList) {
				Map map1 = new HashMap<>();
				List<Leads> leads = leadsRepository.getLeadsListByUserIdAndSource(userId, source.getSourceId());

				map1.put("source", source.getName());
				map1.put("number", leads.size());
				map.add(map1);
			}
		}

		return map;
	}

	@Override
	public List<Map<String, List<LeadsViewMapper>>> getLeadsListSourceWiseByUserId(String userId, String orgId) {
		List<Map<String, List<LeadsViewMapper>>> map = new ArrayList<>();

		List<Source> sourceList = sourceRepository.findByOrgIdAndLiveInd(orgId, true);
		if (null != sourceList && !sourceList.isEmpty()) {
			for (Source source : sourceList) {
				Map map1 = new HashMap<>();
				List<LeadsViewMapper> resulList = new ArrayList<>();
				List<Leads> leads = leadsRepository.getLeadsListByUserIdAndSource(userId, source.getSourceId());
				if (null != leads && !leads.isEmpty()) {
					leads.stream().map(lead -> {
						LeadsViewMapper leadsViewMapper = getLeadsDetailsById(lead.getLeadsId());
						if (null != leadsViewMapper) {
							resulList.add(leadsViewMapper);
						}
						return resulList;
					}).collect(Collectors.toList());
				}

				map1.put("source", source.getName());
				map1.put("leadsList", resulList);
				map.add(map1);
			}
		}

		return map;
	}

	@Override
	public List<LeadsViewMapper> getHotListByUserIdAndDateRange(String userId, String startDate, String endDate) {
		Date end_date = null;
		Date start_date = null;
		try {
			end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<LeadsViewMapper> resulList = new ArrayList<>();
		List<Leads> list = leadsRepository.getLeadsByUserIdAndTypeWithDateRange(userId, "Hot", start_date, end_date);
		if (null != list && !list.isEmpty()) {
			list.stream().map(leads -> {
				LeadsViewMapper leadsMapper = getLeadsDetailsById(leads.getLeadsId());
				if (null != leadsMapper) {
					resulList.add(leadsMapper);
				}
				return resulList;
			}).collect(Collectors.toList());

		}
		return resulList;
	}

	@Override
	public List<LeadsViewMapper> getWarmListByUserIdAndDateRange(String userId, String startDate, String endDate) {
		Date end_date = null;
		Date start_date = null;
		try {
			end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<LeadsViewMapper> resulList = new ArrayList<>();
		List<Leads> list = leadsRepository.getLeadsByUserIdAndTypeWithDateRange(userId, "Warm", start_date, end_date);
		if (null != list && !list.isEmpty()) {
			list.stream().map(leads -> {
				LeadsViewMapper leadsMapper = getLeadsDetailsById(leads.getLeadsId());
				if (null != leadsMapper) {
					resulList.add(leadsMapper);
				}
				return resulList;
			}).collect(Collectors.toList());

		}
		return resulList;
	}

	@Override
	public List<LeadsViewMapper> getcoldListByUserIdAndDateRange(String userId, String startDate, String endDate) {
		Date end_date = null;
		Date start_date = null;
		try {
			end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<LeadsViewMapper> resulList = new ArrayList<>();
		List<Leads> list = leadsRepository.getLeadsByUserIdAndTypeWithDateRange(userId, "Cold", start_date, end_date);
		if (null != list && !list.isEmpty()) {
			list.stream().map(leads -> {
				LeadsViewMapper leadsMapper = getLeadsDetailsById(leads.getLeadsId());
				if (null != leadsMapper) {
					resulList.add(leadsMapper);
				}
				return resulList;
			}).collect(Collectors.toList());

		}
		return resulList;
	}

	@Override
	public List<ActivityMapper> getActivityListByLeadsId(String leadsId) {
		List<ActivityMapper> resultList = new ArrayList<>();

		List<LeadsCallLink> callLink = leadsCallLinkRepo.getCallListByLeadsIdAndLiveInd(leadsId);
		if (null != callLink && !callLink.isEmpty()) {
			callLink.stream().map(call -> {
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

		List<LeadsEventLink> eventLink = leadsEventRepo.getByLeadsIdAndLiveInd(leadsId);
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

		List<LeadsTaskLink> taskLink = leadsTaskRepo.getTaskListByLeadsIdAndLiveInd(leadsId);
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
	public void deleteLeadsNotesById(String notesId) {
		LeadsNotesLink notesList = leadsNotesLinkRepository.findByNotesId(notesId);
		if (null != notesList) {

			Notes notes = notesRepository.findByNoteId(notesId);
			if (null != notes) {
				notes.setLiveInd(false);
				notesRepository.save(notes);
			}
		}
	}

	@Override
	public List<LeadsViewMapper> getLeadsDetailsByUserIdPagging(String userId, int pageNo, int pageSize,
			String filter) {
		System.out.println("start.....1");
		Pageable paging = null;
		if (filter.equalsIgnoreCase("creationdate")) {
			paging = PageRequest.of(pageNo, pageSize, Sort.by("creationDate").descending());

		} else if (filter.equalsIgnoreCase("ascending")) {
			paging = PageRequest.of(pageNo, pageSize, Sort.by("name"));

		} else if (filter.equalsIgnoreCase("descending")) {
			paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "name"));
		}
		Page<Leads> leadsList = leadsRepository.getInvestorsListByUserIdPagging(userId, paging);
//		System.out.println("leadsList>>>" + leadsList.toString());
		List<LeadsViewMapper> mapperList = new ArrayList<>();

		if (null != leadsList && !leadsList.isEmpty()) {
			mapperList = leadsList.stream().map(li -> {
				LeadsViewMapper mapper = getLeadsDetailsById(li.getLeadsId());
				mapper.setPageCount(leadsList.getTotalPages());
				mapper.setDataCount(leadsList.getSize());
				mapper.setListCount(leadsList.getTotalElements());
				return mapper;
			}).collect(Collectors.toList());

		}
		return mapperList;
	}

	@Override
	public List<LeadsViewMapper> getLeadsListByOrgId(String orgId, int pageNo, int pageSize, String filter,
			String type) {
		Pageable paging = null;
		if (filter.equalsIgnoreCase("creationdate")) {
			paging = PageRequest.of(pageNo, pageSize, Sort.by("creationDate").descending());

		} else if (filter.equalsIgnoreCase("ascending")) {
			paging = PageRequest.of(pageNo, pageSize, Sort.by("name"));

		} else if (filter.equalsIgnoreCase("descending")) {
			paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "name"));
		}
		Page<Leads> leadsList = leadsRepository.getLeadsListByOrgIdPagging(orgId, paging, type);
		List<LeadsViewMapper> mapperList = new ArrayList<>();

		if (null != leadsList && !leadsList.isEmpty()) {
			mapperList = leadsList.stream().map(li -> {
				LeadsViewMapper mapper = getLeadsDetailsById(li.getLeadsId());
				mapper.setPageCount(leadsList.getTotalPages());
				mapper.setDataCount(leadsList.getSize());
				mapper.setListCount(leadsList.getTotalElements());
				return mapper;
			}).collect(Collectors.toList());
		}
		return mapperList;
	}

	@Override
	public HashMap getLeadsListCountByOrgId(String orgId) {
		List<Leads> list = leadsRepository.getLeadsListByOrgId(orgId);
		HashMap map = new HashMap();
		map.put("leadsDetails", list.size());

		return map;
	}

	@Override
	public Set<LeadsViewMapper> getTeamLeadsDetailsByUserId(String userId, int pageNo, int pageSize, String filter) {
		Pageable paging = null;
		Team team = teamRepository.findByTeamLeadAndLiveInd(userId, true);

		List<String> userIds = teamMemberLinkRepo.findByTeamId(team.getTeamId()).stream()
				.map(TeamMemberLink::getTeamMemberId).collect(Collectors.toList());

		if (filter.equalsIgnoreCase("creationdate")) {
			paging = PageRequest.of(pageNo, pageSize, Sort.by("creationDate").descending());

		} else if (filter.equalsIgnoreCase("ascending")) {
			paging = PageRequest.of(pageNo, pageSize, Sort.by("name"));

		} else if (filter.equalsIgnoreCase("descending")) {
			paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "name"));
		}
		Page<Leads> leadsPage = leadsRepository.getTeamInvestorsListByUserIdsPaginated(userIds, paging);

		Set<LeadsViewMapper> mapperSet = new HashSet<>();

		if (leadsPage != null && !leadsPage.isEmpty()) {
			mapperSet = leadsPage.getContent().stream().map(li -> {
				LeadsViewMapper mapper = getLeadsDetailsById(li.getLeadsId());
				mapper.setPageCount(leadsPage.getTotalPages());
				mapper.setDataCount(leadsPage.getSize());
				mapper.setListCount(leadsPage.getTotalElements());
				return mapper;
			}).collect(Collectors.toSet());
		}
		return mapperSet;
	}

	@Override
	public String saveLeadsTroughWebsite(LeadsMapper leadsMapper) throws IOException, TemplateException {
		String leadsInfoId = null;
		String userId = null;
		LeadsInfo leadsInfo = new LeadsInfo();

		leadsInfo.setCreationDate(new Date());

		leadsInfoId = leadsInfoRepository.save(leadsInfo).getId();

		Leads leads = new Leads();

		ContactMapper contactMapper = new ContactMapper();
		contactMapper.setSalutation(leadsMapper.getSalutation());
		contactMapper.setFirstName(leadsMapper.getFirstName());
		contactMapper.setMiddleName(leadsMapper.getMiddleName());
		contactMapper.setLastName(leadsMapper.getLastName());
		contactMapper.setEmailId(leadsMapper.getEmail());
		contactMapper.setPhoneNumber(leadsMapper.getPhoneNumber());
		contactMapper.setMobileNumber(leadsMapper.getPhoneNumber());
		contactMapper.setLeadsId(leadsInfoId);
		contactMapper.setCountryDialCode(leadsMapper.getCountryDialCode());
		contactMapper.setCountryDialCode1(leadsMapper.getCountryDialCode());
		contactMapper.setImageId(leadsMapper.getImageId());
		contactMapper.setAddress(leadsMapper.getAddress());
		// contactMapper.setUserId(leadsMapper.getUserId());
		contactMapper.setOrganizationId(leadsMapper.getOrganizationId());

		DistributionAutomation distributionAutomation = distributionAutomationRepository
				.getByOrgIdAndType(leadsMapper.getOrganizationId(), "lead");
		if (null != distributionAutomation) {
			if (distributionAutomation.isSingleMultiInd()) {

				List<DistributionAutomationAssignedTo> userList = distributionAutomationAssignedToRepository
						.getByDistributionAutomationIdAndLiveInd(distributionAutomation.getDistributionAutomationId(),
								true);

				if (userList.isEmpty()) {
					return null;
				}

				RoundRobbinConfig config = roundRobbinConfigRepo.findByName("lead");

				int lastAssignedUserId = (config != null) ? config.getValue() : 0;
				int numUsers = userList.size();

				DistributionAutomationAssignedTo assignedUser = userList.get(lastAssignedUserId % numUsers);
				userId = assignedUser.getAsignedTO();
				contactMapper.setUserId(userId);

				lastAssignedUserId = (lastAssignedUserId + 1) % numUsers;

				if (config != null) {
					config.setValue(lastAssignedUserId);
				} else {
					config = new RoundRobbinConfig("lead", lastAssignedUserId);
				}

				roundRobbinConfigRepo.save(config);
			} else {
				List<DistributionAutomationAssignedTo> list = distributionAutomationAssignedToRepository
						.getByDistributionAutomationIdAndLiveInd(distributionAutomation.getDistributionAutomationId(),
								true);
				if (null != list && !list.isEmpty()) {
					if (list.size() > 0 && list.size() < 2) {
						contactMapper.setUserId(list.get(0).getAsignedTO());
						userId = list.get(0).getAsignedTO();
					}
				}
			}
		}

		ContactViewMapper resultMapperr = contactService.saveContact(contactMapper);
		System.out.println("contactID===========++++++++++++++++++++++++++++++++++===" + resultMapperr.getContactId());
		if (null != resultMapperr.getContactId()) {

			/* insert to leads-contact-link */
			LeadsContactLink leadsContactLink = new LeadsContactLink();
			leadsContactLink.setLeadsId(contactMapper.getLeadsId());
			leadsContactLink.setContactId(resultMapperr.getContactId());
			leadsContactLink.setCreationDate(new Date());

			leadsContactLinkRepository.save(leadsContactLink);

		}
		leadsMapper.setUserId(userId);
		leadsMapper.setSourceUserId(userId);
		setPropertyOnInputForWebSite(leadsMapper, leads, leadsInfoId);

		leadsRepository.save(leads);
		return "Thank you for Registering ..";
	}

	private void setPropertyOnInputForWebSite(LeadsMapper leadsMapper, Leads leads, String leadsInfoId) {

		leads.setLeadsId(leadsInfoId);
		leads.setName(leadsMapper.getName());
		leads.setSalutation(leadsMapper.getSalutation());
		leads.setFirstName(leadsMapper.getFirstName());
		leads.setMiddleName(leadsMapper.getMiddleName());
		leads.setLastName(leadsMapper.getLastName());
		leads.setImageId(leadsMapper.getImageId());
		leads.setUrl(leadsMapper.getUrl());
		leads.setNotes(leadsMapper.getNotes());
		leads.setCountryDialCode(leadsMapper.getCountryDialCode());
		leads.setPhoneNumber(leadsMapper.getPhoneNumber());
		leads.setEmail(leadsMapper.getEmail());
		leads.setUserId(leadsMapper.getUserId());
		leads.setOrganizationId(leadsMapper.getOrganizationId());
		leads.setGroup(leadsMapper.getGroup());
		leads.setVatNo(leadsMapper.getVatNo());
		leads.setCreationDate(new Date());
		leads.setLiveInd(true);
		leads.setDocumentId(leadsMapper.getDocumentId());
		leads.setSector(leadsMapper.getSectorId());
		System.out.println("get sector in frontend............" + leadsMapper.getSectorId());
		leads.setCountry(leadsMapper.getCountry());
		leads.setZipcode(leadsMapper.getZipcode());
		leads.setZipcode(leadsMapper.getZipcode());
		leads.setCategory(leadsMapper.getCategory());
		leads.setImageURL(leadsMapper.getImageURL());
		leads.setAssignedTo(leadsMapper.getAssignedTo());
		leads.setBusinessRegistration(leadsMapper.getBusinessRegistration());
		leads.setConvertInd(false);
		leads.setCompanyName(leadsMapper.getName());
		leads.setType("Hot");
		leads.setTypeUpdationDate(new Date());
		leads.setJunkInd(false);
		leads.setCreationType("Website");
		leads.setSource(leadsMapper.getSource());
		leads.setSourceUserId(leadsMapper.getSourceUserId());

		String middleName2 = "";
		String lastName2 = "";
		String satutation1 = "";

		if (!StringUtils.isEmpty(leadsMapper.getLastName())) {

			lastName2 = leadsMapper.getLastName();
		}
		if (leadsMapper.getMiddleName() != null && leadsMapper.getMiddleName().length() > 0) {

			middleName2 = leadsMapper.getMiddleName();
			if (leadsMapper.getSalutation() != null && leadsMapper.getSalutation().length() > 0) {
				satutation1 = leadsMapper.getSalutation();
				leads.setName(satutation1 + " " + leadsMapper.getFirstName() + " " + middleName2 + " " + lastName2);
			} else {

				leads.setName(leadsMapper.getFirstName() + " " + middleName2 + " " + lastName2);
			}
		} else {

			if (leadsMapper.getSalutation() != null && leadsMapper.getSalutation().length() > 0) {
				satutation1 = leadsMapper.getSalutation();
				leads.setName(satutation1 + " " + leadsMapper.getFirstName() + " " + lastName2);
			} else {

				leads.setName(leadsMapper.getFirstName() + " " + lastName2);
			}
		}

		if (leadsMapper.getAddress().size() > 0) {
			for (AddressMapper addressMapper : leadsMapper.getAddress()) {
				/* insert to address info & address deatils & leadsaddressLink */

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

					LeadsAddressLink leadsAddressLink = new LeadsAddressLink();
					leadsAddressLink.setLeadsId(leadsInfoId);
					leadsAddressLink.setAddressId(addressId);
					leadsAddressLink.setCreationDate(new Date());

					leadsAddressLinkRepository.save(leadsAddressLink);

				}
			}
		}

	}

	@Override
	public HashMap getTeamLeadsCountByUserId(String userId) {
		HashMap map = new HashMap();
		Team team = teamRepository.findByTeamLeadAndLiveInd(userId, true);

		List<String> userIds = teamMemberLinkRepo.findByTeamId(team.getTeamId()).stream()
				.map(TeamMemberLink::getTeamMemberId).collect(Collectors.toList());
		List<Leads> investorPage = leadsRepository.getTeamLeadsListByUserIds(userIds);
		map.put("LeadsTeam", investorPage.size());

		return map;
	}

	@Override
	public HashMap getActivityRecordByLeadsId(String leadsId) {
		int count = 0;
		List<LeadsCallLink> callLink = leadsCallLinkRepo.getCallListByLeadsIdAndLiveInd(leadsId);
		if (null != callLink && !callLink.isEmpty()) {
			count = callLink.size();
		}

		List<LeadsEventLink> eventLink = leadsEventRepo.getByLeadsIdAndLiveInd(leadsId);
		if (null != eventLink && !eventLink.isEmpty()) {
			count += eventLink.size();
		}

		List<LeadsTaskLink> taskLink = leadsTaskRepo.getTaskListByLeadsIdAndLiveInd(leadsId);
		if (null != taskLink && !taskLink.isEmpty()) {
			count += taskLink.size();
		}

		HashMap map = new HashMap();
		map.put("count", count);
		return map;
	}

	@Override
	public boolean getLeadsByUrl(String url) {
		List<Leads> leads = leadsRepository.getByUrl(url);
		if (null != leads && !leads.isEmpty()) {

			return true;
		}

		return false;
	}

	@Override
	public boolean getLeadsByEmail(String email) {
		List<Leads> leads = leadsRepository.getByEmail(email);
		if (null != leads && !leads.isEmpty()) {

			return true;
		}

		return false;
	}

	@Override
	public List<LeadsReportMapper> getAllLeadsByOrgIdForReport(String orgId, String startDate, String endDate) {
		Date endDate1 = null;
		Date startDate1 = null;
		try {
			endDate1 = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			startDate1 = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<LeadsReportMapper> resultMapper = new ArrayList<>();
		List<Leads> leadsList = leadsRepository.getLeadsListByOrgIdWithDateRange(orgId, startDate1, endDate1);
		if (null != leadsList && !leadsList.isEmpty()) {
			resultMapper = leadsList.stream().map(leads -> {
				LeadsReportMapper mapper = getLeadsDetailsByIdForReport(leads.getLeadsId());
				return mapper;
			}).collect(Collectors.toList());
		}
		return resultMapper;
	}

	private LeadsReportMapper getLeadsDetailsByIdForReport(String leadsId) {
		Leads leads = leadsRepository.getLeadsByIdAndLiveInd(leadsId);
		System.out.println("Leads object is..." + leads);

		LeadsReportMapper leadsViewMapper = new LeadsReportMapper();

		if (null != leads) {

			if (leads.getSector() != null && leads.getSector().trim().length() > 0) {
				SectorDetails sector = sectorDetailsRepository.getSectorDetailsBySectorId(leads.getSector());
				System.out.println("get sectordetails by id returns........." + leads.getSector());
				System.out.println("sector object is......." + sector);
				if (sector != null) {
					leadsViewMapper.setSector(sector.getSectorName());
					leadsViewMapper.setSectorId(leads.getSector());
				} else {
					leadsViewMapper.setSector("");
					leadsViewMapper.setSectorId("");
				}
			}
			leadsViewMapper.setLeadsId(leadsId);
			// leadsViewMapper.setName(leads.getName());
			leadsViewMapper.setSalutation(leads.getSalutation());
			leadsViewMapper.setFirstName(leads.getFirstName());
			leadsViewMapper.setMiddleName(leads.getMiddleName());
			leadsViewMapper.setLastName(leads.getLastName());
			leadsViewMapper.setImageId(leads.getImageId());
			leadsViewMapper.setUrl(leads.getUrl());
			leadsViewMapper.setNotes(leads.getNotes());
			leadsViewMapper.setEmail(leads.getEmail());
			leadsViewMapper.setGroup(leads.getGroup());
			leadsViewMapper.setVatNo(leads.getVatNo());
			leadsViewMapper.setPhoneNumber(leads.getPhoneNumber());
			leadsViewMapper.setCountryDialCode(leads.getCountryDialCode());
			leadsViewMapper.setUserId(leads.getUserId());
			leadsViewMapper.setOrganizationId(leads.getOrganizationId());
			leadsViewMapper.setCreationDate(Utility.getISOFromDate(leads.getCreationDate()));
			leadsViewMapper.setImageURL(leads.getImageURL());

			leadsViewMapper.setCountry(leads.getCountry());
			// customerMapper.setSector(sector.getSectorName());
			leadsViewMapper.setZipcode(leads.getZipcode());

			leadsViewMapper.setDocumentId(leads.getDocumentId());
			leadsViewMapper.setCategory(leads.getCategory());
			leadsViewMapper.setBusinessRegistration(leads.getBusinessRegistration());
			leadsViewMapper.setConvertInd(leads.isConvertInd());
			leadsViewMapper.setConvertionDate(Utility.getISOFromDate(leads.getConvertionDate()));
			leadsViewMapper.setCompanyName(leads.getCompanyName());
			leadsViewMapper.setType(leads.getType());
			leadsViewMapper.setTypeUpdationDate(Utility.getISOFromDate(leads.getTypeUpdationDate()));
			leadsViewMapper.setJunkInd(leads.isJunkInd());
			leadsViewMapper.setSourceUserID(leads.getSourceUserId());
			leadsViewMapper.setSourceUserName(employeeService.getEmployeeFullName(leads.getSourceUserId()));

			Source source = sourceRepository.findBySourceId(leads.getSource());
			if (null != source) {
				leadsViewMapper.setSource(source.getName());
			}

			String middleName2 = "";
			String lastName2 = "";
			String satutation1 = "";

			if (!StringUtils.isEmpty(leads.getLastName())) {

				lastName2 = leads.getLastName();
			}
			if (leads.getMiddleName() != null && leads.getMiddleName().length() > 0) {

				middleName2 = leads.getMiddleName();
				if (leads.getSalutation() != null && leads.getSalutation().length() > 0) {
					satutation1 = leads.getSalutation();
					leadsViewMapper
							.setName(satutation1 + " " + leads.getFirstName() + " " + middleName2 + " " + lastName2);
				} else {

					leadsViewMapper.setName(leads.getFirstName() + " " + middleName2 + " " + lastName2);
				}
			} else {

				if (leads.getSalutation() != null && leads.getSalutation().length() > 0) {
					satutation1 = leads.getSalutation();
					leadsViewMapper.setName(satutation1 + " " + leads.getFirstName() + " " + lastName2);
				} else {

					leadsViewMapper.setName(leads.getFirstName() + " " + lastName2);
				}
			}

			EmployeeDetails employeeDetails = employeeRepository.getEmployeeDetailsByEmployeeId(leads.getUserId());
			if (null != employeeDetails) {
				String middleName = " ";
				String lastName = "";

				if (!StringUtils.isEmpty(employeeDetails.getLastName())) {

					lastName = employeeDetails.getLastName();
				}

				if (employeeDetails.getMiddleName() != null && employeeDetails.getMiddleName().length() > 0) {

					middleName = employeeDetails.getMiddleName();
					leadsViewMapper.setOwnerName(employeeDetails.getFirstName() + " " + middleName + " " + lastName);
					leadsViewMapper.setOwnerImageId(employeeDetails.getImageId());
				} else {

					leadsViewMapper.setOwnerName(employeeDetails.getFirstName() + " " + lastName);
					leadsViewMapper.setOwnerImageId(employeeDetails.getImageId());
				}

			}
			EmployeeDetails employeeDetail = employeeRepository.getEmployeeDetailsByEmployeeId(leads.getAssignedTo());
			if (null != employeeDetail) {
				String middleName = " ";
				String lastName = "";

				if (!StringUtils.isEmpty(employeeDetail.getLastName())) {

					lastName = employeeDetail.getLastName();
				}

				if (employeeDetail.getMiddleName() != null && employeeDetail.getMiddleName().length() > 0) {

					middleName = employeeDetail.getMiddleName();
					leadsViewMapper.setAssignedTo(employeeDetail.getFirstName() + " " + middleName + " " + lastName);

				} else {

					leadsViewMapper.setAssignedTo(employeeDetail.getFirstName() + " " + lastName);

				}

			}
//		//leads opportunity
//		LeadsOpportunityLink leadsOpportunityLinkList = leadsOpportunityLinkRepository.getOpportunityByLeadsId(leadsId);
//		if (leadsOpportunityLinkList != null ) {
//			leadsViewMapper.setOpportunityName(leadsOpportunityLinkList.getOpportunityName());
//			leadsViewMapper.setProposalValue(leadsOpportunityLinkList.getProposalValue());
//		}

			List<AddressMapper> addressList = new ArrayList<AddressMapper>();
			List<LeadsAddressLink> leadsAddressLink = leadsAddressLinkRepository.getAddressListByLeadsId(leadsId);

			if (null != leadsAddressLink && !leadsAddressLink.isEmpty()) {

				for (LeadsAddressLink leadsAddressLink2 : leadsAddressLink) {
					AddressDetails addressDetails = addressRepository
							.getAddressDetailsByAddressId(leadsAddressLink2.getAddressId());

					AddressMapper addressMapper = new AddressMapper();
					if (null != addressDetails) {

						addressMapper.setAddress1(addressDetails.getAddressLine1());
						addressMapper.setAddress2(addressDetails.getAddressLine2());
						// addressMapper.setAddressType(addressDetails.getAddress_type());
						addressMapper.setPostalCode(addressDetails.getPostalCode());

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
						Country country1 = countryRepository.getCountryDetailsByCountryNameAndOrgId(
								addressDetails.getCountry(), leads.getOrganizationId());
						if (null != country1) {
							addressMapper.setCountry_alpha2_code(country1.getCountry_alpha2_code());
							addressMapper.setCountry_alpha3_code(country1.getCountry_alpha3_code());
						}
						addressList.add(addressMapper);
					}
				}

				System.out.println("addressList.......... " + addressList);
			}
			leadsViewMapper.setAddress(addressList);
		}
		return leadsViewMapper;
	}

	@Override
	public List<LeadsReportMapper> getAllLeadsByUserIdForReport(String userId, String startDate, String endDate) {
		Date endDate1 = null;
		Date startDate1 = null;
		try {
			endDate1 = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			startDate1 = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<LeadsReportMapper> resultMapper = new ArrayList<>();
		List<Leads> leadsList = leadsRepository.getLeadsListByUserIdWithDateRange(userId, startDate1, endDate1);
		if (null != leadsList && !leadsList.isEmpty()) {
			resultMapper = leadsList.stream().map(leads -> {
				LeadsReportMapper mapper = getLeadsDetailsByIdForReport(leads.getLeadsId());
				return mapper;
			}).collect(Collectors.toList());
		}
		return resultMapper;
	}

	@Override
	public List<LeadsReportMapper> getQualifiedLeadsDetailsForReportByUserId(String userId, String startDate,
			String endDate) {
		List<LeadsReportMapper> mapperList = new ArrayList<>();
		Date end_date = null;
		Date start_date = null;
		try {
			end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<Leads> leadsList = leadsRepository.getQualifiedLeadsListByUserIdAndDateRange(userId, start_date, end_date);
		System.out.println("leadsList>>>" + leadsList.size());

		if (null != leadsList && !leadsList.isEmpty()) {
			leadsList.stream().map(li -> {
				LeadsReportMapper mapper = getLeadsDetailsByIdForReport(li.getLeadsId());
				if (null != mapper.getLeadsId()) {
					mapperList.add(mapper);
				}

				return mapperList;
			}).collect(Collectors.toList());

		}

		return mapperList;
	}

	@Override
	public List<LeadsReportMapper> getQualifiedLeadsDetailsForReportByOrgId(String orgId, String startDate,
			String endDate) {
		List<LeadsReportMapper> mapperList = new ArrayList<>();
		Date end_date = null;
		Date start_date = null;
		try {
			end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<Leads> leadsList = leadsRepository.getQualifiedLeadsListByOrgIdAndDateRange(orgId, start_date, end_date);
		System.out.println("leadsList>>>" + leadsList.size());

		if (null != leadsList && !leadsList.isEmpty()) {
			leadsList.stream().map(li -> {
				LeadsReportMapper mapper = getLeadsDetailsByIdForReport(li.getLeadsId());
				if (null != mapper.getLeadsId()) {
					mapperList.add(mapper);
				}

				return mapperList;
			}).collect(Collectors.toList());

		}

		return mapperList;
	}

	@Override
	public List<LeadsViewMapper> getTeamLeadsDetailsByUnderAUserId(String userId, int pageNo, int pageSize,
			String type) {
		Set<String> userIds = employeeRepository.getEmployeeDetailsByReportingManagerId(userId).stream()
				.map(EmployeeDetails::getUserId).collect(Collectors.toSet());
		userIds.add(userId);

		List<String> userIdss = new ArrayList<>(userIds);

		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("creationDate").descending());

		Page<Leads> leadsPage = leadsRepository.getTeamInvestorsListByUserIdsPaginatedAType(userIdss, paging, type);

		List<LeadsViewMapper> result = new ArrayList<>();

		if (leadsPage != null && !leadsPage.isEmpty()) {
			result = leadsPage.getContent().stream().map(li -> {
				LeadsViewMapper mapper = getLeadsDetailsById(li.getLeadsId());
				mapper.setPageCount(leadsPage.getTotalPages());
				mapper.setDataCount(leadsPage.getSize());
				mapper.setListCount(leadsPage.getTotalElements());
				return mapper;
			}).collect(Collectors.toList());
		}
		return result;
	}

	@Override
	public HashMap getTeamLeadsCountByUnderAUserId(String userId) {
		HashMap map = new HashMap();
		Set<String> userIds = employeeRepository.getEmployeeDetailsByReportingManagerId(userId).stream()
				.map(EmployeeDetails::getUserId).collect(Collectors.toSet());
		userIds.add(userId);

		List<String> userIdss = new ArrayList<>(userIds);
		List<Leads> leadslist = leadsRepository.getTeamLeadsListByUserIds(userIdss);
		map.put("leadsTeam", leadslist.size());

		return map;
	}

	@Override
	public List<LeadsViewMapper> getLeadsDetailsByUserIdAndType(String userId, int pageNo, int pageSize, String filter,
			String type) {
		System.out.println("start.....1");
		Pageable paging = null;
		if (filter.equalsIgnoreCase("creationdate")) {
			paging = PageRequest.of(pageNo, pageSize, Sort.by("creationDate").descending());

		} else if (filter.equalsIgnoreCase("ascending")) {
			paging = PageRequest.of(pageNo, pageSize, Sort.by("name"));

		} else if (filter.equalsIgnoreCase("descending")) {
			paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "name"));
		}
		Page<Leads> leadsList = leadsRepository.getInvestorsListByUserIdPaggingAndType(userId, paging, type);
//		System.out.println("leadsList>>>" + leadsList.toString());
		List<LeadsViewMapper> mapperList = new ArrayList<>();

		if (null != leadsList && !leadsList.isEmpty()) {
			mapperList = leadsList.stream().map(li -> {
				LeadsViewMapper mapper = getLeadsDetailsById(li.getLeadsId());
				mapper.setPageCount(leadsList.getTotalPages());
				mapper.setDataCount(leadsList.getSize());
				mapper.setListCount(leadsList.getTotalElements());
				return mapper;
			}).collect(Collectors.toList());

		}
		return mapperList;
	}

	@Override
	public List<LeadsViewMapper> getLeadsDetailsByNameByOrgLevel(String name, String orgId) {
		List<Leads> detailsList = leadsRepository.getLeadsListByOrgId(orgId);
		List<Leads> filterList = detailsList.parallelStream().filter(detail -> {
			return detail.getName() != null && Utility.containsIgnoreCase(detail.getName(), name.trim());
		}).collect(Collectors.toList());
		List<LeadsViewMapper> mapperList = new ArrayList<LeadsViewMapper>();
		if (null != filterList && !filterList.isEmpty()) {

			mapperList = filterList.parallelStream().map(li -> getLeadsDetailsById(li.getLeadsId()))
					.collect(Collectors.toList());

		}

		return mapperList;
	}

	@Override
	public List<LeadsViewMapper> getLeadsDetailsBySectorInOrgLevel(String name, String orgId) {
		List<LeadsViewMapper> mapperList = new ArrayList<>();
		SectorDetails sectorDetails = sectorDetailsRepository.getSectorDetailsBySectorNameAndOrgId(name, orgId);
		if (null != sectorDetails) {
			List<Leads> detailsList = leadsRepository
					.findBySectorAndLiveIndAndOrganizationId(sectorDetails.getSectorId(), true, orgId);
			if (null != detailsList && !detailsList.isEmpty()) {
				mapperList = detailsList.stream().map(li -> getLeadsDetailsById(li.getLeadsId()))
						.collect(Collectors.toList());
			}
		}
		return mapperList;
	}

	@Override
	public List<LeadsViewMapper> getLeadsDetailsBySourceInOrgLevel(String name, String orgId) {
		List<LeadsViewMapper> mapperList = new ArrayList<>();
		Source source = sourceRepository.findByNameAndOrgId(name, orgId);
		if (null != source) {
			List<Leads> detailsList = leadsRepository.findBySourceAndLiveIndAndOrganizationId(source.getSourceId(),
					true, orgId);
			if (null != detailsList && !detailsList.isEmpty()) {
				mapperList = detailsList.stream().map(li -> getLeadsDetailsById(li.getLeadsId()))
						.collect(Collectors.toList());
			}
		}
		return mapperList;
	}

	@Override
	public List<LeadsViewMapper> getLeadsDetailsByOwnerNameInOrgLevel(String name, String orgId) {
		List<LeadsViewMapper> mapperList = new ArrayList<>();

		List<EmployeeDetails> detailsList = employeeRepository.getEmployeesByOrgId(orgId);

		List<EmployeeDetails> filterList = detailsList.parallelStream().filter(
				detail -> detail.getFullName() != null && Utility.containsIgnoreCase(detail.getFullName(), name.trim()))
				.collect(Collectors.toList());

		if (null != filterList && !filterList.isEmpty()) {
			mapperList = filterList.stream().flatMap(
					employeeDetails -> leadsRepository.findByUserIdAndLiveInd(employeeDetails.getUserId(),true).stream())
					.map(leads -> getLeadsDetailsById(leads.getLeadsId())).collect(Collectors.toList());
		}

		return mapperList;
	}

	@Override
	public List<LeadsViewMapper> getLeadsDetailsByNameForTeam(String name, String userId) {
		Set<String> userIds = employeeRepository.getEmployeeDetailsByReportingManagerId(userId).stream()
				.map(EmployeeDetails::getUserId).collect(Collectors.toSet());
		userIds.add(userId);
		List<String> userIdss = new ArrayList<>(userIds);
		List<Leads> list = leadsRepository.getTeamLeadsListByUserIds(userIdss);
		List<Leads> filterList = list.parallelStream().filter(detail -> {
			return detail.getName() != null && Utility.containsIgnoreCase(detail.getName(), name.trim());
		}).collect(Collectors.toList());
		List<LeadsViewMapper> mapperList = new ArrayList<LeadsViewMapper>();
		if (null != filterList && !filterList.isEmpty()) {
			mapperList = filterList.parallelStream().map(li -> getLeadsDetailsById(li.getLeadsId()))
					.collect(Collectors.toList());
		}
		return mapperList;
	}

	@Override
	public List<LeadsViewMapper> getLeadsDetailsBySectorForTeam(String name, String userId, String orgId) {
		List<LeadsViewMapper> mapperList = new ArrayList<>();
		SectorDetails sectorDetails = sectorDetailsRepository.getSectorDetailsBySectorNameAndOrgId(name, orgId);
		if (null != sectorDetails) {
			Set<String> userIds = employeeRepository.getEmployeeDetailsByReportingManagerId(userId).stream()
					.map(EmployeeDetails::getUserId).collect(Collectors.toSet());
			userIds.add(userId);
			List<String> userIdss = new ArrayList<>(userIds);
			List<Leads> filterList = leadsRepository.getTeamLeadsListByUserIdsAndSector(userIdss,
					sectorDetails.getSectorId());
			mapperList = new ArrayList<LeadsViewMapper>();
			if (null != filterList && !filterList.isEmpty()) {
				mapperList = filterList.parallelStream().map(li -> getLeadsDetailsById(li.getLeadsId()))
						.collect(Collectors.toList());
			}
		}
		return mapperList;
	}

	@Override
	public List<LeadsViewMapper> getLeadsDetailsBySourceForTeam(String name, String userId, String orgId) {
		List<LeadsViewMapper> mapperList = new ArrayList<>();
		Source source = sourceRepository.findByNameAndOrgId(name, orgId);
		if (null != source) {
			Set<String> userIds = employeeRepository.getEmployeeDetailsByReportingManagerId(userId).stream()
					.map(EmployeeDetails::getUserId).collect(Collectors.toSet());
			userIds.add(userId);
			List<String> userIdss = new ArrayList<>(userIds);
			List<Leads> filterList = leadsRepository.getTeamLeadsListByUserIdsAndSource(userIdss, source.getSourceId());
			mapperList = new ArrayList<LeadsViewMapper>();
			if (null != filterList && !filterList.isEmpty()) {
				mapperList = filterList.parallelStream().map(li -> getLeadsDetailsById(li.getLeadsId()))
						.collect(Collectors.toList());
			}
		}
		return mapperList;
	}

	@Override
	public List<LeadsViewMapper> getLeadsDetailsByOwnerNameForTeam(String name, String userId, String orgId) {
		List<LeadsViewMapper> mapperList = new ArrayList<>();

		List<EmployeeDetails> detailsList = employeeRepository.getEmployeeDetailsByReportingManagerIdAndUserId(userId);

		List<EmployeeDetails> filterList = detailsList.parallelStream().filter(
				detail -> detail.getFullName() != null && Utility.containsIgnoreCase(detail.getFullName(), name.trim()))
				.collect(Collectors.toList());

		if (null != filterList && !filterList.isEmpty()) {
			mapperList = filterList.stream().flatMap(
					employeeDetails -> leadsRepository.findByUserIdAndLiveInd(employeeDetails.getUserId(),true).stream())
					.map(leads -> getLeadsDetailsById(leads.getLeadsId())).collect(Collectors.toList());
		}
		return mapperList;
	}

	@Override
	public List<LeadsViewMapper> getLeadsDetailsByNameByUserId(String name, String userId) {
		List<Leads> list = leadsRepository.getByUserIdandLiveInd(userId);
		List<Leads> filterList = list.parallelStream().filter(detail -> {
			return detail.getName() != null && Utility.containsIgnoreCase(detail.getName(), name.trim());
		}).collect(Collectors.toList());
		List<LeadsViewMapper> mapperList = new ArrayList<LeadsViewMapper>();
		if (null != filterList && !filterList.isEmpty()) {
			mapperList = filterList.parallelStream().map(leads -> getLeadsDetailsById(leads.getLeadsId()))
					.collect(Collectors.toList());
		}
		return mapperList;
	}

	@Override
	public List<LeadsViewMapper> getLeadsBySectorInUserLevel(String name, String userId, String orgId) {
		List<LeadsViewMapper> mapperList = new ArrayList<>();
		SectorDetails sectorDetails = sectorDetailsRepository.getSectorDetailsBySectorNameAndOrgId(name, orgId);
		if (null != sectorDetails) {
			List<Leads> detailsList = leadsRepository.findBySectorAndLiveIndAndUserId(sectorDetails.getSectorId(), true,
					userId);
			if (null != detailsList && !detailsList.isEmpty()) {
				mapperList = detailsList.stream().map(leads -> getLeadsDetailsById(leads.getLeadsId()))
						.collect(Collectors.toList());
			}
		}
		return mapperList;
	}

	@Override
	public List<LeadsViewMapper> getLeadsBySourceInUserLevel(String name, String userId, String orgId) {
		List<LeadsViewMapper> mapperList = new ArrayList<>();
		Source source = sourceRepository.findByNameAndOrgId(name, orgId);
		if (null != source) {
			List<Leads> detailsList = leadsRepository.findBySourceAndLiveIndAndUserId(source.getSourceId(), true,
					userId);
			if (null != detailsList && !detailsList.isEmpty()) {
				mapperList = detailsList.stream().map(leads -> getLeadsDetailsById(leads.getLeadsId()))
						.collect(Collectors.toList());
			}
		}
		return mapperList;
	}

	@Override
	public List<LeadsViewMapper> getLeadsByOwnerNameInUserLevel(String name, String userId, String orgId) {
		List<LeadsViewMapper> mapperList = new ArrayList<>();

		List<EmployeeDetails> detailsList = employeeRepository.getEmployeesByOrgId(orgId);

		List<EmployeeDetails> filterList = detailsList.parallelStream().filter(
				detail -> detail.getFullName() != null && Utility.containsIgnoreCase(detail.getFullName(), name.trim()))
				.filter(detail -> detail.getUserId().equalsIgnoreCase(userId)).collect(Collectors.toList());

		if (null != filterList && !filterList.isEmpty()) {
			mapperList = filterList.stream().flatMap(
					employeeDetails -> leadsRepository.findByUserIdAndLiveInd(employeeDetails.getUserId(),true).stream())
					.map(leads -> getLeadsDetailsById(leads.getLeadsId())).collect(Collectors.toList());
		}
		return mapperList;
	}

}
