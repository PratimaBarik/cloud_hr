package com.app.employeePortal.employee.mapper;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)

public class BankDetailsMapper {
	
	@JsonProperty("id")
 	private String id;

	@JsonProperty("employeeId")
 	private String employeeId;
	
	@JsonProperty("accountHolderName")
	private String accountHolderName;
	
	@JsonProperty("bankName")
	private String bankName;
	
	@JsonProperty("branchName")
	private String branchName;
	
	@JsonProperty("accountNo")
	private String accountNo;
	
	@JsonProperty("ifscCode")
	private String ifscCode;
	
	@JsonProperty("creationDate")
	private String creationDate;

	@JsonProperty("defaultInd")
	private boolean defaultInd;
	
}
