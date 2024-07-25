package com.app.employeePortal.recruitment.entity;

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
@Getter
@Setter
@Entity
@Table(name="website_partner_link")
public class WebsitePartnerLink {

	@Id
	@GenericGenerator(name = "website_partner_link_id", strategy = "com.app.employeePortal.recruitment.generator.WebsitePartnerLinkGenerator")
    @GeneratedValue(generator = "website_partner_link_id")
	
	
	
	@Column(name="website_partner_link_id")
	private String websitePartnerLinkId;
	

	@Column(name="url")
	private String url;
	
	@Column(name="ip")
	private String ip;
	
	@Column(name="org_id")
	private String orgId;

	@Column(name = "user_id")
	private String user_id;
	
	@Column(name ="last_updated_on")
	private Date lastUpdatedOn;

	@Column(name = "assign_to_user_id")
	private String assignToUserId;
	
}
