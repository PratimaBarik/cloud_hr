package com.app.employeePortal.customer.mapper;
import java.util.List;

import com.app.employeePortal.address.mapper.AddressMapper;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
public class CustomerMapper {

	
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


	@JsonProperty("customerIds")
	private List<String> customerIds;
	

	@JsonProperty("imageURL")
    private String imageURL;
	
	@JsonProperty("assignedTo")
	private String assignedTo;
	
	@JsonProperty("businessRegistration")
	private String businessRegistration;
	
	@JsonProperty("gst")
	private float gst;
	
	@JsonProperty("source")
	private String source;
	
	@JsonProperty("type")
	private String type;
	
	@JsonProperty("sourceUserID")
	private String sourceUserID;

	@JsonProperty("potentialValue")
	private double potentialValue;

	@JsonProperty("currencyId")
	private String currencyId;
	
	@JsonProperty("assignedBy")
	private String assignedBy;
}