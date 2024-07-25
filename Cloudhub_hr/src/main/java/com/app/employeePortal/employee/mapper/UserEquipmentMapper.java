package com.app.employeePortal.employee.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class UserEquipmentMapper {
	
	@JsonProperty("userEquipmentLinkId")
	private String userEquipmentLinkId;
	
	@JsonProperty("equipmentId")
	private String equipmentId;

	@JsonProperty("orgId")
 	private String orgId;
	
	@JsonProperty("userId")
 	private String userId;
	
	@JsonProperty("creationDate")
	private String creationDate;
	
	@JsonProperty("liveInd")
	private boolean liveInd;

	@JsonProperty("value")
	private String value;	
	
	@JsonProperty("equipmentName")
	private String equipmentName;	

	
}
