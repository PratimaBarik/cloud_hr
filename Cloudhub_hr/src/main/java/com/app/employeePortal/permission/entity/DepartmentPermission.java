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
@Table(name = "department_permission")
@ToString
public class DepartmentPermission {

	@Id
	@GenericGenerator(name = "department_permission_id", strategy = "com.app.employeePortal.permission.generator.DepartmentPermissionGenerator")
	@GeneratedValue(generator = "department_permission_id")

	@Column(name = "department_permission_id")
	private String id;

	@Column(name = "department_id")
	private String departmentId;
	
	@Column(name="role_type_id")
	private String roleTypeId;

	@Column(name = "user_id")
	private String userId;

	@Column(name = "vendor_access_Ind")
	private boolean vendorAccessInd;

	@Column(name = "vendor_create_Ind")
	private boolean vendorCreateInd;

	@Column(name = "vendor_update_Ind")
	private boolean vendorUpdateInd;

	@Column(name = "vendor_delete_Ind")
	private boolean vendorDeleteInd;
	
	@Column(name = "vendor_full_list_Ind", nullable = false)
	private boolean vendorFullListInd=false;
	
	@Column(name = "customer_access_Ind")
	private boolean customerAccessInd;

	@Column(name = "customer_create_Ind")
	private boolean customerCreateInd;

	@Column(name = "customer_update_Ind")
	private boolean customerUpdateInd;

	@Column(name = "customer_delete_Ind")
	private boolean customerDeleteInd;
	
	@Column(name = "customer_full_list_Ind", nullable = false)
	private boolean customerFullListInd=false;

	@Column(name = "opportunity_access_Ind")
	private boolean opportunityAccessInd;

	@Column(name = "opportunity_create_Ind")
	private boolean opportunityCreateInd;

	@Column(name = "opportunity_update_Ind")
	private boolean opportunityUpdateInd;

	@Column(name = "opportunity_delete_Ind")
	private boolean opportunityDeleteInd;
	
	@Column(name = "opportunity_full_list_Ind", nullable = false)
	private boolean opportunityFullListInd=false;

	@Column(name = "talent_access_Ind")
	private boolean talentAccessInd;

	@Column(name = "talent_create_Ind")
	private boolean talentCreateInd;

	@Column(name = "talent_update_Ind")
	private boolean talentUpdateInd;

	@Column(name = "talent_delete_Ind")
	private boolean talentDeleteInd;
	
	@Column(name = "talent_full_list_Ind", nullable = false)
	private boolean talentFullListInd=false;

	@Column(name = "org_id")
	private String orgId;

	@Column(name = "requirement_access_Ind")
	private boolean requirementAccessInd;

	@Column(name = "requirement_create_Ind")
	private boolean requirementCreateInd;

	@Column(name = "requirement_update_Ind")
	private boolean requirementUpdateInd;

	@Column(name = "requirement_delete_Ind")
	private boolean requirementDeleteInd;
	
	@Column(name = "requirement_full_list_Ind", nullable = false)
	private boolean requirementFullListInd=false;

	@Column(name = "publish_access_Ind")
	private boolean publishAccessInd;

	@Column(name = "publish_create_Ind")
	private boolean publishCreateInd;

	@Column(name = "publish_update_Ind")
	private boolean publishUpdateInd;

	@Column(name = "publish_delete_Ind")
	private boolean publishDeleteInd;
	
	@Column(name = "publish_full_list_Ind", nullable = false)
	private boolean publishFullListInd=false;

	@Column(name = "pulse_access_Ind")
	private boolean pulseAccessInd;

	@Column(name = "pulse_create_Ind")
	private boolean pulseCreateInd;

	@Column(name = "pulse_update_Ind")
	private boolean pulseUpdateInd;

	@Column(name = "pulse_delete_Ind")
	private boolean pulseDeleteInd;
	
	@Column(name = "pulse_full_list_Ind", nullable = false)
	private boolean pulseFullListInd=false;

	@Column(name = "contact_access_Ind")
	private boolean contactAccessInd;

	@Column(name = "contact_create_Ind")
	private boolean contactCreateInd;

	@Column(name = "contact_update_Ind")
	private boolean contactUpdateInd;

	@Column(name = "contact_delete_Ind")
	private boolean contactDeleteInd;
	
	@Column(name = "contact_full_list_Ind", nullable = false)
	private boolean contactFullListInd=false;

	@Column(name = "last_updated_on")
	private Date lastUpdatedOn;

	@Column(name = "assessment_access_Ind")
	private boolean assessmentAccessInd;

	@Column(name = "assessment_create_Ind")
	private boolean assessmentCreateInd;

	@Column(name = "assessment_update_Ind")
	private boolean assessmentUpdateInd;

	@Column(name = "assessment_delete_Ind")
	private boolean assessmentDeleteInd;
	
	@Column(name = "assessment_full_list_Ind", nullable = false)
	private boolean assessmentFullListInd=false;
	
	@Column(name = "leads_access_Ind", nullable = false)
	private boolean leadsAccessInd =false;

	@Column(name = "leads_create_Ind", nullable = false)
	private boolean leadsCreateInd =false;

	@Column(name = "leads_update_Ind", nullable = false)
	private boolean leadsUpdateInd =false;

	@Column(name = "leads_delete_Ind", nullable = false)
	private boolean leadsDeleteInd =false;
	
	@Column(name = "leads_full_list_Ind", nullable = false)
	private boolean leadsFullListInd=false;
	
	
	@Column(name = "test_access_Ind", nullable = false)
	private boolean testAccessInd =false;

	@Column(name = "test_create_Ind", nullable = false)
	private boolean testCreateInd =false;

	@Column(name = "test_update_Ind", nullable = false)
	private boolean testUpdateInd =false;

	@Column(name = "test_delete_Ind", nullable = false)
	private boolean testDeleteInd =false;
	
	@Column(name = "test_full_list_Ind", nullable = false)
	private boolean testFullListInd=false;
	
	@Column(name = "program_access_Ind", nullable = false)
	private boolean programAccessInd =false;

	@Column(name = "program_create_Ind", nullable = false)
	private boolean programCreateInd =false;

	@Column(name = "program_update_Ind", nullable = false)
	private boolean programUpdateInd =false;

	@Column(name = "program_delete_Ind", nullable = false)
	private boolean programDeleteInd =false;
	
	@Column(name = "program_full_list_Ind", nullable = false)
	private boolean programFullListInd=false;
	
	@Column(name = "course_access_Ind", nullable = false)
	private boolean courseAccessInd =false;

	@Column(name = "course_create_Ind", nullable = false)
	private boolean courseCreateInd =false;

	@Column(name = "course_update_Ind", nullable = false)
	private boolean courseUpdateInd =false;

	@Column(name = "course_delete_Ind", nullable = false)
	private boolean courseDeleteInd =false;
	
	@Column(name = "course_full_list_Ind", nullable = false)
	private boolean courseFullListInd=false;
	
	@Column(name = "hours_access_Ind", nullable = false)
	private boolean hoursAccessInd =false;

	@Column(name = "hours_create_Ind", nullable = false)
	private boolean hoursCreateInd =false;

	@Column(name = "hours_update_Ind", nullable = false)
	private boolean hoursUpdateInd =false;

	@Column(name = "hours_delete_Ind", nullable = false)
	private boolean hoursDeleteInd =false;
	
	@Column(name = "hours_full_list_Ind", nullable = false)
	private boolean hoursFullListInd=false;
	
//	@Column(name = "task_access_Ind", nullable = false)
//	private boolean taskAccessInd =false;
//
//	@Column(name = "task_create_Ind", nullable = false)
//	private boolean taskCreateInd =false;
//
//	@Column(name = "task_update_Ind", nullable = false)
//	private boolean taskUpdateInd =false;
//
//	@Column(name = "task_delete_Ind", nullable = false)
//	private boolean taskDeleteInd =false;
	
	@Column(name = "task_full_list_Ind", nullable = false)
	private boolean taskFullListInd=false;
	
	@Column(name = "comercial_access_Ind", nullable = false)
	private boolean comercialAccessInd =false;

	@Column(name = "comercial_create_Ind", nullable = false)
	private boolean comercialCreateInd =false;

	@Column(name = "comercial_update_Ind", nullable = false)
	private boolean comercialUpdateInd =false;

	@Column(name = "comercial_delete_Ind", nullable = false)
	private boolean comercialDeleteInd =false;
	
	@Column(name = "comercial_full_list_Ind", nullable = false)
	private boolean comercialFullListInd=false;
	
	@Column(name = "location_access_Ind", nullable = false)
	private boolean locationAccessInd =false;

	@Column(name = "location_create_Ind", nullable = false)
	private boolean locationCreateInd =false;

	@Column(name = "location_update_Ind", nullable = false)
	private boolean locationUpdateInd =false;

	@Column(name = "location_delete_Ind", nullable = false)
	private boolean locationDeleteInd =false;
	
	@Column(name = "location_full_list_Ind", nullable = false)
	private boolean locationFullListInd=false;
	
	@Column(name = "expense_full_list_Ind", nullable = false)
	private boolean expenseFullListInd=false;
	
	@Column(name = "mileage_full_list_Ind", nullable = false)
	private boolean mileageFullListInd=false;
	
	@Column(name = "leave_full_list_Ind", nullable = false)
	private boolean leaveFullListInd=false;
	
	@Column(name = "user_access_Ind", nullable = false)
	private boolean userAccessInd =false;

	@Column(name = "user_create_Ind", nullable = false)
	private boolean userCreateInd =false;

	@Column(name = "user_update_Ind", nullable = false)
	private boolean userUpdateInd =false;

	@Column(name = "user_delete_Ind", nullable = false)
	private boolean userDeleteInd =false;
	
	@Column(name = "user_access_plus_Ind", nullable = false)
	private boolean userAccessPlusInd=false;
	
	@Column(name = "account_access_Ind", nullable = false)
	private boolean accountAccessInd =false;

	@Column(name = "account_create_Ind", nullable = false)
	private boolean accountCreateInd =false;

	@Column(name = "account_update_Ind", nullable = false)
	private boolean accountUpdateInd =false;

	@Column(name = "account_delete_Ind", nullable = false)
	private boolean accountDeleteInd =false;
	
	@Column(name = "account_full_list_Ind", nullable = false)
	private boolean accountFullListInd=false;
	
	@Column(name = "account_Info_Ind", nullable = false)
	private boolean accountInfoInd=false;
	
	@Column(name = "order_access_Ind", nullable = false)
	private boolean orderAccessInd =false;

	@Column(name = "order_create_Ind", nullable = false)
	private boolean orderCreateInd =false;

	@Column(name = "order_update_Ind", nullable = false)
	private boolean orderUpdateInd =false;

	@Column(name = "order_delete_Ind", nullable = false)
	private boolean orderDeleteInd =false;
	
	@Column(name = "order_full_list_Ind", nullable = false)
	private boolean orderFullListInd=false;
	
	@Column(name = "material_access_Ind", nullable = false)
	private boolean materialAccessInd =false;

	@Column(name = "material_create_Ind", nullable = false)
	private boolean materialCreateInd =false;

	@Column(name = "material_update_Ind", nullable = false)
	private boolean materialUpdateInd =false;

	@Column(name = "material_delete_Ind", nullable = false)
	private boolean materialDeleteInd =false;
	
	@Column(name = "supplier_access_Ind", nullable = false)
	private boolean supplierAccessInd =false;

	@Column(name = "supplier_create_Ind", nullable = false)
	private boolean supplierCreateInd =false;

	@Column(name = "supplier_update_Ind", nullable = false)
	private boolean supplierUpdateInd =false;

	@Column(name = "supplier_delete_Ind", nullable = false)
	private boolean supplierDeleteInd =false;
	
	@Column(name = "supplier_full_list_Ind", nullable = false)
	private boolean supplierFullListInd=false;
	
	@Column(name = "inventory_access_Ind", nullable = false)
	private boolean inventoryAccessInd =false;

	@Column(name = "inventory_create_Ind", nullable = false)
	private boolean inventoryCreateInd =false;

	@Column(name = "inventory_update_Ind", nullable = false)
	private boolean inventoryUpdateInd =false;

	@Column(name = "inventory_delete_Ind", nullable = false)
	private boolean inventoryDeleteInd =false;
	
	@Column(name = "inventory_full_list_Ind", nullable = false)
	private boolean inventoryFullListInd=false;
	
	@Column(name = "refurbish_workshop_Ind", nullable = false)
	private boolean refurbishWorkshopInd =false;

	@Column(name = "refurbish_adminview_Ind", nullable = false)
	private boolean refurbishAdminviewInd =false;

	@Column(name = "refurbish_adminassign_Ind", nullable = false)
	private boolean refurbishAdminAssignInd =false;
	
	@Column(name = "dashboard_access_Ind", nullable = false)
	private boolean dashboardAccessInd=false;
	
	@Column(name = "dashboard_full_list_Ind", nullable = false)
	private boolean dashboardFullListInd=false;
	
	@Column(name = "dashboard_regional_Ind", nullable = false)
	private boolean dashboardRegionalInd=false;
	
	@Column(name = "settings_access_Ind", nullable = false)
	private boolean settingsAccessInd=false;
	
	@Column(name = "junk_access_Ind", nullable = false)
	private boolean junkAccessInd =false;

	@Column(name = "junk_transfer_Ind", nullable = false)
	private boolean junkTransferInd =false;
	
	@Column(name = "investor_access_Ind", nullable = false)
	private boolean investorAccessInd=false;

	@Column(name = "investor_create_Ind", nullable = false)
	private boolean investorCreateInd=false;

	@Column(name = "investor_update_Ind", nullable = false)
	private boolean investorUpdateInd=false;

	@Column(name = "investor_delete_Ind", nullable = false)
	private boolean investorDeleteInd=false;
	
	@Column(name = "investor_full_list_Ind", nullable = false)
	private boolean investorFullListInd=false;

	@Column(name = "investor_contact_access_Ind", nullable = false)
	private boolean investorContactAccessInd=false;

	@Column(name = "investor_contact_create_Ind", nullable = false)
	private boolean investorContactCreateInd=false;

	@Column(name = "investor_contact_update_Ind", nullable = false)
	private boolean investorContactUpdateInd=false;

	@Column(name = "investor_contact_delete_Ind", nullable = false)
	private boolean investorContactDeleteInd=false;
	
	@Column(name = "investor_contact_full_list_Ind", nullable = false)
	private boolean investorContactFullListInd=false;
	
	@Column(name = "deal_access_Ind", nullable = false)
	private boolean dealAccessInd=false;

	@Column(name = "deal_create_Ind", nullable = false)
	private boolean dealCreateInd=false;

	@Column(name = "deal_update_Ind", nullable = false)
	private boolean dealUpdateInd=false;

	@Column(name = "deal_delete_Ind", nullable = false)
	private boolean dealDeleteInd=false;
	
	@Column(name = "deal_full_list_Ind", nullable = false)
	private boolean dealFullListInd=false;

	@Column(name = "pitch_access_Ind", nullable = false)
	private boolean pitchAccessInd=false;

	@Column(name = "pitch_create_Ind", nullable = false)
	private boolean pitchCreateInd=false;

	@Column(name = "pitch_update_Ind", nullable = false)
	private boolean pitchUpdateInd=false;

	@Column(name = "pitch_delete_Ind", nullable = false)
	private boolean pitchDeleteInd=false;
	
	@Column(name = "pitch_full_list_Ind", nullable = false)
	private boolean pitchFullListInd=false;
	
	@Column(name = "repository_create_Ind", nullable = false)
	private boolean repositoryCreateInd=false;
	
	@Column(name = "shipper_access_Ind", nullable = false)
	private boolean shipperAccessInd=false;

	@Column(name = "shipper_create_Ind", nullable = false)
	private boolean shipperCreateInd=false;

	@Column(name = "shipper_update_Ind", nullable = false)
	private boolean shipperUpdateInd=false;

	@Column(name = "shipper_delete_Ind", nullable = false)
	private boolean shipperDeleteInd=false;
	
	@Column(name = "shipper_full_list_Ind", nullable = false)
	private boolean shipperFullListInd=false;
	
	@Column(name = "plant_access_Ind", nullable = false)
	private boolean plantAccessInd=false;

	@Column(name = "plant_create_Ind", nullable = false)
	private boolean plantCreateInd=false;

	@Column(name = "plant_update_Ind", nullable = false)
	private boolean plantUpdateInd=false;

	@Column(name = "plant_delete_Ind", nullable = false)
	private boolean plantDeleteInd=false;
	
	@Column(name = "plant_full_list_Ind", nullable = false)
	private boolean plantFullListInd=false;
	
	@Column(name = "teams_access_Ind", nullable = false)
	private boolean teamsAccessInd=false;

	@Column(name = "teams_create_Ind", nullable = false)
	private boolean teamsCreateInd=false;

	@Column(name = "teams_update_Ind", nullable = false)
	private boolean teamsUpdateInd=false;

	@Column(name = "teams_delete_Ind", nullable = false)
	private boolean teamsDeleteInd=false;
	
	@Column(name = "teams_full_list_Ind", nullable = false)
	private boolean teamsFullListInd=false;
	
	@Column(name = "basic_access_Ind", nullable = false)
	private boolean basicAccessInd=false;
	
	@Column(name = "catalog_access_Ind", nullable = false)
	private boolean catalogAccessInd=false;

	@Column(name = "catalog_create_Ind", nullable = false)
	private boolean catalogCreateInd=false;

	@Column(name = "catalog_update_Ind", nullable = false)
	private boolean catalogUpdateInd=false;

	@Column(name = "catalog_delete_Ind", nullable = false)
	private boolean catalogDeleteInd=false;
	
	@Column(name = "payment_access_Ind", nullable = false)
	private boolean paymentAccessInd=false;
	
	@Column(name = "collection_access_Ind", nullable = false)
	private boolean collectionAccessInd=false;
	
	@Column(name = "collection_approve_Ind", nullable = false)
	private boolean collectionApproveInd=false;
	
	@Column(name = "expense_access_Ind", nullable = false)
	private boolean expenseAccessInd=false;
	
	@Column(name = "mileage_access_Ind", nullable = false)
	private boolean mileageAccessInd=false;
	
	@Column(name = "leave_access_Ind", nullable = false)
	private boolean leaveAccessInd=false;
	
	@Column(name = "holiday_access_Ind", nullable = false)
	private boolean holidayAccessInd=false;
	
	@Column(name = "topic_access_Ind", nullable = false)
	private boolean topicAccessInd=false;

	@Column(name = "topic_create_Ind", nullable = false)
	private boolean topicCreateInd=false;

	@Column(name = "topic_update_Ind", nullable = false)
	private boolean topicUpdateInd=false;

	@Column(name = "topic_delete_Ind", nullable = false)
	private boolean topicDeleteInd=false;
	
	@Column(name = "topic_full_list_Ind", nullable = false)
	private boolean topicFullListInd=false;
	
	@Column(name = "procurement_access_Ind", nullable = false)
	private boolean procurementAccessInd=false;

	@Column(name = "procurement_create_Ind", nullable = false)
	private boolean procurementCreateInd=false;

	@Column(name = "procurement_update_Ind", nullable = false)
	private boolean procurementUpdateInd=false;

	@Column(name = "procurement_delete_Ind", nullable = false)
	private boolean procurementDeleteInd=false;
	
	@Column(name = "procurement_full_list_Ind", nullable = false)
	private boolean procurementFullListInd=false;
	
	@Column(name = "subscription_access_Ind", nullable = false)
	private boolean subscriptionAccessInd=false;

	@Column(name = "subscription_create_Ind", nullable = false)
	private boolean subscriptionCreateInd=false;

	@Column(name = "subscription_update_Ind", nullable = false)
	private boolean subscriptionUpdateInd=false;

	@Column(name = "subscription_delete_Ind", nullable = false)
	private boolean subscriptionDeleteInd=false;
	
	@Column(name = "production_access_Ind", nullable = false)
	private boolean productionAccessInd=false;

	@Column(name = "production_create_Ind", nullable = false)
	private boolean productionCreateInd=false;

	@Column(name = "production_update_Ind", nullable = false)
	private boolean productionUpdateInd=false;

	@Column(name = "production_delete_Ind", nullable = false)
	private boolean productionDeleteInd=false;
	
	@Column(name = "report_full_list_Ind", nullable = false)
	private boolean reportFullListInd=false;
	
	@Column(name = "data_room_access_Ind", nullable = false)
	private boolean dataRoomAccessInd=false;

	@Column(name = "data_room_create_Ind", nullable = false)
	private boolean dataRoomCreateInd=false;

	@Column(name = "data_room_update_Ind", nullable = false)
	private boolean dataRoomUpdateInd=false;

	@Column(name = "data_room_delete_Ind", nullable = false)
	private boolean dataRoomDeleteInd=false;
	
	@Column(name = "data_room_full_list_Ind", nullable = false)
	private boolean dataRoomFullListInd=false;
	
	@Column(name = "scanner_access_Ind", nullable = false)
	private boolean scannerAccessInd=false;
	
	@Column(name = "quality_access_Ind", nullable = false)
	private boolean qualityAccessInd=false;

	@Column(name = "quality_create_Ind", nullable = false)
	private boolean qualityCreateInd=false;

	@Column(name = "quality_update_Ind", nullable = false)
	private boolean qualityUpdateInd=false;

	@Column(name = "quality_delete_Ind", nullable = false)
	private boolean qualityDeleteInd=false;
	
	@Column(name = "quality_full_list_Ind", nullable = false)
	private boolean qualityFullListInd=false;
	
	@Column(name = "club_access_Ind", nullable = false)
	private boolean clubAccessInd=false;

	@Column(name = "club_create_Ind", nullable = false)
	private boolean clubCreateInd=false;

	@Column(name = "club_update_Ind", nullable = false)
	private boolean clubUpdateInd=false;

	@Column(name = "club_delete_Ind", nullable = false)
	private boolean clubDeleteInd=false;
	
	@Column(name = "club_full_list_Ind", nullable = false)
	private boolean clubFullListInd=false;
	
	@Column(name = "supplier_block_Ind", nullable = false)
	private boolean supplierBlockInd =false;
	
	@Column(name = "supplier_inventory_Ind", nullable = false)
	private boolean supplierInventoryInd =false;
	
	@Column(name = "calender_view_Ind", nullable = false)
	private boolean calenderViewInd =false;
	
	@Column(name = "calender_manage_Ind", nullable = false)
	private boolean calenderManageInd =false;
}
