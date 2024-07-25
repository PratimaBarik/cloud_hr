package com.app.employeePortal.investor.mapper;

import java.util.List;

import com.app.employeePortal.address.mapper.AddressMapper;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class InvestorMapper {
    @JsonProperty("investorId")
    private String investorId;

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


    @JsonProperty("investorIds")
    private List<String> investorIds;


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
    
    @JsonProperty("sourceUserId")
    private String sourceUserId;
    
    @JsonProperty("pvtAndIntunlInd")
	private boolean pvtAndIntunlInd;
    
    @JsonProperty("firstMeetingDate")
	private String firstMeetingDate;
}
