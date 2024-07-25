package com.app.employeePortal.customer.mapper;

import java.util.List;

import com.app.employeePortal.address.mapper.AddressMapper;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CustomerNetflixMapper {
	
	@JsonProperty("customerId")
	private String customerId;
	
	@JsonProperty("name")
	private String name;

	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("position")
	private int position;
	
/*	@JsonProperty("selected")
	private int selected;*/
	
	@JsonProperty("onBoarded")
	private int onBoarded;
	
	@JsonProperty("oppNo")
	private int oppNo;
	
	@JsonProperty("address")
	private List<AddressMapper> address;
}
