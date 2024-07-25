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

@Entity
@Getter
@Setter
@Table(name="opportunity_recruiter_link")
public class OpportunityRecruiterLink {
	@Id
	@GenericGenerator(name = "opportunity_recruiter_link_id", strategy = "com.app.employeePortal.recruitment.generator.OpportunityRecruiterLinkGenerator")
	@GeneratedValue(generator = "opportunity_recruiter_link_id")
	
	@Column(name = "opportunity_recruiter_link_id")
	private String opportunity_recruiter_link_id;
	
	@Column(name = "recruiter_id")
	private String recruiter_id;
	
	@Column(name = "opportunity_id")
	private String opportunity_id;
	
	@Column(name="creation_date")
	private Date creation_date;
	
	@Column(name="live_ind")
	private boolean live_ind;

}
