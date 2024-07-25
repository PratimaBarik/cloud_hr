package com.app.employeePortal.recruitment.mapper;


import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@JsonPropertyOrder({"addressId", "address1", "town", "street","city", "postalCode", "country","longitude","latitude"})

public class JobPublishAddressMapper {
	
	@JsonProperty("addressId")
	private String addressId;
	
	@JsonProperty("address1")
	private String address1;
	
	@JsonProperty("town")
	private String town;
	
	@JsonProperty("street")
	private String street;
	
	@JsonProperty("city")
	private String city;
	
	@JsonProperty("postalCode")
	private String postalCode;
	
	@JsonProperty("state")
	private String state;
	
	@JsonProperty("country")
	private String country;
	
	@JsonProperty("longitude")
	private String longitude ;
	
	@JsonProperty("latitude")
	private String latitude ;
	
	@JsonProperty("houseNo")
	private String houseNo ;
	
	

}
