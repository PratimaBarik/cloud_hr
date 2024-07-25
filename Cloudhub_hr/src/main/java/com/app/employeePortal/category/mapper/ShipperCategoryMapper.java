package com.app.employeePortal.category.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ShipperCategoryMapper {
	
	@JsonProperty("shipperCategoryId")
	private String shipperCategoryId;

	@JsonProperty("shipperCatName")
	private String shipperCatName;
	
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

}
