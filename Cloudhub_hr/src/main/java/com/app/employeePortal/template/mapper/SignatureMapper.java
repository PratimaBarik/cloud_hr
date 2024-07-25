package com.app.employeePortal.template.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class SignatureMapper {
	
	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("orgId")
	private String orgId;
	
	@JsonProperty("signature")
	private String signature;
	
	@JsonProperty("type")
	private String type;

	
	@JsonProperty("signatureId")
	private String signatureId;


	
	

}
