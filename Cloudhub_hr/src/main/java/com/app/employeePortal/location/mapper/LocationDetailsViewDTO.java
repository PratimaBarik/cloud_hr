package com.app.employeePortal.location.mapper;

import java.util.List;

import com.app.employeePortal.address.mapper.AddressMapper;
import com.app.employeePortal.location.entity.LocationDetails;
import com.app.employeePortal.util.Utility;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter

@Setter

@NoArgsConstructor

@AllArgsConstructor

@Builder
public class LocationDetailsViewDTO {
	
	 @JsonProperty("locationDetailsId")
	private String locationDetailsId;
	 
	 @JsonProperty("locationName")
    private String locationName;
    
    @JsonProperty("timeZone")
    private String timeZone;
  
	@JsonProperty("productionInd")
	private boolean productionInd;
	    
	@JsonProperty("inventoryInd")
	private boolean inventoryInd;
	    
	@JsonProperty("billingInd")
	private boolean billingInd;
	    
	@JsonProperty("corporateInd")
	private boolean corporateInd;
	    
	@JsonProperty("projectInd")
	private boolean projectInd;
	
	@JsonProperty("retailInd")
	private boolean retailInd;
	
	@JsonProperty("creationDate")
    private String creationDate;
	
	@JsonProperty("activeInd")
	private boolean activeInd;
	
	@JsonProperty("address")
	private List<AddressMapper> address;
	
	@JsonProperty("orgId")
	private  String orgId;
	
	@JsonProperty("userId")
	private String userId;

	@JsonProperty("updatedBy")
	private String updatedBy;
	
	@JsonProperty("updationDate")
	private String updationDate;
	
	@JsonProperty("country_name")
	private String country_name;

	private boolean defaultLocInd;
	
	private boolean defaultBillToLocInd;
	
	@JsonProperty("countryAlpha2Code")
	private String country_alpha2_code;
	
	@JsonProperty("countryAlpha3Code")
	private String country_alpha3_code;
	
	@JsonProperty("country_id")
	private String country_id;
	
	@JsonProperty("prodManufactureInd")
	private boolean prodManufactureInd;
	
	@JsonProperty("regionsId")
	private String regionsId;
	
	@JsonProperty("regions")
	private String regions;
	
	@JsonProperty("thirdPartyInd")
	private boolean thirdPartyInd;
	
	@JsonProperty("thirdPartyContactDpt")
	private String thirdPartyContactDpt;

	@JsonProperty("thirdPartyContact")
	private String thirdPartyContact;
    
	 public static LocationDetailsViewDTO from(LocationDetails locationDetails) {
		 LocationDetailsViewDTO viewDTO;
	        if (locationDetails == null) {
	            viewDTO = null;
	        } else {
	        	String creationDate= null;
	        	if(null!=locationDetails.getCreationDate()) {
	        		
	        		creationDate = Utility.getISOFromDate(locationDetails.getCreationDate());
	        	}
	  
	        	viewDTO = LocationDetailsViewDTO.builder()
	        			.locationDetailsId(locationDetails.getLocationDetailsId())
	        			.locationName(locationDetails.getLocationName())
	        			.inventoryInd(locationDetails.isInventoryInd())
	        			.activeInd(locationDetails.isActiveInd())
	        			.corporateInd(locationDetails.isCorporateInd())
	        			.billingInd(locationDetails.isBillingInd())
	        			.productionInd(locationDetails.isProductionInd())
	        			.retailInd(locationDetails.isRetailInd())
	        			.projectInd(locationDetails.isProjectInd())
	        			.defaultBillToLocInd(locationDetails.isDefaultBillToLocInd())
	        			.defaultLocInd(locationDetails.isDefaultLocInd())
	        			.creationDate(creationDate)
	        			.orgId(locationDetails.getOrgId())
	        			.userId(locationDetails.getUserId())
	        			.country_name(locationDetails.getCountry())
	        			.prodManufactureInd(locationDetails.isProdManufactureInd())
	        			.thirdPartyInd(locationDetails.isThirdPartyInd())
	        			.build();
	        }
	        return viewDTO;
	    }




}
