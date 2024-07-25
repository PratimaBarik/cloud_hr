package com.app.employeePortal.candidate.mapper;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import com.app.employeePortal.address.mapper.AddressMapper;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown=true)
public class CandidateMapper {
	
	@JsonProperty("candidateId")
	private String candidateId;
	
	//@NotEmpty(message="first name should not empty")
	@JsonProperty("firstName")
	private String firstName;
	
	@JsonProperty("middleName")
	private String middleName;
	
	@JsonProperty("lastName")
	private String lastName;
	
	@JsonProperty("mobileNumber")
	private String mobileNumber;
	
	@JsonProperty("phoneNumber")
	private String phoneNumber;
	
	@JsonProperty("emailId")
	private String emailId;
	
	@JsonProperty("linkedin")
	private String linkedin;
	
	@JsonProperty("notes")
	private String notes;
	
	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("organizationId")
	private String organizationId;
	
	@JsonProperty("customerId")
	private String customerId;
	
	@JsonProperty("address")
	private List<AddressMapper> address;

	@JsonProperty("creationDate")
	private String creationDate;
	
	@JsonProperty( "salutation")
	private String salutation;
	
	@JsonProperty( "availableDate")
	private String availableDate;
	
	@JsonProperty( "billing")
	private String billing;
	
	@JsonProperty("linkedin_public_url")
	private String linkedin_public_url;
	
	@JsonProperty("tag_with_company")
	private String tag_with_company;
	
	@JsonProperty("departmentName")
	private String departmentName;
	
	@JsonProperty("designation")
	private String designation;
	
	@JsonProperty("countryDialCode")
	private String countryDialCode;
	
	@JsonProperty("countryDialCode1")
	private String countryDialCode1;
	
	@JsonProperty("currency")
	private String currency;
		
	@JsonProperty( "active")
	private boolean active;
	
	@JsonProperty("dateOfBirth")
	private String dateOfBirth;
	
	@JsonProperty("idProof")
	private String idProof;
	
	@JsonProperty("idNumber")
	private String idNumber;
	
	@JsonProperty("skill")
	private List<SkillSetMapper> skill;
	
	@JsonProperty("education")
	private String education;
	
	@JsonProperty("experience")
	private float experience;
	
	@JsonProperty("language")
	private String language;
	
	@JsonProperty("workLocation")
	private String workLocation;
	
	@JsonProperty("workType")
	private String workType;

	@JsonProperty("gender")
	private String gender;
	
	@JsonProperty("nationality")
	private String nationality;
	
	@JsonProperty("fullName")
	private String fullName;

	@JsonProperty("country")
	private String country;
	
	@JsonProperty("imageId")
	private String imageId;
	
	@JsonProperty("partnerId")
	private String partnerId;
	
	@JsonProperty("designationTypeId")
	private String designationTypeId;
	
	@JsonProperty("departmentId")
	private String departmentId;
	
	@JsonProperty("currentCtc")
	private String currentCtc;
	
	@JsonProperty("roleType")
	private String roleType;

	@JsonProperty("roleTypeId")
	private String roleTypeId;
	
	@JsonProperty("noticePeriod")
	private long noticePeriod;
	
	@JsonProperty("partnerName")
	private String partnerName;
	
	@JsonProperty("skills")
	private List<String> skills;
	
	@JsonProperty("documentId")
	private String documentId;
	
	@JsonProperty("category")
	private String category;
	
	@JsonProperty("benifit")
	private String benifit;
	
	@JsonProperty("costType")
	private String costType;
	
	@JsonProperty("noticeDetail")
	private String noticeDetail;

	@JsonProperty("whatsApp")
	private String whatsApp;
	
	@JsonProperty("candidateIds")
	private List<String> candidateIds;
	
	@JsonProperty("website")
	private String website;

	@JsonProperty("workPreference")
	private String workPreference;
	
	@JsonProperty("channel")
	private String channel;
	
	@JsonProperty("allowSharing")
	private String allowSharing;
	
	@JsonProperty("empInd")
    private boolean empInd;
	
	@JsonProperty("shareInd")
    private boolean shareInd;
	
	@JsonProperty("videoClipsId")
	private String videoClipsId;
	
	@JsonProperty("currentCtcCurency")
	private String currentCtcCurency;
	
	@JsonProperty("partnerContact")
	private String partnerContact;
	
	@JsonProperty("sectorId")
	private String sectorId;
	
	@JsonProperty("sector")
	private String sector;
	
	@JsonProperty("liveInd")
    private boolean liveInd;
	
	@JsonProperty("reInStateInd")
	private boolean reInStateInd;
	
	@JsonProperty("tAndCInd")
	private boolean tAndCInd;
	
	@JsonProperty("preferredDistance")
	private int preferredDistance;
}
