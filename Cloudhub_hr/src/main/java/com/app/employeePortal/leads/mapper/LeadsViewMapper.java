package com.app.employeePortal.leads.mapper;
import java.util.List;

import com.app.employeePortal.address.mapper.AddressMapper;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LeadsViewMapper {
	
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
	
	@JsonProperty("zipcode")
	private String zipcode;
	
//	@JsonProperty("address")
//	private List<AddressMapper> address;

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

	@JsonProperty("convertInd")
	private boolean convertInd;
	
	@JsonProperty("type")
	private String type;
	
	@JsonProperty("companyName")
	private String companyName;
	
	@JsonProperty("convertionDate")
	private String convertionDate;
	
	@JsonProperty("junkInd")
	private boolean junkInd;
	
	@JsonProperty("opportunityName")
	private String opportunityName;
	
	@JsonProperty("proposalValue")
	private String proposalValue;
	
	@JsonProperty("typeUpdationDate")
	private String typeUpdationDate;
	
	@JsonProperty("source")
	private String source;
	
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
	
	@JsonProperty("CreationType")
	private String CreationType;
	
	@JsonProperty("assignedBy")
	private String assignedBy;

	@JsonProperty("bedrooms")
	private String bedrooms;

	@JsonProperty("price")
	private String price;

	@JsonProperty("propertyType")
	private String propertyType;
	
	@JsonProperty("lobId")
	private String lobId;
	
	@JsonProperty("lob")
	private String lob;

	@JsonProperty("countryAlpha2Code")
	private String country_alpha2_code;

	@JsonProperty("countryAlpha3Code")
	private String country_alpha3_code;
}
