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
@Table(name="website")

public class Website {
	
	@Id
	@GenericGenerator(name = "website_id", strategy = "com.app.employeePortal.recruitment.generator.WebsiteGenerator")
    @GeneratedValue(generator = "website_id")

	
	@Column(name="website_id")
	private String websiteId;
	

	@Column(name="url")
	private String url;
	
	@Column(name="ip")
	private String ip;
	
	@Column(name="org_id")
	private String orgId;

	@Column(name = "user_id")
	private String user_id;
	
	@Column(name = "assign_to_user_id")
	private String assignToUserId;
	
	@Column(name="last_updated_on")
	private Date lastUpdatedOn;
	

}
