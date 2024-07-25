package com.app.employeePortal.excelBulkImport.mapper;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)

public class FieldDetailss {
	
	 @JsonProperty("fieldViewName")
	    private String fieldViewName;

	    @JsonProperty("fieldKey")
	    private String fieldKey;

	    @JsonProperty("required")
	    private boolean required;


	    public FieldDetailss(String fieldViewName, String fieldKey) {
	        super();
	        this.fieldViewName = fieldViewName;
	        this.fieldKey = fieldKey;
	    }

	    public FieldDetailss(String fieldViewName, String fieldKey, boolean required) {
	        super();
	        this.fieldViewName = fieldViewName;
	        this.fieldKey = fieldKey;
	        this.required = required;
	    }

	    

}
