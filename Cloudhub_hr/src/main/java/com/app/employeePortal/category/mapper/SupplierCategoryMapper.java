package com.app.employeePortal.category.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class SupplierCategoryMapper {
	
	@JsonProperty("supplierCategoryId")
	private String supplierCategoryId;

	@JsonProperty("supplierCatName")
	private String supplierCatName;
	
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
