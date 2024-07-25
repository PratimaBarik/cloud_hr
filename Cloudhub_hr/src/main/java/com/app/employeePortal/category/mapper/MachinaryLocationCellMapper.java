package com.app.employeePortal.category.mapper;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class MachinaryLocationCellMapper {

	@JsonProperty("machinaryLocationLinkId")
	private String machinaryLocationLinkId;
	
	@JsonProperty("cellId")
	private String cellId;
	
	@JsonProperty("cell")
	private String cell;

	@JsonProperty("orgId")
	private String orgId;

	@JsonProperty("userId")
	private String userId;
	 
	@JsonProperty("creationDate")
	private String creationDate;

}
