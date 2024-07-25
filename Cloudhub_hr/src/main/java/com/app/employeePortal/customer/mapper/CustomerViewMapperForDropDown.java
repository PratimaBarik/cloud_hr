package com.app.employeePortal.customer.mapper;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerViewMapperForDropDown {
	
	@JsonProperty("customerId")
	private String customerId;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("creationDate")
	private String creationDate;

}
