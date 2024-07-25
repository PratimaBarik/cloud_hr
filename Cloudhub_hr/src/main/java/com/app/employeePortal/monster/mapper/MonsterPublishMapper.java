package com.app.employeePortal.monster.mapper;

import org.codehaus.jackson.annotate.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MonsterPublishMapper {
	
	@JsonProperty("monsterPublishId")
	private String monsterPublishId; 
	
	@JsonProperty("recruitmentId")
	private String recruitmentId;

	@JsonProperty("monsterId")
	private String monsterId;
	
	@JsonProperty("jobDuration")
	private String jobDuration;

	@JsonProperty("jobCategory")
	private String jobCategory;
	
	@JsonProperty("jobOccupation")
	private String jobOccupation;
	
	@JsonProperty("jobBoardName")
	private String jobBoardName;
	
	@JsonProperty("displayTemplate")
	private String displayTemplate;
	
	@JsonProperty("industry")
	private String industry;

	@JsonProperty("UserName")
	private String UserName;
	
	@JsonProperty("Password")
	private String Password;
	
	@JsonProperty("jobRefCode")
	private String jobRefCode;
	
	@JsonProperty("jobAction")
	private String jobAction;
	
	@JsonProperty("JobTitle")
	private String JobTitle;
	
	@JsonProperty("hideAll")
	private String hideAll;
	
	@JsonProperty("hideCompanyName")
	private String hideCompanyName;
	
	@JsonProperty("hideEmailAddress")
	private String hideEmailAddress;
	
	@JsonProperty("managerName")
	private String managerName;
	
	@JsonProperty("CompanyName")
	private String CompanyName;
	
	@JsonProperty("E-mail")
	private String Email;
	
//	@JsonProperty("PhysicalAddress")
//	private List<AddressMapper> PhysicalAddress;
//	
//	@JsonProperty("Location")
//	private List<AddressMapper> Location;
	
	@JsonProperty("street")
	private String street;
	
	@JsonProperty("city")
	private String city;
	
	@JsonProperty("state")
    private String state;
	
	@JsonProperty("countryCode")
	private String countryCode;
	
	@JsonProperty("postalCode")
	private String postalCode;
	
	@JsonProperty("locationCity")
	private String locationCity;
	
	@JsonProperty("locationState")
	private String locationState;
	
	@JsonProperty("locationCountryCode")
	private String locationCountryCode;
	
	@JsonProperty("locationPostalCode")
	private String locationPostalCode;
	
	@JsonProperty("description")
	private String description;
	
	@JsonProperty("desiredDuration")
	private String desiredDuration;
	
	@JsonProperty("IndustryName ")
	private String IndustryName ;
	
	@JsonProperty("organizationId")
	private String organizationId;
	
	@JsonProperty("userId")
	private String userId;
		
}
