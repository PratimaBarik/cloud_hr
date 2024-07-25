package com.app.employeePortal.organization.mapper;



import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.app.employeePortal.address.mapper.AddressMapper;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)

public class OrganizationMapper {
	
	@JsonProperty("organizationId")
	private String organizationId;
	
	//@NotEmpty(message="organization name should not be empty")
	@JsonProperty("organizationName")
	private String organizationName;
	
	@JsonProperty("creatorId")
	private String creatorId;
	
	@JsonProperty("organizationUrl")
	private String organizationUrl;
	
	@JsonProperty("fiscalStartMonth")
	private String fiscalStartMonth;
	
	@JsonProperty("fiscalStartDate")
	private String fiscalStartDate;
	
	@JsonProperty("tradeCurrency")
	private String tradeCurrency;
	
	@JsonProperty("baseCountry")
	private String baseCountry;
	
	@JsonProperty("companySize")
	private String companySize;
	
	@JsonProperty("industryType")
	private String industryType;
	
	@JsonProperty("address")
	private List<AddressMapper> address ;
	
	@JsonProperty("imageId")
	private String imageId;
	

	@JsonProperty("linkedinUrl")
	private String linkedinUrl;
	
	@JsonProperty("twitter")
	private String twitter;
	
	@JsonProperty("facebook")
	private String facebook;	
	
	@JsonProperty("revenue")
	private String revenue;
	
	@JsonProperty("countryDialCode")
	private String countryDialCode;
	
	@JsonProperty("countryDialCode1")
	private String countryDialCode1;
	
	@JsonProperty("phoneNo")
	private String phoneNo;
	
	@JsonProperty("mobileNo")
	private String mobileNo;	
	
	@JsonProperty("subscriptionType")
	private String subscriptionType;
	
	@JsonProperty("SubscriptionEndDate()")
	private String SubscriptionEndDate;

	@JsonProperty("vat")
	private String vat;
	
	@JsonProperty("creationDate")
	private String creationDate;
	
	@JsonProperty("industryId")
	private String industryId;
	
	@JsonProperty("bToB")
	private boolean bToB;
	
	@JsonProperty("industry")
	private String industry;
}
