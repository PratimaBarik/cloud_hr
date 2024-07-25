package com.app.employeePortal.investorleads.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
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
@Table(name = "investor_leads_details")
public class InvestorLeads {

	@Id
	@GenericGenerator(name = "investor_leads_id", strategy = "com.app.employeePortal.investorleads.generator.InvestorLeadsGenerator")
    @GeneratedValue(generator = "investor_leads_id")
	
	@Column(name="investor_leads_id")
	private String investorLeadsId;
	
	@Convert(converter = AesEncryptor.class)
	@Column(name="name")
	private String name;
	
	@Column(name="salutation")
	private String salutation;
	
	@Convert(converter = AesEncryptor.class)
	@Column(name="first_name")
	private String firstName;
	
	@Convert(converter = AesEncryptor.class)
	@Column(name="middle_name")
	private String middleName;
	
	@Convert(converter = AesEncryptor.class)
	@Column(name="last_name")
	private String lastName;
	
	@Column(name="image_id")
	private String imageId;
	
	@Convert(converter = AesEncryptor.class)
	@Column(name="url")
	private String url;
	
	@Lob
	@Column(name="notes")
	private String notes;
	
	@Convert(converter = AesEncryptor.class)
	@Column(name="phone_number")
	private String phoneNumber;
	
	@Column(name="organization_id")
	private String organizationId;
	
	@Column(name="user_id")
	private String userId;
	
	@Column(name = "creation_date")
	private Date creationDate;

	@Column(name = "live_ind")
	private boolean liveInd;
	
	@Convert(converter = AesEncryptor.class)
	@Column(name="email")
	private String email;
	
	@Column(name="vatNo")
	private String vatNo; 
	
	@Column(name="document_id")
	private String documentId;
	
	@Column(name="group1")
	private String group;
	
	@Column(name="country")
	private String country;
	
	@Column(name="sector")
	private String sector;
	
	@Column(name="zipcode")
	private String zipcode;
	
	@Column(name="country_dialCode")
	private String countryDialCode;
	
	@Column(name="category")
	private String category;
	
	@Column(name="image_url")
    private String imageURL;
		
	@Column(name="assigned_to")
	private String assignedTo;
	
	@Column(name="business_registration")
	private String businessRegistration;
	
	@Column(name="convert_ind")
	private boolean convertInd;
	
	@Column(name = "convertion_date")
	private Date convertionDate;
	
	@Column(name="type")
	private String type;
	
	@Column(name="company_name")
	private String companyName;
	
	@Column(name = "type_updation_date")
	private Date typeUpdationDate;
	
	@Column(name="junk_ind")
	private boolean junkInd;
	
	@Column(name = "junked_date")
	private Date junkedDate;
	
	@Column(name="source")
	private String source;
	
	@Column(name="sourceUserId")
	private String sourceUserId;
	
	@Column(name="assigned_by")
	private String assignedby;
	
	@Column(name="pvt_and_intunl_ind", nullable =false)
	private boolean pvtAndIntunlInd =false;
	
	@Column(name="unit_of_share", nullable =false)
	private double unitOfShare;
	
	@Column(name="value_of_share", nullable =false)
	private double valueOfShare;
	
	@Column(name="total_share_value", nullable =false)
	private double totalShareValue;
	
	@Column(name="share_currency")
	private String shareCurrency;
	
	@Column(name="first_meeting_date")
	private Date firstMeetingDate;
}
