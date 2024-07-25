package com.app.employeePortal.contact.mapper;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import com.app.employeePortal.address.mapper.AddressMapper;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContactViewMapper {
	
	@JsonProperty("contactId")
	private String contactId;
	
	@JsonProperty("name")
	private String name;
	
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
	
	@JsonProperty("linkedinPublicUrl")
	private String linkedinPublicUrl;
	
	@JsonProperty("tagWithCompany")
	private String tagWithCompany;
	
	@JsonProperty("department")
	private String department;
	
	@JsonProperty("designation")
	private String designation;

	@JsonProperty("salary")
	private String salary;
	
	@JsonProperty("notes")
	private String notes;
	
	@JsonProperty("userId")
	private String userId;
	
	@JsonProperty("organizationId")
	private String organizationId;
	
	@JsonProperty("customerId")
	private String customerId;
	
//	@JsonProperty("address")
//	private List<AddressMapper> address;

	@JsonProperty("creationDate")
	private String creationDate;
	
	@JsonProperty("countryDialCode")
	private String countryDialCode;
	
	@JsonProperty("countryDialCode1")
	private String countryDialCode1;
	
	@JsonProperty("contactType")
	private String contactType;

	@JsonProperty("fullName")
	private String fullName;
	
	@JsonProperty("imageId")
	private String imageId;
	
	@JsonProperty("ownerName")
	private String ownerName;
	
	@JsonProperty("ownerImageId")
	private String ownerImageId;
	
	@JsonProperty("designationTypeId")
	private String designationTypeId;
	
	@JsonProperty("departmentId")
	private String departmentId;
	
	@JsonProperty("sector")
	private String sector;
	
	@JsonProperty("sectorId")
	private String sectorId;

	@JsonProperty("accessInd")
	private int accessInd;
	
	@JsonProperty("ThirdPartyAccessInd")
	private boolean ThirdPartyAccessInd;
	
	@JsonProperty("closer")
	private float closer;

	@JsonProperty("contactRole")
    private String contactRole;
	
	@JsonProperty("lastRequirementOn")
    private String lastRequirementOn;
	
	@JsonProperty("partnerId")
	private String partnerId;
	
	@JsonProperty("whatsapp_number")
	private String whatsappNumber;
	
	@JsonProperty("leadsId")
	private String leadsId;
	
	@JsonProperty("oppNo")
	private int oppNo;
	
	@JsonProperty("totalProposalValue")
	private double totalProposalValue;
	
	@JsonProperty("pageCount")
	private int pageCount;
	
	@JsonProperty("dataCount")
	private int dataCount;
	
	@JsonProperty("listCount")
	private long listCount;

	@JsonProperty("sourceUserId")
	private String sourceUserId;
	
	@JsonProperty("source")
	private String source;
	
	@JsonProperty("sourceUserName")
	private String sourceUserName;

	@JsonProperty("bedrooms")
	private String bedrooms;

	@JsonProperty("price")
	private String price;

	@JsonProperty("propertyType")
	private String propertyType;

	@JsonProperty("country")
	private String country;

	@JsonProperty("countryAlpha2Code")
	private String country_alpha2_code;

	@JsonProperty("countryAlpha3Code")
	private String country_alpha3_code;
}
