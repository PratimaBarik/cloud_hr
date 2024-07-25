package com.app.employeePortal.organization.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@ToString
@Entity
@Getter
@Setter
@Table(name="organization_details")
public class OrganizationDetails {
	

	@Id
	@GenericGenerator(name = "organization_details_id", strategy = "com.app.employeePortal.organization.generator.OrganizationDetailsGenerator")
	@GeneratedValue(generator = "organization_details_id")
	
	@Column(name="organization_details_id")
	private String organization_details_id;
	
	@Column(name="organization_id")
	private String organization_id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="url")
	private String url;
	
	
	@Column(name="fiscal_start_month")
	private String fiscal_start_month;
	
	@Column(name="fiscal_start_date")
	private String fiscal_start_date;
	
	@Column(name="trade_currency")
	private String trade_currency;
	
	@Column(name="company_size")
	private String company_size;
	
	@Column(name="industry_type")
	private String industry_type;
	
	@Column(name="base_country")
	private String base_country;
	
    @Column(name="creation_date")
	private Date creation_date;
	
	@Column(name="creator_id")
	private String creator_id;
	
	@Column(name="image_id")
	private String image_id;
	
	@Column(name="twitter")
	private String twitter;
	
	@Column(name="linkedin_url")
	private String linkedin_url;
	
	@Column(name="facebook")
	private String facebook;
		
	@Column(name="revenue")
	private String revenue;
			
	@Column(name="live_ind")
	private boolean live_ind;	

	@Column(name="country_code")
	private String country_code;
	
	@Column(name="country_code1")
	private String country_code1;

	@Column(name="phone_number")
	private String phone_number;
	
	@Column(name="mobile_number")
	private String mobile_number;
	
	@Column(name="vat")
	private String vat;
	
	@Column(name="btob_ind", nullable =false)
	private boolean bToBInd= false;
	
	@Column(name="industryId")
	private String industryId;

	@Column(name="type",columnDefinition = "VARCHAR(255) default 'Child'")
	private String type;
}
