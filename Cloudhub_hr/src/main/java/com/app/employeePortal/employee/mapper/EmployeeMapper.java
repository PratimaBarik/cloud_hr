package com.app.employeePortal.employee.mapper;

import java.util.List;
import java.util.Map;

import com.app.employeePortal.address.mapper.AddressMapper;
import com.app.employeePortal.organization.mapper.FiscalMapper;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeMapper {

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

	// @NotEmpty(message="first name should be provided")
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

	//@NotEmpty
	//@Email(message = "please provide a valid email address")
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

	@JsonProperty("address")
	private List<AddressMapper> address;

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

	@JsonProperty("designationTypeId")
	private String designationTypeId;

	@JsonProperty("departmentId")
	private String departmentId;

	@JsonProperty("type")
	private boolean type;

	@JsonProperty("billedInd")
	private boolean billedInd;
	
	private String roleType;
	
	@JsonProperty("location")
	private String location;
	
	@JsonProperty("reportingManagerDept")
	private String reportingManagerDept;
	
	@JsonProperty("reportingManagerDeptId")
	private String reportingManagerDeptId;
	
	@JsonProperty("salary")
	private double salary;

	@JsonProperty("serviceLineId")
	private String serviceLineId;
	
	@JsonProperty("secondaryReptManager")
	private String secondaryReptManager;
	
	@JsonProperty("secondaryReptManagerDept")
	private String secondaryReptManagerDept;
}
