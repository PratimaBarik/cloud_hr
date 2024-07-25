package com.app.employeePortal.category.mapper;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminSecreteKeyMapper {

	@JsonProperty("adminSecreteKeyId")
	private String adminSecreteKeyId;

	@JsonProperty("adminSecreteKey")
	private String adminSecreteKey;

	@JsonProperty("orgId")
	private String orgId;

	@JsonProperty("userId")
	private String userId;

	@JsonProperty("creationDate")
	private String creationDate;

	@JsonProperty("liveInd")
	private boolean liveInd;
}
