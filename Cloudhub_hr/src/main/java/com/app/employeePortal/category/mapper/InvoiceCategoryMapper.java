package com.app.employeePortal.category.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvoiceCategoryMapper {

	@JsonProperty("invoiceCategoryId")
	private String invoiceCategoryId;

	@JsonProperty("orgId")
	private String orgId;

	@JsonProperty("piInd")
	private boolean piInd;

	@JsonProperty("autoCiInd")
	private boolean autoCiInd;

	@JsonProperty("inniInspectInd")
	private boolean inniInspectInd;
	
	@JsonProperty("type")
	private String type;

}
