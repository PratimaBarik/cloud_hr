package com.app.employeePortal.sector.mapper;



import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SectorMapper {

	
	@JsonProperty("sectorId")
	private String sectorId;
	
	@JsonProperty("sectorName")
	private String sectorName;
	
	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("organizationId")
	private String organizationId;
	
	@JsonProperty("editInd")
	private boolean editInd;
	
	@JsonProperty("liveInd")
	private boolean liveInd;
	
	@JsonProperty("creationDate")
	private String creationDate;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("updationDate")
	private String updationDate;
}
