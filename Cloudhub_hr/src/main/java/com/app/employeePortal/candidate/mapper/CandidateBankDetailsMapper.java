package com.app.employeePortal.candidate.mapper;

import javax.persistence.Column;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CandidateBankDetailsMapper {
	
	@JsonProperty("id")
 	private String id;

	@JsonProperty("candidateId")
 	private String candidateId;
	
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

	@Column(name="defaultInd")
	private boolean defaultInd;
}
