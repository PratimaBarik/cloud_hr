package com.app.employeePortal.source.mapper;

import javax.persistence.Column;

import org.codehaus.jackson.annotate.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class SourceMapper {
	
	@JsonProperty("sourceId")
	private String sourceId;

	@JsonProperty("name")
	private String name;
	
	@JsonProperty("OrgId")
	private String OrgId;

	@JsonProperty("userId")
	private String userId;
	 
	@JsonProperty("liveInd")
	private boolean liveInd;
	
	@JsonProperty("creationDate")
	private String creationDate;
	
	@JsonProperty("updationDate")
	private String updationDate;
	
	@JsonProperty("updatedBy")
	private String updatedBy;
	
	@Column(name = "editInd")
	private boolean editInd;
	
	@JsonProperty("link")
	private String link;
}
