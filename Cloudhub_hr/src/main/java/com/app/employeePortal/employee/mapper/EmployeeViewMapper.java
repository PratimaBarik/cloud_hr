package com.app.employeePortal.employee.mapper;

import java.util.List;
import java.util.Map;

import com.app.employeePortal.address.mapper.AddressMapper;
import com.app.employeePortal.category.mapper.ModuleDepartmentResponseMapper;
import com.app.employeePortal.organization.mapper.FiscalMapper;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeViewMapper {

	@JsonProperty("employeeId")
	private String employeeId;

	@JsonProperty("creatorId")
	private String creatorId;

	@JsonProperty("organizationId")
	private String organizationId;

	@JsonProperty("userId")
	private String userId;

	@JsonProperty("creationDate")
	private String creationDate;

	@JsonProperty("salutation")
	private String salutation;

	@JsonProperty("firstName")
	private String firstName;

	@JsonProperty("middleName")
	private String middleName;

	@JsonProperty("lastName")
	private String lastName;

	@JsonProperty("fullName")
	private String fullName;

	@JsonProperty("dob")
	private String dob;

	@JsonProperty("dateOfJoining")
	private String dateOfJoining;

	@JsonProperty("status")
	private String status;

	@JsonProperty("emailValidationInd")
	private boolean emailValidationInd;

	@JsonProperty("userType")
	private String userType;

	@JsonProperty("gender")
	private String gender;

	@JsonProperty("phoneNo")
	private String phoneNo;

	@JsonProperty("mobileNo")
	private String mobileNo;

	@JsonProperty("preferedLanguage")
	private String preferedLanguage;

	@JsonProperty("emailId")
	private String emailId;

	@JsonProperty("secondaryEmailId")
	private String secondaryEmailId;

	@JsonProperty("linkedinPublicUrl")
	private String linkedinPublicUrl;

	@JsonProperty("skypeId")
	private String skypeId;

	@JsonProperty("fax")
	private String fax;

	@JsonProperty("currency")
	private String currency;

	@JsonProperty("imageId")
	private String imageId;

	@JsonProperty("timeZone")
	private String timeZone;

	@JsonProperty("designation")
	private String designation;

	@JsonProperty("role")
	private String role;

	@JsonProperty("reportsTo")
	private String reportsTo;

	@JsonProperty("country")
	private String country;

	@JsonProperty("twitter")
	private String twitter;

	@JsonProperty("facebook")
	private String facebook;

	@JsonProperty("department")
	private String department;

	@JsonProperty("metaData")
	private Map metaData;

	@JsonProperty("countryDialCode")
	private String countryDialCode;

	@JsonProperty("countryDialCode1")
	private String countryDialCode1;

	@JsonProperty("teamLeadInd")
	private boolean teamLeadInd;

//	@JsonProperty("address")
//	private List<AddressMapper> address;

	@JsonProperty("label")
	private String label;

	@JsonProperty("workplace")
	private String workplace;

	@JsonProperty("job_type")
	private String jobType;

	@JsonProperty("employee_type")
	private String employeeType;

	@JsonProperty("bloodGroup")
	private String bloodGroup;

	@JsonProperty("reportingManager")
	private String reportingManager;

	@JsonProperty("fiscalMapper")
	private FiscalMapper fiscalMapper;

	@JsonProperty("skill")
	private List<KeyskillsMapper> skill;

	@JsonProperty("designationTypeId")
	private String designationTypeId;

	@JsonProperty("designationType")
	private String designationType;

	@JsonProperty("departmentId")
	private String departmentId;

	@JsonProperty("departmentName")
	private String departmentName;

	@JsonProperty("suspendInd")
	private boolean suspendInd;

	@JsonProperty("talentWebInd")
	private boolean talentWebInd;

	@JsonProperty("orgImageId")
	private String orgImageId;

	@JsonProperty("vendorAccessInd")
	private boolean vendorAccessInd;

	@JsonProperty("vendorCreateInd")
	private boolean vendorCreateInd;

	@JsonProperty("vendorUpdateInd")
	private boolean vendorUpdateInd;

	@JsonProperty("vendorDeleteInd")
	private boolean vendorDeleteInd;

	@JsonProperty("customerAccessInd")
	private boolean customerAccessInd;

	@JsonProperty("customerCreateInd")
	private boolean customerCreateInd;

	@JsonProperty("customerUpdateInd")
	private boolean customerUpdateInd;

	@JsonProperty("customerDeleteInd")
	private boolean customerDeleteInd;

	@JsonProperty("opportunityAccessInd")
	private boolean opportunityAccessInd;

	@JsonProperty("opportunityCreateInd")
	private boolean opportunityCreateInd;

	@JsonProperty("opportunityUpdateInd")
	private boolean opportunityUpdateInd;

	@JsonProperty("opportunityDeleteInd")
	private boolean opportunityDeleteInd;

	@JsonProperty("talentAccessInd")
	private boolean talentAccessInd;

	@JsonProperty("talentCreateInd")
	private boolean talentCreateInd;

	@JsonProperty("talentUpdateInd")
	private boolean talentUpdateInd;

	@JsonProperty("talentDeleteInd")
	private boolean talentDeleteInd;

	@JsonProperty("publishAccessInd")
	private boolean publishAccessInd;

	@JsonProperty("publishCreateInd")
	private boolean publishCreateInd;

	@JsonProperty("publishUpdateInd")
	private boolean publishUpdateInd;

	@JsonProperty("publishDeleteInd")
	private boolean publishDeleteInd;

	@JsonProperty("contactAccessInd")
	private boolean contactAccessInd;

	@JsonProperty("contactCreateInd")
	private boolean contactCreateInd;

	@JsonProperty("contactUpdateInd")
	private boolean contactUpdateInd;

	@JsonProperty("contactDeleteInd")
	private boolean contactDeleteInd;

	@JsonProperty("requirementAccessInd")
	private boolean requirementAccessInd;

	@JsonProperty("requirementCreateInd")
	private boolean requirementCreateInd;

	@JsonProperty("requirementUpdateInd")
	private boolean requirementUpdateInd;

	@JsonProperty("requirementDeleteInd")
	private boolean requirementDeleteInd;

	@JsonProperty("pulseAccessInd")
	private boolean pulseAccessInd;

	@JsonProperty("pulseCreateInd")
	private boolean pulseCreateInd;

	@JsonProperty("pulseUpdateInd")
	private boolean pulseUpdateInd;

	@JsonProperty("pulseDeleteInd")
	private boolean pulseDeleteInd;

	@JsonProperty("assessmentAccessInd")
	private boolean assessmentAccessInd;

	@JsonProperty("assessmentCreateInd")
	private boolean assessmentCreateInd;

	@JsonProperty("assessmentUpdateInd")
	private boolean assessmentUpdateInd;

	@JsonProperty("assessmentDeleteInd")
	private boolean assessmentDeleteInd;

	@JsonProperty("leadsAccessInd")
	private boolean leadsAccessInd;

	@JsonProperty("leadsCreateInd")
	private boolean leadsCreateInd;

	@JsonProperty("leadsUpdateInd")
	private boolean leadsUpdateInd;

	@JsonProperty("leadsDeleteInd")
	private boolean leadsDeleteInd;

	@JsonProperty("testAccessInd")
	private boolean testAccessInd;

	@JsonProperty("testCreateInd")
	private boolean testCreateInd;

	@JsonProperty("testUpdateInd")
	private boolean testUpdateInd;

	@JsonProperty("testDeleteInd")
	private boolean testDeleteInd;

	@JsonProperty("programAccessInd")
	private boolean programAccessInd;

	@JsonProperty("programCreateInd")
	private boolean programCreateInd;

	@JsonProperty("programUpdateInd")
	private boolean programUpdateInd;

	@JsonProperty("programDeleteInd")
	private boolean programDeleteInd;

	@JsonProperty("courseAccessInd")
	private boolean courseAccessInd;

	@JsonProperty("courseCreateInd")
	private boolean courseCreateInd;

	@JsonProperty("courseUpdateInd")
	private boolean courseUpdateInd;

	@JsonProperty("courseDeleteInd")
	private boolean courseDeleteInd;

	@JsonProperty("hoursAccessInd")
	private boolean hoursAccessInd;

	@JsonProperty("hoursCreateInd")
	private boolean hoursCreateInd;

	@JsonProperty("hoursUpdateInd")
	private boolean hoursUpdateInd;

	@JsonProperty("hoursDeleteInd")
	private boolean hoursDeleteInd;

	@JsonProperty("taskAccessInd")
	private boolean taskAccessInd;

	@JsonProperty("taskCreateInd")
	private boolean taskCreateInd;

	@JsonProperty("taskUpdateInd")
	private boolean taskUpdateInd;

	@JsonProperty("taskDeleteInd")
	private boolean taskDeleteInd;

	@JsonProperty("comercialAccessInd")
	private boolean comercialAccessInd;

	@JsonProperty("comercialCreateInd")
	private boolean comercialCreateInd;

	@JsonProperty("comercialUpdateInd")
	private boolean comercialUpdateInd;

	@JsonProperty("comercialDeleteInd")
	private boolean comercialDeleteInd;

	@JsonProperty("gdprApplicableInd")
	private boolean gdprApplicableInd;

	@JsonProperty("emailCustomerInd")
	private boolean emailCustomerInd;

	@JsonProperty("emailJobDesInd")
	private boolean emailJobDesInd;

	@JsonProperty("candidateEventUpdateInd")
	private boolean candidateEventUpdateInd;

	@JsonProperty("customerContactInd")
	private boolean customerContactInd;

	@JsonProperty("partnerContactInd")
	private boolean partnerContactInd;

	@JsonProperty("type")
	private boolean type;

	@JsonProperty("billedInd")
	private boolean billedInd;

	@JsonProperty("candiContSrchInd")
	private boolean candiContSrchInd;

	@JsonProperty("candiEmpShareInd")
	private boolean candiEmpShareInd;

	@JsonProperty("candiEmpSrchInd")
	private boolean candiEmpSrchInd;

	@JsonProperty("candiContShareInd")
	private boolean candiContShareInd;

	@JsonProperty("whatsappJobDesInd")
	private boolean whatsappJobDesInd;

	@JsonProperty("whatsappCustomerInd")
	private boolean whatsappCustomerInd;

	@JsonProperty("candiWorkflowEnabledInstInd")
	private boolean candiWorkflowEnabledInstInd;

	@JsonProperty("candiPipelineEmailInd")
	private boolean candiPipelineEmailInd;

	@JsonProperty("sequenceAvailableInd")
	private boolean sequenceAvailableInd;

	private String roleType;

	@JsonProperty("location")
	private String location;

	@JsonProperty("locationId")
	private String locationId;

	@JsonProperty("vendorFullListInd")
	private boolean vendorFullListInd;

	@JsonProperty("customerFullListInd")
	private boolean customerFullListInd;

	@JsonProperty("opportunityFullListInd")
	private boolean opportunityFullListInd;

	@JsonProperty("talentFullListInd")
	private boolean talentFullListInd;

	@JsonProperty("publishFullListInd")
	private boolean publishFullListInd;

	@JsonProperty("contactFullListInd")
	private boolean contactFullListInd;

	@JsonProperty("requirementFullListInd")
	private boolean requirementFullListInd;

	@JsonProperty("pulseFullListInd")
	private boolean pulseFullListInd;

	@JsonProperty("assessmentFullListInd")
	private boolean assessmentFullListInd;

	@JsonProperty("leadsFullListInd")
	private boolean leadsFullListInd;

	@JsonProperty("testFullListInd")
	private boolean testFullListInd;

	@JsonProperty("programFullListInd")
	private boolean programFullListInd;

	@JsonProperty("courseFullListInd")
	private boolean courseFullListInd;

	@JsonProperty("hoursFullListInd")
	private boolean hoursFullListInd;

	@JsonProperty("taskFullListInd")
	private boolean taskFullListInd;

	@JsonProperty("comercialFullListInd")
	private boolean comercialFullListInd;

	@JsonProperty("locationAccessInd")
	private boolean locationAccessInd;

	@JsonProperty("locationCreateInd")
	private boolean locationCreateInd;

	@JsonProperty("locationUpdateInd")
	private boolean locationUpdateInd;

	@JsonProperty("locationDeleteInd")
	private boolean locationDeleteInd;

	@JsonProperty("locationFullListInd")
	private boolean locationFullListInd;

	@JsonProperty("crmInd")
	private boolean crmInd;

	@JsonProperty("erpInd")
	private boolean erpInd;

	@JsonProperty("expenseFullListInd")
	private boolean expenseFullListInd;

	@JsonProperty("mileageFullListInd")
	private boolean mileageFullListInd;

	@JsonProperty("leaveFullListInd")
	private boolean leaveFullListInd;

	@JsonProperty("userAccessInd")
	private boolean userAccessInd;

	@JsonProperty("userCreateInd")
	private boolean userCreateInd;

	@JsonProperty("userUpdateInd")
	private boolean userUpdateInd;

	@JsonProperty("userDeleteInd")
	private boolean userDeleteInd;

	@JsonProperty("userAccessPlusInd")
	private boolean userAccessPlusInd;

	@JsonProperty("accountAccessInd")
	private boolean accountAccessInd;

	@JsonProperty("accountCreateInd")
	private boolean accountCreateInd;

	@JsonProperty("accountUpdateInd")
	private boolean accountUpdateInd;

	@JsonProperty("accountDeleteInd")
	private boolean accountDeleteInd;

	@JsonProperty("accountFullListInd")
	private boolean accountFullListInd;
	
	@JsonProperty("accountInfoInd")
	private boolean accountInfoInd;

	@JsonProperty("orderAccessInd")
	private boolean orderAccessInd;

	@JsonProperty("orderCreateInd")
	private boolean orderCreateInd;

	@JsonProperty("orderUpdateInd")
	private boolean orderUpdateInd;

	@JsonProperty("orderDeleteInd")
	private boolean orderDeleteInd;

	@JsonProperty("orderFullListInd")
	private boolean orderFullListInd;

	@JsonProperty("materialAccessInd")
	private boolean materialAccessInd;

	@JsonProperty("materialCreateInd")
	private boolean materialCreateInd;

	@JsonProperty("materialUpdateInd")
	private boolean materialUpdateInd;

	@JsonProperty("materialDeleteInd")
	private boolean materialDeleteInd;

	@JsonProperty("materialFullListInd")
	private boolean materialFullListInd;

	@JsonProperty("supplierAccessInd")
	private boolean supplierAccessInd;

	@JsonProperty("supplierCreateInd")
	private boolean supplierCreateInd;

	@JsonProperty("supplierUpdateInd")
	private boolean supplierUpdateInd;

	@JsonProperty("supplierDeleteInd")
	private boolean supplierDeleteInd;

	@JsonProperty("supplierFullListInd")
	private boolean supplierFullListInd;

	@JsonProperty("inventoryAccessInd")
	private boolean inventoryAccessInd;

	@JsonProperty("inventoryCreateInd")
	private boolean inventoryCreateInd;

	@JsonProperty("inventoryUpdateInd")
	private boolean inventoryUpdateInd;

	@JsonProperty("inventoryDeleteInd")
	private boolean inventoryDeleteInd;

	@JsonProperty("inventoryFullListInd")
	private boolean inventoryFullListInd;

	@JsonProperty("refurbishAdminviewInd")
	private boolean refurbishAdminviewInd;

	@JsonProperty("refurbishAdminAssignInd")
	private boolean refurbishAdminAssignInd;

	@JsonProperty("refurbishWorkshopInd")
	private boolean refurbishWorkshopInd;

	@JsonProperty("dashboardFullListInd")
	private boolean dashboardFullListInd;

	@JsonProperty("dashboardAccessInd")
	private boolean dashboardAccessInd;

	@JsonProperty("dashboardRegionalInd")
	private boolean dashboardRegionalInd;
	
	@JsonProperty("settingsAccessInd")
	private boolean settingsAccessInd;

	@JsonProperty("junkAccessInd")
	private boolean junkAccessInd;

	@JsonProperty("junkTransferInd")
	private boolean junkTransferInd;

	@JsonProperty("startInd")
	private boolean startInd;

	@JsonProperty("investorAccessInd")
	private boolean investorAccessInd;

	@JsonProperty("investorCreateInd")
	private boolean investorCreateInd;

	@JsonProperty("investorUpdateInd")
	private boolean investorUpdateInd;

	@JsonProperty("investorDeleteInd")
	private boolean investorDeleteInd;

	@JsonProperty("investorFullListInd")
	private boolean investorFullListInd;

	@JsonProperty("investorContactAccessInd")
	private boolean investorContactAccessInd;

	@JsonProperty("investorContactCreateInd")
	private boolean investorContactCreateInd;

	@JsonProperty("investorContactUpdateInd")
	private boolean investorContactUpdateInd;

	@JsonProperty("investorContactDeleteInd")
	private boolean investorContactDeleteInd;

	@JsonProperty("investorContactFullListInd")
	private boolean investorContactFullListInd;

	@JsonProperty("dealAccessInd")
	private boolean dealAccessInd;

	@JsonProperty("dealCreateInd")
	private boolean dealCreateInd;

	@JsonProperty("dealUpdateInd")
	private boolean dealUpdateInd;

	@JsonProperty("dealDeleteInd")
	private boolean dealDeleteInd;

	@JsonProperty("dealFullListInd")
	private boolean dealFullListInd;

	@JsonProperty("pitchAccessInd")
	private boolean pitchAccessInd;

	@JsonProperty("pitchCreateInd")
	private boolean pitchCreateInd;

	@JsonProperty("pitchUpdateInd")
	private boolean pitchUpdateInd;

	@JsonProperty("pitchDeleteInd")
	private boolean pitchDeleteInd;

	@JsonProperty("pitchFullListInd")
	private boolean pitchFullListInd;

	@JsonProperty("repositoryCreateInd")
	private boolean repositoryCreateInd;

	@JsonProperty("listOfDocPending")
	private List<String> listOfDocPending;

	@JsonProperty("noOfDocPending")
	private int noOfDocPending;

	@JsonProperty("shipperAccessInd")
	private boolean shipperAccessInd;

	@JsonProperty("shipperCreateInd")
	private boolean shipperCreateInd;

	@JsonProperty("shipperUpdateInd")
	private boolean shipperUpdateInd;

	@JsonProperty("shipperDeleteInd")
	private boolean shipperDeleteInd;

	@JsonProperty("shipperFullListInd")
	private boolean shipperFullListInd;

	@JsonProperty("plantAccessInd")
	private boolean plantAccessInd;

	@JsonProperty("plantCreateInd")
	private boolean plantCreateInd;

	@JsonProperty("plantUpdateInd")
	private boolean plantUpdateInd;

	@JsonProperty("plantDeleteInd")
	private boolean plantDeleteInd;

	@JsonProperty("plantFullListInd")
	private boolean plantFullListInd;

	@JsonProperty("teamsAccessInd")
	private boolean teamsAccessInd;

	@JsonProperty("teamsCreateInd")
	private boolean teamsCreateInd;

	@JsonProperty("teamsUpdateInd")
	private boolean teamsUpdateInd;

	@JsonProperty("teamsDeleteInd")
	private boolean teamsDeleteInd;

	@JsonProperty("teamsFullListInd")
	private boolean teamsFullListInd;

	@JsonProperty("imInd")
	private boolean imInd;

	@JsonProperty("basicAccessInd")
	private boolean basicAccessInd;

	@JsonProperty("catalogAccessInd")
	private boolean catalogAccessInd;

	@JsonProperty("catalogCreateInd")
	private boolean catalogCreateInd;

	@JsonProperty("catalogUpdateInd")
	private boolean catalogUpdateInd;

	@JsonProperty("catalogDeleteInd")
	private boolean catalogDeleteInd;

	@JsonProperty("catalogFullListInd")
	private boolean catalogFullListInd;

	@JsonProperty("paymentAccessInd")
	private boolean paymentAccessInd;

	@JsonProperty("accountInd")
	private boolean accountInd;

	@JsonProperty("collectionAccessInd")
	private boolean collectionAccessInd;

	@JsonProperty("collectionApproveInd")
	private boolean collectionApproveInd;

	@JsonProperty("dashboardrecruitDashInd")
	private boolean dashboardrecruitDashInd;

	@JsonProperty("recruitOppsInd")
	private boolean recruitOppsInd;

	@JsonProperty("hrInd")
	private boolean hrInd;

	@JsonProperty("leaveAccessInd")
	private boolean leaveAccessInd;

	@JsonProperty("mileageAccessInd")
	private boolean mileageAccessInd;

	@JsonProperty("expenseAccessInd")
	private boolean expenseAccessInd;

	@JsonProperty("holidayAccessInd")
	private boolean holidayAccessInd;

	@JsonProperty("teamAccessInd")
	private boolean teamAccessInd;

	@JsonProperty("inspectionRequiredInd")
	private boolean inspectionRequiredInd;

	@JsonProperty("reportingManagerDept")
	private String reportingManagerDept;

	@JsonProperty("reportingManagerDeptId")
	private String reportingManagerDeptId;

	@JsonProperty("reportingManagerName")
	private String reportingManagerName;

	@JsonProperty("recruitProInd")
	private boolean recruitProInd;

	@JsonProperty("productionInd")
	private boolean productionInd;

	@JsonProperty("repairInd")
	private boolean repairInd;

	@JsonProperty("inventoryInd")
	private boolean inventoryInd;

	@JsonProperty("orderManagementInd")
	private boolean orderManagementInd;

	@JsonProperty("logisticsInd")
	private boolean logisticsInd;

	@JsonProperty("procurementInd")
	private boolean procurementInd;

	@JsonProperty("eLearningInd")
	private boolean eLearningInd;

	@JsonProperty("orderCreatProductionInd")
	private boolean orderCreatProductionInd;

	@JsonProperty("orderCreatRepairInd")
	private boolean orderCreatRepairInd;

	@JsonProperty("moduleMapper")
	private ModuleDepartmentResponseMapper moduleMapper;

	@JsonProperty("topicAccessInd")
	private boolean topicAccessInd;

	@JsonProperty("topicCreateInd")
	private boolean topicCreateInd;

	@JsonProperty("topicUpdateInd")
	private boolean topicUpdateInd;

	@JsonProperty("topicDeleteInd")
	private boolean topicDeleteInd;

	@JsonProperty("topicFullListInd")
	private boolean topicFullListInd;

	@JsonProperty("procurementAccessInd")
	private boolean procurementAccessInd;

	@JsonProperty("procurementCreateInd")
	private boolean procurementCreateInd;

	@JsonProperty("procurementUpdateInd")
	private boolean procurementUpdateInd;

	@JsonProperty("procurementDeleteInd")
	private boolean procurementDeleteInd;

	@JsonProperty("procurementFullListInd")
	private boolean procurementFullListInd;

	@JsonProperty("subscriptionAccessInd")
	private boolean subscriptionAccessInd;

	@JsonProperty("subscriptionCreateInd")
	private boolean subscriptionCreateInd;

	@JsonProperty("subscriptionUpdateInd")
	private boolean subscriptionUpdateInd;

	@JsonProperty("subscriptionDeleteInd")
	private boolean subscriptionDeleteInd;

	@JsonProperty("subscriptionFullListInd")
	private boolean subscriptionFullListInd;

	@JsonProperty("financeInd")
	private boolean financeInd;

	@JsonProperty("makeToInd")
	private boolean makeToInd;

	@JsonProperty("independentInd")
	private boolean independentInd;

	@JsonProperty("productionAccessInd")
	private boolean productionAccessInd;

	@JsonProperty("productionCreateInd")
	private boolean productionCreateInd;

	@JsonProperty("productionUpdateInd")
	private boolean productionUpdateInd;

	@JsonProperty("productionDeleteInd")
	private boolean productionDeleteInd;

	@JsonProperty("productionFullListInd")
	private boolean productionFullListInd;

	@JsonProperty("reportFullListInd")
	private boolean reportFullListInd;

	@JsonProperty("partNoInd")
	private boolean partNoInd;

	@JsonProperty("salary")
	private double salary;

	@JsonProperty("trnsfrToErpQtionWinInd")
	private boolean trnsfrToErpQtionWinInd;

	@JsonProperty("trnsfrEvthngToErpInd")
	private boolean trnsfrEvthngToErpInd;

	@JsonProperty("serviceLine")
	private String serviceLine;
	
	@JsonProperty("serviceLineId")
	private String serviceLineId;
	
	@JsonProperty("typeInd")
	private boolean typeInd;
	
	@JsonProperty("processInd")
	private boolean processInd;
	
	@JsonProperty("fifoInd")
	private boolean fifoInd;
	
	@JsonProperty("dataRoomAccessInd")
	private boolean dataRoomAccessInd;

	@JsonProperty("dataRoomCreateInd")
	private boolean dataRoomCreateInd;

	@JsonProperty("dataRoomUpdateInd")
	private boolean dataRoomUpdateInd;

	@JsonProperty("dataRoomDeleteInd")
	private boolean dataRoomDeleteInd;

	@JsonProperty("dataRoomFullListInd")
	private boolean dataRoomFullListInd;
	
	@JsonProperty("proInd")
	private boolean proInd;

	@JsonProperty("repairOrdInd")
	private boolean repairOrdInd;
	
	@JsonProperty("scannerAccessInd")
	private boolean scannerAccessInd;
	
	@JsonProperty("secondaryReptManagerId")
	private String secondaryReptManagerId;
	
	@JsonProperty("secondaryReptManagerName")
	private String secondaryReptManagerName;
	
	@JsonProperty("secondaryReptManagerDeptId")
	private String secondaryReptManagerDeptId;
	
	@JsonProperty("secondaryReptManagerDeptName")
	private String secondaryReptManagerDeptName;
	
	@JsonProperty("multyOrgLinkInd")
	private boolean multyOrgLinkInd;
	
	@JsonProperty("multyOrgAccessInd")
	private boolean multyOrgAccessInd;
	
	@JsonProperty("orgType")
	private String orgType;

	@JsonProperty("countryAlpha2Code")
	private String country_alpha2_code;

	@JsonProperty("countryAlpha3Code")
	private String country_alpha3_code;
	
	@JsonProperty("qualityAccessInd")
	private boolean qualityAccessInd;

	@JsonProperty("qualityCreateInd")
	private boolean qualityCreateInd;

	@JsonProperty("qualityUpdateInd")
	private boolean qualityUpdateInd;

	@JsonProperty("qualityDeleteInd")
	private boolean qualityDeleteInd;

	@JsonProperty("qualityFullListInd")
	private boolean qualityFullListInd;
	
	@JsonProperty("clubAccessInd")
	private boolean clubAccessInd;

	@JsonProperty("clubCreateInd")
	private boolean clubCreateInd;

	@JsonProperty("clubUpdateInd")
	private boolean clubUpdateInd;

	@JsonProperty("clubDeleteInd")
	private boolean clubDeleteInd;

	@JsonProperty("clubFullListInd")
	private boolean clubFullListInd;
	
	@JsonProperty("supplierBlockInd")
	private boolean supplierBlockInd;
	
	@JsonProperty("supplierInventoryInd")
	private boolean supplierInventoryInd;
	
	@JsonProperty("calenderViewInd")
	private boolean calenderViewInd;
	
	@JsonProperty("calenderManageInd")
	private boolean calenderManageInd;

	@JsonProperty("primaryOrgType")
	private String primaryOrgType;
}
