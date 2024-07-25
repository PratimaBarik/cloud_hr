package com.app.employeePortal.immport.mapper;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExcelMapping {
	
	 @JsonProperty("excelHeader")
	    private String excelHeader;

	    @JsonProperty("mappingField")
	    private String mappingField;

}
