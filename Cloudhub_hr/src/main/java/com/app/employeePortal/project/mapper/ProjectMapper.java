package com.app.employeePortal.project.mapper;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ProjectMapper {
	
	@JsonProperty("projectId")
	private String projectId;
	
	@JsonProperty("projectName")
	private String projectName;
	
	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("orgId")
	private String orgId;
	
	@JsonProperty("creationDate")
	private Date creationDate;
	
	@JsonProperty("liveInd")
	private boolean liveInd;
	
	@JsonProperty("EditInd")
	private boolean EditInd;
	
	@JsonProperty("creatorName")
	private String creatorName;
	
	@JsonProperty("customerName")
	private String customerName;
	
	@JsonProperty("customerId")
	private String customerId;

	


}
