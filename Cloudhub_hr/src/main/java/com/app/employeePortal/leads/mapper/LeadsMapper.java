package com.app.employeePortal.leads.mapper;
import java.util.List;

import javax.persistence.Column;

import com.app.employeePortal.address.mapper.AddressMapper;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class LeadsMapper {

	
	@JsonProperty("leadsId")
	private String leadsId;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("imageId")
	private String imageId;
	
	@JsonProperty("salutation")
	private String salutation;
	
	@JsonProperty("firstName")
	private String firstName;
	
	@JsonProperty("middleName")
	private String middleName;
	
	@JsonProperty("lastName")
	private String lastName;
	
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
	
	@JsonProperty("documentId")
	private String documentId;
	
	@JsonProperty("country")
	private String country;
	
	@JsonProperty("sector")
	private String sector;
	
	@JsonProperty("sectorId")
	private String sectorId;
	
	@JsonProperty("zipcode")
	private String zipcode;
	
	@JsonProperty("countryDialCode")
	private String countryDialCode;
	
	@JsonProperty("category")
	private String category;
	
	@JsonProperty("address")
	private List<AddressMapper> address;
	
	@JsonProperty("imageURL")
    private String imageURL;
	
	@JsonProperty("assignedTo")
	private String assignedTo;
	
	@JsonProperty("businessRegistration")
	private String businessRegistration;
	
	@JsonProperty("convertInd")
	private boolean convertInd;
	
	@JsonProperty("type")
	private String type;
	
	@JsonProperty("companyName")
	private String companyName;
	
	@JsonProperty("opportunityName")
	private String opportunityName;
	
	@JsonProperty("proposalValue")
	private String proposalValue;
	
	@JsonProperty("leadsIds")
	private List<String> leadsIds;
	
	@JsonProperty("source")
	private String source;
	
	@JsonProperty("sourceUserId")
	private String sourceUserId;
	
	@JsonProperty("assigned_by")
	private String assignedBy;

	@JsonProperty("bedrooms")
	private String bedrooms;

	@JsonProperty("price")
	private String price;

	@JsonProperty("propertyType")
	private String propertyType;
	
	@JsonProperty("lob_id")
	private String lobId;
	
}