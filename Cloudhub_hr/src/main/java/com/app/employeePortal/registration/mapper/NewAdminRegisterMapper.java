package com.app.employeePortal.registration.mapper;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class NewAdminRegisterMapper {
	
	private String organizationName;

	private String fiscalStartDate;
	
	private String fiscalStartMonth;	
	
	private String firstName;

	private String middleName;

	private String lastName;

	private String emailId;

	private String password;
	
	private String userType;
	
	private String role;

}
