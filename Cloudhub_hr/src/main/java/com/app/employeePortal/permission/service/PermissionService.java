package com.app.employeePortal.permission.service;

import java.util.List;

import com.app.employeePortal.Opportunity.mapper.OpportunityViewMapper;
import com.app.employeePortal.call.mapper.CallViewMapper;
import com.app.employeePortal.candidate.mapper.CandidateViewMapper;
import com.app.employeePortal.contact.mapper.ContactViewMapper;
import com.app.employeePortal.customer.mapper.CustomerResponseMapper;
import com.app.employeePortal.event.mapper.EventViewMapper;
import com.app.employeePortal.partner.mapper.PartnerMapper;
import com.app.employeePortal.permission.mapper.AssessmentMapper;
import com.app.employeePortal.permission.mapper.CommunicationMapper;
import com.app.employeePortal.permission.mapper.ComplianceMapper;
import com.app.employeePortal.permission.mapper.DepartmentPermissionMapper;
import com.app.employeePortal.permission.mapper.NotificationPermissionMapper;
import com.app.employeePortal.permission.mapper.PermissionMapper;
import com.app.employeePortal.permission.mapper.SourcingMapper;
import com.app.employeePortal.permission.mapper.ThirdPartyMapper;
import com.app.employeePortal.task.mapper.TaskViewMapper;

public interface PermissionService {

	String savePermission(PermissionMapper permissionMapper);

	List<CandidateViewMapper> getCandidateList(String userId);

	List<PermissionMapper> getUserList();

	List<PermissionMapper> getUserListForContact();

	List<PermissionMapper> getUserListForCustomer();

	List<PermissionMapper> getUserListForOpportunity();

	List<PermissionMapper> getUserListForPartner();

	List<ContactViewMapper> getContactList(String userId);

	List<CallViewMapper> getCallList(String userId);

	List<EventViewMapper> getEventList(String userId);

	List<TaskViewMapper> getTaskList(String userId);

	List<CustomerResponseMapper> getCustomerListList(String userId);

	List<OpportunityViewMapper> getOpportunityList(String userId);

	List<PartnerMapper> getPartnerList(String userId);

	PermissionMapper getList(String userId);

	List<ContactViewMapper> getPartnerContactsList(String userId);

	List<PermissionMapper> getUserListForPartnerContact();

	List<CandidateViewMapper> getCandidateList();

	List<ContactViewMapper> getContactListForAllUsers(List<String> permissionMapperList);

	List<CustomerResponseMapper> getCustomerListListForAllUsers(List<String> permissionMapperList);

	List<OpportunityViewMapper> getOpportunityListForAllUsers(List<String> permissionMapperList);

	List<PartnerMapper> getPartnerListForAllUsers(List<String> permissionMapperList);

	List<ContactViewMapper> getPartnerContactsListForAllUsers(List<String> permissionMapperList);

	List<CandidateViewMapper> getCandidateListForAllUsers(List<String> permissionMapperList);

	List<PermissionMapper> getUserListforCall();
	
	List<PermissionMapper> getUserListforEvent();

	List<PermissionMapper> getUserListforPlanner();

	List<PermissionMapper> getUserListforTask();

	List<CallViewMapper> getCallListForAllUsers(List<String> permissionMapperList);

	List<EventViewMapper> getEventListForAllUsers(List<String> permissionMapperList);

	List<TaskViewMapper> getTaskListForAllUsers(List<String> permissionMapperList);

	boolean candidateShareTrueByOrgId(String orgId);

	String saveCandidateSharePermission(PermissionMapper permissionMapper);

	PermissionMapper getAdminCandidateShareByOrgId(String orgId);

	String saveEngagementAccess(ThirdPartyMapper thirdPartyMapper);

	ThirdPartyMapper getEngagementAccessByOrgId(String orgId);

	String saveDepartmentPermission(DepartmentPermissionMapper departmentPermissionMapper);

	DepartmentPermissionMapper getPermisionByDepartmentId(String roleTypeId, String loggedInOrgId);

	String savecompliance(ComplianceMapper complianceMapper);

	ComplianceMapper getComplianceAccessByOrgId(String orgId);

	String saveCommunicationAccess(CommunicationMapper communicationMapper);

	CommunicationMapper getCommunicationAccessByOrgId(String orgId);

	String saveSourcingAccess(SourcingMapper sourcingMapper);

	SourcingMapper getSourcingAccessByOrgId(String orgId);

	String saveNotificationPermission(NotificationPermissionMapper notificationPermissionMapper);

	NotificationPermissionMapper getPermisionByUserId(String userId, String loggedInOrgId);

	String saveAssessment(AssessmentMapper assessmentMapper);

	AssessmentMapper getAssessmentByOrgId(String orgId);

	List<PartnerMapper> getPartnerList(String userId, int pageNo, int pageSize);

}
