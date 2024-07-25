package com.app.employeePortal.contact.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.app.employeePortal.config.AesEncryptor;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@ToString
@Entity
@Getter
@Setter
@Table(name = "contact_details")
public class ContactDetails {
	
	@Id
	@GenericGenerator(name = "contact_details_id", strategy = "com.app.employeePortal.contact.generator.ContactGenerator")
    @GeneratedValue(generator = "contact_details_id")
	
	@Column(name="contact_details_id")
	private String contact_details_id;
	
	@Column(name="contact_id")
	private String contactId;
	
	@Column(name="salutation")
	private String salutation;
	
	@Convert(converter = AesEncryptor.class)
	@Column(name="first_name")
	private String first_name;
	
	@Convert(converter = AesEncryptor.class)
	@Column(name="middle_name")
	private String middle_name;
	
	@Convert(converter = AesEncryptor.class)
	@Column(name="last_name")
	private String last_name;
	
	@Convert(converter = AesEncryptor.class)
	@Column(name="mobile_number")
	private String mobile_number;
	
	@Convert(converter = AesEncryptor.class)
	@Column(name="phone_number")
	private String phone_number;
	
	@Convert(converter = AesEncryptor.class)
	@Column(name="email_id")
	private String email_id;
	
	@Column(name="linkedin_public_url")
	private String linkedin_public_url;
	
	@Column(name="tag_with_company")
	private String tag_with_company;
	
	@Column(name="department")
	private String department;
	
	@Column(name="designation")
	private String designation;
	
	@Column(name="salary")
	private String salary;
	
	//@Lob
	//@Column(name="notes",columnDefinition = "Longtext")
	@Column(name="notes",length = 10000)
//	@Column(name="notes")
	private String notes;
	

	@Column(name="user_id")
	private String user_id;
	
	@Column(name="organization_id")
	private String organization_id;
	
	@Column(name="customerId")
	private String customerId;
	
	@Column(name = "creation_date")
	private Date creationDate;

	@Column(name = "live_ind")
	private boolean liveInd;
	
	
	@Column(name="country_dialcode")
	private String country_dialcode;
	
	@Column(name="country_dialcode1")
	private String country_dialcode1;
	
	@Column(name="contactType")
	private String contactType;
	
	@Convert(converter = AesEncryptor.class)
	@Column(name="full_name")
	private String fullName;
	
	@Column(name="image_id")
	private String imageId;
	
	@Column(name="sector")
	private String sector;
	
	@Column(name="access_ind")
	private int accessInd;

	@Column(name="contact_role")
    private String contactRole;
	
	@Convert(converter = AesEncryptor.class)
	@Column(name="whatsapp_number")
	private String whatsappNumber;
	
	@Column(name="source")
	private String source;
	
	@Column(name="sourceUserId")
	private String sourceUserId;

	@Column(name="bedroom")
	private String bedroom;

	@Column(name="price")
	private String price;

	@Column(name="property_type")
	private String propertyType;

	@Column(name="country")
	private String country;
}



