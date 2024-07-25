package com.app.employeePortal.customer.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerAddressResponseMapper {
	
	@JsonProperty("addressId")
	private String addressId;
	
	@JsonProperty("addressType")
	private String addressType;
	
	@JsonProperty("address1")
	private String address1;
	
	@JsonProperty("address2")
	private String address2;
	
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

	@JsonProperty("creatorId")
	private String creatorId ;
	
	@JsonProperty("employeeId")
	private String employeeId ;
	
	@JsonProperty("countryCode")
	private String countryCode;
	
	@JsonProperty("houseNo")
	private String houseNo;

	@JsonProperty("countryAlpha2Code")
	private String country_alpha2_code;
	
	@JsonProperty("countryAlpha3Code")
	private String country_alpha3_code;
	
	
	

}
