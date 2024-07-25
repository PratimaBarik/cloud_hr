package com.app.employeePortal.category.mapper;

import java.util.List;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class MachinaryLocationMapper {
	
	@JsonProperty("machinaryLocationLinkId")
	private String machinaryLocationLinkId;

	@JsonProperty("machinaryId")
	private String machinaryId;
	
	@JsonProperty("locationId")
	private String locationId;
	
	@JsonProperty("orgId")
	private String orgId;

	@JsonProperty("userId")
	private String userId;
	 
	@JsonProperty("liveInd")
	private boolean liveInd;
	
	@JsonProperty("creationDate")
	private String creationDate;
	
	@JsonProperty("locationName")
	private String locationName;
	
	@JsonProperty("machinaryName")
	private String machinaryName;
	
	@Column(name="machineCode")
	private String machineCode;
	
	@JsonProperty("cellId")
	private String cellId;
}
