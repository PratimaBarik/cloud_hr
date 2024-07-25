package com.app.employeePortal.employee.mapper;

import java.util.List;

import com.app.employeePortal.address.mapper.AddressMapper;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeTableMapper {

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

	@JsonProperty("roleType")
	private String roleType;
	
	@JsonProperty("roleTypeName")
	private String roleTypeName;
	
	@JsonProperty("location")
	private String location;
	
	@JsonProperty("locationName")
	private String locationName;
	
	@JsonProperty("listOfDocPending")
	private List<String> listOfDocPending;

	@JsonProperty("noOfDocPending")
	private int noOfDocPending;
	
	@JsonProperty("type")
	private boolean type;

	@JsonProperty("billedInd")
	private boolean billedInd;
	
	@JsonProperty("reportingManagerName")
	private String reportingManagerName;
	
	@JsonProperty("reportingManagerDept")
	private String reportingManagerDept;
	
	@JsonProperty("reportingManagerDeptId")
	private String reportingManagerDeptId;
	
	@JsonProperty("salary")
	private double salary;
	
//	@JsonProperty("adminInd")
//	private boolean adminInd;
	
	@JsonProperty("serviceLine")
	private String serviceLine;
	
	@JsonProperty("serviceLineId")
	private String serviceLineId;
	
	@JsonProperty("secondaryReptManagerId")
	private String secondaryReptManagerId;
	
	@JsonProperty("secondaryReptManager")
	private String secondaryReptManager;
	
	@JsonProperty("secondaryReptManagerDeptId")
	private String secondaryReptManagerDeptId;
	
	@JsonProperty("secondaryReptManagerDept")
	private String secondaryReptManagerDept;
	
	@JsonProperty("multyOrgAccessInd")
	private boolean multyOrgAccessInd;
	
	@JsonProperty("multyOrgLinkInd")
	private boolean multyOrgLinkInd;
	
	@JsonProperty("cellChamber")
	private String cellChamber;

	@JsonProperty("countryAlpha2Code")
	private String country_alpha2_code;

	@JsonProperty("countryAlpha3Code")
	private String country_alpha3_code;
}
