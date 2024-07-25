package com.app.employeePortal.contact.mapper;

import java.util.List;

//import org.codehaus.jackson.annotate.JsonProperty;

import com.app.employeePortal.address.mapper.AddressMapper;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ContactMapper {

	@JsonProperty("contactId")
	private String contactId;
	
	@JsonProperty("salutation")
	private String salutation;
	
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
	
	@JsonProperty("address")
	private List<AddressMapper> address;

	@JsonProperty("creationDate")
	private String creationDate;
	
	@JsonProperty("countryDialCode")
	private String countryDialCode;
	
	@JsonProperty("countryDialCode1")
	private String countryDialCode1;

	@JsonProperty("partnerId")
	private String partnerId;	

	@JsonProperty("opportunityId")
	private String opportunityId;

	@JsonProperty("fullName")
	private String fullName;
	
	@JsonProperty("imageId")
	private String imageId;
	
	@JsonProperty("designationTypeId")
	private String designationTypeId;
	
	@JsonProperty("departmentId")
	private String departmentId;
	
	@JsonProperty("sector")
	private String sector;
	
	@JsonProperty("sectorId")
	private String sectorId;
	
	@JsonProperty("contactIds")
	private List<String> contactIds;

	@JsonProperty("accessInd")
	private int accessInd;	
	
	@JsonProperty("whatsapp_number")
	private String whatsappNumber;
	
	@JsonProperty("leadsId")
	private String leadsId;
	
	@JsonProperty("investorLeadsId")
	private String investorLeadsId;

	@JsonProperty("investorId")
	private String investorId;

	@JsonProperty("invOpportunityId")
	private String invOpportunityId;
	
	@JsonProperty("sourceUserId")
	private String sourceUserId;
	
	@JsonProperty("source")
	private String source;

	@JsonProperty("bedrooms")
	private String bedrooms;

	@JsonProperty("price")
	private String price;

	@JsonProperty("propertyType")
	private String propertyType;

	@JsonProperty("country")
	private String country;

	@JsonProperty("contactType")
	private String contactType;
}
