package com.app.employeePortal.contact.mapper;

import org.codehaus.jackson.annotate.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContactViewMapperForDropDown {
	
	@JsonProperty("contactId")
	private String contactId;

	@JsonProperty("creationDate")
	private String creationDate;
	
	@JsonProperty("fullName")
	private String fullName;
	
	@JsonProperty("customerId")
	private String customerId;
	
	@JsonProperty("partnerId")
	private String partnerId;
	
	@JsonProperty("investorId")
	private String investorId;
	
}
