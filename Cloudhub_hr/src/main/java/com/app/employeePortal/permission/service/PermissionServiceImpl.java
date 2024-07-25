package com.app.employeePortal.permission.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.employeePortal.Opportunity.mapper.OpportunityViewMapper;
import com.app.employeePortal.Opportunity.service.OpportunityService;
import com.app.employeePortal.call.mapper.CallViewMapper;
import com.app.employeePortal.call.service.CallService;
import com.app.employeePortal.candidate.mapper.CandidateViewMapper;
import com.app.employeePortal.candidate.service.CandidateService;
import com.app.employeePortal.contact.mapper.ContactViewMapper;
import com.app.employeePortal.contact.service.ContactService;
import com.app.employeePortal.customer.mapper.CustomerResponseMapper;
import com.app.employeePortal.customer.service.CustomerService;
import com.app.employeePortal.employee.entity.EmployeeDetails;
import com.app.employeePortal.employee.repository.EmployeeRepository;
import com.app.employeePortal.event.mapper.EventViewMapper;
import com.app.employeePortal.event.service.EventService;
import com.app.employeePortal.partner.mapper.PartnerMapper;
import com.app.employeePortal.partner.service.PartnerService;
import com.app.employeePortal.permission.entity.AssessmentDetails;
import com.app.employeePortal.permission.entity.Communication;
import com.app.employeePortal.permission.entity.Compliance;
import com.app.employeePortal.permission.entity.DepartmentPermission;
import com.app.employeePortal.permission.entity.NotificationPermission;
import com.app.employeePortal.permission.entity.Permission;
import com.app.employeePortal.permission.entity.Sourcing;
import com.app.employeePortal.permission.entity.ThirdParty;
import com.app.employeePortal.permission.mapper.AssessmentMapper;
import com.app.employeePortal.permission.mapper.CommunicationMapper;
import com.app.employeePortal.permission.mapper.ComplianceMapper;
import com.app.employeePortal.permission.mapper.DepartmentPermissionMapper;
import com.app.employeePortal.permission.mapper.NotificationPermissionMapper;
import com.app.employeePortal.permission.mapper.PermissionMapper;
import com.app.employeePortal.permission.mapper.SourcingMapper;
import com.app.employeePortal.permission.mapper.ThirdPartyMapper;
import com.app.employeePortal.permission.repository.AssessmentDetailsRepository;
import com.app.employeePortal.permission.repository.CommunicationRepository;
import com.app.employeePortal.permission.repository.ComplianceRepository;
import com.app.employeePortal.permission.repository.DepartmentPermissionRepository;
import com.app.employeePortal.permission.repository.NotificationPermissionRepository;
import com.app.employeePortal.permission.repository.PermissionRepository;
import com.app.employeePortal.permission.repository.SourcingRepository;
import com.app.employeePortal.permission.repository.ThirdPartyRepository;
import com.app.employeePortal.task.mapper.TaskViewMapper;
import com.app.employeePortal.task.service.TaskService;
import com.app.employeePortal.util.Utility;

@Service
@Transactional
public class PermissionServiceImpl implements PermissionService {
	@Autowired
	PermissionRepository permissionRepository;
	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	CandidateService candidateService;
	@Autowired
	ContactService contactService;
	@Autowired
	CustomerService customerService;
	@Autowired
	OpportunityService opportunityService;
	@Autowired
	PartnerService partnerService;
	@Autowired
	PermissionService permissionService;
	@Autowired
	CallService callService;
	@Autowired
	EventService eventService;
	@Autowired
	TaskService taskService;
	@Autowired
	ThirdPartyRepository thirdPartyRepository;
	@Autowired
	DepartmentPermissionRepository departmentPermissionRepository;
	@Autowired
	ComplianceRepository complianceRepository;
	@Autowired
	CommunicationRepository communicationRepository;
	@Autowired
	SourcingRepository sourcingRepository;
	// private OpportunityViewMapper opportunityMapper;
	@Autowired
	NotificationPermissionRepository notificationPermissionRepository;
	@Autowired
	AssessmentDetailsRepository assessmentDetailsRepository;

	@Override
	public String savePermission(PermissionMapper permissionMapper) {
		String id = null;

		if (permissionMapper != null) {
			System.out.println("inside ....." + permissionMapper.getUserId());
			Permission permission = permissionRepository.findByUserId(permissionMapper.getUserId());
			// System.out.println("inside ....."+permission.toString());
			if (permission != null) {
				permission.setCandidateShareInd(permissionMapper.isCandidateShareInd());
				// permission.setPlannerShareInd(permissionMapper.isPlannerShareInd());
				permission.setPartnerContactInd(permissionMapper.isPartnerContactInd());
				permission.setOpportunityInd(permissionMapper.isOpportunityShareInd());
				permission.setContactInd(permissionMapper.isContactInd());
				permission.setCustomerInd(permissionMapper.isCustomerInd());
				permission.setPartnerInd(permissionMapper.isPartnerInd());
				permission.setCallInd(permissionMapper.isCalleInd());
				permission.setEventInd(permissionMapper.isEventInd());
				permission.setTaskInd(permissionMapper.isTaskeInd());

				permission.setUserId(permission.getUserId());

				permissionRepository.save(permission);
				id = permission.getId();
				System.out.println("in if....." + id);
			} else {
				Permission permission1 = new Permission();
				permission1.setCandidateShareInd(permissionMapper.isCandidateShareInd());
				permission1.setPlannerShareInd(permissionMapper.isPlannerShareInd());
				permission1.setPartnerContactInd(permissionMapper.isPartnerContactInd());
				permission1.setOpportunityInd(permissionMapper.isOpportunityShareInd());
				permission1.setContactInd(permissionMapper.isContactInd());
				permission1.setCustomerInd(permissionMapper.isCustomerInd());
				permission1.setPartnerInd(permissionMapper.isPartnerInd());

				permission1.setCallInd(permissionMapper.isCalleInd());
				permission1.setEventInd(permissionMapper.isEventInd());
				permission1.setTaskInd(permissionMapper.isTaskeInd());

				permission1.setUserId(permissionMapper.getUserId());

				Permission permission3 = permissionRepository.save(permission1);
				id = permission3.getId();
				System.out.println("in else....." + id);
			}

		}
		System.out.println("outside block....." + id);
		return id;

	}

	@Override
	public List<PermissionMapper> getUserList() {
		List<PermissionMapper> resultMapper = new ArrayList<PermissionMapper>();

		List<Permission> list1 = permissionRepository.getUserList();
		if (null != list1 && !list1.isEmpty()) {

			for (Permission list2 : list1) {
				EmployeeDetails employeeDetails = employeeRepository.getEmployeeDetailsByEmployeeId(list2.getUserId());

				PermissionMapper obj = new PermissionMapper();
				if (employeeDetails != null) {

					String middleName = " ";
					String lastName = " ";

					if (!StringUtils.isEmpty(employeeDetails.getLastName())) {

						lastName = employeeDetails.getLastName();
					}

					if (employeeDetails.getMiddleName() != null && employeeDetails.getMiddleName().length() > 0) {

						middleName = employeeDetails.getMiddleName();
						obj.setUserName(employeeDetails.getFirstName() + " " + middleName + " " + lastName);
					} else {

						obj.setUserName(employeeDetails.getFirstName() + " " + lastName);
					}

					// obj.setUserName(employeeDetails.getFirstName()+"
					// "+employeeDetails.getLastName());
					obj.setUserId(employeeDetails.getUserId());
					resultMapper.add(obj);
				}
			}

		}
		return resultMapper;

	}

	@Override
	public List<PermissionMapper> getUserListForContact() {
		List<PermissionMapper> resultMapper = new ArrayList<PermissionMapper>();

		List<Permission> list1 = permissionRepository.getUserListForContact();
		if (null != list1 && !list1.isEmpty()) {

			for (Permission list2 : list1) {
				EmployeeDetails employeeDetails = employeeRepository.getEmployeeDetailsByEmployeeId(list2.getUserId());

				PermissionMapper obj = new PermissionMapper();
				if (employeeDetails != null) {

					String middleName = " ";
					String lastName = " ";

					if (!StringUtils.isEmpty(employeeDetails.getLastName())) {

						lastName = employeeDetails.getLastName();
					}

					if (employeeDetails.getMiddleName() != null && employeeDetails.getMiddleName().length() > 0) {

						middleName = employeeDetails.getMiddleName();
						obj.setUserName(employeeDetails.getFirstName() + " " + middleName + " " + lastName);
					} else {

						obj.setUserName(employeeDetails.getFirstName() + " " + lastName);
					}

					// obj.setUserName(employeeDetails.getFirstName()+"
					// "+employeeDetails.getLastName());
					obj.setUserId(employeeDetails.getUserId());
					resultMapper.add(obj);

				}
			}

		}
		return resultMapper;
	}

	@Override
	public List<PermissionMapper> getUserListForCustomer() {
		List<PermissionMapper> resultMapper = new ArrayList<PermissionMapper>();

		List<Permission> list1 = permissionRepository.getUserListForCustomer();
		if (null != list1 && !list1.isEmpty()) {

			for (Permission list2 : list1) {
				EmployeeDetails employeeDetails = employeeRepository.getEmployeeDetailsByEmployeeId(list2.getUserId());

				PermissionMapper obj = new PermissionMapper();
				if (employeeDetails != null) {

					String middleName = " ";
					String lastName = " ";

					if (!StringUtils.isEmpty(employeeDetails.getLastName())) {

						lastName = employeeDetails.getLastName();
					}

					if (employeeDetails.getMiddleName() != null && employeeDetails.getMiddleName().length() > 0) {

						middleName = employeeDetails.getMiddleName();
						obj.setUserName(employeeDetails.getFirstName() + " " + middleName + " " + lastName);
					} else {

						obj.setUserName(employeeDetails.getFirstName() + " " + lastName);
					}

					// obj.setUserName(employeeDetails.getFirstName()+"
					// "+employeeDetails.getLastName());
					obj.setUserId(employeeDetails.getUserId());
					resultMapper.add(obj);

				}
			}

		}
		return resultMapper;
	}

	@Override
	public List<PermissionMapper> getUserListForOpportunity() {
		List<PermissionMapper> resultMapper = new ArrayList<PermissionMapper>();

		List<Permission> list1 = permissionRepository.getUserListForOpportunity();
		if (null != list1 && !list1.isEmpty()) {

			for (Permission list2 : list1) {
				EmployeeDetails employeeDetails = employeeRepository.getEmployeeDetailsByEmployeeId(list2.getUserId());

				PermissionMapper obj = new PermissionMapper();
				if (employeeDetails != null) {
					String middleName = " ";
					String lastName = " ";

					if (!StringUtils.isEmpty(employeeDetails.getLastName())) {

						lastName = employeeDetails.getLastName();
					}

					if (employeeDetails.getMiddleName() != null && employeeDetails.getMiddleName().length() > 0) {

						middleName = employeeDetails.getMiddleName();
						obj.setUserName(employeeDetails.getFirstName() + " " + middleName + " " + lastName);
					} else {

						obj.setUserName(employeeDetails.getFirstName() + " " + lastName);
					}

					// obj.setUserName(employeeDetails.getFirstName()+"
					// "+employeeDetails.getLastName());
					obj.setUserId(employeeDetails.getUserId());
					resultMapper.add(obj);
				}
			}

		}
		return resultMapper;
	}

	@Override
	public List<PermissionMapper> getUserListForPartner() {
		List<PermissionMapper> resultMapper = new ArrayList<PermissionMapper>();

		List<Permission> list1 = permissionRepository.getUserListForPartner();
		if (null != list1 && !list1.isEmpty()) {

			for (Permission list2 : list1) {
				EmployeeDetails employeeDetails = employeeRepository.getEmployeeDetailsByEmployeeId(list2.getUserId());

				PermissionMapper obj = new PermissionMapper();
				if (employeeDetails != null) {

					String middleName = " ";
					String lastName = " ";

					if (!StringUtils.isEmpty(employeeDetails.getLastName())) {

						lastName = employeeDetails.getLastName();
					}

					if (employeeDetails.getMiddleName() != null && employeeDetails.getMiddleName().length() > 0) {

						middleName = employeeDetails.getMiddleName();
						obj.setUserName(employeeDetails.getFirstName() + " " + middleName + " " + lastName);
					} else {

						obj.setUserName(employeeDetails.getFirstName() + " " + lastName);
					}

					// obj.setUserName(employeeDetails.getFirstName()+"
					// "+employeeDetails.getLastName());
					obj.setUserId(employeeDetails.getUserId());
					resultMapper.add(obj);
				}
			}

		}
		return resultMapper;
	}

	@Override
	public PermissionMapper getList(String userId) {
		System.out.println("data....." + userId);
		PermissionMapper mapper = new PermissionMapper();
		if (userId != null) {
			Permission pem = permissionRepository.getPermission(userId);
			System.out.println("data....." + pem.toString());

			mapper.setCandidateShareInd(pem.isCandidateShareInd());
			mapper.setPlannerShareInd(pem.isPlannerShareInd());
			mapper.setContactInd(pem.isContactInd());
			mapper.setCustomerInd(pem.isCustomerInd());
			mapper.setOpportunityShareInd(pem.isOpportunityInd());
			mapper.setPartnerContactInd(pem.isPartnerContactInd());
			mapper.setPartnerInd(pem.isPartnerInd());
			mapper.setUserId(userId);

		}
		return mapper;
	}

	@Override
	public List<CandidateViewMapper> getCandidateList(String userId) {

		System.out.println("inside getCandidateList.............");

		List<CandidateViewMapper> candidateViewMapper = candidateService.getCandidateListByUserId(userId);

		System.out.println("inside getCandidateList............." + candidateViewMapper.toString());

		return candidateViewMapper;
	}

	@Override
	public List<CandidateViewMapper> getCandidateListForAllUsers(List<String> permissionMapperList) {
		List<CandidateViewMapper> resultMapper = new ArrayList<CandidateViewMapper>();

		for (String userId : permissionMapperList) {

			List<CandidateViewMapper> candidateViewMapper = getCandidateList(userId);
			resultMapper.addAll(candidateViewMapper);

		}

		return resultMapper;
	}

	@Override
	public List<ContactViewMapper> getContactList(String userId) {

		List<ContactViewMapper> contactViewMapper = contactService.getContactDetailsListByUserId(userId);

		return contactViewMapper;
	}

	@Override
	public List<CustomerResponseMapper> getCustomerListList(String userId) {

		List<CustomerResponseMapper> customerViewMapper = customerService.getCustomerDetailsByuserId(userId);

		return customerViewMapper;
	}

	@Override
	public List<OpportunityViewMapper> getOpportunityList(String userId) {

		List<OpportunityViewMapper> opportunityViewMapper = opportunityService
				.getOpportunityDetailsListByUserId(userId);

		return opportunityViewMapper;
	}

	@Override
	public List<PartnerMapper> getPartnerList(String userId) {

		List<PartnerMapper> partnerMapper = partnerService.getPartnerDetailsListByUserId(userId);

		return partnerMapper;
	}

	@Override
	public List<PartnerMapper> getPartnerList(String userId,int pageNo, int pageSize) {
		return partnerService.getPartnerDetailsListByUserId(userId,pageNo,pageSize);
	}
	
	@Override
	public List<ContactViewMapper> getPartnerContactsList(String userId) {

		List<ContactViewMapper> partnerMapper = partnerService.getAllPartnerContatListByUserId(userId);

		return partnerMapper;
	}

	@Override
	public List<PermissionMapper> getUserListForPartnerContact() {
		List<PermissionMapper> resultMapper = new ArrayList<PermissionMapper>();

		List<Permission> list1 = permissionRepository.getUserListForPartnerContact();
		if (null != list1 && !list1.isEmpty()) {

			for (Permission list2 : list1) {
				EmployeeDetails employeeDetails = employeeRepository.getEmployeeDetailsByEmployeeId(list2.getUserId());

				PermissionMapper obj = new PermissionMapper();
				if (employeeDetails != null) {
					obj.setUserName(employeeDetails.getFirstName() + " " + employeeDetails.getLastName());
					obj.setUserId(employeeDetails.getUserId());
					resultMapper.add(obj);
				}
			}

		}
		return resultMapper;
	}

	@Override
	public List<CandidateViewMapper> getCandidateList() {
		List<CandidateViewMapper> resultMapper = new ArrayList<CandidateViewMapper>();

		List<Permission> permission = permissionRepository.getUserList();
		System.out.println(" user$$$$$$$$$$$$==" + permission.toString());

		if (null != permission && !permission.isEmpty()) {

			for (Permission permissionn : permission) {
				List<CandidateViewMapper> mp = candidateService.getCandidateListByUserId(permissionn.getUserId());

				System.out.println(" userId$$$$$$$$$$$$==" + permissionn.getUserId());

				resultMapper.addAll(mp);
			}
		}
		return resultMapper;

	}

	@Override
	public List<ContactViewMapper> getContactListForAllUsers(List<String> mapperList) {
		List<ContactViewMapper> resultMapper = new ArrayList<ContactViewMapper>();

		if (null != mapperList && !mapperList.isEmpty()) {

			for (String userId : mapperList) {

				List<ContactViewMapper> mp = getContactList(userId);

				resultMapper.addAll(mp);
			}
		}
		return resultMapper;

	}

	@Override
	public List<CustomerResponseMapper> getCustomerListListForAllUsers(List<String> mapperList) {
		List<CustomerResponseMapper> resultMapper = new ArrayList<CustomerResponseMapper>();

		if (null != mapperList && !mapperList.isEmpty()) {

			for (String userId : mapperList) {
				List<CustomerResponseMapper> mp = getCustomerListList(userId);

				resultMapper.addAll(mp);
			}
		}
		return resultMapper;

	}

	@Override
	public List<OpportunityViewMapper> getOpportunityListForAllUsers(List<String> mapperList) {
		List<OpportunityViewMapper> resultMapper = new ArrayList<OpportunityViewMapper>();

		if (null != mapperList && !mapperList.isEmpty()) {

			for (String userId : mapperList) {
				List<OpportunityViewMapper> mp = getOpportunityList(userId);

				resultMapper.addAll(mp);
			}
		}
		return resultMapper;
	}

	@Override
	public List<PartnerMapper> getPartnerListForAllUsers(List<String> mapperList) {

		List<PartnerMapper> resultMapper = new ArrayList<PartnerMapper>();

		if (null != mapperList && !mapperList.isEmpty()) {

			for (String userId : mapperList) {
				List<PartnerMapper> mp = getPartnerList(userId);

				resultMapper.addAll(mp);
			}
		}
		return resultMapper;

	}

	@Override
	public List<ContactViewMapper> getPartnerContactsListForAllUsers(List<String> mapperList) {
		List<ContactViewMapper> resultMapper = new ArrayList<ContactViewMapper>();

		if (null != mapperList && !mapperList.isEmpty()) {

			for (String userId : mapperList) {
				List<ContactViewMapper> mp = getPartnerContactsList(userId);

				resultMapper.addAll(mp);
			}
		}
		return resultMapper;

	}

	@Override
	public List<PermissionMapper> getUserListforCall() {
		List<PermissionMapper> resultMapper = new ArrayList<PermissionMapper>();

		List<Permission> list1 = permissionRepository.getUserListForCall();
		if (null != list1 && !list1.isEmpty()) {

			for (Permission list2 : list1) {
				EmployeeDetails employeeDetails = employeeRepository.getEmployeeDetailsByEmployeeId(list2.getUserId());

				PermissionMapper obj = new PermissionMapper();
				if (employeeDetails != null) {

					String middleName = " ";
					String lastName = " ";

					if (!StringUtils.isEmpty(employeeDetails.getLastName())) {

						lastName = employeeDetails.getLastName();
					}

					if (employeeDetails.getMiddleName() != null && employeeDetails.getMiddleName().length() > 0) {

						middleName = employeeDetails.getMiddleName();
						obj.setUserName(employeeDetails.getFirstName() + " " + middleName + " " + lastName);
					} else {

						obj.setUserName(employeeDetails.getFirstName() + " " + lastName);
					}

					obj.setUserName(employeeDetails.getFirstName() + " " + employeeDetails.getLastName());
					obj.setUserId(employeeDetails.getUserId());
					resultMapper.add(obj);
				}
			}

		}
		return resultMapper;
	}

	@Override
	public List<PermissionMapper> getUserListforPlanner() {
		List<PermissionMapper> resultMapper = new ArrayList<PermissionMapper>();

		List<Permission> list1 = permissionRepository.getUserListforPlanner();
		if (null != list1 && !list1.isEmpty()) {

			for (Permission list2 : list1) {
				EmployeeDetails employeeDetails = employeeRepository.getEmployeeDetailsByEmployeeId(list2.getUserId());

				PermissionMapper obj = new PermissionMapper();
				if (employeeDetails != null) {

					String middleName = " ";
					String lastName = " ";

					if (!StringUtils.isEmpty(employeeDetails.getLastName())) {

						lastName = employeeDetails.getLastName();
					}

					if (employeeDetails.getMiddleName() != null && employeeDetails.getMiddleName().length() > 0) {

						middleName = employeeDetails.getMiddleName();
						obj.setUserName(employeeDetails.getFirstName() + " " + middleName + " " + lastName);
					} else {

						obj.setUserName(employeeDetails.getFirstName() + " " + lastName);
					}

					obj.setUserName(employeeDetails.getFirstName() + " " + employeeDetails.getLastName());
					obj.setUserId(employeeDetails.getUserId());
					resultMapper.add(obj);
				}
			}

		}
		return resultMapper;
	}

	@Override
	public List<PermissionMapper> getUserListforTask() {
		List<PermissionMapper> resultMapper = new ArrayList<PermissionMapper>();

		List<Permission> list1 = permissionRepository.getUserListForTask();
		if (null != list1 && !list1.isEmpty()) {

			for (Permission list2 : list1) {
				EmployeeDetails employeeDetails = employeeRepository.getEmployeeDetailsByEmployeeId(list2.getUserId());

				PermissionMapper obj = new PermissionMapper();
				if (employeeDetails != null) {

					String middleName = " ";
					String lastName = " ";

					if (!StringUtils.isEmpty(employeeDetails.getLastName())) {

						lastName = employeeDetails.getLastName();
					}

					if (employeeDetails.getMiddleName() != null && employeeDetails.getMiddleName().length() > 0) {

						middleName = employeeDetails.getMiddleName();
						obj.setUserName(employeeDetails.getFirstName() + " " + middleName + " " + lastName);
					} else {

						obj.setUserName(employeeDetails.getFirstName() + " " + lastName);
					}

					obj.setUserName(employeeDetails.getFirstName() + " " + employeeDetails.getLastName());
					obj.setUserId(employeeDetails.getUserId());
					resultMapper.add(obj);
				}
			}

		}
		return resultMapper;
	}

	@Override
	public List<CallViewMapper> getCallList(String userId) {
		List<CallViewMapper> callViewMapper = callService.getCallDetailsByEmployeeId(userId);

		return callViewMapper;
	}

	@Override
	public List<EventViewMapper> getEventList(String userId) {
		List<EventViewMapper> eventViewMapper = eventService.getEventDetailsByEmployeeId(userId);

		return eventViewMapper;
	}

	@Override
	public List<TaskViewMapper> getTaskList(String userId) {
		List<TaskViewMapper> taskViewMapper = taskService.getTaskDetailsByEmployeeId(userId);

		return taskViewMapper;
	}

	@Override
	public List<CallViewMapper> getCallListForAllUsers(List<String> permissionMapperList) {
		List<CallViewMapper> resultMapper = new ArrayList<CallViewMapper>();

		for (String userId : permissionMapperList) {

			List<CallViewMapper> callViewMapper = getCallList(userId);
			resultMapper.addAll(callViewMapper);

		}

		return resultMapper;
	}

	@Override
	public List<EventViewMapper> getEventListForAllUsers(List<String> permissionMapperList) {
		List<EventViewMapper> resultMapper = new ArrayList<EventViewMapper>();

		for (String userId : permissionMapperList) {

			List<EventViewMapper> eventViewMapper = getEventList(userId);
			resultMapper.addAll(eventViewMapper);

		}

		return resultMapper;
	}

	@Override
	public List<TaskViewMapper> getTaskListForAllUsers(List<String> permissionMapperList) {
		List<TaskViewMapper> resultMapper = new ArrayList<TaskViewMapper>();

		for (String userId : permissionMapperList) {

			List<TaskViewMapper> taskViewMapper = getTaskList(userId);
			resultMapper.addAll(taskViewMapper);

		}
		return resultMapper;
	}

	@Override
	public boolean candidateShareTrueByOrgId(String orgId) {
		Permission permission = permissionRepository.getcandidateShareTrueByOrgId(orgId);
		if (permission != null) {

			return true;
		}

		return false;
	}

	@Override
	public String saveCandidateSharePermission(PermissionMapper permissionMapper) {
		String id = null;
		if (permissionMapper != null) {
			Permission permission = permissionRepository.findByOrgId(permissionMapper.getOrganizationId());
			if (permission != null) {
				System.out.println("permission userid....." + permission.getId());

				permission.setCandiEmpShareInd(permissionMapper.isCandiEmpShareInd());
				permission.setCandiContShareInd(permissionMapper.isCandiContShareInd());
				permission.setCandiContSrchInd(permissionMapper.isCandiContSrchInd());
				permission.setLastUpdatedOn(new Date());
//				if (permissionMapper.isCandiEmpShareInd()) {
//					List<Permission> empList = permissionRepository.findAll();
//					if (null != empList && !empList.isEmpty()) {
//						for (Permission permission2 : empList) {
//							EmployeeDetails employeeDetails = employeeRepository
//									.getEmployeeDetailsByEmployeeId(permission2.getUserId());
//							if (employeeDetails.getEmployeeType().equalsIgnoreCase("employee")) {
//								permission2.setCandiEmpShareInd(permissionMapper.isCandiEmpShareInd());
//							}
//						}
//					}
//				}
				permission.setCandiEmpSrchInd(permissionMapper.isCandiEmpSrchInd());
				permission.setOrgId(permissionMapper.getOrganizationId());
				permission.setUserId(permissionMapper.getUserId());
				id = permissionRepository.save(permission).getId();
			} else {
				Permission newPermission = new Permission();
				newPermission.setCandiEmpShareInd(permissionMapper.isCandiEmpShareInd());
				newPermission.setCandiContShareInd(permissionMapper.isCandiContShareInd());
//				if (permissionMapper.isCandidateIntenalVisibleInd()) {
//					List<Permission> empList = permissionRepository.findAll();
//					if (null != empList && !empList.isEmpty()) {
//						for (Permission permission2 : empList) {
//							EmployeeDetails employeeDetails = employeeRepository
//									.getEmployeeDetailsByEmployeeId(permission2.getUserId());
//							if (employeeDetails.getEmployeeType().equalsIgnoreCase("employee")) {
//								permission2.setAdminCandiShareInd(permissionMapper.isAdminCandiShareInd());
//							}
//						}
//					}
//				}
				newPermission.setCandiContSrchInd(permissionMapper.isCandiContSrchInd());
				newPermission.setCandiEmpSrchInd(permissionMapper.isCandiEmpSrchInd());
				newPermission.setOrgId(permissionMapper.getOrganizationId());
				newPermission.setUserId(permissionMapper.getUserId());
				newPermission.setLastUpdatedOn(new Date());

				id = permissionRepository.save(newPermission).getId();

			}

		}
		return id;

	}

	@Override
	public PermissionMapper getAdminCandidateShareByOrgId(String orgId) {
		PermissionMapper mapper = new PermissionMapper();
		Permission permission = permissionRepository.findByOrgId(orgId);
		if (permission != null) {
			mapper.setCandiEmpShareInd(permission.isCandiEmpShareInd());
			mapper.setCandiContShareInd(permission.isCandiContShareInd());
			mapper.setCandiContSrchInd(permission.isCandiContSrchInd());
			mapper.setCandiContSrchInd(permission.isCandiEmpSrchInd());
			mapper.setUserId(permission.getUserId());
			mapper.setOrganizationId(permission.getOrgId());
			mapper.setLastUpdatedOn(Utility.getISOFromDate(permission.getLastUpdatedOn()));

			EmployeeDetails employeeDetails = employeeRepository.getEmployeeByUserId(permission.getUserId());

			if (employeeDetails != null) {

				String middleName = " ";
				String lastName = "";

				if (!StringUtils.isEmpty(employeeDetails.getLastName())) {

					lastName = employeeDetails.getLastName();
				}

				if (employeeDetails.getMiddleName() != null && employeeDetails.getMiddleName().length() > 0) {

					middleName = employeeDetails.getMiddleName();
					mapper.setName(employeeDetails.getFirstName() + " " + middleName + " " + lastName);
				} else {

					mapper.setName(employeeDetails.getFirstName() + " " + lastName);
				}

			}
		}

		return mapper;
	}

	@Override
	public String saveEngagementAccess(ThirdPartyMapper thirdPartyMapper) {
		String id = null;
		if (thirdPartyMapper != null) {
			ThirdParty dbThirdParty = thirdPartyRepository.findByOrgId(thirdPartyMapper.getOrgId());
			if (dbThirdParty != null) {
				System.out.println("CustomerContactInd():" + thirdPartyMapper.isCustomerContactInd());
				dbThirdParty.setCustomerContactInd(thirdPartyMapper.isCustomerContactInd());
				dbThirdParty.setPartnerContactInd(thirdPartyMapper.isPartnerContactInd());
				dbThirdParty.setMonitizeInd(thirdPartyMapper.isMonitizeInd());
				dbThirdParty.setCustomerAiInd(thirdPartyMapper.isCustomerAiInd());
				dbThirdParty.setPartnerAiInd(thirdPartyMapper.isPartnerAiInd());
				dbThirdParty.setOrgId(thirdPartyMapper.getOrgId());
				dbThirdParty.setUserId(thirdPartyMapper.getUserId());
				dbThirdParty.setLastUpdatedOn(new Date());
				dbThirdParty.setAllowPrfWithVendorInd(thirdPartyMapper.isAllowPrfWithVendorInd());
				dbThirdParty.setEnableHiringTeamInd(thirdPartyMapper.isEnableHiringTeamInd());
				id = thirdPartyRepository.save(dbThirdParty).getThirdPartyId();
			} else {
				ThirdParty thirdParty = new ThirdParty();
				thirdParty.setCustomerContactInd(thirdPartyMapper.isCustomerContactInd());
				thirdParty.setPartnerContactInd(thirdPartyMapper.isPartnerContactInd());
				thirdParty.setMonitizeInd(thirdPartyMapper.isMonitizeInd());
				thirdParty.setCustomerAiInd(thirdPartyMapper.isCustomerAiInd());
				thirdParty.setPartnerAiInd(thirdPartyMapper.isPartnerAiInd());
				thirdParty.setOrgId(thirdPartyMapper.getOrgId());
				thirdParty.setUserId(thirdPartyMapper.getUserId());
				thirdParty.setLastUpdatedOn(new Date());
				thirdParty.setAllowPrfWithVendorInd(thirdPartyMapper.isAllowPrfWithVendorInd());
				thirdParty.setEnableHiringTeamInd(thirdPartyMapper.isEnableHiringTeamInd());
				id = thirdPartyRepository.save(thirdParty).getThirdPartyId();
			}
		}
		return id;
	}

	@Override
	public ThirdPartyMapper getEngagementAccessByOrgId(String orgId) {
		ThirdPartyMapper mapper = new ThirdPartyMapper();
		ThirdParty pem = thirdPartyRepository.findByOrgId(orgId);

		if (pem != null) {
			mapper.setCustomerContactInd(pem.isCustomerContactInd());
			mapper.setPartnerContactInd(pem.isPartnerContactInd());
			mapper.setMonitizeInd(pem.isMonitizeInd());
			mapper.setCustomerAiInd(pem.isCustomerAiInd());
			mapper.setPartnerAiInd(pem.isPartnerAiInd());
			mapper.setOrgId(pem.getOrgId());
			mapper.setUserId(pem.getUserId());
			mapper.setThirdPartyId(pem.getThirdPartyId());
			mapper.setLastUpdatedOn(Utility.getISOFromDate(pem.getLastUpdatedOn()));
			mapper.setAllowPrfWithVendorInd(pem.isAllowPrfWithVendorInd());
			mapper.setEnableHiringTeamInd(pem.isEnableHiringTeamInd());
			EmployeeDetails employeeDetails = employeeRepository.getEmployeeByUserId(pem.getUserId());

			if (employeeDetails != null) {

				String middleName = " ";
				String lastName = "";

				if (!StringUtils.isEmpty(employeeDetails.getLastName())) {

					lastName = employeeDetails.getLastName();
				}

				if (employeeDetails.getMiddleName() != null && employeeDetails.getMiddleName().length() > 0) {

					middleName = employeeDetails.getMiddleName();
					mapper.setName(employeeDetails.getFirstName() + " " + middleName + " " + lastName);
				} else {

					mapper.setName(employeeDetails.getFirstName() + " " + lastName);
				}

			}
		}

		return mapper;
	}

	@Override
	public String saveDepartmentPermission(DepartmentPermissionMapper departmentPermissionMapper) {
		String id = null;

		if (departmentPermissionMapper != null) {
			DepartmentPermission departmentPermission = departmentPermissionRepository.getDepartmentPermission(
					departmentPermissionMapper.getRoleTypeId(), departmentPermissionMapper.getOrganizationId());
			if (departmentPermission != null) {
				List<String> vendorInds = departmentPermissionMapper.getVendor();
				// if (null!=vendorInds && !vendorInds.isEmpty()) {

				if (departmentPermissionMapper.getVendor().contains("Access")) {
					departmentPermission.setVendorAccessInd(true);
				} else {
					departmentPermission.setVendorAccessInd(false);
				}
				System.out.println("VendorAccessInd()>>" + departmentPermission.isCustomerAccessInd());
				if (vendorInds.contains("Create")) {
					departmentPermission.setVendorCreateInd(true);
				} else {
					departmentPermission.setVendorCreateInd(false);
				}
				System.out.println("VendorCreateInd>>" + departmentPermission.isVendorCreateInd());
				if (vendorInds.contains("Update")) {
					departmentPermission.setVendorUpdateInd(true);
				} else {
					departmentPermission.setVendorUpdateInd(false);
				}
				System.out.println("VendorUpdateInd>>" + departmentPermission.isVendorUpdateInd());
				if (vendorInds.contains("Delete")) {
					departmentPermission.setVendorDeleteInd(true);
				} else {
					departmentPermission.setVendorDeleteInd(false);
				}
				System.out.println("VendorDeleteInd>>" + departmentPermission.isVendorDeleteInd());
				if (vendorInds.contains("Full List")) {
					departmentPermission.setVendorFullListInd(true);
				} else {
					departmentPermission.setVendorFullListInd(false);
				}
				System.out.println("VendorFullListInd>>" + departmentPermission.isVendorFullListInd());

				List<String> customerInds = departmentPermissionMapper.getCustomer();
				// if (null!=customerInds && !customerInds.isEmpty()) {
				if (customerInds.contains("Access")) {
					departmentPermission.setCustomerAccessInd(true);
				} else {
					departmentPermission.setCustomerAccessInd(false);
				}
				if (customerInds.contains("Create")) {
					departmentPermission.setCustomerCreateInd(true);
				} else {
					departmentPermission.setCustomerCreateInd(false);
				}
				if (customerInds.contains("Update")) {
					departmentPermission.setCustomerUpdateInd(true);
				} else {
					departmentPermission.setCustomerUpdateInd(false);
				}
				if (customerInds.contains("Delete")) {
					departmentPermission.setCustomerDeleteInd(true);
				} else {
					departmentPermission.setCustomerDeleteInd(false);
				}
				if (customerInds.contains("Full List")) {
					departmentPermission.setCustomerFullListInd(true);
				} else {
					departmentPermission.setCustomerFullListInd(false);
				}
				

				List<String> opportunityInds = departmentPermissionMapper.getOpportunity();
				// if (null!=opportunityInds && !opportunityInds.isEmpty()) {
				if (opportunityInds.contains("Access")) {
					departmentPermission.setOpportunityAccessInd(true);
				} else {
					departmentPermission.setOpportunityAccessInd(false);
				}
				if (opportunityInds.contains("Create")) {
					departmentPermission.setOpportunityCreateInd(true);
				} else {
					departmentPermission.setOpportunityCreateInd(false);
				}
				if (opportunityInds.contains("Update")) {
					departmentPermission.setOpportunityUpdateInd(true);
				} else {
					departmentPermission.setOpportunityUpdateInd(false);
				}
				if (opportunityInds.contains("Delete")) {
					departmentPermission.setOpportunityDeleteInd(true);
				} else {
					departmentPermission.setOpportunityDeleteInd(false);
				}
				if (opportunityInds.contains("Full List")) {
					departmentPermission.setOpportunityFullListInd(true);
				} else {
					departmentPermission.setOpportunityFullListInd(false);
				}

				List<String> talentInds = departmentPermissionMapper.getTalent();
				// if (null!=talentInds && !talentInds.isEmpty()) {
				if (talentInds.contains("Access")) {
					departmentPermission.setTalentAccessInd(true);
				} else {
					departmentPermission.setTalentAccessInd(false);
				}
				if (talentInds.contains("Create")) {
					departmentPermission.setTalentCreateInd(true);
				} else {
					departmentPermission.setTalentCreateInd(false);
				}
				if (talentInds.contains("Update")) {
					departmentPermission.setTalentUpdateInd(true);
				} else {
					departmentPermission.setTalentUpdateInd(false);
				}
				if (talentInds.contains("Delete")) {
					departmentPermission.setTalentDeleteInd(true);
				} else {
					departmentPermission.setTalentDeleteInd(false);
				}
				if (talentInds.contains("Full List")) {
					departmentPermission.setTalentFullListInd(true);
				} else {
					departmentPermission.setTalentFullListInd(false);
				}
				
				List<String> requirementInds = departmentPermissionMapper.getRequirement();
				// if (null!=talentInds && !talentInds.isEmpty()) {
				if (requirementInds.contains("Access")) {
					departmentPermission.setRequirementAccessInd(true);
				} else {
					departmentPermission.setRequirementAccessInd(false);
				}
				if (requirementInds.contains("Create")) {
					departmentPermission.setRequirementCreateInd(true);
				} else {
					departmentPermission.setRequirementCreateInd(false);
				}
				if (requirementInds.contains("Update")) {
					departmentPermission.setRequirementUpdateInd(true);
				} else {
					departmentPermission.setRequirementUpdateInd(false);
				}
				if (requirementInds.contains("Delete")) {
					departmentPermission.setRequirementDeleteInd(true);
				} else {
					departmentPermission.setRequirementDeleteInd(false);
				}
				if (requirementInds.contains("Full List")) {
					departmentPermission.setRequirementFullListInd(true);
				} else {
					departmentPermission.setRequirementFullListInd(false);
				}

				List<String> publishInds = departmentPermissionMapper.getPublish();
				// if (null!=publishInds && !publishInds.isEmpty()) {
				if (publishInds.contains("Access")) {
					departmentPermission.setPublishAccessInd(true);
				} else {
					departmentPermission.setPublishAccessInd(false);
				}
				if (publishInds.contains("Create")) {
					departmentPermission.setPublishCreateInd(true);
				} else {
					departmentPermission.setPublishCreateInd(false);
				}
				if (publishInds.contains("Update")) {
					departmentPermission.setPublishUpdateInd(true);
				} else {
					departmentPermission.setPublishUpdateInd(false);
				}
				if (publishInds.contains("Delete")) {
					departmentPermission.setPublishDeleteInd(true);
				} else {
					departmentPermission.setPublishDeleteInd(false);
				}
				if (publishInds.contains("Full List")) {
					departmentPermission.setPublishFullListInd(true);
				} else {
					departmentPermission.setPublishFullListInd(false);
				}

				List<String> pulseInds = departmentPermissionMapper.getPulse();
				// if (null!=publishInds && !publishInds.isEmpty()) {
				if (pulseInds.contains("Access")) {
					departmentPermission.setPulseAccessInd(true);
				} else {
					departmentPermission.setPulseAccessInd(false);
				}
				if (pulseInds.contains("Create")) {
					departmentPermission.setPulseCreateInd(true);
				} else {
					departmentPermission.setPulseCreateInd(false);
				}
				if (pulseInds.contains("Update")) {
					departmentPermission.setPulseUpdateInd(true);
				} else {
					departmentPermission.setPulseUpdateInd(false);
				}
				if (pulseInds.contains("Delete")) {
					departmentPermission.setPulseDeleteInd(true);
				} else {
					departmentPermission.setPulseDeleteInd(false);
				}
				if (pulseInds.contains("Full List")) {
					departmentPermission.setPulseFullListInd(true);
				} else {
					departmentPermission.setPulseFullListInd(false);
				}

				List<String> contactInds = departmentPermissionMapper.getContact();
				// if (null!=customerInds && !customerInds.isEmpty()) {
				if (contactInds.contains("Access")) {
					departmentPermission.setContactAccessInd(true);
				} else {
					departmentPermission.setContactAccessInd(false);
				}
				if (contactInds.contains("Create")) {
					departmentPermission.setContactCreateInd(true);
				} else {
					departmentPermission.setContactCreateInd(false);
				}
				if (contactInds.contains("Update")) {
					departmentPermission.setContactUpdateInd(true);
				} else {
					departmentPermission.setContactUpdateInd(false);
				}
				if (contactInds.contains("Delete")) {
					departmentPermission.setContactDeleteInd(true);
				} else {
					departmentPermission.setContactDeleteInd(false);
				}
				if (contactInds.contains("Full List")) {
					departmentPermission.setContactFullListInd(true);
				} else {
					departmentPermission.setContactFullListInd(false);
				}

				List<String> assessmentInds = departmentPermissionMapper.getAssessment();
				// if (null!=customerInds && !customerInds.isEmpty()) {
				if (assessmentInds.contains("Access")) {
					departmentPermission.setAssessmentAccessInd(true);
				} else {
					departmentPermission.setAssessmentAccessInd(false);
				}
				if (assessmentInds.contains("Create")) {
					departmentPermission.setAssessmentCreateInd(true);
				} else {
					departmentPermission.setAssessmentCreateInd(false);
				}
				if (assessmentInds.contains("Update")) {
					departmentPermission.setAssessmentUpdateInd(true);
				} else {
					departmentPermission.setAssessmentUpdateInd(false);
				}
				if (assessmentInds.contains("Delete")) {
					departmentPermission.setAssessmentDeleteInd(true);
				} else {
					departmentPermission.setAssessmentDeleteInd(false);
				}
				if (assessmentInds.contains("Full List")) {
					departmentPermission.setAssessmentFullListInd(true);
				} else {
					departmentPermission.setAssessmentFullListInd(false);
				}

				List<String> leadsInds = departmentPermissionMapper.getLeads();
				// if (null!=customerInds && !customerInds.isEmpty()) {
				if (leadsInds.contains("Access")) {
					departmentPermission.setLeadsAccessInd(true);
				} else {
					departmentPermission.setLeadsAccessInd(false);
				}
				if (leadsInds.contains("Create")) {
					departmentPermission.setLeadsCreateInd(true);
				} else {
					departmentPermission.setLeadsCreateInd(false);
				}
				if (leadsInds.contains("Update")) {
					departmentPermission.setLeadsUpdateInd(true);
				} else {
					departmentPermission.setLeadsUpdateInd(false);
				}
				if (leadsInds.contains("Delete")) {
					departmentPermission.setLeadsDeleteInd(true);
				} else {
					departmentPermission.setLeadsDeleteInd(false);
				}
				if (leadsInds.contains("Full List")) {
					departmentPermission.setLeadsFullListInd(true);
				} else {
					departmentPermission.setLeadsFullListInd(false);
				}
				
				
				List<String> testInds = departmentPermissionMapper.getTest();
				// if (null!=customerInds && !customerInds.isEmpty()) {
				if (testInds.contains("Access")) {
					departmentPermission.setTestAccessInd(true);
				} else {
					departmentPermission.setTestAccessInd(false);
				}
				if (testInds.contains("Create")) {
					departmentPermission.setTestCreateInd(true);
				} else {
					departmentPermission.setTestCreateInd(false);
				}
				if (testInds.contains("Update")) {
					departmentPermission.setTestUpdateInd(true);
				} else {
					departmentPermission.setTestUpdateInd(false);
				}
				if (testInds.contains("Delete")) {
					departmentPermission.setTestDeleteInd(true);
				} else {
					departmentPermission.setTestDeleteInd(false);
				}
				if (testInds.contains("Full List")) {
					departmentPermission.setTestFullListInd(true);
				} else {
					departmentPermission.setTestFullListInd(false);
				}
				
				List<String> programInds = departmentPermissionMapper.getProgram();
				// if (null!=customerInds && !customerInds.isEmpty()) {
				if (programInds.contains("Access")) {
					departmentPermission.setProgramAccessInd(true);
				} else {
					departmentPermission.setProgramAccessInd(false);
				}
				if (programInds.contains("Create")) {
					departmentPermission.setProgramCreateInd(true);
				} else {
					departmentPermission.setProgramCreateInd(false);
				}
				if (programInds.contains("Update")) {
					departmentPermission.setProgramUpdateInd(true);
				} else {
					departmentPermission.setProgramUpdateInd(false);
				}
				if (programInds.contains("Delete")) {
					departmentPermission.setProgramDeleteInd(true);
				} else {
					departmentPermission.setProgramDeleteInd(false);
				}
				if (programInds.contains("Full List")) {
					departmentPermission.setProgramFullListInd(true);
				} else {
					departmentPermission.setProgramFullListInd(false);
				}
				
				
				
				List<String> courseInds = departmentPermissionMapper.getCourse();
				// if (null!=customerInds && !customerInds.isEmpty()) {
				if (courseInds.contains("Access")) {
					departmentPermission.setCourseAccessInd(true);
				} else {
					departmentPermission.setCourseAccessInd(false);
				}
				if (courseInds.contains("Create")) {
					departmentPermission.setCourseCreateInd(true);
				} else {
					departmentPermission.setCourseCreateInd(false);
				}
				if (courseInds.contains("Update")) {
					departmentPermission.setCourseUpdateInd(true);
				} else {
					departmentPermission.setCourseUpdateInd(false);
				}
				if (courseInds.contains("Delete")) {
					departmentPermission.setCourseDeleteInd(true);
				} else {
					departmentPermission.setCourseDeleteInd(false);
				}
				if (courseInds.contains("Full List")) {
					departmentPermission.setCourseFullListInd(true);
				} else {
					departmentPermission.setCourseFullListInd(false);
				}
				
				
				List<String> hoursInds = departmentPermissionMapper.getHours();
				// if (null!=customerInds && !customerInds.isEmpty()) {
				if (hoursInds.contains("Access")) {
					departmentPermission.setHoursAccessInd(true);
				} else {
					departmentPermission.setHoursAccessInd(false);
				}
				if (hoursInds.contains("Create")) {
					departmentPermission.setHoursCreateInd(true);
				} else {
					departmentPermission.setHoursCreateInd(false);
				}
				if (hoursInds.contains("Update")) {
					departmentPermission.setHoursUpdateInd(true);
				} else {
					departmentPermission.setHoursUpdateInd(false);
				}
				if (hoursInds.contains("Delete")) {
					departmentPermission.setHoursDeleteInd(true);
				} else {
					departmentPermission.setHoursDeleteInd(false);
				}
				if (hoursInds.contains("Full List")) {
					departmentPermission.setHoursFullListInd(true);
				} else {
					departmentPermission.setHoursFullListInd(false);
				}
				
				
				List<String> taskInds = departmentPermissionMapper.getTask();
				// if (null!=customerInds && !customerInds.isEmpty()) {
				/*if (taskInds.contains("Access")) {
					departmentPermission.setTaskAccessInd(true);
				} else {
					departmentPermission.setTaskAccessInd(false);
				}
				if (taskInds.contains("Create")) {
					departmentPermission.setTaskCreateInd(true);
				} else {
					departmentPermission.setTaskCreateInd(false);
				}
				if (taskInds.contains("Update")) {
					departmentPermission.setTaskUpdateInd(true);
				} else {
					departmentPermission.setTaskUpdateInd(false);
				}
				if (taskInds.contains("Delete")) {
					departmentPermission.setTaskDeleteInd(true);
				} else {
					departmentPermission.setTaskDeleteInd(false);
				}*/
				if (taskInds.contains("Full List")) {
					departmentPermission.setTaskFullListInd(true);
				} else {
					departmentPermission.setTaskFullListInd(false);
				}
				
				
				List<String> comercialInds = departmentPermissionMapper.getComercial();
				// if (null!=customerInds && !customerInds.isEmpty()) {
				if (comercialInds.contains("Access")) {
					departmentPermission.setComercialAccessInd(true);
				} else {
					departmentPermission.setComercialAccessInd(false);
				}
				if (comercialInds.contains("Create")) {
					departmentPermission.setComercialCreateInd(true);
				} else {
					departmentPermission.setComercialCreateInd(false);
				}
				if (comercialInds.contains("Update")) {
					departmentPermission.setComercialUpdateInd(true);
				} else {
					departmentPermission.setComercialUpdateInd(false);
				}
				if (comercialInds.contains("Delete")) {
					departmentPermission.setComercialDeleteInd(true);
				} else {
					departmentPermission.setComercialDeleteInd(false);
				}
				if (comercialInds.contains("Full List")) {
					departmentPermission.setComercialFullListInd(true);
				} else {
					departmentPermission.setComercialFullListInd(false);
				}
				
				List<String> locationInds = departmentPermissionMapper.getLocation();
				// if (null!=customerInds && !customerInds.isEmpty()) {
				if (locationInds.contains("Access")) {
					departmentPermission.setLocationAccessInd(true);
				} else {
					departmentPermission.setLocationAccessInd(false);
				}
				if (locationInds.contains("Create")) {
					departmentPermission.setLocationCreateInd(true);
				} else {
					departmentPermission.setLocationCreateInd(false);
				}
				if (locationInds.contains("Update")) {
					departmentPermission.setLocationUpdateInd(true);
				} else {
					departmentPermission.setLocationUpdateInd(false);
				}
				if (locationInds.contains("Delete")) {
					departmentPermission.setLocationDeleteInd(true);
				} else {
					departmentPermission.setLocationDeleteInd(false);
				}
				if (locationInds.contains("Full List")) {
					departmentPermission.setLocationFullListInd(true);
				} else {
					departmentPermission.setLocationFullListInd(false);
				}
				
				List<String> leaveInds = departmentPermissionMapper.getLeave();
				// if (null!=customerInds && !customerInds.isEmpty()) {
				if (leaveInds.contains("Access")) {
					departmentPermission.setLeaveAccessInd(true);
				} else {
					departmentPermission.setLeaveAccessInd(false);
				}
				if (leaveInds.contains("Full List")) {
					departmentPermission.setLeaveFullListInd(true);
				} else {
					departmentPermission.setLeaveFullListInd(false);
				}
				
				List<String> expenseInds = departmentPermissionMapper.getExpense();
				// if (null!=customerInds && !customerInds.isEmpty()) {
				if (expenseInds.contains("Access")) {
					departmentPermission.setExpenseAccessInd(true);
				} else {
					departmentPermission.setExpenseAccessInd(false);
				}
				if (expenseInds.contains("Full List")) {
					departmentPermission.setExpenseFullListInd(true);
				} else {
					departmentPermission.setExpenseFullListInd(false);
				}
				
				List<String> mileageInds = departmentPermissionMapper.getMileage();
				// if (null!=customerInds && !customerInds.isEmpty()) {
				if (mileageInds.contains("Access")) {
					departmentPermission.setMileageAccessInd(true);
				} else {
					departmentPermission.setMileageAccessInd(false);
				}
				if (mileageInds.contains("Full List")) {
					departmentPermission.setMileageFullListInd(true);
				} else {
					departmentPermission.setMileageFullListInd(false);
				}
				
				List<String> userInds = departmentPermissionMapper.getUser();
				// if (null!=customerInds && !customerInds.isEmpty()) {
				if (userInds.contains("Access")) {
					departmentPermission.setUserAccessInd(true);
				} else {
					departmentPermission.setUserAccessInd(false);
				}
				if (userInds.contains("Create")) {
					departmentPermission.setUserCreateInd(true);
				} else {
					departmentPermission.setUserCreateInd(false);
				}
				if (userInds.contains("Update")) {
					departmentPermission.setUserUpdateInd(true);
				} else {
					departmentPermission.setUserUpdateInd(false);
				}
				if (userInds.contains("Delete")) {
					departmentPermission.setUserDeleteInd(true);
				} else {
					departmentPermission.setUserDeleteInd(false);
				}
				if (userInds.contains("Access Plus")) {
					departmentPermission.setUserAccessPlusInd(true);
				} else {
					departmentPermission.setUserAccessPlusInd(false);
				}
				
				List<String> accountInds = departmentPermissionMapper.getAccount();
				// if (null!=customerInds && !customerInds.isEmpty()) {
				if (accountInds.contains("Access")) {
					departmentPermission.setAccountAccessInd(true);
				} else {
					departmentPermission.setAccountAccessInd(false);
				}
				if (accountInds.contains("Create")) {
					departmentPermission.setAccountCreateInd(true);
				} else {
					departmentPermission.setAccountCreateInd(false);
				}
				if (accountInds.contains("Update")) {
					departmentPermission.setAccountUpdateInd(true);
				} else {
					departmentPermission.setAccountUpdateInd(false);
				}
				if (accountInds.contains("Delete")) {
					departmentPermission.setAccountDeleteInd(true);
				} else {
					departmentPermission.setAccountDeleteInd(false);
				}
				if (accountInds.contains("Full List")) {
					departmentPermission.setAccountFullListInd(true);
				} else {
					departmentPermission.setAccountFullListInd(false);
				}
				if (accountInds.contains("Info")) {
					departmentPermission.setAccountInfoInd(true);
				} else {
					departmentPermission.setAccountInfoInd(false);
				}
				
				List<String> orderInds = departmentPermissionMapper.getOrder();
				// if (null!=customerInds && !customerInds.isEmpty()) {
				if (orderInds.contains("Access")) {
					departmentPermission.setOrderAccessInd(true);
				} else {
					departmentPermission.setOrderAccessInd(false);
				}
				if (orderInds.contains("Create")) {
					departmentPermission.setOrderCreateInd(true);
				} else {
					departmentPermission.setOrderCreateInd(false);
				}
				if (orderInds.contains("Update")) {
					departmentPermission.setOrderUpdateInd(true);
				} else {
					departmentPermission.setOrderUpdateInd(false);
				}
				if (orderInds.contains("Delete")) {
					departmentPermission.setOrderDeleteInd(true);
				} else {
					departmentPermission.setOrderDeleteInd(false);
				}
				if (orderInds.contains("Full List")) {
					departmentPermission.setOrderFullListInd(true);
				} else {
					departmentPermission.setOrderFullListInd(false);
				}

				
				List<String> materialInd = departmentPermissionMapper.getMaterial();
				// if (null!=customerInds && !customerInds.isEmpty()) {
				if (materialInd.contains("Access")) {
					departmentPermission.setMaterialAccessInd(true);;
				} else {
					departmentPermission.setMaterialAccessInd(false);
				}
				if (materialInd.contains("Create")) {
					departmentPermission.setMaterialCreateInd(true);
				} else {
					departmentPermission.setMaterialCreateInd(false);
				}
				if (materialInd.contains("Update")) {
					departmentPermission.setMaterialUpdateInd(true);
				} else {
					departmentPermission.setMaterialUpdateInd(false);
				}
				if (materialInd.contains("Delete")) {
					departmentPermission.setMaterialDeleteInd(true);
				} else {
					departmentPermission.setMaterialDeleteInd(false);
				}
			
				
				List<String> supplierInds = departmentPermissionMapper.getSupplier();
				// if (null!=customerInds && !customerInds.isEmpty()) {
				if (supplierInds.contains("Access")) {
					departmentPermission.setSupplierAccessInd(true);
				} else {
					departmentPermission.setSupplierAccessInd(false);
				}
				if (supplierInds.contains("Create")) {
					departmentPermission.setSupplierCreateInd(true);
				} else {
					departmentPermission.setSupplierCreateInd(false);
				}
				if (supplierInds.contains("Update")) {
					departmentPermission.setSupplierUpdateInd(true);
				} else {
					departmentPermission.setSupplierUpdateInd(false);
				}
				if (supplierInds.contains("Delete")) {
					departmentPermission.setSupplierDeleteInd(true);
				} else {
					departmentPermission.setSupplierDeleteInd(false);
				}
				if (supplierInds.contains("Full List")) {
					departmentPermission.setSupplierFullListInd(true);
				} else {
					departmentPermission.setSupplierFullListInd(false);
				}
				if (supplierInds.contains("Block")) {
					departmentPermission.setSupplierBlockInd(true);
				} else {
					departmentPermission.setSupplierBlockInd(false);
				}
				if (supplierInds.contains("Inventory")) {
					departmentPermission.setSupplierInventoryInd(true);
				} else {
					departmentPermission.setSupplierInventoryInd(false);
				}
				
				List<String> inventoryInds = departmentPermissionMapper.getInventory();
				// if (null!=customerInds && !customerInds.isEmpty()) {
				if (inventoryInds.contains("Access")) {
					departmentPermission.setInventoryAccessInd(true);
				} else {
					departmentPermission.setInventoryAccessInd(false);
				}
				if (inventoryInds.contains("Create")) {
					departmentPermission.setInventoryCreateInd(true);
				} else {
					departmentPermission.setInventoryCreateInd(false);
				}
				if (inventoryInds.contains("Update")) {
					departmentPermission.setInventoryUpdateInd(true);
				} else {
					departmentPermission.setInventoryUpdateInd(false);
				}
				if (inventoryInds.contains("Delete")) {
					departmentPermission.setInventoryDeleteInd(true);
				} else {
					departmentPermission.setInventoryDeleteInd(false);
				}
				if (inventoryInds.contains("Full List")) {
					departmentPermission.setInventoryFullListInd(true);
				} else {
					departmentPermission.setInventoryFullListInd(false);
				}
				
				List<String> refurbishInds = departmentPermissionMapper.getRefurbish();
				// if (null!=customerInds && !customerInds.isEmpty()) {
				if (refurbishInds.contains("Workshop")) {
					departmentPermission.setRefurbishWorkshopInd(true);
				} else {
					departmentPermission.setRefurbishWorkshopInd(false);
				}
				if (refurbishInds.contains("Adminview")) {
					departmentPermission.setRefurbishAdminviewInd(true);
				} else {
					departmentPermission.setRefurbishAdminviewInd(false);
				}
				if (refurbishInds.contains("Adminassign")) {
					departmentPermission.setRefurbishAdminAssignInd(true);
				} else {
					departmentPermission.setRefurbishAdminAssignInd(false);
				}
				
				
				// if (null!=customerInds && !customerInds.isEmpty()) {
				List<String> dashboardInds = departmentPermissionMapper.getDashboard();
				if (dashboardInds.contains("Access")) {
					departmentPermission.setDashboardAccessInd(true);
				} else {
					departmentPermission.setDashboardAccessInd(false);
				}if (dashboardInds.contains("Full List")) {
					departmentPermission.setDashboardFullListInd(true);
				} else {
					departmentPermission.setDashboardFullListInd(false);
				}if (dashboardInds.contains("Regional")) {
					departmentPermission.setDashboardRegionalInd(true);
				} else {
					departmentPermission.setDashboardRegionalInd(false);
				}
				
				List<String> settingInds = departmentPermissionMapper.getSettings();
				// if (null!=customerInds && !customerInds.isEmpty()) {
				if (settingInds.contains("Access")) {
					departmentPermission.setSettingsAccessInd(true);
				} else {
					departmentPermission.setSettingsAccessInd(false);
				}
				
				List<String> junkInds = departmentPermissionMapper.getJunk();
				// if (null!=customerInds && !customerInds.isEmpty()) {
				if (junkInds.contains("Access")) {
					departmentPermission.setJunkAccessInd(true);
				} else {
					departmentPermission.setJunkAccessInd(false);
				}
				if (junkInds.contains("Transfer")) {
					departmentPermission.setJunkTransferInd(true);
				} else {
					departmentPermission.setJunkAccessInd(false);
				}
				
				List<String> investorInds = departmentPermissionMapper.getInvestor();
				// if (null!=customerInds && !customerInds.isEmpty()) {
				if (investorInds.contains("Access")) {
					departmentPermission.setInvestorAccessInd(true);
				} else {
					departmentPermission.setInvestorAccessInd(false);
				}
				if (investorInds.contains("Create")) {
					departmentPermission.setInvestorCreateInd(true);
				} else {
					departmentPermission.setInvestorCreateInd(false);
				}
				if (investorInds.contains("Update")) {
					departmentPermission.setInvestorUpdateInd(true);
				} else {
					departmentPermission.setInvestorUpdateInd(false);
				}
				if (investorInds.contains("Delete")) {
					departmentPermission.setInvestorDeleteInd(true);
				} else {
					departmentPermission.setInvestorDeleteInd(false);
				}
				if (investorInds.contains("Full List")) {
					departmentPermission.setInvestorFullListInd(true);
				} else {
					departmentPermission.setInvestorFullListInd(false);
				}
				
				List<String> investorCustomerInds = departmentPermissionMapper.getPitch();
				// if (null!=customerInds && !customerInds.isEmpty()) {
				if (investorCustomerInds.contains("Access")) {
					departmentPermission.setPitchAccessInd(true);
				} else {
					departmentPermission.setPitchAccessInd(false);
				}
				if (investorCustomerInds.contains("Create")) {
					departmentPermission.setPitchCreateInd(true);
				} else {
					departmentPermission.setPitchCreateInd(false);
				}
				if (investorCustomerInds.contains("Update")) {
					departmentPermission.setPitchUpdateInd(true);
				} else {
					departmentPermission.setPitchUpdateInd(false);
				}
				if (investorCustomerInds.contains("Delete")) {
					departmentPermission.setPitchDeleteInd(true);
				} else {
					departmentPermission.setPitchDeleteInd(false);
				}
				if (investorCustomerInds.contains("Full List")) {
					departmentPermission.setPitchFullListInd(true);
				} else {
					departmentPermission.setPitchFullListInd(false);
				}
				
				List<String> investorContactInds = departmentPermissionMapper.getInvestorContact();
				// if (null!=customerInds && !customerInds.isEmpty()) {
				if (investorContactInds.contains("Access")) {
					departmentPermission.setInvestorContactAccessInd(true);
				} else {
					departmentPermission.setInvestorContactAccessInd(false);
				}
				if (investorContactInds.contains("Create")) {
					departmentPermission.setInvestorContactCreateInd(true);
				} else {
					departmentPermission.setInvestorContactCreateInd(false);
				}
				if (investorContactInds.contains("Update")) {
					departmentPermission.setInvestorContactUpdateInd(true);
				} else {
					departmentPermission.setInvestorContactUpdateInd(false);
				}
				if (investorContactInds.contains("Delete")) {
					departmentPermission.setInvestorContactDeleteInd(true);
				} else {
					departmentPermission.setInvestorContactDeleteInd(false);
				}
				if (investorContactInds.contains("Full List")) {
					departmentPermission.setInvestorContactFullListInd(true);
				} else {
					departmentPermission.setInvestorContactFullListInd(false);
				}
				
				List<String> investorOppertunityInds = departmentPermissionMapper.getDeal();
				// if (null!=customerInds && !customerInds.isEmpty()) {
				if (investorOppertunityInds.contains("Access")) {
					departmentPermission.setDealAccessInd(true);
				} else {
					departmentPermission.setDealAccessInd(false);
				}
				if (investorOppertunityInds.contains("Create")) {
					departmentPermission.setDealCreateInd(true);
				} else {
					departmentPermission.setDealCreateInd(false);
				}
				if (investorOppertunityInds.contains("Update")) {
					departmentPermission.setDealUpdateInd(true);
				} else {
					departmentPermission.setDealUpdateInd(false);
				}
				if (investorOppertunityInds.contains("Delete")) {
					departmentPermission.setDealDeleteInd(true);
				} else {
					departmentPermission.setDealDeleteInd(false);
				}
				if (investorOppertunityInds.contains("Full List")) {
					departmentPermission.setDealFullListInd(true);
				} else {
					departmentPermission.setDealFullListInd(false);
				}
				
				List<String> repositoryInds = departmentPermissionMapper.getRepository();
				// if (null!=customerInds && !customerInds.isEmpty()) {
				if (repositoryInds.contains("Create")) {
					departmentPermission.setRepositoryCreateInd(true);
				} else {
					departmentPermission.setRepositoryCreateInd(false);
				}
				
				
				List<String> shipperInd = departmentPermissionMapper.getShipper();
				// if (null!=customerInds && !customerInds.isEmpty()) {
				if (shipperInd.contains("Access")) {
					departmentPermission.setShipperAccessInd(true);
				} else {
					departmentPermission.setShipperAccessInd(false);
				}
				if (shipperInd.contains("Create")) {
					departmentPermission.setShipperCreateInd(true);
				} else {
					departmentPermission.setShipperCreateInd(false);
				}
				if (shipperInd.contains("Update")) {
					departmentPermission.setShipperUpdateInd(true);
				} else {
					departmentPermission.setShipperUpdateInd(false);
				}
				if (shipperInd.contains("Delete")) {
					departmentPermission.setShipperDeleteInd(true);
				} else {
					departmentPermission.setShipperDeleteInd(false);
				}
				if (shipperInd.contains("Full List")) {
					departmentPermission.setShipperFullListInd(true);
				} else {
					departmentPermission.setShipperFullListInd(false);
				}
				
				List<String> plantAccessInd = departmentPermissionMapper.getPlant();
				// if (null!=customerInds && !customerInds.isEmpty()) {
				if (plantAccessInd.contains("Access")) {
					departmentPermission.setPlantAccessInd(true);
				} else {
					departmentPermission.setPlantAccessInd(false);
				}
				if (plantAccessInd.contains("Create")) {
					departmentPermission.setPlantCreateInd(true);
				} else {
					departmentPermission.setPlantCreateInd(false);
				}
				if (plantAccessInd.contains("Update")) {
					departmentPermission.setPlantUpdateInd(true);
				} else {
					departmentPermission.setPlantUpdateInd(false);
				}
				if (plantAccessInd.contains("Delete")) {
					departmentPermission.setPlantDeleteInd(true);
				} else {
					departmentPermission.setPlantDeleteInd(false);
				}
				if (plantAccessInd.contains("Full List")) {
					departmentPermission.setPlantFullListInd(true);
				} else {
					departmentPermission.setPlantFullListInd(false);
				}
				
				List<String> teamsInd = departmentPermissionMapper.getTeams();
				// if (null!=customerInds && !customerInds.isEmpty()) {
				if (teamsInd.contains("Access")) {
					departmentPermission.setTeamsAccessInd(true);
				} else {
					departmentPermission.setTeamsAccessInd(false);
				}
				if (teamsInd.contains("Create")) {
					departmentPermission.setTeamsCreateInd(true);
				} else {
					departmentPermission.setTeamsCreateInd(false);
				}
				if (teamsInd.contains("Update")) {
					departmentPermission.setTeamsUpdateInd(true);
				} else {
					departmentPermission.setTeamsUpdateInd(false);
				}
				if (teamsInd.contains("Delete")) {
					departmentPermission.setTeamsDeleteInd(true);
				} else {
					departmentPermission.setTeamsDeleteInd(false);
				}
				if (teamsInd.contains("Full List")) {
					departmentPermission.setTeamsFullListInd(true);
				} else {
					departmentPermission.setTeamsFullListInd(false);
				}
				
				List<String> basicInds = departmentPermissionMapper.getBasic();
				// if (null!=customerInds && !customerInds.isEmpty()) {
				if (basicInds.contains("Access")) {
					departmentPermission.setBasicAccessInd(true);
				} else {
					departmentPermission.setBasicAccessInd(false);
				}
				
				List<String> catalogInd = departmentPermissionMapper.getCatalog();
				// if (null!=customerInds && !customerInds.isEmpty()) {
				if (catalogInd.contains("Access")) {
					departmentPermission.setCatalogAccessInd(true);
				} else {
					departmentPermission.setCatalogAccessInd(false);
				}
				if (catalogInd.contains("Create")) {
					departmentPermission.setCatalogCreateInd(true);
				} else {
					departmentPermission.setCatalogCreateInd(false);
				}
				if (catalogInd.contains("Update")) {
					departmentPermission.setCatalogUpdateInd(true);
				} else {
					departmentPermission.setCatalogUpdateInd(false);
				}
				if (catalogInd.contains("Delete")) {
					departmentPermission.setCatalogDeleteInd(true);
				} else {
					departmentPermission.setCatalogDeleteInd(false);
				}
				
				List<String> paymentInd = departmentPermissionMapper.getPayment();
				// if (null!=customerInds && !customerInds.isEmpty()) {
				if (paymentInd.contains("Access")) {
					departmentPermission.setPaymentAccessInd(true);
				} else {
					departmentPermission.setPaymentAccessInd(false);
				}
				
				List<String>collectionInd = departmentPermissionMapper.getCollection();
				// if (null!=customerInds && !customerInds.isEmpty()) {
				if (collectionInd.contains("Access")) {
					departmentPermission.setCollectionAccessInd(true);
				} else {
					departmentPermission.setCollectionAccessInd(false);
				}
				if (collectionInd.contains("Approve")) {
					departmentPermission.setCollectionApproveInd(true);
				} else {
					departmentPermission.setCollectionApproveInd(false);
				}
				List<String> holidayInds = departmentPermissionMapper.getHoliday();
				// if (null!=customerInds && !customerInds.isEmpty()) {
				if (holidayInds.contains("Access")) {
					departmentPermission.setHolidayAccessInd(true);
				} else {
					departmentPermission.setHolidayAccessInd(false);
				}
				
				List<String> topicInd = departmentPermissionMapper.getTopic();
				if (topicInd.contains("Access")) {
					departmentPermission.setTopicAccessInd(true);
				} else {
					departmentPermission.setTopicAccessInd(false);
				}
				if (topicInd.contains("Create")) {
					departmentPermission.setTopicCreateInd(true);
				} else {
					departmentPermission.setTopicCreateInd(false);
				}
				if (topicInd.contains("Update")) {
					departmentPermission.setTopicUpdateInd(true);
				} else {
					departmentPermission.setTopicUpdateInd(false);
				}
				if (topicInd.contains("Delete")) {
					departmentPermission.setTopicDeleteInd(true);
				} else {
					departmentPermission.setTopicDeleteInd(false);
				}
				if (topicInd.contains("Full List")) {
					departmentPermission.setTopicFullListInd(true);
				} else {
					departmentPermission.setTopicFullListInd(false);
				}
				
				List<String> procurementInd = departmentPermissionMapper.getProcurement();
				if (procurementInd.contains("Access")) {
					departmentPermission.setProcurementAccessInd(true);
				} else {
					departmentPermission.setProcurementAccessInd(false);
				}
				if (procurementInd.contains("Create")) {
					departmentPermission.setProcurementCreateInd(true);
				} else {
					departmentPermission.setProcurementCreateInd(false);
				}
				if (procurementInd.contains("Update")) {
					departmentPermission.setProcurementUpdateInd(true);
				} else {
					departmentPermission.setProcurementUpdateInd(false);
				}
				if (procurementInd.contains("Delete")) {
					departmentPermission.setProcurementDeleteInd(true);
				} else {
					departmentPermission.setProcurementDeleteInd(false);
				}
				if (procurementInd.contains("Full List")) {
					departmentPermission.setProcurementFullListInd(true);
				} else {
					departmentPermission.setProcurementFullListInd(false);
				}
				
				List<String> subscriptionInd = departmentPermissionMapper.getSubscription();
				if (subscriptionInd.contains("Access")) {
					departmentPermission.setSubscriptionAccessInd(true);
				} else {
					departmentPermission.setSubscriptionAccessInd(false);
				}
				if (subscriptionInd.contains("Create")) {
					departmentPermission.setSubscriptionCreateInd(true);
				} else {
					departmentPermission.setSubscriptionCreateInd(false);
				}
				if (subscriptionInd.contains("Update")) {
					departmentPermission.setSubscriptionUpdateInd(true);
				} else {
					departmentPermission.setSubscriptionUpdateInd(false);
				}
				if (subscriptionInd.contains("Delete")) {
					departmentPermission.setSubscriptionDeleteInd(true);
				} else {
					departmentPermission.setSubscriptionDeleteInd(false);
				}
			
				
				List<String> productionInd = departmentPermissionMapper.getProduction();
				if (productionInd.contains("Access")) {
					departmentPermission.setProductionAccessInd(true);
				} else {
					departmentPermission.setProductionAccessInd(false);
				}
				if (productionInd.contains("Create")) {
					departmentPermission.setProductionCreateInd(true);
				} else {
					departmentPermission.setProductionCreateInd(false);
				}
				if (productionInd.contains("Update")) {
					departmentPermission.setProductionUpdateInd(true);
				} else {
					departmentPermission.setProductionUpdateInd(false);
				}
				if (productionInd.contains("Delete")) {
					departmentPermission.setProductionDeleteInd(true);
				} else {
					departmentPermission.setProductionDeleteInd(false);
				}
				
				List<String> reportInd = departmentPermissionMapper.getReport();
				if (reportInd.contains("Full List")) {
					departmentPermission.setReportFullListInd(true);
				} else {
					departmentPermission.setReportFullListInd(false);
				}
				
				List<String> dataRoomInd = departmentPermissionMapper.getDataRoom();
				if (dataRoomInd.contains("Access")) {
					departmentPermission.setDataRoomAccessInd(true);
				} else {
					departmentPermission.setDataRoomAccessInd(false);
				}
				if (dataRoomInd.contains("Create")) {
					departmentPermission.setDataRoomCreateInd(true);
				} else {
					departmentPermission.setDataRoomCreateInd(false);
				}
				if (dataRoomInd.contains("Update")) {
					departmentPermission.setDataRoomUpdateInd(true);
				} else {
					departmentPermission.setDataRoomUpdateInd(false);
				}
				if (dataRoomInd.contains("Delete")) {
					departmentPermission.setDataRoomDeleteInd(true);
				} else {
					departmentPermission.setDataRoomDeleteInd(false);
				}
				if (dataRoomInd.contains("Full List")) {
					departmentPermission.setDataRoomFullListInd(true);
				} else {
					departmentPermission.setDataRoomFullListInd(false);
				}
				
				List<String> scannerInd = departmentPermissionMapper.getScanner();
				if (scannerInd.contains("Access")) {
					departmentPermission.setScannerAccessInd(true);
				} else {
					departmentPermission.setScannerAccessInd(false);
				}
				
				List<String> qualityInd = departmentPermissionMapper.getQuality();
				if (qualityInd.contains("Access")) {
					departmentPermission.setQualityAccessInd(true);
				} else {
					departmentPermission.setQualityAccessInd(false);
				}
				if (qualityInd.contains("Create")) {
					departmentPermission.setQualityCreateInd(true);
				} else {
					departmentPermission.setQualityCreateInd(false);
				}
				if (qualityInd.contains("Update")) {
					departmentPermission.setQualityUpdateInd(true);
				} else {
					departmentPermission.setQualityUpdateInd(false);
				}
				if (qualityInd.contains("Delete")) {
					departmentPermission.setQualityDeleteInd(true);
				} else {
					departmentPermission.setQualityDeleteInd(false);
				}
				if (qualityInd.contains("Full List")) {
					departmentPermission.setQualityFullListInd(true);
				} else {
					departmentPermission.setQualityFullListInd(false);
				}
				
				List<String> clubInd = departmentPermissionMapper.getClub();
				if (clubInd.contains("Access")) {
					departmentPermission.setClubAccessInd(true);
				} else {
					departmentPermission.setClubAccessInd(false);
				}
				if (clubInd.contains("Create")) {
					departmentPermission.setClubCreateInd(true);
				} else {
					departmentPermission.setClubCreateInd(false);
				}
				if (clubInd.contains("Update")) {
					departmentPermission.setClubUpdateInd(true);
				} else {
					departmentPermission.setClubUpdateInd(false);
				}
				if (clubInd.contains("Delete")) {
					departmentPermission.setClubDeleteInd(true);
				} else {
					departmentPermission.setClubDeleteInd(false);
				}
				if (clubInd.contains("Full List")) {
					departmentPermission.setClubFullListInd(true);
				} else {
					departmentPermission.setClubFullListInd(false);
				}
				
				List<String> calenderInd = departmentPermissionMapper.getCalender();
				if (calenderInd.contains("Manage")) {
					departmentPermission.setCalenderManageInd(true);
				} else {
					departmentPermission.setCalenderManageInd(false);
				}
				if (calenderInd.contains("View")) {
					departmentPermission.setCalenderViewInd(true);
				} else {
					departmentPermission.setCalenderViewInd(false);
				}
				
				departmentPermission.setRoleTypeId(departmentPermissionMapper.getRoleTypeId());
				departmentPermission.setDepartmentId(departmentPermissionMapper.getDepartmentId());
				departmentPermission.setUserId(departmentPermissionMapper.getUserId());
				departmentPermission.setOrgId(departmentPermissionMapper.getOrganizationId());
				departmentPermission.setLastUpdatedOn(new Date());

				id = departmentPermissionRepository.save(departmentPermission).getId();
				System.out.println("in if....." + id);

			} else {
				DepartmentPermission departmentPermission1 = new DepartmentPermission();

				List<String> vendorInds = departmentPermissionMapper.getVendor();
				// if (null!=vendorInds && !vendorInds.isEmpty()) {
				if (vendorInds.contains("Access")) {
					departmentPermission1.setVendorAccessInd(true);
				} else {
					departmentPermission1.setVendorAccessInd(false);
				}
				if (vendorInds.contains("Create")) {
					departmentPermission1.setVendorCreateInd(true);
				} else {
					departmentPermission1.setVendorCreateInd(false);
				}
				if (vendorInds.contains("Update")) {
					departmentPermission1.setVendorUpdateInd(true);
				} else {
					departmentPermission1.setVendorUpdateInd(false);
				}
				if (vendorInds.contains("Delete")) {
					departmentPermission1.setVendorDeleteInd(true);
				} else {
					departmentPermission1.setVendorDeleteInd(false);
				}
				if (vendorInds.contains("Full List")) {
					departmentPermission1.setVendorFullListInd(true);
				} else {
					departmentPermission1.setVendorFullListInd(false);
				}

				List<String> customerInds = departmentPermissionMapper.getCustomer();
				// if (null!=customerInds && !customerInds.isEmpty()) {
				if (customerInds.contains("Access")) {
					departmentPermission1.setCustomerAccessInd(true);
				} else {
					departmentPermission1.setCustomerAccessInd(false);
				}
				if (customerInds.contains("Create")) {
					departmentPermission1.setCustomerCreateInd(true);
				} else {
					departmentPermission1.setCustomerCreateInd(false);
				}
				if (customerInds.contains("Update")) {
					departmentPermission1.setCustomerUpdateInd(true);
				} else {
					departmentPermission1.setCustomerUpdateInd(false);
				}
				if (customerInds.contains("Delete")) {
					departmentPermission1.setCustomerDeleteInd(true);
				} else {
					departmentPermission1.setCustomerDeleteInd(false);
				}
				if (customerInds.contains("Full List")) {
					departmentPermission1.setCustomerFullListInd(true);
				} else {
					departmentPermission1.setCustomerFullListInd(false);
				}

				List<String> opportunityInds = departmentPermissionMapper.getOpportunity();
				// if (null!=opportunityInds && !opportunityInds.isEmpty()) {
				if (opportunityInds.contains("Access")) {
					departmentPermission1.setOpportunityAccessInd(true);
				} else {
					departmentPermission1.setOpportunityAccessInd(false);
				}
				if (opportunityInds.contains("Create")) {
					departmentPermission1.setOpportunityCreateInd(true);
				} else {
					departmentPermission1.setOpportunityCreateInd(false);
				}
				if (opportunityInds.contains("Update")) {
					departmentPermission1.setOpportunityUpdateInd(true);
				} else {
					departmentPermission1.setOpportunityUpdateInd(false);
				}
				if (opportunityInds.contains("Delete")) {
					departmentPermission1.setOpportunityDeleteInd(true);
				} else {
					departmentPermission1.setOpportunityDeleteInd(false);
				}
				if (opportunityInds.contains("Full List")) {
					departmentPermission1.setOpportunityFullListInd(true);
				} else {
					departmentPermission1.setOpportunityFullListInd(false);
				}

				List<String> talentInds = departmentPermissionMapper.getTalent();
				// if (null!=talentInds && !talentInds.isEmpty()) {
				if (talentInds.contains("Access")) {
					departmentPermission1.setTalentAccessInd(true);
				} else {
					departmentPermission1.setTalentAccessInd(false);
				}
				if (talentInds.contains("Create")) {
					departmentPermission1.setTalentCreateInd(true);
				} else {
					departmentPermission1.setTalentCreateInd(false);
				}
				if (talentInds.contains("Update")) {
					departmentPermission1.setTalentUpdateInd(true);
				} else {
					departmentPermission1.setTalentUpdateInd(false);
				}
				if (talentInds.contains("Delete")) {
					departmentPermission1.setTalentDeleteInd(true);
				} else {
					departmentPermission1.setTalentDeleteInd(false);
				}
				if (talentInds.contains("Full List")) {
					departmentPermission1.setTalentFullListInd(true);
				} else {
					departmentPermission1.setTalentFullListInd(false);
				}

				List<String> requirementInds = departmentPermissionMapper.getRequirement();
				// if (null!=talentInds && !talentInds.isEmpty()) {
				if (requirementInds.contains("Access")) {
					departmentPermission1.setRequirementAccessInd(true);
				} else {
					departmentPermission1.setRequirementAccessInd(false);
				}
				if (requirementInds.contains("Create")) {
					departmentPermission1.setRequirementCreateInd(true);
				} else {
					departmentPermission1.setRequirementCreateInd(false);
				}
				if (requirementInds.contains("Update")) {
					departmentPermission1.setRequirementUpdateInd(true);
				} else {
					departmentPermission1.setRequirementUpdateInd(false);
				}
				if (requirementInds.contains("Delete")) {
					departmentPermission1.setRequirementDeleteInd(true);
				} else {
					departmentPermission1.setRequirementDeleteInd(false);
				}
				if (requirementInds.contains("Full List")) {
					departmentPermission1.setRequirementFullListInd(true);
				} else {
					departmentPermission1.setRequirementFullListInd(false);
				}

				List<String> publishInds = departmentPermissionMapper.getPublish();
				// if (null!=talentInds && !talentInds.isEmpty()) {
				if (publishInds.contains("Access")) {
					departmentPermission1.setPublishAccessInd(true);
				} else {
					departmentPermission1.setPublishAccessInd(false);
				}
				if (publishInds.contains("Create")) {
					departmentPermission1.setPublishCreateInd(true);
				} else {
					departmentPermission1.setPublishCreateInd(false);
				}
				if (publishInds.contains("Update")) {
					departmentPermission1.setPublishUpdateInd(true);
				} else {
					departmentPermission1.setPublishUpdateInd(false);
				}
				if (publishInds.contains("Delete")) {
					departmentPermission1.setPublishDeleteInd(true);
				} else {
					departmentPermission1.setPublishDeleteInd(false);
				}
				if (publishInds.contains("Full List")) {
					departmentPermission1.setPublishFullListInd(true);
				} else {
					departmentPermission1.setPublishFullListInd(false);
				}

				List<String> pulseInds = departmentPermissionMapper.getPulse();
				// if (null!=talentInds && !talentInds.isEmpty()) {
				if (pulseInds.contains("Access")) {
					departmentPermission1.setPulseAccessInd(true);
				} else {
					departmentPermission1.setPulseAccessInd(false);
				}
				if (pulseInds.contains("Create")) {
					departmentPermission1.setPulseCreateInd(true);
				} else {
					departmentPermission1.setPulseCreateInd(false);
				}
				if (pulseInds.contains("Update")) {
					departmentPermission1.setPulseUpdateInd(true);
				} else {
					departmentPermission1.setPulseUpdateInd(false);
				}
				if (pulseInds.contains("Delete")) {
					departmentPermission1.setPulseDeleteInd(true);
				} else {
					departmentPermission1.setPulseDeleteInd(false);
				}
				if (pulseInds.contains("Full List")) {
					departmentPermission1.setPulseFullListInd(true);
				} else {
					departmentPermission1.setPulseFullListInd(false);
				}

				List<String> contactInds = departmentPermissionMapper.getContact();
				// if (null!=customerInds && !customerInds.isEmpty()) {
				if (contactInds.contains("Access")) {
					departmentPermission1.setContactAccessInd(true);
				} else {
					departmentPermission1.setContactAccessInd(false);
				}
				if (contactInds.contains("Create")) {
					departmentPermission1.setContactCreateInd(true);
				} else {
					departmentPermission1.setContactCreateInd(false);
				}
				if (contactInds.contains("Update")) {
					departmentPermission1.setContactUpdateInd(true);
				} else {
					departmentPermission1.setContactUpdateInd(false);
				}
				if (contactInds.contains("Delete")) {
					departmentPermission1.setContactDeleteInd(true);
				} else {
					departmentPermission1.setContactDeleteInd(false);
				}
				if (pulseInds.contains("Full List")) {
					departmentPermission1.setContactFullListInd(true);
				} else {
					departmentPermission1.setContactFullListInd(false);
				}

				List<String> assessmentInds = departmentPermissionMapper.getAssessment();
				// if (null!=customerInds && !customerInds.isEmpty()) {
				if (assessmentInds.contains("Access")) {
					departmentPermission1.setAssessmentAccessInd(true);
				} else {
					departmentPermission1.setAssessmentAccessInd(false);
				}
				if (assessmentInds.contains("Create")) {
					departmentPermission1.setAssessmentCreateInd(true);
				} else {
					departmentPermission1.setAssessmentCreateInd(false);
				}
				if (assessmentInds.contains("Update")) {
					departmentPermission1.setAssessmentUpdateInd(true);
				} else {
					departmentPermission1.setAssessmentUpdateInd(false);
				}
				if (assessmentInds.contains("Delete")) {
					departmentPermission1.setAssessmentDeleteInd(true);
				} else {
					departmentPermission1.setAssessmentDeleteInd(false);
				}
				if (assessmentInds.contains("Full List")) {
					departmentPermission1.setAssessmentFullListInd(true);
				} else {
					departmentPermission1.setAssessmentFullListInd(false);
				}

				List<String> leadsInds = departmentPermissionMapper.getLeads();
				// if (null!=customerInds && !customerInds.isEmpty()) {
				if (leadsInds.contains("Access")) {
					departmentPermission1.setLeadsAccessInd(true);
				} else {
					departmentPermission1.setLeadsAccessInd(false);
				}
				if (leadsInds.contains("Create")) {
					departmentPermission1.setLeadsCreateInd(true);
				} else {
					departmentPermission1.setLeadsCreateInd(false);
				}
				if (leadsInds.contains("Update")) {
					departmentPermission1.setLeadsUpdateInd(true);
				} else {
					departmentPermission1.setLeadsUpdateInd(false);
				}
				if (leadsInds.contains("Delete")) {
					departmentPermission1.setLeadsDeleteInd(true);
				} else {
					departmentPermission1.setLeadsDeleteInd(false);
				}
				if (leadsInds.contains("Full List")) {
					departmentPermission1.setLeadsFullListInd(true);
				} else {
					departmentPermission1.setLeadsFullListInd(false);
				}

				List<String> testInds = departmentPermissionMapper.getTest();
				// if (null!=customerInds && !customerInds.isEmpty()) {
				if (testInds.contains("Access")) {
					departmentPermission1.setTestAccessInd(true);
				} else {
					departmentPermission1.setTestAccessInd(false);
				}
				if (testInds.contains("Create")) {
					departmentPermission1.setTestCreateInd(true);
				} else {
					departmentPermission1.setTestCreateInd(false);
				}
				if (testInds.contains("Update")) {
					departmentPermission1.setTestUpdateInd(true);
				} else {
					departmentPermission1.setTestUpdateInd(false);
				}
				if (testInds.contains("Delete")) {
					departmentPermission1.setTestDeleteInd(true);
				} else {
					departmentPermission1.setTestDeleteInd(false);
				}
				if (testInds.contains("Full List")) {
					departmentPermission1.setTestFullListInd(true);
				} else {
					departmentPermission1.setTestFullListInd(false);
				}
				
				
				List<String> programInds = departmentPermissionMapper.getProgram();
				// if (null!=customerInds && !customerInds.isEmpty()) {
				if (programInds.contains("Access")) {
					departmentPermission1.setProgramAccessInd(true);
				} else {
					departmentPermission1.setProgramAccessInd(false);
				}
				if (programInds.contains("Create")) {
					departmentPermission1.setProgramCreateInd(true);
				} else {
					departmentPermission1.setProgramCreateInd(false);
				}
				if (programInds.contains("Update")) {
					departmentPermission1.setProgramUpdateInd(true);
				} else {
					departmentPermission1.setProgramUpdateInd(false);
				}
				if (programInds.contains("Delete")) {
					departmentPermission1.setProgramDeleteInd(true);
				} else {
					departmentPermission1.setProgramDeleteInd(false);
				}
				if (programInds.contains("Full List")) {
					departmentPermission1.setProgramFullListInd(true);
				} else {
					departmentPermission1.setProgramFullListInd(false);
				}
				
				
				
				List<String> courseInds = departmentPermissionMapper.getCourse();
				// if (null!=customerInds && !customerInds.isEmpty()) {
				if (courseInds.contains("Access")) {
					departmentPermission1.setCourseAccessInd(true);
				} else {
					departmentPermission1.setCourseAccessInd(false);
				}
				if (courseInds.contains("Create")) {
					departmentPermission1.setCourseCreateInd(true);
				} else {
					departmentPermission1.setCourseCreateInd(false);
				}
				if (courseInds.contains("Update")) {
					departmentPermission1.setCourseUpdateInd(true);
				} else {
					departmentPermission1.setCourseUpdateInd(false);
				}
				if (courseInds.contains("Delete")) {
					departmentPermission1.setCourseDeleteInd(true);
				} else {
					departmentPermission1.setCourseDeleteInd(false);
				}
				if (courseInds.contains("Full List")) {
					departmentPermission1.setCourseFullListInd(true);
				} else {
					departmentPermission1.setCourseFullListInd(false);
				}
				
				
				
				List<String> hoursInds = departmentPermissionMapper.getHours();
				// if (null!=customerInds && !customerInds.isEmpty()) {
				if (hoursInds.contains("Access")) {
					departmentPermission1.setHoursAccessInd(true);
				} else {
					departmentPermission1.setHoursAccessInd(false);
				}
				if (hoursInds.contains("Create")) {
					departmentPermission1.setHoursCreateInd(true);
				} else {
					departmentPermission1.setHoursCreateInd(false);
				}
				if (hoursInds.contains("Update")) {
					departmentPermission1.setHoursUpdateInd(true);
				} else {
					departmentPermission1.setHoursUpdateInd(false);
				}
				if (hoursInds.contains("Delete")) {
					departmentPermission1.setHoursDeleteInd(true);
				} else {
					departmentPermission1.setHoursDeleteInd(false);
				}
				if (hoursInds.contains("Full List")) {
					departmentPermission1.setHoursFullListInd(true);
				} else {
					departmentPermission1.setHoursFullListInd(false);
				}
				
				List<String> taskInds = departmentPermissionMapper.getTask();
				// if (null!=customerInds && !customerInds.isEmpty()) {
				/*if (taskInds.contains("Access")) {
					departmentPermission1.setTaskAccessInd(true);
				} else {
					departmentPermission1.setTaskAccessInd(false);
				}
				if (taskInds.contains("Create")) {
					departmentPermission1.setTaskCreateInd(true);
				} else {
					departmentPermission1.setTaskCreateInd(false);
				}
				if (taskInds.contains("Update")) {
					departmentPermission1.setTaskUpdateInd(true);
				} else {
					departmentPermission1.setTaskUpdateInd(false);
				}
				if (taskInds.contains("Delete")) {
					departmentPermission1.setTaskDeleteInd(true);
				} else {
					departmentPermission1.setTaskDeleteInd(false);
				}*/
				if (taskInds.contains("Full List")) {
					departmentPermission1.setTaskFullListInd(true);
				} else {
					departmentPermission1.setTaskFullListInd(false);
				}
				
				
				List<String> comercialInds = departmentPermissionMapper.getComercial();
				// if (null!=customerInds && !customerInds.isEmpty()) {
				if (comercialInds.contains("Access")) {
					departmentPermission1.setComercialAccessInd(true);
				} else {
					departmentPermission1.setComercialAccessInd(false);
				}
				if (comercialInds.contains("Create")) {
					departmentPermission1.setComercialCreateInd(true);
				} else {
					departmentPermission1.setComercialCreateInd(false);
				}
				if (comercialInds.contains("Update")) {
					departmentPermission1.setComercialUpdateInd(true);
				} else {
					departmentPermission1.setComercialUpdateInd(false);
				}
				if (comercialInds.contains("Delete")) {
					departmentPermission1.setComercialDeleteInd(true);
				} else {
					departmentPermission1.setComercialDeleteInd(false);
				}
				if (comercialInds.contains("Full List")) {
					departmentPermission1.setComercialFullListInd(true);
				} else {
					departmentPermission1.setComercialFullListInd(false);
				}
				
				
				List<String> locationInds = departmentPermissionMapper.getLocation();
				// if (null!=customerInds && !customerInds.isEmpty()) {
				if (locationInds.contains("Access")) {
					departmentPermission1.setLocationAccessInd(true);
				} else {
					departmentPermission1.setLocationAccessInd(false);
				}
				if (locationInds.contains("Create")) {
					departmentPermission1.setLocationCreateInd(true);
				} else {
					departmentPermission1.setLocationCreateInd(false);
				}
				if (locationInds.contains("Update")) {
					departmentPermission1.setLocationUpdateInd(true);
				} else {
					departmentPermission1.setLocationUpdateInd(false);
				}
				if (locationInds.contains("Delete")) {
					departmentPermission1.setLocationDeleteInd(true);
				} else {
					departmentPermission1.setLocationDeleteInd(false);
				}
				if (locationInds.contains("Full List")) {
					departmentPermission1.setLocationFullListInd(true);
				} else {
					departmentPermission1.setLocationFullListInd(false);
				}
				
				
				List<String> leaveInds = departmentPermissionMapper.getLeave();
				// if (null!=customerInds && !customerInds.isEmpty()) {
				if (leaveInds.contains("Access")) {
					departmentPermission1.setLeaveAccessInd(true);
				} else {
					departmentPermission1.setLeaveAccessInd(false);
				}
				if (leaveInds.contains("Full List")) {
					departmentPermission1.setLeaveFullListInd(true);
				} else {
					departmentPermission1.setLeaveFullListInd(false);
				}
				
				List<String> expenseInds = departmentPermissionMapper.getExpense();
				// if (null!=customerInds && !customerInds.isEmpty()) {
				if (expenseInds.contains("Access")) {
					departmentPermission1.setExpenseAccessInd(true);
				} else {
					departmentPermission1.setExpenseAccessInd(false);
				}if (expenseInds.contains("Access")) {
					departmentPermission1.setExpenseFullListInd(true);
				} else {
					departmentPermission1.setExpenseFullListInd(false);
				}
				
				List<String> mileageInds = departmentPermissionMapper.getMileage();
				// if (null!=customerInds && !customerInds.isEmpty()) {
				if (mileageInds.contains("Access")) {
					departmentPermission1.setMileageAccessInd(true);
				} else {
					departmentPermission1.setMileageAccessInd(false);
				}
				if (mileageInds.contains("Full List")) {
					departmentPermission1.setMileageFullListInd(true);
				} else {
					departmentPermission1.setMileageFullListInd(false);
				}
				
				List<String> userInds = departmentPermissionMapper.getUser();
				// if (null!=customerInds && !customerInds.isEmpty()) {
				if (userInds.contains("Access")) {
					departmentPermission1.setUserAccessInd(true);
				} else {
					departmentPermission1.setUserAccessInd(false);
				}
				if (userInds.contains("Create")) {
					departmentPermission1.setUserCreateInd(true);
				} else {
					departmentPermission1.setUserCreateInd(false);
				}
				if (userInds.contains("Update")) {
					departmentPermission1.setUserUpdateInd(true);
				} else {
					departmentPermission1.setUserUpdateInd(false);
				}
				if (userInds.contains("Delete")) {
					departmentPermission1.setUserDeleteInd(true);
				} else {
					departmentPermission1.setUserDeleteInd(false);
				}
				if (userInds.contains("Access Plus")) {
					departmentPermission1.setUserAccessPlusInd(true);
				} else {
					departmentPermission1.setUserAccessPlusInd(false);
				}
				
				List<String> accountInds = departmentPermissionMapper.getAccount();
				// if (null!=customerInds && !customerInds.isEmpty()) {
				if (accountInds.contains("Access")) {
					departmentPermission1.setAccountAccessInd(true);
				} else {
					departmentPermission1.setAccountAccessInd(false);
				}
				if (accountInds.contains("Create")) {
					departmentPermission1.setAccountCreateInd(true);
				} else {
					departmentPermission1.setAccountCreateInd(false);
				}
				if (accountInds.contains("Update")) {
					departmentPermission1.setAccountUpdateInd(true);
				} else {
					departmentPermission1.setAccountUpdateInd(false);
				}
				if (accountInds.contains("Delete")) {
					departmentPermission1.setAccountDeleteInd(true);
				} else {
					departmentPermission1.setAccountDeleteInd(false);
				}
				if (accountInds.contains("Full List")) {
					departmentPermission1.setAccountFullListInd(true);
				} else {
					departmentPermission1.setAccountFullListInd(false);
				}
				if (accountInds.contains("Info")) {
					departmentPermission1.setAccountInfoInd(true);
				} else {
					departmentPermission1.setAccountInfoInd(false);
				}
				
				List<String> orderInds = departmentPermissionMapper.getOrder();
				// if (null!=customerInds && !customerInds.isEmpty()) {
				if (orderInds.contains("Access")) {
					departmentPermission1.setOrderAccessInd(true);
				} else {
					departmentPermission1.setOrderAccessInd(false);
				}
				if (orderInds.contains("Create")) {
					departmentPermission1.setOrderCreateInd(true);
				} else {
					departmentPermission1.setOrderCreateInd(false);
				}
				if (orderInds.contains("Update")) {
					departmentPermission1.setOrderUpdateInd(true);
				} else {
					departmentPermission1.setOrderUpdateInd(false);
				}
				if (orderInds.contains("Delete")) {
					departmentPermission1.setOrderDeleteInd(true);
				} else {
					departmentPermission1.setOrderDeleteInd(false);
				}
				if (orderInds.contains("Full List")) {
					departmentPermission1.setOrderFullListInd(true);
				} else {
					departmentPermission1.setOrderFullListInd(false);
				}
				
				
				List<String> materialInd = departmentPermissionMapper.getMaterial();
				// if (null!=customerInds && !customerInds.isEmpty()) {
				if (materialInd.contains("Access")) {
					departmentPermission1.setMaterialAccessInd(true);;
				} else {
					departmentPermission1.setMaterialAccessInd(false);
				}
				if (materialInd.contains("Create")) {
					departmentPermission1.setMaterialCreateInd(true);
				} else {
					departmentPermission1.setMaterialCreateInd(false);
				}
				if (materialInd.contains("Update")) {
					departmentPermission1.setMaterialUpdateInd(true);
				} else {
					departmentPermission1.setMaterialUpdateInd(false);
				}
				if (materialInd.contains("Delete")) {
					departmentPermission1.setMaterialDeleteInd(true);
				} else {
					departmentPermission1.setMaterialDeleteInd(false);
				}
				
				List<String> supplierInds = departmentPermissionMapper.getSupplier();
				// if (null!=customerInds && !customerInds.isEmpty()) {
				if (supplierInds.contains("Access")) {
					departmentPermission1.setSupplierAccessInd(true);
				} else {
					departmentPermission1.setSupplierAccessInd(false);
				}
				if (supplierInds.contains("Create")) {
					departmentPermission1.setSupplierCreateInd(true);
				} else {
					departmentPermission1.setSupplierCreateInd(false);
				}
				if (supplierInds.contains("Update")) {
					departmentPermission1.setSupplierUpdateInd(true);
				} else {
					departmentPermission1.setSupplierUpdateInd(false);
				}
				if (supplierInds.contains("Delete")) {
					departmentPermission1.setSupplierDeleteInd(true);
				} else {
					departmentPermission1.setSupplierDeleteInd(false);
				}
				if (supplierInds.contains("Full List")) {
					departmentPermission1.setSupplierFullListInd(true);
				} else {
					departmentPermission1.setSupplierFullListInd(false);
				}
				if (supplierInds.contains("Block")) {
					departmentPermission1.setSupplierBlockInd(true);
				} else {
					departmentPermission1.setSupplierBlockInd(false);
				}
				if (supplierInds.contains("Inventory")) {
					departmentPermission1.setSupplierInventoryInd(true);
				} else {
					departmentPermission1.setSupplierInventoryInd(false);
				}
				
				List<String> inventoryInds = departmentPermissionMapper.getInventory();
				// if (null!=customerInds && !customerInds.isEmpty()) {
				if (inventoryInds.contains("Access")) {
					departmentPermission1.setInventoryAccessInd(true);
				} else {
					departmentPermission1.setInventoryAccessInd(false);
				}
				if (inventoryInds.contains("Create")) {
					departmentPermission1.setInventoryCreateInd(true);
				} else {
					departmentPermission1.setInventoryCreateInd(false);
				}
				if (inventoryInds.contains("Update")) {
					departmentPermission1.setInventoryUpdateInd(true);
				} else {
					departmentPermission1.setInventoryUpdateInd(false);
				}
				if (inventoryInds.contains("Delete")) {
					departmentPermission1.setInventoryDeleteInd(true);
				} else {
					departmentPermission1.setInventoryDeleteInd(false);
				}
				if (inventoryInds.contains("Full List")) {
					departmentPermission1.setInventoryFullListInd(true);
				} else {
					departmentPermission1.setInventoryFullListInd(false);
				}
				
				List<String> refurbishInds = departmentPermissionMapper.getRefurbish();
				// if (null!=customerInds && !customerInds.isEmpty()) {
				if (refurbishInds.contains("Workshop")) {
					departmentPermission1.setRefurbishWorkshopInd(true);
				} else {
					departmentPermission1.setRefurbishWorkshopInd(false);
				}
				if (refurbishInds.contains("Adminview")) {
					departmentPermission1.setRefurbishAdminviewInd(true);
				} else {
					departmentPermission1.setRefurbishAdminviewInd(false);
				}
				if (refurbishInds.contains("Adminassign")) {
					departmentPermission1.setRefurbishAdminAssignInd(true);
				} else {
					departmentPermission1.setRefurbishAdminAssignInd(false);
				}
				
				// if (null!=customerInds && !customerInds.isEmpty()) {
				List<String> dashboardInds = departmentPermissionMapper.getDashboard();
				if (dashboardInds.contains("Access")) {
					departmentPermission1.setDashboardAccessInd(true);
				} else {
					departmentPermission1.setDashboardAccessInd(false);
				}if (dashboardInds.contains("Full List")) {
					departmentPermission1.setDashboardFullListInd(true);
				} else {
					departmentPermission1.setDashboardFullListInd(false);
				}if (dashboardInds.contains("Regional")) {
					departmentPermission1.setDashboardRegionalInd(true);
				} else {
					departmentPermission1.setDashboardRegionalInd(false);
				}
				
				List<String> settingInds = departmentPermissionMapper.getSettings();
				// if (null!=customerInds && !customerInds.isEmpty()) {
				if (settingInds.contains("Access")) {
					departmentPermission1.setSettingsAccessInd(true);
				} else {
					departmentPermission1.setSettingsAccessInd(false);
				}
				
				List<String> junkInds = departmentPermissionMapper.getJunk();
				// if (null!=customerInds && !customerInds.isEmpty()) {
				if (junkInds.contains("Access")) {
					departmentPermission1.setJunkAccessInd(true);
				} else {
					departmentPermission1.setJunkAccessInd(false);
				}
				if (junkInds.contains("Transfer")) {
					departmentPermission1.setJunkTransferInd(true);
				} else {
					departmentPermission1.setJunkAccessInd(false);
				}
				
				List<String> investorInds = departmentPermissionMapper.getInvestor();
				// if (null!=customerInds && !customerInds.isEmpty()) {
				if (investorInds.contains("Access")) {
					departmentPermission1.setInvestorAccessInd(true);
				} else {
					departmentPermission1.setInvestorAccessInd(false);
				}
				if (investorInds.contains("Create")) {
					departmentPermission1.setInvestorCreateInd(true);
				} else {
					departmentPermission1.setInvestorCreateInd(false);
				}
				if (investorInds.contains("Update")) {
					departmentPermission1.setInvestorUpdateInd(true);
				} else {
					departmentPermission1.setInvestorUpdateInd(false);
				}
				if (investorInds.contains("Delete")) {
					departmentPermission1.setInvestorDeleteInd(true);
				} else {
					departmentPermission1.setInvestorDeleteInd(false);
				}
				if (investorInds.contains("Full List")) {
					departmentPermission1.setInvestorFullListInd(true);
				} else {
					departmentPermission1.setInvestorFullListInd(false);
				}
				
				List<String> investorCustomerInds = departmentPermissionMapper.getPitch();
				// if (null!=customerInds && !customerInds.isEmpty()) {
				if (investorCustomerInds.contains("Access")) {
					departmentPermission1.setPitchAccessInd(true);
				} else {
					departmentPermission1.setPitchAccessInd(false);
				}
				if (investorCustomerInds.contains("Create")) {
					departmentPermission1.setPitchCreateInd(true);
				} else {
					departmentPermission1.setPitchCreateInd(false);
				}
				if (investorCustomerInds.contains("Update")) {
					departmentPermission1.setPitchUpdateInd(true);
				} else {
					departmentPermission1.setPitchUpdateInd(false);
				}
				if (investorCustomerInds.contains("Delete")) {
					departmentPermission1.setPitchDeleteInd(true);
				} else {
					departmentPermission1.setPitchDeleteInd(false);
				}
				if (investorCustomerInds.contains("Full List")) {
					departmentPermission1.setPitchFullListInd(true);
				} else {
					departmentPermission1.setPitchFullListInd(false);
				}
				
				List<String> investorContactInds = departmentPermissionMapper.getInvestorContact();
				// if (null!=customerInds && !customerInds.isEmpty()) {
				if (investorContactInds.contains("Access")) {
					departmentPermission1.setInvestorContactAccessInd(true);
				} else {
					departmentPermission1.setInvestorContactAccessInd(false);
				}
				if (investorContactInds.contains("Create")) {
					departmentPermission1.setInvestorContactCreateInd(true);
				} else {
					departmentPermission1.setInvestorContactCreateInd(false);
				}
				if (investorContactInds.contains("Update")) {
					departmentPermission1.setInvestorContactUpdateInd(true);
				} else {
					departmentPermission1.setInvestorContactUpdateInd(false);
				}
				if (investorContactInds.contains("Delete")) {
					departmentPermission1.setInvestorContactDeleteInd(true);
				} else {
					departmentPermission1.setInvestorContactDeleteInd(false);
				}
				if (investorContactInds.contains("Full List")) {
					departmentPermission1.setInvestorContactFullListInd(true);
				} else {
					departmentPermission1.setInvestorContactFullListInd(false);
				}
				
				List<String> investorOppertunityInds = departmentPermissionMapper.getDeal();
				// if (null!=customerInds && !customerInds.isEmpty()) {
				if (investorOppertunityInds.contains("Access")) {
					departmentPermission1.setDealAccessInd(true);
				} else {
					departmentPermission1.setDealAccessInd(false);
				}
				if (investorOppertunityInds.contains("Create")) {
					departmentPermission1.setDealCreateInd(true);
				} else {
					departmentPermission1.setDealCreateInd(false);
				}
				if (investorOppertunityInds.contains("Update")) {
					departmentPermission1.setDealUpdateInd(true);
				} else {
					departmentPermission1.setDealUpdateInd(false);
				}
				if (investorOppertunityInds.contains("Delete")) {
					departmentPermission1.setDealDeleteInd(true);
				} else {
					departmentPermission1.setDealDeleteInd(false);
				}
				if (investorOppertunityInds.contains("Full List")) {
					departmentPermission1.setDealFullListInd(true);
				} else {
					departmentPermission1.setDealFullListInd(false);
				}
				
				List<String> repositoryInds = departmentPermissionMapper.getRepository();
				// if (null!=customerInds && !customerInds.isEmpty()) {
				if (repositoryInds.contains("Create")) {
					departmentPermission1.setRepositoryCreateInd(true);
				} else {
					departmentPermission1.setRepositoryCreateInd(false);
				}
				
				List<String> shipperInd = departmentPermissionMapper.getShipper();
				// if (null!=customerInds && !customerInds.isEmpty()) {
				if (shipperInd.contains("Access")) {
					departmentPermission1.setShipperAccessInd(true);
				} else {
					departmentPermission1.setShipperAccessInd(false);
				}
				if (shipperInd.contains("Create")) {
					departmentPermission1.setShipperCreateInd(true);
				} else {
					departmentPermission1.setShipperCreateInd(false);
				}
				if (shipperInd.contains("Update")) {
					departmentPermission1.setShipperUpdateInd(true);
				} else {
					departmentPermission1.setShipperUpdateInd(false);
				}
				if (shipperInd.contains("Delete")) {
					departmentPermission1.setShipperDeleteInd(true);
				} else {
					departmentPermission1.setShipperDeleteInd(false);
				}
				if (shipperInd.contains("Full List")) {
					departmentPermission1.setShipperFullListInd(true);
				} else {
					departmentPermission1.setShipperFullListInd(false);
				}
				
				List<String> shipperInds = departmentPermissionMapper.getShipper();
				// if (null!=customerInds && !customerInds.isEmpty()) {
				if (shipperInds.contains("Access")) {
					departmentPermission1.setShipperAccessInd(true);
				} else {
					departmentPermission1.setShipperAccessInd(false);
				}
				if (shipperInds.contains("Create")) {
					departmentPermission1.setShipperCreateInd(true);
				} else {
					departmentPermission1.setShipperCreateInd(false);
				}
				if (shipperInds.contains("Update")) {
					departmentPermission1.setShipperUpdateInd(true);
				} else {
					departmentPermission1.setShipperUpdateInd(false);
				}
				if (shipperInds.contains("Delete")) {
					departmentPermission1.setShipperDeleteInd(true);
				} else {
					departmentPermission1.setShipperDeleteInd(false);
				}
				if (shipperInds.contains("Full List")) {
					departmentPermission1.setShipperFullListInd(true);
				} else {
					departmentPermission1.setShipperFullListInd(false);
				}
				
				List<String> plantInd = departmentPermissionMapper.getPlant();
				// if (null!=customerInds && !customerInds.isEmpty()) {
				if (plantInd.contains("Access")) {
					departmentPermission1.setPlantAccessInd(true);
				} else {
					departmentPermission1.setPlantAccessInd(false);
				}
				if (plantInd.contains("Create")) {
					departmentPermission1.setPlantCreateInd(true);
				} else {
					departmentPermission1.setPlantCreateInd(false);
				}
				if (plantInd.contains("Update")) {
					departmentPermission1.setPlantUpdateInd(true);
				} else {
					departmentPermission1.setPlantUpdateInd(false);
				}
				if (plantInd.contains("Delete")) {
					departmentPermission1.setPlantDeleteInd(true);
				} else {
					departmentPermission1.setPlantDeleteInd(false);
				}
				if (plantInd.contains("Full List")) {
					departmentPermission1.setPlantFullListInd(true);
				} else {
					departmentPermission1.setPlantFullListInd(false);
				}
				
				List<String> teamInd = departmentPermissionMapper.getTeams();
				// if (null!=customerInds && !customerInds.isEmpty()) {
				if (teamInd.contains("Access")) {
					departmentPermission1.setTeamsAccessInd(true);
				} else {
					departmentPermission1.setTeamsAccessInd(false);
				}
				if (teamInd.contains("Create")) {
					departmentPermission1.setTeamsCreateInd(true);
				} else {
					departmentPermission1.setTeamsCreateInd(false);
				}
				if (teamInd.contains("Update")) {
					departmentPermission1.setTeamsUpdateInd(true);
				} else {
					departmentPermission1.setTeamsUpdateInd(false);
				}
				if (teamInd.contains("Delete")) {
					departmentPermission1.setTeamsDeleteInd(true);
				} else {
					departmentPermission1.setTeamsDeleteInd(false);
				}
				if (teamInd.contains("Full List")) {
					departmentPermission1.setTeamsFullListInd(true);
				} else {
					departmentPermission1.setTeamsFullListInd(false);
				}
				
				List<String> basicInds = departmentPermissionMapper.getBasic();
				// if (null!=customerInds && !customerInds.isEmpty()) {
				if (basicInds.contains("Access")) {
					departmentPermission1.setBasicAccessInd(true);
				} else {
					departmentPermission1.setBasicAccessInd(false);
				}
				
				List<String> catalogInd = departmentPermissionMapper.getCatalog();
				// if (null!=customerInds && !customerInds.isEmpty()) {
				if (catalogInd.contains("Access")) {
					departmentPermission1.setCatalogAccessInd(true);
				} else {
					departmentPermission1.setCatalogAccessInd(false);
				}
				if (catalogInd.contains("Create")) {
					departmentPermission1.setCatalogCreateInd(true);
				} else {
					departmentPermission1.setCatalogCreateInd(false);
				}
				if (catalogInd.contains("Update")) {
					departmentPermission1.setCatalogUpdateInd(true);
				} else {
					departmentPermission1.setCatalogUpdateInd(false);
				}
				if (catalogInd.contains("Delete")) {
					departmentPermission1.setCatalogDeleteInd(true);
				} else {
					departmentPermission1.setCatalogDeleteInd(false);
				}
				
				
				List<String> paymentInd = departmentPermissionMapper.getPayment();
				// if (null!=customerInds && !customerInds.isEmpty()) {
				if (paymentInd.contains("Access")) {
					departmentPermission1.setPaymentAccessInd(true);
				} else {
					departmentPermission1.setPaymentAccessInd(false);
				}
				
				List<String>collectionInd = departmentPermissionMapper.getCollection();
				// if (null!=customerInds && !customerInds.isEmpty()) {
				if (collectionInd.contains("Access")) {
					departmentPermission1.setCollectionAccessInd(true);
				} else {
					departmentPermission1.setCollectionAccessInd(false);
				}
				if (collectionInd.contains("Approve")) {
					departmentPermission1.setCollectionApproveInd(true);
				} else {
					departmentPermission1.setCollectionApproveInd(false);
				}
				
				List<String> holidayInds = departmentPermissionMapper.getHoliday();
				// if (null!=customerInds && !customerInds.isEmpty()) {
				if (holidayInds.contains("Access")) {
					departmentPermission1.setHolidayAccessInd(true);
				} else {
					departmentPermission1.setHolidayAccessInd(false);
				}
				
				List<String> topicInd = departmentPermissionMapper.getTopic();
				if (topicInd.contains("Access")) {
					departmentPermission1.setTopicAccessInd(true);
				} else {
					departmentPermission1.setTopicAccessInd(false);
				}
				if (topicInd.contains("Create")) {
					departmentPermission1.setTopicCreateInd(true);
				} else {
					departmentPermission1.setTopicCreateInd(false);
				}
				if (topicInd.contains("Update")) {
					departmentPermission1.setTopicUpdateInd(true);
				} else {
					departmentPermission1.setTopicUpdateInd(false);
				}
				if (topicInd.contains("Delete")) {
					departmentPermission1.setTopicDeleteInd(true);
				} else {
					departmentPermission1.setTopicDeleteInd(false);
				}
				if (topicInd.contains("Full List")) {
					departmentPermission1.setTopicFullListInd(true);
				} else {
					departmentPermission1.setTopicFullListInd(false);
				}
				
				List<String> procurementInd = departmentPermissionMapper.getProcurement();
				if (procurementInd.contains("Access")) {
					departmentPermission1.setProcurementAccessInd(true);
				} else {
					departmentPermission1.setProcurementAccessInd(false);
				}
				if (procurementInd.contains("Create")) {
					departmentPermission1.setProcurementCreateInd(true);
				} else {
					departmentPermission1.setProcurementCreateInd(false);
				}
				if (procurementInd.contains("Update")) {
					departmentPermission1.setProcurementUpdateInd(true);
				} else {
					departmentPermission1.setProcurementUpdateInd(false);
				}
				if (procurementInd.contains("Delete")) {
					departmentPermission1.setProcurementDeleteInd(true);
				} else {
					departmentPermission1.setProcurementDeleteInd(false);
				}
				if (procurementInd.contains("Full List")) {
					departmentPermission1.setProcurementFullListInd(true);
				} else {
					departmentPermission1.setProcurementFullListInd(false);
				}
				
				List<String> subscriptionInd = departmentPermissionMapper.getSubscription();
				if (subscriptionInd.contains("Access")) {
					departmentPermission1.setSubscriptionAccessInd(true);
				} else {
					departmentPermission1.setSubscriptionAccessInd(false);
				}
				if (subscriptionInd.contains("Create")) {
					departmentPermission1.setSubscriptionCreateInd(true);
				} else {
					departmentPermission1.setSubscriptionCreateInd(false);
				}
				if (subscriptionInd.contains("Update")) {
					departmentPermission1.setSubscriptionUpdateInd(true);
				} else {
					departmentPermission1.setSubscriptionUpdateInd(false);
				}
				if (subscriptionInd.contains("Delete")) {
					departmentPermission1.setSubscriptionDeleteInd(true);
				} else {
					departmentPermission1.setSubscriptionDeleteInd(false);
				}
				
				
				List<String> productionInd = departmentPermissionMapper.getProduction();
				if (productionInd.contains("Access")) {
					departmentPermission1.setProductionAccessInd(true);
				} else {
					departmentPermission1.setProductionAccessInd(false);
				}
				if (productionInd.contains("Create")) {
					departmentPermission1.setProductionCreateInd(true);
				} else {
					departmentPermission1.setProductionCreateInd(false);
				}
				if (productionInd.contains("Update")) {
					departmentPermission1.setProductionUpdateInd(true);
				} else {
					departmentPermission1.setProductionUpdateInd(false);
				}
				if (productionInd.contains("Delete")) {
					departmentPermission1.setProductionDeleteInd(true);
				} else {
					departmentPermission1.setProductionDeleteInd(false);
				}
				
				List<String> reportInd = departmentPermissionMapper.getReport();
				if (reportInd.contains("Full List")) {
					departmentPermission1.setReportFullListInd(true);
				} else {
					departmentPermission1.setReportFullListInd(false);
				}
				
				List<String> dataRoomInd = departmentPermissionMapper.getDataRoom();
				if (dataRoomInd.contains("Access")) {
					departmentPermission1.setDataRoomAccessInd(true);
				} else {
					departmentPermission1.setDataRoomAccessInd(false);
				}
				if (dataRoomInd.contains("Create")) {
					departmentPermission1.setDataRoomCreateInd(true);
				} else {
					departmentPermission1.setDataRoomCreateInd(false);
				}
				if (dataRoomInd.contains("Update")) {
					departmentPermission1.setDataRoomUpdateInd(true);
				} else {
					departmentPermission1.setDataRoomUpdateInd(false);
				}
				if (dataRoomInd.contains("Delete")) {
					departmentPermission1.setDataRoomDeleteInd(true);
				} else {
					departmentPermission1.setDataRoomDeleteInd(false);
				}
				if (dataRoomInd.contains("Full List")) {
					departmentPermission1.setDataRoomFullListInd(true);
				} else {
					departmentPermission1.setDataRoomFullListInd(false);
				}
				
				List<String> scannerInd = departmentPermissionMapper.getScanner();
				if (scannerInd.contains("Access")) {
					departmentPermission1.setScannerAccessInd(true);
				} else {
					departmentPermission1.setScannerAccessInd(false);
				}
				
				List<String> qualityInd = departmentPermissionMapper.getQuality();
				if (qualityInd.contains("Access")) {
					departmentPermission1.setQualityAccessInd(true);
				} else {
					departmentPermission1.setQualityAccessInd(false);
				}
				if (qualityInd.contains("Create")) {
					departmentPermission1.setQualityCreateInd(true);
				} else {
					departmentPermission1.setQualityCreateInd(false);
				}
				if (qualityInd.contains("Update")) {
					departmentPermission1.setQualityUpdateInd(true);
				} else {
					departmentPermission1.setQualityUpdateInd(false);
				}
				if (qualityInd.contains("Delete")) {
					departmentPermission1.setQualityDeleteInd(true);
				} else {
					departmentPermission1.setQualityDeleteInd(false);
				}
				if (qualityInd.contains("Full List")) {
					departmentPermission1.setQualityFullListInd(true);
				} else {
					departmentPermission1.setQualityFullListInd(false);
				}
				
				List<String> clubInd = departmentPermissionMapper.getClub();
				if (clubInd.contains("Access")) {
					departmentPermission1.setClubAccessInd(true);
				} else {
					departmentPermission1.setClubAccessInd(false);
				}
				if (clubInd.contains("Create")) {
					departmentPermission1.setClubCreateInd(true);
				} else {
					departmentPermission1.setClubCreateInd(false);
				}
				if (clubInd.contains("Update")) {
					departmentPermission1.setClubUpdateInd(true);
				} else {
					departmentPermission1.setClubUpdateInd(false);
				}
				if (clubInd.contains("Delete")) {
					departmentPermission1.setClubDeleteInd(true);
				} else {
					departmentPermission1.setClubDeleteInd(false);
				}
				if (clubInd.contains("Full List")) {
					departmentPermission1.setClubFullListInd(true);
				} else {
					departmentPermission1.setClubFullListInd(false);
				}
				
				List<String> calenderInd = departmentPermissionMapper.getCalender();
				if (calenderInd.contains("Manage")) {
					departmentPermission1.setCalenderManageInd(true);
				} else {
					departmentPermission1.setCalenderManageInd(false);
				}
				if (calenderInd.contains("View")) {
					departmentPermission1.setCalenderViewInd(true);
				} else {
					departmentPermission1.setCalenderViewInd(false);
				}
				
				departmentPermission1.setRoleTypeId(departmentPermissionMapper.getRoleTypeId());
				departmentPermission1.setDepartmentId(departmentPermissionMapper.getDepartmentId());
				departmentPermission1.setUserId(departmentPermissionMapper.getUserId());
				departmentPermission1.setOrgId(departmentPermissionMapper.getOrganizationId());
				departmentPermission1.setLastUpdatedOn(new Date());

				id = departmentPermissionRepository.save(departmentPermission1).getId();
				System.out.println("in if....." + id);
			}

		}

		return id;

	}

	@Override
	public DepartmentPermissionMapper getPermisionByDepartmentId(String roleTypeId, String loggedInOrgId) {

		DepartmentPermissionMapper mapper = new DepartmentPermissionMapper();
		if (roleTypeId != null) {
			DepartmentPermission dep = departmentPermissionRepository.getDepartmentPermission(roleTypeId,
					loggedInOrgId);
			// System.out.println("data....." + dep.toString());
			if (null != dep) {
				List<String> vendorInds = new ArrayList<>();
				List<String> customerInds = new ArrayList<>();
				List<String> opportunityInds = new ArrayList<>();
				List<String> talentInds = new ArrayList<>();
				List<String> requirementInds = new ArrayList<>();
				List<String> publishInds = new ArrayList<>();
				List<String> pulseInds = new ArrayList<>();
				List<String> contactInds = new ArrayList<>();
				List<String> assessmentInds = new ArrayList<>();
				List<String> leadsInds = new ArrayList<>();
				List<String> testInds = new ArrayList<>();
				List<String> programInds = new ArrayList<>();
				List<String> courseInds = new ArrayList<>();
				List<String> hoursInds = new ArrayList<>();
				List<String> taskInds = new ArrayList<>();
				List<String> comercialInds = new ArrayList<>();
				List<String> locationInds = new ArrayList<>();
				List<String> leaveInds = new ArrayList<>();
				List<String> expenseInds = new ArrayList<>();
				List<String> mileageInds = new ArrayList<>();
				List<String> userInds = new ArrayList<>();
				List<String> accountInds = new ArrayList<>();
				List<String> orderInds = new ArrayList<>();
				List<String> materialsInds = new ArrayList<>();
				List<String> inventoryInds = new ArrayList<>();
				List<String> supplierInds = new ArrayList<>();
				List<String> refurbishInds = new ArrayList<>();
				List<String> dashboardInds = new ArrayList<>();
				List<String> settingsInds = new ArrayList<>();
				List<String> junkInds = new ArrayList<>();
				List<String> investorInds = new ArrayList<>();
				List<String> investorContactInds = new ArrayList<>();
				List<String> pitchInds = new ArrayList<>();
				List<String> dealInds = new ArrayList<>();
				List<String> repositoryInds = new ArrayList<>();
				List<String> shipperInds = new ArrayList<>();
				List<String> plantInds = new ArrayList<>();
				List<String> teamInds = new ArrayList<>();
				List<String> basicInds = new ArrayList<>();
				List<String> catalogInds = new ArrayList<>();
				List<String> paymentInds = new ArrayList<>();
				List<String> collectionInds = new ArrayList<>();
				List<String> holidayInds = new ArrayList<>();
				List<String> topicInds = new ArrayList<>();
				List<String> procurementInds = new ArrayList<>();
				List<String> subscriptionInds= new ArrayList<>();
				List<String> productionInds= new ArrayList<>();
				List<String> reportInds= new ArrayList<>();
				List<String> dataRoomInds= new ArrayList<>();
				List<String> scannerInds= new ArrayList<>();
				List<String> qualityInds= new ArrayList<>();
				List<String> clubInds= new ArrayList<>();
				List<String> calenderInds= new ArrayList<>();
				
				// vendor
				if (dep.isVendorAccessInd()) {
					vendorInds.add("Access");
				}
				if (dep.isVendorCreateInd()) {
					vendorInds.add("Create");
				}
				if (dep.isVendorUpdateInd()) {
					vendorInds.add("Update");
				}
				if (dep.isVendorDeleteInd()) {
					vendorInds.add("Delete");
				}
				if (dep.isVendorFullListInd()) {
					vendorInds.add("Full List");
				}
				mapper.setVendor(vendorInds);

				// customer
				if (dep.isCustomerAccessInd()) {
					customerInds.add("Access");
				}
				if (dep.isCustomerCreateInd()) {
					customerInds.add("Create");
				}
				if (dep.isCustomerUpdateInd()) {
					customerInds.add("Update");
				}
				if (dep.isCustomerDeleteInd()) {
					customerInds.add("Delete");
				}
				if (dep.isCustomerFullListInd()) {
					customerInds.add("Full List");
				}
				mapper.setCustomer(customerInds);

				// opportunity
				if (dep.isOpportunityAccessInd()) {
					opportunityInds.add("Access");
				}
				if (dep.isOpportunityCreateInd()) {
					opportunityInds.add("Create");
				}
				if (dep.isOpportunityUpdateInd()) {
					opportunityInds.add("Update");
				}
				if (dep.isOpportunityDeleteInd()) {
					opportunityInds.add("Delete");
				}
				if (dep.isOpportunityFullListInd()) {
					opportunityInds.add("Full List");
				}
				mapper.setOpportunity(opportunityInds);

				// talent
				if (dep.isTalentAccessInd()) {
					talentInds.add("Access");
				}
				if (dep.isTalentCreateInd()) {
					talentInds.add("Create");
				}
				if (dep.isTalentUpdateInd()) {
					talentInds.add("Update");
				}
				if (dep.isTalentDeleteInd()) {
					talentInds.add("Delete");
				}
				if (dep.isTalentFullListInd()) {
					talentInds.add("Full List");
				}
				mapper.setTalent(talentInds);

				// requirement
				if (dep.isRequirementAccessInd()) {
					requirementInds.add("Access");
				}
				if (dep.isRequirementCreateInd()) {
					requirementInds.add("Create");
				}
				if (dep.isRequirementUpdateInd()) {
					requirementInds.add("Update");
				}
				if (dep.isRequirementDeleteInd()) {
					requirementInds.add("Delete");
				}
				if (dep.isRequirementFullListInd()) {
					requirementInds.add("Full List");
				}
				mapper.setRequirement(requirementInds);

				// publish
				if (dep.isPublishAccessInd()) {
					publishInds.add("Access");
				}
				if (dep.isPublishCreateInd()) {
					publishInds.add("Create");
				}
				if (dep.isPublishUpdateInd()) {
					publishInds.add("Update");
				}
				if (dep.isPublishDeleteInd()) {
					publishInds.add("Delete");
				}
				if (dep.isPublishFullListInd()) {
					publishInds.add("Full List");
				}
				mapper.setPublish(publishInds);

				// pulse
				if (dep.isPulseAccessInd()) {
					pulseInds.add("Access");
				}
				if (dep.isPulseCreateInd()) {
					pulseInds.add("Create");
				}
				if (dep.isPulseUpdateInd()) {
					pulseInds.add("Update");
				}
				if (dep.isPulseDeleteInd()) {
					pulseInds.add("Delete");
				}
				if (dep.isPulseFullListInd()) {
					pulseInds.add("Full List");
				}
				mapper.setPulse(pulseInds);

				// contact
				if (dep.isContactAccessInd()) {
					contactInds.add("Access");
				}
				if (dep.isContactCreateInd()) {
					contactInds.add("Create");
				}
				if (dep.isContactUpdateInd()) {
					contactInds.add("Update");
				}
				if (dep.isContactDeleteInd()) {
					contactInds.add("Delete");
				}
				if (dep.isContactFullListInd()) {
					contactInds.add("Full List");
				}
				mapper.setContact(contactInds);

				// assessment

				if (dep.isAssessmentAccessInd()) {
					assessmentInds.add("Access");
				}
				if (dep.isAssessmentCreateInd()) {
					assessmentInds.add("Create");
				}
				if (dep.isAssessmentUpdateInd()) {
					assessmentInds.add("Update");
				}
				if (dep.isAssessmentDeleteInd()) {
					assessmentInds.add("Delete");
				}
				if (dep.isAssessmentFullListInd()) {
					assessmentInds.add("Full List");
				}
				mapper.setAssessment(assessmentInds);

				// leads

				if (dep.isLeadsAccessInd()) {
					leadsInds.add("Access");
				}
				if (dep.isLeadsCreateInd()) {
					leadsInds.add("Create");
				}
				if (dep.isLeadsUpdateInd()) {
					leadsInds.add("Update");
				}
				if (dep.isLeadsDeleteInd()) {
					leadsInds.add("Delete");
				}
				if (dep.isLeadsFullListInd()) {
					leadsInds.add("Full List");
				}
				mapper.setLeads(leadsInds);
				
				//test
				if (dep.isTestAccessInd()) {
					testInds.add("Access");
				}
				if (dep.isTestCreateInd()) {
					testInds.add("Create");
				}
				if (dep.isTestUpdateInd()) {
					testInds.add("Update");
				}
				if (dep.isTestDeleteInd()) {
					testInds.add("Delete");
				}
				if (dep.isTestFullListInd()) {
					testInds.add("Full List");
				}
				mapper.setTest(testInds);
				
				//program
				if (dep.isProgramAccessInd()) {
					programInds.add("Access");
				}
				if (dep.isProgramCreateInd()) {
					programInds.add("Create");
				}
				if (dep.isProgramUpdateInd()) {
					programInds.add("Update");
				}
				if (dep.isProgramDeleteInd()) {
					programInds.add("Delete");
				}
				if (dep.isProgramFullListInd()) {
					programInds.add("Full List");
				}
				mapper.setProgram(programInds);
				
				//course
				if (dep.isCourseAccessInd()) {
					courseInds.add("Access");
				}
				if (dep.isCourseCreateInd()) {
					courseInds.add("Create");
				}
				if (dep.isCourseUpdateInd()) {
					courseInds.add("Update");
				}
				if (dep.isCourseDeleteInd()) {
					courseInds.add("Delete");
				}
				if (dep.isCourseFullListInd()) {
					courseInds.add("Full List");
				}
				mapper.setCourse(courseInds);
				
				//hours
				if (dep.isHoursAccessInd()) {
					hoursInds.add("Access");
				}
				if (dep.isHoursCreateInd()) {
					hoursInds.add("Create");
				}
				if (dep.isHoursUpdateInd()) {
					hoursInds.add("Update");
				}
				if (dep.isHoursDeleteInd()) {
					hoursInds.add("Delete");
				}
				if (dep.isHoursFullListInd()) {
					hoursInds.add("Full List");
				}
				mapper.setHours(hoursInds);
				
				
				//task
				/*if (dep.isTaskAccessInd()) {
					taskInds.add("Access");
				}
				if (dep.isTaskCreateInd()) {
					taskInds.add("Create");
				}
				if (dep.isTaskUpdateInd()) {
					taskInds.add("Update");
				}
				if (dep.isTaskDeleteInd()) {
					taskInds.add("Delete");
				}*/
				if (dep.isTaskFullListInd()) {
					taskInds.add("Full List");
				}
				mapper.setTask(taskInds);
				
				//Commercial
				if (dep.isComercialAccessInd()) {
					comercialInds.add("Access");
				}
				if (dep.isComercialCreateInd()) {
					comercialInds.add("Create");
				}
				if (dep.isComercialUpdateInd()) {
					comercialInds.add("Update");
				}
				if (dep.isComercialDeleteInd()) {
					comercialInds.add("Delete");
				}
				if (dep.isComercialFullListInd()) {
					comercialInds.add("Full List");
				}
				mapper.setComercial(comercialInds);

				//location
				if (dep.isLocationAccessInd()) {
					locationInds.add("Access");
				}
				if (dep.isLocationCreateInd()) {
					locationInds.add("Create");
				}
				if (dep.isLocationUpdateInd()) {
					locationInds.add("Update");
				}
				if (dep.isLocationDeleteInd()) {
					locationInds.add("Delete");
				}
				if (dep.isLocationFullListInd()) {
					locationInds.add("Full List");
				}
				mapper.setLocation(locationInds);
				
				//leave
				if (dep.isLeaveAccessInd()) {
					leaveInds.add("Access");
				}
				if (dep.isLeaveFullListInd()) {
					leaveInds.add("Full List");
				}
				mapper.setLeave(leaveInds);
				
				//expense
				if (dep.isExpenseAccessInd()) {
					expenseInds.add("Access");
				}
				if (dep.isExpenseFullListInd()) {
					expenseInds.add("Full List");
				}
				mapper.setExpense(expenseInds);
				
				//mileage
				if (dep.isExpenseAccessInd()) {
					mileageInds.add("Access");
				}
				if (dep.isMileageFullListInd()) {
					mileageInds.add("Full List");
				}
				mapper.setMileage(mileageInds);

				//user
				if (dep.isUserAccessInd()) {
					userInds.add("Access");
				}
				if (dep.isUserCreateInd()) {
					userInds.add("Create");
				}
				if (dep.isUserUpdateInd()) {
					userInds.add("Update");
				}
				if (dep.isUserDeleteInd()) {
					userInds.add("Delete");
				}
				if (dep.isUserAccessPlusInd()) {
					userInds.add("Access Plus");
				}
				mapper.setUser(userInds);
				
				//account
				if (dep.isAccountAccessInd()) {
					accountInds.add("Access");
				}
				if (dep.isAccountCreateInd()) {
					accountInds.add("Create");
				}
				if (dep.isAccountUpdateInd()) {
					locationInds.add("Update");
				}
				if (dep.isAccountDeleteInd()) {
					accountInds.add("Delete");
				}
				if (dep.isAccountFullListInd()) {
					accountInds.add("Full List");
				}
				if (dep.isAccountInfoInd()) {
					accountInds.add("Info");
				}
				mapper.setAccount(accountInds);
				
				
				//order
				if (dep.isOrderAccessInd()) {
					orderInds.add("Access");
				}
				if (dep.isOrderCreateInd()) {
					orderInds.add("Create");
				}
				if (dep.isOrderUpdateInd()) {
					orderInds.add("Update");
				}
				if (dep.isOrderDeleteInd()) {
					orderInds.add("Delete");
				}
				if (dep.isOrderFullListInd()) {
					orderInds.add("Full List");
				}
				mapper.setOrder(orderInds);
				
				
				//material
				if (dep.isMaterialAccessInd()) {
					materialsInds.add("Access");
				}
				if (dep.isMaterialCreateInd()) {
					materialsInds.add("Create");
				}
				if (dep.isMaterialUpdateInd()) {
					materialsInds.add("Update");
				}
				if (dep.isMaterialDeleteInd()) {
					materialsInds.add("Delete");
				}
				mapper.setMaterial(materialsInds);
				
				
				//supplier
				if (dep.isSupplierAccessInd()) {
					supplierInds.add("Access");
				}
				if (dep.isSupplierCreateInd()) {
					supplierInds.add("Create");
				}
				if (dep.isSupplierUpdateInd()) {
					supplierInds.add("Update");
				}
				if (dep.isSupplierDeleteInd()) {
					supplierInds.add("Delete");
				}
				if (dep.isSupplierFullListInd()) {
					supplierInds.add("Full List");
				}
				if (dep.isSupplierBlockInd()) {
					supplierInds.add("Block");
				}
				if (dep.isSupplierInventoryInd()) {
					supplierInds.add("Inventory");
				}
				mapper.setSupplier(supplierInds);
				
				
				//inventory
				if (dep.isInventoryAccessInd()) {
					inventoryInds.add("Access");
				}
				if (dep.isInventoryCreateInd()) {
					inventoryInds.add("Create");
				}
				if (dep.isInventoryUpdateInd()) {
					inventoryInds.add("Update");
				}
				if (dep.isInventoryDeleteInd()) {
					inventoryInds.add("Delete");
				}
				if (dep.isInventoryFullListInd()) {
					inventoryInds.add("Full List");
				}
				mapper.setInventory(inventoryInds);
				
				
				//refurbish
				if (dep.isRefurbishAdminAssignInd()) {
					refurbishInds.add("Adminassign");
				}
				if (dep.isRefurbishAdminviewInd()) {
					refurbishInds.add("Adminview");
				}
				if (dep.isRefurbishWorkshopInd()) {
					refurbishInds.add("Workshop");
				}
				
				mapper.setRefurbish(refurbishInds);
				
				//dashboard
				if (dep.isDashboardAccessInd()) {
					dashboardInds.add("Access");
				}
				if (dep.isDashboardFullListInd()) {
					dashboardInds.add("Full List");
				}
				if (dep.isDashboardRegionalInd()) {
					dashboardInds.add("Regional");
				}
				
				mapper.setDashboard(dashboardInds);

				//settings
				if (dep.isSettingsAccessInd()) {
					settingsInds.add("Access");
				}
				mapper.setSettings(settingsInds);
				
				//junk
				if (dep.isJunkAccessInd()) {
					junkInds.add("Access");
				}
				if (dep.isJunkTransferInd()) {
					junkInds.add("Transfer");
				}
				mapper.setJunk(junkInds);
				
				//investor
				if (dep.isInvestorAccessInd()) {
					investorInds.add("Access");
				}
				if (dep.isInvestorCreateInd()) {
					investorInds.add("Create");
				}
				if (dep.isInvestorUpdateInd()) {
					investorInds.add("Update");
				}
				if (dep.isInvestorDeleteInd()) {
					investorInds.add("Delete");		
				}
				if (dep.isInvestorFullListInd()) {
					investorInds.add("Full List");
				}
				mapper.setInvestor(investorInds);
				
				//investorContact
				if (dep.isInvestorContactAccessInd()) {
					investorContactInds.add("Access");
				}
				if (dep.isInvestorContactCreateInd()) {
					investorContactInds.add("Create");
				}
				if (dep.isInvestorContactUpdateInd()) {
					investorContactInds.add("Update");
				}
				if (dep.isInvestorContactDeleteInd()) {
					investorContactInds.add("Delete");		
				}
				if (dep.isInvestorContactFullListInd()) {
					investorContactInds.add("Full List");
				}
				mapper.setInvestorContact(investorContactInds);
				
				//deal
				if (dep.isDealAccessInd()) {
					dealInds.add("Access");
				}
				if (dep.isDealCreateInd()) {
					dealInds.add("Create");
				}
				if (dep.isDealUpdateInd()) {
					dealInds.add("Update");
				}
				if (dep.isDealDeleteInd()) {
					dealInds.add("Delete");		
				}
				if (dep.isDealFullListInd()) {
					dealInds.add("Full List");
				}
				mapper.setDeal(dealInds);
				
				//Pitch
				if (dep.isPitchAccessInd()) {
					pitchInds.add("Access");
				}
				if (dep.isPitchCreateInd()) {
					pitchInds.add("Create");
				}
				if (dep.isPitchUpdateInd()) {
					pitchInds.add("Update");
				}
				if (dep.isPitchDeleteInd()) {
					pitchInds.add("Delete");		
				}
				if (dep.isPitchFullListInd()) {
					pitchInds.add("Full List");
				}
				mapper.setPitch(pitchInds);
				
				//Repository
				if (dep.isRepositoryCreateInd()) {
					repositoryInds.add("Create");
				}
				mapper.setRepository(repositoryInds);
				
				// Shipper
				if (dep.isShipperAccessInd()) {
					shipperInds.add("Access");
				}
				if (dep.isShipperCreateInd()) {
					shipperInds.add("Create");
				}
				if (dep.isShipperUpdateInd()) {
					shipperInds.add("Update");
				}
				if (dep.isShipperDeleteInd()) {
					shipperInds.add("Delete");
				}
				if (dep.isShipperFullListInd()) {
					shipperInds.add("Full List");
				}
				mapper.setShipper(shipperInds);
				
				//Plant
				if (dep.isPlantAccessInd()) {
					plantInds.add("Access");
				}
				if (dep.isPlantCreateInd()) {
					plantInds.add("Create");
				}
				if (dep.isPlantUpdateInd()) {
					plantInds.add("Update");
				}
				if (dep.isPlantDeleteInd()) {
					plantInds.add("Delete");		
				}
				if (dep.isPlantFullListInd()) {
					plantInds.add("Full List");
				}
				mapper.setPlant(plantInds);
				
				//Teams
				if (dep.isTeamsAccessInd()) {
					teamInds.add("Access");
				}
				if (dep.isTeamsCreateInd()) {
					teamInds.add("Create");
				}
				if (dep.isTeamsUpdateInd()) {
					teamInds.add("Update");
				}
				if (dep.isTeamsDeleteInd()) {
					teamInds.add("Delete");		
				}
				if (dep.isTeamsFullListInd()) {
					teamInds.add("Full List");
				}
				mapper.setTeams(teamInds);
				
				//Basic
				if (dep.isBasicAccessInd()) {
					basicInds.add("Access");
				}
				mapper.setBasic(basicInds);
				
				// catalog
				if (dep.isCatalogAccessInd()) {
					catalogInds.add("Access");
				}
				if (dep.isCatalogCreateInd()) {
					catalogInds.add("Create");
				}
				if (dep.isCatalogUpdateInd()) {
					catalogInds.add("Update");
				}
				if (dep.isCatalogDeleteInd()) {
					catalogInds.add("Delete");
				}
				mapper.setCatalog(catalogInds);
				
				// collection
				if (dep.isCollectionAccessInd()) {
					collectionInds.add("Access");
				}
				if (dep.isCollectionApproveInd()) {
					collectionInds.add("Approve");
				}
				mapper.setCollection(collectionInds);
				
				// payment
				if (dep.isPaymentAccessInd()) {
					paymentInds.add("Access");
				}
				mapper.setPayment(paymentInds);
				
				//holydays
				if (dep.isHolidayAccessInd()) {
					holidayInds.add("Access");
				}
				mapper.setHoliday(holidayInds);
				
				// topic
				if (dep.isTopicAccessInd()) {
					topicInds.add("Access");
				}
				if (dep.isTopicCreateInd()) {
					topicInds.add("Create");
				}
				if (dep.isTopicUpdateInd()) {
					topicInds.add("Update");
				}
				if (dep.isTopicDeleteInd()) {
					topicInds.add("Delete");
				}
				if (dep.isTopicFullListInd()) {
					topicInds.add("Full List");
				}
				mapper.setTopic(topicInds);
				
				// procurement
				if (dep.isProcurementAccessInd()) {
					procurementInds.add("Access");
				}
				if (dep.isProcurementCreateInd()) {
					procurementInds.add("Create");
				}
				if (dep.isProcurementUpdateInd()) {
					procurementInds.add("Update");
				}
				if (dep.isProcurementDeleteInd()) {
					procurementInds.add("Delete");
				}
				if (dep.isProcurementFullListInd()) {
					procurementInds.add("Full List");
				}
				mapper.setProcurement(procurementInds);
				
				// subscription
				if (dep.isSubscriptionAccessInd()) {
					subscriptionInds.add("Access");
				}
				if (dep.isSubscriptionCreateInd()) {
					subscriptionInds.add("Create");
				}
				if (dep.isSubscriptionUpdateInd()) {
					subscriptionInds.add("Update");
				}
				if (dep.isSubscriptionDeleteInd()) {
					subscriptionInds.add("Delete");
				}
				mapper.setSubscription(subscriptionInds);
				
				// Production
				if (dep.isProductionAccessInd()) {
					productionInds.add("Access");
				}
				if (dep.isProductionCreateInd()) {
					productionInds.add("Create");
				}
				if (dep.isProductionUpdateInd()) {
					productionInds.add("Update");
				}
				if (dep.isProductionDeleteInd()) {
					productionInds.add("Delete");
				}
				mapper.setProduction(productionInds);
				
				 //report
				if (dep.isReportFullListInd()) {
					reportInds.add("Full List");
				}
				mapper.setReport(reportInds);

				// dataRoom
				if (dep.isDataRoomAccessInd()) {
					dataRoomInds.add("Access");
				}
				if (dep.isDataRoomCreateInd()) {
					dataRoomInds.add("Create");
				}
				if (dep.isDataRoomDeleteInd()) {
					dataRoomInds.add("Update");
				}
				if (dep.isDataRoomDeleteInd()) {
					dataRoomInds.add("Delete");
				}
				if (dep.isDataRoomFullListInd()) {
					dataRoomInds.add("Full List");
				}
				mapper.setDataRoom(dataRoomInds);
				
				// scanner
				if (dep.isScannerAccessInd()) {
					scannerInds.add("Access");
				}
				mapper.setScanner(scannerInds);
				
				// quality
				if (dep.isQualityAccessInd()) {
					qualityInds.add("Access");
				}
				if (dep.isQualityCreateInd()) {
					qualityInds.add("Create");
				}
				if (dep.isQualityDeleteInd()) {
					qualityInds.add("Update");
				}
				if (dep.isQualityDeleteInd()) {
					qualityInds.add("Delete");
				}
				if (dep.isQualityFullListInd()) {
					qualityInds.add("Full List");
				}
				mapper.setQuality(qualityInds);
				
				// club
				if (dep.isClubAccessInd()) {
					clubInds.add("Access");
				}
				if (dep.isClubCreateInd()) {
					clubInds.add("Create");
				}
				if (dep.isClubUpdateInd()) {
					clubInds.add("Update");
				}
				if (dep.isClubDeleteInd()) {
					clubInds.add("Delete");
				}
				if (dep.isClubFullListInd()) {
					clubInds.add("Full List");
				}
				mapper.setClub(clubInds);
				
				// calender
				if (dep.isCalenderManageInd()) {
					calenderInds.add("Manage");
				}
				if (dep.isCalenderViewInd()) {
					calenderInds.add("View");
				}
				mapper.setCalender(calenderInds);
				
				mapper.setRoleTypeId(dep.getRoleTypeId());
				mapper.setDepartmentId(dep.getDepartmentId());
				mapper.setUserId(dep.getUserId());
				mapper.setLastUpdatedOn(Utility.getISOFromDate(dep.getLastUpdatedOn()));

				EmployeeDetails employeeDetails = employeeRepository.getEmployeeByUserId(dep.getUserId());

				if (employeeDetails != null) {

					String middleName = " ";
					String lastName = "";

					if (!StringUtils.isEmpty(employeeDetails.getLastName())) {

						lastName = employeeDetails.getLastName();
					}

					if (employeeDetails.getMiddleName() != null && employeeDetails.getMiddleName().length() > 0) {

						middleName = employeeDetails.getMiddleName();
						mapper.setName(employeeDetails.getFirstName() + " " + middleName + " " + lastName);
					} else {

						mapper.setName(employeeDetails.getFirstName() + " " + lastName);
					}

				}
			}
		}
		return mapper;
	}

	@Override
	public String savecompliance(ComplianceMapper complianceMapper) {
		String id = null;

		Compliance compliance = complianceRepository.findByOrgId(complianceMapper.getOrganizationId());

		if (compliance != null) {
			// compliance.setCandidateId(complianceMapper.getCandidateId());
			compliance.setGdprApplicableInd(complianceMapper.isGdprApplicableInd());
			compliance.setOrgId(complianceMapper.getOrganizationId());
			compliance.setUserId(complianceMapper.getUserId());
			compliance.setLastUpdatedOn(new Date());
			id = complianceRepository.save(compliance).getComplianceId();
			
		} else {
			Compliance compliance1 = new Compliance();
			// compliance1.setCandidateId(complianceMapper.getCandidateId());
			compliance1.setGdprApplicableInd(complianceMapper.isGdprApplicableInd());
			compliance1.setOrgId(complianceMapper.getOrganizationId());
			compliance1.setUserId(complianceMapper.getUserId());
			compliance1.setLastUpdatedOn(new Date());
			id = complianceRepository.save(compliance1).getComplianceId();
		}

		return id;
	}

	@Override
	public ComplianceMapper getComplianceAccessByOrgId(String orgId) {
		ComplianceMapper mapper = new ComplianceMapper();
		Compliance pem = complianceRepository.findByOrgId(orgId);
		if (pem != null) {
			mapper.setGdprApplicableInd(pem.isGdprApplicableInd());
			mapper.setLastUpdatedOn(Utility.getISOFromDate(pem.getLastUpdatedOn()));
			EmployeeDetails employeeDetails = employeeRepository.getEmployeeByUserId(pem.getUserId());

			if (employeeDetails != null) {

				String middleName = " ";
				String lastName = "";

				if (!StringUtils.isEmpty(employeeDetails.getLastName())) {

					lastName = employeeDetails.getLastName();
				}

				if (employeeDetails.getMiddleName() != null && employeeDetails.getMiddleName().length() > 0) {

					middleName = employeeDetails.getMiddleName();
					mapper.setName(employeeDetails.getFirstName() + " " + middleName + " " + lastName);
				} else {

					mapper.setName(employeeDetails.getFirstName() + " " + lastName);
				}

			}
		}

		return mapper;

	}

	@Override
	public String saveCommunicationAccess(CommunicationMapper communicationMapper) {
		String id = null;
		if (communicationMapper != null) {
			Communication dbCommunication = communicationRepository.findByOrgId(communicationMapper.getOrgId());
			if (dbCommunication != null) {
				dbCommunication.setEmailCustomerInd(communicationMapper.isEmailCustomerInd());
				dbCommunication.setEmailJobDesInd(communicationMapper.isEmailJobDesInd());
				dbCommunication.setWhatsappCustomerInd(communicationMapper.isWhatsappCustomerInd());
				dbCommunication.setWhatsappJobDesInd(communicationMapper.isWhatsappJobDesInd());
				dbCommunication.setOrgId(communicationMapper.getOrgId());
				dbCommunication.setUserId(communicationMapper.getUserId());
				dbCommunication.setLastUpdatedOn(new Date());
				dbCommunication.setCandidateEventUpdateInd(communicationMapper.isCandidateEventUpdateInd());
				dbCommunication.setCandiWorkflowEnabledInstInd(communicationMapper.isCandiWorkflowEnabledInstInd());
				dbCommunication.setCandiPipelineEmailInd(communicationMapper.isCandiPipelineEmailInd());

				id = communicationRepository.save(dbCommunication).getCommunicationId();
			} else {
				Communication communication = new Communication();
				communication.setEmailCustomerInd(communicationMapper.isEmailCustomerInd());
				communication.setEmailJobDesInd(communicationMapper.isEmailJobDesInd());
				communication.setWhatsappCustomerInd(communicationMapper.isWhatsappCustomerInd());
				communication.setWhatsappJobDesInd(communicationMapper.isWhatsappJobDesInd());
				communication.setOrgId(communicationMapper.getOrgId());
				communication.setUserId(communicationMapper.getUserId());
				communication.setLastUpdatedOn(new Date());
				communication.setCandidateEventUpdateInd(communicationMapper.isCandidateEventUpdateInd());
				communication.setCandiWorkflowEnabledInstInd(communicationMapper.isCandiWorkflowEnabledInstInd());
				communication.setCandiPipelineEmailInd(communicationMapper.isCandiPipelineEmailInd());
				id = communicationRepository.save(communication).getCommunicationId();
			}
		}
		return id;
	}

	@Override
	public CommunicationMapper getCommunicationAccessByOrgId(String orgId) {
		CommunicationMapper mapper = new CommunicationMapper();
		Communication pem = communicationRepository.findByOrgId(orgId);

		if (pem != null) {
			mapper.setEmailCustomerInd(pem.isEmailCustomerInd());
			mapper.setEmailJobDesInd(pem.isEmailJobDesInd());
			mapper.setWhatsappCustomerInd(pem.isWhatsappCustomerInd());
			mapper.setWhatsappJobDesInd(pem.isWhatsappJobDesInd());
			mapper.setOrgId(pem.getOrgId());
			mapper.setUserId(pem.getUserId());
			mapper.setCandidateEventUpdateInd(pem.isCandidateEventUpdateInd());
			mapper.setCommunicationId(pem.getCommunicationId());
			mapper.setLastUpdatedOn(Utility.getISOFromDate(pem.getLastUpdatedOn()));
			mapper.setCandiWorkflowEnabledInstInd(pem.isCandiWorkflowEnabledInstInd());
			mapper.setCandiPipelineEmailInd(pem.isCandiPipelineEmailInd());
			EmployeeDetails employeeDetails = employeeRepository.getEmployeeByUserId(pem.getUserId());

			if (employeeDetails != null) {

				String middleName = " ";
				String lastName = "";

				if (!StringUtils.isEmpty(employeeDetails.getLastName())) {

					lastName = employeeDetails.getLastName();
				}

				if (employeeDetails.getMiddleName() != null && employeeDetails.getMiddleName().length() > 0) {

					middleName = employeeDetails.getMiddleName();
					mapper.setName(employeeDetails.getFirstName() + " " + middleName + " " + lastName);
				} else {

					mapper.setName(employeeDetails.getFirstName() + " " + lastName);
				}

			}
		}
		return mapper;
	}

	@Override
	public String saveSourcingAccess(SourcingMapper sourcingMapper) {
		String id = null;
		if (sourcingMapper != null) {
			Sourcing dbSourcing = sourcingRepository.findByOrgId(sourcingMapper.getOrgId());
			if (dbSourcing != null) {
				dbSourcing.setTalentOutRichInd(sourcingMapper.isTalentOutRichInd());
				dbSourcing.setOrgId(sourcingMapper.getOrgId());
				dbSourcing.setUserId(sourcingMapper.getUserId());
				dbSourcing.setLastUpdatedOn(new Date());

				id = sourcingRepository.save(dbSourcing).getSourcingId();
			} else {
				Sourcing sourcing = new Sourcing();
				sourcing.setTalentOutRichInd(sourcingMapper.isTalentOutRichInd());
				sourcing.setOrgId(sourcingMapper.getOrgId());
				sourcing.setUserId(sourcingMapper.getUserId());
				sourcing.setLastUpdatedOn(new Date());
				id = sourcingRepository.save(sourcing).getSourcingId();
			}
		}
		return id;
	}

	@Override
	public SourcingMapper getSourcingAccessByOrgId(String orgId) {
		SourcingMapper mapper = new SourcingMapper();
		Sourcing pem = sourcingRepository.findByOrgId(orgId);

		if (pem != null) {
			mapper.setSourcingId(pem.getSourcingId());
			mapper.setTalentOutRichInd(pem.isTalentOutRichInd());
			mapper.setOrgId(pem.getOrgId());
			mapper.setUserId(pem.getUserId());
			mapper.setLastUpdatedOn(Utility.getISOFromDate(pem.getLastUpdatedOn()));

			EmployeeDetails employeeDetails = employeeRepository.getEmployeeByUserId(pem.getUserId());

			if (employeeDetails != null) {

				String middleName = " ";
				String lastName = "";

				if (!StringUtils.isEmpty(employeeDetails.getLastName())) {

					lastName = employeeDetails.getLastName();
				}

				if (employeeDetails.getMiddleName() != null && employeeDetails.getMiddleName().length() > 0) {

					middleName = employeeDetails.getMiddleName();
					mapper.setName(employeeDetails.getFirstName() + " " + middleName + " " + lastName);
				} else {

					mapper.setName(employeeDetails.getFirstName() + " " + lastName);
				}
			}
		}
		return mapper;
	}

	@Override
	public String saveNotificationPermission(NotificationPermissionMapper notificationPermissionMapper) {

		String id = null;

		if (notificationPermissionMapper != null) {
			NotificationPermission notificationPermission = notificationPermissionRepository.getNotificationPermission(
					notificationPermissionMapper.getUserId(), notificationPermissionMapper.getOrganizationId());
			if (notificationPermission != null) {

				List<String> customerInds = notificationPermissionMapper.getCustomer();
				if (notificationPermissionMapper.getCustomer().contains("Self")) {
					notificationPermission.setCustomerSelfInd(true);
				} else {
					notificationPermission.setCustomerSelfInd(false);
				}
				System.out.println("CustomerSelfInd()>>" + notificationPermission.isCustomerSelfInd());
				if (customerInds.contains("Management")) {
					notificationPermission.setCustomerManagementInd(true);
				} else {
					notificationPermission.setCustomerManagementInd(false);
				}
				System.out.println("CustomerManagementInd>>" + notificationPermission.isCustomerManagementInd());
				if (customerInds.contains("ReportPerson")) {
					notificationPermission.setCustomerReportPersonInd(true);
				} else {
					notificationPermission.setCustomerReportPersonInd(false);
				}
				System.out.println("CustomerReportPersonInd>>" + notificationPermission.isCustomerReportPersonInd());

				List<String> contactInds = notificationPermissionMapper.getContact();
				if (notificationPermissionMapper.getContact().contains("Self")) {
					notificationPermission.setContactSelfInd(true);
				} else {
					notificationPermission.setContactSelfInd(false);
				}
				System.out.println("ContactSelfInd()>>" + notificationPermission.isContactSelfInd());
				if (contactInds.contains("Management")) {
					notificationPermission.setContactManagementInd(true);
				} else {
					notificationPermission.setContactManagementInd(false);
				}
				System.out.println("ContactManagementInd>>" + notificationPermission.isContactManagementInd());
				if (customerInds.contains("ReportPerson")) {
					notificationPermission.setContactReportPersonInd(true);
				} else {
					notificationPermission.setContactReportPersonInd(false);
				}
				System.out.println("ContactReportPersonInd>>" + notificationPermission.isContactReportPersonInd());

				List<String> opportunityInds = notificationPermissionMapper.getOpportunity();

				if (notificationPermissionMapper.getOpportunity().contains("Self")) {
					notificationPermission.setOpportunitySelfInd(true);
				} else {
					notificationPermission.setOpportunitySelfInd(false);
				}
				System.out.println("OpportunitySelfInd()>>" + notificationPermission.isOpportunitySelfInd());
				if (opportunityInds.contains("Management")) {
					notificationPermission.setOpportunityManagementInd(true);
				} else {
					notificationPermission.setOpportunityManagementInd(false);
				}
				System.out.println("OpportunityManagementInd>>" + notificationPermission.isOpportunityManagementInd());
				if (opportunityInds.contains("ReportPerson")) {
					notificationPermission.setOpportunityReportPersonInd(true);
				} else {
					notificationPermission.setOpportunityReportPersonInd(false);
				}
				System.out.println(
						"OpportunityReportPersonInd>>" + notificationPermission.isOpportunityReportPersonInd());

				List<String> requirementCreateInds = notificationPermissionMapper.getRequirementCreate();

				if (notificationPermissionMapper.getRequirementCreate().contains("Self")) {
					notificationPermission.setRequirementCreateSelfInd(true);
				} else {
					notificationPermission.setRequirementCreateSelfInd(false);
				}
				System.out
						.println("RequirementCreateSelfInd()>>" + notificationPermission.isRequirementCreateSelfInd());
				if (requirementCreateInds.contains("Management")) {
					notificationPermission.setRequirementCreateManagementInd(true);
				} else {
					notificationPermission.setRequirementCreateManagementInd(false);
				}
				System.out.println(
						"RequirementCreateManagementInd>>" + notificationPermission.isRequirementCreateManagementInd());
				if (requirementCreateInds.contains("ReportPerson")) {
					notificationPermission.setRequirementCreateReportPersonInd(true);
				} else {
					notificationPermission.setRequirementCreateReportPersonInd(false);
				}
				System.out.println("RequirementCreateReportPersonInd>>"
						+ notificationPermission.isRequirementCreateReportPersonInd());

				List<String> requirementCloseInds = notificationPermissionMapper.getRequirementClose();

				if (notificationPermissionMapper.getRequirementClose().contains("Self")) {
					notificationPermission.setRequirementCloseSelfInd(true);
				} else {
					notificationPermission.setRequirementCloseSelfInd(false);
				}
				System.out.println("RequirementCloseSelfInd()>>" + notificationPermission.isRequirementCloseSelfInd());
				if (requirementCloseInds.contains("Management")) {
					notificationPermission.setRequirementCloseManagementInd(true);
				} else {
					notificationPermission.setRequirementCloseManagementInd(false);
				}
				System.out.println(
						"RequirementCloseManagementInd>>" + notificationPermission.isRequirementCloseManagementInd());
				if (requirementCloseInds.contains("ReportPerson")) {
					notificationPermission.setRequirementCloseReportPersonInd(true);
				} else {
					notificationPermission.setRequirementCloseReportPersonInd(false);
				}
				System.out.println("RequirementCloseReportPersonInd>>"
						+ notificationPermission.isRequirementCloseReportPersonInd());

				List<String> vendorInds = notificationPermissionMapper.getVendor();

				if (notificationPermissionMapper.getVendor().contains("Self")) {
					notificationPermission.setVendorSelfInd(true);
				} else {
					notificationPermission.setVendorSelfInd(false);
				}
				System.out.println("VendorSelfInd()>>" + notificationPermission.isVendorSelfInd());
				if (vendorInds.contains("Management")) {
					notificationPermission.setVendorManagementInd(true);
				} else {
					notificationPermission.setVendorManagementInd(false);
				}
				System.out.println("VendorManagementInd>>" + notificationPermission.isVendorManagementInd());
				if (vendorInds.contains("ReportPerson")) {
					notificationPermission.setVendorReportPersonInd(true);
				} else {
					notificationPermission.setVendorReportPersonInd(false);
				}
				System.out.println("VendorReportPersonInd>>" + notificationPermission.isVendorReportPersonInd());

				List<String> customerLoginInds = notificationPermissionMapper.getCustomerLogin();

				if (notificationPermissionMapper.getCustomerLogin().contains("Self")) {
					notificationPermission.setCustomerLoginSelfInd(true);
				} else {
					notificationPermission.setCustomerLoginSelfInd(false);
				}
				System.out.println("CustomerLoginSelfInd()>>" + notificationPermission.isCustomerLoginSelfInd());
				if (customerLoginInds.contains("Management")) {
					notificationPermission.setCustomerLoginManagementInd(true);
				} else {
					notificationPermission.setCustomerLoginManagementInd(false);
				}
				System.out.println(
						"CustomerLoginManagementInd>>" + notificationPermission.isCustomerLoginManagementInd());
				if (customerLoginInds.contains("ReportPerson")) {
					notificationPermission.setCustomerLoginReportPersonInd(true);
				} else {
					notificationPermission.setCustomerLoginReportPersonInd(false);
				}
				System.out.println(
						"CustomerLoginReportPersonInd>>" + notificationPermission.isCustomerLoginReportPersonInd());

				List<String> vendorLoginInds = notificationPermissionMapper.getVendorLogin();

				if (notificationPermissionMapper.getVendorLogin().contains("Self")) {
					notificationPermission.setVendorLoginSelfInd(true);
				} else {
					notificationPermission.setVendorLoginSelfInd(false);
				}
				System.out.println("VendorLoginSelfInd()>>" + notificationPermission.isVendorLoginSelfInd());
				if (vendorLoginInds.contains("Management")) {
					notificationPermission.setVendorLoginManagementInd(true);
				} else {
					notificationPermission.setVendorLoginManagementInd(false);
				}
				System.out.println("VendorLoginManagementInd>>" + notificationPermission.isVendorLoginManagementInd());
				if (vendorLoginInds.contains("ReportPerson")) {
					notificationPermission.setVendorLoginReportPersonInd(true);
				} else {
					notificationPermission.setVendorLoginReportPersonInd(false);
				}
				System.out.println(
						"VendorLoginReportPersonInd>>" + notificationPermission.isVendorLoginReportPersonInd());

				List<String> talentSelectInds = notificationPermissionMapper.getTalentSelect();

				if (notificationPermissionMapper.getTalentSelect().contains("Self")) {
					notificationPermission.setCandidateSelectSelfInd(true);
				} else {
					notificationPermission.setCandidateSelectSelfInd(false);
				}
				System.out.println(
						"CandidateSelectSelfIndSelfInd()>>" + notificationPermission.isCandidateSelectSelfInd());
				if (talentSelectInds.contains("Management")) {
					notificationPermission.setCandidateSelectManagementInd(true);
				} else {
					notificationPermission.setCandidateSelectManagementInd(false);
				}
				System.out.println(
						"CandidateSelectManagementInd>>" + notificationPermission.isCandidateSelectManagementInd());
				if (talentSelectInds.contains("ReportPerson")) {
					notificationPermission.setCandidateSelectReportPersonInd(true);
				} else {
					notificationPermission.setCandidateSelectReportPersonInd(false);
				}
				System.out.println(
						"CandidateSelectReportPersonInd>>" + notificationPermission.isCandidateSelectReportPersonInd());

				List<String> talentOnboardInds = notificationPermissionMapper.getTalentOnboard();

				if (notificationPermissionMapper.getTalentOnboard().contains("Self")) {
					notificationPermission.setCandidateOnboardSelfInd(true);
				} else {
					notificationPermission.setCandidateOnboardSelfInd(false);
				}
				System.out.println("CandidateOnboardSelfInd()>>" + notificationPermission.isCandidateOnboardSelfInd());
				if (talentOnboardInds.contains("Management")) {
					notificationPermission.setCandidateOnboardManagementInd(true);
				} else {
					notificationPermission.setCandidateOnboardManagementInd(false);
				}
				System.out.println(
						"CandidateOnboardManagementInd>>" + notificationPermission.isCandidateOnboardManagementInd());
				if (talentOnboardInds.contains("ReportPerson")) {
					notificationPermission.setCandidateOnboardReportPersonInd(true);
				} else {
					notificationPermission.setCandidateOnboardReportPersonInd(false);
				}
				System.out.println("CandidateOnboardReportPersonInd>>"
						+ notificationPermission.isCandidateOnboardReportPersonInd());

				List<String> talentDropInds = notificationPermissionMapper.getTalentDrop();

				if (notificationPermissionMapper.getTalentDrop().contains("Self")) {
					notificationPermission.setCandidateDropSelfInd(true);
				} else {
					notificationPermission.setCandidateDropSelfInd(false);
				}
				System.out.println("CandidateDropSelfInd()>>" + notificationPermission.isCandidateDropSelfInd());
				if (talentDropInds.contains("Management")) {
					notificationPermission.setCandidateDropManagementInd(true);
				} else {
					notificationPermission.setCandidateDropManagementInd(false);
				}
				System.out.println(
						"CandidateDropManagementInd>>" + notificationPermission.isCandidateDropManagementInd());
				if (talentDropInds.contains("ReportPerson")) {
					notificationPermission.setCandidateDropReportPersonInd(true);
				} else {
					notificationPermission.setCandidateDropReportPersonInd(false);
				}
				System.out.println(
						"CandidateDropReportPersonInd>>" + notificationPermission.isCandidateDropReportPersonInd());

				List<String> taskInds = notificationPermissionMapper.getTask();

				if (notificationPermissionMapper.getTask().contains("Self")) {
					notificationPermission.setTaskSelfInd(true);
				} else {
					notificationPermission.setTaskSelfInd(false);
				}
				System.out.println("TaskSelfInd()>>" + notificationPermission.isTaskSelfInd());
				if (taskInds.contains("Management")) {
					notificationPermission.setTaskManagementInd(true);
				} else {
					notificationPermission.setTaskManagementInd(false);
				}
				System.out.println("TaskManagementInd>>" + notificationPermission.isTaskManagementInd());
				if (taskInds.contains("ReportPerson")) {
					notificationPermission.setTaskReportPersonInd(true);
				} else {
					notificationPermission.setTaskReportPersonInd(false);
				}
				System.out.println("CustomerReportPersonInd>>" + notificationPermission.isTaskReportPersonInd());

				List<String> eventInds = notificationPermissionMapper.getEvent();

				if (notificationPermissionMapper.getEvent().contains("Self")) {
					notificationPermission.setCustomerSelfInd(true);
				} else {
					notificationPermission.setEventSelfInd(false);
				}
				System.out.println("EventSelfInd()>>" + notificationPermission.isEventSelfInd());
				if (eventInds.contains("Management")) {
					notificationPermission.setEventManagementInd(true);
				} else {
					notificationPermission.setEventManagementInd(false);
				}
				System.out.println("EventManagementInd>>" + notificationPermission.isEventManagementInd());
				if (eventInds.contains("ReportPerson")) {
					notificationPermission.setEventReportPersonInd(true);
				} else {
					notificationPermission.setEventReportPersonInd(false);
				}
				System.out.println("EventReportPersonInd>>" + notificationPermission.isEventReportPersonInd());

				List<String> callInds = notificationPermissionMapper.getCall();

				if (notificationPermissionMapper.getCall().contains("Self")) {
					notificationPermission.setCallSelfInd(true);
				} else {
					notificationPermission.setCallSelfInd(false);
				}
				System.out.println("CallSelfInd()>>" + notificationPermission.isCallSelfInd());
				if (callInds.contains("Management")) {
					notificationPermission.setCallManagementInd(true);
				} else {
					notificationPermission.setCallManagementInd(false);
				}
				System.out.println("CallManagementInd>>" + notificationPermission.isCallManagementInd());
				if (callInds.contains("ReportPerson")) {
					notificationPermission.setCallReportPersonInd(true);
				} else {
					notificationPermission.setCallReportPersonInd(false);
				}
				System.out.println("CallReportPersonInd>>" + notificationPermission.isCallReportPersonInd());

				List<String> publishJobInds = notificationPermissionMapper.getPublishJob();

				if (notificationPermissionMapper.getPublishJob().contains("Self")) {
					notificationPermission.setPublishJobSelfInd(true);
				} else {
					notificationPermission.setPublishJobSelfInd(false);
				}
				System.out.println("PublishJobSelfInd()>>" + notificationPermission.isPublishJobSelfInd());
				if (publishJobInds.contains("Management")) {
					notificationPermission.setPublishJobManagementInd(true);
				} else {
					notificationPermission.setPublishJobManagementInd(false);
				}
				System.out.println("PublishJobManagementInd>>" + notificationPermission.isPublishJobManagementInd());
				if (publishJobInds.contains("ReportPerson")) {
					notificationPermission.setPublishJobReportPersonInd(true);
				} else {
					notificationPermission.setPublishJobReportPersonInd(false);
				}
				System.out
						.println("PublishJobReportPersonInd>>" + notificationPermission.isPublishJobReportPersonInd());

				List<String> publishJobOnWebsiteInds = notificationPermissionMapper.getPublishJobOnWebsite();

				if (notificationPermissionMapper.getPublishJobOnWebsite().contains("Self")) {
					notificationPermission.setPublishJobOnWebsiteSelfInd(true);
				} else {
					notificationPermission.setPublishJobOnWebsiteSelfInd(false);
				}
				System.out.println(
						"PublishJobOnWebsiteSelfInd()>>" + notificationPermission.isPublishJobOnWebsiteSelfInd());
				if (customerInds.contains("Management")) {
					notificationPermission.setPublishJobOnWebsiteManagementInd(true);
				} else {
					notificationPermission.setPublishJobOnWebsiteManagementInd(false);
				}
				System.out.println("PublishJobOnWebsiteManagementInd>>"
						+ notificationPermission.isPublishJobOnWebsiteManagementInd());
				if (customerInds.contains("ReportPerson")) {
					notificationPermission.setPublishJobOnWebsiteReportPersonInd(true);
				} else {
					notificationPermission.setPublishJobOnWebsiteReportPersonInd(false);
				}
				System.out.println("PublishJobOnWebsiteReportPersonInd>>"
						+ notificationPermission.isPublishJobOnWebsiteReportPersonInd());

				List<String> publishJobOnJobboardInds = notificationPermissionMapper.getPublishJobOnJobboard();

				if (notificationPermissionMapper.getPublishJobOnJobboard().contains("Self")) {
					notificationPermission.setPublishJobOnJobboardSelfInd(true);
				} else {
					notificationPermission.setPublishJobOnJobboardSelfInd(false);
				}
				System.out.println(
						"PublishJobOnJobboardSelfInd()>>" + notificationPermission.isPublishJobOnJobboardSelfInd());
				if (publishJobOnJobboardInds.contains("Management")) {
					notificationPermission.setPublishJobOnJobboardManagementInd(true);
				} else {
					notificationPermission.setPublishJobOnJobboardManagementInd(false);
				}
				System.out.println("PublishJobOnJobboardManagementInd>>"
						+ notificationPermission.isPublishJobOnJobboardManagementInd());
				if (publishJobOnJobboardInds.contains("ReportPerson")) {
					notificationPermission.setPublishJobOnJobboardReportPersonInd(true);
				} else {
					notificationPermission.setPublishJobOnJobboardReportPersonInd(false);
				}
				System.out.println("PublishJobOnJobboardReportPersonInd>>"
						+ notificationPermission.isPublishJobOnJobboardReportPersonInd());

				List<String> unpublishJobInds = notificationPermissionMapper.getUnpublishJob();

				if (notificationPermissionMapper.getUnpublishJob().contains("Self")) {
					notificationPermission.setUnpublishJobSelfInd(true);
				} else {
					notificationPermission.setUnpublishJobSelfInd(false);
				}
				System.out.println("UnpublishJobSelfInd()>>" + notificationPermission.isUnpublishJobSelfInd());
				if (unpublishJobInds.contains("Management")) {
					notificationPermission.setUnpublishJobManagementInd(true);
				} else {
					notificationPermission.setUnpublishJobManagementInd(false);
				}
				System.out
						.println("UnpublishJobManagementInd>>" + notificationPermission.isUnpublishJobManagementInd());
				if (unpublishJobInds.contains("ReportPerson")) {
					notificationPermission.setUnpublishJobReportPersonInd(true);
				} else {
					notificationPermission.setUnpublishJobReportPersonInd(false);
				}
				System.out.println(
						"UnpublishJobReportPersonInd>>" + notificationPermission.isUnpublishJobReportPersonInd());

				// notificationPermission.setNotificationId(notificationPermissionMapper.getNotificationId());
				notificationPermission.setUserId(notificationPermissionMapper.getUserId());
				notificationPermission.setOrgId(notificationPermissionMapper.getOrganizationId());
				notificationPermission.setLastUpdatedOn(new Date());

				id = notificationPermissionRepository.save(notificationPermission).getId();
				System.out.println("in if....." + id);
			} else {
				NotificationPermission notificationPermission1 = new NotificationPermission();

				List<String> customerInds = notificationPermissionMapper.getCustomer();
				// if (null!=vendorInds && !vendorInds.isEmpty()) {
				if (customerInds.contains("Self")) {
					notificationPermission1.setCustomerSelfInd(true);
				} else {
					notificationPermission1.setCustomerSelfInd(false);
				}
				if (customerInds.contains("Management")) {
					notificationPermission1.setCustomerManagementInd(true);
				} else {
					notificationPermission1.setCustomerManagementInd(false);
				}
				if (customerInds.contains("ReportPerson")) {
					notificationPermission1.setCustomerReportPersonInd(true);
				} else {
					notificationPermission1.setCustomerReportPersonInd(false);
				}

				List<String> contactInds = notificationPermissionMapper.getContact();
				// if (null!=vendorInds && !vendorInds.isEmpty()) {
				if (contactInds.contains("Self")) {
					notificationPermission1.setContactSelfInd(true);
				} else {
					notificationPermission1.setContactSelfInd(false);
				}
				if (contactInds.contains("Management")) {
					notificationPermission1.setContactManagementInd(true);
				} else {
					notificationPermission1.setContactManagementInd(false);
				}
				if (contactInds.contains("ReportPerson")) {
					notificationPermission1.setContactReportPersonInd(true);
				} else {
					notificationPermission1.setContactReportPersonInd(false);
				}

				List<String> opportunityInds = notificationPermissionMapper.getOpportunity();
				// if (null!=vendorInds && !vendorInds.isEmpty()) {
				if (opportunityInds.contains("Self")) {
					notificationPermission1.setOpportunitySelfInd(true);
				} else {
					notificationPermission1.setOpportunitySelfInd(false);
				}
				if (opportunityInds.contains("Management")) {
					notificationPermission1.setOpportunityManagementInd(true);
				} else {
					notificationPermission1.setOpportunityManagementInd(false);
				}
				if (opportunityInds.contains("ReportPerson")) {
					notificationPermission1.setOpportunityReportPersonInd(true);
				} else {
					notificationPermission1.setOpportunityReportPersonInd(false);
				}

				List<String> requirementCreateInds = notificationPermissionMapper.getRequirementCreate();
				// if (null!=vendorInds && !vendorInds.isEmpty()) {
				if (requirementCreateInds.contains("Self")) {
					notificationPermission1.setRequirementCreateSelfInd(true);
				} else {
					notificationPermission1.setRequirementCreateSelfInd(false);
				}
				if (requirementCreateInds.contains("Management")) {
					notificationPermission1.setRequirementCreateManagementInd(true);
				} else {
					notificationPermission1.setRequirementCreateManagementInd(false);
				}
				if (requirementCreateInds.contains("ReportPerson")) {
					notificationPermission1.setRequirementCreateReportPersonInd(true);
				} else {
					notificationPermission1.setRequirementCreateReportPersonInd(false);
				}

				List<String> requirementCloseInds = notificationPermissionMapper.getRequirementClose();
				// if (null!=vendorInds && !vendorInds.isEmpty()) {
				if (requirementCloseInds.contains("Self")) {
					notificationPermission1.setRequirementCloseSelfInd(true);
				} else {
					notificationPermission1.setRequirementCloseSelfInd(false);
				}
				if (requirementCloseInds.contains("Management")) {
					notificationPermission1.setRequirementCloseManagementInd(true);
				} else {
					notificationPermission1.setRequirementCloseManagementInd(false);
				}
				if (requirementCloseInds.contains("ReportPerson")) {
					notificationPermission1.setRequirementCloseReportPersonInd(true);
				} else {
					notificationPermission1.setRequirementCloseReportPersonInd(false);
				}

				List<String> vendorInds = notificationPermissionMapper.getVendor();
				// if (null!=vendorInds && !vendorInds.isEmpty()) {
				if (vendorInds.contains("Self")) {
					notificationPermission1.setVendorSelfInd(true);
				} else {
					notificationPermission1.setVendorSelfInd(false);
				}
				if (vendorInds.contains("Management")) {
					notificationPermission1.setVendorManagementInd(true);
				} else {
					notificationPermission1.setVendorManagementInd(false);
				}
				if (vendorInds.contains("ReportPerson")) {
					notificationPermission1.setVendorReportPersonInd(true);
				} else {
					notificationPermission1.setVendorReportPersonInd(false);
				}

				List<String> customerLoginInds = notificationPermissionMapper.getCustomerLogin();
				// if (null!=vendorInds && !vendorInds.isEmpty()) {
				if (customerLoginInds.contains("Self")) {
					notificationPermission1.setCustomerLoginSelfInd(true);
				} else {
					notificationPermission1.setCustomerLoginSelfInd(false);
				}
				if (customerLoginInds.contains("Management")) {
					notificationPermission1.setCustomerLoginManagementInd(true);
				} else {
					notificationPermission1.setCustomerLoginManagementInd(false);
				}
				if (customerLoginInds.contains("ReportPerson")) {
					notificationPermission1.setCustomerLoginReportPersonInd(true);
				} else {
					notificationPermission1.setCustomerLoginReportPersonInd(false);
				}

				List<String> vendorLoginInds = notificationPermissionMapper.getVendorLogin();
				// if (null!=vendorInds && !vendorInds.isEmpty()) {
				if (vendorLoginInds.contains("Self")) {
					notificationPermission1.setVendorLoginSelfInd(true);
				} else {
					notificationPermission1.setVendorLoginSelfInd(false);
				}
				if (vendorLoginInds.contains("Management")) {
					notificationPermission1.setVendorLoginManagementInd(true);
				} else {
					notificationPermission1.setVendorLoginManagementInd(false);
				}
				if (vendorLoginInds.contains("ReportPerson")) {
					notificationPermission1.setVendorLoginReportPersonInd(true);
				} else {
					notificationPermission1.setVendorLoginReportPersonInd(false);
				}

				List<String> talentSelectInds = notificationPermissionMapper.getTalentSelect();
				// if (null!=vendorInds && !vendorInds.isEmpty()) {
				if (talentSelectInds.contains("Self")) {
					notificationPermission1.setCandidateSelectSelfInd(true);
				} else {
					notificationPermission1.setCandidateSelectSelfInd(false);
				}
				if (talentSelectInds.contains("Management")) {
					notificationPermission1.setCandidateSelectManagementInd(true);
				} else {
					notificationPermission1.setCandidateSelectManagementInd(false);
				}
				if (talentSelectInds.contains("ReportPerson")) {
					notificationPermission1.setCandidateSelectReportPersonInd(true);
				} else {
					notificationPermission1.setCandidateSelectReportPersonInd(false);
				}

				List<String> talentOnboardInds = notificationPermissionMapper.getTalentOnboard();
				// if (null!=vendorInds && !vendorInds.isEmpty()) {
				if (talentOnboardInds.contains("Self")) {
					notificationPermission1.setCandidateOnboardSelfInd(true);
				} else {
					notificationPermission1.setCandidateOnboardSelfInd(false);
				}
				if (talentOnboardInds.contains("Management")) {
					notificationPermission1.setCandidateOnboardManagementInd(true);
				} else {
					notificationPermission1.setCandidateOnboardManagementInd(false);
				}
				if (talentOnboardInds.contains("ReportPerson")) {
					notificationPermission1.setCandidateOnboardReportPersonInd(true);
				} else {
					notificationPermission1.setCandidateOnboardReportPersonInd(false);
				}

				List<String> talentDropInds = notificationPermissionMapper.getTalentDrop();
				// if (null!=vendorInds && !vendorInds.isEmpty()) {
				if (talentDropInds.contains("Self")) {
					notificationPermission1.setCandidateDropSelfInd(true);
				} else {
					notificationPermission1.setCandidateDropSelfInd(false);
				}
				if (talentDropInds.contains("Management")) {
					notificationPermission1.setCandidateDropManagementInd(true);
				} else {
					notificationPermission1.setCandidateDropManagementInd(false);
				}
				if (talentDropInds.contains("ReportPerson")) {
					notificationPermission1.setCandidateDropReportPersonInd(true);
				} else {
					notificationPermission1.setCandidateDropReportPersonInd(false);
				}

				List<String> taskInds = notificationPermissionMapper.getTask();
				// if (null!=vendorInds && !vendorInds.isEmpty()) {
				if (taskInds.contains("Self")) {
					notificationPermission1.setTaskSelfInd(true);
				} else {
					notificationPermission1.setTaskSelfInd(false);
				}
				if (taskInds.contains("Management")) {
					notificationPermission1.setTaskManagementInd(true);
				} else {
					notificationPermission1.setTaskManagementInd(false);
				}
				if (taskInds.contains("ReportPerson")) {
					notificationPermission1.setTaskReportPersonInd(true);
				} else {
					notificationPermission1.setTaskReportPersonInd(false);
				}

				List<String> eventInds = notificationPermissionMapper.getEvent();
				// if (null!=vendorInds && !vendorInds.isEmpty()) {
				if (eventInds.contains("Self")) {
					notificationPermission1.setEventSelfInd(true);
				} else {
					notificationPermission1.setEventSelfInd(false);
				}
				if (eventInds.contains("Management")) {
					notificationPermission1.setEventManagementInd(true);
				} else {
					notificationPermission1.setEventManagementInd(false);
				}
				if (eventInds.contains("ReportPerson")) {
					notificationPermission1.setEventReportPersonInd(true);
				} else {
					notificationPermission1.setEventReportPersonInd(false);
				}

				List<String> callInds = notificationPermissionMapper.getCall();
				// if (null!=vendorInds && !vendorInds.isEmpty()) {
				if (callInds.contains("Self")) {
					notificationPermission1.setCallSelfInd(true);
				} else {
					notificationPermission1.setCallSelfInd(false);
				}
				if (callInds.contains("Management")) {
					notificationPermission1.setCallManagementInd(true);
				} else {
					notificationPermission1.setCallManagementInd(false);
				}
				if (callInds.contains("ReportPerson")) {
					notificationPermission1.setCallReportPersonInd(true);
				} else {
					notificationPermission1.setCallReportPersonInd(false);
				}

				List<String> publishJobInds = notificationPermissionMapper.getPublishJob();
				// if (null!=vendorInds && !vendorInds.isEmpty()) {
				if (publishJobInds.contains("Self")) {
					notificationPermission1.setPublishJobSelfInd(true);
				} else {
					notificationPermission1.setPublishJobSelfInd(false);
				}
				if (publishJobInds.contains("Management")) {
					notificationPermission1.setPublishJobManagementInd(true);
				} else {
					notificationPermission1.setPublishJobManagementInd(false);
				}
				if (publishJobInds.contains("ReportPerson")) {
					notificationPermission1.setPublishJobReportPersonInd(true);
				} else {
					notificationPermission1.setPublishJobReportPersonInd(false);
				}

				List<String> publishJobOnWebsiteInds = notificationPermissionMapper.getPublishJobOnWebsite();
				// if (null!=vendorInds && !vendorInds.isEmpty()) {
				if (publishJobOnWebsiteInds.contains("Self")) {
					notificationPermission1.setPublishJobOnWebsiteSelfInd(true);
				} else {
					notificationPermission1.setPublishJobOnWebsiteSelfInd(false);
				}
				if (publishJobOnWebsiteInds.contains("Management")) {
					notificationPermission1.setPublishJobOnWebsiteManagementInd(true);
				} else {
					notificationPermission1.setPublishJobOnWebsiteManagementInd(false);
				}
				if (publishJobOnWebsiteInds.contains("ReportPerson")) {
					notificationPermission1.setPublishJobOnWebsiteReportPersonInd(true);
				} else {
					notificationPermission1.setPublishJobOnWebsiteReportPersonInd(false);
				}

				List<String> publishJobOnJobboardInds = notificationPermissionMapper.getPublishJobOnJobboard();
				// if (null!=vendorInds && !vendorInds.isEmpty()) {
				if (publishJobOnJobboardInds.contains("Self")) {
					notificationPermission1.setPublishJobOnJobboardSelfInd(true);
				} else {
					notificationPermission1.setPublishJobOnJobboardSelfInd(false);
				}
				if (publishJobOnJobboardInds.contains("Management")) {
					notificationPermission1.setPublishJobOnJobboardManagementInd(true);
				} else {
					notificationPermission1.setPublishJobOnJobboardManagementInd(false);
				}
				if (publishJobOnJobboardInds.contains("ReportPerson")) {
					notificationPermission1.setPublishJobOnJobboardReportPersonInd(true);
				} else {
					notificationPermission1.setPublishJobOnJobboardReportPersonInd(false);
				}

				List<String> unpublishJobInds = notificationPermissionMapper.getUnpublishJob();
				// if (null!=vendorInds && !vendorInds.isEmpty()) {
				if (unpublishJobInds.contains("Self")) {
					notificationPermission1.setUnpublishJobSelfInd(true);
				} else {
					notificationPermission1.setUnpublishJobSelfInd(false);
				}
				if (unpublishJobInds.contains("Management")) {
					notificationPermission1.setUnpublishJobManagementInd(true);
				} else {
					notificationPermission1.setUnpublishJobManagementInd(false);
				}
				if (unpublishJobInds.contains("ReportPerson")) {
					notificationPermission1.setUnpublishJobReportPersonInd(true);
				} else {
					notificationPermission1.setUnpublishJobReportPersonInd(false);
				}

				// notificationPermission1.setNotificationId(notificationPermissionMapper.getNotificationId());
				notificationPermission1.setUserId(notificationPermissionMapper.getUserId());
				notificationPermission1.setOrgId(notificationPermissionMapper.getOrganizationId());
				notificationPermission1.setLastUpdatedOn(new Date());
				id = notificationPermissionRepository.save(notificationPermission1).getId();
				System.out.println("in if....." + id);
			}

		}

		return id;

	}

	@Override
	public NotificationPermissionMapper getPermisionByUserId(String userId, String loggedInOrgId) {

		NotificationPermissionMapper mapper = new NotificationPermissionMapper();
		if (userId != null) {
			NotificationPermission dep = notificationPermissionRepository.getNotificationPermission(userId,
					loggedInOrgId);
			// System.out.println("data....." + dep.toString());
			if (null != dep) {
				List<String> customerInds = new ArrayList<>();
				List<String> contactInds = new ArrayList<>();
				List<String> opportunityInds = new ArrayList<>();
				List<String> requirementCreateInds = new ArrayList<>();
				List<String> requirementCloseInds = new ArrayList<>();
				List<String> vendorInds = new ArrayList<>();
				List<String> customerLoginInds = new ArrayList<>();
				List<String> vendorLoginInds = new ArrayList<>();
				List<String> talentSelectInds = new ArrayList<>();
				List<String> talentOnboardInds = new ArrayList<>();
				List<String> talentDropInds = new ArrayList<>();
				List<String> taskInds = new ArrayList<>();
				List<String> eventInds = new ArrayList<>();
				List<String> callInds = new ArrayList<>();
				List<String> publishJobInds = new ArrayList<>();
				List<String> publishJobOnWebsiteInds = new ArrayList<>();
				List<String> publishJobOnJobboardInds = new ArrayList<>();
				List<String> unpublishJobInds = new ArrayList<>();

				if (dep.isCustomerSelfInd()) {
					customerInds.add("Self");
				}
				if (dep.isCustomerManagementInd()) {
					customerInds.add("Management");
				}
				if (dep.isCustomerReportPersonInd()) {
					customerInds.add("ReportPerson");
				}

				mapper.setCustomer(customerInds);

				if (dep.isContactSelfInd()) {
					contactInds.add("Self");
				}
				if (dep.isContactManagementInd()) {
					contactInds.add("Management");
				}
				if (dep.isContactReportPersonInd()) {
					contactInds.add("ReportPerson");
				}

				mapper.setContact(contactInds);

				if (dep.isOpportunitySelfInd()) {
					opportunityInds.add("Self");
				}
				if (dep.isOpportunityManagementInd()) {
					opportunityInds.add("Management");
				}
				if (dep.isOpportunityReportPersonInd()) {
					opportunityInds.add("ReportPerson");
				}

				mapper.setOpportunity(opportunityInds);

				if (dep.isRequirementCreateSelfInd()) {
					requirementCreateInds.add("Self");
				}
				if (dep.isRequirementCreateManagementInd()) {
					requirementCreateInds.add("Management");
				}
				if (dep.isRequirementCreateReportPersonInd()) {
					requirementCreateInds.add("ReportPerson");
				}

				mapper.setRequirementCreate(requirementCreateInds);

				if (dep.isRequirementCloseSelfInd()) {
					requirementCloseInds.add("Self");
				}
				if (dep.isRequirementCloseManagementInd()) {
					requirementCloseInds.add("Management");
				}
				if (dep.isRequirementCloseReportPersonInd()) {
					requirementCloseInds.add("ReportPerson");
				}

				mapper.setRequirementClose(requirementCloseInds);

				if (dep.isVendorSelfInd()) {
					vendorInds.add("Self");
				}
				if (dep.isVendorManagementInd()) {
					vendorInds.add("Management");
				}
				if (dep.isVendorReportPersonInd()) {
					vendorInds.add("ReportPerson");
				}

				mapper.setVendor(vendorInds);

				if (dep.isCustomerLoginSelfInd()) {
					customerLoginInds.add("Self");
				}
				if (dep.isCustomerLoginManagementInd()) {
					customerLoginInds.add("Management");
				}
				if (dep.isCustomerLoginReportPersonInd()) {
					customerLoginInds.add("ReportPerson");
				}

				mapper.setCustomerLogin(customerLoginInds);

				if (dep.isVendorLoginSelfInd()) {
					vendorLoginInds.add("Self");
				}
				if (dep.isVendorLoginManagementInd()) {
					vendorLoginInds.add("Management");
				}
				if (dep.isVendorLoginReportPersonInd()) {
					vendorLoginInds.add("ReportPerson");
				}

				mapper.setVendorLogin(vendorLoginInds);

				if (dep.isCandidateSelectSelfInd()) {
					talentSelectInds.add("Self");
				}
				if (dep.isCandidateSelectManagementInd()) {
					talentSelectInds.add("Management");
				}
				if (dep.isCandidateSelectReportPersonInd()) {
					talentSelectInds.add("ReportPerson");
				}

				mapper.setTalentSelect(talentSelectInds);

				if (dep.isCandidateOnboardSelfInd()) {
					talentOnboardInds.add("Self");
				}
				if (dep.isCandidateOnboardManagementInd()) {
					talentOnboardInds.add("Management");
				}
				if (dep.isCandidateOnboardReportPersonInd()) {
					talentOnboardInds.add("ReportPerson");
				}

				mapper.setTalentOnboard(talentOnboardInds);

				if (dep.isCandidateDropSelfInd()) {
					talentDropInds.add("Self");
				}
				if (dep.isCandidateDropManagementInd()) {
					talentDropInds.add("Management");
				}
				if (dep.isCandidateDropReportPersonInd()) {
					talentDropInds.add("ReportPerson");
				}

				mapper.setTalentDrop(talentDropInds);

				if (dep.isTaskSelfInd()) {
					taskInds.add("Self");
				}
				if (dep.isTaskManagementInd()) {
					taskInds.add("Management");
				}
				if (dep.isTaskReportPersonInd()) {
					taskInds.add("ReportPerson");
				}

				mapper.setTask(taskInds);

				if (dep.isEventSelfInd()) {
					eventInds.add("Self");
				}
				if (dep.isEventManagementInd()) {
					eventInds.add("Management");
				}
				if (dep.isEventReportPersonInd()) {
					eventInds.add("ReportPerson");
				}

				mapper.setEvent(eventInds);

				if (dep.isCallSelfInd()) {
					callInds.add("Self");
				}
				if (dep.isCallManagementInd()) {
					callInds.add("Management");
				}
				if (dep.isCallReportPersonInd()) {
					callInds.add("ReportPerson");
				}

				mapper.setCall(callInds);

				if (dep.isPublishJobSelfInd()) {
					publishJobInds.add("Self");
				}
				if (dep.isPublishJobManagementInd()) {
					publishJobInds.add("Management");
				}
				if (dep.isPublishJobReportPersonInd()) {
					publishJobInds.add("ReportPerson");
				}

				mapper.setPublishJob(publishJobInds);

				if (dep.isPublishJobOnWebsiteSelfInd()) {
					publishJobOnWebsiteInds.add("Self");
				}
				if (dep.isPublishJobOnWebsiteManagementInd()) {
					publishJobOnWebsiteInds.add("Management");
				}
				if (dep.isPublishJobOnWebsiteReportPersonInd()) {
					publishJobOnWebsiteInds.add("ReportPerson");
				}

				mapper.setPublishJobOnWebsite(publishJobOnWebsiteInds);

				if (dep.isPublishJobOnJobboardSelfInd()) {
					publishJobOnJobboardInds.add("Self");
				}
				if (dep.isPublishJobOnJobboardManagementInd()) {
					publishJobOnJobboardInds.add("Management");
				}
				if (dep.isPublishJobOnJobboardReportPersonInd()) {
					publishJobOnJobboardInds.add("ReportPerson");
				}

				mapper.setPublishJobOnJobboard(publishJobOnJobboardInds);

				if (dep.isUnpublishJobSelfInd()) {
					unpublishJobInds.add("Self");
				}
				if (dep.isUnpublishJobManagementInd()) {
					unpublishJobInds.add("Management");
				}
				if (dep.isUnpublishJobReportPersonInd()) {
					unpublishJobInds.add("ReportPerson");
				}

				mapper.setUnpublishJob(unpublishJobInds);

				;
				// mapper.setNotificationId(dep.getNotificationId());
				mapper.setUserId(dep.getUserId());
				mapper.setOrganizationId(dep.getOrgId());

				mapper.setLastUpdatedOn(Utility.getISOFromDate(dep.getLastUpdatedOn()));

				EmployeeDetails employeeDetails = employeeRepository.getEmployeeByUserId(dep.getUserId());

				if (employeeDetails != null) {

					String middleName = " ";
					String lastName = "";

					if (!StringUtils.isEmpty(employeeDetails.getLastName())) {

						lastName = employeeDetails.getLastName();
					}

					if (employeeDetails.getMiddleName() != null && employeeDetails.getMiddleName().length() > 0) {

						middleName = employeeDetails.getMiddleName();
						mapper.setName(employeeDetails.getFirstName() + " " + middleName + " " + lastName);
					} else {

						mapper.setName(employeeDetails.getFirstName() + " " + lastName);
					}

				}
			}
		}
		return mapper;
	}

	@Override
	public String saveAssessment(AssessmentMapper assessmentMapper) {
		String id = null;

		AssessmentDetails dbAssessmentDetails = assessmentDetailsRepository.findByOrgId(assessmentMapper.getOrgId());

		if (dbAssessmentDetails != null) {
			dbAssessmentDetails.setAssessmentInd(assessmentMapper.isAssessmentInd());
			dbAssessmentDetails.setOrgId(assessmentMapper.getOrgId());
			dbAssessmentDetails.setUserId(assessmentMapper.getUserId());
			dbAssessmentDetails.setLastUpdatedOn(new Date());

			id = assessmentDetailsRepository.save(dbAssessmentDetails).getId();
		} else {
			AssessmentDetails assessmentDetails = new AssessmentDetails();
			assessmentDetails.setAssessmentInd(assessmentMapper.isAssessmentInd());
			assessmentDetails.setOrgId(assessmentMapper.getOrgId());
			assessmentDetails.setUserId(assessmentMapper.getUserId());
			assessmentDetails.setLastUpdatedOn(new Date());
			id = assessmentDetailsRepository.save(assessmentDetails).getId();
		}

		return id;
	}

	@Override
	public AssessmentMapper getAssessmentByOrgId(String orgId) {
		AssessmentMapper mapper = new AssessmentMapper();
		AssessmentDetails pem = assessmentDetailsRepository.findByOrgId(orgId);

		if (pem != null) {
			mapper.setAssessmentDetailsId(pem.getId());
			mapper.setAssessmentInd(pem.isAssessmentInd());
			mapper.setOrgId(pem.getOrgId());
			mapper.setUserId(pem.getUserId());
			mapper.setLastUpdatedOn(Utility.getISOFromDate(pem.getLastUpdatedOn()));

			EmployeeDetails employeeDetails = employeeRepository.getEmployeeByUserId(pem.getUserId());

			if (employeeDetails != null) {

				String middleName = " ";
				String lastName = "";

				if (!StringUtils.isEmpty(employeeDetails.getLastName())) {

					lastName = employeeDetails.getLastName();
				}

				if (employeeDetails.getMiddleName() != null && employeeDetails.getMiddleName().length() > 0) {

					middleName = employeeDetails.getMiddleName();
					mapper.setName(employeeDetails.getFirstName() + " " + middleName + " " + lastName);
				} else {

					mapper.setName(employeeDetails.getFirstName() + " " + lastName);
				}

			}
		}
		return mapper;
	}

	@Override
	public List<PermissionMapper> getUserListforEvent() {
		List<PermissionMapper> resultMapper = new ArrayList<PermissionMapper>();

		List<Permission> list1 = permissionRepository.getUserListForEvent();
		if (null != list1 && !list1.isEmpty()) {

			for (Permission list2 : list1) {
				EmployeeDetails employeeDetails = employeeRepository.getEmployeeDetailsByEmployeeId(list2.getUserId());

				PermissionMapper obj = new PermissionMapper();
				if (employeeDetails != null) {

					String middleName = " ";
					String lastName = " ";

					if (!StringUtils.isEmpty(employeeDetails.getLastName())) {

						lastName = employeeDetails.getLastName();
					}

					if (employeeDetails.getMiddleName() != null && employeeDetails.getMiddleName().length() > 0) {

						middleName = employeeDetails.getMiddleName();
						obj.setUserName(employeeDetails.getFirstName() + " " + middleName + " " + lastName);
					} else {

						obj.setUserName(employeeDetails.getFirstName() + " " + lastName);
					}

					obj.setUserName(employeeDetails.getFirstName() + " " + employeeDetails.getLastName());
					obj.setUserId(employeeDetails.getUserId());
					resultMapper.add(obj);
				}
			}

		}
		return resultMapper;
	}

	
}