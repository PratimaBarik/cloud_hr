package com.app.employeePortal.Opportunity.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class FieldDetailsDTO {

	@JsonProperty("fieldViewName")
	private String fieldViewName ;
	
	@JsonProperty("fieldKey")
	private String fieldKey ;
	
	@JsonProperty("required")
	private boolean required ;
	
	@JsonProperty("excelId")
	private String excelId ;
	
	@JsonProperty("userId")
	private String userId ;
	
	@JsonProperty("orgId")
	private String orgId ;
	
	@JsonProperty("uploadDate")
	private String uploadDate ;
	
	@JsonProperty("opportunityId")
	private String opportunityId;
	
	@JsonProperty("customerId")
	private String customerId;
	
	@JsonProperty("xlUpdateInd")
	private boolean xlUpdateInd;
	
	@JsonProperty("excelJson")
	private String excelJson;
	
	 //private List<OrderTaskTypeLinkDTO> taskList;
	
	public FieldDetailsDTO(String fieldViewName, String fieldKey) {
		
		this.fieldViewName = fieldViewName;
		this.fieldKey = fieldKey;
	}
	public FieldDetailsDTO(String fieldViewName, String fieldKey, boolean required) {
		
		this.fieldViewName = fieldViewName;
		this.fieldKey = fieldKey;
		this.required = required;
	}
	
	
	
}
