package com.app.employeePortal.customer.mapper;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CustomerViewMapperrForMap {
	
	@JsonProperty("customerId")
	private String customerId;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("email")
	private String email;
	
	@JsonProperty("latitude")
	private String latitude;
	
	@JsonProperty("longitude")
	private String longitude;
	
	@JsonProperty("imageURL")
    private String imageURL;

	@JsonProperty("creationDate")
	private String creationDate;
}
