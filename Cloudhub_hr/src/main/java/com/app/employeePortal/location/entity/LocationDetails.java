package com.app.employeePortal.location.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "location_details")
public class LocationDetails{

	@Id
	@GenericGenerator(name = "locationDetailsId", strategy = "com.app.employeePortal.location.generator.LocationDetailsGenerator")
	@GeneratedValue(generator = "locationDetailsId")

	@Column(name = "location_details_id")
	private String locationDetailsId;

	@Column(name = "location_name")
	private String locationName;

	@Column(name = "time_zone")
	private String timeZone;

	@Column(name = "production_ind")
	private boolean productionInd;

	@Column(name = "inventory_ind")
	private boolean inventoryInd;

	@Column(name = "billing_ind")
	private boolean billingInd;

	@Column(name = "corporate_ind")
	private boolean corporateInd;

	@Column(name = "project_ind")
	private boolean projectInd;

	@Column(name = "retail_ind")
	private boolean retailInd;
	
	@Column(name="default_loc_ind")
	private boolean defaultLocInd;
	
	@Column(name="default_bill_to_loc_ind")
	private boolean defaultBillToLocInd;
	
	@Column(name = "creation_date")
	private Date creationDate;
	
	@Column(name = "active_ind")
	private boolean activeInd;
	
	@Column(name = "updated_by")
	private String updatedBy;
	
	@Column(name = "updation_date")
	private Date updationDate;
	
	@Column(name = "org_id")
	private String orgId;
	
	@Column(name = "user_id")
	private String userId;
	
	@Column(name="address_id")
	private String addressId;
	
	@Column(name="country")
	private String country;
	
	@Column(name="prod_manufacture_Ind", nullable =false)
	private boolean prodManufactureInd =false;
	
	@Column(name="regionsId")
	private String regionsId;
	
	@Column(name="third_party_ind", nullable =false)
	private boolean thirdPartyInd =false;
	
	@Column(name="third_party_contact_dpt")
	private String thirdPartyContactDpt;

	@Column(name="third_party_contact")
	private String thirdPartyContact;

}
