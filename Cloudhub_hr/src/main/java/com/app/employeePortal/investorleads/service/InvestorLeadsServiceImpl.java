package com.app.employeePortal.investorleads.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
import com.app.employeePortal.call.entity.EmployeeCallLink;
import com.app.employeePortal.call.entity.InvestorCallLink;
import com.app.employeePortal.call.entity.InvestorLeadsCallLink;
import com.app.employeePortal.call.repository.CallDetailsRepository;
import com.app.employeePortal.call.repository.EmployeeCallRepository;
import com.app.employeePortal.call.repository.InvestorCallLinkRepo;
import com.app.employeePortal.call.repository.InvestorLeadsCallLinkRepo;
import com.app.employeePortal.category.entity.Club;
import com.app.employeePortal.category.repository.ClubRepository;
import com.app.employeePortal.contact.entity.ContactAddressLink;
import com.app.employeePortal.contact.entity.ContactDetails;
import com.app.employeePortal.contact.entity.ContactInfo;
import com.app.employeePortal.contact.entity.ContactNotesLink;
import com.app.employeePortal.contact.mapper.ContactMapper;
import com.app.employeePortal.contact.mapper.ContactViewMapper;
import com.app.employeePortal.contact.repository.ContactAddressLinkRepository;
import com.app.employeePortal.contact.repository.ContactInfoRepository;
import com.app.employeePortal.contact.repository.ContactNotesLinkRepository;
import com.app.employeePortal.contact.repository.ContactRepository;
import com.app.employeePortal.contact.service.ContactService;
import com.app.employeePortal.customer.mapper.TransferMapper;
import com.app.employeePortal.distributor.entity.Distributor;
import com.app.employeePortal.distributor.entity.DistributorAddressLink;
import com.app.employeePortal.distributor.entity.DistributorContactPersonLink;
import com.app.employeePortal.distributor.entity.MonthlyDistributor;
import com.app.employeePortal.distributor.entity.TodayDistributor;
import com.app.employeePortal.distributor.entity.YearlyDistributor;
import com.app.employeePortal.distributor.repository.DistributorAddressLinkRepository;
import com.app.employeePortal.distributor.repository.DistributorContactPersonLinkRepository;
import com.app.employeePortal.distributor.repository.DistributorRepository;
import com.app.employeePortal.distributor.repository.MonthlyDistributorRepository;
import com.app.employeePortal.distributor.repository.TodayDistributorRepository;
import com.app.employeePortal.distributor.repository.YearlyDistributorRepository;
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
import com.app.employeePortal.event.entity.EmployeeEventLink;
import com.app.employeePortal.event.entity.EventDetails;
import com.app.employeePortal.event.entity.InvestorEventLink;
import com.app.employeePortal.event.entity.InvestorLeadsEventLink;
import com.app.employeePortal.event.repository.EmployeeEventRepository;
import com.app.employeePortal.event.repository.EventDetailsRepository;
import com.app.employeePortal.event.repository.InvestorEventRepo;
import com.app.employeePortal.event.repository.InvestorLeadsEventRepo;
import com.app.employeePortal.investor.entity.Investor;
import com.app.employeePortal.investor.entity.InvestorAddressLink;
import com.app.employeePortal.investor.entity.InvestorContactLink;
import com.app.employeePortal.investor.entity.InvestorDocumentLink;
import com.app.employeePortal.investor.entity.InvestorsShare;
import com.app.employeePortal.investor.mapper.InvestorOpportunityMapper;
import com.app.employeePortal.investor.repository.InvestorAddressLinkRepo;
import com.app.employeePortal.investor.repository.InvestorContactLinkRepo;
import com.app.employeePortal.investor.repository.InvestorDocumentLinkRepo;
import com.app.employeePortal.investor.repository.InvestorRepository;
import com.app.employeePortal.investor.repository.InvestorShareRepo;
import com.app.employeePortal.investor.service.InvestorOppService;
import com.app.employeePortal.investorleads.entity.InvestorLeads;
import com.app.employeePortal.investorleads.entity.InvestorLeadsAddressLink;
import com.app.employeePortal.investorleads.entity.InvestorLeadsContactLink;
import com.app.employeePortal.investorleads.entity.InvestorLeadsDocumentLink;
import com.app.employeePortal.investorleads.entity.InvestorLeadsNotesLink;
import com.app.employeePortal.investorleads.entity.InvestorLeadsOpportunityLink;
import com.app.employeePortal.investorleads.mapper.InvestorLeadsMapper;
import com.app.employeePortal.investorleads.mapper.InvestorLeadsOpportunityMapper;
import com.app.employeePortal.investorleads.mapper.InvestorLeadsReportMapper;
import com.app.employeePortal.investorleads.mapper.InvestorLeadsViewMapper;
import com.app.employeePortal.investorleads.repository.InvestorLeadsAddressLinkRepository;
import com.app.employeePortal.investorleads.repository.InvestorLeadsContactLinkRepository;
import com.app.employeePortal.investorleads.repository.InvestorLeadsDocumentLinkRepository;
import com.app.employeePortal.investorleads.repository.InvestorLeadsNotesLinkRepository;
import com.app.employeePortal.investorleads.repository.InvestorLeadsOpportunityLinkRepository;
import com.app.employeePortal.investorleads.repository.InvestorLeadsRepository;
import com.app.employeePortal.leads.entity.Leads;
import com.app.employeePortal.leads.mapper.ActivityMapper;
import com.app.employeePortal.leads.mapper.LeadsViewMapper;
import com.app.employeePortal.leads.repository.LeadsAddressLinkRepository;
import com.app.employeePortal.notification.entity.Notificationparam;
import com.app.employeePortal.notification.repository.NotificationRepository;
import com.app.employeePortal.notification.service.NotificationService;
import com.app.employeePortal.opportunityWorkflow.entity.OpportunityStages;
import com.app.employeePortal.opportunityWorkflow.entity.OpportunityWorkflowDetails;
import com.app.employeePortal.opportunityWorkflow.repository.OpportunityStagesRepository;
import com.app.employeePortal.opportunityWorkflow.repository.OpportunityWorkflowDetailsRepository;
import com.app.employeePortal.registration.entity.Country;
import com.app.employeePortal.registration.entity.Currency;
import com.app.employeePortal.registration.repository.CountryRepository;
import com.app.employeePortal.registration.repository.CurrencyRepository;
import com.app.employeePortal.sector.entity.SectorDetails;
import com.app.employeePortal.sector.repository.SectorDetailsRepository;
import com.app.employeePortal.source.entity.Source;
import com.app.employeePortal.source.repository.SourceRepository;
import com.app.employeePortal.task.entity.EmployeeTaskLink;
import com.app.employeePortal.task.entity.InvestorLeadsTaskLink;
import com.app.employeePortal.task.entity.InvestorTaskLink;
import com.app.employeePortal.task.entity.TaskDetails;
import com.app.employeePortal.task.entity.TaskDocumentLink;
import com.app.employeePortal.task.repository.EmployeeTaskRepository;
import com.app.employeePortal.task.repository.InvestorLeadsTaskRepo;
import com.app.employeePortal.task.repository.InvestorTaskRepo;
import com.app.employeePortal.task.repository.TaskDetailsRepository;
import com.app.employeePortal.task.repository.TaskDocumentLinkRepository;
import com.app.employeePortal.util.Utility;

import freemarker.template.TemplateException;

@Service
@Transactional
public class InvestorLeadsServiceImpl implements InvestorLeadsService {

	@Autowired
	ContactService contactService;
	@Autowired
	InvestorLeadsContactLinkRepository investorleadsContactLinkRepository;
	@Autowired
	InvestorLeadsRepository investorLeadsRepository;
	@Autowired
	AddressInfoRepository addressInfoRepository;
	@Autowired
	AddressRepository addressRepository;
	@Autowired
	LeadsAddressLinkRepository leadsAddressLinkRepository;
	@Autowired
	EmployeeService employeeService;
	@Autowired
	SectorDetailsRepository sectorDetailsRepository;
	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	InvestorLeadsAddressLinkRepository investorLeadsAddressLinkRepository;
	@Autowired
	InvestorLeadsContactLinkRepository investorLeadsContactLinkRepository;
	@Autowired
	DocumentDetailsRepository documentDetailsRepository;
	@Autowired
	InvestorLeadsDocumentLinkRepository investorLeadsDocumentLinkRepository;
	@Autowired
	DocumentTypeRepository documentTypeRepository;
	@Autowired
	InvestorLeadsOpportunityLinkRepository investorLeadsOpportunityLinkRepository;
	@Autowired
	OpportunityWorkflowDetailsRepository opportunityWorkflowDetailsRepository;
	@Autowired
	OpportunityStagesRepository opportunityStagesRepository;
	@Autowired
	ContactRepository contactRepository;
	@Autowired
	InvestorRepository investorRepository;
	@Autowired
	InvestorAddressLinkRepo investorAddressLinkRepository;
	@Autowired
	InvestorContactLinkRepo investorContactLinkRepository;
	@Autowired
	InvestorDocumentLinkRepo investorDocumentLinkRepository;
	@Autowired
	SourceRepository sourceRepository;
	@Autowired
	InvestorOppService investorOpportunityService;
	@Autowired
	CountryRepository countryRepository;
	@Autowired
	InvestorLeadsCallLinkRepo investorLeadsCallLinkRepo;
	@Autowired
	CallDetailsRepository callDetailsRepository;
	@Autowired
	InvestorLeadsEventRepo investorLeadsEventRepo;
	@Autowired
	EventDetailsRepository eventDetailsRepository;
	@Autowired
	InvestorLeadsTaskRepo investorLeadsTaskRepo;
	@Autowired
	TaskDetailsRepository taskDetailsRepository;
	@Autowired
	NotesRepository notesRepository;
	@Autowired
	InvestorLeadsNotesLinkRepository investorLeadsNotesLinkRepository;
	@Autowired
	ContactNotesLinkRepository contactNotesLinkRepository;
	@Autowired
	InvestorLeadsCallLinkRepo investorLeadsCallLinkRepository;
	@Autowired
	InvestorCallLinkRepo investorCallLinkRepository;
	@Autowired
	InvestorLeadsEventRepo investorLeadsEventLinkRepository;
	@Autowired
	InvestorEventRepo investorEventLinkRepository;
	@Autowired
	InvestorLeadsTaskRepo investorLeadsTaskLinkRepository;
	@Autowired
	InvestorTaskRepo investorTaskLinkRepository;
	@Autowired
	TeamRepository teamRepository;
	@Autowired
	TeamMemberLinkRepo teamMemberLinkRepo;
	@Autowired
	NotificationService notificationService;
	@Autowired
	NotificationRepository notificationRepository;
	@Autowired
	EmployeeCallRepository employeeCallRepository;
	@Autowired
	EmployeeTaskRepository employeeTaskRepository;
	@Autowired
	EmployeeEventRepository employeeEventRepository;
	@Autowired
	DocumentService documentService;
	@Autowired
	TaskDocumentLinkRepository taskDocumentLinkRepository;
	@Autowired
	InvestorShareRepo investorShareRepo;
	@Autowired
	ClubRepository clubRepository;
	@Autowired
	DistributorRepository distributorRepository;
	@Autowired
	InvestorAddressLinkRepo investorAddressLinkRepo;
	@Autowired
	YearlyDistributorRepository yearlyDistributorRepository;
	@Autowired
	TodayDistributorRepository todayDistributorRepository;
	@Autowired
	MonthlyDistributorRepository monthlyDistributorRepository;
	@Autowired
	DistributorAddressLinkRepository distributorAddressLinkRepository;
	@Autowired
	InvestorContactLinkRepo investorContactLinkRepo;
	@Autowired
	ContactInfoRepository contactInfoRepository;
	@Autowired
	DistributorContactPersonLinkRepository distributorContactPersonLinkRepository;
	@Autowired
	ContactAddressLinkRepository contactAddressLinkRepository;
	@Autowired
	CurrencyRepository currencyRepository;
	@Value("${companyName}")
	private String companyName;

	public InvestorLeadsViewMapper saveInvestorLeads(InvestorLeadsMapper investorleadsMapper)
			throws IOException, TemplateException {
		InvestorLeadsViewMapper resultMapper = null;

		InvestorLeads investorleads = new InvestorLeads();
		String investorLeadsId = investorLeadsRepository.save(investorleads).getInvestorLeadsId();
		setPropertyOnInput(investorleadsMapper, investorleads, investorLeadsId);
		System.out.println("investorLeadsId   ==" + investorLeadsId);

		ContactMapper contactMapper = new ContactMapper();
		contactMapper.setSalutation(investorleadsMapper.getSalutation());
		contactMapper.setFirstName(investorleadsMapper.getFirstName());
		contactMapper.setMiddleName(investorleadsMapper.getMiddleName());
		contactMapper.setLastName(investorleadsMapper.getLastName());
		contactMapper.setEmailId(investorleadsMapper.getEmail());
		contactMapper.setPhoneNumber(investorleadsMapper.getPhoneNumber());
		contactMapper.setMobileNumber(investorleadsMapper.getPhoneNumber());
		contactMapper.setInvestorLeadsId(investorLeadsId);
		contactMapper.setCountryDialCode(investorleadsMapper.getCountryDialCode());
		contactMapper.setCountryDialCode1(investorleadsMapper.getCountryDialCode());
		contactMapper.setImageId(investorleadsMapper.getImageId());
		contactMapper.setAddress(investorleadsMapper.getAddress());
		contactMapper.setUserId(investorleadsMapper.getUserId());
		contactMapper.setOrganizationId(investorleadsMapper.getOrgId());

		ContactViewMapper resultMapperr = contactService.saveContact(contactMapper);
		System.out.println("contactID===========++++++++++++++++++++++++++++++++++===" + resultMapperr.getContactId());
		if (null != resultMapperr.getContactId()) {

			/* insert to investor-leads-contact-link */
			InvestorLeadsContactLink investorleadsContactLink = new InvestorLeadsContactLink();
			investorleadsContactLink.setInvestorLeadsId(contactMapper.getInvestorLeadsId());
			investorleadsContactLink.setContactId(resultMapperr.getContactId());
			investorleadsContactLink.setCreationDate(new Date());

			investorleadsContactLinkRepository.save(investorleadsContactLink);

		}
		investorLeadsRepository.save(investorleads);
		/* insert to notification table */

		/* insert to Notification Table */
		Notificationparam param = new Notificationparam();
		EmployeeDetails emp = employeeRepository.getEmployeesByuserId(investorleadsMapper.getUserId());
		String name = employeeService.getEmployeeFullNameByObject(emp);
		param.setEmployeeDetails(emp);
		param.setAdminMsg("Pitch " + "'" + investorleadsMapper.getName() + "' created by " + name);
		param.setOwnMsg("Pitch " + investorleadsMapper.getName() + " created.");
		param.setNotificationType("Lead Creation");
		param.setProcessNmae("Pitch");
		param.setType("create");
		param.setEmailSubject("Korero alert- Pitch created");
		param.setCompanyName(companyName);
		param.setUserId(investorleadsMapper.getUserId());

		if (!investorleadsMapper.getUserId().equals(investorleadsMapper.getAssignedTo())) {
			List<String> assignToUserIds = new ArrayList<>();
			assignToUserIds.add(investorleadsMapper.getAssignedTo());
			param.setAssignToUserIds(assignToUserIds);
			param.setAssignToMsg("Pitch " + "'" + investorleadsMapper.getName() + "'" + " assigned to "
					+ employeeService.getEmployeeFullName(investorleadsMapper.getAssignedTo()) + " by " + name);
		}
		notificationService.createNotificationForDynamicUsers(param);

		resultMapper = getInvestorLeadsDetailsById(investorLeadsId);
		return resultMapper;
	}

	private void setPropertyOnInput(InvestorLeadsMapper investorleadsMapper, InvestorLeads investorleads,
			String investorLeadsId) {

		investorleads.setInvestorLeadsId(investorLeadsId);
		// leads.setName(leadsMapper.getName());
		investorleads.setSalutation(investorleadsMapper.getSalutation());
		investorleads.setFirstName(investorleadsMapper.getFirstName());
		investorleads.setMiddleName(investorleadsMapper.getMiddleName());
		investorleads.setLastName(investorleadsMapper.getLastName());
		investorleads.setImageId(investorleadsMapper.getImageId());
		investorleads.setUrl(investorleadsMapper.getUrl());
		investorleads.setNotes(investorleadsMapper.getNotes());
		investorleads.setCountryDialCode(investorleadsMapper.getCountryDialCode());
		investorleads.setPhoneNumber(investorleadsMapper.getPhoneNumber());
		investorleads.setEmail(investorleadsMapper.getEmail());
		investorleads.setUserId(investorleadsMapper.getUserId());
		investorleads.setOrganizationId(investorleadsMapper.getOrgId());
		investorleads.setGroup(investorleadsMapper.getGroup());
		investorleads.setVatNo(investorleadsMapper.getVatNo());
		investorleads.setCreationDate(new Date());
		investorleads.setLiveInd(true);
		investorleads.setDocumentId(investorleadsMapper.getDocumentId());
		investorleads.setSector(investorleadsMapper.getSectorId());
		System.out.println("get sector in frontend............" + investorleadsMapper.getSectorId());
		investorleads.setCountry(investorleadsMapper.getCountry());
		investorleads.setZipcode(investorleadsMapper.getZipcode());
		investorleads.setZipcode(investorleadsMapper.getZipcode());
		investorleads.setCategory(investorleadsMapper.getCategory());
		investorleads.setImageURL(investorleadsMapper.getImageURL());
		investorleads.setAssignedTo(investorleadsMapper.getAssignedTo());
		investorleads.setBusinessRegistration(investorleadsMapper.getBusinessRegistration());
		investorleads.setConvertInd(false);
		investorleads.setCompanyName(investorleadsMapper.getName());
		investorleads.setType(investorleadsMapper.getType());
		investorleads.setTypeUpdationDate(new Date());
		investorleads.setJunkInd(false);
		investorleads.setSource(investorleadsMapper.getSource());
		investorleads.setSourceUserId(investorleadsMapper.getSourceUserID());
		investorleads.setAssignedby(investorleadsMapper.getUserId());
		investorleads.setValueOfShare(investorleadsMapper.getValueOfShare());
		investorleads.setUnitOfShare(investorleadsMapper.getUnitOfShare());
		investorleads.setPvtAndIntunlInd(investorleadsMapper.isPvtAndIntunlInd());
		investorleads
				.setTotalShareValue((investorleadsMapper.getValueOfShare()) * (investorleadsMapper.getUnitOfShare()));
		investorleads.setShareCurrency(investorleadsMapper.getShareCurrency());
		try {
			investorleads.setFirstMeetingDate(Utility.getDateFromISOString(investorleadsMapper.getFirstMeetingDate()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		String middleName2 = "";
		String lastName2 = "";
		String satutation1 = "";

		if (!StringUtils.isEmpty(investorleadsMapper.getLastName())) {

			lastName2 = investorleadsMapper.getLastName();
		}
		if (investorleadsMapper.getMiddleName() != null && investorleadsMapper.getMiddleName().length() > 0) {

			middleName2 = investorleadsMapper.getMiddleName();
			if (investorleadsMapper.getSalutation() != null && investorleadsMapper.getSalutation().length() > 0) {
				satutation1 = investorleadsMapper.getSalutation();
				investorleads.setName(
						satutation1 + " " + investorleadsMapper.getFirstName() + " " + middleName2 + " " + lastName2);
			} else {

				investorleads.setName(investorleadsMapper.getFirstName() + " " + middleName2 + " " + lastName2);
			}
		} else {

			if (investorleadsMapper.getSalutation() != null && investorleadsMapper.getSalutation().length() > 0) {
				satutation1 = investorleadsMapper.getSalutation();
				investorleads.setName(satutation1 + " " + investorleadsMapper.getFirstName() + " " + lastName2);
			} else {

				investorleads.setName(investorleadsMapper.getFirstName() + " " + lastName2);
			}
		}

		if (investorleadsMapper.getAddress().size() > 0) {
			for (AddressMapper addressMapper : investorleadsMapper.getAddress()) {
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

					InvestorLeadsAddressLink investorLeadsAddressLink = new InvestorLeadsAddressLink();
					investorLeadsAddressLink.setInvestorLeadsId(investorLeadsId);
					investorLeadsAddressLink.setAddressId(addressId);
					investorLeadsAddressLink.setCreationDate(new Date());

					investorLeadsAddressLinkRepository.save(investorLeadsAddressLink);

				}
			}
		}
		/* insert to Notification Table */
		String message = "An investorLeads is created By ";
		notificationService.createNotification(investorleadsMapper.getUserId(), "investor creation", message,
				"investorLeads", "create");

		Notes notes = new Notes();
		notes.setCreation_date(new Date());
		notes.setNotes(investorleadsMapper.getNotes());
		notes.setLiveInd(true);
		Notes note = notesRepository.save(notes);

		String notesId = note.getNotes_id();
		/* insert to InvestorNoteLink table */

		InvestorLeadsNotesLink callNote = new InvestorLeadsNotesLink();
		callNote.setInvestorLeadsId(investorLeadsId);
		callNote.setNoteId(notesId);
		callNote.setCreationDate(new Date());
		investorLeadsNotesLinkRepository.save(callNote);
	}

	public InvestorLeadsViewMapper getInvestorLeadsDetailsById(String investorLeadsId) {
		InvestorLeads investorLeads = investorLeadsRepository.getInvestorLeadsByIdAndLiveInd(investorLeadsId);
		System.out.println("Leads object is..." + investorLeadsId);

		InvestorLeadsViewMapper leadsViewMapper = new InvestorLeadsViewMapper();

		if (null != investorLeads) {

			if (investorLeads.getSector() != null && investorLeads.getSector().trim().length() > 0) {
				SectorDetails sector = sectorDetailsRepository.getSectorDetailsBySectorId(investorLeads.getSector());
				System.out.println("get sectordetails by id returns........." + investorLeads.getSector());
				System.out.println("sector object is......." + sector);
				if (sector != null) {
					leadsViewMapper.setSector(sector.getSectorName());
					leadsViewMapper.setSectorId(investorLeads.getSector());
				} else {
					leadsViewMapper.setSector("");
					leadsViewMapper.setSectorId("");
				}
			}
			leadsViewMapper.setInvestorLeadsId(investorLeadsId);
			leadsViewMapper.setName(employeeService.getEmployeeFullName(investorLeads.getUserId()));
			leadsViewMapper.setSalutation(investorLeads.getSalutation());
			leadsViewMapper.setFirstName(investorLeads.getFirstName());
			leadsViewMapper.setMiddleName(investorLeads.getMiddleName());
			leadsViewMapper.setLastName(investorLeads.getLastName());
			leadsViewMapper.setImageId(investorLeads.getImageId());
			leadsViewMapper.setUrl(investorLeads.getUrl());
			leadsViewMapper.setNotes(investorLeads.getNotes());
			leadsViewMapper.setEmail(investorLeads.getEmail());
			leadsViewMapper.setGroup(investorLeads.getGroup());
			leadsViewMapper.setVatNo(investorLeads.getVatNo());
			leadsViewMapper.setPhoneNumber(investorLeads.getPhoneNumber());
			leadsViewMapper.setCountryDialCode(investorLeads.getCountryDialCode());
			leadsViewMapper.setUserId(investorLeads.getUserId());
			leadsViewMapper.setOrgId(investorLeads.getOrganizationId());
			leadsViewMapper.setCreationDate(Utility.getISOFromDate(investorLeads.getCreationDate()));
			leadsViewMapper.setImageURL(investorLeads.getImageURL());
			leadsViewMapper.setValueOfShare(investorLeads.getValueOfShare());
			leadsViewMapper.setUnitOfShare(investorLeads.getUnitOfShare());
			leadsViewMapper.setPvtAndIntunlInd(investorLeads.isPvtAndIntunlInd());

			if (!StringUtils.isEmpty(investorLeads.getCountry())) {
				leadsViewMapper.setCountry(investorLeads.getCountry());
				Country country = countryRepository.getCountryDetailsByCountryNameAndOrgId(investorLeads.getCountry(),
						investorLeads.getOrganizationId());
				if (null != country) {
					leadsViewMapper.setCountry_alpha3_code(country.getCountry_alpha3_code());
					leadsViewMapper.setCountry_alpha2_code(country.getCountry_alpha2_code());
				}
			} else {
				List<InvestorLeadsAddressLink> investorLeadsAddressLinkLists = investorLeadsAddressLinkRepository
						.getAddressListByInvestorLeadsId(investorLeads.getInvestorLeadsId());
				if (null != investorLeadsAddressLinkLists && !investorLeadsAddressLinkLists.isEmpty()) {
					for (InvestorLeadsAddressLink employeeAddressLink : investorLeadsAddressLinkLists) {
						AddressDetails addressDetails = addressRepository
								.getAddressDetailsByAddressId(employeeAddressLink.getAddressId());
						if (null != addressDetails) {
							if (!StringUtils.isEmpty(addressDetails.getCountry())) {
								leadsViewMapper.setCountry(addressDetails.getCountry());
								Country country = countryRepository.getCountryDetailsByCountryNameAndOrgId(
										addressDetails.getCountry(), investorLeads.getOrganizationId());
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
			leadsViewMapper.setZipcode(investorLeads.getZipcode());

			leadsViewMapper.setDocumentId(investorLeads.getDocumentId());
			leadsViewMapper.setCategory(investorLeads.getCategory());
			leadsViewMapper.setBusinessRegistration(investorLeads.getBusinessRegistration());
			leadsViewMapper.setConvertInd(investorLeads.isConvertInd());
			leadsViewMapper.setConvertionDate(Utility.getISOFromDate(investorLeads.getConvertionDate()));
			leadsViewMapper.setCompanyName(investorLeads.getCompanyName());
			leadsViewMapper.setType(investorLeads.getType());
			leadsViewMapper.setTypeUpdationDate(Utility.getISOFromDate(investorLeads.getTypeUpdationDate()));
			leadsViewMapper.setJunkInd(investorLeads.isJunkInd());
			leadsViewMapper.setSourceUserID(investorLeads.getSourceUserId());
			leadsViewMapper.setSourceUserName(employeeService.getEmployeeFullName(investorLeads.getSourceUserId()));
			leadsViewMapper.setAssignedBy(employeeService.getEmployeeFullName(investorLeads.getAssignedby()));
			leadsViewMapper.setFirstMeetingDate(Utility.getISOFromDate(investorLeads.getFirstMeetingDate()));

			Currency currency = currencyRepository.getByCurrencyId(investorLeads.getShareCurrency());
			if (null != currency) {
				leadsViewMapper.setShareCurrency(currency.getCurrencyName());
			}

			Source source = sourceRepository.findBySourceId(investorLeads.getSource());
			if (null != source) {
				leadsViewMapper.setSource(source.getName());
			}

			EmployeeDetails employeeDetails = employeeRepository
					.getEmployeeDetailsByEmployeeId(investorLeads.getUserId());
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
			EmployeeDetails employeeDetail = employeeRepository
					.getEmployeeDetailsByEmployeeId(investorLeads.getAssignedTo());
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

//			List<AddressMapper> addressList = new ArrayList<AddressMapper>();
//			List<InvestorLeadsAddressLink> inventoryleadsAddressLink = investorLeadsAddressLinkRepository
//					.getAddressListByInvestorLeadsId(investorLeadsId);
//
//			if (null != inventoryleadsAddressLink && !inventoryleadsAddressLink.isEmpty()) {
//
//				for (InvestorLeadsAddressLink inventoryleadsAddressLink2 : inventoryleadsAddressLink) {
//					AddressDetails addressDetails = addressRepository
//							.getAddressDetailsByAddressId(inventoryleadsAddressLink2.getAddressId());
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
//								addressDetails.getCountry(), investorLeads.getOrganizationId());
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
	public InvestorLeadsViewMapper updateLeads(String investorleadsId, InvestorLeadsMapper leadsMapper)
			throws IOException, TemplateException {
		InvestorLeadsViewMapper resultMapper = null;

		InvestorLeads investorLeads = investorLeadsRepository.getInvestorLeadsByIdAndLiveInd(investorleadsId);

		if (null != investorLeads) {

			investorLeads.setLiveInd(false);
			investorLeadsRepository.save(investorLeads);

			InvestorLeads newinvestorLeads = new InvestorLeads();

			newinvestorLeads.setInvestorLeadsId(investorleadsId);

			if (null != leadsMapper.getName()) {
				newinvestorLeads.setName(leadsMapper.getName());
			} else {
				newinvestorLeads.setName(investorLeads.getName());
			}
			if (null != leadsMapper.getSalutation()) {
				newinvestorLeads.setSalutation(leadsMapper.getSalutation());
			} else {
				newinvestorLeads.setSalutation(investorLeads.getSalutation());
			}
			if (null != leadsMapper.getFirstName()) {
				newinvestorLeads.setFirstName(leadsMapper.getFirstName());
			} else {
				newinvestorLeads.setFirstName(investorLeads.getFirstName());
			}
			if (null != leadsMapper.getMiddleName()) {
				newinvestorLeads.setMiddleName(leadsMapper.getMiddleName());
			} else {
				newinvestorLeads.setMiddleName(investorLeads.getMiddleName());
			}
			if (null != leadsMapper.getLastName()) {
				newinvestorLeads.setLastName(leadsMapper.getLastName());
			} else {
				newinvestorLeads.setLastName(investorLeads.getLastName());
			}
			if (null != leadsMapper.getImageId()) {
				newinvestorLeads.setImageId(leadsMapper.getImageId());
			} else {
				newinvestorLeads.setImageId(investorLeads.getImageId());
			}

			if (null != leadsMapper.getShareCurrency()) {
				newinvestorLeads.setShareCurrency(leadsMapper.getShareCurrency());
			} else {
				newinvestorLeads.setShareCurrency(investorLeads.getShareCurrency());
			}

			if (null != leadsMapper.getFirstMeetingDate()) {
				try {
					newinvestorLeads
							.setFirstMeetingDate(Utility.getDateFromISOString(leadsMapper.getFirstMeetingDate()));
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				newinvestorLeads.setFirstMeetingDate(investorLeads.getFirstMeetingDate());
			}

			if (null != leadsMapper.getUrl()) {
				newinvestorLeads.setUrl(leadsMapper.getUrl());
			} else {
				newinvestorLeads.setUrl(investorLeads.getUrl());
			}

			if (0 != leadsMapper.getUnitOfShare()) {
				newinvestorLeads.setUnitOfShare(leadsMapper.getUnitOfShare());
			} else {
				newinvestorLeads.setUnitOfShare(investorLeads.getUnitOfShare());
			}

			if (null != leadsMapper.getType()) {
				newinvestorLeads.setType(leadsMapper.getType());
			} else {
				newinvestorLeads.setType(investorLeads.getType());
			}

			if (0 != leadsMapper.getValueOfShare()) {
				newinvestorLeads.setValueOfShare(leadsMapper.getValueOfShare());
			} else {
				newinvestorLeads.setValueOfShare(investorLeads.getValueOfShare());
			}

			newinvestorLeads.setPvtAndIntunlInd(leadsMapper.isPvtAndIntunlInd());

			if (null != leadsMapper.getNotes()) {
				List<InvestorLeadsNotesLink> list = investorLeadsNotesLinkRepository
						.getNotesIdByInvestorLeadsId(investorleadsId);
				if (null != list && !list.isEmpty()) {
					list.sort((m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
					Notes notes = notesRepository.findByNoteId(list.get(0).getNoteId());
					if (null != notes) {
						notes.setNotes(leadsMapper.getNotes());
						notesRepository.save(notes);
					}
				}
			}

			if (null != leadsMapper.getPhoneNumber()) {

				newinvestorLeads.setPhoneNumber(leadsMapper.getPhoneNumber());
			} else {
				newinvestorLeads.setPhoneNumber(investorLeads.getPhoneNumber());

			}

			if (null != leadsMapper.getCountryDialCode()) {

				newinvestorLeads.setCountryDialCode(leadsMapper.getCountryDialCode());
			} else {
				newinvestorLeads.setCountryDialCode(investorLeads.getCountryDialCode());

			}

			if (null != leadsMapper.getCountry()) {

				newinvestorLeads.setCountry(leadsMapper.getCountry());
			} else {
				newinvestorLeads.setCountry(investorLeads.getCountry());

			}

			if (null != leadsMapper.getSectorId()) {

				newinvestorLeads.setSector(leadsMapper.getSectorId());
			} else {
				newinvestorLeads.setSector(investorLeads.getSector());

			}

			if (null != leadsMapper.getZipcode()) {

				newinvestorLeads.setZipcode(leadsMapper.getZipcode());
			} else {
				newinvestorLeads.setZipcode(investorLeads.getZipcode());

			}

			if (null != leadsMapper.getEmail()) {

				newinvestorLeads.setEmail(leadsMapper.getEmail());
			} else {
				newinvestorLeads.setEmail(investorLeads.getEmail());

			}
			if (null != leadsMapper.getVatNo()) {

				newinvestorLeads.setVatNo(leadsMapper.getVatNo());
			} else {
				newinvestorLeads.setVatNo(investorLeads.getVatNo());

			}
			newinvestorLeads.setUserId(investorLeads.getUserId());
			newinvestorLeads.setOrganizationId(investorLeads.getOrganizationId());

			if (null != leadsMapper.getDocumentId()) {

				newinvestorLeads.setDocumentId(leadsMapper.getDocumentId());
			} else {
				newinvestorLeads.setDocumentId(investorLeads.getDocumentId());

			}

			if (null != leadsMapper.getCategory()) {

				newinvestorLeads.setCategory(leadsMapper.getCategory());
			} else {
				newinvestorLeads.setCategory(investorLeads.getCategory());

			}
			if (null != leadsMapper.getAssignedTo()) {

				newinvestorLeads.setAssignedTo(leadsMapper.getAssignedTo());
			} else {
				newinvestorLeads.setAssignedTo(investorLeads.getAssignedTo());

			}
			if (null != leadsMapper.getBusinessRegistration()) {

				newinvestorLeads.setBusinessRegistration(leadsMapper.getBusinessRegistration());
			} else {
				newinvestorLeads.setBusinessRegistration(investorLeads.getBusinessRegistration());

			}
			if (null != leadsMapper.getCompanyName()) {

				newinvestorLeads.setCompanyName(leadsMapper.getCompanyName());
			} else {
				newinvestorLeads.setCompanyName(investorLeads.getCompanyName());

			}
			if (null != leadsMapper.getSource()) {

				newinvestorLeads.setSource(leadsMapper.getSource());
			} else {
				newinvestorLeads.setSource(investorLeads.getSource());

			}
			if (null != leadsMapper.getAssignedTo()
					&& !leadsMapper.getAssignedTo().equals(investorLeads.getAssignedby())) {

				newinvestorLeads.setAssignedTo(leadsMapper.getUserId());
			} else {
				newinvestorLeads.setAssignedTo(investorLeads.getAssignedTo());

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
						satutation1 = investorLeads.getSalutation();
						newinvestorLeads.setName(
								satutation1 + " " + leadsMapper.getFirstName() + " " + middleName + " " + lastName);
					} else {

						newinvestorLeads.setName(leadsMapper.getFirstName() + " " + middleName + " " + lastName);
					}
				} else {
					if (investorLeads.getSalutation() != null && investorLeads.getSalutation().length() > 0) {
						satutation1 = investorLeads.getSalutation();
						newinvestorLeads.setName(satutation1 + " " + leadsMapper.getFirstName() + " " + lastName);
					} else {

						newinvestorLeads.setName(leadsMapper.getFirstName() + " " + lastName);
					}
					newinvestorLeads.setName(leadsMapper.getFirstName() + " " + lastName);
				}
			} else {
				newinvestorLeads.setName(investorLeads.getName());
			}

			// customerMapper.setCreationDate(Utility.getISOFromDate(customer.getCreation_date()))

			newinvestorLeads.setCreationDate(new Date());
			newinvestorLeads.setLiveInd(true);
			newinvestorLeads.setConvertInd(false);

			InvestorLeads updatedInvestorLeads = investorLeadsRepository.save(newinvestorLeads);

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

							InvestorLeadsAddressLink investorleadsAddressLink = new InvestorLeadsAddressLink();
							investorleadsAddressLink.setInvestorLeadsId(investorleadsId);
							investorleadsAddressLink.setAddressId(addressId);
							investorleadsAddressLink.setCreationDate(new Date());

							investorLeadsAddressLinkRepository.save(investorleadsAddressLink);

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
			contactMapper.setInvestorLeadsId(leadsMapper.getInvestorLeadsId());
			contactMapper.setCountryDialCode(leadsMapper.getCountryDialCode());
			contactMapper.setCountryDialCode1(leadsMapper.getCountryDialCode());
			contactMapper.setImageId(leadsMapper.getImageId());
			contactMapper.setAddress(leadsMapper.getAddress());
			contactMapper.setUserId(leadsMapper.getUserId());
			contactMapper.setOrganizationId(leadsMapper.getOrgId());
			contactMapper.setSectorId(leadsMapper.getSector());
			InvestorLeadsContactLink leadsContactLinkList = investorLeadsContactLinkRepository
					.getContactByInvestorLeadsId(investorleadsId);
			if (null != leadsContactLinkList) {

				ContactViewMapper mapper = contactService.updateContact(leadsContactLinkList.getContactId(),
						contactMapper);
				if (null != mapper) {
					leadsContactLinkList.setInvestorLeadsId(investorleadsId);
					leadsContactLinkList.setContactId(mapper.getContactId());
					leadsContactLinkList.setCreationDate(new Date());

					investorLeadsContactLinkRepository.save(leadsContactLinkList);
				}
			}

			/* insert to Notification Table */
			Notificationparam param = new Notificationparam();
			EmployeeDetails emp = employeeRepository.getEmployeesByuserId(leadsMapper.getUserId());
			String name = employeeService.getEmployeeFullNameByObject(emp);
			param.setEmployeeDetails(emp);
			param.setAdminMsg("Pitch " + "'" + leadsMapper.getName() + "' updated by " + name);
			param.setOwnMsg("Pitch " + leadsMapper.getName() + " updated.");
			param.setNotificationType("Pitch update");
			param.setProcessNmae("Pitch");
			param.setType("update");
			param.setEmailSubject("Korero alert- Pitch updated");
			param.setCompanyName(companyName);
			param.setUserId(leadsMapper.getUserId());

			if (leadsMapper.getUserId().equals(investorLeads.getAssignedTo())) {
				List<String> assignToUserIds = new ArrayList<>();
				assignToUserIds.add(leadsMapper.getAssignedTo());
				param.setAssignToUserIds(assignToUserIds);
				param.setAssignToMsg("Pitch " + "'" + leadsMapper.getName() + "' updated by " + name);
			} else {
				List<String> assignToUserIds = new ArrayList<>();
				assignToUserIds.add(leadsMapper.getAssignedTo());
				param.setAssignToUserIds(assignToUserIds);
				param.setAssignToMsg("Pitch " + "'" + leadsMapper.getName() + "'" + " assigned to "
						+ employeeService.getEmployeeFullName(leadsMapper.getAssignedTo()) + " by " + name);
			}
			notificationService.createNotificationForDynamicUsers(param);

			resultMapper = getInvestorLeadsDetailsById(updatedInvestorLeads.getInvestorLeadsId());
		}
		return resultMapper;
	}

	@Override
	public void deleteInvestorLeads(String investorleadsId, String userId, String orgId)
			throws IOException, TemplateException {
		if (null != investorleadsId) {
			InvestorLeads investorleads = investorLeadsRepository.getInvestorLeadsByIdAndLiveInd(investorleadsId);

			investorleads.setLiveInd(false);
			investorLeadsRepository.save(investorleads);

			Notificationparam param = new Notificationparam();
			EmployeeDetails emp = employeeRepository.getEmployeesByuserId(investorleads.getUserId());
			String name = employeeService.getEmployeeFullNameByObject(emp);
			param.setEmployeeDetails(emp);
			param.setAdminMsg("Pitch " + "'" + investorleads.getName() + "' deleted by " + name);
			param.setOwnMsg("Pitch " + investorleads.getName() + " deleted.");
			param.setNotificationType("Pitch delete");
			param.setProcessNmae("Pitch");
			param.setType("delete");
			param.setEmailSubject("Korero alert- Pitch deleted");
			param.setCompanyName(companyName);
			param.setUserId(investorleads.getUserId());

			if (investorleads.getUserId().equals(investorleads.getAssignedTo())) {
				List<String> assignToUserIds = new ArrayList<>();
				assignToUserIds.add(investorleads.getAssignedTo());
				param.setAssignToUserIds(assignToUserIds);
				param.setAssignToMsg("Pitch " + "'" + investorleads.getName() + "' deleted by " + name);
			}
			notificationService.createNotificationForDynamicUsers(param);
		}

	}

	@Override
	public List<InvestorLeadsViewMapper> getInvestorLeadsDetailsByUserId(String userId, int pageNo, int pageSize,
			String filter) {
		Pageable paging = null;
		if (filter.equalsIgnoreCase("creationdate")) {
			paging = PageRequest.of(pageNo, pageSize, Sort.by("creationDate").descending());

		} else if (filter.equalsIgnoreCase("ascending")) {
			paging = PageRequest.of(pageNo, pageSize, Sort.by("name"));

		} else if (filter.equalsIgnoreCase("descending")) {
			paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "name"));
		}
		Page<InvestorLeads> investorleadsList = investorLeadsRepository.getInvestorLeadsListByUserIdAndPagging(userId,
				paging);
		List<InvestorLeadsViewMapper> resultMapper = new ArrayList<>();
		if (null != investorleadsList && !investorleadsList.isEmpty()) {
			resultMapper = investorleadsList.stream().map(li -> {
				InvestorLeadsViewMapper mapper = getInvestorLeadsDetailsById(li.getInvestorLeadsId());
				mapper.setPageCount(investorleadsList.getTotalPages());
				mapper.setDataCount(investorleadsList.getSize());
				mapper.setListCount(investorleadsList.getTotalElements());
				return mapper;
			}).collect(Collectors.toList());

		}
		return resultMapper;
	}

	@Override
	public List<ContactViewMapper> getContactListByInvestorLeadsId(String investorLeadsId) {
		List<InvestorLeadsContactLink> investorLeadsContactLinkList = investorLeadsContactLinkRepository
				.getContactIdByInvestorLeadsId(investorLeadsId);
		if (investorLeadsContactLinkList != null && !investorLeadsContactLinkList.isEmpty()) {
			return investorLeadsContactLinkList.stream().map(investorLeadsContactLink -> {
				ContactViewMapper contactMapper = contactService
						.getContactDetailsById(investorLeadsContactLink.getContactId());

				return contactMapper;
			}).collect(Collectors.toList());

		}

		return null;
	}

	@Override
	public List<DocumentMapper> getDocumentListByInvestorLeadsId(String investorLeadsId) {
		List<DocumentMapper> resultList = new ArrayList<>();
		List<InvestorLeadsDocumentLink> investorLeadsDocumentLinklist = investorLeadsDocumentLinkRepository
				.getDocumentByInvestorLeadsId(investorLeadsId);
		Set<String> documentIds = investorLeadsDocumentLinklist.stream().map(InvestorLeadsDocumentLink::getDocumentId)
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
	public InvestorLeadsOpportunityMapper saveInvestorLeadsOpportunity(
			InvestorLeadsOpportunityMapper investorLeadsOpportunityMapper) {

		InvestorLeadsOpportunityLink investorLeadsOpportunityLink = new InvestorLeadsOpportunityLink();
		investorLeadsOpportunityLink.setInvestorLeadsId(investorLeadsOpportunityMapper.getInvestorLeadsId());
		investorLeadsOpportunityLink.setOpportunityName(investorLeadsOpportunityMapper.getOpportunityName());
		investorLeadsOpportunityLink.setProposalValue(investorLeadsOpportunityMapper.getProposalAmount());
		investorLeadsOpportunityLink.setCreationDate(new Date());
		investorLeadsOpportunityLink.setLiveInd(true);
		investorLeadsOpportunityLink.setUserId(investorLeadsOpportunityMapper.getUserId());
		investorLeadsOpportunityLink.setContactId(investorLeadsOpportunityMapper.getContactId());
		investorLeadsOpportunityLink.setAssignedTo(investorLeadsOpportunityMapper.getAssignedTo());
		investorLeadsOpportunityLink.setOrgId(investorLeadsOpportunityMapper.getOrgId());
		try {
			investorLeadsOpportunityLink.setStartDate(
					Utility.removeTime(Utility.getDateFromISOString(investorLeadsOpportunityMapper.getStartDate())));
			investorLeadsOpportunityLink.setEndDate(
					Utility.removeTime(Utility.getDateFromISOString(investorLeadsOpportunityMapper.getEndDate())));
		} catch (Exception e) {
			e.printStackTrace();
		}

		investorLeadsOpportunityLink.setCurrency(investorLeadsOpportunityMapper.getCurrency());
		investorLeadsOpportunityLink.setDescription(investorLeadsOpportunityMapper.getDescription());
		investorLeadsOpportunityLink.setOppStage(investorLeadsOpportunityMapper.getOppStage());
		investorLeadsOpportunityLink.setOppWorkflow(investorLeadsOpportunityMapper.getOppWorkflow());
		InvestorLeadsOpportunityLink investorLeadsOpportunityLink1 = investorLeadsOpportunityLinkRepository
				.save(investorLeadsOpportunityLink);

		InvestorLeadsOpportunityMapper resultMapper = getInvestorLeadsOpportunityById(
				investorLeadsOpportunityLink1.getInvestorLeadOppId());

		return resultMapper;
	}

	public InvestorLeadsOpportunityMapper getInvestorLeadsOpportunityById(String investorleadOppId) {
		InvestorLeadsOpportunityMapper investorLeadsOpportunityMapper = new InvestorLeadsOpportunityMapper();
		InvestorLeadsOpportunityLink investorLeadsOpportunityLink = investorLeadsOpportunityLinkRepository
				.getInvestorLeadsOpportunityDetailsByInvestorLeadsOppId(investorleadOppId);
		if (null != investorLeadsOpportunityLink) {
			investorLeadsOpportunityMapper.setInvestorLeadOppId(investorleadOppId);
			investorLeadsOpportunityMapper.setOpportunityName(investorLeadsOpportunityLink.getOpportunityName());
			investorLeadsOpportunityMapper.setProposalAmount(investorLeadsOpportunityLink.getProposalValue());
			investorLeadsOpportunityMapper
					.setStartDate(Utility.getISOFromDate(investorLeadsOpportunityLink.getStartDate()));
			investorLeadsOpportunityMapper
					.setEndDate(Utility.getISOFromDate(investorLeadsOpportunityLink.getEndDate()));
			investorLeadsOpportunityMapper
					.setCreationDate(Utility.getISOFromDate(investorLeadsOpportunityLink.getCreationDate()));
			investorLeadsOpportunityMapper.setUserId(investorLeadsOpportunityLink.getUserId());
			investorLeadsOpportunityMapper.setContactId(investorLeadsOpportunityLink.getContactId());
			investorLeadsOpportunityMapper.setAssignedTo(investorLeadsOpportunityLink.getAssignedTo());
			investorLeadsOpportunityMapper.setOrgId(investorLeadsOpportunityLink.getOrgId());
			investorLeadsOpportunityMapper.setCurrency(investorLeadsOpportunityLink.getCurrency());
			investorLeadsOpportunityMapper.setDescription(investorLeadsOpportunityLink.getDescription());
			investorLeadsOpportunityMapper.setInvestorLeadsId(investorLeadsOpportunityLink.getInvestorLeadsId());

			InvestorLeads leads = investorLeadsRepository
					.getInvestorLeadsByIdAndLiveInd(investorLeadsOpportunityLink.getInvestorLeadsId());
			if (null != leads) {
				investorLeadsOpportunityMapper.setInvestorLeadsName(leads.getCompanyName());
			}

			OpportunityWorkflowDetails workflowDetails = opportunityWorkflowDetailsRepository
					.getOpportunityWorkflowDetailsByOpportunityWorkflowDetailsId(
							investorLeadsOpportunityLink.getOppWorkflow());
			if (null != workflowDetails) {
				investorLeadsOpportunityMapper.setOppWorkflow(workflowDetails.getWorkflowName());
			}

			OpportunityStages oppStages = opportunityStagesRepository
					.getOpportunityStagesByOpportunityStagesId(investorLeadsOpportunityLink.getOppStage());
			if (null != oppStages) {
				investorLeadsOpportunityMapper.setOppStage(oppStages.getStageName());
			}
			if (investorLeadsOpportunityLink.getContactId() != null
					&& investorLeadsOpportunityLink.getContactId().trim().length() > 0) {
				ContactDetails contact = contactRepository
						.getContactDetailsById(investorLeadsOpportunityLink.getContactId());
				if (null != contact) {
					String middleName = " ";
					String lastName = "";

					if (!StringUtils.isEmpty(contact.getLast_name())) {

						lastName = contact.getLast_name();
					}
					if (!StringUtils.isEmpty(contact.getMiddle_name())) {
						middleName = contact.getMiddle_name();

					}
					investorLeadsOpportunityMapper.setContactId(contact.getContactId());
					investorLeadsOpportunityMapper
							.setContactName(contact.getFirst_name() + " " + middleName + " " + lastName);

				}
			}

			investorLeadsOpportunityMapper.setAssignedToName(
					employeeService.getEmployeeFullName(investorLeadsOpportunityLink.getAssignedTo()));
			investorLeadsOpportunityMapper
					.setOwner(employeeService.getEmployeeFullName(investorLeadsOpportunityLink.getUserId()));

		}

		return investorLeadsOpportunityMapper;

	}

	@Override
	public List<InvestorLeadsOpportunityMapper> getOpportunityListByInvestorLeadsId(String investorLeadsId) {
		List<InvestorLeadsOpportunityMapper> resultMapper = new ArrayList<>();
		List<InvestorLeadsOpportunityLink> investorLeadsOpportunityLinkList = investorLeadsOpportunityLinkRepository
				.getOpportunityListByInvestorLeadsId(investorLeadsId);
		System.out.println("investorLeadsOpportunityLinkList size===" + investorLeadsOpportunityLinkList.size());
		if (investorLeadsOpportunityLinkList != null && !investorLeadsOpportunityLinkList.isEmpty()) {
			for (InvestorLeadsOpportunityLink investorLeadsOpportunityLink : investorLeadsOpportunityLinkList) {
				InvestorLeadsOpportunityMapper investorLeadsOpportunityMapper = getInvestorLeadsOpportunityById(
						investorLeadsOpportunityLink.getInvestorLeadOppId());
				if (null != investorLeadsOpportunityMapper.getInvestorLeadsId()) {
					resultMapper.add(investorLeadsOpportunityMapper);
				}
			}
		}

		return resultMapper;
	}

	@Override
	public InvestorLeadsOpportunityMapper updateInvestorLeadsOpportunity(String investorLeadsOppId,
			InvestorLeadsOpportunityMapper investorLeadsOpportunityMapper) {
		InvestorLeadsOpportunityMapper resultMapper = new InvestorLeadsOpportunityMapper();
		InvestorLeadsOpportunityLink newOpportunity = investorLeadsOpportunityLinkRepository
				.getById(investorLeadsOppId);
		if (null != newOpportunity) {
			if (null != investorLeadsOpportunityMapper.getOpportunityName()) {
				newOpportunity.setOpportunityName(investorLeadsOpportunityMapper.getOpportunityName());
			}
			if (null != investorLeadsOpportunityMapper.getProposalAmount()) {
				newOpportunity.setProposalValue(investorLeadsOpportunityMapper.getProposalAmount());
			}
			if (null != investorLeadsOpportunityMapper.getUserId()) {
				newOpportunity.setUserId(investorLeadsOpportunityMapper.getUserId());
			}
			if (null != investorLeadsOpportunityMapper.getContactId()) {
				newOpportunity.setContactId(investorLeadsOpportunityMapper.getContactId());
			}
			if (null != investorLeadsOpportunityMapper.getOrgId()) {
				newOpportunity.setOrgId(investorLeadsOpportunityMapper.getOrgId());
			}
			if (null != investorLeadsOpportunityMapper.getCurrency()) {
				newOpportunity.setCurrency(investorLeadsOpportunityMapper.getCurrency());
			}
			if (null != investorLeadsOpportunityMapper.getInvestorLeadsId()) {
				newOpportunity.setInvestorLeadsId(investorLeadsOpportunityMapper.getInvestorLeadsId());
			}
			if (null != investorLeadsOpportunityMapper.getDescription()) {
				newOpportunity.setDescription(investorLeadsOpportunityMapper.getDescription());
			}
			if (null != investorLeadsOpportunityMapper.getAssignedTo()) {
				newOpportunity.setAssignedTo(investorLeadsOpportunityMapper.getAssignedTo());
			}
			if (null != investorLeadsOpportunityMapper.getOppStage()) {
				newOpportunity.setOppStage(investorLeadsOpportunityMapper.getOppStage());
			}
			if (null != investorLeadsOpportunityMapper.getOppWorkflow()) {
				newOpportunity.setOppWorkflow(investorLeadsOpportunityMapper.getOppWorkflow());
			}

			try {
				if (null != investorLeadsOpportunityMapper.getStartDate()) {
					newOpportunity.setStartDate(Utility
							.removeTime(Utility.getDateFromISOString(investorLeadsOpportunityMapper.getStartDate())));
				}
				if (null != investorLeadsOpportunityMapper.getEndDate()) {
					newOpportunity.setEndDate(Utility
							.removeTime(Utility.getDateFromISOString(investorLeadsOpportunityMapper.getEndDate())));
				}
			} catch (Exception e) {
				// TODO Auto-generated catch blockemployeeCallRepository
				e.printStackTrace();
			}

			InvestorLeadsOpportunityLink newOpportunity1 = investorLeadsOpportunityLinkRepository.save(newOpportunity);

			resultMapper = getInvestorLeadsOpportunityById(newOpportunity1.getInvestorLeadOppId());
		}
		return resultMapper;
	}

	@Override
	public void deleteLeadsOppertunity(String investorLeadOppId) {

		InvestorLeadsOpportunityLink investorLeadsOpportunityLink = investorLeadsOpportunityLinkRepository
				.getInvestorLeadsOpportunityDetailsByInvestorLeadsOppId(investorLeadOppId);
		System.out.println(investorLeadsOpportunityLink);
		if (null != investorLeadsOpportunityLink) {
			investorLeadsOpportunityLink.setLiveInd(false);
			investorLeadsOpportunityLinkRepository.save(investorLeadsOpportunityLink);

		}

	}

	@Override
	public String updateInvestorLeadsType(String investorLeadsId, String type) {
		String msg = null;
		InvestorLeads investorLeads = investorLeadsRepository.getInvestorLeadsByIdAndLiveInd(investorLeadsId);
		if (null != investorLeads) {
			investorLeads.setType(type);
			investorLeads.setTypeUpdationDate(new Date());
			investorLeadsRepository.save(investorLeads);
			msg = "Lead Changed Successfuly";
		} else {
			msg = "Something Went Wrong";
		}
		return msg;

	}

	@Override
	public String convertInvestorLeadsById(String investorLeadsId, String assignedToId)
			throws TemplateException, IOException {

		if (null != investorLeadsId) {
			InvestorLeads investorLeads = investorLeadsRepository.getInvestorLeadsByIdAndLiveInd(investorLeadsId);
			if (null != investorLeads) {
				investorLeads.setConvertInd(true);
				investorLeads.setConvertionDate(new Date());
				investorLeadsRepository.save(investorLeads);

				Investor investor = new Investor();
				String investorId = investorRepository.save(investor).getInvestorId();

				if (null != investorId) {
					Investor dbinvestor = new Investor();
					dbinvestor.setInvestorId(investorId);
					dbinvestor.setName(investorLeads.getCompanyName());
					dbinvestor.setUrl(investorLeads.getUrl());
					dbinvestor.setNotes(investorLeads.getNotes());
					dbinvestor.setCountryDialCode(investorLeads.getCountryDialCode());
					dbinvestor.setPhoneNumber(investorLeads.getPhoneNumber());
					dbinvestor.setEmail(investorLeads.getEmail());
					dbinvestor.setUserId(investorLeads.getUserId());
					dbinvestor.setOrganizationId(investorLeads.getOrganizationId());
					dbinvestor.setGroup(investorLeads.getGroup());
					dbinvestor.setVatNo(investorLeads.getVatNo());
					dbinvestor.setCreationDate(new Date());
					dbinvestor.setLiveInd(true);
					dbinvestor.setCountry(investorLeads.getCountry());
					dbinvestor.setZipcode(investorLeads.getZipcode());
					dbinvestor.setZipcode(investorLeads.getZipcode());
					dbinvestor.setCategory(investorLeads.getCategory());
					dbinvestor.setImageURL(investorLeads.getImageURL());
					dbinvestor.setAssignedTo(assignedToId);
					dbinvestor.setSector(investorLeads.getSector());
					dbinvestor.setBusinessRegistration(investorLeads.getBusinessRegistration());
					dbinvestor.setFirstMeetingDate(investorLeads.getFirstMeetingDate());

					investorRepository.save(dbinvestor);
					// Investor address Link
					List<InvestorLeadsAddressLink> investorLeadsAddressLinkList = investorLeadsAddressLinkRepository
							.getAddressListByInvestorLeadsId(investorLeadsId);
					if (investorLeadsAddressLinkList != null && !investorLeadsAddressLinkList.isEmpty()) {
						for (InvestorLeadsAddressLink investorLeadsAddressLink : investorLeadsAddressLinkList) {
							InvestorAddressLink investorAddressLink = new InvestorAddressLink();
							investorAddressLink.setAddressId(investorLeadsAddressLink.getAddressId());
							investorAddressLink.setInvestorId(investorId);
							investorAddressLink.setCreationDate(new Date());
							investorAddressLinkRepository.save(investorAddressLink);
						}
					}
					// Investor Document Link
					List<InvestorLeadsDocumentLink> investorleadsDocumentLinkList = investorLeadsDocumentLinkRepository
							.getDocumentByInvestorLeadsId(investorLeadsId);
					if (investorleadsDocumentLinkList != null && !investorleadsDocumentLinkList.isEmpty()) {
						for (InvestorLeadsDocumentLink investorLeadsDocumentLink : investorleadsDocumentLinkList) {
							InvestorDocumentLink investorDocumentLink = new InvestorDocumentLink();
							investorDocumentLink.setDocumentId(investorLeadsDocumentLink.getDocumentId());
							investorDocumentLink.setInvestorId(investorId);
							investorDocumentLinkRepository.save(investorDocumentLink);
						}
					}
					// Investor contact Link
					List<InvestorLeadsContactLink> investorLeadsContactLinkList = investorLeadsContactLinkRepository
							.getContactIdByInvestorLeadsId(investorLeadsId);
					if (investorLeadsContactLinkList != null && !investorLeadsContactLinkList.isEmpty()) {
						for (InvestorLeadsContactLink investorLeadsContactLink : investorLeadsContactLinkList) {

							ContactDetails contact = contactRepository
									.getContactDetailsById(investorLeadsContactLink.getContactId());
							if (null != contact) {
								contact.setContactType("Investor");
								contact.setTag_with_company(investorId);
								contact.setCreationDate(new Date());
								contactRepository.save(contact);
							}

							InvestorContactLink investorContactLink = new InvestorContactLink();
							investorContactLink.setContactId(investorLeadsContactLink.getContactId());
							investorContactLink.setInvestorId(investorId);
							investorContactLink.setCreationDate(new Date());
							investorContactLinkRepository.save(investorContactLink);

							// Contact notes Link
							List<InvestorLeadsNotesLink> investorleadsNotesLinkList = investorLeadsNotesLinkRepository
									.getNotesIdByInvestorLeadsId(investorLeadsId);
							if (investorleadsNotesLinkList != null && !investorleadsNotesLinkList.isEmpty()) {
								for (InvestorLeadsNotesLink investorleadsNotesLink : investorleadsNotesLinkList) {
									ContactNotesLink contactNotesLink = new ContactNotesLink();
									contactNotesLink.setContact_id(investorLeadsContactLink.getContactId());
									contactNotesLink.setNotesId(investorleadsNotesLink.getNoteId());
									contactNotesLink.setCreation_date(new Date());

									contactNotesLinkRepository.save(contactNotesLink);
								}
							}
						}
					}

					// Investor Opportunity Link
					InvestorLeadsOpportunityLink investorLeadsOpportunityLink = investorLeadsOpportunityLinkRepository
							.getByInvestorLeadsId(investorLeadsId);
					if (investorLeadsOpportunityLink != null) {
						System.out.println("investorLeadsOpportunityLink.getInvestorLeadOppId()"
								+ investorLeadsOpportunityLink.getInvestorLeadOppId());
						InvestorLeadsOpportunityMapper investorLeadsOpportunityMapper = getInvestorLeadsOpportunityById(
								investorLeadsOpportunityLink.getInvestorLeadOppId());
						if (null != investorLeadsOpportunityMapper) {
							InvestorOpportunityMapper investorOpportunityMapper = addDataInInvestorOpportunityMapper(
									investorLeadsOpportunityMapper);
							investorOpportunityMapper.setInvestorId(investorId);
							if (null != investorOpportunityMapper) {
								InvestorOpportunityMapper resultMapper = investorOpportunityService
										.saveInvestorOpportunity(investorOpportunityMapper);
								if (null != resultMapper.getInvOpportunityId()) {
									investorLeadsOpportunityLink.setLiveInd(false);
									investorLeadsOpportunityLinkRepository.save(investorLeadsOpportunityLink);
								}
							}
						}
					}

					// Investor Call Link
					List<InvestorLeadsCallLink> investorLeadsCallLinkList = investorLeadsCallLinkRepository
							.getCallListByInvestorLeadsIdAndLiveInd(investorLeadsId);
					if (investorLeadsCallLinkList != null && !investorLeadsCallLinkList.isEmpty()) {
						for (InvestorLeadsCallLink investorLeadsCallLink : investorLeadsCallLinkList) {
							InvestorCallLink investorCallLink = new InvestorCallLink();
							investorCallLink.setCallId(investorLeadsCallLink.getCallId());
							investorCallLink.setInvestorId(investorId);
							investorCallLink.setCreationDate(new Date());
							investorCallLink.setLiveInd(true);
							investorCallLinkRepository.save(investorCallLink);
						}
					}

					// Investor Event Link
					List<InvestorLeadsEventLink> investorLeadsEventLinkList = investorLeadsEventLinkRepository
							.getByInvestorLeadsIdAndLiveInd(investorLeadsId);
					if (investorLeadsEventLinkList != null && !investorLeadsEventLinkList.isEmpty()) {
						for (InvestorLeadsEventLink investorLeadsEventLink : investorLeadsEventLinkList) {
							InvestorEventLink investorEventLink = new InvestorEventLink();
							investorEventLink.setEventId(investorLeadsEventLink.getEventId());
							investorEventLink.setInvestorId(investorId);
							investorEventLink.setCreationDate(new Date());
							investorEventLink.setLiveInd(true);
							investorEventLinkRepository.save(investorEventLink);
						}
					}

					// Investor Task Link
					List<InvestorLeadsTaskLink> leadsTaskLinkList = investorLeadsTaskLinkRepository
							.getTaskListByInvestorLeadsIdAndLiveInd(investorLeadsId);
					if (leadsTaskLinkList != null && !leadsTaskLinkList.isEmpty()) {
						for (InvestorLeadsTaskLink leadsTaskLink : leadsTaskLinkList) {
							InvestorTaskLink investorTaskLink = new InvestorTaskLink();
							investorTaskLink.setTaskId(leadsTaskLink.getTaskId());
							investorTaskLink.setInvestorId(investorId);
							investorTaskLink.setCreationDate(new Date());
							investorTaskLink.setLiveInd(true);
							investorTaskLinkRepository.save(investorTaskLink);
						}
					}

					InvestorsShare investorsShare = new InvestorsShare();

//					try {
//						investorsShare.setBuyingDate(Utility.getDateFromISOString(investorShareMapper.getBuyingDate()));
//					} catch (Exception e) {
//						e.printStackTrace();
//					}

					investorsShare.setCreationDate(new Date());
					investorsShare.setInvestorId(investorId);
					investorsShare.setOrgId(investorLeads.getOrganizationId());
					investorsShare.setAmountPerShare(investorLeads.getValueOfShare());
					System.out.println("investorLeads.getValueOfShare()=========" + investorLeads.getValueOfShare());
					investorsShare.setQuantityOfShare(investorLeads.getUnitOfShare());
					System.out.println("investorLeads.getUnitOfShare()=========" + investorLeads.getUnitOfShare());
					investorsShare
							.setTotalAmountOfShare(investorLeads.getUnitOfShare() * investorLeads.getValueOfShare());
					investorsShare.setUserId(investorLeads.getUserId());
					investorsShare.setCurrency(investorLeads.getShareCurrency());
					investorsShare.setLiveInd(true);
					investorShareRepo.save(investorsShare);

					Investor investor1 = investorRepository.getById(investorId);
					if (null != investor1) {

						double investorShareCount = 0;
						List<InvestorsShare> investorsShares = investorShareRepo.findByInvestorIdAndLiveInd(investorId,
								true);
						System.out.println("investorsShares.size()=========" + investorsShares.size());
						if (null != investorsShare && !investorsShares.isEmpty()) {
							for (InvestorsShare share : investorsShares) {
								investorShareCount = investorShareCount + share.getQuantityOfShare();
								System.out.println("investorShareCount0=========" + investorShareCount);
							}
						}

						List<Club> clubs = clubRepository.findAllByLiveIndTrueOrderByNoOfShareDesc();
						System.out.println("clubs.size()=========" + clubs.size());
						Club previousClub = null;
						if (null != clubs && !clubs.isEmpty()) {
							for (Club club : clubs) {
								System.out.println("investorShareCount1=========" + investorShareCount);
								System.out.println("club.getNoOfShare(=========" + club.getNoOfShare());

								if (investorShareCount >= club.getNoOfShare()) {
									previousClub = (previousClub != null ? previousClub : club);
									System.out.println("previousClub1=========" + previousClub.toString());
								}
								// previousClub = club;
							}
							System.out.println("previousClub2=========" + previousClub.toString());
						}
						if (null != previousClub) {
							System.out.println("previousClub.getClubId()=========" + previousClub.getClubId());
							System.out.println("previousClub.getClubName()=========" + previousClub.getClubName());
							investor1.setClub(previousClub.getClubId());
							investorRepository.save(investor1);
						}

						if (null != investor1.getClub() && !investor1.getClub().isEmpty()) {
							Club club = clubRepository.findByClubIdAndLiveIndAndInvToCusInd(investor.getClub(), true,
									true);
							if (null != club) {
								if (club.isInvToCusInd()) {
									Distributor distributor = new Distributor();
									distributor.setName(investor1.getName());
									// distributor.setImageId(distributorDTO.getImageId());
									distributor.setUrl(investor1.getUrl());
									distributor.setPhoneNo(investor1.getPhoneNumber());
									distributor.setDialCode(investor1.getCountryDialCode());
									distributor.setDescription(investor1.getNotes());
									distributor.setMobileNo(investor1.getPhoneNumber());
									distributor.setEmailId(investor1.getEmail());
									distributor.setImageURL(investor1.getImageURL());
									distributor.setCountryId(investor1.getCountry());
									if (investor1.getCountry() != null) {
										Country country = countryRepository.getByCountryId(investor1.getCountry());
										distributor.setCountryName(country.getCountryName());
									}
									distributor.setAssignTo(investor1.getAssignedTo());
									distributor.setUserId(investor1.getUserId());
									distributor.setOrgId(investor1.getOrganizationId());
									distributor.setCreateAt(new Date());
									distributor.setInvestor(investor1.getInvestorId());
									distributor.setClub(investor1.getClub());

									distributorRepository.save(distributor);

									List<InvestorAddressLink> investorAddressList = investorAddressLinkRepo
											.getAddressListByInvestorId(investor1.getInvestorId());

									/* fetch investor address & set to investor mapper */
									if (null != investorAddressList && !investorAddressList.isEmpty()) {

										for (InvestorAddressLink investorAddressLink : investorAddressList) {
											AddressDetails addressDetails1 = addressRepository
													.getAddressDetailsByAddressId(investorAddressLink.getAddressId());

											if (null != addressDetails1) {
												/* insert to address info & address deatils & customeraddressLink */

												AddressInfo addressInfo = new AddressInfo();
												addressInfo.setCreationDate(new Date());
												// addressInfo.setCreatorId(candidateMapperr.getUserId());
												AddressInfo addressInfoo = addressInfoRepository.save(addressInfo);

												String addressId = addressInfoo.getId();

												if (null != addressId) {

													AddressDetails addressDetails = new AddressDetails();
													addressDetails.setAddressId(addressId);
													addressDetails.setAddressLine1(addressDetails1.getAddressLine1());
													addressDetails.setAddressLine2(addressDetails1.getAddressLine2());
													addressDetails.setAddressType(addressDetails1.getAddressType());
													addressDetails.setCountry(addressDetails1.getCountry());
													addressDetails.setCreationDate(new Date());
													addressDetails.setStreet(addressDetails1.getStreet());
													addressDetails.setCity(addressDetails1.getCity());
													addressDetails.setPostalCode(addressDetails1.getPostalCode());
													addressDetails.setTown(addressDetails1.getTown());
													addressDetails.setState(addressDetails1.getState());
													addressDetails.setLatitude(addressDetails1.getLatitude());
													addressDetails.setLongitude(addressDetails1.getLongitude());
													addressDetails.setLiveInd(true);
													addressDetails.setHouseNo(addressDetails1.getHouseNo());
													addressRepository.save(addressDetails);

													DistributorAddressLink distributorAddressLink = new DistributorAddressLink();
													distributorAddressLink.setDistributorId(distributor.getId());
													distributorAddressLink.setAddressId(addressId);
													distributorAddressLink.setCreationDate(new Date());

													distributorAddressLinkRepository.save(distributorAddressLink);

												}
											}
										}
									}

									List<InvestorContactLink> investorContactLinkList = investorContactLinkRepo
											.getContactIdByInvestorId(investor1.getInvestorId());
									if (investorContactLinkList != null && !investorContactLinkList.isEmpty()) {
										for (InvestorContactLink investorContact : investorContactLinkList) {
											ContactDetails contact1 = contactRepository
													.getContactDetailsById(investorContact.getContactId());
											if (null != contact1) {
												String contactId = null;

												ContactInfo contactInfo = new ContactInfo();
												contactInfo.setCreation_date(new Date());

												ContactInfo contactt = contactInfoRepository.save(contactInfo);

												contactId = contactt.getContact_id();
												System.out.println("contact id.........." + contactId);

												ContactDetails contact = new ContactDetails();

												contact.setContactId(contactId);
												contact.setSalutation(contact1.getSalutation());
												contact.setFirst_name(contact1.getFirst_name());
												contact.setMiddle_name(contact1.getMiddle_name());
												contact.setLast_name(contact1.getLast_name());
												contact.setMobile_number(contact1.getMobile_number());
												contact.setPhone_number(contact1.getPhone_number());
												contact.setEmail_id(contact1.getEmail_id());
												contact.setLinkedin_public_url(contact1.getLinkedin_public_url());
												// contact.setTag_with_company(contactMapperr.getTagWithCompany());
												contact.setDepartment(contact1.getDepartment());
												contact.setDesignation(contact1.getDesignation());
												contact.setSector(contact1.getSector());
												contact.setSalary(contact1.getSalary());
												contact.setNotes(contact1.getNotes());
												contact.setCountry_dialcode(contact1.getCountry_dialcode());
												contact.setCountry_dialcode1(contact1.getCountry_dialcode1());
												contact.setImageId(contact1.getImageId());
												contact.setCreationDate(new Date());
												contact.setContactId(contactId);
												contact.setAccessInd(0);
												contact.setWhatsappNumber(contact1.getWhatsappNumber());
												contact.setSource(contact1.getSource());
												contact.setSourceUserId(contact1.getSourceUserId());
												contact.setBedroom(contact1.getBedroom());
												contact.setPrice(contact1.getPrice());
												contact.setPropertyType(contact1.getPropertyType());
												contact.setLiveInd(true);
												contact.setUser_id(contact1.getUser_id());
												contact.setOrganization_id(contact1.getOrganization_id());

												String middleName3 = " ";
												String lastName3 = "";

												if (!StringUtils.isEmpty(contact1.getLast_name())) {

													lastName3 = contact1.getLast_name();
												}
												if (contact1.getMiddle_name() != null
														&& contact1.getMiddle_name().length() > 0) {

													middleName3 = contact1.getMiddle_name();
													contact.setFullName(contact1.getFirst_name() + " " + middleName3
															+ " " + lastName3);
												} else {

													contact.setFullName(contact1.getFirst_name() + " " + lastName3);
												}

												contact.setContactType("Distributor");
												contact.setTag_with_company(distributor.getId());
												contactRepository.save(contact);

												DistributorContactPersonLink distributorContactLink = new DistributorContactPersonLink();
												distributorContactLink.setContactId(contactId);
												distributorContactLink.setDistributorId(distributor.getId());
												distributorContactPersonLinkRepository.save(distributorContactLink);

												List<ContactAddressLink> contactAddressList = contactAddressLinkRepository
														.getAddressListByContactId(contactId);
												if (null != contactAddressList && !contactAddressList.isEmpty()) {
													for (ContactAddressLink addressMapper : contactAddressList) {
														AddressDetails addressDetails1 = addressRepository
																.getAddressDetailsByAddressId(
																		addressMapper.getAddress_id());

														if (null != addressDetails1) {
															/*
															 * insert to address info & address deatils &
															 * customeraddressLink
															 */

															AddressInfo addressInfo = new AddressInfo();
															addressInfo.setCreationDate(new Date());
															// addressInfo.setCreatorId(candidateMapperr.getUserId());
															AddressInfo addressInfoo = addressInfoRepository
																	.save(addressInfo);

															String addressId = addressInfoo.getId();

															if (null != addressId) {

																AddressDetails addressDetails = new AddressDetails();
																addressDetails.setAddressId(addressId);
																addressDetails.setAddressLine1(
																		addressDetails1.getAddressLine1());
																addressDetails.setAddressLine2(
																		addressDetails1.getAddressLine2());
																addressDetails.setAddressType(
																		addressDetails1.getAddressType());
																addressDetails.setCountry(addressDetails1.getCountry());
																addressDetails.setCreationDate(new Date());
																addressDetails.setStreet(addressDetails1.getStreet());
																addressDetails.setCity(addressDetails1.getCity());
																addressDetails
																		.setPostalCode(addressDetails1.getPostalCode());
																addressDetails.setTown(addressDetails1.getTown());
																addressDetails.setState(addressDetails1.getState());
																addressDetails
																		.setLatitude(addressDetails1.getLatitude());
																addressDetails
																		.setLongitude(addressDetails1.getLongitude());
																addressDetails.setLiveInd(true);
																addressDetails.setHouseNo(addressDetails1.getHouseNo());
																addressRepository.save(addressDetails);

																ContactAddressLink contactAddressLink = new ContactAddressLink();
																contactAddressLink.setContact_id(contactId);
																contactAddressLink.setAddress_id(addressId);
																contactAddressLink.setCreation_date(new Date());
																contactAddressLinkRepository.save(contactAddressLink);

															}
														}
													}
												}

												ContactDetails dbContact = contactRepository.save(contact);

												System.out.println("contactdetails id.........."
														+ dbContact.getContact_details_id());

												/* insert to notification table */
												Notificationparam param = new Notificationparam();
												EmployeeDetails emp = employeeRepository
														.getEmployeesByuserId(contact1.getUser_id());
												String name = employeeService.getEmployeeFullNameByObject(emp);
												param.setEmployeeDetails(emp);
												param.setAdminMsg("Contact " + "'"
														+ Utility.FullName(contact1.getFirst_name(),
																contact1.getMiddle_name(), contact1.getLast_name())
														+ "' created by " + name);
												param.setOwnMsg("Contact "
														+ Utility.FullName(contact1.getFirst_name(),
																contact1.getMiddle_name(), contact1.getLast_name())
														+ " created.");
												param.setNotificationType("Contact creation");
												param.setProcessNmae("Contact");
												param.setType("create");
												param.setEmailSubject("Korero alert- Contact created");
												param.setCompanyName(companyName);
												param.setUserId(contact1.getUser_id());
												notificationService.createNotificationForDynamicUsers(param);
											}
										}
									}

									TodayDistributor distribitorToday = todayDistributorRepository
											.findByCreateAt(Utility.removeTime(new Date()));
									if (distribitorToday != null) {
										distribitorToday
												.setDistributorCount(distribitorToday.getDistributorCount() + 1);
										distribitorToday
												.setPendingDistributor(distribitorToday.getPendingDistributor() + 1);
										todayDistributorRepository.save(distribitorToday);
									} else {
										TodayDistributor distribitorToday1 = new TodayDistributor();
										distribitorToday1.setDistributorCount(1);
										distribitorToday1.setPendingDistributor(1);
										distribitorToday1.setCreateAt(Utility.removeTime(new Date()));
										todayDistributorRepository.save(distribitorToday1);
									}

									Calendar calendar = Calendar.getInstance();
									String year = Integer.toString(calendar.get(Calendar.YEAR));

									YearlyDistributor yearlyDistributor = yearlyDistributorRepository.findByYear(year);
									if (yearlyDistributor != null) {
										yearlyDistributor
												.setDistributorCount(yearlyDistributor.getDistributorCount() + 1);
										yearlyDistributor
												.setPendingDistributor(yearlyDistributor.getPendingDistributor() + 1);
										yearlyDistributorRepository.save(yearlyDistributor);
									} else {
										YearlyDistributor yearlyDistributor1 = new YearlyDistributor();
										yearlyDistributor1.setDistributorCount(1);
										yearlyDistributor1.setPendingDistributor(1);
										yearlyDistributor1.setYear(year);
										yearlyDistributor1.setCreateAt(new Date());
										yearlyDistributorRepository.save(yearlyDistributor1);
									}
									String month = Integer.toString(calendar.get(Calendar.MONTH));
									MonthlyDistributor monthlyDistributor = monthlyDistributorRepository
											.findByMonth(month);
									if (monthlyDistributor != null) {
										monthlyDistributor
												.setDistributorCount(monthlyDistributor.getDistributorCount() + 1);
										monthlyDistributor
												.setPendingDistributor(monthlyDistributor.getPendingDistributor() + 1);
										monthlyDistributorRepository.save(monthlyDistributor);
									} else {
										MonthlyDistributor monthlyDistributor1 = new MonthlyDistributor();
										monthlyDistributor1.setDistributorCount(1);
										monthlyDistributor1.setPendingDistributor(1);
										monthlyDistributor1.setMonth(month);
										monthlyDistributor1.setCreateAt(new Date());
										monthlyDistributorRepository.save(monthlyDistributor1);
									}

									/* insert to Notification Table */
									Notificationparam param = new Notificationparam();
									EmployeeDetails emp = employeeRepository
											.getEmployeesByuserId(investor1.getUserId());
									String name = employeeService.getEmployeeFullNameByObject(emp);
									param.setEmployeeDetails(emp);
									param.setAdminMsg("Customer " + "'" + investor1.getName() + "' created by " + name);
									param.setOwnMsg("Customer " + investor1.getName() + " created.");
									param.setNotificationType("Customer Creation");
									param.setProcessNmae("Customer");
									param.setType("create");
									param.setEmailSubject("Korero alert- Customer created");
									param.setCompanyName(companyName);
									param.setUserId(investor1.getUserId());

									if (!investor1.getUserId().equals(investor1.getAssignedTo())) {
										List<String> assignToUserIds = new ArrayList<>();
										assignToUserIds.add(investor1.getAssignedTo());
										param.setAssignToUserIds(assignToUserIds);
										param.setAssignToMsg(
												"Customer " + "'" + investor1.getName() + "'" + " assigned to "
														+ employeeService.getEmployeeFullName(investor1.getAssignedTo())
														+ " by " + name);
									}
									notificationService.createNotificationForDynamicUsers(param);

								}
							}
						}

					}

					return investorLeads.getCompanyName() + " Successfully Converted to Investor";
				}
			}
			return "we are not found the InvestorLeads";
		}
		return "Sorry this InvestorLeads is not converted to Investor";
	}

	private InvestorOpportunityMapper addDataInInvestorOpportunityMapper(
			InvestorLeadsOpportunityMapper investorLeadsOpportunityMapper) {
		InvestorOpportunityMapper resultMapper = new InvestorOpportunityMapper();

		resultMapper.setOpportunityName(investorLeadsOpportunityMapper.getOpportunityName());
		resultMapper.setProposalAmount(investorLeadsOpportunityMapper.getProposalAmount());
		resultMapper.setUserId(investorLeadsOpportunityMapper.getUserId());
		resultMapper.setOrgId(investorLeadsOpportunityMapper.getOrgId());
		resultMapper.setContactId(investorLeadsOpportunityMapper.getContactId());
		resultMapper.setSalesUserIds(investorLeadsOpportunityMapper.getAssignedTo());
		resultMapper.setCurrency(investorLeadsOpportunityMapper.getCurrency());
		resultMapper.setDescription(investorLeadsOpportunityMapper.getDescription());
		resultMapper.setOppStage(investorLeadsOpportunityMapper.getOppStage());
		resultMapper.setOppWorkflow(investorLeadsOpportunityMapper.getOppWorkflow());
		resultMapper.setStartDate(investorLeadsOpportunityMapper.getStartDate());
		resultMapper.setEndDate(investorLeadsOpportunityMapper.getEndDate());

		return resultMapper;
	}

	@Override
	public HashMap getCountListByuserId(String userId) {
		List<InvestorLeads> investorLeads = investorLeadsRepository.getInvestorLeadsListByUserId(userId);
		HashMap map = new HashMap();
		map.put("InvestorLeadsDetails", investorLeads.size());

		return map;
	}

	@Override
	public Map getQualifiedInvestorLeadsListCountByUserId(String userId, String startDate, String endDate) {
		Date end_date = null;
		Date start_date = null;
		try {
			end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<InvestorLeads> investorLeads = investorLeadsRepository
				.getQualifiedInvestorLeadsListByUserIdAndDateRange(userId, start_date, end_date);
		HashMap map = new HashMap();
		map.put("qualifiedInvestorLeadsList", investorLeads.size());

		return map;
	}

	@Override
	public List<InvestorLeadsViewMapper> getQualifiedInvestorLeadsDetailsByUserId(String userId, String startDate,
			String endDate) {
		Date end_date = null;
		Date start_date = null;
		try {
			end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<InvestorLeadsViewMapper> mapperList = new ArrayList<>();
		List<InvestorLeads> investorLeads = investorLeadsRepository
				.getQualifiedInvestorLeadsListByUserIdAndDateRange(userId, start_date, end_date);
		if (null != investorLeads && !investorLeads.isEmpty()) {
			investorLeads.stream().map(li -> {
				InvestorLeadsViewMapper mapper = getInvestorLeadsDetailsById(li.getInvestorLeadsId());
				if (null != mapper.getInvestorLeadsId()) {
					mapperList.add(mapper);
				}

				return mapperList;
			}).collect(Collectors.toList());

		}
		return mapperList;
	}

	@Override
	public String ReinstateInvestorLeadsToJunk(String userId, TransferMapper transferMapper) {
		String message = null;
		if (null != transferMapper.getInvLeadsIds() && !transferMapper.getInvLeadsIds().isEmpty()) {
			List<String> investorLeadsIds = transferMapper.getInvLeadsIds();
			for (String investorLeadsId : investorLeadsIds) {
				InvestorLeads investorleads = investorLeadsRepository.getInvestorLeadsByIdAndLiveInd(investorLeadsId);
				if (null != investorleads) {
					investorleads.setUserId(userId);
					investorleads.setJunkInd(false);
					investorleads.setTypeUpdationDate(new Date());
					investorLeadsRepository.save(investorleads);
				}
			}
			message = "Successfuly junk investorleads converted to investorleads And Transfered To "
					+ employeeService.getEmployeeFullName(userId);
		}
		return message;
	}

	@Override
	public List<InvestorLeadsViewMapper> getCreatededInvestorLeadsDetailsByUserId(String userId, String startDate,
			String endDate) {
		List<InvestorLeadsViewMapper> mapperList = new ArrayList<>();
		Date end_date = null;
		Date start_date = null;
		try {
			end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<InvestorLeads> investorLeadsList = investorLeadsRepository
				.getCreatedInvestorLeadsListByUserIdAndDateRange(userId, start_date, end_date);
		System.out.println("investorLeadsList>>>" + investorLeadsList.size());

		if (null != investorLeadsList && !investorLeadsList.isEmpty()) {
			investorLeadsList.stream().map(li -> {
				InvestorLeadsViewMapper mapper = getInvestorLeadsDetailsById(li.getInvestorLeadsId());
				if (null != mapper.getInvestorLeadsId()) {
					mapperList.add(mapper);
				}

				return mapperList;
			}).collect(Collectors.toList());

		}

		return mapperList;
	}

	@Override
	public Map getInvestorCreatededLeadsListCountByUserId(String userId, String startDate, String endDate) {
		Date end_date = null;
		Date start_date = null;
		try {
			end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<InvestorLeads> investorLeadsList = investorLeadsRepository
				.getCreatedInvestorLeadsListByUserIdAndDateRange(userId, start_date, end_date);
		System.out.println("investorLeadsList>>>" + investorLeadsList.size());

		HashMap map = new HashMap();
		map.put("createdinvestorLeadsList", investorLeadsList.size());

		return map;
	}

	@Override
	public Map getJunkedInvestorLeadsCountByUserId(String userId) {
		List<InvestorLeads> investorLeadsList = investorLeadsRepository.getJunkLeadsListByUserId(userId);
		System.out.println("investorLeadsList>>>" + investorLeadsList.size());
		HashMap map = new HashMap();
		map.put("junkedList", investorLeadsList.size());

		return map;
	}

	@Override
	public List<InvestorLeadsViewMapper> getJunkedInvestorLeadsListByUserId(String userId) {
		List<InvestorLeads> investorLeadsList = investorLeadsRepository.getJunkLeadsListByUserId(userId);
		List<InvestorLeadsViewMapper> resultList = new ArrayList<>();
		if (null != investorLeadsList && !investorLeadsList.isEmpty()) {
			investorLeadsList.stream().map(li -> {
				InvestorLeadsViewMapper mapper = getInvestorLeadsDetailsById(li.getInvestorLeadsId());
				if (null != mapper.getInvestorLeadsId()) {
					resultList.add(mapper);
				}
				return resultList;
			}).collect(Collectors.toList());
		}
		return resultList;
	}

	@Override
	public Map getJunkedInvestorLeadsListCountByUserId(String userId, String startDate, String endDate) {
		Date end_date = null;
		Date start_date = null;
		try {
			end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<InvestorLeads> investorLeads = investorLeadsRepository
				.getJunkedInvestorLeadsListByUserIdAndDateRange(userId, start_date, end_date);
		System.out.println("leadsList>>>" + investorLeads.size());

		HashMap map = new HashMap();
		map.put("junkedInvestorLeadsList", investorLeads.size());

		return map;
	}

	@Override
	public List<InvestorLeadsViewMapper> getJunkedInvestorLeadsDetailsByUserId(String userId, String startDate,
			String endDate) {
		List<InvestorLeadsViewMapper> mapperList = new ArrayList<>();
		Date end_date = null;
		Date start_date = null;
		try {
			end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<InvestorLeads> investorLeads = investorLeadsRepository
				.getJunkedInvestorLeadsListByUserIdAndDateRange(userId, start_date, end_date);
		System.out.println("leadsList>>>" + investorLeads.size());

		if (null != investorLeads && !investorLeads.isEmpty()) {
			investorLeads.stream().map(li -> {
				InvestorLeadsViewMapper mapper = getInvestorLeadsDetailsById(li.getInvestorLeadsId());
				if (null != mapper.getInvestorLeadsId()) {
					mapperList.add(mapper);
				}

				return mapperList;
			}).collect(Collectors.toList());

		}

		return mapperList;
	}

	@Override
	public List<String> transferInvestorLeadsOneUserToAnother(String userId, TransferMapper transferMapper) {
		List<String> investorLeadsList = transferMapper.getLeadsIds();
		System.out.println("candiList::::::::::::::::::::::::::::::::::::::::::::::::::::" + investorLeadsList);
		if (null != investorLeadsList && !investorLeadsList.isEmpty()) {
			for (String investorLeadsId : investorLeadsList) {
				System.out.println("the candiate id is : " + investorLeadsId);
				System.out.println("the user id is : " + userId);
				InvestorLeads investorLeads = investorLeadsRepository.getInvestorLeadsByIdAndLiveInd(investorLeadsId);
				if (null != investorLeads) {
					System.out.println(
							"candidate::::::::::::::::::::::::::::::::::::::::::::::::::::" + investorLeads.toString());
					System.out.println("the user id is :=== " + userId);

					investorLeads.setUserId(userId);
					investorLeads.setJunkInd(false);
					investorLeads.setTypeUpdationDate(new Date());
					investorLeadsRepository.save(investorLeads);
				}
			}

		}
		return investorLeadsList;
	}

	@Override
	public Map getInvestorLeadsListCountByUserIdAndtypeAndDateRange(String userId, String startDate, String endDate) {
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

		HashMap map = new HashMap();

		List<InvestorLeads> investorLeadsList = investorLeadsRepository
				.getInvestorLeadsByUserIdAndTypeWithDateRange(userId, "Cold", start_date, end_date);
		map.put("coldList", investorLeadsList.size());

		List<InvestorLeads> investorLeadsList1 = investorLeadsRepository
				.getInvestorLeadsByUserIdAndTypeWithDateRange(userId, "Warm", start_date, end_date);
		map.put("warmList", investorLeadsList1.size());

		List<InvestorLeads> investorLeadsList2 = investorLeadsRepository
				.getInvestorLeadsByUserIdAndTypeWithDateRange(userId, "Hot", start_date, end_date);
		map.put("hotList", investorLeadsList2.size());

		return map;
	}

	@Override
	public List<Map<String, Double>> getAddedInvestorLeadsListCountByUserIdWithDateWise(String userId, String startDate,
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
			List<InvestorLeads> leadsList1 = investorLeadsRepository
					.getCreatedInvestorLeadsListByUserIdAndDateRange(userId, startDate2, endDate2);
			double number = leadsList1.size();
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
	public List<Map<String, Double>> getInvestorLeadsCountSourceWiseByUserId(String userId, String orgId) {
		List<Map<String, Double>> map = new ArrayList<>();

		List<Source> sourceList = sourceRepository.findByOrgIdAndLiveInd(orgId, true);
		if (null != sourceList && !sourceList.isEmpty()) {
			for (Source source : sourceList) {
				Map map1 = new HashMap<>();
				List<InvestorLeads> leads = investorLeadsRepository.getInvestorLeadsListByUserIdAndSource(userId,
						source.getSourceId());

				map1.put("source", source.getName());
				map1.put("number", leads.size());
				map.add(map1);
			}
		}

		return map;
	}

	@Override
	public List<Map<String, List<InvestorLeadsViewMapper>>> getInvestorLeadsListSourceWiseByUserId(String userId,
			String orgId) {
		List<Map<String, List<InvestorLeadsViewMapper>>> map = new ArrayList<>();

		List<Source> sourceList = sourceRepository.findByOrgIdAndLiveInd(orgId, true);
		if (null != sourceList && !sourceList.isEmpty()) {
			for (Source source : sourceList) {
				Map map1 = new HashMap<>();
				List<InvestorLeadsViewMapper> resulList = new ArrayList<>();
				List<InvestorLeads> investorLeads = investorLeadsRepository
						.getInvestorLeadsListByUserIdAndSource(userId, source.getSourceId());
				if (null != investorLeads && !investorLeads.isEmpty()) {
					investorLeads.stream().map(investorLead -> {
						InvestorLeadsViewMapper investorLeadsViewMapper = getInvestorLeadsDetailsById(
								investorLead.getInvestorLeadsId());
						if (null != investorLeadsViewMapper) {
							resulList.add(investorLeadsViewMapper);
						}
						return resulList;
					}).collect(Collectors.toList());
				}

				map1.put("source", source.getName());
				map1.put("investorLeadsList", resulList);
				map.add(map1);
			}
		}

		return map;
	}

	@Override
	public List<InvestorLeadsViewMapper> getInvestorLeadsHotByUserIdAndTypeAndDateRange(String userId, String startDate,
			String endDate) {
		List<InvestorLeadsViewMapper> mapperList = new ArrayList<>();
		Date end_date = null;
		Date start_date = null;
		try {
			end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<InvestorLeads> investorLeads = investorLeadsRepository.getInvestorLeadsByUserIdAndTypeWithDateRange(userId,
				"Hot", start_date, end_date);
		if (null != investorLeads && !investorLeads.isEmpty()) {
			investorLeads.stream().map(li -> {
				InvestorLeadsViewMapper mapper = getInvestorLeadsDetailsById(li.getInvestorLeadsId());
				if (null != mapper.getInvestorLeadsId()) {
					mapperList.add(mapper);
				}

				return mapperList;
			}).collect(Collectors.toList());

		}

		return mapperList;
	}

	@Override
	public List<InvestorLeadsViewMapper> getInvestorLeadsColdByUserIdAndTypeAndDateRange(String userId,
			String startDate, String endDate) {
		List<InvestorLeadsViewMapper> mapperList = new ArrayList<>();
		Date end_date = null;
		Date start_date = null;
		try {
			end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<InvestorLeads> investorLeads = investorLeadsRepository.getInvestorLeadsByUserIdAndTypeWithDateRange(userId,
				"Cold", start_date, end_date);
		if (null != investorLeads && !investorLeads.isEmpty()) {
			investorLeads.stream().map(li -> {
				InvestorLeadsViewMapper mapper = getInvestorLeadsDetailsById(li.getInvestorLeadsId());
				if (null != mapper.getInvestorLeadsId()) {
					mapperList.add(mapper);
				}

				return mapperList;
			}).collect(Collectors.toList());

		}

		return mapperList;
	}

	@Override
	public List<InvestorLeadsViewMapper> getInvestorLeadsWarmByUserIdAndTypeAndDateRange(String userId,
			String startDate, String endDate) {
		List<InvestorLeadsViewMapper> mapperList = new ArrayList<>();
		Date end_date = null;
		Date start_date = null;
		try {
			end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<InvestorLeads> investorLeads = investorLeadsRepository.getInvestorLeadsByUserIdAndTypeWithDateRange(userId,
				"Warm", start_date, end_date);
		if (null != investorLeads && !investorLeads.isEmpty()) {
			investorLeads.stream().map(li -> {
				InvestorLeadsViewMapper mapper = getInvestorLeadsDetailsById(li.getInvestorLeadsId());
				if (null != mapper.getInvestorLeadsId()) {
					mapperList.add(mapper);
				}

				return mapperList;
			}).collect(Collectors.toList());

		}

		return mapperList;
	}

	@Override

	public List<ActivityMapper> getActivityListByInvestorLeadsId(String investorleadsId) {
		List<ActivityMapper> resultList = new ArrayList<>();

		List<InvestorLeadsCallLink> callLink = investorLeadsCallLinkRepo
				.getCallListByInvestorLeadsIdAndLiveInd(investorleadsId);
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

		List<InvestorLeadsEventLink> eventLink = investorLeadsEventRepo.getByInvestorLeadsIdAndLiveInd(investorleadsId);
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

		List<InvestorLeadsTaskLink> taskLink = investorLeadsTaskRepo
				.getTaskListByInvestorLeadsIdAndLiveInd(investorleadsId);
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

	public List<InvestorLeadsViewMapper> getInvestorLeadsListByName(String name) {
		List<InvestorLeads> investorleadsList = investorLeadsRepository.findByNameContainingAndLiveInd(name, true);
		List<InvestorLeadsViewMapper> resultMapper = new ArrayList<>();
		if (null != investorleadsList && !investorleadsList.isEmpty()) {
			resultMapper = investorleadsList.stream().map(li -> getInvestorLeadsDetailsById(li.getInvestorLeadsId()))
					.collect(Collectors.toList());
		}
		return resultMapper;
	}

	@Override
	public List<InvestorLeadsViewMapper> getInvestorLeadsBySector(String name) {
		List<InvestorLeadsViewMapper> mapperList = new ArrayList<>();
		SectorDetails sectorDetails = sectorDetailsRepository.getSectorDetailsBySectorName(name);
		if (null != sectorDetails) {
			List<InvestorLeads> detailsList = investorLeadsRepository
					.findBySectorAndLiveInd(sectorDetails.getSectorId(), true);
			if (null != detailsList && !detailsList.isEmpty()) {
				mapperList = detailsList.stream().map(li -> getInvestorLeadsDetailsById(li.getInvestorLeadsId()))
						.collect(Collectors.toList());
			}
		}
		return mapperList;
	}

	@Override
	public List<InvestorLeadsViewMapper> getInvestorLeadsByOwnerName(String name) {
		List<InvestorLeadsViewMapper> mapperList = new ArrayList<>();
		EmployeeDetails employeeDetails = employeeRepository.findByFullName(name);
		if (null != employeeDetails) {
			List<InvestorLeads> detailsList = investorLeadsRepository
					.getInvestorLeadsListByUserId(employeeDetails.getUserId());
			if (null != detailsList && !detailsList.isEmpty()) {
				mapperList = detailsList.stream().map(li -> getInvestorLeadsDetailsById(li.getInvestorLeadsId()))
						.collect(Collectors.toList());
			}
		}
		return mapperList;
	}

	@Override
	public String saveInvestorLeadsNotes(NotesMapper notesMapper) {

		String investorLeadsNotesId = null;
		if (null != notesMapper) {
			Notes notes = new Notes();
			notes.setNotes(notesMapper.getNotes());
			notes.setCreation_date(new Date());
			notes.setUserId(notesMapper.getEmployeeId());
			notes.setLiveInd(true);
			Notes note = notesRepository.save(notes);
			investorLeadsNotesId = note.getNotes_id();

			/* insert to leads-notes-link */

			InvestorLeadsNotesLink investorLeadsNotesLink = new InvestorLeadsNotesLink();
			investorLeadsNotesLink.setInvestorLeadsId(notesMapper.getInvestorLeadsId());
			investorLeadsNotesLink.setNoteId(investorLeadsNotesId);
			investorLeadsNotesLink.setCreationDate(new Date());

			investorLeadsNotesLinkRepository.save(investorLeadsNotesLink);

		}
		return investorLeadsNotesId;

	}

	@Override
	public List<NotesMapper> getNoteListByInvestorLeadsId(String investorLeadsId) {
		List<InvestorLeadsNotesLink> leadsNotesLinkList = investorLeadsNotesLinkRepository
				.getNotesIdByInvestorLeadsId(investorLeadsId);
		if (leadsNotesLinkList != null && !leadsNotesLinkList.isEmpty()) {
			return leadsNotesLinkList.stream().map(leadsNotesLink -> {
				NotesMapper notesMapper = getNotes(leadsNotesLink.getNoteId());
				return notesMapper;
			}).collect(Collectors.toList());

		}

		return null;
	}

	private NotesMapper getNotes(String id) {
		Notes notes = notesRepository.findByNoteId(id);
		NotesMapper notesMapper = new NotesMapper();
		if (null != notes) {
			notesMapper.setNotesId(notes.getNotes_id());
			notesMapper.setNotes(notes.getNotes());
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
	public void deleteInvestorLeadsNotesById(String noteId) {
		InvestorLeadsNotesLink notesList = investorLeadsNotesLinkRepository.findByNoteId(noteId);
		if (null != notesList) {

			Notes notes = notesRepository.findByNoteId(noteId);
			if (null != notes) {
				notes.setLiveInd(false);
				notesRepository.save(notes);
			}
		}
	}

	@Override
	public List<InvestorLeadsViewMapper> getInvestorLeadsListByOrgId(String orgId, int pageNo, int pageSize,
			String filter) {
		Pageable paging = null;
		paging = PageRequest.of(pageNo, pageSize, Sort.by("creationDate").descending());
		if (filter.equalsIgnoreCase("creationdate")) {
			paging = PageRequest.of(pageNo, pageSize, Sort.by("creationDate").descending());

		} else if (filter.equalsIgnoreCase("ascending")) {
			paging = PageRequest.of(pageNo, pageSize, Sort.by("name"));

		} else if (filter.equalsIgnoreCase("descending")) {
			paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "name"));
		}
		Page<InvestorLeads> investorleadsList = investorLeadsRepository.getInvestorLeadsListByOrgIdAndPagging(orgId,
				paging);
		List<InvestorLeadsViewMapper> resultMapper = new ArrayList<>();
		if (null != investorleadsList && !investorleadsList.isEmpty()) {
			resultMapper = investorleadsList.stream().map(li -> {
				InvestorLeadsViewMapper mapper = getInvestorLeadsDetailsById(li.getInvestorLeadsId());
				mapper.setPageCount(investorleadsList.getTotalPages());
				mapper.setDataCount(investorleadsList.getSize());
				mapper.setListCount(investorleadsList.getTotalElements());
				return mapper;
			}).collect(Collectors.toList());
		}
		return resultMapper;
	}

	@Override
	public HashMap getInvestorLeadsCountByOrgId(String orgId) {
		List<InvestorLeads> list = investorLeadsRepository.getInvestorLeadsListByOrgId(orgId);
		HashMap map = new HashMap();
		map.put("investorLeadsDetails", list.size());

		return map;
	}

	@Override
	public Set<InvestorLeadsViewMapper> getTeamInvestorLeadsDetailsByUserId(String userId, int pageNo, int pageSize,
			String filter) {
		Pageable paging = null;
		Set<InvestorLeadsViewMapper> resultMapper = new HashSet<>();
		if (filter.equalsIgnoreCase("creationdate")) {
			paging = PageRequest.of(pageNo, pageSize, Sort.by("creationDate").descending());

		} else if (filter.equalsIgnoreCase("ascending")) {
			paging = PageRequest.of(pageNo, pageSize, Sort.by("name"));

		} else if (filter.equalsIgnoreCase("descending")) {
			paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "name"));
		}

		Team team = teamRepository.findByTeamLeadAndLiveInd(userId, true);
		if (null != team) {
			List<String> userIds = teamMemberLinkRepo.findByTeamId(team.getTeamId()).stream()
					.map(TeamMemberLink::getTeamMemberId).collect(Collectors.toList());

			Page<InvestorLeads> investorleadsList = investorLeadsRepository
					.getTeamInvestorLeadsListByUserIdAndPagging(userIds, paging);

			if (null != investorleadsList && !investorleadsList.isEmpty()) {
				resultMapper = investorleadsList.getContent().stream().map(li -> {
					InvestorLeadsViewMapper mapper = getInvestorLeadsDetailsById(li.getInvestorLeadsId());
					mapper.setPageCount(investorleadsList.getTotalPages());
					mapper.setDataCount(investorleadsList.getSize());
					mapper.setListCount(investorleadsList.getTotalElements());
					return mapper;
				}).collect(Collectors.toSet());
			}
		}
		return resultMapper;
	}

	@Override
	public HashMap getActivityRecordByInvestorLeadsId(String investorLeadsId) {
		int count = 0;
		List<InvestorLeadsCallLink> callLink = investorLeadsCallLinkRepo
				.getCallListByInvestorLeadsIdAndLiveInd(investorLeadsId);
		if (null != callLink && !callLink.isEmpty()) {
			count = callLink.size();
		}

		List<InvestorLeadsEventLink> eventLink = investorLeadsEventRepo.getByInvestorLeadsIdAndLiveInd(investorLeadsId);
		if (null != eventLink && !eventLink.isEmpty()) {
			count += eventLink.size();
		}

		List<InvestorLeadsTaskLink> taskLink = investorLeadsTaskRepo
				.getTaskListByInvestorLeadsIdAndLiveInd(investorLeadsId);
		if (null != taskLink && !taskLink.isEmpty()) {
			count += taskLink.size();
		}

		HashMap map = new HashMap();
		map.put("count", count);
		return map;

	}

	@Override
	public HashMap getInvestorLeadsAllCount(String orgId) {
		List<InvestorLeads> investorleadsList = investorLeadsRepository.getByOrgIdAndLiveInd(orgId);
		HashMap map = new HashMap();
		if (null != investorleadsList) {
			map.put("deal", investorleadsList.size());
		}
		return map;
	}

	public InvestorLeadsReportMapper getInvestorLeadsDetailsByIdForReport(String investorLeadsId) {
		InvestorLeads investorLeads = investorLeadsRepository.getInvestorLeadsByIdAndLiveInd(investorLeadsId);
		System.out.println("Leads object is..." + investorLeadsId);

		InvestorLeadsReportMapper leadsViewMapper = new InvestorLeadsReportMapper();

		if (null != investorLeads) {

			if (investorLeads.getSector() != null && investorLeads.getSector().trim().length() > 0) {
				SectorDetails sector = sectorDetailsRepository.getSectorDetailsBySectorId(investorLeads.getSector());
				System.out.println("get sectordetails by id returns........." + investorLeads.getSector());
				System.out.println("sector object is......." + sector);
				if (sector != null) {
					leadsViewMapper.setSector(sector.getSectorName());
					leadsViewMapper.setSectorId(investorLeads.getSector());
				} else {
					leadsViewMapper.setSector("");
					leadsViewMapper.setSectorId("");
				}
			}
			leadsViewMapper.setInvestorLeadsId(investorLeadsId);
			leadsViewMapper.setName(employeeService.getEmployeeFullName(investorLeads.getUserId()));
			leadsViewMapper.setSalutation(investorLeads.getSalutation());
			leadsViewMapper.setFirstName(investorLeads.getFirstName());
			leadsViewMapper.setMiddleName(investorLeads.getMiddleName());
			leadsViewMapper.setLastName(investorLeads.getLastName());
			leadsViewMapper.setImageId(investorLeads.getImageId());
			leadsViewMapper.setUrl(investorLeads.getUrl());
			leadsViewMapper.setNotes(investorLeads.getNotes());
			leadsViewMapper.setEmail(investorLeads.getEmail());
			leadsViewMapper.setGroup(investorLeads.getGroup());
			leadsViewMapper.setVatNo(investorLeads.getVatNo());
			leadsViewMapper.setPhoneNumber(investorLeads.getPhoneNumber());
			leadsViewMapper.setCountryDialCode(investorLeads.getCountryDialCode());
			leadsViewMapper.setUserId(investorLeads.getUserId());
			leadsViewMapper.setOrgId(investorLeads.getOrganizationId());
			leadsViewMapper.setCreationDate(Utility.getISOFromDate(investorLeads.getCreationDate()));
			leadsViewMapper.setImageURL(investorLeads.getImageURL());

			leadsViewMapper.setCountry(investorLeads.getCountry());
			// customerMapper.setSector(sector.getSectorName());
			leadsViewMapper.setZipcode(investorLeads.getZipcode());

			leadsViewMapper.setDocumentId(investorLeads.getDocumentId());
			leadsViewMapper.setCategory(investorLeads.getCategory());
			leadsViewMapper.setBusinessRegistration(investorLeads.getBusinessRegistration());
			leadsViewMapper.setConvertInd(investorLeads.isConvertInd());
			leadsViewMapper.setConvertionDate(Utility.getISOFromDate(investorLeads.getConvertionDate()));
			leadsViewMapper.setCompanyName(investorLeads.getCompanyName());
			leadsViewMapper.setType(investorLeads.getType());
			leadsViewMapper.setTypeUpdationDate(Utility.getISOFromDate(investorLeads.getTypeUpdationDate()));
			leadsViewMapper.setJunkInd(investorLeads.isJunkInd());
			leadsViewMapper.setSourceUserID(investorLeads.getSourceUserId());
			leadsViewMapper.setSourceUserName(employeeService.getEmployeeFullName(investorLeads.getSourceUserId()));
			Source source = sourceRepository.findBySourceId(investorLeads.getSource());
			if (null != source) {
				leadsViewMapper.setSource(source.getName());
			}

			EmployeeDetails employeeDetails = employeeRepository
					.getEmployeeDetailsByEmployeeId(investorLeads.getUserId());
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
			EmployeeDetails employeeDetail = employeeRepository
					.getEmployeeDetailsByEmployeeId(investorLeads.getAssignedTo());
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

			List<AddressMapper> addressList = new ArrayList<AddressMapper>();
			List<InvestorLeadsAddressLink> inventoryleadsAddressLink = investorLeadsAddressLinkRepository
					.getAddressListByInvestorLeadsId(investorLeadsId);

			if (null != inventoryleadsAddressLink && !inventoryleadsAddressLink.isEmpty()) {

				for (InvestorLeadsAddressLink inventoryleadsAddressLink2 : inventoryleadsAddressLink) {
					AddressDetails addressDetails = addressRepository
							.getAddressDetailsByAddressId(inventoryleadsAddressLink2.getAddressId());

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
								addressDetails.getCountry(), investorLeads.getOrganizationId());
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
	public List<InvestorLeadsReportMapper> getAllInvestorLeadsByOrgIdForReport(String orgId, String startDate,
			String endDate) {
		Date endDate1 = null;
		Date startDate1 = null;
		try {
			endDate1 = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			startDate1 = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<InvestorLeadsReportMapper> resultMapper = new ArrayList<>();
		List<InvestorLeads> investorList = investorLeadsRepository.getInvestorLeadsListByOrgIdWithDateRange(orgId,
				startDate1, endDate1);
		if (null != investorList && !investorList.isEmpty()) {
			resultMapper = investorList.stream().map(investor -> {
				InvestorLeadsReportMapper mapper = getInvestorLeadsDetailsByIdForReport(investor.getInvestorLeadsId());
				return mapper;
			}).collect(Collectors.toList());
		}
		return resultMapper;
	}

	@Override
	public List<InvestorLeadsReportMapper> getAllInvestorLeadsByUserIdForReport(String userId, String startDate,
			String endDate) {
		Date endDate1 = null;
		Date startDate1 = null;
		try {
			endDate1 = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			startDate1 = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<InvestorLeadsReportMapper> resultMapper = new ArrayList<>();
		List<InvestorLeads> investorList = investorLeadsRepository.getInvestorLeadsListByUserIdWithDateRange(userId,
				startDate1, endDate1);
		if (null != investorList && !investorList.isEmpty()) {
			resultMapper = investorList.stream().map(investor -> {
				InvestorLeadsReportMapper mapper = getInvestorLeadsDetailsByIdForReport(investor.getInvestorLeadsId());
				return mapper;
			}).collect(Collectors.toList());
		}
		return resultMapper;
	}

	@Override
	public List<InvestorLeadsReportMapper> getAllInvestorQualifiedLeadsListByOrgIdForReport(String orgId,
			String startDate, String endDate) {
		Date endDate1 = null;
		Date startDate1 = null;
		try {
			endDate1 = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			startDate1 = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<InvestorLeadsReportMapper> mapperList = new ArrayList<>();
		List<InvestorLeads> investorLeads = investorLeadsRepository
				.getQualifiedInvestorLeadsListByOrgIdAndDateRange(orgId, startDate1, endDate1);
		if (null != investorLeads && !investorLeads.isEmpty()) {
			investorLeads.stream().map(li -> {
				InvestorLeadsReportMapper mapper = getInvestorLeadsDetailsByIdForReport(li.getInvestorLeadsId());
				if (null != mapper.getInvestorLeadsId()) {
					mapperList.add(mapper);
				}

				return mapperList;
			}).collect(Collectors.toList());

		}
		return mapperList;
	}

	@Override
	public boolean getInvestorLeadsByUrl(String url) {
		List<InvestorLeads> leads = investorLeadsRepository.getByUrl(url);
		if (null != leads && !leads.isEmpty()) {
			return true;
		}
		return false;
	}

	@Override
	public boolean getInvestorLeadsByEmail(String email) {
		List<InvestorLeads> leads = investorLeadsRepository.getByEmail(email);
		if (null != leads && !leads.isEmpty()) {
			return true;
		}
		return false;
	}

	@Override
	public Set<InvestorLeadsViewMapper> getTeamInvestorLeadsDetailsUnderAyUserId(String userId, int pageNo,
			int pageSize) {
		Set<InvestorLeadsViewMapper> resultMapper = new HashSet<>();
		Set<String> userIds = employeeRepository.getEmployeeDetailsByReportingManagerId(userId).stream()
				.map(EmployeeDetails::getUserId).collect(Collectors.toSet());
		userIds.add(userId);

		List<String> userIdss = new ArrayList<>(userIds);

		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("creationDate").descending());
		Page<InvestorLeads> investorleadsList = investorLeadsRepository
				.getTeamInvestorLeadsListByUserIdAndPagging(userIdss, paging);

		if (null != investorleadsList && !investorleadsList.isEmpty()) {
			resultMapper = investorleadsList.getContent().stream().map(li -> {
				InvestorLeadsViewMapper mapper = getInvestorLeadsDetailsById(li.getInvestorLeadsId());
				mapper.setPageCount(investorleadsList.getTotalPages());
				mapper.setDataCount(investorleadsList.getSize());
				mapper.setListCount(investorleadsList.getTotalElements());
				return mapper;
			}).collect(Collectors.toSet());
		}
		return resultMapper;
	}

	@Override
	public HashMap getTeamInvestorLeadsCountUnderAyUserId(String userId) {
		HashMap map = new HashMap();
		Set<String> userIds = employeeRepository.getEmployeeDetailsByReportingManagerId(userId).stream()
				.map(EmployeeDetails::getUserId).collect(Collectors.toSet());
		userIds.add(userId);

		List<String> userIdss = new ArrayList<>(userIds);
		List<InvestorLeads> investorleadsList = investorLeadsRepository.getTeamInvestorLeadsListByUserIds(userIdss);
		map.put("pitchTeam", investorleadsList.size());

		return map;
	}

	@Override
	public List<InvestorLeadsViewMapper> getInvLeadsDetailsByUserIdAndType(String userId, int pageNo, int pageSize,
			String filter, String type) {
		Pageable paging = null;
		if (filter.equalsIgnoreCase("creationdate")) {
			paging = PageRequest.of(pageNo, pageSize, Sort.by("creationDate").descending());

		} else if (filter.equalsIgnoreCase("ascending")) {
			paging = PageRequest.of(pageNo, pageSize, Sort.by("name"));

		} else if (filter.equalsIgnoreCase("descending")) {
			paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "name"));
		}
		Page<InvestorLeads> leadsList = investorLeadsRepository.getInvestorsLeadsListByUserIdPaggingAndType(userId,
				paging, type);
		List<InvestorLeadsViewMapper> mapperList = new ArrayList<>();

		if (null != leadsList && !leadsList.isEmpty()) {
			mapperList = leadsList.stream().map(li -> {
				InvestorLeadsViewMapper mapper = getInvestorLeadsDetailsById(li.getInvestorLeadsId());
				mapper.setPageCount(leadsList.getTotalPages());
				mapper.setDataCount(leadsList.getSize());
				mapper.setListCount(leadsList.getTotalElements());
				return mapper;
			}).collect(Collectors.toList());

		}
		return mapperList;
	}

	@Override
	public List<InvestorLeadsViewMapper> getTeamInvLeadsDetailsByUnderAUserId(String userId, int pageNo, int pageSize,
			String type) {
		Set<String> userIds = employeeRepository.getEmployeeDetailsByReportingManagerId(userId).stream()
				.map(EmployeeDetails::getUserId).collect(Collectors.toSet());
		userIds.add(userId);

		List<String> userIdss = new ArrayList<>(userIds);

		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("creationDate").descending());

		Page<InvestorLeads> leadsPage = investorLeadsRepository
				.getTeamInvestorsLeadsListByUserIdsPaginatedAType(userIdss, paging, type);

		List<InvestorLeadsViewMapper> result = new ArrayList<>();

		if (leadsPage != null && !leadsPage.isEmpty()) {
			result = leadsPage.getContent().stream().map(li -> {
				InvestorLeadsViewMapper mapper = getInvestorLeadsDetailsById(li.getInvestorLeadsId());
				mapper.setPageCount(leadsPage.getTotalPages());
				mapper.setDataCount(leadsPage.getSize());
				mapper.setListCount(leadsPage.getTotalElements());
				return mapper;
			}).collect(Collectors.toList());
		}
		return result;
	}

	@Override
	public List<InvestorLeadsViewMapper> getInvLeadsListByOrgId(String orgId, int pageNo, int pageSize, String filter,
			String type) {
		Pageable paging = null;
		if (filter.equalsIgnoreCase("creationdate")) {
			paging = PageRequest.of(pageNo, pageSize, Sort.by("creationDate").descending());

		} else if (filter.equalsIgnoreCase("ascending")) {
			paging = PageRequest.of(pageNo, pageSize, Sort.by("name"));

		} else if (filter.equalsIgnoreCase("descending")) {
			paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "name"));
		}
		Page<InvestorLeads> leadsList = investorLeadsRepository.getInvLeadsListByOrgIdPaggingAndType(orgId, paging,
				type);
		List<InvestorLeadsViewMapper> mapperList = new ArrayList<>();

		if (null != leadsList && !leadsList.isEmpty()) {
			mapperList = leadsList.stream().map(li -> {
				InvestorLeadsViewMapper mapper = getInvestorLeadsDetailsById(li.getInvestorLeadsId());
				mapper.setPageCount(leadsList.getTotalPages());
				mapper.setDataCount(leadsList.getSize());
				mapper.setListCount(leadsList.getTotalElements());
				return mapper;
			}).collect(Collectors.toList());
		}
		return mapperList;
	}

	@Override
	public void deletePitchUserLevel(String userId) {
		List<InvestorLeads> leadsList = investorLeadsRepository.getInvLeadsListByUserId(userId);
		if (null != leadsList && !leadsList.isEmpty()) {
			for (InvestorLeads investorLeads : leadsList) {
				investorLeads.setLiveInd(false);
				investorLeadsRepository.save(investorLeads);
			}
		}
	}

	@Override
	public List<InvestorLeadsViewMapper> getInvLeadsDetailsByNameByOrgLevel(String name, String orgId) {
		List<InvestorLeads> detailsList = investorLeadsRepository.getByOrgIdAndLiveInd(orgId);
		List<InvestorLeads> filterList = detailsList.parallelStream().filter(detail -> {
			return detail.getCompanyName() != null && Utility.containsIgnoreCase(detail.getCompanyName(), name.trim());
		}).collect(Collectors.toList());
		List<InvestorLeadsViewMapper> mapperList = new ArrayList<InvestorLeadsViewMapper>();
		if (null != filterList && !filterList.isEmpty()) {

			mapperList = filterList.parallelStream().map(li -> getInvestorLeadsDetailsById(li.getInvestorLeadsId()))
					.collect(Collectors.toList());

		}

		return mapperList;
	}

	@Override
	public List<InvestorLeadsViewMapper> getInvLeadsDetailsBySectorInOrgLevel(String name, String orgId) {
		List<InvestorLeadsViewMapper> mapperList = new ArrayList<>();
		SectorDetails sectorDetails = sectorDetailsRepository.getSectorDetailsBySectorNameAndOrgId(name, orgId);
		if (null != sectorDetails) {
			List<InvestorLeads> detailsList = investorLeadsRepository
					.findBySectorAndLiveIndAndOrganizationId(sectorDetails.getSectorId(), true, orgId);
			if (null != detailsList && !detailsList.isEmpty()) {
				mapperList = detailsList.stream().map(li -> getInvestorLeadsDetailsById(li.getInvestorLeadsId()))
						.collect(Collectors.toList());
			}
		}
		return mapperList;
	}

	@Override
	public List<InvestorLeadsViewMapper> getInvLeadsDetailsBySourceInOrgLevel(String name, String orgId) {
		List<InvestorLeadsViewMapper> mapperList = new ArrayList<>();
		Source source = sourceRepository.findByNameAndOrgId(name, orgId);
		if (null != source) {
			List<InvestorLeads> detailsList = investorLeadsRepository
					.findBySourceAndLiveIndAndOrganizationId(source.getSourceId(), true, orgId);
			if (null != detailsList && !detailsList.isEmpty()) {
				mapperList = detailsList.stream().map(li -> getInvestorLeadsDetailsById(li.getInvestorLeadsId()))
						.collect(Collectors.toList());
			}
		}
		return mapperList;
	}

	@Override
	public List<InvestorLeadsViewMapper> getInvLeadsDetailsByOwnerNameInOrgLevel(String name, String orgId) {
		List<InvestorLeadsViewMapper> mapperList = new ArrayList<>();

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
					.flatMap(employeeDetails -> investorLeadsRepository
							.findByUserIdAndLiveInd(employeeDetails.getUserId(), true).stream())
					.map(leads -> getInvestorLeadsDetailsById(leads.getInvestorLeadsId())).collect(Collectors.toList());

		}

		return mapperList;
	}

	@Override
	public List<InvestorLeadsViewMapper> getInvLeadsDetailsByNameForTeam(String name, String userId) {
		Set<String> userIds = employeeRepository.getEmployeeDetailsByReportingManagerId(userId).stream()
				.map(EmployeeDetails::getUserId).collect(Collectors.toSet());
		userIds.add(userId);
		List<String> userIdss = new ArrayList<>(userIds);
		List<InvestorLeads> list = investorLeadsRepository.getTeamInvestorLeadsListByUserIds(userIdss);
		List<InvestorLeads> filterList = list.parallelStream().filter(detail -> {
			return detail.getCompanyName() != null && Utility.containsIgnoreCase(detail.getCompanyName(), name.trim());
		}).collect(Collectors.toList());
		List<InvestorLeadsViewMapper> mapperList = new ArrayList<InvestorLeadsViewMapper>();
		if (null != filterList && !filterList.isEmpty()) {
			mapperList = filterList.parallelStream().map(li -> getInvestorLeadsDetailsById(li.getInvestorLeadsId()))
					.collect(Collectors.toList());
		}
		return mapperList;
	}

	@Override
	public List<InvestorLeadsViewMapper> getInvLeadsDetailsBySectorForTeam(String name, String userId, String orgId) {
		List<InvestorLeadsViewMapper> mapperList = new ArrayList<>();
		SectorDetails sectorDetails = sectorDetailsRepository.getSectorDetailsBySectorNameAndOrgId(name, orgId);
		if (null != sectorDetails) {
			Set<String> userIds = employeeRepository.getEmployeeDetailsByReportingManagerId(userId).stream()
					.map(EmployeeDetails::getUserId).collect(Collectors.toSet());
			userIds.add(userId);
			List<String> userIdss = new ArrayList<>(userIds);
			List<InvestorLeads> filterList = investorLeadsRepository.getTeamLeadsListByUserIdsAndSector(userIdss,
					sectorDetails.getSectorId());
			
			if (null != filterList && !filterList.isEmpty()) {
				mapperList = filterList.parallelStream().map(li -> getInvestorLeadsDetailsById(li.getInvestorLeadsId()))
						.collect(Collectors.toList());
			}
		}
		return mapperList;
	}

	@Override
	public List<InvestorLeadsViewMapper> getInvLeadsDetailsBySourceForTeam(String name, String userId, String orgId) {
		List<InvestorLeadsViewMapper> mapperList = new ArrayList<>();
		Source source = sourceRepository.findByNameAndOrgId(name, orgId);
		if (null != source) {
			Set<String> userIds = employeeRepository.getEmployeeDetailsByReportingManagerId(userId).stream()
					.map(EmployeeDetails::getUserId).collect(Collectors.toSet());
			userIds.add(userId);
			List<String> userIdss = new ArrayList<>(userIds);
			List<InvestorLeads> filterList = investorLeadsRepository.getTeaminvestorLeadsListByUserIdsAndSource(userIdss, source.getSourceId());
			if (null != filterList && !filterList.isEmpty()) {
				mapperList = filterList.parallelStream().map(li -> getInvestorLeadsDetailsById(li.getInvestorLeadsId()))
						.collect(Collectors.toList());
			}
		}
		return mapperList;
	}

	@Override
	public List<InvestorLeadsViewMapper> getInvLeadsDetailsByOwnerNameForTeam(String name, String userId,
			String orgId) {
		List<InvestorLeadsViewMapper> mapperList = new ArrayList<>();

		List<EmployeeDetails> detailsList = employeeRepository.getEmployeeDetailsByReportingManagerIdAndUserId(userId);

		List<EmployeeDetails> filterList = detailsList.parallelStream().filter(
				detail -> detail.getFullName() != null && Utility.containsIgnoreCase(detail.getFullName(), name.trim()))
				.collect(Collectors.toList());

		if (null != filterList && !filterList.isEmpty()) {
			mapperList = filterList.stream().flatMap(
					employeeDetails -> investorLeadsRepository.findByUserIdAndLiveInd(employeeDetails.getUserId(),true).stream())
					.map(leads -> getInvestorLeadsDetailsById(leads.getInvestorLeadsId())).collect(Collectors.toList());
		}
		return mapperList;
	}

	@Override
	public List<InvestorLeadsViewMapper> getInvLeadsDetailsByNameByUserId(String name, String userId) {
		List<InvestorLeads> list = investorLeadsRepository.getByUserIdandLiveInd(userId);
		List<InvestorLeads> filterList = list.parallelStream().filter(detail -> {
			return detail.getCompanyName() != null && Utility.containsIgnoreCase(detail.getCompanyName(), name.trim());
		}).collect(Collectors.toList());
		List<InvestorLeadsViewMapper> mapperList = new ArrayList<InvestorLeadsViewMapper>();
		if (null != filterList && !filterList.isEmpty()) {
			mapperList = filterList.parallelStream().map(leads -> getInvestorLeadsDetailsById(leads.getInvestorLeadsId()))
					.collect(Collectors.toList());
		}
		return mapperList;
	}

	@Override
	public List<InvestorLeadsViewMapper> getInvLeadsBySectorInUserLevel(String name, String userId, String orgId) {
		List<InvestorLeadsViewMapper> mapperList = new ArrayList<>();
		SectorDetails sectorDetails = sectorDetailsRepository.getSectorDetailsBySectorNameAndOrgId(name, orgId);
		if (null != sectorDetails) {
			List<InvestorLeads> detailsList = investorLeadsRepository.findBySectorAndLiveIndAndUserId(sectorDetails.getSectorId(), true,
					userId);
			if (null != detailsList && !detailsList.isEmpty()) {
				mapperList = detailsList.stream().map(leads -> getInvestorLeadsDetailsById(leads.getInvestorLeadsId()))
						.collect(Collectors.toList());
			}
		}
		return mapperList;
	}

	@Override
	public List<InvestorLeadsViewMapper> getInvLeadsBySourceInUserLevel(String name, String userId, String orgId) {
		List<InvestorLeadsViewMapper> mapperList = new ArrayList<>();
		Source source = sourceRepository.findByNameAndOrgId(name, orgId);
		if (null != source) {
			List<InvestorLeads> detailsList = investorLeadsRepository.findBySourceAndLiveIndAndUserId(source.getSourceId(), true,
					userId);
			if (null != detailsList && !detailsList.isEmpty()) {
				mapperList = detailsList.stream().map(leads -> getInvestorLeadsDetailsById(leads.getInvestorLeadsId()))
						.collect(Collectors.toList());
			}
		}
		return mapperList;
	}

	@Override
	public List<InvestorLeadsViewMapper> getInvLeadsByOwnerNameInUserLevel(String name, String userId, String orgId) {
		List<InvestorLeadsViewMapper> mapperList = new ArrayList<>();

		List<EmployeeDetails> detailsList = employeeRepository.getEmployeesByOrgId(orgId);

		List<EmployeeDetails> filterList = detailsList.parallelStream().filter(
				detail -> detail.getFullName() != null && Utility.containsIgnoreCase(detail.getFullName(), name.trim()))
				.filter(detail -> detail.getUserId().equalsIgnoreCase(userId)).collect(Collectors.toList());

		if (null != filterList && !filterList.isEmpty()) {
			mapperList = filterList.stream().flatMap(
					employeeDetails -> investorLeadsRepository.findByUserIdAndLiveInd(employeeDetails.getUserId(),true).stream())
					.map(leads -> getInvestorLeadsDetailsById(leads.getInvestorLeadsId())).collect(Collectors.toList());
		}
		return mapperList;
	}

}
