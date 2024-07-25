package com.app.employeePortal.call.service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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

import com.app.employeePortal.Opportunity.entity.OpportunityDetails;
import com.app.employeePortal.Opportunity.entity.OpportunityNotesLink;
import com.app.employeePortal.Opportunity.repository.OpportunityDetailsRepository;
import com.app.employeePortal.Opportunity.repository.OpportunityNotesLinkRepository;
import com.app.employeePortal.call.entity.CallCandidateLink;
import com.app.employeePortal.call.entity.CallDetails;
import com.app.employeePortal.call.entity.CallInfo;
import com.app.employeePortal.call.entity.CallNotesLink;
import com.app.employeePortal.call.entity.ContactCallLink;
import com.app.employeePortal.call.entity.CustomerCallLink;
import com.app.employeePortal.call.entity.EmployeeCallLink;
import com.app.employeePortal.call.entity.InvestorCallLink;
import com.app.employeePortal.call.entity.InvestorLeadsCallLink;
import com.app.employeePortal.call.entity.LeadsCallLink;
import com.app.employeePortal.call.mapper.CallMapper;
import com.app.employeePortal.call.mapper.CallViewMapper;
import com.app.employeePortal.call.mapper.DonotCallMapper;
import com.app.employeePortal.call.repository.CallCandidateLinkRepository;
import com.app.employeePortal.call.repository.CallDetailsRepository;
import com.app.employeePortal.call.repository.CallInfoRepository;
import com.app.employeePortal.call.repository.CallNotesLinkRepository;
import com.app.employeePortal.call.repository.ContactCallLinkRepo;
import com.app.employeePortal.call.repository.CustomerCallLinkRepo;
import com.app.employeePortal.call.repository.EmployeeCallRepository;
import com.app.employeePortal.call.repository.InvestorCallLinkRepo;
import com.app.employeePortal.call.repository.InvestorLeadsCallLinkRepo;
import com.app.employeePortal.call.repository.LeadsCallLinkRepo;
import com.app.employeePortal.candidate.entity.CandidateDetails;
import com.app.employeePortal.candidate.repository.CandidateDetailsRepository;
import com.app.employeePortal.contact.entity.ContactDetails;
import com.app.employeePortal.contact.entity.ContactNotesLink;
import com.app.employeePortal.contact.mapper.ContactViewMapper;
import com.app.employeePortal.contact.repository.ContactNotesLinkRepository;
import com.app.employeePortal.contact.repository.ContactRepository;
import com.app.employeePortal.customer.entity.Customer;
import com.app.employeePortal.customer.repository.CustomerRepository;
import com.app.employeePortal.employee.entity.EmployeeDetails;
import com.app.employeePortal.employee.entity.Notes;
import com.app.employeePortal.employee.mapper.EmployeeShortMapper;
import com.app.employeePortal.employee.mapper.NotesMapper;
import com.app.employeePortal.employee.repository.EmployeeRepository;
import com.app.employeePortal.employee.repository.NotesRepository;
import com.app.employeePortal.employee.service.EmployeeService;
import com.app.employeePortal.leads.mapper.ActivityMapper;
import com.app.employeePortal.notification.entity.Notificationparam;
import com.app.employeePortal.notification.repository.NotificationRepository;
import com.app.employeePortal.notification.service.NotificationService;
import com.app.employeePortal.task.entity.LastActivityLog;
import com.app.employeePortal.task.repository.LastActivityLogRepository;
import com.app.employeePortal.util.Utility;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;

@Service
@Transactional
public class CallServiceImpl implements CallService {

	@Autowired
	CallInfoRepository callInfoRepository;

	@Autowired
	CallDetailsRepository callDetailsRepository;

	@Autowired
	EmployeeCallRepository employeeCallRepository;
	@Autowired
	EmployeeRepository employeeRepository;

	final Configuration configuration;
	@Autowired
	EmployeeService employeeService;
	@Autowired
	ContactRepository contactRepository;
	@Autowired
	NotificationRepository notificationRepository;
	@Autowired
	CandidateDetailsRepository candidateDetailsRepository;
	@Autowired
	CallCandidateLinkRepository callCandidateLinkRepository;

	@Autowired
	LeadsCallLinkRepo leadsCallLinkRepo;
	@Autowired
	InvestorLeadsCallLinkRepo investorLeadsCallLinkRepo;
	@Autowired
	OpportunityDetailsRepository opportunityDetailsRepository;
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	CallNotesLinkRepository callNotesLinkRepository;
	@Autowired
	NotesRepository notesRepository;
	@Autowired
	CustomerCallLinkRepo customerCallLinkRepo;
	@Autowired
	ContactCallLinkRepo contactCallLinkRepo;
	@Autowired
	OpportunityNotesLinkRepository opportunityNotesLinkRepository;
	@Autowired
	ContactNotesLinkRepository contactNotesLinkRepository;
	@Autowired
	InvestorCallLinkRepo investorCallLinkRepo;
	@Autowired
	LastActivityLogRepository lastActivityLogRepository;

	public CallServiceImpl(Configuration configuration) {
		this.configuration = configuration;
	}

	@Autowired
	NotificationService notificationService;
	@Value("${companyName}")
	private String companyName;

	@Override
	public CallViewMapper saveCallProcess(CallMapper callMapper) throws IOException, TemplateException {
		/* insert to call info table */

		CallInfo callInfo = new CallInfo();
		callInfo.setCreation_date(new Date());
		CallInfo info = callInfoRepository.save(callInfo);

		CallViewMapper resultMapper = null;
		String callId = info.getCall_id();
		if (null != callId) {
			/* insert to call details table */

			CallDetails callDetails = new CallDetails();
			callDetails.setCall_id(callId);
			callDetails.setCreation_date(new Date());
			callDetails.setCall_category(callMapper.getCallCategory());
			callDetails.setCall_description(callMapper.getCallDescription());
			callDetails.setTime_zone(callMapper.getTimeZone());
			callDetails.setCall_start_time(callMapper.getStartTime());
			callDetails.setCall_end_time(callMapper.getEndTime());
			callDetails.setCallType(callMapper.getCallType());
			callDetails.setSubject(callMapper.getCallPurpose());
			callDetails.setLive_ind(true);
			callDetails.setMode(callMapper.getMode());
			callDetails.setModeType(callMapper.getModeType());
			callDetails.setModeLink(callMapper.getModeLink());
			callDetails.setMode(callId);
			callDetails.setRemind_time(callMapper.getRemindTime());
			callDetails.setRemind_ind(callMapper.isRemindInd());
			callDetails.setAssignedTo(callMapper.getAssignedTo());
			callDetails.setComplitionInd(false);
			callDetails.setRating(0);
			callDetails.setAssignedBy(callMapper.getUserId());

			try {

				callDetails.setCall_start_date(Utility.getDateFromISOString((callMapper.getStartDate())));
				callDetails.setCall_end_date(Utility.getDateFromISOString(callMapper.getEndDate()));

			} catch (Exception e) {
				e.printStackTrace();
			}

			callDetails.setUser_id(callMapper.getUserId());
			callDetails.setOrganization_id(callMapper.getOrganizationId());
			callDetails.setContact(callMapper.getContactId());
			callDetails.setCandidateId(callMapper.getCandidateId());
			callDetails.setCustomer(callMapper.getCustomer());
			callDetails.setOppertunity(callMapper.getOppertunity());
			callDetailsRepository.save(callDetails);

			/* insert to employee call link table */

			List<String> empList = callMapper.getIncluded();
			empList.add(callMapper.getUserId());
			if (!callMapper.getUserId().equals(callMapper.getAssignedTo())) {
				empList.add(callMapper.getAssignedTo());
			}

			if (null != empList && !empList.isEmpty()) {
				for (String employeeId : empList) {

					EmployeeCallLink employeeCallLink = new EmployeeCallLink();
					employeeCallLink.setCall_id(callId);
					employeeCallLink.setEmployee_id(employeeId);
					employeeCallLink.setCreation_date(new Date());
					employeeCallLink.setLive_ind(true);
					employeeCallRepository.save(employeeCallLink);

//					EmployeeDetails employeeDetails1 = employeeRepository.getEmployeeByUserId(employeeId);
//					if (null != employeeDetails1) {

//                        String fromEmail = "support@innoverenit.com";
//                        String to = employeeDetails1.getEmailId();
//                        String subject = " ";
//                        String message = "";
//
//                        message = getCallEmailContent(employeeDetails1);
//
//                        System.out.println("MSG>>" + message);
//                        String serverUrl = "https://develop.tekorero.com/kite/email/send";
//                        HttpHeaders headers = new HttpHeaders();
//                        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
//                        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
//                        body.add("fromEmail", fromEmail);
//                        body.add("message", message);
//                        body.add("subject", subject);
//                        body.add("toEmail", to);
//                        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
//                        RestTemplate restTemplate = new RestTemplate();
//                        ResponseEntity<String> response = restTemplate.postForEntity(serverUrl, requestEntity, String.class);

//                    EmployeeDetails emp = employeeRepository.getEmployeesByuserId(employeeId);				

//					}

				}
			}

			/* insert to CallCandidateLink table */

			if (!StringUtils.isEmpty(callMapper.getCandidateId())) {
				CallCandidateLink callCandidateLink = new CallCandidateLink();
				callCandidateLink.setCallId(callId);
				callCandidateLink.setCandidateId(callMapper.getCandidateId());
				callCandidateLink.setCreationDate(new Date());
				callCandidateLink.setLiveInd(true);
				callCandidateLinkRepository.save(callCandidateLink);
				updateActivityCallLog("candidate", callId, callMapper.getCandidateId(), callMapper);

			}

			/* insert to LeadsCallLink table */
			if (!StringUtils.isEmpty(callMapper.getLeadsId())) {
				LeadsCallLink leadsCallLink = new LeadsCallLink();
				leadsCallLink.setCallId(callId);
				leadsCallLink.setLeadsId(callMapper.getLeadsId());
				leadsCallLink.setCreationDate(new Date());
				leadsCallLink.setLiveInd(true);
				leadsCallLinkRepo.save(leadsCallLink);
				updateActivityCallLog("leads", callId, callMapper.getLeadsId(), callMapper);
			}

			/* insert to InvestorLeadsCallLink table */
			if (!StringUtils.isEmpty(callMapper.getInvestorLeadsId())) {
				InvestorLeadsCallLink investorCallLink = new InvestorLeadsCallLink();
				investorCallLink.setCallId(callId);
				investorCallLink.setInvestorLeadsId(callMapper.getInvestorLeadsId());
				investorCallLink.setCreationDate(new Date());
				investorCallLink.setLiveInd(true);
				investorLeadsCallLinkRepo.save(investorCallLink);
				updateActivityCallLog("investorLeads", callId, callMapper.getInvestorLeadsId(), callMapper);
			}

			/* insert to CustomerCallLink table */
			if (!StringUtils.isEmpty(callMapper.getCustomer())) {
				CustomerCallLink customerCallLink = new CustomerCallLink();
				customerCallLink.setCallId(callId);
				customerCallLink.setCustomerId(callMapper.getCustomer());
				customerCallLink.setCreationDate(new Date());
				customerCallLink.setLiveInd(true);
				customerCallLinkRepo.save(customerCallLink);
				updateActivityCallLog("customer", callId, callMapper.getCustomer(), callMapper);
			}

			/* insert to ContactCallLink table */
			if (!StringUtils.isEmpty(callMapper.getContactId())) {
				ContactCallLink contactCallLink = new ContactCallLink();
				contactCallLink.setCallId(callId);
				contactCallLink.setContactId(callMapper.getContactId());
				contactCallLink.setCreationDate(new Date());
				contactCallLink.setLiveInd(true);
				contactCallLinkRepo.save(contactCallLink);
				updateActivityCallLog("contact", callId, callMapper.getContactId(), callMapper);
			}
			/* insert to InvestorCallLink table */
			if (!StringUtils.isEmpty(callMapper.getInvestorId())) {
				InvestorCallLink investorLink = new InvestorCallLink();
				investorLink.setCallId(callId);
				investorLink.setInvestorId(callMapper.getInvestorId());
				investorLink.setCreationDate(new Date());
				investorLink.setLiveInd(true);
				investorCallLinkRepo.save(investorLink);
				updateActivityCallLog("investor", callId, callMapper.getInvestorId(), callMapper);
			}
			Notes notes = new Notes();
			notes.setCreation_date(new Date());
			notes.setNotes(callMapper.getCallDescription());
			notes.setLiveInd(true);
			Notes note = notesRepository.save(notes);

			String notesId = note.getNotes_id();
			/* insert to CallNoteLink table */

			CallNotesLink callNote = new CallNotesLink();
			callNote.setCallId(callId);
			callNote.setNotesId(notesId);
			callNote.setCreationDate(new Date());
			callNotesLinkRepository.save(callNote);

			String taskNote1 = callMapper.getCallDescription();
			String link = callMapper.getModeLink();
			String msg = "";
			if (taskNote1 != null && link != null) {
				msg = "\n" + "Notes :" + taskNote1 + "\n" + "Link :" + link;
			} else if (taskNote1 == null && link != null) {
				msg = "\n" + "Link :" + link;
			} else if (taskNote1 != null && link == null) {
				msg = "\n" + "Notes :" + taskNote1;
			}

			if (callMapper.getCustomer() != null && !callMapper.getCustomer().isEmpty()
					&& !callMapper.getContactId().isEmpty() && callMapper.getContactId() != null) {
				Customer customer = customerRepository.getcustomerDetailsById(callMapper.getCustomer());
				ContactDetails contact = contactRepository.getcontactDetailsById(callMapper.getContactId());
				msg = "\n" + customer.getName() + "\n"
						+ Utility.FullName(contact.getFirst_name(), contact.getMiddle_name(), contact.getLast_name());
			} else if (callMapper.getCustomer() == null && callMapper.getCustomer().isEmpty()
					&& callMapper.getContactId() != null && !callMapper.getContactId().isEmpty()) {
				ContactDetails contact = contactRepository.getcontactDetailsById(callMapper.getContactId());
				msg = "\n"
						+ Utility.FullName(contact.getFirst_name(), contact.getMiddle_name(), contact.getLast_name());
			} else if (callMapper.getCustomer() != null && !callMapper.getCustomer().isEmpty()
					&& callMapper.getContactId() == null && callMapper.getContactId().isEmpty()) {
				Customer customer = customerRepository.getcustomerDetailsById(callMapper.getCustomer());
				msg = "\n" + customer.getName();
			}

			/* insert to Notification Table */
			Notificationparam param = new Notificationparam();
			EmployeeDetails emp = employeeRepository.getEmployeesByuserId(callMapper.getUserId());
			String name = employeeService.getEmployeeFullNameByObject(emp);
			param.setEmployeeDetails(emp);
			param.setAdminMsg("Call scheduled for " + callMapper.getStartDate() + ", " + "'"
					+ callMapper.getCallPurpose() + "' created by " + name + msg);
			param.setOwnMsg("Call scheduled for " + callMapper.getStartDate() + ", " + callMapper.getCallPurpose()
					+ " created." + msg);
			param.setNotificationType("Call creation");
			param.setProcessNmae("Call");
			param.setType("create");
			param.setEmailSubject("Korero alert - Call created");
			param.setCompanyName(companyName);
			param.setUserId(callMapper.getUserId());

			if (!callMapper.getUserId().equals(callMapper.getAssignedTo())) {
				List<String> assignToUserIds = new ArrayList<>();
				assignToUserIds.add(callMapper.getAssignedTo());
				param.setAssignToUserIds(assignToUserIds);
				param.setAssignToMsg("Call " + "'" + callMapper.getCallPurpose() + "' on " + callMapper.getStartDate()
						+ " assigned to " + employeeService.getEmployeeFullName(callMapper.getAssignedTo()) + " by "
						+ name + msg);
			}
			if (null != callMapper.getIncluded() && !callMapper.getIncluded().isEmpty()) {
				List<String> includedids = new ArrayList<>(callMapper.getIncluded());
				param.setIncludeedUserIds(includedids);
				param.setIncludeMsg("You have been added to the Call " + "'" + callMapper.getCallPurpose() + "' on "
						+ callMapper.getStartDate() + " by " + name + msg);
			}
			notificationService.createNotificationForDynamicUsers(param);

		}
		resultMapper = getCallDetails(callId);

		return resultMapper;
	}

	@Override
	public CallViewMapper getCallDetails(String callId) {

		CallDetails callDetails = callDetailsRepository.getCallDetailsById(callId);
		CallViewMapper callMapper = new CallViewMapper();

		if (null != callDetails) {

			callMapper.setCallId(callId);
			callMapper.setCallDescription(callDetails.getCall_description());
			callMapper.setCallType(callDetails.getCallType());
			callMapper.setCallCategory(callDetails.getCall_category());
			callMapper.setTimeZone(callDetails.getTime_zone());
			callMapper.setStatus(callDetails.getCall_status());
			callMapper.setCallPurpose(callDetails.getSubject());
			callMapper.setCreationDate(Utility.getISOFromDate(callDetails.getCreation_date()));
			callMapper.setMode(callDetails.getMode());
			callMapper.setModeLink(callDetails.getModeLink());
			callMapper.setModeType(callDetails.getModeType());
			callMapper.setRemindInd(callDetails.isRemind_ind());
			callMapper.setRemindTime(callDetails.getRemind_time());
			callMapper.setUpdateDate(Utility.getISOFromDate(callDetails.getUpdateDate()));
			callMapper.setCompletionInd(callDetails.isComplitionInd());
			callMapper.setRating(callDetails.getRating());
			callMapper.setAssignedTo(employeeService.getEmployeeFullName(callDetails.getAssignedBy()));

			OpportunityDetails opportunityDetails = opportunityDetailsRepository
					.getopportunityDetailsById(callDetails.getOppertunity());
			if (opportunityDetails != null) {
				callMapper.setOppertunity(opportunityDetails.getOpportunityName());

			}
			Customer customer = customerRepository.getCustomerDetailsByCustomerId(callDetails.getCustomer());
			if (customer != null) {
				callMapper.setCustomer(customer.getName());

			}
			EmployeeDetails employeeDetails = employeeRepository.getEmployeeByUserId(callDetails.getAssignedTo());
			if (null != employeeDetails) {
				String middleName = " ";
				String lastName = " ";

				if (null != employeeDetails.getLastName()) {

					lastName = employeeDetails.getLastName();
				}
				if (null != employeeDetails.getMiddleName()) {

					middleName = employeeDetails.getMiddleName();
					callMapper.setAssignedTo(employeeDetails.getFirstName() + " " + middleName + " " + lastName);
				} else {
					callMapper.setAssignedTo(employeeDetails.getFirstName() + " " + lastName);
				}
			}

			EmployeeDetails employeeDetails1 = employeeRepository.getEmployeeByUserId(callDetails.getUser_id());
			if (null != employeeDetails1) {
				String middleName = " ";
				String lastName = " ";

				if (null != employeeDetails1.getLastName()) {

					lastName = employeeDetails1.getLastName();
				}
				if (null != employeeDetails1.getMiddleName()) {

					middleName = employeeDetails1.getMiddleName();
					callMapper.setWoner(employeeDetails1.getFirstName() + " " + middleName + " " + lastName);
				} else {
					callMapper.setWoner(employeeDetails1.getFirstName() + " " + lastName);
				}
			}

			if (null != callDetails.getCall_start_date()) {

				callMapper.setStartDate(Utility.getISOFromDate(callDetails.getCall_start_date()));

			} else {
				callMapper.setStartDate("");
			}
			if (null != callDetails.getCall_end_date()) {

				callMapper.setEndDate(Utility.getISOFromDate(callDetails.getCall_end_date()));
			} else {
				callMapper.setEndDate("");
			}

			/*
			 * if (callDetails.isCompletion_ind()) { callMapper.setCompletionInd("true"); }
			 * else { callMapper.setCompletionInd("false");
			 * 
			 * }
			 */

			if (callDetails.getContact() != null && callDetails.getContact().trim().length() > 0) {
				ContactDetails contact = contactRepository.getContactDetailsById(callDetails.getContact());
				if (contact != null) {
					if (contact.getFirst_name() != null && contact.getLast_name() != null) {
						callMapper.setContactName(contact.getFirst_name() + " " + contact.getLast_name());
					}
				}
			}

			CallCandidateLink callCandidateLink = callCandidateLinkRepository.getcallOfCandidateByCallId(callId);
			if (null != callCandidateLink) {
				if (null != callCandidateLink.getCandidateId()) {
					CandidateDetails candidateDetails = candidateDetailsRepository
							.getcandidateDetailsById(callCandidateLink.getCandidateId());
					callMapper.setCandidateId(candidateDetails.getCandidateId());
					String middleName = " ";
					String lastName = " ";

					if (null != candidateDetails.getLastName()) {

						lastName = candidateDetails.getLastName();
					}
					if (null != candidateDetails.getMiddleName()) {

						middleName = candidateDetails.getMiddleName();
						callMapper
								.setCandidateName(candidateDetails.getFirstName() + " " + middleName + " " + lastName);
					} else {
						callMapper.setCandidateName(candidateDetails.getFirstName() + " " + lastName);
					}
				}
			} else {

				callMapper.setCandidateId("");
				callMapper.setCandidateName("");
			}

			// callMapper.setContactName(contact.getFirst_name()+"
			// "+contact.getLast_name());
			// set call owner list
			List<EmployeeShortMapper> empList = new ArrayList<EmployeeShortMapper>();

			List<EmployeeCallLink> employeeList = employeeCallRepository.getEmpListByCallId(callId);
			System.out.println("employeeList====="+employeeList.size());
			if (null != employeeList && !employeeList.isEmpty()) {
				for (EmployeeCallLink employeeCallLink : employeeList) {
					if (null != employeeCallLink.getEmployee_id()) {
						System.out.println("EmployeeCallLinkemployeeId=111===="+employeeCallLink.getEmployee_id());
						if (!employeeCallLink.getEmployee_id().equals(callDetails.getAssignedTo())) {
							System.out.println("EmployeeCallLinkemployeeId2222===="+employeeCallLink.getEmployee_id());
							System.out.println("callDetails---employeeId=111===="+callDetails.getAssignedTo());
							if (!employeeCallLink.getEmployee_id().equals(callDetails.getUser_id())) {
								
								System.out.println("EmployeeCallLinkemployeeId3333===="+employeeCallLink.getEmployee_id());
								System.out.println("callDetails---employeeId=222===="+callDetails.getAssignedTo());
								EmployeeShortMapper employeeMapper = new EmployeeShortMapper();

								String employeeId = "";
								EmployeeDetails employeeDetails2 = employeeRepository
										.getEmployeeByUserId(employeeCallLink.getEmployee_id());
								if (null != employeeDetails2) {
									String middleName = " ";
									String lastName = " ";

									if (null != employeeDetails2.getLastName()) {

										lastName = employeeDetails2.getLastName();
									}
									if (null != employeeDetails2.getMiddleName()) {

										middleName = employeeDetails2.getMiddleName();
										employeeMapper.setEmpName(
												employeeDetails2.getFirstName() + " " + middleName + " " + lastName);
									} else {
										employeeMapper.setEmpName(employeeDetails2.getFirstName() + " " + lastName);
									}
									employeeId = employeeDetails2.getEmployeeId();
								}

								employeeMapper.setEmployeeId(employeeId);
								// employeeMapper.setUserId(employeeId);
								System.out.println("employeeIdFFFF===="+employeeId);
								empList.add(employeeMapper);
							}
						}
					}
				}

			}
			callMapper.setIncluded(empList);
		}
		// Need to check for the candidate
		return callMapper;
	}

	@Override
	public List<CallViewMapper> getCallDetailsByEmployeeIdPageWise(String employeeId, int pageNo, int pageSize) {
		Pageable paging1 = PageRequest.of(pageNo, pageSize, Sort.by("creation_date").descending());
//        return employeeCallRepository.getCallListByEmpId(employeeId,paging1).stream().map(c -> getCallDetails(c.getCall_id())).collect(Collectors.toList());
		Page<EmployeeCallLink> list = employeeCallRepository.getCallListByEmpId(employeeId, paging1);

		return list.stream().map(employeeCallLink -> {
			CallViewMapper mapper = getCallDetails(employeeCallLink.getCall_id());
			mapper.setPageCount(list.getTotalPages());
			mapper.setDataCount(list.getSize());
			mapper.setListCount(list.getTotalElements());
			return mapper;
		}).collect(Collectors.toList());
	}

	@Override
	public List<CallViewMapper> getCallDetailsByEmployeeId(String employeeId) {
		return employeeCallRepository.getByEmployeeId(employeeId).stream().map(c -> getCallDetails(c.getCall_id()))
//                .sorted(Comparator.comparing(mapper -> mapper != null ? mapper.getCreationDate() : null))
				.collect(Collectors.toList());

	}

	@Override
	public List<CallViewMapper> getCallDetailsByOrganizationId(String organizationId) {
		return callDetailsRepository.getCallListByOrgId(organizationId).stream()
				.map(c -> getCallDetails(c.getCall_id())).collect(Collectors.toList());
	}

	@Override
	public CallViewMapper updateCallDetails(String callId, CallMapper callMapper)
			throws IOException, TemplateException {
		CallViewMapper resultMapper = new CallViewMapper();
		if (null != callId) {

			CallDetails callDetails = callDetailsRepository.getCallDetailsById(callId);

			if (null != callDetails) {

				callDetails.setLive_ind(false);

				callDetailsRepository.save(callDetails);

				CallDetails callDetailsNewData = new CallDetails();
				if (callMapper.getCallCategory() != null) {
					callDetailsNewData.setCall_category(callMapper.getCallCategory());
				} else {
					callDetailsNewData.setCall_category(callDetails.getCall_category());
				}

//                if (callMapper.getCallDescription() != null) {
//                    callDetailsNewData.setCall_description(callMapper.getCallDescription());
//                } else {
//                    callDetailsNewData.setCall_description(callDetails.getCall_description());
//                }
				if (callMapper.getCallDescription() != null) {
					List<CallNotesLink> list = callNotesLinkRepository.getNoteByCallId(callId);
					if (null != list && !list.isEmpty()) {
						list.sort((m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
						Notes notes = notesRepository.findByNoteId(list.get(0).getNotesId());
						if (null != notes) {
							notes.setNotes(callMapper.getCallDescription());
							notesRepository.save(notes);
						}
					}
				}

				if (callMapper.getCallPurpose() != null) {
					callDetailsNewData.setSubject(callMapper.getCallPurpose());
				} else {
					callDetailsNewData.setSubject(callDetails.getSubject());
				}
				if (callMapper.getCallType() != null) {
					callDetailsNewData.setCallType(callMapper.getCallType());
				} else {
					callDetailsNewData.setCallType(callDetails.getCallType());
				}

				if (callMapper.getMode() != null) {
					callDetailsNewData.setMode(callMapper.getMode());
				} else {
					callDetailsNewData.setMode(callDetails.getMode());

				}
				if (callMapper.getModeType() != null) {
					callDetailsNewData.setModeType(callMapper.getModeType());
				} else {
					callDetailsNewData.setModeType(callDetails.getModeType());
				}
				if (callMapper.getModeLink() != null) {
					callDetailsNewData.setModeLink(callMapper.getModeLink());
				} else {
					callDetailsNewData.setModeLink(callDetails.getModeLink());
				}

				if (callMapper.getStartDate() != null) {
					try {
						callDetailsNewData
								.setCall_start_date(Utility.getDateFromISOString((callMapper.getStartDate())));
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					callDetailsNewData.setCall_start_date(callDetails.getCall_start_date());
				}
				if (callMapper.getStartTime() != 0) {
					callDetailsNewData.setCall_start_time(callMapper.getStartTime());
				} else {
					callDetailsNewData.setCall_start_time(callDetails.getCall_start_time());
				}
				if (callMapper.getEndDate() != null) {
					try {
						callDetailsNewData.setCall_end_date(Utility.getDateFromISOString(callMapper.getEndDate()));
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					callDetailsNewData.setCall_end_date(callDetails.getCall_end_date());
				}
				if (callMapper.getEndTime() != 0) {
					callDetailsNewData.setCall_end_time(callMapper.getEndTime());
				} else {
					callDetailsNewData.setCall_end_time(callDetails.getCall_end_time());
				}

				if (callMapper.getTimeZone() != null) {
					callDetailsNewData.setTime_zone(callMapper.getTimeZone());
				} else {
					callDetailsNewData.setTime_zone(callDetails.getTime_zone());
				}

				if (callMapper.getAssignedTo() != null) {
					callDetailsNewData.setAssignedTo(callMapper.getAssignedTo());
				} else {
					callDetailsNewData.setAssignedTo(callDetails.getAssignedTo());
				}

				if (callMapper.getComplitionInd() != null && !callMapper.getComplitionInd().isEmpty()) {
					if (callMapper.getComplitionInd().equalsIgnoreCase("true")) {
						callDetailsNewData.setComplitionInd(true);
					} else if (callMapper.getComplitionInd().equalsIgnoreCase("false")) {
						callDetailsNewData.setComplitionInd(false);

					}

				} else {
					callDetailsNewData.setComplitionInd(callDetails.isComplitionInd());

				}
				if (callMapper.getCustomer() != null) {
					callDetailsNewData.setCustomer(callMapper.getCustomer());
				} else {
					callDetailsNewData.setCustomer(callDetails.getCustomer());
				}
				if (callMapper.getOppertunity() != null) {
					callDetailsNewData.setOppertunity(callMapper.getOppertunity());
				} else {
					callDetailsNewData.setOppertunity(callDetails.getOppertunity());
				}
				if (null != callDetails.getAssignedBy()) {
					if (null != callMapper.getAssignedTo()
							&& !callDetails.getAssignedBy().equals(callMapper.getAssignedTo())) {
						callDetailsNewData.setAssignedBy(callMapper.getUserId());
					} else {
						callDetailsNewData.setAssignedBy(callDetails.getAssignedBy());
					}
				} else {
					callDetails.setAssignedBy(callMapper.getUserId());
				}

				callDetailsNewData.setCall_id(callId);
				callDetailsNewData.setUser_id(callMapper.getUserId());
				callDetailsNewData.setOrganization_id(callMapper.getOrganizationId());
				callDetailsNewData.setLive_ind(true);
				callDetailsNewData.setCreation_date(callDetails.getCreation_date());
				callDetailsRepository.save(callDetailsNewData);

				// update call employee link

				List<String> empList = callMapper.getIncluded();
				empList.add(callMapper.getUserId());
				if (!callMapper.getUserId().equals(callMapper.getAssignedTo())) {
					empList.add(callMapper.getAssignedTo());
				}
				if (null != empList && !empList.isEmpty()) {
					List<EmployeeCallLink> employeeList = employeeCallRepository.getEmpListByCallId(callId);

					if (null != employeeList && !employeeList.isEmpty()) {
						for (EmployeeCallLink employeeCallLink : employeeList) {

							employeeCallLink.setLive_ind(false);
							employeeCallRepository.save(employeeCallLink);

						}
					}

					// insert new data to call employee link//

					if (null != empList && !empList.isEmpty()) {
						for (String employeeId : empList) {
							EmployeeCallLink employeeCallLink = new EmployeeCallLink();
							employeeCallLink.setCall_id(callId);
							employeeCallLink.setEmployee_id(employeeId);
							employeeCallLink.setCreation_date(new Date());
							employeeCallLink.setLive_ind(true);
							employeeCallRepository.save(employeeCallLink);

						}

					}
				}

				String taskNote1 = callMapper.getCallDescription();
				String link = callMapper.getModeLink();
				String msg = "";
				if (taskNote1 != null && link != null) {
					msg = "/n" + "Notes :" + taskNote1 + "/n" + "Link :" + link;
				} else if (taskNote1 == null && link != null) {
					msg = "/n" + "Link :" + link;
				} else if (taskNote1 != null && link == null) {
					msg = "/n" + "Notes :" + taskNote1;
				}
				/* insert to Notification Table */
				Notificationparam param = new Notificationparam();
				EmployeeDetails emp = employeeRepository.getEmployeesByuserId(callMapper.getUserId());
				String name = employeeService.getEmployeeFullNameByObject(emp);
				param.setEmployeeDetails(emp);
				param.setAdminMsg("Call " + "'" + callMapper.getCallPurpose() + "' updated by " + name + msg);
				param.setOwnMsg("Call " + callMapper.getCallPurpose() + " created." + msg);
				param.setNotificationType("Call update");
				param.setProcessNmae("Call");
				param.setType("update");
				param.setEmailSubject("Korero alert - Call info updated");
				param.setCompanyName(companyName);
				param.setUserId(callMapper.getUserId());

				if (!callMapper.getUserId().equals(callMapper.getAssignedTo())) {
					List<String> assignToUserIds = new ArrayList<>();
					assignToUserIds.add(callMapper.getAssignedTo());
					param.setAssignToUserIds(assignToUserIds);
					param.setAssignToMsg("Call " + "'" + callMapper.getCallPurpose() + "'" + " assigned to "
							+ employeeService.getEmployeeFullName(callMapper.getAssignedTo()) + " by " + name + msg);
				}
				if (null != callMapper.getIncluded() && !callMapper.getIncluded().isEmpty()) {
					List<String> includedids = new ArrayList<>(callMapper.getIncluded());
					param.setIncludeedUserIds(includedids);
					param.setIncludeMsg("You have been added Call " + "'" + callMapper.getCallPurpose() + "'" + " by "
							+ name + msg);
				}
				notificationService.createNotificationForDynamicUsers(param);

				resultMapper = getCallDetails(callId);

			}
		}
		return resultMapper;
	}

	@Override
	public boolean delinkCall(String employeeId, String callId) {

		EmployeeCallLink employeeCallLink = employeeCallRepository.getEmployeeCallLink(employeeId, callId);

		if (null != employeeCallLink) {
			employeeCallLink.setLive_ind(false);

			CallDetails callDetails = callDetailsRepository.getCallDetailsById(callId);
			if (null != callDetails) {
				callDetails.setLive_ind(false);
				callDetailsRepository.save(callDetails);
			}
			employeeCallRepository.save(employeeCallLink);
			return true;
		}

		return false;
	}

	@Override
	public List<CallViewMapper> getcallListOfACandidate(String candidateId) {
		return callCandidateLinkRepository.getCallListByCandidateId(candidateId).stream()
				.map(c -> getCallDetails(c.getCallId())).collect(Collectors.toList());
	}

	@Override
	public boolean doNotCallCandidate(CallMapper callMapper, String candidateId) {
		CandidateDetails candidatedetails = candidateDetailsRepository.getcandidateDetailsById(candidateId);
		String callId = null;
		if (null != candidatedetails) {
			if (callMapper.getCallType().equalsIgnoreCase("DoNotCall")) {
				candidatedetails.setDoNotCallInd(true);
				candidateDetailsRepository.save(candidatedetails);
			} else {
				candidatedetails.setDoNotCallInd(false);
				candidateDetailsRepository.save(candidatedetails);
			}
			CallInfo callInfo = new CallInfo();
			callInfo.setCreation_date(new Date());
			callId = callInfoRepository.save(callInfo).getCall_id();

			if (null != callId) {
				/* insert to call details table */

				CallDetails callDetails = new CallDetails();
				callDetails.setCall_id(callId);
				callDetails.setCreation_date(new Date());
				callDetails.setCall_category("Do Not Call");
				callDetails.setCall_description("");
				callDetails.setTime_zone("");
				callDetails.setCall_start_time(callMapper.getStartTime());
				callDetails.setCall_end_time(callMapper.getEndTime());
				callDetails.setCallType("DoNotCall");
				callDetails.setSubject(callMapper.getCallPurpose());
				callDetails.setLive_ind(true);
				callDetails.setComplitionInd(false);
				callDetails.setMode(callMapper.getMode());
				callDetails.setModeType(callMapper.getModeType());
				callDetails.setModeLink(callMapper.getModeLink());

				try {

					callDetails.setCall_start_date(Utility.getDateFromISOString((callMapper.getStartDate())));
					callDetails.setCall_end_date(Utility.getDateFromISOString(callMapper.getEndDate()));

				} catch (Exception e) {
					e.printStackTrace();
				}

				callDetails.setUser_id(callMapper.getUserId());
				callDetails.setOrganization_id(callMapper.getOrganizationId());
				callDetails.setContact(callMapper.getContactId());
				// callDetails.setCandidateId(callMapper.getCandidateId());
				callDetailsRepository.save(callDetails);

				/* insert to CallCandidateLink table */
				CallCandidateLink callCandidateLink = new CallCandidateLink();
				callCandidateLink.setCallId(callId);
				callCandidateLink.setCandidateId(candidateId);
				callCandidateLink.setCreationDate(new Date());
				callCandidateLink.setLiveInd(true);
				callCandidateLinkRepository.save(callCandidateLink);

				/* insert to CallCandidateLink table */
				EmployeeCallLink employeeCallLink = new EmployeeCallLink();
				employeeCallLink.setCall_id(callId);
				employeeCallLink.setEmployee_id(callMapper.getUserId());
				employeeCallLink.setCreation_date(new Date());
				employeeCallLink.setLive_ind(true);
				employeeCallRepository.save(employeeCallLink);
			}
			return true;
		}
		return false;
	}

	@Override
	public DonotCallMapper getUpdatedDoNotCallDetail(String candidateId) {
		DonotCallMapper resultMapper = new DonotCallMapper();
		List<CallCandidateLink> callList = callCandidateLinkRepository.getCallListByCandidateId(candidateId);
		List<DonotCallMapper> mapperList = new ArrayList<>();

		if (null != callList && !callList.isEmpty()) {

			mapperList = callList.stream().map(li -> getDonotCallDetails(li.getCallId())).collect(Collectors.toList());
		}
		System.out.println("mapperList>>>>>>>" + mapperList.toString());

		// Collections.sort(mapperList, ( m1, m2) -> m2.getCreationDate()
		// .compareTo(m1.getCreationDate()));
		resultMapper = mapperList.get(0);

		return resultMapper;
	}

	public DonotCallMapper getDonotCallDetails(String callId) {

		CallDetails callDetails = callDetailsRepository.getDonotCallDetailsById(callId, "DoNotCall");
		DonotCallMapper callMapper = new DonotCallMapper();

		if (null != callDetails) {
			callMapper.setCreationDate(Utility.getISOFromDate(callDetails.getCreation_date()));
			if (null != callDetails.getCall_start_date()) {

				callMapper.setStartDate(Utility.getISOFromDate(callDetails.getCall_start_date()));

			} else {
				callMapper.setStartDate("");
			}
			if (null != callDetails.getCall_end_date()) {

				callMapper.setEndDate(Utility.getISOFromDate(callDetails.getCall_end_date()));
			} else {
				callMapper.setEndDate("");
			}

			callMapper.setStartTime(callDetails.getCall_start_time());
			callMapper.setCallId(callDetails.getCall_id());
			callMapper.setEndTime(callDetails.getCall_end_time());
			System.out.println("callId:::>>>" + callDetails.getCall_id());
			System.out.println("callDetails::" + callMapper.toString());

		}
		return callMapper;
	}

	@Override
	public List<CallViewMapper> getCallDetailsByName(String name) {
		return callDetailsRepository.findByCallTypeContaining(name).stream().map(c -> getCallDetails(c.getCall_id()))
				.collect(Collectors.toList());
	}

	@Override
	public boolean checkCallNameInCallType(String callType) {
		List<CallDetails> callTypes = callDetailsRepository.getByCallType(callType);
		if (callTypes.size() > 0) {
			return true;
		}
		return false;
	}

	public String getCallEmailContent(EmployeeDetails employeeDetails) throws IOException, TemplateException {

		StringWriter stringWriter = new StringWriter();
		Map<String, Object> model = new HashMap<>();
		model.put("employeeDetails", employeeDetails);
		configuration.getTemplate("call.ftlh").process(model, stringWriter);
		return stringWriter.getBuffer().toString();
	}

	@Override
	public HashMap getCallListsByUserIdStartdateAndEndDate(String userId, String startDate, String endDate) {

		HashMap map = new HashMap();

		Date end_date = null;
		Date start_date = null;
		try {
			end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<CallDetails> callDetails = callDetailsRepository
				.getCallListsByUserIdAndStartdateAndEndDateAndLiveInd(userId, start_date, end_date);
		if (null != callDetails && !callDetails.isEmpty()) {
			map.put("totalCall", callDetails.size());

		} else {
			map.put("totalCall", callDetails.size() + " , No Call Created");

			System.out.println("totalCall" + callDetails.size());
		}

		List<CallDetails> callDetails1 = callDetailsRepository
				.getCallListsByUserIdAndStartdateAndEndDateAndComplitionInd(userId, start_date, end_date);
		if (null != callDetails1 && !callDetails1.isEmpty()) {
			map.put("totalCallCompleted", callDetails1.size());
		} else {
			map.put("totalCallCompleted", callDetails1.size() + " , No Call Completed");
		}

		List<CallDetails> callDetails2 = callDetailsRepository
				.getCallListByUserIdAndStartdateAndEndDateAndComplitionInd(userId, start_date, end_date);
		if (null != callDetails2 && !callDetails2.isEmpty()) {
			map.put("totalCallInCompleted", callDetails2.size());
		} else {
			map.put("totalCallInCompleted", callDetails2.size() + " , No Call Pending");
		}

		return map;
	}

	@Override
	public List<CallViewMapper> getCallDetailsByLeadsId(String leadsId, int pageNo, int pageSize) {
		Pageable paging1 = PageRequest.of(pageNo, pageSize, Sort.by("creationDate").descending());
//        return leadsCallLinkRepo.getCallListByLeadsId(leadsId,paging1).stream().map(c -> getCallDetails(c.getCallId())).collect(Collectors.toList());
		Page<LeadsCallLink> list = leadsCallLinkRepo.getCallListByLeadsId(leadsId, paging1);
		return list.stream().map(leadsCallLink -> {
			CallViewMapper mapper = getCallDetails(leadsCallLink.getCallId());
			mapper.setPageCount(list.getTotalPages());
			mapper.setDataCount(list.getSize());
			mapper.setListCount(list.getTotalElements());
			return mapper;
		}).collect(Collectors.toList());
	}

	@Override
	public List<CallViewMapper> getCallDetailsByInvestorLeadsId(String investorLeadsId, int pageNo, int pageSize) {
		Pageable paging1 = PageRequest.of(pageNo, pageSize, Sort.by("creationDate").descending());
//        return investorLeadsCallLinkRepo.getCallListByInvestorLeadsId(investorLeadsId,paging1).stream().map(c -> getCallDetails(c.getCallId())).collect(Collectors.toList());
		Page<InvestorLeadsCallLink> list = investorLeadsCallLinkRepo.getCallListByInvestorLeadsId(investorLeadsId,
				paging1);
		return list.stream().map(leadsCallLink -> {
			CallViewMapper mapper = getCallDetails(leadsCallLink.getCallId());
			mapper.setPageCount(list.getTotalPages());
			mapper.setDataCount(list.getSize());
			mapper.setListCount(list.getTotalElements());
			return mapper;
		}).collect(Collectors.toList());

	}

	@Override
	public HashMap getCompletedCallsCountByUserIdAndStartdateAndEndDate(String userId, String startDate,
			String endDate) {
		HashMap map = new HashMap();

		Date end_date = null;
		Date start_date = null;
		try {
			end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<CallDetails> callDetails1 = callDetailsRepository
				.getCallListsByUserIdAndStartdateAndEndDateAndComplitionInd(userId, start_date, end_date);
		if (null != callDetails1 && !callDetails1.isEmpty()) {
			map.put("totalCallCompleted", callDetails1.size());
		} else {
			map.put("totalCallCompleted", callDetails1.size() + " , No Call Completed");
		}
		return map;
	}

	@Override
	public List<CallViewMapper> getCompletedCallsListByUserIdAndStartdateAndEndDate(String userId, String startDate,
			String endDate) {
		Date end_date = null;
		Date start_date = null;
		try {
			end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<CallViewMapper> resultList = new ArrayList<>();
		List<CallDetails> callDetails1 = callDetailsRepository
				.getCallListsByUserIdAndStartdateAndEndDateAndComplitionInd(userId, start_date, end_date);

		if (null != callDetails1 && !callDetails1.isEmpty()) {
			callDetails1.stream().map(li -> {
				CallViewMapper mapper = getCallDetails(li.getCall_id());
				if (null != mapper.getCallId()) {
					resultList.add(mapper);
				}

				return resultList;
			}).collect(Collectors.toList());

		}

		return resultList;
	}

	@Override
	public String saveCallNotes(NotesMapper notesMapper) {

		String callNotesId = null;
		if (null != notesMapper) {
			Notes notes = new Notes();
			notes.setNotes(notesMapper.getNotes());
			notes.setCreation_date(new Date());
			notes.setUserId(notesMapper.getEmployeeId());
			notes.setLiveInd(true);
			Notes note = notesRepository.save(notes);
			callNotesId = note.getNotes_id();

			/* insert to call-notes-link */
			if (!StringUtils.isEmpty(notesMapper.getCallId())) {
				CallNotesLink callNotesLink = new CallNotesLink();
				callNotesLink.setCallId(notesMapper.getCallId());
				callNotesLink.setNotesId(callNotesId);
				callNotesLink.setCreationDate(new Date());
				callNotesLinkRepository.save(callNotesLink);
			}
			/* insert to leads-notes-link */
			CallDetails callDetails = callDetailsRepository.getCallDetailsById(notesMapper.getCallId());
			if (!StringUtils.isEmpty(callDetails.getOppertunity())) {

				OpportunityNotesLink opportunityNotesLink = new OpportunityNotesLink();
				opportunityNotesLink.setOpportunity_id(callDetails.getOppertunity());
				opportunityNotesLink.setNotesId(callNotesId);
				opportunityNotesLink.setCreation_date(new Date());
				opportunityNotesLinkRepository.save(opportunityNotesLink);
			}

			/* insert to customer-notes-link */
			if (!StringUtils.isEmpty(callDetails.getContact())) {

				ContactNotesLink contactNotesLink = new ContactNotesLink();
				contactNotesLink.setContact_id(callDetails.getContact());
				contactNotesLink.setNotesId(callNotesId);
				contactNotesLink.setCreation_date(new Date());
				contactNotesLinkRepository.save(contactNotesLink);
			}
		}
		return callNotesId;

	}

	@Override
	public List<NotesMapper> getNoteListByCallId(String callId) {
		List<NotesMapper> resultList = new ArrayList<>();
		List<CallNotesLink> callNotesLinkList = callNotesLinkRepository.getNoteByCallId(callId);
		if (callNotesLinkList != null && !callNotesLinkList.isEmpty()) {
			return callNotesLinkList.stream().map(callNotesLink -> {
				NotesMapper notesMapper = getNotes(callNotesLink.getNotesId());
				if (null != notesMapper) {
					resultList.add(notesMapper);
				}
				return notesMapper;
			}).collect(Collectors.toList());
		}
		return resultList;
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
	public void deleteCallNotesById(String notesId) {
		CallNotesLink notesList = callNotesLinkRepository.findByNotesId(notesId);
		if (null != notesList) {

			Notes notes = notesRepository.findByNoteId(notesId);
			if (null != notes) {
				notes.setLiveInd(false);
				notesRepository.save(notes);
			}
		}
	}

	@Override
	public List<CallViewMapper> getCompletedCallsTypeListByUserIdAndStartdateAndEndDate(String userId, String startDate,
			String endDate, String callType) {
		Date end_date = null;
		Date start_date = null;
		try {
			end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<CallViewMapper> resultList = new ArrayList<>();
		List<CallDetails> callDetails = callDetailsRepository
				.getCallTypeListsByUserIdAndStartdateAndEndDateAndComplitionInd(userId, start_date, end_date, callType);
		System.out.println("callDetails size" + callDetails.size());
		if (null != callDetails && !callDetails.isEmpty()) {
			callDetails.stream().map(li -> {
				CallViewMapper mapper = getCallDetails(li.getCall_id());
				if (null != mapper.getCallId()) {
					resultList.add(mapper);
				}

				return resultList;
			}).collect(Collectors.toList());

		}

		return resultList;
	}

	@Override
	public HashMap getCompletedCallsTypeCountByUserIdAndStartdateAndEndDate(String userId, String startDate,
			String endDate, String callType) {
		HashMap map = new HashMap();

		Date end_date = null;
		Date start_date = null;
		try {
			end_date = Utility.getDateAfterEndDate(Utility.removeTime(Utility.getDateFromISOString(endDate)));
			start_date = Utility.removeTime(Utility.getDateFromISOString(startDate));
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<CallViewMapper> resultList = new ArrayList<>();
		List<CallDetails> callDetails = callDetailsRepository
				.getCallTypeListsByUserIdAndStartdateAndEndDateAndComplitionInd(userId, start_date, end_date, callType);
		if (null != callDetails && !callDetails.isEmpty()) {
			map.put("totalCallCompleted", callDetails.size());
		} else {
			map.put("totalCallCompleted", callDetails.size() + " , No Call Completed");
		}
		return map;

	}

	private ActivityMapper getCallById(String callId) {
		ActivityMapper activityMapper = new ActivityMapper();
		CallDetails callDetails = callDetailsRepository.getCallDetailsById(callId);
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
			activityMapper.setAssignedBy(employeeService.getEmployeeFullName(callDetails.getAssignedBy()));
			activityMapper.setAssignedTo(employeeService.getEmployeeFullName(callDetails.getAssignedTo()));

		}
		return activityMapper;
	}

	@Override
	public ActivityMapper saveActivityCall(CallMapper callMapper) throws IOException, TemplateException {

		/* insert to call info table */

		CallInfo callInfo = new CallInfo();
		callInfo.setCreation_date(new Date());
		CallInfo info = callInfoRepository.save(callInfo);

		CallViewMapper resultMapper = null;
		String callId = info.getCall_id();
		if (null != callId) {
			/* insert to call details table */

			CallDetails callDetails = new CallDetails();
			callDetails.setCall_id(callId);
			callDetails.setCreation_date(new Date());
			callDetails.setCall_category(callMapper.getCallCategory());
			callDetails.setCall_description(callMapper.getCallDescription());
			callDetails.setTime_zone(callMapper.getTimeZone());
			callDetails.setCall_start_time(callMapper.getStartTime());
			callDetails.setCall_end_time(callMapper.getEndTime());
			callDetails.setCallType(callMapper.getCallType());
			callDetails.setSubject(callMapper.getCallPurpose());
			callDetails.setLive_ind(true);
			callDetails.setMode(callMapper.getMode());
			callDetails.setModeType(callMapper.getModeType());
			callDetails.setModeLink(callMapper.getModeLink());
			callDetails.setMode(callId);
			callDetails.setRemind_time(callMapper.getRemindTime());
			callDetails.setRemind_ind(callMapper.isRemindInd());
			callDetails.setAssignedTo(callMapper.getAssignedTo());
			callDetails.setComplitionInd(false);
			callDetails.setRating(0);
			callDetails.setAssignedBy(callMapper.getUserId());

			try {

				callDetails.setCall_start_date(Utility.getDateFromISOString((callMapper.getStartDate())));
				callDetails.setCall_end_date(Utility.getDateFromISOString(callMapper.getEndDate()));

			} catch (Exception e) {
				e.printStackTrace();
			}

			callDetails.setUser_id(callMapper.getUserId());
			callDetails.setOrganization_id(callMapper.getOrganizationId());
			callDetails.setContact(callMapper.getContactId());
			callDetails.setCandidateId(callMapper.getCandidateId());
			callDetails.setCustomer(callMapper.getCustomer());
			callDetails.setOppertunity(callMapper.getOppertunity());
			callDetailsRepository.save(callDetails);

			/* insert to employee call link table */

			List<String> empList = callMapper.getIncluded();
			empList.add(callMapper.getUserId());
			if (!callMapper.getUserId().equals(callMapper.getAssignedTo())) {
				empList.add(callMapper.getAssignedTo());
			}

			if (null != empList && !empList.isEmpty()) {
				for (String employeeId : empList) {
					EmployeeCallLink employeeCallLink = new EmployeeCallLink();
					employeeCallLink.setCall_id(callId);
					employeeCallLink.setEmployee_id(employeeId);
					employeeCallLink.setCreation_date(new Date());
					employeeCallLink.setLive_ind(true);
					employeeCallRepository.save(employeeCallLink);
				}
			}

			/* insert to CallCandidateLink table */

			if (!StringUtils.isEmpty(callMapper.getCandidateId())) {
				CallCandidateLink callCandidateLink = new CallCandidateLink();
				callCandidateLink.setCallId(callId);
				callCandidateLink.setCandidateId(callMapper.getCandidateId());
				callCandidateLink.setCreationDate(new Date());
				callCandidateLink.setLiveInd(true);
				callCandidateLinkRepository.save(callCandidateLink);

			}

			/* insert to LeadsCallLink table */
			if (!StringUtils.isEmpty(callMapper.getLeadsId())) {
				LeadsCallLink leadsCallLink = new LeadsCallLink();
				leadsCallLink.setCallId(callId);
				leadsCallLink.setLeadsId(callMapper.getLeadsId());
				leadsCallLink.setCreationDate(new Date());
				leadsCallLink.setLiveInd(true);
				leadsCallLinkRepo.save(leadsCallLink);
			}

			/* insert to InvestorLeadsCallLink table */
			if (!StringUtils.isEmpty(callMapper.getInvestorLeadsId())) {
				InvestorLeadsCallLink investorCallLink = new InvestorLeadsCallLink();
				investorCallLink.setCallId(callId);
				investorCallLink.setInvestorLeadsId(callMapper.getInvestorLeadsId());
				investorCallLink.setCreationDate(new Date());
				investorCallLink.setLiveInd(true);
				investorLeadsCallLinkRepo.save(investorCallLink);
			}

			/* insert to CustomerCallLink table */
			if (!StringUtils.isEmpty(callMapper.getCustomer())) {
				CustomerCallLink customerCallLink = new CustomerCallLink();
				customerCallLink.setCallId(callId);
				customerCallLink.setCustomerId(callMapper.getCustomer());
				customerCallLink.setCreationDate(new Date());
				customerCallLink.setLiveInd(true);
				customerCallLinkRepo.save(customerCallLink);
			}

			/* insert to ContactCallLink table */
			if (!StringUtils.isEmpty(callMapper.getContactId())) {
				ContactCallLink contactCallLink = new ContactCallLink();
				contactCallLink.setCallId(callId);
				contactCallLink.setContactId(callMapper.getContactId());
				contactCallLink.setCreationDate(new Date());
				contactCallLink.setLiveInd(true);
				contactCallLinkRepo.save(contactCallLink);
			}
			/* insert to InvestorCallLink table */
			if (!StringUtils.isEmpty(callMapper.getInvestorId())) {
				InvestorCallLink investorLink = new InvestorCallLink();
				investorLink.setCallId(callId);
				investorLink.setInvestorId(callMapper.getInvestorId());
				investorLink.setCreationDate(new Date());
				investorLink.setLiveInd(true);
				investorCallLinkRepo.save(investorLink);
			}

			/* insert to Note table */
			Notes notes = new Notes();
			notes.setCreation_date(new Date());
			notes.setNotes(callMapper.getCallDescription());
			notes.setLiveInd(true);
			Notes note = notesRepository.save(notes);

			String notesId = note.getNotes_id();
			/* insert to CallNoteLink table */

			CallNotesLink callNote = new CallNotesLink();
			callNote.setCallId(callId);
			callNote.setNotesId(notesId);
			callNote.setCreationDate(new Date());
			callNotesLinkRepository.save(callNote);

			/* insert to Notification Table */
			Notificationparam param = new Notificationparam();
			EmployeeDetails emp = employeeRepository.getEmployeesByuserId(callMapper.getUserId());
			String name = employeeService.getEmployeeFullNameByObject(emp);
			param.setEmployeeDetails(emp);
			param.setAdminMsg("Call " + "'" + callMapper.getCallPurpose() + "' created by " + name);
			param.setOwnMsg("Call " + callMapper.getCallPurpose() + " created.");
			param.setNotificationType("Call creation");
			param.setProcessNmae("Call");
			param.setType("create");
			param.setEmailSubject("Korero alert- Call created");
			param.setCompanyName(companyName);
			param.setUserId(callMapper.getUserId());

			if (!callMapper.getUserId().equals(callMapper.getAssignedTo())) {
				List<String> assignToUserIds = new ArrayList<>();
				assignToUserIds.add(callMapper.getAssignedTo());
				param.setAssignToUserIds(assignToUserIds);
				param.setAssignToMsg("Call " + "'" + callMapper.getCallPurpose() + "'" + " assigned to "
						+ employeeService.getEmployeeFullName(callMapper.getAssignedTo()) + " by " + name);
			}
			if (null != callMapper.getIncluded() && !callMapper.getIncluded().isEmpty()) {
				param.setIncludeedUserIds(callMapper.getIncluded());
				param.setIncludeMsg(
						"You have been added Call " + "'" + callMapper.getCallPurpose() + "'" + " by " + name);
			}
			notificationService.createNotificationForDynamicUsers(param);
		}
		ActivityMapper mapper = getCallById(callId);
		return mapper;
	}

	@Override
	public ActivityMapper updateActivityCallDetails(String callId, CallMapper callMapper)
			throws IOException, TemplateException {
		ActivityMapper resultMapper = new ActivityMapper();
		if (null != callId) {

			CallDetails callDetails = callDetailsRepository.getCallDetailsById(callId);

			if (null != callDetails) {

//				callDetails.setLive_ind(false);
//
//				callDetailsRepository.save(callDetails);
//
//				CallDetails callDetailsNewData = new CallDetails();
				if (callMapper.getCallCategory() != null) {
					callDetails.setCall_category(callMapper.getCallCategory());
				} else {
					callDetails.setCall_category(callDetails.getCall_category());
				}

//                if (callMapper.getCallDescription() != null) {
//                    callDetailsNewData.setCall_description(callMapper.getCallDescription());
//                } else {
//                    callDetailsNewData.setCall_description(callDetails.getCall_description());
//                }
				if (callMapper.getCallDescription() != null) {
					List<CallNotesLink> list = callNotesLinkRepository.getNoteByCallId(callId);
					if (null != list && !list.isEmpty()) {
						list.sort((m1, m2) -> m2.getCreationDate().compareTo(m1.getCreationDate()));
						Notes notes = notesRepository.findByNoteId(list.get(0).getNotesId());
						if (null != notes) {
							notes.setNotes(callMapper.getCallDescription());
							notesRepository.save(notes);
						}
					}
				}

				if (callMapper.getCallPurpose() != null) {
					callDetails.setSubject(callMapper.getCallPurpose());
				} else {
					callDetails.setSubject(callDetails.getSubject());
				}
				if (callMapper.getCallType() != null) {
					callDetails.setCallType(callMapper.getCallType());
				} else {
					callDetails.setCallType(callDetails.getCallType());
				}

				if (callMapper.getMode() != null) {
					callDetails.setMode(callMapper.getMode());
				} else {
					callDetails.setMode(callDetails.getMode());

				}
				if (callMapper.getModeType() != null) {
					callDetails.setModeType(callMapper.getModeType());
				} else {
					callDetails.setModeType(callDetails.getModeType());
				}
				if (callMapper.getModeLink() != null) {
					callDetails.setModeLink(callMapper.getModeLink());
				} else {
					callDetails.setModeLink(callDetails.getModeLink());
				}

				if (callMapper.getStartDate() != null) {
					try {
						callDetails.setCall_start_date(Utility.getDateFromISOString((callMapper.getStartDate())));
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					callDetails.setCall_start_date(callDetails.getCall_start_date());
				}
				if (callMapper.getStartTime() != 0) {
					callDetails.setCall_start_time(callMapper.getStartTime());
				} else {
					callDetails.setCall_start_time(callDetails.getCall_start_time());
				}
				if (callMapper.getEndDate() != null) {
					try {
						callDetails.setCall_end_date(Utility.getDateFromISOString(callMapper.getEndDate()));
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					callDetails.setCall_end_date(callDetails.getCall_end_date());
				}
				if (callMapper.getEndTime() != 0) {
					callDetails.setCall_end_time(callMapper.getEndTime());
				} else {
					callDetails.setCall_end_time(callDetails.getCall_end_time());
				}

				if (callMapper.getTimeZone() != null) {
					callDetails.setTime_zone(callMapper.getTimeZone());
				} else {
					callDetails.setTime_zone(callDetails.getTime_zone());
				}

				if (callMapper.getAssignedTo() != null) {
					callDetails.setAssignedTo(callMapper.getAssignedTo());
				} else {
					callDetails.setAssignedTo(callDetails.getAssignedTo());
				}

				if (callMapper.getComplitionInd() != null && !callMapper.getComplitionInd().isEmpty()) {
					if (callMapper.getComplitionInd().equalsIgnoreCase("true")) {
						callDetails.setComplitionInd(true);
					} else if (callMapper.getComplitionInd().equalsIgnoreCase("false")) {
						callDetails.setComplitionInd(false);

					}

				} else {
					callDetails.setComplitionInd(callDetails.isComplitionInd());

				}
				if (callMapper.getCustomer() != null) {
					callDetails.setCustomer(callMapper.getCustomer());
				} else {
					callDetails.setCustomer(callDetails.getCustomer());
				}
				if (callMapper.getOppertunity() != null) {
					callDetails.setOppertunity(callMapper.getOppertunity());
				} else {
					callDetails.setOppertunity(callDetails.getOppertunity());
				}
				if (null != callDetails.getAssignedBy()) {
					if (null != callMapper.getAssignedTo()
							&& !callDetails.getAssignedBy().equals(callMapper.getAssignedTo())) {
						callDetails.setAssignedBy(callMapper.getUserId());
					} else {
						callDetails.setAssignedBy(callDetails.getAssignedBy());
					}
				} else {
					callDetails.setAssignedBy(callMapper.getUserId());
				}

				callDetails.setCall_id(callId);
				callDetails.setUser_id(callMapper.getUserId());
				callDetails.setOrganization_id(callMapper.getOrganizationId());
				callDetails.setLive_ind(true);
				callDetails.setCreation_date(callDetails.getCreation_date());
				callDetailsRepository.save(callDetails);

				// update call employee link

				List<String> empList = callMapper.getIncluded();
				empList.add(callMapper.getUserId());
				if (!callMapper.getUserId().equals(callMapper.getAssignedTo())) {
					empList.add(callMapper.getAssignedTo());
				}
				if (null != empList && !empList.isEmpty()) {
					List<EmployeeCallLink> employeeList = employeeCallRepository.getEmpListByCallId(callId);

					if (null != employeeList && !employeeList.isEmpty()) {
						for (EmployeeCallLink employeeCallLink : employeeList) {

							employeeCallLink.setLive_ind(false);
							employeeCallRepository.save(employeeCallLink);

						}
					}

					// insert new data to call employee link//

					if (null != empList && !empList.isEmpty()) {
						for (String employeeId : empList) {
							EmployeeCallLink employeeCallLink = new EmployeeCallLink();
							employeeCallLink.setCall_id(callId);
							employeeCallLink.setEmployee_id(employeeId);
							employeeCallLink.setCreation_date(new Date());
							employeeCallLink.setLive_ind(true);
							employeeCallRepository.save(employeeCallLink);

						}

					}
				}

				String taskNote1 = callMapper.getCallDescription();
				String link = callMapper.getModeLink();
				String msg = "";
				if (taskNote1 != null && link != null) {
					msg = "/n" + "Notes :" + taskNote1 + "/n" + "Link :" + link;
				} else if (taskNote1 == null && link != null) {
					msg = "/n" + "Link :" + link;
				} else if (taskNote1 != null && link == null) {
					msg = "/n" + "Notes :" + taskNote1;
				}
				/* insert to Notification Table */
				Notificationparam param = new Notificationparam();
				EmployeeDetails emp = employeeRepository.getEmployeesByuserId(callMapper.getUserId());
				String name = employeeService.getEmployeeFullNameByObject(emp);
				param.setEmployeeDetails(emp);
				param.setAdminMsg("Call " + "'" + callMapper.getCallPurpose() + "' updated by " + name + msg);
				param.setOwnMsg("Call " + callMapper.getCallPurpose() + " created." + msg);
				param.setNotificationType("Call update");
				param.setProcessNmae("Call");
				param.setType("update");
				param.setEmailSubject("Korero alert - Call info updated");
				param.setCompanyName(companyName);
				param.setUserId(callMapper.getUserId());

				if (!callMapper.getUserId().equals(callMapper.getAssignedTo())) {
					List<String> assignToUserIds = new ArrayList<>();
					assignToUserIds.add(callMapper.getAssignedTo());
					param.setAssignToUserIds(assignToUserIds);
					param.setAssignToMsg("Call " + "'" + callMapper.getCallPurpose() + "'" + " assigned to "
							+ employeeService.getEmployeeFullName(callMapper.getAssignedTo()) + " by " + name + msg);
				}
				if (null != callMapper.getIncluded() && !callMapper.getIncluded().isEmpty()) {
					List<String> includedids = new ArrayList<>(callMapper.getIncluded());
					param.setIncludeedUserIds(includedids);
					param.setIncludeMsg("You have been added Call " + "'" + callMapper.getCallPurpose() + "'" + " by "
							+ name + msg);
				}
				notificationService.createNotificationForDynamicUsers(param);

				resultMapper = getCallById(callId);

			}
		}
		return resultMapper;
	}

	private void updateActivityCallLog(String userType, String callId, String userTypeId, CallMapper callMapper) {
		LastActivityLog dbData = lastActivityLogRepository.findByUserTypeAndUserTypeId(userType, userTypeId);
		if (null != dbData) {

			dbData.setActivityId(callId);
			dbData.setActivityType("call");
			dbData.setCreationDate(new Date());
			dbData.setDescription(callMapper.getCallDescription());
			try {
				dbData.setStartDate(Utility.getDateFromISOString((callMapper.getStartDate())));
				dbData.setEndDate(Utility.getDateFromISOString(callMapper.getEndDate()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			dbData.setStartTime(callMapper.getStartTime());
			dbData.setEndTime(callMapper.getEndTime());
			dbData.setSubject(callMapper.getCallPurpose());
			dbData.setUserId(callMapper.getUserId());
			dbData.setOrganizationId(callMapper.getOrganizationId());
			dbData.setTimeZone(callMapper.getTimeZone());
			dbData.setUserType(userType);
			dbData.setUserTypeId(userTypeId);
			lastActivityLogRepository.save(dbData);
		} else {
			LastActivityLog dynamo = new LastActivityLog();
			dynamo.setActivityId(callId);
			dynamo.setActivityType("call");
			dynamo.setCreationDate(new Date());
			dynamo.setDescription(callMapper.getCallDescription());
			try {
				dynamo.setStartDate(Utility.getDateFromISOString((callMapper.getStartDate())));
				dynamo.setEndDate(Utility.getDateFromISOString(callMapper.getEndDate()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			dynamo.setStartTime(callMapper.getStartTime());
			dynamo.setEndTime(callMapper.getEndTime());
			dynamo.setSubject(callMapper.getCallPurpose());
			dynamo.setUserId(callMapper.getUserId());
			dynamo.setOrganizationId(callMapper.getOrganizationId());
			dynamo.setTimeZone(callMapper.getTimeZone());
			dynamo.setUserType(userType);
			dynamo.setUserTypeId(userTypeId);
			lastActivityLogRepository.save(dynamo);
		}
	}

	@Override
	public List<CallViewMapper> getCallDetailsByNameByOrgLevel(String name, String orgId) {
		List<CallDetails> list = callDetailsRepository.getByOrgIdAndCallTypeAndLiveInd(orgId, name);
		List<CallViewMapper> mapperList = new ArrayList<>();
		if (list != null && !list.isEmpty()) {
		    mapperList = list.stream()
		            .map(li -> getCallDetails(li.getCall_id()))
		            .collect(Collectors.toList());
		}
		return mapperList;
	}

	@Override
	public List<CallViewMapper> getCallDetailsByNameForTeam(String name, String userId) {
		Set<String> userIds = employeeRepository.getEmployeeDetailsByReportingManagerId(userId).stream()
				.map(EmployeeDetails::getUserId).collect(Collectors.toSet());
		userIds.add(userId);

		List<String> userIdss = new ArrayList<>(userIds);
		List<CallDetails> list = callDetailsRepository.getByUserIdsAndCallTypeAndLiveInd(userIds, name);
		List<CallViewMapper> mapperList = new ArrayList<>();
		if (list != null && !list.isEmpty()) {
		    mapperList = list.stream()
		            .map(li -> getCallDetails(li.getCall_id()))
		            .collect(Collectors.toList());
		}
		return mapperList;
	}

	@Override
	public List<CallViewMapper> getCallDetailsByNameByUserId(String name, String userId) {
		List<CallDetails> list = callDetailsRepository.getByUserIdAndCallTypeAndLiveInd(userId, name);
		List<CallViewMapper> mapperList = new ArrayList<>();
		if (list != null && !list.isEmpty()) {
		    mapperList = list.stream()
		            .map(li -> getCallDetails(li.getCall_id()))
		            .collect(Collectors.toList());
		}
		return mapperList;
	}

}
