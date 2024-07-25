package com.app.employeePortal.excelBulkImport.mapper;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExcelBulkMapping {

	@JsonProperty("excelHeader")
	private String excelHeader;

	@JsonProperty("mappingField")
	private String mappingField;

}
