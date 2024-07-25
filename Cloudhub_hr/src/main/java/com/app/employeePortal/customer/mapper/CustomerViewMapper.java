package com.app.employeePortal.customer.mapper;
import java.util.List;

import com.app.employeePortal.address.mapper.AddressMapper;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerViewMapper {
	
	@JsonProperty("customerId")
	private String customerId;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("url")
	private String url;
	
	@JsonProperty("notes")
	private String notes;
	
	@JsonProperty("phoneNumber")
	private String phoneNumber;
	
	@JsonProperty("organizationId")
	private String organizationId;
	
	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("group")
	private String group;
	
	@JsonProperty("vatNo")
	private String vatNo;
	
	@JsonProperty("email")
	private String email;
	
	@JsonProperty("creationDate")
	private String creationDate;
	
	@JsonProperty("documentId")
	private String documentId;
	
	@JsonProperty("country")
	private String country;
	
	@JsonProperty("sector")
	private String sector;
	
	@JsonProperty("sectorId")
	private String sectorId;
	
	@JsonProperty("countryAlpha2Code")
	private String country_alpha2_code;
	
	@JsonProperty("countryAlpha3Code")
	private String country_alpha3_code;
	
	@JsonProperty("zipcode")
	private String zipcode;
	
	@JsonProperty("address")
	private List<AddressMapper> address;

	@JsonProperty("ownerName")
	private String ownerName;
	
	@JsonProperty("ownerImageId")
	private String ownerImageId;

	@JsonProperty("countryDialCode")
	private String countryDialCode;
	
	@JsonProperty("category")
	private String category;
	
	@JsonProperty("latitude")
	private String latitude;
	
	@JsonProperty("longitude")
	private String longitude;
	
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
	
	@JsonProperty("gst")
	private float gst;
	
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
}
