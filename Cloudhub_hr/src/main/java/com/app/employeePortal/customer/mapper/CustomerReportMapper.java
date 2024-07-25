package com.app.employeePortal.customer.mapper;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerReportMapper {
	
	@JsonProperty("customerId")
	private String customerId;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("url")
	private String url;
	
	@JsonProperty("phoneNumber")
	private String phoneNumber;
	
	@JsonProperty("organizationId")
	private String organizationId;
	
	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("vatNo")
	private String vatNo;
	
	@JsonProperty("email")
	private String email;
	
	@JsonProperty("creationDate")
	private String creationDate;
	
	@JsonProperty("sector")
	private String sector;
	
	@JsonProperty("sectorId")
	private String sectorId;
	
	@JsonProperty("address")
	private List<CustomerAddressResponseMapper> address;

	@JsonProperty("ownerName")
	private String ownerName;
	
	@JsonProperty("ownerImageId")
	private String ownerImageId;

	@JsonProperty("countryDialCode")
	private String countryDialCode;
	
	@JsonProperty("imageURL")
    private String imageURL;
	
	@JsonProperty("lastRequirementOn")
    private String lastRequirementOn;
	
	@JsonProperty("assignedTo")
	private String assignedTo;
 
	@JsonProperty("businessRegistration")
	private String businessRegistration;
	
	@JsonProperty("skill")
	private List<CustomerSkillLinkMapper> skill;
	
	@JsonProperty("oppNo")
	private int oppNo;
	
	@JsonProperty("source")
	private String source;
	
	@JsonProperty("totalProposalValue")
	private double totalProposalValue;

	@JsonProperty("type")
	private String type;
	
	@JsonProperty("pageCount")
	private int pageCount;
	
	@JsonProperty("dataCount")
	private int dataCount;
	
	@JsonProperty("listCount")
	private long listCount;

	@JsonProperty("sourceUserID")
	private String sourceUserID;
	
	@JsonProperty("sourceUserName")
	private String sourceUserName;
}
