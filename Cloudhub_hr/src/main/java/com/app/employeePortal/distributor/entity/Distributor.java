package com.app.employeePortal.distributor.entity;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.app.employeePortal.Opportunity.entity.Auditable;
import com.app.employeePortal.config.AesEncryptor;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "distributor")

public class Distributor extends Auditable {

	@Id
	@GenericGenerator(name = "id", strategy = "com.app.employeePortal.distributor.generator.DistributorGenerator")
	@GeneratedValue(generator = "id")

	@Column(name = "distributor_id")
	private String id;

	@Convert(converter = AesEncryptor.class)
	@Column(name = "name")
	private String name;

	@Column(name = "image_id")
	private String imageId;

	@Convert(converter = AesEncryptor.class)
	@Column(name = "url")
	private String url;

	@Convert(converter = AesEncryptor.class)
	@Column(name = "phone_no")
	private String phoneNo;

	@Convert(converter = AesEncryptor.class)
	@Column(name = "mobile_no")
	private String mobileNo;

	@Column(name = "description")
	private String description;

	@Column(name = "dial_code")
	private String dialCode;

	@Convert(converter = AesEncryptor.class)
	@Column(name = "email_id")
	private String emailId;

	@Column(name = "reason")
	private String reason;

	@Column(name = "country_id")
	private String countryId;

	@Column(name = "country_name")
	private String countryName;

	@Column(name = "country_value")
	private String countryValue;

	@Column(name = "currency")
	private String currency;

	@Column(name = "currency_price")
	private float currencyPrice;

	@Column(name = "vat_ind")
	private boolean vatInd;

	@Column(name = "insurace_grade")
	private String insuranceGrade;

	@Column(name = "group_id")
	private String groupId;

	@Column(name = "assign_to")
	private String assignTo;

	@Column(name = "client_id")
	private String clientId;

	@Column(name = "client_name")
	private String clientName;

	@Column(name = "payment")
	private int payment;

	@Column(name = "user_id")
	private String userId;

	@Column(name = "org_id")
	private String orgId;

	@Column(name = "image_url")
	private String imageURL;

	@Column(name = "dcategory")
	private String dCategory;
	
	@Column(name = "type")
	private String type;
	
	@Column(name = "club")
	private String club;
	
	@Column(name = "investor")
	private String investor;

	/*
	 * sb @OneToMany(mappedBy = "distributor", cascade = CascadeType.ALL, fetch =
	 * FetchType.LAZY) private List<DistributorAddressLink> addresses;
	 * 
	 * @OneToMany(mappedBy = "distributor", cascade = CascadeType.ALL, fetch =
	 * FetchType.LAZY) private List<DistributorShipToAddressLink> shipToAddresses;
	 * 
	 * @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	 * 
	 * @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name =
	 * "user_distributor_fk")) private User user;
	 * 
	 * @OneToMany(mappedBy = "distributor", cascade = CascadeType.ALL, fetch =
	 * FetchType.LAZY) private List<DistributorNotesLink> notes;
	 * 
	 * @OneToMany(mappedBy = "distributor", cascade = CascadeType.ALL, fetch =
	 * FetchType.LAZY) private List<EventDistributorLink> events; sb
	 */

//    @OneToMany(mappedBy = "distributor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//   	private List<DistributorFeedbackLink> feedbacks;
//   	

	/*
	 * sb @OneToMany(mappedBy = "distributor", cascade = CascadeType.ALL, fetch =
	 * FetchType.LAZY) private List<TaskDistributorLink> tasks;
	 * 
	 * @OneToMany(mappedBy = "distributor", cascade = CascadeType.ALL, fetch =
	 * FetchType.LAZY) private List<CallDistributorLink> calls; sb
	 */
//    
//    @OneToMany(mappedBy = "distributor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	private List<OrderDistributorLink> orders;

	// @OneToMany(mappedBy = "distributor", cascade = CascadeType.ALL, fetch =
	// FetchType.LAZY)
	// private List<DistributorDocumentLink> document;

//    @OneToMany(mappedBy = "distributor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//   	private List<DistributorContactPersonLink> contactPerson;
//
//    @OneToMany(mappedBy = "distributor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//   	private List<QuoteDistributorLink> quotes;
//    
}