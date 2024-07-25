package com.app.employeePortal.category.mapper;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceLineRespMapper {

	@JsonProperty("serviceLineId")
	private String serviceLineId;

	@JsonProperty("serviceLineName")
	private String serviceLineName;

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

	@JsonProperty("departmentId")
	private String departmentId;
}
