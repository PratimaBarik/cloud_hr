package com.app.employeePortal.investor.entity;

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
@Table(name = "investor_detail")
public class Investor {
    @Id
    @GenericGenerator(name = "investor_id", strategy = "com.app.employeePortal.investor.generator.InvestorGenerator")
    @GeneratedValue(generator = "investor_id")

    @Column(name="investor_id")
    private String investorId;

    @Convert(converter = AesEncryptor.class)
    @Column(name="name")
    private String name;

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

    @Column(name="categoryyyy")
    private String categoryyyy;

    @Column(name="gst")
    private float gst;

    @Column(name="updated_on")
    private Date updateDate;

    @Column(name="source")
    private String source;
    
    @Column(name="sourceUserId")
    private String sourceUserId;

    @Column(name="assigned_by")
    private String assignedBy;
    
    @Column(name="pvt_and_intunl_ind", nullable =false)
	private boolean pvtAndIntunlInd =false;

    @Column(name="club")
    private String club;
    
    @Column(name="first_meeting_date")
	private Date firstMeetingDate;
}
