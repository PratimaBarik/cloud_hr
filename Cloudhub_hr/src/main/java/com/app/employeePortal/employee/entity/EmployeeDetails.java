package com.app.employeePortal.employee.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.app.employeePortal.config.AesEncryptor;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Entity
@Getter
@Setter
@Table(name="employee_details")
public class EmployeeDetails {
	

	@Id
	@GenericGenerator(name = "employee_details_id", strategy = "com.app.employeePortal.employee.generator.EmployeeDetailsGenerator")
	@GeneratedValue(generator = "employee_details_id")
	
	@Column(name="employee_details_id")
	private String id;
	
	@Column(name="employee_id")
	private String employeeId;
	
	@Column(name="org_id")
	private String orgId;
	
	@Column(name="user_id")
	private String userId;
	
	@Column(name="salutation")
	private String salutation;
	
	@Convert(converter = AesEncryptor.class)
	@Column(name="first_name")
	private String firstName;
	
	@Convert(converter = AesEncryptor.class)
	@Column(name="middle_name")
	private String middleName;
	
	@Convert(converter = AesEncryptor.class)
	@Column(name="last_name")
	private String lastName;
	
	@Convert(converter = AesEncryptor.class)
	@Column(name="full_name")
	private String fullName;
	
	
	@Column(name="status")
	private String status;
			
	@Column(name="dob")
	private Date dob;
	
	@Column(name="date_of_joining")
	private Date dateOfJoining;
	
	@Column(name="gender")
	private String gender;
	
	@Convert(converter = AesEncryptor.class)
	@Column(name="phone_no")
	private String phoneNo;
	
	@Convert(converter = AesEncryptor.class)
	@Column(name="mobile_no")
	private String mobileNo;
	
	@Convert(converter = AesEncryptor.class)
	@Column(name="email_id")
	private String emailId;
	
	@Column(name="secondary_email_id")
	private String secondaryEmailId;
	
	@Column(name="skype_id")
	private String skypeId;
	
	@Column(name="fax")
	private String fax;
	
	@Column(name="linkedin_public_url")
	private String linkedinPublicUrl;
	
	@Lob
	@Column(name="description")
	private String description;
	
	@Column(name="currency")
	private String currency;
	
	@Column(name="image_id")
	private String imageId;
	
	@Column(name="time_zone")
	private String timeZone;
	
	@Column(name="creation_date")
	private Date creationDate;
	
	@Column(name="creator_id")
	private String creatorId;
	
	@Column(name="modification_by")
	private String modificationBy;
	
	@Column(name="modification_date")
	private Date modificationDate;
	
	@Column(name="language")
	private String language;
	
	@Column(name="designation")
	private String designation;
	
	@Column(name="role")
	private String role;
	
	@Column(name="country")
	private String country;
	
	@Column(name="twitter")
	private String twitter;
	
	@Column(name="facebook")
	private String facebook;
	
	@Column(name="department")
	private String department;
	
	@Column(name="country_dial_code")
	private String countryDialCode;
	
	@Column(name="country_dial_code1")
	private String countryDialCode1;
	
	@Column(name="label")
	private String label;
	
	@Column(name="workplace")
	private String workplace;
	
	@Column(name="job_type")
	private String jobType;
	
	@Column(name="employee_type")
	private String employeeType;
	
	@Column(name="blood_group")
	private String bloodGroup;
	
	@Column(name="reporting_manager")
	private String reportingManager;
	
	@Column(name="live_ind")
	private boolean liveInd;
	
	@Column(name="suspend_ind")
	private boolean suspendInd;
	
	@Column(name="user_type")
	private String userType;
	
	@Column(name="billed_ind")
	private boolean billedInd;
	
	@Column(name="role_type")
	private String roleType;

	@Column(name="location")
	private String location;
	
	@Column(name="reporting_manager_dept")
	private String reportingManagerDept;
	
	@Column(name="salary", nullable =false)
	private double salary;
	
	@Column(name="serviceLine")
	private String serviceLine;
	
	@Column(name="secondary_rept_manager")
	private String secondaryReptManager;
	
	@Column(name="secondary_rept_manager_dept")
	private String secondaryReptManagerDept;
	
	@Column(name="multy_org_link_Ind", nullable =false)
	private boolean multyOrgLinkInd =false;
	
	@Column(name="multy_org_access_Ind", nullable =false)
	private boolean multyOrgAccessInd =false;
}
