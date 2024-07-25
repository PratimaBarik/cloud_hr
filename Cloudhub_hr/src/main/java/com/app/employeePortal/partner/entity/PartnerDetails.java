package com.app.employeePortal.partner.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="partner_details")
public class PartnerDetails {
	@Id
	@GenericGenerator(name = "partner_details_id", strategy = "com.app.employeePortal.partner.generator.PartnerDetailsGenerator")
    @GeneratedValue(generator = "partner_details_id")
	
	@Column(name="partner_details_id")
	private String partner_details_id;
	
	@Column(name="partner_id")
	private String partnerId;
	
	@Column(name="creator_id")
	private String creatorId;
	
	@Column(name="partner_name")
	private String partnerName;
	
	@Column(name="image_id")
	private String imageId;
	
	
	@Column(name="url")
	private String url;
	
	@Column(name="phoneNo")
	private String phoneNo;
	
	
	@Column(name="eMail")
	private String email;
	
	/*
	 * @Column(name="full_name") private String fullName;
	 */
	
	
	@Column(name="live_ind")
	private boolean liveInd;
	
	@Column(name="tax_registration_number")
	private String taxRegistrationNumber;
	
	@Column(name="business_registration_number")
	private String businessRegistrationNumber;
	
	@Column(name="account_number")
	private String accountNumber;
	
	@Column(name="bank_name")
	private String bankName;
	
	@Lob
	@Column(name="note")
	private String note;
	
	@Column(name="country")
	private String country;
	
	@Column(name="sector")
	private String sector;

	@Column(name="tnc_ind")
	private String TncInd;
	
	@Column(name="user_id")
	private String userId;
	
	@Column(name="org_id")
	private String orgId;
	
	@Column(name = "creation_date")
	private Date creationDate;
	
	@Column(name="status")
	private boolean status;
	
	@Column(name="country_dialcode")
	private String countryDialcode;
	
	@Column(name="image_url")
    private String imageURL;
	
	@Column(name="assigned_To")
    private String assignedTo;
	
	@Column(name="document")
    private String document;
	
	@Column(name="document_share_ind", nullable =false)
	private boolean documentShareInd =false;
	
	@Column(name="re_in_state_ind", nullable = false)
	private boolean reInStateInd =false;
	
	@Column(name="channel")
    private String channel;
}
