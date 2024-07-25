package com.app.employeePortal.employee.mapper;

import java.util.List;

import com.app.employeePortal.address.mapper.AddressMapper;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonalDetailsMapper {
	
	@JsonProperty("id")
 	private String id;

	@JsonProperty("employeeId")
 	private String employeeId;
	
	@JsonProperty("contactSalutation")
	private String contactSalutation;
	
	@JsonProperty("contactFirstName")
	private String contactFirstName;
	
	@JsonProperty("contactMiddleName")
	private String contactMiddleName;
	
	@JsonProperty("contactLastName")
	private String contactLastName;
	
	@JsonProperty("countryDialCode")
	private String countryDialCode;
	
	@JsonProperty("countryDialCode1")
	private String countryDialCode1;
	
	@JsonProperty("phoneNo")
	private String phoneNo;  
	
	@JsonProperty("mobileNo")
	private String mobileNo;
	
    @JsonProperty("bloodGroup")
	private String bloodGroup;
    
    @JsonProperty("dob")
  	private String dob;
    
    @JsonProperty("creationDate")
	private String creationDate;
    
    @JsonProperty("address")
	private List<AddressMapper> address ;

	public String getEmployeeId() {
		return employeeId;
	}

	
    
    
	
}
