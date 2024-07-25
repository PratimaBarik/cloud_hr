package com.app.employeePortal.recruitment.mapper;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@ToString
@Getter
@Setter

public class RecruitmentRecruitOwnerMapper {

	@JsonProperty("recruitOwner")
	private String recruitOwner;
	
	@JsonProperty("recruitOwnerId")
	private String recruitOwnerId;
}
