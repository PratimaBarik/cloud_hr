package com.app.employeePortal.partner.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;



@Getter
@Setter

public class PartnerMapperForDropDown {
	
	@JsonProperty("partnerId")
	private String partnerId;
	
	@JsonProperty("partnerName")
	private String partnerName;
	
	@JsonProperty("creationDate")
	private String creationDate;
	
}
