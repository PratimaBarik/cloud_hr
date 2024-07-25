package com.app.employeePortal.investor.mapper;



import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)

public class DataRoomMapper {
	
	@JsonProperty("dataRoomId")
	private String dataRoomId;
	
	@JsonProperty("creationDate")
	private String creationDate;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("orgId")
	private String orgId;

	
	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("documentType")
	private String documentType;
	
	@JsonProperty("included")
	private List<String> included;
	
	@JsonProperty("external")
	private List<String> external;
	
}
