package com.app.employeePortal.investor.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.app.employeePortal.Opportunity.entity.OpportunityDetails;
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
import com.app.employeePortal.call.entity.EmployeeCallLink;
import com.app.employeePortal.call.entity.InvestorCallLink;
import com.app.employeePortal.call.repository.CallDetailsRepository;
import com.app.employeePortal.call.repository.EmployeeCallRepository;
import com.app.employeePortal.call.repository.InvestorCallLinkRepo;
import com.app.employeePortal.candidate.entity.DefinationDetails;
import com.app.employeePortal.candidate.entity.DefinationInfo;
import com.app.employeePortal.candidate.repository.DefinationInfoRepository;
import com.app.employeePortal.candidate.repository.DefinationRepository;
import com.app.employeePortal.category.entity.Club;
import com.app.employeePortal.category.entity.CurrencyConversion;
import com.app.employeePortal.category.repository.ClubRepository;
import com.app.employeePortal.category.repository.CurrencyConversionRepository;
import com.app.employeePortal.contact.entity.ContactAddressLink;
import com.app.employeePortal.contact.entity.ContactDetails;
import com.app.employeePortal.contact.entity.ContactInfo;
import com.app.employeePortal.contact.mapper.ContactMapper;
import com.app.employeePortal.contact.mapper.ContactReportMapper;
import com.app.employeePortal.contact.mapper.ContactViewMapper;
import com.app.employeePortal.contact.repository.ContactAddressLinkRepository;
import com.app.employeePortal.contact.repository.ContactInfoRepository;
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
import com.app.employeePortal.document.mapper.DocumentTypeMapper;
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
import com.app.employeePortal.event.repository.EmployeeEventRepository;
import com.app.employeePortal.event.repository.EventDetailsRepository;
import com.app.employeePortal.event.repository.InvestorEventRepo;
import com.app.employeePortal.investor.entity.InOppConversionValue;
import com.app.employeePortal.investor.entity.Investor;
import com.app.employeePortal.investor.entity.InvestorAddressLink;
import com.app.employeePortal.investor.entity.InvestorContactLink;
import com.app.employeePortal.investor.entity.InvestorDocumentLink;
import com.app.employeePortal.investor.entity.InvestorDocumentType;
import com.app.employeePortal.investor.entity.InvestorInvoice;
import com.app.employeePortal.investor.entity.InvestorInvoiceAddressLink;
import com.app.employeePortal.investor.entity.InvestorNoteLink;
import com.app.employeePortal.investor.entity.InvestorOppStages;
import com.app.employeePortal.investor.entity.InvestorOpportunity;
import com.app.employeePortal.investor.entity.InvestorSkillLink;
import com.app.employeePortal.investor.entity.InvestorsShare;
import com.app.employeePortal.investor.mapper.InvestorDocTypeMapper;
import com.app.employeePortal.investor.mapper.InvestorInvoiceMapper;
import com.app.employeePortal.investor.mapper.InvestorKeySkillMapper;
import com.app.employeePortal.investor.mapper.InvestorMapper;
import com.app.employeePortal.investor.mapper.InvestorOpportunityMapper;
import com.app.employeePortal.investor.mapper.InvestorReportMapper;
import com.app.employeePortal.investor.mapper.InvestorShareMapper;
import com.app.employeePortal.investor.mapper.InvestorSkillLinkMapper;
import com.app.employeePortal.investor.mapper.InvestorViewMapper;
import com.app.employeePortal.investor.mapper.InvestorViewMapperForDropDown;
import com.app.employeePortal.investor.repository.InOppConversionValueRepo;
import com.app.employeePortal.investor.repository.InvestorAddressLinkRepo;
import com.app.employeePortal.investor.repository.InvestorContactLinkRepo;
import com.app.employeePortal.investor.repository.InvestorDocumentLinkRepo;
import com.app.employeePortal.investor.repository.InvestorDocumentTypeRepo;
import com.app.employeePortal.investor.repository.InvestorInvoiceAddressLinkRepo;
import com.app.employeePortal.investor.repository.InvestorInvoiceRepo;
import com.app.employeePortal.investor.repository.InvestorNotesLinkRepo;
import com.app.employeePortal.investor.repository.InvestorOppStagesRepo;
import com.app.employeePortal.investor.repository.InvestorOpportunityRepo;
import com.app.employeePortal.investor.repository.InvestorRepository;
import com.app.employeePortal.investor.repository.InvestorShareRepo;
import com.app.employeePortal.investor.repository.InvestorSkillLinkRepo;
import com.app.employeePortal.leads.mapper.ActivityMapper;
import com.app.employeePortal.notification.entity.Notificationparam;
import com.app.employeePortal.notification.repository.NotificationRepository;
import com.app.employeePortal.notification.service.NotificationService;
import com.app.employeePortal.organization.entity.OrganizationDetails;
import com.app.employeePortal.organization.repository.OrganizationRepository;
import com.app.employeePortal.permission.entity.Permission;
import com.app.employeePortal.permission.entity.ThirdParty;
import com.app.employeePortal.permission.repository.PermissionRepository;
import com.app.employeePortal.permission.repository.ThirdPartyRepository;
import com.app.employeePortal.recruitment.entity.OpportunityRecruitDetails;
import com.app.employeePortal.recruitment.repository.RecruitmentOpportunityDetailsRepository;
import com.app.employeePortal.registration.entity.Country;
import com.app.employeePortal.registration.entity.Currency;
import com.app.employeePortal.registration.repository.CountryRepository;
import com.app.employeePortal.registration.repository.CurrencyRepository;
import com.app.employeePortal.registration.repository.DepartmentRepository;
import com.app.employeePortal.sector.entity.SectorDetails;
import com.app.employeePortal.sector.repository.SectorDetailsRepository;
import com.app.employeePortal.source.entity.Source;
import com.app.employeePortal.source.repository.SourceRepository;
import com.app.employeePortal.task.entity.EmployeeTaskLink;
import com.app.employeePortal.task.entity.InvestorTaskLink;
import com.app.employeePortal.task.entity.TaskDetails;
import com.app.employeePortal.task.entity.TaskDocumentLink;
import com.app.employeePortal.task.repository.EmployeeTaskRepository;
import com.app.employeePortal.task.repository.InvestorTaskRepo;
import com.app.employeePortal.task.repository.TaskDetailsRepository;
import com.app.employeePortal.task.repository.TaskDocumentLinkRepository;
import com.app.employeePortal.util.Utility;

import freemarker.template.TemplateException;

@Service
@Transactional
public class InvestorServiceImpl implements InvestorService {
	@Autowired
	DefinationInfoRepository definationInfoRepository;
	@Autowired
	InvestorInvoiceAddressLinkRepo investorInvoiceAddressLinkRepository;
	@Autowired
	InvestorInvoiceRepo investorInvoiceRepo;
	@Autowired
	DocumentDetailsRepository documentDetailsRepository;
	@Autowired
	OpportunityDetailsRepository opportunityDetailsRepository;
	@Autowired
	ContactRepository contactRepository;
	@Autowired
	InvestorDocumentLinkRepo investorDocumentLinkRepo;
	@Autowired
	InvestorSkillLinkRepo investorSkillLinkRepo;
	@Autowired
	InvestorRepository investorRepo;
	@Autowired
	NotificationRepository notificationRepository;
	@Autowired
	InvestorContactLinkRepo investorContactLinkRepo;
	@Autowired
	AddressRepository addressRepository;
	@Autowired
	AddressInfoRepository addressInfoRepository;
	@Autowired
	InvestorAddressLinkRepo investorAddressLinkRepo;
	@Autowired
	NotesRepository notesRepository;
	@Autowired
	InvestorNotesLinkRepo investorNotesLinkRepo;
	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	SectorDetailsRepository sectorDetailsRepository;
	@Autowired
	CountryRepository countryRepository;
	@Autowired
	DefinationRepository definationRepository;

	@Autowired
	PermissionRepository permissionRepository;
	@Autowired
	InvestorService investorService;

	@Autowired
	ThirdPartyRepository thirdPartyRepository;
	@Autowired
	ContactService contactService;
	@Autowired
	SourceRepository sourceRepository;
	@Autowired
	InvestorOpportunityRepo investorOpportunityRepo;

	@Autowired
	RecruitmentOpportunityDetailsRepository recruitmentOpportunityDetailsRepository;
	@Autowired
	EmployeeService employeeService;
	@Autowired
	InvestorCallLinkRepo investorCallLinkRepo;
	@Autowired
	CallDetailsRepository callDetailsRepository;
	@Autowired
	InvestorEventRepo investorEventRepo;
	@Autowired
	EventDetailsRepository eventDetailsRepository;
	@Autowired
	TaskDetailsRepository taskDetailsRepository;
	@Autowired
	InvestorTaskRepo investorTaskRepo;
	@Autowired
	TeamRepository teamRepository;
	@Autowired
	TeamMemberLinkRepo teamMemberLinkRepo;
	@Autowired
	NotificationService notificationService;
	@Autowired
	InvestorOppStagesRepo investorOppStagesRepo;
	@Autowired
	CurrencyRepository currencyRepository;
	@Autowired
	CurrencyConversionRepository currencyConversionRepository;
	@Autowired
	OrganizationRepository organizationRepository;
	@Autowired
	InOppConversionValueRepo inOppConversionValueRepo;
	@Autowired
	InvestorOppService investorOppService;
	@Autowired
	EmployeeCallRepository employeeCallRepository;
	@Autowired
	EmployeeEventRepository employeeEventRepository;
	@Autowired
	EmployeeTaskRepository employeeTaskRepository;
	@Autowired
	DepartmentRepository departmentRepository;
	@Autowired
	DocumentTypeRepository documentTypeRepository;
	@Autowired
	TaskDocumentLinkRepository taskDocumentLinkRepository;
	@Autowired
	InvestorShareRepo investorShareRepo;
	@Autowired
	ClubRepository clubRepository;

	@Value("${companyName}")
	private String companyName;
	@Autowired
	DocumentService documentService;
	@Autowired
	InvestorDocumentTypeRepo investorDocumentTypeRepo;
	@Autowired
	DistributorRepository distributorRepository;
	@Autowired
	YearlyDistributorRepository yearlyDistributorRepository;
	@Autowired
	TodayDistributorRepository todayDistributorRepository;
	@Autowired
	MonthlyDistributorRepository monthlyDistributorRepository;
	@Autowired
	DistributorAddressLinkRepository distributorAddressLinkRepository;
	@Autowired
	ContactInfoRepository contactInfoRepository;
	@Autowired
	DistributorContactPersonLinkRepository distributorContactPersonLinkRepository;
	@Autowired
	ContactAddressLinkRepository contactAddressLinkRepository;

	@Override
	public InvestorViewMapper saveInvestor(InvestorMapper investorMapper) throws IOException, TemplateException {

		InvestorViewMapper resultMapper = null;
		Investor investor = new Investor();
		setPropertyOnInput(investorMapper, investor);

		Investor investor1 = investorRepo.save(investor);

		System.out.println("investorMapper.isPvtAndIntunlInd()1============" + investorMapper.isPvtAndIntunlInd());

		/* insert to Contact Table if Investor is Private */
		if (!investorMapper.isPvtAndIntunlInd()) {
			System.out.println("investorMapper.isPvtAndIntunlInd()2============" + investorMapper.isPvtAndIntunlInd());
			ContactMapper contactMapper = new ContactMapper();
//			contactMapper.setSalutation(investorMapper.getSalutation());
			contactMapper.setFirstName(investorMapper.getName());
//			contactMapper.setMiddleName(investorMapper.getMiddleName());
//			contactMapper.setLastName(investorMapper.getLastName());
			contactMapper.setEmailId(investorMapper.getEmail());
			contactMapper.setPhoneNumber(investorMapper.getPhoneNumber());
			contactMapper.setMobileNumber(investorMapper.getPhoneNumber());
			contactMapper.setInvestorId(investor1.getInvestorId());
			contactMapper.setCountryDialCode(investorMapper.getCountryDialCode());
			contactMapper.setCountryDialCode1(investorMapper.getCountryDialCode());
			// contactMapper.setImageId(investorMapper.getImageId());
			contactMapper.setAddress(investorMapper.getAddress());
			contactMapper.setUserId(investorMapper.getUserId());
			contactMapper.setOrganizationId(investorMapper.getOrganizationId());

			ContactViewMapper resultMapperr = contactService.saveContact(contactMapper);
			System.out.println(
					"contactID===========++++++++++++++++++++++++++++++++++===" + resultMapperr.getContactId());

			/* insert to Investor-contact-link */
			if (null != resultMapperr.getContactId()) {

				InvestorContactLink investorContactLink = new InvestorContactLink();
				investorContactLink.setContactId(resultMapperr.getContactId());
				investorContactLink.setInvestorId(investor1.getInvestorId());
				investorContactLink.setCreationDate(new Date());
				investorContactLinkRepo.save(investorContactLink);

			}

		}

		/* insert to Notification Table */
		Notificationparam param = new Notificationparam();
		EmployeeDetails emp = employeeRepository.getEmployeesByuserId(investorMapper.getUserId());
		String name = employeeService.getEmployeeFullNameByObject(emp);
		param.setEmployeeDetails(emp);
		param.setAdminMsg("Investor " + "'" + investorMapper.getName() + "' created by " + name);
		param.setOwnMsg("Investor " + investorMapper.getName() + " created.");
		param.setNotificationType("Investor Creation");
		param.setProcessNmae("Investor");
		param.setType("create");
		param.setEmailSubject("Korero alert- Investor created");
		param.setCompanyName(companyName);
		param.setUserId(investorMapper.getUserId());

		if (!investorMapper.getUserId().equals(investorMapper.getAssignedTo())) {
			List<String> assignToUserIds = new ArrayList<>();
			assignToUserIds.add(investorMapper.getAssignedTo());
			param.setAssignToUserIds(assignToUserIds);
			param.setAssignToMsg("Investor " + "'" + investorMapper.getName() + "'" + " assigned to "
					+ employeeService.getEmployeeFullName(investorMapper.getAssignedTo()) + " by " + name);
		}
		notificationService.createNotificationForDynamicUsers(param);

		resultMapper = getInvestorDetailsById(investor1.getInvestorId());
		return resultMapper;
	}

	@Override
	public InvestorViewMapper getInvestorDetailsById(String investorId) {
		Investor investor = investorRepo.findById(investorId).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "investor not found with id " + investorId));
		System.out.println("investor object is..." + investor);

		InvestorViewMapper investorMapper = new InvestorViewMapper();

		if (null != investor) {

			if (investor.getSector() != null && !investor.getSector().trim().isEmpty()) {
				SectorDetails sector = sectorDetailsRepository.getSectorDetailsBySectorId(investor.getSector());
				System.out.println("get sectordetails by id returns........." + investor.getSector());
				System.out.println("sector object is......." + sector);
				if (sector != null) {
					investorMapper.setSector(sector.getSectorName());
					investorMapper.setSectorId(investor.getSector());
				} else {
					investorMapper.setSector("");
					investorMapper.setSectorId("");
				}
			}
			investorMapper.setInvestorId(investorId);
			investorMapper.setName(investor.getName());
			investorMapper.setUrl(investor.getUrl());
			investorMapper.setNotes(investor.getNotes());
			investorMapper.setEmail(investor.getEmail());
			investorMapper.setGroup(investor.getGroup());
			investorMapper.setVatNo(investor.getVatNo());
			investorMapper.setPhoneNumber(investor.getPhoneNumber());
			investorMapper.setCountryDialCode(investor.getCountryDialCode());
			investorMapper.setUserId(investor.getUserId());
			investorMapper.setOrganizationId(investor.getOrganizationId());
			investorMapper.setCreationDate(Utility.getISOFromDate(investor.getCreationDate()));
			investorMapper.setImageURL(investor.getImageURL());
			investorMapper.setGst(investor.getGst());

			if (!StringUtils.isEmpty(investor.getCountry())) {
				investorMapper.setCountry(investor.getCountry());
				Country country = countryRepository.getCountryDetailsByCountryNameAndOrgId(investor.getCountry(),
						investor.getOrganizationId());
				if (null != country) {
					investorMapper.setCountry_alpha3_code(country.getCountry_alpha3_code());
					investorMapper.setCountry_alpha2_code(country.getCountry_alpha2_code());
				}
			} else {
				List<InvestorAddressLink> investorAddressLinks = investorAddressLinkRepo
						.getAddressListByInvestorId(investor.getInvestorId());
				if (null != investorAddressLinks && !investorAddressLinks.isEmpty()) {
					for (InvestorAddressLink employeeAddressLink : investorAddressLinks) {
						AddressDetails addressDetails = addressRepository
								.getAddressDetailsByAddressId(employeeAddressLink.getAddressId());
						if (null != addressDetails) {
							if (!StringUtils.isEmpty(addressDetails.getCountry())) {
								investorMapper.setCountry(addressDetails.getCountry());
								Country country = countryRepository.getCountryDetailsByCountryNameAndOrgId(
										addressDetails.getCountry(), investor.getOrganizationId());
								if (null != country) {
									investorMapper.setCountry_alpha3_code(country.getCountry_alpha3_code());
									investorMapper.setCountry_alpha2_code(country.getCountry_alpha2_code());
								}
							}
						}
					}
				}
			}

			// investorMapper.setSector(sector.getSectorName());
			investorMapper.setZipcode(investor.getZipcode());

			investorMapper.setDocumentId(investor.getDocumentId());
			investorMapper.setCategory(investor.getCategory());
			investorMapper.setBusinessRegistration(investor.getBusinessRegistration());
			investorMapper.setSourceUserId(investor.getSourceUserId());
			investorMapper.setSourceUserName(employeeService.getEmployeeFullName(investor.getSourceUserId()));
			investorMapper.setAssignedBy(employeeService.getEmployeeFullName(investor.getAssignedBy()));
			investorMapper.setPvtAndIntunlInd(investor.isPvtAndIntunlInd());
			investorMapper.setFirstMeetingDate(Utility.getISOFromDate(investor.getFirstMeetingDate()));

			double totalunit = 0;
			double totalShareValue = 0;
			List<InvestorsShare> investorsShare = investorShareRepo.findByInvestorIdAndLiveInd(investorId, true);
			if (null != investorsShare && !investorsShare.isEmpty()) {
				for (InvestorsShare share : investorsShare) {
					totalunit = totalunit + share.getQuantityOfShare();
					totalShareValue = totalShareValue + share.getTotalAmountOfShare();
				}
				investorMapper.setAllTotalAmountOfShare(totalShareValue);
				investorMapper.setAllTotalQuantityOfShare(totalunit);
				investorMapper.setShareCurrency("EUR");
			}

			Club club = clubRepository.findByClubIdAndLiveInd(investor.getClub(), true);
			if (null != club) {
				investorMapper.setClub(club.getClubName());
			}

			if (null != investor.getSource() && !investor.getSource().isEmpty()) {
				Source source = sourceRepository.findBySourceId(investor.getSource());
				if (null != source) {
					investorMapper.setSource(source.getName());
				}
			}
			EmployeeDetails employeeDetails = employeeRepository.getEmployeeDetailsByEmployeeId(investor.getUserId());
			if (null != employeeDetails) {
				String middleName = " ";
				String lastName = "";

				if (!StringUtils.isEmpty(employeeDetails.getLastName())) {

					lastName = employeeDetails.getLastName();
				}

				if (null != employeeDetails.getMiddleName() && !employeeDetails.getMiddleName().isEmpty()) {

					middleName = employeeDetails.getMiddleName();
					investorMapper.setOwnerName(employeeDetails.getFirstName() + " " + middleName + " " + lastName);
					investorMapper.setOwnerImageId(employeeDetails.getImageId());
				} else {

					investorMapper.setOwnerName(employeeDetails.getFirstName() + " " + lastName);
					investorMapper.setOwnerImageId(employeeDetails.getImageId());
				}

			}
			EmployeeDetails employeeDetail = employeeRepository
					.getEmployeeDetailsByEmployeeId(investor.getAssignedTo());
			if (null != employeeDetail) {
				String middleName = " ";
				String lastName = "";

				if (!StringUtils.isEmpty(employeeDetail.getLastName())) {

					lastName = employeeDetail.getLastName();
				}

				if (employeeDetail.getMiddleName() != null && !employeeDetail.getMiddleName().isEmpty()) {

					middleName = employeeDetail.getMiddleName();
					investorMapper.setAssignedTo(employeeDetail.getFirstName() + " " + middleName + " " + lastName);

				} else {

					investorMapper.setAssignedTo(employeeDetail.getFirstName() + " " + lastName);

				}

			}

//        investorRecruitUpdate dbinvestorRecruitUpdate = investorRecruitUpdateRepository
//                .findByinvestorId(investor.getinvestorId());
//        if (null != dbinvestorRecruitUpdate) {
//            System.out.println("====" + dbinvestorRecruitUpdate.getId());
//            investorMapper.setLastRequirementOn(Utility.getISOFromDate(dbinvestorRecruitUpdate.getUpdatedDate()));
//        }

			List<InvestorOpportunity> oppinvestorsList = investorOpportunityRepo
					.getOpportunityListByInvestorIdAndLiveInd(investorId);
			if (null != oppinvestorsList && !oppinvestorsList.isEmpty()) {
				double totalValue = oppinvestorsList.stream().mapToDouble(li -> {
					double value = 0;
					if (!StringUtils.isEmpty(li.getProposalAmount())) {
						value = Double.parseDouble(li.getProposalAmount());
					}
					return value;
				}).sum();
				investorMapper.setTotalProposalValue(totalValue);
				investorMapper.setOppNo(oppinvestorsList.size());
			}

			List<InvestorOpportunity> oppinvestorsList1 = investorOpportunityRepo
					.getOpportunityListByInvestorIdAndLiveIndAndWonInd(investorId);
			if (null != oppinvestorsList1 && !oppinvestorsList1.isEmpty()) {
				double totalValue = oppinvestorsList1.stream().mapToDouble(li -> {
					double value = 0;
					if (!StringUtils.isEmpty(li.getProposalAmount())) {
						value = Double.parseDouble(li.getProposalAmount());
					}
					return value;
				}).sum();
				investorMapper.setTotalWonOppProposalValue(totalValue);
				investorMapper.setWonOppNo(oppinvestorsList1.size());
			}

			System.out.println("investorId" + investorId);
			InOppConversionValue inOppConversionValue = inOppConversionValueRepo.findByInvestorId(investorId);
			if (null != inOppConversionValue) {
				if (investor.getUserId().equalsIgnoreCase(inOppConversionValue.getUserId())) {
					investorMapper.setTotalProposalValue(inOppConversionValue.getUserConversionValue());
					investorMapper.setUserCurrency(inOppConversionValue.getUserConversionCurrency());
				} else {
					investorMapper.setTotalProposalValue(inOppConversionValue.getOrgConversionValue());
					investorMapper.setUserCurrency(inOppConversionValue.getOrgConversionCurrency());
				}
			}

			List<InvestorSkillLinkMapper> skillList = new ArrayList<InvestorSkillLinkMapper>();
			List<InvestorSkillLink> list = investorSkillLinkRepo.getByInvestorId(investorId);
			if (null != list && !list.isEmpty()) {
				for (InvestorSkillLink skillSetDetails : list) {
					InvestorSkillLink list2 = investorSkillLinkRepo.getById(skillSetDetails.getInvestorSkillLinkId());

					InvestorSkillLinkMapper mapper = new InvestorSkillLinkMapper();
					if (null != list2) {

						DefinationDetails definationDetails1 = definationRepository
								.findByDefinationId(list2.getSkillName());
						if (null != definationDetails1) {
							mapper.setSkillName(definationDetails1.getName());

						}

						mapper.setInvestorId(list2.getInvestorId());
						mapper.setInvestorSkillLinkId(list2.getInvestorSkillLinkId());
						skillList.add(mapper);
					}
				}
				investorMapper.setSkill(skillList);
			}

			List<AddressMapper> addressList = new ArrayList<AddressMapper>();
			List<InvestorAddressLink> investorAddressList = investorAddressLinkRepo
					.getAddressListByInvestorId(investorId);

			/* fetch investor address & set to investor mapper */
//            if (null != investorAddressList && !investorAddressList.isEmpty()) {
//
//                for (InvestorAddressLink investorAddressLink : investorAddressList) {
//                    AddressDetails addressDetails = addressRepository
//                            .getAddressDetailsByAddressId(investorAddressLink.getAddressId());
//
//                    AddressMapper addressMapper = new AddressMapper();
//                    if (null != addressDetails) {
//
//                        addressMapper.setAddress1(addressDetails.getAddressLine1());
//                        addressMapper.setAddress2(addressDetails.getAddressLine2());
//                        // addressMapper.setAddressType(addressDetails.getAddress_type());
//                        addressMapper.setPostalCode(addressDetails.getPostalCode());
//
//                        addressMapper.setAddress1(addressDetails.getAddressLine1());
//                        addressMapper.setAddress2(addressDetails.getAddressLine2());
//                        addressMapper.setAddressType(addressDetails.getAddressType());
//                        addressMapper.setPostalCode(addressDetails.getPostalCode());
//
//                        addressMapper.setStreet(addressDetails.getStreet());
//                        addressMapper.setCity(addressDetails.getCity());
//                        addressMapper.setTown(addressDetails.getTown());
//                        addressMapper.setCountry(addressDetails.getCountry());
//                        addressMapper.setLatitude(addressDetails.getLatitude());
//                        addressMapper.setLongitude(addressDetails.getLongitude());
//                        addressMapper.setState(addressDetails.getState());
//                        addressMapper.setAddressId(addressDetails.getAddressId());
//                        addressMapper.setHouseNo(addressDetails.getHouseNo());
//                        Country country1 = countryRepository.getCountryDetailsByCountryNameAndOrgId(
//                                addressDetails.getCountry(), investor.getOrganizationId());
//                        if (null != country1) {
//                            addressMapper.setCountry_alpha2_code(country1.getCountry_alpha2_code());
//                            addressMapper.setCountry_alpha3_code(country1.getCountry_alpha3_code());
//                        }
//                        addressList.add(addressMapper);
//                    }
//                }
//                System.out.println("addressList.......... " + addressList);
//            }
//            investorMapper.setAddress(addressList);
//            Country country1 = countryRepository.getCountryDetailsByCountryNameAndOrgId(
//                    investorMapper.getAddress().get(0).getCountry(), investor.getOrganizationId());
//            if (null != country1) {
//                investorMapper.setCountry_alpha2_code(country1.getCountry_alpha2_code());
//                investorMapper.setCountry_alpha3_code(country1.getCountry_alpha3_code());
//            }
		}
		return investorMapper;
	}

	private void setPropertyOnInput(InvestorMapper investorMapper, Investor investor) {
		investor.setName(investorMapper.getName());
		investor.setUrl(investorMapper.getUrl());
		investor.setNotes(investorMapper.getNotes());
		investor.setCountryDialCode(investorMapper.getCountryDialCode());
		investor.setPhoneNumber(investorMapper.getPhoneNumber());
		investor.setEmail(investorMapper.getEmail());
		investor.setUserId(investorMapper.getUserId());
		investor.setOrganizationId(investorMapper.getOrganizationId());
		investor.setGroup(investorMapper.getGroup());
		investor.setVatNo(investorMapper.getVatNo());
		investor.setCreationDate(new Date());
		investor.setLiveInd(true);
		investor.setDocumentId(investorMapper.getDocumentId());
		investor.setSector(investorMapper.getSectorId());
		System.out.println("get sector in frontend............" + investorMapper.getSectorId());
		investor.setCountry(investorMapper.getCountry());
		investor.setZipcode(investorMapper.getZipcode());
		investor.setZipcode(investorMapper.getZipcode());
		investor.setCategory(investorMapper.getCategory());
		investor.setImageURL(investorMapper.getImageURL());
		investor.setAssignedTo(investorMapper.getAssignedTo());
		investor.setBusinessRegistration(investorMapper.getBusinessRegistration());
		investor.setGst(investorMapper.getGst());
		investor.setSource(investorMapper.getSource());
		investor.setSourceUserId(investorMapper.getSourceUserId());
		investor.setAssignedBy(investorMapper.getUserId());
		investor.setPvtAndIntunlInd(investorMapper.isPvtAndIntunlInd());
		try {
			investor.setFirstMeetingDate(Utility.getDateFromISOString(investorMapper.getFirstMeetingDate()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		Investor investor1 = investorRepo.save(investor);
		if (!investorMapper.getAddress().isEmpty()) {
			for (AddressMapper addressMapper : investorMapper.getAddress()) {
				/* insert to address info & address deatils & investoraddressLink */

				AddressInfo addressInfo = new AddressInfo();
				addressInfo.setCreationDate(new Date());

				AddressInfo addressInfoo = addressInfoRepository.save(addressInfo);

				String addressId = addressInfoo.getId();

				if (null != addressId) {

					AddressDetails addressDetails = getAddressDetails(addressMapper, addressId);
					addressDetails.setXlAddress(addressMapper.getXlAddress());
					addressRepository.save(addressDetails);

					InvestorAddressLink investorAddressLink = new InvestorAddressLink();
					investorAddressLink.setInvestorId(investor1.getInvestorId());
					investorAddressLink.setAddressId(addressId);
					investorAddressLink.setCreationDate(new Date());

					investorAddressLinkRepo.save(investorAddressLink);

				}
			}
		}

		/* insert to notes */
		if (null != investorMapper.getNotes() && !investorMapper.getNotes().isEmpty()) {
			String notesId = null;

			Notes notes = new Notes();
			notes.setNotes(investorMapper.getNotes());
			notes.setCreation_date(new Date());
			notes.setLiveInd(true);
			Notes note = notesRepository.save(notes);
			notesId = note.getNotes_id();

			/* insert to investor-notes-link */

			InvestorNoteLink investorNotesLink = new InvestorNoteLink();
			investorNotesLink.setInvestorId(investor1.getInvestorId());
			investorNotesLink.setNoteId(notesId);
			investorNotesLink.setCreationDate(new Date());

			investorNotesLinkRepo.save(investorNotesLink);
		}

		/* insert to Notification Table */
		String message = "A investor is created By ";
		notificationService.createNotification(investorMapper.getUserId(), "investor create", message, "investor",
				"create");
	}

	@NotNull
	private static AddressDetails getAddressDetails(AddressMapper addressMapper, String addressId) {
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
		return addressDetails;
	}

//	@Override
//	public List<InvestorViewMapper> getAllInvestorList(int pageNo, int pageSize) {
//		List<Permission> list = permissionRepository.getUserList();
//		System.out.println(" user$$$$$$$$$$$$==" + list.toString());
//
//		if (!list.isEmpty()) {
//			return list.stream().map(permission -> {
//				List<InvestorViewMapper> mp = investorService.getInvestorsPageWiseByUserId(permission.getUserId(),
//						pageNo, pageSize);
//
//				System.out.println(" userId$$$$$$$$$$$$==" + permission.getUserId());
//				return mp;
//			}).filter(Objects::nonNull).flatMap(Collection::stream).collect(Collectors.toList());
//
//		}
//		return null;
//
//	}

	@Override
	public List<InvestorViewMapper> getInvestorsPageWiseByUserId(String userId, int pageNo, int pageSize) {
		List<InvestorViewMapper> investorViewMappers = new ArrayList<>();
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("creationDate").descending());
		Page<Investor> investors = investorRepo.getInvestorsListByUserId(userId, paging);
		System.out.println("###########" + investors);
		if (null != investors && !investors.isEmpty()) {
			investorViewMappers = investors.stream().map(investor -> {
				InvestorViewMapper mapper = null;
				if (null != investor) {

					mapper = getInvestorDetailsById(investor.getInvestorId());
					mapper.setPageCount(investors.getTotalPages());
					mapper.setDataCount(investors.getSize());
					mapper.setListCount(investors.getTotalElements());
				}
				return mapper;

			})

					.collect(Collectors.toList());
		}
		return investorViewMappers;
	}

	@Override
	public InvestorViewMapper updateInvestor(String investorId, InvestorMapper investorMapper)
			throws IOException, TemplateException {
		InvestorViewMapper resultMapper = null;

		Investor newinvestor = investorRepo.getById(investorId);

		if (null != newinvestor) {
			if (null != investorMapper.getName()) {
				newinvestor.setName(investorMapper.getName());
			}

			if (null != investorMapper.getSource() && !investorMapper.getSource().isEmpty()) {
				newinvestor.setSource(investorMapper.getSource());
			}

			if (null != investorMapper.getUrl()) {
				newinvestor.setUrl(investorMapper.getUrl());
			}

			if (null != investorMapper.getNotes()) {
				List<InvestorNoteLink> list = investorNotesLinkRepo.getNotesIdByInvestorId(investorId);
				if (null != list && !list.isEmpty()) {
					list.sort((m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
					Notes notes = notesRepository.findByNoteId(list.get(0).getNoteId());
					if (null != notes) {
						notes.setNotes(investorMapper.getNotes());
						notesRepository.save(notes);
					}
				}
			}

			newinvestor.setPvtAndIntunlInd(investorMapper.isPvtAndIntunlInd());

			if (null != investorMapper.getPhoneNumber()) {

				newinvestor.setPhoneNumber(investorMapper.getPhoneNumber());
			}

			if (null != investorMapper.getFirstMeetingDate()) {
				try {
					newinvestor.setFirstMeetingDate(Utility.getDateFromISOString(investorMapper.getFirstMeetingDate()));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			if (null != investorMapper.getCountryDialCode()) {

				newinvestor.setCountryDialCode(investorMapper.getCountryDialCode());
			}

			if (null != investorMapper.getCountry()) {

				newinvestor.setCountry(investorMapper.getCountry());
			}

			if (null != investorMapper.getSectorId()) {

				newinvestor.setSector(investorMapper.getSectorId());
			}

			if (null != investorMapper.getZipcode()) {

				newinvestor.setZipcode(investorMapper.getZipcode());
			}

			if (null != investorMapper.getEmail()) {

				newinvestor.setEmail(investorMapper.getEmail());
			}
			if (null != investorMapper.getVatNo()) {

				newinvestor.setVatNo(investorMapper.getVatNo());
			}
			newinvestor.setUserId(newinvestor.getUserId());
			newinvestor.setOrganizationId(newinvestor.getOrganizationId());

			if (null != investorMapper.getDocumentId()) {

				newinvestor.setDocumentId(investorMapper.getDocumentId());
			}

			if (null != investorMapper.getCategory()) {

				newinvestor.setCategory(investorMapper.getCategory());
			}
			if (null != investorMapper.getAssignedTo()) {

				newinvestor.setAssignedTo(investorMapper.getAssignedTo());
			}
			if (null != investorMapper.getBusinessRegistration()) {

				newinvestor.setBusinessRegistration(investorMapper.getBusinessRegistration());
			}
			// investorMapper.setCreationDate(Utility.getISOFromDate(investor.getCreation_date()))

			if (null != investorMapper.getAssignedTo()
					&& !newinvestor.getAssignedBy().equals(investorMapper.getAssignedTo())) {
				newinvestor.setAssignedBy(investorMapper.getUserId());
			}

			newinvestor.setUpdateDate(new Date());
//            newinvestor.setLiveInd(true);

			Investor updatedinvestor = investorRepo.save(newinvestor);

			if (null != investorMapper.getAddress()) {
				List<AddressMapper> addressList = investorMapper.getAddress();

				for (AddressMapper addressMapper : addressList) {

					InvestorAddressLink investorAddressLink = investorAddressLinkRepo.findByInvestorId(investorId);
					String addId = investorAddressLink.getAddressId();
					if (null != addId) {

						AddressDetails addressDetails = addressRepository.getAddressDetailsByAddressId(addId);

						if (null != addressMapper.getAddress1()) {
							addressDetails.setAddressLine1(addressMapper.getAddress1());

						} else {
							addressDetails.setAddressLine1(addressDetails.getAddressLine1());
						}

						if (null != addressMapper.getAddress2()) {
							addressDetails.setAddressLine2(addressMapper.getAddress2());
						} else {
							addressDetails.setAddressLine2(addressDetails.getAddressLine2());
						}
						if (null != addressMapper.getAddressType()) {
							addressDetails.setAddressType(addressMapper.getAddressType());
						} else {
							addressDetails.setAddressType(addressDetails.getAddressType());
						}
						if (null != addressMapper.getTown()) {
							addressDetails.setTown(addressMapper.getTown());
						} else {
							addressDetails.setTown(addressDetails.getTown());
						}
						if (null != addressMapper.getStreet()) {
							addressDetails.setStreet(addressMapper.getStreet());
						} else {
							addressDetails.setStreet(addressDetails.getStreet());
						}

						if (null != addressMapper.getCity()) {
							addressDetails.setCity(addressMapper.getCity());
						} else {
							addressDetails.setCity(addressDetails.getCity());
						}

						if (null != addressMapper.getPostalCode()) {
							addressDetails.setPostalCode(addressMapper.getPostalCode());
						} else {
							addressDetails.setPostalCode(addressDetails.getPostalCode());
						}

						if (null != addressMapper.getState()) {
							addressDetails.setState(addressMapper.getState());
						} else {
							addressDetails.setState(addressDetails.getState());
						}

						if (null != addressMapper.getCountry()) {
							addressDetails.setCountry(addressMapper.getCountry());
						} else {
							addressDetails.setTown(addressDetails.getTown());
						}

						if (null != addressMapper.getLatitude()) {
							addressDetails.setLatitude(addressMapper.getLatitude());
						} else {
							addressDetails.setLatitude(addressDetails.getLatitude());
						}

						if (null != addressMapper.getLongitude()) {
							addressDetails.setLongitude(addressMapper.getLongitude());
						} else {
							addressDetails.setLongitude(addressDetails.getLongitude());
						}

						if (null != addressMapper.getHouseNo()) {
							addressDetails.setHouseNo(addressMapper.getHouseNo());
						} else {
							addressDetails.setHouseNo(addressDetails.getHouseNo());
						}

						addressDetails.setCreatorId("");
						addressDetails.setCreationDate(new Date());
//                        addressDetails.setLiveInd(true);
						AddressDetails addressDetails1 = addressRepository.save(addressDetails);
//                        String addressDetailsId = addressDetails1.getAddressId();
					}

				}
			}

			/* insert to Notification Table */
			Notificationparam param = new Notificationparam();
			EmployeeDetails emp = employeeRepository.getEmployeesByuserId(investorMapper.getUserId());
			String name = employeeService.getEmployeeFullNameByObject(emp);
			param.setEmployeeDetails(emp);
			param.setAdminMsg("Investor " + "'" + investorMapper.getName() + "' updated by " + name);
			param.setOwnMsg("Investor " + investorMapper.getName() + " updated.");
			param.setNotificationType("Investor updation");
			param.setProcessNmae("Investor");
			param.setType("update");
			param.setEmailSubject("Korero alert- Investor updated");
			param.setCompanyName(companyName);
			param.setUserId(investorMapper.getUserId());

			if (investorMapper.getUserId().equals(newinvestor.getAssignedTo())) {
				List<String> assignToUserIds = new ArrayList<>();
				assignToUserIds.add(investorMapper.getAssignedTo());
				param.setAssignToUserIds(assignToUserIds);
				param.setAssignToMsg("Investor " + "'" + investorMapper.getName() + "' updated by " + name);
			} else {
				List<String> assignToUserIds = new ArrayList<>();
				assignToUserIds.add(investorMapper.getAssignedTo());
				param.setAssignToUserIds(assignToUserIds);
				param.setAssignToMsg("Investor " + "'" + investorMapper.getName() + "'" + " assigned to "
						+ employeeService.getEmployeeFullName(investorMapper.getAssignedTo()) + " by " + name);
			}
			notificationService.createNotificationForDynamicUsers(param);

			resultMapper = getInvestorDetailsById(updatedinvestor.getInvestorId());
		}
		return resultMapper;

	}

	@Override
	public List<ContactViewMapper> getContactListByInvestorId(String investorId) {
		List<ContactViewMapper> contactViewMappers = new ArrayList<>();
		List<InvestorContactLink> customerContactLinkList = investorContactLinkRepo
				.getContactIdByInvestorId(investorId);
		if (customerContactLinkList != null && !customerContactLinkList.isEmpty()) {
			return customerContactLinkList.stream().map(customerContactLink -> {
				ContactViewMapper contactMapper = contactService
						.getContactDetailsById(customerContactLink.getContactId());
				ThirdParty thirdParty = thirdPartyRepository.findByOrgId(contactMapper.getOrganizationId());
				if (thirdParty != null) {
					contactMapper.setThirdPartyAccessInd(thirdParty.isCustomerContactInd());
				}
				return contactMapper;
			}).collect(Collectors.toList());

		}
		return contactViewMappers;
	}

	@Override
	public List<InvestorViewMapper> getAllInvestor() {
		return investorRepo.findByLiveInd().stream().map(i -> {
			return investorService.getInvestorDetailsById(i.getInvestorId());
		}).collect(Collectors.toList());
	}

	@Override
	public List<NotesMapper> getNoteListByInvestorId(String investorId) {
		List<NotesMapper> list = new ArrayList<>();
		List<InvestorNoteLink> investorNoteLinks = investorNotesLinkRepo.getNotesIdByInvestorId(investorId);
		if (investorNoteLinks != null && !investorNoteLinks.isEmpty()) {
			return investorNoteLinks.stream().map(investorNotesLink -> {
				return getNotes(investorNotesLink.getNoteId());
			}).collect(Collectors.toList());
		}
		return list;
	}

	public NotesMapper getNotes(String id) {
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

	@Override
	public String saveInvestorNotes(NotesMapper notesMapper) {
		String investorNoteId = null;
		if (null != notesMapper) {
			Notes notes = new Notes();
			notes.setNotes(notesMapper.getNotes());
			notes.setCreation_date(new Date());
			notes.setUserId(notesMapper.getEmployeeId());
			notes.setLiveInd(true);
			Notes note = notesRepository.save(notes);
			investorNoteId = note.getNotes_id();

			/* insert to customer-notes-link */

			InvestorNoteLink customerNotesLink = new InvestorNoteLink();
			customerNotesLink.setInvestorId(notesMapper.getInvestorId());
			customerNotesLink.setNoteId(investorNoteId);
			customerNotesLink.setCreationDate(new Date());

			investorNotesLinkRepo.save(customerNotesLink);

		}
		return investorNoteId;
	}

	@Override
	public ContactViewMapper saveInvestorContact(ContactMapper contactMapper) throws IOException, TemplateException {
		ContactViewMapper resultMapper = contactService.saveContact(contactMapper);

		ThirdParty pem1 = thirdPartyRepository.findByOrgId(resultMapper.getOrganizationId());
		if (null != pem1) {
			resultMapper.setThirdPartyAccessInd(pem1.isCustomerContactInd());
		}

		return resultMapper;
	}

	@Override
	public List<DocumentMapper> getDocumentListByInvestorId(String investorId) {
		List<DocumentMapper> resultList = new ArrayList<>();
		List<InvestorDocumentLink> customerDocumentLinkList = investorDocumentLinkRepo
				.getDocumentByInvestorId(investorId);
		Set<String> documentIds = customerDocumentLinkList.stream().map(InvestorDocumentLink::getDocumentId)
				.collect(Collectors.toSet());
		if (documentIds != null && !documentIds.isEmpty()) {
			documentIds.stream().map(documentId -> {
				DocumentMapper mapper = documentService.getDocument(documentId);
				if (null != mapper.getDocumentId()) {
					resultList.add(mapper);
				}
				return mapper;
			}).collect(Collectors.toList());

		}

		return resultList;
	}

	@Override
	public List<InvestorViewMapper> getInvestorListByName(String name) {

		List<Investor> detailsList = investorRepo.findByLiveInd(true);
		List<Investor> filterList = detailsList.parallelStream().filter(detail -> {
			return detail.getName() != null && Utility.containsIgnoreCase(detail.getName(), name.trim());
		}).collect(Collectors.toList());
		List<InvestorViewMapper> mapperList = new ArrayList<InvestorViewMapper>();
		if (null != filterList && !filterList.isEmpty()) {

			mapperList = filterList.parallelStream().map(li -> getInvestorDetailsById(li.getInvestorId()))
					.collect(Collectors.toList());

		}

		return mapperList;

	}

	@Override
	public List<InvestorViewMapper> getInvestors(int pageNo, int pageSize) {
		List<Permission> list = permissionRepository.getUserList();
		System.out.println(" user$$$$$$$$$$$$==" + list.toString());

		if (null != list && !list.isEmpty()) {
			return list.stream().map(permission -> {
				List<InvestorViewMapper> mp = investorService.getInvestorsPageWiseByUserId(permission.getUserId(),
						pageNo, pageSize);

				System.out.println(" userId$$$$$$$$$$$$==" + permission.getUserId());
				return mp;
			}).filter(l -> l != null).flatMap(l -> l.stream()).collect(Collectors.toList());

		}
		return null;
	}

	@Override
	public HashMap getCountListByuserId(String userId) {
		List<Investor> list = investorRepo.getByUserIdandLiveInd(userId);
		HashMap map = new HashMap();
		map.put("investor", list.size());

		return map;
	}

	@Override
	public HashMap getInvestorCountByOrgId(String orgId) {
		List<Investor> list = investorRepo.getInvestorListByOrgId(orgId);
		HashMap map = new HashMap();
		map.put("investor", list.size());

		return map;
	}

	@Override
	public HashMap getCountNoOfContactByUserId(String userId) {
		List<ContactDetails> contactList = contactRepository.getInvesterContactByUserIdAndContactTypeAndLiveInd(userId,
				"Investor");
		HashMap map = new HashMap();
		map.put("contactDetails", contactList.size());
		return map;
	}

	@Override
	public InvestorKeySkillMapper getKeySkilListByInvestorIdAndDateRange(String investorId, String orgId,
			String startDate, String endDate) {
		Date end_date = null;
		Date start_date = null;
		try {
			end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}

		InvestorKeySkillMapper keySkillList = new InvestorKeySkillMapper();
		List<OpportunityDetails> opportunityDetails = opportunityDetailsRepository
				.getOpportunityListByCustomerIdAndLiveIndAndDateRange(investorId, start_date, end_date);
		List<String> skillLibery = investorService.getSkillSetOfSkillLibery(orgId);

		if (null != opportunityDetails) {
			for (OpportunityDetails opp : opportunityDetails) {

				List<OpportunityRecruitDetails> opportunityRecruitDetails = recruitmentOpportunityDetailsRepository
						.getRecriutmentByOpportunityIdAndLiveInd(opp.getOpportunityId());

				if (null != opportunityRecruitDetails) {
					for (OpportunityRecruitDetails req : opportunityRecruitDetails) {

						ArrayList<String> requiredSkills = new ArrayList<>();
						String description = req.getDescription().replace(",", " ");

						if (!StringUtils.isEmpty(description)) {
							// List<String> descriptionList = Arrays.asList(description.split("\\s*,\\s*"));

							List<String> descriptionList = Arrays.asList(description.split(" "));
							for (String word : skillLibery) {
								for (String description1 : descriptionList) {
									if (!requiredSkills.contains(word)) {
										if (description1.equalsIgnoreCase(word)) {
											requiredSkills.add(word);
										}
									}
								}
							}
							keySkillList.setSkillSetList(requiredSkills);

							// skills =
							// requiredSkills.stream().map(n->String.valueOf(n)).collect(Collectors.joining(","));
						}
					}
				}
				// keySkillList.add(customerKeySkillMapper);

			}

		}
		return keySkillList;
	}

	@Override
	public List<String> getSkillSetOfSkillLibery(String orgId) {

		List<String> skills = null;

		List<DefinationDetails> list = definationRepository.getDefinationsOfAdmin(orgId);
		if (null != list && !list.isEmpty()) {

			skills = (list.stream().map(DefinationDetails::getName).collect(Collectors.toList()));
		}

		return skills;
	}

	@Override
	public String saveinvoiceInvestor(InvestorInvoiceMapper customerInvoiceMapper) {
		String investorInvoiceId = null;

		InvestorInvoice invoiceCustomerDetails = new InvestorInvoice();

		invoiceCustomerDetails.setId(customerInvoiceMapper.getInvestorInvoiceId());
		invoiceCustomerDetails.setCreationDate(new Date());
		invoiceCustomerDetails.setInvoiceAmount(customerInvoiceMapper.getInvoiceAmount());
		invoiceCustomerDetails.setInvoiceNumber(customerInvoiceMapper.getInvoiceNumber());
		invoiceCustomerDetails.setCurrency(customerInvoiceMapper.getCurrency());
		invoiceCustomerDetails.setStatus(customerInvoiceMapper.getStatus());
		invoiceCustomerDetails.setDocumentId(customerInvoiceMapper.getDocumentId());

		// invoiceCustomerDetails.setLiveInd(true);

		investorInvoiceId = investorInvoiceRepo.save(invoiceCustomerDetails).getId();

		if (!customerInvoiceMapper.getAddress().isEmpty()) {
			for (AddressMapper addressMapper : customerInvoiceMapper.getAddress()) {
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

					InvestorInvoiceAddressLink investorInvoiceAddressLink = new InvestorInvoiceAddressLink();
					investorInvoiceAddressLink.setInvestorInvoiceId(investorInvoiceId);
					investorInvoiceAddressLink.setAddressId(addressId);
					investorInvoiceAddressLink.setCreationDate(new Date());

					investorInvoiceAddressLinkRepository.save(investorInvoiceAddressLink);

				}
			}
		}
		return investorInvoiceId;
	}

	@Override
	public List<InvestorInvoiceMapper> getInvoiceListByInvestorId(String investorId) {
		List<InvestorInvoiceMapper> mapperList = new ArrayList<>();

		List<InvestorInvoiceAddressLink> investorInvoiceAddressLinkList = investorInvoiceAddressLinkRepository
				.findByInvestorId(investorId);
		List<AddressMapper> addressList = new ArrayList<AddressMapper>();
		List<InvestorInvoice> list = investorInvoiceRepo.findByInvestorId(investorId);
		if (null != list && !list.isEmpty()) {

			list.stream().map(li -> {
				InvestorInvoiceMapper investorInvoiceMapper = new InvestorInvoiceMapper();

				investorInvoiceMapper.setInvestorInvoiceId(li.getId());
				investorInvoiceMapper.setCreationDate(Utility.getISOFromDate(li.getCreationDate()));
				investorInvoiceMapper.setInvoiceAmount(li.getInvoiceAmount());
				investorInvoiceMapper.setInvoiceNumber(li.getInvoiceNumber());
				investorInvoiceMapper.setCurrency(li.getCurrency());
				investorInvoiceMapper.setStatus(li.getStatus());
				investorInvoiceMapper.setDocumentId(li.getDocumentId());

				if (null != investorInvoiceAddressLinkList && !investorInvoiceAddressLinkList.isEmpty()) {

					for (InvestorInvoiceAddressLink investorInvoiceAddressLink : investorInvoiceAddressLinkList) {
						AddressDetails addressDetails = addressRepository
								.getAddressDetailsByAddressId(investorInvoiceAddressLink.getAddressId());

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
					}
					investorInvoiceMapper.setAddress(addressList);

				}
				mapperList.add(investorInvoiceMapper);
				return mapperList;
			}).collect(Collectors.toList());
		}
		return mapperList;
	}

	@Override
	public boolean checkSkillInInvestorSkillSet(String skillName, String investorId) {
		List<DefinationDetails> definationDetails = definationRepository.findByNameContaining(skillName);
		for (DefinationDetails definationDetails2 : definationDetails) {
			if (null != definationDetails2) {
				List<InvestorSkillLink> investorSkillLink = investorSkillLinkRepo
						.findBySkillNameAndInvestorId(definationDetails2.getDefinationId(), investorId);
				if (!investorSkillLink.isEmpty()) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public String saveSkillSet(InvestorSkillLinkMapper investorSkillLinkMapper) {
		DefinationDetails definationDetails1 = definationRepository
				.getBySkillNameAndLiveInd(investorSkillLinkMapper.getSkillName());

		if (null != definationDetails1) {

			InvestorSkillLink customerSkillLink1 = new InvestorSkillLink();
			customerSkillLink1.setSkillName(definationDetails1.getDefinationId());
			customerSkillLink1.setInvestorId(investorSkillLinkMapper.getInvestorId());
			customerSkillLink1.setCreationDate(new Date());
			customerSkillLink1.setEditInd(true);
			customerSkillLink1.setUserId(investorSkillLinkMapper.getOrgId());
			customerSkillLink1.setOrgId(investorSkillLinkMapper.getOrgId());
			investorSkillLinkRepo.save(customerSkillLink1);

		} else {

			DefinationInfo definationInfo = new DefinationInfo();

			definationInfo.setCreation_date(new Date());
			String id = definationInfoRepository.save(definationInfo).getDefination_info_id();

			DefinationDetails newDefinationDetails = new DefinationDetails();
			newDefinationDetails.setDefinationId(id);
			newDefinationDetails.setName(investorSkillLinkMapper.getSkillName());
			newDefinationDetails.setOrg_id(investorSkillLinkMapper.getOrgId());
			newDefinationDetails.setUser_id(investorSkillLinkMapper.getUserId());
			newDefinationDetails.setCreation_date(new Date());
			newDefinationDetails.setLiveInd(true);
			newDefinationDetails.setEditInd(true);
			definationRepository.save(newDefinationDetails);

			InvestorSkillLink customerSkillLink1 = new InvestorSkillLink();
			customerSkillLink1.setSkillName(id);
			customerSkillLink1.setInvestorId(investorSkillLinkMapper.getInvestorId());
			customerSkillLink1.setCreationDate(new Date());
			customerSkillLink1.setEditInd(true);
			customerSkillLink1.setUserId(investorSkillLinkMapper.getOrgId());
			customerSkillLink1.setOrgId(investorSkillLinkMapper.getOrgId());
			investorSkillLinkRepo.save(customerSkillLink1);

		}

		return investorSkillLinkMapper.getSkillName();

	}

	@Override
	public List<InvestorSkillLinkMapper> getSkillSetByInvestorId(String investorId) {
		List<InvestorSkillLinkMapper> investorSkillLinkMappers = new ArrayList<>();
		List<InvestorSkillLink> skillList = investorSkillLinkRepo.getByInvestorId(investorId);
		if (null != skillList && !skillList.isEmpty()) {
			return skillList.stream().map(customerSkillLink -> {
				InvestorSkillLinkMapper customerSkillLinkMapper = new InvestorSkillLinkMapper();
				DefinationDetails definationDetails1 = definationRepository
						.findByDefinationId(customerSkillLink.getSkillName());
				if (null != definationDetails1) {
					customerSkillLinkMapper.setSkillName(definationDetails1.getName());

				}
				customerSkillLinkMapper.setInvestorId(customerSkillLink.getInvestorId());
				customerSkillLinkMapper.setCreationDate(Utility.getISOFromDate(customerSkillLink.getCreationDate()));
				customerSkillLinkMapper.setInvestorSkillLinkId(customerSkillLink.getInvestorSkillLinkId());
				customerSkillLinkMapper.setEditInd(customerSkillLink.isEditInd());
				customerSkillLinkMapper.setUserId(customerSkillLink.getOrgId());
				customerSkillLinkMapper.setOrgId(customerSkillLink.getOrgId());
				return customerSkillLinkMapper;

			}).collect(Collectors.toList());
		}
		return investorSkillLinkMappers;
	}

	@Override
	public List<InvestorViewMapperForDropDown> getInvestorByUserIdForDropDown(String userId) {
		List<Investor> investors = investorRepo.findByUserIdandLiveInd(userId);

		if (null != investors && !investors.isEmpty()) {
			return investors.stream().map(investor -> {
				InvestorViewMapperForDropDown investorMapper = new InvestorViewMapperForDropDown();
				investorMapper.setInvestorId(investor.getInvestorId());
				investorMapper.setName(investor.getName());
				investorMapper.setCreationDate(Utility.getISOFromDate(investor.getCreationDate()));
				return investorMapper;
			}).collect(Collectors.toList());
		}
		return null;
	}

	@Override
	public HashMap getInvestorContactCountByInvestorId(String investorId) {
		List<InvestorContactLink> investorContactLinkList = investorContactLinkRepo
				.getContactIdByInvestorId(investorId);
		HashMap map = new HashMap();
		int count = 0;
		for (InvestorContactLink investorContactLink : investorContactLinkList) {
			ContactDetails contact = contactRepository.getcontactDetailsById(investorContactLink.getContactId());
			if (null != contact) {
				count++;

			}

		}
		map.put("contact", count);

		return map;
	}

	@Override
	public HashMap getDocumentCountByInvestorId(String investorId) {
		List<InvestorDocumentLink> investorDocumentLinkList = investorDocumentLinkRepo
				.getDocumentByInvestorId(investorId);
		Set<String> documentIds = investorDocumentLinkList.stream().map(InvestorDocumentLink::getDocumentId)
				.collect(Collectors.toSet());
		int count = 0;
		HashMap map = new HashMap();
		for (String documentId : documentIds) {
			DocumentDetails doc = documentDetailsRepository.getDocumentDetailsById(documentId);
			if (null != doc) {
				count++;
			}

		}

		map.put("document", count);
		return map;
	}

	@Override
	public List<String> updateTransferOneUserToAnother(String userId, TransferMapper transferMapper) {
		List<String> list = transferMapper.getInvestorIds();
		System.out.println("custoList::::::::::::::::::::::::::::::::::::::::::::::::::::" + list);
		if (null != list && !list.isEmpty()) {
			for (String investorId : list) {
				System.out.println("the customer id is : " + investorId);
				Investor investor = investorRepo.getById(investorId);
				System.out.println("customer::::::::::::::::::::::::::::::::::::::::::::::::::::" + investor);
				investor.setUserId(userId);
				investorRepo.save(investor);
			}

		}
		return list;
	}

	@Override
	public List<Map<String, Double>> getInvestorCountSourceWiseByUserId(String userId, String orgId) {
		List<Map<String, Double>> map = new ArrayList<>();

		List<Source> sourceList = sourceRepository.findByOrgIdAndLiveInd(orgId, true);
		if (null != sourceList && !sourceList.isEmpty()) {
			for (Source source : sourceList) {
				Map map1 = new HashMap<>();
				List<Investor> investors = investorRepo.findByUserIdAndSourceAndLiveInd(userId, source.getSourceId(),
						true);

				map1.put("source", source.getName());
				map1.put("number", investors.size());
				map.add(map1);
			}
		}

		return map;
	}

	@Override
	public List<Map<String, List<InvestorViewMapper>>> getInvestorListSourceWiseByUserId(String userId, String orgId) {
		List<Map<String, List<InvestorViewMapper>>> map = new ArrayList<>();

		List<Source> sourceList = sourceRepository.findByOrgIdAndLiveInd(orgId, true);
		if (null != sourceList && !sourceList.isEmpty()) {
			for (Source source : sourceList) {
				Map map1 = new HashMap<>();
				List<InvestorViewMapper> resulList = new ArrayList<>();
				List<Investor> list = investorRepo.findByUserIdAndSourceAndLiveInd(userId, source.getSourceId(), true);
				if (null != list && !list.isEmpty()) {
					list.stream().map(investor -> {
						InvestorViewMapper investorViewMapper = getInvestorDetailsById(investor.getInvestorId());
						if (null != investorViewMapper) {
							resulList.add(investorViewMapper);
						}
						return resulList;
					}).collect(Collectors.toList());
				}

				map1.put("source", source.getName());
				map1.put("investors", resulList);
				map.add(map1);
			}
		}

		return map;

	}

	@Override
	public List<InvestorViewMapper> getFilterInvestorsPageWiseByUserId(String userId, int pageNo, int pageSize,
			String filter) {
		List<InvestorViewMapper> investorViewMappers = new ArrayList<>();
		Pageable paging = null;
		if (filter.equalsIgnoreCase("creationdate")) {
			paging = PageRequest.of(pageNo, pageSize, Sort.by("creationDate").descending());

		} else if (filter.equalsIgnoreCase("ascending")) {
			paging = PageRequest.of(pageNo, pageSize, Sort.by("name"));

		} else if (filter.equalsIgnoreCase("descending")) {
			paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "name"));
		}
		Page<Investor> investors = investorRepo.getInvestorsListByUserId(userId, paging);
		System.out.println("###########" + investors);
		if (null != investors && !investors.isEmpty()) {
			investorViewMappers = investors.stream().map(investor -> {
				InvestorViewMapper mapper = null;
				if (null != investor) {
					mapper = getInvestorDetailsById(investor.getInvestorId());
					mapper.setPageCount(investors.getTotalPages());
					mapper.setDataCount(investors.getSize());
					mapper.setListCount(investors.getTotalElements());
				}
				return mapper;
			}).collect(Collectors.toList());
		}
		return investorViewMappers;
	}

	@Override
	public List<InvestorViewMapper> getInvestorBySector(String name) {
		List<InvestorViewMapper> mapperList = new ArrayList<>();
		SectorDetails sectorDetails = sectorDetailsRepository.getSectorDetailsBySectorName(name);
		if (null != sectorDetails) {
			List<Investor> detailsList = investorRepo.findBySectorAndLiveInd(sectorDetails.getSectorId(), true);
			if (null != detailsList && !detailsList.isEmpty()) {
				mapperList = detailsList.stream().map(li -> getInvestorDetailsById(li.getInvestorId()))
						.collect(Collectors.toList());
			}
		}
		return mapperList;
	}

	@Override
	public List<InvestorViewMapper> getInvestorByOwnerName(String name) {
//		List<InvestorViewMapper> mapperList = new ArrayList<InvestorViewMapper>();
//		EmployeeDetails employeeDetails = employeeRepository.findByFullName(name);
//		
//		if (null != employeeDetails) {
//			List<Investor> detailsList = investorRepo.findByUserIdandLiveInd(employeeDetails.getUserId());
//			if (null != detailsList && !detailsList.isEmpty()) {
//
//				mapperList = detailsList.stream().map(li -> getInvestorDetailsById(li.getInvestorId()))
//						.collect(Collectors.toList());
//			}
//		}
//
//		return mapperList;

		List<InvestorViewMapper> mapperList = new ArrayList<>();

		List<EmployeeDetails> detailsList = employeeRepository.findByLiveInd(true);

		List<EmployeeDetails> filterList = detailsList.parallelStream().filter(
				detail -> detail.getFullName() != null && Utility.containsIgnoreCase(detail.getFullName(), name.trim()))
				.collect(Collectors.toList());

		if (!filterList.isEmpty()) {
			mapperList = filterList.stream().flatMap(
					employeeDetails -> investorRepo.findByUserIdandLiveInd(employeeDetails.getUserId()).stream())
					.map(investor -> getInvestorDetailsById(investor.getInvestorId())).collect(Collectors.toList());
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
	public void deleteInvestorNotesById(String noteId) {
		InvestorNoteLink notesList = investorNotesLinkRepo.findByNoteId(noteId);
		if (null != notesList) {

			Notes notes = notesRepository.findByNoteId(noteId);
			if (null != notes) {
				notes.setLiveInd(false);
				notesRepository.save(notes);
			}
		}
	}

	@Override
	public List<ActivityMapper> getActivityListByInvestorId(String investorId) {
		List<ActivityMapper> resultList = new ArrayList<>();

		List<InvestorCallLink> investorCallLink = investorCallLinkRepo.getCallListByInvestorIdAndLiveInd(investorId);
		if (null != investorCallLink && !investorCallLink.isEmpty()) {
			investorCallLink.stream().map(call -> {
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

		List<InvestorEventLink> eventLink = investorEventRepo.getByInvestorIdAndLiveInd(investorId);
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

		List<InvestorTaskLink> taskLink = investorTaskRepo.getTaskListByInvestorIdAndLiveInd(investorId);
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
	public List<InvestorViewMapper> getInvestorListByOrgId(String orgId, int pageNo, int pageSize, String filter) {
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("creationDate").descending());
		// paging = PageRequest.of(pageNo, pageSize,
		// Sort.by("creationDate").descending());
		if (filter.equalsIgnoreCase("creationdate")) {
			paging = PageRequest.of(pageNo, pageSize, Sort.by("creationDate").descending());

		} else if (filter.equalsIgnoreCase("ascending")) {
			paging = PageRequest.of(pageNo, pageSize, Sort.by("name"));

		} else if (filter.equalsIgnoreCase("descending")) {
			paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "name"));
		}
		List<InvestorViewMapper> resultMapper = new ArrayList<>();
		Page<Investor> investorList = investorRepo.getInvestorListPageWiseByOrgId(orgId, paging);
		if (null != investorList && !investorList.isEmpty()) {
			resultMapper = investorList.stream().map(investor -> {
				InvestorViewMapper mapper = getInvestorDetailsById(investor.getInvestorId());
				mapper.setPageCount(investorList.getTotalPages());
				mapper.setDataCount(investorList.getSize());
				mapper.setListCount(investorList.getTotalElements());
				return mapper;
			}).collect(Collectors.toList());
		}

		return resultMapper;
	}

//	@Override
//	public List<ContactViewMapper> getInvestorContactListByOrgId(String orgId, int pageNo, int pageSize,
//			String filter) {
//		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("creationDate").descending());
//		// paging = PageRequest.of(pageNo, pageSize,
//		// Sort.by("creationDate").descending());
//		if (filter.equalsIgnoreCase("creationdate")) {
//			paging = PageRequest.of(pageNo, pageSize, Sort.by("creationDate").descending());
//
//		} else if (filter.equalsIgnoreCase("ascending")) {
//			paging = PageRequest.of(pageNo, pageSize, Sort.by("name"));
//
//		} else if (filter.equalsIgnoreCase("descending")) {
//			paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "name"));
//		}
//		Page<ContactDetails> contactList = contactRepository.getInvesterContactByOrgIdAndContactTypeAndLiveInd(orgId,
//				"Investor", paging);
//		List<ContactViewMapper> contactViewMappers = new ArrayList<>();
//
//		if (contactList != null && !contactList.isEmpty()) {
//			return contactList.stream().map(contactDetails -> {
//				ContactViewMapper contactMapper = contactService.getContactDetailsById(contactDetails.getContactId());
//				contactMapper.setPageCount(contactList.getTotalPages());
//				contactMapper.setDataCount(contactList.getSize());
//				contactMapper.setListCount(contactList.getTotalElements());
////				ThirdParty thirdParty = thirdPartyRepository.findByOrgId(contactMapper.getOrganizationId());
////                if (thirdParty != null) {
////                    contactMapper.setThirdPartyAccessInd(thirdParty.isCustomerContactInd());
////                }
//				return contactMapper;
//			}).collect(Collectors.toList());
//
//		}
//		return contactViewMappers;
//	}
//
//    @Override
//    public Set<InvestorViewMapper> getTeamInvestorDetailsByUserId(String userId, int pageNo, int pageSize,
//                                                                  String filter) {
//
//        Pageable paging = null;
//        Team team = teamRepository.findByTeamLeadAndLiveInd(userId, true);
//
//        List<String> userIds = teamMemberLinkRepo.findByTeamId(team.getTeamId()).stream()
//                .map(TeamMemberLink::getTeamMemberId).collect(Collectors.toList());
//
//        if (filter.equalsIgnoreCase("creationdate")) {
//            paging = PageRequest.of(pageNo, pageSize, Sort.by("creationDate").descending());
//
//        } else if (filter.equalsIgnoreCase("ascending")) {
//            paging = PageRequest.of(pageNo, pageSize, Sort.by("name"));
//
//        } else if (filter.equalsIgnoreCase("descending")) {
//            paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "name"));
//        }
//        Page<Investor> investorPage = investorRepo.getTeamInvestorsListByUserIdsPaginated(userIds, paging);
//
//        Set<InvestorViewMapper> mapperSet = new HashSet<>();
//
//        if (investorPage != null && !investorPage.isEmpty()) {
//            mapperSet = investorPage.getContent().stream().map(li -> {
//                InvestorViewMapper mapper = getInvestorDetailsById(li.getInvestorId());
//                mapper.setPageCount(investorPage.getTotalPages());
//                mapper.setDataCount(investorPage.getSize());
//                mapper.setListCount(investorPage.getTotalElements());
//                return mapper;
//            }).collect(Collectors.toSet());
//        }
//        return mapperSet;
//
//    }
//
//    @Override
//    public Set<ContactViewMapper> getTeamInvestorContactListByUserId(String userId, int pageNo, int pageSize,
//                                                                     String filter) {
//        Pageable paging = null;
//        if (filter.equalsIgnoreCase("creationdate")) {
//            paging = PageRequest.of(pageNo, pageSize, Sort.by("creationDate").descending());
//
//        } else if (filter.equalsIgnoreCase("ascending")) {
//            paging = PageRequest.of(pageNo, pageSize, Sort.by("fullName"));
//
//        } else if (filter.equalsIgnoreCase("descending")) {
//            paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "fullName"));
//        }
//        Team team = teamRepository.findByTeamLeadAndLiveInd(userId, true);
//
//        List<String> userIds = teamMemberLinkRepo.findByTeamId(team.getTeamId()).stream()
//                .map(TeamMemberLink::getTeamMemberId).collect(Collectors.toList());
//        Page<ContactDetails> contactList = contactRepository.getTeamContactListdAndContactTypeAndLiveInd(userIds,
//                "Investor", paging);
//
//        System.out.println("###########" + contactList);
//        if (null != contactList && !contactList.isEmpty()) {
//            return contactList.getContent().stream().map(contact -> {
//                ContactViewMapper contactMapper = contactService.getContactDetailsById(contact.getContactId());
//                String middleName = "";
//                String lastName = "";
//                String salutation = "";
//
//                if (contact.getSalutation() != null && contact.getSalutation().length() > 0) {
//                    salutation = contact.getSalutation();
//                }
//                if (!StringUtils.isEmpty(contact.getLast_name())) {
//
//                    lastName = contact.getLast_name();
//                }
//
//                if (contact.getMiddle_name() != null && contact.getMiddle_name().length() > 0) {
//
//                    middleName = contact.getMiddle_name();
//                    contactMapper.setFullName(
//                            salutation + " " + contact.getFirst_name() + " " + middleName + " " + lastName);
//                } else {
//
//                    contactMapper.setFullName(salutation + " " + contact.getFirst_name() + " " + lastName);
//                }
//                contactMapper.setPageCount(contactList.getTotalPages());
//                contactMapper.setDataCount(contactList.getSize());
//                contactMapper.setListCount(contactList.getTotalElements());
//
//                return contactMapper;
//
//            }).filter(l -> l != null).collect(Collectors.toSet());
//        }
//        return null;
//    }

	@Override
	public HashMap getTeamInvestorCountByUserId(String userId) {
		HashMap map = new HashMap();
		Team team = teamRepository.findByTeamLeadAndLiveInd(userId, true);

		List<String> userIds = teamMemberLinkRepo.findByTeamId(team.getTeamId()).stream()
				.map(TeamMemberLink::getTeamMemberId).collect(Collectors.toList());
		List<Investor> investorPage = investorRepo.getTeamInvestorsListByUserIds(userIds);
		map.put("InvestorTeam", investorPage.size());

		return map;
	}

	@Override
	public HashMap getTeamInvestorContactCountByUserId(String userId) {
		HashMap map = new HashMap();
		Team team = teamRepository.findByTeamLeadAndLiveInd(userId, true);

		List<String> userIds = teamMemberLinkRepo.findByTeamId(team.getTeamId()).stream()
				.map(TeamMemberLink::getTeamMemberId).collect(Collectors.toList());
		List<ContactDetails> contactList = contactRepository.getTeamContactListByUserIds(userIds, "Investor");
		map.put("InvestorContactTeam", contactList.size());

		return map;
	}

	@Override
	public boolean investorByUrl(String url) {
		List<Investor> investor = investorRepo.getByUrl(url);
		if (investor != null & !investor.isEmpty()) {
			return true;
		}
		return false;
	}

	public InvestorReportMapper getInvestorDetailsByIdForReport(String investorId) {
		Investor investor = investorRepo.findById(investorId).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "investor not found with id " + investorId));
		System.out.println("investor object is..." + investor);

		InvestorReportMapper investorMapper = new InvestorReportMapper();

		if (null != investor) {

			if (investor.getSector() != null && !investor.getSector().trim().isEmpty()) {
				SectorDetails sector = sectorDetailsRepository.getSectorDetailsBySectorId(investor.getSector());
				System.out.println("get sectordetails by id returns........." + investor.getSector());
				System.out.println("sector object is......." + sector);
				if (sector != null) {
					investorMapper.setSector(sector.getSectorName());
					investorMapper.setSectorId(investor.getSector());
				} else {
					investorMapper.setSector("");
					investorMapper.setSectorId("");
				}
			}
			investorMapper.setInvestorId(investorId);
			investorMapper.setName(investor.getName());
			investorMapper.setUrl(investor.getUrl());
			investorMapper.setNotes(investor.getNotes());
			investorMapper.setEmail(investor.getEmail());
			investorMapper.setGroup(investor.getGroup());
			investorMapper.setVatNo(investor.getVatNo());
			investorMapper.setPhoneNumber(investor.getPhoneNumber());
			investorMapper.setCountryDialCode(investor.getCountryDialCode());
			investorMapper.setUserId(investor.getUserId());
			investorMapper.setOrganizationId(investor.getOrganizationId());
			investorMapper.setCreationDate(Utility.getISOFromDate(investor.getCreationDate()));
			investorMapper.setImageURL(investor.getImageURL());
			investorMapper.setGst(investor.getGst());

			investorMapper.setCountry(investor.getCountry());
			// investorMapper.setSector(sector.getSectorName());
			investorMapper.setZipcode(investor.getZipcode());

			investorMapper.setDocumentId(investor.getDocumentId());
			investorMapper.setCategory(investor.getCategory());
			investorMapper.setBusinessRegistration(investor.getBusinessRegistration());
			investorMapper.setSourceUserId(investor.getSourceUserId());
			investorMapper.setSourceUserName(employeeService.getEmployeeFullName(investor.getSourceUserId()));
			;
			if (null != investor.getSource() && !investor.getSource().isEmpty()) {
				Source source = sourceRepository.findBySourceId(investor.getSource());
				if (null != source) {
					investorMapper.setSource(source.getName());
				}
			}
			EmployeeDetails employeeDetails = employeeRepository.getEmployeeDetailsByEmployeeId(investor.getUserId());
			if (null != employeeDetails) {
				String middleName = " ";
				String lastName = "";

				if (!StringUtils.isEmpty(employeeDetails.getLastName())) {

					lastName = employeeDetails.getLastName();
				}

				if (null != employeeDetails.getMiddleName() && !employeeDetails.getMiddleName().isEmpty()) {

					middleName = employeeDetails.getMiddleName();
					investorMapper.setOwnerName(employeeDetails.getFirstName() + " " + middleName + " " + lastName);
					investorMapper.setOwnerImageId(employeeDetails.getImageId());
				} else {

					investorMapper.setOwnerName(employeeDetails.getFirstName() + " " + lastName);
					investorMapper.setOwnerImageId(employeeDetails.getImageId());
				}

			}
			EmployeeDetails employeeDetail = employeeRepository
					.getEmployeeDetailsByEmployeeId(investor.getAssignedTo());
			if (null != employeeDetail) {
				String middleName = " ";
				String lastName = "";

				if (!StringUtils.isEmpty(employeeDetail.getLastName())) {

					lastName = employeeDetail.getLastName();
				}

				if (employeeDetail.getMiddleName() != null && !employeeDetail.getMiddleName().isEmpty()) {

					middleName = employeeDetail.getMiddleName();
					investorMapper.setAssignedTo(employeeDetail.getFirstName() + " " + middleName + " " + lastName);

				} else {

					investorMapper.setAssignedTo(employeeDetail.getFirstName() + " " + lastName);

				}

			}

//        investorRecruitUpdate dbinvestorRecruitUpdate = investorRecruitUpdateRepository
//                .findByinvestorId(investor.getinvestorId());
//        if (null != dbinvestorRecruitUpdate) {
//            System.out.println("====" + dbinvestorRecruitUpdate.getId());
//            investorMapper.setLastRequirementOn(Utility.getISOFromDate(dbinvestorRecruitUpdate.getUpdatedDate()));
//        }

			List<InvestorOpportunity> oppinvestorsList = investorOpportunityRepo
					.getOpportunityListByInvestorIdAndLiveInd(investorId);
			if (null != oppinvestorsList && !oppinvestorsList.isEmpty()) {
				double totalValue = oppinvestorsList.stream().mapToDouble(li -> {
					double value = 0;
					if (null != li.getProposalAmount()) {
						value = Double.parseDouble(li.getProposalAmount());
					}
					return value;
				}).sum();
				investorMapper.setTotalProposalValue(totalValue);
				investorMapper.setOppNo(oppinvestorsList.size());
			}

			List<InvestorSkillLinkMapper> skillList = new ArrayList<InvestorSkillLinkMapper>();
			List<InvestorSkillLink> list = investorSkillLinkRepo.getByInvestorId(investorId);
			if (null != list && !list.isEmpty()) {
				for (InvestorSkillLink skillSetDetails : list) {
					InvestorSkillLink list2 = investorSkillLinkRepo.getById(skillSetDetails.getInvestorSkillLinkId());

					InvestorSkillLinkMapper mapper = new InvestorSkillLinkMapper();
					if (null != list2) {

						DefinationDetails definationDetails1 = definationRepository
								.findByDefinationId(list2.getSkillName());
						if (null != definationDetails1) {
							mapper.setSkillName(definationDetails1.getName());

						}

						mapper.setInvestorId(list2.getInvestorId());
						mapper.setInvestorSkillLinkId(list2.getInvestorSkillLinkId());
						skillList.add(mapper);
					}
				}
				investorMapper.setSkill(skillList);
			}

			List<AddressMapper> addressList = new ArrayList<AddressMapper>();
			List<InvestorAddressLink> investorAddressList = investorAddressLinkRepo
					.getAddressListByInvestorId(investorId);

			/* fetch investor address & set to investor mapper */
			if (null != investorAddressList && !investorAddressList.isEmpty()) {

				for (InvestorAddressLink investorAddressLink : investorAddressList) {
					AddressDetails addressDetails = addressRepository
							.getAddressDetailsByAddressId(investorAddressLink.getAddressId());

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
								addressDetails.getCountry(), investor.getOrganizationId());
						if (null != country1) {
							addressMapper.setCountry_alpha2_code(country1.getCountry_alpha2_code());
							addressMapper.setCountry_alpha3_code(country1.getCountry_alpha3_code());
						}
						addressList.add(addressMapper);
					}
				}

				System.out.println("addressList.......... " + addressList);
			}
			investorMapper.setAddress(addressList);
			Country country1 = countryRepository.getCountryDetailsByCountryNameAndOrgId(
					investorMapper.getAddress().get(0).getCountry(), investor.getOrganizationId());
			if (null != country1) {
				investorMapper.setCountry_alpha2_code(country1.getCountry_alpha2_code());
				investorMapper.setCountry_alpha3_code(country1.getCountry_alpha3_code());
			}
		}
		return investorMapper;
	}

	@Override
	public List<InvestorReportMapper> getAllInvestorByOrgIdForReport(String orgId, String startDate, String endDate) {
		Date endDate1 = null;
		Date startDate1 = null;
		try {
			endDate1 = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			startDate1 = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<InvestorReportMapper> resultMapper = new ArrayList<>();
		List<Investor> investorList = investorRepo.getInvestorListByOrgIdWithDateRange(orgId, startDate1, endDate1);
		if (null != investorList && !investorList.isEmpty()) {
			resultMapper = investorList.stream().map(investor -> {
				InvestorReportMapper mapper = getInvestorDetailsByIdForReport(investor.getInvestorId());
				return mapper;
			}).collect(Collectors.toList());
		}
		return resultMapper;
	}

	@Override
	public List<InvestorReportMapper> getAllInvestorByUserIdForReport(String userId, String startDate, String endDate) {
		Date endDate1 = null;
		Date startDate1 = null;
		try {
			endDate1 = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			startDate1 = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<InvestorReportMapper> resultMapper = new ArrayList<>();
		List<Investor> investorList = investorRepo.getInvestorListByUserIdWithDateRange(userId, startDate1, endDate1);
		if (null != investorList && !investorList.isEmpty()) {
			resultMapper = investorList.stream().map(investor -> {
				InvestorReportMapper mapper = getInvestorDetailsByIdForReport(investor.getInvestorId());
				return mapper;
			}).collect(Collectors.toList());
		}
		return resultMapper;
	}

	@Override
	public List<ContactReportMapper> getAllInvestorContactListByOrgIdForReport(String orgId, String startDate,
			String endDate) {
		Date endDate1 = null;
		Date startDate1 = null;
		try {
			endDate1 = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			startDate1 = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<ContactDetails> contactList = contactRepository
				.getInvesterContactByOrgIdAndContactTypeAndLiveIndWithDateRange(orgId, "Investor", startDate1,
						endDate1);
		List<ContactReportMapper> contactViewMappers = new ArrayList<>();

		if (contactList != null && !contactList.isEmpty()) {
			return contactList.stream().map(contactDetails -> {
				ContactReportMapper contactMapper = contactService
						.getContactDetailsByIdForReport(contactDetails.getContactId());
				return contactMapper;
			}).collect(Collectors.toList());

		}
		return contactViewMappers;
	}

	@Override
	public List<ContactReportMapper> getAllInvestorContactListByUserIdForReport(String userId, String startDate,
			String endDate) {
		Date endDate1 = null;
		Date startDate1 = null;
		try {
			endDate1 = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			startDate1 = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<ContactDetails> contactList = contactRepository.getCreatedContactListByUserIdAndDateRange(userId,
				"Investor", startDate1, endDate1);
		List<ContactReportMapper> contactViewMappers = new ArrayList<>();

		if (contactList != null && !contactList.isEmpty()) {
			return contactList.stream().map(contactDetails -> {
				ContactReportMapper contactMapper = contactService
						.getContactDetailsByIdForReport(contactDetails.getContactId());
				return contactMapper;
			}).collect(Collectors.toList());
		}
		return contactViewMappers;
	}

	@Override
	public HashMap getActivityRecordByInvestorId(String investorId) {
		int count = 0;
		List<InvestorCallLink> investorCallLink = investorCallLinkRepo.getCallListByInvestorIdAndLiveInd(investorId);
		if (null != investorCallLink && !investorCallLink.isEmpty()) {
			count = investorCallLink.size();
		}

		List<InvestorEventLink> eventLink = investorEventRepo.getByInvestorIdAndLiveInd(investorId);
		if (null != eventLink && !eventLink.isEmpty()) {
			count += eventLink.size();
		}

		List<InvestorTaskLink> taskLink = investorTaskRepo.getTaskListByInvestorIdAndLiveInd(investorId);
		if (null != taskLink && !taskLink.isEmpty()) {
		}

		HashMap map = new HashMap();
		map.put("count", count);
		return map;
	}

	@Override
	public HashMap getInvestorOpportunityCountByInvestorId(String investorId) {
		HashMap map = new HashMap();
		List<InvestorOpportunity> oppList = investorOpportunityRepo.getByInvestorId(investorId);
		map.put("opportunity", oppList.size());
		return map;
	}

	@Override
	public HashMap getInvestorOppProposalValueCountByInvestorId(String investorId, String userId, String orgId) {
		HashMap map = new HashMap();
		int count = 0;
		int conversionAmount = 0;
		Investor investor = investorRepo.getInvestorIdByIdAndLiveInd(investorId);
		if (null != investor) {
			if (investor.getUserId().equalsIgnoreCase(userId)) {

				List<InvestorOpportunity> opportunityList = investorOpportunityRepo.getByInvestorId(investorId);
				if (null != opportunityList && !opportunityList.isEmpty()) {
					for (InvestorOpportunity opportunityDetails : opportunityList) {
						if (!StringUtils.isEmpty(opportunityDetails.getProposalAmount())) {
							EmployeeDetails employeeDetails = employeeRepository
									.getEmployeeDetailsByEmployeeId(investor.getUserId());
							if (null != employeeDetails.getCurrency()) {
								Currency userCurrency = currencyRepository
										.getByCurrencyId(employeeDetails.getCurrency());
								if (null != userCurrency) {
									if (null != opportunityDetails.getCurrency()) {
										if (userCurrency.getCurrencyName()
												.equalsIgnoreCase(opportunityDetails.getCurrency())) {
											System.out.println(
													"Opp currency in user 0: " + opportunityDetails.getCurrency());
											System.out.println(
													"user currency in user 0: " + userCurrency.getCurrencyName());
											System.out.println("ProposalAmount in user 0: "
													+ opportunityDetails.getProposalAmount());
											count = count + Integer.parseInt(opportunityDetails.getProposalAmount());
											System.out.println("count in user 0: " + count);
											System.out.println("in user currency == opp currency");
										} else {

											CurrencyConversion currencyConversion = currencyConversionRepository
													.findByReportingCurrencyAndConversionCurrencyAndLiveInd(
															userCurrency.getCurrencyName(),
															opportunityDetails.getCurrency(), true);
											if (null != currencyConversion) {
												System.out.println(
														"Trade currency in user 1: " + userCurrency.getCurrencyName());
												System.out.println("Conversion currency in user 1: "
														+ opportunityDetails.getCurrency());
												System.out.println("ProposalAmount in user 1: "
														+ opportunityDetails.getProposalAmount());
												conversionAmount = (int) (Integer
														.parseInt(opportunityDetails.getProposalAmount())
														/ currencyConversion.getConversionFactor());
												System.out.println("conversionAmount in user 1: " + conversionAmount);
												count = count + conversionAmount;
												System.out.println("count in user 1: " + count);
												System.out.println("in user 0");
											} else {
												CurrencyConversion currencyConversion1 = currencyConversionRepository
														.findByReportingCurrencyAndConversionCurrencyAndLiveInd(
																opportunityDetails.getCurrency(),
																userCurrency.getCurrencyName(), true);
												if (null != currencyConversion1) {
													System.out.println("Trade currency in user 2: "
															+ opportunityDetails.getCurrency());
													System.out.println("Conversion currency in user 2: "
															+ userCurrency.getCurrencyName());
													System.out.println("ProposalAmount in user 2: "
															+ opportunityDetails.getProposalAmount());
													conversionAmount = (int) (Integer
															.parseInt(opportunityDetails.getProposalAmount())
															* currencyConversion1.getConversionFactor());
													System.out
															.println("conversionAmount in user 2: " + conversionAmount);
													count = count + conversionAmount;
													System.out.println("count in user 2: " + count);
													System.out.println("in user 1");
												} else {
													System.out.println("in user 2");
													map.put("message",
															"No Conversion Available from "
																	+ userCurrency.getCurrencyName() + "to"
																	+ opportunityDetails.getCurrency());
													break;
												}
											}
										}
									}
								}
							} else {
								System.out.println("in user 3");
								map.put("message", "Currency Not Available for user : "
										+ employeeService.getEmployeeFullName(userId));
								count = count + Integer.parseInt(opportunityDetails.getProposalAmount());
							}
						}
					}
					EmployeeDetails employeeDetails = employeeRepository
							.getEmployeeDetailsByEmployeeId(investor.getUserId());
					if (null != employeeDetails.getCurrency()) {
						Currency userCurrency = currencyRepository.getByCurrencyId(employeeDetails.getCurrency());
						if (null != userCurrency) {
							System.out.println("in user trade currency : " + userCurrency.getCurrencyName());
							map.put("tradeCurrency", userCurrency.getCurrencyName());
						}
					}
				} else {
					System.out.println("in user 4");
					map.put("message", "Opportunity not available for this investor");
				}

			} else {

				List<InvestorOpportunity> opportunityList = investorOpportunityRepo
						.getOpportunityListByInvestorIdAndLiveInd(investorId);
				if (null != opportunityList && !opportunityList.isEmpty()) {
					for (InvestorOpportunity opportunityDetails : opportunityList) {
						if (!StringUtils.isEmpty(opportunityDetails.getProposalAmount())) {
							OrganizationDetails organizationDetails = organizationRepository
									.getOrganizationDetailsById(investor.getOrganizationId());
							if (null != organizationDetails.getTrade_currency()) {
								if (null != opportunityDetails.getCurrency()) {
									if (organizationDetails.getTrade_currency()
											.equalsIgnoreCase(opportunityDetails.getCurrency())) {
										System.out
												.println("Opp currency in org 0: " + opportunityDetails.getCurrency());
										System.out.println(
												"org currency in org 0: " + organizationDetails.getTrade_currency());
										System.out.println(
												"ProposalAmount in org 0: " + opportunityDetails.getProposalAmount());
										count = count + Integer.parseInt(opportunityDetails.getProposalAmount());
										System.out.println("count in org 0: " + count);
										System.out.println("in org currency == opp currency");
									} else {

										CurrencyConversion currencyConversion = currencyConversionRepository
												.findByReportingCurrencyAndConversionCurrencyAndLiveInd(
														organizationDetails.getTrade_currency(),
														opportunityDetails.getCurrency(), true);
										if (null != currencyConversion) {
											System.out.println("Trade currency in org 1: "
													+ organizationDetails.getTrade_currency());
											System.out.println("Conversion currency in org 1: "
													+ opportunityDetails.getCurrency());
											System.out.println("ProposalAmount in org 1: "
													+ opportunityDetails.getProposalAmount());
											conversionAmount = (int) (Integer
													.parseInt(opportunityDetails.getProposalAmount())
													/ currencyConversion.getConversionFactor());
											System.out.println("conversionAmount in org 1: " + conversionAmount);
											count = count + conversionAmount;
											System.out.println("count in org 1: " + count);
											System.out.println("in org 0");
										} else {
											CurrencyConversion currencyConversion1 = currencyConversionRepository
													.findByReportingCurrencyAndConversionCurrencyAndLiveInd(
															opportunityDetails.getCurrency(),
															organizationDetails.getTrade_currency(), true);
											if (null != currencyConversion1) {
												System.out.println(
														"Trade currency in org 2: " + opportunityDetails.getCurrency());
												System.out.println("Conversion currency in org 2: "
														+ organizationDetails.getTrade_currency());
												System.out.println("ProposalAmount in org 2: "
														+ opportunityDetails.getProposalAmount());
												conversionAmount = (int) (Integer
														.parseInt(opportunityDetails.getProposalAmount())
														* currencyConversion1.getConversionFactor());
												System.out.println("conversionAmount in org 2: " + conversionAmount);
												count = count + conversionAmount;
												System.out.println("count in org 2: " + count);
												System.out.println("in org 1");
											} else {
												System.out.println("in org 2");
												map.put("message",
														"No Conversion Available from "
																+ organizationDetails.getTrade_currency() + "to"
																+ opportunityDetails.getCurrency());
												break;
											}
										}
									}
								}
							} else {
								System.out.println("in org 3");
								map.put("message", "Trade Currency Not Available for This Organization");
								count = count + Integer.parseInt(opportunityDetails.getProposalAmount());
							}
						}
					}
					OrganizationDetails organizationDetails = organizationRepository
							.getOrganizationDetailsById(investor.getOrganizationId());
					if (null != organizationDetails.getTrade_currency()) {
						System.out.println("in org trade currency : " + organizationDetails.getTrade_currency());
						map.put("tradeCurrency", organizationDetails.getTrade_currency());
					}
				} else {
					System.out.println("in org 4");
					map.put("message", "Opportunity not available for this investor");
				}
			}
		} else {
			map.put("message", "Investor not available");
		}

		map.put("pipeLineValue", count);

		return map;

	}

	@Override
	public HashMap getInvestorOppWeigthedValueCountByInvestorId(String investorId, String userId, String orgId) {
		HashMap map = new HashMap();
		double count = 0;
		int conversionAmount = 0;
		Investor investor = investorRepo.getInvestorIdByIdAndLiveInd(investorId);
		if (null != investor) {
			if (investor.getUserId().equalsIgnoreCase(userId)) {

				List<InvestorOpportunity> opportunityList = investorOpportunityRepo.getByInvestorId(investorId);
				if (null != opportunityList && !opportunityList.isEmpty()) {
					for (InvestorOpportunity opportunityDetails : opportunityList) {
						int pAmount = 0;
						double wValue = 0;
						double total = 0;
						if (!StringUtils.isEmpty(opportunityDetails.getProposalAmount())) {
							EmployeeDetails employeeDetails = employeeRepository
									.getEmployeeDetailsByEmployeeId(investor.getUserId());
							if (null != employeeDetails.getCurrency()) {
								Currency userCurrency = currencyRepository
										.getByCurrencyId(employeeDetails.getCurrency());
								if (null != userCurrency) {
									if (null != opportunityDetails.getCurrency()) {
										if (userCurrency.getCurrencyName()
												.equalsIgnoreCase(opportunityDetails.getCurrency())) {

											pAmount = Integer.parseInt(opportunityDetails.getProposalAmount());
											System.out.println(
													"Opp currency in user 0: " + opportunityDetails.getCurrency());
											System.out.println(
													"user currency in user 0: " + userCurrency.getCurrencyName());
											System.out.println("ProposalAmount in user 0: " + pAmount);
											InvestorOppStages oppStages = investorOppStagesRepo
													.getInvestorOppStagesByInvestorOppStagesId(
															opportunityDetails.getOppStage());
											if (null != oppStages) {
												if (0 != oppStages.getProbability()) {
													wValue = ((oppStages.getProbability()) / 100);
													System.out.println("wValue in user 0: " + wValue);
												}
											}
											total = pAmount * wValue;
											System.out.println("total in user 0: " + total);
											count = count + total;

											// count = count + Integer.parseInt(opportunityDetails.getProposalAmount());
											System.out.println("count in user 0: " + count);
											System.out.println("in user currency == opp currency");
										} else {

											CurrencyConversion currencyConversion = currencyConversionRepository
													.findByReportingCurrencyAndConversionCurrencyAndLiveInd(
															userCurrency.getCurrencyName(),
															opportunityDetails.getCurrency(), true);
											if (null != currencyConversion) {
												pAmount = Integer.parseInt(opportunityDetails.getProposalAmount());
												System.out.println(
														"Trade currency in user 1: " + userCurrency.getCurrencyName());
												System.out.println("Conversion currency in user 1: "
														+ opportunityDetails.getCurrency());
												System.out.println("ProposalAmount in user 1: " + pAmount);
												InvestorOppStages oppStages = investorOppStagesRepo
														.getInvestorOppStagesByInvestorOppStagesId(
																opportunityDetails.getOppStage());
												if (null != oppStages) {
													if (0 != oppStages.getProbability()) {
														wValue = ((oppStages.getProbability()) / 100);
														System.out.println("wValue in user 1: " + wValue);
													}
												}
												total = pAmount * wValue;
												System.out.println("total in user 1: " + total);
												conversionAmount = (int) ((total)
														/ currencyConversion.getConversionFactor());
												System.out.println("conversionAmount in user 1: " + conversionAmount);
												count = count + conversionAmount;
												System.out.println("count in user 1: " + count);
												System.out.println("in user 0");
											} else {
												CurrencyConversion currencyConversion1 = currencyConversionRepository
														.findByReportingCurrencyAndConversionCurrencyAndLiveInd(
																opportunityDetails.getCurrency(),
																userCurrency.getCurrencyName(), true);
												if (null != currencyConversion1) {
													pAmount = Integer.parseInt(opportunityDetails.getProposalAmount());
													System.out.println("Trade currency in user 2: "
															+ opportunityDetails.getCurrency());
													System.out.println("Conversion currency in user 2: "
															+ userCurrency.getCurrencyName());
													System.out.println("ProposalAmount in user 2: " + pAmount);
													InvestorOppStages oppStages = investorOppStagesRepo
															.getInvestorOppStagesByInvestorOppStagesId(
																	opportunityDetails.getOppStage());
													if (null != oppStages) {
														if (0 != oppStages.getProbability()) {
															wValue = ((oppStages.getProbability()) / 100);
															System.out.println("wValue in user 1: " + wValue);
														}
													}
													total = pAmount * wValue;
													System.out.println("total in user 1: " + total);
													conversionAmount = (int) ((total)
															/ currencyConversion1.getConversionFactor());
													System.out
															.println("conversionAmount in user 2: " + conversionAmount);
													count = count + conversionAmount;
													System.out.println("count in user 2: " + count);
													System.out.println("in user 1");
												} else {
													System.out.println("in user 2");
													map.put("message",
															"No Conversion Available from "
																	+ userCurrency.getCurrencyName() + "to"
																	+ opportunityDetails.getCurrency());
													break;
												}
											}
										}
									}
								}
							} else {
								System.out.println("in user 3");
								map.put("message", "Currency Not Available for user : "
										+ employeeService.getEmployeeFullName(userId));
								count = count + Integer.parseInt(opportunityDetails.getProposalAmount());
							}
						}
					}
					EmployeeDetails employeeDetails = employeeRepository
							.getEmployeeDetailsByEmployeeId(investor.getUserId());
					if (null != employeeDetails.getCurrency()) {
						Currency userCurrency = currencyRepository.getByCurrencyId(employeeDetails.getCurrency());
						if (null != userCurrency) {
							System.out.println("in user trade currency : " + userCurrency.getCurrencyName());
							map.put("tradeCurrency", userCurrency.getCurrencyName());
						}
					}
				} else {
					System.out.println("in user 4");
					map.put("message", "Opportunity not available for this Investor");
				}

			} else {
				List<InvestorOpportunity> opportunityList = investorOpportunityRepo.getByInvestorId(investorId);
				if (null != opportunityList && !opportunityList.isEmpty()) {
					for (InvestorOpportunity opportunityDetails : opportunityList) {
						int pAmount = 0;
						double wValue = 0;
						double total = 0;
						if (!StringUtils.isEmpty(opportunityDetails.getProposalAmount())) {
							OrganizationDetails organizationDetails = organizationRepository
									.getOrganizationDetailsById(investor.getOrganizationId());
							if (null != organizationDetails.getTrade_currency()) {
								if (null != opportunityDetails.getCurrency()) {
									if (organizationDetails.getTrade_currency()
											.equalsIgnoreCase(opportunityDetails.getCurrency())) {
										pAmount = Integer.parseInt(opportunityDetails.getProposalAmount());
										System.out
												.println("Opp currency in org 0: " + opportunityDetails.getCurrency());
										System.out.println(
												"org currency in org 0: " + organizationDetails.getTrade_currency());
										System.out.println("ProposalAmount in org 0: " + pAmount);
										InvestorOppStages oppStages = investorOppStagesRepo
												.getInvestorOppStagesByInvestorOppStagesId(
														opportunityDetails.getOppStage());
										if (null != oppStages) {
											if (0 != oppStages.getProbability()) {
												wValue = ((oppStages.getProbability()) / 100);
												System.out.println("wValue in user 0: " + wValue);
											}
										}
										total = pAmount * wValue;
										System.out.println("total in user 0: " + total);
										count = count + total;
										// count = count + Integer.parseInt(opportunityDetails.getProposalAmount());
										System.out.println("count in org 0: " + count);
										System.out.println("in org currency == opp currency");
									} else {

										CurrencyConversion currencyConversion = currencyConversionRepository
												.findByReportingCurrencyAndConversionCurrencyAndLiveInd(
														organizationDetails.getTrade_currency(),
														opportunityDetails.getCurrency(), true);
										if (null != currencyConversion) {
											pAmount = Integer.parseInt(opportunityDetails.getProposalAmount());
											System.out.println("Trade currency in org 1: "
													+ organizationDetails.getTrade_currency());
											System.out.println("Conversion currency in org 1: "
													+ opportunityDetails.getCurrency());
											System.out.println("ProposalAmount in org 1: "
													+ opportunityDetails.getProposalAmount());
											InvestorOppStages oppStages = investorOppStagesRepo
													.getInvestorOppStagesByInvestorOppStagesId(
															opportunityDetails.getOppStage());
											if (null != oppStages) {
												if (0 != oppStages.getProbability()) {
													wValue = ((oppStages.getProbability()) / 100);
													System.out.println("wValue in user 1: " + wValue);
												}
											}
											total = pAmount * wValue;
											System.out.println("total in user 1: " + total);
											conversionAmount = (int) ((total)
													/ currencyConversion.getConversionFactor());
											System.out.println("conversionAmount in org 1: " + conversionAmount);
											count = count + conversionAmount;
											System.out.println("count in org 1: " + count);
											System.out.println("in org 0");
										} else {
											CurrencyConversion currencyConversion1 = currencyConversionRepository
													.findByReportingCurrencyAndConversionCurrencyAndLiveInd(
															opportunityDetails.getCurrency(),
															organizationDetails.getTrade_currency(), true);
											if (null != currencyConversion1) {
												pAmount = Integer.parseInt(opportunityDetails.getProposalAmount());
												System.out.println(
														"Trade currency in org 2: " + opportunityDetails.getCurrency());
												System.out.println("Conversion currency in org 2: "
														+ organizationDetails.getTrade_currency());
												System.out.println("ProposalAmount in org 2: " + pAmount);
												InvestorOppStages oppStages = investorOppStagesRepo
														.getInvestorOppStagesByInvestorOppStagesId(
																opportunityDetails.getOppStage());
												if (null != oppStages) {
													if (0 != oppStages.getProbability()) {
														wValue = ((oppStages.getProbability()) / 100);
														System.out.println("wValue in user 2: " + wValue);
													}
												}
												total = pAmount * wValue;
												System.out.println("total in user 2: " + total);
												conversionAmount = (int) ((total)
														/ currencyConversion1.getConversionFactor());
												System.out.println("conversionAmount in org 2: " + conversionAmount);
												count = count + conversionAmount;
												System.out.println("count in org 2: " + count);
												System.out.println("in org 1");
											} else {
												System.out.println("in org 2");
												map.put("message",
														"No Conversion Available from "
																+ organizationDetails.getTrade_currency() + "to"
																+ opportunityDetails.getCurrency());
												break;
											}
										}
									}
								}
							} else {
								System.out.println("in org 3");
								map.put("message", "Trade Currency Not Available for This Organization");
								count = count + Integer.parseInt(opportunityDetails.getProposalAmount());
							}
						}
					}
					OrganizationDetails organizationDetails = organizationRepository
							.getOrganizationDetailsById(investor.getOrganizationId());
					if (null != organizationDetails.getTrade_currency()) {
						System.out.println("in org trade currency : " + organizationDetails.getTrade_currency());
						map.put("tradeCurrency", organizationDetails.getTrade_currency());
					}
				} else {
					System.out.println("in org 4");
					map.put("message", "Opportunity not available for this Investor");
				}
			}
		} else {
			map.put("message", "Investor not available");
		}

		map.put("weightedValue", count);

		return map;
	}

	@Override
	public HashMap getInvestorWonOpportunityCountByInvestorId(String investorId) {
		HashMap map = new HashMap();
		List<InvestorOpportunity> oppList = investorOpportunityRepo.getByInvestorIdAndWonInd(investorId);
		map.put("opportunityWon", oppList.size());
		return map;
	}

	@Override
	public HashMap getInvestorWonOppProposalValueCountByInvestorId(String investorId, String userId, String orgId) {
		HashMap map = new HashMap();
		int count = 0;
		int conversionAmount = 0;
		Investor investor = investorRepo.getInvestorIdByIdAndLiveInd(investorId);
		if (null != investor) {
			if (investor.getUserId().equalsIgnoreCase(userId)) {

				List<InvestorOpportunity> opportunityList = investorOpportunityRepo.getByInvestorId(investorId);
				if (null != opportunityList && !opportunityList.isEmpty()) {
					for (InvestorOpportunity opportunityDetails : opportunityList) {
						if (!StringUtils.isEmpty(opportunityDetails.getProposalAmount())) {
							EmployeeDetails employeeDetails = employeeRepository
									.getEmployeeDetailsByEmployeeId(investor.getUserId());
							if (null != employeeDetails.getCurrency()) {
								Currency userCurrency = currencyRepository
										.getByCurrencyId(employeeDetails.getCurrency());
								if (null != userCurrency) {
									if (null != opportunityDetails.getCurrency()) {
										if (userCurrency.getCurrencyName()
												.equalsIgnoreCase(opportunityDetails.getCurrency())) {
											System.out.println(
													"Opp currency in user 0: " + opportunityDetails.getCurrency());
											System.out.println(
													"user currency in user 0: " + userCurrency.getCurrencyName());
											System.out.println("ProposalAmount in user 0: "
													+ opportunityDetails.getProposalAmount());
											count = count + Integer.parseInt(opportunityDetails.getProposalAmount());
											System.out.println("count in user 0: " + count);
											System.out.println("in user currency == opp currency");
										} else {

											CurrencyConversion currencyConversion = currencyConversionRepository
													.findByReportingCurrencyAndConversionCurrencyAndLiveInd(
															userCurrency.getCurrencyName(),
															opportunityDetails.getCurrency(), true);
											if (null != currencyConversion) {
												System.out.println(
														"Trade currency in user 1: " + userCurrency.getCurrencyName());
												System.out.println("Conversion currency in user 1: "
														+ opportunityDetails.getCurrency());
												System.out.println("ProposalAmount in user 1: "
														+ opportunityDetails.getProposalAmount());
												conversionAmount = (int) (Integer
														.parseInt(opportunityDetails.getProposalAmount())
														/ currencyConversion.getConversionFactor());
												System.out.println("conversionAmount in user 1: " + conversionAmount);
												count = count + conversionAmount;
												System.out.println("count in user 1: " + count);
												System.out.println("in user 0");
											} else {
												CurrencyConversion currencyConversion1 = currencyConversionRepository
														.findByReportingCurrencyAndConversionCurrencyAndLiveInd(
																opportunityDetails.getCurrency(),
																userCurrency.getCurrencyName(), true);
												if (null != currencyConversion1) {
													System.out.println("Trade currency in user 2: "
															+ opportunityDetails.getCurrency());
													System.out.println("Conversion currency in user 2: "
															+ userCurrency.getCurrencyName());
													System.out.println("ProposalAmount in user 2: "
															+ opportunityDetails.getProposalAmount());
													conversionAmount = (int) (Integer
															.parseInt(opportunityDetails.getProposalAmount())
															* currencyConversion1.getConversionFactor());
													System.out
															.println("conversionAmount in user 2: " + conversionAmount);
													count = count + conversionAmount;
													System.out.println("count in user 2: " + count);
													System.out.println("in user 1");
												} else {
													System.out.println("in user 2");
													map.put("message",
															"No Conversion Available from "
																	+ userCurrency.getCurrencyName() + "to"
																	+ opportunityDetails.getCurrency());
													break;
												}
											}
										}
									}
								}
							} else {
								System.out.println("in user 3");
								map.put("message", "Currency Not Available for user : "
										+ employeeService.getEmployeeFullName(userId));
								count = count + Integer.parseInt(opportunityDetails.getProposalAmount());
							}
						}
					}
					EmployeeDetails employeeDetails = employeeRepository
							.getEmployeeDetailsByEmployeeId(investor.getUserId());
					if (null != employeeDetails.getCurrency()) {
						Currency userCurrency = currencyRepository.getByCurrencyId(employeeDetails.getCurrency());
						if (null != userCurrency) {
							System.out.println("in user trade currency : " + userCurrency.getCurrencyName());
							map.put("tradeCurrency", userCurrency.getCurrencyName());
						}
					}
				} else {
					System.out.println("in user 4");
					map.put("message", "Opportunity not available for this Investor");
				}

			} else {

				List<InvestorOpportunity> opportunityList = investorOpportunityRepo.getByInvestorId(investorId);
				if (null != opportunityList && !opportunityList.isEmpty()) {
					for (InvestorOpportunity opportunityDetails : opportunityList) {
						if (!StringUtils.isEmpty(opportunityDetails.getProposalAmount())) {
							OrganizationDetails organizationDetails = organizationRepository
									.getOrganizationDetailsById(investor.getOrganizationId());
							if (null != organizationDetails.getTrade_currency()) {
								if (null != opportunityDetails.getCurrency()) {
									if (organizationDetails.getTrade_currency()
											.equalsIgnoreCase(opportunityDetails.getCurrency())) {
										System.out
												.println("Opp currency in org 0: " + opportunityDetails.getCurrency());
										System.out.println(
												"org currency in org 0: " + organizationDetails.getTrade_currency());
										System.out.println(
												"ProposalAmount in org 0: " + opportunityDetails.getProposalAmount());
										count = count + Integer.parseInt(opportunityDetails.getProposalAmount());
										System.out.println("count in org 0: " + count);
										System.out.println("in org currency == opp currency");
									} else {

										CurrencyConversion currencyConversion = currencyConversionRepository
												.findByReportingCurrencyAndConversionCurrencyAndLiveInd(
														organizationDetails.getTrade_currency(),
														opportunityDetails.getCurrency(), true);
										if (null != currencyConversion) {
											System.out.println("Trade currency in org 1: "
													+ organizationDetails.getTrade_currency());
											System.out.println("Conversion currency in org 1: "
													+ opportunityDetails.getCurrency());
											System.out.println("ProposalAmount in org 1: "
													+ opportunityDetails.getProposalAmount());
											conversionAmount = (int) (Integer
													.parseInt(opportunityDetails.getProposalAmount())
													/ currencyConversion.getConversionFactor());
											System.out.println("conversionAmount in org 1: " + conversionAmount);
											count = count + conversionAmount;
											System.out.println("count in org 1: " + count);
											System.out.println("in org 0");
										} else {
											CurrencyConversion currencyConversion1 = currencyConversionRepository
													.findByReportingCurrencyAndConversionCurrencyAndLiveInd(
															opportunityDetails.getCurrency(),
															organizationDetails.getTrade_currency(), true);
											if (null != currencyConversion1) {
												System.out.println(
														"Trade currency in org 2: " + opportunityDetails.getCurrency());
												System.out.println("Conversion currency in org 2: "
														+ organizationDetails.getTrade_currency());
												System.out.println("ProposalAmount in org 2: "
														+ opportunityDetails.getProposalAmount());
												conversionAmount = (int) (Integer
														.parseInt(opportunityDetails.getProposalAmount())
														* currencyConversion1.getConversionFactor());
												System.out.println("conversionAmount in org 2: " + conversionAmount);
												count = count + conversionAmount;
												System.out.println("count in org 2: " + count);
												System.out.println("in org 1");
											} else {
												System.out.println("in org 2");
												map.put("message",
														"No Conversion Available from "
																+ organizationDetails.getTrade_currency() + "to"
																+ opportunityDetails.getCurrency());
												break;
											}
										}
									}
								}
							} else {
								System.out.println("in org 3");
								map.put("message", "Trade Currency Not Available for This Organization");
								count = count + Integer.parseInt(opportunityDetails.getProposalAmount());
							}
						}
					}
					OrganizationDetails organizationDetails = organizationRepository
							.getOrganizationDetailsById(investor.getOrganizationId());
					if (null != organizationDetails.getTrade_currency()) {
						System.out.println("in org trade currency : " + organizationDetails.getTrade_currency());
						map.put("tradeCurrency", organizationDetails.getTrade_currency());
					}
				} else {
					System.out.println("in org 4");
					map.put("message", "Opportunity not available for this Investor");
				}
			}
		} else {
			map.put("message", "Investor not available");
		}

		map.put("WonPipeLineValue", count);

		return map;
	}

	@Override
	public HashMap getInvestorWonOppWeigthedValueCountByInvestorId(String investorId, String userId, String orgId) {
//		HashMap map = new HashMap();
//		double count = 0;
//		List<InvestorOpportunity> oppList = investorOpportunityRepo.getByInvestorIdAndWonInd(investorId);
//		if (null != oppList && !oppList.isEmpty()) {
//			for (InvestorOpportunity investorOpportunity : oppList) {
//				int pAmount = 0;
//				double wValue = 0;
//				double total = 0;
//				if (!StringUtils.isEmpty(investorOpportunity.getProposalAmount())) {
//					pAmount = Integer.parseInt(investorOpportunity.getProposalAmount());
//					InvestorOppStages invStage = investorOppStagesRepo.getById(investorOpportunity.getOppStage());
//					if (null != invStage) {
//						if (0 != invStage.getProbability()) {
//							wValue = ((invStage.getProbability()) / 100);
//						}
//					}
//					total = pAmount * wValue;
//					count = count + total;
//				}
//			}
//		}
//		map.put("weightedWonValue", count);
//		return map;

		HashMap map = new HashMap();
		double count = 0;
		int conversionAmount = 0;
		Investor investor = investorRepo.getInvestorIdByIdAndLiveInd(investorId);
		if (null != investor) {
			if (investor.getUserId().equalsIgnoreCase(userId)) {

				List<InvestorOpportunity> opportunityList = investorOpportunityRepo
						.getByInvestorIdAndWonInd(investorId);
				if (null != opportunityList && !opportunityList.isEmpty()) {
					for (InvestorOpportunity opportunityDetails : opportunityList) {
						int pAmount = 0;
						double wValue = 0;
						double total = 0;
						if (!StringUtils.isEmpty(opportunityDetails.getProposalAmount())) {
							EmployeeDetails employeeDetails = employeeRepository
									.getEmployeeDetailsByEmployeeId(investor.getUserId());
							if (null != employeeDetails.getCurrency()) {
								Currency userCurrency = currencyRepository
										.getByCurrencyId(employeeDetails.getCurrency());
								if (null != userCurrency) {
									if (null != opportunityDetails.getCurrency()) {
										if (userCurrency.getCurrencyName()
												.equalsIgnoreCase(opportunityDetails.getCurrency())) {

											pAmount = Integer.parseInt(opportunityDetails.getProposalAmount());
											System.out.println(
													"Opp currency in user 0: " + opportunityDetails.getCurrency());
											System.out.println(
													"user currency in user 0: " + userCurrency.getCurrencyName());
											System.out.println("ProposalAmount in user 0: " + pAmount);
											InvestorOppStages oppStages = investorOppStagesRepo
													.getInvestorOppStagesByInvestorOppStagesId(
															opportunityDetails.getOppStage());
											if (null != oppStages) {
												if (0 != oppStages.getProbability()) {
													wValue = ((oppStages.getProbability()) / 100);
													System.out.println("wValue in user 0: " + wValue);
												}
											}
											total = pAmount * wValue;
											System.out.println("total in user 0: " + total);
											count = count + total;

											// count = count + Integer.parseInt(opportunityDetails.getProposalAmount());
											System.out.println("count in user 0: " + count);
											System.out.println("in user currency == opp currency");
										} else {

											CurrencyConversion currencyConversion = currencyConversionRepository
													.findByReportingCurrencyAndConversionCurrencyAndLiveInd(
															userCurrency.getCurrencyName(),
															opportunityDetails.getCurrency(), true);
											if (null != currencyConversion) {
												pAmount = Integer.parseInt(opportunityDetails.getProposalAmount());
												System.out.println(
														"Trade currency in user 1: " + userCurrency.getCurrencyName());
												System.out.println("Conversion currency in user 1: "
														+ opportunityDetails.getCurrency());
												System.out.println("ProposalAmount in user 1: " + pAmount);
												InvestorOppStages oppStages = investorOppStagesRepo
														.getInvestorOppStagesByInvestorOppStagesId(
																opportunityDetails.getOppStage());
												if (null != oppStages) {
													if (0 != oppStages.getProbability()) {
														wValue = ((oppStages.getProbability()) / 100);
														System.out.println("wValue in user 1: " + wValue);
													}
												}
												total = pAmount * wValue;
												System.out.println("total in user 1: " + total);
												conversionAmount = (int) ((total)
														/ currencyConversion.getConversionFactor());
												System.out.println("conversionAmount in user 1: " + conversionAmount);
												count = count + conversionAmount;
												System.out.println("count in user 1: " + count);
												System.out.println("in user 0");
											} else {
												CurrencyConversion currencyConversion1 = currencyConversionRepository
														.findByReportingCurrencyAndConversionCurrencyAndLiveInd(
																opportunityDetails.getCurrency(),
																userCurrency.getCurrencyName(), true);
												if (null != currencyConversion1) {
													pAmount = Integer.parseInt(opportunityDetails.getProposalAmount());
													System.out.println("Trade currency in user 2: "
															+ opportunityDetails.getCurrency());
													System.out.println("Conversion currency in user 2: "
															+ userCurrency.getCurrencyName());
													System.out.println("ProposalAmount in user 2: " + pAmount);
													InvestorOppStages oppStages = investorOppStagesRepo
															.getInvestorOppStagesByInvestorOppStagesId(
																	opportunityDetails.getOppStage());
													if (null != oppStages) {
														if (0 != oppStages.getProbability()) {
															wValue = ((oppStages.getProbability()) / 100);
															System.out.println("wValue in user 1: " + wValue);
														}
													}
													total = pAmount * wValue;
													System.out.println("total in user 1: " + total);
													conversionAmount = (int) ((total)
															/ currencyConversion1.getConversionFactor());
													System.out
															.println("conversionAmount in user 2: " + conversionAmount);
													count = count + conversionAmount;
													System.out.println("count in user 2: " + count);
													System.out.println("in user 1");
												} else {
													System.out.println("in user 2");
													map.put("message",
															"No Conversion Available from "
																	+ userCurrency.getCurrencyName() + "to"
																	+ opportunityDetails.getCurrency());
													break;
												}
											}
										}
									}
								}
							} else {
								System.out.println("in user 3");
								map.put("message", "Currency Not Available for user : "
										+ employeeService.getEmployeeFullName(userId));
								count = count + Integer.parseInt(opportunityDetails.getProposalAmount());
							}
						}
					}
					EmployeeDetails employeeDetails = employeeRepository
							.getEmployeeDetailsByEmployeeId(investor.getUserId());
					if (null != employeeDetails.getCurrency()) {
						Currency userCurrency = currencyRepository.getByCurrencyId(employeeDetails.getCurrency());
						if (null != userCurrency) {
							System.out.println("in user trade currency : " + userCurrency.getCurrencyName());
							map.put("tradeCurrency", userCurrency.getCurrencyName());
						}
					}
				} else {
					System.out.println("in user 4");
					map.put("message", "Opportunity not available for this Investor");
				}

			} else {

				List<InvestorOpportunity> opportunityList = investorOpportunityRepo
						.getByInvestorIdAndWonInd(investorId);
				if (null != opportunityList && !opportunityList.isEmpty()) {
					for (InvestorOpportunity opportunityDetails : opportunityList) {
						int pAmount = 0;
						double wValue = 0;
						double total = 0;
						if (!StringUtils.isEmpty(opportunityDetails.getProposalAmount())) {
							OrganizationDetails organizationDetails = organizationRepository
									.getOrganizationDetailsById(investor.getOrganizationId());
							if (null != organizationDetails.getTrade_currency()) {
								if (null != opportunityDetails.getCurrency()) {
									if (organizationDetails.getTrade_currency()
											.equalsIgnoreCase(opportunityDetails.getCurrency())) {
										pAmount = Integer.parseInt(opportunityDetails.getProposalAmount());
										System.out
												.println("Opp currency in org 0: " + opportunityDetails.getCurrency());
										System.out.println(
												"org currency in org 0: " + organizationDetails.getTrade_currency());
										System.out.println("ProposalAmount in org 0: " + pAmount);
										InvestorOppStages oppStages = investorOppStagesRepo
												.getInvestorOppStagesByInvestorOppStagesId(
														opportunityDetails.getOppStage());
										if (null != oppStages) {
											if (0 != oppStages.getProbability()) {
												wValue = ((oppStages.getProbability()) / 100);
												System.out.println("wValue in user 0: " + wValue);
											}
										}
										total = pAmount * wValue;
										System.out.println("total in user 0: " + total);
										count = count + total;
										// count = count + Integer.parseInt(opportunityDetails.getProposalAmount());
										System.out.println("count in org 0: " + count);
										System.out.println("in org currency == opp currency");
									} else {

										CurrencyConversion currencyConversion = currencyConversionRepository
												.findByReportingCurrencyAndConversionCurrencyAndLiveInd(
														organizationDetails.getTrade_currency(),
														opportunityDetails.getCurrency(), true);
										if (null != currencyConversion) {
											pAmount = Integer.parseInt(opportunityDetails.getProposalAmount());
											System.out.println("Trade currency in org 1: "
													+ organizationDetails.getTrade_currency());
											System.out.println("Conversion currency in org 1: "
													+ opportunityDetails.getCurrency());
											System.out.println("ProposalAmount in org 1: "
													+ opportunityDetails.getProposalAmount());
											InvestorOppStages oppStages = investorOppStagesRepo
													.getInvestorOppStagesByInvestorOppStagesId(
															opportunityDetails.getOppStage());
											if (null != oppStages) {
												if (0 != oppStages.getProbability()) {
													wValue = ((oppStages.getProbability()) / 100);
													System.out.println("wValue in user 1: " + wValue);
												}
											}
											total = pAmount * wValue;
											System.out.println("total in user 1: " + total);
											conversionAmount = (int) ((total)
													/ currencyConversion.getConversionFactor());
											System.out.println("conversionAmount in org 1: " + conversionAmount);
											count = count + conversionAmount;
											System.out.println("count in org 1: " + count);
											System.out.println("in org 0");
										} else {
											CurrencyConversion currencyConversion1 = currencyConversionRepository
													.findByReportingCurrencyAndConversionCurrencyAndLiveInd(
															opportunityDetails.getCurrency(),
															organizationDetails.getTrade_currency(), true);
											if (null != currencyConversion1) {
												pAmount = Integer.parseInt(opportunityDetails.getProposalAmount());
												System.out.println(
														"Trade currency in org 2: " + opportunityDetails.getCurrency());
												System.out.println("Conversion currency in org 2: "
														+ organizationDetails.getTrade_currency());
												System.out.println("ProposalAmount in org 2: " + pAmount);
												InvestorOppStages oppStages = investorOppStagesRepo
														.getInvestorOppStagesByInvestorOppStagesId(
																opportunityDetails.getOppStage());
												if (null != oppStages) {
													if (0 != oppStages.getProbability()) {
														wValue = ((oppStages.getProbability()) / 100);
														System.out.println("wValue in user 2: " + wValue);
													}
												}
												total = pAmount * wValue;
												System.out.println("total in user 2: " + total);
												conversionAmount = (int) ((total)
														/ currencyConversion1.getConversionFactor());
												System.out.println("conversionAmount in org 2: " + conversionAmount);
												count = count + conversionAmount;
												System.out.println("count in org 2: " + count);
												System.out.println("in org 1");
											} else {
												System.out.println("in org 2");
												map.put("message",
														"No Conversion Available from "
																+ organizationDetails.getTrade_currency() + "to"
																+ opportunityDetails.getCurrency());
												break;
											}
										}
									}
								}
							} else {
								System.out.println("in org 3");
								map.put("message", "Trade Currency Not Available for This Organization");
								count = count + Integer.parseInt(opportunityDetails.getProposalAmount());
							}
						}
					}
					OrganizationDetails organizationDetails = organizationRepository
							.getOrganizationDetailsById(investor.getOrganizationId());
					if (null != organizationDetails.getTrade_currency()) {
						System.out.println("in org trade currency : " + organizationDetails.getTrade_currency());
						map.put("tradeCurrency", organizationDetails.getTrade_currency());
					}
				} else {
					System.out.println("in org 4");
					map.put("message", "Opportunity not available for this Investor");
				}
			}
		} else {
			map.put("message", "Investor not available");
		}

		map.put("weightedWonValue", count);

		return map;

	}

	@Override
	public InvestorOpportunityMapper saveInvestorOpportunity(InvestorOpportunityMapper opportunityMapper)
			throws TemplateException, IOException {
		InvestorOpportunityMapper id = investorOppService.saveInvestorOpportunity(opportunityMapper);

		return id;
	}

	@Override
	public HashMap getInvestorCountByCountry(String country) {
		List<AddressDetails> addressDetails = addressRepository.getAddressDetailsByCountry(country);
		HashMap map = new HashMap();
		int count = 0;
		if (null != addressDetails && !addressDetails.isEmpty()) {
			for (AddressDetails address : addressDetails) {
				List<InvestorAddressLink> investorAddressLinkList = investorAddressLinkRepo
						.getByAddressId(address.getAddressId());
				if (null != investorAddressLinkList && !investorAddressLinkList.isEmpty()) {
					for (InvestorAddressLink investorAddressLink : investorAddressLinkList) {
						List<Investor> investorList = investorRepo
								.getInvestorByIdAndLiveInd(investorAddressLink.getInvestorId());
						if (null != investorList) {
							count = count + investorList.size();
						}
					}
				}
			}
		}

		map.put("InvestorCountByCountry", count);
		return map;
	}

	@Override
	public List<InvestorViewMapper> getInvestorListByCountry(String country) {
		List<AddressDetails> addressDetails = addressRepository.getAddressDetailsByCountry(country);
		List<InvestorViewMapper> resultList = new ArrayList<>();
		int count = 0;
		if (null != addressDetails && !addressDetails.isEmpty()) {
			for (AddressDetails address : addressDetails) {
				List<InvestorAddressLink> investorAddressLinkList = investorAddressLinkRepo
						.getByAddressId(address.getAddressId());
				if (null != investorAddressLinkList && !investorAddressLinkList.isEmpty()) {
					for (InvestorAddressLink investorAddressLink : investorAddressLinkList) {
						List<Investor> investorList = investorRepo
								.getInvestorByIdAndLiveInd(investorAddressLink.getInvestorId());
						if (null != investorList && !investorList.isEmpty()) {
							for (Investor investor : investorList) {
								InvestorViewMapper mapper = getInvestorDetailsById(investor.getInvestorId());
								resultList.add(mapper);
							}

						}
					}
				}
			}
		}

		return resultList;
	}

	@Override
	public Set<InvestorViewMapper> getTeamInvestorListByUnderUserId(String userId, int pageNo, int pageSize) {
		Set<String> userIds = employeeRepository.getEmployeeDetailsByReportingManagerId(userId).stream()
				.map(EmployeeDetails::getUserId).collect(Collectors.toSet());
		userIds.add(userId);

		List<String> userIdss = new ArrayList<>(userIds);

		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("creationDate").descending());

		Page<Investor> investorPage = investorRepo.getTeamInvestorsListByUserIdsPaginated(userIdss, paging);

		Set<InvestorViewMapper> mapperSet = new HashSet<>();

		if (investorPage != null && !investorPage.isEmpty()) {
			mapperSet = investorPage.getContent().stream().map(li -> {
				InvestorViewMapper mapper = getInvestorDetailsById(li.getInvestorId());
				mapper.setPageCount(investorPage.getTotalPages());
				mapper.setDataCount(investorPage.getSize());
				mapper.setListCount(investorPage.getTotalElements());
				return mapper;
			}).collect(Collectors.toSet());
		}
		return mapperSet;
	}

	@Override
	public HashMap getTeamInvestorCountByUnderUserId(String userId) {
		HashMap map = new HashMap();

		Set<String> userIds = employeeRepository.getEmployeeDetailsByReportingManagerId(userId).stream()
				.map(EmployeeDetails::getUserId).collect(Collectors.toSet());
		userIds.add(userId);

		List<String> userIdss = new ArrayList<>(userIds);
		List<Investor> investorPage = investorRepo.getTeamInvestorsListByUserIds(userIdss);
		map.put("investorTeam", investorPage.size());

		return map;
	}

	@Override
	public Set<ContactViewMapper> getTeamInvestorContactListByUnderUserId(String userId, int pageNo, int pageSize) {
		Set<ContactViewMapper> result = new HashSet<>();
		Set<String> userIds = employeeRepository.getEmployeeDetailsByReportingManagerId(userId).stream()
				.map(EmployeeDetails::getUserId).collect(Collectors.toSet());
		userIds.add(userId);

		List<String> userIdss = new ArrayList<>(userIds);

		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("creationDate").descending());

		Page<ContactDetails> contactList = contactRepository.getTeamContactListdAndContactTypeAndLiveInd(userIdss,
				"Investor", paging);

		System.out.println("###########" + contactList);
		if (null != contactList && !contactList.isEmpty()) {
			result = contactList.getContent().stream().map(contact -> {
				ContactViewMapper contactMapper = contactService.getContactDetailsById(contact.getContactId());
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
	public HashMap getTeamInvestorContactCountByUnderUserId(String userId) {
		HashMap map = new HashMap();

		Set<String> userIds = employeeRepository.getEmployeeDetailsByReportingManagerId(userId).stream()
				.map(EmployeeDetails::getUserId).collect(Collectors.toSet());
		userIds.add(userId);

		List<String> userIdss = new ArrayList<>(userIds);

		List<ContactDetails> contactList = contactRepository.getTeamContactListByUserIds(userIdss, "Investor");
		map.put("investorContactTeam", contactList.size());

		return map;
	}

	@Override
	public List<InvestorOpportunityMapper> getWonOpportunityListByInvestorId(String investorId) {
		List<InvestorOpportunity> list = investorOpportunityRepo.getByInvestorIdAndWonInd(investorId);

		List<InvestorOpportunityMapper> mapperList = new ArrayList<>();
		list.stream().map(opportunityDetails -> {

			InvestorOpportunityMapper opportunityMapper = investorOppService
					.getOpportunityDetails(opportunityDetails.getInvOpportunityId());
			mapperList.add(opportunityMapper);
			return mapperList;
		}).collect(Collectors.toList());

		return mapperList;
	}

	@Override
	public void deleteInvestorById(String investorId) {
		Investor investor = investorRepo.getInvestorIdByIdAndLiveInd(investorId);
		if (null != investor) {
			investor.setLiveInd(false);
			investorRepo.save(investor);
		}
	}

	@Override
	public List<ContactViewMapper> getDeletedInvestorContactList(String userId) {
		List<ContactDetails> contactList = contactRepository.findByUserIdAndContactType(userId, "Investor");
		if (null != contactList && !contactList.isEmpty()) {
			return contactList.stream().map(contact -> contactService.getContactDetailsById(contact.getContactId()))
					.collect(Collectors.toList());
		}
		return Collections.emptyList();
	}

	@Override
	public InvestorShareMapper saveShare(InvestorShareMapper investorShareMapper)
			throws IOException, TemplateException {

		InvestorsShare investorsShare = new InvestorsShare();

		try {
			investorsShare.setBuyingDate(Utility.getDateFromISOString(investorShareMapper.getBuyingDate()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		investorsShare.setLiveInd(true);
		investorsShare.setCreationDate(new Date());
		investorsShare.setCurrency(investorShareMapper.getCurrency());
		investorsShare.setDocumentId(investorShareMapper.getDocumentId());
		investorsShare.setInvestorId(investorShareMapper.getInvestorId());
		investorsShare.setOrgId(investorShareMapper.getOrgId());
		investorsShare.setAmountPerShare(investorShareMapper.getAmountPerShare());
		investorsShare.setQuantityOfShare(investorShareMapper.getQuantityOfShare());
		investorsShare.setTotalAmountOfShare(
				investorShareMapper.getQuantityOfShare() * investorShareMapper.getAmountPerShare());
		investorsShare.setUserId(investorShareMapper.getUserId());
		try {
			investorsShare.setBuyingDate(Utility.getDateFromISOString(investorShareMapper.getBuyingDate()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		;
		String id = investorShareRepo.save(investorsShare).getInvestorsShareId();

		Investor investor = investorRepo.getById(investorShareMapper.getInvestorId());
		if (null != investor) {

			double investorShareCount = 0;
			List<InvestorsShare> investorsShares = investorShareRepo
					.findByInvestorIdAndLiveInd(investorShareMapper.getInvestorId(), true);
			if (null != investorsShare && !investorsShares.isEmpty()) {
				for (InvestorsShare share : investorsShares) {
					investorShareCount = investorShareCount + share.getQuantityOfShare();
				}
			}

			List<Club> clubs = clubRepository.findAllByLiveIndTrueOrderByNoOfShareDesc();
			Club previousClub = null;
			if (null != clubs && !clubs.isEmpty()) {
				for (Club club : clubs) {
					if (investorShareCount >= club.getNoOfShare()) {
						previousClub = (previousClub != null ? previousClub : club);
					}
					// previousClub = club;
				}
			}

			if (null != previousClub) {
				investor.setClub(previousClub.getClubId());
				investorRepo.save(investor);
			}

			if (null != investor.getClub() && !investor.getClub().isEmpty()) {
				Club club = clubRepository.findByClubIdAndLiveIndAndInvToCusInd(investor.getClub(), true, true);
				if (null != club) {
					if (club.isInvToCusInd()) {
						Distributor distributor1 = distributorRepository
								.findByInvestorAndActive(investor.getInvestorId(), true);
						if (null != distributor1) {
							distributor1.setClub(club.getClubId());
							distributorRepository.save(distributor1);
						} else {
							Distributor distributor = new Distributor();
							distributor.setName(investor.getName());
							// distributor.setImageId(distributorDTO.getImageId());
							distributor.setUrl(investor.getUrl());
							distributor.setPhoneNo(investor.getPhoneNumber());
							distributor.setDialCode(investor.getCountryDialCode());
							distributor.setDescription(investor.getNotes());
							distributor.setMobileNo(investor.getPhoneNumber());
							distributor.setEmailId(investor.getEmail());
							distributor.setImageURL(investor.getImageURL());
							distributor.setCountryId(investor.getCountry());
							if (investor.getCountry() != null) {
								Country country = countryRepository.getByCountryId(investor.getCountry());
								distributor.setCountryName(country.getCountryName());
							}
							distributor.setAssignTo(investor.getAssignedTo());
							distributor.setUserId(investor.getUserId());
							distributor.setOrgId(investor.getOrganizationId());
							distributor.setCreateAt(new Date());
							distributor.setInvestor(investor.getInvestorId());
							distributor.setClub(investor.getClub());

							distributorRepository.save(distributor);

							List<InvestorAddressLink> investorAddressList = investorAddressLinkRepo
									.getAddressListByInvestorId(investor.getInvestorId());

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
									.getContactIdByInvestorId(investor.getInvestorId());
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
											contact.setFullName(
													contact1.getFirst_name() + " " + middleName3 + " " + lastName3);
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
														.getAddressDetailsByAddressId(addressMapper.getAddress_id());

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
														addressDetails
																.setAddressLine1(addressDetails1.getAddressLine1());
														addressDetails
																.setAddressLine2(addressDetails1.getAddressLine2());
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

										System.out.println(
												"contactdetails id.........." + dbContact.getContact_details_id());

										/* insert to notification table */
										Notificationparam param = new Notificationparam();
										EmployeeDetails emp = employeeRepository
												.getEmployeesByuserId(contact1.getUser_id());
										String name = employeeService.getEmployeeFullNameByObject(emp);
										param.setEmployeeDetails(emp);
										param.setAdminMsg("Contact "
												+ "'" + Utility.FullName(contact1.getFirst_name(),
														contact1.getMiddle_name(), contact1.getLast_name())
												+ "' created by " + name);
										param.setOwnMsg(
												"Contact "
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
								distribitorToday.setDistributorCount(distribitorToday.getDistributorCount() + 1);
								distribitorToday.setPendingDistributor(distribitorToday.getPendingDistributor() + 1);
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
								yearlyDistributor.setDistributorCount(yearlyDistributor.getDistributorCount() + 1);
								yearlyDistributor.setPendingDistributor(yearlyDistributor.getPendingDistributor() + 1);
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
							MonthlyDistributor monthlyDistributor = monthlyDistributorRepository.findByMonth(month);
							if (monthlyDistributor != null) {
								monthlyDistributor.setDistributorCount(monthlyDistributor.getDistributorCount() + 1);
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
							EmployeeDetails emp = employeeRepository.getEmployeesByuserId(investor.getUserId());
							String name = employeeService.getEmployeeFullNameByObject(emp);
							param.setEmployeeDetails(emp);
							param.setAdminMsg("Customer " + "'" + investor.getName() + "' created by " + name);
							param.setOwnMsg("Customer " + investor.getName() + " created.");
							param.setNotificationType("Customer Creation");
							param.setProcessNmae("Customer");
							param.setType("create");
							param.setEmailSubject("Korero alert- Customer created");
							param.setCompanyName(companyName);
							param.setUserId(investor.getUserId());

							if (!investor.getUserId().equals(investor.getAssignedTo())) {
								List<String> assignToUserIds = new ArrayList<>();
								assignToUserIds.add(investor.getAssignedTo());
								param.setAssignToUserIds(assignToUserIds);
								param.setAssignToMsg("Customer " + "'" + investor.getName() + "'" + " assigned to "
										+ employeeService.getEmployeeFullName(investor.getAssignedTo()) + " by "
										+ name);
							}
							notificationService.createNotificationForDynamicUsers(param);
						}
					}
				}
			}
		}

		return getShareByInvestorShareId(id);
	}

	@Override
	public InvestorShareMapper getShareByInvestorShareId(String investorsShareId) {
		InvestorShareMapper mapper = new InvestorShareMapper();
		InvestorsShare investorsShare = investorShareRepo.getById(investorsShareId);
		if (null != investorsShare) {
			mapper.setAmountPerShare(investorsShare.getAmountPerShare());
			mapper.setBuyingDate(Utility.getISOFromDate(investorsShare.getBuyingDate()));
			mapper.setCreationDate(Utility.getISOFromDate(investorsShare.getCreationDate()));
			Currency currency = currencyRepository.getByCurrencyId(investorsShare.getCurrency());
			if (null != currency) {
				mapper.setCurrency(currency.getCurrencyName());
			}
			mapper.setDocumentId(investorsShare.getDocumentId());
			mapper.setInvestorId(investorsShare.getInvestorId());
			mapper.setOrgId(investorsShare.getOrgId());
			mapper.setQuantityOfShare(investorsShare.getQuantityOfShare());
			mapper.setTotalAmountOfShare(investorsShare.getTotalAmountOfShare());
			mapper.setUserId(investorsShare.getUserId());
			mapper.setInvestorsShareId(investorsShare.getInvestorsShareId());
		}
		return mapper;
	}

	@Override
	public List<InvestorShareMapper> getAllInvestorShareList(String investorId) {
		List<InvestorShareMapper> resultMapper = new ArrayList<>();
		List<InvestorsShare> investorsShare = investorShareRepo.findByInvestorIdAndLiveInd(investorId, true);
		if (null != investorsShare && !investorsShare.isEmpty()) {
			return investorsShare.stream().map(share -> getShareByInvestorShareId(share.getInvestorsShareId()))
					.collect(Collectors.toList());
		}
		return resultMapper;
	}

	@Override
	public InvestorShareMapper getAllInvestorShare(String investorId) {
		InvestorShareMapper mapper = new InvestorShareMapper();
		double totalunit = 0;
		double totalValue = 0;
		List<InvestorsShare> investorsShare = investorShareRepo.findByInvestorIdAndLiveInd(investorId, true);
		if (null != investorsShare && !investorsShare.isEmpty()) {
			for (InvestorsShare share : investorsShare) {
				totalunit = totalunit + share.getQuantityOfShare();
				totalValue = totalValue + share.getTotalAmountOfShare();
			}
			mapper.setAllTotalAmountOfShare(totalValue);
			mapper.setAllTotalQuantityOfShare(totalunit);
		}
		return mapper;
	}

	@Override
	public void deleteInvestorShareByInvestorShareId(String investorShareId, String userId) {
		InvestorsShare investorsShare = investorShareRepo.getById(investorShareId);
		if (null != investorsShare) {
			investorsShare.setLiveInd(false);
			investorsShare.setUpdationDate(new Date());
			investorsShare.setUpdatedBy(userId);
			investorShareRepo.save(investorsShare);
		}
	}

	@Override
	public InvestorShareMapper updateInvestorShare(InvestorShareMapper investorShareMapper)
			throws IOException, TemplateException {
		InvestorShareMapper mapper = new InvestorShareMapper();
		InvestorsShare investorsShare = investorShareRepo.getById(investorShareMapper.getInvestorsShareId());
		if (null != investorsShare) {

			try {
				investorsShare.setBuyingDate(Utility.getDateFromISOString(investorShareMapper.getBuyingDate()));
			} catch (Exception e) {
				e.printStackTrace();
			}
			investorsShare.setUpdationDate(new Date());
			investorsShare.setCurrency(investorShareMapper.getCurrency());
			investorsShare.setDocumentId(investorShareMapper.getDocumentId());
			investorsShare.setInvestorId(investorShareMapper.getInvestorId());
			investorsShare.setAmountPerShare(investorShareMapper.getAmountPerShare());
			investorsShare.setQuantityOfShare(investorShareMapper.getQuantityOfShare());
			investorsShare.setTotalAmountOfShare(
					investorShareMapper.getQuantityOfShare() * investorShareMapper.getAmountPerShare());
			investorsShare.setUpdatedBy(investorShareMapper.getUserId());
			mapper = getShareByInvestorShareId(investorShareRepo.save(investorsShare).getInvestorsShareId());

			Investor investor = investorRepo.getById(investorShareMapper.getInvestorId());
			if (null != investor) {

				double investorShareCount = 0;
				List<InvestorsShare> investorsShares = investorShareRepo
						.findByInvestorIdAndLiveInd(investorShareMapper.getInvestorId(), true);
				if (null != investorsShare && !investorsShares.isEmpty()) {
					for (InvestorsShare share : investorsShares) {
						investorShareCount = investorShareCount + share.getQuantityOfShare();
					}
				}

				List<Club> clubs = clubRepository.findAllByLiveIndTrueOrderByNoOfShareDesc();
				Club previousClub = null;
				if (null != clubs && !clubs.isEmpty()) {
					for (Club club : clubs) {
						if (investorShareCount >= club.getNoOfShare()) {
							previousClub = (previousClub != null ? previousClub : club);
						}
						// previousClub = club;
					}
				}

				if (null != previousClub) {
					investor.setClub(previousClub.getClubId());
					investorRepo.save(investor);
				}

				if (null != investor.getClub() && !investor.getClub().isEmpty()) {
					Club club = clubRepository.findByClubIdAndLiveIndAndInvToCusInd(investor.getClub(), true, true);
					if (null != club) {
						if (club.isInvToCusInd()) {
							Distributor distributor1 = distributorRepository
									.findByInvestorAndActive(investor.getInvestorId(), true);
							if (null != distributor1) {
								distributor1.setClub(club.getClubId());
								distributorRepository.save(distributor1);
							} else {
								Distributor distributor = new Distributor();
								distributor.setName(investor.getName());
								// distributor.setImageId(distributorDTO.getImageId());
								distributor.setUrl(investor.getUrl());
								distributor.setPhoneNo(investor.getPhoneNumber());
								distributor.setDialCode(investor.getCountryDialCode());
								distributor.setDescription(investor.getNotes());
								distributor.setMobileNo(investor.getPhoneNumber());
								distributor.setEmailId(investor.getEmail());
								distributor.setImageURL(investor.getImageURL());
								distributor.setCountryId(investor.getCountry());
								if (investor.getCountry() != null) {
									Country country = countryRepository.getByCountryId(investor.getCountry());
									distributor.setCountryName(country.getCountryName());
								}
								distributor.setAssignTo(investor.getAssignedTo());
								distributor.setUserId(investor.getUserId());
								distributor.setOrgId(investor.getOrganizationId());
								distributor.setCreateAt(new Date());
								distributor.setInvestor(investor.getInvestorId());
								distributor.setClub(investor.getClub());

								distributorRepository.save(distributor);

								List<InvestorAddressLink> investorAddressList = investorAddressLinkRepo
										.getAddressListByInvestorId(investor.getInvestorId());

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
										.getContactIdByInvestorId(investor.getInvestorId());
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
												contact.setFullName(
														contact1.getFirst_name() + " " + middleName3 + " " + lastName3);
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
															addressDetails
																	.setAddressLine1(addressDetails1.getAddressLine1());
															addressDetails
																	.setAddressLine2(addressDetails1.getAddressLine2());
															addressDetails
																	.setAddressType(addressDetails1.getAddressType());
															addressDetails.setCountry(addressDetails1.getCountry());
															addressDetails.setCreationDate(new Date());
															addressDetails.setStreet(addressDetails1.getStreet());
															addressDetails.setCity(addressDetails1.getCity());
															addressDetails
																	.setPostalCode(addressDetails1.getPostalCode());
															addressDetails.setTown(addressDetails1.getTown());
															addressDetails.setState(addressDetails1.getState());
															addressDetails.setLatitude(addressDetails1.getLatitude());
															addressDetails.setLongitude(addressDetails1.getLongitude());
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

											System.out.println(
													"contactdetails id.........." + dbContact.getContact_details_id());

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
									distribitorToday.setDistributorCount(distribitorToday.getDistributorCount() + 1);
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
									yearlyDistributor.setDistributorCount(yearlyDistributor.getDistributorCount() + 1);
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
								MonthlyDistributor monthlyDistributor = monthlyDistributorRepository.findByMonth(month);
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
								EmployeeDetails emp = employeeRepository.getEmployeesByuserId(investor.getUserId());
								String name = employeeService.getEmployeeFullNameByObject(emp);
								param.setEmployeeDetails(emp);
								param.setAdminMsg("Customer " + "'" + investor.getName() + "' created by " + name);
								param.setOwnMsg("Customer " + investor.getName() + " created.");
								param.setNotificationType("Customer Creation");
								param.setProcessNmae("Customer");
								param.setType("create");
								param.setEmailSubject("Korero alert- Customer created");
								param.setCompanyName(companyName);
								param.setUserId(investor.getUserId());

								if (!investor.getUserId().equals(investor.getAssignedTo())) {
									List<String> assignToUserIds = new ArrayList<>();
									assignToUserIds.add(investor.getAssignedTo());
									param.setAssignToUserIds(assignToUserIds);
									param.setAssignToMsg("Customer " + "'" + investor.getName() + "'" + " assigned to "
											+ employeeService.getEmployeeFullName(investor.getAssignedTo()) + " by "
											+ name);
								}
								notificationService.createNotificationForDynamicUsers(param);
							}
						}
					}
				}

			}
		}
		return mapper;
	}

	@Override
	public DocumentTypeMapper createAndUpdateInvestorDocType(InvestorDocTypeMapper mapper) {
		InvestorDocumentType investorDocumentType = investorDocumentTypeRepo
				.findByDocumentTypeIdAndInvestorIdAndLiveInd(mapper.getDocumentTypeId(), mapper.getInvestorId(), true);
		if (null != investorDocumentType) {
			investorDocumentType.setAvailableInd(mapper.isAvailableInd());
			investorDocumentType.setUpdatedBy(mapper.getUserId());
			investorDocumentType.setUpdationDate(new Date());
			InvestorDocumentType invDocType = investorDocumentTypeRepo.save(investorDocumentType);
			DocumentTypeMapper docTypeMapper = documentService.getdocumentTypes(invDocType.getDocumentTypeId());
			docTypeMapper.setAvailableInd(invDocType.isAvailableInd());
			if (null != invDocType.getCreationDate()) {
				docTypeMapper.setCreationDate(Utility.getISOFromDate(invDocType.getCreationDate()));
			}
			if (null != invDocType.getUpdationDate()) {
				docTypeMapper.setUpdationDate(Utility.getISOFromDate(invDocType.getUpdationDate()));
			}
			return docTypeMapper;
		} else {
			InvestorDocumentType invDocType = new InvestorDocumentType();
			invDocType.setDocumentTypeId(mapper.getDocumentTypeId());
			invDocType.setInvestorId(mapper.getInvestorId());
			invDocType.setLiveInd(true);
			invDocType.setAvailableInd(mapper.isAvailableInd());
			invDocType.setUserId(mapper.getUserId());
			invDocType.setCreationDate(new Date());
			invDocType.setUpdatedBy(mapper.getUserId());
			invDocType.setUpdationDate(new Date());
			InvestorDocumentType invDocType1 = investorDocumentTypeRepo.save(invDocType);
			DocumentTypeMapper docTypeMapper = documentService.getdocumentTypes(invDocType1.getDocumentTypeId());
			docTypeMapper.setAvailableInd(invDocType.isAvailableInd());
			if (null != invDocType.getCreationDate()) {
				docTypeMapper.setCreationDate(Utility.getISOFromDate(invDocType.getCreationDate()));
			}
			if (null != invDocType.getUpdationDate()) {
				docTypeMapper.setUpdationDate(Utility.getISOFromDate(invDocType.getUpdationDate()));
			}
			return docTypeMapper;
		}
	}

	@Override
	public List<DocumentTypeMapper> getDocTypesByInvestor(String investorId, String userId, String orgId) {
		List<DocumentTypeMapper> mappers = new ArrayList<>();
		List<DocumentType> documentTypes = documentTypeRepository
				.getDocumentTypeListByOrgIdAndUserTypeWithLiveIndAndMandatoryInd(orgId, "Investor");
		if (null != documentTypes && !documentTypes.isEmpty()) {
			for (DocumentType doc : documentTypes) {
				DocumentTypeMapper docTypeMapper = documentService.getdocumentTypes(doc.getDocument_type_id());
				InvestorDocumentType investorDocumentType = investorDocumentTypeRepo
						.findByDocumentTypeIdAndInvestorIdAndLiveInd(doc.getDocument_type_id(), investorId, true);
				if (null != investorDocumentType) {

					docTypeMapper.setAvailableInd(investorDocumentType.isAvailableInd());
					if (null != investorDocumentType.getCreationDate()) {
						docTypeMapper.setCreationDate(Utility.getISOFromDate(investorDocumentType.getCreationDate()));
					}
					if (null != investorDocumentType.getUpdationDate()) {
						docTypeMapper.setUpdationDate(Utility.getISOFromDate(investorDocumentType.getUpdationDate()));
					}
				}
				mappers.add(docTypeMapper);
			}
		}
		return mappers;
	}

	@Override
	public List<InvestorViewMapper> getInvestorDetailsByNameInOrgLevel(String name, String orgId) {
		List<Investor> list = investorRepo.getInvestorListByOrgId(orgId);
		List<Investor> filterList = list.parallelStream().filter(detail -> {
			return detail.getName() != null && Utility.containsIgnoreCase(detail.getName(), name.trim());
		}).collect(Collectors.toList());
		List<InvestorViewMapper> mapperList = new ArrayList<InvestorViewMapper>();
		if (null != filterList && !filterList.isEmpty()) {

			mapperList = filterList.parallelStream().map(li -> getInvestorDetailsById(li.getInvestorId()))
					.collect(Collectors.toList());

		}

		return mapperList;
	}

	@Override
	public List<InvestorViewMapper> getInvestorDetailsByNameForTeam(String name, String userId) {
		Set<String> userIds = employeeRepository.getEmployeeDetailsByReportingManagerId(userId).stream()
				.map(EmployeeDetails::getUserId).collect(Collectors.toSet());
		userIds.add(userId);
		List<String> userIdss = new ArrayList<>(userIds);
		List<Investor> list = investorRepo.getTeamInvestorsListByUserIds(userIdss);
		List<Investor> filterList = list.parallelStream().filter(detail -> {
			return detail.getName() != null && Utility.containsIgnoreCase(detail.getName(), name.trim());
		}).collect(Collectors.toList());
		List<InvestorViewMapper> mapperList = new ArrayList<InvestorViewMapper>();
		if (null != filterList && !filterList.isEmpty()) {
			mapperList = filterList.parallelStream().map(li -> getInvestorDetailsById(li.getInvestorId()))
					.collect(Collectors.toList());
		}
		return mapperList;
	}

	@Override
	public List<InvestorViewMapper> getInvestorDetailsByNameInUserLevel(String name, String userId) {
		List<Investor> list = investorRepo.getByUserIdandLiveInd(userId);
		List<Investor> filterList = list.parallelStream().filter(detail -> {
			return detail.getName() != null && Utility.containsIgnoreCase(detail.getName(), name.trim());
		}).collect(Collectors.toList());
		List<InvestorViewMapper> mapperList = new ArrayList<InvestorViewMapper>();
		if (null != filterList && !filterList.isEmpty()) {
			mapperList = filterList.parallelStream().map(li -> getInvestorDetailsById(li.getInvestorId()))
					.collect(Collectors.toList());
		}
		return mapperList;
	}

	@Override
	public List<InvestorViewMapper> getInvestorBySectorInOrgLevel(String name, String orgId) {
		List<InvestorViewMapper> mapperList = new ArrayList<>();
		SectorDetails sectorDetails = sectorDetailsRepository.getSectorDetailsBySectorNameAndOrgId(name, orgId);
		if (null != sectorDetails) {
			List<Investor> detailsList = investorRepo
					.findBySectorAndLiveIndAndOrganizationId(sectorDetails.getSectorId(), true, orgId);
			if (null != detailsList && !detailsList.isEmpty()) {
				mapperList = detailsList.stream().map(li -> getInvestorDetailsById(li.getInvestorId()))
						.collect(Collectors.toList());
			}
		}
		return mapperList;
	}

	@Override
	public List<InvestorViewMapper> getInvestorBySourceInOrgLevel(String name, String orgId) {
		List<InvestorViewMapper> mapperList = new ArrayList<>();
		Source source = sourceRepository.findByNameAndOrgId(name, orgId);
		if (null != source) {
			List<Investor> detailsList = investorRepo.findBySourceAndLiveIndAndOrganizationId(source.getSourceId(),
					true, orgId);
			if (null != detailsList && !detailsList.isEmpty()) {
				mapperList = detailsList.stream().map(li -> getInvestorDetailsById(li.getInvestorId()))
						.collect(Collectors.toList());
			}
		}
		return mapperList;
	}

	@Override
	public List<InvestorViewMapper> getInvestorByOwnerNameInOrgLevel(String name, String orgId) {
		List<InvestorViewMapper> mapperList = new ArrayList<>();

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
		    System.out.println("ownerName====="+ownerName);
		}

		if (null != filterList && !filterList.isEmpty()) {
			mapperList = filterList.stream().flatMap(
					employeeDetails -> investorRepo.findByUserIdandLiveInd(employeeDetails.getUserId()).stream())
					.map(investor -> getInvestorDetailsById(investor.getInvestorId())).collect(Collectors.toList());
		}

		return mapperList;
	}

	@Override
	public List<InvestorViewMapper> getInvestorByClubInOrgLevel(String name, String orgId) {
		List<InvestorViewMapper> mapperList = new ArrayList<>();
		Club club = clubRepository.findByClubNameAndOrgIdAndLiveInd(name, orgId, true);
		if (null != club) {
			List<Investor> detailsList = investorRepo.findByClubAndLiveIndAndOrganizationId(club.getClubId(), true,
					orgId);
			if (null != detailsList && !detailsList.isEmpty()) {
				mapperList = detailsList.stream().map(li -> getInvestorDetailsById(li.getInvestorId()))
						.collect(Collectors.toList());
			}
		}
		return mapperList;
	}

	@Override
	public List<InvestorViewMapper> getInvestorBySectorInUserLevel(String name, String userId, String orgId) {
		List<InvestorViewMapper> mapperList = new ArrayList<>();
		SectorDetails sectorDetails = sectorDetailsRepository.getSectorDetailsBySectorNameAndOrgId(name, orgId);
		if (null != sectorDetails) {
			List<Investor> detailsList = investorRepo.findBySectorAndLiveIndAndUserId(sectorDetails.getSectorId(), true,
					userId);
			if (null != detailsList && !detailsList.isEmpty()) {
				mapperList = detailsList.stream().map(li -> getInvestorDetailsById(li.getInvestorId()))
						.collect(Collectors.toList());
			}
		}
		return mapperList;
	}

	@Override
	public List<InvestorViewMapper> getInvestorBySourceInUserLevel(String name, String userId, String orgId) {
		List<InvestorViewMapper> mapperList = new ArrayList<>();
		Source source = sourceRepository.findByNameAndOrgId(name, orgId);
		if (null != source) {
			List<Investor> detailsList = investorRepo.findBySourceAndLiveIndAndUserId(source.getSourceId(), true,
					userId);
			if (null != detailsList && !detailsList.isEmpty()) {
				mapperList = detailsList.stream().map(li -> getInvestorDetailsById(li.getInvestorId()))
						.collect(Collectors.toList());
			}
		}
		return mapperList;
	}

	@Override
	public List<InvestorViewMapper> getInvestorByOwnerNameInUserLevel(String name, String userId, String orgId) {
		List<InvestorViewMapper> mapperList = new ArrayList<>();

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
		    System.out.println("ownerName====="+ownerName);
		}

		if (null != filterList && !filterList.isEmpty()) {
			mapperList = filterList.stream().flatMap(
					employeeDetails -> investorRepo.findByUserIdandLiveInd(employeeDetails.getUserId()).stream())
					.map(investor -> getInvestorDetailsById(investor.getInvestorId())).collect(Collectors.toList());
		}
		return mapperList;
	}

	@Override
	public List<InvestorViewMapper> getInvestorByClubInUserLevel(String name, String userId, String orgId) {
		List<InvestorViewMapper> mapperList = new ArrayList<>();
		Club club = clubRepository.findByClubNameAndOrgIdAndLiveInd(name, orgId, true);
		if (null != club) {
			List<Investor> detailsList = investorRepo.findByClubAndLiveIndAndUserId(club.getClubId(), true, userId);
			if (null != detailsList && !detailsList.isEmpty()) {
				mapperList = detailsList.stream().map(li -> getInvestorDetailsById(li.getInvestorId()))
						.collect(Collectors.toList());
			}
		}
		return mapperList;
	}

	@Override
	public List<InvestorViewMapper> getInvestorBySectorForTeam(String name, String userId, String orgId) {
		List<InvestorViewMapper> mapperList = new ArrayList<>();
		SectorDetails sectorDetails = sectorDetailsRepository.getSectorDetailsBySectorNameAndOrgId(name, orgId);
		if (null != sectorDetails) {
			Set<String> userIds = employeeRepository.getEmployeeDetailsByReportingManagerId(userId).stream()
					.map(EmployeeDetails::getUserId).collect(Collectors.toSet());
			userIds.add(userId);
			List<String> userIdss = new ArrayList<>(userIds);
			List<Investor> filterList = investorRepo.getTeamInvestorsListByUserIdsAndSector(userIdss,
					sectorDetails.getSectorId());
			mapperList = new ArrayList<InvestorViewMapper>();
			if (null != filterList && !filterList.isEmpty()) {
				mapperList = filterList.parallelStream().map(li -> getInvestorDetailsById(li.getInvestorId()))
						.collect(Collectors.toList());
			}
		}
		return mapperList;
	}

	@Override
	public List<InvestorViewMapper> getInvestorBySourceForTeam(String name, String userId, String orgId) {
		List<InvestorViewMapper> mapperList = new ArrayList<>();
		Source source = sourceRepository.findByNameAndOrgId(name, orgId);
		if (null != source) {
			Set<String> userIds = employeeRepository.getEmployeeDetailsByReportingManagerId(userId).stream()
					.map(EmployeeDetails::getUserId).collect(Collectors.toSet());
			userIds.add(userId);
			List<String> userIdss = new ArrayList<>(userIds);
			List<Investor> filterList = investorRepo.getTeamInvestorsListByUserIdsAndSource(userIdss,
					source.getSourceId());
			mapperList = new ArrayList<InvestorViewMapper>();
			if (null != filterList && !filterList.isEmpty()) {
				mapperList = filterList.parallelStream().map(li -> getInvestorDetailsById(li.getInvestorId()))
						.collect(Collectors.toList());
			}
		}
		return mapperList;
	}

	@Override
	public List<InvestorViewMapper> getInvestorByOwnerNameForTeam(String name, String userId, String orgId) {
		List<InvestorViewMapper> mapperList = new ArrayList<>();

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
		    System.out.println("ownerName====="+ownerName);
		}

		if (null != filterList && !filterList.isEmpty()) {
			mapperList = filterList.stream().flatMap(
					employeeDetails -> investorRepo.findByUserIdandLiveInd(employeeDetails.getUserId()).stream())
					.map(investor -> getInvestorDetailsById(investor.getInvestorId())).collect(Collectors.toList());
		}
		return mapperList;
	}

	@Override
	public List<InvestorViewMapper> getInvestorByClubInForTeam(String name, String userId, String orgId) {
		List<InvestorViewMapper> mapperList = new ArrayList<>();
		Club club = clubRepository.findByClubNameAndOrgIdAndLiveInd(name, orgId, true);
		if (null != club) {
			Set<String> userIds = employeeRepository.getEmployeeDetailsByReportingManagerId(userId).stream()
					.map(EmployeeDetails::getUserId).collect(Collectors.toSet());
			userIds.add(userId);
			List<String> userIdss = new ArrayList<>(userIds);
			List<Investor> filterList = investorRepo.getTeamInvestorsListByUserIdsAndClub(userIdss, club.getClubId());
			mapperList = new ArrayList<InvestorViewMapper>();
			if (null != filterList && !filterList.isEmpty()) {
				mapperList = filterList.parallelStream().map(li -> getInvestorDetailsById(li.getInvestorId()))
						.collect(Collectors.toList());
			}
		}
		return mapperList;
	}

	@Override
	public List<InvestorViewMapper> getInvestorListByOrgIdAndClubType(String orgId, int pageNo, int pageSize,
			String clubType) {
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("creationDate").descending());
		List<InvestorViewMapper> resultMapper = new ArrayList<>();
		Page<Investor> investorList = investorRepo.getInvestorListPageWiseByOrgIdAndClub(orgId, clubType, paging);
		if (null != investorList && !investorList.isEmpty()) {
			resultMapper = investorList.stream().map(investor -> {
				InvestorViewMapper mapper = getInvestorDetailsById(investor.getInvestorId());
				mapper.setPageCount(investorList.getTotalPages());
				mapper.setDataCount(investorList.getSize());
				mapper.setListCount(investorList.getTotalElements());
				return mapper;
			}).collect(Collectors.toList());
		}

		return resultMapper;
	}

	@Override
	public Set<InvestorViewMapper> getTeamInvestorListByUnderUserIdAndClubType(String userId, int pageNo,
			int pageSize, String clubType) {
		Set<String> userIds = employeeRepository.getEmployeeDetailsByReportingManagerId(userId).stream()
				.map(EmployeeDetails::getUserId).collect(Collectors.toSet());
		userIds.add(userId);

		List<String> userIdss = new ArrayList<>(userIds);

		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("creationDate").descending());
		System.out.println("clubType###########" + clubType);
		Page<Investor> investorPage = investorRepo.getTeamInvestorsListByUserIdsAndClubPaginated(userIdss, clubType, paging);

		Set<InvestorViewMapper> mapperSet = new HashSet<>();

		if (investorPage != null && !investorPage.isEmpty()) {
			mapperSet = investorPage.getContent().stream().map(li -> {
				InvestorViewMapper mapper = getInvestorDetailsById(li.getInvestorId());
				mapper.setPageCount(investorPage.getTotalPages());
				mapper.setDataCount(investorPage.getSize());
				mapper.setListCount(investorPage.getTotalElements());
				return mapper;
			}).collect(Collectors.toSet());
		}
		return mapperSet;
	}

	@Override
	public List<InvestorViewMapper> getFilterInvestorsPageWiseByUserIdAndClubType(String userId, int pageNo,
			int pageSize, String clubType) {
		List<InvestorViewMapper> investorViewMappers = new ArrayList<>();
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("creationDate").descending());
		Page<Investor> investors = investorRepo.getInvestorsListByUserIdAndClub(userId, clubType, paging);
		System.out.println("###########" + investors);
		if (null != investors && !investors.isEmpty()) {
			investorViewMappers = investors.stream().map(investor -> {
				InvestorViewMapper mapper = null;
				if (null != investor) {
					mapper = getInvestorDetailsById(investor.getInvestorId());
					mapper.setPageCount(investors.getTotalPages());
					mapper.setDataCount(investors.getSize());
					mapper.setListCount(investors.getTotalElements());
				}
				return mapper;
			}).collect(Collectors.toList());
		}
		return investorViewMappers;
	}

}
