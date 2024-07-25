package com.app.employeePortal.category.mapper;

import org.codehaus.jackson.annotate.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SecreteKeyMapper {

	@JsonProperty("secreteKeyId")
	private String secreteKeyId;

	@JsonProperty("epiKey")
	private String epiKey;

	@JsonProperty("secreteKey")
	private String secreteKey;

	@JsonProperty("type")
	private String type;

	@JsonProperty("creationDate")
	private String creationDate;
	
}
