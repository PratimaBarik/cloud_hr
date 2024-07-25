package com.app.employeePortal.mileage.mapper;

import com.app.employeePortal.address.mapper.AddressMapper;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MileageMapper {
	
	@JsonProperty("mileageId")
 	private String mileageId;
	
	@JsonProperty("projectName")
 	private String projectName;
	
	@JsonProperty("clientName")
 	private String clientName;
	
	@JsonProperty("mileageDate")
 	private String mileageDate;
	
	@JsonProperty("fromLocation")
 	private String fromLocation;
	
	@JsonProperty("toLocation")
 	private String toLocation;
	
	@JsonProperty("distances")
 	private double distances;
	
	@JsonProperty("remark")
 	private String remark;
	
	@JsonProperty("unit")
 	private String unit;
	
	@JsonProperty("mileageRate")
 	private double mileageRate;

	
	@JsonProperty("userId")
 	private String userId;
	
	@JsonProperty("organizationId")
 	private String organizationId;
	
	@JsonProperty("creationDate")
 	private String creationDate;

	@JsonProperty("currency")
 	private String currency;

	@JsonProperty("amount")
	private double amount;
	
	@JsonProperty("status")
	private String status;
	
	@JsonProperty("fromaAddress")
	private AddressMapper fromAddress;
	
	@JsonProperty("toAddress")
	private AddressMapper toAddress;
	
}
