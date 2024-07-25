package com.app.employeePortal.customer.mapper;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class CustomerKeySkillMapper {

//	@JsonProperty("opportunityId")
//	private String opportunityId;
	
	@JsonProperty("skillSetList")
	private List<String> skillSetList;
	
//	@JsonProperty("customerId")
//	private String customerId;
//	
//	@Column(name = "recruitmentId")
//	private String recruitmentId;	
	
}
