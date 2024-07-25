package com.app.employeePortal.customer.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
import com.app.employeePortal.address.service.AddressService;
import com.app.employeePortal.call.entity.CallDetails;
import com.app.employeePortal.call.entity.CustomerCallLink;
import com.app.employeePortal.call.entity.EmployeeCallLink;
import com.app.employeePortal.call.repository.CallDetailsRepository;
import com.app.employeePortal.call.repository.CustomerCallLinkRepo;
import com.app.employeePortal.call.repository.EmployeeCallRepository;
import com.app.employeePortal.candidate.entity.DefinationDetails;
import com.app.employeePortal.candidate.entity.DefinationInfo;
import com.app.employeePortal.candidate.repository.CandidateDetailsRepository;
import com.app.employeePortal.candidate.repository.DefinationInfoRepository;
import com.app.employeePortal.candidate.repository.DefinationRepository;
import com.app.employeePortal.candidate.service.CandidateService;
import com.app.employeePortal.category.entity.CurrencyConversion;
import com.app.employeePortal.category.entity.CustomerType;
import com.app.employeePortal.category.entity.LobDetails;
import com.app.employeePortal.category.repository.CurrencyConversionRepository;
import com.app.employeePortal.category.repository.CustomerTypeRepository;
import com.app.employeePortal.category.repository.LobDetailsRepository;
import com.app.employeePortal.config.AesEncryptor;
import com.app.employeePortal.contact.entity.ContactDetails;
import com.app.employeePortal.contact.mapper.ContactMapper;
import com.app.employeePortal.contact.mapper.ContactReportMapper;
import com.app.employeePortal.contact.mapper.ContactViewMapper;
import com.app.employeePortal.contact.mapper.ContactViewMapperForDropDown;
import com.app.employeePortal.contact.repository.ContactRepository;
import com.app.employeePortal.contact.service.ContactService;
import com.app.employeePortal.customer.entity.Campaign;
import com.app.employeePortal.customer.entity.CuOppConversionValue;
import com.app.employeePortal.customer.entity.Customer;
import com.app.employeePortal.customer.entity.CustomerAddressLink;
import com.app.employeePortal.customer.entity.CustomerContactLink;
import com.app.employeePortal.customer.entity.CustomerDocumentLink;
import com.app.employeePortal.customer.entity.CustomerInfo;
import com.app.employeePortal.customer.entity.CustomerInitiativeLink;
import com.app.employeePortal.customer.entity.CustomerInvoice;
import com.app.employeePortal.customer.entity.CustomerInvoiceAddressLink;
import com.app.employeePortal.customer.entity.CustomerLobLink;
import com.app.employeePortal.customer.entity.CustomerNotesLink;
import com.app.employeePortal.customer.entity.CustomerRecruitUpdate;
import com.app.employeePortal.customer.entity.CustomerRecruitmentLink;
import com.app.employeePortal.customer.entity.CustomerSkillLink;
import com.app.employeePortal.customer.entity.InitiativeDetails;
import com.app.employeePortal.customer.mapper.CampaignReqMapper;
import com.app.employeePortal.customer.mapper.CampaignRespMapper;
import com.app.employeePortal.customer.mapper.CustomerAddressResponseMapper;
import com.app.employeePortal.customer.mapper.CustomerInvoiceMapper;
import com.app.employeePortal.customer.mapper.CustomerKeySkillMapper;
import com.app.employeePortal.customer.mapper.CustomerLobLinkMapper;
import com.app.employeePortal.customer.mapper.CustomerMapper;
import com.app.employeePortal.customer.mapper.CustomerRecruitmentMapper;
import com.app.employeePortal.customer.mapper.CustomerReportMapper;
import com.app.employeePortal.customer.mapper.CustomerResponseMapper;
import com.app.employeePortal.customer.mapper.CustomerSkillLinkMapper;
import com.app.employeePortal.customer.mapper.CustomerViewMapper;
import com.app.employeePortal.customer.mapper.CustomerViewMapperForDropDown;
import com.app.employeePortal.customer.mapper.CustomerViewMapperrForMap;
import com.app.employeePortal.customer.mapper.InitiativeDetailsMapper;
import com.app.employeePortal.customer.mapper.InitiativeSkillMapper;
import com.app.employeePortal.customer.mapper.TransferMapper;
import com.app.employeePortal.customer.repository.CampaignRepository;
import com.app.employeePortal.customer.repository.CuOppConversionValueRepo;
import com.app.employeePortal.customer.repository.CustomerAddressLinkRepository;
import com.app.employeePortal.customer.repository.CustomerContactLinkRepository;
import com.app.employeePortal.customer.repository.CustomerDocumentLinkRepository;
import com.app.employeePortal.customer.repository.CustomerInfoRepository;
import com.app.employeePortal.customer.repository.CustomerInitiativeLinkRepository;
import com.app.employeePortal.customer.repository.CustomerInvoiceAddressLinkRepository;
import com.app.employeePortal.customer.repository.CustomerInvoiceRepository;
import com.app.employeePortal.customer.repository.CustomerLobLinkRepository;
import com.app.employeePortal.customer.repository.CustomerNotesLinkRepository;
import com.app.employeePortal.customer.repository.CustomerRecruitUpdateRepository;
import com.app.employeePortal.customer.repository.CustomerRecruitmentLinkRepository;
import com.app.employeePortal.customer.repository.CustomerRepository;
import com.app.employeePortal.customer.repository.CustomerSkillLinkRepository;
import com.app.employeePortal.customer.repository.InitiativeDetailsRepository;
import com.app.employeePortal.document.entity.DocumentDetails;
import com.app.employeePortal.document.mapper.DocumentMapper;
import com.app.employeePortal.document.repository.DocumentDetailsRepository;
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
import com.app.employeePortal.event.entity.EventType;
import com.app.employeePortal.event.repository.CustomerEventRepo;
import com.app.employeePortal.event.repository.EmployeeEventRepository;
import com.app.employeePortal.event.repository.EventDetailsRepository;
import com.app.employeePortal.event.repository.EventTypeRepository;
import com.app.employeePortal.leads.mapper.ActivityMapper;
import com.app.employeePortal.notification.entity.Notificationparam;
import com.app.employeePortal.notification.repository.NotificationRepository;
import com.app.employeePortal.notification.service.NotificationService;
import com.app.employeePortal.opportunityWorkflow.entity.OpportunityStages;
import com.app.employeePortal.opportunityWorkflow.repository.OpportunityStagesRepository;
import com.app.employeePortal.organization.entity.OrganizationDetails;
import com.app.employeePortal.organization.repository.OrganizationRepository;
import com.app.employeePortal.partner.repository.PartnerDetailsRepository;
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
import com.app.employeePortal.sector.entity.SectorDetails;
import com.app.employeePortal.sector.repository.SectorDetailsRepository;
import com.app.employeePortal.source.entity.Source;
import com.app.employeePortal.source.repository.SourceRepository;
import com.app.employeePortal.task.entity.CustomerTaskLink;
import com.app.employeePortal.task.entity.EmployeeTaskLink;
import com.app.employeePortal.task.entity.TaskDetails;
import com.app.employeePortal.task.entity.TaskDocumentLink;
import com.app.employeePortal.task.repository.CustomerTaskRepo;
import com.app.employeePortal.task.repository.EmployeeTaskRepository;
import com.app.employeePortal.task.repository.TaskDetailsRepository;
import com.app.employeePortal.task.repository.TaskDocumentLinkRepository;
import com.app.employeePortal.util.Utility;

import freemarker.template.TemplateException;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CampaignRepository campaignRepository;

	@Autowired
	EventTypeRepository eventTypeRepository;
	@Autowired
	CustomerNotesLinkRepository customerNotesLinkRepository;
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	AddressInfoRepository addressInfoRepository;
	@Autowired
	AddressRepository addressRepository;
	@Autowired
	CustomerAddressLinkRepository customerAddressLinkRepository;
	@Autowired
	NotesRepository notesRepository;
	@Autowired
	CustomerContactLinkRepository customerContactLinkRepository;
	@Autowired
	ContactRepository contactRepository;

	@Autowired
	CandidateDetailsRepository candidateDetailsRepository;

	@Autowired
	OpportunityDetailsRepository opportunityDetailsRepository;

	@Autowired
	PartnerDetailsRepository partnerDetailsRepository;

	@Autowired
	SectorDetailsRepository sectorDetailsRepository;
	@Autowired
	ContactService contactService;
	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	NotificationRepository notificationRepository;

	@Autowired
	CustomerInfoRepository customerInfoRepository;

	@Autowired
	CustomerDocumentLinkRepository customerDocumentLinkRepository;

	@Autowired
	DocumentDetailsRepository documentDetailsRepository;
	@Autowired
	CustomerInvoiceRepository customerInvoiceRepository;
	@Autowired
	AddressService addressService;
	@Autowired
	OpportunityService opportunityService;

	@Autowired
	CustomerService customerService;

	@Autowired
	CustomerRecruitmentLinkRepository customerRecruitmentLinkRepository;
	@Autowired
	CustomerInvoiceAddressLinkRepository customerInvoiceAddressLinkRepository;

	@Autowired
	ThirdPartyRepository thirdPartyRepository;
	@Autowired
	PermissionRepository permissionRepository;
	@Autowired
	InitiativeDetailsRepository initiativeDetailsRepository;

	@Autowired
	CustomerInitiativeLinkRepository customerInitiativeLinkRepository;
	@Autowired
	DefinationRepository definationRepository;
	@Autowired
	RecruitmentOpportunityDetailsRepository recruitmentOpportunityDetailsRepository;
	@Autowired
	CandidateService candidateService;
	@Autowired
	CustomerRecruitUpdateRepository customerRecruitUpdateRepository;
	@Autowired
	CustomerSkillLinkRepository customerSkillLinkRepository;
	@Autowired
	DefinationInfoRepository definationInfoRepository;
	@Autowired
	CountryRepository countryRepository;
	@Autowired
	SourceRepository sourceRepository;
	@Autowired
	CustomerCallLinkRepo customerCallLinkRepo;
	@Autowired
	CustomerEventRepo customerEventRepo;
	@Autowired
	CustomerTaskRepo customerTaskRepo;
	@Autowired
	CallDetailsRepository callDetailsRepository;
	@Autowired
	EventDetailsRepository eventDetailsRepository;
	@Autowired
	TaskDetailsRepository taskDetailsRepository;
	@Autowired
	EmployeeService employeeService;
	@Autowired
	CustomerTypeRepository customerTypeRepository;
	@Autowired
	TeamRepository teamRepository;
	@Autowired
	TeamMemberLinkRepo teamMemberLinkRepo;
	@Autowired
	NotificationService notificationService;
	@Autowired
	OpportunityStagesRepository opportunityStagesRepository;
	@Autowired
	CurrencyRepository currencyRepository;
	@Autowired
	CurrencyConversionRepository currencyConversionRepository;
	@Autowired
	OrganizationRepository organizationRepository;
	@Autowired
	CuOppConversionValueRepo cuOppConversionValueRepo;
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

	private String[] customer_headings = { "Customer Id", "Name", "URL", "Sector", "Vat#", "Phone#", "Email", "Notes" };
	// private Object category;

	@Value("${companyName}")
	private String companyName;
	@Autowired
	CustomerLobLinkRepository customerLobLinkRepository;
	@Autowired
	LobDetailsRepository lobDetailsRepository;

	@Autowired
	AesEncryptor encryptor;

	@Override
	public CustomerResponseMapper saveCustomer(CustomerMapper customerMapper) throws IOException, TemplateException {

		CustomerResponseMapper resultMapper = null;
		String customerInfoId = null;

		CustomerInfo customerInfo = new CustomerInfo();

		customerInfo.setCreationDate(new Date());

		CustomerInfo customerr = customerInfoRepository.save(customerInfo);
		customerInfoId = customerr.getId();

		Customer customer = new Customer();

		setPropertyOnInput(customerMapper, customer, customerInfoId);

		customerRepository.save(customer);
//        /* insert to notification table */
//
//        NotificationDetails notification = new NotificationDetails();
//        notification.setAssignedTo(customerMapper.getUserId());
//        notification.setMessage(customerMapper.getName() + " Created as a Prospect.");
//        notification.setMessageReadInd(false);
//        notification.setNotificationDate(new Date());
//        notification.setNotificationType("Prospect Creation");
//        notification.setUser_id(customerMapper.getUserId());
//        notificationRepository.save(notification);

		resultMapper = getCustomerDetailsById(customerInfoId);
		return resultMapper;
	}

	private void setPropertyOnInput(CustomerMapper customerMapperr, Customer customer, String customerInfoId)
			throws IOException, TemplateException {

		customer.setCustomerId(customerInfoId);
		customer.setName(customerMapperr.getName());
		customer.setUrl(customerMapperr.getUrl());
		customer.setNotes(customerMapperr.getNotes());
		//customer.setCountryDialCode(customerMapperr.getCountryDialCode());
		
		if (Utility.isNumeric(customerMapperr.getCountryDialCode())) {

			String numberStr = new BigDecimal(customerMapperr.getCountryDialCode()).toPlainString();
			System.out.println("phoneNumber before split" + numberStr);
			int result = Integer.parseInt(numberStr.split("\\.")[0]);
			System.out.println("phoneNumber split" + result);
			String dialCode = Integer.toString(result);
			System.out.println("dialCode else" + dialCode);
			
			customer.setCountryDialCode(dialCode);

			System.out.println("  IFF   dialCode" + dialCode);
		} else {
			customer.setCountryDialCode(customerMapperr.getCountryDialCode());
			System.out.println("  ELSE    dialCode" + customerMapperr.getCountryDialCode());
		}
		
		
		if (Utility.isNumeric(customerMapperr.getPhoneNumber())) {

			String numberStr = new BigDecimal(customerMapperr.getPhoneNumber()).toPlainString();
			System.out.println("phoneNumber before split" + numberStr);
			int result = Integer.parseInt(numberStr.split("\\.")[0]);
			System.out.println("phoneNumber split" + result);
			String phoneNumber = Integer.toString(result);
			System.out.println("phoneNumber else" + phoneNumber);
			
			
			customer.setPhoneNumber(phoneNumber);

			System.out.println("  IFF   phoneNumber" + phoneNumber);
		} else {
			customer.setPhoneNumber(customerMapperr.getPhoneNumber());
			System.out.println("  ELSE    phoneNumber" + customerMapperr.getPhoneNumber());
		}
		
		//customer.setPhoneNumber(customerMapperr.getPhoneNumber());
		customer.setEmail(customerMapperr.getEmail());
		customer.setUserId(customerMapperr.getUserId());
		customer.setOrganizationId(customerMapperr.getOrganizationId());
		customer.setGroup(customerMapperr.getGroup());
		customer.setVatNo(customerMapperr.getVatNo());
		customer.setCreationDate(new Date());
		customer.setLiveInd(true);
		customer.setDocumentId(customerMapperr.getCustomerId());
		customer.setSector(customerMapperr.getSectorId());
		System.out.println("get sector in frontend............" + customerMapperr.getSectorId());
		customer.setCountry(customerMapperr.getCountry());
		customer.setZipcode(customerMapperr.getZipcode());
		customer.setCategory(customerMapperr.getCategory());
		customer.setImageURL(customerMapperr.getImageURL());
		customer.setAssignedTo(customerMapperr.getAssignedTo());
		customer.setBusinessRegistration(customerMapperr.getBusinessRegistration());
		customer.setGst(customerMapperr.getGst());
		customer.setSource(customerMapperr.getSource());
		customer.setType(customerMapperr.getType());
		customer.setSourceUserId(customerMapperr.getSourceUserID());
		customer.setPotentialValue(customerMapperr.getPotentialValue());
		customer.setCurrencyId(customerMapperr.getCurrencyId());
		customer.setConvertInd(0);
		customer.setAssignedBy(customerMapperr.getUserId());
		if (customerMapperr.getAddress().size() > 0) {
			for (AddressMapper addressMapper : customerMapperr.getAddress()) {
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
					addressDetails.setXlAddress(addressMapper.getXlAddress());
					addressRepository.save(addressDetails);

					CustomerAddressLink customerAddressLink = new CustomerAddressLink();
					customerAddressLink.setCustomerId(customerInfoId);
					customerAddressLink.setAddressId(addressId);
					customerAddressLink.setCreationDate(new Date());

					customerAddressLinkRepository.save(customerAddressLink);

				}
			}
		}

		/* insert to notes */
		String notesId = null;

		Notes notes = new Notes();
		notes.setNotes(customerMapperr.getNotes());
		notes.setCreation_date(new Date());
		notes.setUserId(customerMapperr.getUserId());
		notes.setLiveInd(true);
		Notes note = notesRepository.save(notes);
		notesId = note.getNotes_id();

		/* insert to customer-notes-link */

		CustomerNotesLink customerNotesLink = new CustomerNotesLink();
		customerNotesLink.setCustomerId(customerInfoId);
		customerNotesLink.setNotesId(notesId);
		customerNotesLink.setCreationDate(new Date());

		customerNotesLinkRepository.save(customerNotesLink);

		/* insert to Notification Table */
		Notificationparam param = new Notificationparam();
		EmployeeDetails emp = employeeRepository.getEmployeesByuserId(customerMapperr.getUserId());
		String name = employeeService.getEmployeeFullNameByObject(emp);
		param.setEmployeeDetails(emp);
		param.setAdminMsg("Prospect " + "'" + customerMapperr.getName() + "' created by " + name + " on " + new Date());
		param.setOwnMsg("Prospect " + customerMapperr.getName() + " created.");
		param.setNotificationType("Prospect Creation");
		param.setProcessNmae("Prospect");
		param.setType("create");
		param.setEmailSubject("Korero alert - Prospect created");
		param.setCompanyName(companyName);
		param.setUserId(customerMapperr.getUserId());

		if (!customerMapperr.getUserId().equals(customerMapperr.getAssignedTo())) {
			List<String> assignToUserIds = new ArrayList<>();
			assignToUserIds.add(customerMapperr.getAssignedTo());
			param.setAssignToUserIds(assignToUserIds);
			param.setAssignToMsg("Prospect " + "'" + customerMapperr.getName() + "'" + " assigned to "
					+ employeeService.getEmployeeFullName(customerMapperr.getAssignedTo()) + " by " + name + " on "
					+ new Date());
		}
		notificationService.createNotificationForDynamicUsers(param);
//        notificationService.createNotification(customerMapperr.getUserId(), "Prospect create", message, "Prospect", "create");
	}

	@Override
	public CustomerResponseMapper getCustomerDetailsById(String customerId) {

		Customer customer = customerRepository.getCustomerByIdAndLiveInd(customerId);
		System.out.println("customer object is..." + customer);

		CustomerResponseMapper customerMapper = new CustomerResponseMapper();

		if (null != customer) {

			if (customer.getSector() != null && customer.getSector().trim().length() > 0) {
				SectorDetails sector = sectorDetailsRepository.getSectorDetailsBySectorId(customer.getSector());
				System.out.println("get sectordetails by id returns........." + customer.getSector());
				System.out.println("sector object is......." + sector);
				if (sector != null) {
					customerMapper.setSector(sector.getSectorName());
					customerMapper.setSectorId(customer.getSector());
				} else {
					customerMapper.setSector("");
					customerMapper.setSectorId("");
				}
			}
			customerMapper.setCustomerId(customerId);
			customerMapper.setName(customer.getName());
			customerMapper.setUrl(customer.getUrl());
//			customerMapper.setNotes(customer.getNotes());
			customerMapper.setEmail(customer.getEmail());
//			customerMapper.setGroup(customer.getGroup());
			customerMapper.setVatNo(customer.getVatNo());

			if (!StringUtils.isEmpty(customer.getCountry())) {
				customerMapper.setCountry(customer.getCountry());
				Country country = countryRepository.getCountryDetailsByCountryNameAndOrgId(customer.getCountry(),
						customer.getOrganizationId());
				if (null != country) {
					customerMapper.setCountry_alpha3_code(country.getCountry_alpha3_code());
					customerMapper.setCountry_alpha2_code(country.getCountry_alpha2_code());
				}
			} else {
				List<CustomerAddressLink> customerAddressLinks = customerAddressLinkRepository
						.getAddressListByCustomerId(customer.getCustomerId());
				if (null != customerAddressLinks && !customerAddressLinks.isEmpty()) {
					for (CustomerAddressLink customerAddressLink : customerAddressLinks) {
						AddressDetails addressDetails = addressRepository
								.getAddressDetailsByAddressId(customerAddressLink.getAddressId());
						if (null != addressDetails) {
							if (!StringUtils.isEmpty(addressDetails.getCountry())) {
								customerMapper.setCountry(addressDetails.getCountry());
								Country country = countryRepository.getCountryDetailsByCountryNameAndOrgId(
										addressDetails.getCountry(), customer.getOrganizationId());
								if (null != country) {
									customerMapper.setCountry_alpha3_code(country.getCountry_alpha3_code());
									customerMapper.setCountry_alpha2_code(country.getCountry_alpha2_code());
								}
							}
						}
					}
				}
			}

			customerMapper.setPhoneNumber(customer.getPhoneNumber());
			customerMapper.setCountryDialCode(customer.getCountryDialCode());
			customerMapper.setUserId(customer.getUserId());
			customerMapper.setOrganizationId(customer.getOrganizationId());
			customerMapper.setCreationDate(Utility.getISOFromDate(customer.getCreationDate()));
			customerMapper.setImageURL(customer.getImageURL());
//			customerMapper.setGst(customer.getGst());
			customerMapper.setSourceUserID(customer.getSourceUserId());
			customerMapper.setSourceUserName(employeeService.getEmployeeFullName(customer.getSourceUserId()));
			customerMapper.setConvertInd(customer.getConvertInd());
			customerMapper.setPotentialValue(customer.getPotentialValue());
			customerMapper.setCurrencyId(customer.getCurrencyId());
			customerMapper.setAssignedBy(employeeService.getEmployeeFullName(customer.getAssignedBy()));
			if (null != customer.getCurrencyId() && !customer.getCurrencyId().isEmpty()) {
				Currency currency = currencyRepository.getByCurrencyId(customer.getCurrencyId());
				if (null != currency) {
					customerMapper.setCurrency(currency.getCurrencyName());
				}
			}

			List<OpportunityDetails> opportunityList = opportunityDetailsRepository
					.getOpportunityDetailsCustomerIdAndWonIndAndLiveInd(customerId);
			if (null != opportunityList && !opportunityList.isEmpty()) {
				customerMapper.setWonOppInd(true);
			}

			CustomerType customerType = customerTypeRepository.findByCustomerTypeId(customer.getType());
			if (null != customerType) {
				customerMapper.setType(customerType.getName());
			}

			Source source = sourceRepository.findBySourceId(customer.getSource());
			if (null != source) {
				customerMapper.setSource(source.getName());
			}

//			customerMapper.setCountry(customer.getCountry());
			// customerMapper.setSector(sector.getSectorName());
//			customerMapper.setZipcode(customer.getZipcode());

//			customerMapper.setDocumentId(customer.getDocumentId());
//			customerMapper.setCategory(customer.getCategory());
			customerMapper.setBusinessRegistration(customer.getBusinessRegistration());

			EmployeeDetails employeeDetails = employeeRepository.getEmployeeDetailsByEmployeeId(customer.getUserId());
			if (null != employeeDetails) {
				String middleName = " ";
				String lastName = "";

				if (!StringUtils.isEmpty(employeeDetails.getLastName())) {
					lastName = employeeDetails.getLastName();
				}
				if (employeeDetails.getMiddleName() != null && employeeDetails.getMiddleName().length() > 0) {

					middleName = employeeDetails.getMiddleName();
					customerMapper.setOwnerName(employeeDetails.getFirstName() + " " + middleName + " " + lastName);
					customerMapper.setOwnerImageId(employeeDetails.getImageId());
				} else {

					customerMapper.setOwnerName(employeeDetails.getFirstName() + " " + lastName);
					customerMapper.setOwnerImageId(employeeDetails.getImageId());
				}

			}
			EmployeeDetails employeeDetail = employeeRepository
					.getEmployeeDetailsByEmployeeId(customer.getAssignedTo());
			if (null != employeeDetail) {
				String middleName = " ";
				String lastName = "";

				if (!StringUtils.isEmpty(employeeDetail.getLastName())) {

					lastName = employeeDetail.getLastName();
				}

				if (employeeDetail.getMiddleName() != null && employeeDetail.getMiddleName().length() > 0) {

					middleName = employeeDetail.getMiddleName();
					customerMapper.setAssignedTo(employeeDetail.getFirstName() + " " + middleName + " " + lastName);

				} else {

					customerMapper.setAssignedTo(employeeDetail.getFirstName() + " " + lastName);

				}

			}

			CustomerRecruitUpdate dbCustomerRecruitUpdate = customerRecruitUpdateRepository
					.findByCustomerId(customer.getCustomerId());
			if (null != dbCustomerRecruitUpdate) {
				System.out.println("====" + dbCustomerRecruitUpdate.getId());
				customerMapper.setLastRequirementOn(Utility.getISOFromDate(dbCustomerRecruitUpdate.getUpdatedDate()));
			}

			System.out.println("customerId====" +customerId);
			List<OpportunityDetails> oppCustomersList = opportunityDetailsRepository
					.getOpportunityListByCustomerIdAndLiveInd(customerId);
			if (null != oppCustomersList && !oppCustomersList.isEmpty()) {
//				double totalValue = oppCustomersList.stream().mapToDouble(li -> {
//					double value = 0;
//					if (!StringUtils.isEmpty(li.getProposalAmount())) {
//						value = Double.parseDouble(li.getProposalAmount());
//					}
//					return value;
//				}).sum();
//				customerMapper.setTotalProposalValue(totalValue);
				customerMapper.setOppNo(oppCustomersList.size());
			}

			CuOppConversionValue cuOppConversionValue = cuOppConversionValueRepo.findByCustomerId(customerId);
			if (null != cuOppConversionValue) {
				if (customer.getUserId().equalsIgnoreCase(cuOppConversionValue.getUserId())) {
					customerMapper.setTotalProposalValue(cuOppConversionValue.getUserConversionValue());
					customerMapper.setUserCurrency(cuOppConversionValue.getUserConversionCurrency());
				} else {
					customerMapper.setTotalProposalValue(cuOppConversionValue.getOrgConversionValue());
					customerMapper.setUserCurrency(cuOppConversionValue.getOrgConversionCurrency());
				}
			}

			List<CustomerSkillLinkMapper> skillList = new ArrayList<CustomerSkillLinkMapper>();
			List<CustomerSkillLink> list = customerSkillLinkRepository.getByCustomerId(customerId);
			if (null != list && !list.isEmpty()) {
				for (CustomerSkillLink skillSetDetails : list) {
					CustomerSkillLink list2 = customerSkillLinkRepository
							.getById(skillSetDetails.getCustomerSkillLinkId());

					CustomerSkillLinkMapper mapper = new CustomerSkillLinkMapper();
					if (null != list2) {

						DefinationDetails definationDetails1 = definationRepository
								.findByDefinationId(list2.getSkillName());
						if (null != definationDetails1) {
							mapper.setSkillName(definationDetails1.getName());

						}

						mapper.setCustomerId(list2.getCustomerId());
						mapper.setCustomerSkillLinkId(list2.getCustomerSkillLinkId());
						skillList.add(mapper);
					}
				}
				customerMapper.setSkill(skillList);
			}

//			List<CustomerAddressResponseMapper> addressList = new ArrayList<CustomerAddressResponseMapper>();
//			List<CustomerAddressLink> customerAddressList = customerAddressLinkRepository
//					.getAddressListByCustomerId(customerId);
//			System.out.println("customer===" + customerId);
////		System.out.println("customerAddressLink.getAddressId()"+customerAddressList.get(0).getAddressId());
//			/* fetch customer address & set to customer mapper */
//			if (null != customerAddressList && !customerAddressList.isEmpty()) {
//
//				for (CustomerAddressLink customerAddressLink : customerAddressList) {
//					AddressDetails addressDetails = addressRepository
//							.getAddressDetailsByAddressId(customerAddressLink.getAddressId());
//					System.out.println("customerAddressLink.getAddressId()" + customerAddressLink.getAddressId());
//					CustomerAddressResponseMapper addressMapper = new CustomerAddressResponseMapper();
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
//								addressDetails.getCountry(), customer.getOrganizationId());
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
//			customerMapper.setAddress(addressList);
//		Country country1 = countryRepository.getCountryDetailsByCountryName(customerMapper.getAddress().get(0).getCountry());
//		if(null!=country1) {
////		customerMapper.setCountry_alpha2_code(country1.getCountry_alpha2_code());
////		customerMapper.setCountry_alpha3_code(country1.getCountry_alpha3_code());
//		}
		}
		return customerMapper;

	}

	@Override
	public List<CustomerResponseMapper> getCustomerDetailsPageWiseByuserId(String userId, int pageNo, int pageSize) {
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("creationDate").descending());
		List<CustomerResponseMapper> result = new ArrayList<>();
		Page<Customer> customerList = customerRepository.getCustomerDetailsListByUserId(userId, paging);
		System.out.println("###########" + customerList);
		if (null != customerList && !customerList.isEmpty()) {
			result = customerList.stream().map(customerDetails -> {
				CustomerResponseMapper customerDetailsMapper = getCustomerDetailsById(customerDetails.getCustomerId());
				if (null != customerDetailsMapper.getCustomerId()) {
					customerDetailsMapper.setPageCount(customerList.getTotalPages());
					customerDetailsMapper.setDataCount(customerList.getSize());
					customerDetailsMapper.setListCount(customerList.getTotalElements());
					return customerDetailsMapper;
				}
				return null;
			}).collect(Collectors.toList());
		}
		return result;
	}

	@Override
	public List<CustomerResponseMapper> getCustomerDetailsPageWiseByuserIds(List<String> userIds, int pageNo,
			int pageSize) {
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("creationDate").descending());
		List<CustomerResponseMapper> result = new ArrayList<>();
		Page<Customer> customerList = customerRepository.getTeamCustomerDetailsListByUserId(userIds, paging);
		System.out.println("###########" + customerList);
		if (null != customerList && !customerList.isEmpty()) {
			result = customerList.stream().map(customerDetails -> {
				CustomerResponseMapper customerDetailsMapper = getCustomerDetailsById(customerDetails.getCustomerId());
				if (null != customerDetailsMapper.getCustomerId()) {
					customerDetailsMapper.setPageCount(customerList.getTotalPages());
					customerDetailsMapper.setDataCount(customerList.getSize());
					customerDetailsMapper.setListCount(customerList.getTotalElements());
					return customerDetailsMapper;
				}
				return null;
			}).collect(Collectors.toList());
		}
		return result;
	}

	@Override
	public List<NotesMapper> getNoteListByCustomerId(String customerId) {
		List<NotesMapper> list = new ArrayList<>();
		List<CustomerNotesLink> customerNotesLinkList = customerNotesLinkRepository.getNotesIdByCustomerId(customerId);
		if (customerNotesLinkList != null && !customerNotesLinkList.isEmpty()) {
			return customerNotesLinkList.stream().map(customerNotesLink -> {
				NotesMapper notesMapper = getNotes(customerNotesLink.getNotesId());
				if (null != notesMapper.getNotesId()) {
					list.add(notesMapper);
				}
				return notesMapper;
			}).collect(Collectors.toList());
		}
		return list;
	}

	@Override
	public NotesMapper getNotes(String id) {
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

	@Override
	public List<ContactViewMapper> getContactListByCustomerId(String customerId) {
		List<ContactViewMapper> resultList = new ArrayList<>();
		List<CustomerContactLink> customerContactLinkList = customerContactLinkRepository
				.getContactIdByCustomerId(customerId);
		if (customerContactLinkList != null && !customerContactLinkList.isEmpty()) {
			return customerContactLinkList.stream().map(customerContactLink -> {
				ContactViewMapper contactMapper = contactService
						.getContactDetailsById(customerContactLink.getContactId());
				if (null != contactMapper) {
					resultList.add(contactMapper);
					ThirdParty thirdParty = thirdPartyRepository.findByOrgId(contactMapper.getOrganizationId());
					if (thirdParty != null) {
						contactMapper.setThirdPartyAccessInd(thirdParty.isCustomerContactInd());
					}
				}
				return contactMapper;
			}).collect(Collectors.toList());

		}

		return resultList;
	}

	@Override
	public CustomerResponseMapper updateCustomer(String customerId, CustomerMapper customerMapper)
			throws IOException, TemplateException {
		CustomerResponseMapper resultMapper = null;

		Customer customer = customerRepository.getCustomerByIdAndLiveInd(customerId);

		if (null != customer) {

			customer.setLiveInd(false);
			customerRepository.save(customer);

			Customer newCustomer = new Customer();

			newCustomer.setCustomerId(customerId);

			if (null != customerMapper.getName()) {
				newCustomer.setName(customerMapper.getName());
			} else {
				newCustomer.setName(customer.getName());
			}

			if (null != customerMapper.getImageURL()) {
				newCustomer.setImageURL(customerMapper.getImageURL());
			} else {
				newCustomer.setImageURL(customer.getImageURL());
			}

			if (null != customerMapper.getUrl()) {
				newCustomer.setUrl(customerMapper.getUrl());
			} else {
				newCustomer.setUrl(customer.getUrl());
			}

			if (null != customerMapper.getNotes()) {
				List<CustomerNotesLink> list = customerNotesLinkRepository.getNotesIdByCustomerId(customerId);
				if (null != list && !list.isEmpty()) {
					list.sort((m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
					Notes notes = notesRepository.findByNoteId(list.get(0).getNotesId());
					if (null != notes) {
						notes.setNotes(customerMapper.getNotes());
						notesRepository.save(notes);
					}
				}
			}

			if (null != customerMapper.getPhoneNumber()) {

				newCustomer.setPhoneNumber(customerMapper.getPhoneNumber());
			} else {
				newCustomer.setPhoneNumber(customer.getPhoneNumber());

			}

			if (null != customerMapper.getCountryDialCode()) {

				newCustomer.setCountryDialCode(customerMapper.getCountryDialCode());
			} else {
				newCustomer.setCountryDialCode(customer.getCountryDialCode());

			}

			if (null != customerMapper.getCountry()) {

				newCustomer.setCountry(customerMapper.getCountry());
			} else {
				newCustomer.setCountry(customer.getCountry());

			}

			if (null != customerMapper.getSectorId()) {

				newCustomer.setSector(customerMapper.getSectorId());
			} else {
				newCustomer.setSector(customer.getSector());

			}

			if (null != customerMapper.getZipcode()) {

				newCustomer.setZipcode(customerMapper.getZipcode());
			} else {
				newCustomer.setZipcode(customer.getZipcode());

			}

			if (null != customerMapper.getEmail()) {

				newCustomer.setEmail(customerMapper.getEmail());
			} else {
				newCustomer.setEmail(customer.getEmail());

			}
			if (null != customerMapper.getVatNo()) {

				newCustomer.setVatNo(customerMapper.getVatNo());
			} else {
				newCustomer.setVatNo(customer.getVatNo());

			}
			newCustomer.setUserId(customer.getUserId());
			newCustomer.setOrganizationId(customer.getOrganizationId());

			if (null != customerMapper.getDocumentId()) {

				newCustomer.setDocumentId(customerMapper.getDocumentId());
			} else {
				newCustomer.setDocumentId(customer.getDocumentId());

			}

			if (null != customerMapper.getCategory()) {

				newCustomer.setCategory(customerMapper.getCategory());
			} else {
				newCustomer.setCategory(customer.getCategory());

			}
			if (null != customerMapper.getAssignedTo()) {

				newCustomer.setAssignedTo(customerMapper.getAssignedTo());
			} else {
				newCustomer.setAssignedTo(customer.getAssignedTo());

			}
			if (null != customerMapper.getBusinessRegistration()) {

				newCustomer.setBusinessRegistration(customerMapper.getBusinessRegistration());
			} else {
				newCustomer.setBusinessRegistration(customer.getBusinessRegistration());

			}

			if (null != customerMapper.getSource()) {
				newCustomer.setSource(customerMapper.getSource());
			} else {
				newCustomer.setSource(customer.getSource());
			}

			if (null != customerMapper.getType()) {
				newCustomer.setType(customerMapper.getType());
			} else {
				newCustomer.setType(customer.getType());
			}
			// customerMapper.setCreationDate(Utility.getISOFromDate(customer.getCreation_date()))

			if (0 != customerMapper.getPotentialValue()) {
				newCustomer.setPotentialValue(customerMapper.getPotentialValue());
			} else {
				newCustomer.setPotentialValue(customer.getPotentialValue());
			}

			if (null != customerMapper.getCurrencyId() && !customerMapper.getCurrencyId().isEmpty()) {
				newCustomer.setCurrencyId(customerMapper.getCurrencyId());
			} else {
				newCustomer.setCurrencyId(customer.getCurrencyId());
			}

			if (null != customerMapper.getAssignedTo()
					&& !customer.getAssignedBy().equals(customerMapper.getAssignedTo())) {
				newCustomer.setAssignedBy(customerMapper.getUserId());
			} else {
				newCustomer.setAssignedBy(customer.getAssignedBy());
			}

			newCustomer.setCreationDate(new Date());
			newCustomer.setLiveInd(true);

			Customer updatedCustomer = customerRepository.save(newCustomer);

			if (null != customerMapper.getAddress()) {
				List<AddressMapper> addressList = customerMapper.getAddress();

				for (AddressMapper addressMapper : addressList) {

					CustomerAddressLink customerAddressLink = customerAddressLinkRepository
							.findByCustomerId(customerId);
					String addId = customerAddressLink.getAddressId();
					if (null != addId) {

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
						AddressDetails addressDetails1 = addressRepository.save(newAddressDetails);
						String newAddressDetailsId = addressDetails1.getAddressId();
					}

				}
			}

			/* insert to Notification Table */
			Notificationparam param = new Notificationparam();
			EmployeeDetails emp = employeeRepository.getEmployeesByuserId(customerMapper.getUserId());
			String name = employeeService.getEmployeeFullNameByObject(emp);
			param.setEmployeeDetails(emp);
			param.setAdminMsg("Prospect " + "'" + customerMapper.getName() + "' updated by " + name);
			param.setOwnMsg("Prospect " + customerMapper.getName() + " updated.");
			param.setNotificationType("Prospect update");
			param.setProcessNmae("Prospect");
			param.setType("update");
			param.setEmailSubject("Korero alert- Prospect updated");
			param.setCompanyName(companyName);
			param.setUserId(customerMapper.getUserId());

			if (customerMapper.getUserId().equals(customer.getAssignedTo())) {
				List<String> assignToUserIds = new ArrayList<>();
				assignToUserIds.add(customerMapper.getAssignedTo());
				param.setAssignToUserIds(assignToUserIds);
				param.setAssignToMsg("Prospect " + "'" + customerMapper.getName() + "' updated by " + name);
			} else {
				List<String> assignToUserIds = new ArrayList<>();
				assignToUserIds.add(customerMapper.getAssignedTo());
				param.setAssignToUserIds(assignToUserIds);
				param.setAssignToMsg("Prospect " + "'" + customerMapper.getName() + "'" + " assigned to "
						+ employeeService.getEmployeeFullName(customerMapper.getAssignedTo()) + " by " + name);
			}
			notificationService.createNotificationForDynamicUsers(param);

			resultMapper = getCustomerDetailsById(updatedCustomer.getCustomerId());
		}
		return resultMapper;
	}

	@Override
	public String saveCustomerNotes(NotesMapper notesMapper) {
		String customerNotesId = null;
		if (null != notesMapper) {
			Notes notes = new Notes();
			notes.setNotes(notesMapper.getNotes());
			notes.setCreation_date(new Date());
			notes.setUserId(notesMapper.getEmployeeId());
			notes.setLiveInd(true);
			Notes note = notesRepository.save(notes);
			customerNotesId = note.getNotes_id();

			/* insert to customer-notes-link */

			CustomerNotesLink customerNotesLink = new CustomerNotesLink();
			customerNotesLink.setCustomerId(notesMapper.getCustomerId());
			customerNotesLink.setNotesId(customerNotesId);
			customerNotesLink.setCreationDate(new Date());

			customerNotesLinkRepository.save(customerNotesLink);

		}
		return customerNotesId;
	}

	public CustomerMapper getResultMapper(Customer customer) {

		CustomerMapper resultMapper = new CustomerMapper();
		resultMapper.setCustomerId(customer.getCustomerId());
		resultMapper.setName(customer.getName());
		resultMapper.setUrl(customer.getUrl());
		resultMapper.setNotes(customer.getNotes());
		resultMapper.setEmail(customer.getEmail());
		resultMapper.setPhoneNumber(customer.getPhoneNumber());
		resultMapper.setUserId(customer.getUserId());
		resultMapper.setOrganizationId(customer.getOrganizationId());

		return resultMapper;

	}

	@Override
	public ContactViewMapper saveCustomerContact(ContactMapper contactMapper) throws IOException, TemplateException {

		ContactViewMapper resultMapper = contactService.saveContact(contactMapper);

		ThirdParty pem1 = thirdPartyRepository.findByOrgId(resultMapper.getOrganizationId());
		if (null != pem1) {
			resultMapper.setThirdPartyAccessInd(pem1.isCustomerContactInd());
		}

		return resultMapper;
	}

	@Override
	public List<DocumentMapper> getDocumentListByCustomerId(String customerId) {
		List<DocumentMapper> resultMapper = new ArrayList<>();
		List<CustomerDocumentLink> customerDocumentLinkList = customerDocumentLinkRepository
				.getDocumentByCustomerId(customerId);
		Set<String> documentIds = customerDocumentLinkList.stream().map(CustomerDocumentLink::getDocumentId)
				.collect(Collectors.toSet());
		if (documentIds != null && !documentIds.isEmpty()) {
			documentIds.stream().map(documentId -> {
				DocumentMapper documentMapper = documentService.getDocument(documentId);
				if (null != documentMapper.getDocumentId()) {
					documentMapper.setContractInd(customerDocumentLinkList.get(0).isContractInd());
					resultMapper.add(documentMapper);
				}
				return documentMapper;
			}).collect(Collectors.toList());

		}

		return resultMapper;
	}

	@Override
	public OpportunityViewMapper saveCustomerOpportunity(OpportunityMapper opportunityMapper) {
		OpportunityViewMapper id = opportunityService.saveOpportunity(opportunityMapper);

		return id;
	}

	@Override
	public List<OpportunityViewMapper> getOpportunityListByCustomerId(String customerId) {
		List<OpportunityViewMapper> list = opportunityService.getOpportunityListByCustomerId(customerId);
		return list;
	}

	@Override
	public List<CustomerResponseMapper> getCustomerListByName(String name) {

		List<Customer> detailsList = customerRepository.findByName(name);
		List<CustomerResponseMapper> mapperList = new ArrayList<CustomerResponseMapper>();
		if (null != detailsList && !detailsList.isEmpty()) {

			mapperList = detailsList.stream().map(li -> getCustomerDetailsById(li.getCustomerId()))
					.collect(Collectors.toList());

			/*
			 * detailsList.forEach(li->{
			 *
			 * AccountMapper accountMapper; try { accountMapper =
			 * getAccountRelatedDetails(li); mapperList.add(accountMapper);
			 *
			 * } catch (Exception e) { // TODO Auto-generated catch block
			 * e.printStackTrace(); }
			 *
			 *
			 * });
			 */

			// for (AccountDetails accountDetails : detailsList) {
		}

		return mapperList;

	}

	@Override
	public ByteArrayInputStream exportEmployeeListToExcel(List<CustomerResponseMapper> customerList) {
		XSSFWorkbook workbook = new XSSFWorkbook();

		// Create a blank sheet
		XSSFSheet sheet = workbook.createSheet("customer");

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
		for (int i = 0; i < customer_headings.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(customer_headings[i]);
			cell.setCellStyle(headerCellStyle);
		}

		int rowNum = 1;
		if (null != customerList && !customerList.isEmpty()) {
			for (CustomerResponseMapper customer : customerList) {
				Row row = sheet.createRow(rowNum++);

				row.createCell(0).setCellValue(customer.getCustomerId());
				row.createCell(1).setCellValue(customer.getName());
				row.createCell(2).setCellValue(customer.getUrl());
				row.createCell(3).setCellValue(customer.getSector());
				row.createCell(4).setCellValue(customer.getVatNo());
				row.createCell(5).setCellValue(customer.getPhoneNumber());

				row.createCell(6).setCellValue(customer.getEmail());
//				row.createCell(7).setCellValue(customer.getNotes());

			}
		}
		// Resize all columns to fit the content size
		for (int i = 0; i < customer_headings.length; i++) {
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
	public List<CustomerResponseMapper> getAllCustomerList(int pageNo, int pageSize, String orgId) {

		List<CustomerResponseMapper> result = new ArrayList<>();
		Pageable paging = null;
		paging = PageRequest.of(pageNo, pageSize, Sort.by("creationDate").descending());

		Page<Customer> customerList = customerRepository.getCustomerDetailsListByOrgId(orgId, paging);
		System.out.println("###########" + customerList.getContent().size());
		if (null != customerList && !customerList.isEmpty()) {
			result = customerList.stream().map(customerDetails -> {
				CustomerResponseMapper customerDetailsMapper = getCustomerDetailsById(customerDetails.getCustomerId());
				if (null != customerDetailsMapper.getCustomerId()) {
					customerDetailsMapper.setPageCount(customerList.getTotalPages());
					customerDetailsMapper.setDataCount(customerList.getSize());
					customerDetailsMapper.setListCount(customerList.getTotalElements());
					return customerDetailsMapper;
				}
				return null;
			}).collect(Collectors.toList());
		}
		return result;

	}

	@Override
	public HashMap getCountListByuserId(String userId) {
		List<Customer> customerList = customerRepository.findByUserIdWithAssignedToandLiveInd(userId);
		HashMap map = new HashMap();
		map.put("customer", customerList.size());

		return map;
	}

	@Override
	public HashMap getCustomerListCountByOrgId(String orgId) {
		List<Customer> customerList = customerRepository.findByOrgIdandLiveInd(orgId);
		HashMap map = new HashMap();
		map.put("customer", customerList.size());

		return map;
	}

	@Override
	public void deleteDocumentsById(String documentId) {
		if (null != documentId) {
			DocumentDetails document = documentDetailsRepository.getDocumentDetailsById(documentId);

			document.setLive_ind(false);
			documentDetailsRepository.save(document);
		}

	}

	/*
	 * @Override public Object getAllCountList() { List<ContactDetails> contactList
	 * = contactRepository.findByliveInd(true);
	 *
	 * List<Customer> customerList = customerRepository.findByliveInd(true);
	 *
	 * List<CandidateDetails> candidateList =
	 * candidateDetailsRepository.findByliveInd(true);
	 *
	 * List<OpportunityDetails>
	 * opportunityList=opportunityDetailsRepository.findByliveInd(true);
	 *
	 * List<PartnerDetails>
	 * partnerList=partnerDetailsRepository.findByliveInd(true);
	 *
	 * //List<Product> productList=productRepository.findByActive(true);
	 *
	 * //List<Shipper> shipperList = shipperRepository.findByActive(true);
	 *
	 * //List<Notification> notificationList =
	 * notificationRepository.findByActiveAndMessageReadInd(true,false);
	 *
	 * HashMap map = new HashMap(); map.put("contact", contactList.size());
	 * map.put("customer", customerList.size());
	 * map.put("candidate",candidateList.size());
	 * map.put("oppotunity",opportunityList.size());
	 * map.put("partner",partnerList.size());
	 * //map.put("product",productList.size()); //map.put("shipper",
	 * shipperList.size()); //map.put("notification", notificationList.size());
	 * return map;
	 *
	 * }
	 */

	@Override
	public OpportunityViewMapper updateCandidateEducationalDetails(OpportunityViewMapper opportunityViewMapper) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OpportunityViewMapper updateCustomerOpportunity(String opportunityId,
			OpportunityMapper opportunityViewMapper) {
		OpportunityViewMapper resultMapper = opportunityService.updateOpportunityDetails(opportunityId,
				opportunityViewMapper);

//		OpportunityDetails opportunity = opportunityDetailsRepository
//				.getOpportunityDetailsByOpportunityId(opportunityId);
//		if (null != opportunity) {
//
//			opportunity.setLiveInd(false);
//			opportunityDetailsRepository.save(opportunity);
//
//			OpportunityDetails newOpportunity = new OpportunityDetails();
//
//			newOpportunity.setOpportunityId(opportunityId);
//
//			newOpportunity.setOpportunityName(opportunityViewMapper.getOpportunityName());
//			newOpportunity.setProposalAmount(opportunityViewMapper.getProposalAmount());
//			newOpportunity.setCustomerId(opportunityViewMapper.getCustomerId());
//			newOpportunity.setCustomerName(opportunityViewMapper.getCustomerId());
//			newOpportunity.setUserId(opportunityViewMapper.getUserId());
//			newOpportunity.setContactId(opportunityViewMapper.getContactId());
//			newOpportunity.setOrgId(opportunityViewMapper.getOrgId());
//			newOpportunity.setDescription(opportunityViewMapper.getDescription());
//			try {
//				newOpportunity.setStartDate(
//						Utility.removeTime(Utility.getDateFromISOString(opportunityViewMapper.getStartDate())));
//				newOpportunity.setEndDate(
//						Utility.removeTime(Utility.getDateFromISOString(opportunityViewMapper.getEndDate())));
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//			newOpportunity.setCurrency(opportunityViewMapper.getCurrency());
//			newOpportunity.setCreationDate(new Date());
//			newOpportunity.setLiveInd(true);
//			opportunityDetailsRepository.save(newOpportunity);
//
//			resultMapper = getOpportunityById(opportunityId);
//		}
		return resultMapper;
	}

	public OpportunityViewMapper getOpportunityById(String opportunityId) {
		OpportunityViewMapper OpportunityViewMapper = new OpportunityViewMapper();
		OpportunityDetails opportunity = opportunityDetailsRepository
				.getOpportunityDetailsByOpportunityId(opportunityId);

		if (null != opportunity) {

			OpportunityViewMapper = opportunityService.getOpportunityRelatedDetails(opportunity);
//			OpportunityViewMapper.setOpportunityName(opportunity.getOpportunityName());
//			OpportunityViewMapper.setProposalAmount(opportunity.getProposalAmount());
//			OpportunityViewMapper.setStartDate(Utility.getISOFromDate(opportunity.getStartDate()));
//			OpportunityViewMapper.setEndDate(Utility.getISOFromDate(opportunity.getEndDate()));

		}

		return OpportunityViewMapper;

	}

	@Override
	public String saverequirementCustomer(CustomerRecruitmentMapper customerRecruitmentMapper) {
		String id = null;

		CustomerRecruitmentLink recruitmentCustomerDetails = new CustomerRecruitmentLink();

		recruitmentCustomerDetails.setCustomerId(customerRecruitmentMapper.getCustomerId());
		// recruitmentCustomerDetails.setRecruitmentProcessId(customerRecruitmentMapper.getRecruitmentProcessId());
		// recruitmentCustomerDetails.setRecruitment_stage_id(customerRecruitmentMapper.getStageId());
		recruitmentCustomerDetails.setCreationDate(new Date());
		recruitmentCustomerDetails.setName(customerRecruitmentMapper.getRequirementName());
		recruitmentCustomerDetails.setRecruitmentId(customerRecruitmentMapper.getRecruitmentId());
		recruitmentCustomerDetails.setName(customerRecruitmentMapper.getName());
		recruitmentCustomerDetails.setRecruiterId(customerRecruitmentMapper.getRecruiterId());
		// recruitmentOpportunityDetails.setCandidateid(customerRecruitmentMapper.getCandidateId());

		recruitmentCustomerDetails.setLiveInd(true);

		id = customerRecruitmentLinkRepository.save(recruitmentCustomerDetails).getId();

		return id;
	}

	@Override
	public List<CustomerRecruitmentMapper> getCustomerRecriutmentListByCustomerId(String customerId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CustomerRecruitmentMapper updateRequirementCustomer(CustomerRecruitmentMapper customerRecruitmentMapper) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CustomerResponseMapper> getCustomerListByCategory(String category, String userId) {

		List<CustomerResponseMapper> resultList = new ArrayList<CustomerResponseMapper>();
		List<Customer> customerList = customerRepository.findByCategoryAndUserIdAndLiveInd(category, userId, true);

		System.out.println("customer list..........." + customerList);

		resultList = customerList.stream().map(li -> getCustomerDetailsById(li.getCustomerId()))
				.collect(Collectors.toList());
		/*
		 * if (null != customerList && !customerList.isEmpty()) { for (Customer customer
		 * : customerList) {
		 *
		 * CustomerViewMapper customerMapper = new CustomerViewMapper();
		 * customerMapper.setCategory(customer.getCategory());
		 *
		 * resultList.add(customerMapper);
		 *
		 * }
		 *
		 * }
		 */
		return resultList;
	}

	@Override
	public HashMap getCountListByCategory(String category, String userId) {
		List<Customer> customerList = customerRepository.findByCategoryAndUserIdAndLiveInd(category, userId, true);
		HashMap map = new HashMap();
		map.put("customer", customerList.size());

		return map;
	}

	@Override
	public HashMap getCountNoOfCustomerByUserId(String userId) {
		// int noOfPartner = 0;
		List<ContactDetails> contactList = contactRepository.findByUserIdAndContactType(userId, "Customer");
		HashMap map = new HashMap();
		map.put("customerDetails", contactList.size());
		return map;
	}

	@Override
	public String saveinvoiceCustomer(CustomerInvoiceMapper customerInvoiceMapper) {
		String customerInvoiceId = null;

		CustomerInvoice invoiceCustomerDetails = new CustomerInvoice();

		invoiceCustomerDetails.setId(customerInvoiceMapper.getCustomerInvoiceId());
		invoiceCustomerDetails.setCreationDate(new Date());
		invoiceCustomerDetails.setInvoiceAmount(customerInvoiceMapper.getInvoiceAmount());
		invoiceCustomerDetails.setInvoiceNumber(customerInvoiceMapper.getInvoiceNumber());
		invoiceCustomerDetails.setCurrency(customerInvoiceMapper.getCurrency());
		invoiceCustomerDetails.setStatus(customerInvoiceMapper.getStatus());
		invoiceCustomerDetails.setDocumentId(customerInvoiceMapper.getDocumentId());

		// invoiceCustomerDetails.setLiveInd(true);

		customerInvoiceId = customerInvoiceRepository.save(invoiceCustomerDetails).getId();

		if (customerInvoiceMapper.getAddress().size() > 0) {
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

					CustomerInvoiceAddressLink customerInvoiceAddressLink = new CustomerInvoiceAddressLink();
					customerInvoiceAddressLink.setCustomerInvoiceId(customerInvoiceId);
					customerInvoiceAddressLink.setAddressId(addressId);
					customerInvoiceAddressLink.setCreationDate(new Date());

					customerInvoiceAddressLinkRepository.save(customerInvoiceAddressLink);

				}
			}
		}
		return customerInvoiceId;
	}

	@Override
	public List<CustomerInvoiceMapper> getInvoiceListByCustomerId(String customerId) {
		List<CustomerInvoiceMapper> mapperList = new ArrayList<>();

		List<CustomerInvoiceAddressLink> customerInvoiceAddressLinkList = customerInvoiceAddressLinkRepository
				.findByCustomerId(customerId);
		List<AddressMapper> addressList = new ArrayList<AddressMapper>();
		List<CustomerInvoice> list = customerInvoiceRepository.findByCustomerId(customerId);
		if (null != list && !list.isEmpty()) {

			list.stream().map(li -> {
				CustomerInvoiceMapper customerInvoiceMapper = new CustomerInvoiceMapper();

				customerInvoiceMapper.setCustomerInvoiceId(li.getId());
				customerInvoiceMapper.setCreationDate(Utility.getISOFromDate(li.getCreationDate()));
				customerInvoiceMapper.setInvoiceAmount(li.getInvoiceAmount());
				customerInvoiceMapper.setInvoiceNumber(li.getInvoiceNumber());
				customerInvoiceMapper.setCurrency(li.getCurrency());
				customerInvoiceMapper.setStatus(li.getStatus());
				customerInvoiceMapper.setDocumentId(li.getDocumentId());

				if (null != customerInvoiceAddressLinkList && !customerInvoiceAddressLinkList.isEmpty()) {

					for (CustomerInvoiceAddressLink customerInvoiceAddressLink : customerInvoiceAddressLinkList) {
						AddressDetails addressDetails = addressRepository
								.getAddressDetailsByAddressId(customerInvoiceAddressLink.getAddressId());

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
					customerInvoiceMapper.setAddress(addressList);

				}
				mapperList.add(customerInvoiceMapper);
				return mapperList;
			}).collect(Collectors.toList());
		}
		return mapperList;
	}

	@Override
	public List<String> updateTransferOneUserToAnother(String userId, TransferMapper transferMapper) {

		List<String> custoList = transferMapper.getCustomerIds();
		System.out.println("custoList::::::::::::::::::::::::::::::::::::::::::::::::::::" + custoList);
		if (null != custoList && !custoList.isEmpty()) {
			for (String customerId : custoList) {
				System.out.println("the customer id is : " + customerId);
				Customer customer = customerRepository.getcustomerDetailsById(customerId);
				System.out.println("customer::::::::::::::::::::::::::::::::::::::::::::::::::::" + customer);
				customer.setUserId(userId);
				;
				customerRepository.save(customer);
			}

		}
		return custoList;
	}

	@Override
	public List<CustomerResponseMapper> getCustomerDetailsByName(String name) {
		List<Customer> list = customerRepository.findByLiveInd(true);
		List<Customer> filterList = list.parallelStream().filter(detail -> {
			return detail.getName() != null && Utility.containsIgnoreCase(detail.getName(), name.trim());
		}).collect(Collectors.toList());
		List<CustomerResponseMapper> mapperList = new ArrayList<CustomerResponseMapper>();
		if (null != filterList && !filterList.isEmpty()) {
			mapperList = filterList.parallelStream().map(li -> getCustomerDetailsById(li.getCustomerId()))
					.collect(Collectors.toList());
		}
		return mapperList;
	}

	@Override
	public List<CustomerResponseMapper> getCustomerDetailsBySector(String name) {
		SectorDetails sectorDetails = sectorDetailsRepository.getSectorDetailsBySectorName(name);
		if (null != sectorDetails) {
			List<Customer> sectorlist = customerRepository.getSectorLinkBySector(sectorDetails.getSectorId());

			// System.out.println("@@@@@@@@@@@@^^^^^^^^^^^^" + sectorlist.toString());
			// System.out.println("sector%%%%%%" + sector);
			if (null != sectorlist && !sectorlist.isEmpty()) {
				return sectorlist.stream().map(customer -> {

					CustomerResponseMapper customerMapper = getCustomerDetailsById(customer.getCustomerId());
					if (null != customerMapper) {
						return customerMapper;
					}
					return null;
				}).collect(Collectors.toList());

			}

			return null;
		}
		return null;
	}

	@Override
	public List<CustomerResponseMapper> getCustomerDetailsByCountry(String name) {
		List<Customer> countrylist = customerRepository.getCountryLinkByCountry(name);
		if (null != countrylist && !countrylist.isEmpty()) {
			return countrylist.stream().map(customer -> {
				CustomerResponseMapper customerMapper = getCustomerDetailsById(customer.getCustomerId());
				if (null != customerMapper) {
					return customerMapper;
				}
				return null;
			}).collect(Collectors.toList());

		}
		return null;
	}

	@Override
	public String saveCustomerInitiative(InitiativeDetailsMapper initiativeDetailsMapper) {

		String initiativeDetailsId = null;
		if (null != initiativeDetailsMapper) {
			InitiativeDetails initiativeDetails = new InitiativeDetails();
			initiativeDetails.setInitiativeName(initiativeDetailsMapper.getInitiativeName());
			initiativeDetails.setCustomerId(initiativeDetailsMapper.getCustomerId());
			initiativeDetails.setUserId(initiativeDetailsMapper.getUserId());
			initiativeDetails.setOrgId(initiativeDetailsMapper.getOrgId());
			initiativeDetails.setDescription(initiativeDetailsMapper.getDescription());
			initiativeDetailsRepository.save(initiativeDetails);
			initiativeDetailsId = initiativeDetails.getInitiativeDetailsId();

			/* insert to customer-initiative-link */

			List<String> skillList = initiativeDetailsMapper.getSkillList();
			if (null != skillList && !skillList.isEmpty()) {
				for (String skillId : skillList) {
					CustomerInitiativeLink customerInitiativeLink = new CustomerInitiativeLink();
					customerInitiativeLink.setSkilId(skillId);
					customerInitiativeLink.setInitiativeDetailsId(initiativeDetails.getInitiativeDetailsId());
					customerInitiativeLink.setUserId(initiativeDetailsMapper.getUserId());
					customerInitiativeLink.setOrgId(initiativeDetailsMapper.getOrgId());
					customerInitiativeLink.setLiveInd(true);
					customerInitiativeLinkRepository.save(customerInitiativeLink);
				}
			}
		}
		return initiativeDetailsId;
	}

	@Override
	public List<InitiativeDetailsMapper> getInitiativeListByCustomerId(String customerId) {
		List<InitiativeDetailsMapper> resultList = new ArrayList<>();

		List<InitiativeDetails> customerInitiativeList = initiativeDetailsRepository
				.getInitiativeListByCustomerId(customerId);

		if (null != customerInitiativeList && !customerInitiativeList.isEmpty()) {
			customerInitiativeList.stream().map(li -> {
				List<InitiativeSkillMapper> InitiativeSkillMapper1 = new ArrayList<>();
				InitiativeDetailsMapper mapper = new InitiativeDetailsMapper();
				mapper.setCustomerId(customerId);
				mapper.setInitiativeName(li.getInitiativeName());
				mapper.setInitiativeDetailsId(li.getInitiativeDetailsId());
				mapper.setUserId(li.getUserId());
				mapper.setOrgId(li.getOrgId());
				mapper.setDescription(li.getDescription());
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
		List<CustomerInitiativeLink> initiativeLinkList = customerInitiativeLinkRepository
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
	public CustomerKeySkillMapper getKeySkilListByCustomerIdAndDateRange(String customerId, String orgId,
			String startDate, String endDate) {

		Date end_date = null;
		Date start_date = null;
		try {
			end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}

		CustomerKeySkillMapper keySkillList = new CustomerKeySkillMapper();
		List<OpportunityDetails> opportunityDetails = opportunityDetailsRepository
				.getOpportunityListByCustomerIdAndLiveIndAndDateRange(customerId, start_date, end_date);
		List<String> skillLibery = candidateService.getSkillSetOfSkillLibery(orgId);

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
	public List<CustomerResponseMapper> getCustomerDetailsByOwnerName(String name) {
//		EmployeeDetails employeeDetails = employeeRepository.findByFullName(name);
//		if (null != employeeDetails) {
//			List<Customer> customerlist = customerRepository.getCustomerListByOwnerId(employeeDetails.getUserId());
//			if (null != customerlist && !customerlist.isEmpty()) {
//				return customerlist.stream().map(customer -> {
//					CustomerResponseMapper customerMapper = getCustomerDetailsById(customer.getCustomerId());
//					if (null != customerMapper) {
//						return customerMapper;
//					}
//					return null;
//				}).collect(Collectors.toList());
//
//			}
//
//			return null;
//		}
//		return null;

		List<CustomerResponseMapper> list = new ArrayList<>();
		List<EmployeeDetails> detailsList = employeeRepository.findByLiveInd(true);
		List<EmployeeDetails> filterList = detailsList.parallelStream().filter(detail -> {
			return detail.getFullName() != null && Utility.containsIgnoreCase(detail.getFullName(), name.trim());
		}).collect(Collectors.toList());

		if (null != filterList && !filterList.isEmpty()) {
			for (EmployeeDetails employeeDetails : filterList) {
				List<Customer> customerlist = customerRepository.getCustomerListByOwnerId(employeeDetails.getUserId());
				if (null != customerlist && !customerlist.isEmpty()) {
					return customerlist.stream().map(customer -> {
						CustomerResponseMapper customerResponseMapper = getCustomerDetailsById(
								customer.getCustomerId());
						if (null != customerResponseMapper) {
							return customerResponseMapper;
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
	public List<InitiativeSkillMapper> getSkillListByInitiativeDetailsId(String initiativeDetailsId) {
		List<InitiativeSkillMapper> InitiativeSkillMapper1 = new ArrayList<>();
		List<CustomerInitiativeLink> initiativeLinkList = customerInitiativeLinkRepository
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
			// initiativeDetailsMapper.setInitiativeSkillMapper(InitiativeSkillMapper1);
		}
		return InitiativeSkillMapper1;
	}

	@Override
	public List<InitiativeDetailsMapper> getInitiativeSkillListByUserId(String userId) {
		List<CustomerInitiativeLink> initiativeLinkList = customerInitiativeLinkRepository.getSkilListByUserId(userId);
		if (null != initiativeLinkList && !initiativeLinkList.isEmpty()) {
			return initiativeLinkList.stream().map(customerInitiativeLink -> {
				InitiativeDetailsMapper initiativeDetailsMapper = new InitiativeDetailsMapper();

				initiativeDetailsMapper.setInitiativeDetailsId(customerInitiativeLink.getInitiativeDetailsId());
				initiativeDetailsMapper.setLiveind(customerInitiativeLink.isLiveInd());

				// InitiativeSkillMapper initiativeSkillMapper = new InitiativeSkillMapper();

				DefinationDetails definationDetails = definationRepository
						.findByDefinationId(customerInitiativeLink.getSkilId());
				if (null != definationDetails) {
					initiativeDetailsMapper.setSkillName(definationDetails.getName());
					initiativeDetailsMapper.setSkilId(definationDetails.getDefinationId());
				}

				// InitiativeSkillMapper.add(initiativeSkillMapper);

				return initiativeDetailsMapper;
			}).collect(Collectors.toList());
			// initiativeDetailsMapper.setInitiativeSkillMapper(InitiativeSkillMapper);

			// initiativeDetailsMapper1.add(initiativeDetailsMapper);
		}

		return null;
	}

	@Override
	public List<InitiativeDetailsMapper> getInitiativeNameByUserId(String userId) {
		List<InitiativeDetails> initiativeDetails = initiativeDetailsRepository.getInitiativeListByUserId(userId);

		if (null != initiativeDetails && !initiativeDetails.isEmpty()) {
			return initiativeDetails.stream().map(initiativeDetails2 -> {
				List<InitiativeDetails> customerInitiativeList = initiativeDetailsRepository
						.getInitiativeListByCustomerId(initiativeDetails2.getCustomerId());

				if (null != customerInitiativeList && !customerInitiativeList.isEmpty()) {
					return customerInitiativeList.stream().map(li -> {
						InitiativeDetailsMapper mapper = new InitiativeDetailsMapper();
						mapper.setCustomerId(li.getCustomerId());
						mapper.setInitiativeName(li.getInitiativeName());
						mapper.setInitiativeDetailsId(li.getInitiativeDetailsId());
						mapper.setUserId(li.getUserId());
						mapper.setOrgId(li.getOrgId());
						return mapper;
					}).collect(Collectors.toList());
				}
				return null;
			}).filter(l -> l != null).flatMap(l -> l.stream()).collect(Collectors.toList());
		}
		return null;

	}

	@Override
	public InitiativeDetailsMapper updateInitiativeDetails(String initiativeDetailsId,
			InitiativeDetailsMapper initiativeDetailsMapper) {
		InitiativeDetailsMapper initiativeDetailMapper = null;

		InitiativeDetails initiativeDetails = initiativeDetailsRepository
				.getInitiativeDetailsByInitiativeDetailsId(initiativeDetailsId);
		if (null != initiativeDetails) {
			initiativeDetails.setInitiativeName(initiativeDetailsMapper.getInitiativeName());
			initiativeDetails.setDescription(initiativeDetailsMapper.getDescription());
			initiativeDetailsRepository.save(initiativeDetails);
		}

		List<CustomerInitiativeLink> initiativeLinkList = customerInitiativeLinkRepository
				.getSkilListByInitiativeDetailsIdAndLiveInd(initiativeDetailsId);
		if (null != initiativeLinkList && !initiativeLinkList.isEmpty()) {
			for (CustomerInitiativeLink customerInitiativeLink : initiativeLinkList) {
				customerInitiativeLinkRepository.delete(customerInitiativeLink);
			}
		}
		List<String> skillList = initiativeDetailsMapper.getSkillList();
		if (null != skillList && !skillList.isEmpty()) {
			for (String skillId : skillList) {
				CustomerInitiativeLink customerInitiativeLink = new CustomerInitiativeLink();
				customerInitiativeLink.setSkilId(skillId);
				customerInitiativeLink.setInitiativeDetailsId(initiativeDetailsId);
				customerInitiativeLink.setUserId(initiativeDetailsMapper.getUserId());
				customerInitiativeLink.setOrgId(initiativeDetailsMapper.getOrgId());
				customerInitiativeLink.setLiveInd(true);
				customerInitiativeLinkRepository.save(customerInitiativeLink);
			}
		}

		initiativeDetailMapper = getInitiativeDetailsByInitiativeDetailsId(initiativeDetailsId);

		return initiativeDetailMapper;
	}

	public InitiativeDetailsMapper getInitiativeDetailsByInitiativeDetailsId(String initiativeDetailsId) {
		InitiativeDetailsMapper initiativeDetailsMapper = new InitiativeDetailsMapper();
		// List<String> skillLibery = new ArrayList<>();

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
		List<CustomerInitiativeLink> initiativeLinkList = customerInitiativeLinkRepository
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

//		List<CustomerInitiativeLink> initiativeLinkList = customerInitiativeLinkRepository
//				.getSkilListByInitiativeDetailsIdAndLiveInd(initiativeDetailsId);
//		if (null != initiativeLinkList && !initiativeLinkList.isEmpty()) {
//			initiativeLinkList.stream().map(li -> {
//
//				DefinationDetails definationDetails = definationRepository.findByDefinationId(li.getSkilId());
//				System.out.println(initiativeDetailsId);
//				System.out.println("Skill name===" + definationDetails.getName());
//				skillLibery.add(definationDetails.getName());
//				return skillLibery;
//			}).collect(Collectors.toList());
//			initiativeDetailsMapper.setSkillList(skillLibery);
//		}
		return initiativeDetailsMapper;
	}

	public List<CustomerViewMapper> getCustomerDetailsByCustomerId(String customerId) {

		List<Customer> customer = customerRepository.getCustomerByCustomerIdAndLiveInd(customerId);
		System.out.println("customer object is..." + customer);

		List<CustomerViewMapper> customerMapper = new ArrayList<>();

		if (null != customer) {
			for (Customer customer2 : customer) {
				CustomerViewMapper customerViewMapper1 = new CustomerViewMapper();
				if (customer2.getSector() != null && customer2.getSector().trim().length() > 0) {
					SectorDetails sector = sectorDetailsRepository.getSectorDetailsBySectorId(customer2.getSector());
					System.out.println("get sectordetails by id returns........." + customer2.getSector());
					System.out.println("sector object is......." + sector);
					if (sector != null) {
						customerViewMapper1.setSector(sector.getSectorName());
						customerViewMapper1.setSectorId(customer2.getSector());
					} else {
						customerViewMapper1.setSector("");
						customerViewMapper1.setSectorId("");
					}
				}
				customerViewMapper1.setCustomerId(customerId);
				customerViewMapper1.setName(customer2.getName());
				customerViewMapper1.setUrl(customer2.getUrl());
				customerViewMapper1.setNotes(customer2.getNotes());
				customerViewMapper1.setEmail(customer2.getEmail());
				customerViewMapper1.setGroup(customer2.getGroup());
				customerViewMapper1.setVatNo(customer2.getVatNo());
				customerViewMapper1.setPhoneNumber(customer2.getPhoneNumber());
				customerViewMapper1.setCountryDialCode(customer2.getCountryDialCode());
				customerViewMapper1.setUserId(customer2.getUserId());
				customerViewMapper1.setOrganizationId(customer2.getOrganizationId());
				customerViewMapper1.setCreationDate(Utility.getISOFromDate(customer2.getCreationDate()));
				customerViewMapper1.setImageURL(customer2.getImageURL());

				customerViewMapper1.setCountry(customer2.getCountry());
				// customerMapper.setSector(sector.getSectorName());
				customerViewMapper1.setZipcode(customer2.getZipcode());

				customerViewMapper1.setDocumentId(customer2.getDocumentId());
				customerViewMapper1.setCategory(customer2.getCategory());
				customerViewMapper1.setBusinessRegistration(customer2.getBusinessRegistration());

				EmployeeDetails employeeDetails = employeeRepository
						.getEmployeeDetailsByEmployeeId(customer2.getUserId());
				if (null != employeeDetails) {
					String middleName = " ";
					String lastName = "";

					if (!StringUtils.isEmpty(employeeDetails.getLastName())) {

						lastName = employeeDetails.getLastName();
					}

					if (employeeDetails.getMiddleName() != null && employeeDetails.getMiddleName().length() > 0) {

						middleName = employeeDetails.getMiddleName();
						customerViewMapper1
								.setOwnerName(employeeDetails.getFirstName() + " " + middleName + " " + lastName);
						customerViewMapper1.setOwnerImageId(employeeDetails.getImageId());
					} else {

						customerViewMapper1.setOwnerName(employeeDetails.getFirstName() + " " + lastName);
						customerViewMapper1.setOwnerImageId(employeeDetails.getImageId());
					}

				}
				EmployeeDetails employeeDetail = employeeRepository
						.getEmployeeDetailsByEmployeeId(customer2.getAssignedTo());
				if (null != employeeDetail) {
					String middleName = " ";
					String lastName = "";

					if (!StringUtils.isEmpty(employeeDetail.getLastName())) {

						lastName = employeeDetails.getLastName();
					}

					if (employeeDetail.getMiddleName() != null && employeeDetail.getMiddleName().length() > 0) {

						middleName = employeeDetail.getMiddleName();
						customerViewMapper1
								.setAssignedTo(employeeDetail.getFirstName() + " " + middleName + " " + lastName);

					} else {

						customerViewMapper1.setAssignedTo(employeeDetail.getFirstName() + " " + lastName);

					}

				}

				CustomerRecruitUpdate dbCustomerRecruitUpdate = customerRecruitUpdateRepository
						.findByCustomerId(customer2.getCustomerId());
				if (null != dbCustomerRecruitUpdate) {
					System.out.println("====" + dbCustomerRecruitUpdate.getId());
					customerViewMapper1
							.setLastRequirementOn(Utility.getISOFromDate(dbCustomerRecruitUpdate.getUpdatedDate()));
				}

				List<AddressMapper> addressList = new ArrayList<AddressMapper>();
				List<CustomerAddressLink> customerAddressList = customerAddressLinkRepository
						.getAddressListByCustomerId(customerId);

				/* fetch customer address & set to customer mapper */
				if (null != customerAddressList && !customerAddressList.isEmpty()) {

					for (CustomerAddressLink customerAddressLink : customerAddressList) {
						AddressDetails addressDetails = addressRepository
								.getAddressDetailsByAddressId(customerAddressLink.getAddressId());

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
							addressList.add(addressMapper);
						}

					}
					customerViewMapper1.setAddress(addressList);
				}
				System.out.println("addressList.......... " + addressList);
				customerMapper.add(customerViewMapper1);
			}

		}

		return customerMapper;

	}

	@Override
	public String saveSkillSet(CustomerSkillLinkMapper customerSkillLinkMapper) {
		DefinationDetails definationDetails1 = definationRepository
				.getBySkillNameAndLiveInd(customerSkillLinkMapper.getSkillName());

		if (null != definationDetails1) {

			CustomerSkillLink customerSkillLink1 = new CustomerSkillLink();
			customerSkillLink1.setSkillName(definationDetails1.getDefinationId());
			customerSkillLink1.setCustomerId(customerSkillLinkMapper.getCustomerId());
			customerSkillLink1.setCreationDate(new Date());
			customerSkillLink1.setEditInd(true);
			customerSkillLink1.setUserId(customerSkillLinkMapper.getOrgId());
			customerSkillLink1.setOrgId(customerSkillLinkMapper.getOrgId());
			customerSkillLinkRepository.save(customerSkillLink1);

		} else {

			DefinationInfo definationInfo = new DefinationInfo();

			definationInfo.setCreation_date(new Date());
			String id = definationInfoRepository.save(definationInfo).getDefination_info_id();

			DefinationDetails newDefinationDetails = new DefinationDetails();
			newDefinationDetails.setDefinationId(id);
			newDefinationDetails.setName(customerSkillLinkMapper.getSkillName());
			newDefinationDetails.setOrg_id(customerSkillLinkMapper.getOrgId());
			newDefinationDetails.setUser_id(customerSkillLinkMapper.getUserId());
			newDefinationDetails.setCreation_date(new Date());
			newDefinationDetails.setLiveInd(true);
			newDefinationDetails.setEditInd(true);
			definationRepository.save(newDefinationDetails);

			CustomerSkillLink customerSkillLink1 = new CustomerSkillLink();
			customerSkillLink1.setSkillName(id);
			customerSkillLink1.setCustomerId(customerSkillLinkMapper.getCustomerId());
			customerSkillLink1.setCreationDate(new Date());
			customerSkillLink1.setEditInd(true);
			customerSkillLink1.setUserId(customerSkillLinkMapper.getOrgId());
			customerSkillLink1.setOrgId(customerSkillLinkMapper.getOrgId());
			customerSkillLinkRepository.save(customerSkillLink1);

		}

		return customerSkillLinkMapper.getSkillName();

	}

	@Override
	public List<CustomerSkillLinkMapper> getSkillSetByCustomerId(String customerId) {
		List<CustomerSkillLink> skillList = customerSkillLinkRepository.getByCustomerId(customerId);
		if (null != skillList && !skillList.isEmpty()) {
			return skillList.stream().map(customerSkillLink -> {
				CustomerSkillLinkMapper customerSkillLinkMapper = new CustomerSkillLinkMapper();
				DefinationDetails definationDetails1 = definationRepository
						.findByDefinationId(customerSkillLink.getSkillName());
				if (null != definationDetails1) {
					customerSkillLinkMapper.setSkillName(definationDetails1.getName());

				}
				customerSkillLinkMapper.setCustomerId(customerSkillLink.getCustomerId());
				customerSkillLinkMapper.setCreationDate(Utility.getISOFromDate(customerSkillLink.getCreationDate()));
				customerSkillLinkMapper.setCustomerSkillLinkId(customerSkillLink.getCustomerSkillLinkId());
				customerSkillLinkMapper.setEditInd(customerSkillLink.isEditInd());
				customerSkillLinkMapper.setUserId(customerSkillLink.getOrgId());
				customerSkillLinkMapper.setOrgId(customerSkillLink.getOrgId());
				return customerSkillLinkMapper;

			}).collect(Collectors.toList());
		}
		return null;
	}

	@Override
	public CustomerSkillLinkMapper deleteSkillsSet(String customerSkillLinkId) {
		CustomerSkillLinkMapper resultMapper = new CustomerSkillLinkMapper();
		if (null != customerSkillLinkId) {
			CustomerSkillLink customerSkillLink = customerSkillLinkRepository
					.findByCustomerSkillLinkId(customerSkillLinkId);
			if (null != customerSkillLink) {
				customerSkillLinkRepository.delete(customerSkillLink);
			}
		}
		return resultMapper;
	}

	@Override
	public boolean checkSkillInCustomerSkillSet(String skillName, String customerId) {
		List<DefinationDetails> definationDetails = definationRepository.findByNameContaining(skillName);
		for (DefinationDetails definationDetails2 : definationDetails) {
			if (null != definationDetails2) {
				List<CustomerSkillLink> customerSkillLink = customerSkillLinkRepository
						.findBySkillNameAndCustomerId(definationDetails2.getDefinationId(), customerId);
				if (customerSkillLink.size() > 0) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void deleteCustomerInnitiative(String initiativeDetailsId) {

		if (null != initiativeDetailsId) {

			InitiativeDetails initiative = initiativeDetailsRepository
					.getInitiativeDetailsByInitiativeDetailsId(initiativeDetailsId);
			if (null != initiative) {
				initiativeDetailsRepository.delete(initiative);
			}

			List<CustomerInitiativeLink> customerInitiativeLink = customerInitiativeLinkRepository
					.getByInitiativeDetailsId(initiativeDetailsId);
			if (null != customerInitiativeLink) {
				// for (CustomerInitiativeLink customerInitiativeLink2 : customerInitiativeLink)
				// {
				customerInitiativeLinkRepository.deleteAll(customerInitiativeLink);
				// }

			}
		}

	}

	@Override
	public List<CustomerResponseMapper> getCustomerDetailsByuserId(String loggeduserId) {
		List<Customer> customerList = customerRepository.findByUserIdandLiveInd(loggeduserId);
		System.out.println("###########" + customerList);
		if (null != customerList && !customerList.isEmpty()) {
			return customerList.stream().map(customerDetails -> {
				CustomerResponseMapper customerDetailsMapper = getCustomerDetailsById(customerDetails.getCustomerId());
				if (null != customerDetailsMapper.getCustomerId()) {
					return customerDetailsMapper;
				}
				return null;
			}).collect(Collectors.toList());
		}
		return null;

	}

	@Override
	public List<CustomerResponseMapper> getAllCustomerListCount() {
		List<Permission> list = permissionRepository.getUserList();
		System.out.println(" user$$$$$$$$$$$$==" + list.toString());
		if (null != list && !list.isEmpty()) {
			return list.stream().map(permission -> {
				List<CustomerResponseMapper> mp = customerService.getCustomerDetailsByuserId(permission.getUserId());
				System.out.println(" userId$$$$$$$$$$$$==" + permission.getUserId());
				return mp;
			}).flatMap(l -> l.stream()).collect(Collectors.toList());

		}
		return null;

	}

	@Override
	public List<CustomerViewMapperrForMap> getCustomerDetailsByuserIdForMap(String userId) {
		List<Customer> customerList = customerRepository.findByUserIdandLiveInd(userId);
		System.out.println("###########" + customerList);
		if (null != customerList && !customerList.isEmpty()) {
			return customerList.stream().map(customerDetails -> {
				Customer customer = customerRepository.getCustomerByIdAndLiveInd(customerDetails.getCustomerId());
				if (null != customer) {

					CustomerViewMapperrForMap customerDetailsMapper = CustomerViewMapperrForMap.builder()
							.customerId(customer.getCustomerId()).email(customer.getEmail()).name(customer.getName())
							.imageURL(customer.getImageURL()).userId(customer.getUserId())
							.creationDate(Utility.getISOFromDate(customer.getCreationDate())).build();

					CustomerAddressLink customerAddressList = customerAddressLinkRepository
							.findByCustomerId(customerDetails.getCustomerId());
					if (null != customerAddressList) {

						AddressDetails addressDetails = addressRepository
								.getAddressDetailsByAddressId(customerAddressList.getAddressId());
						if (null != addressDetails) {
							customerDetailsMapper.setLongitude(addressDetails.getLongitude());
							customerDetailsMapper.setLatitude(addressDetails.getLatitude());
						}
					}
					return customerDetailsMapper;
				}
				return null;
			}).filter(Objects::nonNull).collect(Collectors.toList());
		}
		return null;
	}

	@Override
	public List<CustomerViewMapperForDropDown> getCustomerDetailsByuserIdForDropDown(String loggeduserId) {
		List<CustomerViewMapperForDropDown> mapper = new ArrayList<>();
		List<Customer> customerList = customerRepository.findByUserIdWithAssignedToandLiveInd(loggeduserId);
		System.out.println("###########" + customerList);
		if (null != customerList && !customerList.isEmpty()) {
			return customerList.stream().map(customerDetails -> {
				CustomerViewMapperForDropDown customerDetailsMapper = new CustomerViewMapperForDropDown();
				customerDetailsMapper.setCustomerId(customerDetails.getCustomerId());
				customerDetailsMapper.setName(customerDetails.getName());
				customerDetailsMapper.setCreationDate(Utility.getISOFromDate(customerDetails.getCreationDate()));
				return customerDetailsMapper;
			}).collect(Collectors.toList());
		}
		return mapper;

	}

	@Override
	public List<CustomerViewMapperForDropDown> getCustomerDetailsByOrgIdForDropDown(String orgId) {
		List<CustomerViewMapperForDropDown> mapper = new ArrayList<>();
		List<Customer> customerList = customerRepository.findByOrgIdandLiveInd(orgId);
		System.out.println("###########" + customerList);
		if (null != customerList && !customerList.isEmpty()) {
			return customerList.stream().map(customerDetails -> {
				CustomerViewMapperForDropDown customerDetailsMapper = new CustomerViewMapperForDropDown();
				customerDetailsMapper.setCustomerId(customerDetails.getCustomerId());
				customerDetailsMapper.setName(customerDetails.getName());
				customerDetailsMapper.setCreationDate(Utility.getISOFromDate(customerDetails.getCreationDate()));
				return customerDetailsMapper;
			}).collect(Collectors.toList());
		}
		return mapper;
	}

	@Override
	public HashMap getCustomerOppertunityCountByCustomerId(String customerId) {
		List<OpportunityDetails> opportunityList = opportunityDetailsRepository
				.getOpportunityListByCustomerIdAndLiveInd(customerId);
		HashMap map = new HashMap();
		map.put("CustomerOppertunityDetails", opportunityList.size());

		return map;
	}

	@Override
	public HashMap getCustomerContactCountByCustomerId(String customerId) {
		List<CustomerContactLink> customerContactLinkList = customerContactLinkRepository
				.getContactIdByCustomerId(customerId);
		HashMap map = new HashMap();
		int count = 0;
		for (CustomerContactLink customerContactLink : customerContactLinkList) {
			ContactDetails contact = contactRepository.getcontactDetailsById(customerContactLink.getContactId());
			if (null != contact) {
				count++;

			}

		}
		map.put("CustomerContactDetails", count);

		return map;
	}

	@Override
	public HashMap getCustomerDocumentCountByCustomerId(String customerId) {
		List<CustomerDocumentLink> customerDocumentLinkList = customerDocumentLinkRepository
				.getDocumentByCustomerId(customerId);
		Set<String> documentIds = customerDocumentLinkList.stream().map(CustomerDocumentLink::getDocumentId)
				.collect(Collectors.toSet());
		int count = 0;
		HashMap map = new HashMap();
		for (String documentId : documentIds) {
			DocumentDetails doc = documentDetailsRepository.getDocumentDetailsById(documentId);
			if (null != doc) {
				count++;
			}

		}

		map.put("CustomerDocumentDetails", count);
		return map;
	}

	@Override
	public List<Map<String, Double>> getCustomerCountSourceWiseByUserId(String userId, String orgId) {
		List<Map<String, Double>> map = new ArrayList<>();

		List<Source> sourceList = sourceRepository.findByOrgIdAndLiveInd(orgId, true);
		if (null != sourceList && !sourceList.isEmpty()) {
			for (Source source : sourceList) {
				Map map1 = new HashMap<>();
				List<Customer> customerList = customerRepository.findByUserIdAndSourceAndLiveInd(userId,
						source.getSourceId(), true);

				map1.put("source", source.getName());
				map1.put("number", customerList.size());
				map.add(map1);
			}
		}

		return map;
	}

	@Override
	public List<Map<String, List<CustomerResponseMapper>>> getCustomerListSourceWiseByUserId(String userId,
			String orgId) {
		List<Map<String, List<CustomerResponseMapper>>> map = new ArrayList<>();

		List<Source> sourceList = sourceRepository.findByOrgIdAndLiveInd(orgId, true);
		if (null != sourceList && !sourceList.isEmpty()) {
			for (Source source : sourceList) {
				Map map1 = new HashMap<>();
				List<CustomerResponseMapper> resulList = new ArrayList<>();
				List<Customer> list = customerRepository.findByUserIdAndSourceAndLiveInd(userId, source.getSourceId(),
						true);
				if (null != list && !list.isEmpty()) {
					list.stream().map(customer -> {
						CustomerResponseMapper customerViewMapper = getCustomerDetailsById(customer.getCustomerId());
						if (null != customerViewMapper) {
							resulList.add(customerViewMapper);
						}
						return resulList;
					}).collect(Collectors.toList());
				}

				map1.put("source", source.getName());
				map1.put("customerList", resulList);
				map.add(map1);
			}
		}

		return map;
	}

	@Override
	public List<CustomerResponseMapper> getFilterCustomerDetailsPageWiseByuserId(String userId, int pageNo,
			int pageSize, String filter) {
		Page<Customer> customerList;
		List<CustomerResponseMapper> result = new ArrayList<>();
		Pageable paging = null;
		if (filter.equalsIgnoreCase("creationdate")) {
			paging = PageRequest.of(pageNo, pageSize, Sort.by("creationDate").descending());
			// customerList = customerRepository.getCustomerDetailsListByUserId(userId,
			// paging);

		} else if (filter.equalsIgnoreCase("ascending")) {
			paging = PageRequest.of(pageNo, pageSize, Sort.by("name"));
			// customerList = customerRepository.getByOrderByNameAsc(userId,pageable);

		} else if (filter.equalsIgnoreCase("descending")) {
			paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "name"));
		}
		customerList = customerRepository.getCustomerDetailsListByUserId(userId, paging);
		System.out.println("###########" + customerList);
		if (null != customerList && !customerList.isEmpty()) {
			result = customerList.stream().map(customerDetails -> {
				CustomerResponseMapper customerDetailsMapper = getCustomerDetailsById(customerDetails.getCustomerId());
				if (null != customerDetailsMapper.getCustomerId()) {
					customerDetailsMapper.setPageCount(customerList.getTotalPages());
					customerDetailsMapper.setDataCount(customerList.getSize());
					customerDetailsMapper.setListCount(customerList.getTotalElements());
					return customerDetailsMapper;
				}
				return null;
			}).collect(Collectors.toList());
		}
		return result;
	}

	@Override
	public List<CustomerViewMapperForDropDown> getCustomerDetailsByUserIdForDropDown(String userId) {
		List<CustomerViewMapperForDropDown> mapper = new ArrayList<>();
		List<Customer> customerList = customerRepository.findByUserIdWithAssignedToandLiveInd(userId);
		if (null != customerList && !customerList.isEmpty()) {
			return customerList.stream().map(customerDetails -> {
				CustomerViewMapperForDropDown customerDetailsMapper = new CustomerViewMapperForDropDown();
				customerDetailsMapper.setCustomerId(customerDetails.getCustomerId());
				customerDetailsMapper.setName(customerDetails.getName());
				customerDetailsMapper.setCreationDate(Utility.getISOFromDate(customerDetails.getCreationDate()));
				return customerDetailsMapper;
			}).collect(Collectors.toList());
		}
		return mapper;
	}

	@Override
	public List<ActivityMapper> getActivityListByCustomerId(String customerId) {
		List<ActivityMapper> resultList = new ArrayList<>();

		List<CustomerCallLink> customerCallLink = customerCallLinkRepo.getCallListByCustomerIdAndLiveInd(customerId);
		if (null != customerCallLink && !customerCallLink.isEmpty()) {
			customerCallLink.stream().map(call -> {
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

		List<CustomerEventLink> eventLink = customerEventRepo.getByCustomerIdAndLiveInd(customerId);
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

		List<CustomerTaskLink> taskLink = customerTaskRepo.getTaskListByCustomerIdAndLiveInd(customerId);
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

//    @Override
//    public NotesMapper updateNoteDetails(NotesMapper notesMapper) {
//
//        NotesMapper resultMapper = employeeService.updateNotes(notesMapper);
//
//        return resultMapper;
//    }

	@Override
	public void deleteCustomerNotesById(String notesId) {
		CustomerNotesLink notesList = customerNotesLinkRepository.findByNotesId(notesId);
		if (null != notesList) {

			Notes notes = notesRepository.findByNoteId(notesId);
			if (null != notes) {
				notes.setLiveInd(false);
				notesRepository.save(notes);
			}
		}
	}

	@Override
	public List<CustomerResponseMapper> getCustomerListByOrgId(String orgId, int pageNo, int pageSize, String filter) {
		Pageable paging = null;
		paging = PageRequest.of(pageNo, pageSize, Sort.by("creationDate").descending());
		if (filter.equalsIgnoreCase("creationdate")) {
			paging = PageRequest.of(pageNo, pageSize, Sort.by("creationDate").descending());

		} else if (filter.equalsIgnoreCase("ascending")) {
			paging = PageRequest.of(pageNo, pageSize, Sort.by("name"));

		} else if (filter.equalsIgnoreCase("descending")) {
			paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "name"));
		}
		Page<Customer> customerList = customerRepository.getCustomerListByOrgIdAndPagging(orgId, paging);
		List<CustomerResponseMapper> resultMapper = new ArrayList<>();
		if (null != customerList && !customerList.isEmpty()) {
			resultMapper = customerList.stream().map(li -> {
				CustomerResponseMapper mapper = getCustomerDetailsById(li.getCustomerId());
				mapper.setPageCount(customerList.getTotalPages());
				mapper.setDataCount(customerList.getSize());
				mapper.setListCount(customerList.getTotalElements());
				return mapper;
			}).collect(Collectors.toList());
		}
		return resultMapper;
	}

	@Override
	public Set<CustomerResponseMapper> getTeamCustomerDetailsPageWiseByuserId(String userId, int pageNo, int pageSize,
			String filter) {
		Page<Customer> customerList = null;
		Set<CustomerResponseMapper> result = new HashSet<>();
		Pageable paging = null;
		Team team = teamRepository.findByTeamLeadAndLiveInd(userId, true);
		if (null != team) {
			List<String> userIds = teamMemberLinkRepo.findByTeamId(team.getTeamId()).stream()
					.map(TeamMemberLink::getTeamMemberId).collect(Collectors.toList());
			if (filter.equalsIgnoreCase("creationdate")) {
				paging = PageRequest.of(pageNo, pageSize, Sort.by("creationDate").descending());
				// customerList = customerRepository.getCustomerDetailsListByUserId(userId,
				// paging);

			} else if (filter.equalsIgnoreCase("ascending")) {
				paging = PageRequest.of(pageNo, pageSize, Sort.by("name"));
				// customerList = customerRepository.getByOrderByNameAsc(userId,pageable);

			} else if (filter.equalsIgnoreCase("descending")) {
				paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "name"));
			}
			customerList = customerRepository.getTeamCustomerDetailsListByUserId(userIds, paging);
//		System.out.println("###########" + customerList);
			if (null != customerList && !customerList.isEmpty()) {
				result = customerList.getContent().stream().map(customerDetails -> {
					CustomerResponseMapper customerDetailsMapper = getCustomerDetailsById(
							customerDetails.getCustomerId());
					if (null != customerDetailsMapper.getCustomerId()) {
						return customerDetailsMapper;
					}
					return null;
				}).collect(Collectors.toSet());
			}
		}
		return result;
	}

	@Override
	public HashMap getTeamCustomerCountByUserId(String userId) {
		HashMap map = new HashMap();
		Team team = teamRepository.findByTeamLeadAndLiveInd(userId, true);

		List<String> userIds = teamMemberLinkRepo.findByTeamId(team.getTeamId()).stream()
				.map(TeamMemberLink::getTeamMemberId).collect(Collectors.toList());
		List<Customer> customerPage = customerRepository.getTeamCustomerListByUserIds(userIds);
		map.put("CustomerTeam", customerPage.size());
		return map;
	}

	@Override
	public DocumentMapper updateContractIndForDocument(DocumentMapper documentMapperr) {
		CustomerDocumentLink customerDocumentLink = customerDocumentLinkRepository
				.getDocumentByDocumentId(documentMapperr.getDocumentId());
		DocumentMapper documentMapper = new DocumentMapper();
		if (null != customerDocumentLink) {
			customerDocumentLink.setContractInd(documentMapperr.isContractInd());
			customerDocumentLinkRepository.save(customerDocumentLink);

			documentMapper = documentService.getDocument(customerDocumentLink.getDocumentId());
			if (null != documentMapper.getDocumentId()) {
				documentMapper.setContractInd(customerDocumentLink.isContractInd());
			}
		}
		return documentMapper;
	}

	@Override
	public boolean customerByUrl(String url) {
		List<Customer> customer = customerRepository.getByUrl(url);
		if (null != customer && !customer.isEmpty()) {

			return true;
		}

		return false;
	}

	@Override
	public HashMap getCustomerOppertunityProposalValueCountByCustomerId(String customerId, String userId,
			String orgId) {
		HashMap map = new HashMap();
		int count = 0;
		int conversionAmount = 0;
		Customer customer = customerRepository.getCustomerByIdAndLiveInd(customerId);
		if (null != customer) {
			System.out.println("in customer: ");
			if (customer.getUserId().equalsIgnoreCase(userId)) {
				System.out.println("same user: ");
				List<OpportunityDetails> opportunityList = opportunityDetailsRepository
						.getOpportunityListByCustomerIdAndLiveInd(customerId);
				if (null != opportunityList && !opportunityList.isEmpty()) {
					System.out.println("same user opportunityList: " + opportunityList.size());
					for (OpportunityDetails opportunityDetails : opportunityList) {
						if (!StringUtils.isEmpty(opportunityDetails.getProposalAmount())) {
							System.out.println("same user opportunityDetails.getProposalAmount(): "
									+ opportunityDetails.getProposalAmount());
							EmployeeDetails employeeDetails = employeeRepository
									.getEmployeeDetailsByEmployeeId(customer.getUserId());
							if (null != employeeDetails.getCurrency()) {
								System.out.println(
										"same user employeeDetails.getCurrency(): " + employeeDetails.getCurrency());
								Currency userCurrency = currencyRepository
										.getByCurrencyId(employeeDetails.getCurrency());
								if (null != userCurrency) {
									System.out.println("same user userCurrency.getCurrencyName(): "
											+ userCurrency.getCurrencyName());
									if (null != opportunityDetails.getCurrency()) {
										System.out.println("same user pportunityDetails.getCurrency(): "
												+ opportunityDetails.getCurrency());
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
							.getEmployeeDetailsByEmployeeId(customer.getUserId());
					if (null != employeeDetails.getCurrency()) {
						Currency userCurrency = currencyRepository.getByCurrencyId(employeeDetails.getCurrency());
						if (null != userCurrency) {
							System.out.println("in user trade currency : " + userCurrency.getCurrencyName());
							map.put("tradeCurrency", userCurrency.getCurrencyName());
						}
					}
				} else {
					System.out.println("in user 4");
					map.put("message", "Opportunity not available for this Customer");
				}

			} else {
				System.out.println("diff user: ");
				List<OpportunityDetails> opportunityList = opportunityDetailsRepository
						.getOpportunityListByCustomerIdAndLiveInd(customerId);
				if (null != opportunityList && !opportunityList.isEmpty()) {
					System.out.println("diff user opportunityList: " + opportunityList.size());
					for (OpportunityDetails opportunityDetails : opportunityList) {
						if (!StringUtils.isEmpty(opportunityDetails.getProposalAmount())) {
							System.out.println("diff user opportunityDetails.getProposalAmount(): "
									+ opportunityDetails.getProposalAmount());
							OrganizationDetails organizationDetails = organizationRepository
									.getOrganizationDetailsById(customer.getOrganizationId());
							if (null != organizationDetails.getTrade_currency()) {
								System.out.println("diff user organizationDetails.getTrade_currency(): "
										+ organizationDetails.getTrade_currency());
								if (null != opportunityDetails.getCurrency()) {
									System.out.println("diff user pportunityDetails.getCurrency(): "
											+ opportunityDetails.getCurrency());
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
							.getOrganizationDetailsById(customer.getOrganizationId());
					if (null != organizationDetails.getTrade_currency()) {
						System.out.println("in org trade currency : " + organizationDetails.getTrade_currency());
						map.put("tradeCurrency", organizationDetails.getTrade_currency());
					}
				} else {
					System.out.println("in org 4");
					map.put("message", "Opportunity not available for this Customer");
				}
			}
		} else {
			map.put("message", "Customer not available");
		}

		map.put("pipeLineValue", count);

		return map;
	}

	@Override
	public HashMap getCustomerOppertunityWeightedValueCountByCustomerId(String customerId, String userId,
			String orgId) {
		HashMap map = new HashMap();
		double count = 0;
		int conversionAmount = 0;
		Customer customer = customerRepository.getCustomerByIdAndLiveInd(customerId);
		if (null != customer) {
			if (customer.getUserId().equalsIgnoreCase(userId)) {

				List<OpportunityDetails> opportunityList = opportunityDetailsRepository
						.getOpportunityListByCustomerIdAndLiveInd(customerId);
				if (null != opportunityList && !opportunityList.isEmpty()) {
					for (OpportunityDetails opportunityDetails : opportunityList) {
						int pAmount = 0;
						double wValue = 0;
						double total = 0;
						if (!StringUtils.isEmpty(opportunityDetails.getProposalAmount())) {
							EmployeeDetails employeeDetails = employeeRepository
									.getEmployeeDetailsByEmployeeId(customer.getUserId());
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
											OpportunityStages oppStages = opportunityStagesRepository
													.getOpportunityStagesByOpportunityStagesId(
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
												OpportunityStages oppStages = opportunityStagesRepository
														.getOpportunityStagesByOpportunityStagesId(
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
													OpportunityStages oppStages = opportunityStagesRepository
															.getOpportunityStagesByOpportunityStagesId(
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
							.getEmployeeDetailsByEmployeeId(customer.getUserId());
					if (null != employeeDetails.getCurrency()) {
						Currency userCurrency = currencyRepository.getByCurrencyId(employeeDetails.getCurrency());
						if (null != userCurrency) {
							System.out.println("in user trade currency : " + userCurrency.getCurrencyName());
							map.put("tradeCurrency", userCurrency.getCurrencyName());
						}
					}
				} else {
					System.out.println("in user 4");
					map.put("message", "Opportunity not available for this Customer");
				}

			} else {

				List<OpportunityDetails> opportunityList = opportunityDetailsRepository
						.getOpportunityListByCustomerIdAndLiveInd(customerId);
				if (null != opportunityList && !opportunityList.isEmpty()) {
					for (OpportunityDetails opportunityDetails : opportunityList) {
						int pAmount = 0;
						double wValue = 0;
						double total = 0;
						if (!StringUtils.isEmpty(opportunityDetails.getProposalAmount())) {
							OrganizationDetails organizationDetails = organizationRepository
									.getOrganizationDetailsById(customer.getOrganizationId());
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
										OpportunityStages oppStages = opportunityStagesRepository
												.getOpportunityStagesByOpportunityStagesId(
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
											OpportunityStages oppStages = opportunityStagesRepository
													.getOpportunityStagesByOpportunityStagesId(
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
												OpportunityStages oppStages = opportunityStagesRepository
														.getOpportunityStagesByOpportunityStagesId(
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
							.getOrganizationDetailsById(customer.getOrganizationId());
					if (null != organizationDetails.getTrade_currency()) {
						System.out.println("in org trade currency : " + organizationDetails.getTrade_currency());
						map.put("tradeCurrency", organizationDetails.getTrade_currency());
					}
				} else {
					System.out.println("in org 4");
					map.put("message", "Opportunity not available for this Customer");
				}
			}
		} else {
			map.put("message", "Customer not available");
		}

		map.put("weightedValue", count);

		return map;
	}

//	@Override
//	public HashMap getCustomerOppertunityWeightedValueCountByCustomerId(String customerId) {
//		double count = 0;
//		List<OpportunityDetails> opportunityList = opportunityDetailsRepository
//				.getOpportunityListByCustomerIdAndLiveInd(customerId);
//		HashMap map = new HashMap();
//		if (null != opportunityList && !opportunityList.isEmpty()) {
//	for(OpportunityDetails opportunityDetails : opportunityList){
//			int pAmount = 0;
//			double wValue = 0;
//			double total = 0;
//			if(!StringUtils.isEmpty(opportunityDetails.getProposalAmount())) {
//				pAmount = Integer.parseInt(opportunityDetails.getProposalAmount());
//				OpportunityStages oppStages = opportunityStagesRepository
//						.getOpportunityStagesByOpportunityStagesId(opportunityDetails.getOppStage());
//				if (null != oppStages) {
//					if (0 != oppStages.getProbability()) {
//					wValue = ((oppStages.getProbability())/100);
//					}
//				}
//				total = pAmount * wValue;
//
//				count = count + total;
//			}
//		}
//		}
//		map.put("Weighted value", count);
//		return map;
//	}

	@Override
	public HashMap getActivityRecordByCustomerId(String customerId) {
		int count = 0;
		List<CustomerCallLink> customerCallLink = customerCallLinkRepo.getCallListByCustomerIdAndLiveInd(customerId);
		if (null != customerCallLink && !customerCallLink.isEmpty()) {
			count = customerCallLink.size();
		}

		List<CustomerEventLink> eventLink = customerEventRepo.getByCustomerIdAndLiveInd(customerId);
		if (null != eventLink && !eventLink.isEmpty()) {
			count += eventLink.size();
		}

		List<CustomerTaskLink> taskLink = customerTaskRepo.getTaskListByCustomerIdAndLiveInd(customerId);
		if (null != taskLink && !taskLink.isEmpty()) {
			count += taskLink.size();
		}
		HashMap map = new HashMap();
		map.put("count", count);

		return map;
	}

	@Override
	public HashMap getCustomerWonOppertunityCountByCustomerId(String customerId) {
		List<OpportunityDetails> opportunityList = opportunityDetailsRepository
				.getOpportunityDetailsCustomerIdAndWonIndAndLiveInd(customerId);
		HashMap map = new HashMap();
		map.put("CustomerWonOppertunityDetails", opportunityList.size());

		return map;
	}

	@Override
	public HashMap getCustomerWonOppertunityProposalValueCountByCustomerId(String customerId, String userId,
			String orgId) {
		HashMap map = new HashMap();
		int count = 0;
		int conversionAmount = 0;
		Customer customer = customerRepository.getCustomerByIdAndLiveInd(customerId);
		if (null != customer) {
			if (customer.getUserId().equalsIgnoreCase(userId)) {

				List<OpportunityDetails> opportunityList = opportunityDetailsRepository
						.getOpportunityDetailsCustomerIdAndWonIndAndLiveInd(customerId);
				if (null != opportunityList && !opportunityList.isEmpty()) {
					for (OpportunityDetails opportunityDetails : opportunityList) {
						if (!StringUtils.isEmpty(opportunityDetails.getProposalAmount())) {
							EmployeeDetails employeeDetails = employeeRepository
									.getEmployeeDetailsByEmployeeId(customer.getUserId());
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
							.getEmployeeDetailsByEmployeeId(customer.getUserId());
					if (null != employeeDetails.getCurrency()) {
						Currency userCurrency = currencyRepository.getByCurrencyId(employeeDetails.getCurrency());
						if (null != userCurrency) {
							System.out.println("in user trade currency : " + userCurrency.getCurrencyName());
							map.put("tradeCurrency", userCurrency.getCurrencyName());
						}
					}
				} else {
					System.out.println("in user 4");
					map.put("message", "Opportunity not available for this Customer");
				}

			} else {

				List<OpportunityDetails> opportunityList = opportunityDetailsRepository
						.getOpportunityDetailsCustomerIdAndWonIndAndLiveInd(customerId);
				if (null != opportunityList && !opportunityList.isEmpty()) {
					for (OpportunityDetails opportunityDetails : opportunityList) {
						if (!StringUtils.isEmpty(opportunityDetails.getProposalAmount())) {
							OrganizationDetails organizationDetails = organizationRepository
									.getOrganizationDetailsById(customer.getOrganizationId());
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
							.getOrganizationDetailsById(customer.getOrganizationId());
					if (null != organizationDetails.getTrade_currency()) {
						System.out.println("in org trade currency : " + organizationDetails.getTrade_currency());
						map.put("tradeCurrency", organizationDetails.getTrade_currency());
					}
				} else {
					System.out.println("in org 4");
					map.put("message", "Opportunity not available for this Customer");
				}
			}
		} else {
			map.put("message", "Customer not available");
		}

		map.put("WonPipeLineValue", count);

		return map;
	}

	@Override
	public HashMap getCustomerWonOppertunityWeightedValueCountByCustomerId(String customerId, String userId,
			String orgId) {
		HashMap map = new HashMap();
		double count = 0;
		int conversionAmount = 0;
		Customer customer = customerRepository.getCustomerByIdAndLiveInd(customerId);
		if (null != customer) {
			if (customer.getUserId().equalsIgnoreCase(userId)) {

				List<OpportunityDetails> opportunityList = opportunityDetailsRepository
						.getOpportunityDetailsCustomerIdAndWonIndAndLiveInd(customerId);
				if (null != opportunityList && !opportunityList.isEmpty()) {
					for (OpportunityDetails opportunityDetails : opportunityList) {
						int pAmount = 0;
						double wValue = 0;
						double total = 0;
						if (!StringUtils.isEmpty(opportunityDetails.getProposalAmount())) {
							EmployeeDetails employeeDetails = employeeRepository
									.getEmployeeDetailsByEmployeeId(customer.getUserId());
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
											OpportunityStages oppStages = opportunityStagesRepository
													.getOpportunityStagesByOpportunityStagesId(
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
												OpportunityStages oppStages = opportunityStagesRepository
														.getOpportunityStagesByOpportunityStagesId(
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
													OpportunityStages oppStages = opportunityStagesRepository
															.getOpportunityStagesByOpportunityStagesId(
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
							.getEmployeeDetailsByEmployeeId(customer.getUserId());
					if (null != employeeDetails.getCurrency()) {
						Currency userCurrency = currencyRepository.getByCurrencyId(employeeDetails.getCurrency());
						if (null != userCurrency) {
							System.out.println("in user trade currency : " + userCurrency.getCurrencyName());
							map.put("tradeCurrency", userCurrency.getCurrencyName());
						}
					}
				} else {
					System.out.println("in user 4");
					map.put("message", "Opportunity not available for this Customer");
				}

			} else {

				List<OpportunityDetails> opportunityList = opportunityDetailsRepository
						.getOpportunityDetailsCustomerIdAndWonIndAndLiveInd(customerId);
				if (null != opportunityList && !opportunityList.isEmpty()) {
					for (OpportunityDetails opportunityDetails : opportunityList) {
						int pAmount = 0;
						double wValue = 0;
						double total = 0;
						if (!StringUtils.isEmpty(opportunityDetails.getProposalAmount())) {
							OrganizationDetails organizationDetails = organizationRepository
									.getOrganizationDetailsById(customer.getOrganizationId());
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
										OpportunityStages oppStages = opportunityStagesRepository
												.getOpportunityStagesByOpportunityStagesId(
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
											OpportunityStages oppStages = opportunityStagesRepository
													.getOpportunityStagesByOpportunityStagesId(
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
												OpportunityStages oppStages = opportunityStagesRepository
														.getOpportunityStagesByOpportunityStagesId(
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
							.getOrganizationDetailsById(customer.getOrganizationId());
					if (null != organizationDetails.getTrade_currency()) {
						System.out.println("in org trade currency : " + organizationDetails.getTrade_currency());
						map.put("tradeCurrency", organizationDetails.getTrade_currency());
					}
				} else {
					System.out.println("in org 4");
					map.put("message", "Opportunity not available for this Customer");
				}
			}
		} else {
			map.put("message", "Customer not available");
		}

		map.put("weightedValue", count);

		return map;
	}

	@Override
	public List<CustomerReportMapper> getAllCustomerByOrgIdForReport(String orgId, String startDate, String endDate) {
		Date endDate1 = null;
		Date startDate1 = null;
		try {
			endDate1 = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			startDate1 = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<CustomerReportMapper> resultMapper = new ArrayList<>();
		List<Customer> customerList = customerRepository.getCustomerListByOrgIdWithDateRange(orgId, startDate1,
				endDate1);
		System.out.println("customerList===" + customerList.size());
		if (null != customerList && !customerList.isEmpty()) {
			resultMapper = customerList.stream().map(customer -> {
				CustomerReportMapper mapper = getCustomerDetailsByIdForReport(customer.getCustomerId());
				return mapper;
			}).collect(Collectors.toList());
		}
		return resultMapper;
	}

	public CustomerReportMapper getCustomerDetailsByIdForReport(String customerId) {

		Customer customer = customerRepository.getCustomerByIdAndLiveInd(customerId);
		System.out.println("customer object is..." + customer);

		CustomerReportMapper customerMapper = new CustomerReportMapper();

		if (null != customer) {

			if (customer.getSector() != null && customer.getSector().trim().length() > 0) {
				SectorDetails sector = sectorDetailsRepository.getSectorDetailsBySectorId(customer.getSector());
				System.out.println("get sectordetails by id returns........." + customer.getSector());
				System.out.println("sector object is......." + sector);
				if (sector != null) {
					customerMapper.setSector(sector.getSectorName());
					customerMapper.setSectorId(customer.getSector());
				} else {
					customerMapper.setSector("");
					customerMapper.setSectorId("");
				}
			}
			customerMapper.setCustomerId(customerId);
			customerMapper.setName(customer.getName());
			customerMapper.setUrl(customer.getUrl());
//			customerMapper.setNotes(customer.getNotes());
			customerMapper.setEmail(customer.getEmail());
//			customerMapper.setGroup(customer.getGroup());
			customerMapper.setVatNo(customer.getVatNo());
			customerMapper.setPhoneNumber(customer.getPhoneNumber());
			customerMapper.setCountryDialCode(customer.getCountryDialCode());
			customerMapper.setUserId(customer.getUserId());
			customerMapper.setOrganizationId(customer.getOrganizationId());
			customerMapper.setCreationDate(Utility.getISOFromDate(customer.getCreationDate()));
			customerMapper.setImageURL(customer.getImageURL());
//			customerMapper.setGst(customer.getGst());
			customerMapper.setSourceUserID(customer.getSourceUserId());
			customerMapper.setSourceUserName(employeeService.getEmployeeFullName(customer.getSourceUserId()));

			CustomerType customerType = customerTypeRepository.findByCustomerTypeId(customer.getType());
			if (null != customerType) {
				customerMapper.setType(customerType.getName());
			}

			Source source = sourceRepository.findBySourceId(customer.getSource());
			if (null != source) {
				customerMapper.setSource(source.getName());
			}

//			customerMapper.setCountry(customer.getCountry());
			// customerMapper.setSector(sector.getSectorName());
//			customerMapper.setZipcode(customer.getZipcode());

//			customerMapper.setDocumentId(customer.getDocumentId());
//			customerMapper.setCategory(customer.getCategory());
			customerMapper.setBusinessRegistration(customer.getBusinessRegistration());

			EmployeeDetails employeeDetails = employeeRepository.getEmployeeDetailsByEmployeeId(customer.getUserId());
			if (null != employeeDetails) {
				String middleName = " ";
				String lastName = "";

				if (!StringUtils.isEmpty(employeeDetails.getLastName())) {
					lastName = employeeDetails.getLastName();
				}
				if (employeeDetails.getMiddleName() != null && employeeDetails.getMiddleName().length() > 0) {

					middleName = employeeDetails.getMiddleName();
					customerMapper.setOwnerName(employeeDetails.getFirstName() + " " + middleName + " " + lastName);
					customerMapper.setOwnerImageId(employeeDetails.getImageId());
				} else {

					customerMapper.setOwnerName(employeeDetails.getFirstName() + " " + lastName);
					customerMapper.setOwnerImageId(employeeDetails.getImageId());
				}

			}
			EmployeeDetails employeeDetail = employeeRepository
					.getEmployeeDetailsByEmployeeId(customer.getAssignedTo());
			if (null != employeeDetail) {
				String middleName = " ";
				String lastName = "";

				if (!StringUtils.isEmpty(employeeDetail.getLastName())) {

					lastName = employeeDetail.getLastName();
				}

				if (employeeDetail.getMiddleName() != null && employeeDetail.getMiddleName().length() > 0) {

					middleName = employeeDetail.getMiddleName();
					customerMapper.setAssignedTo(employeeDetail.getFirstName() + " " + middleName + " " + lastName);

				} else {

					customerMapper.setAssignedTo(employeeDetail.getFirstName() + " " + lastName);

				}

			}

			CustomerRecruitUpdate dbCustomerRecruitUpdate = customerRecruitUpdateRepository
					.findByCustomerId(customer.getCustomerId());
			if (null != dbCustomerRecruitUpdate) {
				System.out.println("====" + dbCustomerRecruitUpdate.getId());
				customerMapper.setLastRequirementOn(Utility.getISOFromDate(dbCustomerRecruitUpdate.getUpdatedDate()));
			}

			List<OpportunityDetails> oppCustomersList = opportunityDetailsRepository
					.getOpportunityListByCustomerIdAndLiveInd(customerId);
			if (null != oppCustomersList && !oppCustomersList.isEmpty()) {
				double totalValue = oppCustomersList.stream().mapToDouble(li -> {
					double value = 0;
					if (!StringUtils.isEmpty(li.getProposalAmount())) {
						value = Double.parseDouble(li.getProposalAmount());
					}
					return value;
				}).sum();
				customerMapper.setTotalProposalValue(totalValue);
				customerMapper.setOppNo(oppCustomersList.size());
			}

			List<CustomerSkillLinkMapper> skillList = new ArrayList<CustomerSkillLinkMapper>();
			List<CustomerSkillLink> list = customerSkillLinkRepository.getByCustomerId(customerId);
			if (null != list && !list.isEmpty()) {
				for (CustomerSkillLink skillSetDetails : list) {
					CustomerSkillLink list2 = customerSkillLinkRepository
							.getById(skillSetDetails.getCustomerSkillLinkId());

					CustomerSkillLinkMapper mapper = new CustomerSkillLinkMapper();
					if (null != list2) {

						DefinationDetails definationDetails1 = definationRepository
								.findByDefinationId(list2.getSkillName());
						if (null != definationDetails1) {
							mapper.setSkillName(definationDetails1.getName());

						}

						mapper.setCustomerId(list2.getCustomerId());
						mapper.setCustomerSkillLinkId(list2.getCustomerSkillLinkId());
						skillList.add(mapper);
					}
				}
				customerMapper.setSkill(skillList);
			}

			List<CustomerAddressResponseMapper> addressList = new ArrayList<CustomerAddressResponseMapper>();
			List<CustomerAddressLink> customerAddressList = customerAddressLinkRepository
					.getAddressListByCustomerId(customerId);
			System.out.println("customer===" + customerId);
//		System.out.println("customerAddressLink.getAddressId()"+customerAddressList.get(0).getAddressId());
			/* fetch customer address & set to customer mapper */
			if (null != customerAddressList && !customerAddressList.isEmpty()) {

				for (CustomerAddressLink customerAddressLink : customerAddressList) {
					AddressDetails addressDetails = addressRepository
							.getAddressDetailsByAddressId(customerAddressLink.getAddressId());
					System.out.println("customerAddressLink.getAddressId()" + customerAddressLink.getAddressId());
					CustomerAddressResponseMapper addressMapper = new CustomerAddressResponseMapper();
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
								addressDetails.getCountry(), customer.getOrganizationId());
						if (null != country1) {
							addressMapper.setCountry_alpha2_code(country1.getCountry_alpha2_code());
							addressMapper.setCountry_alpha3_code(country1.getCountry_alpha3_code());
						}
						addressList.add(addressMapper);
					}
				}

				System.out.println("addressList.......... " + addressList);
			}
			customerMapper.setAddress(addressList);
//		Country country1 = countryRepository.getCountryDetailsByCountryName(customerMapper.getAddress().get(0).getCountry());
//		if(null!=country1) {
////		customerMapper.setCountry_alpha2_code(country1.getCountry_alpha2_code());
////		customerMapper.setCountry_alpha3_code(country1.getCountry_alpha3_code());
//		}
		}
		return customerMapper;

	}

	@Override
	public List<CustomerReportMapper> getAllCustomerByUserIdForReport(String userId, String startDate, String endDate) {
		Date endDate1 = null;
		Date startDate1 = null;
		try {
			endDate1 = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			startDate1 = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<CustomerReportMapper> resultMapper = new ArrayList<>();
		List<Customer> customerList = customerRepository.getCustomerListByUserIdWithDateRange(userId, startDate1,
				endDate1);
		if (null != customerList && !customerList.isEmpty()) {
			resultMapper = customerList.stream().map(customer -> {
				CustomerReportMapper mapper = getCustomerDetailsByIdForReport(customer.getCustomerId());
				return mapper;
			}).collect(Collectors.toList());
		}
		return resultMapper;
	}

	@Override
	public List<ContactReportMapper> getAllCustomerContactListByOrgIdForReport(String orgId, String startDate,
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
				.getInvesterContactByOrgIdAndContactTypeAndLiveIndWithDateRange(orgId, "Customer", startDate1,
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
	public List<ContactReportMapper> getAllCustomerContactListByUserIdForReport(String userId, String startDate,
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
				"Customer", startDate1, endDate1);
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
	public HashMap getCustomerCountByCountry(String country) {
		List<AddressDetails> addressDetails = addressRepository.getAddressDetailsByCountry(country);
		HashMap map = new HashMap();
		int count = 0;
		if (null != addressDetails && !addressDetails.isEmpty()) {
			for (AddressDetails address : addressDetails) {
				List<CustomerAddressLink> customerAddressLinkList = customerAddressLinkRepository
						.getByAddressId(address.getAddressId());
				if (null != customerAddressLinkList && !customerAddressLinkList.isEmpty()) {
					for (CustomerAddressLink customerAddressLink : customerAddressLinkList) {
						List<Customer> opportunityList = customerRepository
								.getCustomerByCustomerIdAndLiveInd(customerAddressLink.getCustomerId());
						if (null != opportunityList) {
							count = count + opportunityList.size();
						}
					}
				}
			}
		}

		map.put("customerCountByCountry", count);
		return map;
	}

	@Override
	public List<CustomerResponseMapper> getCustomerListByCountry(String country) {

		List<CustomerResponseMapper> resultList = new ArrayList<>();
		List<AddressDetails> addressDetails = addressRepository.getAddressDetailsByCountry(country);
		HashMap map = new HashMap();
		int count = 0;
		if (null != addressDetails && !addressDetails.isEmpty()) {
			for (AddressDetails address : addressDetails) {
				List<CustomerAddressLink> customerAddressLinkList = customerAddressLinkRepository
						.getByAddressId(address.getAddressId());
				if (null != customerAddressLinkList && !customerAddressLinkList.isEmpty()) {
					for (CustomerAddressLink customerAddressLink : customerAddressLinkList) {
						List<Customer> customerList = customerRepository
								.getCustomerByCustomerIdAndLiveInd(customerAddressLink.getCustomerId());
						if (null != customerList && !customerList.isEmpty()) {
							for (Customer customer : customerList) {
								CustomerResponseMapper mapper = getCustomerDetailsById(customer.getCustomerId());
								resultList.add(mapper);
							}

						}

					}
				}
			}
		}
		return resultList;

//        List<CustomerResponseMapper> resultList = new ArrayList<>();
//        List<AddressDetails> addressDetails = addressRepository.getAddressDetailsByCountry(country);
//        if (null != addressDetails && !addressDetails.isEmpty()) {
//            for (AddressDetails address : addressDetails) {
//                CustomerAddressLink customerAddressLink = customerAddressLinkRepository
//                        .findByAddressId(address.getAddressId());
//                if (null != customerAddressLink) {
//                    Customer customer = customerRepository
//                            .getCustomerByIdAndLiveInd(customerAddressLink.getCustomerId());
//                    if (null != customer) {
//                        CustomerResponseMapper mapper = getCustomerDetailsById(customer.getCustomerId());
//                        resultList.add(mapper);
//                    }
//                }
//            }
//        }
//        return resultList;
	}

	@Override
	public List<CampaignRespMapper> getCampaign(String customerId) {
		List<CampaignRespMapper> campaignRespMappers = new ArrayList<>();
		List<CustomerEventLink> eventLink = customerEventRepo.getByCustomerIdAndLiveInd(customerId);
		if (null != eventLink && !eventLink.isEmpty()) {
			eventLink.stream().map(event -> {
				CampaignRespMapper mapper = getCampaignByCustomerIdAndEventId(customerId, event.getEventId());
				if (null != mapper) {
					campaignRespMappers.add(mapper);
				}
				return mapper;
			}).collect(Collectors.toList());
		}
		return campaignRespMappers;
	}

	@Override
	public CampaignRespMapper createCampaign(CampaignReqMapper requestMapper) {
		Campaign campaign = campaignRepository.findByCustomerIdAndEventId(requestMapper.getCustomerId(),
				requestMapper.getEventId());
		if (null != campaign) {
//            campaign.setCustomerId(requestMapper.getCustomerId());
//            campaign.setEventId(requestMapper.getEventId());
			campaign.setBudgetValue(requestMapper.getBudgetValue());
//            campaign.setOrgId(requestMapper.getOrgId());
			campaign.setUpdatedBy(requestMapper.getUserId());
			campaign.setUpdationDate(new Date());
			Campaign campaign2 = campaignRepository.save(campaign);
			return getCampaignByCustomerIdAndEventId(campaign2.getCustomerId(), campaign2.getEventId());
		} else {
			Campaign campaign1 = new Campaign();
			campaign1.setCustomerId(requestMapper.getCustomerId());
			campaign1.setEventId(requestMapper.getEventId());
			campaign1.setBudgetValue(requestMapper.getBudgetValue());
			campaign1.setOrgId(requestMapper.getOrgId());
			campaign1.setUserId(requestMapper.getUserId());
			campaign1.setUpdatedBy(requestMapper.getUserId());
			campaign1.setCreationDate(new Date());
			campaign1.setUpdationDate(new Date());
			campaign1.setLiveInd(true);
			Campaign campaign2 = campaignRepository.save(campaign1);
			return getCampaignByCustomerIdAndEventId(campaign2.getCustomerId(), campaign2.getEventId());
		}
	}

	@Override
	public CampaignRespMapper getCampaignByCustomerIdAndEventId(String customerId, String eventId) {
		CampaignRespMapper campaignRespMapper = new CampaignRespMapper();

		EventDetails eventDetails = eventDetailsRepository.getEventDetailsById(eventId);
		if (null != eventDetails) {

			Campaign campaign = campaignRepository.findByCustomerIdAndEventId(customerId, eventId);
			if (null != campaign) {
				campaignRespMapper.setCampaignId(campaign.getCampaignId());
				campaignRespMapper.setBudgetValue(campaign.getBudgetValue());
				campaignRespMapper.setUpdateBy(employeeService.getEmployeeFullName(campaign.getUpdatedBy()));
				campaignRespMapper.setUpdateDate(Utility.getISOFromDate(campaign.getUpdationDate()));
			}
//
			if (eventDetails.getEvent_type() != null && eventDetails.getEvent_type().trim().length() > 0) {
				EventType type = eventTypeRepository.findByEventTypeId(eventDetails.getEvent_type());
				if (null != type) {
					System.out.println("id======" + eventDetails.getEvent_type());
					campaignRespMapper.setEventType(type.getEventType());
					campaignRespMapper.setEventTypeId(type.getEventTypeId());
				}
			}
			campaignRespMapper.setEventId(eventDetails.getEvent_id());
			campaignRespMapper.setStartTime(eventDetails.getStart_time());
			campaignRespMapper.setEndTime(eventDetails.getEnd_time());
			campaignRespMapper.setStatus(eventDetails.getEvent_status());
			campaignRespMapper.setEventDescription(eventDetails.getEvent_description());
			campaignRespMapper.setTimeZone(eventDetails.getTime_zone());
			campaignRespMapper.setEventSubject(eventDetails.getSubject());
			campaignRespMapper.setCompletionInd(eventDetails.isComplitionInd());
			campaignRespMapper.setRating(eventDetails.getRating());
			campaignRespMapper.setStartDate(Utility.getISOFromDate(eventDetails.getStart_date()));
			campaignRespMapper.setEndDate(Utility.getISOFromDate(eventDetails.getEnd_date()));
			campaignRespMapper.setEventDescription(eventDetails.getEvent_description());
			campaignRespMapper.setUserId(eventDetails.getUser_id());
			campaignRespMapper.setOrganizationId(eventDetails.getOrganization_id());
			campaignRespMapper.setWoner(employeeService.getEmployeeFullName(eventDetails.getUser_id()));
			campaignRespMapper.setAssignedTo(employeeService.getEmployeeFullName(eventDetails.getAssignedTo()));
			campaignRespMapper.setCreationDate(Utility.getISOFromDate(eventDetails.getCreation_date()));

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
			campaignRespMapper.setIncluded(empList);
		}
		return campaignRespMapper;
	}

	@Override
	public List<CustomerResponseMapper> getCustomerDetailsByUnderUserId(String userId, int pageNo, int pageSize) {
		Set<String> userIds = employeeRepository.getEmployeeDetailsByReportingManagerId(userId).stream()
				.map(EmployeeDetails::getUserId).collect(Collectors.toSet());
		userIds.add(userId);

		List<String> userIdss = new ArrayList<>(userIds);

		List<CustomerResponseMapper> result = new ArrayList<>();
		Pageable paging = null;
//		 if (filter.equalsIgnoreCase("creationdate")) {
		paging = PageRequest.of(pageNo, pageSize, Sort.by("creationDate").descending());

//         } else if (filter.equalsIgnoreCase("ascending")) {
//             paging = PageRequest.of(pageNo, pageSize, Sort.by("name"));//
//         } else if (filter.equalsIgnoreCase("descending")) {
//             paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "name"));
//         }
		Page<Customer> customerList = customerRepository.getTeamCustomerDetailsListByUserId(userIdss, paging);
		if (null != customerList && !customerList.isEmpty()) {
			result = customerList.getContent().stream().map(customerDetails -> {
				CustomerResponseMapper customerDetailsMapper = getCustomerDetailsById(customerDetails.getCustomerId());
				if (null != customerDetailsMapper.getCustomerId()) {
					customerDetailsMapper.setPageCount(customerList.getTotalPages());
					customerDetailsMapper.setDataCount(customerList.getSize());
					customerDetailsMapper.setListCount(customerList.getTotalElements());
					return customerDetailsMapper;
				}
				return null;
			}).collect(Collectors.toList());
		}

		return result;
	}

	@Override
	public HashMap getCountCustomerDetailsByUnderUserId(String userId) {
		HashMap map = new HashMap();

		Set<String> userIds = employeeRepository.getEmployeeDetailsByReportingManagerId(userId).stream()
				.map(EmployeeDetails::getUserId).collect(Collectors.toSet());
		userIds.add(userId);

		List<String> userIdss = new ArrayList<>(userIds);

		List<Customer> customerPage = customerRepository.getTeamCustomerListByUserIds(userIdss);
		map.put("prospectTeam", customerPage.size());
		return map;
	}

	@Override
	public List<ContactViewMapperForDropDown> getAllCustomerContactListByCustomerIdForDropDown(String customerId) {
		List<ContactViewMapperForDropDown> resultList = new ArrayList<>();
		List<CustomerContactLink> customerContactLinkList = customerContactLinkRepository
				.getContactIdByCustomerId(customerId);
		if (customerContactLinkList != null && !customerContactLinkList.isEmpty()) {
			return customerContactLinkList.stream().map(customerContactLink -> {
				ContactDetails contact = contactRepository.getContactDetailsById(customerContactLink.getContactId());

				ContactViewMapperForDropDown contactMapper = new ContactViewMapperForDropDown();

				if (null != contact) {
					contactMapper.setContactId(contact.getContactId());
					contactMapper.setCustomerId(contact.getCustomerId());
					contactMapper.setFullName(Utility.FullName(contact.getFirst_name(), contact.getMiddle_name(),
							contact.getLast_name()));
				}
				return contactMapper;
			}).collect(Collectors.toList());

		}

		return resultList;
	}

	@Override
	public List<OpportunityViewMapper> getWonOpportunityListByCustomerId(String customerId) {
		List<OpportunityDetails> opportunityList = opportunityDetailsRepository
				.getOpportunityDetailsCustomerIdAndWonIndAndLiveInd(customerId);
		List<OpportunityViewMapper> resultList = new ArrayList<>();
		opportunityList.stream().map(opp -> {
			OpportunityViewMapper opportunityMapper = opportunityService.getOpportunityRelatedDetails(opp);
			resultList.add(opportunityMapper);
			return resultList;
		}).collect(Collectors.toList());

		return resultList;
	}

	@Override
	public List<CustomerLobLinkMapper> getDistributorLObListByDistributorId(String customerId, String orgId) {
		List<CustomerLobLink> existingLinks = customerLobLinkRepository.findByCustomerId(customerId);

		List<LobDetails> allLobs = lobDetailsRepository.findByOrgIdAndLiveInd(orgId, true);

		return allLobs.stream().map(lob -> {
			CustomerLobLinkMapper mapper = new CustomerLobLinkMapper();
			mapper.setLobDetailsId(lob.getLobDetailsId());
			mapper.setName(lob.getName());

			existingLinks.stream().filter(link -> link.getLobDetailsId().equals(lob.getLobDetailsId())).findFirst()
					.ifPresent(link -> {
						mapper.setApplicable(link.isApplicable());
						mapper.setPotential(link.getPotential());
						if (!StringUtils.isEmpty(link.getCurrency())) {
							Optional<Currency> cud = currencyRepository
									.getCurrencyDetailsByCurrencyId(link.getCurrency());
							cud.ifPresent(currency -> mapper.setCurrency(link.getCurrency()));
						}
					});

			return mapper;
		}).collect(Collectors.toList());
	}

	@Override
	public CustomerLobLinkMapper createOrUpdateLOb(CustomerLobLinkMapper customerLobLinkMapper) {
		CustomerLobLink existing = customerLobLinkRepository.findByLobDetailsIdAndCustomerId(
				customerLobLinkMapper.getLobDetailsId(), customerLobLinkMapper.getCustomeId());

		CustomerLobLink link;
		if (null != existing) {
			link = existing;
			link.setLobDetailsId(customerLobLinkMapper.getLobDetailsId());
			link.setApplicable(customerLobLinkMapper.isApplicable());
			link.setPotential(customerLobLinkMapper.getPotential());
			link.setCurrency(customerLobLinkMapper.getCurrency());
			link.setCustomerId(customerLobLinkMapper.getCustomeId());
		} else {
			link = new CustomerLobLink();
			link.setLobDetailsId(customerLobLinkMapper.getLobDetailsId());
			link.setApplicable(customerLobLinkMapper.isApplicable());
			link.setPotential(customerLobLinkMapper.getPotential());
			link.setCurrency(customerLobLinkMapper.getCurrency());
			link.setCustomerId(customerLobLinkMapper.getCustomeId());
			link.setCreationDate(new Date());
		}

		customerLobLinkRepository.save(link);

		return getCustomerLobLink(link);
	}

	private CustomerLobLinkMapper getCustomerLobLink(CustomerLobLink link) {
		CustomerLobLinkMapper customerLobLinkMapper = new CustomerLobLinkMapper();
		customerLobLinkMapper.setLobDetailsId(link.getLobDetailsId());
		customerLobLinkMapper.setApplicable(link.isApplicable());
		customerLobLinkMapper.setPotential(link.getPotential());
		if (!StringUtils.isEmpty(link.getLobDetailsId())) {
			LobDetails lob = lobDetailsRepository.findByLobDetailsId(link.getLobDetailsId());
			customerLobLinkMapper.setName(lob.getName());
		}
		if (!StringUtils.isEmpty(link.getCurrency())) {
			Optional<Currency> cud = currencyRepository.getCurrencyDetailsByCurrencyId(link.getCurrency());
			cud.ifPresent(currency -> customerLobLinkMapper.setCurrency(link.getCurrency()));
		}
		customerLobLinkMapper.setCustomeId(link.getCustomerId());
		return customerLobLinkMapper;
	}

	@Override
	public List<CustomerResponseMapper> getCustomerDetailsByNameByOrgLevel(String name, String orgId) {
		List<Customer> list = customerRepository.findByOrgIdandLiveInd(orgId);
		List<Customer> filterList = list.parallelStream().filter(detail -> {
			return detail.getName() != null && Utility.containsIgnoreCase(detail.getName(), name.trim());
		}).collect(Collectors.toList());
		List<CustomerResponseMapper> mapperList = new ArrayList<CustomerResponseMapper>();
		if (null != filterList && !filterList.isEmpty()) {

			mapperList = filterList.parallelStream().map(li -> getCustomerDetailsById(li.getCustomerId()))
					.collect(Collectors.toList());

		}

		return mapperList;

	}

	@Override
	public List<CustomerResponseMapper> getCustomerBySectorInOrgLevel(String name, String orgId) {
		List<CustomerResponseMapper> mapperList = new ArrayList<>();
		SectorDetails sectorDetails = sectorDetailsRepository.getSectorDetailsBySectorNameAndOrgId(name, orgId);
		if (null != sectorDetails) {
			List<Customer> detailsList = customerRepository
					.findBySectorAndLiveIndAndOrganizationId(sectorDetails.getSectorId(), true, orgId);
			if (null != detailsList && !detailsList.isEmpty()) {
				mapperList = detailsList.stream().map(li -> getCustomerDetailsById(li.getCustomerId()))
						.collect(Collectors.toList());
			}
		}
		return mapperList;
	}

	@Override
	public List<CustomerResponseMapper> getCustomerBySourceInOrgLevel(String name, String orgId) {
		List<CustomerResponseMapper> mapperList = new ArrayList<>();
		Source source = sourceRepository.findByNameAndOrgId(name, orgId);
		if (null != source) {
			List<Customer> detailsList = customerRepository
					.findBySourceAndLiveIndAndOrganizationId(source.getSourceId(), true, orgId);
			if (null != detailsList && !detailsList.isEmpty()) {
				mapperList = detailsList.stream().map(li -> getCustomerDetailsById(li.getCustomerId()))
						.collect(Collectors.toList());
			}
		}
		return mapperList;
	}

	@Override
	public List<CustomerResponseMapper> getCustomerByOwnerNameInOrgLevel(String name, String orgId) {
		List<CustomerResponseMapper> mapperList = new ArrayList<>();

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
					employeeDetails -> customerRepository.findByUserIdandLiveInd(employeeDetails.getUserId()).stream())
					.map(customer -> getCustomerDetailsById(customer.getCustomerId())).collect(Collectors.toList());
		}

		return mapperList;
	}

	@Override
	public List<CustomerResponseMapper> getCustomerDetailsByNameForTeam(String name, String userId) {
		Set<String> userIds = employeeRepository.getEmployeeDetailsByReportingManagerId(userId).stream()
				.map(EmployeeDetails::getUserId).collect(Collectors.toSet());
		userIds.add(userId);

		List<String> userIdss = new ArrayList<>(userIds);

		List<Customer> list = customerRepository.getTeamCustomerListByUserIds(userIdss);
		List<Customer> filterList = list.parallelStream().filter(detail -> {
			return detail.getName() != null && Utility.containsIgnoreCase(detail.getName(), name.trim());
		}).collect(Collectors.toList());
		List<CustomerResponseMapper> mapperList = new ArrayList<CustomerResponseMapper>();
		if (null != filterList && !filterList.isEmpty()) {

			mapperList = filterList.parallelStream().map(li -> getCustomerDetailsById(li.getCustomerId()))
					.collect(Collectors.toList());

		}

		return mapperList;

	}

	@Override
	public List<CustomerResponseMapper> getCustomerBySectorForTeam(String name, String userId, String orgId) {
		Set<String> userIds = employeeRepository.getEmployeeDetailsByReportingManagerId(userId).stream()
				.map(EmployeeDetails::getUserId).collect(Collectors.toSet());
		userIds.add(userId);

		List<String> userIdss = new ArrayList<>(userIds);
		List<CustomerResponseMapper> mapperList = new ArrayList<>();
		SectorDetails sectorDetails = sectorDetailsRepository.getSectorDetailsBySectorNameAndOrgId(name, orgId);
		if (null != sectorDetails) {
			List<Customer> list = customerRepository.getTeamCustomerListByUserIdsAndSector(userIdss,
					sectorDetails.getSectorId());
			List<Customer> filterList = list.parallelStream().filter(detail -> {
				return detail.getName() != null && Utility.containsIgnoreCase(detail.getName(), name.trim());
			}).collect(Collectors.toList());
			if (null != filterList && !filterList.isEmpty()) {

				mapperList = filterList.parallelStream().map(li -> getCustomerDetailsById(li.getCustomerId()))
						.collect(Collectors.toList());

			}
		}
		return mapperList;

	}

	@Override
	public List<CustomerResponseMapper> getCustomerBySourceForTeam(String name, String userId, String orgId) {
		Set<String> userIds = employeeRepository.getEmployeeDetailsByReportingManagerId(userId).stream()
				.map(EmployeeDetails::getUserId).collect(Collectors.toSet());
		userIds.add(userId);

		List<String> userIdss = new ArrayList<>(userIds);
		List<CustomerResponseMapper> mapperList = new ArrayList<>();
		Source source = sourceRepository.findByNameAndOrgId(name, orgId);
		if (null != source) {
			List<Customer> list = customerRepository.getTeamCustomerListByUserIdsAndSource(userIdss,
					source.getSourceId());
			List<Customer> filterList = list.parallelStream().filter(detail -> {
				return detail.getName() != null && Utility.containsIgnoreCase(detail.getName(), name.trim());
			}).collect(Collectors.toList());
			if (null != filterList && !filterList.isEmpty()) {

				mapperList = filterList.parallelStream().map(li -> getCustomerDetailsById(li.getCustomerId()))
						.collect(Collectors.toList());

			}
		}
		return mapperList;

	}

	@Override
	public List<CustomerResponseMapper> getCustomerByOwnerNameForTeam(String name, String userId, String orgId) {
		List<CustomerResponseMapper> mapperList = new ArrayList<>();

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
					employeeDetails -> customerRepository.findByUserIdandLiveInd(employeeDetails.getUserId()).stream())
					.map(customer -> getCustomerDetailsById(customer.getCustomerId())).collect(Collectors.toList());
		}
		return mapperList;
	}

	@Override
	public List<CustomerResponseMapper> getCustomerDetailsByNameByUserId(String name, String userId) {
		List<Customer> list = customerRepository.findByUserIdWithAssignedToandLiveInd(userId);
		List<Customer> filterList = list.parallelStream().filter(detail -> {
			return detail.getName() != null && Utility.containsIgnoreCase(detail.getName(), name.trim());
		}).collect(Collectors.toList());
		List<CustomerResponseMapper> mapperList = new ArrayList<CustomerResponseMapper>();
		if (null != filterList && !filterList.isEmpty()) {

			mapperList = filterList.parallelStream().map(li -> getCustomerDetailsById(li.getCustomerId()))
					.collect(Collectors.toList());

		}

		return mapperList;

	}

	@Override
	public List<CustomerResponseMapper> getCustomerBySectorAndByUserId(String name, String userId, String orgId) {
		List<CustomerResponseMapper> mapperList = new ArrayList<CustomerResponseMapper>();

		SectorDetails sectorDetails = sectorDetailsRepository.getSectorDetailsBySectorNameAndOrgId(name, orgId);
		if (null != sectorDetails) {
			List<Customer> list = customerRepository.findBySectorAndLiveIndAndUserId(sectorDetails.getSectorId(),
					true,userId);

			List<Customer> filterList = list.parallelStream().filter(detail -> {
				return detail.getName() != null && Utility.containsIgnoreCase(detail.getName(), name.trim());
			}).collect(Collectors.toList());
			if (null != filterList && !filterList.isEmpty()) {

				mapperList = filterList.parallelStream().map(li -> getCustomerDetailsById(li.getCustomerId()))
						.collect(Collectors.toList());

			}
		}
		return mapperList;

	}

	@Override
	public List<CustomerResponseMapper> getCustomerBySourceAndByUserId(String name, String userId, String orgId) {
		List<CustomerResponseMapper> mapperList = new ArrayList<CustomerResponseMapper>();

		Source source = sourceRepository.findByNameAndOrgId(name, orgId);
		if (null != source) {
			List<Customer> list = customerRepository.findBySourceAndLiveIndAndUserId(source.getSourceId(),true,userId);

			List<Customer> filterList = list.parallelStream().filter(detail -> {
				return detail.getName() != null && Utility.containsIgnoreCase(detail.getName(), name.trim());
			}).collect(Collectors.toList());
			if (null != filterList && !filterList.isEmpty()) {

				mapperList = filterList.parallelStream().map(li -> getCustomerDetailsById(li.getCustomerId()))
						.collect(Collectors.toList());

			}
		}
		return mapperList;

	}

	@Override
	public List<CustomerResponseMapper> getCustomerByOwnerNameAndByUserId(String name, String userId, String orgId) {
		List<CustomerResponseMapper> mapperList = new ArrayList<>();

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
					employeeDetails -> customerRepository.findByUserIdandLiveInd(employeeDetails.getUserId()).stream())
					.map(customer -> getCustomerDetailsById(customer.getCustomerId())).collect(Collectors.toList());
			
		}
		return mapperList;
	}

}
//initiativeDetailsId

/*
 * @Override public List<CustomerViewMapper> getCustomerListByUserId(String
 * userId) {
 *
 * List<CustomerViewMapper> resultList = new ArrayList<CustomerViewMapper>();
 *
 * List<Customer> customerList = customerRepository.getCustomerByUserId(userId);
 * if (null != customerList && !customerList.isEmpty()) { for (Customer customer
 * : customerList) { CustomerViewMapper customerMapper =
 * getCustomerDetailsById(customer.getCustomerId());
 * resultList.add(customerMapper);
 *
 * String middleName = " "; String lastName = "";
 *
 * if (!StringUtils.isEmpty(customer.getName())) {
 *
 * lastName = customer.getName(); }
 *
 * if (customer.getName() != null && customer.getName().length() > 0) {
 *
 * middleName = customer.getName(); customerMapper.setName(customer.getName() +
 * " " + middleName + " " + lastName); } else {
 *
 * customerMapper.setName(customer.getName() + " " + lastName); }
 *
 * }
 *
 * } return resultList; }
 */
// }
