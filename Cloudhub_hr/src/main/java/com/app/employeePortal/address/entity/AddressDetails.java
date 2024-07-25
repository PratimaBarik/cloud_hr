package com.app.employeePortal.address.entity;

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
@Table(name="address_details")
public class AddressDetails {

	@Id
	@GenericGenerator(name = "address_details_id", strategy = "com.app.employeePortal.address.generator.AddressDetailsGenerator")
	@GeneratedValue(generator = "address_details_id")
	
	
	
	@Column(name="address_details_id")
	private String id;
	
	@Column(name="address_id")
	private String addressId;
	
	@Column(name="address_type")
	private String addressType;
	
	@Column(name="address_line_1")
	private String addressLine1;
	
	@Column(name="address_line_2")
	private String addressLine2;
	
	@Column(name="town")
	private String town;
	
	@Column(name="street")
	private String street;
	
	@Column(name="city")
	private String city;
	
	@Column(name="postal_code")
	private String postalCode;
	
	@Column(name="state")
	private String state;  
	
	@Column(name="country")
	private String country;
	
	@Column(name="creation_date")
	private Date creationDate;
	
	@Column(name="creator_id")
	private String creatorId;
	
	
	@Column(name="live_ind")
	private boolean liveInd;
	
	@Column(name="longitude")
	private String longitude;
	
	@Column(name="latitude")
	private String latitude;
	
	@Column(name="house_no")
	private String houseNo;


	@Column(name="xl_address")
	private String xlAddress;


	@Column(name="primaryInd",nullable = false)
	private boolean primaryInd;



}
