package com.app.employeePortal.registration.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Table(name="country")
public class Country {
	@Id
	@Column(name="country_id")
	private String country_id;
	
	@Column(name="country_name")
	private String countryName;

	@Column(name="country_alpha2_code")
	private String country_alpha2_code;

	@Column(name="country_alpha3_code")
	private String country_alpha3_code;

	@Column(name="country_dial_code")
	private String country_dial_code;

	@Column(name ="country_currency_name")
	private String country_currency_name;

	@Column(name="country_currency_code")
	private String country_currency_code;

	@Column(name="country_flag")
	private String country_flag;

	@Column(name="language")
	private String language;

	@Column(name="latitude")
	private String latitude;

	@Column(name="longitude")
	private String longitude;

	@Column(name="capital")
	private String capital;

	@Column(name="mandatory_ind", nullable =false)
	private boolean mandatoryInd=false;

	@Column(name = "updation_date")
	private Date updationDate;

	@Column(name="updated_by")
	private String updatedBy;
	
	@Column(name="sales_ind", nullable =false)
	private boolean salesInd=false;
	
	@Column(name="org_id")
	private String orgId;
}
