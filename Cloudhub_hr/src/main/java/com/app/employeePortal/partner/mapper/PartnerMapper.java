package com.app.employeePortal.partner.mapper;

import java.util.List;

import com.app.employeePortal.address.mapper.AddressMapper;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;



@Getter
@Setter

public class PartnerMapper {
	
	@JsonProperty("partnerId")
	private String partnerId;
	
	@JsonProperty("partnerName")
	private String partnerName;
	
	@JsonProperty("url")
	private String url;
	
	@JsonProperty("country")
	private String country;
	
	@JsonProperty("sector")
	private String sector;
	
	@JsonProperty("sectorId")
	private String sectorId;
	
	@JsonProperty("creatorId")
	private String creatorId;
	
	@JsonProperty("opportunityId")
	private String opportunityId;
	
	@JsonProperty("creationDate")
	private String creationDate;
	
	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("organizationId")
	private String organizationId;
	
	@JsonProperty("address")
	private List<AddressMapper> address ;
	
	@JsonProperty("phoneNo")
	private String phoneNo;
	
	@JsonProperty("email")
	private String email;
	
	@JsonProperty("taxRegistrationNumber")
	private String taxRegistrationNumber;
	
	@JsonProperty("businessRegistrationNumber")
	private String businessRegistrationNumber;
	
	@JsonProperty("accountNumber")
	private String accountNumber;
	
	@JsonProperty("bankName")
	private String bankName;
	
	@JsonProperty("note")
	private String note;
	
	@JsonProperty("tncInd")
	private String tncInd;
	
	@JsonProperty("status")
	private boolean status;
	
	/*
	 * @JsonProperty("fullName") private String fullName;
	 */
	
	
	@JsonProperty("skill")
	private List<PartnerSkillSetMapper> skill;
	
	@JsonProperty("skills")
	private List<String> skills;
	
	@JsonProperty("ownerName")
	private String ownerName; 
	 
	@JsonProperty("ownerImageId")
	private String ownerImageId;

	@JsonProperty("countryDialCode")
	private String countryDialCode;
	
	@JsonProperty("partnerIds")
	private List<String> partnerIds;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("imageURL")
    private String imageURL;
	
	@JsonProperty("assignedTo")
    private String assignedTo;;
    
    @JsonProperty("document")
    private String document;
    
    @JsonProperty("documentShareInd")
	private boolean documentShareInd;
    
    @JsonProperty("reInStateInd")
	private boolean reInStateInd;
    
    @JsonProperty("channel")
    private String channel;
    
    @JsonProperty("pageCount")
	private int pageCount;
	
	@JsonProperty("dataCount")
	private int dataCount;
	
	@JsonProperty("listCount")
	private long listCount;

}
