package com.app.employeePortal.support.mapper;

import org.codehaus.jackson.annotate.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ProductResponseMapper {

	@JsonProperty("productName")
	private String productName;
	
	@JsonProperty("productId")
	private String productId;
}
