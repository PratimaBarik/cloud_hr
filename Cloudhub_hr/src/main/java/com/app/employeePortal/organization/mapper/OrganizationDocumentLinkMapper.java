package com.app.employeePortal.organization.mapper;



import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.app.employeePortal.employee.mapper.EmployeeShortMapper;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)

public class OrganizationDocumentLinkMapper {
	
	@JsonProperty("organizationDocumentLinkId")
	private String organizationDocumentLinkId;
	
	@JsonProperty("catagory")
	private String catagory;
	
	@JsonProperty("creationDate")
	private String creationDate;
	
	@JsonProperty("department")
	private String department;
	
	@JsonProperty("description")
	private String description;
	
	@JsonProperty("documentId")
	private String documentId;
	
	@JsonProperty("liveInd")
	private boolean liveInd;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("orgId")
	private String orgId;
	
	@JsonProperty("shareInd")
	private boolean shareInd;
	
	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("documentType")
	private String documentType;
	
	@JsonProperty("included")
	private List<String> included;
	
	@JsonProperty("includeds")
	private List<EmployeeShortMapper> includeds;
	
}
