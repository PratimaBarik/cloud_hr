package com.app.employeePortal.category.mapper;

import javax.persistence.Column;

import org.codehaus.jackson.annotate.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class CustomerTypeMapper {
	
	@JsonProperty("customerTypeId")
	private String customerTypeId;

	@JsonProperty("name")
	private String name;
	
	@JsonProperty("orgId")
	private String orgId;

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
}
