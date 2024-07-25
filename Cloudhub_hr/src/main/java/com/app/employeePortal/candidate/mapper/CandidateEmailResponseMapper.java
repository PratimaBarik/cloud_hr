package com.app.employeePortal.candidate.mapper;

import java.util.List;

import com.app.employeePortal.document.mapper.DocumentMapper;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CandidateEmailResponseMapper {

	@JsonProperty("name")
	private String name;
	
	@JsonProperty("role")
	private String role;
	
	@JsonProperty("mobileNo")
	private String mobileNo;
	
	@JsonProperty("skill")
	private List<String> skill;
	
	@JsonProperty("category")
	private String category;
	
	@JsonProperty("billing")
	private String billing;
	
	@JsonProperty("availableDate")
	private String availableDate;
	
	@JsonProperty("email")
	private String email;
	
	@JsonProperty("experience")
	private float experience;
	
	@JsonProperty("idProof")
	private String idProof;
	
	@JsonProperty("idNumber")
	private String idNumber;
	
	@JsonProperty("skillWiseExperience")
	private List<SkillSetMapper> skillWiseExperience;
	
	@JsonProperty("nameInd")
	private boolean nameInd;
	
	@JsonProperty("roleInd")
	private boolean roleInd;
	
	@JsonProperty("mobileNoInd")
	private boolean mobileNoInd;
	
	@JsonProperty("skillInd")
	private boolean skillInd;
	
	@JsonProperty("categoryInd")
	private boolean categoryInd;
	
	@JsonProperty("billingInd")
	private boolean billingInd;
	
	@JsonProperty("availableDateInd")
	private boolean availableDateInd;
	
	@JsonProperty("emailInd")
	private boolean emailInd;
	
	@JsonProperty("experienceInd")
	private boolean experienceInd;
	
	@JsonProperty("identityCardInd")
	private boolean identityCardInd;
	
	@JsonProperty("skillWiseExperienceInd")
	private boolean skillWiseExperienceInd;
	
	@JsonProperty("resumeInd")
	private boolean resumeInd;
	
	@JsonProperty("documentMapper")
	private DocumentMapper documentMapper;
	
//	@JsonProperty("documentMapper")
//	private List<DocumentMapper> documentMapper;
}
