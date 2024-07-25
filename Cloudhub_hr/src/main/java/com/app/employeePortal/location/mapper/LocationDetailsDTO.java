package com.app.employeePortal.location.mapper;

import java.util.List;

import com.app.employeePortal.address.mapper.AddressMapper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LocationDetailsDTO {

   private String locationName;
   
   private String timeZone;
  
	private boolean productionInd;
	    
	private boolean inventoryInd;
	
	private boolean billingInd;
	    
	private boolean corporateInd;
	    
	private boolean projectInd;
	
	private boolean retailInd;
	 
	private String userId;
    
    private List<AddressMapper> address;
   
	private boolean activeInd;
	
	private String orgId;
	
    private String creationDate;

	private String updatedBy;
	
	private String updationDate;
	
	private String addressId;
	
	private boolean defaultLocInd;
	
	private boolean defaultBillToLocInd;
	
	private boolean prodManufactureInd;
	
	private String regionsId;
	
	private boolean thirdPartyInd;
	
	private String thirdPartyContactDpt;
	
	private String thirdPartyContact;

}
