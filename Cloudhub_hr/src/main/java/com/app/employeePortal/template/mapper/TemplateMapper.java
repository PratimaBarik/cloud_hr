package com.app.employeePortal.template.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class TemplateMapper {
	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("orgId")
	private String orgId;
	
	@JsonProperty("subject")
	private String subject;
	
	@JsonProperty("template")
	private String template;
	
	@JsonProperty("description")
	private String description;
	
	@JsonProperty("type")
	private String type;

	
	@JsonProperty("templateId")
	private String templateId;
	
	@JsonProperty("creationDate")
	private String creationDate;

	
	@JsonProperty("customerId")
	private String customerId;
	
	@JsonProperty("customerName")
	private String customerName;
}
