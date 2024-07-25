package com.app.employeePortal.permission.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@Table(name = "notification_permission")
@ToString

public class NotificationPermission {

	@Id
	@GenericGenerator(name = "notification_permission_id", strategy = "com.app.employeePortal.permission.generator.NotificationPermissionGenerator")
	@GeneratedValue(generator = "notification_permission_id")

	@Column(name = "notification_permission_id")
	private String id;

	@Column(name = "notification_id")
	private String notificationId;

	@Column(name = "user_id")
	private String userId;

	@Column(name = "org_id")
	private String orgId;

	@Column(name = "customer_self_Ind")
	private boolean customerSelfInd;

	@Column(name = "customer_management_Ind")
	private boolean customerManagementInd;

	@Column(name = "customer_report_person_Ind")
	private boolean customerReportPersonInd;

	@Column(name = "contact_self_Ind")
	private boolean contactSelfInd;

	@Column(name = "contact_management_Ind")
	private boolean contactManagementInd;

	@Column(name = "contact_report_person_Ind")
	private boolean contactReportPersonInd;

	@Column(name = "opportunity_self_Ind")
	private boolean opportunitySelfInd;

	@Column(name = "opportunity_management_Ind")
	private boolean opportunityManagementInd;

	@Column(name = "opportunity_report_person_Ind")
	private boolean opportunityReportPersonInd;

	@Column(name = "requirement_create_self_Ind")
	private boolean requirementCreateSelfInd;

	@Column(name = "requirement_create_management_Ind")
	private boolean requirementCreateManagementInd;

	@Column(name = "requirement_create_report_person_Ind")
	private boolean requirementCreateReportPersonInd;

	@Column(name = "requirement_close_self_Ind")
	private boolean requirementCloseSelfInd;

	@Column(name = "requirement_close_management_Ind")
	private boolean requirementCloseManagementInd;

	@Column(name = "requirement_close_report_person_Ind")
	private boolean requirementCloseReportPersonInd;

	@Column(name = "vendor_self_Ind")
	private boolean vendorSelfInd;

	@Column(name = "vendor_management_Ind")
	private boolean vendorManagementInd;

	@Column(name = "vendor_report_person_Ind")
	private boolean vendorReportPersonInd;

	@Column(name = "customer_login_self_Ind")
	private boolean customerLoginSelfInd;

	@Column(name = "customer_login_management_Ind")
	private boolean customerLoginManagementInd;

	@Column(name = "customer_login_report_person_Ind")
	private boolean customerLoginReportPersonInd;

	@Column(name = "vendor_login_self_Ind")
	private boolean vendorLoginSelfInd;

	@Column(name = "vendor_login_management_Ind")
	private boolean vendorLoginManagementInd;

	@Column(name = "vendor_login_report_person_Ind")
	private boolean vendorLoginReportPersonInd;

	@Column(name = "candidate_select_self_Ind")
	private boolean candidateSelectSelfInd;

	@Column(name = "candidate_select_management_Ind")
	private boolean candidateSelectManagementInd;

	@Column(name = "candidate_select_report_person_Ind")
	private boolean candidateSelectReportPersonInd;

	@Column(name = "candidate_onboard_self_Ind")
	private boolean candidateOnboardSelfInd;

	@Column(name = "candidate_onboard_management_Ind")
	private boolean candidateOnboardManagementInd;

	@Column(name = "candidate_onboard_report_person_Ind")
	private boolean candidateOnboardReportPersonInd;

	@Column(name = "candidate_drop_self_Ind")
	private boolean candidateDropSelfInd;

	@Column(name = "candidate_drop_management_Ind")
	private boolean candidateDropManagementInd;

	@Column(name = "candidate_drop_report_person_Ind")
	private boolean candidateDropReportPersonInd;

	@Column(name = "task_self_Ind")
	private boolean taskSelfInd;

	@Column(name = "task_management_Ind")
	private boolean taskManagementInd;

	@Column(name = "task_report_person_Ind")
	private boolean taskReportPersonInd;

	@Column(name = "event_self_Ind")
	private boolean eventSelfInd;

	@Column(name = "event_management_Ind")
	private boolean eventManagementInd;

	@Column(name = "event_report_person_Ind")
	private boolean eventReportPersonInd;

	@Column(name = "call_self_Ind")
	private boolean callSelfInd;

	@Column(name = "call_management_Ind")
	private boolean callManagementInd;

	@Column(name = "call_report_person_Ind")
	private boolean callReportPersonInd;

	@Column(name = "publish_job_self_Ind")
	private boolean publishJobSelfInd;

	@Column(name = "publish_job_management_Ind")
	private boolean publishJobManagementInd;

	@Column(name = "publish_job_report_person_Ind")
	private boolean publishJobReportPersonInd;

	@Column(name = "publish_job_on_website_self_Ind")
	private boolean publishJobOnWebsiteSelfInd;

	@Column(name = "publish_job_on_website_management_Ind")
	private boolean publishJobOnWebsiteManagementInd;

	@Column(name = "publish_job_on_website_report_person_Ind")
	private boolean publishJobOnWebsiteReportPersonInd;

	@Column(name = "publish_job_on_jobboard_self_Ind")
	private boolean publishJobOnJobboardSelfInd;

	@Column(name = "publish_job_on_jobboard_management_Ind")
	private boolean publishJobOnJobboardManagementInd;

	@Column(name = "publish_job_on_jobboard_report_person_Ind")
	private boolean publishJobOnJobboardReportPersonInd;

	@Column(name = "unpublish_job_self_Ind")
	private boolean unpublishJobSelfInd;

	@Column(name = "unpublish_job_management_Ind")
	private boolean unpublishJobManagementInd;

	@Column(name = "unpublish_job_report_person_Ind")
	private boolean unpublishJobReportPersonInd;
	
	@Column(name="last_updated_on")
	private Date lastUpdatedOn;
	

}
