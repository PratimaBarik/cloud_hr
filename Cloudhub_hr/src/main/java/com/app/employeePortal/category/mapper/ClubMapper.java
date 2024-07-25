package com.app.employeePortal.category.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ClubMapper {

	@JsonProperty("clubId")
	private String clubId;

	@JsonProperty("clubName")
	private String clubName;

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

	@JsonProperty("noOfShare")
	private double noOfShare;

	@JsonProperty("discount")
	private float discount;
	
	@JsonProperty("invToCusInd")
	private boolean invToCusInd;

}
