package com.app.employeePortal.event.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.app.employeePortal.Opportunity.entity.OpportunityNotesLink;
import com.app.employeePortal.Opportunity.repository.OpportunityDetailsRepository;
import com.app.employeePortal.Opportunity.repository.OpportunityNotesLinkRepository;
import com.app.employeePortal.address.entity.AddressDetails;
import com.app.employeePortal.address.entity.AddressInfo;
import com.app.employeePortal.address.mapper.AddressMapper;
import com.app.employeePortal.address.repository.AddressInfoRepository;
import com.app.employeePortal.address.repository.AddressRepository;
import com.app.employeePortal.address.service.AddressService;
import com.app.employeePortal.candidate.entity.CandidateDetails;
import com.app.employeePortal.candidate.repository.CandidateDetailsRepository;
import com.app.employeePortal.contact.entity.ContactDetails;
import com.app.employeePortal.contact.entity.ContactNotesLink;
import com.app.employeePortal.contact.repository.ContactNotesLinkRepository;
import com.app.employeePortal.contact.repository.ContactRepository;
import com.app.employeePortal.customer.entity.Customer;
import com.app.employeePortal.customer.mapper.CustomerResponseMapper;
import com.app.employeePortal.customer.repository.CustomerRepository;
import com.app.employeePortal.employee.entity.EmployeeDetails;
import com.app.employeePortal.employee.entity.Notes;
import com.app.employeePortal.employee.mapper.EmployeeViewMapper;
import com.app.employeePortal.employee.mapper.NotesMapper;
import com.app.employeePortal.employee.repository.EmployeeRepository;
import com.app.employeePortal.employee.repository.NotesRepository;
import com.app.employeePortal.employee.service.EmployeeService;
import com.app.employeePortal.event.entity.CandidateEventLink;
import com.app.employeePortal.event.entity.ContactEventLink;
import com.app.employeePortal.event.entity.CustomerEventLink;
import com.app.employeePortal.event.entity.EmployeeEventLink;
import com.app.employeePortal.event.entity.EventAddressLink;
import com.app.employeePortal.event.entity.EventDetails;
import com.app.employeePortal.event.entity.EventInfo;
import com.app.employeePortal.event.entity.EventNotesLink;
import com.app.employeePortal.event.entity.EventType;
import com.app.employeePortal.event.entity.EventTypeDelete;
import com.app.employeePortal.event.entity.InvestorEventLink;
import com.app.employeePortal.event.entity.InvestorLeadsEventLink;
import com.app.employeePortal.event.entity.LeadsEventLink;
import com.app.employeePortal.event.mapper.EventMapper;
import com.app.employeePortal.event.mapper.EventViewMapper;
import com.app.employeePortal.event.repository.CandidateEventLinkRepository;
import com.app.employeePortal.event.repository.ContactEventRepo;
import com.app.employeePortal.event.repository.CustomerEventRepo;
import com.app.employeePortal.event.repository.EmployeeEventRepository;
import com.app.employeePortal.event.repository.EventAddressRepository;
import com.app.employeePortal.event.repository.EventDetailsRepository;
import com.app.employeePortal.event.repository.EventInfoRepository;
import com.app.employeePortal.event.repository.EventNotesLinkRepository;
import com.app.employeePortal.event.repository.EventTypeDeleteRepository;
import com.app.employeePortal.event.repository.EventTypeRepository;
import com.app.employeePortal.event.repository.InvestorEventRepo;
import com.app.employeePortal.event.repository.InvestorLeadsEventRepo;
import com.app.employeePortal.event.repository.LeadsEventRepo;
import com.app.employeePortal.leads.mapper.ActivityMapper;
import com.app.employeePortal.notification.entity.NotificationDetails;
import com.app.employeePortal.notification.entity.Notificationparam;
import com.app.employeePortal.notification.repository.NotificationRepository;
import com.app.employeePortal.notification.service.NotificationService;
import com.app.employeePortal.task.entity.LastActivityLog;
import com.app.employeePortal.task.repository.LastActivityLogRepository;
import com.app.employeePortal.util.Utility;

import freemarker.template.TemplateException;

@Service
@Transactional
public class EventServiceImpl implements EventService {

	@Autowired
	EventInfoRepository eventInfoRepository;

	@Autowired
	EventDetailsRepository eventDetailsRepository;

	@Autowired
	EmployeeEventRepository employeeEventRepository;

	@Autowired
	EmployeeService employeeService;

	@Autowired
	AddressInfoRepository addressInfoRepository;

	@Autowired
	AddressRepository addressDetailsRepository;

	@Autowired
	EventAddressRepository eventAddressRepository;

	@Autowired
	AddressService addressService;
	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	NotificationRepository notificationRepository;
	@Autowired
	CandidateDetailsRepository candidateDetailsRepository;
	@Autowired
	EventTypeRepository eventTypeRepository;
	@Autowired
	CandidateEventLinkRepository candidateEventLinkRepository;
	@Autowired
	EventTypeDeleteRepository eventTypeDeleteRepository;

	@Autowired
	LeadsEventRepo leadsEventRepo;
	@Autowired
	InvestorLeadsEventRepo investorLeadsEventRepo;
	@Autowired
	ContactRepository contactRepository;
	@Autowired
	OpportunityDetailsRepository opportunityDetailsRepository;
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	CustomerEventRepo customerEventRepo;
	@Autowired
	ContactEventRepo contactEventRepo;
	@Autowired
	NotesRepository notesRepository;
	@Autowired
	OpportunityNotesLinkRepository opportunityNotesLinkRepository;
	@Autowired
	ContactNotesLinkRepository contactNotesLinkRepository;
	@Autowired
	EventNotesLinkRepository eventNotesLinkRepository;
	@Autowired
	InvestorEventRepo investorEventRepo;
	@Autowired
	LastActivityLogRepository lastActivityLogRepository;
	private String[] event_headings = { "Name" };

	@Value("${companyName}")
	private String companyName;

	@Autowired
	NotificationService notificationService;

	@Override
	public EventViewMapper saveEventProcess(EventMapper eventMapper) throws IOException, TemplateException {

		EventInfo eventInfo = new EventInfo();
		eventInfo.setCreation_date(new Date());

		EventInfo info = eventInfoRepository.save(eventInfo);
		String eventId = info.getEvent_id();

		EventViewMapper resultMapper = null;
		if (null != eventId) {
			/* insert to event details table */

			EventDetails eventDetails = new EventDetails();

			eventDetails.setEvent_id(eventId);
			try {
				eventDetails.setStart_date(Utility.getDateFromISOString(eventMapper.getStartDate()));
				eventDetails.setEnd_date(Utility.getDateFromISOString(eventMapper.getEndDate()));

			} catch (Exception e) {
				e.printStackTrace();
			}
			eventDetails.setStart_time(eventMapper.getStartTime());
			eventDetails.setEnd_time(eventMapper.getEndTime());
			eventDetails.setTime_zone(eventMapper.getTimeZone());
			eventDetails.setEvent_status(eventMapper.getStatus());
			eventDetails.setEvent_type(eventMapper.getEventTypeId());
			eventDetails.setSubject(eventMapper.getEventSubject());
			eventDetails.setEvent_description(eventMapper.getEventDescription());
			eventDetails.setCreation_date(new Date());
			eventDetails.setUser_id(eventMapper.getUserId());
			eventDetails.setOrganization_id(eventMapper.getOrganizationId());
			eventDetails.setLive_ind(true);
			eventDetails.setComplitionInd(false);
			eventDetails.setRating(0);
			eventDetails.setAssignedTo(eventMapper.getAssignedTo());
			eventDetails.setContact(eventMapper.getContact());
			eventDetails.setOppertunity(eventMapper.getOppertunity());
			eventDetails.setCustomer(eventMapper.getCustomer());
			eventDetails.setAssignedBy(eventMapper.getUserId());
			eventDetailsRepository.save(eventDetails);

			/* insert to CandidateEventLink table */

			if (!StringUtils.isEmpty(eventMapper.getCandidateId())) {

				CandidateEventLink candidateEventLink = new CandidateEventLink();
				candidateEventLink.setEventId(eventId);
				candidateEventLink.setCandidateId(eventMapper.getCandidateId());
				candidateEventLink.setCreationDate(new Date());
				candidateEventLink.setLiveInd(true);
				candidateEventLinkRepository.save(candidateEventLink);
				updateActivityEventLog("candidate", eventId, eventMapper.getCandidateId(), eventMapper);
			}

			// Event leads link

			if (!StringUtils.isEmpty(eventMapper.getLeadsId())) {
				LeadsEventLink leadsEventLink = new LeadsEventLink();
				leadsEventLink.setEventId(eventId);
				leadsEventLink.setLeadsId(eventMapper.getLeadsId());
				leadsEventLink.setCreationDate(new Date());
				leadsEventLink.setLiveInd(true);
				leadsEventRepo.save(leadsEventLink);
				updateActivityEventLog("leads", eventId, eventMapper.getCandidateId(), eventMapper);
			}

			// Event InvestorLeads link
			if (!StringUtils.isEmpty(eventMapper.getInvestorLeadsId())) {
				InvestorLeadsEventLink investorLeadsEventLink = new InvestorLeadsEventLink();
				investorLeadsEventLink.setEventId(eventId);
				investorLeadsEventLink.setInvestorLeadsId(eventMapper.getInvestorLeadsId());
				investorLeadsEventLink.setCreationDate(new Date());
				investorLeadsEventLink.setLiveInd(true);
				investorLeadsEventRepo.save(investorLeadsEventLink);
				updateActivityEventLog("investorLeads", eventId, eventMapper.getInvestorLeadsId(), eventMapper);
			}

			// Event Investor link
			if (!StringUtils.isEmpty(eventMapper.getInvestorId())) {
				InvestorEventLink investorEventLink = new InvestorEventLink();
				investorEventLink.setEventId(eventId);
				investorEventLink.setInvestorId(eventMapper.getInvestorId());
				investorEventLink.setCreationDate(new Date());
				investorEventLink.setLiveInd(true);
				investorEventRepo.save(investorEventLink);
				updateActivityEventLog("investor", eventId, eventMapper.getInvestorId(), eventMapper);
			}
			// Event customer link

			if (!StringUtils.isEmpty(eventMapper.getCustomer())) {
				CustomerEventLink customerEventLink = new CustomerEventLink();
				customerEventLink.setEventId(eventId);
				customerEventLink.setCustomerId(eventMapper.getCustomer());
				customerEventLink.setCreationDate(new Date());
				customerEventLink.setLiveInd(true);
				customerEventRepo.save(customerEventLink);
				updateActivityEventLog("customer", eventId, eventMapper.getCustomer(), eventMapper);
			}

			// Event contact link

			if (!StringUtils.isEmpty(eventMapper.getContact())) {
				ContactEventLink contactEventLink = new ContactEventLink();
				contactEventLink.setEventId(eventId);
				contactEventLink.setContactId(eventMapper.getContact());
				contactEventLink.setCreationDate(new Date());
				contactEventLink.setLiveInd(true);
				contactEventRepo.save(contactEventLink);
				updateActivityEventLog("contact", eventId, eventMapper.getContact(), eventMapper);
			}

			/* insert into todo table */
			/*
			 * if (null != eventId) { ToDoDetails toDoDetails = new ToDoDetails();
			 * toDoDetails.setEventId(eventId); toDoDetails.setComplitionInd(false);
			 * toDoDetails.setCreationDate(new Date());
			 * toDoDetails.setUserId(eventMapper.getUserId());
			 * toDoDetails.setRating("Not Given"); toDoDetailsRepository.save(toDoDetails);
			 * }
			 */

			/* insert to event address link table */

			List<AddressMapper> addressMapperList = eventMapper.getAddress();
			String addressId = null;

			if (null != addressMapperList && !addressMapperList.isEmpty()) {
				for (AddressMapper addressMapper : addressMapperList) {

					AddressInfo addressInfo = new AddressInfo();
					addressInfo.setCreationDate(new Date());
					addressInfo.setCreatorId(eventMapper.getUserId());

					AddressInfo info1 = addressInfoRepository.save(addressInfo);
					addressId = info1.getId();
					if (null != addressId) {

						AddressDetails addressDetails = new AddressDetails();
						addressDetails.setAddressId(addressId);
						addressDetails.setAddressLine1(addressMapper.getAddress1());
						addressDetails.setAddressLine2(addressMapper.getAddress2());
						addressDetails.setAddressType(addressMapper.getAddressType());
						addressDetails.setCountry(addressMapper.getCountry());
						addressDetails.setCreationDate(new Date());
						addressDetails.setCreatorId(eventMapper.getUserId());
						addressDetails.setStreet(addressMapper.getStreet());
						addressDetails.setCity(addressMapper.getCity());
						addressDetails.setPostalCode(addressMapper.getPostalCode());
						addressDetails.setTown(addressMapper.getTown());
						addressDetails.setState(addressMapper.getState());
						addressDetails.setLatitude(addressMapper.getLatitude());
						addressDetails.setLongitude(addressMapper.getLongitude());
						addressDetails.setLiveInd(true);
						addressDetails.setHouseNo(addressMapper.getHouseNo());
						addressDetailsRepository.save(addressDetails);
					}
					/* insert to event address link table */

					EventAddressLink eventAddressLink = new EventAddressLink();
					eventAddressLink.setAddress_id(addressId);
					eventAddressLink.setCreation_date(new Date());
					eventAddressLink.setEvent_id(eventId);
					eventAddressLink.setLive_ind(true);
					eventAddressRepository.save(eventAddressLink);

				}

			}

			List<String> empList = eventMapper.getIncluded();

			empList.add(eventMapper.getUserId());
			if (!eventMapper.getUserId().equals(eventMapper.getAssignedTo())) {
				empList.add(eventMapper.getAssignedTo());
			}
			if (null != empList && !empList.isEmpty()) {
				empList.forEach(employeeId -> {

					/* insert to employee event link table */

					EmployeeEventLink employeeEventLink = new EmployeeEventLink();
					employeeEventLink.setEvent_id(eventId);
					employeeEventLink.setEmployee_id(employeeId);
					employeeEventLink.setCreation_date(new Date());
					employeeEventLink.setLive_ind(true);
					employeeEventRepository.save(employeeEventLink);
				});
			}
			Notes notes = new Notes();
			notes.setCreation_date(new Date());
			notes.setNotes(eventMapper.getEventDescription());
			notes.setLiveInd(true);
			notes.setUserId(eventMapper.getUserId());
			Notes note = notesRepository.save(notes);

			String notesId = note.getNotes_id();
			/* insert to EventNoteLink table */

			EventNotesLink eventNote = new EventNotesLink();
			eventNote.setEventId(eventId);
			eventNote.setNotesId(notesId);
			eventNote.setCreationDate(new Date());
			eventNotesLinkRepository.save(eventNote);

			/* insert to Notification Table */
			String msg = "";
			if (eventMapper.getCustomer() != null && !eventMapper.getCustomer().isEmpty()
					&& !eventMapper.getContact().isEmpty() && eventMapper.getContact() != null) {
				Customer customer = customerRepository.getcustomerDetailsById(eventMapper.getCustomer());
				ContactDetails contact = contactRepository.getcontactDetailsById(eventMapper.getContact());
				msg = "\n" + customer.getName() + "\n"
						+ Utility.FullName(contact.getFirst_name(), contact.getMiddle_name(), contact.getLast_name());
			} else if (eventMapper.getCustomer() == null && eventMapper.getCustomer().isEmpty()
					&& eventMapper.getContact() != null && !eventMapper.getContact().isEmpty()) {
				ContactDetails contact = contactRepository.getcontactDetailsById(eventMapper.getContact());
				msg = "\n"
						+ Utility.FullName(contact.getFirst_name(), contact.getMiddle_name(), contact.getLast_name());
			} else if (eventMapper.getCustomer() != null && !eventMapper.getCustomer().isEmpty()
					&& eventMapper.getContact() == null && eventMapper.getContact().isEmpty()) {
				Customer customer = customerRepository.getcustomerDetailsById(eventMapper.getCustomer());
				msg = "\n" + customer.getName();
			}

			Notificationparam param = new Notificationparam();
			EmployeeDetails emp = employeeRepository.getEmployeesByuserId(eventMapper.getUserId());
			String name = employeeService.getEmployeeFullNameByObject(emp);
			param.setEmployeeDetails(emp);
			param.setAdminMsg("Event scheduled for " + eventMapper.getStartDate() + ", " + eventMapper.getName()
					+ "' created by " + name + msg);
			param.setOwnMsg("Event scheduled for " + eventMapper.getStartDate() + ", " + eventMapper.getName()
					+ " created." + msg);
			param.setNotificationType("Event Creation");
			param.setProcessNmae("Event");
			param.setType("create");
			param.setEmailSubject("Korero alert- Event created");
			param.setCompanyName(companyName);
			param.setUserId(eventMapper.getUserId());

			if (!eventMapper.getUserId().equals(eventMapper.getAssignedTo())) {
				List<String> assignToUserIds = new ArrayList<>();
				assignToUserIds.add(eventMapper.getAssignedTo());
				param.setAssignToUserIds(assignToUserIds);
				param.setAssignToMsg("Event " + "'" + eventMapper.getName() + "' on " + eventMapper.getStartDate()
						+ " assigned to " + employeeService.getEmployeeFullName(eventMapper.getAssignedTo()) + " by "
						+ name + msg);
			}
			if (null != eventMapper.getIncluded() && !eventMapper.getIncluded().isEmpty()) {
				List<String> includedids = new ArrayList<>(eventMapper.getIncluded());
				param.setIncludeedUserIds(includedids);
				param.setIncludeMsg("You have been added Event " + "'" + eventMapper.getName() + "' on "
						+ eventMapper.getStartDate() + " by " + name + msg);
			}
			notificationService.createNotificationForDynamicUsers(param);

			resultMapper = getEventDetails(eventId);
		}
		return resultMapper;
	}

	@Override
	public EventViewMapper getEventDetails(String eventId) {

		EventDetails eventDetails = eventDetailsRepository.getEventDetailsById(eventId);
		EventViewMapper eventMapper = new EventViewMapper();

		if (null != eventDetails) {

			if (eventDetails.getEvent_type() != null && eventDetails.getEvent_type().trim().length() > 0) {
				EventType type = eventTypeRepository.findByEventTypeId(eventDetails.getEvent_type());
				if (null != type) {
					System.out.println("id======" + eventDetails.getEvent_type());
					eventMapper.setEventType(type.getEventType());
					eventMapper.setEventTypeId(type.getEventTypeId());
				}
			}
			eventMapper.setEventId(eventId);
			eventMapper.setStartTime(eventDetails.getStart_time());
			eventMapper.setStartDate(Utility.getISOFromDate(eventDetails.getStart_date()));
			eventMapper.setEndDate(Utility.getISOFromDate(eventDetails.getEnd_date()));
			eventMapper.setEndTime(eventDetails.getEnd_time());
			eventMapper.setStatus(eventDetails.getEvent_status());
			eventMapper.setEventDescription(eventDetails.getEvent_description());
			eventMapper.setTimeZone(eventDetails.getTime_zone());
			eventMapper.setEventSubject(eventDetails.getSubject());
			eventMapper.setUserId(eventDetails.getUser_id());
			eventMapper.setOrganizationId(eventDetails.getOrganization_id());
			eventMapper.setCreationDate(Utility.getISOFromDate(eventDetails.getCreation_date()));
			eventMapper.setUpdateDate(Utility.getISOFromDate(eventDetails.getUpdateDate()));
			eventMapper.setCompletionInd(eventDetails.isComplitionInd());
			eventMapper.setRating(eventDetails.getRating());
			eventMapper.setAssignedTo(eventDetails.getAssignedTo());
			eventMapper.setAssignedBy(employeeService.getEmployeeFullName(eventDetails.getAssignedBy()));

			if (null != eventDetails.getAssignedTo()) {
				EmployeeDetails employeeDetails = employeeRepository.getEmployeeByUserId(eventDetails.getAssignedTo());
				if (null != employeeDetails) {
					String middleName = " ";
					String lastName = " ";

					if (null != employeeDetails.getLastName()) {

						lastName = employeeDetails.getLastName();
					}
					if (null != employeeDetails.getMiddleName()) {

						middleName = employeeDetails.getMiddleName();
						eventMapper
								.setAssignedToName(employeeDetails.getFirstName() + " " + middleName + " " + lastName);
					} else {
						eventMapper.setAssignedToName(employeeDetails.getFirstName() + " " + lastName);
					}
				}
			}
			EmployeeDetails employeeDetails1 = employeeRepository.getEmployeeByUserId(eventDetails.getUser_id());
			if (null != employeeDetails1) {
				String middleName = " ";
				String lastName = " ";

				if (null != employeeDetails1.getLastName()) {

					lastName = employeeDetails1.getLastName();
				}
				if (null != employeeDetails1.getMiddleName()) {

					middleName = employeeDetails1.getMiddleName();
					eventMapper.setWoner(employeeDetails1.getFirstName() + " " + middleName + " " + lastName);
				} else {
					eventMapper.setWoner(employeeDetails1.getFirstName() + " " + lastName);
				}
			}

			if (!StringUtils.isEmpty(eventDetails.getContact())) {
				ContactDetails contact = contactRepository.getcontactDetailsById(eventDetails.getContact());
				if (contact != null) {
					if (contact.getFirst_name() != null && contact.getLast_name() != null) {
						eventMapper.setContact(contact.getFirst_name() + " " + contact.getLast_name());
					}
				}
			}
		}

		CandidateEventLink candidateEventLink = candidateEventLinkRepository.getCandidateEventLinkByEventId(eventId);
		if (null != candidateEventLink) {
			CandidateDetails candidateDetails = candidateDetailsRepository
					.getcandidateDetailsById(candidateEventLink.getCandidateId());

			eventMapper.setCandidateId(candidateDetails.getCandidateId());
			String middleName = " ";
			String lastName = "";
			if (null != candidateDetails.getLastName()) {

				lastName = candidateDetails.getLastName();
			}
			if (null != candidateDetails.getMiddleName()) {

				middleName = candidateDetails.getMiddleName();
				eventMapper.setCandidateName(candidateDetails.getFirstName() + " " + middleName + " " + lastName);
			} else {
				eventMapper.setCandidateName(candidateDetails.getFirstName() + " " + lastName);
			}

		} else {

			eventMapper.setCandidateId("");
			eventMapper.setCandidateName("");
		}

		// set event owner list
		if (null != eventDetails) {
			List<EmployeeViewMapper> empList = new ArrayList<EmployeeViewMapper>();

			List<EmployeeEventLink> employeeList = employeeEventRepository.getEmpListByEventId(eventId);

			if (null != employeeList && !employeeList.isEmpty()) {
				employeeList.stream().map(employeeEventLink -> {
					if (null != employeeEventLink.getEmployee_id()) {
						if (!employeeEventLink.getEmployee_id().equals(eventDetails.getAssignedTo())) {
							if (!employeeEventLink.getEmployee_id().equals(eventDetails.getUser_id())) {
								EmployeeViewMapper employeeMapper = new EmployeeViewMapper();

								EmployeeDetails employeeDetails2 = employeeRepository
										.getEmployeeByUserId(employeeEventLink.getEmployee_id());
								if (null != employeeDetails2) {
									String middleName = " ";
									String lastName = " ";

									if (null != employeeDetails2.getLastName()) {

										lastName = employeeDetails2.getLastName();
									}
									if (null != employeeDetails2.getMiddleName()) {

										middleName = employeeDetails2.getMiddleName();
										employeeMapper.setFullName(
												employeeDetails2.getFirstName() + " " + middleName + " " + lastName);
									} else {
										employeeMapper.setFullName(employeeDetails2.getFirstName() + " " + lastName);
									}

									employeeMapper.setEmployeeId(employeeDetails2.getEmployeeId());
									employeeMapper.setUserId(employeeDetails2.getUserId());
									empList.add(employeeMapper);
								}
							}
						}

					}

					return empList;
				}).collect(Collectors.toList());
			}
			eventMapper.setIncluded(empList);
			// set event address list //

			List<EventAddressLink> eventAddressList = eventAddressRepository.getAddressListByEventId(eventId);
			List<AddressMapper> addressList = new ArrayList<AddressMapper>();
			if (null != eventAddressList && !eventAddressList.isEmpty()) {

				eventAddressList.stream().map(eventAddressLink -> {
					AddressMapper addressMapper = addressService.getAddressDetails(eventAddressLink.getAddress_id());

					addressList.add(addressMapper);
					return addressList;
				}).collect(Collectors.toList());

			}

			if (null != eventDetails.getOppertunity()) {
				OpportunityDetails opportunityDetails = opportunityDetailsRepository
						.getopportunityDetailsById(eventDetails.getOppertunity());
				if (opportunityDetails != null) {
					eventMapper.setOppertunity(opportunityDetails.getOpportunityName());

				}
			}
			Customer customer = customerRepository.getCustomerDetailsByCustomerId(eventDetails.getCustomer());
			if (customer != null) {
				eventMapper.setCustomer(customer.getName());

			}

			eventMapper.setAddress(addressList);
		}
		return eventMapper;
	}

	@Override
	public List<EventViewMapper> getEventDetailsByEmployeeIdPageWise(String employeeId, int pageNo, int pageSize) {
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("creation_date").descending());
		Page<EmployeeEventLink> list = employeeEventRepository.getEventListByEmpId(employeeId, paging);
		List<EventViewMapper> resultMapper = list.stream().map(li -> {
			EventViewMapper mapper = getEventDetails(li.getEvent_id());
			mapper.setPageCount(list.getTotalPages());
			mapper.setDataCount(list.getSize());
			mapper.setListCount(list.getTotalElements());
			return mapper;
		}).filter(li1 -> li1.getEventId() != null)
				.sorted((o1, o2) -> o1.getCreationDate().compareTo(o2.getCreationDate())).collect(Collectors.toList());
		return resultMapper;
	}

	@Override
	public List<EventViewMapper> getEventDetailsByEmployeeId(String employeeId) {
		return employeeEventRepository.getByEmployeeId(employeeId).stream()
				.map(employeeEventLink -> getEventDetails(employeeEventLink.getEvent_id()))
				.filter(li1 -> li1.getEventId() != null)
				.sorted((o1, o2) -> o1.getCreationDate().compareTo(o2.getCreationDate())).collect(Collectors.toList());
	}

	@Override
	public List<EventViewMapper> getEventDetailsByOrganizationId(String organizationId) {
		return eventDetailsRepository.getEventListByOrgId(organizationId).stream()
				.map(li -> getEventDetails(li.getEvent_id()))
				.sorted((o1, o2) -> o1.getCreationDate().compareTo(o2.getCreationDate())).collect(Collectors.toList());

	}

	@Override
	public EventViewMapper updateEventDetails(String eventId, EventMapper eventMapper)
			throws IOException, TemplateException {
		EventViewMapper resultMapper = new EventViewMapper();
		if (null != eventId) {
			EventDetails eventDetails = eventDetailsRepository.getEventDetailsById(eventId);

			if (null != eventDetails) {
				eventDetails.setLive_ind(false);
				eventDetailsRepository.save(eventDetails);
				EventDetails newEventDetailsData = new EventDetails();
				if (eventMapper.getEventType() != null) {
					newEventDetailsData.setEvent_type(eventMapper.getEventType());

				} else {
					newEventDetailsData.setEvent_type(eventDetails.getEvent_type());
				}

				if (eventMapper.getStartDate() != null) {
					try {
						newEventDetailsData.setStart_date(Utility.getDateFromISOString(eventMapper.getStartDate()));
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					newEventDetailsData.setStart_date(eventDetails.getStart_date());
				}

				if (eventMapper.getStartTime() != 0) {
					newEventDetailsData.setStart_time(eventMapper.getStartTime());
				} else {
					newEventDetailsData.setStart_time(eventDetails.getStart_time());
				}
				if (eventMapper.getEndDate() != null) {
					try {
						newEventDetailsData.setEnd_date(Utility.getDateFromISOString(eventMapper.getEndDate()));
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					newEventDetailsData.setEnd_date(eventDetails.getEnd_date());
				}
				if (eventMapper.getEndTime() != 0) {
					newEventDetailsData.setEnd_time(eventMapper.getEndTime());
				} else {
					newEventDetailsData.setEnd_time(eventDetails.getEnd_time());
				}

				if (eventMapper.getStatus() != null) {
					newEventDetailsData.setEvent_status(eventMapper.getStatus());
				} else {
					newEventDetailsData.setEvent_status(eventDetails.getEvent_status());
				}

				if (eventMapper.getEventSubject() != null) {
					newEventDetailsData.setSubject((eventMapper.getEventSubject()));
				} else {
					newEventDetailsData.setSubject((eventDetails.getSubject()));
				}

				if (eventMapper.getEventDescription() != null) {
					List<EventNotesLink> list = eventNotesLinkRepository.getNoteByEventId(eventId);
					if (null != list && !list.isEmpty()) {
						list.sort((m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
						Notes notes = notesRepository.findByNoteId(list.get(0).getNotesId());
						if (null != notes) {
							notes.setNotes(eventMapper.getEventDescription());
							notesRepository.save(notes);
						}
					}
				}

				if (eventMapper.getTimeZone() != null) {
					newEventDetailsData.setTime_zone(eventMapper.getTimeZone());
				} else {
					newEventDetailsData.setTime_zone(eventDetails.getTime_zone());
				}

				if (eventMapper.getAssignedTo() != null) {
					newEventDetailsData.setAssignedTo(eventMapper.getAssignedTo());
				} else {
					newEventDetailsData.setAssignedTo(eventDetails.getAssignedTo());
				}

				if (eventMapper.getComplitionInd() != null && !eventMapper.getComplitionInd().isEmpty()) {
					if (eventMapper.getComplitionInd().equalsIgnoreCase("true")) {
						newEventDetailsData.setComplitionInd(true);
					} else if (eventMapper.getComplitionInd().equalsIgnoreCase("false")) {
						newEventDetailsData.setComplitionInd(false);

					}

				} else {
					newEventDetailsData.setComplitionInd(eventDetails.isComplitionInd());

				}

				if (eventMapper.getContact() != null) {
					newEventDetailsData.setContact(eventMapper.getContact());
				} else {
					newEventDetailsData.setContact(eventDetails.getContact());
				}

				if (eventMapper.getCustomer() != null) {
					newEventDetailsData.setCustomer(eventMapper.getCustomer());
				} else {
					newEventDetailsData.setCustomer(eventDetails.getCustomer());
				}
				if (eventMapper.getOppertunity() != null) {
					newEventDetailsData.setOppertunity(eventMapper.getOppertunity());
				} else {
					newEventDetailsData.setOppertunity(eventDetails.getOppertunity());
				}
				if (null != eventDetails.getAssignedBy()) {
					if (null != eventMapper.getAssignedTo()
							&& !eventDetails.getAssignedBy().equals(eventMapper.getAssignedTo())) {
						newEventDetailsData.setAssignedBy(eventMapper.getUserId());
					} else {
						newEventDetailsData.setAssignedBy(eventDetails.getAssignedBy());
					}
				} else {
					eventDetails.setAssignedBy(eventMapper.getUserId());
				}
				newEventDetailsData.setEvent_id(eventId);
				newEventDetailsData.setLive_ind(true);
				newEventDetailsData.setCreation_date(eventDetails.getCreation_date());
				newEventDetailsData.setUser_id(eventMapper.getUserId());
				newEventDetailsData.setOrganization_id(eventMapper.getOrganizationId());
				eventDetailsRepository.save(newEventDetailsData);

				/* update event address link */

				List<EventAddressLink> eventAddressList = eventAddressRepository.getAddressListByEventId(eventId);
				if (null != eventAddressList && !eventAddressList.isEmpty()) {
					eventAddressList.forEach(eventAddressLink -> {
						eventAddressLink.setLive_ind(false);
						eventAddressRepository.save(eventAddressLink);
					});

				}

				/* insert new address */

				if (null != eventMapper.getAddress()) {
					List<AddressMapper> addressList = eventMapper.getAddress();

					for (AddressMapper addressMapper : addressList) {
						/* insert to address info table */

						AddressInfo addressInfo = new AddressInfo();
						addressInfo.setCreationDate(new Date());
						addressInfo.setCreatorId(eventMapper.getUserId());
						AddressInfo info = addressInfoRepository.save(addressInfo);
						String addressId = info.getId();
						if (null != addressId) {

							/* insert to address details table */
							AddressDetails newAddressDetails = new AddressDetails();
							newAddressDetails.setAddressId(addressId);
							newAddressDetails.setAddressLine1(addressMapper.getAddress1());
							newAddressDetails.setAddressLine2(addressMapper.getAddress2());
							newAddressDetails.setAddressType(addressMapper.getAddressType());
							newAddressDetails.setCountry(addressMapper.getCountry());
							newAddressDetails.setCreationDate(new Date());
							newAddressDetails.setStreet(addressMapper.getStreet());
							newAddressDetails.setCity(addressMapper.getCity());
							newAddressDetails.setPostalCode(addressMapper.getPostalCode());
							newAddressDetails.setTown(addressMapper.getTown());
							newAddressDetails.setLatitude(addressMapper.getLatitude());
							newAddressDetails.setLongitude(addressMapper.getLongitude());
							newAddressDetails.setState(addressMapper.getState());
							newAddressDetails.setLiveInd(true);
							newAddressDetails.setHouseNo(addressMapper.getHouseNo());
							addressDetailsRepository.save(newAddressDetails);

							/* insert to account address link table */

							EventAddressLink eventAddressLink = new EventAddressLink();
							eventAddressLink.setAddress_id(addressId);
							eventAddressLink.setCreation_date(new Date());
							eventAddressLink.setEvent_id(eventId);
							eventAddressLink.setLive_ind(true);
							eventAddressRepository.save(eventAddressLink);

						}
					}
					/* update event employee link table */
					// update event employee link

					List<String> empList = eventMapper.getIncluded();

					empList.add(eventMapper.getUserId());
					if (!eventMapper.getUserId().equals(eventMapper.getAssignedTo())) {
						empList.add(eventMapper.getAssignedTo());
					}
					if (null != empList && !empList.isEmpty()) {

						List<EmployeeEventLink> employeeList = employeeEventRepository.getEmpListByEventId(eventId);

						if (null != employeeList && !employeeList.isEmpty()) {
							employeeList.forEach(employeeEventLink -> {
								employeeEventLink.setLive_ind(false);
								employeeEventRepository.save(employeeEventLink);

							});
						}

						// insert new data to call employee link//

						if (null != empList && !empList.isEmpty()) {
							for (String employeeId : empList) {
								EmployeeEventLink employeeEventLink = new EmployeeEventLink();
								employeeEventLink.setEvent_id(eventId);
								employeeEventLink.setEmployee_id(employeeId);
								employeeEventLink.setCreation_date(new Date());
								employeeEventLink.setLive_ind(true);
								employeeEventRepository.save(employeeEventLink);

							}

						}
					}

				}
				/* insert to Notification Table */
				Notificationparam param = new Notificationparam();
				EmployeeDetails emp = employeeRepository.getEmployeesByuserId(eventMapper.getUserId());
				String name = employeeService.getEmployeeFullNameByObject(emp);
				param.setEmployeeDetails(emp);
				param.setAdminMsg("Event " + "'" + eventMapper.getName() + "'"
						+ " has been updated, latest details are " + eventMapper.getStartDate() + " ");
				param.setOwnMsg("Event " + eventMapper.getName() + " updated.");
				param.setNotificationType("Event update");
				param.setProcessNmae("Event");
				param.setType("update");
				param.setEmailSubject("Korero alert- Event updated");
				param.setCompanyName(companyName);
				param.setUserId(eventMapper.getUserId());

				if (!eventMapper.getUserId().equals(eventMapper.getAssignedTo())) {
					List<String> assignToUserIds = new ArrayList<>();
					assignToUserIds.add(eventMapper.getAssignedTo());
					param.setAssignToUserIds(assignToUserIds);
					param.setAssignToMsg("Event " + "'" + eventMapper.getName() + "'" + " assigned to "
							+ employeeService.getEmployeeFullName(eventMapper.getAssignedTo()) + " by " + name);
				}
				if (null != eventMapper.getIncluded() && !eventMapper.getIncluded().isEmpty()) {
					List<String> includedids = new ArrayList<>(eventMapper.getIncluded());
					param.setIncludeedUserIds(includedids);
					param.setIncludeMsg(
							"You have been added Event " + "'" + eventMapper.getName() + "'" + " by " + name);
				}
				notificationService.createNotificationForDynamicUsers(param);

				resultMapper = getEventDetails(eventId);
			}

		}

		return resultMapper;
	}

	@Override
	public boolean delinkEvent(String employeeId, String eventId) {

		EmployeeEventLink employeeEventLink = employeeEventRepository.getEmployeeEventLink(employeeId, eventId);

		if (null != employeeEventLink) {
			employeeEventLink.setLive_ind(false);

			EventDetails eventDetails = eventDetailsRepository.getEventDetailsById(eventId);
			if (null != eventDetails) {
				eventDetails.setLive_ind(false);
				eventDetailsRepository.save(eventDetails);
			}
			employeeEventRepository.save(employeeEventLink);
			return true;
		}

		return false;
	}

	@Override
	public EventMapper saveEventType(EventMapper eventMapper) {
		String eventTypeId = null;

		if (eventMapper != null) {
			EventType eventType = new EventType();

			eventType.setEventType(eventMapper.getEventType());
			eventType.setCreationDate(new Date());
			eventType.setUserId(eventMapper.getUserId());
			eventType.setOrgId(eventMapper.getOrganizationId());
			eventType.setEditInd(eventMapper.isEditInd());
			eventType.setLiveInd(true);

			EventType dbEventType = eventTypeRepository.save(eventType);
			eventTypeId = dbEventType.getEventTypeId();

			EventTypeDelete eventTypeDelete = new EventTypeDelete();
			eventTypeDelete.setUserId(eventMapper.getUserId());
			eventTypeDelete.setOrgId(eventMapper.getOrganizationId());
			eventTypeDelete.setEventTypeId(eventTypeId);
			eventTypeDelete.setUpdationDate(new Date());
			eventTypeDelete.setUpdatedBy(eventMapper.getUserId());
			eventTypeDeleteRepository.save(eventTypeDelete);
		}
		EventMapper resultMapper = getEventById(eventTypeId);
		return resultMapper;
	}

	@Override
	public List<EventMapper> getEventTypeByOrgId(String orgId) {
		List<EventMapper> resultList = new ArrayList<EventMapper>();
		List<EventType> eventTypeList = eventTypeRepository.findByOrgIdAndLiveInd(orgId, true);

		if (null != eventTypeList && !eventTypeList.isEmpty()) {
			eventTypeList.stream().map(eventType -> {

				EventMapper eventMapper = new EventMapper();
				eventMapper.setEventTypeId(eventType.getEventTypeId());
				eventMapper.setEventType(eventType.getEventType());
				eventMapper.setEditInd(eventType.isEditInd());
				eventMapper.setCreationDate(Utility.getISOFromDate(eventType.getCreationDate()));

				resultList.add(eventMapper);
				return resultList;
			}).collect(Collectors.toList());

		}
		Collections.sort(resultList, (m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));

		List<EventTypeDelete> eventTypeDelete = eventTypeDeleteRepository.findByOrgId(orgId);
		if (null != eventTypeDelete && !eventTypeDelete.isEmpty()) {
			Collections.sort(eventTypeDelete, (p1, p2) -> p2.getUpdationDate().compareTo(p1.getUpdationDate()));

			resultList.get(0).setUpdationDate(Utility.getISOFromDate(eventTypeDelete.get(0).getUpdationDate()));
			EmployeeDetails employeeDetails = employeeRepository
					.getEmployeeByUserId(eventTypeDelete.get(0).getUserId());
			if (null != employeeDetails) {
				String middleName = " ";
				String lastName = "";

				if (!StringUtils.isEmpty(employeeDetails.getLastName())) {

					lastName = employeeDetails.getLastName();
				}

				if (employeeDetails.getMiddleName() != null && employeeDetails.getMiddleName().length() > 0) {

					middleName = employeeDetails.getMiddleName();
					resultList.get(0).setName(employeeDetails.getFirstName() + " " + middleName + " " + lastName);
				} else {

					resultList.get(0).setName(employeeDetails.getFirstName() + " " + lastName);
				}
			}
		}

		return resultList;
	}

	@Override
	public EventMapper updateEventType(String eventTypeId, EventMapper eventMapper) {
		EventMapper resultMapper = null;

		if (null != eventMapper.getEventTypeId()) {

			EventType eventType = eventTypeRepository.findByEventTypeId(eventMapper.getEventTypeId());

			if (null != eventType.getEventTypeId()) {
				eventType.setEventType(eventMapper.getEventType());
				eventType.setEditInd(eventMapper.isEditInd());
				eventTypeRepository.save(eventType);

				EventTypeDelete eventTypeDelete = eventTypeDeleteRepository
						.findByEventTypeId(eventMapper.getEventTypeId());
				if (null != eventTypeDelete) {
					eventTypeDelete.setUpdationDate(new Date());
					eventTypeDelete.setUpdatedBy(eventMapper.getUserId());
					eventTypeDeleteRepository.save(eventTypeDelete);
				} else {
					EventTypeDelete eventTypeDelete1 = new EventTypeDelete();
					eventTypeDelete1.setEventTypeId(eventTypeId);
					eventTypeDelete1.setUserId(eventMapper.getUserId());
					eventTypeDelete1.setOrgId(eventMapper.getOrganizationId());
					eventTypeDelete1.setUpdationDate(new Date());
					eventTypeDelete1.setUpdatedBy(eventMapper.getUserId());
					eventTypeDeleteRepository.save(eventTypeDelete1);
				}
			}
			resultMapper = getEventById(eventMapper.getEventTypeId());
		}
		return resultMapper;
	}

	@Override
	public EventMapper getEventById(String eventTypeId) {
		EventType eventType = eventTypeRepository.findByEventTypeId(eventTypeId);
		EventMapper eventMapper = new EventMapper();

		if (null != eventType) {
			eventMapper.setEventTypeId(eventType.getEventTypeId());

			eventMapper.setEventType(eventType.getEventType());
			eventMapper.setCreationDate(Utility.getISOFromDate(eventType.getCreationDate()));
			// eventMapper.setOrganizationId(eventType.getOrgId());
			// eventMapper.setUserId(eventType.getUserId());
			eventMapper.setEditInd(eventType.isEditInd());
			List<EventTypeDelete> list = eventTypeDeleteRepository.findByOrgId(eventType.getOrgId());
			if (null != list && !list.isEmpty()) {
				Collections.sort(list, (p1, p2) -> p2.getUpdationDate().compareTo(p1.getUpdationDate()));

				eventMapper.setUpdateDate(Utility.getISOFromDate(list.get(0).getUpdationDate()));
				eventMapper.setName(employeeService.getEmployeeFullName(list.get(0).getUpdatedBy()));
			}
		}
		return eventMapper;
	}

	@Override
	public List<EventViewMapper> getEventListOfCandidate(String candidateId) {

		List<EventViewMapper> mappList = new ArrayList<>();
		List<CandidateEventLink> list = candidateEventLinkRepository.getEventListByCandidateId(candidateId);
		mappList = list.stream().map(li -> getEventDetails(li.getEventId())).collect(Collectors.toList());

		return mappList;
	}

	@Override
	public List<EventMapper> getEventDetailsByNameByOrgLevel(String name, String orgId) {
		return eventTypeRepository.findByEventTypeContainingAndOrgId(name, orgId).stream().map(li -> getEventMapper(li))
				.collect(Collectors.toList());
	}

	@Override
	public boolean checkEventNameInEventTypeByOrgLevel(String eventType, String orgId) {
		List<EventType> eventTypes = eventTypeRepository.findByEventTypeAndLiveIndAndOrgId(eventType, true, orgId);
		if (eventTypes.size() > 0) {
			return true;
		}
		return false;
	}

	@Override
	public void deleteEventTypeById(String eventTypeId) {
		if (null != eventTypeId) {
			EventType eventTypes = eventTypeRepository.findByEventTypeId(eventTypeId);

			EventTypeDelete eventTypeDelete = eventTypeDeleteRepository.findByEventTypeId(eventTypeId);
			if (null != eventTypeDelete) {
				eventTypeDelete.setUpdationDate(new Date());
				eventTypeDelete.setUpdatedBy(eventTypes.getUserId());
				eventTypeDeleteRepository.save(eventTypeDelete);
			}
			eventTypes.setLiveInd(false);
			eventTypeRepository.save(eventTypes);
		}

	}

	@Override
	public HashMap getEventListsByUserIdStartdateAndEndDate(String userId, String startDate, String endDate) {

		HashMap map = new HashMap();
		Date end_date = null;
		Date start_date = null;
		try {
			end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<EventDetails> eventDetails = eventDetailsRepository
				.getEventListsByUserIdAndStartdateAndEndDateAndLiveInd(userId, start_date, end_date);
		if (null != eventDetails && !eventDetails.isEmpty()) {
			map.put("totalEvent", eventDetails.size());
		} else {
			map.put("totalEvent", eventDetails.size() + " , No Event Created");

		}
		List<EventDetails> eventDetails1 = eventDetailsRepository
				.getEventListsByUserIdAndStartdateAndEndDateAndComplitionInd(userId, start_date, end_date);
		if (null != eventDetails1 && !eventDetails1.isEmpty()) {
			map.put("totalEventCompleted", eventDetails1.size());
		} else {
			map.put("totalEventCompleted", eventDetails1.size() + " , No Event Completed");
		}
		List<EventDetails> eventDetails2 = eventDetailsRepository
				.getEventListByUserIdAndStartdateAndEndDateAndComplitionInd(userId, start_date, end_date);
		if (null != eventDetails2 && !eventDetails2.isEmpty()) {
			map.put("totalEventInCompleted", eventDetails2.size());
		} else {
			map.put("totalEventInCompleted", eventDetails2.size() + " , No Event Pending");
		}
		return map;
	}

	@Override
	public Map<String, List<EventViewMapper>> getEventListsByEventTypeAndUserIdAndStartdateAndEndDate(String userId,
			String startDate, String endDate) {

		List<EventViewMapper> resultList = new ArrayList<EventViewMapper>();

		int eventTypeCount = 0;
		int eventDetailsCount = 0;
		int total = 0;

		Date end_date = null;
		Date start_date = null;
		try {
			end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<EventType> eventTypes = eventTypeRepository.findByUserIdAndLiveInd(userId, true);
		if (null != eventTypes && !eventTypes.isEmpty()) {
			for (EventType eventType : eventTypes) {
				eventTypeCount++;

				List<EventDetails> eventDetails = eventDetailsRepository
						.getEventListsByEventTypeIdAndStartdateAndEndDateAndLiveInd(eventType.getEventTypeId(),
								start_date, end_date);
				if (null != eventDetails && !eventDetails.isEmpty()) {
					for (EventDetails eventDetail : eventDetails) {
						eventDetailsCount++;
						EventViewMapper eventViewMapper = getEventDetails(eventDetail.getEvent_id());
						resultList.add(eventViewMapper);
					}

				}

			}
		}

//		System.out.println("eventTypeCount==========="+eventTypeCount);
//		System.out.println("eventDetailsCount==========="+eventDetailsCount);
//		System.out.println("total==========="+resultList.size());

		Map<String, List<EventViewMapper>> EmpByDepartment = new HashMap<>();

		for (EventViewMapper p : resultList) {

			if (!EmpByDepartment.containsKey(p.getEventType())) {
				EmpByDepartment.put(p.getEventType(), new ArrayList<>());
			}
			EmpByDepartment.get(p.getEventType()).add(p);
		}
		// System.out.println("EmpByDepartment================="+EmpByDepartment);

		return EmpByDepartment;
	}

	@Override
	public EventMapper getEventMapper(EventType eventType) {

		EventMapper eventMapper = new EventMapper();

		eventMapper.setEventTypeId(eventType.getEventTypeId());
		eventMapper.setEventType(eventType.getEventType());
		eventMapper.setCreationDate(Utility.getISOFromDate(eventType.getCreationDate()));
		// eventMapper.setOrganizationId(eventType.getOrgId());
		// eventMapper.setUserId(eventType.getUserId());

		return eventMapper;
	}

	@Override
	public List<Map<String, Integer>> getOpenEventCountByUserId(String userId, String organizationId) {
		List<Map<String, Integer>> map = new ArrayList<>();
		List<EmployeeEventLink> list = employeeEventRepository.getByEmployeeId(userId);
		List<EventType> eventTypes = eventTypeRepository.findByOrgIdAndLiveInd(organizationId, true);
		int count = 0;
		for (EventType eventType : eventTypes) {
			for (EmployeeEventLink employeeEventLink : list) {
				EventDetails event = eventDetailsRepository.getDataByEventAndEventTypeAndComplitionInd(
						employeeEventLink.getEvent_id(), eventType.getEventTypeId(), false);
				if (null != event) {
					count++;
				}
			}
			Map map1 = new HashMap<>();
			map1.put("type", eventType.getEventType());
			System.out.print(eventType.getEventType());
			System.out.print(count);
			map1.put("count", count);
			map.add(map1);
		}
		return map;
	}

	@Override
	public List<Map<String, List<EventViewMapper>>> getOpenEventListByUserId(String userId, String organizationId) {
		List<Map<String, List<EventViewMapper>>> mapList = new ArrayList<>();

		List<EventType> eventTypes = eventTypeRepository.findByOrgIdAndLiveInd(organizationId, true);
		for (EventType eventType : eventTypes) {
			Map<String, List<EventViewMapper>> map = new HashMap<>();
			List<EventViewMapper> resultList = new ArrayList<EventViewMapper>();

			List<EmployeeEventLink> list = employeeEventRepository.getByEmployeeId(userId);
			for (EmployeeEventLink employeeEventLink : list) {
				EventDetails event = eventDetailsRepository.getDataByEventAndEventTypeAndComplitionInd(
						employeeEventLink.getEvent_id(), eventType.getEventTypeId(), false);
				if (null != event) {
					EventViewMapper eventViewMapper = getEventDetails(employeeEventLink.getEvent_id());
					resultList.add(eventViewMapper);
				}

			}
			map.put(eventType.getEventType(), resultList);
			mapList.add(map);
		}
		return mapList;
	}

	@Override
	public List<EventViewMapper> getEventsByLeads(String leadsId, int pageNo) {
		Pageable paging = PageRequest.of(pageNo, 20, Sort.by("creationDate").descending());
		Page<LeadsEventLink> list = leadsEventRepo.getByLeadsId(leadsId, paging);

		return list.stream().map(li -> {
			EventViewMapper mapper = getEventDetails(li.getEventId());
			mapper.setPageCount(list.getTotalPages());
			mapper.setDataCount(list.getSize());
			mapper.setListCount(list.getTotalElements());
			return mapper;
		}).filter(li1 -> li1.getEventId() != null).collect(Collectors.toList());
	}

	@Override
	public List<EventViewMapper> getEventsByInvestorLeads(String investorLeadsId, int pageNo) {
		Pageable paging = PageRequest.of(pageNo, 20, Sort.by("creationDate").descending());
		Page<InvestorLeadsEventLink> list = investorLeadsEventRepo.getByInvestorLeadsId(investorLeadsId, paging);
		return list.stream().map(li -> {
			EventViewMapper mapper = getEventDetails(li.getEventId());
			mapper.setPageCount(list.getTotalPages());
			mapper.setDataCount(list.getSize());
			mapper.setListCount(list.getTotalElements());
			return mapper;
		}).filter(li1 -> li1.getEventId() != null).collect(Collectors.toList());
	}

	@Override
	public Map<String, List<EventViewMapper>> getCompletedEventListsByEventTypeAndUserIdAndStartdateAndEndDate(
			String userId, String startDate, String endDate) {

		List<EventViewMapper> resultList = new ArrayList<EventViewMapper>();

		Date end_date = null;
		Date start_date = null;
		try {
			end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<EventType> eventTypes = eventTypeRepository.findByUserIdAndLiveInd(userId, true);
		if (null != eventTypes && !eventTypes.isEmpty()) {
			for (EventType eventType : eventTypes) {
				List<EventDetails> eventDetails = eventDetailsRepository
						.getEventListByEventTypeIdAndStartdateAndEndDateAndComplitionInd(eventType.getEventTypeId(),
								start_date, end_date);
				if (null != eventDetails && !eventDetails.isEmpty()) {
					for (EventDetails eventDetail : eventDetails) {
						EventViewMapper eventViewMapper = getEventDetails(eventDetail.getEvent_id());
						resultList.add(eventViewMapper);
					}

				}

			}
		}

		Map<String, List<EventViewMapper>> EmpByDepartment = new HashMap<>();

		for (EventViewMapper p : resultList) {

			if (!EmpByDepartment.containsKey(p.getEventType())) {
				EmpByDepartment.put(p.getEventType(), new ArrayList<>());
			}
			EmpByDepartment.get(p.getEventType()).add(p);
		}

		return EmpByDepartment;
	}

	@Override
	public List<Map<String, Integer>> getCountEventListsByEventTypeAndUserIdAndStartdateAndEndDate(String userId,
			String startDate, String endDate) {

		Date end_date = null;
		Date start_date = null;
		try {
			end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<Map<String, Integer>> map = new ArrayList<>();
		List<EventType> eventTypes = eventTypeRepository.findByUserIdAndLiveInd(userId, true);
		if (null != eventTypes && !eventTypes.isEmpty()) {
			for (EventType eventType : eventTypes) {
				List<EventDetails> eventDetails = eventDetailsRepository
						.getEventListByEventTypeIdAndStartdateAndEndDateAndComplitionInd(eventType.getEventTypeId(),
								start_date, end_date);
				Map map1 = new HashMap<>();
				map1.put("type", eventType.getEventType());
				System.out.print(eventType.getEventType());
				System.out.print(eventDetails.size());
				map1.put("count", eventDetails.size());
				map.add(map1);
			}

		}
		return map;
	}

	@Override
	public String createEventNotes(NotesMapper notesMapper) {
		String notesId = null;
		if (null != notesMapper) {
			Notes notes = new Notes();
			notes.setNotes(notesMapper.getNotes());
			notes.setCreation_date(new Date());
			notes.setUserId(notesMapper.getEmployeeId());
			notes.setLiveInd(true);
			Notes note = notesRepository.save(notes);
			notesId = note.getNotes_id();

			/* insert to call-notes-link */

			EventNotesLink eventNotesLink = new EventNotesLink();
			eventNotesLink.setEventId(notesMapper.getEventId());
			eventNotesLink.setNotesId(notesId);
			eventNotesLink.setCreationDate(new Date());
			eventNotesLinkRepository.save(eventNotesLink);

			/* insert to leads-notes-link */
			EventDetails eventDetails = eventDetailsRepository.getEventDetailsById(notesMapper.getEventId());
			if (!StringUtils.isEmpty(eventDetails.getOppertunity())) {

				OpportunityNotesLink opportunityNotesLink = new OpportunityNotesLink();
				opportunityNotesLink.setOpportunity_id(eventDetails.getOppertunity());
				opportunityNotesLink.setNotesId(notesId);
				opportunityNotesLink.setCreation_date(new Date());
				opportunityNotesLinkRepository.save(opportunityNotesLink);
			}

			/* insert to customer-notes-link */
			if (!StringUtils.isEmpty(eventDetails.getContact())) {

				ContactNotesLink contactNotesLink = new ContactNotesLink();
				contactNotesLink.setContact_id(eventDetails.getContact());
				contactNotesLink.setNotesId(notesId);
				contactNotesLink.setCreation_date(new Date());
				contactNotesLinkRepository.save(contactNotesLink);
			}
		}
		return notesId;
	}

	@Override
	public List<NotesMapper> getNoteListByEventId(String eventId) {
		List<NotesMapper> resultList = new ArrayList<>();
		List<EventNotesLink> eventNotesLink = eventNotesLinkRepository.getNoteByEventId(eventId);
		if (eventNotesLink != null && !eventNotesLink.isEmpty()) {
			return eventNotesLink.stream().map(eventNotesLink1 -> {
				NotesMapper notesMapper = getNotes(eventNotesLink1.getNotesId());
				if (null != notesMapper) {
					resultList.add(notesMapper);
				}
				return notesMapper;
			}).collect(Collectors.toList());
		}
		return resultList;
	}

	private NotesMapper getNotes(String noteId) {
		Notes notes = notesRepository.findByNoteId(noteId);
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
//	public NotesMapper updateNoteDetails(NotesMapper notesMapper) {
//
//		NotesMapper resultMapper = employeeService.updateNotes(notesMapper);
//
//		return resultMapper;
//	}

	@Override
	public void deleteEventNotesById(String notesId) {
		EventNotesLink notesList = eventNotesLinkRepository.findByNotesId(notesId);
		if (null != notesList) {

			Notes notes = notesRepository.findByNoteId(notesId);
			if (null != notes) {
				notes.setLiveInd(false);
				notesRepository.save(notes);
			}
		}
	}

	public ActivityMapper getEventDetailsById(String eventId) {
		ActivityMapper activityMapper = new ActivityMapper();
		EventDetails eventDetails = eventDetailsRepository.getEventDetailsById(eventId);
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
			activityMapper.setAssignedBy(employeeService.getEmployeeFullName(eventDetails.getAssignedBy()));
			activityMapper.setAssignedTo(employeeService.getEmployeeFullName(eventDetails.getAssignedTo()));
		}
		return activityMapper;
	}

	@Override
	public ActivityMapper saveActivityEvent(EventMapper eventMapper) {

		EventInfo eventInfo = new EventInfo();
		eventInfo.setCreation_date(new Date());

		EventInfo info = eventInfoRepository.save(eventInfo);
		String eventId = info.getEvent_id();

		// EventViewMapper resultMapper = null;
		if (null != eventId) {
			/* insert to event details table */

			EventDetails eventDetails = new EventDetails();

			eventDetails.setEvent_id(eventId);
			try {
				eventDetails.setStart_date(Utility.getDateFromISOString(eventMapper.getStartDate()));
				eventDetails.setEnd_date(Utility.getDateFromISOString(eventMapper.getEndDate()));

			} catch (Exception e) {
				e.printStackTrace();
			}
			eventDetails.setStart_time(eventMapper.getStartTime());
			eventDetails.setEnd_time(eventMapper.getEndTime());
			eventDetails.setTime_zone(eventMapper.getTimeZone());
			eventDetails.setEvent_status(eventMapper.getStatus());
			eventDetails.setEvent_type(eventMapper.getEventTypeId());
			eventDetails.setSubject(eventMapper.getEventSubject());
			eventDetails.setEvent_description(eventMapper.getEventDescription());
			eventDetails.setCreation_date(new Date());
			eventDetails.setUser_id(eventMapper.getUserId());
			eventDetails.setOrganization_id(eventMapper.getOrganizationId());
			eventDetails.setLive_ind(true);
			eventDetails.setComplitionInd(false);
			eventDetails.setRating(0);
			eventDetails.setAssignedTo(eventMapper.getAssignedTo());
			eventDetails.setContact(eventMapper.getContact());
			eventDetails.setOppertunity(eventMapper.getOppertunity());
			eventDetails.setCustomer(eventMapper.getCustomer());
			eventDetails.setAssignedBy(eventMapper.getUserId());
			eventDetailsRepository.save(eventDetails);

			/* insert to CandidateEventLink table */

			if (!StringUtils.isEmpty(eventMapper.getCandidateId())) {

				CandidateEventLink candidateEventLink = new CandidateEventLink();
				candidateEventLink.setEventId(eventId);
				candidateEventLink.setCandidateId(eventMapper.getCandidateId());
				candidateEventLink.setCreationDate(new Date());
				candidateEventLink.setLiveInd(true);
				candidateEventLinkRepository.save(candidateEventLink);
			}

			// Event leads link

			if (!StringUtils.isEmpty(eventMapper.getLeadsId())) {
				LeadsEventLink leadsEventLink = new LeadsEventLink();
				leadsEventLink.setEventId(eventId);
				leadsEventLink.setLeadsId(eventMapper.getLeadsId());
				leadsEventLink.setCreationDate(new Date());
				leadsEventLink.setLiveInd(true);
				leadsEventRepo.save(leadsEventLink);
			}

			// Event InvestorLeads link
			if (!StringUtils.isEmpty(eventMapper.getInvestorLeadsId())) {
				InvestorLeadsEventLink investorLeadsEventLink = new InvestorLeadsEventLink();
				investorLeadsEventLink.setEventId(eventId);
				investorLeadsEventLink.setInvestorLeadsId(eventMapper.getInvestorLeadsId());
				investorLeadsEventLink.setCreationDate(new Date());
				investorLeadsEventLink.setLiveInd(true);
				investorLeadsEventRepo.save(investorLeadsEventLink);
			}

			// Event Investor link
			if (!StringUtils.isEmpty(eventMapper.getInvestorId())) {
				InvestorEventLink investorEventLink = new InvestorEventLink();
				investorEventLink.setEventId(eventId);
				investorEventLink.setInvestorId(eventMapper.getInvestorId());
				investorEventLink.setCreationDate(new Date());
				investorEventLink.setLiveInd(true);
				investorEventRepo.save(investorEventLink);
			}
			// Event customer link

			if (!StringUtils.isEmpty(eventMapper.getCustomer())) {
				CustomerEventLink customerEventLink = new CustomerEventLink();
				customerEventLink.setEventId(eventId);
				customerEventLink.setCustomerId(eventMapper.getCustomer());
				customerEventLink.setCreationDate(new Date());
				customerEventLink.setLiveInd(true);
				customerEventLink.setCampaignInd(eventMapper.isCampaignInd());
				customerEventRepo.save(customerEventLink);
			}

			// Event contact link

			if (!StringUtils.isEmpty(eventMapper.getContact())) {
				ContactEventLink contactEventLink = new ContactEventLink();
				contactEventLink.setEventId(eventId);
				contactEventLink.setContactId(eventMapper.getContact());
				contactEventLink.setCreationDate(new Date());
				contactEventLink.setLiveInd(true);
				contactEventRepo.save(contactEventLink);
			}

			/* insert into todo table */
			/*
			 * if (null != eventId) { ToDoDetails toDoDetails = new ToDoDetails();
			 * toDoDetails.setEventId(eventId); toDoDetails.setComplitionInd(false);
			 * toDoDetails.setCreationDate(new Date());
			 * toDoDetails.setUserId(eventMapper.getUserId());
			 * toDoDetails.setRating("Not Given"); toDoDetailsRepository.save(toDoDetails);
			 * }
			 */

			/* insert to event address link table */

			List<AddressMapper> addressMapperList = eventMapper.getAddress();
			String addressId = null;

			if (null != addressMapperList && !addressMapperList.isEmpty()) {
				for (AddressMapper addressMapper : addressMapperList) {

					AddressInfo addressInfo = new AddressInfo();
					addressInfo.setCreationDate(new Date());
					addressInfo.setCreatorId(eventMapper.getUserId());

					AddressInfo info1 = addressInfoRepository.save(addressInfo);
					addressId = info1.getId();
					if (null != addressId) {

						AddressDetails addressDetails = new AddressDetails();
						addressDetails.setAddressId(addressId);
						addressDetails.setAddressLine1(addressMapper.getAddress1());
						addressDetails.setAddressLine2(addressMapper.getAddress2());
						addressDetails.setAddressType(addressMapper.getAddressType());
						addressDetails.setCountry(addressMapper.getCountry());
						addressDetails.setCreationDate(new Date());
						addressDetails.setCreatorId(eventMapper.getUserId());
						addressDetails.setStreet(addressMapper.getStreet());
						addressDetails.setCity(addressMapper.getCity());
						addressDetails.setPostalCode(addressMapper.getPostalCode());
						addressDetails.setTown(addressMapper.getTown());
						addressDetails.setState(addressMapper.getState());
						addressDetails.setLatitude(addressMapper.getLatitude());
						addressDetails.setLongitude(addressMapper.getLongitude());
						addressDetails.setLiveInd(true);
						addressDetails.setHouseNo(addressMapper.getHouseNo());
						addressDetailsRepository.save(addressDetails);
					}
					/* insert to event address link table */

					EventAddressLink eventAddressLink = new EventAddressLink();
					eventAddressLink.setAddress_id(addressId);
					eventAddressLink.setCreation_date(new Date());
					eventAddressLink.setEvent_id(eventId);
					eventAddressLink.setLive_ind(true);
					eventAddressRepository.save(eventAddressLink);

				}

			}

			List<String> empList = eventMapper.getIncluded();

			empList.add(eventMapper.getUserId());
			if (!eventMapper.getUserId().equals(eventMapper.getAssignedTo())) {
				empList.add(eventMapper.getAssignedTo());
			}
			if (null != empList && !empList.isEmpty()) {
				empList.forEach(employeeId -> {

					/* insert to employee event link table */

					EmployeeEventLink employeeEventLink = new EmployeeEventLink();
					employeeEventLink.setEvent_id(eventId);
					employeeEventLink.setEmployee_id(employeeId);
					employeeEventLink.setCreation_date(new Date());
					employeeEventLink.setLive_ind(true);
					employeeEventRepository.save(employeeEventLink);

					/* insert to Note table */
					Notes notes = new Notes();
					notes.setCreation_date(new Date());
					notes.setNotes(eventMapper.getEventDescription());
					notes.setLiveInd(true);
					notes.setUserId(eventMapper.getUserId());
					Notes note = notesRepository.save(notes);

					String notesId = note.getNotes_id();
					/* insert to EventNoteLink table */

					EventNotesLink eventNote = new EventNotesLink();
					eventNote.setEventId(eventId);
					eventNote.setNotesId(notesId);
					eventNote.setCreationDate(new Date());
					eventNotesLinkRepository.save(eventNote);

					/* insert to Notification info */

					NotificationDetails notification = new NotificationDetails();
					notification.setNotificationType("event");
					notification.setUser_id(eventMapper.getUserId());
					EmployeeDetails emp = employeeRepository.getEmployeesByuserId(employeeId);

					String middleName1 = " ";
					String lastName1 = "";
					String satutation = "";

					if (!StringUtils.isEmpty(emp.getLastName())) {

						lastName1 = emp.getLastName();
					}
					if (emp.getSalutation() != null && emp.getSalutation().length() > 0) {
						satutation = emp.getSalutation();
					}

					if (emp.getMiddleName() != null && emp.getMiddleName().length() > 0) {

						middleName1 = emp.getMiddleName();
						notification.setMessage("" + satutation + " " + emp.getFirstName() + " " + middleName1 + " "
								+ lastName1 + " has assigned to a " + notification.getNotificationType() + " you ");
					} else {

						notification.setMessage("" + satutation + " " + emp.getFirstName() + " " + lastName1
								+ " has assigned to a " + notification.getNotificationType() + " you ");
					}

					String middleName2 = " ";
					String lastName2 = "";
					String satutation1 = "";

					if (!StringUtils.isEmpty(emp.getLastName())) {

						lastName2 = emp.getLastName();
					}
					if (emp.getSalutation() != null && emp.getSalutation().length() > 0) {
						satutation1 = emp.getSalutation();
					}

					if (emp.getMiddleName() != null && emp.getMiddleName().length() > 0) {

						middleName2 = emp.getMiddleName();
						notification.setAssignedBy(
								satutation1 + " " + emp.getFirstName() + " " + middleName2 + " " + lastName2);
					} else {

						notification.setAssignedBy(satutation1 + " " + emp.getFirstName() + " " + lastName2);
					}

					// notification.setAssignedBy(employee.getFullName());
					// EmployeeDetails employee2
					// =employeeRepository.getEmployeeDetailsByEmployeeId(callMapper.getEmployeeId());
					notification.setAssignedTo(eventMapper.getEmployeeId());
					notification.setNotificationDate(new Date());
					// notification.setMessage("" + employee.getFullName() + " has assigned to a " +
					// notification.getNotificationType() + " you ");
					notification.setOrg_id(notification.getOrg_id());
					notification.setMessageReadInd(false);
					notificationRepository.save(notification);
				});

			}
		}
		ActivityMapper mapper = getEventDetailsById(eventId);
		return mapper;
	}

	@Override
	public ActivityMapper updateActivityEventDetails(String eventId, EventMapper eventMapper)
			throws IOException, TemplateException {
		ActivityMapper resultMapper = new ActivityMapper();
		if (null != eventId) {
			EventDetails eventDetails = eventDetailsRepository.getEventDetailsById(eventId);

			if (null != eventDetails) {
//				eventDetails.setLive_ind(false);
//				eventDetailsRepository.save(eventDetails);
//				EventDetails newEventDetailsData = new EventDetails();
				if (eventMapper.getEventType() != null) {
					eventDetails.setEvent_type(eventMapper.getEventType());

				} else {
					eventDetails.setEvent_type(eventDetails.getEvent_type());
				}

				if (eventMapper.getStartDate() != null) {
					try {
						eventDetails.setStart_date(Utility.getDateFromISOString(eventMapper.getStartDate()));
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					eventDetails.setStart_date(eventDetails.getStart_date());
				}

				if (eventMapper.getStartTime() != 0) {
					eventDetails.setStart_time(eventMapper.getStartTime());
				} else {
					eventDetails.setStart_time(eventDetails.getStart_time());
				}
				if (eventMapper.getEndDate() != null) {
					try {
						eventDetails.setEnd_date(Utility.getDateFromISOString(eventMapper.getEndDate()));
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					eventDetails.setEnd_date(eventDetails.getEnd_date());
				}
				if (eventMapper.getEndTime() != 0) {
					eventDetails.setEnd_time(eventMapper.getEndTime());
				} else {
					eventDetails.setEnd_time(eventDetails.getEnd_time());
				}

				if (eventMapper.getStatus() != null) {
					eventDetails.setEvent_status(eventMapper.getStatus());
				} else {
					eventDetails.setEvent_status(eventDetails.getEvent_status());
				}

				if (eventMapper.getEventSubject() != null) {
					eventDetails.setSubject((eventMapper.getEventSubject()));
				} else {
					eventDetails.setSubject((eventDetails.getSubject()));
				}

				if (eventMapper.getEventDescription() != null) {
					List<EventNotesLink> list = eventNotesLinkRepository.getNoteByEventId(eventId);
					if (null != list && !list.isEmpty()) {
						list.sort((m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
						Notes notes = notesRepository.findByNoteId(list.get(0).getNotesId());
						if (null != notes) {
							notes.setNotes(eventMapper.getEventDescription());
							notesRepository.save(notes);
						}
					}
				}

				if (eventMapper.getTimeZone() != null) {
					eventDetails.setTime_zone(eventMapper.getTimeZone());
				} else {
					eventDetails.setTime_zone(eventDetails.getTime_zone());
				}

				if (eventMapper.getAssignedTo() != null) {
					eventDetails.setAssignedTo(eventMapper.getAssignedTo());
				} else {
					eventDetails.setAssignedTo(eventDetails.getAssignedTo());
				}

				if (eventMapper.getComplitionInd() != null && !eventMapper.getComplitionInd().isEmpty()) {
					if (eventMapper.getComplitionInd().equalsIgnoreCase("true")) {
						eventDetails.setComplitionInd(true);
					} else if (eventMapper.getComplitionInd().equalsIgnoreCase("false")) {
						eventDetails.setComplitionInd(false);

					}

				} else {
					eventDetails.setComplitionInd(eventDetails.isComplitionInd());

				}

				if (eventMapper.getContact() != null) {
					eventDetails.setContact(eventMapper.getContact());
				} else {
					eventDetails.setContact(eventDetails.getContact());
				}

				if (eventMapper.getCustomer() != null) {
					eventDetails.setCustomer(eventMapper.getCustomer());
				} else {
					eventDetails.setCustomer(eventDetails.getCustomer());
				}
				if (eventMapper.getOppertunity() != null) {
					eventDetails.setOppertunity(eventMapper.getOppertunity());
				} else {
					eventDetails.setOppertunity(eventDetails.getOppertunity());
				}
				if (null != eventDetails.getAssignedBy()) {
					if (null != eventMapper.getAssignedTo()
							&& !eventDetails.getAssignedBy().equals(eventMapper.getAssignedTo())) {
						eventDetails.setAssignedBy(eventMapper.getUserId());
					} else {
						eventDetails.setAssignedBy(eventDetails.getAssignedBy());
					}
				} else {
					eventDetails.setAssignedBy(eventMapper.getUserId());
				}
				eventDetails.setEvent_id(eventId);
				eventDetails.setLive_ind(true);
				eventDetails.setCreation_date(eventDetails.getCreation_date());
				eventDetails.setUser_id(eventMapper.getUserId());
				eventDetails.setOrganization_id(eventMapper.getOrganizationId());
				eventDetailsRepository.save(eventDetails);

				/* update event address link */

				List<EventAddressLink> eventAddressList = eventAddressRepository.getAddressListByEventId(eventId);
				if (null != eventAddressList && !eventAddressList.isEmpty()) {
					eventAddressList.forEach(eventAddressLink -> {
						eventAddressLink.setLive_ind(false);
						eventAddressRepository.save(eventAddressLink);
					});

				}

				/* insert new address */

				if (null != eventMapper.getAddress()) {
					List<AddressMapper> addressList = eventMapper.getAddress();

					for (AddressMapper addressMapper : addressList) {
						/* insert to address info table */

						AddressInfo addressInfo = new AddressInfo();
						addressInfo.setCreationDate(new Date());
						addressInfo.setCreatorId(eventMapper.getUserId());
						AddressInfo info = addressInfoRepository.save(addressInfo);
						String addressId = info.getId();
						if (null != addressId) {

							/* insert to address details table */
							AddressDetails newAddressDetails = new AddressDetails();
							newAddressDetails.setAddressId(addressId);
							newAddressDetails.setAddressLine1(addressMapper.getAddress1());
							newAddressDetails.setAddressLine2(addressMapper.getAddress2());
							newAddressDetails.setAddressType(addressMapper.getAddressType());
							newAddressDetails.setCountry(addressMapper.getCountry());
							newAddressDetails.setCreationDate(new Date());
							newAddressDetails.setStreet(addressMapper.getStreet());
							newAddressDetails.setCity(addressMapper.getCity());
							newAddressDetails.setPostalCode(addressMapper.getPostalCode());
							newAddressDetails.setTown(addressMapper.getTown());
							newAddressDetails.setLatitude(addressMapper.getLatitude());
							newAddressDetails.setLongitude(addressMapper.getLongitude());
							newAddressDetails.setState(addressMapper.getState());
							newAddressDetails.setLiveInd(true);
							newAddressDetails.setHouseNo(addressMapper.getHouseNo());
							addressDetailsRepository.save(newAddressDetails);

							/* insert to account address link table */

							EventAddressLink eventAddressLink = new EventAddressLink();
							eventAddressLink.setAddress_id(addressId);
							eventAddressLink.setCreation_date(new Date());
							eventAddressLink.setEvent_id(eventId);
							eventAddressLink.setLive_ind(true);
							eventAddressRepository.save(eventAddressLink);

						}
					}
					/* update event employee link table */
					// update event employee link

					List<String> empList = eventMapper.getIncluded();

					empList.add(eventMapper.getUserId());
					if (!eventMapper.getUserId().equals(eventMapper.getAssignedTo())) {
						empList.add(eventMapper.getAssignedTo());
					}
					if (null != empList && !empList.isEmpty()) {

						List<EmployeeEventLink> employeeList = employeeEventRepository.getEmpListByEventId(eventId);

						if (null != employeeList && !employeeList.isEmpty()) {
							employeeList.forEach(employeeEventLink -> {
								employeeEventLink.setLive_ind(false);
								employeeEventRepository.save(employeeEventLink);

							});
						}

						// insert new data to call employee link//

						if (null != empList && !empList.isEmpty()) {
							for (String employeeId : empList) {
								EmployeeEventLink employeeEventLink = new EmployeeEventLink();
								employeeEventLink.setEvent_id(eventId);
								employeeEventLink.setEmployee_id(employeeId);
								employeeEventLink.setCreation_date(new Date());
								employeeEventLink.setLive_ind(true);
								employeeEventRepository.save(employeeEventLink);

							}

						}
					}

				}
				/* insert to Notification Table */
				Notificationparam param = new Notificationparam();
				EmployeeDetails emp = employeeRepository.getEmployeesByuserId(eventMapper.getUserId());
				String name = employeeService.getEmployeeFullNameByObject(emp);
				param.setEmployeeDetails(emp);
				param.setAdminMsg("Event " + "'" + eventMapper.getName() + "' updated by " + name);
				param.setOwnMsg("Event " + eventMapper.getName() + " updated.");
				param.setNotificationType("Event update");
				param.setProcessNmae("Event");
				param.setType("update");
				param.setEmailSubject("Korero alert- Event updated");
				param.setCompanyName(companyName);
				param.setUserId(eventMapper.getUserId());

				if (!eventMapper.getUserId().equals(eventMapper.getAssignedTo())) {
					List<String> assignToUserIds = new ArrayList<>();
					assignToUserIds.add(eventMapper.getAssignedTo());
					param.setAssignToUserIds(assignToUserIds);
					param.setAssignToMsg("Event " + "'" + eventMapper.getName() + "'" + " assigned to "
							+ employeeService.getEmployeeFullName(eventMapper.getAssignedTo()) + " by " + name);
				}
				if (null != eventMapper.getIncluded() && !eventMapper.getIncluded().isEmpty()) {
					List<String> includedids = new ArrayList<>(eventMapper.getIncluded());
					param.setIncludeedUserIds(includedids);
					param.setIncludeMsg(
							"You have been added Event " + "'" + eventMapper.getName() + "'" + " by " + name);
				}
				notificationService.createNotificationForDynamicUsers(param);

				resultMapper = getEventDetailsById(eventId);
			}

		}

		return resultMapper;
	}

	private void updateActivityEventLog(String userType, String callId, String userTypeId, EventMapper mapper) {
		LastActivityLog dbData = lastActivityLogRepository.findByUserTypeAndUserTypeId(userType, userTypeId);
		if (null != dbData) {

			dbData.setActivityId(callId);
			dbData.setActivityType("event");
			dbData.setCreationDate(new Date());
			dbData.setDescription(mapper.getEventDescription());
			try {
				dbData.setStartDate(Utility.getDateFromISOString((mapper.getStartDate())));
				dbData.setEndDate(Utility.getDateFromISOString(mapper.getEndDate()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			dbData.setStartTime(mapper.getStartTime());
			dbData.setEndTime(mapper.getEndTime());
			dbData.setSubject(mapper.getEventSubject());
			dbData.setUserId(mapper.getUserId());
			dbData.setOrganizationId(mapper.getOrganizationId());
			dbData.setTimeZone(mapper.getTimeZone());
			dbData.setUserType(userType);
			dbData.setUserTypeId(userTypeId);
			lastActivityLogRepository.save(dbData);
		} else {
			LastActivityLog dynamo = new LastActivityLog();
			dynamo.setActivityId(callId);
			dynamo.setActivityType("event");
			dynamo.setCreationDate(new Date());
			dynamo.setDescription(mapper.getEventDescription());
			try {
				dynamo.setStartDate(Utility.getDateFromISOString((mapper.getStartDate())));
				dynamo.setEndDate(Utility.getDateFromISOString(mapper.getEndDate()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			dynamo.setStartTime(mapper.getStartTime());
			dynamo.setEndTime(mapper.getEndTime());
			dynamo.setSubject(mapper.getEventSubject());
			dynamo.setUserId(mapper.getUserId());
			dynamo.setOrganizationId(mapper.getOrganizationId());
			dynamo.setTimeZone(mapper.getTimeZone());
			dynamo.setUserType(userType);
			dynamo.setUserTypeId(userTypeId);
			lastActivityLogRepository.save(dynamo);
		}
	}

	@Override
	public HashMap getEventTypeCountByOrgId(String orgId) {
		HashMap map = new HashMap();
		List<EventType> list = eventTypeRepository.findByOrgIdAndLiveInd(orgId, true);
		map.put("EventTypeCount", list.size());
		return map;
	}

	@Override
	public ByteArrayInputStream exportEventTypeListToExcel(List<EventMapper> list) {
		XSSFWorkbook workbook = new XSSFWorkbook();

		// Create a blank sheet
		XSSFSheet sheet = workbook.createSheet("candidate");

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
		for (int i = 0; i < event_headings.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(event_headings[i]);
			cell.setCellStyle(headerCellStyle);
		}

		int rowNum = 1;
		if (null != list && !list.isEmpty()) {
			for (EventMapper mapper : list) {
				Row row = sheet.createRow(rowNum++);
				row.createCell(0).setCellValue(mapper.getEventType());
			}
		}
		// Resize all columns to fit the content size
		for (int i = 0; i < event_headings.length; i++) {
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
	public List<EventViewMapper> getEventDetailsByNameByOrgId(String name, String orgId) {
		List<EventViewMapper> mapperList = new ArrayList<>();

		EventType eventType = eventTypeRepository.findByEventTypeAndOrgIdAndLiveInd(name, orgId, true);
		if (eventType != null) {
			List<EventDetails> list = eventDetailsRepository
					.getByEventTypeAndOrgId(eventType.getEventTypeId(), orgId);
			if (list != null && !list.isEmpty()) {
				mapperList = list.stream().map(li -> getEventDetails(li.getEvent_id())).collect(Collectors.toList());
			}
		}

		return mapperList;
	}

	@Override
	public List<EventViewMapper> getEventDetailsByNameForTeam(String name, String userId, String orgId) {
		Set<String> userIds = employeeRepository.getEmployeeDetailsByReportingManagerId(userId).stream()
				.map(EmployeeDetails::getUserId).collect(Collectors.toSet());
		userIds.add(userId);

		List<String> userIdss = new ArrayList<>(userIds);

		List<EventViewMapper> mapperList = new ArrayList<>();

		EventType eventType = eventTypeRepository.findByEventTypeAndOrgIdAndLiveInd(name, orgId, true);
		if (eventType != null) {
			List<EventDetails> list = eventDetailsRepository
					.getByEventTypeAndLiveIndAndUserIds(eventType.getEventTypeId(), userIds);
			if (list != null && !list.isEmpty()) {
				mapperList = list.stream().map(li -> getEventDetails(li.getEvent_id())).collect(Collectors.toList());
			}
		}

		return mapperList;
	}

	@Override
	public List<EventViewMapper> getEventDetailsByNameByUserId(String name, String userId, String orgId) {
		List<EventViewMapper> mapperList = new ArrayList<>();

		EventType eventType = eventTypeRepository.findByEventTypeAndOrgIdAndLiveInd(name, orgId, true);
		if (eventType != null) {
			List<EventDetails> list = eventDetailsRepository
					.getByEventTypeAndLiveIndAndUserId(eventType.getEventTypeId(), userId);
			if (list != null && !list.isEmpty()) {
				mapperList = list.stream().map(li -> getEventDetails(li.getEvent_id())).collect(Collectors.toList());
			}
		}

		return mapperList;
	}

}
